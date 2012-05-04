package org.openmrs.module.jsslab.rest.resource;

import java.util.List;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabOrderService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabOrderSpecimen;
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
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;  
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource("labOrderSpecimen")
@Handler(supports = LabOrderSpecimen.class, order = 0)
public class LabOrderSpecimenResource extends DataDelegatingCrudResource<LabOrderSpecimen> {

		@Override
	public LabOrderSpecimen getByUniqueId(String uniqueId) {
		LabOrderSpecimen labOrderSpecimen=Context.getService(LabOrderService.class).getLabOrderSpecimenByUuid(uniqueId);
		return labOrderSpecimen;
	}

	@Override
	public LabOrderSpecimen newDelegate() {
		return new LabOrderSpecimen();
	}

	@Override
	public LabOrderSpecimen save(LabOrderSpecimen delegate) {
		LabOrderSpecimen labOrderSpecimen=Context.getService(LabOrderService.class).saveLabOrderSpecimen(delegate);
		return labOrderSpecimen;
	}


	@Override
	public DelegatingResourceDescription getRepresentationDescription(
			Representation rep) {
		DelegatingResourceDescription Descri=new DelegatingResourceDescription();
		if(rep instanceof DefaultRepresentation)
		{
			//			
			Descri.addProperty("uuid");
			Descri.addProperty("order",Representation.REF);
			Descri.addProperty("specimen",Representation.REF);
			Descri.addProperty("specimenRoleConcept",Representation.REF);
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return Descri;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			Descri.addProperty("uuid");
			Descri.addProperty("order",Representation.FULL);
			Descri.addProperty("specimen",Representation.FULL);
			Descri.addProperty("specimenRoleConcept",Representation.FULL);
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addProperty("auditInfo",findMethod("getAuditInfo"));
			return Descri;
		}
		return null;
	}
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return new NeedsPaging<LabOrderSpecimen>(Context.getService(LabOrderService.class).getLabOrderSpecimens("",false,null,null), context);
	}
	
	@Override
	protected AlreadyPaged<LabOrderSpecimen> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabOrderSpecimen>(LabOrderService.class, "getLabOrderSpecimen", "getCountOfLabOrderSpecimen").search(query,
		    context);
	}

	@Override
	protected void delete(LabOrderSpecimen labOrderSpecimen, String reason, RequestContext context)
		throws ResponseException {
		if(labOrderSpecimen!=null) {
			labOrderSpecimen.setVoided(true);
			labOrderSpecimen.setVoidReason(reason);
			Context.getService(LabOrderService.class).saveLabOrderSpecimen(labOrderSpecimen);
		}
	}

	@Override
	public void purge(LabOrderSpecimen labOrderSpecimen, RequestContext context)
			throws ResponseException {
		if(labOrderSpecimen!=null)
			Context.getService(LabOrderService.class).purgeLabOrderSpecimen(labOrderSpecimen);
		
	}

	public String getDisplayString(LabOrderSpecimen labOrderSpecimen) {
		return labOrderSpecimen.getDisplayString();
	}
}
