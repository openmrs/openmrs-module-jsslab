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
import org.openmrs.api.APIException;
import org.openmrs.module.jsslab.db.LabInstrumentDAO;
import org.openmrs.module.jsslab.db.LabTestRun;

/**
 * Hibernate lab instrument related database functions
 */
public class HibernateLabInstrumentDAO implements LabInstrumentDAO {
	
	private SessionFactory sessionFactory;
	
	/**
	 * @see org.openmrs.api.db.LabInstrumentDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @see org.openmrs.api.db.LabInstrumentDAO#saveLabInstrument(org.openmrs.LabInstrument)
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
	 * @see org.openmrs.api.db.LabInstrumentDAO#getLabInstrument(java.lang.Integer)
	 */
	public LabInstrument getLabInstrument(Integer instrumentId) {
		return (LabInstrument) sessionFactory.getCurrentSession().get(LabInstrument.class, instrumentId);
	}
	
	/**
	 * @see org.openmrs.api.db.LabInstrumentDAO#getLabInstrumentByUuid(java.lang.String)
	 */
	public LabInstrument getLabInstrumentByUuid(String uuid) {
		return (LabInstrument) sessionFactory.getCurrentSession().createQuery("from LabInstrument l where l.uuid = :uuid").setString(
		    "uuid", uuid).uniqueResult();
	}

	/**
	 * @see org.openmrs.api.db.LabInstrumentDAO#getLabInstrument(java.lang.String)
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
	 * @see org.openmrs.api.db.LabInstrumentDAO#getAllLabInstruments(Boolean)
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
	 * @see org.openmrs.api.db.LabInstrumentDAO#deleteLabInstrument(org.openmrs.LabInstrument)
	 */
	public void deleteLabInstrument(LabInstrument instrument) throws APIException {
		if (instrument.getTestRuns().size() != 0)
			throw new APIException("Cannot delete a referenced lab instrument");
		sessionFactory.getCurrentSession().delete(instrument);
	}
	
	/**
	 * @see org.openmrs.api.db.LabInstrumentDAO#getCountOfLabInstruments(String, Boolean)
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
	 * @see LabInstrumentDAO#getLabInstruments(String, Boolean, Integer, Integer)
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
		
		return (List<LabInstrument>) criteria.list();
	}


// TODO: replace stubs below with real methods in LabTestingService
	private LabTestRun saveLabTestRun(LabTestRun testRun) { return testRun; }
	
}
