/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.jsslab.web.controller;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.openmrs.Concept;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.LocationTag;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "module/jsslab/admin/settings")
public class JssLabAdminSettingsController {

	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/jsslab/admin/settings";
	
	/**
	 * Initially called to fill the model with relevant data  
	 * @throws IOException 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void showForm(ModelMap model) throws IOException {
		
		Concept labSet = Context.getConceptService().getConcept("33333121");
		List<Concept> conceptSets = labSet.getSetMembers();
		
		GlobalProperty gpOrderType = Context.getAdministrationService().getGlobalPropertyObject("jsslab.laborder.type");
		GlobalProperty gpOrderIdPattern = Context.getAdministrationService().getGlobalPropertyObject("jsslab.laborder.idPattern");
		GlobalProperty gpSpecimenIdPattern = Context.getAdministrationService().getGlobalPropertyObject("jsslab.labspecimen.idPattern");
		GlobalProperty gpReportIdPattern= Context.getAdministrationService().getGlobalPropertyObject("jsslab.labreport.idPattern");
		
		model.addAttribute("conceptSets", conceptSets);
		model.addAttribute("json", getHierarchyAsJson());
		model.addAttribute("gpOrderType", gpOrderType);
		model.addAttribute("gpOrderIdPattern", gpOrderIdPattern);
		model.addAttribute("gpSpecimenIdPattern", gpSpecimenIdPattern);
		model.addAttribute("gpReportIdPattern", gpReportIdPattern);
	}
	
	@RequestMapping(value = "/saveGlobalProperties", method = RequestMethod.POST)
	@ResponseBody
	public SimpleObject saveGlobalProperties(HttpServletRequest request, 
			@RequestParam(value = "labOrderType", required = false) String labOrderType,
			@RequestParam(value = "labOrderIdPattern", required = false) String labOrderIdPattern,
			@RequestParam(value = "labSpecimenIdPattern", required = false) String labSpecimenIdPattern,
			@RequestParam(value = "labReportIdPattern", required = false) String labReportIdPattern) {

		SimpleObject o = new SimpleObject();
		for (Object param : request.getParameterMap().entrySet()) {
				Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) param;
				GlobalProperty gp = Context.getAdministrationService().getGlobalPropertyObject(entry.getKey());
				if (gp != null) {
					gp.setPropertyValue(entry.getValue()[0]);
					Context.getAdministrationService().saveGlobalProperty(gp);
				} else {
					o.put("code", "failure");
					o.put("message", Context.getMessageSourceService().getMessage("jsslab.settings.globalproperties.result.failed"));
					return o;
				}
		}
		o.put("code", "success");
		o.put("message", Context.getMessageSourceService().getMessage("jsslab.settings.globalproperties.result.success"));
		return o;
	}
	
	
	@RequestMapping(value = "/getConceptsByConceptSet", method = RequestMethod.GET)
	@ResponseBody
	public SimpleObject getConceptsBySet(HttpServletRequest request,
			@RequestParam(value = "setUuid") String uuid) throws ResponseException {
		
		Concept memberOf = Context.getConceptService().getConceptByUuid(uuid);
//		memberOf.getSetMembers()
		List<Concept> setMembers = Context.getConceptService().getConceptsByConceptSet(memberOf);
		RequestContext context = RestUtil.getRequestContext(request, Representation.FULL);
		PageableResult result = new NeedsPaging<Concept>(setMembers, context);
		return result.toSimpleObject();
	}
	
	/**
	 * Gets JSON formatted for jstree jquery plugin
	 * [
	 *   { data: ..., children: ...},
	 *   ...
	 * ]
	 * 
	 * @return
	 * @throws IOException
	 */
	private String getHierarchyAsJson() throws IOException {
		// TODO fetch all locations at once to avoid n+1 lazy-loads
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Location loc : Context.getLocationService().getAllLocations()) {
			if (loc.getParentLocation() == null) {
				list.add(toJsonHelper(loc));
			}
		}
		
		// If this gets slow with lots of locations then switch out ObjectMapper for the
		// stream-based version. (But the TODO above is more likely to be a performance hit.)
		StringWriter w = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(w, list);
		return w.toString();
	}
	
	/**
	 * { data: "Location's name (tags)",
	 *   children: [ recursive calls to this method, ... ] }
	 * 
	 * @param loc
	 * @return
	 */
	private Map<String, Object> toJsonHelper(Location loc) {
		Map<String, Object> ret = new LinkedHashMap<String, Object>();
		StringBuilder sb = new StringBuilder(loc.getName());
		if (loc.getTags() != null && loc.getTags().size() > 0) {
			sb.append(" (");
			for (Iterator<LocationTag> i = loc.getTags().iterator(); i.hasNext();) {
				LocationTag t = i.next();
				sb.append(t.getName());
				if (i.hasNext())
					sb.append(", ");
			}
			sb.append(")");
		}
		ret.put("data", sb.toString());
		if (loc.getChildLocations() != null && loc.getChildLocations().size() > 0) {
			List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
			for (Location child : loc.getChildLocations())
				children.add(toJsonHelper(child));
			ret.put("children", children);
		}
		return ret;
	}
}
