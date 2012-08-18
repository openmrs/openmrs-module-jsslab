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
 * LabSpecimenTemplate related database functions
 */

public interface LabSpecimenTemplateDAO {

	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * Create or update a LabSpecimenTemplate.
	 * 
	 * @param LabSpecimenTemplate
	 * @return the saved <code>LabSpecimenTemplate</code>
	 */
	public LabSpecimenTemplate saveLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate);

	/**
	 * Get a LabSpecimenTemplate by <code>testId</code>
	 * 
	 * @param preconditionId
	 *            <code>Integer</code> identifier of the LabSpecimenTemplate to get
	 * @return the requested <code>LabSpecimenTemplate</code>
	 */
	public LabSpecimenTemplate getLabSpecimenTemplate(Integer labSpecimenTemplateId);

	/**
	 * @param uuid
	 * @return
	 */
	public LabSpecimenTemplate getLabSpecimenTemplateByUuid(String uuid);

	/**
	 * Get a labInstrument LabSpecimenTemplate by name
	 * 
	 * @param LabSpecimenTemplate
	 *            String representation of the <code>LabSpecimenTemplate</code> to get
	 * @return the requested <code>LabSpecimenTemplate</code>
	 */
	public LabSpecimenTemplate getLabSpecimenTemplateByName(String LabSpecimenTemplate);

	/**
	 * Get all LabSpecimenTemplates
	 * 
	 * @param includeRetired
	 *            Boolean - include retired LabSpecimenTemplates as well?
	 * @return List<LabSpecimenTemplate> object with all <code>LabSpecimenTemplate</code>s, possibly
	 *         included retired ones
	 */
	public List<LabSpecimenTemplate> getLabSpecimenTemplates(String search, Boolean includeRetired, Integer start, Integer length);

	/**
	 * Completely remove the LabSpecimenTemplate from the database.
	 * 
	 * @param LabSpecimenTemplate
	 *            The <code>LabSpecimenTemplate</code> to delete
	 */
	public void deleteLabSpecimenTemplate(LabSpecimenTemplate LabSpecimenTemplate);

	/**
	 * @see org.openmrs.api.LabCatalogService#getCountOfLabSpecimenTemplates(String,
	 *      Boolean)
	 */
	public Long getCountOfLabSpecimenTemplates(String search,
			Boolean includeRetired);

}
