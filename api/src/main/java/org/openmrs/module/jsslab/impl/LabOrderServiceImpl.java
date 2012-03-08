package org.openmrs.module.jsslab.impl;

import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jsslab.LabOrderService;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabOrderSpecimen;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.module.jsslab.db.LabOrderDAO;
import org.openmrs.module.jsslab.db.hibernate.HibernateLabOrderDAO;
import org.openmrs.module.jsslab.db.LabOrderSpecimenDAO;
import org.openmrs.module.jsslab.db.hibernate.HibernateLabOrderSpecimenDAO;
import org.openmrs.module.jsslab.db.LabSupplyItemDAO;
import org.openmrs.module.jsslab.db.hibernate.HibernateLabSupplyItemDAO;

public class LabOrderServiceImpl extends BaseOpenmrsService implements
		LabOrderService {

	private final Log log = LogFactory.getLog(this.getClass());
	
	protected LabOrderDAO labOrderDAO;

	protected LabOrderSpecimenDAO labOrderSpecimenDAO;
	
	public void setLabOrderDAO(LabOrderDAO labOrderDAO) {
		this.labOrderDAO = labOrderDAO;
	}

	public void setLabOrderSpecimenDAO(LabOrderSpecimenDAO labOrderSpecimenDAO) {
		this.labOrderSpecimenDAO = labOrderSpecimenDAO;
	}

	public LabOrder saveLabOrder(LabOrder labOrder)
			throws APIException {
		return labOrderDAO.saveLabOrder(labOrder);
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
	
	@Override
	public LabOrderSpecimen saveLabOrderSpecimen(
			LabOrderSpecimen labOrderSpecimen) throws APIException {
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


}
