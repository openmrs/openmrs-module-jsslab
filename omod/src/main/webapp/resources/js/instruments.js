// define startsWith String function if not exists
if (typeof String.prototype.startsWith != 'function') {
  String.prototype.startsWith = function (str){
    return this.indexOf(str) == 0;
  };
}

instrumentPage = {
	editing : false,
	instrumentLocations : null,
	instrumentConditions : null,
	supplyItemTypes : null,
	radioBtnId : -1,
	
	isValidUuid : function(uuid) {
		return uuid.length >= 36;
	},

	findLocationByUuid : function(uuid) {
		for (var i = 0; i < instrumentPage.instrumentLocations.length; i++) {
			if (instrumentPage.instrumentLocations[i].uuid == uuid) return instrumentPage.instrumentLocations[i];
		}
		return null;
	},
	findConditionByUuid : function(uuid) {
		for (var i = 0; i < instrumentPage.instrumentConditions.length; i++) {
			if (instrumentPage.instrumentConditions[i].uuid == uuid) return instrumentPage.instrumentConditions[i];
		}
		return null;
	},
	
	deleteInstrument : function(uuid, reason) {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab/labInstrument/" + uuid + "?reason="+reason,
			type : "DELETE",
			success : function() {
				updateInstrumentsTable();
			}
		});
	},
	deleteSupplyItem : function(uuid, reason) {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab/labSupplyItem/" + uuid + "?reason="+reason,
			type : "DELETE",
			success : function() {
				updateSupplyItemsTable();
			}
		});
	}
};

jQuery(document).ready(function() {
	
	// Setup controls for instruments
	
	jQuery("#buttonAddNewInstrument").click(function(event) {
		event.preventDefault();

		if (instrumentPage.editing) {
//			var shouldContinue = confirm("You are currently editing another item. Are you sure you want to dismiss the changes and add a new item instead?");
//			if (!shouldContinue) return;
		}
		
		var radioBtn = jQuery("<input type='radio' class='radioBtnSelectInstrument' name='instrumentSelect' id='radioBtnSelectInstrument_" + instrumentPage.radioBtnId-- + "' />");
		radioBtn.click(onRadioButtonClick);
		addNewTableRow("tableInstruments", new Array( radioBtn, "", "", "", "", "" ));
		instrumentPage.editing = true;
		radioBtn.click();
	});
	
	jQuery("#buttonRetireInstrument").click(function(event) {
		event.preventDefault();
		var uuid = getSelectedInstrumentUuid();
		var retireReason = jQuery('#textBoxRetireReasonInstrument').val()
		
		instrumentPage.deleteInstrument(uuid, retireReason);
	});
	
	jQuery("#buttonSaveInstrument").click(function(event) {
		event.preventDefault();

		var instrument = getInstrumentFromForm();
		saveInstrument(instrument, getSelectedInstrumentUuid());
		
		instrumentPage.editing = false;
		jQuery('#instrumentEditForm').fadeOut();
	});
	
	jQuery("#buttonCancelEditingInstrument").click(function(event) {
		event.preventDefault();
//		var shouldContinue = confirm("You have made changes to this item. Are you sure you want to cancel and dismiss the changes?");
//		if (!shouldContinue) return;
		
		instrumentPage.editing = false;
		
		if (instrumentPage.isValidUuid(getSelectedInstrumentUuid())) {
			jQuery('.radioBtnSelectInstrument:checked').attr('checked', false);
		} else {
			jQuery('.radioBtnSelectInstrument:checked').parent().parent().remove();
		}
		jQuery('#instrumentEditForm').fadeOut();
	});
	
	jQuery(".radioBtnSelectInstrument").click(onRadioButtonClick);
	
	
	// Setup controls for supply items
	
	jQuery("#buttonAddNewSupplyItem").click(function(event) {
		event.preventDefault();

		if (instrumentPage.editing) {
//			var shouldContinue = confirm("You are currently editing another item. Are you sure you want to dismiss the changes and add a new item instead?");
//			if (!shouldContinue) return;
		}
		
		var radioBtn = jQuery("<input type='radio' class='radioBtnSelectSupplyItem' name='supplyItemSelect' id='radioBtnSelectSupplyItem_" + instrumentPage.radioBtnId-- + "' />");
		radioBtn.click(onRadioButtonClick);
		addNewTableRow("tableSupplyItems", new Array( radioBtn, "", "", "", "", "" ));
		instrumentPage.editing = true;
		radioBtn.click();
	});
	
	jQuery("#buttonDuplicateSupplyItem").click(function(event) {
		event.preventDefault();

		var uuid = getSelectedSupplyItemUuid();
		
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab/labSupplyItem/" + uuid + "?v=full",
			success : function(data) {
				var supplyItem = data;

				var radioBtn = jQuery("<input type='radio' class='radioBtnSelectSupplyItem' name='supplyItemSelect' id='radioBtnSelectSupplyItem_" + instrumentPage.radioBtnId-- + "' />");
				
				addNewTableRow("tableSupplyItems", new Array( radioBtn, supplyItem.labStockNumber, supplyItem.manufacturer, supplyItem.itemName, supplyItem.lotNumber, supplyItem.retired ? "true" : "false" ));
				updateSupplyItemForm(supplyItem, true);

				radioBtn.click(onRadioButtonClick);
				radioBtn.attr('checked', 'checked');
			}
		});
		
		
	});
	
	jQuery("#buttonRetireSupplyItem").click(function(event) {
		event.preventDefault();
		var uuid = getSelectedSupplyItemUuid();
		var retireReason = jQuery('#textBoxRetireReasonSupplyItem').val()
		
		instrumentPage.deleteSupplyItem(uuid, retireReason);
	});
	
	jQuery("#buttonSaveSupplyItem").click(function(event) {
		event.preventDefault();

		var supplyItem = getSupplyItemFromForm();
		saveSupplyItem(supplyItem, getSelectedSupplyItemUuid());
		
		instrumentPage.editing = false;
		jQuery('#supplyItemEditForm').fadeOut();
	});
	
	jQuery("#buttonCancelEditingSupplyItem").click(function(event) {
		event.preventDefault();
//		var shouldContinue = confirm("You have made changes to this item. Are you sure you want to cancel and dismiss the changes?");
//		if (!shouldContinue) return;
		
		instrumentPage.editing = false;
		
		if (instrumentPage.isValidUuid(getSelectedSupplyItemUuid())) {
			jQuery('.radioBtnSelectSupplyItem:checked').attr('checked', false);
		} else {
			jQuery('.radioBtnSelectSupplyItem:checked').parent().parent().remove();
		}
		jQuery('#supplyItemEditForm').fadeOut();
	});
	
	jQuery(".radioBtnSelectSupplyItem").click(onRadioButtonClick);
	
	// Setup dropdown options for location
	jQuery.ajax({
		url : openmrsContextPath + "/ws/rest/v1/location",
		success : function(data) {
			instrumentPage.instrumentLocations = data.results;
			
			for (var i = 0; i < instrumentPage.instrumentLocations.length; i++) {
				var instrLoc = instrumentPage.instrumentLocations[i];
				jQuery('#instrumentLocation').append('<option value=' + instrLoc.uuid + '>' + instrLoc.display + '</option>');
			}
			
			
		}
	});
	
	// Setup dropdown options for condition
	jQuery.ajax({
		// This URL is used to retrieve the ASSET CONDITION concept by its uuid
		// It should be considered whether there is a more robust approach to retrieve the required information
		url : openmrsContextPath + "/ws/rest/v1/concept/b816ddc6-4873-11e1-b5ed-0024e8c61285",
		success : function(data) {
			instrumentPage.instrumentConditions = data.setMembers;

			for (var i = 0; i < instrumentPage.instrumentConditions.length; i++) {
				var instrCond = instrumentPage.instrumentConditions[i];
				jQuery('#instrumentCondition').append('<option value=' + instrCond.uuid + '>' + instrCond.display + '</option>');
			}
		}
	});
	
	// Setup dropdown options for supply item type
	jQuery.ajax({
		url : openmrsContextPath + "/ws/rest/v1/concept/b816df69-4873-11e1-b5ed-0024e8c61285",
		success : function(data) {
			instrumentPage.supplyItemTypes = data.setMembers;

			for (var i = 0; i < instrumentPage.supplyItemTypes.length; i++) {
				var supplyItemType = instrumentPage.supplyItemTypes[i];
				jQuery('#supplyItemType').append('<option value=' + supplyItemType.uuid + '>' + supplyItemType.display + '</option>');
			}
			
		}
	});
});


/**
 * Adds a new row to the table with the given ID, using the values provided. 
 * @param tableId the ID of the table to add a row to
 * @param values the values to add into each column
 */
function addNewTableRow(tableId, values) {
	
	tableRow = jQuery("<tr />");
	for (var i = 0; i < values.length; i++) {
		tableRow.append(
				jQuery("<td />").html(values[i])
			);
	}
	
	jQuery('#' + tableId + ' tr:last').after(tableRow);
	
}

/**
 * Click handler for radio buttons
 * Will invoke <code>editInstrumentInForm()</code> for the selected instrument 
 */
function onRadioButtonClick(event) {
	if (instrumentPage.editing) {
//		var shouldContinue = confirm("You are currently editing another item. Are you sure you want to dismiss the changes and add a new item instead?");
//		if (!shouldContinue) {
//			event.preventDefault();
//			return;
//		}
	}

	var btnId = jQuery(this).attr('id');
	var itemUuid = btnId.substring(btnId.lastIndexOf("_")+1);
	instrumentPage.editing = true;

	if (btnId.startsWith("radioBtnSelectInstrument_")) {
		showInstrumentForm(itemUuid);
	} else {
		showSupplyItemForm(itemUuid);
	}
	
}

/**
 * Retrieves the list of all LabInstruments and displays them in the table.
 * 
 * This is used to update the table when an item in the table has been changed.  
 */
function updateInstrumentsTable() {
	jQuery.ajax({
		url : openmrsContextPath + "/ws/rest/v1/jsslab/labInstrument?v=full",
		success : function(data) {
				
			jQuery('#tableInstruments tr').filter(function() {
				return !jQuery(this).hasClass('header');	
			}).remove();
			
			for (var i = 0; i < data.results.length; i++) {
				var instrument = data.results[i];
				var radioBtn = jQuery("<input type='radio' class='radioBtnSelectInstrument' name='instrumentSelect' id='radioBtnSelectInstrument_" + instrument.uuid + "' />");
				radioBtn.click(onRadioButtonClick);
				
				addNewTableRow("tableInstruments", new Array( radioBtn, instrument.propertyTag, instrument.manufacturer, instrument.model, instrument.serialNumber, instrument.retired ? "true" : false ));
			}
		}
	});
}

/**
 * Displays a form to edit the instrument associated with the given UUID
 * 
 * @param uuid The UUID of the instrument to be edited
 */
function showInstrumentForm(uuid) {
	//check whether object already has an existing uuid or is newly created
	if (!instrumentPage.isValidUuid(uuid)) {
		updateInstrumentForm({});
	} else {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab/labInstrument/" + uuid + "?v=full",
			success : function(data) {
				var instrument = data;
				updateInstrumentForm(instrument);
			}
		});
	}
}

/**
 * Sets the instrument form elements to the values of the given instrument object
 * 
 * @param instrument An instrument object containing the values to set the form elements to
 */
function updateInstrumentForm(instrument) {
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
	
}

/**
 * Reads the form data of the Instrument Form and writes it in one object that can be used for further processing.
 * 
 * @returns an Instrument object containing the form data
 */
function getInstrumentFromForm() {
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
	
		//TODO
		retired : false,
	};
		
	return instrument;
}

/**
 * 
 * @param instrument
 */
function saveInstrument(instrument, uuid) {
	var url = openmrsContextPath + "/ws/rest/v1/jsslab/labInstrument";
	if (instrumentPage.isValidUuid(uuid)) {
		url += "/" + uuid;
	}
	
	jQuery.ajax({
		"url" : url,
		"type" : "POST",
		"contentType" : "application/json",
		"data": JSON.stringify(instrument),
		"success" : function(data) {
			updateInstrumentsTable();
		}
	});
}

function getSelectedInstrumentUuid() {
	var btnId = jQuery('.radioBtnSelectInstrument:checked').attr('id');
	return btnId.substring(btnId.lastIndexOf("_")+1);
}

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
		url : openmrsContextPath + "/ws/rest/v1/jsslab/labSupplyItem?v=full",
		success : function(data) {
				
			jQuery('#tableSupplyItems tr').filter(function() {
				return !jQuery(this).hasClass('header');	
			}).remove();
			
			for (var i = 0; i < data.results.length; i++) {
				var supplyItem = data.results[i];
				var radioBtn = jQuery("<input type='radio' class='radioBtnSelectSupplyItem' name='supplyItemSelect' id='radioBtnSelectSupplyItem_" + supplyItem.uuid + "' />");
				radioBtn.click(onRadioButtonClick);
				
				addNewTableRow("tableSupplyItems", new Array( radioBtn, supplyItem.labStockNumber, supplyItem.manufacturer, supplyItem.itemName, supplyItem.lotNumber, supplyItem.retired ? "true" : "false" ));
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
	if (!instrumentPage.isValidUuid(uuid)) {
		updateSupplyItemForm({});
		jQuery('#buttonDuplicateSupplyItem').attr('disabled', 'disabled');
	} else {
		jQuery.ajax({
			url : openmrsContextPath + "/ws/rest/v1/jsslab/labSupplyItem/" + uuid + "?v=full",
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
		
		//TODO
		retired : false,
	};
	
}

/**
 * 
 * @param supplyItem
 */
function saveSupplyItem(supplyItem, uuid) {
	var url = openmrsContextPath + "/ws/rest/v1/jsslab/labSupplyItem";
	if (instrumentPage.isValidUuid(uuid)) {
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

