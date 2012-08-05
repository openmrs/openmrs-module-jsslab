package org.openmrs.module.jsslab;

import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LabManagementService extends OpenmrsService {

	/**
	 * Save or update the given <code>LabInstrument</code> in the database
	 * 
	 * @param labInstrument the LabInstrument to save
	 * @return the LabInstrument that was saved
	 * 
	 * @throws APIException
	 * 
	 * @should not save LabInstrument if LabInstrument doesn't validate
	 */
	@Authorized( { PrivilegeConstants.EDIT_LAB_MGMT, PrivilegeConstants.ADD_LAB_MGMT })
	public LabInstrument saveLabInstrument(LabInstrument labInstrument) throws APIException;
	
	/**
	 * Get the <code>LabInstrument</code> with the given uuid from the database
	 * 
	 * @param uuid the uuid to find
	 * @return the LabInstrument that was found or null
	 */
	@Authorized( PrivilegeConstants.VIEW_LAB_MGMT )
	public LabInstrument getLabInstrumentByUuid(String uuid);
	
	/**
	 * Completely delete an LabInstrument from the database. This should not typically be used unless
	 * desperately needed. Most LabInstruments should just be retired. See {@link #retireLabInstrument(LabInstrument, String)}
	 * 
	 * @param labInstrument The LabInstrument to remove from the system
	 * 
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.PURGE_LAB_MGMT)
	public void purgeLabInstrument(LabInstrument labInstrument) throws APIException;
	
	/**
	 * Mark an LabInstrument as retired. This functionally removes the LabInstrument from the system while keeping a
	 * semblance
	 * 
	 * @param retireReason String reason
	 * @param labInstrument LabInstrument to retire
	 * @return the LabInstrument that was retired
	 * 
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.DELETE_LAB_MGMT)
	public LabInstrument retireLabInstrument(LabInstrument labInstrument, String retireReason) throws APIException;
	
	/**
	 * Get all LabInstrument, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabInstruments in this list
	 * @return LabInstruments list
	 * 
	 * @throws APIException
	 * 
	 * @should get all <code>LabInstrument</code>s by if includeRetired
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_MGMT)
	public List<LabInstrument> getAllLabInstruments(Boolean includeRetired) throws APIException;
	
	/**
	 * Get all unretired LabInstrument
	 * 
	 * @return LabInstruments list
	 * @throws APIException
	 * 
	 * @should get all <code>LabInstrument</code>s
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_MGMT)
	public List<LabInstrument> getAllLabInstruments() throws APIException;
	
	/**
	 * Returns a specified number of labInstruments starting with a given string from the specified index
	 * 
	 * @param nameFragment The name or part of a name that retrieved <code>LabInstrument</code>s should match
	 * @param includeRetired Whether or not retired <code>LabInstrument</code>s should be included
	 * @param start The index of the first <code>LabInstrument</code> to retrieve from all matching items
	 * @param length The amount of <code>LabInstrument</code>s to retrieve 
	 * @return A list of <code>LabInstrument</code>s matching the conditions
	 */
	public List<LabInstrument> getLabInstruments(String nameFragment, Boolean includeRetired, Integer start, Integer length);
	
	/**
	 * Get count of LabInstrument, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabInstruments in this list
	 * @return LabInstruments list
	 * 
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_MGMT)
	public Integer getCountOfLabInstruments(Boolean includeRetired) throws APIException;
	
	/**
	 * Returns a specified number of supplyItems starting with a given string from the specified index
	 */
	public List<LabSupplyItem> getLabSupplyItems(String nameFragment, Boolean includeRetired, Integer start, Integer length);
	
	/**
	 * Get count of unretired LabInstrument
	 * 
	 * @return LabInstruments list
	 * 
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_MGMT)
	public Integer getCountOfLabInstruments() throws APIException;
	
	/**
	 * Get the <code>LabInstrument</code> with the given propertyTag or serialNumber
	 * 
	 * @param name propertyTag, serialNumber or model to find
	 * @return the LabInstrument that was found or null
	 */
	@Authorized( PrivilegeConstants.VIEW_LAB_MGMT )
	public LabInstrument getLabInstrument(String name);
	
	/**
	 * Save or update the given <code>LabSupplyItem</code> in the database
	 * 
	 * @param labSupplyItem the LabSupplyItem to save
	 * @return the LabSupplyItem that was saved
	 * 
	 * @throws APIException
	 * 
	 * @should not save LabSupplyItem if LabSupplyItem doesn't validate
	 */
	@Authorized( { PrivilegeConstants.EDIT_LAB_MGMT, PrivilegeConstants.ADD_LAB_MGMT })
	public LabSupplyItem saveLabSupplyItem(LabSupplyItem labSupplyItem) throws APIException;
	
	/**
	 * Get the <code>LabSupplyItem</code> with the given uuid from the database
	 * 
	 * @param uuid the uuid to find
	 * @return the LabSupplyItem that was found or null
	 */
	@Authorized( PrivilegeConstants.VIEW_LAB_MGMT )
	public LabSupplyItem getLabSupplyItemByUUID(String uuid);
	
	/**
	 * Completely delete an LabSupplyItem from the database. This should not typically be used unless
	 * desperately needed. Most LabSupplyItems should just be retired. See {@link #retireLabSupplyItem(LabSupplyItem, String)}
	 * 
	 * @param labSupplyItem The LabSupplyItem to remove from the system
	 * 
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.PURGE_LAB_MGMT)
	public void purgeLabSupplyItem(LabSupplyItem labSupplyItem) throws APIException;
	
	/**
	 * Mark an LabSupplyItem as retired. This functionally removes the LabSupplyItem from the system while keeping a
	 * semblance
	 * 
	 * @param retireReason String reason
	 * @param labSupplyItem LabSupplyItem to retire
	 * @return the LabSupplyItem that was retired
	 * 
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.DELETE_LAB_MGMT)
	public LabSupplyItem retireLabSupplyItem(LabSupplyItem labSupplyItem, String retireReason) throws APIException;
	
	/**
	 * Get all LabSupplyItem, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabSupplyItems in this list
	 * @return LabSupplyItems list
	 * 
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_MGMT)
	public List<LabSupplyItem> getAllLabSupplyItems(Boolean includeRetired) throws APIException;
	
	/**
	 * Get all unretired LabSupplyItem
	 * 
	 * @return LabSupplyItems list
	 * 
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_MGMT)
	public List<LabSupplyItem> getAllLabSupplyItems() throws APIException;
	
	/**
	 * Get count of LabSupplyItem, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabSupplyItems in this list
	 * @return LabSupplyItems list
	 * 
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_MGMT)
	public Integer getCountOfLabSupplyItems(Boolean includeRetired) throws APIException;
	
	/**
	 * Get count of unretired LabSupplyItem
	 * 
	 * @return LabSupplyItems list
	 * 
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_MGMT)
	public Integer getCountOfLabSupplyItems() throws APIException;
	
	
}
