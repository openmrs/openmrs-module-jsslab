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
import java.math.BigDecimal;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Location;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab test panel. What tests the doctor can order.
 * 
 */
@Root(strict = false)
public class LabTestPanel extends BaseOpenmrsMetadata implements Serializable, Comparable<LabTestPanel> {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabTestPanel.class);
	
	private Integer testPanelId;
	
	private Location labLocation;
	
	private String labLocationName;
	
	private Concept testGroupConcept;
	
	private String testGroupName;
	
	private Concept testPanelConcept;
	
	private String testPanelName;
	
	private String testPanelCode;
	
	private Concept receivedSpecimenTypeConcept;
	
	private Concept analysisSpecimenTypeConcept;
	
	private BigDecimal cost;
	
	private Double turnaround;
	
	private Double holdTime;
	
	private Set<LabTest> tests = new HashSet<LabTest>();
	
	private Set<LabPrecondition> preconditions = new HashSet<LabPrecondition>();
	
	public Integer getId() {
		return testPanelId;
	}

	public void setId(Integer testPanelId) {
		this.testPanelId = testPanelId;
	}

	public Integer getTestPanelId() {
		return testPanelId;
	}

	public void setTestPanelId(Integer testPanelId) {
		this.testPanelId = testPanelId;
	}

	/**
	 * @return Returns labLocation.
	 */
	@Attribute(required = true)
	public Location getLabLocation() {
		return labLocation;
	}
	
	/**
	 * @param labLocation.  The labLocation to set.
	 */
	@Attribute(required = true)
	public void setLabLocation(Location labLocation) {
		this.labLocation = labLocation;
		this.labLocationName = null;
	}
	
	/**
	 * @return Returns the testGroupConcept concept.
	 */
	@Attribute(required = true)
	public Concept getTestGroupConcept() {
		return testGroupConcept;
	}
	
	/**
	 * @param testGroupConcept The testGroupConcept to set.  The groups should represent lab sections or specialties
	 */
	@Attribute(required = true)
	public void setTestGroupConcept(Concept testGroupConcept) {
		this.testGroupConcept = testGroupConcept;
		this.testGroupName = null;
	}
	
	/**
	 * @return Returns the testPanelConcept concept.
	 */
	@Attribute(required = true)
	public Concept getTestPanelConcept() {
		return testPanelConcept;
	}
	
	/**
	 * @param testPanelConcept The testPanelConcept to set.
	 */
	@Attribute(required = true)
	public void setTestPanelConcept(Concept testPanelConcept) {
		this.testPanelConcept = testPanelConcept;
		this.testPanelName = null;
	}
	
	/**
	 * @return Returns the testPanelCode .
	 */
	@Attribute(required = false)
	public String getTestPanelCode() {
		return testPanelCode;
	}
	
	/**
	 * @param testPanelCode The testPanelCode to set.
	 */
	@Attribute(required = false)
	public void setTestPanelCode(String testPanelCode) {
		this.testPanelCode = testPanelCode;
	}
	
	/**
	 * @return Returns the receivedSpecimenTypeConcept concept.
	 */
	@Attribute(required = false)
	public Concept getReceivedSpecimenTypeConcept() {
		return receivedSpecimenTypeConcept;
	}
	
	/**
	 * @param receivedSpecimenType The receivedSpecimenType to set.
	 */
	@Attribute(required = false)
	public void setReceivedSpecimenTypeConcept(Concept receivedSpecimenTypeConcept) {
		this.receivedSpecimenTypeConcept = receivedSpecimenTypeConcept;
	}
	
	/**
	 * @return Returns the analysisSpecimenTypeConcept concept.
	 */
	@Attribute(required = false)
	public Concept getAnalysisSpecimenTypeConcept() {
		return analysisSpecimenTypeConcept;
	}
	
	/**
	 * @param analysisSpecimenType The analysisSpecimenType to set.
	 */
	@Attribute(required = false)
	public void setAnalysisSpecimenTypeConcept(Concept analysisSpecimenTypeConcept) {
		this.analysisSpecimenTypeConcept = analysisSpecimenTypeConcept;
	}
	
	/**
	 * @return Returns turnaround.
	 */
	@Attribute(required = false)
	public Double getTurnaround() {
		return turnaround;
	}
	
	/**
	 * @param turnaround.  The turnaround to set.
	 */
	@Attribute(required = false)
	public void setTurnaround(Double turnaround) {
		this.turnaround = turnaround;
	}
	
	/**
	 * @return Returns Cost.
	 */
	@Attribute(required = false)
	public BigDecimal getCost() {
		return cost;
	}
	
	/**
	 * @param cost.  The cost to set.
	 */
	@Attribute(required = false)
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	
	/**
	 * @return Returns holdTime.
	 */
	@Attribute(required = false)
	public Double getHoldTime() {
		return holdTime;
	}
	
	/**
	 * @param holdTime.  The holdTime to set.
	 */
	@Attribute(required = false)
	public void setHoldTime(Double holdTime) {
		this.holdTime = holdTime;
	}
	
	/**
	 * @return Returns the labPreconditions set.
	 */
	@Attribute(required = false)
	public Set<LabPrecondition> getPreconditions(){
		return preconditions;
	}
	
	/**
	 * @param labPrecondition The set of labPrecondition to set.
	 */
	@Attribute(required = false)
	public void setPreconditions(Set<LabPrecondition> preconditions) {
		this.preconditions = preconditions;
	}
	
	/**
	 * @return Returns the Tests set.
	 */
	@Attribute(required = false)
	public Set<LabTest> getTests(){
		return tests;
	}
	
	/**
	 * @param Tests The set of lTests to set.
	 */
	@Attribute(required = false)
	public void setTests(Set<LabTest> tests) {
		this.tests = tests;
	}
	
	/**
	 * @return Returns the labLocation name
	 */
	public String getLabLocationName(){
		if (labLocationName == null)
			labLocationName = this.getLabLocation().getName();
		return labLocationName;
	}
	
	/**
	 * @return Returns the Test Group name
	 */
	public String getTestGroupName(){
		if (testGroupName == null)
			testGroupName = Context.getConceptService().getConceptName(this.getTestGroupConcept().getId()).getName();
		return testGroupName;
	}
	
	/**
	 * @return Returns the Test Panel name
	 */
	public String getTestPanelName(){
		if (testPanelName == null)
			testPanelName = Context.getConceptService().getConceptName(this.getTestPanelConcept().getId()).getName();
		return testPanelName;
	}
	
	/**
	 * @return Returns the Test Panel full name
	 */
	public String getName(){
		return this.getTestGroupName() + ": " + this.getTestPanelName();
	}
	
	/**
	 * @return integer showing comparison result
	 */
	public int compareTo(LabTestPanel labTestPanel){
		return this.getName().compareToIgnoreCase(labTestPanel.getName());
	}
	
	
}
