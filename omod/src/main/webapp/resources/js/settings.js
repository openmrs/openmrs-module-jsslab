// define startsWith String function if not exists
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}

jsslab.settingsPage = {
		
	location : null,
	isManagedLocation : false,
	isReferralLocation : false,
	labLocationTag : null,
	
	concepts : null,
	selectedConceptIndex : -1,

	
	// =================================
	// methods for lab selection section
	// =================================
	
	showLocationEditPanel : function(locationUuid) {
		jQuery.ajax({
			"url" : openmrsContextPath + "/ws/rest/v1/location/" + locationUuid,
			"type" : "GET",
			"success" : function(location) {
				jsslab.settingsPage.location = location;
				jQuery('#editLocationName').val(location.name);
				if (location.name) {
					jQuery('editLocationName').attr('disabled', 'disabled');
				}
				jQuery('#editLocationAddress').val(location.address1);

				DWRAdministrationService.getGlobalProperty("jsslab.object.location.homeLab", function(managedHomeLabID) {
					if (managedHomeLabID == location.uuid) {
						jQuery('#editLocationManaged').attr('checked', 'checked');
						jsslab.settingsPage.isManagedLocation = true;
					} else {
						jQuery('#editLocationManaged').removeAttr('checked');
						jsslab.settingsPage.isManagedLocation = false;
					}
				});
				
				jsslab.settingsPage.checkIfReferral(location);
				
			}
		});
		
		
		jQuery('#editLocationPanel').fadeIn();
	},
	
	checkIfReferral : function(location) {
		
		jQuery('#editLocationReferral').removeAttr('checked');
		jsslab.settingsPage.isReferralLocation = false;
		
		if (location.tags != null && location.tags.length > 0) {
			for (var i = 0; i < location.tags.length; i++) {
				var tagUuid = location.tags[i].uuid;
				if (tagUuid == jsslab.settingsPage.labLocationTag.uuid) {
					jQuery('#editLocationReferral').attr('checked', 'checked');
					jsslab.settingsPage.isReferralLocation = true;
					break;
				}
			}
		}
	},
	
	saveLocation : function() {
		var location = jsslab.settingsPage.location;
		
		//TODO validate
		location.name = jQuery('#editLocationName').val();
		location.address1 = jQuery('#editLocationAddress').val();
		
		var isManaged = jQuery('#editLocationManaged').attr('checked');
		var isReferral = jQuery('#editLocationReferral').attr('checked');
		
		if (isManaged != jsslab.settingsPage.isManagedLocation) {
			if (isManaged) {
				DWRAdministrationService.setGlobalProperty("jsslab.object.location.homeLab", location.uuid); 
			} else {
				DWRAdministrationService.setGlobalProperty("jsslab.object.location.homeLab", ""); 
			}
		}
		
		if (isReferral != jsslab.settingsPage.isReferralLocation) {
			if (isReferral) {
				location.tags[tags.length] = {
					"uuid" : jsslab.settingsPage.labLocationTag.uuid
				};
			} else {
				for (var i = 0; i < location.tags.length; i++) {
					if (labLocationTag.uuid == location.tags[i].uuid) {
						locations.tags.splice(i, 1);
					}
				}
			}
		}
		
		locUuid = location.uuid;
		
		jQuery.ajax({
			"url" : openmrsContextPath + "/ws/rest/v1/location/" + locUuid,
			"type" : "POST",
			"contentType" : "application/json",
			"data" : JSON.stringify( jsslab.settingsPage.getSaveableLocation(location) ),
			"success" : function(result) {
				//TODO update tree
				//TODO display errors
			}
		});
	},
	
	getSaveableLocation : function(location) {
		location.display = undefined;
		location.uuid = undefined;
		
		var tags = []; 
		for (var i = 0; i < location.tags.length; i++) {
			tags[i] = location.tags[i].uuid;
		}
		location.tags = tags;
		
		return location;
	},
	
	
	
	// ===================================
	// methods for global property section
	// ===================================
	
	/**
	 * Displays feedback information on whether a save operation has completed successfully.
	 * 
	 * @param element The element to display the feedback information in
	 * @param result A result object containing a <code>code</code> and a <code>message</code> field
	 */
	setSaveResult : function(element, result) {
		if (result.code == "success") {
			element.attr('class', 'success');
		} else {
			element.attr('class', 'error');
		}
		element.text(result.message);
		element.fadeIn();
		setTimeout( function(){ element.fadeOut(); }, 2000 );
	},
	
	
	// ==============================
	// methods for code lists section
	// ==============================
	
	codeListSelectionHandler : function(event) {
		var uuid = jsslab.settingsPage.getSelectedConceptSetUuid();
		
		jQuery.ajax({
			"url" : openmrsContextPath + "/ws/rest/v1/jsslab?getConceptsByConceptSet",
			"data" : "setUuid=" + uuid,
			"success" : function(data) {
				var concepts = data.results;
				jsslab.settingsPage.updateCodeTable(concepts);
			}
		});
	},
	
	reloadCodeList : function() {
		DWRAdministrationService.getGlobalProperty("jsslab.object.conceptSet.allConcepts", function(rootConceptUuid) {
			jQuery.ajax({
				"url" : openmrsContextPath + "/ws/rest/v1/jsslab?getConceptsByConceptSet",
				"data" : "setUuid=" + rootConceptUuid,
				"success" : function(data) {
					var codeLists = data.results;
					jQuery('#codeLists div').remove();
					for (var i = 0; i < codeLists.length; i++) {
						jsslab.settingsPage.addRowToCodeList(codeLists[i]);
					}
					jQuery('#radioSpecimenTypeCodes_'+codeLists[0].uuid).click();
					
				}
			});
		});
		
	},
	
	addRowToCodeList : function(codeList) {
		var codeListDiv = jQuery('<div />');
		var codeListInput = jQuery('<input type="radio" class="radioSpecimenTypeCode" name="codeList"  id="radioSpecimenTypeCodes_' + codeList.uuid +'" />');
		var codeListLabel = jQuery('<label for="radioSpecimenTypeCodes">' + codeList.display + '</label>');
		
		codeListInput.click(jsslab.settingsPage.codeListSelectionHandler);
		
		codeListDiv.append(codeListInput);
		codeListDiv.append(codeListLabel);
		
		jQuery('#codeLists').append(codeListDiv);
	},
	
	/**
	 * Updates the Code Table Panel with the given Concept objects.
	 * 
	 * @param concepts The Concept objects to be displayed in the Code Table Panel.
	 */
	updateCodeTable : function(concepts) {
		jsslab.settingsPage.concepts = concepts;
		
		jQuery('#codeTable tbody tr').remove();
		
		if (concepts.length > 0) {
			for (var i = 0; i < concepts.length; i++) {
				jsslab.settingsPage.addRowToCodeTable(concepts[i]);
			}
			jQuery('#codeTable tbody tr').first().click();
		} else {
			var tableRow = jQuery('<tr />');
			jQuery('#codeTable tbody').append(tableRow);
			
			tableRow = jQuery('<tr />');
			tableRow.append( jQuery('<td colspan="3" />').html( jsslab.i18n['settings.codelists.codetable.empty'] ) );
			jQuery('#codeTable tbody').append(tableRow);
		}

	},
	
	/**
	 * Adds a new row to the Code Table for the given concept.
	 * 
	 * @param concept The Concept object to be displayed in the new table row
	 * @return The newly created table row of the Code Table
	 */
	addRowToCodeTable : function(concept) {
		var tableRow = jQuery('<tr id="concept_' + concept.uuid + '"/>');

		var name = jsslab.settingsPage.getConceptName(concept, "FULLY_SPECIFIED");
		var shortName = jsslab.settingsPage.getConceptName(concept, "SHORT");
		
		tableRow.append( jQuery("<td />").html( name != null ? name.name : " " ) );
		tableRow.append( jQuery("<td />").html( shortName != null ? shortName.name : " " ) );
		tableRow.append( jQuery("<td />").html( concept.retired ? "true" : "false" ) );
		
		tableRow.click(function(event) {
			jQuery('#codeTable tbody tr').removeClass('selected');
			jQuery(this).addClass('selected');
			
			jsslab.settingsPage.selectedConceptIndex = jQuery(this).index();
			jsslab.settingsPage.showCodeEditPanel(jsslab.settingsPage.concepts[jsslab.settingsPage.selectedConceptIndex]);
			
			//if the clicked table row is not the newly created one
			var uuid = jsslab.settingsPage.getSelectedConceptUuid();
			if (jsslab.settingsPage.isValidUuid(uuid)) {
				jsslab.settingsPage.removeUnsavedCodeTableRow();
			}
		});
		
		jQuery('#codeTable tbody').append(tableRow);
		
		return tableRow;
	},
	
	/**
	 * Displays the Concept Edit Panel with the values of the given Concept object or an empty
	 * Panel if null is passed to the function.
	 * 
	 * @param concept The Concept object to be edited
	 */
	showCodeEditPanel : function(concept) {
		var shortName = jsslab.settingsPage.getConceptName(concept, "SHORT");
		jQuery('#editTextConcept').val( concept.display );
		jQuery('#editCode').val( shortName != null ? shortName.name : " " );
		if (concept.retired) {
			jQuery('#checkboxRetireConcept').attr('checked', 'checked');
		} else {
			jQuery('#checkboxRetireConcept').removeAttr('checked');
		}
		jQuery('#editConceptRetireReason').val(""); 
				
		jQuery('#codeEditPanel').fadeIn();
	},
	
	/**
	 * Retrieves the values entered in the Concept Edit Panel and returns them as a Concept object.
	 * 
	 * @returns A Concept object containing the user specified values 
	 */
	getConceptFromEditPanel : function() {
		//TODO test if this works + test for new concept
		var idx = jsslab.settingsPage.selectedConceptIndex;
		
		var concept = jsslab.settingsPage.concepts[idx];
		concept.name = jQuery('#editTextConcept').val();
		
		jsslab.settingsPage.setConceptName(concept, jQuery('#editTextConcept').val(), "FULLY_SPECIFIED");
		jsslab.settingsPage.setConceptName(concept, jQuery('#editCode').val(), "SHORT");
		
		return concept;
	},
	
	/**
	 * Display an empty Concept Edit Panel to allow the User to add a new Concept/Code pair
	 * to the currently selected ConceptSet
	 */
	addNewConcept : function() {
		var newConcept = {
				"uuid" : "",
				"display" : "",
				"names" : [{
					"name" : "",
					"conceptNameType" : "SHORT",
					"locale" : "EN"
				}]
		};

		jsslab.settingsPage.concepts[jsslab.settingsPage.concepts.length] = newConcept;
		var tr = jsslab.settingsPage.addRowToCodeTable(newConcept);
		jQuery('#buttonAddNewCode').attr('disabled', 'disabled');

		tr.click();
//		jsslab.settingsPage.showCodeEditPanel(newConcept);
	},
	
	/**
	 * Removes the currently selected table row from the Code Table, if it belongs to an
	 * unsaved Concept object.
	 */
	removeUnsavedCodeTableRow : function() {
		var lastRow = jQuery('#codeTable tbody tr:last');
		var lastRowId = lastRow.attr('id');
		var uuid = lastRowId.substring(lastRowId.lastIndexOf("_"+1));
		if ( !jsslab.settingsPage.isValidUuid(uuid) ) {
			lastRow.remove();
			jQuery('#buttonAddNewCode').removeAttr('disabled');
		}
	},
	
	/**
	 * Saves a Concept object
	 * 
	 * @param concept The Concept to be saved
	 * @param uuid The UUID of the Concept if it is being updated or null if it is being added
	 */
	saveConcept : function(concept, uuid) {
		var baseUrl = openmrsContextPath + "/ws/rest/v1/concept";
		
		// update existing concept
		if (jsslab.settingsPage.isValidUuid(uuid)) {
			// if the concept already exists save/update its names
			baseUrl += "/" + uuid;
			
			var fullname = jsslab.settingsPage.getConceptName(concept, "FULLY_SPECIFIED");
			var shortname = jsslab.settingsPage.getConceptName(concept, "SHORT");
			
			jsslab.settingsPage.saveConceptName(
					uuid, 
					fullname, 
					fullname.uuid,
					
					//wait for the first call to return before starting the next, to avoid concurrency problems
					jsslab.settingsPage.saveConceptName(
							uuid,
							shortname,
							shortname.uuid,
							null
					)
			);
			jsslab.settingsPage.setConceptRetired(concept, jQuery('#checkboxRetireConcept').attr('checked'));
		
		// save new concept
		} else {
			// first save the concept and then its names
			jQuery.ajax({
				"url" : baseUrl,
				"type" : "POST",
				"contentType" : "application/json",
				"data" : JSON.stringify(jsslab.settingsPage.getSaveableConcept(concept)),
				"success" : function(savedConcept) {
					var conceptName = jsslab.settingsPage.getConceptName(concept, "SHORT");
					
					jsslab.settingsPage.saveConceptName(savedConcept.uuid, conceptName, conceptName.uuid,
							jsslab.settingsPage.updateCodeTable(jsslab.settingsPage.concepts)
					);
					
					jsslab.settingsPage.addConceptToConceptSet(savedConcept);
					jsslab.settingsPage.setConceptRetired(savedConcept, jQuery('#checkboxRetireConcept').attr('checked'));
					
				}
			});
		}

	},
	
	addConceptToConceptSet : function(concept) {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab?addConceptToConceptSet",
			type : "POST",
			contentType : "application/x-www-form-urlencoded",
			data: "conceptUuid=" + concept.uuid + "&setUuid=" + jsslab.settingsPage.getSelectedConceptSetUuid(),
		});
	},
	
	setConceptRetired : function(concept, retired) {
		if (retired) {
			var retireReason = jQuery('#editConceptRetireReason').val();
			var url = openmrsContextPath + "/ws/rest/v1/concept/" + concept.uuid + "?reason="+retireReason;
			
			jQuery.ajax({
				"url" : url,
				"type" : "DELETE",
				"success" : function() {
					jsslab.settingsPage.updateCodeTable(jsslab.settingsPage.concepts);
				}
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
	saveConceptName : function(conceptUuid, conceptName, conceptNameUuid, onSuccess) {
		var url = openmrsContextPath + "/ws/rest/v1/concept/" + conceptUuid + "/name";
//		if (jsslab.settingsPage.isValidUuid(conceptNameUuid)) {
//			url += "/" + conceptNameUuid;
//		}
		jQuery.ajax({
			"url" : url,
			"type" : "POST",
			"contentType" : "application/json",
			"data" : JSON.stringify(jsslab.settingsPage.getSaveableConceptName(conceptName)),
			"success" : onSuccess
		});
	},
	
	// ======================
	// util methods
	// ======================
	
	/**
	 * @returns The UUID of the currently selected ConceptSet
	 */
	getSelectedConceptSetUuid : function() {
		if (jQuery('.radioSpecimenTypeCode:checked') == null) return null;
		
		var btnId = jQuery('.radioSpecimenTypeCode:checked').attr('id');
		return btnId.substring(btnId.lastIndexOf("_")+1);
	},
	
	/**
	 * @returns The UUID of the currently selected Concept
	 */
	getSelectedConceptUuid : function() {
		var rowId = jQuery('#codeTable tbody tr.selected').attr('id');
		return rowId.substring(rowId.lastIndexOf("_")+1);
	},
	
	/**
	 * Checks whether the given UUID is valid, based on the length of the string.
	 * 
	 * @param uuid The string that is to be checked
	 * @returns {Boolean}
	 */
	isValidUuid : function(uuid) {
		return uuid != undefined && uuid != null && uuid.length >= 36;
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
};

jQuery(document).ready(function() {
	
	// ======================
	// locations
	// ======================
	
	jQuery('#hierarchyTree').jstree({
		"json_data" : {
			"ajax" : {
				"url" : "settings/getAllRootLocations.htm",
			}
		},
		"plugins" : [ "themes", "json_data", "ui" ]
	}).bind("select_node.jstree", function (e, data) { 
		var locationUuid = data.rslt.obj.attr('id'); 
		jsslab.settingsPage.showLocationEditPanel(locationUuid);
		
	});
	
	jQuery.ajax({
		"url" : openmrsContextPath + "/ws/rest/v1/locationtag?q=" + jsslab.constants.labTagName,
		"type" : "GET",
		"success" : function(locationTags) {
			jsslab.settingsPage.labLocationTag = locationTags.results[0];
		}
	});
	
	
	jQuery('#addLocation').click(function(event) {
		jsslab.settingsPage.showLocationEditPanel(locationUuid);
	});
	jQuery('#btnSaveLocation').click(function(event) {
		jsslab.settingsPage.saveLocation();
		jQuery('#editLocationPanel').fadeOut();
	});
	jQuery('#btnCancelLocation').click(function(event) {
		jQuery('#editLocationPanel').fadeOut();
	});
	
	// ======================
	// global properties
	// ======================
	
	jQuery('.globalPropertyStringSubmit').click(function(event) {
		event.preventDefault();
		var btnId = jQuery(this).attr('id');
		var gpId = btnId.substring(btnId.lastIndexOf("_")+1);
		var textFieldSelector = '#globalPropertyStringSelect_'+gpId;
		
		var gpName = jQuery(textFieldSelector).attr('name');
		var gpValue = jQuery(textFieldSelector).val();
		
		jsslab.saveGlobalProperty( gpName, gpValue, jQuery('#globalPropertyStringResult_'+gpId) );
	});
	jQuery('.globalPropertyObjectSubmit').click(function(event) {
		event.preventDefault();
		var btnId = jQuery(this).attr('id');
		var gpId = btnId.substring(btnId.lastIndexOf("_")+1);
		var textFieldSelector = '#globalPropertyObjectSelect_'+gpId;
		
		var gpName = jQuery(textFieldSelector).attr('name');
		var gpValue = jQuery(textFieldSelector).val();
		
		jsslab.saveGlobalProperty( gpName, gpValue, jQuery('#globalPropertyObjectResult_'+gpId) );
	});
	
	
	// ======================
	// code lists
	// ======================
	
	jQuery('.radioSpecimenTypeCode').click(jsslab.settingsPage.codeListSelectionHandler);
	
	jQuery('#btnSubstituteConceptSet').click(function(event) {
		var newUuid = jQuery('#editSubstituteConceptSet').val();
		var oldUuid = jsslab.settingsPage.getSelectedConceptSetUuid();
		
		if (jsslab.settingsPage.isValidUuid(newUuid)) {
			jQuery.ajax({
				"url" : openmrsContextPath + "/ws/rest/v1/jsslab?substituteConceptSet",
				"type" : "POST",
				"data" : "oldUuid=" + oldUuid + "&newUuid=" + newUuid,
				"success" : function(data) {
					jsslab.settingsPage.reloadCodeList();
				}
			});
		}
	});
	
	jQuery('#buttonAddNewCode').click(function(event) {
		jsslab.settingsPage.addNewConcept();
		event.preventDefault();
	});

	jQuery('#buttonSaveCode').click(function(event) {
		event.preventDefault();
		//TODO form validation
		
		var concept = jsslab.settingsPage.getConceptFromEditPanel();
		var uuid = jsslab.settingsPage.getSelectedConceptUuid();
		
		jsslab.settingsPage.saveConcept(concept, uuid);
		
		jQuery('#codeEditPanel').fadeOut();
		jQuery('#buttonAddNewCode').removeAttr('disabled');
		
		//TODO update table
	});
	
	jQuery('#buttonCancelEditingCode').click(function(event) {
		event.preventDefault();
		
		jQuery('#codeEditPanel').fadeOut();
		jQuery('#buttonAddNewCode').removeAttr('disabled');
		
		jsslab.settingsPage.removeUnsavedCodeTableRow();
	});
	
	jQuery('#checkboxRetireConcept').click(function(event) {
		jQuery('#panelRetireReason').toggle();
	});
	
	
	jQuery('.radioSpecimenTypeCode:first').click();
});

