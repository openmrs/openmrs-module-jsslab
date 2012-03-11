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

import org.openmrs.module.jsslab.db.LabSpecimenTemplate;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.ConceptService;
import org.openmrs.module.jsslab.db.LabSpecimenTemplateDAO;
import org.openmrs.module.jsslab.db.LabTest;

/**
 * Hibernate lab labSpecimenTemplate related database functions
 */
public class HibernateLabSpecimenTemplateDAO implements LabSpecimenTemplateDAO {
	
	private SessionFactory sessionFactory;
	
	/**
	 * @see org.openmrs.api.db.LabSpecimenTemplateDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @see org.openmrs.api.db.LabSpecimenTemplateDAO#saveLabSpecimenTemplate(org.openmrs.LabSpecimenTemplate)
	 */
	public LabSpecimenTemplate saveLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate) {
		sessionFactory.getCurrentSession().saveOrUpdate(labSpecimenTemplate);
		return labSpecimenTemplate;
	}
	
	/**
	 * @see org.openmrs.api.db.LabSpecimenTemplateDAO#getLabSpecimenTemplate(java.lang.Integer)
	 */
	public LabSpecimenTemplate getLabSpecimenTemplate(Integer labSpecimenTemplateId) {
		return (LabSpecimenTemplate) sessionFactory.getCurrentSession().get(LabSpecimenTemplate.class, labSpecimenTemplateId);
	}
	
	/**
	 * @see org.openmrs.api.db.LabSpecimenTemplateDAO#getLabSpecimenTemplateByUuid(java.lang.String)
	 */
	public LabSpecimenTemplate getLabSpecimenTemplateByUuid(String uuid) {
		return (LabSpecimenTemplate) sessionFactory.getCurrentSession().createQuery("from LabSpecimenTemplate l where l.uuid = :uuid")
				.setString("uuid", uuid).uniqueResult();
	}

	/**
	 * @see org.openmrs.api.db.LabSpecimenTemplateDAO#getLabSpecimenTemplateByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public LabSpecimenTemplate getLabSpecimenTemplateByName(String name) {
		List<LabSpecimenTemplate> specimenTemplates = (List<LabSpecimenTemplate>) sessionFactory.getCurrentSession()
				.createQuery("from LabSpecimenTemplate").list();
		for (LabSpecimenTemplate specimenTemplate : specimenTemplates) {
			if (specimenTemplate.getName().equalsIgnoreCase(name))
				return specimenTemplate;
		}
		return null;
	}

	/**
	 * @see org.openmrs.api.db.LabSpecimenTemplateDAO#getLabSpecimenTemplate(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public LabSpecimenTemplate getLabSpecimenTemplate(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabSpecimenTemplate.class);
		
		List<LabSpecimenTemplate> labSpecimenTemplates = (List<LabSpecimenTemplate>) criteria.list();
		if (null == labSpecimenTemplates || labSpecimenTemplates.isEmpty()) {
			return null;
		}
		for (LabSpecimenTemplate lsp : labSpecimenTemplates) {
			if (lsp.getName().equals(name)) {
				return lsp;
			}
		}
		return null;
	}
	
	/**
	 * @see org.openmrs.api.db.LabSpecimenTemplateDAO#getAllLabSpecimenTemplates(Boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<LabSpecimenTemplate> getAllLabSpecimenTemplates(Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabSpecimenTemplate.class);
		if (!includeRetired) {
			criteria.add(Restrictions.eq("retired", false));
		}
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}
	
	/**
	 * @see org.openmrs.api.db.LabSpecimenTemplateDAO#deleteLabSpecimenTemplate(org.openmrs.LabSpecimenTemplate)
	 */
	public void deleteLabSpecimenTemplate(LabSpecimenTemplate labSpecimenTemplate) throws APIException {
		sessionFactory.getCurrentSession().delete(labSpecimenTemplate);
	}
	
	/**
	 * @see org.openmrs.api.db.LabSpecimenTemplateDAO#getCountOfLabSpecimenTemplates(String, Boolean)
	 */
	public Integer getCountOfLabSpecimenTemplates(String nameFragment, Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabSpecimenTemplate.class);
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
	 * @see LabSpecimenTemplateDAO#getLabSpecimenTemplates(String, Boolean, Integer, Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<LabSpecimenTemplate> getLabSpecimenTemplates(String nameFragment, Boolean includeVoided, Integer start, Integer length) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabSpecimenTemplate.class);
		if (!includeVoided)
			criteria.add(Restrictions.ne("retired", true));
		
		if (StringUtils.isNotBlank(nameFragment))
			criteria.add(Restrictions.disjunction()
				.add(Restrictions.eq("order.uuid", nameFragment))
				.add(Restrictions.eq("specimen.uuid", nameFragment))
			);
		
		criteria.addOrder(Order.asc("order.uuid")).addOrder(Order.asc("specimen.uuid"));
		
		if (start != null)
			criteria.setFirstResult(start);
		if (length != null && length > 0)
			criteria.setMaxResults(length);
		
		return (List<LabSpecimenTemplate>) criteria.list();
	}
	
}
