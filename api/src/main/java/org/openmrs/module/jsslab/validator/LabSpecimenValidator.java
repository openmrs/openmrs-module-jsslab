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
package org.openmrs.module.jsslab.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.jsslab.db.LabSpecimen;
import org.openmrs.validator.OrderValidator;
import org.openmrs.annotation.Handler;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates the {@link LabOrder} class.
 * 
 * @since 1.5
 */
@Handler(supports = { LabSpecimen.class }, order = 50)
public class LabSpecimenValidator extends OrderValidator implements Validator {
	
	/**
	 * Determines if the command object being submitted is a valid type
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class c) {
		return LabSpecimen.class.isAssignableFrom(c);
	}
	
	/**
	 * Checks the form object for any inconsistencies/errors
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 * @should fail validation if required fields are missing
	 */
	public void validate(Object obj, Errors errors) {
		super.validate(obj, errors);
		
		LabSpecimen specimen = (LabSpecimen) obj;
		// for the following elements Order.hbm.xml says: not-null="true"NULLLocation
		ValidationUtils.rejectIfEmpty(errors, "labSpecimenId", "jsslab.Validation.NullField");
		ValidationUtils.rejectIfEmpty(errors, "orderedBy", "jsslab.Validation.NullField");
		ValidationUtils.rejectIfEmpty(errors, "orderedByFacility", "jsslab.Validation.NUllLocation");
		ValidationUtils.rejectIfEmpty(errors, "specimenDate", "jsslab.Validation.NullField");
		ValidationUtils.rejectIfEmpty(errors, "receivedDate", "jsslab.Validation.NullField");
		ValidationUtils.rejectIfEmpty(errors, "receivedSpecimenTypeConcept", "jsslab.Validation.NullConcept");		
		ValidationUtils.rejectIfEmpty(errors, "report", "jsslab.Validation.NullReport");	
		ValidationUtils.rejectIfEmpty(errors, "urgent", "jsslab.Validation.NullField");		
		ValidationUtils.rejectIfEmpty(errors, "hidden", "jsslab.Validation.NullField");		
		ValidationUtils.rejectIfEmpty(errors, "creator", "jsslab.Validation.NullField");		
		ValidationUtils.rejectIfEmpty(errors, "dateCreated", "jsslab.Validation.NullField");		
		ValidationUtils.rejectIfEmpty(errors, "retired", "jsslab.Validation.NullField");			
	}
}
