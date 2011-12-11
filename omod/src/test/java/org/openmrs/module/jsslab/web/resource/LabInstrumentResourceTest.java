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
package org.openmrs.module.jsslab.web.resource;


import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.rest.resource.LabInstrumentResource;
import org.openmrs.module.jsslab.LabManagementService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;

public class LabInstrumentResourceTest extends BaseDelegatingResourceTest<LabInstrumentResource, LabInstrument> {
	
	@Override
	public LabInstrument newObject() {
		return Context.getService(LabManagementService.class).getLabInstrumentByUuid(getUuidProperty());
	}
	
	@Override
	public void validateRefRepresentation() throws Exception {
		super.validateRefRepresentation();
		assertPropEquals("retired", getObject().isRetired());
	}
	
	@Override
	public void validateDefaultRepresentation() throws Exception {
		super.validateDefaultRepresentation();
		assertPropEquals("name", getObject().getName());
		assertPropEquals("propertyTag", getObject().getPropertyTag());
		assertPropEquals("location", getObject().getLocation());
		assertPropEquals("retired", getObject().isRetired());
	}
	
	@Override
	public void validateFullRepresentation() throws Exception {
		super.validateFullRepresentation();
		assertPropEquals("propertyTag", getObject().getPropertyTag());
		assertPropEquals("manufacturer", getObject().getManufacturer());
		assertPropEquals("model", getObject().getModel());
		assertPropEquals("serialNumber", getObject().getSerialNumber());
		assertPropEquals("location", getObject().getLocation());
		assertPropEquals("receivedDate", getObject().getReceivedDate());
		assertPropEquals("receivedFrom", getObject().getReceivedFrom());
		assertPropEquals("receivedCost", getObject().getReceivedCost());
		assertPropEquals("receivedValue", getObject().getReceivedValue());
		assertPropEquals("conditionDate", getObject().getConditionDate());
		assertPropEquals("conditionConcept", getObject().getConditionConcept());
		assertPropEquals("maintenanceVendor", getObject().getMaintenanceVendor());
		assertPropEquals("maintenancePhone", getObject().getMaintenancePhone());
		assertPropEquals("maintenanceDescription", getObject().getMaintenanceDescription());
		assertPropPresent("testRuns");
		assertPropEquals("retired", getObject().isRetired());
		assertPropPresent("auditInfo");
	}
	
	@Override
	public String getDisplayProperty() {
		return getObject().getName();
	}
	
	@Override
	public String getUuidProperty() {
		return getObject().getUuid();
	}
	
}
