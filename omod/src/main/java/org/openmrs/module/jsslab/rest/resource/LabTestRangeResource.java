package org.openmrs.module.jsslab.rest.resource;

import java.util.List;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.LabTestingService;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.jsslab.db.LabTestRange;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource("LabTestRange")
@Handler(supports = LabTestRange.class, order = 0)
public class LabTestRangeResource extends DataDelegatingCrudResource<LabTestRange>{

	@Override
	public LabTestRange getByUniqueId(String uniqueId) {
		LabTestRange labTestRange=Context.getService(LabTestingService.class).getLabTestRangeByUuid(uniqueId);
		return labTestRange;
	}

	@Override
	protected LabTestRange newDelegate() {
		return new LabTestRange();
	}

	@Override
	protected LabTestRange save(LabTestRange delegate) {
		LabTestRange labTestRange=Context.getService(LabTestingService.class).saveLabTestRange(delegate);
		return labTestRange;
	}

	@Override
	protected void delete(LabTestRange labTestRange, String reason,
			RequestContext context) throws ResponseException {
		if(labTestRange!=null)
		{
			//
			Context.getService(LabTestingService.class).deleteLabTestRange(labTestRange,reason);
		}			
	}

	@Override
	public void purge(LabTestRange labTestRange, RequestContext context)
			throws ResponseException {
		if(labTestRange!=null)
		{
			//
			Context.getService(LabTestingService.class).purgeLabTestRange(labTestRange);
		}
				
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(
			Representation rep) {
		DelegatingResourceDescription Descri=new DelegatingResourceDescription();
		if(rep instanceof DefaultRepresentation)
		{
			//			
			Descri.addProperty("uuid");
			Descri.addProperty("test",Representation.REF);
			Descri.addProperty("sortWeight");
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return Descri;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			Descri.addProperty("uuid");
			Descri.addProperty("test",Representation.REF);
			Descri.addProperty("logicRule",Representation.REF);
			Descri.addProperty("rangeSex");
			Descri.addProperty("rangeNormalLow");
			Descri.addProperty("rangeNormalHigh");
			Descri.addProperty("rangeCriticalLow");
			Descri.addProperty("rangeCriticalHigh");
			Descri.addProperty("sortWeight");
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addProperty("auditInfo",findMethod("getAuditInfo"));
			return Descri;
		}
		return null;
	}
	
	@Override
	protected List<LabTestRange> doGetAll(RequestContext context) {
		return Context.getService(LabTestingService.class).getAllLabTestRanges(false);
	}
	
	@Override
	protected AlreadyPaged<LabTestRange> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTestRange>(LabCatalogService.class, "getAllLabTestRanges", "getCountOfLabTestRange")
				.search(query, context);
	}

}
