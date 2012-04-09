package org.openmrs.module.jsslab;

import org.junit.Before;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabSpecimenTemplate;
import org.openmrs.module.jsslab.rest.resource.LabSpecimenTemplateResource;

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

}
