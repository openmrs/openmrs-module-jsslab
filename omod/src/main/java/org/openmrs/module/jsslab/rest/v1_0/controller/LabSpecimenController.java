package org.openmrs.module.jsslab.rest.v1_0.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.rest.v1_0.resource.LabSpecimenResource;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value= "/rest/"+RestConstants.VERSION_1+"/jsslab/labSpecimen")
public class LabSpecimenController extends BaseCrudController<LabSpecimenResource>{

	private static final Log log=LogFactory.getLog(LabSpecimenController.class);
	
	@Override
	public LabSpecimenResource getResource()
	{
		//
		log.info("getting LabSpecimen resource");
		return Context.getService(RestService.class).getResource(LabSpecimenResource.class);
	}
}
