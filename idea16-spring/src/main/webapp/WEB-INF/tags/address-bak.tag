<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="prefix" type="java.lang.String" required="true" description="Address Id Prefix" %>
<%@ attribute name="addressValue" type="org.hkicpa.base.vo.AddressVo" required="false" description="Address of Value" %>
<%--<%@ attribute name="value" type="java.lang.String" required="true" description="Input Value" %>--%>
<%--<%@ attribute name="valueField" type="java.lang.String" required="true" description="Value Field" %>--%>
<%--<%@ attribute name="labelField" type="java.lang.String" required="true" description="Label Field" %>--%>
<%--<%@ attribute name="title" type="java.lang.String" required="false" description="Title" %>--%>
<%--<%@ attribute name="css" type="java.lang.String" required="false" description="Css" %>--%>
<%--<%@ attribute name="dataList" type="java.util.List" required="true" description="dataList" %>--%>
<%--<%@ attribute name="multiple" type="java.lang.Boolean"  required="true" description="Is Multiple" %>--%>

<%@ attribute name="leftCols" type="java.lang.String" required="false" description="Left Cols" %>
<%@ attribute name="rightCols" type="java.lang.String" required="false" description="Right Cols" %>
<%@ attribute name="align" type="java.lang.String" required="false" description="align-right" %>


<c:if test="${leftCols == null}"><c:set var="leftCols" value="col-sm-4" /></c:if>
<c:if test="${rightCols == null}"><c:set var="rightCols" value="col-sm-8" /></c:if>
<c:if test="${align == null}"><c:set var="align" value="align-right" /></c:if>

<div class="form-group">
    <label class="${leftCols} control-label ${align}" id="${prefix}_country_lbl">Country</label>
    <div class="${rightCols}">
        <select class="select2 input-large" name="country" id="${prefix}_country">
            <option value="">Please select</option>
            <%--<option value="HK">HK</option>--%>
            <%--<option value="CN">China</option>--%>
            <%--<option value="OT">Other</option>--%>
        </select>
    </div>
</div>
<div class="form-group">
    <label class="${leftCols} control-label ${align}"  id="${prefix}_district_lbl">District</label>

    <div class="${rightCols}">
        <select class="select2 input-large" name="district" id="${prefix}_district">
            <option value="Nil">Please select</option>
            <%--<option value="M">District 01</option>--%>
            <%--<option value="F">District 02</option>--%>
            <%--<option value="M">District 03</option>--%>
        </select>
    </div>
</div>
<div class="form-group">
    <label class="${leftCols} control-label ${align}" id="${prefix}_line1_lbl">Room, Floor, name of the Building</label>

    <div class="${rightCols}">
        <input type="text" id="${prefix}Line1" class="input-xlarge" placeholder="" name="line1"
               value="${addressValue.line1}">
    </div>
</div>
<div class="form-group">
    <label class="${leftCols} control-label ${align}" id="${prefix}_line2_lbl">Street No & name</label>

    <div class="${rightCols}">
        <input type="text" id="${prefix}Line2" class="input-xlarge" placeholder="" name="line2"
               value="${addressValue.line2}">
    </div>
</div>
<div class="form-group">
    <label class="${leftCols} control-label ${align}" id="${prefix}_line3_lbl">Postcode / Zipcode</label>

    <div class="${rightCols}">
        <input type="text" id="${prefix}Line3" class="input-xlarge" placeholder="" name="line3"
               value="${addressValue.line3}">
    </div>
</div>

<script type="text/javascript">
    function ${prefix}addressStart() {
        var countrySelectValue = "${addressValue.country}";
        var districtSelectValue = "${addressValue.district}";
        var countryId = "${prefix}_country";
        var districtId = "${prefix}_district";
        ${prefix}writeLable(countrySelectValue)
        ${prefix}writeSelectOptions(_appContext +"/common/service/label-value/COUNTRY",countryId,countrySelectValue);
        ${prefix}writeSelectOptions(_appContext +"/common/service/label-value/CITY/"+countrySelectValue,districtId,districtSelectValue);

    }
    if (document.attachEvent) {
        window.attachEvent("onload", ${prefix}addressStart);
    } else {
        window.addEventListener('load', ${prefix}addressStart, false);
    }
    function ${prefix}writeSelectOptions(url,inputId,selectValue){
        ${prefix}addressOptionsClear(inputId);
        $.post(url, function (data, textStatus) {
            var dataobj = eval('(' + data + ')');
            for(var key in dataobj){
                var opn ='<option value='+key+'>'+dataobj[key]+'</option>';
                if((selectValue != undefined || selectValue == "") && key == selectValue){
                    opn = '<option value='+key+' selected>'+dataobj[key]+'</option>';
                }
                $("#"+inputId).append(opn);
            }
        });
    }
    function ${prefix}addressOptionsClear(inputId) {
        $("#"+inputId).find('option').remove();
    }
    function ${prefix}writeLable(countrySelValue){
        if(countrySelValue == 'chn'){
            $("#${prefix}_country_lbl").html("国家");
            $("#${prefix}_district_lbl").html("省/直辖市/自治区");
            $("#${prefix}_line1_lbl").html("市/县/区");
            $("#${prefix}_line2_lbl").html("详细地址");
            $("#${prefix}_line3_lbl").html("邮政编码");
        }else{
            $("#${prefix}_country_lbl").html("Country");
            $("#${prefix}_district_lbl").html("District");
            $("#${prefix}_line1_lbl").html("Room, Floor, name of the Building");
            $("#${prefix}_line2_lbl").html("Street No & name");
            $("#${prefix}_line3_lbl").html("Postcode / Zipcode");
        }
    }
    $(function(){
        $("#${prefix}_country").bind('change', function(e){
            var districtId = "${prefix}_district";
            var countrySelectValue = $(this).val();
            ${prefix}writeLable(countrySelectValue);
            ${prefix}writeSelectOptions(_appContext +"/common/service/label-value/CITY/"+countrySelectValue,districtId);
        });
    });
</script>