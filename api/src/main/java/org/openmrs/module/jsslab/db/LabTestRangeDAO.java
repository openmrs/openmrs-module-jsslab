package org.openmrs.module.jsslab.db;

import java.util.List;

import org.hibernate.SessionFactory;
import org.openmrs.api.APIException;

public interface LabTestRangeDAO {
	
	public void setSessionFactory(SessionFactory sessionFactory);
	
	public LabTestRange getLabTestRange(Integer labTestRangeId);
	
	public LabTestRange getLabTestRangeByUuid(String uuid);
	
	public LabTestRange saveLabTestRange(LabTestRange labTestRange);
	
	public void deleteLabTestRange(LabTestRange labTestRange);
	
    public List<LabTestRange> getAllLabTestRanges(Boolean includeVoided);

	/**
	 * Get all labTestRanges
	 * 
	 * @param includeVoided
	 *            Boolean - include voided labTestRanges as well?
	 * @return List<LabTestRange> object with all <code>LabTestRange</code>s, possibly
	 *         included voided ones
	 */
	public List<LabTestRange> getLabTestRanges(String search, Boolean includeVoided, Integer start, Integer length);

	/**
	 * @see org.openmrs.api.LabCatalogService#getCountOfLabTestRanges(String,
	 *      Boolean)
	 */
	public Integer getCountOfLabTestRanges(String search,
			Boolean includeVoided);

}
