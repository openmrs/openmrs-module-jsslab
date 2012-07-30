package org.openmrs.module.jsslab;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.hibernate.HibernateException;
import org.junit.Before;
import org.junit.Test;
import org.openmrs.Encounter;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabOrderSpecimen;
import org.openmrs.module.jsslab.db.LabSpecimen;
<<<<<<< .mine
import org.openmrs.module.jsslab.db.LabReport;
import org.openmrs.module.jsslab.impl.LabOrderServiceImpl;
//import org.openmrs.module.webservices.rest.test.Util;
=======
>>>>>>> .r28715
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;
import org.openmrs.test.TestUtil;

public class LabOrderServiceTest extends BaseModuleContextSensitiveTest {

	private final String EXISTING_LAB_ORDER_UUID = "ee8b270c-49b9-11e1-812a-0024e8c61285";
	private final String EXISTING_SPECIMEN_UUID = "cbbf4eae-49bc-11e1-812a-0024e8c61285";
	
	@Before
	public void before() throws Exception {
		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		executeDataSet("jsslab_order_specimen.xml");
		authenticate();
//		TestUtil.saveGlobalProperty("jsslab.LabOrder", "33333001");
		TestUtil.printOutTableContents(getConnection(), "global_property");
	}
	
	/**
	 * @see LabOrderService#getLabOrderByUuid(String)
	 * @verifies get by uuid
	 */
	@Test
	@SkipBaseSetup
	public void getLabOrderByUuid_shouldGetByUuid() throws Exception {

		LabOrder lo = Context.getService(LabOrderService.class).getLabOrderByUuid(EXISTING_LAB_ORDER_UUID);
		Assert.assertNotNull("GetLabOrderByUuid should not return null",lo);
		Assert.assertEquals("GetLabOrderByUuid should return the right object",EXISTING_LAB_ORDER_UUID, lo.getUuid());
	}

	/**
	 * @see LabOrderService#saveLabOrder(LabOrder)
	 * @verifies save
	 */
	@Test
	@SkipBaseSetup
	public void saveLabOrder_shouldCreateNewLabOrder() throws Exception {

		LabOrderService los = Context.getService(LabOrderService.class);
		LabOrder lo = new LabOrder();
		fillLabOrder(lo);
		los.saveLabOrder(lo);
		
		LabOrder lo2 = los.getLabOrderByUuid(lo.getUuid());
		Assert.assertNotNull("GetLabOrderByUuid should not return null",lo2);
		Assert.assertEquals("GetLabOrderByUuid should return the right object",lo2.getUuid(), lo.getUuid());

		TestUtil.printOutTableContents(getConnection(), "jsslab_order","orders");
	}

	private void fillLabOrder(LabOrder lo) {
		Encounter encounter=Context.getEncounterService().getEncounter(33333001);
		
		lo.setConcept(Context.getConceptService().getConcept(33333067));
		lo.setCreator(Context.getAuthenticatedUser());
		lo.setDateCreated(new Date());
		lo.setEncounter(encounter);
		lo.setLabOrderId("TEST ORDER 1");
		lo.setOrderer(lo.getCreator());
		lo.setPatient(encounter.getPatient());
		lo.setRetestReason("");
		lo.setUrgent(false);
		if (lo.getUuid() == null)
			lo.setUuid("12312312-1231-1231-1231-123123123");
		lo.setVoided(false);
		}

	@Test(expected=APIException.class)
	@SkipBaseSetup
	public void saveLabOrder_shouldFailIfNull() throws Exception {
		LabOrder labOrder = null;
		Context.getService(LabOrderService.class).saveLabOrder(labOrder);
		Assert.assertNotNull("saveLabOrder should fail if null",null);
	}
	
	@Test(expected=APIException.class)
	@SkipBaseSetup
	public void saveLabOrder_shouldFailIfRequiredFieldsAreMissing() throws Exception {
		LabOrder labOrder = new LabOrder();
		Context.getService(LabOrderService.class).saveLabOrder(labOrder);
		Assert.assertNotNull("saveLabOrder should fail if required fields are missing",null);
	}
	
	/**
	 * @see LabOrderService#getCountOfLabOrder(String search,Boolean ifVoided)
	 * @verifies get count of LabOrder
	 */
	
	@Test
	@SkipBaseSetup
	public void getCountOfLabOrders_shouldGetCountOfLabOrders() throws Exception {
		Integer count = Context.getService(LabOrderService.class).getCountOfLabOrders(false);
		Assert.assertNotNull("getCountOfLabOrder should not return null", count);
		Assert.assertEquals("getCountOfLabOrder should return the right number", "1", count.toString());
	}
	
	/**
	 * @see LabOrderService#getAllLabOrders(Boolean ifVoided)
	 * @verifies get all LabOrders
	 */
	
	@Test
	@SkipBaseSetup
	public void getAllLabOrders_shouldGetAllLabOrders() throws Exception {
		List<LabOrder> list = Context.getService(LabOrderService.class).getAllLabOrders(false);
		Assert.assertNotNull("getAllLabOrders should not return null", list);
		Assert.assertEquals("getAllLabOrders should return the right objects", 1, list.size());
	}
	
	/**
	 * @see LabOrderService#getLabOrders(String displayFragment, Boolean ifVoided, Integer
	 *      index, Integer length)
	 * @verifies get specific LabOrders
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabOrders_shouldGetLabOrders() throws Exception {
		List<LabOrder> list = Context.getService(LabOrderService.class).getLabOrders("", false, 0, 0);
		Assert.assertNotNull("getLabOrders should not return null", list);
		Assert.assertEquals("getLabOrders should return the right objects", 1, list.size());
	}
	
	/**
	 * @see voidLabOrder(LabOrder labLabOrder, String reason)
	 * @verifies void LabOrder
	 */
	
	@Test
	@SkipBaseSetup
	public void voidLabOrder_shouldVoidedLabOrder() throws Exception {
		LabOrder labOrder = Context.getService(LabOrderService.class).getLabOrderByUuid(EXISTING_LAB_ORDER_UUID);
		Context.getService(LabOrderService.class).voidLabOrder(labOrder, "");
		labOrder = Context.getService(LabOrderService.class).getLabOrderByUuid(EXISTING_LAB_ORDER_UUID);
		Assert.assertNotNull("voidLabOrder should not return null", labOrder);
		Assert.assertEquals("voidLabOrder should return the right object with voided true", "true", labOrder
		        .getVoided().toString());
	}
	
	/**
	 * @see purgeLabOrder(LabOrder labLabOrder)
	 * @verifies purge LabOrder
	 */
	
	@Test
	@SkipBaseSetup
	public void purgeLabOrder_shouldPurgeLabOrder() throws Exception {
		LabOrder labOrder = Context.getService(LabOrderService.class).getLabOrderByUuid(EXISTING_LAB_ORDER_UUID);
		Context.getService(LabOrderService.class).purgeLabOrder(labOrder);
		try {
			labOrder = Context.getService(LabOrderService.class).getLabOrderByUuid(EXISTING_LAB_ORDER_UUID);
		} catch (HibernateException e) {
			labOrder = null;
		}
		Assert.assertNull("purgeLabOrder should return null", labOrder);
	}
	
	/**
	 * @see getLabOrder(Integer labOrderId)
	 * @verifies get LabOrder by LabOrderId
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabOrder_shouldGetLabOrder() throws Exception {
		LabOrder labOrder = Context.getService(LabOrderService.class).getLabOrder(33333001);
		Assert.assertNotNull("getLabOrder should not return null", labOrder);
		Assert.assertEquals("getLabOrder should return the right object",
		    EXISTING_LAB_ORDER_UUID, labOrder.getUuid());
	}
	
	
	/**
	 * @see LabOrderService#getLabSpecimenByUuid(String)
	 * @verifies get by uuid
	 */
	@Test
	@SkipBaseSetup
	public void getLabSpecimenByUuid_shouldGetByUuid() throws Exception {
		LabSpecimen ls = Context.getService(LabOrderService.class).getLabSpecimenByUuid(EXISTING_SPECIMEN_UUID);
		Assert.assertNotNull("GetLabSpecimenByUuid should not return null",ls);
		Assert.assertEquals("GetLabSpecimenByUuid should return the right object",EXISTING_SPECIMEN_UUID, ls.getUuid());
	}

	/**
	 * @see LabOrderService#saveLabSpecimen(LabSpecimen)
	 * @verifies save
	 */
	@Test
	@SkipBaseSetup
	public void saveLabSpecimen_shouldCreateNewLabSpecimen() throws Exception {

		LabOrderService los = Context.getService(LabOrderService.class);
		LabSpecimen ls = new LabSpecimen();
		fillLabSpecimen(ls);
		los.saveLabSpecimen(ls);
		
		LabSpecimen ls2 = los.getLabSpecimenByUuid(ls.getUuid());
		Assert.assertNotNull("GetLabSpecimenByUuid should not return null",ls2);
		Assert.assertEquals("GetLabSpecimenByUuid should return the right object",ls2.getUuid(), ls.getUuid());

		TestUtil.printOutTableContents(getConnection(), "jsslab_specimen");
	}

	private void fillLabSpecimen(LabSpecimen ls) {
		ls.setLabSpecimenId("TEST Specimen");
		ls.setClientSpecimenId("TEST CLIENT");
		ls.setUrgent(false);
		ls.setHidden(false);
		ls.setLabSpecimenId("33333333");
		ls.setOrderedBy(Context.getUserService().getUser(33333001));
		ls.setOrderedByFacility(Context.getLocationService().getLocation(33333001));
		ls.setPatient(Context.getPatientService().getPatient(33333007));
		ls.setPerson(Context.getPersonService().getPerson(ls.getPatient().getPersonId()));
		ls.setReceivedDate(new Date());
		ls.setReceivedSpecimenTypeConcept(Context.getConceptService().getConcept(33333001));
		ls.setReport(Context.getService(LabTestingService.class).getLabReport(33333001));
		ls.setSpecimenDate(ls.getReceivedDate());
		ls.setDateCreated(ls.getReceivedDate());
		ls.setCreator(Context.getAuthenticatedUser());
		if (ls.getUuid() == null)
			ls.setUuid("12312312-1231-1231-1231-123123123");
		ls.setRetired(false);
	}
	
	/**
	 * @see LabOrderService#saveLabOrderSpecimen(LabOrderSpecimen)
	 * @verifies saveLabOrderSpecimen
	 */
	@Test
	@SkipBaseSetup
	@Ignore
	public void saveLabOrderSpecimen_ShouldAddToOrderSpecimen() throws Exception {
		LabOrderService los = Context.getService(LabOrderService.class);
		
		LabOrder lo = new LabOrder();
		fillLabOrder(lo);
		los.saveLabOrder(lo);
		
		LabSpecimen ls = new LabSpecimen();
		fillLabSpecimen(ls);
		los.saveLabSpecimen(ls);

		LabOrderSpecimen os = new LabOrderSpecimen();
		os.setSpecimenRoleConcept(Context.getConceptService().getConcept(33333088));
		os.setOrder(lo);
		os.setSpecimen(ls);
		os.setDateCreated(ls.getReceivedDate());
		os.setCreator(Context.getAuthenticatedUser());
		if (os.getUuid() == null)
			os.setUuid("12312312-1231-1231-1231-123123123");
		os.setVoided(false);
		los.saveLabOrderSpecimen(os);
		
		LabOrderSpecimen os2 = Context.getService(LabOrderService.class).getLabOrderSpecimenByUuid(os.getUuid());
		Assert.assertNotNull("GetOrderSpecimenByUuid should not return null", os2);
		Assert.assertEquals("GetOrderSpecimenByUuid should return the right object", os2.getUuid(), os.getUuid());
		TestUtil.printOutTableContents(getConnection(), "jsslab_order_specimen");

		LabOrder lo2 = los.getLabOrderByUuid(lo.getUuid());
		LabSpecimen ls2 = los.getLabSpecimenByUuid(ls.getUuid());
		Assert.assertTrue("Created order specimen should be in LabOrder", lo2.getOrderSpecimens().contains(os2));
		Assert.assertTrue("Created order specimen should be in LabSpecimen", ls2.getOrderSpecimens().contains(os2));

	}

	/**
	 * @see LabOrderService#saveLabOrderSpecimen(LabOrderSpecimen)
	 * @verifies saveLabOrderSpecimen
	 */
	@Test
	@SkipBaseSetup
	public void saveLabOrderSpecimen_ShouldAddToLabOrder() throws Exception {
		LabOrderService los = Context.getService(LabOrderService.class);
		
		LabOrder lo = new LabOrder();
		fillLabOrder(lo);
		los.saveLabOrder(lo);
		
		LabSpecimen ls = new LabSpecimen();
		fillLabSpecimen(ls);
		los.saveLabSpecimen(ls);

		LabOrderSpecimen os = new LabOrderSpecimen();
		os.setSpecimenRoleConcept(Context.getConceptService().getConcept(33333088));
		os.setOrder(lo);
		os.setSpecimen(ls);
		os.setDateCreated(ls.getReceivedDate());
		os.setCreator(Context.getAuthenticatedUser());
		if (os.getUuid() == null)
			os.setUuid("12312312-1231-1231-1231-123123123");
		os.setVoided(false);
		los.saveLabOrderSpecimen(os);
		
		lo.getOrderSpecimens().add(os);
		los.saveLabOrder(lo);
		
		LabOrderSpecimen os2 = Context.getService(LabOrderService.class).getLabOrderSpecimenByUuid(os.getUuid());
		Assert.assertNotNull("GetOrderSpecimenByUuid should not return null",os2);
		Assert.assertEquals("GetOrderSpecimenByUuid should return the right object",os2.getUuid(), os.getUuid());
		TestUtil.printOutTableContents(getConnection(), "jsslab_order_specimen");

		LabOrder lo2 = los.getLabOrderByUuid(lo.getUuid());
		LabSpecimen ls2 = los.getLabSpecimenByUuid(ls.getUuid());
		Assert.assertTrue("Created order specimen should be in LabOrder", lo2.getOrderSpecimens().contains(os2));
		Assert.assertTrue("Created order specimen should be in LabSpecimen", ls2.getOrderSpecimens().contains(os2));

	}
	/**
	 * @see LabOrderService#saveLabOrderSpecimen(LabOrderSpecimen)
	 * @verifies saveLabOrderSpecimen
	 */
	@Test
	@SkipBaseSetup
	public void saveLabOrderSpecimen_ShouldAddToSpecimen() throws Exception {
		LabOrderService los = Context.getService(LabOrderService.class);
		LabOrder lo = new LabOrder();
		fillLabOrder(lo);
		los.saveLabOrder(lo);
		
		LabSpecimen ls = new LabSpecimen();
		fillLabSpecimen(ls);
		los.saveLabSpecimen(ls);

		LabOrderSpecimen os = new LabOrderSpecimen();
		os.setSpecimenRoleConcept(Context.getConceptService().getConcept(33333088));
		os.setOrder(lo);
		os.setSpecimen(ls);
		os.setDateCreated(ls.getReceivedDate());
		os.setCreator(Context.getAuthenticatedUser());
		if (os.getUuid() == null)
			os.setUuid("12312312-1231-1231-1231-123123123");
		os.setVoided(false);
		los.saveLabOrderSpecimen(os);
		
		ls.getOrderSpecimens().add(os);
		los.saveLabSpecimen(ls);
		
		LabOrderSpecimen os2 = Context.getService(LabOrderService.class).getLabOrderSpecimenByUuid(os.getUuid());
		Assert.assertNotNull("GetOrderSpecimenByUuid should not return null",os2);
		Assert.assertEquals("GetOrderSpecimenByUuid should return the right object",os2.getUuid(), os.getUuid());
		TestUtil.printOutTableContents(getConnection(), "jsslab_order_specimen");

		LabOrder lo2 = los.getLabOrderByUuid(lo.getUuid());
		LabSpecimen ls2 = los.getLabSpecimenByUuid(ls.getUuid());
		Assert.assertTrue("Created order specimen should be in LabOrder", lo2.getOrderSpecimens().contains(os2));
		Assert.assertTrue("Created order specimen should be in LabSpecimen", ls2.getOrderSpecimens().contains(os2));

	}
	
	
}