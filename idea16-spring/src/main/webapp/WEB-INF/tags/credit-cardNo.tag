<%--
  Created by IntelliJ IDEA.
  User: hzh
  Date: 2015/10/29
  Time: 17:40
  To change this template use File | Settings | File Templates.
--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="No" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="value" type="java.lang.String" required="false" description="Input Value" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Input Value" %>
<%@ attribute name="hideFlag" type="java.lang.String" required="false" description="Input Value" %>

<div class="controls">
    <input style="width:58px;text-align:center" type="text" id="${id}first" minlength="4" maxlength="4"
           class="input-sm" ${css}
           onkeyup="${id}changeFirst();" onKeypress="return (/[\d.*]/.test(String.fromCharCode(event.keyCode)))"
           style="ime-mode:Disabled"/>
    - <input style="width:58px;text-align:center" type="text" id="${id}second" minlength="4" maxlength="4"
             class="input-sm"  ${css} onkeyup="${id}changeSecond();"
             onKeypress="return (/[\d.*]/.test(String.fromCharCode(event.keyCode)))"
             style="ime-mode:Disabled"/>
    - <input style="width:58px;text-align:center" type="text" id="${id}third" minlength="4" maxlength="4"
             class="input-sm " ${css} onkeyup="${id}changeThird();"
             onKeypress="return (/[\d.*]/.test(String.fromCharCode(event.keyCode)))"
             style="ime-mode:Disabled"/>
    - <input style="width:58px;text-align:center" type="text" id="${id}fourth" minlength="4" maxlength="7"
             class="input-sm " ${css} onkeyup="${id}changeFourth();"
             onKeypress="return (/[\d.*]/.test(String.fromCharCode(event.keyCode)))"
             style="ime-mode:Disabled"/>
    <input id="${id}" name="${name}" type="hidden" value="${value}"/>
</div>
<script type="text/javascript">
    function ${id}changeFirst() {
        if ($("#${id}first").val().length == 4) {
            $("#${id}second").focus();
        }
        ${id}getValue();
        //todo
    }
    function ${id}changeSecond() {
        if ($("#${id}second").val().length == 4) {
            $("#${id}third").focus();
        }
        ${id}getValue();
        //todo
    }
    function ${id}changeThird() {
        if ($("#${id}third").val().length == 4) {
            $("#${id}fourth").focus();
        }
        ${id}getValue();
        //todo
    }
    function ${id}changeFourth() {
        ${id}getValue();
        //todo
    }
    function ${id}getValue() {
        var val1 = $("#${id}first").val().trim().toString();
        var val2 = $("#${id}second").val().trim().toString();
        var val3 = $("#${id}third").val().trim().toString();
        var val4 = $("#${id}fourth").val().trim().toString();
        $("#${id}").val(val1 + val2 + val3 + val4);
    }
    $(function () {
        var creditValue = '${value}';
        var flag = '${!empty value}';
        if (flag) {
            $("#${id}first").val(creditValue.substr(0, 4));
            $("#${id}second").val(creditValue.substr(4, 4));
            $("#${id}third").val(creditValue.substr(8, 4));
            $("#${id}fourth").val(creditValue.substr(12, 7));
        }
    });
</script>
