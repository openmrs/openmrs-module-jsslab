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

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabManagementService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "module/jsslab/admin/instruments")
public class JssLabAdminInstrumentsController {

	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/jsslab/admin/instruments";
	
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
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(	@RequestParam(required = false, value = "instrumentId") Integer instrumentId,
				            @ModelAttribute("instrument") LabInstrument instrument, ModelMap model) {
		
		LabManagementService labManagementService = Context.getService(LabManagementService.class);
		
		List<LabInstrument> instruments = labManagementService.getAllLabInstruments(true);
		List<LabSupplyItem> supplies = labManagementService.getAllLabSupplyItems(true);
		model.put("instruments", instruments);
		model.put("supplies", supplies);
		
//		Concept c = Context.getConceptService().getConcept("ASSET CONDITION");
//		Collection<ConceptSet> cSets = c.getConceptSets();
//		for (ConceptSet cSet : cSets) {
//			Concept cc = cSet.getConcept();
//			String name = cc.getDescription().getDescription();
//			name = "";
//		}
		
		return SUCCESS_FORM_VIEW;
	}
	
//	@RequestMapping(method = RequestMethod.POST)
//	public void catchPost() {
//		String a = "a";
//		a += a;
//	}
	
}
