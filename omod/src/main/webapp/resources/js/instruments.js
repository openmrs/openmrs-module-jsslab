// define startsWith String function if not exists
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}

jsslab.instrumentsPage = {
	editing : false,
	instrumentLocations : null,
	instrumentConditions : null,
	supplyItemTypes : null,
	radioBtnId : -1,
	
	
	/**
	 * Checks whether the given UUID is valid, based on the length of the string.
	 * 
	 * @param uuid The string that is to be checked
	 * @returns {Boolean}
	 */
	isValidUuid : function(uuid) {
		return uuid.length >= 36;
	},

	
	findLocationByUuid : function(uuid) {
		for (var i = 0; i < jsslab.instrumentsPage.instrumentLocations.length; i++) {
			if (jsslab.instrumentsPage.instrumentLocations[i].uuid == uuid) return jsslab.instrumentsPage.instrumentLocations[i];
		}
		return null;
	},
	findConditionByUuid : function(uuid) {
		for (var i = 0; i < jsslab.instrumentsPage.instrumentConditions.length; i++) {
			if (jsslab.instrumentsPage.instrumentConditions[i].uuid == uuid) return jsslab.instrumentsPage.instrumentConditions[i];
		}
		return null;
	},
	
	deleteInstrument : function(uuid, reason) {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab/labinstrument/" + uuid + "?reason="+reason,
			type : "DELETE",
			success : function() {
				jsslab.instrumentsPage.reloadInstrumentsTable();
			}
		});
	},
	deleteSupplyItem : function(uuid, reason) {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab/labsupplyitem/" + uuid + "?reason="+reason,
			type : "DELETE",
			success : function() {
				updateSupplyItemsTable();
			}
		});
	},
	
	
	/**
	 * Adds a new row to the table with the given ID, using the values provided. 
	 * @param tableId the ID of the table to add a row to
	 * @param values the values to add into each column
	 */
	addNewTableRow : function(tableId, values) {
		
		tableRow = jQuery("<tr />");
		for (var i = 0; i < values.length; i++) {
			tableRow.append(
					jQuery("<td />").html(values[i])
				);
		}
		
		jQuery('#' + tableId + ' tr:last').after(tableRow);
		
	},
	
	
	/**
	 * Click handler for radio buttons
	 * Will invoke <code>editInstrumentInForm()</code> for the selected instrument 
	 */
	onRadioButtonClick : function(event) {
		if (jsslab.instrumentsPage.editing) {
//			var shouldContinue = confirm("You are currently editing another item. Are you sure you want to dismiss the changes and add a new item instead?");
//			if (!shouldContinue) {
//				event.preventDefault();
//				return;
//			}
		}
		
		var btnId = jQuery(this).attr('id');
		var itemUuid = btnId.substring(btnId.lastIndexOf("_")+1);
		jsslab.instrumentsPage.editing = true;

		if (btnId.startsWith("radioBtnSelectInstrument_")) {
			
			//ensure click is not on the new row and that the last row belongs to a new instrument (only then the button will be disabled)
			if (jsslab.instrumentsPage.isValidUuid(itemUuid) && jQuery('#buttonAddNewInstrument').attr('disabled') == true) {
				jQuery('#buttonAddNewInstrument').removeAttr('disabled');
				jQuery('#tableInstruments tr:last').remove();
			}
			
			jsslab.instrumentsPage.showInstrumentForm(itemUuid);
			
		} else {

			//ensure click is not on the new row and that the last row belongs to a new supply item (only then the button will be disabled)
			if (jsslab.instrumentsPage.isValidUuid(itemUuid) && jQuery('#buttonAddNewSupplyItem').attr('disabled') == true) {
				jQuery('#buttonAddNewSupplyItem').removeAttr('disabled');
				jQuery('#tableSupplyItems tr:last').remove();
			}
			
			showSupplyItemForm(itemUuid);
		}
		
	},
	
	
	/**
	 * Retrieves the list of all LabInstruments and displays them in the table.
	 * 
	 * This is used to update the table when an item in the table has been changed.  
	 */
	reloadInstrumentsTable : function() {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab/labinstrument?v=full&includeAll=true",
			success : function(data) {
					
				jQuery('#tableInstruments tr').filter(function() {
					return !jQuery(this).hasClass('header');	
				}).remove();
				
				for (var i = 0; i < data.results.length; i++) {
					var instrument = data.results[i];
					var radioBtn = jQuery("<input type='radio' class='radioBtnSelectInstrument' name='instrumentSelect' id='radioBtnSelectInstrument_" + instrument.uuid + "' />");
					radioBtn.click(jsslab.instrumentsPage.onRadioButtonClick);
					
					jsslab.instrumentsPage.addNewTableRow("tableInstruments", new Array( radioBtn, instrument.propertyTag, instrument.manufacturer, instrument.model, instrument.serialNumber, instrument.retired ? "true" : "false" ));
				}
			}
		});
	},
	
	
	/**
	 * Displays a form to edit the instrument associated with the given UUID
	 * 
	 * @param uuid The UUID of the instrument to be edited
	 */
	showInstrumentForm : function(uuid) {
		//check whether object already has an existing uuid or is newly created
		if (!jsslab.instrumentsPage.isValidUuid(uuid)) {
			jsslab.instrumentsPage.updateInstrumentForm({});
		} else {
			jQuery.ajax({
				"url" : openmrsContextPath + "/ws/rest/v1/jsslab/labinstrument/" + uuid + "?v=full",
				"success" : function(data) {
					var instrument = data;
					jsslab.instrumentsPage.updateInstrumentForm(instrument);
				}
			});
		}
	},

	/**
	 * Sets the instrument form elements to the values of the given instrument object
	 * 
	 * @param instrument An instrument object containing the values to set the form elements to
	 */
	updateInstrumentForm : function(instrument) {
		jQuery('#instrumentEditForm').fadeIn();

		jQuery('#instrumentPropertyTag').val(instrument.propertyTag != null ? instrument.propertyTag : "");
		jQuery('#instrumentManufacturer').val(instrument.manufacturer != null ? instrument.manufacturer : "");
		jQuery('#instrumentSerialNumber').val(instrument.serialNumber != null ? instrument.serialNumber : "");
		jQuery('#instrumentModel').val(instrument.model != null ? instrument.model : "");
		
		jQuery('#instrumentConditionDate').val(instrument.conditionDate != null ? jQuery.datepicker.formatDate( 'dd/mm/yy', new Date(instrument.conditionDate) ) : "");
		
		jQuery('#instrumentReceivedDate').val(instrument.receivedDate != null ? jQuery.datepicker.formatDate( 'dd/mm/yy', new Date(instrument.receivedDate) ) : "");
		jQuery('#instrumentReceivedFrom').val(instrument.receivedFrom != null ? instrument.receivedFrom : "");
		jQuery('#instrumentReceivedCost').val(instrument.receivedCost != null ? instrument.receivedCost.value : "0");
		jQuery('#instrumentReceivedValue').val(instrument.receivedValue != null ? instrument.receivedValue.value : "0");
		
		jQuery('#instrumentMaintenanceVendor').val(instrument.maintenanceVendor != null ? instrument.maintenanceVendor : "");
		jQuery('#instrumentMaintenancePhone').val(instrument.maintenancePhone != null ? instrument.maintenancePhone : "");
		
		// Set selector to the current value of the instrument's location
		jQuery('#instrumentLocation').val(-1);
		if (instrument != null && instrument.location != null) {
			jQuery('#instrumentLocation').val(instrument.location.uuid);
		}
		
		// Set selector to the current value of the instrument's location
		jQuery('#instrumentCondition').val(-1);
		if (instrument != null && instrument.conditionConcept != null) {
			jQuery('#instrumentCondition').val(instrument.conditionConcept.uuid);
		}
		
	},
	

	/**
	 * Reads the form data of the Instrument Form and writes it in one object that can be used for further processing.
	 * 
	 * @returns an Instrument object containing the form data
	 */
	getInstrumentFromForm : function() {
		var instrument = {
			propertyTag : jQuery('#instrumentPropertyTag').val(),
			manufacturer : jQuery('#instrumentManufacturer').val(),
			serialNumber : jQuery('#instrumentSerialNumber').val(),
			model : jQuery('#instrumentModel').val(),
			
			conditionDate : jQuery.datepicker.parseDate('dd/mm/yy', jQuery('#instrumentConditionDate').val() ),
			
			receivedDate : jQuery.datepicker.parseDate('dd/mm/yy', jQuery('#instrumentReceivedDate').val() ),
			receivedFrom : jQuery('#instrumentReceivedFrom').val(),
			receivedCost : jQuery('#instrumentReceivedCost').val(),
			receivedValue : jQuery('#instrumentReceivedValue').val(),
			
			maintenanceVendor : jQuery('#instrumentMaintenanceVendor').val(),
			maintenancePhone : jQuery('#instrumentMaintenancePhone').val(),
			maintenanceDescription : jQuery('#instrumentMaintenanceDescription').val(),
			
			location : jQuery('#instrumentLocation').val(),
			conditionConcept : jQuery('#instrumentCondition').val(),
		
		};
			
		return instrument;
		
	},

	
	/**
	 * 
	 * @param instrument
	 */
	saveInstrument : function(instrument, uuid) {
		var url = openmrsContextPath + "/ws/rest/v1/jsslab/labinstrument";
		if (jsslab.instrumentsPage.isValidUuid(uuid)) {
			url += "/" + uuid;
		}
		
		jQuery.ajax({
			"url" : url,
			"type" : "POST",
			"contentType" : "application/json",
			"data": JSON.stringify(instrument),
			"success" : function(data) {
				jsslab.instrumentsPage.reloadInstrumentsTable();
			}
		});
	},

	getSelectedInstrumentUuid : function() {
		var btnId = jQuery('.radioBtnSelectInstrument:checked').attr('id');
		return btnId.substring(btnId.lastIndexOf("_")+1);
	},
	
};



jQuery(document).ready(function() {
	
	// Setup controls for instruments
	
	jQuery("#buttonAddNewInstrument").click(function(event) {
		event.preventDefault();
		jQuery(this).attr('disabled', 'disabled');
		
		if (jsslab.instrumentsPage.editing) {
//			var shouldContinue = confirm("You are currently editing another item. Are you sure you want to dismiss the changes and add a new item instead?");
//			if (!shouldContinue) return;
		}
		
		var radioBtn = jQuery("<input type='radio' class='radioBtnSelectInstrument' name='instrumentSelect' id='radioBtnSelectInstrument_" + jsslab.instrumentsPage.radioBtnId-- + "' />");
		radioBtn.click(jsslab.instrumentsPage.onRadioButtonClick);
		jsslab.instrumentsPage.addNewTableRow("tableInstruments", new Array( radioBtn, "", "", "", "", "" ));
		jsslab.instrumentsPage.editing = true;
		radioBtn.click();
	});
	
	jQuery("#buttonRetireInstrument").click(function(event) {
		event.preventDefault();
		var uuid = jsslab.instrumentsPage.getSelectedInstrumentUuid();
		var retireReason = jQuery('#textBoxRetireReasonInstrument').val();
		
		jsslab.instrumentsPage.deleteInstrument(uuid, retireReason);
	});
	
	jQuery("#buttonSaveInstrument").click(function(event) {
		event.preventDefault();
		jQuery("#buttonAddNewInstrument").removeAttr('disabled');
		
		var instrument = jsslab.instrumentsPage.getInstrumentFromForm();
		jsslab.instrumentsPage.saveInstrument(instrument, jsslab.instrumentsPage.getSelectedInstrumentUuid());
		
		jsslab.instrumentsPage.editing = false;
		jQuery('#instrumentEditForm').fadeOut();
	});
	
	jQuery("#buttonCancelEditingInstrument").click(function(event) {
		event.preventDefault();
		
		if (jQuery('#buttonAddNewInstrument').attr('disabled') == true) {
			jQuery('#buttonAddNewInstrument').removeAttr('disabled');
			jQuery('#tableInstruments tr:last').remove();
		}
		
//		var shouldContinue = confirm("You have made changes to this item. Are you sure you want to cancel and dismiss the changes?");
//		if (!shouldContinue) return;
		
		jsslab.instrumentsPage.editing = false;
		
		if (jsslab.instrumentsPage.isValidUuid(jsslab.instrumentsPage.getSelectedInstrumentUuid())) {
			jQuery('.radioBtnSelectInstrument:checked').attr('checked', false);
		} else {
			jQuery('.radioBtnSelectInstrument:checked').parent().parent().remove();
		}
		jQuery('#instrumentEditForm').fadeOut();
	});
	
	jQuery(".radioBtnSelectInstrument").click(jsslab.instrumentsPage.onRadioButtonClick);
	
	
	// Setup controls for supply items
	
	jQuery("#buttonAddNewSupplyItem").click(function(event) {
		event.preventDefault();
		jQuery("#buttonAddNewSupplyItem").attr('disabled', 'disabled');
		jQuery("#buttonDuplicateSupplyItem").attr('disabled', 'disabled');
		
		if (jsslab.instrumentsPage.editing) {
//			var shouldContinue = confirm("You are currently editing another item. Are you sure you want to dismiss the changes and add a new item instead?");
//			if (!shouldContinue) return;
		}
		
		var radioBtn = jQuery("<input type='radio' class='radioBtnSelectSupplyItem' name='supplyItemSelect' id='radioBtnSelectSupplyItem_" + jsslab.instrumentsPage.radioBtnId-- + "' />");
		radioBtn.click(jsslab.instrumentsPage.onRadioButtonClick);
		jsslab.instrumentsPage.addNewTableRow("tableSupplyItems", new Array( radioBtn, "", "", "", "", "" ));
		jsslab.instrumentsPage.editing = true;
		radioBtn.click();
	});
	
	jQuery("#buttonDuplicateSupplyItem").click(function(event) {
		event.preventDefault();
		jQuery("#buttonAddNewSupplyItem").attr('disabled', 'disabled');
		jQuery("#buttonDuplicateSupplyItem").attr('disabled', 'disabled');

		var uuid = getSelectedSupplyItemUuid();
		
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab/labsupplyitem/" + uuid + "?v=full",
			success : function(data) {
				var supplyItem = data;

				var radioBtn = jQuery("<input type='radio' class='radioBtnSelectSupplyItem' name='supplyItemSelect' id='radioBtnSelectSupplyItem_" + jsslab.instrumentsPage.radioBtnId-- + "' />");
				
				jsslab.instrumentsPage.addNewTableRow("tableSupplyItems", new Array( radioBtn, supplyItem.labStockNumber, supplyItem.manufacturer, supplyItem.itemName, supplyItem.lotNumber, supplyItem.retired ? "true" : "false" ));
				updateSupplyItemForm(supplyItem, true);

				radioBtn.click(jsslab.instrumentsPage.onRadioButtonClick);
				radioBtn.attr('checked', 'checked');
			}
		});
		
		
	});
	
	jQuery("#buttonRetireSupplyItem").click(function(event) {
		event.preventDefault();
		var uuid = getSelectedSupplyItemUuid();
		var retireReason = jQuery('#textBoxRetireReasonSupplyItem').val();
		
		jsslab.instrumentsPage.deleteSupplyItem(uuid, retireReason);
	});
	
	jQuery("#buttonSaveSupplyItem").click(function(event) {
		event.preventDefault();
		jQuery("#buttonAddNewSupplyItem").removeAttr('disabled');
		jQuery("#buttonDuplicateSupplyItem").removeAttr('disabled');
		
		var supplyItem = getSupplyItemFromForm();
		saveSupplyItem(supplyItem, getSelectedSupplyItemUuid());
		
		jsslab.instrumentsPage.editing = false;
		jQuery('#supplyItemEditForm').fadeOut();
	});
	
	jQuery("#buttonCancelEditingSupplyItem").click(function(event) {
		event.preventDefault();
		if (jQuery('#buttonAddNewSupplyItem').attr('disabled') == true) {
			jQuery("#buttonAddNewSupplyItem").removeAttr('disabled');
			jQuery("#buttonDuplicateSupplyItem").removeAttr('disabled');
			jQuery('#tableSupplyItems tr:last').remove();
		}
		
//		var shouldContinue = confirm("You have made changes to this item. Are you sure you want to cancel and dismiss the changes?");
//		if (!shouldContinue) return;
		
		jsslab.instrumentsPage.editing = false;
		
		if (jsslab.instrumentsPage.isValidUuid(getSelectedSupplyItemUuid())) {
			jQuery('.radioBtnSelectSupplyItem:checked').attr('checked', false);
		} else {
			jQuery('.radioBtnSelectSupplyItem:checked').parent().parent().remove();
		}
		jQuery('#supplyItemEditForm').fadeOut();
	});
	
	jQuery(".radioBtnSelectSupplyItem").click(jsslab.instrumentsPage.onRadioButtonClick);
	
	// Setup dropdown options for location
	jQuery.ajax({
		url : openmrsContextPath + "/ws/rest/v1/location",
		success : function(data) {
			jsslab.instrumentsPage.instrumentLocations = data.results;
			
			for (var i = 0; i < jsslab.instrumentsPage.instrumentLocations.length; i++) {
				var instrLoc = jsslab.instrumentsPage.instrumentLocations[i];
				jQuery('#instrumentLocation').append('<option value="' + instrLoc.uuid + '">' + instrLoc.display + '</option>');
			}
			
			
		}
	});
	
	// Setup dropdown options for condition
	var assetConditionUuid = jsslab.constants.conceptSets.conditions["ASSET CONDITION"];
	jQuery.ajax({
		url : openmrsContextPath + "/ws/rest/v1/concept/" + assetConditionUuid,
		success : function(data) {
			jsslab.instrumentsPage.instrumentConditions = data.setMembers;

			for (var i = 0; i < jsslab.instrumentsPage.instrumentConditions.length; i++) {
				var instrCond = jsslab.instrumentsPage.instrumentConditions[i];
				jQuery('#instrumentCondition').append('<option value=' + instrCond.uuid + '>' + instrCond.display + '</option>');
			}
		}
	});
	
	// Setup dropdown options for supply item type
	jQuery.ajax({
		url : openmrsContextPath + "/ws/rest/v1/concept/b816df69-4873-11e1-b5ed-0024e8c61285",
		success : function(data) {
			jsslab.instrumentsPage.supplyItemTypes = data.setMembers;

			for (var i = 0; i < jsslab.instrumentsPage.supplyItemTypes.length; i++) {
				var supplyItemType = jsslab.instrumentsPage.supplyItemTypes[i];
				jQuery('#supplyItemType').append('<option value=' + supplyItemType.uuid + '>' + supplyItemType.display + '</option>');
			}
			
		}
	});
});


// =========================================
// SUPPLY ITEM METHODS
// =========================================

/**
 * Retrieves the list of all LabInstruments and displays them in the table.
 * 
 * This is used to update the table when an item in the table has been changed.  
 */
function updateSupplyItemsTable() {
	jQuery.ajax({
		url : openmrsContextPath + "/ws/rest/v1/jsslab/labsupplyitem?v=full&includeAll=true",
		success : function(data) {
				
			jQuery('#tableSupplyItems tr').filter(function() {
				return !jQuery(this).hasClass('header');	
			}).remove();
			
			for (var i = 0; i < data.results.length; i++) {
				var supplyItem = data.results[i];
				var radioBtn = jQuery("<input type='radio' class='radioBtnSelectSupplyItem' name='supplyItemSelect' id='radioBtnSelectSupplyItem_" + supplyItem.uuid + "' />");
				radioBtn.click(jsslab.instrumentsPage.onRadioButtonClick);
				
				jsslab.instrumentsPage.addNewTableRow("tableSupplyItems", new Array( radioBtn, supplyItem.labStockNumber, supplyItem.manufacturer, supplyItem.itemName, supplyItem.lotNumber, supplyItem.retired ? "true" : "false" ));
			}
		}
	});
	jQuery('#buttonDuplicateSupplyItem').attr('disabled', 'disabled');
}

/**
 * Displays a form to edit the supply item associated with the given UUID
 * 
 * @param uuid The UUID of the supply item to be edited
 */
function showSupplyItemForm(uuid) {
	//check whether object already has an existing uuid or is newly created
	if (!jsslab.instrumentsPage.isValidUuid(uuid)) {
		updateSupplyItemForm({});
		jQuery('#buttonDuplicateSupplyItem').attr('disabled', 'disabled');
	} else {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab/labsupplyitem/" + uuid + "?v=full",
			success : function(data) {
				var supplyItem = data;
				updateSupplyItemForm(supplyItem);
			}
		});
	}
}

/**
 * Sets the supply item form elements to the values of the given supply item object
 * 
 * @param supplyItem A supply item object containing the values to set the form elements to
 */
function updateSupplyItemForm(supplyItem) {
	updateSupplyItemForm(supplyItem, false);
}
function updateSupplyItemForm(supplyItem, duplicate) {
	jQuery('#supplyItemEditForm').fadeIn();
	
	jQuery('#supplyItemStockNumber').val(supplyItem.labStockNumber != null ? supplyItem.labStockNumber : "");
	jQuery('#supplyItemManufacturer').val(supplyItem.manufacturer != null ? supplyItem.manufacturer : "");
	jQuery('#supplyItemName').val(supplyItem.itemName != null ? supplyItem.itemName : "");
	jQuery('#supplyItemLotNumber').val(supplyItem.lotNumber != null ? supplyItem.lotNumber : "");
	
	jQuery('#supplyItemManufacturerStockNumber').val(supplyItem.manufacturerStockNumber != null ? supplyItem.manufacturerStockNumber : "");
	jQuery('#supplyItemExpirationDate').val(supplyItem.expirationDate != null ? jQuery.datepicker.formatDate( 'dd/mm/yy', new Date(supplyItem.expirationDate)) : "" );
	
	// Set selector to the current value of the supply item's type
	jQuery('#supplyItemType').val(-1);
	if (supplyItem != null && supplyItem.itemClassConcept != null) {
		jQuery('#supplyItemType').val(supplyItem.itemClassConcept.uuid);
	}
	if (duplicate) {
		jQuery('#buttonDuplicateSupplyItem').attr('disabled', 'disabled');
	} else {
		jQuery('#buttonDuplicateSupplyItem').removeAttr('disabled');
	}
}

/**
 * Reads the form data of the Supply Item Form and writes it in one object that can be used for further processing.
 * 
 * @returns a SupplyItem object containing the form data
 */
function getSupplyItemFromForm() {
	
	return {
		labStockNumber : jQuery('#supplyItemStockNumber').val(),
		manufacturer : jQuery('#supplyItemManufacturer').val(),
		itemName : jQuery('#supplyItemName').val(),
		lotNumber : jQuery('#supplyItemLotNumber').val(),
		
		manufacturerStockNumber : jQuery('#supplyItemManufacturerStockNumber').val(),
		expirationDate : jQuery.datepicker.parseDate('dd/mm/yy', jQuery('#supplyItemExpirationDate').val()),
		
		itemClassConcept : jQuery('#supplyItemType').val(),
		
	};
	
}

/**
 * 
 * @param supplyItem
 */
function saveSupplyItem(supplyItem, uuid) {
	var url = openmrsContextPath + "/ws/rest/v1/jsslab/labsupplyitem";
	if (jsslab.instrumentsPage.isValidUuid(uuid)) {
		url += "/" + uuid;
	}
	
	jQuery.ajax({
		"url" : url,
		"type" : "POST",
		"contentType" : "application/json",
		"data": JSON.stringify(supplyItem),
		"success" : function(data) {
			updateSupplyItemsTable();
		}
	});
}

function getSelectedSupplyItemUuid() {
	var btnId = jQuery('.radioBtnSelectSupplyItem:checked').attr('id');
	return btnId.substring(btnId.lastIndexOf("_")+1);
}

