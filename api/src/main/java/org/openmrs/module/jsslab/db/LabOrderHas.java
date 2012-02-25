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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab test specimen. The processed specimen type that can actually be tested.
 * 
 */
@Root(strict = false)
public class LabOrderHas extends BaseOpenmrsObject implements Auditable, Voidable, Serializable {
	
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
		if (this.getOrder() == null) {return null;}
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
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getAccessionNumber();
	}

	public Date getAutoExpireDate() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getAutoExpireDate();
	}

	public User getChangedBy() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getChangedBy();
	}

	public Concept getConcept() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getConcept();
	}

	public User getCreator() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getCreator();
	}

	public Date getDateChanged() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getDateChanged();
	}

	public Date getDateCreated() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getDateCreated();
	}

	public Date getDateVoided() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getDateVoided();
	}

	public Boolean getDiscontinued() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getDiscontinued();
	}

	public User getDiscontinuedBy() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getDiscontinuedBy();
	}

	public Date getDiscontinuedDate() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getDiscontinuedDate();
	}

	public Concept getDiscontinuedReason() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getDiscontinuedReason();
	}

	public Encounter getEncounter() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getEncounter();
	}

	public Integer getId() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getId();
	}

	public String getInstructions() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getInstructions();
	}

	public Integer getOrderId() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getOrderId();
	}

	public OrderType getOrderType() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getOrderType();
	}

	public User getOrderer() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getOrderer();
	}

	public Patient getPatient() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getPatient();
	}

	public Date getStartDate() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getStartDate();
	}

	public String getVoidReason() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getVoidReason();
	}

	public Boolean getVoided() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getVoided();
	}

	public User getVoidedBy() {
		if (this.getOrder() == null) {return null;}
		return this.getOrder().getVoidedBy();
	}

	public int hashCode() {
		CreateOrderIfNecessary();
		return this.getOrder().hashCode();
	}

	public boolean equals(Object other) {
		if (this.getOrder() == null) {
			return false;
		} else {
			try {
				LabOrder temp = (LabOrder) other;
// TODO: UNCOMMENT NEXT LINE AND COMMENT SUBSEQUENT LINE IF THIS IS USED
//				return this.getOrder().equals(temp.getOrder());
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}
	@Override
	public String getUuid() {
		CreateOrderIfNecessary();
		return this.getOrder().getUuid();
	}
	
	@Override
	public void setUuid(String uuid) {
		CreateOrderIfNecessary();
		this.getOrder().setUuid(uuid);
	}
	public boolean isCurrent() {
		if (this.getOrder() == null) {return false;}
		return this.getOrder().isCurrent();
	}

	public boolean isCurrent(Date checkDate) {
		if (this.getOrder() == null) {return false;}
		return this.getOrder().isCurrent(checkDate);
	}

	public boolean isDiscontinued(Date checkDate) {
		if (this.getOrder() == null) {return false;}
		return this.getOrder().isDiscontinued(checkDate);
	}

	public boolean isDiscontinuedRightNow() {
		if (this.getOrder() == null) {return false;}
		return this.getOrder().isDiscontinuedRightNow();
	}

	public boolean isDrugOrder() {
		return false;
	}

	public boolean isFuture() {
		if (this.getOrder() == null) {return false;}
		return this.getOrder().isFuture();
	}

	public boolean isFuture(Date checkDate) {
		if (this.getOrder() == null) {return false;}
		return this.getOrder().isFuture(checkDate);
	}

	public Boolean isVoided() {
		if (this.getOrder() == null) {return false;}
		return this.getOrder().isVoided();
	}

	public void setAccessionNumber(String accessionNumber) {
		CreateOrderIfNecessary();
		this.getOrder().setAccessionNumber(accessionNumber);
	}

	public void setAutoExpireDate(Date autoExpireDate) {
		CreateOrderIfNecessary();
		this.getOrder().setAutoExpireDate(autoExpireDate);
	}

	public void setChangedBy(User changedBy) {
		CreateOrderIfNecessary();
		this.getOrder().setChangedBy(changedBy);
	}

	public void setConcept(Concept concept) {
		CreateOrderIfNecessary();
		this.getOrder().setConcept(concept);
	}

	public void setCreator(User creator) {
		CreateOrderIfNecessary();
		this.getOrder().setCreator(creator);
	}

	public void setDateChanged(Date dateChanged) {
		CreateOrderIfNecessary();
		this.getOrder().setDateChanged(dateChanged);
	}

	public void setDateCreated(Date dateCreated) {
		CreateOrderIfNecessary();
		this.getOrder().setDateCreated(dateCreated);
	}

	public void setDateVoided(Date dateVoided) {
		CreateOrderIfNecessary();
		this.getOrder().setDateVoided(dateVoided);
	}

	public void setDiscontinued(Boolean discontinued) {
		CreateOrderIfNecessary();
		this.getOrder().setDiscontinued(discontinued);
	}

	public void setDiscontinuedBy(User discontinuedBy) {
		CreateOrderIfNecessary();
		this.getOrder().setDiscontinuedBy(discontinuedBy);
	}

	public void setDiscontinuedDate(Date discontinuedDate) {
		CreateOrderIfNecessary();
		this.getOrder().setDiscontinuedDate(discontinuedDate);
	}

	public void setDiscontinuedReason(Concept discontinuedReason) {
		CreateOrderIfNecessary();
		this.getOrder().setDiscontinuedReason(discontinuedReason);
	}

	public void setEncounter(Encounter encounter) {
		CreateOrderIfNecessary();
		this.getOrder().setEncounter(encounter);
	}

	public void setId(Integer id) {
		CreateOrderIfNecessary();
		this.getOrder().setId(id);
	}

	public void setInstructions(String instructions) {
		CreateOrderIfNecessary();
		this.getOrder().setInstructions(instructions);
	}

	public void setOrderId(Integer orderId) {
		CreateOrderIfNecessary();
		this.getOrder().setOrderId(orderId);
	}

	public void setOrderType(OrderType orderType) {
		CreateOrderIfNecessary();
		this.getOrder().setOrderType(orderType);
	}

	public void setOrderer(User orderer) {
		CreateOrderIfNecessary();
		this.getOrder().setOrderer(orderer);
	}

	public void setPatient(Patient patient) {
		CreateOrderIfNecessary();
		this.getOrder().setPatient(patient);
	}

	public void setStartDate(Date startDate) {
		CreateOrderIfNecessary();
		this.getOrder().setStartDate(startDate);
	}

	public void setVoidReason(String voidReason) {
		CreateOrderIfNecessary();
		this.getOrder().setVoidReason(voidReason);
	}

	public void setVoided(Boolean voided) {
		CreateOrderIfNecessary();
		this.getOrder().setVoided(voided);
	}

	public void setVoidedBy(User voidedBy) {
		CreateOrderIfNecessary();
		this.getOrder().setVoidedBy(voidedBy);
	}

	public String toString() {
		CreateOrderIfNecessary();
		return "LabOrder:" + order.toString();
	}
	
	private void CreateOrderIfNecessary() {
		if (this.getOrder() == null) 
			{this.setOrder(new Order());
		}
	}
	
}
