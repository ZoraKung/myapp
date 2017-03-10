<!--  
<%@ taglib prefix="input" uri="http://struts.apache.org/tags-html" %>
-->
<%--
  User: Carey
  Date: 15-04-29
  Description:
--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="enrolFormVo" type="org.hkicpa.mas.event.vo.master.EnrolFormVo" required="true"
              description="Enrolment Form VO" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Css Class" %>
<%@ attribute name="leftCols" type="java.lang.String" required="false" description="Left Cols" %>
<%@ attribute name="rightCols" type="java.lang.String" required="false" description="Right Cols" %>
<%@ attribute name="readonly" type="java.lang.String" required="false" description="field disabeld or read-only" %>

<c:if test="${leftCols == null}"><c:set var="leftCols" value="col-sm-4"/></c:if>
<c:if test="${rightCols == null}"><c:set var="rightCols" value="col-sm-8"/></c:if>

<c:if test="${readonly == null}"><c:set var="readonly" value=""/></c:if>

<input type=hidden name="idvId" value="${enrolFormVo.idvId}"/>
<input type=hidden name="${enrolFormVo.idvId}_enrolmentParticipantId" value="${enrolFormVo.enrolmentParticipantId}"/>
<input type=hidden name="${enrolFormVo.idvId}_fullName" value="<c:out value="${enrolFormVo.enrolmentParticipant.familyName}"/> <c:out value="${enrolFormVo.enrolmentParticipant.givenName}"/>" />

<div class="row">
    <table class="table table-white-bg table-hover table-bordered">
        <thead>
        <tr>
            <th class="center" style="width:40px;">#</th>
            <th>Event Code</th>
            <th>Description</th>
            <th>Type</th>
            <th class="text-center">Qty</th>
			<th class="text-right">Fee</th>
            <th class="text-right" style="padding-right:15px;">Total</th>
        </tr>
        </thead>

        <tbody>
        <c:set var="disabled" value=""/>
        <c:if test="${!empty(enrolFormVo.formAction) || readonly == 'readonly'}">
        	<c:set var="disabled" value="disabled"/>
        </c:if>
        <c:if test="${!empty(enrolFormVo.enrolFeeVoList)}">
        	<c:set var="rowCnt" value="0"/>
        	<c:set var="feeFieldName" value=""/>
        	<c:forEach items="${enrolFormVo.enrolFeeVoList}" var="enrolFeeVo">
				<c:set var="deleteIcon" value="true"/>
				<c:choose>
					<c:when test="${!empty(enrolFeeVo.roleBaseFeeId)}">
						<c:set var="feeFieldName" value="${enrolFormVo.idvId}_${enrolFeeVo.eventId}_${enrolFeeVo.roleBaseFeeId}"/>
					</c:when>
					<c:when test="${!empty(enrolFeeVo.optionalFeeId)}">
						<c:set var="feeFieldName" value="${enrolFormVo.idvId}_${enrolFeeVo.eventId}_${enrolFeeVo.optionalFeeId}"/>
						<c:set var="deleteIcon" value="false"/>
					</c:when>
					<c:otherwise>
						<c:set var="feeFieldName" value="${enrolFormVo.idvId}_${enrolFeeVo.eventId}"/>
					</c:otherwise>
				</c:choose>
				
    			<c:if test="${!empty(enrolFormVo.idvId)}">
					<c:set var="deleteIcon" value="false"/>
				</c:if>
				
            		<tr>
        		<c:set var="rowCnt" value="${rowCnt+1}"/>
	            <td class="center">${rowCnt}</td>
	            <td style="width:135px;">
	            	${enrolFeeVo.eventCode}
					<c:if test="${empty(enrolFormVo.formAction) && enrolFormVo.eventVoList.size() > 1 && deleteIcon == 'true'}">
					<span class="ui-pg-div"><span title="Remove Event" class="ui-icon fam-delete" onclick="removeEvent('${enrolFeeVo.eventId}');"></span></span>
					</c:if>
					<c:if test="${empty(enrolFeeVo.optionalFeeId) && enrolFormVo.enrolmentSourceCode != 'on'}">
						<span class="icon-circle-arrow-down red" id="expand_${enrolFormVo.idvId}_${enrolFeeVo.eventId}" 
							onclick="expandSpecialHandling_${enrolFormVo.idvId}('${enrolFormVo.idvId}','${enrolFeeVo.eventId}');"></span>
					</c:if>
	            	<input type=hidden name="${feeFieldName}_feeType" value="${enrolFeeVo.feeType}"/>
	            </td>
	            <td style="width:400px;">
					${enrolFeeVo.feeDesc}
	            	<input type=hidden name="${feeFieldName}_feeDesc" value="${enrolFeeVo.feeDesc}"/>
	            </td>
				<td >
				<span id="${feeFieldName}_discountDesc" 
					class="${enrolFormVo.idvId}_${enrolFeeVo.eventId}_discountDesc"
					row="${rowCnt}"
					feeFieldName="${feeFieldName}"
					>${enrolFeeVo.discountDesc}</span>
				<input type=hidden id="${feeFieldName}_discountDescOrg" value="${enrolFeeVo.discountDesc}"/>
				</td>
				<td class="text-right">
				<c:choose>
					<c:when test="${empty(enrolFormVo.formAction) && (enrolFeeVo.maxQty > 1 || enrolFeeVo.compulsoryFlag == 'N')}">
						<select style="width:60px;" id="${feeFieldName}_qty" name="${feeFieldName}_qty" ${readonly} ${disabled} row="${rowCnt}" class="${enrolFormVo.idvId}_optionalQty">
						<c:forEach var="i" begin="${enrolFeeVo.minQty}" end="${enrolFeeVo.maxQty}" step="1">
							<option value="${i}"
								<c:if test="${i ==  enrolFeeVo.qty}">
								selected
								</c:if>
							>${i}</option>
						</c:forEach>
						</select>
					</c:when>
					<c:otherwise>
						<span class="input-mini ui-spinner-input">
							<fmt:formatNumber type="number" 
            					maxFractionDigits="0" value="${enrolFeeVo.qty}" />
						</span>
						<input type="hidden" id="${feeFieldName}_qty" name="${feeFieldName}_qty" value="${enrolFeeVo.qty}"/>
					</c:otherwise>
				</c:choose>
				<input type="hidden" name="${feeFieldName}_unit" value="${enrolFeeVo.unit}"/>
				</td>
				<td class="text-right">
				<input type=hidden name="${feeFieldName}_fee" value="${enrolFeeVo.fee}"/>
				<input type=hidden id="${feeFieldName}_feeOrg" value="${enrolFeeVo.fee}"/>
				<span id="${enrolFormVo.idvId}_unitFee_${rowCnt}">
	            <c:choose>
	            	<c:when test="${enrolFeeVo.fee > 0}">
	            		<fmt:formatNumber type="number" 
            				minFractionDigits="2" maxFractionDigits="2" value="${enrolFeeVo.fee}" />
	            	</c:when>
	            	<c:otherwise>
	            		Free
	            	</c:otherwise>
	            </c:choose>				
				</span>
				</td>
				<td class="text-right" style="padding-right:15px;">
				<input type=hidden  id="${enrolFormVo.idvId}_subTotal_${rowCnt}" name="${feeFieldName}_subTotalFee" value="${enrolFeeVo.qty * enrolFeeVo.fee}"/>
				<span id="${enrolFormVo.idvId}_subTotalFee_${rowCnt}" class="${enrolFormVo.idvId}_subTotalFee">
	            <c:choose>
	            	<c:when test="${enrolFeeVo.fee > 0}">
	            		<c:set var="totalFee" value="${totalFee +enrolFeeVo.subTotalFee}"/>
	            		<fmt:formatNumber type="number" 
            				minFractionDigits="2" maxFractionDigits="2" value="${enrolFeeVo.qty * enrolFeeVo.fee}" />
	            	</c:when>
	            	<c:otherwise>
	            		Free
	            	</c:otherwise>
	            </c:choose>
				</span>
				</td>
	            </tr>
				<%-- Special Handling Fee / Support Program Section for Offline Enrolment --%>
				<c:if test="${enrolFormVo.enrolmentSourceCode != 'on' && empty(enrolFeeVo.optionalFeeId)}">
					<c:set var="specialHandlingFee" value=""/>
					<tr id="special_row_${enrolFormVo.idvId}_${enrolFeeVo.eventId}" class="hide">
					<td colspan="2">
					<input type="radio" class="ace special_handling_${enrolFormVo.idvId}" 
						name="${feeFieldName}_specialHandling"
						idvId="${enrolFormVo.idvId}"
						eventId="${enrolFeeVo.eventId}"
						feeFieldName="${feeFieldName}"
						row="${rowCnt}"
						<c:if test="${empty(enrolFeeVo.specialHandling)}">
							checked
						</c:if>
						${readonly}
						${disabled}
						value=""/>
					<span class="lbl"> No Special Handling</span><br/>
					<c:if test="${enrolFeeVo.supportProgram == '1'}">
						<input type="radio" class="ace special_handling_${enrolFormVo.idvId}"
						name="${feeFieldName}_specialHandling"
						idvId="${enrolFormVo.idvId}"
						eventId="${enrolFeeVo.eventId}"
						feeFieldName="${feeFieldName}"
						row="${rowCnt}"
						<c:if test="${enrolFeeVo.specialHandling == 'sup'}">
							checked
						</c:if>
						${readonly}
						${disabled}
						value="sup"/>
						<span class="lbl"> Support Program</span><br/>
					</c:if>
					<input type="radio" class="ace special_handling_${enrolFormVo.idvId}"
						name="${feeFieldName}_specialHandling"
						idvId="${enrolFormVo.idvId}"
						eventId="${enrolFeeVo.eventId}"
						feeFieldName="${feeFieldName}"
						row="${rowCnt}"
						<c:if test="${enrolFeeVo.specialHandling == 'spec'}">
							<c:set var="specialHandlingFee" value="${enrolFeeVo.specialHandlingFee}"/>
							checked
						</c:if>
						${readonly}
						${disabled}
						value="spec"/>
					<span class="lbl"> Special Handling</span><br/>
					</td>
					<td colspan="2">
						<table class="table-no-top-border" id="${feeFieldName}_specialHandlingDetails">
						<tr>
							<td class="text-right">Reason:</td>
							<td>
							<select ${disabled} ${readonly} 
							class="specialHandlingReasonCode_${enrolFormVo.idvId}"
							idvId="${enrolFormVo.idvId}"
							eventId="${enrolFeeVo.eventId}"
							feeFieldName="${feeFieldName}"
							id="${feeFieldName}_specialHandlingReasonCode"
							name="${feeFieldName}_specialHandlingReasonCode">
							<option value=""></option>
							<c:forEach items="${enrolFormVo.specialHandlingReasonList}" var="specialHandlingReason">
							<option value="${specialHandlingReason.value}"
								<c:if test="${specialHandlingReason.value == enrolFeeVo.specialHandlingReasonCode}">
									selected
								</c:if>
							>
							${specialHandlingReason.label}
							</option>
							</c:forEach>
							</select>
							</td>
						</tr>
						<tr>
							<td class="text-right">Specify Reason:</td>
							<td>
							<input type="text" style="width:400px;" maxlength="250" ${readonly}
										id="${feeFieldName}_specialHandlingOtherReason"
										name="${feeFieldName}_specialHandlingOtherReason"
										idvId="${enrolFormVo.idvId}"
										eventId="${enrolFeeVo.eventId}"
										feeFieldName="${feeFieldName}"
										row="${rowCnt}"
										value="<c:out value="${enrolFeeVo.specialHandlingOtherReason}"/>"
										/>
							</td>
						</tr>
						<tr>
							<td class="text-right">Fee:</td>
							<td>
							<input type="text" class="number" size="10" ${readonly}
								class="specialHandlingFee_${enrolFormVo.idvId}"
								id="${feeFieldName}_specialHandlingFee"
								name="${feeFieldName}_specialHandlingFee"
								idvId="${enrolFormVo.idvId}"
								eventId="${enrolFeeVo.eventId}"
								feeFieldName="${feeFieldName}"
								row="${rowCnt}"
								value="${specialHandlingFee}"
								onChange="updateSpecialHandlingFee_${enrolFormVo.idvId}('${feeFieldName}');"
								/>
							</td>
						</tr>
					</table>
					</td>
					<td>
					</td>
					<td>
					</td>
					<td>
					</td>
					</tr>
				</c:if>
        	</c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
<div class="row">
    <div class="col-sm-5 pull-right">
        <h4 class="pull-right">
            Total amount :
            <span class="red">HKD <span id="${enrolFormVo.idvId}_totalFee"><fmt:formatNumber type="number" 
            				minFractionDigits="2" maxFractionDigits="2" value="${totalFee}" /></span></span>
        </h4>
    </div>
</div>
<input type=hidden id="${enrolFormVo.idvId}_total" name="total" value="${totalFee}"/>

<div class="space-12"></div>

<div class="row">
    
	<div class="col-xs-12">
        <table class="table table-bordered table-white-bg table-hover table-inline-edit">
	<thead>
	<tr>
		
		<th>Event Code</th>
		
		<th>Session</th>
		
	</tr>
	</thead>
	<tbody>
	<c:if test="${!empty(enrolFormVo.eventVoList)}">
      	<c:forEach items="${enrolFormVo.eventVoList}" var="eventVo">
		<tr>
			<td>
				${eventVo.eventCode}
				<input type=hidden name="${enrolFormVo.idvId}_eventId" value="${eventVo.id}"/>
			</td>
			<td>
			<table class="table-no-top-border">
			<c:if test="${empty(enrolFormVo.formAction)}">
				<input type=hidden name="${enrolFormVo.idvId}_${eventVo.id}_noOfLessonRequired" value="${eventVo.noOfLessonRequired}"/>
				<c:choose>
					<c:when test="${empty(eventVo.noOfLessonRequired) || eventVo.noOfLessonRequired == 0}">
						<c:if test="${!empty(eventVo.lessonVoList)}">
							<c:set var="rowCnt" value="0"/>
							<c:forEach items="${eventVo.lessonVoList}" var="lessonVo">
								<c:set var="rowCnt" value="${rowCnt+1}"/>
								<tr>
								<td>${rowCnt}.</td>
								<td>
									<c:if test="${!empty(lessonVo.lessonName)}">
										(${lessonVo.lessonName})
									</c:if>
									${lessonVo.displayDate}
									${lessonVo.enrolWarning}
									<input type=hidden name="${enrolFormVo.idvId}_${eventVo.id}_lessonId" value="${lessonVo.id}"/>
								</td>
								</tr>
							</c:forEach>
						</c:if>
					</c:when>
					<c:otherwise>
						<c:forEach var="lessonRowCnt" begin="1" end="${eventVo.noOfLessonRequired}" step="1">
							<c:set var="selectedRowCnt" value="0"/>
							<c:set var="selectedLessonId" value=""/>
							<c:forEach items="${eventVo.selectedLessonVoList}" var="selectedLessonVo">
								<c:set var="selectedRowCnt" value="${selectedRowCnt+1}"/>
								<c:if test="${selectedRowCnt == lessonRowCnt}">
									<c:set var="selectedLessonId" value="${selectedLessonVo.id}"/>
								</c:if>
							</c:forEach>

							<tr>
							<td>${lessonRowCnt}.</td>
							<td>
							<select name="${enrolFormVo.idvId}_${eventVo.id}_lessonId" style="width:840px" ${disabled}>
							<c:if test="${!empty(eventVo.lessonVoList)}">
								<c:forEach items="${eventVo.lessonVoList}" var="lessonVo">
									<option value="${lessonVo.id}"
											<c:if test="${lessonVo.id == selectedLessonId}">
												selected
											</c:if>
									>
									<c:if test="${!empty(lessonVo.lessonName)}">
										(${lessonVo.lessonName})
									</c:if>
									${lessonVo.displayDate}
									<c:if test="${lessonVo.cpdHours > 0}">
										(CPD: <fmt:formatNumber type="number" 
											minFractionDigits="1" maxFractionDigits="1" value="${lessonVo.cpdHours}" />)
									</c:if>
									${lessonVo.enrolWarning}
									</option>
								</c:forEach>
							</c:if>
							</select></td></tr>
						</c:forEach>
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:if test="${!empty(enrolFormVo.formAction)}">
				<c:if test="${!empty(eventVo.selectedLessonVoList)}">
					<c:set var="rowCnt" value="0"/>
					<c:forEach items="${eventVo.selectedLessonVoList}" var="lessonVo">
						<c:set var="rowCnt" value="${rowCnt+1}"/>
						<tr>
						<td>${rowCnt}.</td>
						<td>
							<c:if test="${!empty(lessonVo.lessonName)}">
								(${lessonVo.lessonName})
							</c:if>
							${lessonVo.displayDate}
							<c:if test="${lessonVo.cpdHours > 0}">
								(CPD: <fmt:formatNumber type="number" 
									minFractionDigits="1" maxFractionDigits="1" value="${lessonVo.cpdHours}" />)
							</c:if>
							<input type=hidden name="${enrolFormVo.idvId}_${eventVo.id}_lessonId" value="${lessonVo.id}"/>
						</td>
						</tr>
					</c:forEach>
				</c:if>
			</c:if>
			</table>
			</td>
		</tr>
		</c:forEach>
	</c:if>
	</tbody>
	</table>
    </div>
    
</div>

<c:set var="printAttrHeader" value="true"/>
<c:set var="printAttrFooter" value="false"/>
<c:if test="${!empty(enrolFormVo.eventVoList)}">
   	<c:forEach items="${enrolFormVo.eventVoList}" var="eventVo">
		<c:if test="${!empty(eventVo.filledEventAttributeVoList)}">
			<c:if test="${printAttrHeader == 'true'}">
				<c:set var="printAttrHeader" value="false"/>
				<c:set var="printAttrFooter" value="true"/>
<div class="space-12"></div>

<div class="row">
	<div class="col-xs-7">
    <table class="table table-bordered table-white-bg table-hover table-inline-edit">
	<thead>
	<tr>
		<th>Event Code</th>
		<th>Additional Information</th>
	</tr>
	</thead>
	<tbody>
			</c:if> <% // Print Attr Header %>
		<tr>
			<td>
				${eventVo.eventCode}
			</td>
			<td>
			<table class="table-no-top-border">
				<c:set var="rowCnt" value="0"/>
				<c:forEach items="${eventVo.filledEventAttributeVoList}" var="filledEventAttributeVo">
					<c:set var="rowCnt" value="${rowCnt+1}"/>
					<tr>
						<td>
							<b>${filledEventAttributeVo.attrName}</b><br/>
							<c:if test="${empty(enrolFormVo.formAction)}">
								<c:set var="requiredClass" value=""/>
								<c:if test="${filledEventAttributeVo.mandatoryFlag == '1'}">
									<c:set var="requiredClass" value="required"/>
								</c:if>
								<c:set var="numberClass" value=""/>
								<c:if test="${filledEventAttributeVo.attrType == 'num'}">
									<c:set var="numberClass" value="number"/>
								</c:if>
							
								<input maxlength="250" name="${enrolFormVo.idvId}_${eventVo.id}_${filledEventAttributeVo.id}" 
								type="text" style="width:480px;" class="${requiredClass} ${numberClass}" autocomplete="off"
							   	role="spinbutton" aria-valuenow="0" 
								<c:if test="${!empty(filledEventAttributeVo.filledAttrValue)}">
									value="<c:out value="${filledEventAttributeVo.filledAttrValue}"/>"
								</c:if>
								<c:if test="${empty(filledEventAttributeVo.filledAttrValue)}">
									placeholder="<c:out value="${filledEventAttributeVo.attrValue}"/>"
								</c:if>
							    ${readonly}/>
							</c:if>
							<c:if test="${!empty(enrolFormVo.formAction)}">
								<c:out value="${filledEventAttributeVo.filledAttrValue}"/>
								<input type=hidden name="${enrolFormVo.idvId}_${eventVo.id}_${filledEventAttributeVo.id}" value="<c:out value="${filledEventAttributeVo.filledAttrValue}"/>"/>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			</td>
			</tr>
		</c:if> <% // !empty(eventVo.filledEventAttributeVoList) %>
	</c:forEach> <% // forEach enrolFormVo.eventVoList %>
</c:if> <% // !empty(enrolFormVo.eventVoList) %>
<c:if test="${printAttrFooter == 'true'}">
	</tbody>
	</table>
	</div>
</div>
</c:if>

<script type="text/javascript">


	
$(function () {
    //initialize
	initSpecialHandling_${enrolFormVo.idvId}();
	<c:if test="${!empty(enrolFormVo.idvId)}">
	$("#${enrolFormVo.idvId}_header_totalFee").html($("#${enrolFormVo.idvId}_totalFee").html());
	</c:if>
	reCalTotal_${enrolFormVo.idvId}();
});

	$(".${enrolFormVo.idvId}_optionalQty").on('change', function() {
		var $this = $(this);
		var row = $this.attr("row");
		reCalRow_${enrolFormVo.idvId}(row, $this.val());
	});

	function reCalRow_${enrolFormVo.idvId}(row, inputQty) {
		var amt = $("#${enrolFormVo.idvId}_unitFee_"+row).html();
		amt = amt.replace(/,/g, "");
		amt = parseFloat(amt);
		if (isNaN(amt)) {
			amt = 0;
		}
		var qty = parseInt(inputQty);
		var qtyFloat = parseFloat(inputQty);
		if (isNaN(qty) || qty != qtyFloat) {
			qty = 0;
			$this.val(qty);
		}
		var new_amt = qty * amt;
		
		$("#${enrolFormVo.idvId}_subTotal_"+row).val(new_amt);
		$("#${enrolFormVo.idvId}_subTotalFee_"+row).html(formatNumber_${enrolFormVo.idvId}(new_amt));
		
		reCalTotal_${enrolFormVo.idvId}();
	}
	
	function reCalTotal_${enrolFormVo.idvId}() {
		var total = 0;
		$( ".${enrolFormVo.idvId}_subTotalFee" ).each(function() {
			var $this = $(this);
			var amt = $this.html();
			amt = amt.replace(/,/g, "");
			amt = parseFloat(amt);
			if (!isNaN(amt)) {
				total += amt;
			}
		});
		
		$("#${enrolFormVo.idvId}_total").val(total);
		$("#${enrolFormVo.idvId}_totalFee").html(formatNumber_${enrolFormVo.idvId}(total));
		$("#${enrolFormVo.idvId}_header_totalFee").html($("#${enrolFormVo.idvId}_totalFee").html());
		<c:if test="${!empty(enrolFormVo.idvId)}">
		reCalGrandTotal_${enrolFormVo.idvId}();
		</c:if>
	}
	
	function expandSpecialHandling_${enrolFormVo.idvId}(idvId, eventId) {
		var icon_id = "expand_"+idvId+"_"+eventId;
		var row_id = "special_row_"+idvId+"_"+eventId;
		if ($("#"+icon_id).hasClass("icon-circle-arrow-down")) {
			$("#"+icon_id).removeClass("icon-circle-arrow-down");
			$("#"+icon_id).addClass("icon-circle-arrow-up");
			$("#"+row_id).removeClass("hide");
		} else {
			$("#"+icon_id).removeClass("icon-circle-arrow-up");
			$("#"+icon_id).addClass("icon-circle-arrow-down");
			$("#"+row_id).addClass("hide");
		}
		
	}
	
	function formatNumber_${enrolFormVo.idvId} (num) {
		return num.toFixed(2).toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, "$1,");
	}
	
	<c:if test="${!empty(enrolFormVo.idvId)}">
	function reCalGrandTotal_${enrolFormVo.idvId}() {
		var grandTotal = 0;
		$( ".headerTotalFee" ).each(function() {
			var $this = $(this);
			var amt = $this.html();
			amt = amt.replace(/,/g, "");
			amt = parseFloat(amt);
			if (!isNaN(amt)) {
				grandTotal += amt;
			}
		});
		
		$("#grandTotalFee").html(formatNumber_${enrolFormVo.idvId}(grandTotal));
	}
	</c:if>
	
	function initSpecialHandling_${enrolFormVo.idvId}() {
	<c:if test="${enrolFormVo.enrolmentSourceCode != 'on'}">
		$( ".special_handling_${enrolFormVo.idvId}" ).each(function() {
			var $this = $(this);
			var idvId = $this.attr("idvId");
			var eventId = $this.attr("eventId");
			var feeFieldName = $this.attr("feeFieldName");
			var value = $this.attr("value");
			if ($this.is(":checked")) {
				if (value == 'spec') {
					$("#"+feeFieldName+"_specialHandlingDetails").removeClass("hide");
					enableSpecialHandlingReasonCode_${enrolFormVo.idvId}(feeFieldName, true);
					enableSpecialHandlingFee_${enrolFormVo.idvId}(feeFieldName, true);
				} else if (value == 'sup') {
					$("#"+feeFieldName+"_specialHandlingDetails").addClass("hide");
					enableSpecialHandlingReasonCode_${enrolFormVo.idvId}(feeFieldName, false);
					updateSupportProgram_${enrolFormVo.idvId}(feeFieldName);
				} else {
					$("#"+feeFieldName+"_specialHandlingDetails").addClass("hide");
					enableSpecialHandlingReasonCode_${enrolFormVo.idvId}(feeFieldName, false);
					enableSpecialHandlingFee_${enrolFormVo.idvId}(feeFieldName, false);
				}
			}
		});
	</c:if>
	}
	
	function enableSpecialHandlingOtherReason_${enrolFormVo.idvId}(feeFieldName, enable) {
		var cntl = $("#"+feeFieldName+"_specialHandlingOtherReason");
		if (enable) {
			cntl.prop("disabled", false);
			cntl.addClass("required");
		} else {
			cntl.prop("disabled", true);
			cntl.val("");
			cntl.removeClass("required");
		}
	}
	
	function enableSpecialHandlingReasonCode_${enrolFormVo.idvId}(feeFieldName, enable) {
		<c:if test="${readonly != 'readonly'}" >
		var cntl = $("#"+feeFieldName+"_specialHandlingReasonCode");
		if (enable) {
			cntl.prop("disabled", false);
			cntl.addClass("required");
		} else {
			cntl.prop("disabled", true);
			cntl.removeClass("required");
		}
		if (cntl.find('option:selected').index() == cntl.find('option').length-1) {
			enableSpecialHandlingOtherReason_${enrolFormVo.idvId}(feeFieldName, true);
		} else {
			enableSpecialHandlingOtherReason_${enrolFormVo.idvId}(feeFieldName, false);
		}
		</c:if>
	}

	function enableSpecialHandlingFee_${enrolFormVo.idvId}(feeFieldName, enable) {
		var cntl = $("#"+feeFieldName+"_specialHandlingFee");
		if (enable) {
			cntl.prop("disabled", false);
			cntl.addClass("required");
			updateSpecialHandlingFee_${enrolFormVo.idvId}(feeFieldName);
		} else {
			cntl.prop("disabled", true);
			cntl.removeClass("required");
			resetFee_${enrolFormVo.idvId}(feeFieldName);
		}
	}
	
	$('.special_handling_${enrolFormVo.idvId}').unbind("change");
	$('.special_handling_${enrolFormVo.idvId}').change(function(){
		var $this = $(this);
		var idvId = $this.attr("idvId");
		var eventId = $this.attr("eventId");
		var feeFieldName = $this.attr("feeFieldName");
		var value = $this.attr("value");
		if (value == 'spec' && $this.is(":checked")) {
			$("#"+feeFieldName+"_specialHandlingDetails").removeClass("hide");
			enableSpecialHandlingReasonCode_${enrolFormVo.idvId}(feeFieldName, true);
			enableSpecialHandlingFee_${enrolFormVo.idvId}(feeFieldName, true);
		} else if (value == 'sup' && $this.is(":checked")) {
			$("#"+feeFieldName+"_specialHandlingDetails").addClass("hide");
			enableSpecialHandlingReasonCode_${enrolFormVo.idvId}(feeFieldName, false);
			updateSupportProgram_${enrolFormVo.idvId}(feeFieldName);
		} else {
			$("#"+feeFieldName+"_specialHandlingDetails").addClass("hide");
			enableSpecialHandlingReasonCode_${enrolFormVo.idvId}(feeFieldName, false);
			enableSpecialHandlingFee_${enrolFormVo.idvId}(feeFieldName, false);
		}
	});
	
	$('.specialHandlingReasonCode_${enrolFormVo.idvId}').unbind("change");
	$('.specialHandlingReasonCode_${enrolFormVo.idvId}').change(function(){
		var $this = $(this);
		var idvId = $this.attr("idvId");
		var eventId = $this.attr("eventId");
		var feeFieldName = $this.attr("feeFieldName");
		if ($this.find('option:selected').index() == $this.find('option').length-1) {
			enableSpecialHandlingOtherReason_${enrolFormVo.idvId}(feeFieldName, true);
		} else {
			enableSpecialHandlingOtherReason_${enrolFormVo.idvId}(feeFieldName, false);
		}
	});

	function updateSupportProgram_${enrolFormVo.idvId}(feeFieldName) {
		var obj = $("#"+feeFieldName+"_specialHandlingFee");
		var idvId = obj.attr("idvId");
		var eventId = obj.attr("eventId");
		$( "."+idvId+"_"+eventId+"_discountDesc" ).each(function() {
			var $this = $(this);
			var feeFieldName = $this.attr("feeFieldName");
			var row = $this.attr("row");
			var qty = $("#"+feeFieldName+"_qty").val();
			var value = 0;
			$("#"+feeFieldName+"_discountDesc").html("Support Program");
			$("#"+feeFieldName+"_fee").val(value);
			$("#"+idvId+"_unitFee_"+row).html(formatNumber_${enrolFormVo.idvId}(parseFloat(value)));
			$this.val(formatNumber_${enrolFormVo.idvId}(parseFloat(value)));
			reCalRow_${enrolFormVo.idvId}(row, qty);
		});

	}
	
	function updateSpecialHandlingFee_${enrolFormVo.idvId}(feeFieldName) {
		resetFee_${enrolFormVo.idvId}(feeFieldName);
		$("#"+feeFieldName+"_discountDesc").html("Special Handling");
		var obj = $("#"+feeFieldName+"_specialHandlingFee");
		var idvId = obj.attr("idvId");
		var eventId = obj.attr("eventId");
		var feeFieldName = obj.attr("feeFieldName");
		var row = obj.attr("row");
		var value = obj.val();
		if (value != "") {
			if (isNaN(value) || parseFloat(value) < 0) {
				value = 0;
			}
			$("#"+feeFieldName+"_fee").val(value);
			$("#"+idvId+"_unitFee_"+row).html(formatNumber_${enrolFormVo.idvId}(parseFloat(value)));
			obj.val(formatNumber_${enrolFormVo.idvId}(parseFloat(value)));
			reCalRow_${enrolFormVo.idvId}(row, 1);
		}
	}
	
	function resetFee_${enrolFormVo.idvId}(feeFieldName) {
		var obj = $("#"+feeFieldName+"_specialHandlingFee");
		var idvId = obj.attr("idvId");
		var eventId = obj.attr("eventId");
		$( "."+idvId+"_"+eventId+"_discountDesc" ).each(function() {
			var $this = $(this);
			var feeFieldName = $this.attr("feeFieldName");
			var row = $this.attr("row");
			var value = $("#"+feeFieldName+"_feeOrg").val();
			$this.val(formatNumber_${enrolFormVo.idvId}(parseFloat(value)));
			$("#"+feeFieldName+"_discountDesc").html($("#"+feeFieldName+"_discountDescOrg").val());
			$("#"+feeFieldName+"_fee").val(value);
			if (value > 0) {
				$("#"+idvId+"_unitFee_"+row).html(formatNumber_${enrolFormVo.idvId}(parseFloat(value)));
			} else {
				$("#"+idvId+"_unitFee_"+row).html("Free");
			}
			var qty = $("#"+feeFieldName+"_qty").val();
			reCalRow_${enrolFormVo.idvId}(row, qty);
		});
	}
</script>
