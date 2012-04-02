package org.openmrs.module.jsslab;

import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.db.LabPrecondition;
import org.openmrs.module.jsslab.db.LabSpecimenTemplate;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;
import org.openmrs.module.jsslab.LabCatalogService;

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
		Assert.assertEquals("getLabPreconditionByUuid should return the right object","0100dc0a-46da-11e1-99f4-0024e8c61285", labPrecondition.getUuid());
	}
	
	/**
	 * @see LabCatalogService#saveLabPrecondition(LabPrecondition labLabPrecondition)
	 * @verifies save LabPrecondition
	 **/
	@Test
	@SkipBaseSetup
	public void saveLabPrecondition_shouldSaveRecord()throws Exception
	{
		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		LabPrecondition labPrecondition = Context.getService(LabCatalogService.class).getLabPreconditionByUuid("0100dc0a-46da-11e1-99f4-0024e8c61285");
		labPrecondition.setUuid("12312312-1231-1231-1231-123123123");
		Context.getService(LabCatalogService.class).saveLabPrecondition(labPrecondition);
		labPrecondition = Context.getService(LabCatalogService.class).getLabPreconditionByUuid("12312312-1231-1231-1231-123123123");
		Assert.assertNotNull("saveLabPrecondition should not return null",labPrecondition);
		Assert.assertEquals("saveLabPrecondition should return the right object","12312312-1231-1231-1231-123123123", labPrecondition.getUuid());
	}
	
	/**
	 * @see LabCatalogService#getCountOfLabPrecondition(String search,Boolean ifVoided)
	 * @verifies get count of LabPrecondition
	 */
	
	@Test
	@SkipBaseSetup
	public void getCountOfLabPrecondition_shouldGetCountOfLabPrecondition() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		Integer count = Context.getService(LabCatalogService.class).getCountOfLabPrecondition("", false);
		Assert.assertNotNull("getCountOfLabPrecondition should not return null",count);
		Assert.assertEquals("getCountOfLabPrecondition should return the right number","1", count.toString());
	}
	
	/**
	 * @see LabCatalogService#getAllLabPreconditions(Boolean ifVoided)
	 * @verifies get all LabPreconditions
	 */
	
	@Test
	@SkipBaseSetup
	public void getAllLabPreconditions_shouldGetAllLabPreconditions() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		List<LabPrecondition> list= Context.getService(LabCatalogService.class).getAllLabPreconditions(false);
		Assert.assertNotNull("getAllLabPreconditions should not return null",list);
		Assert.assertEquals("getAllLabPreconditions should return the right objects",1, list.size());
	}
	
	/**
	 * @see LabCatalogService#getLabPreconditions(String displayFragment, Boolean ifVoided, Integer index, Integer length)
	 * @verifies get specific LabPreconditions
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabPreconditions_shouldGetLabPreconditions() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		List<LabPrecondition> list= Context.getService(LabCatalogService.class).getLabPreconditions("", false, 0, 0);
		Assert.assertNotNull("getLabPreconditions should not return null",list);
		Assert.assertEquals("getLabPreconditions should return the right objects",1, list.size());
	}

	/**
	 * @see voidLabPrecondition(LabPrecondition labLabPrecondition, String reason)
	 * @verifies void LabPrecondition
	 */
	
	@Test
	@SkipBaseSetup
	public void voidLabPrecondition_shouldVoidedLabPrecondition() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		LabPrecondition labPrecondition = Context.getService(LabCatalogService.class).getLabPreconditionByUuid("0100dc0a-46da-11e1-99f4-0024e8c61285");
		Context.getService(LabCatalogService.class).voidLabPrecondition(labPrecondition, "");
		labPrecondition = Context.getService(LabCatalogService.class).getLabPreconditionByUuid("0100dc0a-46da-11e1-99f4-0024e8c61285");
		Assert.assertNotNull("voidLabPrecondition should not return null",labPrecondition);
		Assert.assertEquals("voidLabPrecondition should return the right object with voided true","true", labPrecondition.getVoided().toString());
	}
	
	/**
	 * @see purgeLabPrecondition(LabPrecondition labLabPrecondition)
	 * @verifies purge LabPrecondition
	 */
	
	@Test
	@SkipBaseSetup
	public void purgeLabPrecondition_shouldPurgeLabPrecondition() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		LabPrecondition labPrecondition = Context.getService(LabCatalogService.class).getLabPreconditionByUuid("0100dc0a-46da-11e1-99f4-0024e8c61285");
		Context.getService(LabCatalogService.class).purgeLabPrecondition(labPrecondition);
		labPrecondition = Context.getService(LabCatalogService.class).getLabPreconditionByUuid("0100dc0a-46da-11e1-99f4-0024e8c61285");
		Assert.assertNull("purgeLabPrecondition should return null",labPrecondition);
	}
	
	/**
	 * @see getLabPrecondition(Integer labPreconditionId)
	 * @verifies get LabPrecondition by LabPreconditionId
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabPrecondition_shouldGetLabPrecondition() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		LabPrecondition labPrecondition = Context.getService(LabCatalogService.class).getLabPrecondition(33333067);
		Assert.assertNotNull("getLabPreconditionByUuid should not return null",labPrecondition);
		Assert.assertEquals("getLabPreconditionByUuid should return the right object","0100dc0a-46da-11e1-99f4-0024e8c61285", labPrecondition.getUuid());
	}
	
	/**
	 * @see LabCatalogService#getLabSpecimenTemplateByUuid(String uuid)
	 * @verifies get by uuid
	 */
	@Test
	@SkipBaseSetup
	public void getLabSpecimenTemplateByUuid_shouldGetByUuid() throws Exception {
	
		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		LabSpecimenTemplate labSpecimenTemplate = Context.getService(LabCatalogService.class).getLabSpecimenTemplateByUuid("35442792-49b7-11e1-812a-0024e8c61285");
		Assert.assertNotNull("getLabSpecimenTemplateByUuid should not return null",labSpecimenTemplate);
		Assert.assertEquals("getLabSpecimenTemplateByUuid should return the right object","35442792-49b7-11e1-812a-0024e8c61285", labSpecimenTemplate.getUuid());
	}

	/**
	 * @see LabCatalogService#saveLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate)
	 * @verifies save LabSpecimenTemplate
	 **/
	@Test
	@SkipBaseSetup
	public void saveLabSpecimenTemplate_shouldSaveRecord()throws Exception
	{
		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		LabSpecimenTemplate labSpecimenTemplate = Context.getService(LabCatalogService.class).getLabSpecimenTemplateByUuid("35442792-49b7-11e1-812a-0024e8c61285");
		labSpecimenTemplate.setUuid("12312312-1231-1231-1231-123123123");
		Context.getService(LabCatalogService.class).saveLabSpecimenTemplate(labSpecimenTemplate);
		labSpecimenTemplate = Context.getService(LabCatalogService.class).getLabSpecimenTemplateByUuid("12312312-1231-1231-1231-123123123");
		Assert.assertNotNull("getLabPreconditionByUuid should not return null",labSpecimenTemplate);
		Assert.assertEquals("getLabPreconditionByUuid should return the right object","12312312-1231-1231-1231-123123123", labSpecimenTemplate.getUuid());
	}
	
	/**
	 * @see LabCatalogService#getCountOfLabSpecimenTemplate(String search,Boolean ifVoided)
	 * @verifies get count of LabSpecimenTemplate
	 */
	
	@Test
	@SkipBaseSetup
	public void getCountOfLabSpecimenTemplate_shouldGetCountOfLabSpecimenTemplate() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		Integer count = Context.getService(LabCatalogService.class).getCountOfLabSpecimenTemplate("", false);
		Assert.assertNotNull("getCountOfLabSpecimenTemplate should not return null",count);
		Assert.assertEquals("ggetCountOfLabSpecimenTemplate should return the right number","8", count.toString());
	}
	
	/**
	 * @see LabCatalogService#getAllLabSpecimenTemplates(Boolean ifVoided)
	 * @verifies get all LabSpecimenTemplates
	 */
	
	@Test
	@SkipBaseSetup
	public void getAllLabSpecimenTemplates_shouldGetAllLabSpecimenTemplates() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		List<LabSpecimenTemplate> list= Context.getService(LabCatalogService.class).getAllLabSpecimenTemplates(false);
		Assert.assertNotNull("getAllLabSpecimenTemplates should not return null",list);
		Assert.assertEquals("getAllLabSpecimenTemplates should return the right objects",8, list.size());
	}
	
	/**
	 * @see LabCatalogService#getLabSpecimenTemplate(String displayFragment, Boolean ifVoided, Integer index, Integer length)
	 * @verifies get specific LabSpecimenTemplate
	 */
	
	@Test
	@SkipBaseSetup
	public void LabSpecimenTemplate_shouldGetLabSpecimenTemplate() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		List<LabSpecimenTemplate> list= Context.getService(LabCatalogService.class).getLabSpecimenTemplate("", false, 0, 0);
		Assert.assertNotNull("getLabSpecimenTemplate should not return null",list);
		Assert.assertEquals("getLabSpecimenTemplate should return the right objects",8, list.size());
	}

	/**
	 * @see retireLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate, String reason)
	 * @verifies retire LabSpecimenTemplate
	 */
	
	@Test
	@SkipBaseSetup
	public void retireLabSpecimenTemplate_shouldRetireLabSpecimenTemplate() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		LabSpecimenTemplate labSpecimenTemplate = Context.getService(LabCatalogService.class).getLabSpecimenTemplateByUuid("35442792-49b7-11e1-812a-0024e8c61285");
		Context.getService(LabCatalogService.class).retireLabSpecimenTemplate(labSpecimenTemplate, "");
		labSpecimenTemplate = Context.getService(LabCatalogService.class).getLabSpecimenTemplateByUuid("35442792-49b7-11e1-812a-0024e8c61285");
		Assert.assertNotNull("retireLabSpecimenTemplate should not return null",labSpecimenTemplate);
		Assert.assertEquals("retireLabSpecimenTemplate should return the right object with voided true","true", labSpecimenTemplate.getRetired().toString());
	}
	
	/**
	 * @see purgeLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate)
	 * @verifies purge LabSpecimenTemplate
	 */
	
	@Test
	@SkipBaseSetup
	public void purgeLabSpecimenTemplate_shouldPurgeLabSpecimenTemplate() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		LabSpecimenTemplate labSpecimenTemplate = Context.getService(LabCatalogService.class).getLabSpecimenTemplateByUuid("35442792-49b7-11e1-812a-0024e8c61285");
		Context.getService(LabCatalogService.class).purgeLabSpecimenTemplate(labSpecimenTemplate);
		labSpecimenTemplate = Context.getService(LabCatalogService.class).getLabSpecimenTemplateByUuid("35442792-49b7-11e1-812a-0024e8c61285");
		Assert.assertNull("purgeLabSpecimenTemplate should return null",labSpecimenTemplate);
	}
	
	/**
	 * @see getLabSpecimenTemplate(Integer labSpecimenTemplateId)
	 * @verifies get LabSpecimenTemplaten by LabSpecimenTemplateId
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabSpecimenTemplate_shouldGetLabSpecimenTemplate() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		LabSpecimenTemplate labSpecimenTemplate = Context.getService(LabCatalogService.class).getLabSpecimenTemplate(33333001);
		Assert.assertNotNull("getLabSpecimenTemplate should not return null",labSpecimenTemplate);
		Assert.assertEquals("getLabSpecimenTemplate should return the right object","35442792-49b7-11e1-812a-0024e8c61285", labSpecimenTemplate.getUuid());
	}
	
	/**
	 * @see getLabSpecimenTemplateByName(String labSpecimenTemplate)
	 * @verifies get LabSpecimenTemplate by getLabSpecimenTemplateName
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabSpecimenTemplateByName_shouldGetLabSpecimenTemplateByName() throws Exception {

		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
		//LabSpecimenTemplate labSpecimenTemplate = Context.getService(LabCatalogService.class).getLabSpecimenTemplateByName("123");
	}
}
