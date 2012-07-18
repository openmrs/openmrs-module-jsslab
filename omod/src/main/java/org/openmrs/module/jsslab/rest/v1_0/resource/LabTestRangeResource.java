package org.openmrs.module.jsslab.rest.v1_0.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.LabTestingService;
import org.openmrs.module.jsslab.db.LabTestRange;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource("labTestRange")
@Handler(supports = LabTestRange.class, order = 0)
public class LabTestRangeResource extends DataDelegatingCrudResource<LabTestRange>{

	@Override
	public LabTestRange getByUniqueId(String uniqueId) {
		LabTestRange labTestRange=Context.getService(LabTestingService.class).getLabTestRangeByUuid(uniqueId);
		return labTestRange;
	}

	@Override
	public LabTestRange newDelegate() {
		return new LabTestRange();
	}

	@Override
	public LabTestRange save(LabTestRange delegate) {
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
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription d = new DelegatingResourceDescription();
		d.addRequiredProperty("test");
		d.addRequiredProperty("sortWeight");
		d.addProperty("rangeSex");
		d.addProperty("rangeAgeMin");
		d.addProperty("rangeAgeMax");
		d.addProperty("logicRule");
		d.addProperty("rangeNormalLow");
		d.addProperty("rangeNormalHigh");
		d.addProperty("rangeCriticalLow");
		d.addProperty("rangeCriticalHigh");
		return d;
	}
	
	@Override
	protected String getNamespacePrefix() {
		return "jsslab";
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return  new NeedsPaging<LabTestRange>(Context.getService(LabTestingService.class).getAllLabTestRanges(false), context);
	}
	
	@Override
	protected AlreadyPaged<LabTestRange> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTestRange>(LabCatalogService.class, "getAllLabTestRanges", "getCountOfLabTestRange")
				.search(query, context);
	}

}
