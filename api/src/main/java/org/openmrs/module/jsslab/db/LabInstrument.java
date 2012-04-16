package org.openmrs.module.jsslab.db;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.math.BigDecimal;

import org.openmrs.BaseOpenmrsMetadata;
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
 * A lab instrument. May be used for lab asset control of other items such as freezers.
 * 
 */
@Root(strict = false)
public class LabInstrument extends BaseOpenmrsMetadata {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabInstrument.class);
	
	private Integer instrumentId;
	
	private String propertyTag;
	
	private String manufacturer;
	
	private String model;
	
	private String serialNumber;
	
	private Location location;
	
	private Date receivedDate;
	
	private String receivedFrom;
	
	private BigDecimal receivedCost;
	
	private BigDecimal receivedValue;
	
	private Date conditionDate;
	
	private Concept conditionConcept;
	
	private String maintenanceVendor;
	
	private String maintenancePhone;
	
	private String maintenanceDescription;
	
	protected Set<LabTestRun> testRuns = new HashSet<LabTestRun>();
	
	private String name;

	public void LabInstrument() {
		
		this.setUuid(UUID.randomUUID().toString());
	}
	
	@Override
	public int hashCode() {
		
		return this.getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			LabInstrument temp = (LabInstrument) other;
			return this.getUuid().equals(temp.getUuid());		
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return instrumentId;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer instrumentId) {
		this.instrumentId = instrumentId;
	}
	
	/**
	 * @return Returns the InstrumentId.
	 */
	@Attribute(required = true)
	public Integer getInstrumentId() {
		return instrumentId;
	}
	
	/**
	 * @param InstrumentId The InstrumentId to set.
	 */
	@Attribute(required = true)
	public void setInstrumentId(Integer instrumentId) {
		this.instrumentId = instrumentId;
	}
	
	/**
	 * @return Returns the propertyTag.
	 */
	@Attribute(required = false)
	public String getPropertyTag() {
		return propertyTag;
	}
	
	/**
	 * @param propertyTag The propertyTag to set.
	 */
	@Attribute(required = false)
	public void setPropertyTag(String propertyTag) {
		this.propertyTag = propertyTag;
	}
	
	/**
	 * @return Returns the manufacturer.
	 */
	@Attribute(required = false)
	public String getManufacturer() {
		return manufacturer;
	}
	
	/**
	 * @param manufacturer The manufacturer to set.
	 */
	@Attribute(required = false)
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	
	/**
	 * @return Returns the model.
	 */
	@Element(required = false)
	public String getModel() {
		return model;
	}
	
	/**
	 * @param model The model to set.
	 */
	@Element(required = false)
	public void setModel(String model) {
		this.model = model;
	}
	
	/**
	 * @return Returns the serialNumber.
	 */
	@Attribute(required = false)
	public String getSerialNumber() {
		return serialNumber;
	}
	
	/**
	 * @param serialNumber The serialNumber to set.
	 */
	@Attribute(required = false)
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	/**
	 * @return Returns the location.
	 */
	@Attribute(required = false)
	public Location getLocation() {
		return location;
	}
	
	/**
	 * @param location The location to set.
	 */
	@Attribute(required = false)
	public void setLocation(Location location) {
		this.location = location;
	}
	
	/**
	 * @return Returns the receivedDate date.
	 */
	@Attribute(required = false)
	public Date getReceivedDate() {
		return receivedDate;
	}
	
	/**
	 * @param receivedDate date.  The receivedDate date to set.
	 */
	@Attribute(required = false)
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	/**
	 * @return Returns the receivedFrom.
	 */
	@Attribute(required = false)
	public String getReceivedFrom() {
		return receivedFrom;
	}
	
	/**
	 * @param receivedFrom The receivedFrom to set.
	 */
	@Attribute(required = false)
	public void setreceivedFrom(String ReceivedFrom) {
		this.receivedFrom = receivedFrom;
	}
	
	/**
	 * @return Returns the receivedCost.
	 */
	@Attribute(required = false)
	public BigDecimal getReceivedCost() {
		return receivedCost;
	}
	
	/**
	 * @param receivedCost The receivedCost BigDecimal to set.
	 */
	@Attribute(required = false)
	public void setReceivedCost(BigDecimal receivedCost) {
		this.receivedCost = receivedCost;
	}
	
	/**
	 * @return Returns the receivedValue.
	 */
	@Attribute(required = false)
	public BigDecimal getReceivedValue() {
		return receivedValue;
	}
	
	/**
	 * @param receivedValue The receivedValue BigDecimal to set.
	 */
	@Attribute(required = false)
	public void setReceivedValue(BigDecimal receivedValue) {
		this.receivedValue = receivedValue;
	}
	
	/**
	 * @return Returns the conditionDate.
	 */
	@Attribute(required = false)
	public Date getConditionDate() {
		return conditionDate;
	}
	
	/**
	 * @param conditionDate.  The conditionDate to set.
	 */
	@Attribute(required = false)
	public void setConditionDate(Date conditionDate) {
		this.conditionDate = conditionDate;
	}

	/**
	 * @return Returns the conditionConcept.
	 */
	@Attribute(required = false)
	public Concept getConditionConcept() {
		return conditionConcept;
	}
	
	/**
	 * @param conditionConcept The conditionConcept to set.
	 */
	@Attribute(required = false)
	public void setConditionConcept(Concept conditionConcept) {
		this.conditionConcept = conditionConcept;
	}
	
	/**
	 * @return Returns the maintenanceVendor.
	 */
	@Attribute(required = false)
	public String getMaintenanceVendor() {
		return maintenanceVendor;
	}
	
	/**
	 * @param maintenanceVendor The maintenanceVendor to set.
	 */
	@Attribute(required = false)
	public void setMaintenanceVendor(String maintenanceVendor) {
		this.maintenanceVendor = maintenanceVendor;
	}
	
	/**
	 * @return Returns the maintenancePhone.
	 */
	@Attribute(required = false)
	public String getMaintenancePhone() {
		return maintenancePhone;
	}
	
	/**
	 * @param maintenancePhone The maintenancePhone to set.
	 */
	@Attribute(required = false)
	public void setMaintenancePhone(String maintenancePhone) {
		this.maintenancePhone = maintenancePhone;
	}
	
	/**
	 * @return Returns the maintenanceDescription.
	 */
	@Attribute(required = false)
	public String getMaintenanceDescription() {
		return maintenanceDescription;
	}
	
	/**
	 * @param maintenanceDescription The maintenanceDescription to set.
	 */
	@Attribute(required = false)
	public void setMaintenanceDescription(String maintenanceDescription) {
		this.maintenanceDescription = maintenanceDescription;
	}
	
	/**
	 * @return Returns the testRuns set.
	 */
	@Attribute(required = false)
	public Set<LabTestRun> getTestRuns(){
		return testRuns;
	}
	
	/**
	 * @param orders The set of testRuns to set.
	 */
	@Attribute(required = false)
	public void setTestRuns(Set<LabTestRun> testRuns) {
		this.testRuns = testRuns;
	}
	
	/**
	 * @return Returns the description (manufacturer, model, serial).
	 */
	@Attribute(required = false)
	public String getName() {
		return this.getManufacturer()+" "+this.getModel()+" "+this.getSerialNumber();
	}
	
	public String getDisplayString() {
		return this.getName();
	}
}
