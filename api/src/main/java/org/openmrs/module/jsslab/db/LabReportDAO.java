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
 * LabReport related database functions
 */

public interface LabReportDAO {

	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * Create or update a labReport.
	 * 
	 * @param labReport
	 * @return the saved <code>LabReport</code>
	 */
	public LabReport saveLabReport(LabReport labReport);

	/**
	 * Get a labReport by <code>testId</code>
	 * 
	 * @param specimenId
	 *            <code>Integer</code> identifier of the labReport to get
	 * @return the requested <code>LabReport</code>
	 */
	public LabReport getLabReport(Integer specimenId);

	/**
	 * @param uuid
	 * @return
	 */
	public LabReport getLabReportByUuid(String uuid);

	/**
	 * Get a labInstrument labReport by name
	 * 
	 * @param labReport
	 *            String representation of the <code>LabReport</code> to get
	 * @return the requested <code>LabReport</code>
	 */
	public LabReport getLabReportByName(String labReport);

	/**
	 * Gets all <code>LabReport</code>s that match the given parameters
	 * 
	 * @param includeRetired Whether or not to include retired <code>LabReports</code>s
	 * @return List<LabReport> object with all <code>LabReport</code>s, possibly
	 *         included retired ones
	 */
	public List<LabReport> getLabReports(String search, Boolean includeRetired, Integer start, Integer length);

	/**
	 * Completely remove the labReport from the database.
	 * 
	 * @param labReport
	 *            The <code>LabReport</code> to delete
	 */
	public void deleteLabReport(LabReport labReport);

	/**
	 * @see org.openmrs.api.LabTestingService#getCountOfLabReports(String,
	 *      Boolean)
	 */
	public Integer getCountOfLabReports(String search,
			Boolean includeRetired);

}
