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
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.api.context.Context;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A precondition question. 
 * 
 */
@Root(strict = false)
public class LabPrecondition extends BaseOpenmrsData implements Serializable {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabPrecondition.class);
	
	private Integer preconditionId;

	private LabTestPanel testPanel;
	
	private Concept preconditionQuestionConcept;
	
	private Concept preconditionAnswerConcept;
	
	private Double sortWeight;
	
	private Locale textLocale;
	
	private String preconditionQuestionText;
	
	private String preconditionAnswerText;
	
	public LabPrecondition() {
		
		this.setUuid(UUID.randomUUID().toString());
	}
	
	@Override
	public int hashCode() {
		
		return this.getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			LabPrecondition temp = (LabPrecondition) other;
			return this.getUuid().equals(temp.getUuid());		
		} catch (Exception e) {
			return false;
		}
	}

	public Integer getId() {
		return preconditionId;
	}

	public void setId(Integer preconditionId) {
		this.preconditionId = preconditionId;
	}

	public Integer getPreconditionId() {
		return preconditionId;
	}

	public void setPreconditionId(Integer preconditionId) {
		this.preconditionId = preconditionId;
	}

	/**
	 * @return Returns testPanel.
	 */
	@Attribute(required = true)
	public LabTestPanel getTestPanel() {
		return testPanel;
	}
	
	/**
	 * @param testPanel.  The testPanel to set.
	 */
	@Attribute(required = true)
	public void setTestPanel(LabTestPanel testPanel) {
		this.testPanel = testPanel;
	}

	/**
	 * @return Returns the preconditionQuestionConcept concept.
	 */
	@Attribute(required = true)
	public Concept getPreconditionQuestionConcept() {
		return preconditionQuestionConcept;
	}
	
	/**
	 * @param preconditionQuestion The preconditionQuestion to set.  
	 */
	@Attribute(required = true)
	public void setPreconditionQuestionConcept(Concept preconditionQuestionConcept) {
		this.preconditionQuestionConcept = preconditionQuestionConcept;
	}
	
	/**
	 * @return Returns the preconditionAnswerConcept concept.
	 */
	@Attribute(required = true)
	public Concept getPreconditionAnswerConcept() {
		return preconditionAnswerConcept;
	}
	
	/**
	 * @param preconditionAnswer The preconditionAnswer to set.  
	 */
	@Attribute(required = true)
	public void setPreconditionAnswerConcept(Concept preconditionAnswerConcept) {
		this.preconditionAnswerConcept = preconditionAnswerConcept;
	}
	
	/**
	 * @return Returns sortWeight.
	 */
	@Attribute(required = false)
	public Double getSortWeight() {
		return sortWeight;
	}
	
	/**
	 * @param sortWeight.  The sortWeight to set.
	 */
	@Attribute(required = false)
	public void setSortWeight(Double sortWeight) {
		this.sortWeight = sortWeight;
	}

	/**
	 * Check for a locale change
	 */
	private void checkLocale() {
		if (textLocale == null) {
			preconditionQuestionText = "";
			preconditionAnswerText = "";
			textLocale = Context.getLocale();
		}
		else if (! textLocale.equals(Context.getLocale())) {
			preconditionQuestionText = "";
			preconditionAnswerText = "";
			textLocale = Context.getLocale();
		}
		return;
	}
	
	/**
	 * Get text corresponding to preconditionQuestionConcept
	 */
	public String getPreconditionQuestionText() {
		checkLocale();
		if (StringUtils.isEmpty(preconditionQuestionText)) {
			if (! (preconditionQuestionConcept == null)) {
				preconditionQuestionText = Context.getConceptService().getConceptName(this.getPreconditionQuestionConcept().getId()).getName();
			}
		}
		return preconditionQuestionText;
	}

	/**
	 * Get text corresponding to preconditionAnswerConcept
	 */
	public String getPreconditionAnswerText() {
		checkLocale();
		if (StringUtils.isEmpty(preconditionAnswerText)) {
			if (! (preconditionAnswerConcept == null)) {
				preconditionAnswerText = Context.getConceptService().getConceptName(this.getPreconditionAnswerConcept().getId()).getName();
				}
			}
		return preconditionAnswerText;
		}

	public String toString() {
		return "Precondition " + this.getId();
	}
	
	public String getName() {
		return this.getTestPanel().getName() + ": " + this.getPreconditionQuestionText();
	}
	
	public String getDisplayString() {
		return this.getPreconditionQuestionText() == null ? "" : this.getPreconditionQuestionText();
	}

}

