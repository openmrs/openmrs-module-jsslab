// define startsWith String function if not exists
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}

var jsslab = {
	"i18n" : {},
	"constants" : {
		"labTagName" : "Lab",
		"conceptSets" : {
			"conditions" : {
				"ASSET CONDITION" : "b816ddc6-4873-11e1-b5ed-0024e8c61285",
			}
		},
		"conceptClasses" : {
			"misc" : "8d492774-c2cc-11de-8d13-0010c6dffd0f"
		},
		"datatypes" : {
			"na" : "8d4a4c94-c2cc-11de-8d13-0010c6dffd0f"
		}
	},
	
	/*
	 * CONCEPTS
	 */
	
	/**
	 * Saves a Concept object
	 * 
	 * @param concept The Concept to be saved
	 * @param uuid The UUID of the Concept if it is being updated or null if it is being added
	 * 
	 * @return The uuid of the saved or updated <code>Concept</code>
	 */
	saveConcept : function(concept, uuid, retire, callback) {
		var baseUrl = openmrsContextPath + "/ws/rest/v1/concept";
		
		// update existing concept
		if (jsslab.isValidUuid(uuid)) {
			// if the concept already exists save/update its names
			baseUrl += "/" + uuid;
			
			var fullname = jsslab.getSaveableConceptName(jsslab.getConceptName(concept, "FULLY_SPECIFIED"));
			var shortname = jsslab.getSaveableConceptName(jsslab.getConceptName(concept, "SHORT"));
			
			jsslab.saveConceptName(uuid, fullname, 
					//wait for the first call to return before starting the next, to avoid concurrency problems
					jsslab.saveConceptName(uuid, shortname,
							//invoke callback after last request has returned
							jsslab.setConceptRetired(concept, retire, callback(concept.uuid))
					)
			);
		
		// save new concept
		} else {
			// first save the concept and then its names
			jQuery.ajax({
				"url" : baseUrl,
				"type" : "POST",
				"contentType" : "application/json",
				"data" : JSON.stringify(jsslab.getSaveableConcept(concept)),
				"success" : function(savedConcept) {
					var conceptName = jsslab.getConceptName(concept, "SHORT");
					
					jsslab.saveConceptName(savedConcept.uuid, conceptName,
							jsslab.addConceptToConceptSet(savedConcept, jsslab.settingsPage.getSelectedConceptSetUuid(),
									jsslab.setConceptRetired(savedConcept, jQuery('#checkboxRetireConcept').attr('checked'),
											callback(savedConcept.uuid)
									)
							)
					);
				}
			});
		}

	},
	
	addConceptToConceptSet : function(concept, setUuid, callback) {
		jQuery.ajax({
			"url" : openmrsContextPath + "/ws/rest/v1/jsslab?addConceptToConceptSet",
			"type" : "POST",
			"contentType" : "application/x-www-form-urlencoded",
			"data" : "conceptUuid=" + concept.uuid + "&setUuid=" + setUuid,
			"success" : callback
		});
	},
	
	setConceptRetired : function(concept, retired, callback) {
		if (retired) {
			var retireReason = jQuery('#editConceptRetireReason').val();
			var url = openmrsContextPath + "/ws/rest/v1/concept/" + concept.uuid + "?reason="+retireReason;
			
			jQuery.ajax({
				"url" : url,
				"type" : "DELETE",
				"success" : callback
			});
		}
	},
	
	/**
	 * Saves a ConceptName object
	 * 
	 * @param conceptUuid The UUID of the Concept object to which the ConceptName belongs 
	 * @param conceptName The ConceptName to be saved
	 * @param conceptNameUuid The UUID of the ConceptName if is being updated or null if it is being added
	 */
	saveConceptName : function(conceptUuid, conceptName, callback) {
		if (jsslab.isValidUuid(conceptName.uuid)) {
			//update
		} else {
			//save new
			var url = openmrsContextPath + "/ws/rest/v1/concept/" + conceptUuid + "/name";
			jQuery.ajax({
				"url" : url,
				"type" : "POST",
				"contentType" : "application/json",
				"data" : JSON.stringify(conceptName),
				"success" : callback
			});
		}
	},
	
	/**
	 * Retrieves the Code that is assigned to the given Concept object, if there is one, otherwise returns an empty String.
	 * 
	 * @param concept The Concept for which the Code is to be retrieved
	 * @returns The Code assigned to the given Concept or an empty String if none is assigned
	 */
	getConceptName : function(concept, type) {
		var name = null;
		for (var i = 0; i < concept.names.length; i++) {
			if (concept.names[i].conceptNameType == type) {
				name = concept.names[i];
				break;
			}
		}
		return name;
	},
	
	/**
	 * Creates a Concept object from an existing one, removing any transiert fields that cannot be saved (e.g. display)
	 * 
	 * @param concept The Concept to be prepared for saving
	 * @returns a Concept object that can be saved via REST
	 */
	getSaveableConcept : function(concept) {
		concept.display = undefined;
		concept.name = undefined;
		concept.uuid = undefined;
		concept.conceptClass = jsslab.constants.conceptClasses["misc"];
		concept.datatype = jsslab.constants.datatypes["na"];
		return concept;
	},
	
	/**
	 * Creates a ConceptName object from an existing one removing any transient fields that cannot be saved (e.g. display)
	 * 
	 * @param conceptName The ConceptName to be prepared for saving
	 * @returns a ConceptName object that can be saved via REST
	 */
	getSaveableConceptName : function(conceptName) {
		return {
			"name" : conceptName.name,
			"conceptNameType" : conceptName.conceptNameType,
			"locale" : conceptName.locale,
		};
	},
	
	/**
	 * Sets the concept name on a concept or updates if the name type is already present
	 * 
	 * @return The given <code>Concept</code> with the name added to it
	 */
	setConceptName : function(concept, name, type) {
		var nameType = false;
		for (var i = 0; i < concept.names.length; i++) {
			if (concept.names[i].conceptNameType == type) {
				concept.names[i].name = name;
				nameType = true;
				break;
			}
		}
		if (!nameType) {
			concept.names[concept.names.length] = {
					"conceptNameType" : type,
					"name" : name,
					//TODO replace with user/system locale
					"locale" : "EN"
			}
		}
		return concept;
	},
	
	/*
	 * GLOBAL PROPERTIES
	 */
	
	
	/**
	 * Saves a global property.
	 * 
	 * A result is be passed to the callback function containing a result code ("success" or other) and
	 * a message explaining the code.
	 * 
	 * @param property
	 * @param value
	 * @param resultElement
	 * @param resultFunction The function to be called when a result is returned
	 */
	saveGlobalProperty : function(property, value, resultElement, resultFunction) {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab?saveGlobalProperty",
			type : "POST",
			contentType : "application/x-www-form-urlencoded",
			data: "property=" + property + "&value=" + value,
			success : function(result) {
				if (resultFunction) {
					resultFunction(resultElement, result);
				}
			}
		});
	},
	
	
	/*
	 * GENERAL
	 */
	
	
	isValidUuid : function(uuid) {
		return uuid != null && uuid.length >= 36;
	},
	
};