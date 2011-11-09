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
package org.openmrs.module.JSSLab.impl;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.JSSLab.LabService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.JSSLab.db.LabDAO;

public class LabServiceImpl extends BaseOpenmrsService implements LabService{
	
	/**
	 * default constructor
	 */
	private final Log log = LogFactory.getLog(this.getClass());
	public LabServiceImpl()
	{
		log.info("LabService");
	}
	
	
	private LabDAO dao;
	
	/**
	 * setter for LabDAO
	 */
	public void setLabDAO(LabDAO dao)
	{
		this.dao = dao;
	}
	
	/**
	 * getter for LabDAO
	 */
	public LabDAO getLabDAO()
	{
		return  dao;
	}	
	
	
	
}