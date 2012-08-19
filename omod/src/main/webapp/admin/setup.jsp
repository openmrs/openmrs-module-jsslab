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
	
	<openmrs:require privilege="Manage Global Properties" otherwise="/login.htm" redirect="/module/jsslab/admin/setup.form" />
	
	<openmrs:htmlInclude file="/dwr/interface/DWRAdministrationService.js" />
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/jsslab.js"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/setup.js"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/css/jsslab.css"/>
	
	<script type="text/javascript">
		jsslab.sampleDataInstalled = ${sampleDataInstalled};
	</script>
	
	<!-- ====================== --> 
	<!--   LAB SETUP SECTION    --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="" /></h2>
	<div>
	
	</div>	
	
	<br />
	
	<!-- ====================== --> 
	<!-- Global Properties --> 
	<!-- ====================== --> 

	<h2><spring:message code="jsslab.setup.globalproperties.title" /></h2>

	<table>
		<%-- <c:forEach var="gp" items="${globalPropertiesObject}" varStatus="loop">
			<tr>
				<td>
					<label for="globalPropertyObjectSelect_${loop.index}">${gp.description}</label>
				</td><td>
					<input type="text" name="${gp.property}" id="globalPropertySelect_${loop.index}" value="${gp.propertyValue}" />
				</td><td>
					<input type="submit" class="globalPropertySubmit" id="globalPropertySubmit_${loop.index}" value="<spring:message code='jsslab.setup.globalproperties.save' />" />
				</td><td>
					<span id="globalPropertyResult_${loop.index}"></span>
				</td>
			</tr>
		</c:forEach>
		--%>
		<tr>
			<td>
				<label id="globalPropertyObjectLabel_orderType" for="globalPropertyObjectSelect_orderType">${gpOrderType.description}</label>
			</td><td>
				<input type="text" name="jsslab.object.orderType.labOrder" id="globalPropertySelect_orderType" value="${gpOrderType.propertyValue}" />
				<input type="hidden" id="globalPropertyName_orderType" value="${gpOrderType.property}" />
				<input type="hidden" id="globalPropertyUuid_orderType" value="${gpOrderType.propertyValue}" />
			</td><td>
				<input type="submit" class="globalPropertySubmit" id="globalPropertySubmit_orderType" value="<spring:message code='jsslab.setup.globalproperties.save' />" />
			</td><td>
				<span id="globalPropertyResult_orderType"></span>
			</td>
		</tr>
		<tr>
			<td>
				<label id="globalPropertyObjectLabel_conceptSets" for="globalPropertyObjectSelect_conceptSets">${gpConceptSets.description}</label>
			</td><td>
				<input type="text" name="jsslab.object.concept.allConcepts" id="globalPropertySelect_conceptSets" value="${gpConceptSets.propertyValue}" />
				<input type="hidden" id="globalPropertyName_conceptSets" value="${gpConceptSets.property}" />
				<input type="hidden" id="globalPropertyUuid_conceptSets" value="${gpConceptSets.propertyValue}" />
			</td><td>
				<input type="submit" class="globalPropertySubmit" id="globalPropertySubmit_conceptSets" value="<spring:message code='jsslab.setup.globalproperties.save' />" />
			</td><td>
				<span id="globalPropertyResult_conceptSets"></span>
			</td>
		</tr>
	</table>
	
	<br />
	
	<!-- ====================== --> 
	<!--   LAB SETUP SECTION    --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="jsslab.setup.databasetables.title" /></h2>
	<div>
<!-- 		<a href="#" id="liquibaseSampleData">run liquibase for sample data</a> -->
		<c:choose>
			<c:when test="${sampleDataInstalled eq 'installed'}">
				<span><spring:message code="jsslab.setup.database.sampledata.installed" /></span>
				<input id="installSampleData" type="submit" value="Install Now" disabled="disabled" />
			</c:when>
			<c:when test="${sampleDataInstalled eq 'update'}">
				<span><spring:message code="jsslab.setup.database.sampledata.updateAvailable" /></span>
				<input id="installSampleData" type="submit" value="Update Now" />
			</c:when>
			<c:otherwise>
				<span><spring:message code="jsslab.setup.database.sampledata.notinstalled" /></span>
				<input id="installSampleData" type="submit" value="Install Now" />
				<span id="installationResult"></span>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div>
		<input id="finishSetup" type="submit" value="Finish Setup" />
	</div>
	<!-- 
	<spring:hasBindErrors name="globalPropertiesModel">
		<spring:message code="fix.error"/>
	</spring:hasBindErrors>
	
	<div>Hallo!</div>
	-->
	
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>