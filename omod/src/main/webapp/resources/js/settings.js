// define startsWith String function if not exists
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}

settingsPage = {
	editing : false,
	locations : null,
	supplyItemTypes : null,
	radioBtnId : -1,
	
	isValidUuid : function(uuid) {
		return uuid.length == 36;
	}
};

jQuery(document).ready(function() {
	
	
	
});