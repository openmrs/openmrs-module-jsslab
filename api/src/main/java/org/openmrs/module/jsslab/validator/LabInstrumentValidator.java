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
import org.openmrs.annotation.Handler;
import org.openmrs.module.jsslab.db.LabInstrument;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Validates the {@link labInstrument} class.
 * 
 * @since 1.5
 */
@Handler(supports = { LabInstrument.class }, order = 50)
public class LabInstrumentValidator implements Validator {
	
	/** Log for this class and subclasses */
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * Determines if the command object being submitted is a valid type
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@SuppressWarnings("rawtypes")
	public boolean supports(Class c) {
		return LabInstrument.class.isAssignableFrom(c);
	}
	
	/**
	 * Checks the form object for any inconsistencies/errors
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 *      org.springframework.validation.Errors)
	 * @should fail validation if required fields are missing
	 */
	public void validate(Object obj, Errors errors) {
		
		LabInstrument instrument = (LabInstrument) obj;
		if (instrument == null) {
			errors.rejectValue("instrument", "jsslab.validation.error.null");
		}
		
		// for the following elements LabPrecondition.hbm.xml says: not-null="true"
//		ValidationUtils.rejectIfEmpty(errors, "creator", "The field 'creator' cannot be null");
//		ValidationUtils.rejectIfEmpty(errors, "dateCreated", "The field 'dateCreated' cannot be null");
//		ValidationUtils.rejectIfEmpty(errors, "retired", "The field 'retired' cannot be null");

		
	}
}
