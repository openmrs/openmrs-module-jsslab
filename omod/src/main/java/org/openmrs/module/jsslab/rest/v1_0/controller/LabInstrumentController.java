package org.openmrs.module.jsslab.rest.v1_0.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.rest.v1_0.resource.LabInstrumentResource;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for REST web service access to the LabInstrument. Supports CRUD on the resource itself.
 */
@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/jsslab/labInstrument")
public class LabInstrumentController extends BaseCrudController<LabInstrumentResource> {

	private static final Log log = LogFactory.getLog(LabInstrumentController.class);
	
	@Override
	public LabInstrumentResource getResource() {
		log.info("getting LabInstrument resource");
		return Context.getService(RestService.class).getResource(LabInstrumentResource.class);
	}

}

