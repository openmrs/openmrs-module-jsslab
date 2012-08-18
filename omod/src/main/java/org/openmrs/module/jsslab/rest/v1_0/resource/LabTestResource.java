package org.openmrs.module.jsslab.rest.v1_0.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabTest;
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

@Resource("labtest")
@Handler(supports = LabTest.class, order = 0)
public class LabTestResource extends MetadataDelegatingCrudResource<LabTest>{

	@Override
	public LabTest getByUniqueId(String uniqueId) {
		LabTest labTest = Context.getService(LabCatalogService.class).getLabTestByUUID(uniqueId);
		return labTest;
	}

	@Override
	public LabTest newDelegate() {
		return new LabTest();
	}

	@Override
	public LabTest save(LabTest delegate) {
		LabTest labTest = Context.getService(LabCatalogService.class).saveLabTest(delegate);
		return labTest;
	}


	@Override
	public void delete(LabTest labTest, String reason,
			RequestContext context) throws ResponseException {
		if(labTest != null) {
			//
			Context.getService(LabCatalogService.class).retireLabTest(labTest,reason);
		}			
	}

	@Override
	public void purge(LabTest labTest, RequestContext context)
			throws ResponseException {
		if(labTest!=null) {
			//
			Context.getService(LabCatalogService.class).purgeLabTest(labTest);
		}
				
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return new NeedsPaging<LabTest>(Context.getService(LabCatalogService.class).getAllLabTests(context.getIncludeAll()), context);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabCatalogService.class, "getLabTests", "getCountOfLabTest").search(query,
		    context);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation) {
			//			
			description.addProperty("uuid");
			description.addProperty("testPanels",Representation.REF);
			description.addProperty("testConcept",Representation.REF);
			description.addProperty("confirmTest",Representation.REF);
			description.addProperty("testRanges",Representation.REF);
			description.addProperty("testPanels",Representation.REF);
			description.addProperty("resultFormat");
			description.addProperty("retired");
			description.addSelfLink();
			description.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return description;
			
		} else if (rep instanceof FullRepresentation) {
			//
			description.addProperty("uuid");
			description.addProperty("testPanels",Representation.DEFAULT);
			description.addProperty("testConcept",Representation.DEFAULT);
			description.addProperty("confirmTest",Representation.DEFAULT);
			description.addProperty("testRanges",Representation.DEFAULT);
			description.addProperty("testPanels",Representation.DEFAULT);
			description.addProperty("resultFormat");
			description.addProperty("sortWeight");
			description.addProperty("retired");
			description.addSelfLink();
			description.addProperty("auditInfo",findMethod("getAuditInfo"));
			return description;
		}
		return null;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription d = new DelegatingResourceDescription();
		d.addRequiredProperty("testConcept");
		d.addProperty("resultFormat");
		d.addProperty("confirmTest");
		d.addProperty("sortWeight");
		d.addProperty("testRanges");
		return d;
	}
	
	@Override
	protected String getNamespacePrefix() {
		return "jsslab";
	}
	
	@Override
	public String getDisplayString(LabTest delegate) {
		return delegate.getTestName();
	}
}
