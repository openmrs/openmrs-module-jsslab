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
import org.openmrs.module.jsslab.LabManagementService;

/**
 * Lab instrument related database functions
 */

public interface LabInstrumentDAO {

	
	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);
	
	/**
	 * Create or update a labInstrument.
	 * 
	 * @param labInstrument <code>LabInstrument</code> to save
	 * @return the saved <code>LabInstrument</code>
	 */
	public LabInstrument saveLabInstrument(LabInstrument labInstrument);
	
	/**
	 * Get a labInstrument by labInstrumentId
	 * 
	 * @param labInstrumentId Internal <code>Integer</code> identifier of the <code>LabInstrument<code> to get
	 * @return the requested <code>LabInstrument</code>
	 */
	public LabInstrument getLabInstrument(Integer labInstrumentId);
	
	/**
	 * @param uuid the uuid to look for
	 * @return labInstrument matching uuid
	 */
	public LabInstrument getLabInstrumentByUuid(String uuid);

	/**
	 * Get a labInstrument by name
	 * 
	 * @param name String value of the propertyTag, serialNumber or model properties of <code>LabInstrument</code> to match
	 * @return the requested <code>LabInstrument</code>
	 */
	public LabInstrument getLabInstrument(String name);
	
	
	/**
	 * Get count of labInstruments
	 * 
	 * @param includeRetired Boolean - include retired labInstruments as well?
	 * @return <code>List<LabInstrument></code> object of all <code>LabInstrument</code>s, possibly including
	 *         retired labInstruments
	 */
	public List<LabInstrument> getAllLabInstruments(Boolean includeRetired);
	
	/**
	 * Returns a specified number of labInstruments starting with a given string from the specified index
	 * 
	 * @see LabManagementService#getLabInstruments(String, Boolean, Integer, Integer)
	 */
	public List<LabInstrument> getLabInstruments(String nameFragment, Boolean includeRetired, Integer start, Integer length);
	
	/**
	 * Completely remove the labInstrument from the database.
	 * 
	 * @param labInstrument <code>LabInstrument</code> object to delete
	 */
	public void deleteLabInstrument(LabInstrument labInstrument) throws APIException;
	
	/**
	 * @see org.openmrs.api.LabManagementService#getCountOfLabInstruments(String, Boolean)
	 */
	public Integer getCountOfLabInstruments(String nameFragment, Boolean includeRetired);


}
