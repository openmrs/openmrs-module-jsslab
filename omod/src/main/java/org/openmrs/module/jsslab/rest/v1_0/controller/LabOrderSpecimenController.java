package org.openmrs.module.jsslab.rest.v1_0.controller;

import org.openmrs.module.jsslab.rest.v1_0.resource.LabOrderSpecimenResource;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseSubResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for REST web service access to the LabInstrument. Supports CRUD on the resource itself.
 */
@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/order/{parentUuid}/orderSpecimen")
public class LabOrderSpecimenController extends BaseSubResourceController<LabOrderSpecimenResource> {

}
