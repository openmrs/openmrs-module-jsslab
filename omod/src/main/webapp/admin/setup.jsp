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
	
	<script type="text/javascript">
		var jsslab = {
			"sampleDataInstalled" : ${sampleDataInstalled}
		};
	</script>
	<openmrs:htmlInclude file="/moduleResources/jsslab/css/jsslab.css"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/setup.js"/>
	
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
		<c:forEach var="gp" items="${globalPropertiesObject}" varStatus="loop">
			<tr>
				<td>
					<label for="globalPropertyObjectSelect_${loop.index}">${gp.description}</label>
				</td><td>
					<input type="text" name="${gp.property}" id="globalPropertyObjectSelect_${loop.index}" value="${gp.propertyValue}" />
				</td><td>
					<input type="submit" class="globalPropertyObjectSubmit" id="globalPropertyObjectSubmit_${loop.index}" value="<spring:message code='jsslab.setup.globalproperties.save' />" />
				</td><td>
					<span id="globalPropertyObjectResult_${loop.index}"></span>
				</td>
			</tr>
		</c:forEach>
	</table>
	
	<br />
	
	<!-- ====================== --> 
	<!--   LAB SETUP SECTION    --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="jsslab.setup.databasetables.title" /></h2>
	<div>
<!-- 		<a href="#" id="liquibaseSampleData">run liquibase for sample data</a> -->
		<c:choose>
			<c:when test="${sampleDataInstalled}">
				<span><spring:message code="jsslab.setup.database.sampledata.installed" /></span>
				<input type="submit" value="Install Now" disabled="disabled" />
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