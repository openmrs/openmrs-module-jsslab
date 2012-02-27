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

import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;

public interface LabOrderDAO {
	
	/**
	 * @see org.openmrs.api.LabOrderService#saveLabOrder(LabOrder)
	 */
	public LabOrder saveLabOrder(LabOrder labLabOrder) throws DAOException;
	
	/**
	 * @see org.openmrs.api.LabOrderService#purgeLabOrder(LabOrder)
	 */
	public void deleteLabOrder(LabOrder labLabOrder) throws DAOException;
	
	/**
	 * @see org.openmrs.api.LabOrderService#getLabOrder(Integer)
	 */
	public LabOrder getLabOrder(Integer labLabOrderId) throws DAOException;
	
	/**
	 * Auto generated method comment
	 * 
	 * @param uuid
	 * @return
	 */
	public LabOrder getLabOrderByUuid(String uuid);
	
	/**
	 * Returns a specified number of labOrders starting with a given string from the specified index
	 */
	public List<LabOrder> getLabOrders(String nameFragment, Boolean includeRetired, Integer start, Integer length);
	
	/**
	 * Get count of LabOrder
	 */
	public Integer getCountOfLabOrders(String nameFragment, Boolean includeRetired) throws APIException;
	
}