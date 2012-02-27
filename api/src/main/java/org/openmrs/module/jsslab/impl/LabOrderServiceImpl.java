package org.openmrs.module.jsslab.impl;

import java.util.List;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.APIException;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.jsslab.LabOrderService;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.module.jsslab.db.LabOrderDAO;
import org.openmrs.module.jsslab.db.hibernate.HibernateLabOrderDAO;
import org.openmrs.module.jsslab.db.LabSupplyItemDAO;
import org.openmrs.module.jsslab.db.hibernate.HibernateLabSupplyItemDAO;

public class LabOrderServiceImpl extends BaseOpenmrsService implements
		LabOrderService {

	private final Log log = LogFactory.getLog(this.getClass());
	
	protected LabOrderDAO labOrderDAO;
	
	public void setLabOrderDAO(LabOrderDAO labOrderDAO) {
		this.labOrderDAO = labOrderDAO;
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
	

}
