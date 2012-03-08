package org.openmrs.module.jsslab.db;

import java.util.List;

import org.hibernate.SessionFactory;
import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.module.jsslab.PrivilegeConstants;
import org.springframework.transaction.annotation.Transactional;

public interface LabOrderSpecimenDAO {
	
	public void setSessionFactory(SessionFactory sessionFactory);
	
	public LabOrderSpecimen getLabOrderSpecimenByUuid(String uuid);
	
	public LabOrderSpecimen saveLabOrderSpecimen(LabOrderSpecimen labOrderSpecimen);
	
	public void deleteLabOrderSpecimen(LabOrderSpecimen labOrderSpecimen);
	
    public LabOrderSpecimen getLabOrderSpecimenByName(String labOrderSpecimen);
	
	/**
	 * Returns a specified number of labOrderSpecimens starting with a given string from the specified index
	 */
	public List<LabOrderSpecimen> getLabOrderSpecimens(String nameFragment, Boolean includeRetired, Integer start, Integer length);
	
	/**
	 * Get count of LabOrderSpecimen, only showing ones not marked as retired if includeRetired is true
	 * 
	 * @param includeVoided true/false whether to include retired LabOrderSpecimens in this list
	 * @return LabOrderSpecimens list
	 * @throws APIException
	 */
	public Integer getCountOfLabOrderSpecimens(Boolean includeVoided) throws APIException;
	

}
