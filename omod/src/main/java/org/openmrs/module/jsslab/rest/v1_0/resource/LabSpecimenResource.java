package org.openmrs.module.jsslab.rest.v1_0.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabOrderService;
import org.openmrs.module.jsslab.db.LabSpecimen;
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

@Resource("labspecimen")
@Handler(supports = LabSpecimen.class, order = 0)
public class LabSpecimenResource extends MetadataDelegatingCrudResource<LabSpecimen> {

	@Override
	public LabSpecimen getByUniqueId(String uniqueId) {
		LabSpecimen labSpecimen=Context.getService(LabOrderService.class).getLabSpecimenByUuid(uniqueId);
		return labSpecimen;
	}

	@Override
	public LabSpecimen newDelegate() {
		return new LabSpecimen();
	}

	@Override
	public LabSpecimen save(LabSpecimen delegate) {
		LabSpecimen labSpecimen=Context.getService(LabOrderService.class).saveLabSpecimen(delegate);
		return labSpecimen;
	}

	@Override
	public void delete(LabSpecimen labSpecimen, String reason,
			RequestContext context) throws ResponseException {
		if(labSpecimen!=null)
		{
			//
			Context.getService(LabOrderService.class).deleteLabSpecimen(labSpecimen,reason);
		}		
		
	}

	@Override
	public void purge(LabSpecimen labSpecimen, RequestContext context)
			throws ResponseException {
		if(labSpecimen!=null)
		{
			//
			Context.getService(LabOrderService.class).purgeLabSpecimen(labSpecimen);
		}
				
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(
			Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation) {
			//			
			description.addProperty("uuid");
			description.addProperty("labSpecimenId");
			description.addProperty("clientSpecimenId");
			description.addProperty("retired");
			description.addSelfLink();
			description.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return description;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			description.addProperty("uuid");
			description.addProperty("labSpecimenId");
			description.addProperty("clientSpecimenId");
			description.addProperty("orderedBy",Representation.REF);
			description.addProperty("orderedByFacility",Representation.REF);
			description.addProperty("person",Representation.REF);
			description.addProperty("patient",Representation.REF);
			description.addProperty("specimenDate");
			description.addProperty("receivedDate");
			description.addProperty("receivedConditionConcept",Representation.REF);
			description.addProperty("receivedSpecimenTypeConcept",Representation.REF);
			description.addProperty("report",Representation.REF);
			description.addProperty("urgent");
			description.addProperty("physicianRetest");
			description.addProperty("hidden");
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
		d.addRequiredProperty("labSpecimenId");
		d.addProperty("clientSpecimenId");
		d.addRequiredProperty("orderedBy");
		d.addRequiredProperty("orderedByFacility");
		d.addProperty("person");
		d.addProperty("patient");
		d.addRequiredProperty("specimenDate");
		d.addRequiredProperty("receivedDate");
		d.addProperty("receivedConditionConcept");
		d.addRequiredProperty("receivedSpecimenTypeConcept");
		d.addRequiredProperty("report");
		d.addRequiredProperty("urgent");
		d.addProperty("physicianRetest");
		d.addProperty("hidden");
		d.addProperty("testSpecimens");
		d.addProperty("orderSpecimens");
		return d;
	}
	
	@Override
	protected String getNamespacePrefix() {
		return "jsslab";
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return new NeedsPaging<LabSpecimen>(Context.getService(LabOrderService.class).getLabSpecimens("", context.getIncludeAll(), null, null), context);
	}
	
	@Override
	protected AlreadyPaged<LabSpecimen> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabSpecimen>(LabOrderService.class, "getLabSpecimens", "getCountOfLabSpecimens").search(query,
		    context);
	}


}
