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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab test specimen. The processed specimen type that can actually be tested.
 * 
 */
@Root(strict = false)
public class LabOrder extends BaseOpenmrsObject implements Auditable, Voidable, Serializable {
	
	public static final long serialVersionUID = 4334343L;
	
	private static final Log log = LogFactory.getLog(LabOrder.class);

	private Order order;
	
	private Boolean urgent;
	
	private LabOrder retestOf;
	
	private Boolean physicianRetest;
	
	private String retestReason;

	protected Set<LabSpecimen> specimens = new HashSet<LabSpecimen>();
	
	public Order getOrder() {
		return order;
	}
	
	public void setOrder(Order order) {
		this.order = order;
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
		CreateOrderIfNecessary();
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
		CreateOrderIfNecessary();
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
		CreateOrderIfNecessary();
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
		CreateOrderIfNecessary();
		this.retestReason = retestReason;
	}
	
	/**
	 * @return Returns the specimens set.
	 */
	@Attribute(required = false)
	public Set<LabSpecimen> getSpecimens(){
		if (order == null) {return null;}
		return specimens;
	}
	
	/**
	 * @param specimens The set of specimens to set.
	 */
	@Attribute(required = false)
	public void setSpecimens(Set<LabSpecimen> specimens) {
		CreateOrderIfNecessary();
		this.specimens = specimens;
	}
	

	
	public String getAccessionNumber() {
		if (order == null) {return null;}
		return order.getAccessionNumber();
	}

	public Date getAutoExpireDate() {
		if (order == null) {return null;}
		return order.getAutoExpireDate();
	}

	public User getChangedBy() {
		if (order == null) {return null;}
		return order.getChangedBy();
	}

	public Concept getConcept() {
		if (order == null) {return null;}
		return order.getConcept();
	}

	public User getCreator() {
		if (order == null) {return null;}
		return order.getCreator();
	}

	public Date getDateChanged() {
		if (order == null) {return null;}
		return order.getDateChanged();
	}

	public Date getDateCreated() {
		if (order == null) {return null;}
		return order.getDateCreated();
	}

	public Date getDateVoided() {
		if (order == null) {return null;}
		return order.getDateVoided();
	}

	public Boolean getDiscontinued() {
		if (order == null) {return null;}
		return order.getDiscontinued();
	}

	public User getDiscontinuedBy() {
		if (order == null) {return null;}
		return order.getDiscontinuedBy();
	}

	public Date getDiscontinuedDate() {
		if (order == null) {return null;}
		return order.getDiscontinuedDate();
	}

	public Concept getDiscontinuedReason() {
		if (order == null) {return null;}
		return order.getDiscontinuedReason();
	}

	public Encounter getEncounter() {
		if (order == null) {return null;}
		return order.getEncounter();
	}

	public Integer getId() {
		if (order == null) {return null;}
		return order.getId();
	}

	public String getInstructions() {
		if (order == null) {return null;}
		return order.getInstructions();
	}

	public Integer getOrderId() {
		if (order == null) {return null;}
		return order.getOrderId();
	}

	public OrderType getOrderType() {
		if (order == null) {return null;}
		return order.getOrderType();
	}

	public User getOrderer() {
		if (order == null) {return null;}
		return order.getOrderer();
	}

	public Patient getPatient() {
		if (order == null) {return null;}
		return order.getPatient();
	}

	public Date getStartDate() {
		if (order == null) {return null;}
		return order.getStartDate();
	}

	public String getVoidReason() {
		if (order == null) {return null;}
		return order.getVoidReason();
	}

	public Boolean getVoided() {
		if (order == null) {return null;}
		return order.getVoided();
	}

	public User getVoidedBy() {
		if (order == null) {return null;}
		return order.getVoidedBy();
	}

	public int hashCode() {
		CreateOrderIfNecessary();
		return order.hashCode();
	}

	public boolean isCurrent() {
		if (order == null) {return false;}
		return order.isCurrent();
	}

	public boolean isCurrent(Date checkDate) {
		if (order == null) {return false;}
		return order.isCurrent(checkDate);
	}

	public boolean isDiscontinued(Date checkDate) {
		if (order == null) {return false;}
		return order.isDiscontinued(checkDate);
	}

	public boolean isDiscontinuedRightNow() {
		if (order == null) {return false;}
		return order.isDiscontinuedRightNow();
	}

	public boolean isDrugOrder() {
		return false;
	}

	public boolean isFuture() {
		if (order == null) {return false;}
		return order.isFuture();
	}

	public boolean isFuture(Date checkDate) {
		if (order == null) {return false;}
		return order.isFuture(checkDate);
	}

	public Boolean isVoided() {
		if (order == null) {return false;}
		return order.isVoided();
	}

	public void setAccessionNumber(String accessionNumber) {
		CreateOrderIfNecessary();
		order.setAccessionNumber(accessionNumber);
	}

	public void setAutoExpireDate(Date autoExpireDate) {
		CreateOrderIfNecessary();
		order.setAutoExpireDate(autoExpireDate);
	}

	public void setChangedBy(User changedBy) {
		CreateOrderIfNecessary();
		order.setChangedBy(changedBy);
	}

	public void setConcept(Concept concept) {
		CreateOrderIfNecessary();
		order.setConcept(concept);
	}

	public void setCreator(User creator) {
		CreateOrderIfNecessary();
		order.setCreator(creator);
	}

	public void setDateChanged(Date dateChanged) {
		CreateOrderIfNecessary();
		order.setDateChanged(dateChanged);
	}

	public void setDateCreated(Date dateCreated) {
		CreateOrderIfNecessary();
		order.setDateCreated(dateCreated);
	}

	public void setDateVoided(Date dateVoided) {
		CreateOrderIfNecessary();
		order.setDateVoided(dateVoided);
	}

	public void setDiscontinued(Boolean discontinued) {
		CreateOrderIfNecessary();
		order.setDiscontinued(discontinued);
	}

	public void setDiscontinuedBy(User discontinuedBy) {
		CreateOrderIfNecessary();
		order.setDiscontinuedBy(discontinuedBy);
	}

	public void setDiscontinuedDate(Date discontinuedDate) {
		CreateOrderIfNecessary();
		order.setDiscontinuedDate(discontinuedDate);
	}

	public void setDiscontinuedReason(Concept discontinuedReason) {
		CreateOrderIfNecessary();
		order.setDiscontinuedReason(discontinuedReason);
	}

	public void setEncounter(Encounter encounter) {
		CreateOrderIfNecessary();
		order.setEncounter(encounter);
	}

	public void setId(Integer id) {
		CreateOrderIfNecessary();
		order.setId(id);
	}

	public void setInstructions(String instructions) {
		CreateOrderIfNecessary();
		order.setInstructions(instructions);
	}

	public void setOrderId(Integer orderId) {
		CreateOrderIfNecessary();
		order.setOrderId(orderId);
	}

	public void setOrderType(OrderType orderType) {
		CreateOrderIfNecessary();
		order.setOrderType(orderType);
	}

	public void setOrderer(User orderer) {
		CreateOrderIfNecessary();
		order.setOrderer(orderer);
	}

	public void setPatient(Patient patient) {
		CreateOrderIfNecessary();
		order.setPatient(patient);
	}

	public void setStartDate(Date startDate) {
		CreateOrderIfNecessary();
		order.setStartDate(startDate);
	}

	public void setVoidReason(String voidReason) {
		CreateOrderIfNecessary();
		order.setVoidReason(voidReason);
	}

	public void setVoided(Boolean voided) {
		CreateOrderIfNecessary();
		order.setVoided(voided);
	}

	public void setVoidedBy(User voidedBy) {
		CreateOrderIfNecessary();
		order.setVoidedBy(voidedBy);
	}

	public String toString() {
		CreateOrderIfNecessary();
		return "LabOrder:" + order.toString();
	}
	
	private void CreateOrderIfNecessary() {
		if (this.order == null) {this.order = new Order();}
	}
	
}
