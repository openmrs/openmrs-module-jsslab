package org.openmrs.module.jsslab.rest.resource;

import java.util.List;

import org.openmrs.Concept;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSpecimenTemplate;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;  
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource("LabSpecimenTemplate")
@Handler(supports = LabSpecimenTemplate.class, order = 0)
public class LabSpecimenTemplateResource extends MetadataDelegatingCrudResource<LabSpecimenTemplate>{

	@Override
	public LabSpecimenTemplate getByUniqueId(String uniqueId) {
		LabSpecimenTemplate labSpecimenTemplate=Context.getService(LabCatalogService.class).getLabSpecimenTemplateByUuid(uniqueId);
		return labSpecimenTemplate;
	}

	@Override
	public LabSpecimenTemplate newDelegate() {
		return new LabSpecimenTemplate();
	}

	@Override
	public LabSpecimenTemplate save(LabSpecimenTemplate delegate) {
		LabSpecimenTemplate labSpecimenTemplate=Context.getService(LabCatalogService.class).saveLabSpecimenTemplate(delegate);
		return labSpecimenTemplate;
	}

	@Override
	public void delete(LabSpecimenTemplate labSpecimenTemplate, String reason,
			RequestContext context) throws ResponseException {
		if(labSpecimenTemplate!=null)
		{
			//
			Context.getService(LabCatalogService.class).retireLabSpecimenTemplate(labSpecimenTemplate,reason);
		}		
		
	}

	@Override
	public void purge(LabSpecimenTemplate labSpecimenTemplate, RequestContext context)
			throws ResponseException {
		if(labSpecimenTemplate!=null)
		{
			//
			Context.getService(LabCatalogService.class).purgeLabSpecimenTemplate(labSpecimenTemplate);
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
			Descri.addProperty("testRoleConcept",Representation.REF);
			Descri.addProperty("parentSubID");
			Descri.addProperty("specimenSubID");
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
			Descri.addProperty("testRoleConcept",Representation.REF);
			Descri.addProperty("parentSubID");
			Descri.addProperty("specimenSubID");
			Descri.addProperty("parentRelationConcept",Representation.REF);
			Descri.addProperty("analysisSpecimenTypeConcept",Representation.REF);
			Descri.addProperty("parentRoleConcept",Representation.REF);
			Descri.addProperty("retired");
			Descri.addSelfLink();
			Descri.addProperty("auditInfo",findMethod("getAuditInfo"));
			return Descri;
		}
		return null;
	}
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return  new NeedsPaging<LabSpecimenTemplate>(Context.getService(LabCatalogService.class).getLabSpecimenTemplate("",false,null,null), context);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabCatalogService.class, "getLabSpecimenTemplate", "getCountOfLabSpecimenTemplate").search(query,
		    context);
	}


}
