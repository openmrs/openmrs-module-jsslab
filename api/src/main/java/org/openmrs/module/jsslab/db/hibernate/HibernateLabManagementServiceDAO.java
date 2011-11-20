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

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.api.APIException;
import org.openmrs.module.jsslab.db.LabManagementServiceDAO;
import org.openmrs.module.jsslab.db.LabTestRun;
import org.openmrs.module.jsslab.db.LabTestSpecimen;

/**
 * Hibernate lab management related database functions
 */
public class HibernateLabManagementServiceDAO implements LabManagementServiceDAO {
	
	private SessionFactory sessionFactory;
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#saveLabInstrument(org.openmrs.LabInstrument)
	 */
	public LabInstrument saveLabInstrument(LabInstrument instrument) {
		if (instrument.getTestRuns() != null && instrument.getId() != null) {
			// Hibernate has a problem updating child collections
			// if the parent object was already saved so we do it 
			// explicitly here
			for (LabTestRun child : instrument.getTestRuns())
				if (child.getId() == null)
					saveLabTestRun(child);
		}
		
		sessionFactory.getCurrentSession().saveOrUpdate(instrument);
		return instrument;
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getLabInstrument(java.lang.Integer)
	 */
	public LabInstrument getLabInstrument(Integer instrumentId) {
		return (LabInstrument) sessionFactory.getCurrentSession().get(LabInstrument.class, instrumentId);
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getLabInstrumentByUuid(java.lang.String)
	 */
	public LabInstrument getLabInstrumentByUuid(String uuid) {
		return (LabInstrument) sessionFactory.getCurrentSession().createQuery("from LabInstrument l where l.uuid = :uuid").setString(
		    "uuid", uuid).uniqueResult();
	}

	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getLabInstrument(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public LabInstrument getLabInstrument(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabInstrument.class)
		.add(Restrictions.disjunction()
		    .add(Restrictions.eq("propertyTag", name))
		    .add(Restrictions.eq("serialNumber", name))
		    .add(Restrictions.eq("model", name))
		);
		
		List<LabInstrument> instruments = criteria.list();
		if (null == instruments || instruments.isEmpty()) {
			return null;
		}
		return instruments.get(0);
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getAllLabInstruments(Boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<LabInstrument> getAllLabInstruments(Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabInstrument.class);
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#deleteLabInstrument(org.openmrs.LabInstrument)
	 */
	public void deleteLabInstrument(LabInstrument instrument) throws APIException {
		if (instrument.getTestRuns().size() != 0)
			throw new APIException("Cannot delete a referenced lab instrument");
		sessionFactory.getCurrentSession().delete(instrument);
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getCountOfLabInstruments(String, Boolean)
	 */
	public Integer getCountOfLabInstruments(String nameFragment, Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabInstrument.class);
		if (!includeRetired)
			criteria.add(Restrictions.eq("retired", false));
		
		if (StringUtils.isNotBlank(nameFragment))
			criteria.add(Restrictions.disjunction()
			   .add(Restrictions.ilike("propertyTag", nameFragment, MatchMode.START))
			   .add(Restrictions.ilike("serialNumber", nameFragment, MatchMode.START))
			   .add(Restrictions.ilike("model", nameFragment, MatchMode.START))
			);
		
		criteria.setProjection(Projections.rowCount());
		
		return (Integer) criteria.uniqueResult();
	}

	/**
	 * @see LabManagementServiceDAO#getLabInstruments(String, Boolean, Integer, Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<LabInstrument> getLabInstruments(String nameFragment, Boolean includeRetired, Integer start, Integer length) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabInstrument.class);
		if (!includeRetired)
			criteria.add(Restrictions.eq("retired", false));
		
		if (StringUtils.isNotBlank(nameFragment))
			criteria.add(Restrictions.disjunction()
			   .add(Restrictions.ilike("propertyTag", nameFragment, MatchMode.START))
			   .add(Restrictions.ilike("serialNumber", nameFragment, MatchMode.START))
			   .add(Restrictions.ilike("model", nameFragment, MatchMode.START))
			);
		
		criteria.addOrder(Order.asc("name"));
		if (start != null)
			criteria.setFirstResult(start);
		if (length != null && length > 0)
			criteria.setMaxResults(length);
		
		return criteria.list();
	}

	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#saveLabSupplyItem(org.openmrs.LabSupplyItem)
	 */
	public LabSupplyItem saveLabSupplyItem(LabSupplyItem supplyItem) {

		if (supplyItem.getTestSpecimens() != null && supplyItem.getId() != null) {
			// Hibernate has a problem updating child collections
			// if the parent object was already saved so we do it 
			// explicitly here
			for (LabTestSpecimen child : supplyItem.getTestSpecimens())
				if (child.getId() == null)
					saveLabTestSpecimen(child);
		}
		
		sessionFactory.getCurrentSession().saveOrUpdate(supplyItem);
		return supplyItem;
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getLabSupplyItem(java.lang.Integer)
	 */
	public LabSupplyItem getLabSupplyItem(Integer instrumentTagId) {
		return (LabSupplyItem) sessionFactory.getCurrentSession().get(LabSupplyItem.class, instrumentTagId);
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getLabSupplyItemByUuid(java.lang.String)
	 */
	public LabSupplyItem getLabSupplyItemByUuid(String uuid) {
		return (LabSupplyItem) sessionFactory.getCurrentSession().createQuery("from LabSupplyItem where uuid = :uuid")
		        .setString("uuid", uuid).uniqueResult();
	}

	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getLabSupplyItemByName(java.lang.String)
	 * will return the non-retired supply item with the earliest expiration date
	 */
	@SuppressWarnings("unchecked")
	public LabSupplyItem getLabSupplyItemByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabSupplyItem.class)
		.add(Restrictions.eq("itemName", name))
		.add(Restrictions.ne("retired", true))
		.addOrder(Order.asc("expirationDate"));
		
		List<LabSupplyItem> supplyItems = criteria.list();
		if (null == supplyItems || supplyItems.isEmpty()) {
			return null;
		}
		return supplyItems.get(0);
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getAllLabSupplyItems(Boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<LabSupplyItem> getAllLabSupplyItems(Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabSupplyItem.class);
		if (!includeRetired) {
			criteria.add(Restrictions.ne("retired", true));
		}
		criteria.addOrder(Order.asc("itemName")).addOrder(Order.desc("expirationDate"));
		
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getLabSupplyItems(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<LabSupplyItem> getLabSupplyItems(String search) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabSupplyItem.class)
			.add(Restrictions.disjunction()
		// 'ilike' case insensitive search
		        .add(Restrictions.ilike("itemName", search, MatchMode.START))
		        .add(Restrictions.ilike("labStockNumber", search, MatchMode.START))
		    );
		criteria.addOrder(Order.asc("itemName")).addOrder(Order.desc("expirationDate"));
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#deleteLabSupplyItem(org.openmrs.LabSupplyItem)
	 */
	public void deleteLabSupplyItem(LabSupplyItem supplyItem) throws APIException {
		if (supplyItem.getTestSpecimens().size() != 0)
			throw new APIException("Cannot delete a referenced supply item");

		sessionFactory.getCurrentSession().delete(supplyItem);
	}

	/**
	 * @see org.openmrs.api.db.LabManagementServiceDAO#getCountOfSupplyItems(String, Boolean)
	 */
	public Integer getCountOfLabSupplyItems(String nameFragment, Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabInstrument.class);
		if (!includeRetired)
			criteria.add(Restrictions.eq("retired", false));
		
		if (StringUtils.isNotBlank(nameFragment))
			criteria.add(Restrictions.disjunction()
			        .add(Restrictions.ilike("itemName", nameFragment, MatchMode.START))
			        .add(Restrictions.ilike("labStockNumber", nameFragment, MatchMode.START))
			);
		
		criteria.setProjection(Projections.rowCount());
		
		return (Integer) criteria.uniqueResult();
	}

// TODO: replace stubs below with real methods in LabTestingService
	private LabTestRun saveLabTestRun(LabTestRun testRun) { return testRun; }
	private LabTestSpecimen saveLabTestSpecimen(LabTestSpecimen testSpecimen) { return testSpecimen; }
	
}
