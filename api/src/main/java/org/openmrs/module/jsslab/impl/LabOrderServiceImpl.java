package org.openmrs.module.jsslab.impl;

import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Order;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jsslab.LabOrderService;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabOrderSpecimen;
import org.openmrs.module.jsslab.db.LabSpecimen;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.module.jsslab.db.LabOrderDAO;
import org.openmrs.module.jsslab.db.LabOrderSpecimenDAO;
import org.openmrs.module.jsslab.db.LabSpecimenDAO;
import org.openmrs.module.jsslab.db.LabSupplyItemDAO;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.validator.ValidateUtil;
import org.springframework.transaction.annotation.Transactional;

public class LabOrderServiceImpl extends BaseOpenmrsService implements
		LabOrderService {

	private final Log log = LogFactory.getLog(this.getClass());
	
	protected LabOrderDAO labOrderDAO;

	protected LabOrderSpecimenDAO labOrderSpecimenDAO;
	
	protected LabSpecimenDAO labSpecimenDAO;
	
	public void setLabOrderDAO(LabOrderDAO labOrderDAO) {
		this.labOrderDAO = labOrderDAO;
	}

	public void setLabOrderSpecimenDAO(LabOrderSpecimenDAO labOrderSpecimenDAO) {
		this.labOrderSpecimenDAO = labOrderSpecimenDAO;
	}

	public void setLabSpecimenDAO(LabSpecimenDAO labSpecimenDAO) {
		this.labSpecimenDAO = labSpecimenDAO;
	}

	public LabOrder saveLabOrder(LabOrder labOrder)
			throws APIException {
		if (labOrder == null)
			throw new APIException(Context.getMessageSourceService().getMessage("error.null"));
		ValidateUtil.validate(labOrder);
		return labOrderDAO.saveLabOrder(labOrder);
	}

	@Override
	public LabOrder getLabOrder(Integer labOrderId) {		
		return labOrderDAO.getLabOrder(labOrderId);
	}

	public LabOrder getLabOrderByUuid(String uuid) {
		return labOrderDAO.getLabOrderByUuid(uuid);
	}

	public void purgeLabOrder(LabOrder labOrder)
			throws APIException {
		labOrderDAO.deleteLabOrder(labOrder);
		
	}

	public LabOrder deleteLabOrder(LabOrder labOrder,
			String deleteReason) throws APIException {
		labOrder.setVoided(true);
		labOrder.setDateVoided(new Date());
		labOrder.setVoidReason(deleteReason);
		return labOrderDAO.saveLabOrder(labOrder);
	}

	public List<LabOrder> getAllLabOrders(Boolean includeDeleted)
			throws APIException {
		return labOrderDAO.getLabOrders("",includeDeleted,null,null);
	}

	public List<LabOrder> getAllLabOrders()
			throws APIException {
		return labOrderDAO.getLabOrders("",false,null,null);
	}

	/**
	 * Get all unretired LabOrders for a specified patient
	 * 
	 * @return LabOrders list
	 * @throws APIException
	 */
	public List<LabOrder> getLabOrdersByPatient(Patient patient) throws APIException {
		return labOrderDAO.getLabOrdersByPatient(patient,false);
	}
	
	public Integer getCountOfLabOrders(Boolean includeDeleted)
			throws APIException {
		return labOrderDAO.getCountOfLabOrders("", includeDeleted);
	}

	public Integer getCountOfLabOrders()
			throws APIException {
		return labOrderDAO.getCountOfLabOrders("", false);
	}

	/**
	 * Returns a specified number of labOrders starting with a given string from the specified index
	 * 
	 * @see LabOrderService#getLabOrders(String, Boolean, Integer, Integer)
	 */
	public List<LabOrder> getLabOrders(String nameFragment, Boolean includeDeleted, Integer start, Integer length) {
		return labOrderDAO.getLabOrders(nameFragment, includeDeleted, start, length);
		
	}
	
	public LabOrder voidLabOrder(LabOrder labOrder,
			String deleteReason) throws APIException {
		labOrder.setVoided(true);
		labOrder.setDateVoided(new Date());
		labOrder.setVoidReason(deleteReason);
		return labOrderDAO.saveLabOrder(labOrder);
	}

	@Override
	public LabOrderSpecimen saveLabOrderSpecimen(
			LabOrderSpecimen labOrderSpecimen) throws APIException {
		if (labOrderSpecimen == null)
			throw new APIException(Context.getMessageSourceService().getMessage("error.null"));
		ValidateUtil.validate(labOrderSpecimen);
		return labOrderSpecimenDAO.saveLabOrderSpecimen(labOrderSpecimen);
	}

	@Override
	public LabOrderSpecimen getLabOrderSpecimenByUuid(String uuid) {
		return labOrderSpecimenDAO.getLabOrderSpecimenByUuid(uuid);
	}

	@Override
	public void purgeLabOrderSpecimen(LabOrderSpecimen labOrderSpecimen)
			throws APIException {
		labOrderSpecimenDAO.deleteLabOrderSpecimen(labOrderSpecimen);
	}

	@Override
	public LabOrderSpecimen deleteLabOrderSpecimen(
			LabOrderSpecimen labOrderSpecimen, String voidReason)
			throws APIException {
		labOrderSpecimen.setVoided(true);
		labOrderSpecimen.setDateVoided(new Date());
		labOrderSpecimen.setVoidReason(voidReason);
		return labOrderSpecimenDAO.saveLabOrderSpecimen(labOrderSpecimen);
	}

	@Override
	public List<LabOrderSpecimen> getAllLabOrderSpecimens(Boolean includeVoided)
			throws APIException {
		return getLabOrderSpecimens("",includeVoided,null,null);
	}

	@Override
	public List<LabOrderSpecimen> getAllLabOrderSpecimens() throws APIException {
		return getLabOrderSpecimens("",false,null,null);
	}

	@Override
	public List<LabOrderSpecimen> getLabOrderSpecimens(String nameFragment,
			Boolean includeVoided, Integer start, Integer length) {
		return labOrderSpecimenDAO.getLabOrderSpecimens(nameFragment, includeVoided, start, length);
	}

	@Override
	public Integer getCountOfLabOrderSpecimens(Boolean includeRetired)
			throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getCountOfLabOrderSpecimens() throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	//------------------------------------------------------------	
		
		public LabSpecimen getLabSpecimen(Integer labSpecimen) {
			//
			return labSpecimenDAO.getLabSpecimen(labSpecimen);
		}

		public LabSpecimen getLabSpecimenByUuid(String uuid) {
			//
			return labSpecimenDAO.getLabSpecimenByUuid(uuid);
		}

		public LabSpecimen saveLabSpecimen(LabSpecimen labSpecimen)
				throws APIException {
			//
			if (labSpecimen == null)
				throw new APIException(Context.getMessageSourceService().getMessage("error.null"));
			ValidateUtil.validate(labSpecimen);
			return labSpecimenDAO.saveLabSpecimen(labSpecimen);
		}

		public LabSpecimen deleteLabSpecimen(LabSpecimen labSpecimen,
				String deleteReason) throws APIException {
			labSpecimen.setRetired(true);
			labSpecimen.setDateRetired(new Date());
			labSpecimen.setRetireReason(deleteReason);
			return labSpecimenDAO.saveLabSpecimen(labSpecimen);
		}

		public void purgeLabSpecimen(LabSpecimen labSpecimen)
				throws APIException {
			//
			labSpecimenDAO.deleteLabSpecimen(labSpecimen);
		}

		public List<LabSpecimen> getAllLabSpecimens(Boolean includeVoided) {
			return labSpecimenDAO.getLabSpecimens("", includeVoided, null, null);
		}
			
		public List<LabSpecimen> getLabSpecimens(String displayFragment,
				Boolean ifVoided, Integer index, Integer length) {
			return labSpecimenDAO.getLabSpecimens(displayFragment,
					ifVoided, index, length);
		}

		public Integer getCountOfLabSpecimen(String search, Boolean ifVoided)
				throws APIException {
			return labSpecimenDAO.getCountOfLabSpecimens(search, ifVoided);
		}


}
