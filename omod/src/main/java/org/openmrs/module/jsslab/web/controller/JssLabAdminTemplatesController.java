package org.openmrs.module.jsslab.web.controller;

import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "module/jsslab/admin/templates")
public class JssLabAdminTemplatesController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Initially called to fill the model with relevant data  
	 * @throws IOException 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void showForm(ModelMap model) throws IOException {
		
		Concept labSet = Context.getConceptService().getConcept("33333121");
		List<Concept> conceptSets = labSet.getSetMembers();
		
		model.addAttribute("conceptSets", conceptSets);
	}

	
}
