package org.openmrs.module.jsslab.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Level;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseSubResourceController;
import org.openmrs.module.webservices.rest.web.v1_0.resource.CohortResource;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.module.jsslab.rest.resource.LabOrderSpecimenSubresource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for REST web service access to the LabInstrument. Supports CRUD on the resource itself.
 */
@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/jsslab/labOrderSpecimen")
public class LabOrderSpecimenController extends BaseSubResourceController<LabOrderSpecimenSubresource> {

	private static final Log log = LogFactory.getLog(LabOrderSpecimenController.class);
	
	@Override
	public LabOrderSpecimenSubresource getResource() {
		log.info("getting LabOrderSpecimen subresource");
		return Context.getService(RestService.class).getResource(LabOrderSpecimenSubresource.class);
	}

}
