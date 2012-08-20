// define startsWith String function if not exists
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}

var jsslab = {
		
	/** 
	 * The base internationalisation object for JavaScript code in this module
	 * 
	 * If a locale specific String needs to be set via JavaScript it is stored in this object using a script-snippet in the
	 * associated .jsp file. In the .jsp the String is retrieved from the messages*.properties using the spring:message 
	 * taglib.
	 */
	"i18n" : {},
	
	/**
	 * The base object containing constants that are referred to in the code of this module
	 */
	"constants" : {
		"labTagName" : "Lab",
		"labTagUuid" : "",
		"conceptSets" : {
			"conditions" : {
				"ASSET CONDITION" : "a0f71212-24d1-11e1-a648-0024e8dbd743",
			}
		},
		"conceptClasses" : {
			"misc" : "8d492774-c2cc-11de-8d13-0010c6dffd0f"
		},
		"datatypes" : {
			"na" : "8d4a4c94-c2cc-11de-8d13-0010c6dffd0f"
		}
	},
	
	"dao" : {
		
		getConcept : function(uuid, representation, callback) {
			var baseUrl = openmrsContextPath + "/ws/rest/v1/concept/" + uuid;
			if (representation) {
				baseUrl += "?v="+representation;
			}
			jQuery.ajax({
				"url" : baseUrl,
				"success" : callback
			});
		},
		
		/*
		 * Methods for CONCEPTS
		 */
		
		/**
		 * Saves a <code>Concept</code> object 
		 * 
		 * Uses an XHR to the REST API
		 * 
		 * @param concept The <code>Concept</code> to be saved
		 * @param uuid The UUID of the <code>Concept</code> if it is being updated or null if it is being added
		 * @param retire Whether the <code>Concept</code> is to be retired
		 * @param callback A function to be called when the XHR to save the <code>Concept</code> returns.
		 * 
		 * @return The uuid of the saved or updated <code>Concept</code>
		 */
		saveConcept : function(concept, callback) {
			var baseUrl = openmrsContextPath + "/ws/rest/v1/concept";
			if (jsslab.isValidUuid(concept.uuid)) {
				baseUrl += "/" + concept.uuid;
			}
			
			var conceptUuid = concept.uuid;
			var names = concept.names;
			concept.uuid = undefined;
			concept.names = undefined;
			
			jQuery.ajax({
				"url" : baseUrl,
				"type" : "POST",
				"contentType" : "application/json",
				"data" : JSON.stringify(concept),
				"success" : function (savedConcept) {
					if (savedConcept != null && jsslab.isValidUuid(savedConcept.uuid)) {
						conceptUuid = savedConcept.uuid;
					}
					if (names != null) {
						jsslab.dao.saveConceptNames(conceptUuid, names, 0);
					}
					
				}
			});
		},
		
		/**
		 * Recursively saves all <code>ConceptName</code>s of the given array of names
		 * 
		 * @param conceptUuid
		 * @param names
		 * @param index
		 */
		saveConceptNames : function(conceptUuid, names, index) {
			if (index < names.length) {
				jsslab.dao.saveConceptName(conceptUuid, names[index], jsslab.dao.saveConceptNames(conceptUuid, names, index+1));
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
		 * Retires a <code>Concept</code>
		 * 
		 * Uses an XHR to the REST API
		 * 
		 * @param uuid The UUID of the <code>Concept</code> to be retired
		 * @param retire Whether the <code>Concept</code> is to be retired
		 * @param callback A function to be called when the XHR returns.
		 * 
		 * @return The uuid of the saved or updated <code>Concept</code>
		 */
		setConceptRetired : function(uuid, retire, retireReason, callback) {
			if (retire) {
				var url = openmrsContextPath + "/ws/rest/v1/concept/" + uuid + "?reason="+retireReason;
				
				jQuery.ajax({
					"url" : url,
					"type" : "DELETE",
					"success" : callback
				});
			} else {
				//TODO unretire the concept
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
		
		/*
		 * Methods for Location
		 */
		
		saveLocation : function(location, isManaged, callback) {
			var locUuid = location.uuid;
			location.uuid = undefined;
			
			if (isManaged != null) {
				if (isManaged) {
					jsslab.saveGlobalProperty("jsslab.object.location.homeLab", locUuid, null, null);
				} else {
					jsslab.saveGlobalProperty("jsslab.object.location.homeLab", "", null, null);
				}
			}
			
			var url = openmrsContextPath + "/ws/rest/v1/location/";
			
			if (jsslab.isValidUuid(locUuid)) url += "/" + locUuid;
			
			jQuery.ajax({
				"url" : url,
				"type" : "POST",
				"contentType" : "application/json",
				"data" : JSON.stringify( location ),
				"success" : callback
			});
		},
		
		saveInvestigation : function(investigation, callback) {
			var baseUrl = openmrsContextPath + "/ws/rest/v1/jsslab/labtestpanel";
			if (jsslab.isValidUuid(investigation.uuid)) {
				baseUrl += "/" + investigation.uuid;
			}
			
			var investigationUuid = investigation.uuid;
			investigation.uuid = undefined;
			
			jQuery.ajax({
				"url" : baseUrl,
				"type" : "POST",
				"contentType" : "application/json",
				"data" : JSON.stringify(investigation),
				"success" : function (savedInvestigation) {
					if (savedInvestigation != null && jsslab.isValidUuid(savedInvestigation.uuid)) {
						investigationUuid = savedInvestigation.uuid;
					}
					callback(investigationUuid);
				}
			});
		},
		
		/**
		 * Removes the currently selected <code>Investigation/LabTestPanel</code> from the currently selected Lab and retires it.
		 */
		setInvestigationRetired : function(uuid, retire, reason, callback) {
			if (jsslab.isValidUuid(uuid) && retire) {
				jQuery.ajax({
					"url" : openmrsContextPath + "/ws/rest/v1/jsslab/labtestpanel/" + uuid + "?reason="+reason,
					"type" : "DELETE",
					"success" : callback
				});
			}
		},
	},
	
	
	/**
	 * Adds a <code>Concept</code> object to the <code>ConceptSet</code> given by its UUID
	 * 
	 * Uses an XHR to the REST API
	 * 
	 * @param concept The <code>Concept</code> to be added to the <code>ConceptSet</code>
	 * @param setUuid The UUID of the <code>ConceptSet</code>
	 * @param callback A function to be called when the XHR returns.
	 * 
	 * @return The uuid of the saved or updated <code>Concept</code>
	 */
	addConceptToConceptSet : function(concept, setUuid, callback) {
		jQuery.ajax({
			"url" : openmrsContextPath + "/ws/rest/v1/jsslab?addConceptToConceptSet",
			"type" : "POST",
			"contentType" : "application/x-www-form-urlencoded",
			"data" : "conceptUuid=" + concept.uuid + "&setUuid=" + setUuid,
			"success" : callback
		});
	},
	
//	setConceptRetired : function(concept, retire, callback) {
//		if (retire) {
//			var retireReason = jQuery('#editConceptRetireReason').val();
//			var url = openmrsContextPath + "/ws/rest/v1/concept/" + concept.uuid + "?reason="+retireReason;
//			
//			jQuery.ajax({
//				"url" : url,
//				"type" : "DELETE",
//				"success" : callback
//			});
//		} else {
//			//TODO unretire the concept
//		}
//	},
	
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
	setConceptName : function(concept, name, type, locale) {
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
					"locale" : locale != null ? locale : "EN"
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
	
	/**
	 * Checks whether the given UUID is valid, based on the length of the string.
	 * 
	 * @param uuid The UUI that is to be checked for validity
	 * @returns {Boolean}
	 */
	isValidUuid : function(uuid) {
		return uuid != null && uuid.length >= 36;
	},
	
};