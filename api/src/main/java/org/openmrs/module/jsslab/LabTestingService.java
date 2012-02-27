package org.openmrs.module.jsslab;

import java.util.List;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
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
	
	@Transactional(readOnly=true)
	@Authorized(PrivilegeConstants.VIEW_LAB_TEST)
	public LabTestRange getLabTestRangeByName(String labTestRange);
	
	@Transactional(readOnly = true)
	@Authorized(PrivilegeConstants.VIEW_LAB_TEST)
	public List<LabTestRange> getAllLabTestRanges(Boolean ifVoided)throws APIException;
}