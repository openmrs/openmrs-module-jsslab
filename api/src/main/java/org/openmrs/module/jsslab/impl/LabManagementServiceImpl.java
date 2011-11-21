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
	
	private LabInstrumentDAO daoLabInstrument = new HibernateLabInstrumentDAO();
	private LabSupplyItemDAO daoSupplyItem = new HibernateLabSupplyItemDAO();

	public LabInstrument saveLabInstrument(LabInstrument labInstrument)
			throws APIException {
		return daoLabInstrument.saveLabInstrument(labInstrument);
		
	}

	public LabInstrument getLabInstrumentByUuid(String uuid) {
		return daoLabInstrument.getLabInstrumentByUuid(uuid);
	}

	public void purgeLabInstrument(LabInstrument labInstrument)
			throws APIException {
		daoLabInstrument.deleteLabInstrument(labInstrument);
		
	}

	public LabInstrument retireLabInstrument(LabInstrument labInstrument,
			String retireReason) throws APIException {
		labInstrument.setRetired(true);
		labInstrument.setDateRetired(new Date());
		labInstrument.setRetireReason(retireReason);
		return daoLabInstrument.saveLabInstrument(labInstrument);
	}

	public List<LabInstrument> getAllLabInstruments(Boolean includeRetired)
			throws APIException {
		return daoLabInstrument.getAllLabInstruments(includeRetired);
	}

	public LabInstrument getLabInstrument(String idNumber) {
		return daoLabInstrument.getLabInstrument(idNumber);
	}

	public LabSupplyItem saveLabSupplyItem(LabSupplyItem labSupplyItem)
			throws APIException {
		return daoSupplyItem.saveLabSupplyItem(labSupplyItem);
	}

	public LabSupplyItem getLabSupplyItemByUUID(String uuid) {
		return daoSupplyItem.getLabSupplyItemByUuid(uuid);
	}

	public void purgeLabSupplyItem(LabSupplyItem labSupplyItem)
			throws APIException {
		daoSupplyItem.deleteLabSupplyItem(labSupplyItem);
	}

	public LabSupplyItem retireLabSupplyItem(LabSupplyItem labSupplyItem,
			String retireReason) throws APIException {
		labSupplyItem.setRetired(true);
		labSupplyItem.setDateRetired(new Date());
		labSupplyItem.setRetireReason(retireReason);
		return daoSupplyItem.saveLabSupplyItem(labSupplyItem);
	}

	public List<LabSupplyItem> getAllLabSupplyItems(Boolean includeRetired)
			throws APIException {
		return daoSupplyItem.getAllLabSupplyItems(includeRetired);
	}


}
