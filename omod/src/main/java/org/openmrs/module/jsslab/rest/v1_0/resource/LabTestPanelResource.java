package org.openmrs.module.jsslab.rest.v1_0.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource("labtestpanel")
@Handler(supports = LabTestPanel.class, order = 0)
public class LabTestPanelResource extends MetadataDelegatingCrudResource<LabTestPanel>{

	@Override
	public LabTestPanel getByUniqueId(String uniqueId) {
		LabTestPanel labTestPanel = Context.getService(LabCatalogService.class).getLabTestPanelByUuid(uniqueId);
		return labTestPanel;
	}

	@Override
	public LabTestPanel newDelegate() {
		return new LabTestPanel();
	}

	@Override
	public LabTestPanel save(LabTestPanel delegate) {
		LabTestPanel labTestPanel=Context.getService(LabCatalogService.class).saveLabTestPanel(delegate);
		return labTestPanel;
	}

	@Override
	public void delete(LabTestPanel labTestPanel, String reason,
			RequestContext context) throws ResponseException {
		if(labTestPanel!=null)
		{
			//
			Context.getService(LabCatalogService.class).retireLabTestPanel(labTestPanel, reason);
		}		
		
	}

	@Override
	public void purge(LabTestPanel labTestPanel, RequestContext context)
			throws ResponseException {
		if(labTestPanel!=null)
		{
			//
			Context.getService(LabCatalogService.class).purgeLabTestPanel(labTestPanel);
		}
				
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(
			Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if(rep instanceof DefaultRepresentation)
		{
			//			
			description.addProperty("uuid");
			description.addProperty("labLocation", Representation.REF);
			description.addProperty("testGroupConcept", Representation.REF);
			description.addProperty("testPanelConcept", Representation.REF);
			description.addProperty("retired");
			description.addSelfLink();
			description.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return description;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			description.addProperty("uuid");
			description.addProperty("labLocation", Representation.REF);
			description.addProperty("testGroupConcept", Representation.REF);
			description.addProperty("testPanelConcept", Representation.REF);
			description.addProperty("receivedSpecimenTypeConcept", Representation.REF);
			description.addProperty("analysisSpecimenTypeConcept", Representation.REF);
			description.addProperty("cost");
			description.addProperty("testPanelCode");
			description.addProperty("turnaround");
			description.addProperty("holdTime");
			description.addProperty("preconditions", Representation.REF);
			description.addProperty("specimenTemplates", Representation.DEFAULT);
			description.addProperty("tests", Representation.REF);
			description.addProperty("retired");
			
			description.addSelfLink();
			description.addProperty("auditInfo", findMethod("getAuditInfo"));
			return description;
		}
		return null;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addRequiredProperty("labLocation");
		description.addRequiredProperty("testGroupConcept");
		description.addRequiredProperty("testPanelConcept");
		description.addProperty("testPanelCode");
		description.addProperty("receivedSpecimenTypeConcept");
		description.addProperty("analysisSpecimenTypeConcept");
		description.addProperty("cost");
		description.addProperty("turnaround");
		description.addProperty("holdTime");
		description.addProperty("tests");
		description.addProperty("preconditions");
		description.addProperty("specimenTemplates");
		return description;
	}
	
	@Override
	protected String getNamespacePrefix() {
		return "jsslab";
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return new NeedsPaging<LabTestPanel>(Context.getService(LabCatalogService.class).getLabTestPanels("", context.getIncludeAll(), null, null), context);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabCatalogService.class, "getLabTestPanels", "getCountOfLabTestPanel").search(query,
		    context);
	}


}
