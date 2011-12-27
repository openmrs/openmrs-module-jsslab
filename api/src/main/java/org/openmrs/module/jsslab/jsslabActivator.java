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
package org.openmrs.module.jsslab;

import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.Activator;
import org.openmrs.module.BaseModuleActivator;


/**
 * This class contains the logic that is run every time this module is either started or shutdown
 */
public class jsslabActivator extends BaseModuleActivator implements Activator {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	/**
	 * @see org.openmrs.module.Activator#startup()
	 */
	public void startup() {
		log.info("Starting JSSLab Module");
	}
	
	/**
	 * @see org.openmrs.module.Activator#shutdown()
	 */
	public void shutdown() {
		log.info("Shutting down JSSLab Module");
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.BaseModuleActivator#contextRefreshed()
	 */
	@Override
	public void contextRefreshed() {
		super.contextRefreshed();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.BaseModuleActivator#started()
	 */
	@Override
	public void started() {
		super.started();
		log.info("JSSLab Module is started");
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.BaseModuleActivator#stopped()
	 */
	@Override
	public void stopped() {
		super.stopped();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.BaseModuleActivator#willRefreshContext()
	 */
	@Override
	public void willRefreshContext() {
		super.willRefreshContext();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.BaseModuleActivator#willStart()
	 */
	@Override
	public void willStart() {
		super.willStart();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.BaseModuleActivator#willStop()
	 */
	@Override
	public void willStop() {
		super.willStop();
	}
	
}
