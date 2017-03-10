<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="prefix" type="java.lang.String" required="true" description="Autocomplete Id Prefix" %>
<%@ attribute name="type" type="java.lang.String" required="true" description="Autocomplete Type" %>
<%@ attribute name="value" type="java.lang.String" required="false" description="Autocomplete of Value" %>
<%@ attribute name="widthClass" type="java.lang.String" required="false" description="Autocomplete of Width" %>


<c:if test="${widthClass == null}"><c:set var="widthClass" value="input-medium" /></c:if>

<input id="${prefix}" type="text" class="required form-control ${widthClass}" name="${prefix}" value="${value}"/>

<script type="text/javascript">
    $.post(_appContext +"/common/service/auto-complete-simple/${type}", function (data, textStatus) {
        var availableTags = eval('(' + data + ')');
        $( "#${prefix}" ).autocomplete({
            source: availableTags
        });
    });
</script>