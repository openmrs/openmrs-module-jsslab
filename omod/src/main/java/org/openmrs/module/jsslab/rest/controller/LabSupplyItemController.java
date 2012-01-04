package org.openmrs.module.jsslab.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.jsslab.rest.resource.LabInstrumentResource;
import org.openmrs.module.jsslab.rest.resource.LabSupplyItemResource;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseCrudController;

/**
 * Controller for REST web service access to the LabSupplyItem. Supports CRUD on the resource itself.
 */
@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/jsslab/labSupplyItem")
public class LabSupplyItemController extends BaseCrudController<LabSupplyItemResource> {

	private static final Log log = LogFactory.getLog(LabSupplyItemController.class);
	
	@Override
	public LabSupplyItemResource getResource() {
		log.info("Lafayette, we are here.");
		return Context.getService(RestService.class).getResource(LabSupplyItemResource.class);
	}
}
