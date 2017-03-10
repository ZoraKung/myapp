<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="prefix" type="java.lang.String" required="true" description="Prefix of Date Of Birth" %>
<%@ attribute name="dateValue" type="java.lang.String" required="false" description="Value of Date Of Birth" %>
<%@ attribute name="leftCols" type="java.lang.String" required="false" description="Left Cols" %>
<%@ attribute name="rightCols" type="java.lang.String" required="false" description="Right Cols" %>
<%@ attribute name="align" type="java.lang.String" required="false" description="align-right" %>

<c:if test="${leftCols == null}"><c:set var="leftCols" value="col-sm-4"/></c:if>
<c:if test="${rightCols == null}"><c:set var="rightCols" value="col-sm-8"/></c:if>
<c:if test="${align == null}"><c:set var="align" value="align-right"/></c:if>
<c:if test="${dateFormat == null}"><c:set var="dateFormat" value="YYYY-MM-DD"/></c:if>

<div class="form-group">
    <label class=" ${leftCols} control-label ${align}">Date of Birth </label>

    <div class="${rightCols} date-of-birth-select">
        <select name=YYYY id="${prefix}_YYYY" class="date-of-birth-year">
        <option value="">&nbsp;&nbsp;&nbsp;</option>
        </select>
        <select name=MM id="${prefix}_MM" class="date-of-birth-month">
        <option value="">&nbsp;&nbsp;&nbsp;</option>
        </select>
        <select name=DD id="${prefix}_DD" class="date-of-birth-day">
        <option value="">&nbsp;&nbsp;&nbsp;</option>
        </select>

        <input type="text" class="input-medium date-if-birth-edit hide" id="${prefix}_date_of_birth" name="${prefix}"
               value="${dateValue}"/>
    </div>
</div>


<script type="text/javascript">
    function ${prefix}YYYYMMDDstart() {
        MonHead = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
        var YY = $("#${prefix}_YYYY");
        var MM = $("#${prefix}_MM");
        var DD = $("#${prefix}_DD");
        var dateOfBirth = $("#${prefix}_date_of_birth");
        var dateValue = $("#${prefix}_date_of_birth").val();
        if (dateValue == undefined || dateValue == "") {
            dateValue = new Date();
        } else {
            dateValue = new Date(dateValue);
        }
        //year
        var YYYYvalue = dateValue.getFullYear();
        var YYYYnow = new Date().getFullYear();
        for (var i = (YYYYnow - 100); i < (YYYYnow - 20); i++) {
            YY.append("<option value='" + i + "'>" + i + "</option>");
        }
        YY.val(YYYYvalue);
        //month
        var MMvalue = dateValue.getMonth() + 1;
        for (var i = 1; i < 13; i++) {
            MM.append("<option value='" + i + "'>" + i + "</option>");
        }
        MM.val(MMvalue);
        //day
        var DDvalue = dateValue.getDate();
        ${prefix}writeDay(YYYYvalue, MMvalue, dateOfBirth, DD);
        DD.val(DDvalue);

        dateOfBirth.val(YYYYvalue + "/" + MMvalue + "/" + DDvalue);
    }

    if (document.attachEvent) {
        window.attachEvent("onload", ${prefix}YYYYMMDDstart);
    } else {
        window.addEventListener('load', ${prefix}YYYYMMDDstart, false);
    }

    function ${prefix}YYYYDD(e) {
        var eDate = $(this).closest(".date-of-birth-select").find(".hide");
        var MM = $(this).closest(".date-of-birth-select").find(".date-of-birth-month");
        var DD = $(this).closest(".date-of-birth-select").find(".date-of-birth-day");
        var MMvalue = MM.val();
        var YYYYvalue = $(this).val();
        if (MMvalue == "" || MMvalue == undefined) {
            ${prefix}optionsClear(DD);
            return;
        }
        ${prefix}writeDay(YYYYvalue, MMvalue, eDate, DD);
    }

    function ${prefix}MMDD(e) {
        var eDate = $(this).closest(".date-of-birth-select").find(".hide");
        var YYYY = $(this).closest(".date-of-birth-select").find(".date-of-birth-year");
//                document.getElementById('YYYY');
        var YYYYvalue = YYYY.val();
        var MMvalue = $(this).val();
        var DD = $(this).closest(".date-of-birth-select").find(".date-of-birth-day");
        if (YYYYvalue == "" || YYYYvalue == undefined) {
            ${prefix}optionsClear(DD);
            return;
        }
        ${prefix}writeDay(YYYYvalue, MMvalue, eDate, DD);
    }

    function ${prefix}DD(e) {
        var eDate = $(this).closest(".date-of-birth-select").find(".hide");
        ${prefix}setDateOfBirth(eDate);
    }

    function ${prefix}writeDay(YYYYvalue, MMvalue, eDate, eDay) {
        var n = MonHead[MMvalue - 1];
        if (MMvalue == 2 && ${prefix}IsPinYear(YYYYvalue)) {
            n++;
        }
//        eDay = $(DDId);
        ${prefix}optionsClear(eDay);
        for (var i = 1; i < (n + 1); i++) {
            eDay.append("<option value='" + i + "'>" + i + "</option>");
        }
        ${prefix}setDateOfBirth(eDate);
    }

    function ${prefix}IsPinYear(year)
    {
        return(0 == year % 4 && (year % 100 != 0 || year % 400 == 0));
    }

    function ${prefix}optionsClear(e) {
        if (e.get(0).options != undefined) {
            e.get(0).options.length = 1;
        }
    }

    function ${prefix}setDateOfBirth(e) {
        var YYYY = e.closest(".date-of-birth-select").find(".date-of-birth-year").val();
        var MM = e.closest(".date-of-birth-select").find(".date-of-birth-month").val();
        var DD = e.closest(".date-of-birth-select").find(".date-of-birth-day").val();

        if (YYYY == "" || YYYY == undefined || MM == "" || MM == undefined || DD == "" || DD == undefined) {
            e.val(undefined);
        } else {
            var dateOfBirth = YYYY + "/" + MM + "/" + DD;
            e.val(dateOfBirth);
        }
    }

    $(function () {
        $(".date-of-birth-year").bind('change', ${prefix}YYYYDD);
        $(".date-of-birth-month").bind('change', ${prefix}MMDD);
        $(".date-of-birth-day").bind('change', ${prefix}DD);

    });
</script>