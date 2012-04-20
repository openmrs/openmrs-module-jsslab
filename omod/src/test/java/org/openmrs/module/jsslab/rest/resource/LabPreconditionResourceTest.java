package org.openmrs.module.jsslab.rest.resource;

import org.junit.Before;
import org.openmrs.api.context.Context;
import org.openmrs.module.jsslab.LabCatalogService;
import org.openmrs.module.jsslab.db.LabPrecondition;
import org.openmrs.module.jsslab.rest.resource.LabPreconditionResource;
import org.openmrs.module.webservices.rest.web.resource.impl.BaseDelegatingResourceTest;


public class LabPreconditionResourceTest extends BaseDelegatingResourceTest<LabPreconditionResource,LabPrecondition>{

	private static final String datasetFilename = "jsslab_data_delta.xml";
	
	@Before
	public void before() throws Exception {
		executeDataSet(datasetFilename);
	}
	
	@Override
	public LabPrecondition newObject() {
		return Context.getService(LabCatalogService.class).getLabPreconditionByUuid(getUuidProperty());
	}

	@Override
	public String getUuidProperty() {
		return "0100dc0a-46da-11e1-99f4-0024e8c61285";
	}

	@Override
	public void validateDefaultRepresentation() throws Exception {
		super.validateDefaultRepresentation();
		assertPropEquals("voided", getObject().getVoided());
		assertPropPresent("testPanel");
		assertPropPresent("preconditionQuestionConcept");
	}
	
	@Override
	public void validateFullRepresentation() throws Exception {
		super.validateFullRepresentation();
		assertPropEquals("voided", getObject().getVoided());
		assertPropPresent("testPanel");
		assertPropPresent("preconditionQuestionConcept");
		assertPropPresent("preconditionAnswerConcept");
		assertPropEquals("sortWeight", getObject().getSortWeight());
		assertPropPresent("auditInfo");
	}

	@Override
	public String getDisplayProperty() {
		return getObject().getDisplayString();
	}
}
