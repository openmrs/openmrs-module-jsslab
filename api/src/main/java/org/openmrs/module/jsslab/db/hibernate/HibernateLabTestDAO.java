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
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.api.APIException;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.db.LabTestDAO;
import org.openmrs.module.jsslab.db.LabTestRange;

/**
 * Hibernate lab management related database functions
 */
public class HibernateLabTestDAO implements LabTestDAO {
	
	private ConceptService conceptService = Context.getConceptService();
	
	private SessionFactory sessionFactory;
	
	/**
	 * @see org.openmrs.api.db.LabTestDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestDAO#saveLabTest(org.openmrs.LabTest)
	 */
	public LabTest saveLabTest(LabTest labTest) {

		if (labTest.getLabTestRanges() != null && labTest.getId() != null) {
			// Hibernate has a problem updating child collections
			// if the parent object was already saved so we do it 
			// explicitly here
			for (LabTestRange child : labTest.getLabTestRanges())
				if (child.getId() == null)
					saveLabTestRange(child);
		}
		
		sessionFactory.getCurrentSession().saveOrUpdate(labTest);
		return labTest;
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestDAO#getLabTest(java.lang.Integer)
	 */
	public LabTest getLabTest(Integer instrumentTagId) {
		return (LabTest) sessionFactory.getCurrentSession().get(LabTest.class, instrumentTagId);
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestDAO#getLabTestByUuid(java.lang.String)
	 */
	public LabTest getLabTestByUuid(String uuid) {
		return (LabTest) sessionFactory.getCurrentSession().createQuery("from LabTest where uuid = :uuid")
		        .setString("uuid", uuid).uniqueResult();
	}

	/**
	 * @see org.openmrs.api.db.LabTestDAO#getLabTestByName(java.lang.String)
	 * will return the non-retired supply item with the earliest expiration date
	 */
	@SuppressWarnings("unchecked")
	public LabTest getLabTestByName(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class)
		.add(Restrictions.eq("itemName", name))
		.add(Restrictions.ne("retired", true))
		.addOrder(Order.asc("expirationDate"));
		
		List<LabTest> labTests = criteria.list();
		if (null == labTests || labTests.isEmpty()) {
			return null;
		}
		return labTests.get(0);
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestDAO#getAllLabTests(Boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<LabTest> getAllLabTests(Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
		if (!includeRetired) {
			criteria.add(Restrictions.ne("retired", true));
		}
		criteria.addOrder(Order.asc("itemName")).addOrder(Order.desc("expirationDate"));
		
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestDAO#getLabTests(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<LabTest> getLabTests(String search) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class)
			.add(Restrictions.disjunction()
		// 'ilike' case insensitive search
		        .add(Restrictions.ilike("itemName", search, MatchMode.START))
		        .add(Restrictions.ilike("labStockNumber", search, MatchMode.START))
		    );
		criteria.addOrder(Order.asc("itemName")).addOrder(Order.desc("expirationDate"));
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestDAO#deleteLabTest(org.openmrs.LabTest)
	 */
	public void deleteLabTest(LabTest labTest) throws APIException {
		if (labTest.getLabTestRanges().size() != 0)
			throw new APIException("Cannot delete a referenced supply item");

		sessionFactory.getCurrentSession().delete(labTest);
	}

	/**
	 * @see org.openmrs.api.db.LabTestDAO#getCountOfSupplyItems(String, Boolean)
	 */
	public Integer getCountOfLabTests(String nameFragment, Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTest.class);
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
	private LabTestRange saveLabTestRange(LabTestRange labTestRange) { return labTestRange; }
	
}
