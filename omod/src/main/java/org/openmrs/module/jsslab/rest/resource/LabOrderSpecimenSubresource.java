/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.jsslab.rest.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openmrs.Order;
import org.openmrs.module.webservices.rest.web.v1_0.resource.OrderResource;
import org.openmrs.module.jsslab.LabOrderService;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabOrderSpecimen;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.annotation.SubResource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubResource;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * {@link Resource} for LabOrderSpecimens, supporting standard CRUD operations
 */
@SubResource(parent = OrderResource.class, path = "orderspecimen")
@Handler(supports = LabOrderSpecimen.class, order = 0)
public class LabOrderSpecimenSubresource extends DelegatingSubResource<LabOrderSpecimen, Order, OrderResource> {
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription Descri=new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation) {
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
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getCreatableProperties()
	 */
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addRequiredProperty("order");
		description.addRequiredProperty("specimen");
		description.addProperty("specimenRoleConcept");
		return description;
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getUpdatableProperties()
	 */
	@Override
	public DelegatingResourceDescription getUpdatableProperties() {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("specimenRoleConcept");
		return description;
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
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#getByUniqueId(java.lang.String)
	 */
	@Override
	public LabOrderSpecimen getByUniqueId(String uuid) {
		return Context.getService(LabOrderService.class).getLabOrderSpecimenByUuid(uuid);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#delete(java.lang.Object,
	 *      java.lang.String, org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	public void delete(LabOrderSpecimen los, String reason, RequestContext context) throws ResponseException {
		los.setVoided(true);
		los.setVoidedBy(Context.getAuthenticatedUser());
		los.setVoidReason(reason);
		los.setDateVoided(new Date());
		Context.getService(LabOrderService.class).saveLabOrderSpecimen(los);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#purge(java.lang.Object,
	 *      org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	public void purge(LabOrderSpecimen los, RequestContext context) throws ResponseException {
		LabOrder lo = los.getOrder();
		lo.getOrderSpecimens().remove(los);
		Context.getService(LabOrderService.class).saveLabOrder(lo);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceHandler#save(java.lang.Object)
	 */
	@Override
	public LabOrderSpecimen save(LabOrderSpecimen newLabOrderSpecimen) {
		// make sure that the labOrderSpecimen has actually been added to the order
		boolean needToAdd = true;
		for (LabOrderSpecimen los : newLabOrderSpecimen.getOrder().getOrderSpecimens()) {
			if (los.equals(newLabOrderSpecimen)) {
				needToAdd = false;
				break;
			}
		}
		if (needToAdd)
			newLabOrderSpecimen.getOrder().getOrderSpecimens().add(newLabOrderSpecimen);
		Context.getService(LabOrderService.class).saveLabOrder(newLabOrderSpecimen.getOrder());
		return newLabOrderSpecimen;
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResource#newDelegate()
	 */
	@Override
	public LabOrderSpecimen newDelegate() {
		return new LabOrderSpecimen();
	}
	
	/**
	 * Gets the display string for a labOrderSpecimen.
	 * 
	 * @param labOrderSpecimen the labOrderSpecimen object.
	 * @return the display string.
	 */
	public String getDisplayString(LabOrderSpecimen labOrderSpecimen) {
		StringBuilder ret = new StringBuilder("Order: ");
		if (labOrderSpecimen.getOrder() == null)
			ret.append(labOrderSpecimen.getOrder().getLabOrderId());
		else
			ret.append("[none]");
		ret.append(" Specimen: ");
		if (labOrderSpecimen.getSpecimen() == null)
			ret.append(labOrderSpecimen.getSpecimen().getLabSpecimenId());
		else
			ret.append("[none]");
		return ret.toString();
	}
}
