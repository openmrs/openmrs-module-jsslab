package org.openmrs.module.jsslab.db;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Locale;
import org.apache.commons.lang.StringUtils;  

import org.openmrs.api.context.Context;
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
 * Join table for LabOrder and LabSpecimen
 * 
 */
@Root(strict = false)
public class LabOrderSpecimen extends BaseOpenmrsData {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabOrderSpecimen.class);
	
	private Integer orderSpecimenId;
	
	private LabOrder order;
	
	private LabSpecimen specimen;
	
	private Concept specimenRoleConcept;

	private Locale textLocale;
	
	private String specimenRoleText;
	
	private String specimenRoleCode;
	
	public void LabOrderSpecimen() {
		
		this.setUuid(UUID.randomUUID().toString());
	}
	
	@Override
	public int hashCode() {
		
		return this.getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			LabOrderSpecimen temp = (LabOrderSpecimen) other;
			return this.getUuid().equals(temp.getUuid());		
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return orderSpecimenId;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer orderSpecimenId) {
		this.orderSpecimenId = orderSpecimenId;
	}
	
	public Integer getOrderSpecimenId() {
		return orderSpecimenId;
	}

	public void setOrderSpecimenId(Integer orderSpecimenId) {
		this.orderSpecimenId = orderSpecimenId;
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
	 * @return Returns the order .
	 */
	@Attribute(required = true)
	public LabOrder getOrder() {
		return order;
	}
	
	/**
	 * @param order The order id to set.
	 */
	@Attribute(required = true)
	public void setOrder(LabOrder order) {
		this.order = order;
	}
	
	/**
	 * @return Returns the SpecimenRoleConcept concept.
	 */
	@Attribute(required = false)
	public Concept getSpecimenRoleConcept() {
		return specimenRoleConcept;
	}
	
	/**
	 * @param SpecimenRoleConcept The SpecimenRoleConcept to set.  
	 */
	@Attribute(required = false)
	public void setSpecimenRoleConcept(Concept specimenRoleConcept) {
		this.specimenRoleConcept = specimenRoleConcept;
		this.specimenRoleText = null;
		this.specimenRoleCode = null;
	}
	
	/**
	 * Check for a locale change
	 */
	private void checkLocale() {
		if (! textLocale.equals(Context.getLocale())) {
			specimenRoleText = "";
			specimenRoleCode = "";
			textLocale = Context.getLocale();
		}
		return;
	}
	
	/**
	 * Get text corresponding to specimenRoleConcept
	 */
	public String getSpecimenRoleText() {
		checkLocale();
		if (StringUtils.isEmpty(specimenRoleText)) {
			if (! (specimenRoleConcept == null)) {
				specimenRoleText = Context.getConceptService().getConceptName(this.getSpecimenRoleConcept().getId()).getName();
				specimenRoleCode = Context.getConceptService().getConceptName(this.getSpecimenRoleConcept().getId()).getName();
			}
		}
		return specimenRoleText;
	}

	/**
	 * Get code corresponding to specimenRoleConcept
	 * TODO: Put the code into the concept dictionary the right way.
	 */
	public String getSpecimenRoleCode() {
		checkLocale();
		if (StringUtils.isEmpty(specimenRoleCode)) {
			if (! (specimenRoleConcept == null)) {
				specimenRoleText = Context.getConceptService().getConceptName(this.getSpecimenRoleConcept().getId()).getName();
				specimenRoleCode = Context.getConceptService().getConceptName(this.getSpecimenRoleConcept().getId()).getName();

				
				}
			}
		return specimenRoleCode;
		}

	public String toString() {
		return "LabOrderSpecimen " + this.getId();
	}
	
	public String getName() {
		return this.getOrder().getId() + " " + this.getSpecimenRoleCode() + " " + this.getSpecimen().getId();
	}
	
	public String getDisplayString() {
		return this.getOrder().getUuid().toString() + " " + this.getSpecimenRoleCode() + " " + this.getSpecimen().getUuid().toString();
	}

}
