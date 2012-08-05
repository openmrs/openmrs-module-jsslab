// define startsWith String function if not exists
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}

jsslab.settingsPage = {
		
	locations : null,
	
	selectedConceptIndex : 0,
	
	concepts : null,

	
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
		tableRow.append( jQuery("<td />").html( concept.display ) );
		tableRow.append( jQuery("<td />").html( jsslab.settingsPage.getConceptShortName(concept).name ) );
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
		jQuery('#editTextConcept').val( concept.display );
		jQuery('#editCode').val( jsslab.settingsPage.getConceptShortName(concept).name );
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
		
		var shortName = jsslab.settingsPage.getConceptShortName(concept);
		shortName.name = jQuery('#editCode').val();
		
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
					"conceptNameType" : "SHORT"
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
		var url = openmrsContextPath + "/ws/rest/v1/concept";
		if (jsslab.settingsPage.isValidUuid(uuid)) {
			// if the concept already exists save/update its names
			url += "/" + uuid;
			
			for (var i = 0; i < concept.names.length; i++) {
				jsslab.settingsPage.saveConceptName(uuid, concept.names[i], concept.names[i].uuid);
			}
		} else {
			// if concept does not exist, save the concept and then its code
			jQuery.ajax({
				"url" : url,
				"type" : "POST",
				"contentType" : "application/json",
				"data" : JSON.stringify(jsslab.settingsPage.getSaveableConcept(concept)),
				"success" : function(savedConcept) {
					var conceptName = jsslab.settingsPage.getConceptShortName(concept);
					jsslab.settingsPage.saveConceptName(savedConcept.uuid, conceptName, conceptName.uuid);
				}
			});
		}
		jsslab.settingsPage.updateCodeTable(jsslab.settingsPage.concepts);
	},
	
	/**
	 * Saves a ConceptName object
	 * 
	 * @param conceptUuid The UUID of the Concept object to which the ConceptName belongs 
	 * @param conceptName The ConceptName to be saved
	 * @param conceptNameUuid The UUID of the ConceptName if is being updated or null if it is being added
	 */
	saveConceptName : function(conceptUuid, conceptName, conceptNameUuid) {
		var url = openmrsContextPath + "/ws/rest/v1/concept/" + conceptUuid + "/name";
		if (jsslab.settingsPage.isValidUuid(conceptNameUuid)) {
			url += "/" + conceptNameUuid;
		}
		jQuery.ajax({
			"url" : url,
			"type" : "POST",
			"contentType" : "application/json",
			"data" : JSON.stringify(jsslab.settingsPage.getSaveableConceptName(conceptName)),
			"success" : function(data) {
			}
		});
	},
	
	// ======================
	// methods for global property section
	// ======================
	
	/**
	 * Saves a global property.
	 * 
	 * A result is be passed to the callback function containing a result code ("success" or other) and
	 * a message explaining the code.
	 * 
	 * @param property
	 * @param value
	 * @param resultElement
	 */
	saveGlobalProperty : function(property, value, resultElement) {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab?saveGlobalProperty",
			type : "POST",
			contentType : "application/x-www-form-urlencoded",
			data: "property=" + property + "&value=" + value,
			success : function(result) {
				jsslab.settingsPage.setSaveResult(resultElement, result);
			}
		});
	},
	
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
	getConceptShortName : function(concept) {
		var shortName = "";
		for (var i = 0; i < concept.names.length; i++) {
			if (concept.names[i].conceptNameType == "SHORT") {
				shortName = concept.names[i];
				break;
			}
		}
		return shortName;
	},
	
	/**
	 * Creates a Concept object from an existing one, removing any transiert fields that cannot be saved (e.g. display)
	 * 
	 * @param concept The Concept to be prepared for saving
	 * @returns a Concept object that can be saved via REST
	 */
	getSaveableConcept : function(concept) {
		var saveableConcept = {
			"name" : concept.display
		};
		return saveableConcept;
	},
	
	/**
	 * Creates a ConceptName object from an existing one removing any transient fields that cannot be saved (e.g. display)
	 * 
	 * @param conceptName The ConceptName to be prepared for saving
	 * @returns a ConceptName object that can be saved via REST
	 */
	getSaveableConceptName : function(conceptName) {
		var saveableConceptName = {
				"name" : conceptName.name,
				"conceptNameType" : conceptName.conceptNameType,
		};
		return saveableConceptName;
	},
};

jQuery(document).ready(function() {
	
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
		
		jsslab.settingsPage.saveGlobalProperty( gpName, gpValue, jQuery('#globalPropertyStringResult_'+gpId) );
	});
	jQuery('.globalPropertyObjectSubmit').click(function(event) {
		event.preventDefault();
		var btnId = jQuery(this).attr('id');
		var gpId = btnId.substring(btnId.lastIndexOf("_")+1);
		var textFieldSelector = '#globalPropertyObjectSelect_'+gpId;
		
		var gpName = jQuery(textFieldSelector).attr('name');
		var gpValue = jQuery(textFieldSelector).val();
		
		jsslab.settingsPage.saveGlobalProperty( gpName, gpValue, jQuery('#globalPropertyObjectResult_'+gpId) );
	});
	
	
	// ======================
	// code lists
	// ======================
	
	jQuery('.radioSpecimenTypeCode').click(function(event) {
		var uuid = jsslab.settingsPage.getSelectedConceptSetUuid();
		
		jQuery.ajax({
			"url" : openmrsContextPath + "/ws/rest/v1/jsslab?getConceptsByConceptSet",
			"data" : "setUuid=" + uuid,
			"success" : function(data) {
				var concepts = data.results;
				jsslab.settingsPage.updateCodeTable(concepts);
			}
		});
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
	
	
	jQuery('.radioSpecimenTypeCode:first').click();
});

