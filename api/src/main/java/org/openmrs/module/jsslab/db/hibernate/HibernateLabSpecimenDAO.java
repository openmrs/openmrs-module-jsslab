/**
 * 
 */
package org.openmrs.module.jsslab.db.hibernate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.openmrs.module.jsslab.db.LabSpecimen;
import org.openmrs.module.jsslab.db.LabSpecimenDAO;
import org.openmrs.module.jsslab.db.LabTest;
import org.openmrs.module.jsslab.db.LabTestSpecimen;

/**
 *
 */
public class HibernateLabSpecimenDAO implements LabSpecimenDAO {

	private SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabSpecimenDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public class LabSpecimenComparator implements Comparator<LabSpecimen> {
		  public int compare(LabSpecimen ls1, LabSpecimen ls2) {
			  return ls1.getId().compareTo(ls2.getId());
		  }
		}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabSpecimenDAO#saveLabSpecimen(org.openmrs.module.jsslab.db.LabSpecimen)
	 */
	@Override
	public LabSpecimen saveLabSpecimen(LabSpecimen labSpecimen) {
		sessionFactory.getCurrentSession().saveOrUpdate(labSpecimen);
		return labSpecimen;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabSpecimenDAO#getLabSpecimen(java.lang.Integer)
	 */
	@Override
	public LabSpecimen getLabSpecimen(Integer specimenId) {
		return (LabSpecimen) sessionFactory.getCurrentSession().get(LabSpecimen.class, specimenId);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabSpecimenDAO#getLabSpecimenByUuid(java.lang.String)
	 */
	@Override
	public LabSpecimen getLabSpecimenByUuid(String uuid) {
		return (LabSpecimen) sessionFactory.getCurrentSession()
			.createQuery("from LabSpecimen l where l.uuid = :uuid")
			.setString("uuid", uuid).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabSpecimenDAO#getLabSpecimenByName(java.lang.String)
	 */
	@Override
	public LabSpecimen getLabSpecimenByName(String name) {
		List<LabSpecimen> labSpecimens = (List<LabSpecimen>) sessionFactory.getCurrentSession()
				.createQuery("from LabSpecimen as ls where :name in (ls.labSpecimenId,ls.clientSpecimenId) and not ls.retired")
				.setString("name", name);

		// if list is exhausted, return null
		if (null == labSpecimens || labSpecimens.isEmpty()) {
			return null;
		}
		return labSpecimens.get(0);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabSpecimenDAO#getLabSpecimens(java.lang.String, java.lang.Boolean, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<LabSpecimen> getLabSpecimens(String search,
			Boolean includeRetired, Integer start, Integer length) {
		// get candidates
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestSpecimen.class, "lts");

		if (!includeRetired) 
			criteria.add(Restrictions.ne("retired", true));

		if (StringUtils.isNotBlank(search))
			criteria.add(Restrictions.disjunction()
			.add(Restrictions.ilike("lts.labSpecimenId", search, MatchMode.START))
			.add(Restrictions.ilike("lts.clientSpecimenId", search, MatchMode.START))
			.add(Restrictions.ilike("lts.orderSpecimen.labOrder.labOrderID", search, MatchMode.START))
			);

		if (start != null)
			criteria.setFirstResult(start);
		if (length != null && length > 0)
			criteria.setMaxResults(length);
		
		criteria.addOrder(Order.asc("labSpecimenId"));
		return (List<LabSpecimen>) criteria.list();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabSpecimenDAO#deleteLabSpecimen(org.openmrs.module.jsslab.db.LabSpecimen)
	 */
	@Override
	public void deleteLabSpecimen(LabSpecimen labSpecimen) {
		sessionFactory.getCurrentSession().delete(labSpecimen);
		return;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabSpecimenDAO#getCountOfLabSpecimens(java.lang.String, java.lang.Boolean)
	 */
	@Override
	public Integer getCountOfLabSpecimens(String search,
			Boolean includeRetired) {
		List<LabSpecimen> labSpecimens = getLabSpecimens(search, includeRetired,0,0);
		return labSpecimens.size();
	}

}
