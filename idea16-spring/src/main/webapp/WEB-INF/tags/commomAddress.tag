<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%-- id name value --%>
<%@ attribute name="id" type="java.lang.String" required="true" description="Address Id Prefix" %>
<%@ attribute name="addressName" type="java.lang.String" required="true" description="Address of Name" %>
<%@ attribute name="addressValue" type="org.hkicpa.mas.common.vo.AddressVo" required="false"
              description="Address of Value" %>
<%-- roleType: student/member    addressType: res/bus/box --%>
<%@ attribute name="roleType" type="java.lang.String" required="true" description="Address Role Type" %>
<%@ attribute name="addressType" type="java.lang.String" required="false" description="Address Type" %>
<%-- css --%>
<%@ attribute name="leftCols" type="java.lang.String" required="false" description="Left Cols" %>
<%@ attribute name="rightCols" type="java.lang.String" required="false" description="Right Cols" %>
<%@ attribute name="mailLeftCols" type="java.lang.String" required="false" description="Main Left Cols" %>
<%@ attribute name="mailRightCols" type="java.lang.String" required="false" description="Main Right Cols" %>
<%@ attribute name="radioReadonly" type="java.lang.String" required="false" description="Radio Address Read Only" %>
<%@ attribute name="freeBoxReadonly" type="java.lang.String" required="false" description="FreeBox Address Read Only" %>
<%@ attribute name="textareaReadonly" type="java.lang.String" required="false"
              description="Textarea Address Read Only" %>
<%@ attribute name="changeFormatRadioReadonly" type="java.lang.String" required="false"
              description="Radio Address Read Only" %>
<%@ attribute name="changeFormatFreeBoxReadonly" type="java.lang.String" required="false"
              description="FreeBox Address Read Only" %>
<%--changeButton: true/false   showTemplate: radio/5 free box--%>
<%@ attribute name="changeButton" type="java.lang.String" required="false" description="Have Change Button" %>
<%@ attribute name="clearButton" type="java.lang.String" required="false" description="Have Clear Button" %>
<%@ attribute name="showTemplate" type="java.lang.String" required="true" description="Show Address Template" %>
<%@ attribute name="showChangeTemplate" type="java.lang.String" required="false" description="Show Address Template" %>

<%@ attribute name="hasTelCountry" type="java.lang.String" required="false" description="Show Tel Country" %>
<%@ attribute name="hasFaxCountry" type="java.lang.String" required="false" description="Show Fax Country" %>
<%@ attribute name="telAndFaxReadonly" type="java.lang.String" required="false" description="Tel And Fax Country" %>

<c:if test="${changeButton == 'true' || clearButton == 'true'}">
    <div class="row col-xs-12">
        <div class="col-sm-8">
            <div class="form-group">
                <label class="${leftCols} control-label"></label>

                <div class="${rightCols}">
                    <c:if test="${changeButton == 'true'}">
                        <button class="btn btn-xs btn-primary" type="button" id="${id}_change_button">
                            <span class="lbl">Add/Change</span>
                        </button>
                    </c:if>

                    <c:if test="${clearButton == 'true'}">
                        <button class="btn btn-xs btn-primary" type="button" id="${id}_clear_button">
                            <span class="lbl">Clear</span>
                        </button>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</c:if>


<div class="row col-xs-12">
<div class="${mailLeftCols}">

    <%--========================== radio ==================================--%>
    <c:if test="${showTemplate == 'radio'}">

        <c:if test="${addressType == 'RES'}">
        <span class="label label-primary arrowed-in-right label-lg">
             <b>Residential address</b>
        </span>
        </c:if>
        <c:if test="${addressType == 'BUS'}">
        <span class="label label-success arrowed-in-right label-lg">
             <b>Business address</b>
        </span>
        </c:if>

        <c:if test="${roleType == 'member'}">
            <div class="row form-group">
                <label class="${leftCols} control-label required" id="languageLabel">Language </label>

                <div class="${rightCols}">
                    <select name="${addressName}.language" maxlength="10" cssClass="select2 input-medium required"
                            id="${id}_language"></select>
                </div>
            </div>

            <div class="form-group">
                <label class="${leftCols} control-label"></label>

                <div class="${rightCols}">
                    <div id="${id}_english">
                        <label>
                            <input type="radio" class="ace required" id="${id}_local_en" name="${addressName}.tempCode"
                                   value="local_en" ${addressValue.tempCode == 'local_en' ? 'checked': ''} />
                            <span class="lbl">Local</span>
                        </label>&nbsp&nbsp&nbsp
                        <label>
                            <input type="radio" class="ace required" id="${id}_overseas_en"
                                   name="${addressName}.tempCode"
                                   value="overseas_en" ${addressValue.tempCode == 'overseas_en' ? 'checked': ''} />
                            <span class="lbl"> Oversea </span>
                        </label>&nbsp&nbsp&nbsp
                    </div>

                    <div id="${id}_chinese">
                        <label>
                            <input type="radio" class="ace required" id="${id}_local_zh" name="${addressName}.tempCode"
                                   value="local_zh" ${addressValue.tempCode == 'local_zh' ? 'checked': ''} />
                            <span class="lbl">本地</span>
                        </label>&nbsp&nbsp&nbsp
                        <label>
                            <input type="radio" class="ace required" id="${id}_china_zh" name="${addressName}.tempCode"
                                   value="china_zh" ${addressValue.tempCode == 'china_zh' ? 'checked': ''} />
                            <span class="lbl"> 中国 </span>
                        </label>&nbsp&nbsp&nbsp
                        <label>
                            <input type="radio" class="ace required" id="${id}_overseas_zh"
                                   name="${addressName}.tempCode"
                                   value="overseas_zh" ${addressValue.tempCode == 'overseas_zh' ? 'checked': ''} />
                            <span class="lbl">海外</span>
                        </label>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${roleType == 'student'}">
            <div class="form-group">
                <label class="${leftCols} control-label"></label>

                <div class="${rightCols}">
                    <label>
                        <input type="radio" class="ace required" id="${id}_local_en" name="${addressName}.tempCode"
                               value="local_en" ${addressValue.tempCode == 'local_en' ? 'checked': ''} ${disabled}/>
                        <span class="lbl">Hong Kong</span>
                    </label>&nbsp&nbsp&nbsp
                    <label>
                        <input type="radio" class="ace required" id="${id}_china_zh" name="${addressName}.tempCode"
                               value="china_zh" ${addressValue.tempCode == 'china_zh' ? 'checked': ''} ${disabled}/>
                        <span class="lbl">China</span>
                    </label>&nbsp&nbsp&nbsp
                    <label>
                        <input type="radio" class="ace required" id="${id}_overseas_en" name="${addressName}.tempCode"
                               value="overseas_en" ${addressValue.tempCode == 'overseas_en' ? 'checked': ''} ${disabled}/>
                        <span class="lbl">Overseas</span>
                    </label>
                </div>
            </div>
        </c:if>

        <div id="${id}_address_div"></div>
    </c:if>
    <%--========================== raido end ==================================--%>

    <%--========================== free box ==================================--%>
    <c:if test="${showTemplate == 'freeBox'}">
        <input type="radio" class="ace hidden" id="${id}_tempCode" name="${addressName}.tempCode"
               value="${addressValue.tempCode}" checked/>

        <div class="row form-group" id="${id}_country_div">
            <label class="${leftCols} control-label" id="${id}_country_lbl">Country / Region</label>

            <div class="${rightCols}">
                <select class="input-xxlarge" name="${addressName}.dialogValidCountry" id="${id}_country"></select>
                <input type="hidden" name="${addressName}.countryUid" id="${id}_country_input"/>
            </div>
        </div>
        <div class="row form-group" id="${id}_attentionTo_div">
            <label class="${leftCols} control-label" id="${id}_attentionTo_lbl">Attention To</label>

            <div class="${rightCols}">
                <input type="text" class="input-xxlarge" ${freeBoxReadonly} name="${addressName}.attentionTo"
                       value="${addressValue.attentionTo}" id="${id}_attentionTo" maxlength="255"/>
            </div>
        </div>
        <div class="row form-group" id="${id}_company_div">
            <label class="${leftCols} control-label" id="${id}_company_lbl">Company Name</label>

            <div class="${rightCols}">
                <input type="text" class="input-xxlarge" ${freeBoxReadonly} name="${addressName}.companyName"
                       value="${addressValue.companyName}" id="${id}_companyName" maxlength="255"/>
            </div>
        </div>
        <div class="row form-group">
            <label class="${leftCols} control-label" id="${id}_address1_lbl">Address</label>

            <div class="${rightCols}" id="${id}_address1_div">
                <input type="text" class="input-xxlarge" ${freeBoxReadonly} name="${addressName}.field1Address"
                       value="${addressValue.field1Address}" id="${id}_field1Address" maxlength="255"/>
            </div>
        </div>
        <div id="${id}_address_box">
            <div class="row form-group">
                <label class="${leftCols} control-label"></label>

                <div class="${rightCols}">
                    <input type="text" class="input-xxlarge" ${freeBoxReadonly} name="${addressName}.field2Address"
                           value="${addressValue.field2Address}" id="${id}_field2Address" maxlength="255"/>
                </div>
            </div>
            <div class="row form-group">
                <label class="${leftCols} control-label"></label>

                <div class="${rightCols}">
                    <input type="text" class="input-xxlarge" ${freeBoxReadonly} name="${addressName}.field3Address"
                           value="${addressValue.field3Address}" id="${id}_field3Address" maxlength="255"/>
                </div>
            </div>
            <div class="row form-group">
                <label class="${leftCols} control-label"></label>

                <div class="${rightCols}">
                    <input type="text" class="input-xxlarge" ${freeBoxReadonly} name="${addressName}.field4Address"
                           value="${addressValue.field4Address}" id="${id}_field4Address" maxlength="255"/>
                </div>
            </div>
            <div class="row form-group">
                <label class="${leftCols} control-label"></label>

                <div class="${rightCols}">
                    <input type="text" class="input-xxlarge" ${freeBoxReadonly} name="${addressName}.field5Address"
                           value="${addressValue.field5Address}" id="${id}_field5Address" maxlength="255"/>
                </div>
            </div>
            <div id="${id}_address_location">
                <div class="row form-group">
                    <label class="${leftCols} control-label">Location</label>

                    <div class="${rightCols}">
                        <select class="input-xxlarge" name="${addressName}.dialogValidLocation"
                                id="${id}_location"></select>
                        <input type="hidden" name="${addressName}.locationUid" id="${id}_location_input"/>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${showTemplate == 'textarea'}">
        <div class="row form-group">
            <label class="${leftCols} control-label">Address</label>

            <div class="${rightCols}">
                <textarea rows="3" class="input-xxlarge" ${textareaReadonly} name="${addressName}.field1Address"
                          id="${id}_field1Address" maxlength="255"></textarea>
            </div>
        </div>
        <div id="${id}_address_location">
            <div class="row form-group">
                <label class="${leftCols} control-label">Location</label>

                <div class="${rightCols}">
                    <select class="input-xxlarge" name="${addressName}.dialogValidLocation"
                            id="${id}_location"></select>
                    <input type="hidden" name="${addressName}.locationUid" id="${id}_location_input"/>
                </div>
            </div>
        </div>
    </c:if>

</div>
<%--col end--%>

<div class="${mailRightCols}">
    <%--col start--%>
    <c:if test="${hasTelCountry == 'true'}">
        <%--<div class="col-xs-12">--%>
    <c:if test="${addressType == 'RES'}">
        <span class="label label-primary arrowed-in-right label-lg">
        </c:if>
        <c:if test="${addressType == 'BUS'}">
        <span class="label label-success arrowed-in-right label-lg">
        </c:if>
            <b>Telephone no.</b>
        </span>

        <div class="space-4"></div>

        <div class="form-group col-sm-12">
            <label class="col-sm-4 control-label">Tel-country</label>

            <div class="col-sm-8">
                <select class="input-medium" id="${id}_phoneCountry"></select>
                <input type="hidden" name="${addressName}.phoneCountry" id="${id}_phoneCountry_input"/>
            </div>
        </div>

        <div class="form-group col-sm-12">
            <label class="col-sm-4 control-label">Tel</label>

            <div class="col-sm-8">
                <input type="text" class="input-medium mobilePhone" ${telAndFaxReadonly}
                       name="${addressName}.phoneNum" maxlength="25"
                       value="${addressValue.phoneNum}" id="${id}_phoneNum" maxlength="25"/>
            </div>
        </div>
    </c:if>

    <div class="space-8"></div>

    <c:if test="${hasFaxCountry == 'true'}">
        <c:if test="${addressType == 'RES'}">
        <span class="label label-primary arrowed-in-right label-lg">
        </c:if>
        <c:if test="${addressType == 'BUS'}">
        <span class="label label-success arrowed-in-right label-lg">
        </c:if>
            <b>Fax no.</b>
        </span>

        <div class="space-4"></div>

        <div class="col-sm-12 form-group">
            <label class="col-sm-4 control-label">Fax-country </label>

            <div class="col-sm-8">
                <select class="input-medium" id="${id}_faxCountry"></select>
                <input type="hidden" name="${addressName}.faxCountry" id="${id}_faxCountry_input"/>
            </div>
        </div>

        <div class="col-sm-12 form-group">
            <label class="col-sm-4 control-label">Fax</label>

            <div class="col-sm-8">
                <input type="text" class="input-medium mobilePhone" ${telAndFaxReadonly}
                       name="${addressName}.faxNum" maxlength="25"
                       value="${addressValue.faxNum}" id="${id}_faxNum" maxlength="25"/>
            </div>
        </div>
        <%--</div>--%>
    </c:if>
</div>
<%--col end--%>

</div>
<%--row end--%>
<%--========================== free box end ==================================--%>


<script>
<%--========================== tel/fax number js ==================================--%>
$(function () {
    if ("${showTemplate}" == "radio") {
        $('#${id}_phoneCountry').dropdownList("tel_country",
                "${addressValue.phoneCountry}" != '' ? "${addressValue.phoneCountry}" : "852");
        $("#${id}_phoneCountry_input").val("${addressValue.phoneCountry}" != '' ? "${addressValue.phoneCountry}" : "852");

        $('#${id}_faxCountry').dropdownList("tel_country",
                "${addressValue.faxCountry}" != '' ? "${addressValue.faxCountry}" : "852");
        $("#${id}_faxCountry_input").val("${addressValue.faxCountry}" != '' ? "${addressValue.faxCountry}" : "852");
    }

    if ("${showTemplate}" == "freeBox") {
        $('#${id}_phoneCountry').dropdownList("tel_country",
                "${addressValue.phoneCountry}" != '' ? "${addressValue.phoneCountry}" : "");
        $("#${id}_phoneCountry_input").val("${addressValue.phoneCountry}" != '' ? "${addressValue.phoneCountry}" : "");

        $('#${id}_faxCountry').dropdownList("tel_country",
                "${addressValue.faxCountry}" != '' ? "${addressValue.faxCountry}" : "");
        $("#${id}_faxCountry_input").val("${addressValue.faxCountry}" != '' ? "${addressValue.faxCountry}" : "");
    }

    $("#${id}_phoneCountry").select2("destroy").select2();
    $("#${id}_faxCountry").select2("destroy").select2();

    if ("${telAndFaxReadonly}" == "readonly") {
        $('#${id}_phoneCountry').select2('readonly', true);
        $('#${id}_faxCountry').select2("readonly", true);
    }

    if ("${hasTelCountry}" == "true" || "${hasFaxCountry}" == "true") {
        $(document).on('change', '#${id}_country', function () {
            var _value = $(this).val();
            if (_value != undefined && _value != null && _value != "") {
                var _country = getMasterAdditionValue('address_country', _value, 1);
                $('#${id}_phoneCountry').select2('val', _country);
                $("#${id}_phoneCountry_input").val(_country);
                $('#${id}_faxCountry').select2('val', _country);
                $("#${id}_faxCountry_input").val(_country);
            }
        });
    }

    $("#${id}_phoneCountry").change(function () {
        $("#${id}_phoneCountry_input").val($("#${id}_phoneCountry").val());
    })

    $("#${id}_faxCountry").change(function () {
        $("#${id}_faxCountry_input").val($("#${id}_faxCountry").val());
    })
})
<%--========================== tel/fax number js ==================================--%>

<%--========================== radio js ==================================--%>
$(function () {
    if ("${showTemplate}" == "radio") {
        if ("${roleType}" == "member") {
            if ('${addressValue.tempCode}' == "local_zh" || '${addressValue.tempCode}' == "china_zh" || '${addressValue.tempCode}' == "overseas_zh") {
                $("#${id}_language").dropdownList("address_language", 'chinese');
                $("label[id='languageLabel']").html("语言");
                showChineseAddress('${id}');
            } else if ('${addressValue.tempCode}' == "local_en" || '${addressValue.tempCode}' == "overseas_en") {
                $("#${id}_language").dropdownList("address_language", 'english');
                $("label[id='languageLabel']").html("language");
                showEnglishAddress('${id}');
            } else {
                $("#${id}_language").dropdownList("address_language", 'english');
                $("label[id='languageLabel']").html("language");
                showEnglishAddress('${id}');
            }

            $("#${id}_language").change(function () {
                if ($("#${id}_language").val() == "english") {
                    showEnglishAddress('${id}');
                    $("label[id='languageLabel']").html("language");
                    $("#${id}_local_en").click();
                } else if ($("#${id}_language").val() == "chinese") {
                    showChineseAddress('${id}');
                    $("label[id='languageLabel']").html("语言");
                    $("#${id}_local_zh").click();
                }
            })
        }

        if ("${radioReadonly}" == "readonly") {
            $('#${id}_country').select2('readonly', true);
            $('#${id}_location').select2("readonly", true);
        }

        var param = {
            id: '${id}',
            addressName: '${addressName}',

            roleType: '${roleType}',
            addressType: '${addressType}',
            tempCode: "${addressValue.tempCode}" != null ? "${addressValue.tempCode}" : "",

            leftCols: '${leftCols}',
            rightCols: '${rightCols}',
            radioReadonly: '${radioReadonly}',
            freeBoxReadonly: '${freeBoxReadonly}',
            changeFormatRadioReadonly: '${changeFormatRadioReadonly}',
            changeFormatFreeBoxReadonly: '${changeFormatFreeBoxReadonly}',

            editCompanyName: "${addressValue.companyName}" != null ? "${addressValue.companyName}" : "",
            editCountry: "${addressValue.countryUid}" != null ? "${addressValue.countryUid}" : "",
            editAttentionTo: "${addressValue.attentionTo}" != null ? "${addressValue.attentionTo}" : "",
            editFiled1Address: "${addressValue.field1Address}" != null ? "${addressValue.field1Address}" : "",
            editFiled2Address: "${addressValue.field2Address}" != null ? "${addressValue.field2Address}" : "",
            editFiled3Address: "${addressValue.field3Address}" != null ? "${addressValue.field3Address}" : "",
            editFiled4Address: "${addressValue.field4Address}" != null ? "${addressValue.field4Address}" : "",
            editFiled5Address: "${addressValue.field5Address}" != null ? "${addressValue.field5Address}" : ""
        };

        if (param.tempCode == '') {
            $(":radio[name='${addressName}.tempCode'][value='local_en']").attr("checked", "true");
            param.tempCode = 'local_en';
        }

        changeTemplate(param);
        $("input[name='${addressName}.tempCode']").change(function () {
            param.tempCode = $(this).val();
            param.editCompanyName = "";
            param.editCountry = "";
            param.editAttentionTo = "";
            param.editFiled1Address = "";
            param.editFiled2Address = "";
            param.editFiled3Address = "";
            param.editFiled4Address = "";
            param.editFiled5Address = "";
            changeTemplate(param);
            if ("${hasTelCountry}" == "true" || "${hasFaxCountry}" == "true") {
                if ($(this).val() == "local_en" || $(this).val() == "local_zn") {
                    var _country = getMasterAdditionValue('address_country', "Hong_Kong", 1);
                    $('#${id}_phoneCountry').select2('val', _country);
                    $("#${id}_phoneCountry_input").val(_country);
                    $('#${id}_faxCountry').select2('val', _country);
                    $("#${id}_faxCountry_input").val(_country);
                } else if ($(this).val() == "china_zh") {
                    var _country = getMasterAdditionValue('address_country', "China", 1);
                    $('#${id}_phoneCountry').select2('val', _country);
                    $("#${id}_phoneCountry_input").val(_country);
                    $('#${id}_faxCountry').select2('val', _country);
                    $("#${id}_faxCountry_input").val(_country);
                }
            }
        });
    }

    function changeTemplate(param) {
        $.ajax({
            type: "POST",
            async: false,
            url: "${ctx}/common/common-address/ajax/refresh-address",
            data: {
                'id': param.id,
                'addressName': param.addressName,

                'roleType': param.roleType,
                'addressType': param.addressType,
                'tempCode': param.tempCode,

                'leftCols': param.leftCols,
                'rightCols': param.rightCols,
                'radioReadonly': param.radioReadonly,
                'freeBoxReadonly': param.freeBoxReadonly,

                'companyName': param.editCompanyName,
                'country': param.editCountry,
                'attentionTo': param.editAttentionTo,
                'filed1Address': param.editFiled1Address,
                'filed2Address': param.editFiled2Address,
                'filed3Address': param.editFiled3Address,
                'filed4Address': param.editFiled4Address,
                'filed5Address': param.editFiled5Address
            },
            success: function (response) {
                $("#${id}_address_div").html(response);
            }
        })
    }

    function showEnglishAddress(id) {
        $("#" + id + "_english").removeClass("hidden");
        $("#" + id + "_chinese").addClass("hidden");
    }

    function showChineseAddress(id) {
        $("#" + id + "_english").addClass("hidden");
        $("#" + id + "_chinese").removeClass("hidden");
    }
})
<%--========================== radio js end ==================================--%>

<%--========================== free box js ==================================--%>
$(function () {
    if ("${showTemplate}" == "freeBox") {
        if ('${addressValue.countryUid}' == undefined || '${addressValue.countryUid}' == null || '${addressValue.countryUid}' == '') {
            $('#${id}_country').dropdownList('address_country', '');
            $("#${id}_country_input").val('');
        } else {
            $('#${id}_country').dropdownList('address_country', '${addressValue.countryUid}');
            $("#${id}_country_input").val('${addressValue.countryUid}');
        }
        if ('${addressValue.locationUid}' == undefined || '${addressValue.locationUid}' == null || '${addressValue.locationUid}' == '') {
            $("#${id}_location").dropdownList('address_location', '');
            $("#${id}_location_input").val('');
        } else {
            $("#${id}_location").dropdownList('address_location', '${addressValue.locationUid}');
            $("#${id}_location_input").val('${addressValue.locationUid}');
        }

        $("#${id}_country").select2("destroy").select2();
        $("#${id}_location").select2("destroy").select2();

        if ("${addressType}" == 'BUS') {
            $('#${id}_company_div').show();
            $('#${id}_attentionTo_div').show();
        } else {
            $('#${id}_company_div').hide();
            $('#${id}_attentionTo_div').hide();
        }

        if ("${roleType}" == "student") {
            $('#${id}_company_div').hide();
            $('#${id}_attentionTo_div').hide();
        }

        if ("${addressType}" == 'BOX') {
            $('#${id}_country_div').hide();
            $('#${id}_attentionTo_div').hide();
            $('#${id}_address_box').hide();
        }

        if ("${addressType}" == 'CPA_REG1B_ENG' || "${addressType}" == 'CPA_REG1B_CHI' || "${addressType}" == 'CPA_REG1A'
                || "${addressType}" == 'RO_ENG' || "${addressType}" == 'RO_CHI') {
            if ("${addressType}" == 'CPA_REG1B_CHI' || "${addressType}" == 'RO_CHI') {
                $('#${id}_address1_lbl').html("Chi Address");
            } else if ("${addressType}" == 'CPA_REG1B_ENG' || "${addressType}" == 'RO_ENG') {
                $('#${id}_address1_lbl').html("Eng Address");
            }
        }

        if ("${addressType}" == 'PC') {
            $('#${id}_location').parent().parent().empty();
            $('#${id}_attentionTo_div').empty();
            $('#${id}_country_div').empty();
        }

        if ("${freeBoxReadonly}" == "readonly") {
            $('#${id}_country').select2('readonly', true);
            $('#${id}_location').select2("readonly", true);
        }

        $("#${id}_country").change(function () {
            $("#${id}_country_input").val($("#${id}_country").val());
        })

        $("#${id}_location").change(function () {
            $("#${id}_location_input").val($("#${id}_location").val());
        })
    }
})

<%--========================== free box js end==================================--%>

<%--========================== textarea js ==================================--%>
$(function () {
    if ("${showTemplate}" == "textarea") {
        $("#${id}_field1Address").val("${addressValue.field1Address}");

        if ('${addressValue.locationUid}' == undefined || '${addressValue.locationUid}' == null || '${addressValue.locationUid}' == '') {
            $("#${id}_location").dropdownList('address_location', '');
            $("#${id}_location_input").val('');
        } else {
            $("#${id}_location").dropdownList('address_location', '${addressValue.locationUid}');
            $("#${id}_location_input").val('${addressValue.locationUid}');
        }

        $("#${id}_location").select2("destroy").select2();

        if ("${textareaReadonly}" == "readonly") {
            $('#${id}_location').select2("readonly", true);
        }

        $("#${id}_location").change(function () {
            $("#${id}_location_input").val($("#${id}_location").val());
        })
    }


})
<%--========================== textarea js end==================================--%>

<%--========================== button js==================================--%>
$(function () {
    if ("${changeButton}" == "true") {
        $("#${id}_change_button").click(function () {
            var _tempCode = $(":radio[name='${addressName}.tempCode']:checked").val();
            var param = {
                id: '${id}',
                roleType: '${roleType}',
                addressType: '${addressType}',
                tempCode: _tempCode,
                radioReadonly: '${radioReadonly}',
                freeBoxReadonly: '${freeBoxReadonly}',
                changeFormatRadioReadonly: '${changeFormatRadioReadonly}',
                changeFormatFreeBoxReadonly: '${changeFormatFreeBoxReadonly}',
                showTemplate: "${showTemplate}",
                showChangeTemplate: "${showChangeTemplate}"
            }
            changeFormat(param);
        })
    }

    if ("${clearButton}" == "true") {
        $("#${id}_clear_button").click(function () {
            if ("${showTemplate}" == "radio") {

            }
            if ("${showTemplate}" == "freeBox") {
                $('#${id}_country').select2('val', '');
                $('#${id}_country_input').val('');
                $('#${id}_field1Address').val('');
                $('#${id}_field2Address').val('');
                $('#${id}_field3Address').val('');
                $('#${id}_field4Address').val('');
                $('#${id}_field5Address').val('');
                $('#${id}_location').select2('val', '');
                $('#${id}_location_input').val('');
            }
        })
    }
})

function changeFormat(param) {
    matrix.ajaxPopupWindow({
        name: 'Address Input',
        url: '${ctx}/common/common-address/ajax/popup',
        params: {
            addressId: param.id,
            roleType: param.roleType,
            addressType: param.addressType,
            tempCode: param.tempCode,
            radioReadonly: param.radioReadonly,
            freeBoxReadonly: param.freeBoxReadonly,
            changeFormatRadioReadonly: param.changeFormatRadioReadonly,
            changeFormatFreeBoxReadonly: param.changeFormatFreeBoxReadonly,
            showTemplate: param.showTemplate,
            showChangeTemplate: param.showChangeTemplate
        }
    });
}

<%--========================== change button js end==================================--%>

<%--========================== validate js ==================================--%>
//radio validate
function ${id}RadioValidator(isValid) {
    if (isValid) {
        if (!($('#${id}_country').hasClass('required'))) {
            $('#${id}_country').addClass('required');
        }
        if (!($('#${id}_location').hasClass('required'))) {
            $('#${id}_location').addClass('required');
        }
        if (!($('#${id}_field1Address').hasClass('required'))) {
            $('#${id}_field1Address').addClass('required');
        }
        if (!($('#${id}_field2Address').hasClass('required'))) {
            $('#${id}_field2Address').addClass('required');
        }
    } else {
        if (($('#${id}_country').hasClass('required'))) {
            $('#${id}_country').removeClass('required');
        }
        if (($('#${id}_location').hasClass('required'))) {
            $('#${id}_location').removeClass('required');
        }
        if ($('#${id}_field1Address').hasClass('required')) {
            $('#${id}_field1Address').removeClass('required');
        }
        if ($('#${id}_field2Address').hasClass('required')) {
            $('#${id}_field2Address').removeClass('required');
        }
    }
}

//freeBox validate
function ${id}FreeBoxValidator(isValid) {
    if (isValid) {
        if (!($('#${id}_country').hasClass('required'))) {
            $('#${id}_country').addClass('required');
        }
        if (!($('#${id}_location').hasClass('required'))) {
            $('#${id}_location').addClass('required');
        }
        if (!($('#${id}_field1Address').hasClass('required'))) {
            $('#${id}_field1Address').addClass('required');
        }
        if (!($('#${id}_field2Address').hasClass('required'))) {
            $('#${id}_field2Address').addClass('required');
        }
    } else {
        if (($('#${id}_country').hasClass('required'))) {
            $('#${id}_country').removeClass('required');
        }
        if (($('#${id}_location').hasClass('required'))) {
            $('#${id}_location').removeClass('required');
        }
        if ($('#${id}_field1Address').hasClass('required')) {
            $('#${id}_field1Address').removeClass('required');
        }
        if ($('#${id}_field2Address').hasClass('required')) {
            $('#${id}_field2Address').removeClass('required');
        }
    }
}

//readonly js
function ${id}FreeBoxReadonly(isReadonly) {
    if (isReadonly) {
        $('#${id}_country').select2('readonly', true);
        $('#${id}_field1Address').attr('readonly', true);
        $('#${id}_field2Address').attr('readonly', true);
        $('#${id}_field3Address').attr('readonly', true);
        $('#${id}_field4Address').attr('readonly', true);
        $('#${id}_field5Address').attr('readonly', true);
        $('#${id}_location').select2("readonly", true);
    } else {
        $('#${id}_country').select2('readonly', false);
        $('#${id}_field1Address').attr('readonly', false);
        $('#${id}_field2Address').attr('readonly', false);
        $('#${id}_field3Address').attr('readonly', false);
        $('#${id}_field4Address').attr('readonly', false);
        $('#${id}_field5Address').attr('readonly', false);
        $('#${id}_field1Address').attr('disabled', false);
        $('#${id}_field2Address').attr('disabled', false);
        $('#${id}_field3Address').attr('disabled', false);
        $('#${id}_field4Address').attr('disabled', false);
        $('#${id}_field5Address').attr('disabled', false);
        $('#${id}_location').select2("readonly", false);
    }
}

</script>