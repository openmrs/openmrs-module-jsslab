package org.openmrs.module.jsslab.rest.v1_0.controller;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptDatatype;
import org.openmrs.ConceptSet;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.OrderType;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
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

    /**
     * Retrieves a List of all Concepts that are members of a ConceptSet given by its UUID.
     * 
     * @param request
     * @param uuid UUID of the ConceptSet
     * @return List of all members of the ConceptSet
     * 
     * @throws ResponseException
     */
    @RequestMapping(method = RequestMethod.GET, params = "getConceptsByConceptSet")
	@ResponseBody
	public SimpleObject getConceptsBySet(HttpServletRequest request,
			@RequestParam(value = "setUuid", required = true) String uuid) throws ResponseException {
		
		Concept conceptSet = Context.getConceptService().getConceptByUuid(uuid);
		List<Concept> setMembers = conceptSet.getSetMembers();
		RequestContext context = RestUtil.getRequestContext(request, Representation.FULL);
		PageableResult result = new NeedsPaging<Concept>(setMembers, context);
		return result.toSimpleObject();
	}

    @RequestMapping(method = RequestMethod.GET, params = "getAllConceptSets")
    @ResponseBody
    public SimpleObject getAllConceptSets(HttpServletRequest request) throws ResponseException {
    	
    	List<Concept> conceptSets = Context.getConceptService().getAllConcepts();
    	for (int i = 0; i < conceptSets.size(); ) {
    		if (conceptSets.get(i).isSet()) {
    			i++;
    		} else {
    			conceptSets.remove(i);
    		}
    	}
    	
    	RequestContext context = RestUtil.getRequestContext(request, Representation.FULL);
    	PageableResult result = new AlreadyPaged<Concept>(context, conceptSets, false);
    	return result.toSimpleObject();
    }

    @RequestMapping(method = RequestMethod.GET, params = "getAllConceptQuestions")
    @ResponseBody
    public SimpleObject getAllConceptQuestions(HttpServletRequest request,
    				@RequestParam(value = "q", required = false) String query
    		) throws ResponseException {
    	
    	List<Concept> concepts = Context.getConceptService().getConceptsByName(query);
    	for (int i = 0; i < concepts.size(); ) {
    		if (concepts.get(i).getDatatype().equals(ConceptDatatype.CODED)) {
    			i++;
    		} else {
    			concepts.remove(i);
    		}
    	}
    	
    	RequestContext context = RestUtil.getRequestContext(request, Representation.FULL);
    	PageableResult result = new AlreadyPaged<Concept>(context, concepts, false);
    	return result.toSimpleObject();
    }

    @RequestMapping(method = RequestMethod.GET, params = "addConceptToConceptSet")
    @ResponseBody
    public String addConceptToConceptSet(HttpServletRequest request,
		    		@RequestParam(value = "setUuid", required = true) String setUuid,
		    		@RequestParam(value = "conceptUuid", required = true) String conceptUuid
    		) throws ResponseException {
    	
    	Concept conceptSet = Context.getConceptService().getConceptByUuid(setUuid);
    	conceptSet.addSetMember( 
			Context.getConceptService().getConceptByUuid(conceptUuid)
		);
    	Context.getConceptService().saveConcept(conceptSet);
    	return null;
    }

    @RequestMapping(method = RequestMethod.POST, params = "substituteConceptSet")
    @ResponseBody
    public String substituteConceptSet(HttpServletRequest request,
		    		@RequestParam(value = "oldUuid", required = true) String oldUuid,
		    		@RequestParam(value = "newUuid", required = true) String newUuid
			) throws ResponseException {
    	
    	String gpConceptSets = Context.getAdministrationService().getGlobalProperty("jsslab.object.concept.allConcepts");
    	
    	
    	if (gpConceptSets != null && !gpConceptSets.isEmpty()) {
			Concept rootSet = Context.getConceptService().getConceptByUuid(gpConceptSets);

			Collection<ConceptSet> conceptSets = rootSet.getConceptSets();
			Set<ConceptSet> newConceptSets = new HashSet<ConceptSet>();
			
			for (ConceptSet cs : conceptSets) {
				String conceptUuid = cs.getConcept().getUuid();
				if (!conceptUuid.equals(oldUuid)) {
					newConceptSets.add(cs);
				}
			}
			// Create a ConceptSet of the Concept to be added
			Concept newConcept = Context.getConceptService().getConceptByUuid(newUuid);
			ConceptSet cs = new ConceptSet(newConcept, 0.0);
			cs.setConceptSet(rootSet);

			newConceptSets.add(cs);
			
			conceptSets.clear();
			conceptSets.addAll(newConceptSets);
			
			Context.getConceptService().saveConcept(rootSet);
		}
    	return null;
    }

    @RequestMapping(method = RequestMethod.GET, params = "getGlobalProperty")
	@ResponseBody
	public Object getGlobalProperty(HttpServletRequest request, 
			@RequestParam(value = "property", required = true) String property
		) {

		SimpleObject o = new SimpleObject();
		
		GlobalProperty gp = Context.getAdministrationService().getGlobalPropertyObject(property);
		
		return gp;
	}
    
    /**
     * Allows saving a GlobalProperty via REST
     * 
     * @param request
     * @param property The name of the GlobalProperty to be saved
     * @param value The value of the GlobalProperty to be saved
     * @return The saved GlobalProperty as a SimpleObject
     */
    @RequestMapping(method = RequestMethod.POST, params = "saveGlobalProperty")
	@ResponseBody
	public SimpleObject saveGlobalProperty(HttpServletRequest request, 
			@RequestParam(value = "property", required = true) String property,
			@RequestParam(value = "value", required = false) String value
		) {

		SimpleObject o = new SimpleObject();
		
		GlobalProperty gp = Context.getAdministrationService().getGlobalPropertyObject(property);
		if (gp != null) {
			gp.setPropertyValue(value);
			Context.getAdministrationService().saveGlobalProperty(gp);
		} else {
			o.put("code", "failure");
			o.put("message", Context.getMessageSourceService().getMessage("jsslab.settings.globalproperties.result.failed"));
			return o;
		}
		
		o.put("code", "success");
		o.put("message", Context.getMessageSourceService().getMessage("jsslab.settings.globalproperties.result.success"));
		return o;
	}
    
    @RequestMapping(method = RequestMethod.GET, params = "getLabTestPanelsByLocation")
    @ResponseBody
    public SimpleObject getLabTestsByLocation(HttpServletRequest request,
		    		@RequestParam(value = "locationUuid", required = true) String uuid,
		    		@RequestParam(value = "includeAll", required = false, defaultValue = "false") boolean retired,
		    		@RequestParam(value = "start", required = false, defaultValue = "0") int startIndex,
    				@RequestParam(value = "length", required = false, defaultValue = "-1") int resultLength
			) throws ResponseException {
    	
    	Location location = Context.getLocationService().getLocationByUuid(uuid);
    	List<LabTestPanel> labTests = Context.getService(LabCatalogService.class).getLabTestPanelsByLocation(location, retired, startIndex, resultLength);
    	
    	boolean hasMoreResults = resultLength > 0 && Context.getService(LabCatalogService.class).getCountOfLabTestPanels(retired) > labTests.size();
    	
    	RequestContext context = RestUtil.getRequestContext(request, Representation.FULL);
    	PageableResult result = new AlreadyPaged<LabTestPanel>(context, labTests, hasMoreResults);
    	return result.toSimpleObject();
    }
    
    @SuppressWarnings("deprecation")
	@RequestMapping(method = RequestMethod.GET, params = "getAllOrderTypes")
    @ResponseBody
    public SimpleObject getAllOrderTypes(HttpServletRequest request) throws ResponseException {
    	
    	List<OrderType> orderTypes = Context.getOrderService().getAllOrderTypes();
    	
    	RequestContext context = RestUtil.getRequestContext(request, Representation.FULL);
    	PageableResult result = new NeedsPaging<OrderType>(orderTypes, context);
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
