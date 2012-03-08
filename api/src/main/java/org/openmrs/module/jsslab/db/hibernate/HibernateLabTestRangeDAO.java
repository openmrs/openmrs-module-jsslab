package org.openmrs.module.jsslab.db.hibernate;

import java.util.List;
import java.util.Comparator;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.jsslab.db.LabPrecondition;
import org.openmrs.module.jsslab.db.LabTestRange;
import org.openmrs.module.jsslab.db.LabTestRangeDAO;

public class HibernateLabTestRangeDAO implements LabTestRangeDAO{

	private SessionFactory sessionFactory;
	
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory=sessionFactory;
	}
	
	@Override
	public LabTestRange getLabTestRangeByUuid(String uuid) {
		return (LabTestRange)this.sessionFactory.getCurrentSession().createCriteria(LabTestRange.class)
				.add(Restrictions.eq("uuid", uuid)).list();
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
				.addOrder(Order.asc("labTest")).addOrder(Order.asc("sortWeight"));
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
			}
		}
		return list;
	}
	@Override
	public Integer getCountOfLabTestRanges(String search, Boolean includeVoided) {
		return getLabTestRanges(search, includeVoided, null, null).size();
	}
	
}
