<%@ tag language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="No" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="value" type="java.lang.String" required="false" description="Input Value" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Input Value" %>
<%@ attribute name="hideFlag" type="java.lang.String" required="false" description="Input Value" %>
<%@ attribute name="checkId" type="java.lang.String" required="false" description="Input Value" %>
<style type="text/css">
    .help-block {
        color: #d16e6c !important;
    }
</style>
<div class="${hideFlag}">
    <div class="controls">
        <input style="width:40px" type="text" id="${id}first" minlength="1" maxlength="2" class="input-sm text" ${css} onchange="${id}changefirst();"/>
        <input style="width:75px" type="text" id="${id}secord" minlength="6" maxlength="6" class="input-sm digits"  ${css} onchange="${id}changesecord();"/>
        <input style="width:30px" type="text" id="${id}third" minlength="1" maxlength="1" class="input-sm text " ${css} ${checkId} onchange="${id}changethird();"/>
        <button class="btn btn-xs" type="button" id="${id}btn_check_hkid">Check</button>
        <input id="${id}" name="${name}" type="hidden" value="${value}" hkid="#${id}"/>
    </div>
</div>


<script type="text/javascript">
    $(function(){
    	var ${id}defaultValue = $('#${id}').val();
    	${id}SetValue(${id}defaultValue);
    });
    function ${id}SetValue(${id}defaultValue){
        if (${id}defaultValue != undefined && ${id}defaultValue != "" && (${id}defaultValue.length == 11 || ${id}defaultValue.length == 10)){
            var ${id}defaultFirst = "";
            var ${id}defaultSecord = "";
            var ${id}defaultThird = "";
            if(${id}defaultValue.length == 11){
                ${id}defaultFirst = ${id}defaultValue.substr(0,2);
                ${id}defaultSecord = ${id}defaultValue.substr(2,6);
                ${id}defaultThird = ${id}defaultValue.substr(9,1);
            }
            if(${id}defaultValue.length == 10){
                ${id}defaultFirst = ${id}defaultValue.substr(0,1);
                ${id}defaultSecord = ${id}defaultValue.substr(1,6);
                ${id}defaultThird = ${id}defaultValue.substr(8,1);
            }
            $('#${id}first').val(${id}defaultFirst);
            $('#${id}secord').val(${id}defaultSecord);
            $('#${id}third').val(${id}defaultThird);
            $('#${id}').val(${id}defaultValue);
        }else {
        	$('#${id}first').val("");
            $('#${id}secord').val("");
            $('#${id}third').val("");
            $('#${id}').val("");
        }
    }
    function ${id}changefirst(){
        var ${id}firstValue = $("#${id}first").val();
        if (${id}firstValue != undefined && ${id}firstValue != ''){
            ${id}firstValue = ${id}firstValue.toUpperCase();
            $("#${id}first").val(${id}firstValue);
        }else{
            $("#${id}secord").val('');
            $("#${id}third").val('');
            $('#${id}').val('');
            return;
        }
        var ${id}secordValue = $("#${id}secord").val();
        var ${id}thirdValue = $("#${id}third").val();

        if (${id}firstValue != '' && ${id}secordValue != '' && ${id}thirdValue != ''){
            $('#${id}').val(${id}firstValue + ${id}secordValue + "(" + ${id}thirdValue + ")");
        }else{
            $('#${id}').val('');
        }
    }
    function ${id}changesecord(){
        var ${id}firstValue = $("#${id}first").val();

        var ${id}secordValue = $("#${id}secord").val();
        if (${id}secordValue != undefined && ${id}secordValue != ''){
            ${id}secordValue = ${id}secordValue.toUpperCase();
            $("#${id}secord").val(${id}secordValue);
        }else{
            $("#${id}first").val('');
            $("#${id}third").val('');
            $('#${id}').val('');
            return;
        }
        var ${id}thirdValue = $("#${id}third").val();

        if (${id}firstValue != '' && ${id}secordValue != '' && ${id}thirdValue != ''){
            $('#${id}').val(${id}firstValue + ${id}secordValue + "(" + ${id}thirdValue + ")");
        }else{
            $('#${id}').val('');
        }
    }
    function ${id}changethird(){
        var ${id}firstValue = $("#${id}first").val();
        var ${id}secordValue = $("#${id}secord").val();

        var ${id}thirdValue = $("#${id}third").val();
        if (${id}thirdValue != undefined && ${id}thirdValue != ''){
            ${id}thirdValue = ${id}thirdValue.toUpperCase();
            $("#${id}third").val(${id}thirdValue);
        }else{
            $("#${id}first").val('');
            $("#${id}secord").val('');
            $('#${id}').val('');
            return;
        }
        if (${id}firstValue != '' && ${id}secordValue != '' && ${id}thirdValue != ''){
            $('#${id}').val(${id}firstValue + ${id}secordValue + "(" + ${id}thirdValue + ")");
        }else{
            $('#${id}').val('');
        }
    }
    $("#${id}btn_check_hkid").click(function(){
        var $form = $(this).closest('form');
        if ($form != undefined && $form.length > 0){
            $form.validate().element($('#${id}third'));
            $form.validate().element($('#${id}'));
        }
    });
</script>