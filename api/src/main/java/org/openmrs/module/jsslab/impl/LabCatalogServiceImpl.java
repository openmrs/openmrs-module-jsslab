package org.openmrs.module.jsslab.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabPrecondition;
import org.openmrs.module.jsslab.db.LabPreconditionDAO;
import org.openmrs.module.jsslab.db.LabSpecimenTemplate;
import org.openmrs.module.jsslab.db.LabSpecimenTemplateDAO;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.jsslab.db.LabTestDAO;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.module.jsslab.db.LabTestPanelDAO;
import org.openmrs.validator.ValidateUtil;

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

// ------------------------------------------------------

	
	public LabTest saveLabTest(LabTest labTest) throws APIException {
		if (labTest.getTestConcept() == null) {
			throw new APIException(Context.getMessageSourceService().getMessage("JSSLab.LabCatalogServiceImpl.saveLabTest.TestConceptRequired", null,
				"Reference to a Concept is required", Context.getLocale()));
		}
		
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

	@Override
	public Integer getCountOfLabTest(Boolean includeRetired)
			throws APIException {
		return labTestPanelDAO.getLabTestPanels("", includeRetired, null, null).size();
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
		if (labPrecondition == null)
			throw new APIException(Context.getMessageSourceService().getMessage("error.null"));
		ValidateUtil.validate(labPrecondition);
		return labPreconditionDAO.saveLabPrecondition(labPrecondition);
	}

	public LabPrecondition voidLabPrecondition(LabPrecondition labPrecondition,
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
		return labPreconditionDAO.getLabPreconditions("", includeVoided, 0, 0);
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

	public LabSpecimenTemplate retireLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate, String reason)
			throws APIException {
		labSpecimenTemplate.setRetired(true);
		labSpecimenTemplate.setRetireReason(reason);
		labSpecimenTemplate.setDateRetired(new Date());
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
		return labSpecimenTemplateDAO.getLabSpecimenTemplates("", false, 0, 0);
	}

	@Override
	public List<LabSpecimenTemplate> getAllLabSpecimenTemplates(Boolean ifVoided)
			throws APIException {
		return labSpecimenTemplateDAO.getLabSpecimenTemplates("", ifVoided, 0, 0);
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
		//--------------------------------------------------------
		
		@Override
		public LabTestPanel getLabTestPanel(Integer labTestPanelId) {
			return labTestPanelDAO.getLabTestPanel(labTestPanelId);
		}

		@Override
		public LabTestPanel getLabTestPanelByUuid(String uuid) {
			return labTestPanelDAO.getLabTestPanelByUuid(uuid);
		}

		@Override
		public LabTestPanel saveLabTestPanel(LabTestPanel labTestPanel)
				throws APIException {
			return labTestPanelDAO.saveLabTestPanel(labTestPanel);
		}

		@Override
		public void purgeLabTestPanel(LabTestPanel labTestPanel)
				throws APIException {
			labTestPanelDAO.deleteLabTestPanel(labTestPanel);
			
		}

		@Override
		public LabTestPanel retireLabTestPanel(
				LabTestPanel labTestPanel, String retireReason)
				throws APIException {
			labTestPanel.setRetired(true);
			labTestPanel.setDateRetired(new Date());
			labTestPanel.setRetireReason(retireReason);
			return labTestPanelDAO.saveLabTestPanel(labTestPanel);
		}

		@Override
		public List<LabTestPanel> getAllLabTestPanels(Boolean includeVoided)
				throws APIException {
			return labTestPanelDAO.getLabTestPanels("",includeVoided,null,null);
		}

		@Override
		public List<LabTestPanel> getAllLabTestPanels() throws APIException {
			return labTestPanelDAO.getLabTestPanels("",false,null,null);
		}

		@Override
		public List<LabTestPanel> getLabTestPanels(String nameFragment,
				Boolean includeVoided, Integer start, Integer length) {
			return labTestPanelDAO.getLabTestPanels(nameFragment,
					includeVoided, start, length);
		}

		@Override
		public Integer getCountOfLabTestPanels(Boolean includeRetired)
				throws APIException {
			return labTestPanelDAO.getLabTestPanels("", includeRetired, null, null).size();
		}


		


}
