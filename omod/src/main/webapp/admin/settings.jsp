<?xml version="1.0" encoding="UTF-8" standalone="no"?>

<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<div id="jsslabSettings" 
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:openmrs="urn:jsptld:/WEB-INF/taglibs/openmrs.tld"
	version="2.0">
	
	<spring:message var="pageTitle" code="jsslab.settings.link" scope="page" />
	
	<%@ include file="localHeader.jsp" %>
	
	<openmrs:require privilege="Manage Global Properties" otherwise="/login.htm" redirect="/module/jsslab/admin/settings.form" />
	<openmrs:requireConfiguration propertyList="jsslab.setup" configurationPage="/module/jsslab/admin/setup.form?targetView=module/jsslab/admin/settings" />
	
	<openmrs:htmlInclude file="/scripts/jquery/jsTree/jquery.tree.min.js" />
	<openmrs:htmlInclude file="/scripts/jquery/jsTree/themes/classic/style.css" />
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			jQuery('#hierarchyTree').tree({
				"data": {
					"type": "json",
					"opts": {
						static: ${json}
					}
				},
				"types": {
					"default" : {
						clickable	: false,
						renameable	: false,
						deletable	: false,
						creatable	: false,
						draggable	: false,
						max_children: -1,
						max_depth	: -1,
						valid_children	: "all"
					}
				},
				"ui": {
					theme_name: "classic"
				}
			});
			jQuery.tree.reference('#hierarchyTree').open_all();
		});
		
		var jsslab = {
			"i18n" : {
				"settings.codelists.codetable.empty" : "<spring:message code='jsslab.settings.codelists.codetable.empty' javaScriptEscape='true' />",
			},
		};
	</script>

	<openmrs:htmlInclude file="/moduleResources/jsslab/js/settings.js"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/css/jsslab.css"/>
	
	
	<!-- ====================== --> 
	<!-- Locations --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="jsslab.settings.locations.title" /></h2>
	
	<div id="locationTreePanel">
		<fieldset>
			<legend><spring:message code="jsslab.settings.locations.hierarchy"/></legend>
			<div id="hierarchyTree"></div>
		</fieldset>
	</div>
	<div style="float:left; margin-left: 10px;">
		
		<div id="addLocationPanel">
			<input type="submit" value="<spring:message code="jsslab.settings.locations.addLocation" />" />
		</div>
		
		<div id="editLocationPanel">
		<span class="boxHeader">
			<spring:message code="jsslab.settings.locations.header" />
		</span>
		<div class="box">
			<input type="submit" value="<spring:message code="jsslab.settings.locations.btnmanaged"/>" />
			<br />
			<input type="submit" value="<spring:message code="jsslab.settings.locations.btnreferral"/>" />
			<br />
			<div>
				<label class="locationsLabel" for="editLocationName"><spring:message code="jsslab.settings.locations.locationname" /></label>
				<input type="text" id="editLocationName" />

				<br />

				<label class="locationsLabel" for="editLocationAddress"><spring:message code="jsslab.settings.locations.locationaddress" /></label>
				<input type="text" id="editLocationAddress" />
			</div>
			<div>
				<input type="submit" value="<spring:message code="jsslab.settings.locations.saveLocation" />" />
				<input type="submit" value="<spring:message code="jsslab.settings.locations.cancelLocation" />" />
			</div>
		</div>
		</div>
	</div>
	<div style="clear:both;"></div>

	<br />
	
	<!-- ====================== --> 
	<!-- Global Properties --> 
	<!-- ====================== --> 

	<h2><spring:message code="jsslab.settings.globalproperties.title" /></h2>

	<table>
		<c:forEach var="gp" items="${globalPropertiesString}" varStatus="loop">
			<tr>
				<td>
					<label for="globalPropertyStringSelect_${loop.index}">${gp.description}</label>
				</td><td>
					<input type="text" name="${gp.property}" id="globalPropertyStringSelect_${loop.index}" value="${gp.propertyValue}" />
				</td><td>
					<input type="submit" class="globalPropertyStringSubmit" id="globalPropertyStringSubmit_${loop.index}" value="<spring:message code='jsslab.settings.globalproperties.save' />" />
				</td><td>
					<span id="globalPropertyStringResult_${loop.index}"></span>
				</td>
			</tr>
		</c:forEach>
		<c:forEach var="gp" items="${globalPropertiesObject}" varStatus="loop">
			<tr>
				<td>
					<label for="globalPropertyObjectSelect_${loop.index}">${gp.description}</label>
				</td><td>
					<input type="text" name="${gp.property}" id="globalPropertyObjectSelect_${loop.index}" value="${gp.propertyValue}" />
				</td><td>
					<input type="submit" class="globalPropertyObjectSubmit" id="globalPropertyObjectSubmit_${loop.index}" value="<spring:message code='jsslab.settings.globalproperties.save' />" />
				</td><td>
					<span id="globalPropertyObjectResult_${loop.index}"></span>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<br />
	
	<!-- ====================== --> 
	<!-- Code Lists --> 
	<!-- ====================== --> 

	<h2><spring:message code="jsslab.settings.codelists.title" /></h2>
	
	<div id="codeListSelection">
		<span class="boxHeader">
			<spring:message code="jsslab.settings.codelists.codelist.title" />
		</span>
		<div class="box">
			<div id="radioPanel">
				<c:forEach var="conceptSet" items="${conceptSets}" varStatus="loop" >
					<div>
						<input type="radio" class="radioSpecimenTypeCode" name="codeList" id="radioSpecimenTypeCodes_${conceptSet.uuid}" ${loop.index == 0 ? 'checked="checked"' : ''} />
						<label for="radioSpecimenTypeCodes">${conceptSet.name}</label>
					</div>
				</c:forEach>
			</div>
			<div>
				<div><spring:message code="jsslab.settings.codelists.codelist.substitute" /></div>
				<div><input type="text" /></div>
				<div><input type="submit" value="<spring:message code="jsslab.settings.codelists.codelist.apply" />" /></div>
			</div>
		</div>
	</div>
	
	<div id="codeTablePanel" class="selectionTable">
		<table id="codeTable">
			<thead><tr>
				<th><spring:message code="jsslab.settings.codelists.codetable.head.text" /></th>
				<th><spring:message code="jsslab.settings.codelists.codetable.head.code" /></th>
				<th><spring:message code="jsslab.settings.codelists.codetable.head.retired" /></th>
			</tr></thead>
			<tbody>
				<tr><td colspan="3">
					<spring:message code="jsslab.settings.codelists.codetable.empty" />
				</td></tr>
			</tbody>
		</table>
		
		<br />
		
		<div>
			<input type="submit" id="buttonAddNewCode" value="<spring:message code="jsslab.settings.codelists.codeeditform.add" />" />
		</div>
	</div>
	
	<div id="codeEditPanel">
		<span class="boxHeader">
			<spring:message code="jsslab.settings.codelists.codeeditform.title" />
		</span>
		<div class="box">
			<div><label for="editTextConcept"><spring:message code="jsslab.settings.codelists.codeeditform.text" /></label></div>
			<div><input type="text" id="editTextConcept" /></div>
			<div><label for="editCode"><spring:message code="jsslab.settings.codelists.codeeditform.code" /></label></div>
			<div><input type="text" id="editCode" /></div>
			
			<br />
			
			<div>
				<input type="submit" id="buttonSaveCode" value="<spring:message code="jsslab.settings.codelists.codeeditform.save" />" />
				<input type="submit" id="buttonCancelEditingCode" value="<spring:message code="jsslab.settings.codelists.codeeditform.cancel" />" />
			</div>
		</div>
	</div>
	<div style="clear:both;"></div>
	
<%@ include file="/WEB-INF/template/footer.jsp"%>