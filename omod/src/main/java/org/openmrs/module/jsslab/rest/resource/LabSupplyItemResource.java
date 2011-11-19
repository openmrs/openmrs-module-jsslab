/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.jsslab.rest.resource;

import java.util.List;

import org.openmrs.module.jsslab.db.LabSupplyItem;
import org.openmrs.annotation.Handler;
import org.openmrs.module.jsslab.LabManagementService;
import org.openmrs.api.context.Context;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.AlreadyPaged;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.MetadataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.ServiceSearcher;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * {@link Resource} for {@link Location}, supporting standard CRUD operations
 */
@Resource("labSupplyItem")
@Handler(supports = LabSupplyItem.class, order = 0)
public class LabSupplyItemResource extends MetadataDelegatingCrudResource<LabSupplyItem> {
	
	/**
	 * @see DelegatingCrudResource#getRepresentationDescription(Representation)
	 */
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		if (rep instanceof DefaultRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("propertyTag");
			description.addProperty("manufacturer");
			description.addProperty("model");
			description.addProperty("serialNumber");
			description.addProperty("retired");
			description.addSelfLink();
			description.addLink("full", ".?v=" + RestConstants.REPRESENTATION_FULL);
			return description;
		} else if (rep instanceof FullRepresentation) {
			DelegatingResourceDescription description = new DelegatingResourceDescription();
			description.addProperty("uuid");
			description.addProperty("propertyTag");
			description.addProperty("manufacturer");
			description.addProperty("model");
			description.addProperty("serialNumber");
			description.addProperty("receivedDate");
			description.addProperty("receivedFrom");
			description.addProperty("receivedCost");
			description.addProperty("receivedValue");
			description.addProperty("conditionDate");
			description.addProperty("conditionConcept",Representation.REF);
			description.addProperty("maintenanceVendor");
			description.addProperty("maintenancePhone");
			description.addProperty("maintenanceDescription");
			description.addProperty("testSpecimens", Representation.REF);
			description.addProperty("retired");
			description.addSelfLink();
			return description;
		}
		return null;
	}
	
	/**
	 * @see DelegatingCrudResource#newDelegate()
	 */
	@Override
	public LabSupplyItem newDelegate() {
		return new LabSupplyItem();
	}
	
	/**
	 * @see DelegatingCrudResource#save(java.lang.Object)
	 */
	@Override
	public LabSupplyItem save(LabSupplyItem labSupplyItem) {
		return Context.getService(LabManagementService.class)
.saveLabSupplyItem(labSupplyItem);
	}
	
	/**
	 * Fetches a LabSupplyItem by uuid, if no match is found, it tries to look up one with a matching
	 * propertyTag or serialNumber
	 * 
	 * @see DelegatingCrudResource#getByUniqueId(java.lang.String)
	 */
	@Override
	public LabSupplyItem getByUniqueId(String uuid) {
		LabSupplyItem labSupplyItem = Context.getService(LabManagementService.class).getLabSupplyItemByUuid(uuid);
		//We assume the caller was fetching by propertyTag or serialNumber
		if (labSupplyItem == null)
			labSupplyItem = Context.getService(LabManagementService.class).getLabSupplyItem(uuid);
		
		return labSupplyItem;
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#purge(java.lang.Object,
	 *      org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	public void purge(LabSupplyItem labSupplyItem, RequestContext context) throws ResponseException {
		if (labSupplyItem == null)
			return;
		Context.getService(LabManagementService.class).purgeLabSupplyItem(LabSupplyItem);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#doGetAll(org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	protected List<LabSupplyItem> doGetAll(RequestContext context) {
		return Context.getService(LabManagementService.class).getAllSupplyItems(false);
	}
	
	/**
	 * @see org.openmrs.module.webservices.rest.web.resource.impl.DelegatingCrudResource#doSearch(java.lang.String,
	 *      org.openmrs.module.webservices.rest.web.RequestContext)
	 */
	@Override
	protected AlreadyPaged<LabSupplyItem> doSearch(String query, RequestContext context) {
		return new ServiceSearcher<LabSupplyItem>(LabSupplyItem.class, "getLabSupplyItems", "getCountOfLabSupplyItems").search(query,
		    context);
	}
	
}
