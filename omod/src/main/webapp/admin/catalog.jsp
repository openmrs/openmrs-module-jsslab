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
	
	<script type="text/javascript">
		var jsslab = {
			"i18n" : {
				"catalog.investigations.investigationTable.empty" : "<spring:message code='jsslab.catalog.investigations.investigationTable.empty' javaScriptEscape='true' />",
			},
		};
	</script>
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/catalog.js"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/css/jsslab.css"/>
	
	<!-- ====================== --> 
	<!-- Investigations --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="jsslab.catalog.investigations.title" /></h2>


	<!-- Investigation Selection -->
	
	<div style="float:left;" id="investigationSelectionPanel">
		<span class="boxHeader">
			<spring:message code="jsslab.catalog.investigations.selection.header" />
		</span>
		<div class="box">
			<div>
				<label for="selectLaboratory"><spring:message code="jsslab.catalog.investigations.laboratoryselector"/></label>
				<select id="selectLaboratory">
					<c:forEach var="location" items="${locations}" varStatus="loop">
						<option value="${location.uuid}" <c:if test="${loop.index == 0}">selected="selected"</c:if>>${location.name}</option>
					</c:forEach>
				</select>
			</div>
			<br />
			<table id="investigationTable" class="selectionTable">
				<thead><tr>
						<th><spring:message code="jsslab.catalog.investigations.laboratorytable.section" /></th>
						<th><spring:message code="jsslab.catalog.investigations.laboratorytable.investigation" /></th>
				</tr></thead>
				<tbody>
				
				</tbody>
			</table>
			
			<br />
			
			<div>
				<input type="submit" id="btnAddInvestigation" value="<spring:message code="jsslab.catalog.investigations.addInvestigation" />" />
				<input type="submit" id="btnRemoveInvestigation" value="<spring:message code="jsslab.catalog.investigations.removeInvestigation" />" disabled="disabled" />
			</div>
		</div>
	</div>
	
	
	<!-- Investigation Add/Edit Box -->
	
	<div style="float:left;" id="investigationEditPanel">
		<span class="boxHeader">
			<spring:message code="jsslab.catalog.investigations.editing.header" />
		</span>
		<div class="box">
		
			<!-- Simple attributes -->
			
			<table>
				<tr>
					<td>
						<label for=""><spring:message code="jsslab.catalog.investigations.editing.name" />
					</td><td colspan="2">
						<openmrs_tag:conceptField formFieldName="conceptId" />
					</td><td>
						<c:if test="${status.errorMessage != ''}"><span class="error">${status.errorMessage}</span></c:if>
					</td>
				<!-- </tr><tr>
					<td>
						<label for="editInvestigationName"><spring:message code="jsslab.catalog.investigations.editing.name" />
						<openmrs_tag:conceptSetField conceptId="33333001" formFieldName="conceptId" />
					</td><td colspan="3">
						<input type="text" id="editInvestigationName" />
					</td>
				 -->
				</tr><tr>
					<td>
						<label for="editInvestigationSection"><spring:message code="jsslab.catalog.investigations.editing.section" />
					</td><td colspan="3">
						<input type="text" id="editInvestigationSection" />
					</td>
				</tr><tr>
					<td>
						<label for="editInvestigationCode"><spring:message code="jsslab.catalog.investigations.editing.code" />
					</td><td>
						<input type="text" id="editInvestigationCode" />
					</td>
					<td>
						<label for="editInvestigationTurnaround"><spring:message code="jsslab.catalog.investigations.editing.turnaround" />
					</td><td>
						<input type="text" id="editInvestigationTurnaround" />
					</td>
				</tr><tr>
					<td>
						<label for="editInvestigationCost"><spring:message code="jsslab.catalog.investigations.editing.cost" />
					</td><td>
						<input type="text" id="editInvestigationCost" />
					</td>
					<td>
						<label for="editInvestigationHoldTime"><spring:message code="jsslab.catalog.investigations.editing.holdtime" />
					</td><td>
						<input type="text" id="editInvestigationHoldTime" />
					</td>
				</tr>
			</table>
			
			<br />
			
			
			<!-- Preconditions -->
			
			<fieldset>
				<legend><spring:message code="jsslab.catalog.investigations.editing.preconditions.title" /></legend>
				
				<select size="5" id="preconditionList">
					<!-- Elements added by jQuery -->
				</select>
				
				<div>
					<input type="submit" id="buttonAddPrecondition" value="<spring:message code="jsslab.catalog.investigations.editing.preconditions.add" />" />
					<input type="submit" id="buttonEditPrecondition" value="<spring:message code="jsslab.catalog.investigations.editing.preconditions.edit" />" />
					<input type="submit" id="buttonRemovePrecondition" value="<spring:message code="jsslab.catalog.investigations.editing.preconditions.remove" />" />
				</div>
			</fieldset>
			
			
			<!-- Required Specimens -->
			
			<fieldset>
				<legend><spring:message code="jsslab.catalog.investigations.editing.specimens.title" /></legend>
			
				<table class="selectionTable" id="specimenSelectionTable">
					<thead><tr>
						<th><spring:message code="jsslab.catalog.investigations.editing.specimens.type" /></th>
						<th><spring:message code="jsslab.catalog.investigations.editing.specimens.role" /></th>
					</tr></thead>
					<tbody>
						<!-- Elements added by jQuery -->
					</tbody>
				</table>
				<div>
					<input type="submit" id="buttonAddPrecondition" value="<spring:message code="jsslab.catalog.investigations.editing.specimens.add" />" />
					<input type="submit" id="buttonRemovePrecondition" value="<spring:message code="jsslab.catalog.investigations.editing.specimens.remove" />" />
				</div>
			
			</fieldset>
			
			
			<!-- Edit box control buttons -->
			
			<div>
				<input type="submit" id="buttonSaveInvestigation" value="<spring:message code="jsslab.catalog.investigations.editing.save" />" />
				<input type="submit" id="buttonCancelInvestigation" value="<spring:message code="jsslab.catalog.investigations.editing.cancel" />" />
			</div>
			
			<!-- End of edit box -->
		</div>
		<!-- End of edit panel -->
	</div>
	
	<div style="clear:both;"></div>

	<!-- ====================== --> 
	<!-- ======== Tests ======= --> 
	<!-- ====================== --> 
	
	<h2><spring:message code="jsslab.catalog.tests.title" /></h2>
	
	
	<!-- Test Selection Panel -->
	
	<div style="float: left;" id="testSelectionPanel">
	
		<!-- Test Selection Box -->
		
		<span class="boxHeader">
			<spring:message code="jsslab.catalog.tests.selection.title" />
		</span>
		<div class="box">
			<div id="testHeading">
				<span><spring:message code="jsslab.catalog.tests.selection.heading" arguments="1, 2"/></span>
			</div>
			
			<div>
				<div style="float: left;">
					<select size="6" id="testList">
						<!-- Elements added by jQuery -->
					</select>
				</div>
				<div style="float: left;">
					<input type="submit" value="<spring:message code="jsslab.catalog.tests.selection.moveUp" />" />
					<br />
					<input type="submit" value="<spring:message code="jsslab.catalog.tests.selection.moveDown" />" />
				</div>
				<div style="clear:both;"></div>
			</div>
			
			<div>
				<input type="submit" value="<spring:message code="jsslab.catalog.tests.selection.add" />" />
				<input type="submit" value="<spring:message code="jsslab.catalog.tests.selection.remove" />" />
			</div>
		</div>
		
	</div>
	
	<!-- Test Add/Edit Panel -->
	
	<div style="float: left;" id="testEditPanel">
		
		<!-- Investigation Add/Edit Box -->
		
		<span class="boxHeader">
			<spring:message code="jsslab.catalog.tests.editing.title" />
		</span>
		<div class="box">
		
		</div>
		
	</div>

	<div style="clear:both;"></div>

<%@ include file="/WEB-INF/template/footer.jsp"%>