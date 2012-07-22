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

import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.api.APIException;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.jsslab.db.LabTestPanel;
import org.openmrs.module.jsslab.db.LabTestPanelDAO;

/**
 * Hibernate lab labTestPanel related database functions
 */
public class HibernateLabTestPanelDAO implements LabTestPanelDAO {
	
	private SessionFactory sessionFactory;
	
	/**
	 * @see org.openmrs.api.db.LabTestPanelDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public class LabTestPanelComparator implements Comparator<LabTestPanel> {
		  public int compare(LabTestPanel lt1, LabTestPanel lt2) {
			  return lt1.getTestPanelConcept().getId().compareTo(lt2.getTestPanelConcept().getId());
		  }
		}

	/**
	 * @see org.openmrs.api.db.LabTestPanelDAO#saveLabTestPanel(org.openmrs.LabTestPanel)
	 */
	public LabTestPanel saveLabTestPanel(LabTestPanel labTestPanel) {
		if (labTestPanel.getTests() != null && labTestPanel.getId() != null) {
			// Hibernate has a problem updating child collections
			// if the parent object was already saved so we do it 
			// explicitly here
			for (LabTest child : labTestPanel.getTests())
				if (child.getId() == null)
					saveLabTest(child);
		}
		
		sessionFactory.getCurrentSession().saveOrUpdate(labTestPanel);
		return labTestPanel;
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestPanelDAO#getLabTestPanel(java.lang.Integer)
	 */
	public LabTestPanel getLabTestPanel(Integer labTestPanelId) {
		return (LabTestPanel) sessionFactory.getCurrentSession().get(LabTestPanel.class, labTestPanelId);
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestPanelDAO#getLabTestPanelByUuid(java.lang.String)
	 */
	public LabTestPanel getLabTestPanelByUuid(String uuid) {
		return (LabTestPanel) sessionFactory.getCurrentSession().createCriteria(LabTestPanel.class)
				.add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	/**
	 * @see org.openmrs.api.db.LabTestPanelDAO#getLabTestPanel(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public LabTestPanel getLabTestPanel(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestPanel.class)
		.add(Restrictions.disjunction()
		    .add(Restrictions.eq("propertyTag", name))
		    .add(Restrictions.eq("serialNumber", name))
		    .add(Restrictions.eq("model", name))
		);
		
		List<LabTestPanel> labTestPanels = criteria.list();
		if (null == labTestPanels || labTestPanels.isEmpty()) {
			return null;
		}
		return labTestPanels.get(0);
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestPanelDAO#getAllLabTestPanels(Boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<LabTestPanel> getAllLabTestPanels(Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestPanel.class);
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestPanelDAO#deleteLabTestPanel(org.openmrs.LabTestPanel)
	 */
	public void deleteLabTestPanel(LabTestPanel labTestPanel) throws APIException {
		if (labTestPanel.getTests().size() != 0)
			throw new APIException("Cannot delete a referenced lab labTestPanel");
		sessionFactory.getCurrentSession().delete(labTestPanel);
	}
	
	/**
	 * @see org.openmrs.api.db.LabTestPanelDAO#getCountOfLabTestPanels(String, Boolean)
	 */
	public Integer getCountOfLabTestPanels(String nameFragment, Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestPanel.class);
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
	 * @see LabTestPanelDAO#getLabTestPanels(String, Boolean, Integer, Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<LabTestPanel> getLabTestPanels(String nameFragment, Boolean includeRetired, Integer start, Integer length) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestPanel.class);
		if (!includeRetired)
			criteria.add(Restrictions.ne("retired", true));
		
		criteria.addOrder(Order.asc("testPanelId"));
		
		if (start != null)
			criteria.setFirstResult(start);
		if (length != null && length > 0)
			criteria.setMaxResults(length);
		
		List<LabTestPanel> list = (List<LabTestPanel>) criteria.list();
		for (int i = 0; i < list.size(); ) {
			LabTestPanel lp = list.get(i);
			if (!lp.getTestPanelConcept().getName().getName().startsWith(nameFragment))
				list.remove(lp);
			else
				i++;
		}
		return list;
	}


// TODO: replace stubs below with real methods in LabTestingService
	private LabTest saveLabTest(LabTest labTestRange) { return labTestRange; }
	
}
