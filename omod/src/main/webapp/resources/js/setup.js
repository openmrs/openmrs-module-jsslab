editing = true;

jQuery(document).ready(function() {
	jsslab.setupPage = {
		
		autocomplete : {
			orderTypes : [],
			conceptSets : [],
		},
		
		initAutoCompletes : function() {
			var url = openmrsContextPath + "/ws/rest/v1/jsslab?getAllOrderTypes"
			jQuery.ajax({
				"url" : url,
				"success" : function(data) {
					var orderTypes = data.results;
					for (var i = 0; i < orderTypes.length; i++) {
						jsslab.setupPage.autocomplete.orderTypes[i] = {
								label: orderTypes[i].display,
								uuid: orderTypes[i].uuid
						}
					}
					jQuery('#globalPropertySelect_orderType').autocomplete({
						source : jsslab.setupPage.autocomplete.orderTypes,
						select : function(event, ui) { 
							jQuery('#globalPropertyUuid_orderType').val(ui.item.uuid);
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
						jsslab.setupPage.autocomplete.conceptSets[i] = {
								label: concepts[i].display,
								uuid: concepts[i].uuid
						}
					}
					jQuery('#globalPropertySelect_conceptSets').autocomplete({
						source : jsslab.setupPage.autocomplete.conceptSets,
						select : function(event, ui) { 
							jQuery('globalPropertyUuid_conceptSets').val(ui.item.uuid);
						}
					});
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
				jQuery('#installSampleData').removeAttr('disabled');
			}
			element.text(result.message);
			element.fadeIn();
			setTimeout( function(){ element.fadeOut(); }, 2000 );
		},
		
	};
	
	jQuery('.globalPropertySubmit').click(function(event) {
		event.preventDefault();
		var btnId = jQuery(this).attr('id');
		var gpId = btnId.substring(btnId.lastIndexOf("_")+1);
		
		var gpName  = jQuery('#globalPropertyName_'+gpId).val();
		var gpValue = jQuery('#globalPropertyUuid_'+gpId).val();
		
		jsslab.saveGlobalProperty( gpName, gpValue, jQuery('#globalPropertyResult_'+gpId), jsslab.setupPage.setSaveResult );
	});
	
	jQuery('#installSampleData').click(function(event) {
		event.preventDefault();
		jQuery('#installSampleData').attr('disabled', 'disabled');
		jQuery.ajax({
			"url" : "setup/installSampleData.htm",
			"type" : "GET",
			"success" : function(result) {
				jsslab.setupPage.setSaveResult(jQuery('#installationResult'), result);
			},
		});
	});
	
	jQuery('#finishSetup').click(function(event) {
		event.preventDefault();
		jQuery.ajax({
			"url" : "setup/finishSetup.htm",
			"type" : "GET",
			"success" : function(view) {
				window.location.href = view;
			}
		});
	});
	
	jsslab.setupPage.initAutoCompletes();
});

