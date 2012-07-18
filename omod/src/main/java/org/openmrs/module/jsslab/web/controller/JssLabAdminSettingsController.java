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
	
	@PostConstruct
	public void init() {
		
	}
//	@ModelAttribute("instrument")
//	public LabInstrument formBackingObject(WebRequest request) {
//		String instrumentId = request.getParameter("instrumentId");
//		LabInstrument instrument = null;
//		instrument = labManagementService.getLabInstrument(instrumentId);
//
//		if (instrument == null) {
//			instrument = new LabInstrument();
//		}
//		return instrument;
//	}
	
	/**
	 * Initially called after the formBackingObject method to get the landing form name  
	 * @return String form view name
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
	
	@RequestMapping(value = "/saveGlobalProperties.form", method = RequestMethod.POST)
	@ResponseBody
	public String saveGlobalProperties(HttpServletRequest request, 
			@RequestParam(value = "labOrderType", required = false) String labOrderType,
			@RequestParam(value = "labOrderIdPattern", required = false) String labOrderIdPattern,
			@RequestParam(value = "labSpecimenIdPattern", required = false) String labSpecimenIdPattern,
			@RequestParam(value = "labReportIdPattern", required = false) String labReportIdPattern) {

		for (Object param : request.getParameterMap().entrySet()) {
				Map.Entry<String, String[]> entry = (Map.Entry<String, String[]>) param;
				GlobalProperty gp = Context.getAdministrationService().getGlobalPropertyObject(entry.getKey());
				if (gp != null) {
					gp.setPropertyValue(entry.getValue()[0]);
					Context.getAdministrationService().saveGlobalProperty(gp);
				} else {
					return "failed";
				}
		}
		return "success";
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
