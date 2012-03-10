package org.openmrs.module.jsslab.rest.resource;

import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabOrderService;
import org.openmrs.module.jsslab.db.LabReport;
import org.openmrs.module.jsslab.db.LabSpecimen;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;  
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
@Resource("LabSpecimen")
@Handler(supports = LabSpecimen.class, order = 0)
public class LabSpecimenResource extends MetadataDelegatingCrudResource<LabSpecimen>{

	@Override
	public LabSpecimen getByUniqueId(String uniqueId) {
		LabSpecimen labSpecimen=Context.getService(LabOrderService.class).getLabSpecimenByUuid(uniqueId);
		return labSpecimen;
	}

	@Override
	protected LabSpecimen newDelegate() {
		return new LabSpecimen();
	}

	@Override
	protected LabSpecimen save(LabSpecimen delegate) {
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
		DelegatingResourceDescription Descri=new DelegatingResourceDescription();
		if(rep instanceof DefaultRepresentation)
		{
			//			
			Descri.addProperty("uuid");
			Descri.addProperty("labSpecimenId");
			Descri.addProperty("clientSpecimenId");
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return Descri;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			Descri.addProperty("uuid");
			Descri.addProperty("labSpecimenId");
			Descri.addProperty("clientSpecimenId");
			Descri.addProperty("orderedBy",Representation.REF);
			Descri.addProperty("orderedByFacility",Representation.REF);
			Descri.addProperty("person",Representation.REF);
			Descri.addProperty("patient",Representation.REF);
			Descri.addProperty("specimenDate");
			Descri.addProperty("receivedDate");
			Descri.addProperty("receivedConditionConcept",Representation.REF);
			Descri.addProperty("receivedSpecimenTypeConcept",Representation.REF);
			Descri.addProperty("report",Representation.REF);
			Descri.addProperty("urgent");
			Descri.addProperty("physicianRetest");
			Descri.addProperty("hidden");
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addProperty("auditInfo",findMethod("getAuditInfo"));
			return Descri;
		}
		return null;
	}
	@Override
	protected List<LabSpecimen> doGetAll(RequestContext context) {
		return Context.getService(LabOrderService.class).getLabSpecimens("",false,null,null);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabOrderService.class, "getLabSpecimens", "getCountOfLabSpecimens").search(query,
		    context);
	}


}
