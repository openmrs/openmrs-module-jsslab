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
 * LabSpecimen related database functions
 */

public interface LabSpecimenDAO {

	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * Create or update a labSpecimen.
	 * 
	 * @param labSpecimen
	 * @return the saved <code>LabSpecimen</code>
	 */
	public LabSpecimen saveLabSpecimen(LabSpecimen labSpecimen);

	/**
	 * Get a labSpecimen by <code>testId</code>
	 * 
	 * @param specimenId
	 *            <code>Integer</code> identifier of the labSpecimen to get
	 * @return the requested <code>LabSpecimen</code>
	 */
	public LabSpecimen getLabSpecimen(Integer specimenId);

	/**
	 * @param uuid
	 * @return
	 */
	public LabSpecimen getLabSpecimenByUuid(String uuid);

	/**
	 * Get a labInstrument labSpecimen by name
	 * 
	 * @param labSpecimen
	 *            String representation of the <code>LabSpecimen</code> to get
	 * @return the requested <code>LabSpecimen</code>
	 */
	public LabSpecimen getLabSpecimenByName(String labSpecimen);

	/**
	 * Get all labSpecimens
	 * 
	 * @param includeVoided
	 *            Boolean - include voided labSpecimens as well?
	 * @return List<LabSpecimen> object with all <code>LabSpecimen</code>s, possibly
	 *         included voided ones
	 */
	public List<LabSpecimen> getLabSpecimens(String search, Boolean includeVoided, Integer start, Integer length);

	/**
	 * Completely remove the labSpecimen from the database.
	 * 
	 * @param labSpecimen
	 *            The <code>LabSpecimen</code> to delete
	 */
	public void deleteLabSpecimen(LabSpecimen labSpecimen);

	/**
	 * @see org.openmrs.api.LabCatalogService#getCountOfLabSpecimens(String,
	 *      Boolean)
	 */
	public Integer getCountOfLabSpecimens(String search,
			Boolean includeVoided);

}
