package org.openmrs.module.jsslab.rest.v1_0.resource;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Order;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabOrderService;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabOrderSpecimen;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.SubResource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.resource.OrderResource;

@SubResource(parent = OrderResource.class, path = "orderSpecimen")
@Handler(supports = LabOrderSpecimen.class, order = 0)
public class LabOrderSpecimenResource extends DelegatingSubResource<LabOrderSpecimen, Order, OrderResource> {

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
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription d = new DelegatingResourceDescription();
		d.addRequiredProperty("specimen");
		d.addRequiredProperty("order");
		d.addProperty("textLocale");
		d.addProperty("specimenRoleConcept");
		d.addProperty("specimenRoleText");
		d.addProperty("specimenRoleCode");
		return d;
	}
	
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

	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubResource#getParent(java.lang.Object)
	 */
	@Override
	public Order getParent(LabOrderSpecimen instance) {
		return instance.getOrder();
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubResource#setParent(java.lang.Object,
	 *      java.lang.Object)
	 */
	@Override
	public void setParent(LabOrderSpecimen instance, Order order) {
		instance.setOrder((LabOrder) order);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.api.SubResource#doGetAll(java.lang.Object,
	 *      org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	public NeedsPaging<LabOrderSpecimen> doGetAll(Order parent, RequestContext context) throws ResponseException {
		List<LabOrderSpecimen> labOrderSpecimens = new ArrayList<LabOrderSpecimen>();
		LabOrder parentLabOrder = (LabOrder) parent;
		if (parent != null) {
			labOrderSpecimens.addAll(parentLabOrder.getOrderSpecimens());
		}
		return new NeedsPaging<LabOrderSpecimen>(labOrderSpecimens, context);
	}
	
}
