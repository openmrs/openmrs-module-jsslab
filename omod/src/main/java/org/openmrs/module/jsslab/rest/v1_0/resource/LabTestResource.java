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

@Resource("labTest")
@Handler(supports = LabTest.class, order = 0)
public class LabTestResource extends MetadataDelegatingCrudResource<LabTest>{

	@Override
	public LabTest getByUniqueId(String uniqueId) {
		LabTest labTest=Context.getService(LabCatalogService.class).getLabTestByUUID(uniqueId);
		return labTest;
	}

	@Override
	public LabTest newDelegate() {
		return new LabTest();
	}

	@Override
	public LabTest save(LabTest delegate) {
		LabTest labTest=Context.getService(LabCatalogService.class).saveLabTest(delegate);
		return labTest;
	}


	@Override
	public void delete(LabTest labTest, String reason,
			RequestContext context) throws ResponseException {
		if(labTest!=null)
		{
			//
			Context.getService(LabCatalogService.class).retireLabTest(labTest,reason);
		}			
	}

	@Override
	public void purge(LabTest labTest, RequestContext context)
			throws ResponseException {
		if(labTest!=null)
		{
			//
			Context.getService(LabCatalogService.class).purgeLabTest(labTest);
		}
				
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return  new NeedsPaging<LabTest>(Context.getService(LabCatalogService.class).getAllLabTests(false), context);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabCatalogService.class, "getLabTests", "getCountOfLabTest").search(query,
		    context);
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(
			Representation rep) {
		DelegatingResourceDescription Descri=new DelegatingResourceDescription();
		if(rep instanceof DefaultRepresentation)
		{
			//			
			Descri.addProperty("uuid");
			Descri.addProperty("testPanel",Representation.REF);
			Descri.addProperty("testConcept",Representation.REF);
			Descri.addProperty("retired");
			Descri.addSelfLink();
			Descri.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return Descri;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			Descri.addProperty("uuid");
			Descri.addProperty("testPanel",Representation.REF);
			Descri.addProperty("testConcept",Representation.REF);
			Descri.addProperty("confirmTest",Representation.REF);
			Descri.addProperty("sortWeight");
			Descri.addProperty("retired");
			Descri.addSelfLink();
			Descri.addProperty("auditInfo",findMethod("getAuditInfo"));
			return Descri;
		}
		return null;
	}
	
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription d = new DelegatingResourceDescription();
		d.addRequiredProperty("testPanel");
		d.addRequiredProperty("testConcept");
//		d.addProperty("testName");
		d.addProperty("resultFormat");
		d.addProperty("confirmTest");
		d.addProperty("sortWeight");
		d.addProperty("testRanges");
		return d;
	}
}
