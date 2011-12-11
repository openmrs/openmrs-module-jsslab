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
package org.openmrs.module.jsslab.web.controller;

import org.openmrs.module.jsslab.rest.controller.LabInstrumentController;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.LabManagementService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.web.test.BaseModuleWebContextSensitiveTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * Tests functionality of {@link LabInstrumentController}. This does not use @should annotations because
 * the controller inherits those methods from a subclass
 */
public class LabInstrumentControllerTest extends BaseModuleWebContextSensitiveTest {
	
	private LabManagementService service;
	
	private LabInstrumentController controller;
	
	private MockHttpServletRequest request;
	
	private HttpServletResponse response;
	
	@Before
	public void before() {
		this.service = Context.getService(LabManagementService.class);
		this.controller = new LabInstrumentController();
		this.request = new MockHttpServletRequest();
		this.response = new MockHttpServletResponse();
	}
	
	@Test
	public void shouldGetALabInstrumentByUuid() throws Exception {
		Object result = controller.retrieve("72a26526-21d2-11e1-9815-00265e639063", request);
		Assert.assertNotNull(result);
		Assert.assertEquals("72a26526-21d2-11e1-9815-00265e639063", PropertyUtils.getProperty(result, "uuid"));
		Assert.assertEquals("Shenzen Mindray", PropertyUtils.getProperty(result, "manufacturer"));
		Assert.assertEquals("BC-3600", PropertyUtils.getProperty(result, "model"));
		Assert.assertEquals("M2345-6428", PropertyUtils.getProperty(result, "serialNumber"));
	}
	
	@Test
	public void shouldListAllUnRetiredLabInstruments() throws Exception {
		List<Object> result = controller.getAll(request, response);
		Assert.assertNotNull(result);
		Assert.assertEquals(1, result.size());
	}
	
	@Test
	public void shouldCreateALabInstrument() throws Exception {
		int originalCount = service.getAllLabInstruments(true).size();
		String json = "{ \"name\":\"test labInstrument\" }";
		SimpleObject post = new ObjectMapper().readValue(json, SimpleObject.class);
		Object newLabInstrument = controller.create(post, request, response);
		Assert.assertNotNull(PropertyUtils.getProperty(newLabInstrument, "uuid"));
		Assert.assertEquals(originalCount + 1, service.getAllLabInstruments(true).size());
	}
	
	@Test
	public void shouldEditALabInstrument() throws Exception {
		String json = "{ \"manufacturer\":\"Becton Dickenson\", \"model\":\"FacsCount\", \"serialNumber\":\"BDFC-35784\" }";
		SimpleObject post = new ObjectMapper().readValue(json, SimpleObject.class);
		controller.update("167ce20c-4785-4285-9119-d197268f7f4a", post, request, response);
		LabInstrument updated = service.getLabInstrumentByUuid("167ce20c-4785-4285-9119-d197268f7f4a");
		Assert.assertNotNull(updated);
		Assert.assertEquals("Becton Dickenson", updated.getManufacturer());
		Assert.assertEquals("FacsCount", updated.getModel());
		Assert.assertEquals("BDFC-35784", updated.getSerialNumber());
	}
	
	@Test
	public void shouldRetireALabInstrument() throws Exception {
		String uuid = "167ce20c-4785-4285-9119-d197268f7f4a";
		LabInstrument labInstrument = service.getLabInstrumentByUuid(uuid);
		Assert.assertFalse(labInstrument.isRetired());
		controller.delete(uuid, "test reason", request, response);
		labInstrument = service.getLabInstrumentByUuid(uuid);
		Assert.assertTrue(labInstrument.isRetired());
		Assert.assertEquals("test reason", labInstrument.getRetireReason());
	}
	
	@Test
	public void shouldPurgeALabInstrument() throws Exception {
		int originalCount = service.getAllLabInstruments(true).size();
		controller.purge("167ce20c-4785-4285-9119-d197268f7f4a", request, response);
		Assert.assertNull(service.getLabInstrumentByUuid("167ce20c-4785-4285-9119-d197268f7f4a"));
		Assert.assertEquals(originalCount - 1, service.getAllLabInstruments(true).size());
	}
	
	@Test
	public void shouldReturnTheAuditInfoForTheFullRepresentation() throws Exception {
		MockHttpServletRequest httpReq = new MockHttpServletRequest();
		httpReq.addParameter(RestConstants.REQUEST_PROPERTY_FOR_REPRESENTATION, RestConstants.REPRESENTATION_FULL);
		Object result = controller.retrieve("72a26526-21d2-11e1-9815-00265e639063", httpReq);
		Assert.assertNotNull(result);
		Assert.assertNotNull(PropertyUtils.getProperty(result, "auditInfo"));
	}
	
}
