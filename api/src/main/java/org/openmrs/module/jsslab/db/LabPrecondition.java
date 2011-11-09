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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
	
	private Double sortOrder;
	
	public Integer getId() {
		return preconditionId;
	}

	public void setId(Integer preconditionId) {
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
	 * @return Returns sortOrder.
	 */
	@Attribute(required = false)
	public Double getSortOrder() {
		return sortOrder;
	}
	
	/**
	 * @param sortOrder.  The sortOrder to set.
	 */
	@Attribute(required = false)
	public void setSortOrder(Double sortOrder) {
		this.sortOrder = sortOrder;
	}
	

}
