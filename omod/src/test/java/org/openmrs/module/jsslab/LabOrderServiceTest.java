package org.openmrs.module.jsslab;


import junit.framework.Assert;

import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.test.BaseModuleContextSensitiveTest;


public class LabOrderServiceTest extends BaseModuleContextSensitiveTest{
	/**
	 * @see LabOrderService#getLabOrderByUuid(String)
	 * @verifies get by uuid
	 */
	@Test
	public void getLabOrderByUuid_shouldGetByUuid() throws Exception {
		executeDataSet("jsslab_data.xml");
		LabOrder lo = Context.getService(LabOrderService.class).getLabOrderByUuid("ee8b270c-49b9-11e1-812a-0024e8c61285");
		Assert.assertNotNull(lo);
	}
}