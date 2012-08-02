<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<div id="jsslabCatalog" 
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:openmrs="urn:jsptld:/WEB-INF/taglibs/openmrs.tld"
	version="2.0">
	
	<%@ include file="localHeader.jsp" %>
	
	<openmrs:require privilege="Manage Global Properties" otherwise="/login.htm" redirect="/module/jsslab/admin/templates.form" />
	<openmrs:requireConfiguration propertyList="jsslab.setup" configurationPage="/module/jsslab/admin/setup.form?targetView=module/jsslab/admin/templates" />
	
	<script type="text/javascript">
		var jsslab = {
			"i18n" : {
				"catalog.investigations.investigationTable.empty" : "<spring:message code='jsslab.catalog.investigations.investigationTable.empty' javaScriptEscape='true' />",
			},
		};
	</script>
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/templates.js"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/css/jsslab.css"/>
	
	<!-- ====================== --> 
	<!--   Specimen Templates   --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="jsslab.templates.title" /></h2>

	<!-- Investigation Selection -->

	<div style="float:left;" id="investigationSelectionPanel">
		<span class="boxHeader">
			<spring:message code="jsslab.templates.investigations.selection.header" />
		</span>
		<div class="box">
			<table id="investigationTable" class="selectionTable">
				<thead><tr>
						<th><spring:message code="jsslab.templates.investigations.selection.section" /></th>
						<th><spring:message code="jsslab.templates.investigations.selection.investigation" /></th>
				</tr></thead>
				<tbody>
				
				</tbody>
			</table>
		</div>
	</div>
	
	<!-- Specimen Tree -->
	
	<div style="float:left;" id="specimenEditPanel">
		<span class="boxHeader">
			<spring:message code="jsslab.templates.specimen.selection.header" />
		</span>
		<div class="box">
			
		</div>
	</div>
	
	<div style="clear: both;"></div>
	
<%@ include file="/WEB-INF/template/footer.jsp"%>