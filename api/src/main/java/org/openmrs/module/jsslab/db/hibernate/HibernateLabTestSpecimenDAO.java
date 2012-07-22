/**
 * 
 */
package org.openmrs.module.jsslab.db.hibernate;

import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.jsslab.db.LabTestSpecimen;
import org.openmrs.module.jsslab.db.LabTestSpecimenDAO;

/**
 *
 */
public class HibernateLabTestSpecimenDAO implements LabTestSpecimenDAO {

	/**
	 * Hibernate session factory
	 */
	private SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestSpecimenDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public class LabTestSpecimenComparator implements Comparator<LabTestSpecimen> {
		 public int compare(LabTestSpecimen lts1, LabTestSpecimen lts2) {
			  int i = lts1.getSpecimen().getLabSpecimenId().compareToIgnoreCase(lts2.getSpecimen().getLabSpecimenId()); 
			  if (i != 0)
				  return i;
			  else
				  return lts1.getSpecimenSubId().compareTo(lts2.getSpecimenSubId());
		}
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestSpecimenDAO#saveLabTestSpecimen(org.openmrs.module.jsslab.db.LabTestSpecimen)
	 */
	@Override
	public LabTestSpecimen saveLabTestSpecimen(LabTestSpecimen labTestSpecimen) {
		sessionFactory.getCurrentSession().saveOrUpdate(labTestSpecimen);
		return labTestSpecimen;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestSpecimenDAO#getLabTestSpecimen(java.lang.Integer)
	 */
	@Override
	public LabTestSpecimen getLabTestSpecimen(Integer testSpecimenId) {
		return (LabTestSpecimen) sessionFactory.getCurrentSession().get(LabTestSpecimen.class, testSpecimenId);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestSpecimenDAO#getLabTestSpecimenByUuid(java.lang.String)
	 */
	@Override
	public LabTestSpecimen getLabTestSpecimenByUuid(String uuid) {
		return (LabTestSpecimen) sessionFactory.getCurrentSession().createCriteria(LabTestSpecimen.class)
				.add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestSpecimenDAO#getLabTestSpecimenByName(java.lang.String)
	 */
	@Override
	public LabTestSpecimen getLabTestSpecimenByName(String name) {
		// get candidates
		List<LabTestSpecimen> labTestSpecimens = (List<LabTestSpecimen>) sessionFactory.getCurrentSession()
				.createQuery("from LabTestSpecimen as lts where :name in (lts.labSpecimenId,lts.clientSpecimenId) and not lts.retired")
				.setString("name", name).list();

		// if list is exhausted, return null
		if (null == labTestSpecimens || labTestSpecimens.isEmpty()) {
			return null;
		}
		return labTestSpecimens.get(0);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestSpecimenDAO#getLabTestSpecimens(java.lang.String, java.lang.Boolean, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<LabTestSpecimen> getLabTestSpecimens(String search,
			Boolean includeRetired, Integer start, Integer length) {
		// get candidates
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestSpecimen.class, "lts");

		if (!includeRetired) 
			criteria.add(Restrictions.ne("retired", true));

		if (StringUtils.isNotBlank(search))
			//TODO review criteria - the ones commented out seem to belong to LabSpecimen
			criteria.add(Restrictions.disjunction()
			.add(Restrictions.ilike("lts.testSpecimenId", search, MatchMode.START))
//			.add(Restrictions.ilike("lts.clientSpecimenId", search, MatchMode.START))
//			.add(Restrictions.ilike("lts.orderSpecimen.labOrder.labOrderID", search, MatchMode.START))
			);

		if (start != null)
			criteria.setFirstResult(start);
		if (length != null && length > 0)
			criteria.setMaxResults(length);
		
		criteria.addOrder(Order.asc("testSpecimenId"));
		return (List<LabTestSpecimen>) criteria.list();
		
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestSpecimenDAO#deleteLabTestSpecimen(org.openmrs.module.jsslab.db.LabTestSpecimen)
	 */
	@Override
	public void deleteLabTestSpecimen(LabTestSpecimen labTestSpecimen) {
		sessionFactory.getCurrentSession().delete(labTestSpecimen);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestSpecimenDAO#getCountOfLabTestSpecimens(java.lang.String, java.lang.Boolean)
	 */
	@Override
	public Integer getCountOfLabTestSpecimens(String search,
			Boolean includeRetired) {
		List<LabTestSpecimen> labTestSpecimens = getLabTestSpecimens(search, includeRetired,0,0);
		return labTestSpecimens.size();
	}

}
