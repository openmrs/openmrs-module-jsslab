package org.openmrs.module.jsslab.rest.v1_0.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.rest.v1_0.resource.LabTestResultResource;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value= "/rest/"+RestConstants.VERSION_1+"/jsslab/labtestresult")
public class LabTestResultController extends BaseCrudController<LabTestResultResource>{

	private static final Log log=LogFactory.getLog(LabTestResultController.class);
	
	@Override
	public LabTestResultResource getResource()
	{
		//
		log.info("getting LabTestResult resource");
		return Context.getService(RestService.class).getResource(LabTestResultResource.class);
	}
}
