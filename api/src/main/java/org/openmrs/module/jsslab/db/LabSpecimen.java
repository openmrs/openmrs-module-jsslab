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

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.Concept;
import org.openmrs.Location;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab specimen. All test specimens are contained in a single lab specimen.
 * 
 */
@Root(strict = false)
public class LabSpecimen extends BaseOpenmrsData implements Serializable {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabSpecimen.class);
	
	private Integer specimenId;
	
	private String labSpecimenId;
	
	private String clientSpecimenId;
	
	private User orderedBy;
	
	private Location orderedByFacility;
	
	private Person person;
	
	private Patient patient;
	
	private Date specimenDate;
	
	private Date receivedDate;
	
	private Concept receivedConditionConcept;
	
	private Concept receivedSpecimenTypeConcept;
	
	private LabReport report;
	
	private Date resultRequestedBy;
	
	private Boolean hidden;
	
	protected Set<LabTestSpecimen> testSpecimens = new HashSet<LabTestSpecimen>();
	
	protected Set<LabOrder> orders = new HashSet<LabOrder>();
	
	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return specimenId;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer specimenId) {
		this.specimenId = specimenId;
	}

	/**
	 * @return Returns the labSpecimenId.
	 */
	@Attribute(required = true)
	public String getLabSpecimenId() {
		return labSpecimenId;
	}
	
	/**
	 * @param labSpecimenId The labSpecimenId to set.
	 */
	@Attribute(required = true)
	public void setLabSpecimenId(String labSpecimenId) {
		this.labSpecimenId = labSpecimenId;
	}
	
	/**
	 * @return Returns the clientSpecimenId.
	 */
	@Attribute(required = false)
	public String getClientSpecimenId() {
		return clientSpecimenId;
	}
	
	/**
	 * @param clientSpecimenId The clientSpecimenId to set.
	 */
	@Attribute(required = false)
	public void setClientSpecimenId(String clientSpecimenId) {
		this.clientSpecimenId = clientSpecimenId;
	}
	
	/**
	 * @return Returns the ordered by user.
	 * TODO: change to provider
	 */
	@Attribute(required = false)
	public User getOrderedBy() {
		return orderedBy;
	}
	
	/**
	 * @param orderedBy The ordered by user to set.
	 * TODO: change to provider
	 */
	@Attribute(required = false)
	public void setOrderedBy(User orderedBy) {
		this.orderedBy = orderedBy;
	}
	
	/**
	 * @return Returns the ordered by Facility.
	 */
	@Attribute(required = false)
	public Location getOrderedByFacility() {
		return orderedByFacility;
	}
	
	/**
	 * @param orderedByFacility The ordered by Facility to set.
	 */
	@Attribute(required = false)
	public void setOrderedByFacility(Location orderedByFacility) {
		this.orderedByFacility = orderedByFacility;
	}
	
	/**
	 * @return Returns the patient.
	 */
	@Attribute(required = false)
	public Patient getPatient() {
		return patient;
	}
	
	/**
	 * @param patient The patient to set.
	 */
	@Attribute(required = false)
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	/**
	 * @return Returns the person.
	 */
	@Attribute(required = false)
	public Person getPerson() {
		return person;
	}
	
	/**
	 * @param person The person to set.
	 */
	@Attribute(required = false)
	public void setPerson(Person person) {
		this.person = person;
	}
	
	/**
	 * @return Returns the specimen date.
	 */
	@Attribute(required = true)
	public Date getSpecimenDate() {
		return specimenDate;
	}
	
	/**
	 * @param specimen date.  The specimen date to set.
	 */
	@Attribute(required = true)
	public void setSpecimenDate(Date specimenDate) {
		this.specimenDate = specimenDate;
	}
	
	/**
	 * @return Returns the received date.
	 */
	@Attribute(required = true)
	public Date getReceivedDate() {
		return receivedDate;
	}
	
	/**
	 * @param received date.  The received date to set.
	 */
	@Attribute(required = true)
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	/**
	 * @return Returns the received condition concept.
	 */
	@Attribute(required = false)
	public Concept getReceivedConditionConcept() {
		return receivedConditionConcept;
	}
	
	/**
	 * @param receivedConditionConcept The receivedConditionConcept to set.
	 */
	@Attribute(required = false)
	public void setReceivedConditionConcept(Concept receivedConditionConcept) {
		this.receivedConditionConcept = receivedConditionConcept;
	}
	
	/**
	 * @return Returns the received specimen type concept.
	 */
	@Attribute(required = true)
	public Concept getReceivedSpecimenTypeConcept() {
		return receivedSpecimenTypeConcept;
	}
	
	/**
	 * @param receivedSpecimenTypeConcept The receivedSpecimenTypeConcept to set.
	 */
	@Attribute(required = true)
	public void setReceivedSpecimenTypeConcept(Concept receivedSpecimenTypeConcept) {
		this.receivedSpecimenTypeConcept = receivedSpecimenTypeConcept;
	}
	
	/**
	 * @return Returns the lab report.
	 */
	@Attribute(required = true)
	public LabReport getReport() {
		return report;
	}
	
	/**
	 * @param labReport The labReport to set.
	 */
	@Attribute(required = true)
	public void setReport(LabReport report) {
		this.report = report;
	}
	
	/**
	 * @return Returns the result requested date.
	 */
	@Attribute(required = false)
	public Date getResultRequestedBy() {
		return resultRequestedBy;
	}
	
	/**
	 * @param result requested date.  The result requested date to set.
	 */
	@Attribute(required = false)
	public void setResultRequestedBy(Date resultRequestedBy) {
		this.resultRequestedBy = resultRequestedBy;
	}
	
	/**
	 * @return Returns the hidden flag.
	 */
	@Attribute(required = false)
	public Boolean getHidden() {
		return hidden;
	}
	
	/**
	 * @return Returns the hidden flag truth value.
	 */
	@Attribute(required = false)
	public Boolean isHidden() {
		return hidden.equals(Boolean.TRUE);
	}
	
	/**
	 * @param hidden.  The hidden value to set.
	 */
	@Attribute(required = false)
	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}
	
	/**
	 * @return Returns the test specimens set.
	 */
	@Attribute(required = false)
	public Set<LabTestSpecimen> getTestSpecimens(){
		return testSpecimens;
	}
	
	/**
	 * @param orders The set of specimens to set.
	 */
	@Attribute(required = false)
	public void setSpecimens(Set<LabTestSpecimen> TestSpecimens) {
		this.testSpecimens = testSpecimens;
	}
	

}
