package org.openmrs.module.jsslab.rest.resource;

import java.util.List;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.LabManagementService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource("LabTest")
@Handler(supports = LabTest.class, order = 0)
public class LabTestResource extends MetadataDelegatingCrudResource<LabTest>{

	@Override
	public LabTest getByUniqueId(String uniqueId) {
		LabTest labTest=Context.getService(LabCatalogService.class).getLabTestByUUID(uniqueId);
		return labTest;
	}

	@Override
	protected LabTest newDelegate() {
		return new LabTest();
	}

	@Override
	protected LabTest save(LabTest delegate) {
		LabTest labTest=Context.getService(LabCatalogService.class).saveLabTest(delegate);
		return labTest;
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
	protected List<LabTest> doGetAll(RequestContext context) {
		return Context.getService(LabCatalogService.class).getAllLabTests(false);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabCatalogService.class, "getLabTest", "getCountOfLabTest").search(query,
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
}
