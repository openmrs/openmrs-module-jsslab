package org.openmrs.module.jsslab.rest.v1_0.resource;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
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
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource("labSpecimentemplate")
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
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if(rep instanceof DefaultRepresentation)
		{
			//			
			description.addProperty("uuid");
			description.addProperty("display", findMethod("getDisplayString"));
			description.addProperty("testPanel",Representation.REF);
			description.addProperty("testRoleConcept",Representation.REF);
			description.addProperty("parentSubId");
			description.addProperty("specimenSubId");
			description.addProperty("retired");
			description.addSelfLink();
			description.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return description;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			description.addProperty("uuid");
			description.addProperty("testPanel",Representation.REF);
			description.addProperty("testRoleConcept",Representation.REF);
			description.addProperty("parentSubId");
			description.addProperty("specimenSubId");
			description.addProperty("parentRelationConcept",Representation.REF);
			description.addProperty("analysisSpecimenTypeConcept",Representation.REF);
			description.addProperty("parentRoleConcept",Representation.REF);
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
		d.addProperty("textLocale");
		d.addRequiredProperty("specimenSubId");
		d.addRequiredProperty("parentSubId");
		d.addProperty("testPanel");
		d.addProperty("supplyItem");
		d.addProperty("instructions");
		d.addRequiredProperty("parentRoleConcept");
//		d.addProperty("parentRoleName");
		d.addRequiredProperty("parentRelationConcept");
//		d.addProperty("parentRelationName");
		d.addProperty("analysisSpecimenTypeConcept");
//		d.addProperty("analysisSpecimenTypeName");
		d.addProperty("testRoleConcept");
//		d.addProperty("testRoleName");
		return d;
	}
	
	@Override
	protected String getNamespacePrefix() {
		return "jsslab";
	}
	
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return new NeedsPaging<LabSpecimenTemplate>(Context.getService(LabCatalogService.class).getLabSpecimenTemplate("", context.getIncludeAll(), null, null), context);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabCatalogService.class, "getLabSpecimenTemplate", "getCountOfLabSpecimenTemplate").search(query,
		    context);
	}


}
