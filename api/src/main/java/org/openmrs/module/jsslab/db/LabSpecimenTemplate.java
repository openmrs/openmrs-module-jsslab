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
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Concept;
import org.openmrs.User;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab test specimen. The processed specimen type that can actually be tested.
 * 
 */
@Root(strict = false)
public class LabSpecimenTemplate extends BaseOpenmrsMetadata implements Serializable {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabSpecimenTemplate.class);
	
	private Integer specimenTemplateId;
	
	private Concept parentRoleConcept;

	private String specimenSubId;
	
	private String parentSubId;
	
	private Concept parentRelationConcept;
	
	private Concept analysisSpecimenTypeConcept;
	
	private LabTestPanel testPanel;
	
	private Concept testRoleConcept;
	
	private LabSupplyItem supplyItem;
	
	private String instructions;
	
	public Integer getId() {
		return specimenTemplateId;
	}

	public void setId(Integer specimenTemplateId) {
		this.specimenTemplateId = specimenTemplateId;
	}

	public Integer getTestSpecimenId() {
		return specimenTemplateId;
	}

	public void setTestSpecimenId(Integer specimenTemplateId) {
		this.specimenTemplateId = specimenTemplateId;
	}

	/**
	 * @return Returns the parentRole concept.
	 */
	@Attribute(required = true)
	public Concept getParentRoleConcept() {
		return parentRoleConcept;
	}
	
	/**
	 * @param parentRelationConcept The parentRelationConcept to set.
	 */
	@Attribute(required = true)
	public void setParentRoleConcept(Concept parentRoleConcept) {
		this.parentRoleConcept = parentRoleConcept;
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
	@Attribute(required = true)
	public Concept getParentRelationConcept() {
		return parentRelationConcept;
	}
	
	/**
	 * @param parentRelationConcept The parentRelationConcept to set.
	 */
	@Attribute(required = true)
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
	public LabTestPanel getTestPanel() {
		return testPanel;
	}
	
	/**
	 * @param labTestPanel The labTestPanel to set.
	 */
	@Attribute(required = false)
	public void setTestPanel(LabTestPanel testPanel) {
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
	 * @return Returns the instructions.
	 */
	@Attribute(required = false)
	public String getInstructions() {
		return instructions;
	}
	
	/**
	 * @param instructions The instructions to set.
	 */
	@Attribute(required = false)
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}
	
}
