package org.openmrs.module.jsslab.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.jsslab.db.LabTestRange;
import org.openmrs.module.jsslab.db.LabTestRangeDAO;

public class HibernateLabTestRangeDAO implements LabTestRangeDAO{

	private SessionFactory sessionFactory;
	
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestRangeDAO#getLabTestRange(java.lang.Integer)
	 */
	@Override
	public LabTestRange getLabTestRange(Integer preconditionId) {
		return (LabTestRange) sessionFactory.getCurrentSession().get(LabTestRange.class, preconditionId);
	}

	@Override
	public LabTestRange getLabTestRangeByUuid(String uuid) {
		return (LabTestRange)this.sessionFactory.getCurrentSession().createCriteria(LabTestRange.class)
				.add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	@Override
	public LabTestRange saveLabTestRange(LabTestRange labTestRange) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(labTestRange);
		return labTestRange;
	}

	@Override
	public void deleteLabTestRange(LabTestRange labTestRange) {
		this.sessionFactory.getCurrentSession().delete(labTestRange);
	}
	
	@Override
	public List<LabTestRange> getAllLabTestRanges(Boolean ifVoided){
		return getLabTestRanges("", ifVoided, null, null);
	}

	@Override
	public List<LabTestRange> getLabTestRanges(String search,
			Boolean includeVoided, Integer start, Integer length) {
		Criteria criteria = this.sessionFactory.getCurrentSession().createCriteria(LabTestRange.class)
				.addOrder(Order.asc("test")).addOrder(Order.asc("sortWeight"));
		if (!includeVoided)
				criteria.add(Restrictions.ne("voided", true));
		if (start != null)
			criteria.setFirstResult(start);
		if (length != null && length > 0)
			criteria.setMaxResults(length);
		List<LabTestRange> list = (List<LabTestRange>) criteria.list();
		if ((search != null) && !search.isEmpty()) {
			for (LabTestRange ltr : list) {
				if (!ltr.getTest().getTestName().startsWith(search))
					list.remove(ltr);
			if ((start == 0) && (length <= 0)) 
				return list;

			if (length <= 0) 
				return list.subList(start, list.size()-1);

			return list.subList(start, start+length-1);
		}
	}
		return list;
	}
	@Override
	public Integer getCountOfLabTestRanges(String search, Boolean includeVoided) {
		return getLabTestRanges(search, includeVoided, null, null).size();
	}
	
}
