package org.openmrs.module.jsslab.rest.controller;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.jsslab.rest.resource.LabInstrumentResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for REST web service access to the LabInstrument. Supports CRUD on the resource itself.
 */
@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/jsslab/labInstrument")
public class LabInstrumentController extends BaseCrudController<LabInstrumentResource> {}
