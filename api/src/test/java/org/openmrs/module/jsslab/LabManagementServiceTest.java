package org.openmrs.module.jsslab;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.test.BaseModuleContextSensitiveTest;

public class LabManagementServiceTest extends BaseModuleContextSensitiveTest{

	@Before
	public void before() throws Exception {
		executeDataSet("jsslab_data_delta.xml");
	}
	
	@Test
	public void getLabInstrumentByUuid_shouldGetByUuid() throws Exception {
		LabInstrument labInstrument = Context.getService(LabManagementService.class).getLabInstrumentByUuid("72a26526-21d2-11e1-9815-00265e639063");
		Assert.assertNotNull("getLabInstrumentByUuid should not return null",labInstrument);
		Assert.assertEquals("getLabInstrumentByUuid should return the right object","72a26526-21d2-11e1-9815-00265e639063", labInstrument.getUuid());
	}
	
	@Test
	public void getLabSupplyItemByUUID_shouldGetByUuid() throws Exception {
		LabSupplyItem labSupplyItem = Context.getService(LabManagementService.class).getLabSupplyItemByUUID("36fa7e2a-21d4-11e1-9815-00265e639063");
		Assert.assertNotNull("getLabInstrumentByUuid should not return null",labSupplyItem);
		Assert.assertEquals("getLabInstrumentByUuid should return the right object","36fa7e2a-21d4-11e1-9815-00265e639063", labSupplyItem.getUuid());
	}
	
	
	
}
