editing = true;

jsslab.templatesPage = {
		
	investigations : null,
	selectedInvestigationIndex : -1,
	
	updateInvestigationTable : function(investigations) {
		jsslab.templatesPage.investigations = investigations;
		
		jQuery('#investigationTable tbody tr').remove();
		
		if (investigations.length > 0) {
			for (var i = 0; i < investigations.length; i++) {
				var tableRow = jQuery('<tr id="investigation_' + investigations[i].uuid + '"/>');
				tableRow.append( jQuery("<td />").html( investigations[i].testGroupConcept.display ) );
				tableRow.append( jQuery("<td />").html( investigations[i].testPanelConcept.display ) );
				tableRow.click(function(event) {
					jQuery('#investigationTable tbody tr').removeClass('selected');
					jQuery(this).addClass('selected');
					
					jsslab.templatesPage.selectedInvestigationIndex = jQuery(this).index();
					
					var investigation = jsslab.templatesPage.investigations[jsslab.templatesPage.selectedInvestigationIndex];
					
					jsslab.templatesPage.showTemplatesEditPanel(investigation);
					
				});
				
				jQuery('#investigationTable tbody').append(tableRow);
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
	
	showTemplatesEditPanel : function(investigation) {
		var specimenTemplates = investigation.specimenTemplates;
		
		jQuery('#specimenEditPanel span').remove();
		
		for (var i = 0; i < specimenTemplates.length; i++) {
			var template = jQuery('<span />').html(specimenTemplates[i].display);
			jQuery('#specimenEditPanel').append(template);
		}
		
		jQuery('#specimenEditPanel').fadeIn();
	}
	
};

jQuery(document).ready(function() {
	
	jQuery.ajax({
		"url" : openmrsContextPath + "/ws/rest/v1/jsslab/labtestpanel?v=full",
		"type" : "GET",
		"success" : function(data) {
			var investigations = data.results;
			//TODO only retrieve investigations for the currently selected lab
			jsslab.templatesPage.updateInvestigationTable(investigations);
		}
	});
	
});

