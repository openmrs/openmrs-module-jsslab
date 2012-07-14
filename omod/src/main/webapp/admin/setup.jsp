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
	
	<openmrs:htmlInclude file="/scripts/jquery/jquery.min.js" />
	<openmrs:htmlInclude file="/moduleResources/jsslab/css/jsslab.css"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/setup.js"/>
	
	<!-- ====================== --> 
	<!-- LAB INSTRUMENT SECTION --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="jsslab.setup.globalproperties.title" /></h2>
	<div>
	
	</div>	
	
	<h2><spring:message code="jsslab.setup.databasetables.title" /></h2>
	<div>
<!-- 		<a href="#" id="liquibaseSampleData">run liquibase for sample data</a> -->
		<c:choose>
			<c:when test="false">
				<span><spring:message code="jsslab.setup.database.sampledata.installed" /></span>
				<input type="submit" value="Install Now" disabled="disabled" />
			</c:when>
			<c:otherwise>
				<span><spring:message code="jsslab.setup.database.sampledata.notinstalled" /></span>
				<form action="setup/installSampleData.htm" method="post">
					<input type="submit" value="Install Now" />
				</form>
			</c:otherwise>
		</c:choose>
	</div>
	
	<div>
		<form action="setup/finishSetup.htm" method="post">
			<input type="submit" value="Finish Setup" />
		</form>
	</div>
	<!-- 
	<spring:hasBindErrors name="globalPropertiesModel">
		<spring:message code="fix.error"/>
	</spring:hasBindErrors>
	
	<div>Hallo!</div>
	-->
	
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>