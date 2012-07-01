package org.openmrs.module.jsslab.impl;

import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jsslab.LabManagementService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.module.jsslab.db.LabInstrumentDAO;
import org.openmrs.module.jsslab.db.LabSupplyItemDAO;
import org.openmrs.validator.ValidateUtil;

public class LabManagementServiceImpl extends BaseOpenmrsService implements
		LabManagementService {

	private final Log log = LogFactory.getLog(this.getClass());
	
	protected LabInstrumentDAO labInstrumentDAO;
	protected LabSupplyItemDAO labSupplyItemDAO;
	
	public void setLabInstrumentDAO(LabInstrumentDAO labInstrumentDAO) {
		this.labInstrumentDAO = labInstrumentDAO;
	}

	public void setLabSupplyItemDAO(LabSupplyItemDAO labSupplyItemDAO) {
		this.labSupplyItemDAO = labSupplyItemDAO;
	}

	public LabInstrument saveLabInstrument(LabInstrument labInstrument)
			throws APIException {
		if (labInstrument == null)
			throw new APIException(Context.getMessageSourceService().getMessage("error.null"));
		ValidateUtil.validate(labInstrument);
		return labInstrumentDAO.saveLabInstrument(labInstrument);
		
	}

	public LabInstrument getLabInstrumentByUuid(String uuid) {
		return labInstrumentDAO.getLabInstrumentByUuid(uuid);
	}

	public void purgeLabInstrument(LabInstrument labInstrument)
			throws APIException {
		labInstrumentDAO.deleteLabInstrument(labInstrument);
		
	}

	public LabInstrument retireLabInstrument(LabInstrument labInstrument,
			String retireReason) throws APIException {
		labInstrument.setRetired(true);
		labInstrument.setDateRetired(new Date());
		labInstrument.setRetireReason(retireReason);
		return labInstrumentDAO.saveLabInstrument(labInstrument);
	}

	public List<LabInstrument> getAllLabInstruments(Boolean includeRetired)
			throws APIException {
		return labInstrumentDAO.getAllLabInstruments(includeRetired);
	}

	public List<LabInstrument> getAllLabInstruments()
			throws APIException {
		return labInstrumentDAO.getAllLabInstruments(false);
	}

	public Integer getCountOfLabInstruments(Boolean includeRetired)
			throws APIException {
		return labInstrumentDAO.getCountOfLabInstruments("", includeRetired);
	}

	public Integer getCountOfLabInstruments()
			throws APIException {
		return labInstrumentDAO.getCountOfLabInstruments("", false);
	}

	public LabInstrument getLabInstrument(Integer idNumber) {
		return labInstrumentDAO.getLabInstrument(idNumber);
	}

	/**
	 * Returns a specified number of labInstruments starting with a given string from the specified index
	 * 
	 * @see LabManagementService#getLabInstruments(String, Boolean, Integer, Integer)
	 */
	public List<LabInstrument> getLabInstruments(String nameFragment, Boolean includeRetired, Integer start, Integer length) {
		return labInstrumentDAO.getLabInstruments(nameFragment, includeRetired, start, length);

	}
	
	public LabSupplyItem saveLabSupplyItem(LabSupplyItem labSupplyItem)
			throws APIException {
		if (labSupplyItem == null)
			throw new APIException(Context.getMessageSourceService().getMessage("error.null"));
		ValidateUtil.validate(labSupplyItem);
		return labSupplyItemDAO.saveLabSupplyItem(labSupplyItem);
	}

	public LabSupplyItem getLabSupplyItemByUUID(String uuid) {
		return labSupplyItemDAO.getLabSupplyItemByUuid(uuid);
	}

	public void purgeLabSupplyItem(LabSupplyItem labSupplyItem)
			throws APIException {
		labSupplyItemDAO.deleteLabSupplyItem(labSupplyItem);
	}

	public LabSupplyItem retireLabSupplyItem(LabSupplyItem labSupplyItem,
			String retireReason) throws APIException {
		labSupplyItem.setRetired(true);
		labSupplyItem.setDateRetired(new Date());
		labSupplyItem.setRetireReason(retireReason);
		return labSupplyItemDAO.saveLabSupplyItem(labSupplyItem);
	}

	public List<LabSupplyItem> getAllLabSupplyItems(Boolean includeRetired)
			throws APIException {
		return labSupplyItemDAO.getAllLabSupplyItems(includeRetired);
	}

	public List<LabSupplyItem> getAllLabSupplyItems()
			throws APIException {
		return labSupplyItemDAO.getAllLabSupplyItems(false);
	}

	/**
	 * Returns a specified number of supplyItems starting with a given string from the specified index
	 */
	public List<LabSupplyItem> getLabSupplyItems(String nameFragment, Boolean includeRetired, Integer start, Integer length) {
			return labSupplyItemDAO.getLabSupplyItems(nameFragment, includeRetired, start, length);
	}
	
	public Integer getCountOfLabSupplyItems(Boolean includeRetired)
			throws APIException {
		return labSupplyItemDAO.getCountOfLabSupplyItems("",includeRetired);
	}

	public Integer getCountOfLabSupplyItems()
			throws APIException {
		return labSupplyItemDAO.getCountOfLabSupplyItems("",false);
	}


}
