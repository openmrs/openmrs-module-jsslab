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

import java.util.Comparator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.openmrs.api.APIException;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.jsslab.LabCatalogService;

/**
 * Lab test panel related database functions
 */

public interface LabTestPanelDAO {

	
	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);

	/**
	 * Create or update a labTestPanel.
	 * 
	 * @param labTestPanel <code>LabTestPanel</code> to save
	 * @return the saved <code>LabTestPanel</code>
	 */
	public LabTestPanel saveLabTestPanel(LabTestPanel labTestPanel);
	
	/**
	 * Get a labTestPanel by labTestPanelId
	 * 
	 * @param labTestPanelId Internal <code>Integer</code> identifier of the <code>LabTestPanel<code> to get
	 * @return the requested <code>LabTestPanel</code>
	 */
	public LabTestPanel getLabTestPanel(Integer labTestPanelId);
	
	/**
	 * @param uuid the uuid to look for
	 * @return labTestPanel matching uuid
	 */
	public LabTestPanel getLabTestPanelByUuid(String uuid);

	/**
	 * Get a labTestPanel by name
	 * 
	 * @param name String name of the <code>LabTestPanel</code> to get
	 * @return the requested <code>LabTestPanel</code>
	 */
	public LabTestPanel getLabTestPanel(String name);
	
	/**
	 * Get all labTestPanels
	 * 
	 * @param includeRetired Boolean - include retired labTestPanels as well?
	 * @return <code>List<LabTestPanel></code> object of all <code>LabTestPanel</code>s, possibly including
	 *         retired labTestPanels
	 */
	public List<LabTestPanel> getAllLabTestPanels(Boolean includeRetired);
	
	/**
	 * Returns a specified number of labTestPanels starting with a given string from the specified index
	 * 
	 * @see LabManagementService#getLabTestPanels(String, Boolean, Integer, Integer)
	 */
	public List<LabTestPanel> getLabTestPanels(String nameFragment, Boolean includeRetired, Integer start, Integer length);
	
	/**
	 * Completely remove the labTestPanel from the database.
	 * 
	 * @param labTestPanel <code>LabTestPanel</code> object to delete
	 */
	public void deleteLabTestPanel(LabTestPanel labTestPanel) throws APIException;
	
	/**
	 * @see org.openmrs.api.LabManagementService#getCountOfLabTestPanels(String, Boolean)
	 */
	public Integer getCountOfLabTestPanels(String nameFragment, Boolean includeRetired);


}
