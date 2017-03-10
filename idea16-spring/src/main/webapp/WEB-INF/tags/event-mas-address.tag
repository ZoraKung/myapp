<!--  
<%@ taglib prefix="input" uri="http://struts.apache.org/tags-html" %>
-->
<%--
  User: Carey
  Date: 15-06-29
  Description:
--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="Address Id Prefix" %>
<%@ attribute name="addressName" type="java.lang.String" required="true" description="Address of Name" %>
<%@ attribute name="addressValue" type="org.hkicpa.mas.common.vo.AddressVo" required="false"
              description="Address of Value" %>
<%@ attribute name="isEnglish" type="java.lang.String" required="false" description="Is English Address" %>
<%@ attribute name="local" type="java.lang.String" required="false" description="Location Type" %>
<%@ attribute name="addressType" type="java.lang.String" required="false" description="Address Type" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Css Class" %>
<%@ attribute name="leftCols" type="java.lang.String" required="false" description="Left Cols" %>
<%@ attribute name="rightCols" type="java.lang.String" required="false" description="Right Cols" %>
<%--15-04-27 ly--%>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="field disabeld or read-only" %>

<input type="hidden" name="${addressName}.id" value="${addressValue.id}"/>

<c:if test="${leftCols == null}"><c:set var="leftCols" value="col-sm-4"/></c:if>
<c:if test="${rightCols == null}"><c:set var="rightCols" value="col-sm-8"/></c:if>

<c:if test="${isEnglish == null}"><c:set var="isEnglish" value="ENG"/></c:if>
<c:if test="${local == null}"><c:set var="local" value="LOCAL"/></c:if>
<c:if test="${addressType == null}"><c:set var="addressType" value="Residential"/></c:if>
<%--15-04-27 ly--%>
<c:if test="${disabled == null}"><c:set var="disabled" value=""/></c:if>

<div class="form-group col-xs-12" id="${id}_country_div">
    <label class="${leftCols} control-label" id="${id}_country_lbl">Country</label>

    <div class="${rightCols}">
        <select class="select2 input-large ${css}" name="${addressName}.countryUid" id="${id}_country"></select>
    </div>
</div>
<div class="form-group col-xs-12" id="${id}_attentionTo_div">
    <label class="${leftCols} control-label" id="${id}_attentionTo_lbl">Attention To</label>

    <div class="${rightCols}">
        <input type="text" class="input-xlarge" name="${addressName}.attentionTo" value="${addressValue.attentionTo}"
               id="${id}_attentionTo" maxlength="255"/>
    </div>
</div>
<div class="form-group col-xs-12" id="${id}_company_div">
    <label class="${leftCols} control-label" id="${id}_company_lbl">Company Name</label>

    <div class="${rightCols}">
        <input type="text" class="input-xlarge" name="${addressName}.companyName" value="${addressValue.companyName}"
               id="${id}_companyName" maxlength="255"/>
    </div>
</div>
<div class="form-group col-xs-12">
    <label class="${leftCols} control-label" id="${id}_address1_lbl"></label>

    <div class="${rightCols}" id="${id}_address1_div">
        <input type="text" class="input-xlarge" name="${addressName}.field1Address"
               value="${addressValue.field1Address}" id="${id}_address1" maxlength="255"/>
    </div>
</div>
<div id="${id}_address_lbls">
    <div class="form-group col-xs-12">
        <label class="${leftCols} control-label" id="${id}_address2_lbl"></label>

        <div class="${rightCols}" id="${id}_address2_div">
            <input type="text" class="input-xlarge" name="${addressName}.field2Address"
                   value="${addressValue.field2Address}" id="${id}_address2" maxlength="255"/>
        </div>
    </div>
    <div class="form-group col-xs-12">
        <label class="${leftCols} control-label" id="${id}_address3_lbl"></label>

        <div class="${rightCols}" id="${id}_address3_div">
            <input type="text" class="input-xlarge" name="${addressName}.field3Address"
                   value="${addressValue.field3Address}" id="${id}_address3" maxlength="255"/>
        </div>
    </div>
    <div class="form-group col-xs-12">
        <label class="${leftCols} control-label" id="${id}_address4_lbl"></label>

        <div class="${rightCols}" id="${id}_address4_div">
            <input type="text" class="input-xlarge" name="${addressName}.field4Address"
                   value="${addressValue.field4Address}" id="${id}_address4" maxlength="255"/>
        </div>
    </div>
    <div class="form-group col-xs-12">
        <label class="${leftCols} control-label" id="${id}_address5_lbl"></label>

        <div class="${rightCols}" id="${id}_address5_div">
            <input type="text" class="input-xlarge" name="${addressName}.field5Address"
                   value="${addressValue.field5Address}" id="${id}_address5" maxlength="255"/>
        </div>
    </div>
</div>
<script type="text/javascript">
var ${id}isEnglish = '${isEnglish}';
var ${id}local = '${local}';
var ${id}country = '${addressValue.countryUid}';
var ${id}addressType = '${addressType}';
<%--15-04-27 ly--%>
var ${id}disabled = '${disabled}';

$(function () {
    //initialize
    $('#${id}_country').dropdownList('address_country', '${addressValue.countryUid}');

    ${id}changeLanguageAndLocation(${id}isEnglish, ${id}local);
    ${id}changeCityOrDistrict(${id}country, ${id}isEnglish, ${id}local);

    if (${id}addressType == 'Business') {
        $('#${id}_company_div').show();
    } else {
        $('#${id}_company_div').hide();
    }

    if (${id}addressType == 'BOX') {
        $('#${id}_country_div').hide();
        /*$('#${id}_address2_div').hide();
         $('#${id}_address3_div').hide();
         $('#${id}_address4_div').hide();
         $('#${id}_address5_div').hide();*/
        $('#${id}_attentionTo_div').hide();
        $('#${id}_address_lbls').hide();
    }
    if (${id}addressType == 'Residential') {
        $('#${id}_attentionTo_div').hide();
    }
    if (${id}addressType == 'CPA') {
        $('#${id}_attentionTo_div').hide();
        $('#${id}_country_div').hide();
        $('#${id}_country').removeClass('required');
        $('#${id}_address4').removeClass('required');
    }

    $('#${id}_country').on('change', function () {
        ${id}country = $(this).val();
        //change city or district
        ${id}changeCityOrDistrict(${id}country, ${id}isEnglish, ${id}local);
    });

    <%--15-04-27 ly--%>
    if(${id}disabled == 'disabled'){
        $('#${id}_country').attr("disabled", "disabled");
        $('#${id}_attentionTo').attr("readonly", "true");
        $('#${id}_companyName').attr("readonly", "true");
        $('#${id}_address1').attr("readonly", "true");
        $('#${id}_address2').attr("readonly", "true");
        $('#${id}_address3').attr("readonly", "true");
        $('#${id}_address4').attr("readonly", "true");
        $('#${id}_address5').attr("readonly", "true");
    }
});

function ${id}changeLanguageAndLocation(isEnglish, local) {
    if (isEnglish && isEnglish == 'ENG' && local && local == 'LOCAL') {
        $('#${id}_address1_lbl').html("Address");

        $('#${id}_address1_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field1Address\" maxlength=\"255\" value=\"${addressValue.field1Address}\" id=\"${id}_address1\"/>");

        $('#${id}_address2_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field2Address\" maxlength=\"255\" value=\"${addressValue.field2Address}\" id=\"${id}_address2\"/>");

        $('#${id}_address3_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field3Address\" maxlength=\"255\" value=\"${addressValue.field3Address}\" id=\"${id}_address3\"/>");

        $('#${id}_address4_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field4Address\" maxlength=\"255\" value=\"${addressValue.field4Address}\" id=\"${id}_address4\"/>");

        $('#${id}_address5_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field5Address\" maxlength=\"255\" value=\"${addressValue.field5Address}\" id=\"${id}_address5\"/>");

    } else if (isEnglish && isEnglish == 'ENG' && local && local == 'OVERSEAS') {
        $('#${id}_address1_lbl').html("Address");

        $('#${id}_address1_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field1Address\" maxlength=\"255\" value=\"${addressValue.field1Address}\" id=\"${id}_address1\"/>");

        $('#${id}_address2_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field2Address\" maxlength=\"255\" value=\"${addressValue.field2Address}\" id=\"${id}_address2\"/>");

        $('#${id}_address3_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field3Address\" maxlength=\"255\" value=\"${addressValue.field3Address}\" id=\"${id}_address3\"/>");

        $('#${id}_address4_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field4Address\" maxlength=\"255\" value=\"${addressValue.field4Address}\" id=\"${id}_address4\"/>");

        $('#${id}_address5_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field5Address\" maxlength=\"255\" value=\"${addressValue.field5Address}\" id=\"${id}_address5\"/>");

    } else if (isEnglish && isEnglish == 'CHI' && local && local == 'LOCAL') {
        $('#${id}_address1_lbl').html("室，楼，座");
        $('#${id}_address1_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field1Address\" maxlength=\"255\" value=\"${addressValue.field1Address}\" id=\"${id}_address1\"/>");

        $('#${id}_address2_lbl').html("大厦");
        $('#${id}_address2_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field2Address\" maxlength=\"255\" value=\"${addressValue.field2Address}\" id=\"${id}_address2\"/>");

        $('#${id}_address3_lbl').html("街道/屋/乡村名称及门牌号");
        $('#${id}_address3_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field3Address\" maxlength=\"255\" value=\"${addressValue.field3Address}\" id=\"${id}_address3\"/>");

        $('#${id}_address4_lbl').html("区域");
        $('#${id}_address4_div').html("<select class=\"select2 input-large ${css}\" name=\"${addressName}.field4Address\" maxlength=\"255\" id=\"${id}_address4\"/>");

        $('#${id}_address5_lbl').html("区");
        $('#${id}_address5_div').html("<select class=\"select2 input-large ${css}\" name=\"${addressName}.field5Address\" maxlength=\"255\"  id=\"${id}_address5\"/>");

    } else if (isEnglish && isEnglish == 'CHI' && local && local == 'CHINA') {
        $('#${id}_address1_lbl').html("省");
        $('#${id}_address1_div').html("<select class=\"select2 input-large ${css}\" name=\"${addressName}.field1Address\" id=\"${id}_address1\"/>");
        <%--$('#${id}_address2_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field1Address" maxlength="255" value="${addressValue.field1Address}" id="${id}_address1"/>');--%>

        $('#${id}_address2_lbl').html("市");
        $('#${id}_address2_div').html("<select class=\"select2 input-large ${css}\" name=\"${addressName}.field2Address\" value=\"${addressValue.field2Address}\" id=\"${id}_address2\"/>");
        <%--$('#${id}_address2_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field2Address" maxlength="255"  value="${addressValue.field2Address}" id="${id}_address2"/>');--%>

        $('#${id}_address3_lbl').html("区，大街，路");
        $('#${id}_address3_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field3Address\" maxlength=\"255\" value=\"${addressValue.field3Address}\" id=\"${id}_address3\"/>");

        $('#${id}_address4_lbl').html("楼，室");
        $('#${id}_address4_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field4Address\" maxlength=\"255\" value=\"${addressValue.field4Address}\" id=\"${id}_address4\"/>");

        $('#${id}_address5_lbl').html("邮政编号");
        $('#${id}_address5_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field5Address\" maxlength=\"255\" value=\"${addressValue.field5Address}\" id=\"${id}_address5\"/>");

    } else if (isEnglish && isEnglish == 'CHI' && local && local == 'OVERSEAS') {
        $('#${id}_address1_lbl').html("县/市");
        $('#${id}_address1_div').html("<select class=\"select2 input-large ${css}\" name=\"${addressName}.field1Address\" maxlength=\"255\"  id=\"${id}_address1\"/>");

        $('#${id}_address2_lbl').html("区");
        $('#${id}_address2_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field2Address\" maxlength=\"255\" value=\"${addressValue.field2Address}\" id=\"${id}_address2\"/>");

        $('#${id}_address3_lbl').html("大街，路");
        $('#${id}_address3_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field3Address\" maxlength=\"255\" value=\"${addressValue.field3Address}\" id=\"${id}_address3\"/>");

        $('#${id}_address4_lbl').html("楼，室");
        $('#${id}_address4_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field4Address\" maxlength=\"255\" value=\"${addressValue.field4Address}\" id=\"${id}_address4\"/>");

        $('#${id}_address5_lbl').html("邮政编号");
        $('#${id}_address5_div').html("<input type=\"text\" class=\"input-xlarge ${css}\" name=\"${addressName}.field5Address\" maxlength=\"255\" value=\"${addressValue.field5Address}\" id=\"${id}_address5\"/>");
    }
}

function ${id}changeCityOrDistrict(countryUid, isEnglish, local) {
    if (isEnglish && isEnglish == 'ENG' && local && local == 'LOCAL') {
        $('#${id}_address5').empty();
        $('#${id}_address4').empty();
    } else if (isEnglish && isEnglish == 'CHI' && local && local == 'LOCAL') {
        $('#${id}_address5').empty();
        $('#${id}_address4').empty();
    } else if (isEnglish && isEnglish == 'CHI' && local && local == 'CHINA') {
        $('#${id}_address1').empty();
        $('#${id}_address2').empty();
    } else if (isEnglish && isEnglish == 'CHI' && local && local == 'OVERSEAS') {
        $('#${id}_address1').empty();
    }
}

function ${id}addAndRemoveValidator(isvlid, isEnglish, local) {
    if (isvlid) {
        if (isEnglish && isEnglish == 'ENG' && local && local == 'LOCAL') {
            if (!($('#${id}_country').hasClass('required'))) {
                $('#${id}_country').addClass('required');
            }
            if (!($('#${id}_address1').hasClass('required'))) {
                $('#${id}_address1').addClass('required');
            }
            if (!($('#${id}_address2').hasClass('required'))) {
                $('#${id}_address2').addClass('required');
            }
            if (!($('#${id}_address4').hasClass('required'))) {
                $('#${id}_address4').addClass('required');
            }
            if (!($('#${id}_address5').hasClass('required'))) {
                $('#${id}_address5').addClass('required');
            }
        } else if (isEnglish && isEnglish == 'ENG' && local && local == 'OVERSEAS') {
            if (!($('#${id}_country').hasClass('required'))) {
                $('#${id}_country').addClass('required');
            }
            if (!($('#${id}_address1').hasClass('required'))) {
                $('#${id}_address1').addClass('required');
            }
            if (!($('#${id}_address2').hasClass('required'))) {
                $('#${id}_address2').addClass('required');
            }
            if (!($('#${id}_address3').hasClass('required'))) {
                $('#${id}_address3').addClass('required');
            }
            if (!($('#${id}_address5').hasClass('required'))) {
                $('#${id}_address5').addClass('required');
            }
        } else if (isEnglish && isEnglish == 'CHI' && local && local == 'LOCAL') {
            if (!($('#${id}_country').hasClass('required'))) {
                $('#${id}_country').addClass('required');
            }
            if (!($('#${id}_address1').hasClass('required'))) {
                $('#${id}_address1').addClass('required');
            }
            if (!($('#${id}_address2').hasClass('required'))) {
                $('#${id}_address2').addClass('required');
            }
            if (!($('#${id}_address4').hasClass('required'))) {
                $('#${id}_address4').addClass('required');
            }
            if (!($('#${id}_address5').hasClass('required'))) {
                $('#${id}_address5').addClass('required');
            }
        } else if (isEnglish && isEnglish == 'CHI' && local && local == 'CHINA') {
            if (!($('#${id}_country').hasClass('required'))) {
                $('#${id}_country').addClass('required');
            }
            if (!($('#${id}_address1').hasClass('required'))) {
                $('#${id}_address1').addClass('required');
            }
            if (!($('#${id}_address2').hasClass('required'))) {
                $('#${id}_address2').addClass('required');
            }
            if (!($('#${id}_address3').hasClass('required'))) {
                $('#${id}_address3').addClass('required');
            }
            if (!($('#${id}_address5').hasClass('required'))) {
                $('#${id}_address5').addClass('required');
            }
        } else if (isEnglish && isEnglish == 'CHI' && local && local == 'OVERSEAS') {
            if (!($('#${id}_country').hasClass('required'))) {
                $('#${id}_country').addClass('required');
            }
            if (!($('#${id}_address1').hasClass('required'))) {
                $('#${id}_address1').addClass('required');
            }
            if (!($('#${id}_address2').hasClass('required'))) {
                $('#${id}_address2').addClass('required');
            }
            if (!($('#${id}_address3').hasClass('required'))) {
                $('#${id}_address3').addClass('required');
            }
            if (!($('#${id}_address5').hasClass('required'))) {
                $('#${id}_address5').addClass('required');
            }
        }
    } else {
        if (isEnglish && isEnglish == 'ENG' && local && local == 'LOCAL') {
            if ($('#${id}_country').hasClass('required')) {
                $('#${id}_country').removeClass('required');
            }
            if ($('#${id}_address1').hasClass('required')) {
                $('#${id}_address1').removeClass('required');
            }
            if ($('#${id}_address2').hasClass('required')) {
                $('#${id}_address2').removeClass('required');
            }
            if ($('#${id}_address4').hasClass('required')) {
                $('#${id}_address4').removeClass('required');
            }
            if ($('#${id}_address5').hasClass('required')) {
                $('#${id}_address5').removeClass('required');
            }
        } else if (isEnglish && isEnglish == 'ENG' && local && local == 'OVERSEAS') {
            if ($('#${id}_country').hasClass('required')) {
                $('#${id}_country').removeClass('required');
            }
            if ($('#${id}_address1').hasClass('required')) {
                $('#${id}_address1').removeClass('required');
            }
            if ($('#${id}_address2').hasClass('required')) {
                $('#${id}_address2').removeClass('required');
            }
            if ($('#${id}_address3').hasClass('required')) {
                $('#${id}_address3').removeClass('required');
            }
            if ($('#${id}_address5').hasClass('required')) {
                $('#${id}_address5').removeClass('required');
            }
        } else if (isEnglish && isEnglish == 'CHI' && local && local == 'LOCAL') {
            if ($('#${id}_country').hasClass('required')) {
                $('#${id}_country').removeClass('required');
            }
            if ($('#${id}_address1').hasClass('required')) {
                $('#${id}_address1').removeClass('required');
            }
            if ($('#${id}_address2').hasClass('required')) {
                $('#${id}_address2').removeClass('required');
            }
            if ($('#${id}_address4').hasClass('required')) {
                $('#${id}_address4').removeClass('required');
            }
            if ($('#${id}_address5').hasClass('required')) {
                $('#${id}_address5').removeClass('required');
            }
        } else if (isEnglish && isEnglish == 'CHI' && local && local == 'CHINA') {
            if ($('#${id}_country').hasClass('required')) {
                $('#${id}_country').removeClass('required');
            }
            if ($('#${id}_address1').hasClass('required')) {
                $('#${id}_address1').removeClass('required');
            }
            if ($('#${id}_address2').hasClass('required')) {
                $('#${id}_address2').removeClass('required');
            }
            if ($('#${id}_address3').hasClass('required')) {
                $('#${id}_address3').removeClass('required');
            }
            if ($('#${id}_address5').hasClass('required')) {
                $('#${id}_address5').removeClass('required');
            }
        } else if (isEnglish && isEnglish == 'CHI' && local && local == 'OVERSEAS') {
            if ($('#${id}_country').hasClass('required')) {
                $('#${id}_country').removeClass('required');
            }
            if ($('#${id}_address1').hasClass('required')) {
                $('#${id}_address1').removeClass('required');
            }
            if ($('#${id}_address2').hasClass('required')) {
                $('#${id}_address2').removeClass('required');
            }
            if ($('#${id}_address3').hasClass('required')) {
                $('#${id}_address3').removeClass('required');
            }
            if ($('#${id}_address5').hasClass('required')) {
                $('#${id}_address5').removeClass('required');
            }
        }
    }
}
</script>
