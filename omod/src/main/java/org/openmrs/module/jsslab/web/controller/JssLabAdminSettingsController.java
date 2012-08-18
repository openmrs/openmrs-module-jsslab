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
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.util.JsonUtil;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET)
	public void showForm(ModelMap model) throws IOException {
		
		String gpConceptSets = Context.getAdministrationService().getGlobalProperty("jsslab.object.concept.allConcepts");
		
		if (gpConceptSets != null && !gpConceptSets.isEmpty()) {
			List<Concept> conceptSets = Context.getConceptService()
					.getConceptByUuid(gpConceptSets).getSetMembers();
			
			model.addAttribute("conceptSets", conceptSets);
		}
		
		List<GlobalProperty> globalPropertiesString = Context.getAdministrationService().getGlobalPropertiesByPrefix("jsslab.string");
		GlobalProperty gpHomeLab = Context.getAdministrationService().getGlobalPropertyObject("jsslab.object.location.homeLab");
		GlobalProperty gpLabOrderType = Context.getAdministrationService().getGlobalPropertyObject("jsslab.object.orderType.labOrder");
		GlobalProperty gpAllConcepts = Context.getAdministrationService().getGlobalPropertyObject("jsslab.object.concept.allConcepts");
		GlobalProperty gpInternalElectronic = Context.getAdministrationService().getGlobalPropertyObject("jsslab.object.concept.report.internalElectronicDelivery");

		model.addAttribute("globalPropertiesString", globalPropertiesString);
		model.addAttribute("gpHomeLab", gpHomeLab);
		model.addAttribute("gpLabOrderType", gpLabOrderType);
		model.addAttribute("gpAllConcepts", gpAllConcepts);
		model.addAttribute("gpInternalElectronic", gpInternalElectronic);

		if (gpHomeLab.getPropertyValue() != null && !gpHomeLab.getPropertyValue().isEmpty()) {
			String homeLabDisplayString = Context.getLocationService().getLocationByUuid(gpHomeLab.getPropertyValue()).getDisplayString();
			model.addAttribute("homeLabDisplayString", homeLabDisplayString);
		}
		
		if (gpLabOrderType.getPropertyValue() != null && !gpLabOrderType.getPropertyValue().isEmpty()) {
			String labOrderDisplayString = Context.getOrderService().getOrderTypeByUuid(gpLabOrderType.getPropertyValue()).getName();
			model.addAttribute("labOrderDisplayString", labOrderDisplayString);
		}
		
		if (gpAllConcepts.getPropertyValue() != null && !gpAllConcepts.getPropertyValue().isEmpty()) {
			String allConceptsDisplayString = Context.getConceptService().getConceptByUuid(gpAllConcepts.getPropertyValue()).getDisplayString();
			model.addAttribute("allConceptsDisplayString", allConceptsDisplayString);
		}
		
		if (gpInternalElectronic.getPropertyValue() != null && !gpInternalElectronic.getPropertyValue().isEmpty()) {
			String internalElectronicDisplayString = Context.getConceptService().getConceptByUuid(gpInternalElectronic.getPropertyValue()).getDisplayString();
			model.addAttribute("internalElectronicDisplayString", internalElectronicDisplayString);
		}
		
	}
	
	
	@RequestMapping(value = "getAllRootLocations.htm", method = RequestMethod.GET)
    @ResponseBody
    public String getAllRootLocations(HttpServletRequest request) throws ResponseException {
    	
    	List<Location> list = new ArrayList<Location>();
		for (Location loc : Context.getLocationService().getAllLocations()) {
			if (loc.getParentLocation() == null) {
				list.add(loc);
			}
		}
		return JsonUtil.getLocationListAsJsonTree(list);
    }
	
}
