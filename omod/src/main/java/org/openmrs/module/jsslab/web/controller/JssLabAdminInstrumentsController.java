package org.openmrs.module.jsslab.web.controller;

import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptSet;
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
	
	@RequestMapping(method = RequestMethod.POST)
	public void catchPost() {
		String a = "a";
		a += a;
	}
	
//	@RequestMapping(value="/{id}")
//	@ResponseBody
//	public String getInstrumentById(@PathVariable("id") Integer id) {
//		LabInstrument instrument = Context.getService(LabManagementService.class).getLabInstrument(id);
//		
//		return instrument.toString();
//	}
}
