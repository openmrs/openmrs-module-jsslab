/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.jsslab.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Patient;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabOrderDAO;
import org.openmrs.module.jsslab.db.LabOrderSpecimen;
import org.openmrs.module.jsslab.db.LabSpecimen;

/**
 * This class should not be used directly. This is just a common implementation of the LabOrderDAO that
 * is used by the LabOrderService. This class is injected by spring into the desired LabOrderService
 * class. This injection is determined by the xml mappings and elements in the spring application
 * context: /metadata/api/spring/applicationContext.xml.<br/>
 * <br/>
 * The LabOrderService should be used for all LabOrder related database manipulation.
 * 
 */

public class HibernateLabOrderDAO implements LabOrderDAO {
	
	protected static final Log log = LogFactory.getLog(HibernateLabOrderDAO.class);
	
	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;
	
	public HibernateLabOrderDAO() {
	}
	
	/**
	 * Set session factory
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @see org.openmrs.api.db.LabOrderDAO#saveLabOrder(org.openmrs.LabOrder)
	 * @see org.openmrs.api.LabOrderService#saveLabOrder(org.openmrs.LabOrder)
	 */
	public LabOrder saveLabOrder(LabOrder labOrder) throws DAOException {
		sessionFactory.getCurrentSession().saveOrUpdate(labOrder);
		
		return labOrder;
	}
	
	/**
	 * @see org.openmrs.api.db.LabOrderDAO#getLabOrder(java.lang.Integer)
	 */
	public LabOrder getLabOrder(Integer labOrderId) throws DAOException {
		return (LabOrder) sessionFactory.getCurrentSession().get(LabOrder.class, labOrderId);
	}
	
	/**
	 * @see org.openmrs.api.db.LabOrderDAO#deleteLabOrder(org.openmrs.LabOrder)
	 * @see org.openmrs.api.LabOrderService#purgeLabOrder(org.openmrs.LabOrder)
	 */
	public void deleteLabOrder(LabOrder labOrder) throws DAOException {
		sessionFactory.getCurrentSession().delete(labOrder);
	}
	
	/**
	 * @see org.openmrs.api.db.LabOrderDAO#getLabOrderByUuid(java.lang.String)
	 */
	public LabOrder getLabOrderByUuid(String uuid) {
		return (LabOrder) sessionFactory.getCurrentSession().createCriteria(LabOrder.class)
		        .add(Restrictions.eq("uuid", uuid)).uniqueResult();
		}
	
	public List<LabOrder> getLabOrders(String search, Boolean includeDeleted, Integer start, Integer length)
			throws APIException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(LabOrder.class);

		if ((search != null) && ! search.isEmpty()) {
			crit.add(Restrictions.ilike("labOrderId", search, MatchMode.START));
		}
		if (!includeDeleted) {
			crit.add(Restrictions.ne("voided", true));
		}
		crit.addOrder(Order.asc("labOrderId")).addOrder(Order.asc("orderId"));
		if ((start != null)  && (start>0))
			crit.setFirstResult(start);
		if ((length != null) && (length > 0))
			crit.setMaxResults(length);
	
		return (List<LabOrder>) crit.list();
	}

	/**
	 * Returns all labOrders for a given patient, retired orders included based on flag
	 */
	public List<LabOrder> getLabOrdersByPatient(Patient patient, Boolean includeVoided) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(LabOrder.class);

		if (patient != null ) {
			crit.add(Restrictions.eq("Patient", patient));
		}
		if (!includeVoided) {
			crit.add(Restrictions.ne("voided", true));
		}
	
		return (List<LabOrder>) crit.list();
	}
	
	public Integer getCountOfLabOrders(String search,Boolean includeDeleted)
			throws APIException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(LabOrder.class);
		if ((search != null) && ! search.isEmpty()) {
			crit.add(Restrictions.ilike("labOrderId", search, MatchMode.START));
		}
		if (!includeDeleted) {
			crit.add(Restrictions.ne("voided", true));
		}
		crit.setProjection(Projections.rowCount());
		
		return (Integer) crit.uniqueResult();
	}
	public void createLink(String uuidL, String uuidR) {
		LabOrder labOrder = this.getLabOrderByUuid(uuidL);
		LabSpecimen labSpecimen = (LabSpecimen) sessionFactory.getCurrentSession().createQuery("from LabSpecimen s where s.uuid = :uuid")
				.setString("uuid",uuidR).uniqueResult();
		if ((labOrder!=null) && (labSpecimen!=null)) {
			LabOrderSpecimen labOrderSpecimen = (LabOrderSpecimen) sessionFactory.getCurrentSession().createCriteria(LabOrderSpecimen.class)
					.add(Restrictions.eq("labOrder", labOrder))
					.add(Restrictions.eq("labSpecimen", labSpecimen))
					.uniqueResult();
			if (labOrderSpecimen==null) {
				labOrderSpecimen=new LabOrderSpecimen();
				labOrderSpecimen.setOrder(labOrder);
				labOrderSpecimen.setSpecimen(labSpecimen);
				sessionFactory.getCurrentSession().save(labOrderSpecimen);
			}
		}
	}

	public void deleteLink(String uuidL, String uuidR) {
		LabOrder labOrder = this.getLabOrderByUuid(uuidL);
		LabSpecimen labSpecimen = (LabSpecimen) sessionFactory.getCurrentSession().createQuery("from LabSpecimen s where s.uuid = :uuid")
				.setString("uuid",uuidR).uniqueResult();
		if ((labOrder!=null) && (labSpecimen!=null)) {
			LabOrderSpecimen labOrderSpecimen = (LabOrderSpecimen) sessionFactory.getCurrentSession().createCriteria(LabOrderSpecimen.class)
					.add(Restrictions.eq("labOrder", labOrder))
					.add(Restrictions.eq("labSpecimen", labSpecimen))
					.uniqueResult();
			if (labOrderSpecimen!=null) {
				sessionFactory.getCurrentSession().delete(labOrderSpecimen);
			}
		}
	}
}
