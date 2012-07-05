package org.openmrs.module.jsslab.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.rest.resource.LabTestPanelResource;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value= "/rest/"+RestConstants.VERSION_1+"/jsslab/labTestPanel")
public class LabTestPanelController extends BaseCrudController<LabTestPanelResource>{

	private static final Log log=LogFactory.getLog(LabTestPanelController.class);
	
	@Override
	public LabTestPanelResource getResource()
	{
		//
		log.info("getting LabTestPanel resource");
		return Context.getService(RestService.class).getResource(LabTestPanelResource.class);
	}
}
