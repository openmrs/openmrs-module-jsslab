package org.openmrs.module.jsslab.rest.v1_0.resource;

import java.util.List;

import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.logic.rule.definition.RuleDefinition;
import org.openmrs.logic.rule.definition.RuleDefinitionService;
import org.openmrs.module.jsslab.impl.ExternalObjectsServiceImpl;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

@Resource("ruledefinition")
@Handler(supports = RuleDefinition.class, order = 0)
public class RuleDefinitionResource extends MetadataDelegatingCrudResource<RuleDefinition> {

	@Override
	public RuleDefinition newDelegate() {
		return new RuleDefinition();
	}

	@Override
	public RuleDefinition save(RuleDefinition delegate) {
		return Context.getService(RuleDefinitionService.class).saveRuleDefinition(delegate);
	}

	@Override
	public RuleDefinition getByUniqueId(String uniqueId) {
		List<RuleDefinition> ruleDefinitions = Context.getService(RuleDefinitionService.class).getAllRuleDefinitions();
		for (RuleDefinition ruleDefinition : ruleDefinitions) {
			if (ruleDefinition.getUuid().equals(uniqueId)) {
				return ruleDefinition;
			}
		}
		
//		throw new NotImplementedException("getByUuid method not available for RuleDefinitions");
		return Context.getService(ExternalObjectsServiceImpl.class).getRuleDefinitionByUuid(uniqueId);
	}

	@Override
	public void purge(RuleDefinition delegate, RequestContext context) throws ResponseException {
		if (delegate != null) {
			Context.getService(RuleDefinitionService.class).purgeRuleDefinition(delegate);
		}
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		
		if (rep instanceof DefaultRepresentation) {
			//			
			description.addProperty("uuid");
			description.addProperty("ruleContent");
			description.addProperty("language");
			description.addProperty("retired");
			description.addSelfLink();
			description.addLink("full", ".?v="+RestConstants.REPRESENTATION_FULL);
			return description;
			
		} else if (rep instanceof FullRepresentation) {
			//
			description.addProperty("uuid");
			description.addProperty("ruleContent");
			description.addProperty("language");
			description.addProperty("retired");
			description.addSelfLink();
			description.addProperty("auditInfo",findMethod("getAuditInfo"));
			return description;
		}
		return null;
	}
	
}
