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
package org.openmrs.module.jsslab.db;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.Auditable;
import org.openmrs.Voidable;
import org.openmrs.Encounter;
import org.openmrs.Order;
import org.openmrs.OrderType;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab order.  This contains the fields extending Order.
 * 
 */
@Root(strict = false)
public class LabOrder extends Order {
	
	private static final String LAB_ORDER_TYPE_PROPERTY_NAME = "jsslab.LabOrder";   
	
	public static final long serialVersionUID = 4334343L;
	
	private static final Log log = LogFactory.getLog(LabOrder.class);
	
	private String labOrderId;

	private Boolean urgent;
	
	private LabOrder retestOf;
	
	private Boolean physicianRetest;
	
	private String retestReason;

	protected Set<LabOrderSpecimen> orderSpecimens = new HashSet<LabOrderSpecimen>();
	
	public LabOrder() {
		super();
		this.setOrderType(Context.getOrderService().getOrderType(Integer.parseInt(Context.getAdministrationService().getGlobalProperty(LAB_ORDER_TYPE_PROPERTY_NAME))));
		return;
	}
	
	/**
	 * @return Returns the labOrderId.
	 */
	@Attribute(required = false)
	public String getLabOrderId() {
		return labOrderId;
	}
	
	/**
	 * @param labOrderId The LabOrderId to set.
	 */
	@Attribute(required = false)
	public void setLabOrderId(String labOrderId) {
		this.labOrderId = labOrderId;
	}
	
	/**
	 * @return Returns urgent .
	 */
	@Attribute(required = true)
	public Boolean getUrgent() {
		return urgent;
	}
	
	/**
	 * @param urgent The urgent flag to set.
	 */
	@Attribute(required = true)
	public void setUrgent(Boolean urgent) {
		this.urgent = urgent;
	}
	
	/**
	 * @return Returns the retestOf.
	 */
	@Attribute(required = false)
	public LabOrder getRetestOf() {
		return retestOf;
	}
	
	/**
	 * @param retestOf The retestOf to set.
	 */
	@Attribute(required = false)
	public void setRetestOf(LabOrder retestOf) {
		this.retestOf = retestOf;
	}
	
	/**
	 * @return Returns physicianRetest .
	 */
	@Attribute(required = false)
	public Boolean getPhysicianRetest() {
		return physicianRetest;
	}
	
	/**
	 * @param physicianRetest The physicianRetest flag to set.
	 */
	@Attribute(required = false)
	public void setPhysicianRetest(Boolean physicianRetest) {
		this.physicianRetest = physicianRetest;
	}
	
	/**
	 * @return Returns the retestReason.
	 */
	@Attribute(required = false)
	public String getRetestReason() {
		return retestReason;
	}
	
	/**
	 * @param retestReason The retestReason to set.
	 */
	@Attribute(required = false)
	public void setRetestReason(String retestReason) {
		this.retestReason = retestReason;
	}
	
	/**
	 * @return Returns the specimens set.
	 */
	@Attribute(required = false)
	public Set<LabOrderSpecimen> getOrderSpecimens() {
		return orderSpecimens;
	}
	
	/**
	 * @param specimens The set of specimens to set.
	 */
	@Attribute(required = false)
	public void setOrderSpecimens(Set<LabOrderSpecimen> orderSpecimens) {
		this.orderSpecimens = orderSpecimens;
	}

	public boolean isDrugOrder() {
		return false;
	}

	public void setOrderType(OrderType orderType) {
		return;
	}
	/**
	 * Compares two LabOrder objects for similarity
	 * 
	 * @param obj
	 * @return boolean true/false whether or not they are the same objects
	 */
	public boolean equals(Object obj) {
		if (obj instanceof LabOrder) {
			return (super.equals((Order) obj));
		}
		return false;
	}

	public String toString() {
		return "LabOrder " + super.getOrderId() + " " + (super.getConcept().getName());
	}

	@Override
	public String getUuid() {
		// TODO Auto-generated method stub
		return super.getUuid();
	}

	@Override
	public void setUuid(String uuid) {
		// TODO Auto-generated method stub
		super.setUuid(uuid);
	}
}
