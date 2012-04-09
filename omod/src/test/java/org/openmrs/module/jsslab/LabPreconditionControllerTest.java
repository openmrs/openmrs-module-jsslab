package org.openmrs.module.jsslab;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabPrecondition;
import org.openmrs.module.jsslab.rest.controller.LabPreconditionController;
//import org.openmrs.module.webservices.rest.test.Util;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class LabPreconditionControllerTest extends BaseModuleWebContextSensitiveTest{
	private static final String LabPreconditionUuid = "0100dc0a-46da-11e1-99f4-0024e8c61285";
	
	private static final String datasetFilename = "jsslab_data_delta.xml";
	
	private LabCatalogService service;
	
	private LabPreconditionController controller;
	
	private MockHttpServletRequest request;
	
	private HttpServletResponse response;
	
	@Before
	public void before()throws Exception
	{
		this.service=Context.getService(LabCatalogService.class);
		this.controller=new LabPreconditionController();
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
		initializeInMemoryDatabase();
		executeDataSet(datasetFilename);
		authenticate();
	}
	
	@Test
	public void getLabPreconditionByUuid_shouldGetLabPreconditionByUuid()throws Exception
	{
		Object result=controller.retrieve(LabPreconditionUuid, request);
		Assert.assertNotNull(result);
		Util.log("LabPrecondition fetched (default)", result);
		Assert.assertEquals(LabPreconditionUuid, PropertyUtils.getProperty(result, "uuid"));
	}
}
