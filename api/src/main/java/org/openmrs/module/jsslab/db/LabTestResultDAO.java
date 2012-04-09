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

import java.util.List;

import org.hibernate.SessionFactory;
import org.openmrs.api.APIException;

import org.openmrs.module.jsslab.db.LabTestPanel;

/**
 * LabTestResult related database functions
 */

public interface LabTestResultDAO {

	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * Create or update a labTestResult.
	 * 
	 * @param labTestResult
	 * @return the saved <code>LabTestResult</code>
	 */
	public LabTestResult saveLabTestResult(LabTestResult labTestResult);

	/**
	 * Get a labTestResult by <code>testId</code>
	 * 
	 * @param preconditionId
	 *            <code>Integer</code> identifier of the labTestResult to get
	 * @return the requested <code>LabTestResult</code>
	 */
	public LabTestResult getLabTestResult(Integer preconditionId);

	/**
	 * @param uuid
	 * @return
	 */
	public LabTestResult getLabTestResultByUuid(String uuid);

	/**
	 * Get a labInstrument labTestResult by name
	 * 
	 * @param labTestResult
	 *            String representation of the <code>LabTestResult</code> to get
	 * @return the requested <code>LabTestResult</code>
	 */
	public LabTestResult getLabTestResultByName(String labTestResult);

	/**
	 * Get all labTestResults
	 * 
	 * @param includeVoided
	 *            Boolean - include voided labTestResults as well?
	 * @return List<LabTestResult> object with all <code>LabTestResult</code>s, possibly
	 *         included voided ones
	 */
	public List<LabTestResult> getLabTestResults(String search, Boolean includeVoided, Integer start, Integer length);

	/**
	 * Completely remove the labTestResult from the database.
	 * 
	 * @param labTestResult
	 *            The <code>LabTestResult</code> to delete
	 */
	public void deleteLabTestResult(LabTestResult labTestResult);

	/**
	 * @see org.openmrs.api.LabCatalogService#getCountOfLabTestResults(String,
	 *      Boolean)
	 */
	public Integer getCountOfLabTestResults(String search,
			Boolean includeVoided);

}
