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
import org.openmrs.module.jsslab.db.LabPrecondition;
import org.openmrs.module.jsslab.db.LabPreconditionDAO;
import org.openmrs.module.jsslab.db.LabTest;

/**
 *
 */
public class HibernateLabPreconditionDAO implements LabPreconditionDAO {

	private SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabPreconditionDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public class LabPreconditionComparator implements Comparator<LabPrecondition> {
		  public int compare(LabPrecondition lp1, LabPrecondition lp2) {
			  int i =  lp1.getTestPanel().getTestPanelConcept().getId().compareTo(lp2.getTestPanel().getTestPanelConcept().getId());
			  if (i != 0)
				  return i;
			  return lp1.getSortWeight().compareTo(lp2.getSortWeight());
		  }
		}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabPreconditionDAO#saveLabPrecondition(org.openmrs.module.jsslab.db.LabPrecondition)
	 */
	@Override
	public LabPrecondition saveLabPrecondition(LabPrecondition labPrecondition) {
		sessionFactory.getCurrentSession().saveOrUpdate(labPrecondition);
		return labPrecondition;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabPreconditionDAO#getLabPrecondition(java.lang.Integer)
	 */
	@Override
	public LabPrecondition getLabPrecondition(Integer preconditionId) {
		return (LabPrecondition) sessionFactory.getCurrentSession().get(LabPrecondition.class, preconditionId);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabPreconditionDAO#getLabPreconditionByUuid(java.lang.String)
	 */
	@Override
	public LabPrecondition getLabPreconditionByUuid(String uuid) {
		return (LabPrecondition) sessionFactory.getCurrentSession()
			.createQuery("from LabPrecondition l where l.uuid = :uuid")
			.setString("uuid", uuid).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabPreconditionDAO#getLabPreconditionByName(java.lang.String)
	 */
	@Override
	public LabPrecondition getLabPreconditionByName(String name) {
		// get candidates
		List<LabPrecondition> labPreconditions = (List<LabPrecondition>) sessionFactory.getCurrentSession()
				.createQuery("from LabPrecondition as lp where lp.preconditionQuestionConcept.names.name=:name and not lp.retired" )
				.setString("name", name);

		// eliminate those that don't really match (perhaps wrong locale)
		for (LabPrecondition labPrecondition : labPreconditions)
			if (! labPrecondition.getPreconditionQuestionText().equalsIgnoreCase(name))
				labPreconditions.remove(labPrecondition);

		// if list is exhausted, return null
		if (null == labPreconditions || labPreconditions.isEmpty()) {
			return null;
		}
		
		// sort the possible returns and take the first
		Collections.sort(labPreconditions,new LabPreconditionComparator());
		return labPreconditions.get(0);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabPreconditionDAO#getLabPreconditions(java.lang.String, java.lang.Boolean, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<LabPrecondition> getLabPreconditions(String search,
			Boolean includeRetired, Integer start, Integer length) {
		// get candidates
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabPrecondition.class, "lp");

		if (!includeRetired) 
			criteria.add(Restrictions.ne("retired", true));

		if (StringUtils.isNotBlank(search))
			criteria.add(Restrictions.disjunction()
			.add(Restrictions.ilike("lp.preconditionQuestionConcept.names.name", search, MatchMode.START))
			.add(Restrictions.ilike("lp.testPanelConcept.names.name", search, MatchMode.START))
			);

		List<LabPrecondition> labPreconditions = (List<LabPrecondition>) criteria.list();
		
		// eliminate those that don't really match (perhaps wrong locale)
		for (LabPrecondition labPrecondition : labPreconditions) {
			if (! ((labPrecondition.getPreconditionQuestionText().startsWith(search)) 
			|| (labPrecondition.getTestPanel().getName().startsWith(search))))
				labPreconditions.remove(labPrecondition);
		}
		
		// sort the possible returns and take the first
		Collections.sort(labPreconditions,new LabPreconditionComparator());
		if ((start == 0) && (length <= 0)) 
			return labPreconditions;

		if (length <= 0) 
			return labPreconditions.subList(start, labPreconditions.size()-1);

		return labPreconditions.subList(start, start+length-1);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabPreconditionDAO#deleteLabPrecondition(org.openmrs.module.jsslab.db.LabPrecondition)
	 */
	@Override
	public void deleteLabPrecondition(LabPrecondition labPrecondition) {
		sessionFactory.getCurrentSession().delete(labPrecondition);
		return;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabPreconditionDAO#getCountOfLabPreconditions(java.lang.String, java.lang.Boolean)
	 */
	@Override
	public Integer getCountOfLabPreconditions(String search,
			Boolean includeRetired) {
		List<LabPrecondition> labPreconditions = getLabPreconditions(search, includeRetired,0,0);
		return labPreconditions.size();
	}

}