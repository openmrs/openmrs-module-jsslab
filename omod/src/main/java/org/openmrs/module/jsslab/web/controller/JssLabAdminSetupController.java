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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleException;
import org.openmrs.module.ModuleFactory;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.util.DatabaseUpdater;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "module/jsslab/admin/setup")
public class JssLabAdminSetupController {

	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/** Success form view name */
	private String successFormView = "module/jsslab/admin/setup";
	private String targetView = null;
	
	/**
	 * Initially called after the formBackingObject method to get the landing form name  
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void showForm(HttpServletRequest request, ModelMap model, 
			@RequestParam(required = false, defaultValue = "admin/index") String targetView) {
		this.targetView = targetView;

		GlobalProperty gpOrderType = Context.getAdministrationService().getGlobalPropertyObject("jsslab.object.orderType.labOrder");
		GlobalProperty gpAllConcepts = Context.getAdministrationService().getGlobalPropertyObject("jsslab.object.conceptSet.allConcepts");
		List<GlobalProperty> globalProperties = new ArrayList<GlobalProperty>();
		globalProperties.add(gpOrderType);
		globalProperties.add(gpAllConcepts);
		
		model.put("globalPropertiesObject", globalProperties);

		String sampleDataInstalled = Context.getAdministrationService().getGlobalProperty("jsslab.setup.sampleData");
		boolean isSampleDataInstalled = sampleDataInstalled != null && !sampleDataInstalled.isEmpty();
		
		model.put("sampleDataInstalled", isSampleDataInstalled);
	}
	
	@RequestMapping(value = "/installSampleData", method = RequestMethod.GET)
	@ResponseBody
	public SimpleObject installSampleData() {
		SimpleObject o = new SimpleObject();
		
		try {
			DatabaseUpdater.executeChangelog("liquibase-sample-data.xml", null, null, null, ModuleFactory.getModuleClassLoader(ModuleFactory.getModuleById("jsslab")));

//			if ( OpenmrsConstants.OPENMRS_VERSION == null || OpenmrsConstants.OPENMRS_VERSION.startsWith("1.8") ) {
//				DatabaseUpdater.executeChangelog("liquibase-sample-data.xml", null, null, null, ModuleFactory.getModuleClassLoader(ModuleFactory.getModuleById("jsslab")));
//			} else if ( OpenmrsConstants.OPENMRS_VERSION.startsWith("1.9") ) {
//				DatabaseUpdater.executeChangelog("liquibase-sample-data-1.9.xml", null, null, null, ModuleFactory.getModuleClassLoader(ModuleFactory.getModuleById("jsslab")));
//			}
			markSampleDataInstalled();
			o.put("code", "success");
			o.put("message", Context.getMessageSourceService().getMessage("jsslab.setup.database.sampledata.result.success"));
			return o;
		} catch (ModuleException e) {
			log.error("Failed to retrieve class loader for jsslab", e);
			o.put("code", "failure");
			o.put("message", Context.getMessageSourceService().getMessage("jsslab.setup.database.sampledata.result.failed") + ": " + e.toString() );
		} catch (Exception e) {
			log.error("Applying sample data using liquibase failed", e);
			o.put("code", "failure");
			o.put("message", Context.getMessageSourceService().getMessage("jsslab.setup.database.sampledata.result.failed"));
		}
		
		return o;
	}


	@RequestMapping(value = "/finishSetup", method = RequestMethod.GET)
	@ResponseBody
	public String finishSetup(HttpServletRequest request) {
		markSetupCompleted();
		
//		String resultView = "forward:" + request.getContextPath() + "/" + targetView + ".htm";
//		return resultView;
		return request.getContextPath() + "/" + targetView + ".htm";
	}

	private void markSampleDataInstalled() {
		Context.getAdministrationService().saveGlobalProperty(new GlobalProperty("jsslab.setup.sampleData", "installed"));
	}
	private void markSetupCompleted() {
		Context.getAdministrationService().saveGlobalProperty(new GlobalProperty("jsslab.setup", "complete"));
	}
	
}
