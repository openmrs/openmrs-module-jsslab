package org.openmrs.module.jsslab.rest.v1_0.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabTestingService;
import org.openmrs.module.jsslab.db.LabReport;
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

@Resource("labreport")
@Handler(supports = LabReport.class, order = 0)
public class LabReportResource extends MetadataDelegatingCrudResource<LabReport>{

	@Override
	public LabReport getByUniqueId(String uniqueId) {
		LabReport labReport=Context.getService(LabTestingService.class).getLabReportByUuid(uniqueId);
		return labReport;
	}

	@Override
	public LabReport newDelegate() {
		return new LabReport();
	}

	@Override
	public LabReport save(LabReport delegate) {
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
			Descri.addProperty("retired");
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
			Descri.addProperty("specimens",Representation.FULL);
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
		d.addProperty("labReportId");
		d.addProperty("reportStatusConcept");
		d.addProperty("approvedBy");
		d.addProperty("approvedDate");
		d.addProperty("comments");
		d.addProperty("resultDeliveryDate");
		d.addProperty("resultDeliveryConcept");
		d.addProperty("specimens");
		return d;
	}
	
	@Override
	protected String getNamespacePrefix() {
		return "jsslab";
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return new NeedsPaging<LabReport>(Context.getService(LabTestingService.class).getLabReports("", context.getIncludeAll(), null, null), context);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabTestingService.class, "getLabReports", "getCountOfLabReports").search(query,
		    context);
	}


}
