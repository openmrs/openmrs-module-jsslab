// define startsWith String function if not exists
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}

var jsslab = {
	"i18n" : {},
	"constants" : {
		"labTagName" : "Lab",
		"conceptSets" : {
			"conditions" : {
				"ASSET CONDITION" : "b816ddc6-4873-11e1-b5ed-0024e8c61285",
			}
		},
		"conceptClasses" : {
			"misc" : "8d492774-c2cc-11de-8d13-0010c6dffd0f"
		},
		"datatypes" : {
			"na" : "8d4a4c94-c2cc-11de-8d13-0010c6dffd0f"
		}
	},
	
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
				jsslab.setupPage.setSaveResult(resultElement, result);
			}
		});
	},
	
};