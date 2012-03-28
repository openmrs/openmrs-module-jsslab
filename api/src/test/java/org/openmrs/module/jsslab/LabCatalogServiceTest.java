package org.openmrs.module.jsslab;

import junit.framework.Assert;

import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.db.LabPrecondition;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;

public class LabCatalogServiceTest extends BaseModuleContextSensitiveTest{

	/**
	 * @see LabCatalogService#getLabPreconditionByUuid(String)
	 * @verifies get by uuid
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabPreconditionByUuid_shouldGetByUuid() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		LabPrecondition labPrecondition = Context.getService(LabCatalogService.class).getLabPreconditionByUuid("0100dc0a-46da-11e1-99f4-0024e8c61285");
		Assert.assertNotNull("getLabPreconditionByUuid should not return null",labPrecondition);
		Assert.assertEquals("GetLabOrderByUuid should return the right object","0100dc0a-46da-11e1-99f4-0024e8c61285", labPrecondition.getUuid());
	}
	
	/**
	 * @see LabCatalogService#saveLabPrecondition(LabPrecondition labLabPrecondition)
	 * @verifies save LabPrecondition
	 **/
	public void saveLabPrecondition_shouldReturnLabPreconditionFromDatabase(LabPrecondition labPrecondition)throws Exception
	{
		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		Context.getService(LabCatalogService.class).saveLabPrecondition(labPrecondition);
		LabPrecondition labPreconditionTemp = Context.getService(LabCatalogService.class).getLabPreconditionByUuid(labPrecondition.getUuid());
		Assert.assertEquals("LabPrecondition should return the right object",labPreconditionTemp.getUuid(), labPrecondition.getUuid());
	}
	
	/**
	 * 
	 * @param labPrecondition
	 * @throws Exception
	 * @verifies delete LabPrecondition
	 */
	public void purgeLabPrecondition_shouldDeleteLabPreconditionFromDatabase(LabPrecondition labPrecondition)throws Exception
	{
		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		Context.getService(LabCatalogService.class).purgeLabPrecondition(labPrecondition);
		Assert.assertNull("should return null",Context.getService(LabCatalogService.class).getLabPreconditionByUuid(labPrecondition.getUuid()));
	}
}
