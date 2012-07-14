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
	
	<openmrs:require privilege="Manage Global Properties" otherwise="/login.htm" redirect="/module/jsslab/admin/catalog.form" />
	
	<openmrs:requireConfiguration propertyList="jsslab.setup" configurationPage="/module/jsslab/admin/setup.form?targetView=module/jsslab/admin/catalog" />
	
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/catalog.js"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/css/jsslab.css"/>
	
	<!-- ====================== --> 
	<!-- Investigations --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="jsslab.catalog.investigations.title" /></h2>

	<div style="float:left;">
		<div>
			<label for="selectLaboratory"><spring:message code="jsslab.catalog.investigations.laboratoryselector"/></label>
			<select id="selectLaboratory">
				<option value="-1" selected="selected"><spring:message code="jsslab.catalog.investigations.laboratoryoption" /></option>
			</select>
		</div>
		<table>
			<thead>
				<tr>
					<th><spring:message code="jsslab.catalog.investigations.laboratorytable.section" /></th>
					<th><spring:message code="jsslab.catalog.investigations.laboratorytable.investigation" /></th>
				</tr>
			</thead>
			<tbody>
			
			</tbody>
		</table>
	</div>
	<div style="clear:both;"></div>

	<h2><spring:message code="jsslab.catalog.tests.title" /></h2>

<%@ include file="/WEB-INF/template/footer.jsp"%>