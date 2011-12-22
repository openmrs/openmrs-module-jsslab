package org.openmrs.module.jsslab.impl;

import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jsslab.LabManagementService;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.module.jsslab.db.LabInstrumentDAO;
import org.openmrs.module.jsslab.db.hibernate.HibernateLabInstrumentDAO;
import org.openmrs.module.jsslab.db.LabSupplyItemDAO;
import org.openmrs.module.jsslab.db.hibernate.HibernateLabSupplyItemDAO;

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

	public LabInstrument getLabInstrument(String idNumber) {
		return labInstrumentDAO.getLabInstrument(idNumber);
	}

	public LabSupplyItem saveLabSupplyItem(LabSupplyItem labSupplyItem)
			throws APIException {
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


}
