package org.openmrs.module.jsslab.rest.v1_0.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.rest.v1_0.resource.LabPreconditionResource;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value= "/rest/"+RestConstants.VERSION_1+"/jsslab/labprecondition")
public class LabPreconditionController extends BaseCrudController<LabPreconditionResource> {

	private static final Log log=LogFactory.getLog(LabPreconditionController.class);
	
	@Override
	public LabPreconditionResource getResource()
	{
		//
		log.info("getting LabPrecondition resource");
		return Context.getService(RestService.class).getResource(LabPreconditionResource.class);
	}
}
