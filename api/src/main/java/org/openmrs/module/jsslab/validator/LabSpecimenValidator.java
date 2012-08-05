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

import org.openmrs.annotation.Handler;
import org.openmrs.module.jsslab.db.LabOrder;
import org.openmrs.module.jsslab.db.LabSpecimen;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Validates the {@link LabOrder} class.
 * 
 * @since 1.5
 */
@Handler(supports = { LabSpecimen.class }, order = 50)
public class LabSpecimenValidator implements Validator {
	
	/**
	 * Determines if the command object being submitted is a valid type
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
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
		LabSpecimen specimen = (LabSpecimen) obj;
		
		if (specimen == null) {
			errors.rejectValue("specimen", "jsslab.validation.error.null");
		}
		// for the following elements Order.hbm.xml says: not-null="true"NULLLocation
		ValidationUtils.rejectIfEmpty(errors, "labSpecimenId", "jsslab.validation.NullField");
		ValidationUtils.rejectIfEmpty(errors, "orderedBy", "jsslab.validation.NullField");
		ValidationUtils.rejectIfEmpty(errors, "orderedByFacility", "jsslab.validation.NullLocation");
		ValidationUtils.rejectIfEmpty(errors, "specimenDate", "jsslab.validation.NullField");
		ValidationUtils.rejectIfEmpty(errors, "receivedDate", "jsslab.validation.NullField");
		ValidationUtils.rejectIfEmpty(errors, "receivedSpecimenTypeConcept", "jsslab.validation.NullConcept");		
		ValidationUtils.rejectIfEmpty(errors, "report", "jsslab.validation.NullReport");	
		ValidationUtils.rejectIfEmpty(errors, "urgent", "jsslab.validation.NullField");		
		ValidationUtils.rejectIfEmpty(errors, "hidden", "jsslab.validation.NullField");		
		ValidationUtils.rejectIfEmpty(errors, "creator", "jsslab.validation.NullField");		
		ValidationUtils.rejectIfEmpty(errors, "dateCreated", "jsslab.validation.NullField");		
		ValidationUtils.rejectIfEmpty(errors, "retired", "jsslab.validation.NullField");			
	}
}
