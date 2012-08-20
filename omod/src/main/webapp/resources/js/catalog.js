jQuery(document).ready(function() {
	
	var editing = true;
	
	jsslab.catalogPage = {

		/** Array of all available <code>Investigation</code>s (LabTestPanels) */
		investigations : null,
		
		/** The currently selected <code>Investigation/LabTestPanel</code> */
		selectedInvestigation : null,
		
		/** Indicates whether an <code>Investigation</code> is currently selected and thus opened for editing */
		editingInvestigation : false,
		
		/** Indicates whether the selected <code>Investigation</code> is newly created and thus not previously persisted */
		isNewInvestigation : false,
		
		selectedPreconditionUuid : null,

		/** Array of all <code>LabTest</code>s that belong to the currently selected <code>Investigation</code> */
		tests : null,

		/** The currently selected <code>LabTest</code> */
		selectedTest : null,

		selectedTestRange : null,
		
		
		/** Index to use for newly created <code>Investigation</code>s. Serves as dummy UUID. */
		newInvestigationIndex : -1,
		/** Index to use for newly created answer <code>Concept</code>s for <code>LabPrecondition</code>s. Serves as dummy UUID. */
		newAnswerIndex : -1,
		/** Index to use for newly created answer <code>Concept</code>s for <code>LabPrecondition</code>s. Serves as dummy UUID. */
		newTestRangeIndex : -1,
		
		/**
		 * Contains all static autocomplete data used throughout the page. 
		 * 
		 * The nested arrays represent their types by label (display attribute) and uuid (uuid attribute).
		 * The arrays are filled by queries that will return only those objects that actually fit into the appropriate
		 * field.
		 */
		autocomplete : {
			investigationNames : [],
			sectionNames : [],
			preconditionQuestions : [],
			testNames : [],
			testRangeUnits : [],
		},
		
		/**
		 * Initializes all autocompletes used throughout the page. Loads the static autocomplete data into their
		 * appropriate arrays and sets up callback functions for dynamic autocompletes.
		 */
		initAutoCompletes : function() {
			var url = openmrsContextPath + "/ws/rest/v1/jsslab/labtestpanel?v=default"
			jQuery.ajax({
				"url" : url,
				"success" : function(data) {
					var investigations = data.results;
					for (var i = 0; i < investigations.length; i++) {
						jsslab.catalogPage.autocomplete.investigationNames[i] = {
								label: investigations[i].testPanelConcept.display,
								uuid:  investigations[i].testPanelConcept.uuid
						}
						jsslab.catalogPage.autocomplete.sectionNames[i] = {
								label: investigations[i].testGroupConcept.display,
								uuid:  investigations[i].testGroupConcept.uuid
						}
					}
					
					jQuery('#editInvestigationName').autocomplete({
						source : jsslab.catalogPage.autocomplete.investigationNames,
						select : function(event, ui) { 
							jQuery('#editInvestigationNameVal').val(ui.item.uuid);
						}
					});
					jQuery('#editInvestigationSection').autocomplete({
						source : jsslab.catalogPage.autocomplete.sectionNames,
						select : function(event, ui) { 
							jQuery('#editInvestigationSectionVal').val(ui.item.uuid);
						}
					});
				}
			});
			
			url = openmrsContextPath + "/ws/rest/v1/jsslab?getAllConceptQuestions"
			jQuery('#editPreconditionQuestion').autocomplete({
				source : function(request, response) {
					jQuery.ajax({
						"url" : url + "&q=" + request.term,
						"success" : function(data) {
							var concepts = data.results;
							for (var i = 0; i < concepts.length; i++) {
								jsslab.catalogPage.autocomplete.preconditionQuestions[i] = {
										label: concepts[i].display,
										uuid: concepts[i].uuid
								}
							}
							response(jsslab.catalogPage.autocomplete.preconditionQuestions);
						}
					});
				},
				select : function(event, ui) { 
					jQuery('#gpInternalElectronicSelectVal').val(ui.item.uuid);
				}
			});
			
			url = openmrsContextPath + "/ws/rest/v1/jsslab/labtest?v=default"
			jQuery.ajax({
				"url" : url,
				"success" : function(data) {
					var tests = data.results;
					for (var i = 0; i < tests.length; i++) {
						jsslab.catalogPage.autocomplete.testNames[i] = {
								label: tests[i].testConcept.display,
								uuid:  tests[i].testConcept.uuid
						}
					}
					
					jQuery('#editTestName').autocomplete({
						source : jsslab.catalogPage.autocomplete.testNames,
						select : function(event, ui) { 
							jQuery('#editTestNameVal').val(ui.item.uuid);
						}
					});
				}
			});
			
			url = openmrsContextPath + "/ws/rest/v1/jsslab?getAllTestRangeUnits"
			jQuery('#selectNumericResultUnit').autocomplete({
				source : function(request, response) {
					jQuery.ajax({
						"url" : url + "&q=" + request.term,
						"success" : function(data) {
							jsslab.catalogPage.autocomplete.testRangeUnits = data.results;
							response(jsslab.catalogPage.autocomplete.testRangeUnits);
						}
					});
				},
				select : function(event, ui) { 
					jQuery('#gpInternalElectronicSelectVal').val(ui.item.uuid);
				}
			});
		},
		
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
		
		/**
		 * Reloads the table of available <code>Investigation</code>s (LabTestPanels)
		 * 
		 * @param The <code>Investigation</code>s to be displayed in the table
		 */
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
				tableRow.append( jQuery('<td colspan="2" />').html( jsslab.i18n.catalog['investigations.investigationTable.empty'] ) );
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
			var uuid;
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
		
		/**
		 * Handles what should happen when an <code>Investigation</code> is selected from the list
		 * 
		 * @param tableRow The table row that was selected
		 */
		handleInvestigationSelection : function(tableRow) {
			var currentTr = jQuery('#investigationTable tbody tr.selected');
			currentTr.removeClass('selected');
			tableRow.addClass('selected');
	
			if (currentTr != null && currentTr.length > 0) {
				
				var currentTrUuid = currentTr.first().attr('id').substring(currentTr.first().attr('id').lastIndexOf('_')+1);
				var newTrUuid = tableRow.first().attr('id').substring(tableRow.first().attr('id').lastIndexOf('_')+1);
				
				if (!jsslab.isValidUuid(currentTrUuid) && newTrUuid != currentTrUuid) {
					currentTr.remove();
				}
			}
			
			
			var idx = tableRow.index();
			var investigation = {};
			
			if (idx < jsslab.catalogPage.investigations.length) {
				investigation = jsslab.catalogPage.investigations[idx];
				
				jQuery.ajax({
					"url" : openmrsContextPath + "/ws/rest/v1/jsslab/labtestpanel/" + investigation.uuid + "?v=full",
					"type" : "GET",
					"success" : function(investigation) {
						jsslab.catalogPage.updateTestList(investigation);
					}
				});
			} else {
				jsslab.catalogPage.updateTestList(null);
			}
			jsslab.catalogPage.showInvestigationEditPanel(investigation);
			
			jQuery('#btnRemoveInvestigation').removeAttr('disabled');
			jsslab.catalogPage.setEditingInvestigation(true, investigation == null);
		},
		
		/**
		 * Displays the given investigation in the edit view
		 * 
		 * @param investigation The <code>Investigation/LabTestPanel</code> to be displayed/edited
		 */
		showInvestigationEditPanel : function(investigation) {
			jsslab.catalogPage.selectedInvestigation = investigation;
			
			jQuery('#editInvestigationName'      ).val(investigation == null || investigation.testPanelConcept == null	? "" : investigation.testPanelConcept.display);
			jQuery('#editInvestigationNameVal'   ).val(investigation == null || investigation.testPanelConcept == null	? "" : investigation.testPanelConcept.uuid);
			jQuery('#editInvestigationSection'   ).val(investigation == null || investigation.testPanelConcept == null	? "" : investigation.testGroupConcept.display);
			jQuery('#editInvestigationSectionVal').val(investigation == null || investigation.testPanelConcept == null	? "" : investigation.testGroupConcept.uuid);
			jQuery('#editInvestigationCode'      ).val(investigation == null || investigation.testPanelCode == null 	? "" : investigation.testPanelCode);
			jQuery('#editInvestigationCost'      ).val(investigation == null || investigation.cost == null 				? "" : investigation.cost.value);
			jQuery('#editInvestigationTurnaround').val(investigation == null || investigation.turnaround == null 		? "" : investigation.turnaround);
			jQuery('#editInvestigationHoldTime'  ).val(investigation == null || investigation.holdTime == null 			? "" : investigation.holdTime);
			
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
						tableRow.append( jQuery("<td />").html( specimenTemplate.analysisSpecimenTypeConcept == null ? "" : specimenTemplate.analysisSpecimenTypeConcept.display ) );
						tableRow.append( jQuery("<td />").html( specimenTemplate.testRoleConcept == null ? "" : specimenTemplate.testRoleConcept.display ) );
						jQuery('#specimenSelectionTable tbody').append( tableRow );
					}
				}
				
			}
			
			jQuery('#investigationEditPanel').fadeIn();
		},
		
		// ======================
		// lab investigation precondition methods
		// ======================
		
		/**
		 * Displays the precondition edit panel showing the precondition that has the given UUID
		 * 
		 * @param uuid The UUID belonging to the <code>LabPrecondition</code> to be edited
		 */
		showPreconditionEditPanel : function(uuid) {
			if (jsslab.isValidUuid(uuid)) {
				var url = openmrsContextPath + "/ws/rest/v1/jsslab/labprecondition/" + uuid + "?v=full";
				jQuery.ajax({
					"url" : url,
					"success" : function(precondition) {
						jsslab.catalogPage.updatePreconditionPanel(precondition);
					}
				});
			} else {
				jsslab.catalogPage.updatePreconditionPanel(null);
			}
		},
		
		/**
		 * Updates the precondition edit panel with the given <code>LabPrecondition</code>
		 * 
		 * @param precondition The <code>LabPrecondition</code> to be displayed in the edit panel
		 */
		updatePreconditionPanel : function(precondition) {
			jQuery('#tablePreconditionAnswers tbody tr').remove();
			
			if (precondition == null || !jsslab.isValidUuid(precondition.uuid)) {
				jQuery('#editPreconditionQuestion').removeAttr('disabled');
			} else {
				jQuery('#editPreconditionQuestion').attr('disabled', 'disabled');
			}
			jQuery('#editPreconditionQuestion').val( precondition != null ? precondition.preconditionQuestionConcept.display : "" );
			
			
			if (precondition != null && precondition.preconditionQuestionConcept != null) {
				
				var correctAnswerUuid = precondition.preconditionAnswerConcept.uuid;
				
				for (var i = 0; i < precondition.preconditionQuestionConcept.answers.length; i++) {
					var answer = precondition.preconditionQuestionConcept.answers[i];
					
					jsslab.catalogPage.addPreconditionAnswer(answer, correctAnswerUuid);
				}
			}
			
			jQuery('#editPreconditionDialog').dialog({
				title: jsslab.i18n.catalog['investigations.editing.preconditions.dialog.title'],
				width: 400,
				minWidth: 400,
				modal: true,
			});
		},
		
		/**
		 * Adds a new answer <code>Concept</code> to the available answers of the <code>LabPrecondition</code> currently displayed in the precondition edit panel
		 * 
		 * If the UUID of the answer matches the correctAnswerUuid, the radiobutton will be checked.
		 * 
		 * @param answer The answer <code>Concept</code> to be appended to the list
		 * @param correctAnswerUuid The UUID of the correct answer to the precondition question 
		 */
		addPreconditionAnswer : function(answer, correctAnswerUuid) {
			var tr = jQuery('<tr class="preconditionAnswerRow"/>');
			
			// answer radio button (true/false)
			var answerRadioBtn = jQuery('<input type="radio" name="preconditionAnswer" value="' + (answer == null ? jsslab.catalogPage.newAnswerIndex-- : answer.uuid) + '" />');
			if (answer != null && jsslab.isValidUuid(answer.uuid) && answer.uuid == correctAnswerUuid) { 
				answerRadioBtn.attr('checked', 'checked'); 
			}
			tr.append( jQuery('<td />').append(answerRadioBtn) );
	
			// answer name/description
			tr.append( jQuery('<td />').append( 
					jQuery('<input type="text" />').val(answer == null ? "" : answer.display ) 
			));
			
			// answer remove
			var answerRemoveAnchor = jQuery('<a />').html( jsslab.i18n.catalog['investigations.editing.preconditions.dialog.remove'] ).click(function (event) {
				jQuery(this).closest('tr').remove();
			});
			tr.append( jQuery('<td />').append( answerRemoveAnchor ) );
			
			jQuery('#tablePreconditionAnswers tbody').append( tr );
		},
		
		getInvestigationFromForm : function(callback) {
			var investigationConceptName = jQuery('#editInvestigationName').val();
			var investigationConceptUuid = jQuery('#editInvestigationNameVal').val();
			var investigationSectionConceptName = jQuery('#editInvestigationCode').val();
			var investigationSectionConceptUuid = jQuery('#editInvestigationSectionVal').val();
			
			jsslab.catalogPage.createConcept(investigationConceptUuid, investigationConceptName, "EN", "FULLY_SPECIFIED", function(investigationConcept) {
			jsslab.catalogPage.createConcept(investigationSectionConceptUuid, investigationSectionConceptName, "EN", "FULLY_SPECIFIED", function(investigationSectionConcept) {
			
				var uuid = jsslab.catalogPage.selectedInvestigation.uuid;
				var investigation = {
					"testPanelConcept" : investigationConcept.uuid,
					"testGroupConcept" : investigationSectionConcept.uuid,
					"testPanelCode" : jQuery('#editInvestigationCode').val(),
					"cost" : jQuery('#editInvestigationCost').val(),
					"turnaround" : jQuery('#editInvestigationTurnaround').val(),
					"holdTime" : jQuery('#editInvestigationHoldTime').val(),
					"labLocation" : jQuery('#selectLaboratory').val(),
				}
				if (jsslab.isValidUuid(uuid)) {
					investigation.uuid = uuid;
				}
				
				callback(investigation);
				
			});
			});
			
		},
		
		createConcept : function(uuid, name, locale, type, callback) {
			var concept = {
					"datatype" : "8d4a4c94-c2cc-11de-8d13-0010c6dffd0f",
					"conceptClass" : "8d492774-c2cc-11de-8d13-0010c6dffd0f",
					"names" : [{ 
						"name" : name,
						"locale" : locale,
						"type" : type
					}]
			};
			
			if (jsslab.isValidUuid(uuid)) {
				concept.uuid = uuid;
				callback(concept);
			} else {
				jsslab.dao.saveConcept(concept, callback);
			}
			
		},
		
		/**
		 * Retrieves the values from the precondition edit panel and returns the updated <code>LabPrecondition</code> object
		 * 
		 * @return The <code>LabPrecondition</code> currently being edited, updated with the values from the edit panel  
		 */
		getPreconditionFromForm : function() {
			var questionConcept = jsslab.catalogPage.createConceptFromValues(
					jQuery('#editPreconditionQuestion').val(),
					jQuery('#editPreconditionQuestionVal').val()
			);
			
			var answers = [];
			var answerRows = jQuery('.preconditionAnswerRow');
			for (var i = 0; i < answerRows.length; i++) {
				answers[i] = jsslab.catalogPage.createConceptFromValues(/*TODO*/);
			}
			
			var precondition = {
					"preconditionQuestionConcept" : questionConcept,
					"answers" : answers
			};
			return precondition;
		},
		
		
		/**
		 * Removes the currently selected <code>Investigation/LabTestPanel</code> from the currently selected Lab and retires it.
		 */
		deleteSelectedInvestigation : function() {
			var selectedtableRow = jQuery('#investigationTable tbody tr.selected');
			var tableRowId = selectedtableRow.attr('id')
			var investigationUuid = tableRowId.substring(tableRowId.lastIndexOf("_")+1);
			var reason = "Deleted from UI";
			
			if (jsslab.isValidUuid(investigationUuid)) {
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
		// lab test methods
		// ======================
		
		/**
		 * Updates the list of selectable tests with the tests of the given investigation and displays its investigation and 
		 * section names in the title
		 * 
		 * Should be called when the selected investigation is changed, to always display the tests of the currently 
		 * selected investigation.
		 * 
		 * @param investigation The <code>Investigation/LabTestPanel</code> which tests are to be displayed in the list of tests adsfadfasdfadfv fdsfv
		 */
		updateTestList : function(investigation) {
			jQuery('#testList option').remove();

			//TOOD i18n / message code
			var title = "No Tests available for the current Investigation";
			var tests = null;
			
			if (investigation != null) {
				var title = jsslab.i18n.catalog['tests.selection.heading']
				.replace('{0}', investigation.testPanelConcept.display)
				.replace('{1}', investigation.testGroupConcept.display);
				tests = investigation.tests;

				jQuery('#testHeading span').html(title);
			}
			
			jsslab.catalogPage.tests = tests;
			if (tests != null && tests.length > 0) {
				for (var i = 0; i < tests.length; i++) {
					tests[i].sortWeight = i;
					var listOption = jQuery('<option value="' + tests[i].uuid + '" >' + tests[i].display + '</option>');
					jQuery('#testList').append(listOption);
				}
				jQuery('#testList').val(tests[0].uuid);
				jQuery('#testList').change();
			} else {
				jQuery('#testEditPanel').fadeOut();
			}
		},
		
		/**
		 * Displays the test edit panel with the values of the given <code>LabTest</code>
		 * 
		 * @param test The <code>LabTest</code> to be edited
		 */
		showTestEditPanel : function(test) {
			
			if (test != null) {
				jQuery('#editTestName').val(test.testConcept.display);
				jQuery('#editTestNameVal').val(test.testConcept.uuid);
				
				if (test.resultFormat) {
					jQuery('.radioTestResultFormat').filter(function () {
						return jQuery(this).val() == test.resultFormat;
					})[0].click();
				} else {
					jQuery('.radioTestResultFormat').removeAttr('selected');
				}
			}
			jQuery('#testEditPanel').fadeIn();
		},
		
		getSelectedTest : function() {
			return jsslab.catalogPage.selectedTest;
		},

		showResultFormatPanel : function(format, test) {
			jQuery('.testResultFormatPanel').fadeOut();
			
			jQuery('#testResultFormatHeading').html(jsslab.i18n.catalog['tests.editing.resultFormat'][format]);
			
			if (format == "CONCEPT") {
				
				jQuery('#testResultFormat_CONCEPT').fadeIn();
				
			} else if (format == "NUMERIC") {
				
				jsslab.dao.getConcept(test.testConcept.uuid, "full", function(testConcept) {
					test.testConcept = testConcept;
					
					if (test.testConcept.precise) {
						jQuery('#checkboxNumericResultShowDecimals').attr('checked', 'checked');
					} else {
						jQuery('#checkboxNumericResultShowDecimals').removeAttr('checked');
					}
					jQuery('#selectNumericResultUnit').val(test.testConcept.units);
				});
				
				if (test.testRanges.length == 0) {
					jQuery('#testRangeSelectionTable tbody tr').empty();
					jQuery('#testRangeSelectionTable tbody').append(jQuery('<tr><td>' + jsslab.i18n.catalog['tests.editing.resultNumeric.noTestRanges'] + '</td></tr>'));
				}
				
				jQuery('#testRangeSelectionTable tbody tr').remove();
				jQuery('#btnEditTestRange').attr('disabled', 'disabled');
				jQuery('#btnRemoveTestRange').attr('disabled', 'disabled');
				for (var i = 0; i < test.testRanges.length; i++) {
					jsslab.catalogPage.addTestRangeTableRow(test.testRanges[i]);
				}
				
				jQuery('#testResultFormat_NUMERIC').fadeIn();
				
			} else if (format == "DURATION") {
			} else if (format == "TITER") {
			} else if (format == "TEXT") {
				
			}
		},
		
		addTestRangeTableRow : function(testRange) {
			var tableRow;
			
			if (testRange != null) {
				var logicRuleId = testRange.logicRule == null ? '' : testRange.logicRule.uuid;
				var logicRuleDisplay = testRange.logicRule == null ? '' : testRange.logicRule.display;
				
				tableRow = jQuery('<tr id="testrange_' + testRange.uuid +'" />');
				tableRow.append( jQuery('<td />').html( testRange.rangeSex == null ? '' : testRange.rangeSex) );
				tableRow.append( jQuery('<td />').html( testRange.rangeAgeMin == null ? '' : testRange.rangeAgeMin ) );
				tableRow.append( jQuery('<td />').html( testRange.rangeAgeMax == null ? '' : testRange.rangeAgeMax ) );
				tableRow.append( jQuery('<td id="rule_' + logicRuleId + '" />').html( logicRuleDisplay ) );
			} else {
				tableRow = jQuery('<tr id="testrange_' + jsslab.catalogPage.newTestRangeIndex-- +'" />');
				for (var i = 0; i < 4; i++) {
					tableRow.append( jQuery('<td> </td>') );
				}
			}

			tableRow.click(function (event) {
				jsslab.catalogPage.handleTestRangeSelection(jQuery(this));
			});
			
			jQuery('#testRangeSelectionTable tbody').append(tableRow);
		},
		
		/**
		 * Handles what should happen when a <code>LabTestRange</code> is selected from the list
		 * 
		 * @param tableRow The table row that was selected
		 */
		handleTestRangeSelection : function(tableRow) {
			var currentTr = jQuery('#testRangeSelectionTable tbody tr.selected');
	
			if (currentTr != null && currentTr.length > 0) {
				currentTr.removeClass('selected');
				var currentTrUuid = currentTr.first().attr('id').substring(currentTr.first().attr('id').lastIndexOf('_')+1);
				
				if (!jsslab.isValidUuid(currentTrUuid)) {
					currentTr.remove();
				}
			}
			tableRow.addClass('selected');
			
			var idx = tableRow.index();
			var testRanges = jsslab.catalogPage.selectedTest.testRanges;
			
			if (idx < jsslab.catalogPage.selectedTest.testRanges.length) {
				jsslab.catalogPage.selectedTestRange = testRanges[idx];
			} else {
				jsslab.catalogPage.selectedTestRange = null;
			}
			jQuery('#btnEditTestRange').removeAttr('disabled');
			jQuery('#btnRemoveTestRange').removeAttr('disabled');
		},
		
		/**
		 * Displays the test range edit panel showing the test range that has the given UUID
		 * 
		 * @param uuid The UUID belonging to the <code>LabTestRange</code> to be edited
		 */
		showTestRangeEditPanel : function(uuid) {
			if (jsslab.isValidUuid(uuid)) {
				var url = openmrsContextPath + "/ws/rest/v1/jsslab/labtestrange/" + uuid + "?v=full";
				jQuery.ajax({
					"url" : url,
					"success" : function(labTestRange) {
						jsslab.catalogPage.updateTestRangeEditPanel(labTestRange);
					}
				});
			} else {
				jsslab.catalogPage.updateTestRangeEditPanel(null);
			}
		},
		
		/**
		 * Updates the test range edit panel with the given <code>LabTestRange</code>
		 * 
		 * @param labTestRange The <code>LabTestRange</code> to be displayed in the edit panel
		 */
		updateTestRangeEditPanel : function(labTestRange) {
			
			jQuery('#textTestRangeSex').val(			labTestRange == null || labTestRange.rangeSex == null ? "" : labTestRange.rangeSex);
			jQuery('#textTestRangeMinAge').val(			labTestRange == null || labTestRange.rangeAgeMin == null ? "" : labTestRange.rangeAgeMin);
			jQuery('#textTestRangeMaxAge').val(			labTestRange == null || labTestRange.rangeAgeMax == null ? "" : labTestRange.rangeAgeMax);
			jQuery('#textTestRangeNormalLow').val(		labTestRange == null || labTestRange.rangeNormalLow == null ? "" : labTestRange.rangeNormalLow);
			jQuery('#textTestRangeNormalHigh').val(		labTestRange == null || labTestRange.rangeNormalHigh == null ? "" : labTestRange.rangeNormalHigh);
			jQuery('#textTestRangeCriticalLow').val(	labTestRange == null || labTestRange.rangeCriticalLow == null ? "" : labTestRange.rangeCriticalLow);
			jQuery('#textTestRangeCriticalHigh').val(	labTestRange == null || labTestRange.rangeCriticalHigh == null ? "" : labTestRange.rangeCriticalHigh);
			jQuery('#selectTestRangeLogicRule').val(	labTestRange == null || labTestRange.logicRule == null ? "" : labTestRange.logicRule.uuid);
			
			jQuery('#editTestRangeDialog').dialog({
				title: jsslab.i18n.catalog['tests.editing.resultNumeric.testRanges.dialog.title'],
				width: 560,
				minWidth: 560,
				modal: true,
			});
		},
	
		// ======================
		// util methods
		// ======================
		
		createConceptFromValues : function(name, uuid) {
			var concept = {
					names : [
						jsslab.isValidUuid(uuid) ? uuid : {
							"name" : name,
							"conceptNameType" : "FULY_SPECIFIED",
							//TODO replace with user locale
							"locale" : "en_GB",
						}
					]
			}
		},
		
		/**
		 * Saves a <code>LabPrecondition</code>
		 * 
		 * @param precondition The <code>LabPrecondition</code> to be saved
		 * @param callback A function to be called after the XHR to save the precondition returns
		 */
		savePrecondition : function(precondition, callback) {
			var url = openmrsContextPath + "/ws/rest/v1/jsslab/labprecondition";
			if (precondition.uuid != null) {
				url += "/" + uuid;
				precondition.uuid = undefined;
			}
			jQuery.ajax({
				"url" : baseUrl,
				"type" : "POST",
				"contentType" : "application/json",
				"data" : JSON.stringify(precondition),
				"success" : function(savedPrecondition) {
					callback(precondition);
				}
			});
			return savedPrecondition;
		},
		
		/**
		 * Sets internal status variables to indicate whether an <code>Investigation</code> is currently being edited and whether it is
		 * newly created.
		 * 
		 * Depending on the status that is set the behaviour on the page is slightly modified (e.g. an unsaved newly created
		 * <code>Investigation</code> is removed when it is unselected).
		 * 
		 * @param editing Whether an <code>Investigation</code> is currently selected/being edited
		 * @param isNew Whether the selected <code>Investigation</code> is newly created (i.e. has no persistent 
		 * representation)
		 */
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
		
		moveOption : function(opt, direction) {
			if (opt.length) {
				if (direction == 'up') {
					opt.first().prev().before(opt); 
				} else {
					opt.last().next().after(opt);
				}
		    }
		},
		
		swapTests : function(tests, idx1, idx2) {
			var test1 = tests[idx1];
			var test2 = tests[idx2];
			var tempSortWeight = test1.sortWeight;
			test1.sortWeight = test2.sortWeight;
			test2.sortWeight = tempSortWeight;
			tests[idx1] = test2;
			tests[idx2] = test1;
		}
	};

	// ======================
	// lab investigation general
	// ======================
	
	jQuery('#selectLaboratory').change(function(event) {
		var locationUuid = jQuery('#selectLaboratory option:selected').val();
		
		jQuery.ajax({
			"url" : openmrsContextPath + "/ws/rest/v1/jsslab?getLabTestPanelsByLocation",
			"type" : "GET",
			"data" : "locationUuid=" + locationUuid + "&includeAll=true",
			"success" : function(data) {
				var investigations = data.results;
				jsslab.catalogPage.updateInvestigationTable(investigations);
				if (investigations == null || investigations.length == 0) {
					jsslab.catalogPage.updateTestList(null);
				}
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
		jsslab.catalogPage.getInvestigationFromForm(function(investigation) {
		jsslab.dao.saveInvestigation(investigation, function(uuid) {
			// will cause an update of the investigation table to refresh the contents 
			jQuery('#investigationEditPanel').fadeOut();
			jQuery('#selectLaboratory').change();
		});
		});
		
		jsslab.catalogPage.setEditingInvestigation(false, false);
	});
	
	jQuery('#buttonCancelInvestigation').click(function (event) {
		event.preventDefault();
		
		if (isNewInvestigation) {
			jsslab.catalogPage.deleteSelectedInvestigation();
		}
		jsslab.catalogPage.setEditingInvestigation(false, false);
		
		jQuery('#investigationEditPanel').fadeOut();
		jQuery('#btnRemoveInvestigation').attr('disabled', 'disabled');
	});
	
	// ======================
	// investigation preconditions
	// ======================
	
	jQuery('#preconditionList').change(function(event) {
		var uuid = jQuery('#preconditionList').val();
		
		if (uuid != null) {
			if (!jsslab.isValidUuid(uuid)) {
				jQuery('#preconditionList option').filter(function () {
					jQuery(this).val() == jsslab.catalogPage.selectedPreconditionUuid
				}).remove();
			}
			selectedPreconditionUuid = uuid;
			jQuery('#buttonEditPrecondition').removeAttr('disabled');
			jQuery('#buttonRemovePrecondition').removeAttr('disabled');
		}
	});
	
	jQuery('#buttonAddPrecondition').click(function (event) {
		event.preventDefault();
		jsslab.catalogPage.showPreconditionEditPanel(null);
	});
	jQuery('#buttonEditPrecondition').click(function (event) {
		event.preventDefault();
		var uuid = jQuery('#preconditionList').val();
		jsslab.catalogPage.showPreconditionEditPanel(uuid);
	});
	jQuery('#buttonRemovePrecondition').click(function (event) {
		event.preventDefault();
		jQuery('#preconditionList option:selected').remove();
		
		jQuery('#buttonEditPrecondition').attr('disabled' , 'disabled');
		jQuery('#buttonRemovePrecondition').attr('disabled' , 'disabled');
	});

	
	jQuery('#buttonAddPreconditionAnswer').click(function (event) {
		event.preventDefault();
		jsslab.catalogPage.addPreconditionAnswer(null, null);
	});
	jQuery('#buttonSavePrecondition').click(function (event) {
		event.preventDefault();

		jsslab.catalogPage.savePrecondition(getPreconditionFromForm(), function(precondition) {
			jQuery('#preconditionList').append( jQuery('<option value="' + precondition.uuid + '">' + precondition.display + '</option>') );
		});
		jQuery('#editPreconditionDialog').dialog('close');
	});
	jQuery('#buttonCancelPrecondition').click(function (event) {
		event.preventDefault();
		
		jQuery('#editPreconditionDialog').dialog('close');
	});
	
	// ======================
	// lab tests general
	// ======================
	
	jQuery('#testList').change(function(event) {
		var testUuid = jQuery('#testList option:selected').val();
		
		jQuery.ajax({
			"url" : openmrsContextPath + "/ws/rest/v1/jsslab/labtest/" + testUuid + "?v=full",
			"type" : "GET",
			"success" : function(test) {
				jsslab.catalogPage.selectedTest = test;
				jsslab.catalogPage.showTestEditPanel(test);
			}
		});
	});
	
	jQuery('.radioTestResultFormat').click(function (event) {
		jsslab.catalogPage.showResultFormatPanel(
				jQuery(this).val(), 
				jsslab.catalogPage.getSelectedTest()
		);
	});
	
	jQuery('#btnAddTest').click(function (event) {
		event.preventDefault();
	});
	jQuery('#btnRemoveTest').click(function (event) {
		event.preventDefault();
	});
	jQuery('#btnMoveUpTest').click(function (event) {
		event.preventDefault();

		var idx = jQuery('#testList').attr("selectedIndex");
		if (idx > 0) {
			jsslab.catalogPage.swapTests(jsslab.catalogPage.tests, idx, idx-1);
			jsslab.catalogPage.moveOption( jQuery('#testList option:selected'), "up" );
		}
	});
	jQuery('#btnMoveDownTest').click(function (event) {
		event.preventDefault();
		
		var idx = jQuery('#testList').attr("selectedIndex");
		if (idx < jsslab.catalogPage.tests.length-1) {
			jsslab.catalogPage.swapTests(jsslab.catalogPage.tests, idx, idx+1);
			jsslab.catalogPage.moveOption( jQuery('#testList option:selected'), "down" );
		}
	});
	
	jQuery('#btnSaveTest').click(function (event) {
		event.preventDefault();
		//TODO check whether there are any validation errors
		//TODO save test
		jQuery('#testEditPanel').fadeOut();
	});
	
	jQuery('#btnCancelTest').click(function (event) {
		event.preventDefault();
		jQuery('#testEditPanel').fadeOut();
	});
	
	jQuery('#buttonCancelTest').click(function (event) {
		event.preventDefault();
		jQuery('#testEditPanel').fadeOut();
	});
	
	
	// ======================
	// lab tests test ranges
	// ======================
	
	jQuery('#btnAddTestRange').click(function (event) {
		event.preventDefault();
		jsslab.catalogPage.showTestRangeEditPanel(null);
	});
	jQuery('#btnEditTestRange').click(function (event) {
		event.preventDefault();
		jsslab.catalogPage.showTestRangeEditPanel(jsslab.catalogPage.selectedTestRange.uuid);
	});
	jQuery('#btnRemoveTestRange').click(function (event) {
		event.preventDefault();
		
		jQuery('#testRangeSelectionTable tbody tr.selected').remove();
		
		jQuery('#btnEditTestRange').attr('disabled', 'disabled');
		jQuery('#btnRemoveTestRange').attr('disabled', 'disabled');
		
		jQuery('#testEditPanel').fadeOut();
	});
	jQuery('#btnSaveTestRange').click(function (event) {
		event.preventDefault();
		jQuery('#editTestRangeDialog').dialog('close');
	});
	jQuery('#btnCancelTestRange').click(function (event) {
		event.preventDefault();
		jQuery('#editTestRangeDialog').dialog('close');
	});
	
	
	// ======================
	// post button binding calls
	// ======================
	
	jsslab.catalogPage.initAutoCompletes();
	jQuery('#selectLaboratory').change();
});

