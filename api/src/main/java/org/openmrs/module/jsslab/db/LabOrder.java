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
	public String getretestReason() {
		return retestReason;
	}
	
	/**
	 * @param retestReason The retestReason to set.
	 */
	@Attribute(required = false)
	public void setretestReason(String retestReason) {
		this.retestReason = retestReason;
	}
	
	/**
	 * @return Returns the specimens set.
	 */
	@Attribute(required = false)
	public Set<LabSpecimen> getSpecimens(){
		return specimens;
	}
	
	/**
	 * @param specimens The set of specimens to set.
	 */
	@Attribute(required = false)
	public void setSpecimens(Set<LabSpecimen> specimens) {
		this.specimens = specimens;
	}
	

	
	public String getAccessionNumber() {
		return order.getAccessionNumber();
	}

	public Date getAutoExpireDate() {
		return order.getAutoExpireDate();
	}

	public User getChangedBy() {
		return order.getChangedBy();
	}

	public Concept getConcept() {
		return order.getConcept();
	}

	public User getCreator() {
		return order.getCreator();
	}

	public Date getDateChanged() {
		return order.getDateChanged();
	}

	public Date getDateCreated() {
		return order.getDateCreated();
	}

	public Date getDateVoided() {
		return order.getDateVoided();
	}

	public Boolean getDiscontinued() {
		return order.getDiscontinued();
	}

	public User getDiscontinuedBy() {
		return order.getDiscontinuedBy();
	}

	public Date getDiscontinuedDate() {
		return order.getDiscontinuedDate();
	}

	public Concept getDiscontinuedReason() {
		return order.getDiscontinuedReason();
	}

	public Encounter getEncounter() {
		return order.getEncounter();
	}

	public Integer getId() {
		return order.getId();
	}

	public String getInstructions() {
		return order.getInstructions();
	}

	public Integer getOrderId() {
		return order.getOrderId();
	}

	public OrderType getOrderType() {
		return order.getOrderType();
	}

	public User getOrderer() {
		return order.getOrderer();
	}

	public Patient getPatient() {
		return order.getPatient();
	}

	public Date getStartDate() {
		return order.getStartDate();
	}

	public String getVoidReason() {
		return order.getVoidReason();
	}

	public Boolean getVoided() {
		return order.getVoided();
	}

	public User getVoidedBy() {
		return order.getVoidedBy();
	}

	public int hashCode() {
		return order.hashCode();
	}

	public boolean isCurrent() {
		return order.isCurrent();
	}

	public boolean isCurrent(Date checkDate) {
		return order.isCurrent(checkDate);
	}

	public boolean isDiscontinued(Date checkDate) {
		return order.isDiscontinued(checkDate);
	}

	public boolean isDiscontinuedRightNow() {
		return order.isDiscontinuedRightNow();
	}

	public boolean isDrugOrder() {
		return order.isDrugOrder();
	}

	public boolean isFuture() {
		return order.isFuture();
	}

	public boolean isFuture(Date checkDate) {
		return order.isFuture(checkDate);
	}

	public Boolean isVoided() {
		return order.isVoided();
	}

	public void setAccessionNumber(String accessionNumber) {
		order.setAccessionNumber(accessionNumber);
	}

	public void setAutoExpireDate(Date autoExpireDate) {
		order.setAutoExpireDate(autoExpireDate);
	}

	public void setChangedBy(User changedBy) {
		order.setChangedBy(changedBy);
	}

	public void setConcept(Concept concept) {
		order.setConcept(concept);
	}

	public void setCreator(User creator) {
		order.setCreator(creator);
	}

	public void setDateChanged(Date dateChanged) {
		order.setDateChanged(dateChanged);
	}

	public void setDateCreated(Date dateCreated) {
		order.setDateCreated(dateCreated);
	}

	public void setDateVoided(Date dateVoided) {
		order.setDateVoided(dateVoided);
	}

	public void setDiscontinued(Boolean discontinued) {
		order.setDiscontinued(discontinued);
	}

	public void setDiscontinuedBy(User discontinuedBy) {
		order.setDiscontinuedBy(discontinuedBy);
	}

	public void setDiscontinuedDate(Date discontinuedDate) {
		order.setDiscontinuedDate(discontinuedDate);
	}

	public void setDiscontinuedReason(Concept discontinuedReason) {
		order.setDiscontinuedReason(discontinuedReason);
	}

	public void setEncounter(Encounter encounter) {
		order.setEncounter(encounter);
	}

	public void setId(Integer id) {
		order.setId(id);
	}

	public void setInstructions(String instructions) {
		order.setInstructions(instructions);
	}

	public void setOrderId(Integer orderId) {
		order.setOrderId(orderId);
	}

	public void setOrderType(OrderType orderType) {
		order.setOrderType(orderType);
	}

	public void setOrderer(User orderer) {
		order.setOrderer(orderer);
	}

	public void setPatient(Patient patient) {
		order.setPatient(patient);
	}

	public void setStartDate(Date startDate) {
		order.setStartDate(startDate);
	}

	public void setVoidReason(String voidReason) {
		order.setVoidReason(voidReason);
	}

	public void setVoided(Boolean voided) {
		order.setVoided(voided);
	}

	public void setVoidedBy(User voidedBy) {
		order.setVoidedBy(voidedBy);
	}

	public String toString() {
		return "LabOrder:" + order.toString();
	}
	
	
	
}
