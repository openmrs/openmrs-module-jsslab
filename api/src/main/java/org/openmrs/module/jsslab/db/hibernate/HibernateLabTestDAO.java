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

import java.util.Collections;
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
 * Hibernate lab test related database functions
 */
public class HibernateLabTestDAO implements LabTestDAO {
	
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
	public LabTest getLabTest(Integer labTestId) {
		return (LabTest) sessionFactory.getCurrentSession().get(LabTest.class, labTestId);
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
	 */
	@SuppressWarnings("unchecked")
	public LabTest getLabTestByName(String name) {
		// get candidates
		List<LabTest> labTests= (List<LabTest>) sessionFactory.getCurrentSession()
				.createQuery("from LabTest as lt where lt.testConcept.names.name=:name and not lt.retired" )
				.setString("name", name);

		// eliminate those that don't really match (perhaps wrong locale)
		for (LabTest labTest : labTests)
			if (! labTest.getTestName().equalsIgnoreCase(name))
				labTests.remove(labTest);

		// if list is exhausted, return null
		if (null == labTests || labTests.isEmpty()) {
			return null;
		}
		
		// sort the possible returns and take the first
		Collections.sort(labTests);
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
		
		List<LabTest> list = (List<LabTest>) criteria.list();
		Collections.sort(list);
		return list;
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestDAO#getLabTests(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<LabTest> getLabTests(String name) {
		// get candidates
		List<LabTest> labTests= (List<LabTest>) sessionFactory.getCurrentSession()
				.createQuery("from LabTest as lt where upper(lt.testConcept.names.name) like concat(upper(:name),'%') and not lt.retired" )
				.setString("name", name);

		// eliminate those that don't really match (perhaps wrong locale)
		for (LabTest labTest : labTests)
			if (! labTest.getTestName().toUpperCase().startsWith(name.toUpperCase()))
				labTests.remove(labTest);

		// if list is exhausted, return null
		if (null == labTests || labTests.isEmpty()) {
			return null;
		}
		
		// sort the possible returns
		Collections.sort(labTests);
		return labTests;
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
	@SuppressWarnings("unchecked")
	public Integer getCountOfLabTests(String name, Boolean includeRetired) {
		// get candidates
		List<LabTest> labTests= (List<LabTest>) sessionFactory.getCurrentSession()
				.createQuery("from LabTest as lt where upper(lt.testConcept.names.name) like concat(upper(:name),'%') and (:retired or not lt.retired" )
				.setString("name", name).setBoolean("retired", includeRetired);

		// eliminate those that don't really match (perhaps wrong locale)
		for (LabTest labTest : labTests)
			if (! labTest.getTestName().toUpperCase().startsWith(name.toUpperCase()))
				labTests.remove(labTest);

		return labTests.size();
	}

// TODO: replace stubs below with real methods in LabTestingService
	private LabTestRange saveLabTestRange(LabTestRange labTestRange) { return labTestRange; }
	
}
