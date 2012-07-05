package org.openmrs.module.jsslab.rest.v1_0.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.rest.v1_0.resource.LabTestResource;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value= "/rest/"+RestConstants.VERSION_1+"/jsslab/labTest")
public class LabTestController extends BaseCrudController<LabTestResource>{

	private static final Log log=LogFactory.getLog(LabTestController.class);
	
	@Override
	public LabTestResource getResource()
	{
		//
		log.info("getting LabTest resource");
		return Context.getService(RestService.class).getResource(LabTestResource.class);
	}
}
