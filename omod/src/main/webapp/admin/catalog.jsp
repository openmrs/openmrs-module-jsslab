<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<div id="jsslabCatalog" 
	xmlns:c="http://java.sun.com/jsp/jstl/core"
	xmlns:fn="http://java.sun.com/jsp/jstl/functions"
	xmlns:jsp="http://java.sun.com/JSP/Page"
	xmlns:spring="http://www.springframework.org/tags"
	xmlns:openmrs="http://www.openmrs.org/taglib/openmrs"
	version="2.0">
	
	<%@ include file="localHeader.jsp" %>
	
	<openmrs:require privilege="Manage Global Properties" otherwise="/login.htm" redirect="/module/jsslab/admin/catalog.form" />
	<openmrs:requireConfiguration propertyList="jsslab.setup" configurationPage="/module/jsslab/admin/setup.form?targetView=module/jsslab/admin/catalog" />
	
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/jsslab.js"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/js/catalog.js"/>
	<openmrs:htmlInclude file="/moduleResources/jsslab/css/jsslab.css"/>
	
	<script type="text/javascript">
		jsslab.i18n.catalog = {
			"investigations.investigationTable.empty" 				: "<spring:message code='jsslab.catalog.investigations.investigationTable.empty' javaScriptEscape='true' />",
			"investigations.editing.preconditions.dialog.title"		: "<spring:message code='jsslab.catalog.investigations.editing.preconditions.dialog.title' />",
			"investigations.editing.preconditions.dialog.remove"	: "<spring:message code='jsslab.catalog.investigations.editing.preconditions.dialog.remove' />",
			"tests.selection.heading"								: "<spring:message code='jsslab.catalog.tests.selection.heading' />",
			"tests.editing.resultFormat" : {
				"CONCEPT"											: "<spring:message code='jsslab.catalog.tests.editing.resultFormat.concept' />",
				"TITER"												: "<spring:message code='jsslab.catalog.tests.editing.resultFormat.titer' />",
				"NUMERIC"											: "<spring:message code='jsslab.catalog.tests.editing.resultFormat.numeric' />",
				"TEXT"												: "<spring:message code='jsslab.catalog.tests.editing.resultFormat.text' />",
				"DURATION"											: "<spring:message code='jsslab.catalog.tests.editing.resultFormat.duration' />",
			},
			"tests.editing.resultNumeric.noTestRanges" 				: "<spring:message code='jsslab.catalog.tests.editing.resultFormat.numeric.noTestRanges' />",
			"tests.editing.resultNumeric.testRanges.dialog.title"	: "<spring:message code='jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.title' />",
		};
	</script>
	
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
						<label for=""><spring:message code="jsslab.catalog.investigations.editing.name" /></label>
					</td><td colspan="2">
						<input type="text" class="editConcept" id="editInvestigationName" />
						<input type="hidden" id="editInvestigationNameVal" />
					</td><td>
						<!-- TODO create concept button -->
					</td>
				</tr><tr>
					<td>
						<label for="editInvestigationSection"><spring:message code="jsslab.catalog.investigations.editing.section" /></label>
					</td><td colspan="2">
						<input type="text" class="editConcept" id="editInvestigationSection" />
						<input type="hidden" id="editInvestigationSectionVal" />
					</td><td>
						<!-- TODO create concept button -->
					</td>
				</tr><tr>
					<td>
						<label for="editInvestigationCode"><spring:message code="jsslab.catalog.investigations.editing.code" /></label>
					</td><td>
						<input type="text" id="editInvestigationCode" />
					</td>
					<td>
						<label for="editInvestigationTurnaround"><spring:message code="jsslab.catalog.investigations.editing.turnaround" /></label>
					</td><td>
						<input type="text" id="editInvestigationTurnaround" />
					</td>
				</tr><tr>
					<td>
						<label for="editInvestigationCost"><spring:message code="jsslab.catalog.investigations.editing.cost" /></label>
					</td><td>
						<input type="text" id="editInvestigationCost" />
					</td>
					<td>
						<label for="editInvestigationHoldTime"><spring:message code="jsslab.catalog.investigations.editing.holdtime" /></label>
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
					<input type="submit" id="buttonEditPrecondition" disabled="disabled" value="<spring:message code="jsslab.catalog.investigations.editing.preconditions.edit" />" />
					<input type="submit" id="buttonRemovePrecondition" disabled="disabled" value="<spring:message code="jsslab.catalog.investigations.editing.preconditions.remove" />" />
				</div>
			</fieldset>
			
			<div id="editPreconditionDialog">
				<div>
					<label for="editPreconditionQuestion"><spring:message code="jsslab.catalog.investigations.editing.preconditions.dialog.question" /></label>
					<input type="text" id="editPreconditionQuestion" disabled="disabled" />
					<input type="hidden" id="editPreconditionQuestionVal" />
				</div>
				
				<fieldset>
					<legend><spring:message code="jsslab.catalog.investigations.editing.preconditions.dialog.answers" /></legend>
					
					<table id="tablePreconditionAnswers">
						<thead><tr>
							<td><spring:message code="jsslab.catalog.investigations.editing.preconditions.dialog.answer.correct"/></td>
							<td><spring:message code="jsslab.catalog.investigations.editing.preconditions.dialog.answer.name"/></td>
							<td><spring:message code="jsslab.catalog.investigations.editing.preconditions.dialog.answer.remove"/></td>
						</tr></thead>
						<tbody>
							<!-- precondition answers go here -->	
						</tbody>
					</table>
					
					<input type="submit" id="buttonAddPreconditionAnswer" value="<spring:message code="jsslab.catalog.investigations.editing.preconditions.dialog.answer.add" />" />
				</fieldset>
				
				<br />
				
				<div>
					<input type="submit" id="buttonSavePrecondition" value="<spring:message code="jsslab.catalog.investigations.editing.preconditions.dialog.save" />" />
					<input type="submit" id="buttonCancelPrecondition" value="<spring:message code="jsslab.catalog.investigations.editing.preconditions.dialog.cancel" />" />
				</div>
			</div>
			
			
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
					<input type="submit" id="buttonAddSpecimen" value="<spring:message code="jsslab.catalog.investigations.editing.specimens.add" />" />
					<input type="submit" id="buttonRemoveSpecimen" value="<spring:message code="jsslab.catalog.investigations.editing.specimens.remove" />" />
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
					<input type="submit" id="btnMoveUpTest" value="<spring:message code="jsslab.catalog.tests.selection.moveUp" />" />
					<br />
					<input type="submit" id="btnMoveDownTest" value="<spring:message code="jsslab.catalog.tests.selection.moveDown" />" />
				</div>
				<div style="clear:both;"></div>
			</div>
			
			<div>
				<input type="submit" id="btnAddTest" value="<spring:message code="jsslab.catalog.tests.selection.add" />" />
				<input type="submit" id="btnRemoveTest" value="<spring:message code="jsslab.catalog.tests.selection.remove" />" />
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
			
			<!-- Simple attributes -->
			
			<table>
				<tr>
					<td>
						<label><spring:message code="jsslab.catalog.tests.editing.name" /></label>
					</td><td>
						<input type="text" class="editConcept" id="editTestName" />
						<input type="hidden" id="editTestNameVal" />
					</td><td>
						<!-- TODO create concept button -->
					</td>
				</tr><tr>
					<td>
						<label><spring:message code="jsslab.catalog.tests.editing.resultFormat" /></label>
					</td><td colspan="2">
						<input type="radio" class="radioTestResultFormat" name="testResultFormat" id="radioTestResultFormat_concept" value="CONCEPT" selected="selected" />
						<label for="radioTestResultFormat_concept"><spring:message code="jsslab.catalog.tests.editing.resultFormat.concept" /></label>
						<input type="radio" class="radioTestResultFormat" name="testResultFormat" id="radioTestResultFormat_duration" value="DURATION" />
						<label for="radioTestResultFormat_duration"><spring:message code="jsslab.catalog.tests.editing.resultFormat.duration" /></label>
						<input type="radio" class="radioTestResultFormat" name="testResultFormat" id="radioTestResultFormat_titer" value="TITER" />
						<label for="radioTestResultFormat_titer"><spring:message code="jsslab.catalog.tests.editing.resultFormat.titer" /></label>
						<input type="radio" class="radioTestResultFormat" name="testResultFormat" id="radioTestResultFormat_numeric" value="NUMERIC" />
						<label for="radioTestResultFormat_numeric"><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric" /></label>
						<input type="radio" class="radioTestResultFormat" name="testResultFormat" id="radioTestResultFormat_text" value="TEXT" />
						<label for="radioTestResultFormat_text"><spring:message code="jsslab.catalog.tests.editing.resultFormat.text" /></label>
					</td>
				</tr>
			</table>
			
			<br />
			
			<!-- result format -->
			
			<fieldset id="fieldsetResultFormat">
				<legend id="testResultFormatHeading"><!-- Update based on selected result format --></legend>
				
				
				<!-- Dynamically show panel for selected result format here -->
				
				<div id="testResultFormatPanel">
					
					
					<!-- result format panel for result format "CONCEPT" -->
					
					<div class="testResultFormatPanel" id="testResultFormat_CONCEPT">
						
						<table id="testRangeAnswers">
						</table>
						
						<input type="submit" id="btnAddTestRangeAnswer" value="<spring:message code="jsslab.catalog.tests.editing.resultFormat.concept.addAnswer" />" />
						<input type="submit" id="btnRemoveTestRangeAnswer" value="<spring:message code="jsslab.catalog.tests.editing.resultFormat.concept.removeAnswer" />" />
					</div>
					
					<!-- result format panel for result format "NUMERIC" -->
					
					<div class="testResultFormatPanel" id="testResultFormat_NUMERIC">
						<table>
							<tr>
								<td><label for="selectResultUnit" ><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.unit" /></label></td>
								<td>
									<input type="text" id="selectNumericResultUnit" value="" />
								</td>
							</tr><tr>
								<td><label for="selectResultUnit" ><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.showDecimals" /></label></td>
								<td><input type="checkbox" id="checkboxNumericResultShowDecimals"/></td>
							</tr>
						</table>
						
						<br />
						
						<!-- test ranges -->
				
						<fieldset id="fieldsetTestRange">
							<legend><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.title" /></legend>
						
						<div id="testRangeSelectionPanel">
							<div style="float: left;">
								<table id="testRangeSelectionTable" class="selectionTable">
									<thead><tr>
										<td><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.sex" /></td>
										<td><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.minAge" /></td>
										<td><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.maxAge" /></td>
										<td><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.logicConditions" /></td>
									</tr></thead>
									<tbody>
										<!-- Add result entries here -->							
									</tbody>
								</table>
							</div>
							<div style="float: left;">
								<input type="submit" id="btnMoveUpTestRange" value="<spring:message code="jsslab.catalog.tests.selection.moveUp" />" />
								<br />
								<input type="submit" id="btnMoveDownTestRange" value="<spring:message code="jsslab.catalog.tests.selection.moveDown" />" />
							</div>
							<div style="clear:both;"></div>
						</div>
						<div id="testRangeButtons" >
							<input type="submit" id="btnAddTestRange" value="<spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.addRange" />" />
							<input type="submit" id="btnEditTestRange" value="<spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.editRange" />" />
							<input type="submit" id="btnRemoveTestRange" value="<spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.removeRange" />" />
						</div>
						</fieldset>
						
						<div id="editTestRangeDialog" style="display: none;">
							<table>
								<tr>
									<td><label for="textTestRangeSex" ><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.sex" /></label></td>
									<td><input type="text" id="textTestRangeSex"/></td>
									<td><label for="selectTestRangeLogicRule" ><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.logicRule" /></label></td>
									<td>
										<select id="selectTestRangeLogicRule">
											<option value="null"> </option>
											<c:forEach var="logicRule" items="${rules}">
												<option value="${logicRule.uuid}">${logicRule.name}</option>
											</c:forEach>
											<!-- add available logic rules here -->
										</select>
										<br />
										<span id=spanTestRangeLogicRuleDescription></span>
									</td>
								</tr><tr>
									<td><label for="textTestRangeMinAge" ><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.minAge" /></label></td>
									<td><input type="text" id="textTestRangeMinAge"/></td>
									<td><label for="textTestRangeMaxAge" ><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.maxAge" /></label></td>
									<td><input type="text" id="textTestRangeMaxAge"/></td>
								</tr><tr>
									<td><label for="textTestRangeNormalLow" ><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.normalLow" /></label></td>
									<td><input type="text" id="textTestRangeNormalLow"/></td>
									<td><label for="textTestRangeNormalHigh" ><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.normalHigh" /></label></td>
									<td><input type="text" id="textTestRangeNormalHigh"/></td>
								</tr><tr>
									<td><label for="textTestRangeCriticalLow" ><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.criticalLow" /></label></td>
									<td><input type="text" id="textTestRangeCriticalLow"/></td>
									<td><label for="textTestRangeCriticalHigh" ><spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.criticalHigh" /></label></td>
									<td><input type="text" id="textTestRangeCriticalHigh"/></td>
								</tr>
							</table>
							
							<input type="submit" id="btnSaveTestRange" value="<spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.save" />" />
							<input type="submit" id="btnCancelTestRange" value="<spring:message code="jsslab.catalog.tests.editing.resultFormat.numeric.testRanges.dialog.cancel" />" />
						</div>
					</div>
					
					<!-- result format panel for result format "TITER" -->
					
					<div class="testResultFormatPanel" id="testResultFormat_TITER">
					
					</div>

					<!-- result format panel for result format "DURATION" -->
					
					<div class="testResultFormatPanel" id="testResultFormat_DURATION">
					
					</div>
					
				</div>
			</fieldset>
			
			<br />
			
			<input type="submit" id="btnSaveTest" value="<spring:message code="jsslab.catalog.tests.editing.save" />" />
			<input type="submit" id="btnCancelTest" value="<spring:message code="jsslab.catalog.tests.editing.cancel" />" />
		</div>
		
	</div>

	<div style="clear:both;"></div>

<%@ include file="/WEB-INF/template/footer.jsp"%>