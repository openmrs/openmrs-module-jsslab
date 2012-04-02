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
import org.openmrs.module.jsslab.db.LabReport;
import org.openmrs.module.jsslab.db.LabReportDAO;
import org.openmrs.module.jsslab.db.LabTest;

/**
 *
 */
public class HibernateLabReportDAO implements LabReportDAO {

	private SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabReportDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabReportDAO#saveLabReport(org.openmrs.module.jsslab.db.LabReport)
	 */
	@Override
	public LabReport saveLabReport(LabReport labReport) {
		sessionFactory.getCurrentSession().saveOrUpdate(labReport);
		return labReport;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabReportDAO#getLabReport(java.lang.Integer)
	 */
	@Override
	public LabReport getLabReport(Integer specimenId) {
		return (LabReport) sessionFactory.getCurrentSession().get(LabReport.class, specimenId);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabReportDAO#getLabReportByUuid(java.lang.String)
	 */
	@Override
	public LabReport getLabReportByUuid(String uuid) {
		return (LabReport) sessionFactory.getCurrentSession()
			.createQuery("from LabReport l where l.uuid = :uuid")
			.setString("uuid", uuid).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabReportDAO#getLabReportByName(java.lang.String)
	 */
	@Override
	public LabReport getLabReportByName(String name) {
		List<LabReport> labReports = (List<LabReport>) sessionFactory.getCurrentSession()
				.createQuery("from LabReport as lr where :name=lr.labReportId and not lr.retired")
				.setString("name", name).list();

		// if list is exhausted, return null
		if (null == labReports || labReports.isEmpty()) {
			return null;
		}
		return labReports.get(0);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabReportDAO#getLabReports(java.lang.String, java.lang.Boolean, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<LabReport> getLabReports(String search,
			Boolean includeRetired, Integer start, Integer length) {
		// get candidates
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabReport.class, "lr");

		if (!includeRetired) 
			criteria.add(Restrictions.ne("retired", true));

		if (StringUtils.isNotBlank(search))
			criteria.add(Restrictions.ilike("labReportId", search, MatchMode.START));

		if (start != null)
			criteria.setFirstResult(start);
		if (length != null && length > 0)
			criteria.setMaxResults(length);
		
		criteria.addOrder(Order.asc("labReportId"));
		criteria.addOrder(Order.asc("reportId"));
		return (List<LabReport>) criteria.list();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabReportDAO#deleteLabReport(org.openmrs.module.jsslab.db.LabReport)
	 */
	@Override
	public void deleteLabReport(LabReport labReport) {
		sessionFactory.getCurrentSession().delete(labReport);
		return;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabReportDAO#getCountOfLabReports(java.lang.String, java.lang.Boolean)
	 */
	@Override
	public Integer getCountOfLabReports(String search,
			Boolean includeRetired) {
		List<LabReport> labReports = getLabReports(search, includeRetired,0,0);
		return labReports.size();
	}

}
