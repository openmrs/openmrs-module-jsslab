package org.openmrs.module.jsslab.rest.resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabManagementService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.rest.v1_0.resource.LabInstrumentResource;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;

public class LabInstrumentResourceTest extends BaseDelegatingResourceTest<LabInstrumentResource,LabInstrument>{

	private static final String datasetFilename = "jsslab_data_delta.xml";
	
	@Before
	public void before() throws Exception {
		executeDataSet(datasetFilename);
	}

	@Override
	public LabInstrument newObject() {
		return Context.getService(LabManagementService.class).getLabInstrumentByUuid(getUuidProperty());
	}

	@Override
	public String getDisplayProperty() {
		return getObject().getDisplayString();
	}

	@Override
	public void validateDefaultRepresentation() throws Exception {
		super.validateDefaultRepresentation();
		assertPropEquals("retired", getObject().getRetired());
		assertPropEquals("name", getObject().getName());
		assertPropEquals("propertyTag", getObject().getPropertyTag());
		assertPropPresent("location");
	}
	
	@Override
	public void validateFullRepresentation() throws Exception {
		super.validateFullRepresentation();
		assertPropEquals("name", getObject().getName());
		assertPropEquals("propertyTag", getObject().getPropertyTag());
		assertPropPresent("location");
		assertPropPresent("conditionConcept");
		assertPropEquals("manufacturer", getObject().getManufacturer());
		assertPropEquals("serialNumber", getObject().getSerialNumber());
		assertPropEquals("receivedDate", getObject().getReceivedDate());
		assertPropEquals("receivedFrom", getObject().getReceivedFrom());
		assertPropEquals("receivedCost", getObject().getReceivedCost());
		assertPropEquals("receivedValue", getObject().getReceivedValue());
		assertPropEquals("conditionDate", getObject().getConditionDate());
		assertPropEquals("maintenanceVendor", getObject().getMaintenanceVendor());
		assertPropEquals("maintenancePhone", getObject().getMaintenancePhone());
		assertPropEquals("maintenanceDescription", getObject().getMaintenanceDescription());
		assertPropPresent("auditInfo");
	}
	
	@Override
	public String getUuidProperty() {
		return "72a26526-21d2-11e1-9815-00265e639063";
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest#asRepresentation_shouldReturnValidFullRepresentation()
	 */
	@Override
	@Test
	@Ignore("Delete the whole test when fixed")
	public void asRepresentation_shouldReturnValidFullRepresentation() throws Exception {
	    super.asRepresentation_shouldReturnValidFullRepresentation();
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest#asRepresentation_shouldReturnValidRefRepresentation()
	 */
	@Override
	@Test
	@Ignore("Delete the whole test when fixed")
	public void asRepresentation_shouldReturnValidRefRepresentation() throws Exception {
	    super.asRepresentation_shouldReturnValidRefRepresentation();
	}
}
