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
import org.openmrs.module.jsslab.LabTestingService;
import org.openmrs.module.jsslab.db.LabReport;
import org.openmrs.module.jsslab.db.LabReport;
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
@Resource("LabReport")
@Handler(supports = LabReport.class, order = 0)
public class LabReportResource extends MetadataDelegatingCrudResource<LabReport>{

	@Override
	public LabReport getByUniqueId(String uniqueId) {
		LabReport labReport=Context.getService(LabTestingService.class).getLabReportByUuid(uniqueId);
		return labReport;
	}

	@Override
	protected LabReport newDelegate() {
		return new LabReport();
	}

	@Override
	protected LabReport save(LabReport delegate) {
		LabReport labReport=Context.getService(LabTestingService.class).saveLabReport(delegate);
		return labReport;
	}

	@Override
	public void delete(LabReport labReport, String reason,
			RequestContext context) throws ResponseException {
		if(labReport!=null)
		{
			//
			Context.getService(LabTestingService.class).retireLabReport(labReport,reason);
		}		
		
	}

	@Override
	public void purge(LabReport labReport, RequestContext context)
			throws ResponseException {
		if(labReport!=null)
		{
			//
			Context.getService(LabTestingService.class).purgeLabReport(labReport);
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
			Descri.addProperty("labReportId");
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return Descri;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			Descri.addProperty("uuid");
			Descri.addProperty("labReportId");
			Descri.addProperty("reportStatusConcept",Representation.REF);
			Descri.addProperty("approvedBy",Representation.REF);
			Descri.addProperty("approvedDate");
			Descri.addProperty("comments");
			Descri.addProperty("resultDeliveryDate");
			Descri.addProperty("resultDeliveryConcept",Representation.REF);
			Descri.addProperty("specimens",Representation.REF);
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addProperty("auditInfo",findMethod("getAuditInfo"));
			return Descri;
		}
		return null;
	}
	@Override
	protected List<LabReport> doGetAll(RequestContext context) {
		return Context.getService(LabTestingService.class).getLabReports("",false,null,null);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabTestingService.class, "getLabReports", "getCountOfLabReports").search(query,
		    context);
	}


}
