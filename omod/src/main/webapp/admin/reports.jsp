<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="Manage Global Properties" otherwise="/login.htm" redirect="/module/jsslab/admin/settings.htm" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<%@ include file="localHeader.jsp" %>

<style type="text/css">
</style>

<h2><spring:message code="jsslab.title" /></h2>

<spring:hasBindErrors name="globalPropertiesModel">
	<spring:message code="fix.error"/>
</spring:hasBindErrors>

<div>Hallo!</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>