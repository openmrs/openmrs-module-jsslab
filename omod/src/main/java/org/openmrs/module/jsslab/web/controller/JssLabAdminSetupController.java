package org.openmrs.module.jsslab.web.controller;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.ModuleException;
import org.openmrs.module.ModuleFactory;
import org.openmrs.util.DatabaseUpdater;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "module/jsslab/admin/setup")
public class JssLabAdminSetupController {

	
	/** Logger for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/** Success form view name */
	private String successFormView = "module/jsslab/admin/setup";
	private String targetView = null;
	
	@PostConstruct
	public void init() {
		
	}
	
	
	@RequestMapping(value = "/saveGlobalProperties")
	public String saveGlobalProperties() {
		return successFormView;
	}
	
	@RequestMapping(value = "/installSampleData")
	public String installSampleData() {
		try {
			DatabaseUpdater.executeChangelog("liquibase-sample-data.xml", null, null, null, ModuleFactory.getModuleClassLoader(ModuleFactory.getModuleById("jsslab")));

//			if ( OpenmrsConstants.OPENMRS_VERSION == null || OpenmrsConstants.OPENMRS_VERSION.startsWith("1.8") ) {
//				DatabaseUpdater.executeChangelog("liquibase-sample-data.xml", null, null, null, ModuleFactory.getModuleClassLoader(ModuleFactory.getModuleById("jsslab")));
//			} else if ( OpenmrsConstants.OPENMRS_VERSION.startsWith("1.9") ) {
//				DatabaseUpdater.executeChangelog("liquibase-sample-data-1.9.xml", null, null, null, ModuleFactory.getModuleClassLoader(ModuleFactory.getModuleById("jsslab")));
//			}
			return successFormView;
		} catch (ModuleException e) {
			log.error("Applying sample data using liquibase failed", e);
		} catch (Exception e) {
			log.error("Applying sample data using liquibase failed", e);
		}
		
		return "error";
	}

	@RequestMapping(value = "/finishSetup")
	public String finishSetup(HttpServletRequest request) {
		markSetupCompleted();
		
		String resultView = "forward:" + request.getContextPath() + "/" + targetView + ".htm";
		
		return resultView;
	}

	private void markSetupCompleted() {
		AdministrationService as = Context.getAdministrationService();
		
		GlobalProperty gpSetup = as.getGlobalPropertyObject("jsslab.setup");
		gpSetup.setPropertyValue("complete");
		
		as.saveGlobalProperty(gpSetup);
	}
	
	/**
	 * Initially called after the formBackingObject method to get the landing form name  
	 * @return String form view name
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(HttpServletRequest request, ModelMap model, @RequestParam(required = false, defaultValue = "admin/index") String targetView) {
		//TODO determine target view and store to redirect the user after setup is finished
		this.targetView = targetView;
		return "module/jsslab/admin/setup";
	}
	
}
