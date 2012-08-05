editing = true;

jsslab.catalogPage = {
		
	investigations : null,
	tests : null,
	newInvestigationIndex : -1,
	selectedTestIndex : -1,
	
	editingInvestigation : false,
	isNewInvestigation : false,
	
//		setSaveResult : function(element, result) {
//			if (result.code == "success") {
//				element.attr('class', 'success');
//			} else {
//				element.attr('class', 'error');
//			}
//			element.text(result.message);
//			element.fadeIn();
//			setTimeout( function(){ element.fadeOut(); }, 2000 );
//		},
	
	updateInvestigationTable : function(investigations) {
		jsslab.catalogPage.investigations = investigations;
		
		jQuery('#investigationTable tbody tr').remove();
		
		if (investigations.length > 0) {
			for (var i = 0; i < investigations.length; i++) {
				jsslab.catalogPage.addInvestigationTableRow(investigations[i]);
			}
			jQuery('#investigationTable tbody tr').first().click();
		} else {
			var tableRow = jQuery('<tr />');
			jQuery('#investigationTable tbody').append(tableRow);
			
			tableRow = jQuery('<tr />');
			tableRow.append( jQuery('<td colspan="2" />').html( jsslab.i18n['catalog.investigations.investigationTable.empty'] ) );
			jQuery('#investigationTable tbody').append(tableRow);
		}
		
	},
	
	/**
	 * Adds a new table row to the table of Investigations.
	 * 
	 * This will also cause the investigation edit panel and the list of tests to be updated.
	 * 
	 * @param investigation The investigation to be added to the table. If null is passed an empty row is created.
	 */
	addInvestigationTableRow : function(investigation) {
		var uuid = "";
		var section = "&nbsp;";
		var inv = "&nbsp;";
		
		if (investigation == null) {
			uuid = jsslab.catalogPage.newInvestigationIndex--;
		} else {
			uuid = investigation.uuid;
			section = investigation.testGroupConcept.display;
			inv = investigation.testPanelConcept.display;
		}
		var tableRow = jQuery('<tr id="investigation_' + uuid + '"/>');
		
		tableRow.append( jQuery("<td />").html( section ) );
		tableRow.append( jQuery("<td />").html( inv ) );
		tableRow.click(function(event) {
			jsslab.catalogPage.handleInvestigationSelection(jQuery(this));
		});
		jQuery('#investigationTable tbody').append(tableRow);
		
		return tableRow;
	},
	
	handleInvestigationSelection : function(tableRow) {
		jQuery('#investigationTable tbody tr').removeClass('selected');
		tableRow.addClass('selected');
		
		var idx = tableRow.index();
		var investigation = null;
		
		if (idx < jsslab.catalogPage.investigations.length) {
			investigation = jsslab.catalogPage.investigations[idx];
			
			jQuery.ajax({
				"url" : openmrsContextPath + "/ws/rest/v1/jsslab/labtestpanel/" + investigation.uuid + "?v=full",
				"type" : "GET",
				"success" : function(investigation) {
					jsslab.catalogPage.updateTestList(investigation.tests);
				}
			});
		} else {
			jsslab.catalogPage.updateTestList(null);
		}
		jsslab.catalogPage.showInvestigationEditPanel(investigation);
		
		jQuery('#btnRemoveInvestigation').removeAttr('disabled');
		jsslab.catalogPage.setEditingInvestigation(true, investigation == null);
	},
	
	showInvestigationEditPanel : function(investigation) {
		//TODO name concept
		//TODO section concept
		jQuery('#editInvestigationCode').val(investigation == null || investigation.testPanelCode == null ? "" : investigation.testPanelCode);
		jQuery('#editInvestigationCost').val(investigation == null || investigation.cost == null ? "" : investigation.cost.value);
		jQuery('#editInvestigationTurnaround').val(investigation == null || investigation.turnaround == null ? "" : investigation.turnaround);
		jQuery('#editInvestigationHoldTime').val(investigation == null || investigation.holdTime == null ? "" : investigation.holdTime);
		
		jQuery('#preconditionList option').remove();
		jQuery('#specimenSelectionTable tbody tr').remove();

		if (investigation != null) {
			if (investigation.preconditions != null) {
				for (var i = 0; i < investigation.preconditions.length; i++) {
					
					var precondition = investigation.preconditions[i];
					
					jQuery('#preconditionList').append( jQuery('<option value="' + precondition.uuid + '">' + precondition.display + '</option>') );
				}
			}
			if (investigation.specimenTemplates != null) {
				for (var i = 0; i < investigation.specimenTemplates.length; i++) {
					
					var specimenTemplate = investigation.specimenTemplates[i];
					
					var tableRow = jQuery('<tr id="' + specimenTemplate.uuid + '" />');
					//			tableRow.append( jQuery("<td />").html( specimenType.display ) );
					tableRow.append( jQuery("<td />").html( specimenTemplate.display ) );
					jQuery('#specimenSelectionTable tbody').append( tableRow );
				}
			}
			
		}
		
		jQuery('#investigationEditPanel').fadeIn();
	},
	
	updateTestList : function(tests) {
		jsslab.catalogPage.tests = tests;
		
		jQuery('#testList option').remove();
		if (tests != null && tests.length > 0) {
			for (var i = 0; i < tests.length; i++) {
				var listOption = jQuery('<option value="' + tests[i].uuid + '" >' + tests[i].display + '</option>');
				jQuery('#testList').append(listOption);
			}
		}
	},
	
	showTestEditPanel : function(test) {
		
		if (test != null) {
			//setup test edit panel with values
		}
		jQuery('#testEditPanel').fadeIn();
	},
	
	deleteSelectedInvestigation : function() {
		var selectedtableRow = jQuery('#investigationTable tbody tr.selected');
		var tableRowId = selectedtableRow.attr('id')
		var investigationUuid = tableRowId.substring(tableRowId.lastIndexOf("_")+1);
		var reason = "Deleted from UI";
		
		if (jsslab.catalogPage.isValidUuid(investigationUuid)) {
			jQuery.ajax({
				"url" : openmrsContextPath + "/ws/rest/v1/jsslab/labtestpanel/" + investigationUuid + "?reason="+reason,
				"type" : "DELETE",
				"success" : function() {
					//TODO consider adding a success confirmation
				}
			});
		}
		selectedtableRow.remove();
	},
	
	// ======================
	// util methods
	// ======================
	
	setEditingInvestigation : function(editing, isNew) {
		jsslab.catalogPage.editingInvestigation = editing;
		jsslab.catalogPage.isNewInvestigation = isNew;
		if (editing) {
			if (isNew) {
				jQuery('#btnAddInvestigation').attr('disabled', 'disabled');
			} else {
				jQuery('#btnAddInvestigation').removeAttr('disabled');
				//TODO remove unsaved row, if any exists
			}
		} else {
			jQuery('#btnAddInvestigation').removeAttr('disabled');
		}
	},
	/**
	 * Checks whether the given UUID is valid, based on the length of the string.
	 * 
	 * @param uuid The string that is to be checked
	 * @returns {Boolean}
	 */
	isValidUuid : function(uuid) {
		return uuid.length >= 36;
	},
	
};

jQuery(document).ready(function() {
	
	jQuery('#selectLaboratory').change(function(event) {
		var locationUuid = jQuery('#selectLaboratory option:selected').val();
		
		jQuery.ajax({
			"url" : openmrsContextPath + "/ws/rest/v1/jsslab/labtestpanel?v=full",
			"type" : "GET",
			"success" : function(data) {
				var investigations = data.results;
				//TODO only retrieve investigations for the currently selected lab
				jsslab.catalogPage.updateInvestigationTable(investigations);
			}
		});
		
		jQuery('#btnRemoveInvestigation').attr('disabled', 'disabled');
		jsslab.catalogPage.setEditingInvestigation(false, false);
	});
	
	jQuery('#btnAddInvestigation').click(function (event) {
		event.preventDefault();
		var tableRow = jsslab.catalogPage.addInvestigationTableRow(null);
		tableRow.click();
		
		jQuery('#btnRemoveInvestigation').removeAttr('disabled');
		jsslab.catalogPage.setEditingInvestigation(true, true);
	});
	
	jQuery('#btnRemoveInvestigation').click(function (event) {
		event.preventDefault();
		
		jsslab.catalogPage.deleteSelectedInvestigation();
		
		jQuery('#investigationEditPanel').fadeOut();
		jQuery('#btnRemoveInvestigation').attr('disabled', 'disabled');
		jsslab.catalogPage.setEditingInvestigation(false, false);
	});
	
	jQuery('#buttonSaveInvestigation').click(function (event) {
		event.preventDefault();
		//TODO check whether there are any validation errors
		//TODO save investigation
		jQuery('#investigationEditPanel').fadeOut();
		jsslab.catalogPage.setEditingInvestigation(false, false);
	});
	
	jQuery('#buttonCancelInvestigation').click(function (event) {
		event.preventDefault();
		
		jsslab.catalogPage.deleteSelectedInvestigation();
		
		jQuery('#investigationEditPanel').fadeOut();
		jQuery('#btnRemoveInvestigation').attr('disabled', 'disabled');
		jsslab.catalogPage.setEditingInvestigation(false, false);
	});
	
	
	jQuery('#testList').change(function(event) {
		var testUuid = jQuery('#testList option:selected').val();
		
		jQuery.ajax({
			"url" : openmrsContextPath + "/ws/rest/v1/jsslab/labtest/" + testUuid + "?v=full",
			"type" : "GET",
			"success" : function(test) {
				jsslab.catalogPage.showTestEditPanel(test);
			}
		});
	});
	
	jQuery('#buttonSaveTest').click(function (event) {
		event.preventDefault();
		//TODO check whether there are any validation errors
		//TODO save test
		jQuery('#testEditPanel').fadeOut();
	});
	jQuery('#buttonCancelTest').click(function (event) {
		event.preventDefault();
		jQuery('#testEditPanel').fadeOut();
	});
	
//	jQuery('#finishSetup').click(function(event) {
//		event.preventDefault();
//		jQuery.ajax({
//			"url" : "setup/finishSetup.htm",
//			"type" : "GET",
//			"success" : function(view) {
//				window.location.href = view;
//			}
//		});
//	});
	
	jQuery('#selectLaboratory').change();
});

