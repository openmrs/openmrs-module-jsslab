package org.openmrs.module.jsslab.rest.resource;

import java.util.List;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabManagementService;
import org.openmrs.module.jsslab.LabOrderService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabSupplyItem;
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

@Resource("labOrder")
@Handler(supports = LabOrder.class, order = 0)
public class LabOrderResource extends DataDelegatingCrudResource<LabOrder>{

	@Override
	public LabOrder getByUniqueId(String uniqueId) {
		LabOrder labOrder=Context.getService(LabOrderService.class).getLabOrderByUuid(uniqueId);
		return labOrder;
	}

	@Override
	public LabOrder newDelegate() {
		return new LabOrder();
	}

	@Override
	public LabOrder save(LabOrder delegate) {
		LabOrder labOrder=Context.getService(LabOrderService.class).saveLabOrder(delegate);
		return labOrder;
	}

	@Override
	public void delete(LabOrder labOrder, String reason,
			RequestContext context) throws ResponseException {
		if (labOrder != null)
		{
			//
			Context.getService(LabOrderService.class).deleteLabOrder(labOrder,reason);
		}		
	}

	@Override
	public void purge(LabOrder labOrder, RequestContext context)
			throws ResponseException {
		if (labOrder != null)
		{
			//
			Context.getService(LabOrderService.class).purgeLabOrder(labOrder);
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
			Descri.addProperty("labOrderId");
			Descri.addProperty("concept",Representation.REF);
			Descri.addProperty("orderer",Representation.REF);
			Descri.addProperty("startDate");
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return Descri;
		}
		else if(rep instanceof FullRepresentation)
		{
			//
			Descri.addProperty("uuid");
			Descri.addProperty("labOrderId");
			Descri.addProperty("urgent");
//			Descri.addProperty("retestOf",Representation.REF);
			Descri.addProperty("physicianRetest");
			Descri.addProperty("retestReason");
//			Descri.addProperty("orderTypeId",Representation.REF);
//			Descri.addProperty("concept",Representation.REF);
//			Descri.addProperty("orderer",Representation.REF);
//			Descri.addProperty("encounter",Representation.REF);
			Descri.addProperty("instructions");
			Descri.addProperty("startDate");
			Descri.addProperty("autoExpireDate");
			Descri.addProperty("discontinued");
			Descri.addProperty("discontinuedDate");
//			Descri.addProperty("discontinuedBy",Representation.REF);
			Descri.addProperty("discontinuedReason");
			Descri.addProperty("voided");
			Descri.addSelfLink();
			Descri.addProperty("auditInfo",findMethod("getAuditInfo"));
			return Descri;
		}
		return null;
	}
	
	public String getDisplayString(LabOrder delegate) {
		return delegate.toString();
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#doGetAll(org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	protected PageableResult doGetAll(RequestContext context) {
		return  new NeedsPaging<LabOrder>(Context.getService(LabOrderService.class).getAllLabOrders(false), context);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#doSearch(java.lang.String,
	 *      org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	protected AlreadyPaged<LabOrder> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabOrder>(LabOrderService.class, "getLabOrders", "getCountOfLabOrders").search(query,
		    context);
	}
	
}
