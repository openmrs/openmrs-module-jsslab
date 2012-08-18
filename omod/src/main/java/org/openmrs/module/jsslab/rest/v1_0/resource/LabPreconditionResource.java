package org.openmrs.module.jsslab.rest.v1_0.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabPrecondition;
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

@Resource("labprecondition")
@Handler(supports = LabPrecondition.class, order = 0)
public class LabPreconditionResource extends DataDelegatingCrudResource<LabPrecondition> {

	@Override
	public LabPrecondition getByUniqueId(String uniqueId) {
		LabPrecondition labPrecondition = Context.getService(LabCatalogService.class).getLabPreconditionByUuid(uniqueId);
		return labPrecondition;
	}

	@Override
	public LabPrecondition newDelegate() {
		return new LabPrecondition();
	}

	@Override
	public LabPrecondition save(LabPrecondition delegate) {
		LabPrecondition labPrecondition=Context.getService(LabCatalogService.class).saveLabPrecondition(delegate);
		return labPrecondition;
	}

	@Override
	protected void delete(LabPrecondition labPrecondition, String reason,
			RequestContext context) throws ResponseException {
		if(labPrecondition!=null)
		{
			//
			Context.getService(LabCatalogService.class).voidLabPrecondition(labPrecondition,reason);
		}		
		
	}

	@Override
	public void purge(LabPrecondition labPrecondition, RequestContext context)
			throws ResponseException {
		if(labPrecondition!=null)
		{
			//
			Context.getService(LabCatalogService.class).purgeLabPrecondition(labPrecondition);
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
			Descri.addProperty("testPanel",Representation.REF);
			Descri.addProperty("preconditionQuestionConcept",Representation.REF);
			Descri.addProperty("preconditionAnswerConcept",Representation.REF);
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return Descri;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			Descri.addProperty("uuid");
			Descri.addProperty("testPanel",Representation.REF);
			Descri.addProperty("preconditionQuestionConcept",Representation.DEFAULT);
			Descri.addProperty("preconditionAnswerConcept",Representation.DEFAULT);
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
		d.addRequiredProperty("testPanel");
		d.addRequiredProperty("preconditionQuestionConcept");
		d.addRequiredProperty("preconditionAnswerConcept");
//		d.addProperty("preconditionQuestionText");
//		d.addProperty("preconditionAnswerText");
		d.addProperty("sortWeight");
		d.addProperty("textLocale");
		return d;
	}
	
	@Override
	protected String getNamespacePrefix() {
		return "jsslab";
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return  new NeedsPaging<LabPrecondition>(Context.getService(LabCatalogService.class).getLabPreconditions("",false,null,null), context);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabCatalogService.class, "getLabPrecondition", "getCountOfLabPrecondition").search(query,
		    context);
	}


	public String getDisplayString(LabPrecondition labPrecondition) {
		return labPrecondition.getDisplayString();
	}
}
