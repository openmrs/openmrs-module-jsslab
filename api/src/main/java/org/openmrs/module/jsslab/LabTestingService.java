package org.openmrs.module.jsslab;

import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabTestSpecimen;
import org.openmrs.module.jsslab.db.LabTestRange;
import org.springframework.transaction.annotation.Transactional;

public interface LabTestingService extends OpenmrsService {

	@Authorized( PrivilegeConstants.VIEW_LAB_TEST )
	public LabTestRange getLabTestRangeByUuid(String uuid);
	
	@Authorized( { PrivilegeConstants.EDIT_LAB_TEST, PrivilegeConstants.ADD_LAB_TEST })
	public LabTestRange saveLabTestRange(LabTestRange labTestRange)throws APIException;
	
	@Transactional(readOnly=false)
	@Authorized(PrivilegeConstants.DELETE_LAB_TEST)
	public void deleteLabTestRange(LabTestRange labTestRange, String reason)throws APIException;
	
	@Authorized(PrivilegeConstants.PURGE_LAB_TEST)
	public void purgeLabTestRange(LabTestRange labTestRange)throws APIException;
	
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_TEST)
	public List<LabTestRange> getAllLabTestRanges(Boolean ifVoided)throws APIException;
	
//-----------------------------------------------------------------------
	
	@Authorized( PrivilegeConstants.VIEW_LAB_TEST )
	public LabTestSpecimen getLabTestSpecimen(Integer labTestSpecimen);
	
	@Authorized( PrivilegeConstants.VIEW_LAB_TEST )
	public LabTestSpecimen getLabTestSpecimenByUuid(String uuid);
	
	@Authorized( { PrivilegeConstants.EDIT_LAB_TEST, PrivilegeConstants.ADD_LAB_TEST })
	public LabTestSpecimen saveLabTestSpecimen(LabTestSpecimen labTestSpecimen)throws APIException;
	
	/**
	 * Completely delete an LabTestSpecimen from the database. This should not typically be used unless
	 * desperately needed. Most LabTestSpecimens should just be retired. See {@link #retireLabTestSpecimen(LabTestSpecimen, String)}
	 * 
	 * @param labTestSpecimen The LabTestSpecimen to remove from the system
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.PURGE_LAB_TEST)
	public void purgeLabTestSpecimen(LabTestSpecimen labTestSpecimen) throws APIException;
	
	/**
	 * Mark an LabTestSpecimen as retired. This functionally removes the LabTestSpecimen from the system while keeping a
	 * semblance
	 * 
	 * @param retireReason String reason
	 * @param labTestSpecimen LabTestSpecimen to retire
	 * @return the LabTestSpecimen that was retired
	 * @throws APIException
	 */
	@Authorized(PrivilegeConstants.DELETE_LAB_TEST)
	public LabTestSpecimen retireLabTestSpecimen(LabTestSpecimen labTestSpecimen, String retireReason) throws APIException;
	
	/**
	 * Get all LabTestSpecimen, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabTestSpecimens in this list
	 * @return LabTestSpecimens list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_TEST)
	public List<LabTestSpecimen> getAllLabTestSpecimens(Boolean includeRetired) throws APIException;
	
	/**
	 * Get all unretired LabTestSpecimen
	 * 
	 * @return LabTestSpecimens list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_TEST)
	public List<LabTestSpecimen> getAllLabTestSpecimens() throws APIException;
	
	/**
	 * Returns a specified number of labTestSpecimens starting with a given string from the specified index
	 */
	public List<LabTestSpecimen> getLabTestSpecimens(String nameFragment, Boolean includeRetired, Integer start, Integer length);
	
	/**
	 * Get count of LabTestSpecimen, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeRetired true/false whether to include retired LabTestSpecimens in this list
	 * @return LabTestSpecimens list
	 * @throws APIException
	 */
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_TEST)
	public Integer getCountOfLabTestSpecimens(Boolean includeRetired) throws APIException;
	

}