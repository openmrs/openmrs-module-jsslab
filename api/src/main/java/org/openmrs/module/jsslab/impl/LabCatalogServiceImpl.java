package org.openmrs.module.jsslab.impl;

import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.jsslab.db.LabPrecondition;
import org.openmrs.module.jsslab.db.LabTestPanelDAO;
import org.openmrs.module.jsslab.db.LabTestDAO;
import org.openmrs.module.jsslab.db.LabPreconditionDAO;
import org.openmrs.module.jsslab.db.LabSpecimenTemplate;
import org.openmrs.module.jsslab.db.LabSpecimenTemplateDAO;

public class LabCatalogServiceImpl extends BaseOpenmrsService implements
		LabCatalogService {

	private final Log log = LogFactory.getLog(this.getClass());

	protected LabTestPanelDAO labTestPanelDAO;

	protected LabTestDAO labTestDAO;

	protected LabPreconditionDAO labPreconditionDAO;
	
	protected LabSpecimenTemplateDAO labSpecimenTemplateDAO;
	
	public void setLabTestPanelDAO(LabTestPanelDAO labTestPanelDAO) {
		this.labTestPanelDAO = labTestPanelDAO;
	}

	public void setLabTestDAO(LabTestDAO labTestDAO) {
		this.labTestDAO = labTestDAO;
	}

	public void setLabPreconditionDAO(LabPreconditionDAO labPreconditionDAO) {
		this.labPreconditionDAO = labPreconditionDAO;
	}

	public void setLabSpecimenTemplateDAO(LabSpecimenTemplateDAO labSpecimenTemplateDAO) {
		this.labSpecimenTemplateDAO = labSpecimenTemplateDAO;
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


	@Override
	public void deleteLabTestPanel(LabTestPanel labTestPanel, String reason)
			throws APIException {
		labTestPanel.setDateRetired(new Date());
		labTestPanel.setRetired(true);
		labTestPanel.setRetireReason(reason);
		labTestPanelDAO.saveLabTestPanel(labTestPanel);
		return;
	}

// ------------------------------------------------------

	
	public LabTest saveLabTest(LabTest labTest) throws APIException {
		return labTestDAO.saveLabTest(labTest);
	}

	public LabTest getLabTestByUUID(String uuid) {
		return labTestDAO.getLabTestByUuid(uuid);
	}

	public LabTest deleteLabTest(LabTest labTest,
			String deleteReason) throws APIException {
		labTest.setRetired(true);
		labTest.setDateRetired(new Date());
		labTest.setRetireReason(deleteReason);
		return labTestDAO.saveLabTest(labTest);
	}

	public void purgeLabTest(LabTest labTest) throws APIException {
		labTestDAO.deleteLabTest(labTest);
	}

	public LabTest retireLabTest(LabTest labTest, String retireReason)
			throws APIException {
		labTest.setRetired(true);
		labTest.setDateRetired(new Date());
		labTest.setRetireReason(retireReason);
		return labTestDAO.saveLabTest(labTest);
	}

	public List<LabTest> getAllLabTests(Boolean includeRetired)
			throws APIException {
		return labTestDAO.getAllLabTests(includeRetired);
	}

	public List<LabTest> getLabTests(String displayFragment,
			Boolean ifVoided, Integer index, Integer length) {
		//
		return labTestDAO.getLabTests(displayFragment,
				ifVoided, index, length);
	}

//------------------------------------------------------------	
	
	public LabPrecondition getLabPrecondition(Integer labPrecondition) {
		//
		return labPreconditionDAO.getLabPrecondition(labPrecondition);
	}

	public LabPrecondition getLabPreconditionByUuid(String uuid) {
		//
		return labPreconditionDAO.getLabPreconditionByUuid(uuid);
	}

	public LabPrecondition saveLabPrecondition(LabPrecondition labPrecondition)
			throws APIException {
		//
		return labPreconditionDAO.saveLabPrecondition(labPrecondition);
	}

	public LabPrecondition deleteLabPrecondition(LabPrecondition labPrecondition,
			String deleteReason) throws APIException {
		labPrecondition.setVoided(true);
		labPrecondition.setDateVoided(new Date());
		labPrecondition.setVoidReason(deleteReason);
		return labPreconditionDAO.saveLabPrecondition(labPrecondition);
	}

	public void purgeLabPrecondition(LabPrecondition labPrecondition)
			throws APIException {
		//
		labPreconditionDAO.deleteLabPrecondition(labPrecondition);
	}

	public List<LabPrecondition> getAllLabPreconditions(Boolean includeVoided) {
		return labPreconditionDAO.getLabPreconditions("", includeVoided, null, null);
	}
		
	public List<LabPrecondition> getLabPreconditions(String displayFragment,
			Boolean ifVoided, Integer index, Integer length) {
		return labPreconditionDAO.getLabPreconditions(displayFragment,
				ifVoided, index, length);
	}

	public Integer getCountOfLabPrecondition(String search, Boolean ifVoided)
			throws APIException {
		return labPreconditionDAO.getCountOfLabPreconditions(search, ifVoided);
	}

//------------------------------------------------------
	
	public LabSpecimenTemplate getLabSpecimenTemplate(Integer specimenTemplateId) {
		//
		return labSpecimenTemplateDAO.getLabSpecimenTemplate(specimenTemplateId);
	}

	public LabSpecimenTemplate getLabSpecimenTemplateByUuid(String uuid) {
		//
		return labSpecimenTemplateDAO.getLabSpecimenTemplateByUuid(uuid);
	}

	public LabSpecimenTemplate saveLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate)
			throws APIException {
		//
		return labSpecimenTemplateDAO.saveLabSpecimenTemplate(labSpecimenTemplate);
	}

	public LabSpecimenTemplate deleteLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate, String reason)
			throws APIException {
		labSpecimenTemplate.setVoided(true);
		labSpecimenTemplate.setVoidReason(reason);
		labSpecimenTemplate.setDateVoided(new Date());
		return labSpecimenTemplateDAO.saveLabSpecimenTemplate(labSpecimenTemplate);
	}

	public void purgeLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate)
			throws APIException {
		//
		labSpecimenTemplateDAO.deleteLabSpecimenTemplate(labSpecimenTemplate);
	}

	public LabSpecimenTemplate getLabSpecimenTemplateByName(String specimenTemplate) {
		//
		return labSpecimenTemplateDAO.getLabSpecimenTemplateByName(specimenTemplate);
	}

	@Override
	public List<LabSpecimenTemplate> getAllLabSpecimenTemplates() throws APIException {
		return labSpecimenTemplateDAO.getLabSpecimenTemplates("", false, null, null);
	}

	@Override
	public List<LabSpecimenTemplate> getAllLabSpecimenTemplates(Boolean ifVoided)
			throws APIException {
		return labSpecimenTemplateDAO.getLabSpecimenTemplates("", ifVoided, null, null);
	}

		public List<LabSpecimenTemplate> getLabSpecimenTemplate(String displayFragment,
				Boolean ifVoided, Integer index, Integer length) {
			//
			return labSpecimenTemplateDAO.getLabSpecimenTemplates(displayFragment,
					ifVoided, index, length);
		}

		public Integer getCountOfLabSpecimenTemplate(String search, Boolean ifVoided)
				throws APIException {
			//
			return labSpecimenTemplateDAO.getCountOfLabSpecimenTemplates(search, ifVoided);
		}


}
