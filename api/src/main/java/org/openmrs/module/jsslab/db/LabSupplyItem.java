package org.openmrs.module.jsslab.db;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Concept;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab supply item. Includes reagents and controls.
 * 
 */
@Root(strict = false)
public class LabSupplyItem extends BaseOpenmrsMetadata {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabSupplyItem.class);
	
	private Integer supplyItemId;
	
	private String labStockNumber;
	
	private String manufacturerStockNumber;
	
	private String manufacturer;
	
	private String itemName;
	
	private Concept itemClassConcept;
	
	private String lotNumber;
	
	private Date expirationDate;
	
	protected Set<LabTestSpecimen> testSpecimens = new HashSet<LabTestSpecimen>();
	
//	private String name;
	
	public LabSupplyItem() {
		this.setUuid(UUID.randomUUID().toString());
	}
	
	@Override
	public int hashCode() {
		return this.getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			LabSupplyItem temp = (LabSupplyItem) other;
			return this.getUuid().equals(temp.getUuid());		
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return supplyItemId;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer supplyItemId) {
		this.supplyItemId = supplyItemId;
	}
	
	public Integer getSupplyItemId() {
		return supplyItemId;
	}

	public void setSupplyItemId(Integer supplyItemId) {
		this.supplyItemId = supplyItemId;
	}
	
	/**
	 * @return Returns the labStockNumber.
	 */
	@Attribute(required = false)
	public String getLabStockNumber() {
		return labStockNumber;
	}
	
	/**
	 * @param labStockNumber The labStockNumber to set.
	 */
	@Attribute(required = false)
	public void setLabStockNumber(String labStockNumber) {
		this.labStockNumber = labStockNumber;
	}
	
	/**
	 * @return Returns the manufacturerStockNumber.
	 */
	@Attribute(required = false)
	public String getManufacturerStockNumber() {
		return manufacturerStockNumber;
	}
	
	/**
	 * @param manufacturerStockNumber The manufacturerStockNumber to set.
	 */
	@Attribute(required = false)
	public void setManufacturerStockNumber(String manufacturerStockNumber) {
		this.manufacturerStockNumber = manufacturerStockNumber;
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
	 * @return Returns the itemName.
	 */
	@Attribute(required = true)
	public String getItemName() {
		return itemName;
	}
	
	/**
	 * @param itemName The itemName to set.
	 */
	@Attribute(required = true)
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	/**
	 * @return Returns the item class concept.
	 */
	@Attribute(required = false)
	public Concept getItemClassConcept() {
		return itemClassConcept;
	}
	
	/**
	 * @param itemClassConcept The itemClassConcept to set.
	 */
	@Attribute(required = false)
	public void setItemClassConcept(Concept itemClassConcept) {
		this.itemClassConcept = itemClassConcept;
	}
	
	/**
	 * @return Returns the lotNumber.
	 */
	@Attribute(required = false)
	public String getLotNumber() {
		return lotNumber;
	}
	
	/**
	 * @param lotNumber The lotNumber to set.
	 */
	@Attribute(required = false)
	public void setLotNumber(String lotNumber) {
		this.lotNumber = lotNumber;
	}
	
	/**
	 * @return Returns the expirationDate date.
	 */
	@Attribute(required = false)
	public Date getExpirationDate() {
		return expirationDate;
	}
	
	/**
	 * @param expirationDate date.  The expirationDate date to set.
	 */
	@Attribute(required = false)
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return Returns the testSpecimens set.
	 */
	@Attribute(required = false)
	public Set<LabTestSpecimen> getTestSpecimens(){
		return testSpecimens;
	}
	
	/**
	 * @param orders The set of testSpecimens to set.
	 */
	@Attribute(required = false)
	public void setTestSpecimens(Set<LabTestSpecimen> testSpecimens) {
		this.testSpecimens = testSpecimens;
	}
	
	/**
	 * @return Returns the description (manufacturer, itemName).
	 */
	@Attribute(required = false)
	public String getName() {
		StringBuilder name = new StringBuilder();
		if (this.getManufacturer() != null) name.append(this.getManufacturer()).append(" ");
		if (this.getItemName() != null) name.append(this.getItemName()).append(" ");
		if (this.getLotNumber() != null) name.append(this.getLotNumber().trim()).append(" ");
		
		return name.toString();
	}
	
	public String getDisplayString() {
		return this.getName();
	}
	
}
