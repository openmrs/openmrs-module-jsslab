package org.openmrs.module.jsslab.rest.v1_0.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/jsslab")
public class JsslabController {

	private static final Log log = LogFactory.getLog(JsslabController.class);
	
    @RequestMapping(method = RequestMethod.GET)
    public String doGet(@RequestParam("method") String method, ModelMap model) {
    	return "";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String doPost(@RequestParam("method") String method, ModelMap model) {
    	return "";
    }

    
}
