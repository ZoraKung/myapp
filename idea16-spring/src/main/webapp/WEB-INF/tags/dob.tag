<%@ tag language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="No" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="value" type="java.util.Date" required="false" description="Input Value" %>
<%@ attribute name="ageId" type="java.lang.String" required="false" description="Age Value" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Input Value" %>
<%@ attribute name="readonly" type="java.lang.String" required="false" description="Input Value" %>
<fmt:formatDate value="${value}" pattern="dd-MM-yyyy" var="dueValue"/>
<input id="${id}" name="${name}" type="hidden" value="${dueValue}"/>

<div class="controls">
    <select style="width:70px" id="${id}dateyear"  ${readonly} class="${css}"
            onchange="${id}changedate('${id}dateyear','${id}datemonth','${id}dateday'); ">
        <option value=""></option>
    </select>-
    <select style="width:60px" id="${id}datemonth" ${readonly} class="${css}"
            onchange="${id}changedate('${id}dateyear','${id}datemonth','${id}dateday');">
        <option value=""></option>
    </select>-
    <select style="width:60px" id="${id}dateday" ${readonly}
            onchange="${id}changeday();" class="${css}">
        <option value=""></option>
    </select>
</div>
<script type="text/javascript">
    $(function () {
        var ${id}defaultDate = $('#${id}').val();
        if (${id}defaultDate != null && ${id}defaultDate != "") {
            var ${id}defaultYear = ${id}defaultDate.split('-')[2];
            var ${id}defaultMonth = ${id}defaultDate.split('-')[1];
            var ${id}defaultDay = ${id}defaultDate.split('-')[0];
            ${id}inityear($("#${id}dateyear"), ${id}defaultYear);
            ${id}initmonth($("#${id}datemonth"), ${id}defaultMonth);
            var days = ${id}getDaysByYearAndMonth(${id}defaultYear, ${id}defaultMonth);
            ${id}initday($("#${id}dateday"), days, ${id}defaultDay);
        } else {
            ${id}inityear($("#${id}dateyear"));
            ${id}initmonth($("#${id}datemonth"));
            //bt:13437
            return;
        }
        ${id}calculateAge();
    });
    function ${id}inityear(jqueryObj, defaultValue) {
        defaultValue = (defaultValue == undefined) ? "0" : defaultValue;
        var currentYear = (new Date()).getFullYear();
        for (var i = currentYear - 100; i <= currentYear; i++) {
            jqueryObj.append("<option value='" + i + ((i != defaultValue) ? "'>" : "' selected>") + i + "</option>");
        }
        if (defaultValue != "0") {
            ${id}calculateAge();
        }
    }
    function ${id}initmonth(jqueryObj, defaultValue) {
        defaultValue = (defaultValue == undefined) ? "0" : defaultValue;
        for (var i = 1; i <= 12; i++) {
            jqueryObj.append("<option value='" + ((i < 10) ? ("0" + i) : i) + ((i != defaultValue) ? "'>" : "' selected>") + ((i < 10) ? ("0" + i) : i) + "</option>");
        }
    }
    function ${id}initday(jqueryObj, days, defaultValue) {
        jqueryObj[0].options.length = 1;
        days = (days == undefined) ? 31 : days;
        defaultValue = (defaultValue == undefined) ? "0" : defaultValue;
        for (var i = 1; i <= days; i++) {
            jqueryObj.append("<option value='" + ((i < 10) ? ("0" + i) : i) + ((i != defaultValue) ? "'>" : "' selected>") + ((i < 10) ? ("0" + i) : i) + "</option>");
        }
    }
    function ${id}getDaysByYearAndMonth(year, month){
        var isLeapYear = ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? true : false;
        var daysOfMonth = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        daysOfMonth[1] = isLeapYear ? 29 : 28;
        return daysOfMonth[month - 1];
    }

    function ${id}changedate(yearId, monthId, dayId) {
        var jqyear = $("#" + yearId);
        var jqmonth = $("#" + monthId);
        var jqday = $("#" + dayId);
        var year = parseInt(jqyear.val(), 10);
        var month = parseInt(jqmonth.val(), 10);
        var day = parseInt(jqday.val(), 10);
        if (isNaN(year)) {
            jqday.val('');
            jqday.empty();
            jqmonth.val('');
            return;
        }
        if (isNaN(month)) {
            jqday.val('');
            jqday.empty();
            return;
        }

        var days = ${id}getDaysByYearAndMonth(year, month);

        ${id}initday(jqday, days, day);
        $('#${id}').val(($("#${id}dateday").val()||'01') + "-" + $("#${id}datemonth").val() + "-" + $("#${id}dateyear").val());
        if (${not empty ageId}) {
            ${id}calculateAge();
        }
    }
    function ${id}changeday() {
        $('#${id}').val(($("#${id}dateday").val()||'01') + "-" + $("#${id}datemonth").val() + "-" + $("#${id}dateyear").val());
        if (${not empty ageId}) {
            ${id}calculateAge();
        }
    }
    function ${id}calculateAge() {
        var birthday = new Date($("#${id}dateyear").val(), $("#${id}datemonth").val(), $("#${id}dateday").val());
        var day = new Date();
        if (birthday && day) {
            $('#${ageId}').val(Math.floor(monthBetween(birthday, day) / 12));
            /*$('#${ageId}').val(Math.floor((day.getTime() - birthday.getTime()) / (1000 * 3600 * 24 * 365)));*/
        } else {
            $('#${ageId}').val('0');
        }
    }
    function ${id}SetValue(dob){
        if (dob != null && dob != "") {
            var _defaultYear = dob.split('-')[2];
            var _defaultMonth = dob.split('-')[1];
            var _defaultDay = dob.split('-')[0];
            ${id}inityear($("#${id}dateyear"), _defaultYear);
            ${id}initmonth($("#${id}datemonth"), _defaultMonth);
            var _days = ${id}getDaysByYearAndMonth(_defaultYear, _defaultMonth);
            ${id}initday($("#${id}dateday"), _days, _defaultDay);
            $("#${id}").val(dob);
        } else {
            ${id}inityear($("#${id}dateyear"));
            ${id}initmonth($("#${id}datemonth"));
        }
    }
</script>