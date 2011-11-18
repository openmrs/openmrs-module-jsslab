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
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.User;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab test specimen. The processed specimen type that can actually be tested.
 * 
 */
@Root(strict = false)
public class LabTestSpecimen extends BaseOpenmrsData implements Serializable {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabTestSpecimen.class);
	
	private Integer testSpecimenId;

	private LabSpecimen specimen;
	
	private String specimenSubId;
	
	private String parentSubId;
	
	private Concept parentRelationConcept;
	
	private Concept analysisSpecimenTypeConcept;
	
	private LabTestPanel testPanel;
	
	private Concept testRoleConcept;
	
	private LabSupplyItem supplyItem;
	
	private User prepTech;
	
	private Date prepDate;
	
	private String rackPosition;
	
	private User labTech;
	
	private Date testDate;
	
	private LabTestRun testRun;
	
	private String wellPosition;
	
	protected Set<LabTestResult> testResults = new HashSet<LabTestResult>();
	
	public Integer getId() {
		return testSpecimenId;
	}

	public void setId(Integer testSpecimenId) {
		this.testSpecimenId = testSpecimenId;
	}

	/**
	 * @return Returns the base specimen .
	 */
	@Attribute(required = true)
	public LabSpecimen getSpecimen() {
		return specimen;
	}
	
	/**
	 * @param specimen The specimen id to set.
	 */
	@Attribute(required = true)
	public void setSpecimen(LabSpecimen specimen) {
		this.specimen = specimen;
	}
	
	/**
	 * @return Returns the specimen id suffix.
	 */
	@Attribute(required = true)
	public String getSpecimenSubId() {
		return specimenSubId;
	}
	
	/**
	 * @param specimenSubId The specimen suffix to set.
	 */
	@Attribute(required = true)
	public void setSpecimenSubId(String specimenSubId) {
		this.specimenSubId = specimenSubId;
	}
	
	/**
	 * @return Returns the parent id suffix.
	 */
	@Attribute(required = true)
	public String getParentSubId() {
		return parentSubId;
	}
	
	/**
	 * @param parentSubId The parent's specimen suffix to set.
	 */
	@Attribute(required = true)
	public void setParentSubId(String parentSubId) {
		this.parentSubId = parentSubId;
	}
	
	/**
	 * @return Returns the parentRelation concept.
	 */
	@Attribute(required = false)
	public Concept getParentRelationConcept() {
		return parentRelationConcept;
	}
	
	/**
	 * @param parentRelationConcept The parentRelationConcept to set.
	 */
	@Attribute(required = false)
	public void setParentRelationConcept(Concept parentRelationConcept) {
		this.parentRelationConcept = parentRelationConcept;
	}
	
	/**
	 * @return Returns the analysis specimen type concept.
	 */
	@Attribute(required = false)
	public Concept getAnalysisSpecimenTypeConcept() {
		return analysisSpecimenTypeConcept;
	}
	
	/**
	 * @param analysisSpecimenTypeConcept The analysisSpecimenTypeConcept to set.
	 */
	@Attribute(required = false)
	public void setAnalysisSpecimenTypeConcept(Concept analysisSpecimenTypeConcept) {
		this.analysisSpecimenTypeConcept = analysisSpecimenTypeConcept;
	}
	
	/**
	 * @return Returns the test panel
	 */
	@Attribute(required = false)
	public LabTestPanel getLabTestPanel() {
		return testPanel;
	}
	
	/**
	 * @param labTestPanel The labTestPanel to set.
	 */
	@Attribute(required = false)
	public void setLabTestPanel(LabTestPanel testPanel) {
		this.testPanel = testPanel;
	}
	
	/**
	 * @return Returns the test role concept.
	 */
	@Attribute(required = false)
	public Concept getTestRoleConcept() {
		return testRoleConcept;
	}
	
	/**
	 * @param testRoleConcept The testRoleConcept to set.
	 */
	@Attribute(required = false)
	public void setTestRoleConcept(Concept testRoleConcept) {
		this.testRoleConcept = testRoleConcept;
	}
	
	/**
	 * @return Returns the supplyItem.
	 */
	@Attribute(required = false)
	public LabSupplyItem getSupplyItem() {
		return supplyItem;
	}
	
	/**
	 * @param supplyItem The supplyItem to set.
	 */
	@Attribute(required = false)
	public void setSupplyItem(LabSupplyItem supplyItem) {
		this.supplyItem = supplyItem;
	}
	
	/**
	 * @return Returns the prepTech user.
	 * TODO: change to provider
	 */
	@Attribute(required = false)
	public User getPrepTech() {
		return prepTech;
	}
	
	/**
	 * @param prepTech The prepTech user to set.
	 * TODO: change to provider
	 */
	@Attribute(required = false)
	public void setPrepTech(User prepTech) {
		this.prepTech = prepTech;
	}
	
	/**
	 * @return Returns the prepDate.
	 */
	@Attribute(required = false)
	public Date getPrepDate() {
		return prepDate;
	}
	
	/**
	 * @param prepDate.  The prepDate to set.
	 */
	@Attribute(required = false)
	public void setPrepDate(Date prepDate) {
		this.prepDate = prepDate;
	}
	
	/**
	 * @return Returns the rackPosition.
	 */
	@Attribute(required = false)
	public String getRackPosition() {
		return rackPosition;
	}
	
	/**
	 * @param wellPosition The wellPosition to set.
	 */
	@Attribute(required = false)
	public void setRackPosition(String rackPosition) {
		this.rackPosition = rackPosition;
	}
	
	/**
	 * @return Returns the labTech user.
	 * TODO: change to provider
	 */
	@Attribute(required = false)
	public User getLabTech() {
		return labTech;
	}
	
	/**
	 * @param labTech The labTech user to set.
	 * TODO: change to provider
	 */
	@Attribute(required = false)
	public void setLabTech(User labTech) {
		this.labTech = labTech;
	}
	
	/**
	 * @return Returns the testDate.
	 */
	@Attribute(required = false)
	public Date getTestDate() {
		return testDate;
	}
	
	/**
	 * @param testDate.  The testDate to set.
	 */
	@Attribute(required = false)
	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}
	
	/**
	 * @return Returns the testRun.
	 */
	@Attribute(required = false)
	public LabTestRun getTestRun() {
		return testRun;
	}
	
	/**
	 * @param testRun.  The testRun to set.
	 */
	@Attribute(required = false)
	public void setTestRun(LabTestRun testRun) {
		this.testRun = testRun;
	}
	
	/**
	 * @return Returns the wellPosition.
	 */
	@Attribute(required = false)
	public String getWellPosition() {
		return wellPosition;
	}
	
	/**
	 * @param wellPosition The wellPosition to set.
	 */
	@Attribute(required = false)
	public void setWellPosition(String wellPosition) {
		this.wellPosition = wellPosition;
	}
	
	/**
	 * @return Returns the labTestResults set.
	 */
	@Attribute(required = false)
	public Set<LabTestResult> getTestResults(){
		return testResults;
	}
	
	/**
	 * @param TestResult The set of labTestResult to set.
	 */
	@Attribute(required = false)
	public void setTestResults(Set<LabTestResult> testResults) {
		this.testResults = testResults;
	}
	
}
