//package org.openmrs.module.jsslab.rest.v1_0.controller;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.log4j.Level;
//import org.openmrs.api.context.Context;
//import org.openmrs.module.webservices.rest.web.RestConstants;
//import org.openmrs.module.webservices.rest.SimpleObject;
//import org.openmrs.module.webservices.rest.web.RequestContext;
//import org.openmrs.module.webservices.rest.web.RestUtil;
//import org.openmrs.module.webservices.rest.web.api.RestService;
//import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseCrudController;
//import org.openmrs.module.webservices.rest.web.v1_0.resource.CohortResource;
//import org.openmrs.module.webservices.rest.web.response.ResponseException;
//import org.openmrs.module.jsslab.db.LabTestPanel;
//import org.openmrs.module.jsslab.rest.v1_0.resource.LabOrderResource;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
///**
// * Controller for REST web service access to the LabOrder. Supports CRUD on the resource itself.
// */
//@Controller
//@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/jsslab/labOrder")
//public class LabOrderController extends BaseCrudController<LabOrderResource> {
//
//	private static final Log log = LogFactory.getLog(LabOrderController.class);
//	
//	@Override
//	public LabOrderResource getResource() {
//		log.info("getting LabOrder resource");
//		return Context.getService(RestService.class).getResource(LabOrderResource.class);
//	}
//
//}
//
