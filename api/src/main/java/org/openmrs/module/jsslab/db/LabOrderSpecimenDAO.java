package org.openmrs.module.jsslab.db;

import java.util.List;

import org.hibernate.SessionFactory;
import org.openmrs.api.APIException;

public interface LabOrderSpecimenDAO {
	
	/**
	 * Set the Hibernate SessionFactory to connect to the database.
	 * 
	 * @param sessionFactory
	 */
	public void setSessionFactory(SessionFactory sessionFactory);
	
	/**
	 * Create or update a labOrder.
	 * 
	 * @param labOrderSpecimen <code>LabOrderSpecimen</code> to save
	 * @return the saved <code>LabOrderSpecimen</code>
	 */
	public LabOrderSpecimen saveLabOrderSpecimen(LabOrderSpecimen labOrderSpecimen);
	
	public LabOrderSpecimen getLabOrderSpecimenByName(String labOrderSpecimen);
	
	public LabOrderSpecimen getLabOrderSpecimenByUuid(String uuid);
	
	public void deleteLabOrderSpecimen(LabOrderSpecimen labOrderSpecimen);
	
	
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
