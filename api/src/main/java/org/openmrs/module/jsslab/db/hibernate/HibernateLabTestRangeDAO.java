package org.openmrs.module.jsslab.db.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
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
	
	/*
	 * (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestRangeDAO#getLabTestRangeByName(java.lang.String)
	 * 
	 * Maybe the method doesn't need implement LabTestRange
	 */
	@Override
	public LabTestRange getLabTestRangeByName(String labTestRange) {
		return null;
	}
	
	@Override
	public List<LabTestRange> getAllLabTestRanges(Boolean ifVoided){
		List <LabTestRange>list=this.sessionFactory.getCurrentSession().createCriteria(LabTestRange.class)
				.add(Restrictions.eq("voided", ifVoided)).list();
		return list;
	}
	
	@Override
	public void purgeLabTestRange(LabTestRange labTestRange) {
		this.sessionFactory.getCurrentSession().delete(labTestRange);
	}
}
