package org.openmrs.module.jsslab.rest.v1_0.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(method = RequestMethod.GET, params = "getConceptsByConceptSet")
	@ResponseBody
	public SimpleObject getConceptsBySet(HttpServletRequest request,
			@RequestParam(value = "getConceptsByConceptSet") String uuid) throws ResponseException {
		
		Concept memberOf = Context.getConceptService().getConceptByUuid(uuid);
		List<Concept> setMembers = Context.getConceptService().getConceptsByConceptSet(memberOf);
		RequestContext context = RestUtil.getRequestContext(request, Representation.FULL);
		PageableResult result = new NeedsPaging<Concept>(setMembers, context);
		return result.toSimpleObject();
	}
    
//	@RequestMapping(method = RequestMethod.GET, params="method")
//    @ResponseBody
//    public SimpleObject search(@RequestParam("method") String queryType, HttpServletRequest request, HttpServletResponse response) {
//
//		if (queryType == "findConcepts") {
//			
//		}
////		switch (queryType) {
////
////		case "Mapped" :
////			
////			break;
////
////		default:
////
////		}
//		return null;
//    }
    
}
