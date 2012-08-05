/**
 * 
 */
package org.openmrs.module.jsslab.db.hibernate;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.openmrs.module.jsslab.db.LabTestResult;
import org.openmrs.module.jsslab.db.LabTestResultDAO;

/**
 *
 */
public class HibernateLabTestResultDAO implements LabTestResultDAO {

	private SessionFactory sessionFactory;
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestResultDAO#setSessionFactory(org.hibernate.SessionFactory)
	 */
	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public class LabTestResultComparator implements Comparator<LabTestResult> {
		  public int compare(LabTestResult ltr1, LabTestResult ltr2) {
			  int i = ltr1.getTestSpecimen().getSpecimen().getLabSpecimenId().compareTo(ltr2.getTestSpecimen().getSpecimen().getLabSpecimenId());
			  if (i != 0)
				  return i;
			  return ltr1.getTestResultText().compareTo(ltr2.getTestResultText());
		  }
		}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestResultDAO#saveLabTestResult(org.openmrs.module.jsslab.db.LabTestResult)
	 */
	@Override
	public LabTestResult saveLabTestResult(LabTestResult labTestResult) {
		sessionFactory.getCurrentSession().saveOrUpdate(labTestResult);
		return labTestResult;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestResultDAO#getLabTestResult(java.lang.Integer)
	 */
	@Override
	public LabTestResult getLabTestResult(Integer testResultId) {
		return (LabTestResult) sessionFactory.getCurrentSession().get(LabTestResult.class, testResultId);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestResultDAO#getLabTestResultByUuid(java.lang.String)
	 */
	@Override
	public LabTestResult getLabTestResultByUuid(String uuid) {
		return (LabTestResult) sessionFactory.getCurrentSession()
			.createQuery("from LabTestResult l where l.uuid = :uuid")
			.setString("uuid", uuid).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestResultDAO#getLabTestResultByName(java.lang.String)
	 */
	@Override
	public LabTestResult getLabTestResultByName(String name) {
		// get candidates
		List<LabTestResult> labTestResults = (List<LabTestResult>) sessionFactory.getCurrentSession()
				.createQuery("from LabTestResult as ltr where ltr.testResultConcept.names.name=:name and not ltr.voided" )
				.setString("name", name).list();

		// eliminate those that don't really match (perhaps wrong locale)
		for (int i=0;i<labTestResults.size();)
			if (! labTestResults.get(i).getTestResultText().equalsIgnoreCase(name))
				labTestResults.remove(i);
			else
				i++;

		// if list is exhausted, return null
		if (null == labTestResults || labTestResults.isEmpty()) {
			return null;
		}
		
		// sort the possible returns and take the first
		Collections.sort(labTestResults,new LabTestResultComparator());
		return labTestResults.get(0);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestResultDAO#getLabTestResults(java.lang.String, java.lang.Boolean, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<LabTestResult> getLabTestResults(String search,
			Boolean includeVoided, Integer start, Integer length) {
		// get candidates
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(LabTestResult.class, "ltr");

		if (!includeVoided) 
			criteria.add(Restrictions.ne("voided", true));

		List<LabTestResult> labTestResults = (List<LabTestResult>) criteria.list();
		
		// eliminate those that don't match
		if (StringUtils.isNotBlank(search))
			for (int i=0;i<labTestResults.size();) {
				if (! ((labTestResults.get(i).getTestResultText().startsWith(search)) 
				|| (labTestResults.get(i).getTestSpecimen().getSpecimen().getLabSpecimenId().startsWith(search))))
					labTestResults.remove(i);
			}

		if (labTestResults.size()==0)
			return null;
		
		// sort the possible returns and take the first
		Collections.sort(labTestResults, new LabTestResultComparator());
		if ((start == null || start == 0) && (length == null || length <= 0)) 
			return labTestResults;

		if (length <= 0) 
			return labTestResults.subList(start, labTestResults.size());

		return labTestResults.subList(start, start+length);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestResultDAO#deleteLabTestResult(org.openmrs.module.jsslab.db.LabTestResult)
	 */
	@Override
	public void deleteLabTestResult(LabTestResult labTestResult) {
		sessionFactory.getCurrentSession().delete(labTestResult);
		return;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.jsslab.db.LabTestResultDAO#getCountOfLabTestResults(java.lang.String, java.lang.Boolean)
	 */
	@Override
	public Integer getCountOfLabTestResults(String search,
			Boolean includeVoided) {
		List<LabTestResult> labTestResults = getLabTestResults(search, includeVoided,0,0);
		return labTestResults.size();
	}

}
