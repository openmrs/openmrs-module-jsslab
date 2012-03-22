package org.openmrs.module.jsslab;


import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

import junit.framework.Assert;

import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabSpecimen;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;

public class LabOrderServiceTest extends BaseModuleContextSensitiveTest{
	
	/**
	 * @see LabOrderService#getLabOrderByUuid(String)
	 * @verifies get by uuid
	 */
	@Test
	@SkipBaseSetup
	public void getLabOrderByUuid_shouldGetByUuid() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
//		TestUtil.printOutTableContents(getConnection(), "users","global_property");
		authenticate();

		LabOrder lo = Context.getService(LabOrderService.class).getLabOrderByUuid("ee8b270c-49b9-11e1-812a-0024e8c61285");
		Assert.assertNotNull("GetLabOrderByUuid should not return null",lo);
		Assert.assertEquals("GetLabOrderByUuid should return the right object","ee8b270c-49b9-11e1-812a-0024e8c61285", lo.getUuid());
	}

	/**
	 * @see LabOrderService#getLabSpecimenByUuid(String)
	 * @verifies get by uuid
	 */
	@Test
	@SkipBaseSetup
	public void getLabSpecimenByUuid_shouldGetByUuid() throws Exception {
		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();

		LabSpecimen ls = Context.getService(LabOrderService.class).getLabSpecimenByUuid("cbbf4eae-49bc-11e1-812a-0024e8c61285");
		Assert.assertNotNull("GetLabSpecimenByUuid should not return null",ls);
		Assert.assertEquals("GetLabSpecimenByUuid should return the right object","cbbf4eae-49bc-11e1-812a-0024e8c61285", ls.getUuid());
	}

}