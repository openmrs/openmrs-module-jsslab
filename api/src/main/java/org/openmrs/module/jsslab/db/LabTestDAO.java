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

/**
 * Lab test related database functions
 */

public interface LabTestDAO {

	
	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);
	
	/**
	 * Create or update a labTest.
	 * 
	 * @param labTest
	 * @return the saved <code>LabTest</code>
	 */
	public LabTest saveLabTest(LabTest labTest);
	
	/**
	 * Get a labTest by <code>testId</code>
	 * 
	 * @param testId  <code>Integer</code> identifier of the labTest to get
	 * @return the requested <code>LabTest</code>
	 */
	public LabTest getLabTest(Integer testId);
	
	/**
	 * @param uuid
	 * @return
	 */
	public LabTest getLabTestByUuid(String uuid);

	/**
	 * Get a labInstrument labTest by name
	 * 
	 * @param labTest String representation of the <code>LabTest</code> to get
	 * @return the requested <code>LabTest</code>
	 */
	public LabTest getLabTestByName(String labTest);
	
	/**
	 * Get all labTests
	 * 
	 * @param includeRetired Boolean - include retired labTests as well?
	 * @return List<LabTest> object with all <code>LabTest</code>s, possibly included
	 *         retired ones
	 */
	public List<LabTest> getAllLabTests(Boolean includeRetired);
	
	/**
	 * Find all labTests with matching names.
	 * 
	 * @param search name to search
	 * @return List<LabTest> with all matching <code>LabTests</code>
	 */
	public List<LabTest> getLabTests(String search);
	
	/**
	 * Completely remove the labTest from the database.
	 * 
	 * @param labTest The <code>LabTest</code> to delete
	 */
	public void deleteLabTest(LabTest labTest);
	
	/**
	 * @see org.openmrs.api.LabCatalogService#getLabTests(String, Boolean, Integer, Integer)
	 */
	public List<LabTest> getLabTests(String nameFragment, Boolean includeRetired, Integer start, Integer length);
	
	/**
	 * @see org.openmrs.api.LabCatalogService#getCountOfLabTests(String, Boolean)
	 */
	public Integer getCountOfLabTests(String nameFragment, Boolean includeRetired);
	

}
