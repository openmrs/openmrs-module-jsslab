package org.openmrs.module.jsslab.impl;

import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.jsslab.db.LabTestPanelDAO;
import org.openmrs.module.jsslab.db.hibernate.HibernateLabTestPanelDAO;
import org.openmrs.module.jsslab.db.LabTestDAO;
import org.openmrs.module.jsslab.db.hibernate.HibernateLabTestDAO;

public class LabCatalogServiceImpl extends BaseOpenmrsService implements
		LabCatalogService {

	private final Log log = LogFactory.getLog(this.getClass());
	
	protected LabTestPanelDAO labTestPanelDAO;
	protected LabTestDAO labTestDAO;
	
	public void setLabTestPanelDAO(LabTestPanelDAO labTestPanelDAO) {
		this.labTestPanelDAO = labTestPanelDAO;
	}

	public void setLabTestDAO(LabTestDAO labTestDAO) {
		this.labTestDAO = labTestDAO;
	}

	public LabTestPanel saveLabTestPanel(LabTestPanel labTestPanel)
			throws APIException {
		return labTestPanelDAO.saveLabTestPanel(labTestPanel);
		
	}

	public LabTestPanel getLabTestPanelByUuid(String uuid) {
		return labTestPanelDAO.getLabTestPanelByUuid(uuid);
	}

	public void purgeLabTestPanel(LabTestPanel labTestPanel)
			throws APIException {
		labTestPanelDAO.deleteLabTestPanel(labTestPanel);
		
	}

	public LabTestPanel retireLabTestPanel(LabTestPanel labTestPanel,
			String retireReason) throws APIException {
		labTestPanel.setRetired(true);
		labTestPanel.setDateRetired(new Date());
		labTestPanel.setRetireReason(retireReason);
		return labTestPanelDAO.saveLabTestPanel(labTestPanel);
	}

	public List<LabTestPanel> getAllLabTestPanels(Boolean includeRetired)
			throws APIException {
		return labTestPanelDAO.getAllLabTestPanels(includeRetired);
	}

	public LabTestPanel getLabTestPanel(String idNumber) {
		return labTestPanelDAO.getLabTestPanel(idNumber);
	}

	public LabTest saveLabTest(LabTest labTest)
			throws APIException {
		return labTestDAO.saveLabTest(labTest);
	}

	public LabTest getLabTestByUUID(String uuid) {
		return labTestDAO.getLabTestByUuid(uuid);
	}

	public void purgeLabTest(LabTest labTest)
			throws APIException {
		labTestDAO.deleteLabTest(labTest);
	}

	public LabTest retireLabTest(LabTest labTest,
			String retireReason) throws APIException {
		labTest.setRetired(true);
		labTest.setDateRetired(new Date());
		labTest.setRetireReason(retireReason);
		return labTestDAO.saveLabTest(labTest);
	}

	public List<LabTest> getAllLabTests(Boolean includeRetired)
			throws APIException {
		return labTestDAO.getAllLabTests(includeRetired);
	}


}
