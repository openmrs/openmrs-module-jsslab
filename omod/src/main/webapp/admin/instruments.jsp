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
				<th><spring:message code="jsslab.instruments.field.propertyTag" /></th>
				<th><spring:message code="jsslab.instruments.field.manufacturer" /></th>
				<th><spring:message code="jsslab.instruments.field.model" /></th>
				<th><spring:message code="jsslab.instruments.field.serialNumber" /></th>
				<th><spring:message code="jsslab.instruments.field.retired" /></th>
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
			<input type="submit" id="buttonAddNewInstrument" value="<spring:message code="jsslab.instruments.list.add" />" />
			
			<label for="textBoxRetireReasonInstrument"><spring:message code="jsslab.instruments.list.retireReason" /></label>
			<input type="text" id="textBoxRetireReasonInstrument" />
			<input type="submit" id="buttonRetireInstrument" value="<spring:message code="jsslab.instruments.list.retire" />" />
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
					<td><spring:message code="jsslab.instruments.field.propertyTag" /></td>
					<td><spring:message code="jsslab.instruments.field.manufacturer" /></dh>
					<td><spring:message code="jsslab.instruments.field.model" /></td>
					<td><spring:message code="jsslab.instruments.field.serialNumber" /></td>
				</tr><tr>
					<td><input type="text" id="instrumentPropertyTag" /></td>
					<td><input type="text" id="instrumentManufacturer"/></td>
					<td><input type="text" id="instrumentModel"/></td>
					<td><input type="text" id="instrumentSerialNumber"/></td>
				</tr><tr>
					<td colspan="2"><spring:message code="jsslab.instruments.field.location" /></td>
					<td><spring:message code="jsslab.instruments.field.conditionDate" /></td>
					<td><spring:message code="jsslab.instruments.field.condition" /></td>
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
					<td><spring:message code="jsslab.instruments.field.receivedDate" /></td>
					<td><spring:message code="jsslab.instruments.field.receivedFrom" /></td>
					<td><spring:message code="jsslab.instruments.field.receivedCost" /></td>
					<td><spring:message code="jsslab.instruments.field.receivedValue" /></td>
				</tr><tr>
					<td><input type="text" id="instrumentReceivedDate" onclick="showCalendar(this)" /></td>
					<td><input type="text" id="instrumentReceivedFrom"/></td>
					<td><input type="text" id="instrumentReceivedCost"/></td>
					<td><input type="text" id="instrumentReceivedValue"/></td>
				</tr><tr>
					<td><spring:message code="jsslab.instruments.field.maintenanceVendor" /></td>
					<td><spring:message code="jsslab.instruments.field.maintenancePhone" /></td>
					<td colspan="2"><spring:message code="jsslab.instruments.field.maintenanceDescription" /></td>
				</tr><tr>
					<td><input type="text" id="instrumentMaintenanceVendor"/></td>
					<td><input type="text" id="instrumentMaintenancePhone"/></td>
					<td colspan="2"><input type="text" id="instrumentMaintenanceDescription"/></td>
				</tr>
			</table>
			
			<br />
			
			<div>
				<input type="submit" id="buttonSaveInstrument" value="<spring:message code="jsslab.instruments.edit.save" />" />
				<input type="submit" id="buttonCancelEditingInstrument" value="<spring:message code="jsslab.instruments.edit.cancel" />" />
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
				<th><spring:message code="jsslab.supplies.field.labStockNumber" /></th>
				<th><spring:message code="jsslab.supplies.field.manufacturer" /></th>
				<th><spring:message code="jsslab.supplies.field.item" /></th>
				<th><spring:message code="jsslab.supplies.field.lotNumber" /></th>
				<th><spring:message code="jsslab.supplies.field.retired" /></th>
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
			<input type="submit" id="buttonAddNewSupplyItem" value="<spring:message code="jsslab.supplies.list.add" />" />
			<input type="submit" id="buttonDuplicateSupplyItem" value="<spring:message code="jsslab.supplies.list.duplicate" />" disabled="disabled" />
			
			<label for="textBoxRetireReasonSupplyItem"><spring:message code="jsslab.supplies.list.retireReason" /></label>
			<input type="text" id="textBoxRetireReasonSupplyItem" />
			<input type="submit" id="buttonRetireSupplyItem" value="<spring:message code="jsslab.supplies.list.retire" />" />
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
					<td><spring:message code="jsslab.supplies.field.labStockNumber" /></td>
					<td><spring:message code="jsslab.supplies.field.manufacturer" /></td>
					<td><spring:message code="jsslab.supplies.field.item" /></td>
					<td><spring:message code="jsslab.supplies.field.lotNumber" /></td>
				</tr><tr>
					<td><input type="text" id="supplyItemStockNumber" /></td>
					<td><input type="text" id="supplyItemManufacturer"/></td>
					<td><input type="text" id="supplyItemName"/></td>
					<td><input type="text" id="supplyItemLotNumber"/></td>
				</tr><tr>
					<td colspan="2"><spring:message code="jsslab.supplies.field.supplyType" /></td>
					<td><spring:message code="jsslab.supplies.field.manufacurerStockNumber" /></td>
					<td><spring:message code="jsslab.supplies.field.expirationDate" /></td>
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
				<input type="submit" id="buttonSaveSupplyItem" value="<spring:message code="jsslab.supplies.edit.save" />" />
				<input type="submit" id="buttonCancelEditingSupplyItem" value="<spring:message code="jsslab.supplies.edit.cancel" />" />
			</div>
		</div>
		
	</div>
	
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>