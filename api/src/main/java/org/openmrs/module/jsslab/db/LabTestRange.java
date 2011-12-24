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

import org.openmrs.BaseOpenmrsData;
import org.openmrs.logic.rule.definition.RuleDefinition;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab test range. Child of test.
 * 
 */
@Root(strict = false)
public class LabTestRange extends BaseOpenmrsData implements Serializable {

	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabTestRange.class);
	
	private Integer testRangeId;
	
	private LabTest test;

	private Double sortOrder;
	
	private String rangeSex;
	
	private Double rangeAgeMin;
	
	private Double rangeAgeMax;
	
	private RuleDefinition logicRule;
	
	private Double rangeNormalLow;
	
	private Double rangeNormalHigh;
	
	private Double rangeCriticalLow;
	
	private Double rangeCriticalHigh;
	
	public Integer getId() {
		return testRangeId;
	}

	public void setId(Integer testRangeId) {
		this.testRangeId = testRangeId;
	}

	public Integer getTestRangeId() {
		return testRangeId;
	}

	public void setTestRangeId(Integer testRangeId) {
		this.testRangeId = testRangeId;
	}

	/**
	 * @return Returns the test.
	 */
	@Attribute(required = true)
	public LabTest getTest() {
		return test;
	}
	
	/**
	 * @param test.  The test value to set.
	 */
	@Attribute(required = true)
	public void setTest(LabTest test) {
		this.test = test;
	}
	
	/**
	 * @return Returns sortOrder.
	 */
	@Attribute(required = true)
	public Double getSortOrder() {
		return sortOrder;
	}
	
	/**
	 * @param sortOrder.  The sortOrder to set.
	 */
	@Attribute(required = true)
	public void setSortOrder(Double sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	/**
	 * @return Returns rangeSex.
	 */
	@Attribute(required = false)
	public String getRangeSex() {
		return rangeSex;
	}
	
	/**
	 * @param rangeSex.  The rangeSex to set.
	 */
	@Attribute(required = false)
	public void setRangeSex(String rangeSex) {
		this.rangeSex = rangeSex;
	}
	
	/**
	 * @return Returns rangeAgeMin.
	 */
	@Attribute(required = false)
	public Double getRangeAgeMin() {
		return rangeAgeMin;
	}
	
	/**
	 * @param rangeAgeMin.  The rangeAgeMin to set.
	 */
	@Attribute(required = false)
	public void setRangeAgeMin(Double rangeAgeMin) {
		this.rangeAgeMin = rangeAgeMin;
	}
	
	/**
	 * @return Returns rangeAgeMax.
	 */
	@Attribute(required = false)
	public Double getRangeAgeMax() {
		return rangeAgeMax;
	}
	
	/**
	 * @param rangeAgeMax.  The rangeAgeMax to set.
	 */
	@Attribute(required = false)
	public void setRangeAgeMax(Double rangeAgeMax) {
		this.rangeAgeMax = rangeAgeMax;
	}
	
	/**
	 * @return Returns logicRule.
	 */
	@Attribute(required = false)
	public RuleDefinition getLogicRule() {
		return logicRule;
	}
	
	/**
	 * @param logicRule.  The logicRule to set.
	 */
	@Attribute(required = false)
	public void setLogicRule(RuleDefinition logicRule) {
		this.logicRule = logicRule;
	}
	/**
	 * @return Returns rangeNormalLow.
	 */
	@Attribute(required = false)
	public Double getRangeNormalLow() {
		return rangeNormalLow;
	}
	
	/**
	 * @param rangeNormalLow.  The rangeNormalLow to set.
	 */
	@Attribute(required = false)
	public void setRangeNormalLow(Double rangeNormalLow) {
		this.rangeNormalLow = rangeNormalLow;
	}
	
	/**
	 * @return Returns rangeNormalHigh.
	 */
	@Attribute(required = false)
	public Double getRangeNormalHigh() {
		return rangeNormalHigh;
	}
	
	/**
	 * @param rangeNormalHigh.  The rangeNormalHigh to set.
	 */
	@Attribute(required = false)
	public void setRangeNormalHigh(Double rangeNormalHigh) {
		this.rangeNormalHigh = rangeNormalHigh;
	}
	
	/**
	 * @return Returns rangeCriticalLow.
	 */
	@Attribute(required = false)
	public Double getRangeCriticalLow() {
		return rangeCriticalLow;
	}
	
	/**
	 * @param rangeCriticalLow.  The rangeCriticalLow to set.
	 */
	@Attribute(required = false)
	public void setRangeCriticalLow(Double rangeCriticalLow) {
		this.rangeCriticalLow = rangeCriticalLow;
	}
	
	/**
	 * @return Returns rangeCriticalHigh.
	 */
	@Attribute(required = false)
	public Double getRangeCriticalHigh() {
		return rangeCriticalHigh;
	}
	
	/**
	 * @param rangeCriticalHigh.  The rangeCriticalHigh to set.
	 */
	@Attribute(required = false)
	public void setRangeCriticalHigh(Double rangeCriticalHigh) {
		this.rangeCriticalHigh = rangeCriticalHigh;
	}
	
	
}
