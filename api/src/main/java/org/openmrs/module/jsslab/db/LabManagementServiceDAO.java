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
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.module.jsslab.LabManagementService;

/**
 * Lab management related database functions
 */

public interface LabManagementServiceDAO {

	
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
	 * @param name String name of the <code>LabInstrument</code> to get
	 * @return the requested <code>LabInstrument</code>
	 */
	public LabInstrument getLabInstrument(String name);
	
	/**
	 * Get all labInstruments
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

	/**
	 * Create or update a labInstrument supplyItem.
	 * 
	 * @param supplyItem
	 * @return the saved <code>LabSupplyItem</code>
	 */
	public LabSupplyItem saveLabSupplyItem(LabSupplyItem supplyItem);
	
	/**
	 * Get a labInstrument supplyItem by <code>labInstrumentTagId</code>
	 * 
	 * @param labInstrumentTagId Internal <code>Integer</code> identifier of the supplyItem to get
	 * @return the requested <code>LabSupplyItem</code>
	 */
	public LabSupplyItem getLabSupplyItem(Integer labInstrumentTagId);
	
	/**
	 * @param uuid
	 * @return
	 */
	public LabSupplyItem getLabSupplyItemByUuid(String uuid);

	/**
	 * Get a labInstrument supplyItem by name
	 * 
	 * @param supplyItem String representation of the <code>LabSupplyItem</code> to get
	 * @return the requested <code>LabSupplyItem</code>
	 */
	public LabSupplyItem getLabSupplyItemByName(String supplyItem);
	
	/**
	 * Get all labInstrument supplyItems
	 * 
	 * @param includeRetired Boolean - include retired supplyItems as well?
	 * @return List<LabSupplyItem> object with all <code>LabSupplyItem</code>s, possibly included
	 *         retired ones
	 */
	public List<LabSupplyItem> getAllLabSupplyItems(Boolean includeRetired);
	
	/**
	 * Find all labInstrument supplyItems with matching names.
	 * 
	 * @param search name to search
	 * @return List<LabSupplyItem> with all matching <code>LabSupplyItems</code>
	 */
	public List<LabSupplyItem> getLabSupplyItems(String search);
	
	/**
	 * Completely remove the supplyItem from the database.
	 * 
	 * @param supplyItem The <code>LabSupplyItem</code> to delete
	 */
	public void deleteLabSupplyItem(LabSupplyItem supplyItem);
	
	/**
	 * @see org.openmrs.api.LabManagementService#getCountOfLabSupplyItems(String, Boolean)
	 */
	public Integer getCountOfLabSupplyItems(String nameFragment, Boolean includeRetired);
	

}
