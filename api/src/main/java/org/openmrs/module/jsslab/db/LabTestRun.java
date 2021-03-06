package org.openmrs.module.jsslab.db;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Provider;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

/**
 * A lab test run. Includes all results subject to the same controls.
 * 
 */
@Root(strict = false)
public class LabTestRun extends BaseOpenmrsMetadata implements Serializable {
	
	public static final long serialVersionUID = 2L;
	
	private static final Log log = LogFactory.getLog(LabTestRun.class);
	
	private Integer testRunId;
	
	private LabInstrument instrument;
	
	private Provider labTech;
	
	private Date runStart;
	
	private Date runEnd;
	
	protected Set<LabTestSpecimen> testSpecimens = new HashSet<LabTestSpecimen>();
	
	public LabTestRun() {
		this.setUuid(UUID.randomUUID().toString());
	}
	
	@Override
	public int hashCode() {
		return this.getUuid().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		try {
			LabTestRun temp = (LabTestRun) other;
			return this.getUuid().equals(temp.getUuid());		
		} catch (Exception e) {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return testRunId;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer testRunId) {
		this.testRunId = testRunId;
	}

	public Integer getTestRunId() {
		return testRunId;
	}

	public void setTestRunId(Integer testRunId) {
		this.testRunId = testRunId;
	}

	/**
	 * @return Returns the instrument.
	 */
	@Attribute(required = false)
	public LabInstrument getInstrument() {
		return instrument;
	}
	
	/**
	 * @param instrument The instrument to set.
	 */
	@Attribute(required = false)
	public void setInstrument(LabInstrument instrument) {
		this.instrument = instrument;
	}
	
	/**
	 * @return Returns the lab tech user.
	 * TODO: change to provider
	 */
	@Attribute(required = false)
	public Provider getLabTech() {
		return labTech;
	}
	
	/**
	 * @param labTech The labTech user to set.
	 * TODO: change to provider
	 */
	@Attribute(required = false)
	public void setLabTech(Provider labTech) {
		this.labTech = labTech;
	}
	
	/**
	 * @return Returns the runStart date.
	 */
	@Attribute(required = false)
	public Date getRunStart() {
		return runStart;
	}
	
	/**
	 * @param runStart date.  The runStart date to set.
	 */
	@Attribute(required = false)
	public void setRunStart(Date runStart) {
		this.runStart = runStart;
	}
	
	/**
	 * @return Returns the runEnd date.
	 */
	@Attribute(required = false)
	public Date getRunEnd() {
		return runEnd;
	}
	
	/**
	 * @param runEnd date.  The runEnd date to set.
	 */
	@Attribute(required = false)
	public void setRunEnd(Date runEnd) {
		this.runEnd = runEnd;
	}
	
	/**
	 * @return Returns the testSpecimens set.
	 */
	@Attribute(required = false)
	public Set<LabTestSpecimen> getTestSpecimens(){
		return testSpecimens;
	}
	
	/**
	 * @param testSpecimens The set of testSpecimens to set.
	 */
	@Attribute(required = false)
	public void setTestSpecimens(Set<LabTestSpecimen> testSpecimens) {
		this.testSpecimens = testSpecimens;
	}
	
}
