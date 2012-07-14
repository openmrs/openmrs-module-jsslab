<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<div id="jsslabSettings" 
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:openmrs="urn:jsptld:/WEB-INF/taglibs/openmrs.tld"
	version="2.0">
	
	<%@ include file="localHeader.jsp" %>
	
	<openmrs:require privilege="Manage Global Properties" otherwise="/login.htm" redirect="/module/jsslab/admin/settings.form" />
	
	<openmrs:requireConfiguration propertyList="jsslab.setup" configurationPage="/module/jsslab/admin/setup.form?targetView=module/jsslab/admin/settings" />
	
	<openmrs:htmlInclude file="/scripts/jquery/jsTree/jquery.tree.min.js" />
	<openmrs:htmlInclude file="/scripts/jquery/jsTree/themes/classic/style.css" />
	
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/settings.js"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/css/jsslab.css"/>
	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			jQuery('#hierarchyTree').tree({
				data: {
					type: "json",
					opts: {
						static: ${json}
					}
				},
				types: {
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
				ui: {
					theme_name: "classic"
				}
			});
			jQuery.tree.reference('#hierarchyTree').open_all();
		});
	</script>
	
	<!-- ====================== --> 
	<!-- Locations --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="jsslab.settings.locations.title" /></h2>
	
	<div style="float:left;">
		<fieldset>
			<legend><spring:message code="jsslab.settings.Locations.hierarchy"/></legend>
			<div id="hierarchyTree"></div>
		</fieldset>
	</div>
	<div style="float:left;">
		<div>
			<input type="submit" value="<spring:message code="jsslab.settings.Locations.btnmanaged"/>" />
			<input type="submit" value="<spring:message code="jsslab.settings.Locations.btnreferral"/>" />
		</div>
		<div>
			<label for="editLocationName"><spring:message code="jsslab.settings.locations.locationname"/></label>
			<input type="text" id="editLocationName" />
		</div>
		<div>
			<label for="editLocationAddress"><spring:message code="jsslab.settings.locations.locationaddress" /></label>
			<input type="text" id="editLocationAddress" />
		</div>
		<div>
			<input type="submit" value="<spring:message code="jsslab.settings.locations.addLocation"/>" />
			<input type="submit" value="<spring:message code="jsslab.settings.locations.saveLocation"/>" />
			<input type="submit" value="<spring:message code="jsslab.settings.locations.cancel"/>" />
			
		</div>
	</div>
	<div style="clear:both;"></div>


	<!-- ====================== --> 
	<!-- Global Properties --> 
	<!-- ====================== --> 

	<h2><spring:message code="jsslab.settings.globalproperties.title" /></h2>

	<div>
		<span><spring:message code="jsslab.settings.globalproperties.orderTypeLabel" /></span>
		<span><select>
			<option value="-1" selected="selected"><spring:message code="jsslab.settings.globalproperties.orderTypeSelect" /></option>
		</select></span>
		<span><input type="submit" value="<spring:message code='jsslab.settings.globalproperties.orderTypeCreate' />" /></span>
	</div>

	<h2><spring:message code="jsslab.settings.codelists.title" /></h2>
	
	<!-- ====================== --> 
	<!-- Code Lists --> 
	<!-- ====================== --> 
	
	<div>
		<div id="codeListSelection">
			<div id="radioPanel">
				<input type="radio" name="codeList" id="radioSpecimenTypeCodes" />
				<label for="radioSpecimenTypeCodes">Specimen Type Codes</label>
			</div>
			<div>
				<div>Substitute Concept Set</div>
				<div><input type="text" /></div>
				<div><input type="submit" value="Apply" /></div>
			</div>
		</div>
		
		<div id="codeTable">
			<table>
				<thead><tr>
					<th></th>
					<th><spring:message code="jsslab.settings.globalproperties.codetable.head.text" /></th>
					<th><spring:message code="jsslab.settings.globalproperties.codetable.head.code" /></th>
					<th><spring:message code="jsslab.settings.globalproperties.codetable.head.retired" /></th>
				</tr></thead>
				<tbody>
					
				</tbody>
			</table>
		</div>
		<div id="codeEditForm">
			<div><label for="editTextConcept"><spring:message code="jsslab.settings.globalproperties.codeeditform.text" /></label></div>
			<div><input type="text" id="editTextConcept" /></div>
			<div><label for="editCode"><spring:message code="jsslab.settings.globalproperties.codeeditform.code" /></label></div>
			<div><input type="text" id="editCode" /></div>
		</div>
		<div style="clear:both;"></div>
	</div>
	
<%@ include file="/WEB-INF/template/footer.jsp"%>