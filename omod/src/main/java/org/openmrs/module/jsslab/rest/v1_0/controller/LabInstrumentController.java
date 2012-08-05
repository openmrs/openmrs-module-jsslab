package org.openmrs.module.jsslab.rest.v1_0.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.rest.v1_0.resource.LabInstrumentResource;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseCrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller for REST web service access to the LabInstrument. Supports CRUD on the resource itself.
 */
@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/jsslab/labinstrument")
public class LabInstrumentController extends BaseCrudController<LabInstrumentResource> {

	private static final Log log = LogFactory.getLog(LabInstrumentController.class);
	
	@Override
	public LabInstrumentResource getResource() {
		log.info("getting LabInstrument resource");
		return Context.getService(RestService.class).getResource(LabInstrumentResource.class);
	}

	public SimpleObject getAll(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) boolean includeVoided) throws ResponseException {
		MetadataDelegatingCrudResource<LabInstrument> listable;
		try {
			listable = (MetadataDelegatingCrudResource<LabInstrument>) getResource();
		}
		catch (ClassCastException ex) {
			throw new ResourceDoesNotSupportOperationException(
			        getResource().getClass().getSimpleName() + " is not Listable", null);
		}
		RequestContext context = RestUtil.getRequestContext(request, Representation.REF);
		return listable.getAll(context);
		
	}
	
}

