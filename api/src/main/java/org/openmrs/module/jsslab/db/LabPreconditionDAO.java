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
 * LabPrecondition related database functions
 */

public interface LabPreconditionDAO {

	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * Create or update a labPrecondition.
	 * 
	 * @param labPrecondition
	 * @return the saved <code>LabPrecondition</code>
	 */
	public LabPrecondition saveLabPrecondition(LabPrecondition labPrecondition);

	/**
	 * Get a labPrecondition by <code>testId</code>
	 * 
	 * @param preconditionId
	 *            <code>Integer</code> identifier of the labPrecondition to get
	 * @return the requested <code>LabPrecondition</code>
	 */
	public LabPrecondition getLabPrecondition(Integer preconditionId);

	/**
	 * @param uuid
	 * @return
	 */
	public LabPrecondition getLabPreconditionByUuid(String uuid);

	/**
	 * Get a labInstrument labPrecondition by name
	 * 
	 * @param labPrecondition
	 *            String representation of the <code>LabPrecondition</code> to get
	 * @return the requested <code>LabPrecondition</code>
	 */
	public LabPrecondition getLabPreconditionByName(String labPrecondition);

	/**
	 * Get all labPreconditions
	 * 
	 * @param includeVoided
	 *            Boolean - include voided labPreconditions as well?
	 * @return List<LabPrecondition> object with all <code>LabPrecondition</code>s, possibly
	 *         included voided ones
	 */
	public List<LabPrecondition> getLabPreconditions(String search, Boolean includeVoided, Integer start, Integer length);

	/**
	 * Completely remove the labPrecondition from the database.
	 * 
	 * @param labPrecondition
	 *            The <code>LabPrecondition</code> to delete
	 */
	public void deleteLabPrecondition(LabPrecondition labPrecondition);

	/**
	 * @see org.openmrs.api.LabCatalogService#getCountOfLabPreconditions(String,
	 *      Boolean)
	 */
	public Integer getCountOfLabPreconditions(String search,
			Boolean includeVoided);

}
