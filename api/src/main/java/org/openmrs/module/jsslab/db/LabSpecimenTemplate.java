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
import java.util.UUID;
import java.util.Locale;
import java.lang.Comparable;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab test specimen. The processed specimen type that can actually be tested.
 * 
 */
@Root(strict = false)
public class LabSpecimenTemplate extends BaseOpenmrsData implements Serializable {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabSpecimenTemplate.class);
	
	private Locale textLocale;
	
	private Integer specimenTemplateId;
	
	private Concept parentRoleConcept;
	
	private String parentRoleName;

	private String specimenSubId;
	
	private String parentSubId;
	
	private Concept parentRelationConcept;
	
	private String parentRelationName;
	
	private Concept analysisSpecimenTypeConcept;
	
	private String analysisSpecimenTypeName;
	
	private LabTestPanel testPanel;
	
	private Concept testRoleConcept;
	
	private String testRoleName;
	
	private LabSupplyItem supplyItem;
	
	private String instructions;
	
	public void LabSpecimenTemplate() {
		
		this.setUuid(UUID.randomUUID().toString());
	}
	
	@Override
	public int hashCode() {
		
		return this.getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			LabSpecimenTemplate temp = (LabSpecimenTemplate) other;
			return this.getUuid().equals(temp.getUuid());		
		} catch (Exception e) {
			return false;
		}
	}

	public Integer getId() {
		return specimenTemplateId;
	}

	public void setId(Integer specimenTemplateId) {
		this.specimenTemplateId = specimenTemplateId;
	}

	public Integer getSpecimenTemplateId() {
		return specimenTemplateId;
	}

	public void setSpecimenTemplateId(Integer specimenTemplateId) {
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

	public String getName() {
		return this.getTestPanel().getName() + " : " + getParentRelationConcept() + "/" + getParentSubId() + "/" + getSpecimenSubId();
	}

	/**
	 * Check for a locale change
	 */
	private void checkLocale() {
		if (! textLocale.equals(Context.getLocale())) {
			parentRoleName = "";
			parentRelationName = "";
			analysisSpecimenTypeName = "";
			testRoleName = "";
			textLocale = Context.getLocale();
		}
		return;
	}
	
	/**
	 * Get text corresponding to parentRoleConcept
	 */
	public String getParentRoleName() {
		checkLocale();
		if (StringUtils.isEmpty(parentRoleName)) {
			if (! (parentRoleConcept == null)) {
				parentRoleName = this.getParentRoleConcept().getName().getName();
			}
		}
		return parentRoleName;
	}

	/**
	 * Get text corresponding to parentRelationConcept
	 */
	public String getParentRelationName() {
		checkLocale();
		if (StringUtils.isEmpty(parentRelationName)) {
			if (! (parentRelationConcept == null)) {
				parentRelationName = this.getParentRelationConcept().getName().getName();
			}
		}
		return parentRelationName;
	}

	/**
	 * Get text corresponding to analysisSpecimenTypeConcept
	 */
	public String getAnalysisSpecimenTypeName() {
		checkLocale();
		if (StringUtils.isEmpty(analysisSpecimenTypeName)) {
			if (! (analysisSpecimenTypeConcept == null)) {
				analysisSpecimenTypeName = this.getAnalysisSpecimenTypeConcept().getName().getName();
			}
		}
		return analysisSpecimenTypeName;
	}

	/**
	 * Get text corresponding to testRoleConcept
	 */
	public String getTestRoleName() {
		checkLocale();
		if (StringUtils.isEmpty(testRoleName)) {
			if (! (testRoleConcept == null)) {
				testRoleName = Context.getConceptService().getConceptName(this.getTestRoleConcept().getId()).getName();
			}
		}
		return testRoleName;
	}


}
