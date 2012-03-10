package org.openmrs.module.jsslab.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jsslab.LabTestingService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSpecimen;
import org.openmrs.module.jsslab.db.LabTestRange;
import org.openmrs.module.jsslab.db.LabTestRangeDAO;
import org.openmrs.module.jsslab.db.LabTestSpecimen;
import org.openmrs.module.jsslab.db.LabTestSpecimenDAO;



public class LabTestingServiceImpl extends BaseOpenmrsService implements LabTestingService{

	private final Log log = LogFactory.getLog(this.getClass());
	
	protected LabTestRangeDAO labTestRangeDAO;
	
	protected LabTestSpecimenDAO labTestSpecimenDAO;
	
	public void setLabTestRangeDAO(LabTestRangeDAO labTestRangeDAO)
	{
		this.labTestRangeDAO=labTestRangeDAO;
	}
	
	public void setLabTestSpecimenDAO(LabTestSpecimenDAO labTestSpecimenDAO)
	{
		this.labTestSpecimenDAO=labTestSpecimenDAO;
	}
	
	@Override
	public LabTestRange getLabTestRange(Integer labTestRangeId) {		
		return labTestRangeDAO.getLabTestRange(labTestRangeId);
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
	public void deleteLabTestRange(LabTestRange labTestRange, String reason)throws APIException {
			labTestRange.setVoided(true);
			labTestRange.setDateVoided(new Date());
			labTestRange.setVoidReason(reason);
			labTestRangeDAO.saveLabTestRange(labTestRange);
			return;
	}

	@Override
	public void purgeLabTestRange(LabTestRange labTestRange)throws APIException {
		labTestRangeDAO.deleteLabTestRange(labTestRange);
	}

	@Override
	public List<LabTestRange> getAllLabTestRanges(Boolean ifVoided)throws APIException{
		return labTestRangeDAO.getAllLabTestRanges(ifVoided);
	}

	@Override
	public List<LabTestRange> getLabTestRanges(String nameFragment,
			Boolean includeVoided, Integer start, Integer length) {
		return labTestRangeDAO.getLabTestRanges(nameFragment,
				includeVoided, start, length);
	}

	@Override
	public Integer getCountOfLabTestRanges(Boolean includeVoided)
			throws APIException {
		return labTestRangeDAO.getLabTestRanges("", includeVoided, null, null).size();
	}

//--------------------------------------------------------
	
	@Override
	public LabTestSpecimen getLabTestSpecimen(Integer labTestSpecimenId) {
		return labTestSpecimenDAO.getLabTestSpecimen(labTestSpecimenId);
	}

	@Override
	public LabTestSpecimen getLabTestSpecimenByUuid(String uuid) {
		return labTestSpecimenDAO.getLabTestSpecimenByUuid(uuid);
	}

	@Override
	public LabTestSpecimen saveLabTestSpecimen(LabTestSpecimen labTestSpecimen)
			throws APIException {
		return labTestSpecimenDAO.saveLabTestSpecimen(labTestSpecimen);
	}

	@Override
	public void purgeLabTestSpecimen(LabTestSpecimen labTestSpecimen)
			throws APIException {
		labTestSpecimenDAO.deleteLabTestSpecimen(labTestSpecimen);
		
	}

	@Override
	public LabTestSpecimen retireLabTestSpecimen(
			LabTestSpecimen labTestSpecimen, String retireReason)
			throws APIException {
		labTestSpecimen.setRetired(true);
		labTestSpecimen.setDateRetired(new Date());
		labTestSpecimen.setRetireReason(retireReason);
		return labTestSpecimenDAO.saveLabTestSpecimen(labTestSpecimen);
	}

	@Override
	public List<LabTestSpecimen> getAllLabTestSpecimens(Boolean includeVoided)
			throws APIException {
		return labTestSpecimenDAO.getLabTestSpecimens("",includeVoided,null,null);
	}

	@Override
	public List<LabTestSpecimen> getAllLabTestSpecimens() throws APIException {
		return labTestSpecimenDAO.getLabTestSpecimens("",false,null,null);
	}

	@Override
	public List<LabTestSpecimen> getLabTestSpecimens(String nameFragment,
			Boolean includeVoided, Integer start, Integer length) {
		return labTestSpecimenDAO.getLabTestSpecimens(nameFragment,
				includeVoided, start, length);
	}

	@Override
	public Integer getCountOfLabTestSpecimens(Boolean includeRetired)
			throws APIException {
		return labTestSpecimenDAO.getLabTestSpecimens("", includeRetired, null, null).size();
	}
	
}
