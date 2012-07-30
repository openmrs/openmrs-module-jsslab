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
package org.openmrs.module.jsslab.rest.v1_0.resource;

import java.lang.reflect.Method;
import java.util.List;

import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabOrderService;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingSubclassHandler;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubclassHandler;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResourceDoesNotSupportOperationException;
import org.openmrs.module.webservices.rest.web.v1_0.resource.OrderResource;
import org.springframework.util.ReflectionUtils;

/**
 * Exposes the {@link LabOrder} subclass as a type in {@link OrderResource}
 */
@Handler(supports = LabOrder.class)
public class LabOrderSubclassHandler extends BaseDelegatingSubclassHandler<Order, LabOrder> implements DelegatingSubclassHandler<Order, LabOrder> {
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingSubclassHandler#getTypeName()
	 */
	@Override
	public String getTypeName() {
		return "laborder";
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceHandler#newDelegate()
	 */
	@Override
	public LabOrder newDelegate() {
		return new LabOrder();
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceHandler#getAllByType(org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	public PageableResult getAllByType(RequestContext context) throws ResourceDoesNotSupportOperationException {
		return new NeedsPaging<LabOrder>(Context.getService(LabOrderService.class).getAllLabOrders(), context);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceHandler#getRepresentationDescription(org.openmrs.module.webservices.rest.web.representation.Representation)
	 */
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		if (rep instanceof DefaultRepresentation) {
			//			
			description.addProperty("uuid");
			description.addProperty("labOrderId");
			description.addProperty("concept",Representation.REF);
			description.addProperty("orderer",Representation.REF);
			description.addProperty("startDate");
			description.addProperty("voided");
			description.addSelfLink();
			description.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return description;
		}
		else if (rep instanceof FullRepresentation) {
			description.addProperty("uuid");
			description.addProperty("labOrderId");
			description.addProperty("urgent");
			description.addProperty("retestOf", Representation.REF);
			description.addProperty("physicianRetest");
			description.addProperty("retestReason");
			description.addProperty("orderType", Representation.REF);
			description.addProperty("patient", Representation.DEFAULT);
			description.addProperty("concept",Representation.REF);
			description.addProperty("orderer",Representation.REF);
			description.addProperty("encounter",Representation.REF);
			description.addProperty("instructions");
			description.addProperty("startDate");
			description.addProperty("autoExpireDate");
			description.addProperty("discontinued");
			description.addProperty("discontinuedDate");
			description.addProperty("discontinuedBy",Representation.REF);
			description.addProperty("discontinuedReason");
			description.addProperty("voided");
			description.addSelfLink();
			Method m = ReflectionUtils.findMethod(getResource().getClass(), "getAuditInfo",(Class<?>[]) null);
			description.addProperty("auditInfo",m);
			return description;
		}
		return null;
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceHandler#getCreatableProperties()
	 */
	@Override
	public DelegatingResourceDescription getCreatableProperties() {
		OrderResource orderResource = Context.getService(RestService.class).getResource(OrderResource.class);
		DelegatingResourceDescription d = orderResource.getCreatableProperties();
		d.addRequiredProperty("urgent");
		d.addProperty("retestOf");
		d.addProperty("physicianRetest");
		d.addProperty("retestReason");
		d.addProperty("orderSpecimens");
		
		// LabOrders have a specific hardcoded value for this property
		d.removeProperty("orderType");
		return d;
	}
	
	/**
	 * Handles getOrdersByPatient for {@link OrderResource} when type=laborder
	 * 
	 * @param patient
	 * @param context
	 * @return
	 */
	public PageableResult getOrdersByPatient(Patient patient, RequestContext context) {
		List<LabOrder> orders = Context.getService(LabOrderService.class).getLabOrdersByPatient(patient);
		return new NeedsPaging<LabOrder>(orders, context);
	}
	
	/**
	 * Gets a user-friendly display representation of the delegate
	 * 
	 * @param o
	 * @return
	 */
	@PropertyGetter("display")
	public static String getDisplay(LabOrder delegate) {
		if (delegate.getConcept() == null)
			return delegate.toString();
		else
			return delegate.getConcept().getName().getName();
	}
}
