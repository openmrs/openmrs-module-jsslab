editing = true;

jQuery(document).ready(function() {
	jsslab.setupPage = {
		
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
		
	};
});

jQuery(document).ready(function() {
	
	jQuery('#installSampleData').click(function(event) {
		event.preventDefault();
		jQuery.ajax({
			"url" : "setup/installSampleData.htm",
			"type" : "GET",
			"success" : function(result) {
				jsslab.setupPage.setSaveResult(jQuery('#installationResult'), result);
				jQuery('#installSampleData').attr('disabled', 'disabled');
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
	
});

