<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<div xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:openmrs="urn:jsptld:/WEB-INF/taglibs/openmrs.tld"
	version="2.0">
	
	<%@ include file="localHeader.jsp" %>
	
	<openmrs:require privilege="Manage Global Properties" otherwise="/login.htm" redirect="/module/jsslab/admin/instruments.form" />
	
	<openmrs:requireConfiguration propertyList="jsslab.setup" configurationPage="/module/jsslab/admin/setup.form?targetView=module/jsslab/admin/instruments" />
	
	<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
	<openmrs:htmlInclude file="/moduleResources/jsslab/css/jsslab.css"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/instruments.js"/>
	
	<!-- ====================== --> 
	<!-- LAB INSTRUMENT SECTION --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="jsslab.instruments.title" /></h2>
	
	<span class="boxHeader">
		<spring:message code="jsslab.instruments.list" />
	</span>
	<div class="box">
		<table id="tableInstruments">
			<tr class="header">
				<th></th>
				<th>Property Tag</th>
				<th>Manufacturer</th>
				<th>Model</th>
				<th>Serial No.</th>
				<th>Retired</th>
			</tr>
			<c:forEach items="${instruments}" var="instrument" varStatus="loop">
			<tr>
				<td><input type="radio" class="radioBtnSelectInstrument" id="radioBtnSelectInstrument_${instrument.uuid}" name="instrumentSelect"></td>
				<td id="insPropertyTag${loop.index}" >${instrument.propertyTag}</td>
				<td id="insManufacturer${loop.index}" >${instrument.manufacturer}</td>
				<td id="insModel${loop.index}" >${instrument.model}</td>
				<td id="insSerialNumber${loop.index}" >${instrument.serialNumber}</td>
				<td id="insRetired${loop.index}" >${instrument.retired}</td>
			</tr>
			</c:forEach>
		</table>
		
		<br />

		<div id="instrumentAdd">
			<input type="submit" id="buttonAddNewInstrument" value="Add New Instrument" />
		</div>
	</div>
	
	
	<br />
	
	<div id="instrumentEditForm">
		<span class="boxHeader">
			<spring:message code="jsslab.instruments.edit" />
		</span>
	
		<div class="box">
			<table>
				<tr>
					<td>Property Tag</td>
					<td>Manufacturer</td>
					<td>Model</td>
					<td>Serial No.</td>
				</tr><tr>
					<td><input type="text" id="instrumentPropertyTag" /></td>
					<td><input type="text" id="instrumentManufacturer"/></td>
					<td><input type="text" id="instrumentModel"/></td>
					<td><input type="text" id="instrumentSerialNumber"/></td>
				</tr><tr>
					<td colspan="2">Location</td>
					<td>Condition Date</td>
					<td>Condition</td>
				</tr><tr>
					<td colspan="2">
						<select id="instrumentLocation" >
							<option value="-1" selected="selected"></option>
						</select>
					</td>
					<td><input type="text" id="instrumentConditionDate" onclick="showCalendar(this)" /></td>
					<td>
						<select id="instrumentCondition" >
							<option value="-1" selected="selected"></option>
						</select>
					</td>
				</tr><tr>
					<td>Received Date</td>
					<td>Received From</td>
					<td>Received Cost</td>
					<td>Received Value</td>
				</tr><tr>
					<td><input type="text" id="instrumentReceivedDate" onclick="showCalendar(this)" /></td>
					<td><input type="text" id="instrumentReceivedFrom"/></td>
					<td><input type="text" id="instrumentReceivedCost"/></td>
					<td><input type="text" id="instrumentReceivedValue"/></td>
				</tr><tr>
					<td>Maintenance Vendor</td>
					<td>Maintenance Phone</td>
					<td colspan="2">Maintenance Description</td>
				</tr><tr>
					<td><input type="text" id="instrumentMaintenanceVendor"/></td>
					<td><input type="text" id="instrumentMaintenancePhone"/></td>
					<td colspan="2"><input type="text" id="instrumentMaintenanceDescription"/></td>
				</tr>
			</table>
			
			<br />
			
			<div>
				<input type="submit" id="buttonSaveInstrument" value="Accept" />
				<input type="submit" id="buttonCancelEditingInstrument" value="Cancel" />
			</div>
		</div>
	</div>
	<br />
	
	<!-- ======================= --> 
	<!-- LAB SUPPLY ITEM SECTION --> 
	<!-- ======================= --> 
	
	<h2><spring:message code="jsslab.supplies.title" /></h2>
	
	<span class="boxHeader">
		<spring:message code="jsslab.instruments.list" />
	</span>
	<div class="box">
		<table id="tableSupplyItems">
			<tr class="header">
				<th></th>
				<th>Lab Stock No.</th>
				<th>Manufacturer</th>
				<th>Item</th>
				<th>Lot No.</th>
				<th>Retired</th>
			</tr>
			<c:forEach items="${supplies}" var="supplyItem" varStatus="loop">
			<tr>
				<td><input type="radio" class="radioBtnSelectSupplyItem" id="radioBtnSelectSupplyItem_${supplyItem.uuid}" name="supplyItemSelect"></td>
				<td>${supplyItem.labStockNumber}</td>
				<td>${supplyItem.manufacturer}</td>
				<td>${supplyItem.itemName}</td>
				<td>${supplyItem.lotNumber}</td>
				<td>${supplyItem.retired}</td>
			</tr>
			</c:forEach>
		</table>
		
		<br />
		
		<div id="supplyItemAdd">
			<input type="submit" id="buttonAddNewSupplyItem" value="Add New Supply Item" />
			<input type="submit" id="buttonDuplicateSupplyItem" value="Duplicate Supply Item" disabled="disabled" />
		</div>
	</div>

	<br />

	<div id="supplyItemEditForm">
		<span class="boxHeader">
			<spring:message code="jsslab.instruments.edit" />
		</span>
		<div class="box">
			<table>
				<tr>
					<td>Lab Stock No.</td>
					<td>Manufacturer</td>
					<td>Item</td>
					<td>Lot No.</td>
				</tr><tr>
					<td><input type="text" id="supplyItemStockNumber" /></td>
					<td><input type="text" id="supplyItemManufacturer"/></td>
					<td><input type="text" id="supplyItemName"/></td>
					<td><input type="text" id="supplyItemLotNumber"/></td>
				</tr><tr>
					<td colspan="2">Supply Type</td>
					<td>Manufacturer Stock No.</td>
					<td>Expiration Date</td>
				</tr><tr>
					<td colspan="2">
						<select id="supplyItemType" >
							<option value="-1" selected="selected"></option>
						</select>
					</td>
					<td><input type="text" id="supplyItemManufacturerStockNumber" /></td>
					<td><input type="text" id="supplyItemExpirationDate" onclick="showCalendar(this)" /></td>
				</tr>
			</table>
			
			<br />
			
			<div>
				<input type="submit" id="buttonSaveSupplyItem" value="Accept" />
				<input type="submit" id="buttonCancelEditingSupplyItem" value="Cancel" />
			</div>
		</div>
		
	</div>
	
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>