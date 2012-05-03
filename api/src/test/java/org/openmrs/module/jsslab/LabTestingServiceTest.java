
package org.openmrs.module.jsslab;
/**
 * LabTestingServiceTest.java
 * Tests LabTestingService class 
 */
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.db.LabReport;
import org.openmrs.module.jsslab.db.LabTestRange;
import org.openmrs.module.jsslab.db.LabTestResult;
import org.openmrs.module.jsslab.db.LabTestSpecimen;
import org.openmrs.test.BaseModuleContextSensitiveTest;
import org.openmrs.test.SkipBaseSetup;

public class LabTestingServiceTest extends BaseModuleContextSensitiveTest{	
	
	@Before
	public void before() throws Exception {
		initializeInMemoryDatabase();
		executeDataSet("jsslab_data_delta.xml");
		authenticate();
	}
	
	
	
	/**
	 * @see LabTestSpecimen#getLabTestRange(Integer)
	 * @verifies getLabTestRange by labTestRangeId
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabTestRange_shouldGetLabTestRange() throws Exception{
		LabTestRange labTestRange = Context.getService(LabTestingService.class).getLabTestRange(3333302);
		Assert.assertNotNull("getLabTestRange by labTestRangeId should not return null",labTestRange);
		Assert.assertEquals("getLabTestRange by labTestRangeId return the right object",new Integer(3333302), labTestRange.getId());
	}
	
	
	/**
	 * @see LabTestSpecimen#getLabTestRange(String)
	 * @verifies getLabTestRange by uuid
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabTestRangeByUuid_shouldGetLabTestRangeByUuid() throws Exception{
		LabTestRange labTestRange = Context.getService(LabTestingService.class).getLabTestRangeByUuid("cfe2da74-8887-11e1-b45c-0024e8c61285");
		Assert.assertNotNull("getLabTestRange by uuid should not return null",labTestRange);
		Assert.assertEquals("getLabTestRange by uuid return the right object","cfe2da74-8887-11e1-b45c-0024e8c61285", labTestRange.getUuid());
	}
	
	
	
	/**
	 * @see LabTestSpecimen#deleteLabTestRange(LabTestRange)
	 * @verifies deleteLabTestRange
	 */
	
	@Test
	@SkipBaseSetup
	public void deleteLabTestRange_shouldDeleteLabTestRange() throws Exception{
		LabTestRange labTestRange = Context.getService(LabTestingService.class).getLabTestRangeByUuid("cfe2da74-8887-11e1-b45c-0024e8c61285");
		Context.getService(LabTestingService.class).deleteLabTestRange(labTestRange, "");
		labTestRange = Context.getService(LabTestingService.class).getLabTestRangeByUuid("cfe2da74-8887-11e1-b45c-0024e8c61285");
		Assert.assertNull("deleteLabTestRange should return null",labTestRange);
	}
	
	
	/**
	 * @see LabTestSpecimen#getAllLabTestRanges(Boolean)
	 * @verifies getAllLabTestRanges
	 */
	
	@Test
	@SkipBaseSetup
	public void getAllLabTestRanges_shouldGetAllLabTestRanges() throws Exception{
		List<LabTestRange> list= Context.getService(LabTestingService.class).getAllLabTestRanges(false);		
		Assert.assertNotNull("getAllLabTestRanges should not return null list",list);
		Assert.assertEquals("getAllLabTestRanges the right number of LabTestRanges",2,list.size());
	}
	
	/**
	 * @see LabTestSpecimen#getCountOfLabTestRanges(Boolean)
	 * @verifies getCountOfLabTestRanges
	 */
	
	@Test
	@SkipBaseSetup
	public void getCountOfLabTestRanges_shouldGetCountOfLabTestRanges() throws Exception{
		Integer count= Context.getService(LabTestingService.class).getCountOfLabTestRanges(false);	
		Assert.assertEquals("getCountOfLabTestRanges should return right size",2,count.intValue());
	}
	
	/**
	 * @see LabTestSpecimen#getLabTestRanges(String,Boolean,Integer,Integer)
	 * @verifies getLabTestRanges
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabTestRanges_shouldGetLabTestRanges() throws Exception{
		List<LabTestRange> list= Context.getService(LabTestingService.class).getLabTestRanges("", false, 0, 2);		
		Assert.assertNotNull("getLabTestRanges should not return null list",list);
		Assert.assertEquals("getLabTestRanges should return right size",2,list.size());
	}
	
	
	/**
	 * @see LabTestSpecimen#purgeLabTestRange(LabTestRange)
	 * @verifies purgeLabTestRange
	 */
	
	@Test
	@SkipBaseSetup
	public void purgeLabTestRange_shouldPurgeLabTestRange() throws Exception{
		LabTestRange labTestRange = Context.getService(LabTestingService.class).getLabTestRangeByUuid("cfe2da74-8887-11e1-b45c-0024e8c61285");
		Context.getService(LabTestingService.class).purgeLabTestRange(labTestRange);
		labTestRange = Context.getService(LabTestingService.class).getLabTestRangeByUuid("cfe2da74-8887-11e1-b45c-0024e8c61285");
		Assert.assertNull("purgeLabTestRange should return null",labTestRange);
	}
	
	/**
	 * @see LabTestSpecimen#saveLabTestRange(LabTestRange)
	 * @verifies saveLabTestRange
	 */
	
	@Test
	@SkipBaseSetup
	public void saveLabTestRange_shouldSaveLabTestRange() throws Exception{
		LabTestRange labTestRange = Context.getService(LabTestingService.class).getLabTestRangeByUuid("cfe2da74-8887-11e1-b45c-0024e8c61285");
		labTestRange.setUuid("12312312-1231-1231-1231-123123124");
		Context.getService(LabTestingService.class).saveLabTestRange(labTestRange);
		labTestRange = Context.getService(LabTestingService.class).getLabTestRangeByUuid("12312312-1231-1231-1231-123123124");
		Assert.assertNotNull("saveLabTestRange should not return null",labTestRange);
		Assert.assertEquals("saveLabTestRange return the right object","12312312-1231-1231-1231-123123124", labTestRange.getId());
	}
	
	
	
	
	/**
	 * @see LabTestSpecimen#getLabTestSpecimen(Integer)
	 * @verifies get by specimenId
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabTestSpecimen_shouldGetBySpecimenId() throws Exception{
		LabTestSpecimen labTestSpecimen = Context.getService(LabTestingService.class).getLabTestSpecimen(33333001);
		Assert.assertNotNull("getLabTestSpecimen by specimenId should not return null",labTestSpecimen);
		Assert.assertEquals("getLabTestSpecimen by specimenId return the right object",new Integer(33333001), labTestSpecimen.getId());
	}
	
	/**
	 * @see LabTestSpecimen#getLabTestSpecimenByUuid(String)
	 * @verifies get by uuid
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabTestSpecimenByUuid_shouldGetByUuid() throws Exception {
		LabTestSpecimen labTestSpecimen = Context.getService(LabTestingService.class).getLabTestSpecimenByUuid("df4988f9-49bf-11e1-812a-0024e8c61285");
		Assert.assertNotNull("getLabTestSpecimenByUuid should not return null",labTestSpecimen);
		Assert.assertEquals("getLabTestSpecimenByUuid should return the right object","df4988f9-49bf-11e1-812a-0024e8c61285", labTestSpecimen.getUuid());
	}
	
	/**
	 * @see LabTestSpecimen#saveLabTestSpecimen(LabTestSpecimen)
	 * @verifies saveLabTestSpecimen
	 */
	
	@Test
	@SkipBaseSetup
	public void saveLabTestSpecimen_shouldSaveLabTestSpecimen() throws Exception {

		LabTestSpecimen labTestSpecimen = Context.getService(LabTestingService.class).getLabTestSpecimenByUuid("df4988f9-49bf-11e1-812a-0024e8c61285");
		labTestSpecimen.setUuid("12312312-1231-1231-1231-123123124");
		Context.getService(LabTestingService.class).saveLabTestSpecimen(labTestSpecimen);
		labTestSpecimen = Context.getService(LabTestingService.class).getLabTestSpecimenByUuid("12312312-1231-1231-1231-123123124");
		Assert.assertNotNull("saveLabTestSpecimen should not return null",labTestSpecimen);
		Assert.assertEquals("saveLabTestSpecimen should return the right object","12312312-1231-1231-1231-123123124", labTestSpecimen.getUuid());
	}
	
	/**
	 * @see LabTestSpecimen#purgeLabTestSpecimen(LabTestSpecimen)
	 * @verifies  purgeLabTestSpecimen
	 */
	
	@Test
	@SkipBaseSetup
	public void purgeLabTestSpecimen_shouldPurgeLabTestSpecimen() throws Exception {

		LabTestSpecimen labTestSpecimen = Context.getService(LabTestingService.class).getLabTestSpecimenByUuid("df4988f9-49bf-11e1-812a-0024e8c61285");		
		Context.getService(LabTestingService.class).purgeLabTestSpecimen(labTestSpecimen);
		labTestSpecimen = Context.getService(LabTestingService.class).getLabTestSpecimenByUuid("df4988f9-49bf-11e1-812a-0024e8c61285");
		Assert.assertNull("purgeLabTestSpecimen should return null",labTestSpecimen);
		
	}
	
	/**
	 * @see LabTestSpecimen#retireLabTestSpecimen(LabTestSpecimen,String)
	 * @verifies  retireLabTestSpecimen
	 */
	
	@Test
	@SkipBaseSetup
	public void retireLabTestSpecimen_shouldRetireLabTestSpecimen() throws Exception {

		LabTestSpecimen labTestSpecimen = Context.getService(LabTestingService.class).getLabTestSpecimenByUuid("df4988f9-49bf-11e1-812a-0024e8c61285");		
		Context.getService(LabTestingService.class).retireLabTestSpecimen(labTestSpecimen,"");
		labTestSpecimen = Context.getService(LabTestingService.class).getLabTestSpecimenByUuid("df4988f9-49bf-11e1-812a-0024e8c61285");
		Assert.assertEquals("retireLabTestSpecimen should return right object",Boolean.TRUE,labTestSpecimen.getRetired());
		
	}
	
	/**
	 * @see LabTestSpecimen#getAllLabTestSpecimens(false)
	 * @verifies  getAllLabTestSpecimens
	 */
	
	@Test
	@SkipBaseSetup
	public void getAllLabTestSpecimens_shouldGetAllLabTestSpecimensWithoutVoided() throws Exception {
		List<LabTestSpecimen> list=	Context.getService(LabTestingService.class).getAllLabTestSpecimens(false);	
		Assert.assertNotNull("getAllLabTestSpecimens should not return null",list);
		Assert.assertEquals("getAllLabTestSpecimens should return the right number of objects",1, list.size());	
		
	}
	
	/**
	 * @see LabTestSpecimen#getAllLabTestSpecimens()
	 * @verifies  getAllLabTestSpecimens
	 */
	
	@Test
	@SkipBaseSetup
	public void getAllLabTestSpecimens_shouldGetAllLabTestSpecimens() throws Exception {
		List<LabTestSpecimen> list=	Context.getService(LabTestingService.class).getAllLabTestSpecimens();	
		Assert.assertNotNull("getAllLabTestSpecimens should not return null",list);
		Assert.assertEquals("getAllLabTestSpecimens should return the right objects",1, list.size());	
		
	}
	
	/**
	 * @see LabTestSpecimen#getLabTestSpecimens(String,Boolean,Integer,Integer)
	 * @verifies  getLabTestSpecimens
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabTestSpecimens_shouldGetLabTestSpecimens() throws Exception {
		List<LabTestSpecimen> list=	Context.getService(LabTestingService.class).getLabTestSpecimens("", true, 0, 1);	
		Assert.assertNotNull("getLabTestSpecimens should not return null",list);
		Assert.assertEquals("getLabTestSpecimens should return the right objects",1, list.size());	
		
	}
	
	/**
	 * @see LabTestSpecimen#getCountOfLabTestSpecimens(Boolean)
	 * @verifies  getCountOfLabTestSpecimens
	 */
	
	@Test
	@SkipBaseSetup
	public void getCountOfLabTestSpecimens_shouldGetCountOfLabTestSpecimens() throws Exception {
		Integer count=	Context.getService(LabTestingService.class).getCountOfLabReports(true);	
		Assert.assertEquals("getCountOfLabTestSpecimens should return the right number objects",1, count.intValue());	
		
	}
	
	
	/**
	 * @see LabTestSpecimen#getLabReport(Integer)
	 * @verifies  getLabReport
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabReport_shouldGetLabReportById() throws Exception {
		LabReport labReport=	Context.getService(LabTestingService.class).getLabReport(33333001);	
		Assert.assertNotNull("getLabReport should not return null",labReport);
		Assert.assertEquals("getLabReport should return the right report",new Integer(33333001), labReport.getId());	
		
	}
	/**
	 * @see LabTestSpecimen#getLabReport(String)
	 * @verifies  getLabReport
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabReportByUuid_shouldGetLabReportByUuid() throws Exception {
		LabReport labReport=	Context.getService(LabTestingService.class).getLabReportByUuid("ac0e7819-49bb-11e1-812a-0024e8c61285");	
		Assert.assertNotNull("getLabReportByUuid should not return null",labReport);
		Assert.assertEquals("getLabReportByUuid should return the right report","ac0e7819-49bb-11e1-812a-0024e8c61285", labReport.getUuid());	
		
	}
	
	/**
	 * @see LabTestSpecimen#saveLabReport(LabReport)
	 * @verifies  saveLabReport
	 */
	
	@Test
	@SkipBaseSetup
	public void saveLabReport_shouldSaveLabReport() throws Exception {
		LabReport labReport=	Context.getService(LabTestingService.class).getLabReportByUuid("ac0e7819-49bb-11e1-812a-0024e8c61285");	
		labReport.setUuid("12312312-1231-1231-1231-123123124");
		Context.getService(LabTestingService.class).saveLabReport(labReport);
		labReport=	Context.getService(LabTestingService.class).getLabReportByUuid("12312312-1231-1231-1231-123123124");	
		Assert.assertNotNull("getLabReport should not return null",labReport);
		Assert.assertEquals("getLabReport should return the right report","12312312-1231-1231-1231-123123124", labReport.getUuid());	
		
	}
	
	
	/**
	 * @see LabTestSpecimen#purgeLabReport(LabReport)
	 * @verifies purgeLabReport
	 */
	
	@Test
	@SkipBaseSetup
	public void purgeLabReport_shouldPurgeLabReport() throws Exception {

		LabReport labReport = Context.getService(LabTestingService.class).getLabReportByUuid("ac0e7819-49bb-11e1-812a-0024e8c61285");		
		Context.getService(LabTestingService.class).purgeLabReport(labReport);
		labReport = Context.getService(LabTestingService.class).getLabReportByUuid("ac0e7819-49bb-11e1-812a-0024e8c61285");
		Assert.assertNull("purgeLabReport should return null",labReport);
		
	}
	/**
	 * @see LabTestSpecimen#retireLabReport(LabReport,String)
	 * @verifies retireLabReport
	 */
	
	@Test
	@SkipBaseSetup
	public void retireLabReport_shouldRetireLabReport() throws Exception {

		LabReport labReport = Context.getService(LabTestingService.class).getLabReportByUuid("ac0e7819-49bb-11e1-812a-0024e8c61285");		
		Context.getService(LabTestingService.class).retireLabReport(labReport,"");
		labReport = Context.getService(LabTestingService.class).getLabReportByUuid("ac0e7819-49bb-11e1-812a-0024e8c61285");
		Assert.assertNotNull("getLabReport should not return null",labReport);
		Assert.assertEquals("getLabReport should return the right object",Boolean.TRUE, labReport.getRetired());	
		
	}
	
	
	/**
	 * @see LabTestSpecimen# getAllLabReports(Boolean)
	 * @verifies  getAllLabReports
	 */
	
	@Test
	@SkipBaseSetup
	public void getAllLabReports_shouldGetAllLabReportsWithoutVoided() throws Exception {
		List<LabReport> list=	Context.getService(LabTestingService.class).getAllLabReports(false);	
		Assert.assertNotNull("getAllLabReports should not return null",list);
		Assert.assertEquals("getAllLabReports should return the right objects",1, list.size());	
		
	}
	
	/**
	 * @see LabTestSpecimen# getAllLabReports()
	 * @verifies  getAllLabReports
	 */
	
	@Test
	@SkipBaseSetup
	public void getAllLabReports_shouldGetAllLabReports() throws Exception {
		List<LabReport> list=	Context.getService(LabTestingService.class).getAllLabReports();	
		Assert.assertNotNull("getAllLabReports should not return null",list);
		Assert.assertEquals("getAllLabReports should return the right objects",1, list.size());	
		
	}
	
	
	/**
	 * @see LabTestSpecimen#getLabReports(String,Boolean,Integer,Integer)
	 * @verifies  getLabReports
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabReports_shouldGetLabReports() throws Exception {
		List<LabReport> list=	Context.getService(LabTestingService.class).getLabReports("",false,0, 1);	
		Assert.assertNotNull("getLabReports should not return null",list);
		Assert.assertEquals("getLabReports should return the right objects",1, list.size());	
		
	}
	

	/**
	 * @see LabTestSpecimen#getCountOfLabReports(Boolean)
	 * @verifies  getCountOfLabReports
	 */
	
	@Test
	@SkipBaseSetup
	public void getCountOfLabReports_shouldGetCountOfLabReports() throws Exception {
		Integer count=	Context.getService(LabTestingService.class).getCountOfLabReports(false);	
		Assert.assertNotNull("getCountOfLabReports should not return null",count);
		Assert.assertEquals("getCountOfLabReports should return the right objects",1, count.intValue());	
		
	}
	
	
	/**
	 * @see LabTestSpecimen#getLabTestResult(Integer)
	 * @verifies  getLabTestResult
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabTestResult_shouldGetLabTestResultById() throws Exception {
		LabTestResult labTestResult=	Context.getService(LabTestingService.class).getLabTestResult(33333001);	
		Assert.assertNotNull("getLabTestResult should not return null",labTestResult);
		Assert.assertEquals("getLabTestResult should return the right objects",new Integer(33333001), labTestResult.getId());	
		
	}
	
	/**
	 * @see LabTestSpecimen#getLabTestResult(String)
	 * @verifies  getLabTestResult
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabTestResult_shouldGetLabTestResultByUuId() throws Exception {
		LabTestResult labTestResult=	Context.getService(LabTestingService.class).getLabTestResultByUuid("ace6f816-49c1-11e1-812a-0024e8c61285");	
		Assert.assertNotNull("getLabTestResult should not return null",labTestResult);
		Assert.assertEquals("getLabTestResult should return the right objects","ace6f816-49c1-11e1-812a-0024e8c61285", labTestResult.getUuid());	
		
	}
	
	/**
	 * @see LabTestSpecimen#saveLabTestResult(LabTestResult)
	 * @verifies  saveLabTestResult
	 */
	
	@Test
	@SkipBaseSetup
	public void saveLabTestResult_shouldSaveLabTestResult() throws Exception {
		LabTestResult labTestResult=	Context.getService(LabTestingService.class).getLabTestResultByUuid("ace6f816-49c1-11e1-812a-0024e8c61285");	
		labTestResult.setUuid("vn123mmm-49c1-11e1-812a-0024e8c61285");
		Context.getService(LabTestingService.class).saveLabTestResult(labTestResult);
		labTestResult=	Context.getService(LabTestingService.class).getLabTestResultByUuid("vn123mmm-49c1-11e1-812a-0024e8c61285");	
		Assert.assertNotNull("saveLabTestResult should not return null",labTestResult);
		Assert.assertEquals("saveLabTestResult should return the right objects","vn123mmm-49c1-11e1-812a-0024e8c61285", labTestResult.getUuid());	
		
	}
	
	/**
	 * @see LabTestSpecimen#voidLabTestResult(LabTestResult,String)
	 * @verifies  voidLabTestResult
	 */
	
	@Test
	@SkipBaseSetup
	public void voidLabTestResult_shouldVoidLabTestResult() throws Exception {
		LabTestResult labTestResult=	Context.getService(LabTestingService.class).getLabTestResultByUuid("ace6f816-49c1-11e1-812a-0024e8c61285");	
		Context.getService(LabTestingService.class).voidLabTestResult(labTestResult,"");
		labTestResult=	Context.getService(LabTestingService.class).getLabTestResultByUuid("ace6f816-49c1-11e1-812a-0024e8c61285");	
		Assert.assertNotNull("voidLabTestResult should not return null",labTestResult);
		Assert.assertEquals("voidLabTestResult should return the right objects",Boolean.TRUE, labTestResult.getVoided());	
		
	}
	
	
	/**
	 * @see LabTestSpecimen#purgeLabTestResult(LabTestResult)
	 * @verifies  purgeLabTestResult
	 */
	
	@Test
	@SkipBaseSetup
	public void purgeLabTestResult_shouldPurgeLabTestResult() throws Exception {
		LabTestResult labTestResult=	Context.getService(LabTestingService.class).getLabTestResultByUuid("ace6f816-49c1-11e1-812a-0024e8c61285");	
		Context.getService(LabTestingService.class).purgeLabTestResult(labTestResult);
		labTestResult=	Context.getService(LabTestingService.class).getLabTestResultByUuid("ace6f816-49c1-11e1-812a-0024e8c61285");	
		Assert.assertNull("purgeLabTestResult should not return null",labTestResult);
		
	}
	
	
	/**
	 * @see LabTestSpecimen#getAllLabTestResults(Boolean)
	 * @verifies getAllLabTestResults
	 */
	
	@Test
	@SkipBaseSetup
	public void getAllLabTestResults_shouldGetAllLabTestResults() throws Exception {
		List<LabTestResult> list=	Context.getService(LabTestingService.class).getAllLabTestResults(true);
		Assert.assertNotNull("getAllLabTestResults should not return null",list);
		Assert.assertEquals("getAllLabTestResults should return the right objects",2, list.size());
		
	}
	
	/**
	 * @see LabTestSpecimen#getLabTestResults(String,Boolean,Integer,Integer)
	 * @verifies getLabTestResults
	 */
	
	@Test
	@SkipBaseSetup
	public void getLabTestResults_shouldGetLabTestResults() throws Exception {
		List<LabTestResult> list=	Context.getService(LabTestingService.class).getLabTestResults("", true, 0, 2);
		Assert.assertNotNull("getLabTestResults should not return null",list);
		Assert.assertEquals("getLabTestResults should return the right objects",2, list.size());
		
	}
	
	
	/**
	 * @see LabTestSpecimen#getCountOfLabTestResult(String,Boolean)
	 * @verifies getCountOfLabTestResult
	 */
	
	@Test
	@SkipBaseSetup
	public void getCountOfLabTestResult_shouldGetCountOfLabTestResult() throws Exception {
		Integer count=	Context.getService(LabTestingService.class).getCountOfLabTestResult("",false);
		Assert.assertNotNull("getCountOfLabTestResult should not return null",count);
		Assert.assertEquals("getCountOfLabTestResult should return the right objects",2, count.intValue());
		
	}
	
	
	
}

