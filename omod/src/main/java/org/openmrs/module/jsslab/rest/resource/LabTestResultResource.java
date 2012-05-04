package org.openmrs.module.jsslab.rest.resource;

import java.util.List;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabTestingService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabTestResult;
import org.openmrs.module.jsslab.db.LabTest;
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
@Resource("LabTestResult")
@Handler(supports = LabTestResult.class, order = 0)
public class LabTestResultResource extends DataDelegatingCrudResource<LabTestResult>{

	@Override
	public LabTestResult getByUniqueId(String uniqueId) {
		LabTestResult labTestResult=Context.getService(LabTestingService.class).getLabTestResultByUuid(uniqueId);
		return labTestResult;
	}

	@Override
	public LabTestResult newDelegate() {
		return new LabTestResult();
	}

	@Override
	public LabTestResult save(LabTestResult delegate) {
		LabTestResult labTestResult=Context.getService(LabTestingService.class).saveLabTestResult(delegate);
		return labTestResult;
	}

	@Override
	protected void delete(LabTestResult labTestResult, String reason,
			RequestContext context) throws ResponseException {
		if(labTestResult!=null)
		{
			//
			Context.getService(LabTestingService.class).voidLabTestResult(labTestResult,reason);
		}		
		
	}

	@Override
	public void purge(LabTestResult labTestResult, RequestContext context)
			throws ResponseException {
		if(labTestResult!=null)
		{
			//
			Context.getService(LabTestingService.class).purgeLabTestResult(labTestResult);
		}
				
	}

	@Override
	@SuppressWarnings("unchecked")
	public DelegatingResourceDescription getRepresentationDescription(
			Representation rep) {
		DelegatingResourceDescription Descri=new DelegatingResourceDescription();
		if(rep instanceof DefaultRepresentation)
		{
			//			
			Descri.addProperty("uuid");
			Descri.addProperty("testResultConcept",Representation.REF);
			Descri.addProperty("resultValue");
			Descri.addProperty("testAnswerFlag");
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return Descri;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			Descri.addProperty("uuid");
			Descri.addProperty("testSpecimen",Representation.REF);
			Descri.addProperty("testResultConcept",Representation.REF);
			Descri.addProperty("resultTypeString");
			Descri.addProperty("resultValue");
			Descri.addProperty("testAnswerFlag");
			Descri.addProperty("testAnswerNote");
			Descri.addProperty("approved");
			Descri.addProperty("approvedBy",Representation.REF);
			Descri.addProperty("testCriticalLow");
			Descri.addProperty("testNormalLow");
			Descri.addProperty("testNormalHigh");
			Descri.addProperty("testCriticalHigh");
			Descri.addProperty("hidden");
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addProperty("auditInfo",findMethod("getAuditInfo"));
			return Descri;
		}
		return null;
	}
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return  new NeedsPaging<LabTestResult>(Context.getService(LabTestingService.class).getLabTestResults("",false,null,null), context);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabTestingService.class, "getLabTestResult", "getCountOfLabTestResult").search(query,
		    context);
	}


	public String getDisplayString(LabTestResult labTestResult) {
		return labTestResult.getDisplayString();
	}
	
}
 