editing = true;
	
jQuery(document).ready(function() {
	jQuery("#liquibaseSampleData").click(function(event) {
		alert('will now add jsslab sample data to openmrs tables');
		jQuery.get("/module/jsslab/admin/setup/installSampleData.htm");
		event.preventDefault();
	});
});

