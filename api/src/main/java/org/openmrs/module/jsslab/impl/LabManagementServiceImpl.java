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
import org.openmrs.module.jsslab.db.LabManagementServiceDAO;
import org.openmrs.module.jsslab.db.hibernate.HibernateLabManagementServiceDAO;

public class LabManagementServiceImpl extends BaseOpenmrsService implements
		LabManagementService {

	private final Log log = LogFactory.getLog(this.getClass());
	
	private LabManagementServiceDAO dao = new HibernateLabManagementServiceDAO();

	public LabInstrument saveLabInstrument(LabInstrument labInstrument)
			throws APIException {
		return dao.saveLabInstrument(labInstrument);
		
	}

	public LabInstrument getLabInstrumentByUuid(String uuid) {
		return dao.getLabInstrumentByUuid(uuid);
	}

	public void purgeLabInstrument(LabInstrument labInstrument)
			throws APIException {
		dao.deleteLabInstrument(labInstrument);
		
	}

	public LabInstrument retireLabInstrument(LabInstrument labInstrument,
			String retireReason) throws APIException {
		labInstrument.setRetired(true);
		labInstrument.setDateRetired(new Date());
		labInstrument.setRetireReason(retireReason);
		return dao.saveLabInstrument(labInstrument);
	}

	public List<LabInstrument> getAllLabInstruments(Boolean includeRetired)
			throws APIException {
		return dao.getAllLabInstruments(includeRetired);
	}

	public LabInstrument getLabInstrument(String idNumber) {
		return dao.getLabInstrument(idNumber);
	}

	public LabSupplyItem saveLabSupplyItem(LabSupplyItem labSupplyItem)
			throws APIException {
		return dao.saveLabSupplyItem(labSupplyItem);
	}

	public LabSupplyItem getLabSupplyItemByUUID(String uuid) {
		return dao.getLabSupplyItemByUuid(uuid);
	}

	public void purgeLabSupplyItem(LabSupplyItem labSupplyItem)
			throws APIException {
		dao.deleteLabSupplyItem(labSupplyItem);
	}

	public LabSupplyItem retireLabSupplyItem(LabSupplyItem labSupplyItem,
			String retireReason) throws APIException {
		labSupplyItem.setRetired(true);
		labSupplyItem.setDateRetired(new Date());
		labSupplyItem.setRetireReason(retireReason);
		return dao.saveLabSupplyItem(labSupplyItem);
	}

	public List<LabSupplyItem> getAllLabSupplyItems(Boolean includeRetired)
			throws APIException {
		return dao.getAllLabSupplyItems(includeRetired);
	}


}
