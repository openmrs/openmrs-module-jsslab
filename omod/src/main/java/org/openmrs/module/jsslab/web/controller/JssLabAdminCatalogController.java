/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.jsslab.web.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Location;
import org.openmrs.LocationTag;
import org.openmrs.api.context.Context;
import org.openmrs.logic.rule.definition.RuleDefinitionService;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "module/jsslab/admin/catalog")
public class JssLabAdminCatalogController {

	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(method = RequestMethod.GET)
	public void showForm(HttpServletRequest request, ModelMap model) throws IOException, ResponseException {
		
		LocationTag locationTag = Context.getLocationService().getLocationTagByName("Lab");
		List<Location> locations = Context.getLocationService().getLocationsByTag(locationTag);
		
		RequestContext context = RestUtil.getRequestContext(request, Representation.FULL);
		PageableResult locationsResult = new AlreadyPaged<Location>(context, locations, false);
		
		model.addAttribute("locations", locations);
		model.addAttribute("rules", Context.getService(RuleDefinitionService.class).getAllRuleDefinitions(true));
		
//		model.put("locationsJSON", locationsResult.toSimpleObject());
		
	}
	
}
