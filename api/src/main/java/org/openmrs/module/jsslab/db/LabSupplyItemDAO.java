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
 * Lab supply item related database functions
 */

public interface LabSupplyItemDAO {

	
	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);
	
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
	 * @param includeRetired true to include retired
	 * @param start record number at which to start
	 * @param length number of records to return
	 * @return List<LabSupplyItem> with all matching <code>LabSupplyItems</code>
	 */
	public List<LabSupplyItem> getLabSupplyItems(String search, Boolean includeRetired, Integer start, Integer length);
	
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
