package org.openmrs.module.jsslab.db;

import java.util.List;

import org.hibernate.SessionFactory;

public interface LabTestRangeDAO {

	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);
	
	/**
	 * Create or update a labTestRange.
	 * 
	 * @param labTestRange <code>LabTestRange</code> to save
	 * @return the saved <code>LabTestRange</code>
	 */
	public LabTestRange saveLabTestRange(LabTestRange labTestRange);
	
	/**
	 * Get a labTestRange by labTestRangeId
	 * 
	 * @param labTestRangeId Internal <code>Integer</code> identifier of the <code>LabTestRange<code> to get
	 * @return the requested <code>LabTestRange</code>
	 */
	public LabTestRange getLabTestRange(Integer labTestRangeId);
	
	/**
	 * @param uuid the uuid to look for
	 * @return labTestRange matching uuid
	 */
	public LabTestRange getLabTestRangeByUuid(String uuid);
	
	/**
	 * Completely remove the labTestRange from the database.
	 * 
	 * @param labTestRange <code>LabTestRange</code> object to delete
	 */
	public void deleteLabTestRange(LabTestRange labTestRange);
	
	/**
	 * Get all labTestRanges
	 * 
	 * @param includeVoided Whether or not to include <code>LabTestRange</code>s that have been voided
	 * @return <code>List<LabTestRange></code> object of all <code>LabTestRange</code>s, possibly including voided ones
	 */
    public List<LabTestRange> getAllLabTestRanges(Boolean includeVoided);

	/**
	 * Get all labTestRanges
	 * 
	 * @param search 
	 * @param includeVoided Whether or not to include <code>LabTestRange</code>s that have been voided
	 * @param start
	 * @param length
	 * @return List<LabTestRange> object with all <code>LabTestRange</code>s, possibly included voided ones
	 */
	public List<LabTestRange> getLabTestRanges(String search, Boolean includeVoided, Integer start, Integer length);

	/**
	 * @see org.openmrs.api.LabCatalogService#getCountOfLabTestRanges(String,
	 *      Boolean)
	 */
	public Integer getCountOfLabTestRanges(String search,
			Boolean includeVoided);

}
