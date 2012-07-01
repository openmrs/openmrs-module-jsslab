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
 * LabTestSpecimen related database functions
 */

public interface LabTestSpecimenDAO {

	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * Create or update a labTestSpecimen.
	 * 
	 * @param labTestSpecimen
	 * @return the saved <code>LabTestSpecimen</code>
	 */
	public LabTestSpecimen saveLabTestSpecimen(LabTestSpecimen labTestSpecimen);

	/**
	 * Get a labTestSpecimen by <code>testId</code>
	 * 
	 * @param testSpecimenId
	 *            <code>Integer</code> identifier of the labTestSpecimen to get
	 * @return the requested <code>LabTestSpecimen</code>
	 */
	public LabTestSpecimen getLabTestSpecimen(Integer testSpecimenId);

	/**
	 * @param uuid
	 * @return
	 */
	public LabTestSpecimen getLabTestSpecimenByUuid(String uuid);

	/**
	 * Get a labInstrument labTestSpecimen by name
	 * 
	 * @param labTestSpecimen
	 *            String representation of the <code>LabTestSpecimen</code> to get
	 * @return the requested <code>LabTestSpecimen</code>
	 */
	public LabTestSpecimen getLabTestSpecimenByName(String labTestSpecimen);

	/**
	 * Get all labTestSpecimens
	 * 
	 * @param includeVoided
	 *            Boolean - include voided labTestSpecimens as well?
	 * @return List<LabTestSpecimen> object with all <code>LabTestSpecimen</code>s, possibly
	 *         included voided ones
	 */
	public List<LabTestSpecimen> getLabTestSpecimens(String search, Boolean includeVoided, Integer start, Integer length);

	/**
	 * Completely remove the labTestSpecimen from the database.
	 * 
	 * @param labTestSpecimen
	 *            The <code>LabTestSpecimen</code> to delete
	 */
	public void deleteLabTestSpecimen(LabTestSpecimen labTestSpecimen);

	/**
	 * @see org.openmrs.api.LabCatalogService#getCountOfLabTestSpecimens(String,
	 *      Boolean)
	 */
	public Integer getCountOfLabTestSpecimens(String search,
			Boolean includeVoided);

}
