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
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.Location;
import org.openmrs.Concept;
import org.openmrs.Order;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 * A lab report. All orders, specimens and test results are contained in one
 * and only one report.
 * 
 */
@Root(strict = false)
public class LabReport extends BaseOpenmrsData implements Serializable {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabReport.class);
	
	private Integer reportId;
	
	private String labReportId;
	
	private Concept reportStatusConcept;
	
	private User approvedBy;
	
	private Date approvedDate;
	
	private String comments;
	
	private Date resultDeliveryDate;
	
	private Concept resultDeliveryConcept;
	
	protected Set<LabSpecimen> specimens = new HashSet<LabSpecimen>();
	
	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return reportId;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return Returns the labReportId.
	 */
	@Attribute(required = true)
	public String getLabReportId() {
		return labReportId;
	}
	
	/**
	 * @param labReportId The labReportId to set.
	 */
	@Attribute(required = true)
	public void setLabReportId(String labReportId) {
		this.labReportId = labReportId;
	}
	
	/**
	 * @return Returns the report status concept.
	 */
	@Attribute(required = false)
	public Concept getReportStatusConcept() {
		return reportStatusConcept;
	}
	
	/**
	 * @param reportStatusConcept The reportStatusConcept to set.
	 */
	@Attribute(required = false)
	public void setReportStatusConcept(Concept reportStatusConcept) {
		this.reportStatusConcept = reportStatusConcept;
	}
	
	/**
	 * @return Returns the approved by user.
	 * TODO: change to provider
	 */
	@Attribute(required = false)
	public User getApprovedBy() {
		return approvedBy;
	}
	
	/**
	 * @param approvedBy The approved by user to set.
	 * TODO: change to provider
	 */
	@Attribute(required = false)
	public void setApprovedBy(User approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	/**
	 * @return Returns the approved date.
	 */
	@Attribute(required = false)
	public Date getApprovedDate() {
		return approvedDate;
	}
	
	/**
	 * @param approved date.  The approved date to set.
	 */
	@Attribute(required = false)
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	/**
	 * @return Returns the report comments.
	 */
	@Element(required = false)
	public String getComments() {
		return comments;
	}
	
	/**
	 * @param report comments The report comments to set.
	 */
	@Element(required = false)
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	/**
	 * @return Returns the result delivery date.
	 */
	@Attribute(required = false)
	public Date getResultDeliveryDate() {
		return resultDeliveryDate;
	}
	
	/**
	 * @param result delivery date.  The result delivery date to set.
	 */
	@Attribute(required = false)
	public void setResultDeliveryDate(Date resultDeliveryDate) {
		this.resultDeliveryDate = resultDeliveryDate;
	}
	
	/**
	 * @return Returns the result delivery concept.
	 */
	@Attribute(required = false)
	public Concept getResultDeliveryConcept() {
		return resultDeliveryConcept;
	}
	
	/**
	 * @param resultDeliveryConcept The resultDeliveryConcept to set.
	 */
	@Attribute(required = false)
	public void setResultDeliveryConcept(Concept resultDeliveryConcept) {
		this.resultDeliveryConcept = resultDeliveryConcept;
	}
	
	/**
	 * @return Returns the specimens set.
	 */
	@Attribute(required = false)
	public Set<LabSpecimen> getSpecimens(){
		return specimens;
	}
	
	/**
	 * @param orders The set of specimens to set.
	 */
	@Attribute(required = false)
	public void setSpecimens(Set<LabSpecimen> specimens) {
		this.specimens = specimens;
	}
	

}
