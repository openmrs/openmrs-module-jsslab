jQuery(document).ready(function() {
	
	/**
	 * Wraps all variables and functions used in this script
	 */
	jsslab.settingsPage = {
		
		/** The currently selected <code>Location</code> */
		location : null,
		
		/** Whether the currently selected <code>Location</code> is the managed <code>Location</code> */
		isManagedLocation : false,

		/** Whether the currently selected <code>Location</code> is a referral <code>Location</code> */
		isReferralLocation : false,

		/** The <code>LocationTag</code> resembling a Lab as used in this module */
		labLocationTag : null,

		/** All <code>Concept</code>s that are members of the currently selected <code>ConceptSet</code> */
		concepts : null,
		
		/** The index of the currently selected <code>Concept</code> */
		selectedConceptIndex : -1,
	
		/**
		 * Contains all static autocomplete data used throughout the page. The nested arrays represent their types by 
		 * label (display attribute) and uuid (uuid attribute).
		 * The arrays are filled by queries that will return only those objects that actually fit into the appropriate
		 * field.
		 */
		autocomplete : {
			orderTypes : [],
			conceptSets : [],
			locations : [],
			concepts : [],
		},
		
		/**
		 * Initializes all autocompletes used throughout the page. Loads the static autocomplete data into their
		 * appropriate arrays and sets up callback functions for dynamic autocompletes.
		 */
		initAutoCompletes : function() {
			var url = openmrsContextPath + "/ws/rest/v1/jsslab?getAllOrderTypes"
			jQuery.ajax({
				"url" : url,
				"success" : function(data) {
					var orderTypes = data.results;
					for (var i = 0; i < orderTypes.length; i++) {
						jsslab.settingsPage.autocomplete.orderTypes[i] = {
								label: orderTypes[i].display,
								uuid: orderTypes[i].uuid
						}
					}
					jQuery('#gpLabOrderTypeSelect').autocomplete({
						source : jsslab.settingsPage.autocomplete.orderTypes,
						select : function(event, ui) { 
							jQuery('#gpLabOrderTypeSelectVal').val(ui.item.uuid);
						}
					});
				}
			});
			url = openmrsContextPath + "/ws/rest/v1/jsslab?getAllConceptSets"
			jQuery.ajax({
				"url" : url,
				"success" : function(data) {
					var concepts = data.results;
					for (var i = 0; i < concepts.length; i++) {
						jsslab.settingsPage.autocomplete.conceptSets[i] = {
								label: concepts[i].display,
								uuid: concepts[i].uuid
						}
					}
					jQuery('#gpAllConceptsSelect').autocomplete({
						source : jsslab.settingsPage.autocomplete.conceptSets,
						select : function(event, ui) { 
							jQuery('#gpAllConceptsSelectVal').val(ui.item.uuid);
						}
					});
					jQuery('#editSubstituteConceptSet').autocomplete({
						source : jsslab.settingsPage.autocomplete.conceptSets,
						select : function(event, ui) { 
							jQuery('#editSubstituteConceptSetVal').val(ui.item.uuid);
						}
					});
				}
			});
			url = openmrsContextPath + "/ws/rest/v1/location"
			jQuery.ajax({
				"url" : url,
				"success" : function(data) {
					var locations = data.results;
					for (var i = 0; i < locations.length; i++) {
						jsslab.settingsPage.autocomplete.locations[i] = {
								label: locations[i].display,
								uuid: locations[i].uuid
						}
					}
					jQuery('#gpHomeLabSelect').autocomplete({
						source : jsslab.settingsPage.autocomplete.locations,
						select : function(event, ui) { 
							jQuery('#gpHomeLabSelectVal').val(ui.item.uuid);
						}
					});
				}
			});
			
			url = openmrsContextPath + "/ws/rest/v1/concept"
			jQuery('#gpInternalElectronicSelect').autocomplete({
				source : function(request, response) {
					jQuery.ajax({
						"url" : url + "?q=" + request.term,
						"success" : function(data) {
							var concepts = data.results;
							for (var i = 0; i < concepts.length; i++) {
								jsslab.settingsPage.autocomplete.concepts[i] = {
										label: concepts[i].display,
										uuid: concepts[i].uuid
								}
							}
							response(jsslab.settingsPage.autocomplete.concepts);
						}
					});
				},
				select : function(event, ui) { 
					jQuery('#gpInternalElectronicSelectVal').val(ui.item.uuid);
				}
			});
		},
	
		// =================================
		// methods for lab selection section
		// =================================
		
		/**
		 * Displays the location edit panel for the selected <code>Location</code>
		 * 
		 * @param locationUuid The UUID of the currently selected <code>Location</code>
		 */
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
					
					if (jsslab.settingsPage.checkIfReferral(location)) {
						jQuery('#editLocationReferral').attr('checked', 'checked');
					} else {
						jQuery('#editLocationReferral').removeAttr('checked');
					}
					
				}
			});
			
			
			jQuery('#editLocationPanel').fadeIn();
		},
		
		/**
		 * Determines whether the currently selected <code>Location</code> is referral
		 * 
		 * A referral <code>Location</code> in this context has an externally managed Lab and can
		 * received <code>LabOrder</code>s.
		 * 
		 * @return Boolean whether the selected <code>Location</code> is a referral Lab or not
		 */
		checkIfReferral : function(location) {
			jsslab.settingsPage.isReferralLocation = false;
			
			if (location.tags != null && location.tags.length > 0) {
				for (var i = 0; i < location.tags.length; i++) {
					var tagUuid = location.tags[i].uuid;
					if (tagUuid == jsslab.settingsPage.labLocationTag.uuid) {
						
						jsslab.settingsPage.isReferralLocation = true;
						break;
					}
				}
			}
			return jsslab.settingsPage.isReferralLocation;
		},
		
		saveLocation : function() {
			var location = {};
			
			//TODO validate
			location.name = jQuery('#editLocationName').val();
			location.address1 = jQuery('#editLocationAddress').val();
			
			var isManaged = jQuery('#editLocationManaged').attr('checked');
			var isReferral = jQuery('#editLocationReferral').attr('checked');
			
			if (isManaged != jsslab.settingsPage.isManagedLocation) {
				if (isManaged) {
					jsslab.saveGlobalProperty("jsslab.object.location.homeLab", jsslab.settingsPage.location.uuid, null, null);
				} else {
					jsslab.saveGlobalProperty("jsslab.object.location.homeLab", "", null, null);
				}
			}
			
			if (isReferral != jsslab.settingsPage.isReferralLocation) {
				var tags = jsslab.settingsPage.location.tags;
				location.tags = [];
				if (tags != null) {
					for (var i = 0; i < tags.length; i++) {
						location.tags[i] = tags[i].uuid;
					}
				}
				
				if (isReferral) {
					location.tags[location.tags.length] = jsslab.settingsPage.labLocationTag.uuid;
				} else {
					//TODO find a mechanism to remove a locationTag from the location
					for (var i = 0; i < location.tags.length; i++) {
						if (jsslab.settingsPage.labLocationTag.uuid == location.tags[i].uuid) {
							locations.tags.splice(i, 1);
						}
					}
				}
				
			}
			
			
			var locUuid = jsslab.settingsPage.location.uuid;
			var url = openmrsContextPath + "/ws/rest/v1/location/";
			if (jsslab.isValidUuid(locUuid)) url += "/" + locUuid;
			
			jQuery.ajax({
				"url" : url,
				"type" : "POST",
				"contentType" : "application/json",
				"data" : JSON.stringify( location ),
				"success" : function(result) {
					//TODO update tree
					//TODO display errors
				}
			});
		},
		
	//	getSaveableLocation : function(location) {
	//		location.display = undefined;
	//		location.uuid = undefined;
	//		
	//		var tags = []; 
	//		for (var i = 0; i < location.tags.length; i++) {
	//			tags[i] = location.tags[i].uuid;
	//		}
	//		location.tags = tags;
	//		
	//		return location;
	//	},
		
		
		
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
		
		/**
		 * Handles what should happen when a <code>ConceptSet</code> is selected from the list
		 */
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
		
		/**
		 * Retrieves the list of all <code>ConceptSet</code>s and displays them.
		 * 
		 * Should be called when the list changes (e.g. on substitution of a <code>ConceptSet</code> by another
		 */
		reloadCodeList : function() {
			DWRAdministrationService.getGlobalProperty("jsslab.object.concept.allConcepts", function(rootConceptUuid) {
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
		
		/**
		 * Adds a new <code>ConceptSet</code> to the list of <code>ConceptSet</code>s.
		 * 
		 *  @param codeList The <code>ConceptSet</code> to be added
		 */
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
	
			var name = jsslab.getConceptName(concept, "FULLY_SPECIFIED");
			var shortName = jsslab.getConceptName(concept, "SHORT");
			
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
				if (jsslab.isValidUuid(uuid)) {
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
			var shortName = jsslab.getConceptName(concept, "SHORT");
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
			
			jsslab.setConceptName(concept, jQuery('#editTextConcept').val(), "FULLY_SPECIFIED");
			jsslab.setConceptName(concept, jQuery('#editCode').val(), "SHORT");
			
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
			if ( !jsslab.isValidUuid(uuid) ) {
				lastRow.remove();
				jQuery('#buttonAddNewCode').removeAttr('disabled');
			}
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
		
	};

	/*
	 * button binding	locations
	 */ 
	
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
	
	/*
	 * button binding	global properties
	 */ 
	
	jQuery('.globalPropertyStringSubmit').click(function(event) {
		event.preventDefault();
		var btnId = jQuery(this).attr('id');
		var gpId = btnId.substring(btnId.lastIndexOf("_")+1);
		var textFieldSelector = '#globalPropertyStringSelect_'+gpId;
		
		var gpName = jQuery(textFieldSelector).attr('name');
		var gpValue = jQuery(textFieldSelector).val();
		
		jsslab.saveGlobalProperty( gpName, gpValue, jQuery('#globalPropertyStringResult_'+gpId), jsslab.settingsPage.setSaveResult );
	});
	jQuery('.gpObjectSubmit').click(function(event) {
		event.preventDefault();
		var btnId = jQuery(this).attr('id');
		var gpId = btnId.substring(0, btnId.indexOf("Submit"));
		var textFieldSelector = '#' + gpId + 'SelectVal';
		var resultSelector = '#' + gpId + 'Result';
		
		var gpName = jQuery(textFieldSelector).attr('name');
		var gpValue = jQuery(textFieldSelector).val();
		
		jsslab.saveGlobalProperty( gpName, gpValue, jQuery(resultSelector), jsslab.settingsPage.setSaveResult );
	});
	
	/*
	 * button binding	code lists
	 */ 
	
	jQuery('.radioSpecimenTypeCode').click(jsslab.settingsPage.codeListSelectionHandler);
	
	jQuery('#btnSubstituteConceptSet').click(function(event) {
		var newUuid = jQuery('#editSubstituteConceptSetVal').val();
		var oldUuid = jsslab.settingsPage.getSelectedConceptSetUuid();
		
		if (jsslab.isValidUuid(newUuid)) {
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
		
		jsslab.saveConcept(concept, uuid, jQuery('#checkboxRetireConcept').attr('checked'), 
				jsslab.settingsPage.updateCodeTable(jsslab.settingsPage.concepts)
		);
		
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
	
	
	/*
	 * post button binding calls
	 */ 
	
	jsslab.settingsPage.initAutoCompletes();
	jQuery('.radioSpecimenTypeCode:first').click();
	
});

