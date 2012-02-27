package org.openmrs.module.jsslab;

import java.util.List;

import org.openmrs.module.jsslab.db.LabPrecondition;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.jsslab.PrivilegeConstants;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

public interface LabCatalogService extends OpenmrsService {

	/**
	 * Save or update the given <code>LabTestPanel</code> in the database
	 * 
	 * @param labTestPanel the LabTestPanel to save
	 * @return the LabTestPanel that was saved
	 * @throws APIException
	 * @should not save LabTestPanel if LabTestPanel doesn't validate
	 */
	@Authorized( { PrivilegeConstants.EDIT_LAB_CAT, PrivilegeConstants.ADD_LAB_CAT })
	public LabTestPanel saveLabTestPanel(LabTestPanel labTestPanel) throws APIException;
	
	/**
	 * Get the <code>LabTestPanel</code> with the given uuid from the database
	 * 
	 * @param uuid the uuid to find
	 * @return the LabTestPanel that was found or null
	 */
	@Authorized( PrivilegeConstants.VIEW_LAB_CAT )
	public LabTestPanel getLabTestPanelByUuid(String uuid);
	
	/*
	 * 
	 */
	@Transactional(readOnly=false)
	@Authorized(PrivilegeConstants.DELETE_LAB_CAT)
	public void deleteLabTestPanel(LabTestPanel labTestPanel, String reason)throws APIException;
	
	/**
	 * Completely delete an LabTestPanel from the database. This should not typically be used unless
	 * desperately needed. Most LabTestPanels should just be retired. See {@link #retireLabTestPanel(LabTestPanel, String)}
	 * 
	 * @param labTestPanel The LabTestPanel to remove from the system
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.PURGE_LAB_CAT)
	public void purgeLabTestPanel(LabTestPanel labTestPanel) throws APIException;
	
	/**
	 * Mark an LabTestPanel as retired. This functionally removes the LabTestPanel from the system while keeping a
	 * semblance
	 * 
	 * @param retireReason String reason
	 * @param labTestPanel LabTestPanel to retire
	 * @return the LabTestPanel that was retired
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.DELETE_LAB_CAT)
	public LabTestPanel retireLabTestPanel(LabTestPanel labTestPanel, String retireReason) throws APIException;
	
	/**
	 * Get all LabTestPanel, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabTestPanels in this list
	 * @return LabTestPanels list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_CAT)
	public List<LabTestPanel> getAllLabTestPanels(Boolean includeRetired) throws APIException;
	
	/**
	 * Get the <code>LabTestPanel</code> with the given propertyTag or serialNumber
	 * 
	 * @param propertyTag or serialNumber to find
	 * @return the LabTestPanel that was found or null
	 */
	@Authorized( PrivilegeConstants.VIEW_LAB_CAT )
	public LabTestPanel getLabTestPanel(String idNumber);
	
	/**
	 * Save or update the given <code>LabTest</code> in the database
	 * 
	 * @param labTest the LabTest to save
	 * @return the LabTest that was saved
	 * @throws APIException
	 * @should not save LabTest if LabTest doesn't validate
	 */
	@Authorized( { PrivilegeConstants.EDIT_LAB_CAT, PrivilegeConstants.ADD_LAB_CAT })
	public LabTest saveLabTest(LabTest labTest) throws APIException;
	
	/**
	 * Get the <code>LabTest</code> with the given uuid from the database
	 * 
	 * @param uuid the uuid to find
	 * @return the LabTest that was found or null
	 */
	@Authorized( PrivilegeConstants.VIEW_LAB_CAT )
	public LabTest getLabTestByUUID(String uuid);
	
	/*
	 * 
	 */
	@Transactional(readOnly=false)
	@Authorized(PrivilegeConstants.DELETE_LAB_CAT)
	public void deleteLabTest(LabPrecondition labTest, String reason)throws APIException;
	
	/**
	 * Completely delete an LabTest from the database. This should not typically be used unless
	 * desperately needed. Most LabTests should just be retired. See {@link #retireLabTest(LabTest, String)}
	 * 
	 * @param labTest The LabTest to remove from the system
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.PURGE_LAB_CAT)
	public void purgeLabTest(LabTest labTest) throws APIException;
	
	/**
	 * Mark an LabTest as retired. This functionally removes the LabTest from the system while keeping a
	 * semblance
	 * 
	 * @param retireReason String reason
	 * @param labTest LabTest to retire
	 * @return the LabTest that was retired
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.DELETE_LAB_CAT)
	public LabTest retireLabTest(LabTest labTest, String retireReason) throws APIException;
	
	/**
	 * Get all LabTests, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabTests in this list
	 * @return LabTests list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_CAT)
	public List<LabTest> getAllLabTests(Boolean includeRetired) throws APIException;
	
	/*
	 * get the specific list of LabPrecondition from String and index.
	 */
	public List<LabPrecondition> getLabPrecondition(String displayFragment, Boolean ifVoided, Integer index, Integer length);
	/*
	 * @param uuid
	 * @return LabPrecondition if founded, or null.
	 */
	public LabPrecondition getLabPreconditionByUuid(String uuid);
	
	/*
	 * @return save LabPrecondition to database.
	 * @throws APIException
	 */
	@Transactional(readOnly=true)
	@Authorized(PrivilegeConstants.VIEW_LAB_CAT)
	public LabPrecondition saveLabPrecondition(LabPrecondition labPrecondition)throws APIException;
	
	/*
	 *  Remove the LabPrecondition from database, most of the time we just voided the LabPrecondition.
	 * @param the LabPrecondition that should be removed
	 * @throws APIException
	 */
	@Transactional(readOnly=false)
	@Authorized(PrivilegeConstants.PURGE_LAB_CAT)
	public void purgeLabPrecondition(LabPrecondition labPrecondition)throws APIException;
	
	/*
	 * 
	 */
	@Transactional(readOnly=false)
	@Authorized(PrivilegeConstants.DELETE_LAB_CAT)
	public LabPrecondition deleteLabPrecondition(LabPrecondition labPrecondition, String reason)throws APIException;
	
	/*
	 * Get all not voided LabPrecondition
	 * 
	 * @return LabPrecondition list
	 * @throws APIException
	 */
	@Transactional(readOnly=true)
	@Authorized(PrivilegeConstants.VIEW_LAB_CAT)
	public List<LabPrecondition> getAllLabPreconditions()throws APIException;
	
	/*
	 * Get all LabPrecondition list including voided if the ifVoided is true
	 * 
	 * @param if include voided LabPrecondition
	 * @return LabPrecondition list(include voided list if ifVoided is true)
	 * @throws APIException
	 */
	@Transactional(readOnly=true)
	@Authorized(PrivilegeConstants.VIEW_LAB_CAT)
	public List<LabPrecondition> getAllLabPreconditions(Boolean ifVoided)throws APIException;
	
	/*
	 * Get the length of the list that LabPrecondition return(include voided if ifVoided is true)
	 * @param search is a String that represent DisplayFragment
	 * @param if include voided resources
	 * @return length of LabPrecondition(include voided if ifVoided is true) resource
	 * throws Exception
	 */
	@Transactional(readOnly=true)
	@Authorized(PrivilegeConstants.VIEW_LAB_CAT)
	public Integer getCountOfLabPrecondition(String search,Boolean ifVoided)throws APIException;
	
	/*
	 * @param use preconditionID to search LabPrecondition
	 */
	@Transactional(readOnly=true)
	@Authorized(PrivilegeConstants.VIEW_LAB_CAT)
	public LabPrecondition getLabPrecondition(Integer preconditionId);
	
	/*
	 * @param use name to search LabPrecondition
	 */
	@Transactional(readOnly=true)
	@Authorized(PrivilegeConstants.VIEW_LAB_CAT)
	public LabPrecondition getLabPreconditionByName(String labPrecondition);
}
