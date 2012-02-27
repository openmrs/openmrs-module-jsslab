package org.openmrs.module.jsslab.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jsslab.LabTestingService;
import org.openmrs.module.jsslab.db.LabTestRange;
import org.openmrs.module.jsslab.db.LabTestRangeDAO;



public class LabTestingServiceImpl extends BaseOpenmrsService implements LabTestingService{

	private final Log log = LogFactory.getLog(this.getClass());
	
	protected LabTestRangeDAO labTestRangeDAO;
	
	public void setLabTestRangeDAO(LabTestRangeDAO labTestRangeDAO)
	{
		this.labTestRangeDAO=labTestRangeDAO;
	}
	
	@Override
	public LabTestRange getLabTestRangeByUuid(String uuid) {		
		return labTestRangeDAO.getLabTestRangeByUuid(uuid);
	}

	@Override
	public LabTestRange saveLabTestRange(LabTestRange labTestRange)throws APIException {
		return labTestRangeDAO.saveLabTestRange(labTestRange);
	}

	@Override
	public void deleteLabTestRange(LabTestRange labTestRange)throws APIException {
		labTestRangeDAO.deleteLabTestRange(labTestRange);		
	}

	@Override
	public void purgeLabTestRange(LabTestRange labTestRange)throws APIException {
		labTestRangeDAO.purgeLabTestRange(labTestRange);
	}

	@Override
	public LabTestRange getLabTestRangeByName(String labTestRange) {		
		return labTestRangeDAO.getLabTestRangeByName(labTestRange);
	}

	@Override
	public List<LabTestRange> getAllLabTestRanges(Boolean ifVoided)throws APIException{
		return labTestRangeDAO.getAllLabTestRanges(ifVoided);
	}
}
