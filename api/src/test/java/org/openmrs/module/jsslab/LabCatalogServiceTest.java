package org.openmrs.module.jsslab;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.Location;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.db.LabPrecondition;
import org.openmrs.module.jsslab.db.LabSpecimenTemplate;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;

public class LabCatalogServiceTest extends BaseModuleContextSensitiveTest {

	@Before
	public void before() throws Exception {
		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
	}

	/**
	 * @see LabCatalogService#getLabPreconditionByUuid(String)
	 * @verifies get by uuid
	 */

	@Test
	@SkipBaseSetup
	public void getLabPreconditionByUuid_shouldGetByUuid() throws Exception {
		LabPrecondition labPrecondition = Context.getService(
				LabCatalogService.class).getLabPreconditionByUuid(
				"0100dc0a-46da-11e1-99f4-0024e8c61285");
		Assert.assertNotNull("getLabPreconditionByUuid should not return null",
				labPrecondition);
		Assert.assertEquals(
				"getLabPreconditionByUuid should return the right object",
				"0100dc0a-46da-11e1-99f4-0024e8c61285",
				labPrecondition.getUuid());
	}

	/**
	 * @see LabCatalogService#saveLabPrecondition(LabPrecondition
	 *      labLabPrecondition)
	 * @verifies save LabPrecondition
	 **/

	@Test
	@SkipBaseSetup
	public void saveLabPrecondition_shouldSaveRecord() throws Exception {
		LabPrecondition labPrecondition = Context.getService(
				LabCatalogService.class).getLabPreconditionByUuid(
				"0100dc0a-46da-11e1-99f4-0024e8c61285");
		labPrecondition.setUuid("12312312-1231-1231-1231-123123123");
		Context.getService(LabCatalogService.class).saveLabPrecondition(
				labPrecondition);
		labPrecondition = Context.getService(LabCatalogService.class)
				.getLabPreconditionByUuid("12312312-1231-1231-1231-123123123");
		Assert.assertNotNull("saveLabPrecondition should not return null",
				labPrecondition);
		Assert.assertEquals(
				"saveLabPrecondition should return the right object",
				"12312312-1231-1231-1231-123123123", labPrecondition.getUuid());
	}

	@Test(expected = APIException.class)
	@SkipBaseSetup
	public void saveLabPrecondition_shouldFailIfNull() throws Exception {
		LabPrecondition labPrecondition = null;
		Context.getService(LabCatalogService.class).saveLabPrecondition(
				labPrecondition);
		Assert.assertNotNull("saveLabPrecondition should fail if null", null);
	}

	@Test(expected = APIException.class)
	@SkipBaseSetup
	public void saveLabPrecondition_shouldFailIfRequiredFieldsAreMissing()
			throws Exception {
		LabPrecondition labPrecondition = new LabPrecondition();
		Context.getService(LabCatalogService.class).saveLabPrecondition(
				labPrecondition);
		Assert.assertNotNull(
				"saveLabPrecondition should fail if required fields are missing",
				null);
	}

	/**
	 * @see LabCatalogService#getCountOfLabPrecondition(String search,Boolean
	 *      ifVoided)
	 * @verifies get count of LabPrecondition
	 */

	@Test
	@SkipBaseSetup
	public void getCountOfLabPrecondition_shouldGetCountOfLabPrecondition()
			throws Exception {
		Integer count = Context.getService(LabCatalogService.class)
				.getCountOfLabPrecondition("", false);
		Assert.assertNotNull(
				"getCountOfLabPrecondition should not return null", count);
		Assert.assertEquals(
				"getCountOfLabPrecondition should return the right number",
				"1", count.toString());
	}

	/**
	 * @see LabCatalogService#getAllLabPreconditions(Boolean ifVoided)
	 * @verifies get all LabPreconditions
	 */

	@Test
	@SkipBaseSetup
	public void getAllLabPreconditions_shouldGetAllLabPreconditions()
			throws Exception {
		List<LabPrecondition> list = Context
				.getService(LabCatalogService.class).getAllLabPreconditions(
						false);
		Assert.assertNotNull("getAllLabPreconditions should not return null",
				list);
		Assert.assertEquals(
				"getAllLabPreconditions should return the right objects", 1,
				list.size());
	}

	/**
	 * @see LabCatalogService#getLabPreconditions(String displayFragment,
	 *      Boolean ifVoided, Integer index, Integer length)
	 * @verifies get specific LabPreconditions
	 */

	@Test
	@SkipBaseSetup
	public void getLabPreconditions_shouldGetLabPreconditions()
			throws Exception {
		List<LabPrecondition> list = Context
				.getService(LabCatalogService.class).getLabPreconditions("",
						false, 0, 0);
		Assert.assertNotNull("getLabPreconditions should not return null", list);
		Assert.assertEquals(
				"getLabPreconditions should return the right objects", 1,
				list.size());
	}

	/**
	 * @see voidLabPrecondition(LabPrecondition labLabPrecondition, String
	 *      reason)
	 * @verifies void LabPrecondition
	 */

	@Test
	@SkipBaseSetup
	public void voidLabPrecondition_shouldVoidedLabPrecondition()
			throws Exception {
		LabPrecondition labPrecondition = Context.getService(
				LabCatalogService.class).getLabPreconditionByUuid(
				"0100dc0a-46da-11e1-99f4-0024e8c61285");
		Context.getService(LabCatalogService.class).voidLabPrecondition(
				labPrecondition, "");
		labPrecondition = Context.getService(LabCatalogService.class)
				.getLabPreconditionByUuid(
						"0100dc0a-46da-11e1-99f4-0024e8c61285");
		Assert.assertNotNull("voidLabPrecondition should not return null",
				labPrecondition);
		Assert.assertEquals(
				"voidLabPrecondition should return the right object with voided true",
				"true", labPrecondition.getVoided().toString());
	}

	/**
	 * @see purgeLabPrecondition(LabPrecondition labLabPrecondition)
	 * @verifies purge LabPrecondition
	 */

	@Test
	@SkipBaseSetup
	public void purgeLabPrecondition_shouldPurgeLabPrecondition()
			throws Exception {
		LabPrecondition labPrecondition = Context.getService(
				LabCatalogService.class).getLabPreconditionByUuid(
				"0100dc0a-46da-11e1-99f4-0024e8c61285");
		Context.getService(LabCatalogService.class).purgeLabPrecondition(
				labPrecondition);
		labPrecondition = Context.getService(LabCatalogService.class)
				.getLabPreconditionByUuid(
						"0100dc0a-46da-11e1-99f4-0024e8c61285");
		Assert.assertNull("purgeLabPrecondition should return null",
				labPrecondition);
	}

	/**
	 * @see getLabPrecondition(Integer labPreconditionId)
	 * @verifies get LabPrecondition by LabPreconditionId
	 */

	@Test
	@SkipBaseSetup
	public void getLabPrecondition_shouldGetLabPrecondition() throws Exception {
		LabPrecondition labPrecondition = Context.getService(
				LabCatalogService.class).getLabPrecondition(33333067);
		Assert.assertNotNull("getLabPreconditionByUuid should not return null",
				labPrecondition);
		Assert.assertEquals(
				"getLabPreconditionByUuid should return the right object",
				"0100dc0a-46da-11e1-99f4-0024e8c61285",
				labPrecondition.getUuid());
	}

	/**
	 * @see LabCatalogService#getLabSpecimenTemplateByUuid(String uuid)
	 * @verifies get by uuid
	 */

	@Test
	@SkipBaseSetup
	public void getLabSpecimenTemplateByUuid_shouldGetByUuid() throws Exception {
		LabSpecimenTemplate labSpecimenTemplate = Context.getService(
				LabCatalogService.class).getLabSpecimenTemplateByUuid(
				"35442792-49b7-11e1-812a-0024e8c61285");
		Assert.assertNotNull(
				"getLabSpecimenTemplateByUuid should not return null",
				labSpecimenTemplate);
		Assert.assertEquals(
				"getLabSpecimenTemplateByUuid should return the right object",
				"35442792-49b7-11e1-812a-0024e8c61285",
				labSpecimenTemplate.getUuid());
	}

	/**
	 * @see LabCatalogService#saveLabSpecimenTemplate(LabSpecimenTemplate
	 *      labSpecimenTemplate)
	 * @verifies save LabSpecimenTemplate
	 **/

	@Test
	@SkipBaseSetup
	public void saveLabSpecimenTemplate_shouldSaveRecord() throws Exception {
		LabSpecimenTemplate labSpecimenTemplate = Context.getService(
				LabCatalogService.class).getLabSpecimenTemplateByUuid(
				"35442792-49b7-11e1-812a-0024e8c61285");
		labSpecimenTemplate.setUuid("12312312-1231-1231-1231-123123123");
		Context.getService(LabCatalogService.class).saveLabSpecimenTemplate(
				labSpecimenTemplate);
		labSpecimenTemplate = Context.getService(LabCatalogService.class)
				.getLabSpecimenTemplateByUuid(
						"12312312-1231-1231-1231-123123123");
		Assert.assertNotNull("getLabPreconditionByUuid should not return null",
				labSpecimenTemplate);
		Assert.assertEquals(
				"getLabPreconditionByUuid should return the right object",
				"12312312-1231-1231-1231-123123123",
				labSpecimenTemplate.getUuid());
	}

	/**
	 * @see LabCatalogService#getCountOfLabSpecimenTemplate(String
	 *      search,Boolean ifVoided)
	 * @verifies get count of LabSpecimenTemplate
	 */

	@Test
	@SkipBaseSetup
	public void getCountOfLabSpecimenTemplate_shouldGetCountOfLabSpecimenTemplate()
			throws Exception {
		Long count = Context.getService(LabCatalogService.class)
				.getCountOfLabSpecimenTemplate("", false);
		Assert.assertNotNull(
				"getCountOfLabSpecimenTemplate should not return null", count);
		Assert.assertEquals(
				"ggetCountOfLabSpecimenTemplate should return the right number",
				"8", count.toString());
	}

	/**
	 * @see LabCatalogService#getAllLabSpecimenTemplates(Boolean ifVoided)
	 * @verifies get all LabSpecimenTemplates
	 */

	@Test
	@SkipBaseSetup
	public void getAllLabSpecimenTemplates_shouldGetAllLabSpecimenTemplates()
			throws Exception {
		List<LabSpecimenTemplate> list = Context.getService(
				LabCatalogService.class).getAllLabSpecimenTemplates(false);
		Assert.assertNotNull(
				"getAllLabSpecimenTemplates should not return null", list);
		Assert.assertEquals(
				"getAllLabSpecimenTemplates should return the right objects",
				8, list.size());
	}

	/**
	 * @see LabCatalogService#getLabSpecimenTemplate(String displayFragment,
	 *      Boolean ifVoided, Integer index, Integer length)
	 * @verifies get specific LabSpecimenTemplate
	 */

	@Test
	@SkipBaseSetup
	public void LabSpecimenTemplate_shouldGetLabSpecimenTemplate()
			throws Exception {
		List<LabSpecimenTemplate> list = Context.getService(
				LabCatalogService.class)
				.getLabSpecimenTemplate("", false, 0, 0);
		Assert.assertNotNull("getLabSpecimenTemplate should not return null",
				list);
		Assert.assertEquals(
				"getLabSpecimenTemplate should return the right objects", 8,
				list.size());
	}

	/**
	 * @see retireLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate,
	 *      String reason)
	 * @verifies retire LabSpecimenTemplate
	 */

	@Test
	@SkipBaseSetup
	public void retireLabSpecimenTemplate_shouldRetireLabSpecimenTemplate()
			throws Exception {
		LabSpecimenTemplate labSpecimenTemplate = Context.getService(
				LabCatalogService.class).getLabSpecimenTemplateByUuid(
				"35442792-49b7-11e1-812a-0024e8c61285");
		Context.getService(LabCatalogService.class).retireLabSpecimenTemplate(
				labSpecimenTemplate, "");
		labSpecimenTemplate = Context.getService(LabCatalogService.class)
				.getLabSpecimenTemplateByUuid(
						"35442792-49b7-11e1-812a-0024e8c61285");
		Assert.assertNotNull(
				"retireLabSpecimenTemplate should not return null",
				labSpecimenTemplate);
		Assert.assertEquals(
				"retireLabSpecimenTemplate should return the right object with voided true",
				"true", labSpecimenTemplate.getRetired().toString());
	}

	/**
	 * @see purgeLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate)
	 * @verifies purge LabSpecimenTemplate
	 */

	@Test
	@SkipBaseSetup
	public void purgeLabSpecimenTemplate_shouldPurgeLabSpecimenTemplate()
			throws Exception {
		LabSpecimenTemplate labSpecimenTemplate = Context.getService(
				LabCatalogService.class).getLabSpecimenTemplateByUuid(
				"35442792-49b7-11e1-812a-0024e8c61285");
		Context.getService(LabCatalogService.class).purgeLabSpecimenTemplate(
				labSpecimenTemplate);
		labSpecimenTemplate = Context.getService(LabCatalogService.class)
				.getLabSpecimenTemplateByUuid(
						"35442792-49b7-11e1-812a-0024e8c61285");
		Assert.assertNull("purgeLabSpecimenTemplate should return null",
				labSpecimenTemplate);
	}

	/**
	 * @see getLabSpecimenTemplate(Integer labSpecimenTemplateId)
	 * @verifies get LabSpecimenTemplaten by LabSpecimenTemplateId
	 */

	@Test
	@SkipBaseSetup
	public void getLabSpecimenTemplate_shouldGetLabSpecimenTemplate()
			throws Exception {
		LabSpecimenTemplate labSpecimenTemplate = Context.getService(
				LabCatalogService.class).getLabSpecimenTemplate(33333001);
		Assert.assertNotNull("getLabSpecimenTemplate should not return null",
				labSpecimenTemplate);
		Assert.assertEquals(
				"getLabSpecimenTemplate should return the right object",
				"35442792-49b7-11e1-812a-0024e8c61285",
				labSpecimenTemplate.getUuid());
	}

	/**
	 * @see getLabSpecimenTemplateByName(String labSpecimenTemplate)
	 * @verifies get LabSpecimenTemplate by getLabSpecimenTemplateName
	 */

	@Test
	@SkipBaseSetup
	public void getLabSpecimenTemplateByName_shouldGetLabSpecimenTemplateByName()
			throws Exception {
		// LabSpecimenTemplate labSpecimenTemplate =
		// Context.getService(LabCatalogService.class).getLabSpecimenTemplateByName("123");
	}

	/**
	 * @see LabCatalogService#getLabTestByUUID(String)
	 * @verifies get LabTest by uuid
	 */

	@Test
	public void getLabTestByUUID_shouldGetLabTestByUuid() throws Exception {
		final String uuid = "5d1545bb-486e-11e1-b5ed-0024e8c61285";

		LabTest labTest = Context.getService(LabCatalogService.class)
				.getLabTestByUUID(uuid);
		Assert.assertNotNull("getLabTestByUUID should not return null", labTest);
		Assert.assertEquals(
				"getLabTestByUUID should return the correct object", uuid,
				labTest.getUuid());
	}

	/**
	 * @see LabCatalogService#purgeLabTest(LabTest)
	 * @verifies delete given LabTest
	 */

	@Test
	public void purgeLabTest_shouldDeleteGivenLabTest() throws Exception {
		String uuid = "5d1545bb-486e-11e1-b5ed-0024e8c61285";

		LabTest labTest = Context.getService(LabCatalogService.class)
				.getLabTestByUUID(uuid);
		Context.getService(LabCatalogService.class).purgeLabTest(labTest);

		labTest = Context.getService(LabCatalogService.class).getLabTestByUUID(
				uuid);
		Assert.assertNull("purgeLabTest should return null", labTest);
	}

	/**
	 * @see LabCatalogService#retireLabTest(LabTest,String)
	 * @verifies retire LabTest
	 */

	@Test
	public void retireLabTest_shouldRetireLabTest() throws Exception {
		String uuid = "5d1eab19-486e-11e1-b5ed-0024e8c61285";

		LabTest labTest;
		labTest = Context.getService(LabCatalogService.class).getLabTestByUUID(
				uuid);
		Context.getService(LabCatalogService.class).retireLabTest(labTest,
				"testing retire");

		labTest = Context.getService(LabCatalogService.class).getLabTestByUUID(
				uuid);
		Assert.assertNotNull(
				"getLabTest should not return null for retired test", labTest);
		Assert.assertEquals(
				"retireLabTest should return the right object with voided true",
				Boolean.TRUE, labTest.getRetired());
		Assert.assertEquals(
				"retireLabTest should return the right object with retire reason",
				"testing retire", labTest.getRetireReason());
		Assert.assertNotNull("LabTestPanel should have retire date",
				labTest.getDateRetired());
	}

	/**
	 * @see LabCatalogService#saveLabTest(LabTest)
	 * @verifies not save LabTest if LabTest doesn't validate
	 */

	@Test
	public void saveLabTest_shouldNotSaveLabTestIfLabTestDoesntValidate()
			throws Exception {
		LabTest labTest = new LabTest();
		String uuid = labTest.getUuid();

		LabTest labTestSaved = null;
		try {
			labTestSaved = Context.getService(LabCatalogService.class)
					.saveLabTest(labTest);
			Assert.fail("should fail saving invalid labTest");
		} catch (Exception e) {
			try {
				labTestSaved = Context.getService(LabCatalogService.class)
						.getLabTestByUUID(uuid);
				Assert.assertNull(
						"LabTest should not be saved, hence be null when loaded",
						labTestSaved);
			} catch (Exception ex) {
				Assert.fail("failed to execute getLabTest - invalid data was written");
			}
		}
	}

	/**
	 * @see LabCatalogService#getAllLabTests(Boolean)
	 * @verifies get all LabTest by if includeRetired
	 */

	@Test
	public void getAllLabTests_shouldGetAllLabTestByIfIncludeRetired()
			throws Exception {
		LabTest labTest = Context.getService(LabCatalogService.class)
				.getLabTestByUUID("5d1545bb-486e-11e1-b5ed-0024e8c61285");
		Context.getService(LabCatalogService.class).retireLabTest(labTest,
				"testing retire");

		List<LabTest> list;

		list = Context.getService(LabCatalogService.class)
				.getAllLabTests(false);
		Assert.assertNotNull("getAllLabTests should not return null", list);
		Assert.assertEquals(
				"getAllLabTests (excluding retired) should return the right objects",
				23, list.size());

		list = Context.getService(LabCatalogService.class).getAllLabTests(true);
		Assert.assertNotNull("getAllLabTests should not return null", list);
		Assert.assertEquals(
				"getAllLabTests (including retired) should return the right objects",
				24, list.size());
	}

	/**
	 * @see LabCatalogService#retireLabTestPanel(LabTestPanel,String)
	 * @verifies retire LabTestPanel
	 */

	@Test
	public void retireLabTestPanel_shouldRetireLabTestPanel() throws Exception {
		LabTestPanel labTestPanel = Context.getService(LabCatalogService.class)
				.getLabTestPanelByUuid("05dec1ac-46d9-11e1-99f4-0024e8c61285");
		Context.getService(LabCatalogService.class).retireLabTestPanel(
				labTestPanel, "testing retire");

		LabTestPanel labTestPanelFromDB = Context.getService(
				LabCatalogService.class).getLabTestPanelByUuid(
				labTestPanel.getUuid());
		Assert.assertNotNull("LabTestPanel should still exist",
				labTestPanelFromDB);
		Assert.assertEquals("LabTestPanel should still exist", Boolean.TRUE,
				labTestPanelFromDB.isRetired());
		Assert.assertEquals("LabTestPanel should have retire reason",
				"testing retire", labTestPanelFromDB.getRetireReason());
		Assert.assertNotNull("LabTestPanel should have retire date",
				labTestPanelFromDB.getDateRetired());
	}

	/**
	 * @see LabCatalogService#getAllLabTestPanels()
	 * @verifies get all LabTestPanel
	 */

	@Test
	public void getAllLabTestPanels_shouldGetAllLabTestPanel() throws Exception {
		List<LabTestPanel> list = Context.getService(LabCatalogService.class)
				.getAllLabTestPanels(false);
		Assert.assertNotNull("getAllLabTestPanels should not return null", list);
		Assert.assertEquals(
				"getAllLabTestPanels should return the right objects", 5,
				list.size());
	}

	/**
	 * @see LabCatalogService#getAllLabTestPanels(Boolean)
	 * @verifies get all LabTestPanel by includeVoided
	 */

	@Test
	public void getAllLabTestPanels_shouldGetAllLabTestPanelByIncludeVoided()
			throws Exception {
		LabTestPanel labTestPanel = Context.getService(LabCatalogService.class)
				.getLabTestPanelByUuid("05de81e6-46d9-11e1-99f4-0024e8c61285");
		Context.getService(LabCatalogService.class).retireLabTestPanel(
				labTestPanel, "testing retire");

		List<LabTestPanel> list;

		list = Context.getService(LabCatalogService.class).getAllLabTestPanels(
				false);
		Assert.assertNotNull("getAllLabTestPanels should not return null", list);
		Assert.assertEquals(
				"getAllLabTestPanels should return the right objects", 4,
				list.size());

		list = Context.getService(LabCatalogService.class).getAllLabTestPanels(
				true);
		Assert.assertNotNull("getAllLabTestPanels should not return null", list);
		Assert.assertEquals(
				"getAllLabTestPanels should return the right objects", 5,
				list.size());
	}

	/**
	 * @see LabCatalogService#getLabTestPanels(String,Boolean,Integer,Integer)
	 * @verifies return LabTestPanel by String nameFragment, Boolean
	 *           includeVoided, Integer start, Integer length
	 */

	@Test
	public void getLabTestPanels_shouldReturnLabTestPanelByStringNameFragmentBooleanIncludeVoidedIntegerStartIntegerLength()
			throws Exception {
		List<LabTestPanel> list;

		// verify default case works like getAllLabTestPanels()
		list = Context.getService(LabCatalogService.class).getLabTestPanels("",
				Boolean.FALSE, 0, 4);
		Assert.assertNotNull("getAllLabTestPanels should not return null", list);
		Assert.assertEquals(
				"getAllLabTestPanels should return the right objects", 4,
				list.size());

		// verify get by nameFragment
		list = Context.getService(LabCatalogService.class).getLabTestPanels(
				"Hematology Panel", Boolean.FALSE, 0, 4);
		Assert.assertNotNull("getLabTestPanels should not return null", list);
		Assert.assertEquals(
				"getLabTestPanels should return the right object when using name fragment",
				"05de81e6-46d9-11e1-99f4-0024e8c61285", list.get(0).getUuid());

		// verify start and length restrictions
		list = Context.getService(LabCatalogService.class).getLabTestPanels("",
				Boolean.FALSE, 1, 2);
		Assert.assertNotNull("getLabTestPanels should not return null", list);
		Assert.assertEquals(
				"getLabTestPanels should return the right amount of objects",
				2, list.size());
		Assert.assertEquals("getLabTestPanels should return the right object",
				"05dec1ac-46d9-11e1-99f4-0024e8c61285", list.get(0).getUuid());
		Assert.assertEquals("getLabTestPanels should return the right object",
				"05df00e1-46d9-11e1-99f4-0024e8c61285", list.get(1).getUuid());
	}

	/**
	 * @see LabCatalogService#getCountOfLabTestPanels(Boolean)
	 * @verifies get number of LabTestPanel
	 */

	@Test
	public void getCountOfLabTestPanels_shouldGetNumberOfLabTestPanel()
			throws Exception {
		int count = Context.getService(LabCatalogService.class)
				.getCountOfLabTestPanels(false);
		Assert.assertNotNull("getCountOfLabTestPanels should not return null",
				count);
		Assert.assertEquals(
				"getCountOfLabTestPanels should return the right count", 5,
				count);
	}

	/**
	 * @see LabCatalogService#purgeLabTestPanel(LabTestPanel)
	 * @verifies delete given LabTestPanel
	 */

	@Test
	public void purgeLabTestPanel_shouldDeleteGivenLabTestPanel()
			throws Exception {
		String uuid = "05df00e1-46d9-11e1-99f4-0024e8c61285";

		// this uuid belongs to a TestPanel instance that does not have any
		// references linking to it, so it can be purged without problems
		String uuidTemp = "05df4577-46d9-11e1-99f4-0024e8c61285";

		LabTestPanel labTestPanel = Context.getService(LabCatalogService.class)
				.getLabTestPanelByUuid(uuidTemp);
		try {
			Context.getService(LabCatalogService.class).purgeLabTestPanel(
					labTestPanel);
			// Assert.fail("purgeLabTestPanel should fail to delete referenced testPanel");
		} catch (APIException e) {
			// TODO delete any references to the labTestPanel
			Context.getService(LabCatalogService.class).purgeLabTestPanel(
					labTestPanel);

			labTestPanel = Context.getService(LabCatalogService.class)
					.getLabTestPanelByUuid(uuid);
			Assert.assertNull("purgeLabTestPanel should return null",
					labTestPanel);
		}
	}

	/**
	 * @see LabCatalogService#getCountOfLabTest(Boolean)
	 * @verifies get number of LabTest
	 */
	@Test
	public void getCountOfLabTest_shouldGetNumberOfLabTestByVoided() throws Exception {

		int count = Context.getService(LabCatalogService.class).getCountOfLabTest(false);
		Assert.assertEquals("getCountOfLabTest(false) should return all non-retired LabTests", 24, count);
		
		List<LabTest> labTests = Context.getService(LabCatalogService.class).getAllLabTests(false);
		labTests.get(0).setRetired(true);
		labTests.get(1).setRetired(true);
		Context.getService(LabCatalogService.class).saveLabTest(labTests.get(0));
		Context.getService(LabCatalogService.class).saveLabTest(labTests.get(1));
		
		count = Context.getService(LabCatalogService.class).getCountOfLabTest(false);
		Assert.assertEquals("getCountOfLabTest(false) should return all non-retired LabTests after retiring some LabTests", 22, count);

		count = Context.getService(LabCatalogService.class).getCountOfLabTest(false);
		Assert.assertEquals("getCountOfLabTest(true) should return all LabTests including retired ones", 24, count);
		
	}

	/**
	 * @see LabCatalogService#getLabTestPanelsByLocation(Location,Boolean,Integer,Integer)
	 * @verifies return LabTestPanel by Location location, Boolean includeVoided, Integer start, Integer length
	 */
	@Test
	public void getLabTestPanelsByLocation_shouldReturnLabTestPanelByLocationLocationBooleanIncludeVoidedIntegerStartIntegerLength()
			throws Exception {
		
		Location jsslab = Context.getLocationService().getLocation(33333005);
		Location extlab = Context.getLocationService().getLocation(33333011);
		
		List<LabTestPanel> testPanels = Context.getService(LabCatalogService.class).getLabTestPanelsByLocation(jsslab, true, 0, -1);
		Assert.assertEquals(3, testPanels.size());
		
		testPanels = Context.getService(LabCatalogService.class).getLabTestPanelsByLocation(extlab, true, 0, -1);
		Assert.assertEquals(2, testPanels.size());
		
	}

}
