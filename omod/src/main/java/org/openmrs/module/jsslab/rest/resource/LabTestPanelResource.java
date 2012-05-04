package org.openmrs.module.jsslab.rest.resource;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.openmrs.Concept;
import org.openmrs.Location;
import org.openmrs.User;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSpecimen;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.module.jsslab.db.LabTestRun;
import org.openmrs.module.jsslab.db.LabTestPanel;
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

@Resource("LabTestPanel")
@Handler(supports = LabTestPanel.class, order = 0)
public class LabTestPanelResource extends MetadataDelegatingCrudResource<LabTestPanel>{

	@Override
	public LabTestPanel getByUniqueId(String uniqueId) {
		LabTestPanel labTestPanel=Context.getService(LabCatalogService.class).getLabTestPanelByUuid(uniqueId);
		return labTestPanel;
	}

	@Override
	public LabTestPanel newDelegate() {
		return new LabTestPanel();
	}

	@Override
	public LabTestPanel save(LabTestPanel delegate) {
		LabTestPanel labTestPanel=Context.getService(LabCatalogService.class).saveLabTestPanel(delegate);
		return labTestPanel;
	}

	@Override
	public void delete(LabTestPanel labTestPanel, String reason,
			RequestContext context) throws ResponseException {
		if(labTestPanel!=null)
		{
			//
			Context.getService(LabCatalogService.class).retireLabTestPanel(labTestPanel,reason);
		}		
		
	}

	@Override
	public void purge(LabTestPanel labTestPanel, RequestContext context)
			throws ResponseException {
		if(labTestPanel!=null)
		{
			//
			Context.getService(LabCatalogService.class).purgeLabTestPanel(labTestPanel);
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
			Descri.addProperty("labLocation",Representation.REF);
			Descri.addProperty("testGroupConcept",Representation.REF);
			Descri.addProperty("testPanelConcept",Representation.REF);
			Descri.addProperty("retired");
			Descri.addSelfLink();
			Descri.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return Descri;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			Descri.addProperty("uuid");
			Descri.addProperty("labLocation",Representation.REF);
			Descri.addProperty("testGroupConcept",Representation.REF);
			Descri.addProperty("testPanelConcept",Representation.REF);
			Descri.addProperty("receivedSpecimenTypeConcept",Representation.REF);
			Descri.addProperty("analysisSpecimenTypeConcept",Representation.REF);
			Descri.addProperty("cost");
			Descri.addProperty("turnaround");
			Descri.addProperty("holdTime");
			Descri.addProperty("retired");
			Descri.addSelfLink();
			Descri.addProperty("auditInfo",findMethod("getAuditInfo"));
			return Descri;
		}
		return null;
	}
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return  new NeedsPaging<LabTestPanel>(Context.getService(LabCatalogService.class).getLabTestPanels("",false,null,null), context);
	}
	
	@Override
	protected AlreadyPaged<LabTest> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabTest>(LabCatalogService.class, "getLabTestPanels", "getCountOfLabTestPanel").search(query,
		    context);
	}


}
