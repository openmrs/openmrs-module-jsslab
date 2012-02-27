package org.openmrs.module.jsslab.db;

import java.util.List;

import org.hibernate.SessionFactory;
import org.openmrs.api.APIException;

public interface LabTestRangeDAO {
	
	public void setSessionFactory(SessionFactory sessionFactory);
	
	public LabTestRange getLabTestRangeByUuid(String uuid);
	
	public LabTestRange saveLabTestRange(LabTestRange labTestRange);
	
	public void deleteLabTestRange(LabTestRange labTestRange);
	
	public void purgeLabTestRange(LabTestRange labTestRange);
	
    public LabTestRange getLabTestRangeByName(String labTestRange);
	
    public List<LabTestRange> getAllLabTestRanges(Boolean ifVoided);

}
