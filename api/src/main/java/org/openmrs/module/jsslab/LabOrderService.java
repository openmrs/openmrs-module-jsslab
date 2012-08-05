package org.openmrs.module.jsslab;

import java.util.List;

import org.openmrs.Patient;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabOrderSpecimen;
import org.openmrs.module.jsslab.db.LabSpecimen;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LabOrderService extends OpenmrsService {

	/**
	 * Save or update the given <code>LabOrder</code> in the database
	 * 
	 * @param labOrder the LabOrder to save
	 * @return the LabOrder that was saved
	 * @throws APIException
	 * @should not save LabOrder if LabOrder doesn't validate
	 */
	//  @Authorized( { PrivilegeConstants.EDIT_LAB_ORDER, PrivilegeConstants.ADD_LAB_ORDER })
	public LabOrder saveLabOrder(LabOrder labOrder) throws APIException;
	
	/**
	 * Get the <code>LabOrder</code> with the given id from the database
	 * 
	 * @param id the id to find
	 * @return the LabOrder that was found or null
	 * @should get by id
	 */
	//  @Authorized( PrivilegeConstants.VIEW_LAB_ORDER )
	public LabOrder getLabOrder(Integer id);
	
	/**
	 * Get the <code>LabOrder</code> with the given uuid from the database
	 * 
	 * @param uuid the uuid to find
	 * @return the LabOrder that was found or null
	 * @should get by uuid
	 */
	//  @Authorized( PrivilegeConstants.VIEW_LAB_ORDER )
	public LabOrder getLabOrderByUuid(String uuid);
	
	/**
	 * Completely delete an LabOrder from the database. This should not typically be used unless
	 * desperately needed. Most LabOrders should just be retired. See {@link #retireLabOrder(LabOrder, String)}
	 * 
	 * @param labOrder The LabOrder to remove from the system
	 * @throws APIException
	 */
	//  @Authorized(PrivilegeConstants.PURGE_LAB_ORDER)
	public void purgeLabOrder(LabOrder labOrder) throws APIException;
	
	/**
	 * Mark an LabOrder as retired. This functionally removes the LabOrder from the system while keeping a
	 * semblance
	 * 
	 * @param retireReason String reason
	 * @param labOrder LabOrder to retire
	 * @return the LabOrder that was retired
	 * @throws APIException
	 */
	//  @Authorized(PrivilegeConstants.DELETE_LAB_ORDER)
	public LabOrder deleteLabOrder(LabOrder labOrder, String retireReason) throws APIException;
	
	/**
	 * Get all LabOrder, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabOrders in this list
	 * @return LabOrders list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public List<LabOrder> getAllLabOrders(Boolean includeRetired) throws APIException;
	
	/**
	 * Get all unretired LabOrder
	 * 
	 * @return LabOrders list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public List<LabOrder> getAllLabOrders() throws APIException;
	
	/**
	 * Get all unretired LabOrders for a specified patient
	 * 
	 * @return LabOrders list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public List<LabOrder> getLabOrdersByPatient(Patient patient) throws APIException;
	
	/**
	 * Returns a specified number of labOrders starting with a given string from the specified index
	 */
	public List<LabOrder> getLabOrders(String nameFragment, Boolean includeRetired, Integer start, Integer length);
	
	/**
	 * Get count of LabOrder, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabOrders in this list
	 * @return LabOrders list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public Integer getCountOfLabOrders(Boolean includeRetired) throws APIException;
	
	/**
	 * Get count of unretired LabOrder
	 * 
	 * @return LabOrders list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public Integer getCountOfLabOrders() throws APIException;
	
	/*
	 * 
	 */
	@Transactional(readOnly=false)
	@Authorized(PrivilegeConstants.DELETE_LAB_TEST)
	public LabOrder voidLabOrder(LabOrder labLabOrder, String reason)throws APIException;
	
//----------------------------------------------------------------
	/**
	 * Save or update the given <code>LabOrder</code> in the database
	 * 
	 * @param labOrderSpecimen the LabOrderSpecimen to save
	 * @return the LabOrderSpecimen that was saved
	 * @throws APIException
	 * @should not save LabOrderSpecimen if LabOrderSpecimen doesn't validate
	 */
	//  @Authorized( { PrivilegeConstants.EDIT_LAB_ORDER, PrivilegeConstants.ADD_LAB_ORDER })
	public LabOrderSpecimen saveLabOrderSpecimen(LabOrderSpecimen labOrderSpecimen) throws APIException;
	
	/**
	 * Get the <code>LabOrderSpecimen</code> with the given uuid from the database
	 * 
	 * @param uuid the uuid to find
	 * @return the LabOrderSpecimen that was found or null
	 */
	//  @Authorized( PrivilegeConstants.VIEW_LAB_ORDER )
	public LabOrderSpecimen getLabOrderSpecimenByUuid(String uuid);
	
	/**
	 * Completely delete an LabOrderSpecimen from the database. This should not typically be used unless
	 * desperately needed. Most LabOrderSpecimens should just be retired. See {@link #purgeLabOrderSpecimen(LabOrderSpecimen, String)}
	 * 
	 * @param labOrderSpecimen The LabOrderSpecimen to remove from the system
	 * @throws APIException
	 */
	//  @Authorized(PrivilegeConstants.PURGE_LAB_ORDER)
	public void purgeLabOrderSpecimen(LabOrderSpecimen labOrderSpecimen) throws APIException;
	
	/**
	 * Mark an LabOrderSpecimen as retired. This functionally removes the LabOrderSpecimen from the system while keeping a
	 * semblance
	 * 
	 * @param reason String reason
	 * @param labOrderSpecimen LabOrderSpecimen to void
	 * @return the LabOrderSpecimen that was voided
	 * @throws APIException
	 */
	//  @Authorized(PrivilegeConstants.DELETE_LAB_ORDER)
	public LabOrderSpecimen deleteLabOrderSpecimen(LabOrderSpecimen labOrderSpecimen, String retireReason) throws APIException;
	
	/**
	 * Get all LabOrderSpecimen, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeVoided true/false whether to include retired LabOrderSpecimens in this list
	 * @return LabOrderSpecimens list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public List<LabOrderSpecimen> getAllLabOrderSpecimens(Boolean includeVoided) throws APIException;
	
	/**
	 * Get all unvoided LabOrderSpecimen
	 * 
	 * @return LabOrderSpecimens list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public List<LabOrderSpecimen> getAllLabOrderSpecimens() throws APIException;
	
	/**
	 * Returns a specified number of labOrderSpecimens starting with a given string from the specified index
	 */
	public List<LabOrderSpecimen> getLabOrderSpecimens(String nameFragment, Boolean includeRetired, Integer start, Integer length);
	
	/**
	 * Get count of LabOrderSpecimen, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabOrderSpecimens in this list
	 * @return LabOrderSpecimens list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public Integer getCountOfLabOrderSpecimens(Boolean includeRetired) throws APIException;
	
	/**
	 * Get count of unretired LabOrderSpecimen
	 * 
	 * @return LabOrderSpecimens list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public Integer getCountOfLabOrderSpecimens() throws APIException;

//--------------------------------------------------------------------------------

	/*
	 * @param id
	 * @return LabSpecimen if founded, or null.
	 */
	public LabSpecimen getLabSpecimen(Integer specimenId);
	
	/*
	 * @param uuid
	 * @return LabSpecimen if found, or null.
	 * @should return specimen by uuid
	 */
	public LabSpecimen getLabSpecimenByUuid(String uuid);
	
	/*
	 * @return save LabSpecimen to database.
	 * @throws APIException
	 */
	@Transactional(readOnly=true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public LabSpecimen saveLabSpecimen(LabSpecimen labLabSpecimen)throws APIException;
	
	/*
	 *  Remove the LabSpecimen from database, most of the time we just voided the LabSpecimen.
	 * @param the LabSpecimen that should be removed
	 * @throws APIException
	 */
	@Transactional(readOnly=false)
	//  @Authorized(PrivilegeConstants.PURGE_LAB_ORDER)
	public void purgeLabSpecimen(LabSpecimen labLabSpecimen)throws APIException;
	
	/*
	 * 
	 */
	@Transactional(readOnly=false)
	//  @Authorized(PrivilegeConstants.DELETE_LAB_ORDER)
	public LabSpecimen deleteLabSpecimen(LabSpecimen labLabSpecimen, String reason)throws APIException;
	
	/*
	 * Get all LabSpecimen list including voided if the ifVoided is true
	 * 
	 * @param if include voided LabSpecimen
	 * @return LabSpecimen list(include voided list if ifVoided is true)
	 * @throws APIException
	 * @should return specimens
	 */
	@Transactional(readOnly=true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public List<LabSpecimen> getAllLabSpecimens(Boolean ifVoided)throws APIException;
	
	/*
	 * get the specific list of LabTests from String and index.
	 */
	public List<LabSpecimen> getLabSpecimens(String displayFragment, Boolean ifVoided, Integer index, Integer length);

	/*
	 * Get the length of the list that LabSpecimen return(include voided if ifVoided is true)
	 * @param search is a String that represent DisplayFragment
	 * @param if include voided resources
	 * @return length of LabSpecimen(include voided if ifVoided is true) resource
	 * throws Exception
	 */
	@Transactional(readOnly=true)
	//  @Authorized(PrivilegeConstants.VIEW_LAB_ORDER)
	public Integer getCountOfLabSpecimen(String search,Boolean ifVoided)throws APIException;
	

	
}
