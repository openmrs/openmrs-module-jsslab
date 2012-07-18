// define startsWith String function if not exists
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}

jsslab.settingsPage = {
	editing : false,
	locations : null,
	supplyItemTypes : null,
	radioBtnId : -1,
	
	concepts : null,

	
	saveGlobalProperty : function(property, value, resultElement) {
		jQuery.ajax({
			url : "settings/saveGlobalProperties.form",
			type : "POST",
			contentType : "application/x-www-form-urlencoded",
			data: property + "=" + value,
			success : function(result) {
				jsslab.settingsPage.setSaveResult(resultElement, result)
			}
		});
	},
	
	setSaveResult : function(element, result) {
		element.text(result);
		element.fadeIn();
		setTimeout( function(){ element.fadeOut(); }, 2000 );
	},
	
	isValidUuid : function(uuid) {
		return uuid.length == 36;
	},
	
	getSelectedRadioButtonID : function() {
		var btnId = jQuery('.radioSpecimenTypeCode:checked').attr('id');
		return btnId.substring(btnId.lastIndexOf("_")+1);
	},
	
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
	
	updateCodeTable : function(concepts) {
		jsslab.settingsPage.concepts = concepts;
		
		jQuery('#codeTable tbody tr').remove();
		
		if (concepts.length > 0) {
			for (var i = 0; i < concepts.length; i++) {
				tableRow = jQuery('<tr />');
				tableRow.append( jQuery("<td />").html( concepts[i].display ) );
				tableRow.append( jQuery("<td />").html( jsslab.settingsPage.getConceptShortName(concepts[i]) ) );
				tableRow.append( jQuery("<td />").html( concepts[i].retired ? "true" : "false" ) );
				tableRow.click(function() {
					jsslab.settingsPage.showCodeEditPanel(jsslab.settingsPage.concepts[jQuery(this).index()]);
				});
				
				jQuery('#codeTable tbody').append(tableRow);
			}
		} else {
			var tableRow = jQuery('<tr />');
			jQuery('#codeTable tbody').append(tableRow);
			
			tableRow = jQuery('<tr />');
			tableRow.append( jQuery('<td colspan="3" />').html( jsslab.i18n['settings.codelists.codetable.empty'] ) );
			jQuery('#codeTable tbody').append(tableRow);
		}

	},
	
	showCodeEditPanel : function(concept) {
		jQuery('#editTextConcept').val( concept.display );
		jQuery('#editCode').val( jsslab.settingsPage.getConceptShortName(concept) );
		jQuery('#codeEditPanel').fadeIn();
	},
};

jQuery(document).ready(function() {
	jQuery('#orderTypeSubmit').click(function(event) {
		event.preventDefault();
		jsslab.settingsPage.saveGlobalProperty( jQuery('#orderTypeSelect').attr('name'), jQuery('#orderTypeSelect').val(), jQuery('#orderTypeResult'));
	});
	jQuery('#orderIdPatternSubmit').click(function(event) {
		event.preventDefault();
		jsslab.settingsPage.saveGlobalProperty( jQuery('#orderIdPatternSelect').attr('name'), jQuery('#orderIdPatternSelect').val(), jQuery('#orderIdPatternResult') );
	});
	jQuery('#specimenIdPatternSubmit').click(function(event) {
		event.preventDefault();
		jsslab.settingsPage.saveGlobalProperty( jQuery('#specimenIdPatternSelect').attr('name'), jQuery('#specimenIdPatternSelect').val(), jQuery('#specimenIdPatternResult') );
	});
	jQuery('#reportIdPatternSubmit').click(function(event) {
		event.preventDefault();
		jsslab.settingsPage.saveGlobalProperty( jQuery('#reportIdPatternSelect').attr('name'), jQuery('#reportIdPatternSelect').val(), jQuery('#reportIdPatternResult') );
	});
	
	jQuery('.radioSpecimenTypeCode').click(function(event) {
		var uuid = jsslab.settingsPage.getSelectedRadioButtonID();
		
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/concept/" + uuid + "?v=full",
			success : function(data) {
				var concepts = data.setMembers;
				jsslab.settingsPage.updateCodeTable(concepts);
			}
		});
	});
	
	jQuery('buttonAddNewCode').click(function(event) {
		event.preventDefault();
	});
	jQuery('buttonSaveCode').click(function(event) {
		event.preventDefault();
	});
	jQuery('buttonCancelEditingCode').click(function(event) {
		event.preventDefault();
	});
	
	
	jQuery('.radioSpecimenTypeCode:first').click();
});

