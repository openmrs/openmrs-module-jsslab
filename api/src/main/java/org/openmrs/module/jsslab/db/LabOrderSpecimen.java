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
	

}
