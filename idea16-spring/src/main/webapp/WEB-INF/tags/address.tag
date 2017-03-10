<%--
  User: Jack 
  Date: 14-10-13
  Description:
--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="Address Id Prefix" %>
<%@ attribute name="addressName" type="java.lang.String" required="true" description="Address of Name" %>
<%@ attribute name="addressValue" type="org.hkicpa.mas.common.vo.AddressVo" required="false" description="Address of Value" %>
<%@ attribute name="isEnglish" type="java.lang.String" required="false" description="Is English Address" %>
<%@ attribute name="local" type="java.lang.String" required="false" description="Location Type" %>
<%@ attribute name="addressType" type="java.lang.String" required="false" description="Address Type" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Css Class" %>
<%@ attribute name="leftCols" type="java.lang.String" required="false" description="Left Cols" %>
<%@ attribute name="rightCols" type="java.lang.String" required="false" description="Right Cols" %>

<c:if test="${leftCols == null}"><c:set var="leftCols" value="col-sm-4" /></c:if>
<c:if test="${rightCols == null}"><c:set var="rightCols" value="col-sm-8" /></c:if>

<c:if test="${isEnglish == null}"><c:set var="isEnglish" value="ENG" /></c:if>
<c:if test="${local == null}"><c:set var="local" value="LOCAL" /></c:if>
<!--Address Type : Residential, Business, Other-->
<c:if test="${addressType == null}"><c:set var="addressType" value="Residential" /></c:if>

<div class="form-group">
    <label class="${leftCols} control-label" id="${id}_country_lbl">Country</label>
    <div class="${rightCols}">
        <select class="select2 input-large ${css}" name="${addressName}.countryUid" id="${id}_country"></select>
    </div>
</div>
<div class="form-group">
    <label class="${leftCols} control-label" id="${id}_attentionTo_lbl">Attention To</label>

    <div class="${rightCols}">
        <input type="text" class="input-large" name="${addressName}.attentionTo" value="${addressValue.attentionTo}" id="${id}_attentionTo"/>
    </div>
</div>
<div class="form-group" id="${id}_company_div">
    <label class="${leftCols} control-label" id="${id}_company_lbl">Company Name</label>

    <div class="${rightCols}">
        <input type="text" class="input-large" name="${addressName}.companyName" value="${addressValue.companyName}" id="${id}_attentionTo"/>
    </div>
</div>
<div class="form-group">
    <label class="${leftCols} control-label" id="${id}_address1_lbl">Flat/Room,Floor,Block</label>
    <div class="${rightCols}" id="${id}_address1_div">
        <input type="text" class="input-xlarge" name="${addressName}.field1Address" value="${addressValue.field1Address}" id="${id}_address1"/>
    </div>
</div>
<div class="form-group">
    <label class="${leftCols} control-label" id="${id}_address2_lbl">Building Name</label>
    <div class="${rightCols}" id="${id}_address2_div">
        <input type="text" class="input-xlarge" name="${addressName}.field2Address" value="${addressValue.field2Address}" id="${id}_address2"/>
    </div>
</div>
<div class="form-group">
    <label class="${leftCols} control-label" id="${id}_address3_lbl">Number and Name of Street/Estate/Village</label>
    <div class="${rightCols}" id="${id}_address3_div">
        <input type="text" class="input-xlarge" name="${addressName}.field3Address" value="${addressValue.field3Address}" id="${id}_address3"/>
    </div>
</div>
<div class="form-group">
    <label class="${leftCols} control-label" id="${id}_address4_lbl">Area</label>
    <div class="${rightCols}" id="${id}_address4_div">
        <input type="text" class="input-xlarge" name="${addressName}.field4Address" value="${addressValue.field4Address}" id="${id}_address4"/>
    </div>
</div>
<div class="form-group">
    <label class="${leftCols} control-label" id="${id}_address5_lbl">District</label>
    <div class="${rightCols}" id="${id}_address5_div">
        <input type="text" class="input-xlarge" name="${addressName}.field5Address" value="${addressValue.field5Address}" id="${id}_address5"/>
    </div>
</div>

<script type="text/javascript">
    var ${id}isEnglish = '${isEnglish}';
    var ${id}local = '${local}';
    var ${id}country = '${addressValue.countryUid}';
    var ${id}addressType = '${addressType}';

    $(function(){
        //initialize
        $('#${id}_country').dropdownList('address_country', '${addressValue.countryUid}');

        ${id}changeLanguageAndLocation(${id}isEnglish, ${id}local);
        ${id}changeCityOrDistrict(${id}country, ${id}isEnglish, ${id}local);

        if (${id}addressType == 'Business'){
            $('#${id}_company_div').show();
        }else{
            $('#${id}_company_div').hide();
        }

        $('#${id}_country').on('change', function(){
            ${id}country = $(this).val();
            //change city or district
            ${id}changeCityOrDistrict(${id}country, ${id}isEnglish, ${id}local);
        });
    });

    function ${id}changeLanguageAndLocation(isEnglish, local){
        if (isEnglish && isEnglish == 'ENG' && local && local == 'LOCAL'){
            $('#${id}_address1_lbl').html('Flat/Room,Floor,Block');
            $('#${id}_address1_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field1Address" value="${addressValue.field1Address}" id="${id}_address1"/>');

            $('#${id}_address2_lbl').html('Building Name');
            $('#${id}_address2_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field2Address" value="${addressValue.field2Address}" id="${id}_address2"/>');

            $('#${id}_address3_lbl').html('Number and Name of Street/Estate/Village');
            $('#${id}_address3_div').html('<input type="text" class="input-xlarge" name="${addressName}.field3Address" value="${addressValue.field3Address}" id="${id}_address3"/>');

            $('#${id}_address4_lbl').html('Area');
            $('#${id}_address4_div').html('<select class="select2 input-large ${css}" name="${addressName}.field4Address" id="${id}_address4"/>');

            $('#${id}_address5_lbl').html('District');
            $('#${id}_address5_div').html('<select class="select2 input-large ${css}" name="${addressName}.field5Address" id="${id}_address5"/>');

        }else if (isEnglish && isEnglish == 'ENG' && local && local == 'OVERSEAS'){
            $('#${id}_address1_lbl').html('Building Name');
            $('#${id}_address1_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field1Address" value="${addressValue.field1Address}" id="${id}_address1"/>');

            $('#${id}_address2_lbl').html('Flat X Room X, Floor X, BLK X');
            $('#${id}_address2_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field2Address" value="${addressValue.field2Address}" id="${id}_address2"/>');

            $('#${id}_address3_lbl').html('Street Name');
            $('#${id}_address3_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field3Address" value="${addressValue.field3Address}" id="${id}_address3"/>');

            $('#${id}_address4_lbl').html('Area');
            $('#${id}_address4_div').html('<input type="text" class="input-xlarge" name="${addressName}.field4Address" value="${addressValue.field4Address}" id="${id}_address4"/>');

            $('#${id}_address5_lbl').html('District / Postal code');
            $('#${id}_address5_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field5Address" value="${addressValue.field5Address}" id="${id}_address5"/>');

        }else if (isEnglish && isEnglish == 'CHI' && local && local == 'LOCAL'){
            $('#${id}_address1_lbl').html('室，楼，座');
            $('#${id}_address1_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field1Address" value="${addressValue.field1Address}" id="${id}_address1"/>');

            $('#${id}_address2_lbl').html('大厦');
            $('#${id}_address2_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field2Address" value="${addressValue.field2Address}" id="${id}_address2"/>');

            $('#${id}_address3_lbl').html('街道/屋/乡村名称及门牌号');
            $('#${id}_address3_div').html('<input type="text" class="input-xlarge" name="${addressName}.field3Address" value="${addressValue.field3Address}" id="${id}_address3"/>');

            $('#${id}_address4_lbl').html('区域');
            $('#${id}_address4_div').html('<select class="select2 input-large ${css}" name="${addressName}.field4Address" id="${id}_address4"/>');

            $('#${id}_address5_lbl').html('区');
            $('#${id}_address5_div').html('<select class="select2 input-large ${css}" name="${addressName}.field5Address" id="${id}_address5"/>');

        }else if (isEnglish && isEnglish == 'CHI' && local && local == 'CHINA'){
            $('#${id}_address1_lbl').html('省');
            $('#${id}_address1_div').html('<select class="select2 input-large ${css}" name="${addressName}.field1Address" id="${id}_address1"/>');

            $('#${id}_address2_lbl').html('市');
            $('#${id}_address2_div').html('<select class="select2 input-large ${css}" name="${addressName}.field2Address" value="${addressValue.field2Address}" id="${id}_address2"/>');

            $('#${id}_address3_lbl').html('区，大街，路');
            $('#${id}_address3_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field3Address" value="${addressValue.field3Address}" id="${id}_address3"/>');

            $('#${id}_address4_lbl').html('楼，室');
            $('#${id}_address4_div').html('<input type="text" class="input-xlarge" name="${addressName}.field4Address" value="${addressValue.field4Address}" id="${id}_address4"/>');

            $('#${id}_address5_lbl').html('邮政编号');
            $('#${id}_address5_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field5Address" value="${addressValue.field5Address}" id="${id}_address5"/>');

        }else if (isEnglish && isEnglish == 'CHI' && local && local == 'OVERSEAS'){
            $('#${id}_address1_lbl').html('县/市');
            $('#${id}_address1_div').html('<select class="select2 input-large ${css}" name="${addressName}.field1Address" id="${id}_address1"/>');

            $('#${id}_address2_lbl').html('区');
            $('#${id}_address2_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field2Address" value="${addressValue.field2Address}" id="${id}_address2"/>');

            $('#${id}_address3_lbl').html('大街，路');
            $('#${id}_address3_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field3Address" value="${addressValue.field3Address}" id="${id}_address3"/>');

            $('#${id}_address4_lbl').html('楼，室');
            $('#${id}_address4_div').html('<input type="text" class="input-xlarge" name="${addressName}.field4Address" value="${addressValue.field4Address}" id="${id}_address4"/>');

            $('#${id}_address5_lbl').html('邮政编号');
            $('#${id}_address5_div').html('<input type="text" class="input-xlarge ${css}" name="${addressName}.field5Address" value="${addressValue.field5Address}" id="${id}_address5"/>');
        }
    }

    function ${id}changeCityOrDistrict(countryUid, isEnglish, local){
        if (isEnglish && isEnglish == 'ENG' && local && local == 'LOCAL'){
            $('#${id}_address5').empty();
            $('#${id}_address5').dropdownList('district', '${addressValue.field5Address}');
            $('#${id}_address4').empty();
            $('#${id}_address4').dropdownList('sub_district', '${addressValue.field4Address}');
        }else if (isEnglish && isEnglish == 'CHI' && local && local == 'LOCAL'){
            $('#${id}_address5').empty();
            $('#${id}_address5').dropdownList('district', '${addressValue.field5Address}');
            $('#${id}_address4').empty();
            $('#${id}_address4').dropdownList('sub_district', '${addressValue.field4Address}');
        }else if (isEnglish && isEnglish == 'CHI' && local && local == 'CHINA'){
            $('#${id}_address1').empty();
            $('#${id}_address1').dropdownList('province', '${addressValue.field1Address}');
            $('#${id}_address2').empty();
            $('#${id}_address2').dropdownList('sub_district', '${addressValue.field2Address}');
        }else if (isEnglish && isEnglish == 'CHI' && local && local == 'OVERSEAS'){
            $('#${id}_address1').empty();
            $('#${id}_address1').dropdownList('sub_distict', '${addressValue.field1Address}');
        }
    }

    function ${id}addAndRemoveValidator(isvlid,isEnglish, local){
        if (isvlid){
            if (isEnglish && isEnglish == 'ENG' && local && local == 'LOCAL'){
                if (!($('#${id}_country').hasClass('required'))){
                    $('#${id}_country').addClass('required');
                }
                if (!($('#${id}_address1').hasClass('required'))){
                    $('#${id}_address1').addClass('required');
                }
                if (!($('#${id}_address2').hasClass('required'))){
                    $('#${id}_address2').addClass('required');
                }
                if (!($('#${id}_address4').hasClass('required'))){
                    $('#${id}_address4').addClass('required');
                }
                if (!($('#${id}_address5').hasClass('required'))){
                    $('#${id}_address5').addClass('required');
                }
            }else if (isEnglish && isEnglish == 'ENG' && local && local == 'OVERSEAS'){
                if (!($('#${id}_country').hasClass('required'))){
                    $('#${id}_country').addClass('required');
                }
                if (!($('#${id}_address1').hasClass('required'))){
                    $('#${id}_address1').addClass('required');
                }
                if (!($('#${id}_address2').hasClass('required'))){
                    $('#${id}_address2').addClass('required');
                }
                if (!($('#${id}_address3').hasClass('required'))){
                    $('#${id}_address3').addClass('required');
                }
                if (!($('#${id}_address5').hasClass('required'))){
                    $('#${id}_address5').addClass('required');
                }
            }else if (isEnglish && isEnglish == 'CHI' && local && local == 'LOCAL'){
                if (!($('#${id}_country').hasClass('required'))){
                    $('#${id}_country').addClass('required');
                }
                if (!($('#${id}_address1').hasClass('required'))){
                    $('#${id}_address1').addClass('required');
                }
                if (!($('#${id}_address2').hasClass('required'))){
                    $('#${id}_address2').addClass('required');
                }
                if (!($('#${id}_address4').hasClass('required'))){
                    $('#${id}_address4').addClass('required');
                }
                if (!($('#${id}_address5').hasClass('required'))){
                    $('#${id}_address5').addClass('required');
                }
            }else if (isEnglish && isEnglish == 'CHI' && local && local == 'CHINA'){
                if (!($('#${id}_country').hasClass('required'))){
                    $('#${id}_country').addClass('required');
                }
                if (!($('#${id}_address1').hasClass('required'))){
                    $('#${id}_address1').addClass('required');
                }
                if (!($('#${id}_address2').hasClass('required'))){
                    $('#${id}_address2').addClass('required');
                }
                if (!($('#${id}_address3').hasClass('required'))){
                    $('#${id}_address3').addClass('required');
                }
                if (!($('#${id}_address5').hasClass('required'))){
                    $('#${id}_address5').addClass('required');
                }
            }else if (isEnglish && isEnglish == 'CHI' && local && local == 'OVERSEAS'){
                if (!($('#${id}_country').hasClass('required'))){
                    $('#${id}_country').addClass('required');
                }
                if (!($('#${id}_address1').hasClass('required'))){
                    $('#${id}_address1').addClass('required');
                }
                if (!($('#${id}_address2').hasClass('required'))){
                    $('#${id}_address2').addClass('required');
                }
                if (!($('#${id}_address3').hasClass('required'))){
                    $('#${id}_address3').addClass('required');
                }
                if (!($('#${id}_address5').hasClass('required'))){
                    $('#${id}_address5').addClass('required');
                }
            }
        }else{
            if (isEnglish && isEnglish == 'ENG' && local && local == 'LOCAL'){
                if ($('#${id}_country').hasClass('required')){
                    $('#${id}_country').removeClass('required');
                }
                if ($('#${id}_address1').hasClass('required')){
                    $('#${id}_address1').removeClass('required');
                }
                if ($('#${id}_address2').hasClass('required')){
                    $('#${id}_address2').removeClass('required');
                }
                if ($('#${id}_address4').hasClass('required')){
                    $('#${id}_address4').removeClass('required');
                }
                if ($('#${id}_address5').hasClass('required')){
                    $('#${id}_address5').removeClass('required');
                }
            }else if (isEnglish && isEnglish == 'ENG' && local && local == 'OVERSEAS'){
                if ($('#${id}_country').hasClass('required')){
                    $('#${id}_country').removeClass('required');
                }
                if ($('#${id}_address1').hasClass('required')){
                    $('#${id}_address1').removeClass('required');
                }
                if ($('#${id}_address2').hasClass('required')){
                    $('#${id}_address2').removeClass('required');
                }
                if ($('#${id}_address3').hasClass('required')){
                    $('#${id}_address3').removeClass('required');
                }
                if ($('#${id}_address5').hasClass('required')){
                    $('#${id}_address5').removeClass('required');
                }
            }else if (isEnglish && isEnglish == 'CHI' && local && local == 'LOCAL'){
                if ($('#${id}_country').hasClass('required')){
                    $('#${id}_country').removeClass('required');
                }
                if ($('#${id}_address1').hasClass('required')){
                    $('#${id}_address1').removeClass('required');
                }
                if ($('#${id}_address2').hasClass('required')){
                    $('#${id}_address2').removeClass('required');
                }
                if ($('#${id}_address4').hasClass('required')){
                    $('#${id}_address4').removeClass('required');
                }
                if ($('#${id}_address5').hasClass('required')){
                    $('#${id}_address5').removeClass('required');
                }
            }else if (isEnglish && isEnglish == 'CHI' && local && local == 'CHINA'){
                if ($('#${id}_country').hasClass('required')){
                    $('#${id}_country').removeClass('required');
                }
                if ($('#${id}_address1').hasClass('required')){
                    $('#${id}_address1').removeClass('required');
                }
                if ($('#${id}_address2').hasClass('required')){
                    $('#${id}_address2').removeClass('required');
                }
                if ($('#${id}_address3').hasClass('required')){
                    $('#${id}_address3').removeClass('required');
                }
                if ($('#${id}_address5').hasClass('required')){
                    $('#${id}_address5').removeClass('required');
                }
            }else if (isEnglish && isEnglish == 'CHI' && local && local == 'OVERSEAS'){
                if ($('#${id}_country').hasClass('required')){
                    $('#${id}_country').removeClass('required');
                }
                if ($('#${id}_address1').hasClass('required')){
                    $('#${id}_address1').removeClass('required');
                }
                if ($('#${id}_address2').hasClass('required')){
                    $('#${id}_address2').removeClass('required');
                }
                if ($('#${id}_address3').hasClass('required')){
                    $('#${id}_address3').removeClass('required');
                }
                if ($('#${id}_address5').hasClass('required')){
                    $('#${id}_address5').removeClass('required');
                }
            }
        }
    }
</script>
