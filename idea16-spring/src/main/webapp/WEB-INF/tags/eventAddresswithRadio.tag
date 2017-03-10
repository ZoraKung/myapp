<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="Address Id Prefix" %>
<%@ attribute name="roleType" type="java.lang.String" required="true" description="Address Role Type" %>
<%@ attribute name="addressName" type="java.lang.String" required="true" description="Address of Name" %>
<%@ attribute name="addressValue" type="org.hkicpa.mas.common.vo.AddressVo" required="false"
              description="Address of Value" %>
<%@ attribute name="addressType" type="java.lang.String" required="false" description="Address Type" %>
<%@ attribute name="disabled" type="java.lang.String" required="false" description="Address Ready Only" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Css Class" %>
<%@ attribute name="leftCols" type="java.lang.String" required="false" description="Left Cols" %>
<%@ attribute name="rightCols" type="java.lang.String" required="false" description="Right Cols" %>

<c:if test="${leftCols == null}"><c:set var="leftCols" value="col-sm-3"/></c:if>
<c:if test="${rightCols == null}"><c:set var="rightCols" value="col-sm-9"/></c:if>

<c:if test="${addressType == 'BUS'}">
        <span class="label label-success arrowed-in-right label-lg">
             <b>Business address</b>
        </span>
</c:if>
<c:if test="${addressType == 'RES'}">
        <span class="label label-primary arrowed-in-right label-lg">
             <b>Residential address</b>
        </span>
</c:if>

<c:if test="${roleType == 'student'}">
    <div class="form-group">
        <label class="${leftCols} control-label"></label>

        <div class="${rightCols}">
            <label>
                <input type="radio" class="ace" id="${id}_local_en" name="${addressName}.tempCode"
                       value="local_en" ${addressValue.tempCode == 'local_en' ? 'checked': ''} ${disabled}/>
                <span class="lbl">Hong Kong</span>
            </label>&nbsp&nbsp&nbsp
            <label>
                <input type="radio" class="ace" id="${id}_china_zh" name="${addressName}.tempCode"
                       value="china_zh" ${addressValue.tempCode == 'china_zh' ? 'checked': ''} ${disabled}/>
                <span class="lbl">China</span>
            </label>&nbsp&nbsp&nbsp
            <label>
                <input type="radio" class="ace" id="${id}_overseas_en" name="${addressName}.tempCode"
                       value="overseas_en" ${addressValue.tempCode == 'overseas_en' ? 'checked': ''} ${disabled}/>
                <span class="lbl">Overseas</span>
            </label>
        </div>
    </div>
</c:if>

<c:if test="${roleType == 'other'}">
    <div class="form-group">
        <label class="${leftCols} control-label">Language </label>

        <div class="${rightCols}">
            <select name="${addressName}.language" maxlength="10" cssClass="select2 input-medium"
                    id="${id}_language"></select>
        </div>
    </div>

    <div class="form-group">
        <label class="${leftCols} control-label"></label>

        <div class="${rightCols}">
            <div id="${id}_english">
                <label>
                    <input type="radio" class="ace" id="${id}_local_en" name="${addressName}.tempCode"
                           value="local_en" ${addressValue.tempCode == 'local_en' ? 'checked': ''} ${disabled}/>
                    <span class="lbl">Local</span>
                </label>&nbsp&nbsp&nbsp
                <label>
                    <input type="radio" class="ace" id="${id}_overseas_en" name="${addressName}.tempCode"
                           value="overseas_en" ${addressValue.tempCode == 'overseas_en' ? 'checked': ''} ${disabled}/>
                    <span class="lbl"> Oversea </span>
                </label>&nbsp&nbsp&nbsp
            </div>

            <div id="${id}_chinese">
                <label>
                    <input type="radio" class="ace" id="${id}_local_zh" name="${addressName}.tempCode"
                           value="local_zh" ${addressValue.tempCode == 'local_zh' ? 'checked': ''} ${disabled}/>
                    <span class="lbl">本地</span>
                </label>&nbsp&nbsp&nbsp
                <label>
                    <input type="radio" class="ace" id="${id}_china_zh" name="${addressName}.tempCode"
                           value="china_zh" ${addressValue.tempCode == 'china_zh' ? 'checked': ''} ${disabled}/>
                    <span class="lbl"> 中国 </span>
                </label>&nbsp&nbsp&nbsp
                <label>
                    <input type="radio" class="ace" id="${id}_overseas_zh" name="${addressName}.tempCode"
                           value="overseas_zh" ${addressValue.tempCode == 'overseas_zh' ? 'checked': ''} ${disabled}/>
                    <span class="lbl">海外</span>
                </label>
            </div>

        </div>
    </div>
</c:if>


<div id="${id}_address_div">
</div>
<script>
    $(function () {
        if ('${addressValue.tempCode}' == "local_zh" || '${addressValue.tempCode}' == "china_zh" || '${addressValue.tempCode}' == "overseas_zh") {
            $("#${id}_language").dropdownList("address_language", 'chinese');
            showChineseAddress('${id}');
        } else if ('${addressValue.tempCode}' == "local_en" || '${addressValue.tempCode}' == "overseas_en") {
            $("#${id}_language").dropdownList("address_language", 'english');
            showEnglishAddress('${id}');
        } else {
            $("#${id}_language").dropdownList("address_language", 'english');
            showEnglishAddress('${id}');
        }
    })

    $("#${id}_language").change(function () {
        if ($("#${id}_language").val() == "english") {
            showEnglishAddress('${id}');
            $("#${id}_local_en").click();
        } else if ($("#${id}_language").val() == "chinese") {
            showChineseAddress('${id}');
            $("#${id}_local_zh").click();
        }
    })

    function showEnglishAddress(id){
        $("#" + id + "_english").removeClass("hidden");
        $("#" + id + "_chinese").addClass("hidden");
    }

    function showChineseAddress(id){
        $("#" + id + "_english").addClass("hidden");
        $("#" + id + "_chinese").removeClass("hidden");
    }

    $(function () {
        var tempCode = "${addressValue.tempCode}";
        var editCompanyName = "";
        var editCountry = "";
        var editAttentionTo = "";
        var editFiled1Address = "";
        var editFiled2Address = "";
        var editFiled3Address = "";
        var editFiled4Address = "";
        var editFiled5Address = "";

        if (tempCode == '') {
            $(":radio[name='${addressName}.tempCode'][value='local_en']").attr("checked", "true")
        } else {
            editCountry = "${addressValue.countryUid}";
            editAttentionTo = "${addressValue.attentionTo}";
            editFiled1Address = "${addressValue.field1Address}";
            editFiled2Address = "${addressValue.field2Address}";
            editFiled3Address = "${addressValue.field3Address}";
            editFiled4Address = "${addressValue.field4Address}";
            editFiled5Address = "${addressValue.field5Address}";
        }
        if ($("input[name='${addressName}.tempCode']:checked").val()) {
            var selectRadio = $("input[name='${addressName}.tempCode']:checked").val();
            var addressType = '${addressType}';
            var leftCols = '${leftCols}';
            var rightCols = '${rightCols}';
            var id = '${id}';
            var disabled = '${disabled}';
            var addressName = '${addressName}';
            if (addressType == 'BUS') {
                editCompanyName = "${addressValue.companyName}"
            }
            $.ajax({
                type: "POST",
                async: false,
                url: "${ctx}/common/address/ajax/refresh-address",
                data: {
                    'id': id,
                    'tempCode': tempCode,
                    'country': editCountry,
                    'leftCols': leftCols,
                    'rightCols': rightCols,
                    'selectRadio': selectRadio,
                    'disabled': disabled,
                    'addressType': addressType,
                    'addressName': addressName,
                    'companyName': editCompanyName,
                    'attentionTo': editAttentionTo,
                    'filed1Address': editFiled1Address,
                    'filed2Address': editFiled2Address,
                    'filed3Address': editFiled3Address,
                    'filed4Address': editFiled4Address,
                    'filed5Address': editFiled5Address
                },
                success: function (response) {
                    $("#${id}_address_div").html(response);
                }
            })
        }

        $("input[name='${addressName}.tempCode']").change(function () {
            var selectRadio = $(this).val();
            var changeCompanyName = "";
            var changeCountry = "";
            var changeAttentionTo = "";
            var changeFiled1Address = "";
            var changeFiled2Address = "";
            var changeFiled3Address = "";
            var changeFiled4Address = "";
            var changeFiled5Address = "";
            var addressType = '${addressType}';
            var leftCols = '${leftCols}';
            var rightCols = '${rightCols}';
            var id = '${id}';
            var disabled = '${disabled}';
            var addressName = '${addressName}';

            if (tempCode == selectRadio) {
                changeCountry = editCountry;
                changeAttentionTo = editAttentionTo;
                changeFiled1Address = editFiled1Address;
                changeFiled2Address = editFiled2Address;
                changeFiled3Address = editFiled3Address;
                changeFiled4Address = editFiled4Address;
                changeFiled5Address = editFiled5Address;
            }
            if (tempCode == selectRadio && addressType == 'BUS') {
                changeCompanyName = editCompanyName;
            }
            $.ajax({
                type: "POST",
                async: false,
                url: "${ctx}/common/address/ajax/refresh-address",
                data: {
                    'id': id,
                    'tempCode': tempCode,
                    'country': changeCountry,
                    'leftCols': leftCols,
                    'rightCols': rightCols,
                    'selectRadio': selectRadio,
                    'disabled': disabled,
                    'addressType': addressType,
                    'addressName': addressName,
                    'companyName': editCompanyName,
                    'attentionTo': changeAttentionTo,
                    'filed1Address': changeFiled1Address,
                    'filed2Address': changeFiled2Address,
                    'filed3Address': changeFiled3Address,
                    'filed4Address': changeFiled4Address,
                    'filed5Address': changeFiled5Address
                },
                success: function (response) {
                    $("#${id}_address_div").html(response);
                }
            })
        });
    })
</script>