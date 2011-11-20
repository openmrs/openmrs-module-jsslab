package org.openmrs.module.jsslab;

import java.util.List;

import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.module.jsslab.PrivilegeConstants;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.springframework.transaction.annotation.Transactional;

public interface LabManagementService extends OpenmrsService {

	/**
	 * Save or update the given <code>LabInstrument</code> in the database
	 * 
	 * @param labInstrument the LabInstrument to save
	 * @return the LabInstrument that was saved
	 * @throws APIException
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
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.DELETE_LAB_MGMT)
	public LabInstrument retireLabInstrument(LabInstrument labInstrument, String retireReason) throws APIException;
	
	/**
	 * Get all LabInstrument, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabInstruments in this list
	 * @return LabInstruments list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_MGMT)
	public List<LabInstrument> getAllLabInstruments(Boolean includeRetired) throws APIException;
	
	/**
	 * Get the <code>LabInstrument</code> with the given propertyTag or serialNumber
	 * 
	 * @param propertyTag or serialNumber to find
	 * @return the LabInstrument that was found or null
	 */
	@Authorized( PrivilegeConstants.VIEW_LAB_MGMT )
	public LabInstrument getLabInstrument(String idNumber);
	
	/**
	 * Save or update the given <code>LabSupplyItem</code> in the database
	 * 
	 * @param labSupplyItem the LabSupplyItem to save
	 * @return the LabSupplyItem that was saved
	 * @throws APIException
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
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.DELETE_LAB_MGMT)
	public LabSupplyItem retireLabSupplyItem(LabSupplyItem labSupplyItem, String retireReason) throws APIException;
	
	/**
	 * Get all LabSupplyItems, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabSupplyItems in this list
	 * @return LabSupplyItems list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_MGMT)
	public List<LabSupplyItem> getAllLabSupplyItems(Boolean includeRetired) throws APIException;
	
	
	
}
