package org.openmrs.module.jsslab.rest.resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabSpecimenTemplate;
import org.openmrs.module.jsslab.rest.resource.LabSpecimenTemplateResource;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;

public class LabSpecimenTemplateResourceTest extends BaseDelegatingResourceTest<LabSpecimenTemplateResource,LabSpecimenTemplate>{

	private static final String datasetFilename = "jsslab_data_delta.xml";
	
	@Before
	public void before() throws Exception {
		executeDataSet(datasetFilename);
	}
	
	@Override
	public LabSpecimenTemplate newObject() {
		return Context.getService(LabCatalogService.class).getLabSpecimenTemplateByUuid(getUuidProperty());
	}

	@Override
	public String getDisplayProperty() {
		return null;
	}

	@Override
	public String getUuidProperty() {
		return "35442792-49b7-11e1-812a-0024e8c61285";
	}
	
	@Override
	public void validateDefaultRepresentation() throws Exception {
		super.validateDefaultRepresentation();
		assertPropEquals("retired", getObject().getRetired());
		assertPropPresent("testPanel");
		assertPropPresent("testRoleConcept");
		assertPropEquals("parentSubId", getObject().getParentSubId());
		assertPropEquals("specimenSubId", getObject().getSpecimenSubId());
	}
	
	@Override
	public void validateFullRepresentation() throws Exception {
		super.validateDefaultRepresentation();
		assertPropEquals("retired", getObject().getRetired());
		assertPropPresent("testPanel");
		assertPropPresent("testRoleConcept");
		assertPropEquals("parentSubId", getObject().getParentSubId());
		assertPropEquals("specimenSubId", getObject().getSpecimenSubId());
		assertPropPresent("auditInfo");
		assertPropPresent("parentRelationConcept");
		assertPropPresent("analysisSpecimenTypeConcept");
		assertPropPresent("parentRoleConcept");
	}

	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest#asRepresentation_shouldReturnValidDefaultRepresentation()
	 */
	@Override
	@Test
	public void asRepresentation_shouldReturnValidRefRepresentation() throws Exception {
		//Ignored!!! Remove the test method to enable again.
	    //super.asRepresentation_shouldReturnValidDefaultRepresentation();
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest#asRepresentation_shouldReturnValidFullRepresentation()
	 */
	@Override
	@Test
	public void asRepresentation_shouldReturnValidDefaultRepresentation() throws Exception {
		//Ignored!!! Remove the test method to enable again.
	    //super.asRepresentation_shouldReturnValidFullRepresentation(); Remove to test again
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest#asRepresentation_shouldReturnValidFullRepresentation()
	 */
	@Override
	@Test
	public void asRepresentation_shouldReturnValidFullRepresentation() throws Exception {
		//Ignored!!! Remove the test method to enable again.
	    //super.asRepresentation_shouldReturnValidFullRepresentation();
	}
}
