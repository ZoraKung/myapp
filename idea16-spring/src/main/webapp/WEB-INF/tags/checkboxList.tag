<%@ tag import="com.wjxinfo.core.base.utils.StringUtils" %>
<%@ tag import="com.wjxinfo.common.master.service.MasterManager" %>
<%@ tag import="java.util.ArrayList" %>
<%@ tag import="java.util.List" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="type" type="java.lang.String" required="true" description="Checkbox Type" %>
<%@ attribute name="selectValues" type="java.lang.String" required="false" description="Checkbox Value List" %>

<%
    if (StringUtils.startsWith(selectValues, "[") && StringUtils.endsWith(selectValues, "]")) {
        selectValues = selectValues.substring(1, selectValues.length() - 1);
    }
    String[] requires = StringUtils.split(selectValues, ",");
    request.setAttribute("requires", selectValues);

    MasterManager masterManager = (MasterManager) request.getAttribute("masterManager");
    List labelValueList = null;
    if (masterManager == null) {
        labelValueList = new ArrayList();
    } else {
        labelValueList = masterManager.getLabelValueList(type);
    }
    request.setAttribute("labelValueList", labelValueList);
%>
<c:choose>
    <%--<c:when test="${empty masterManager.getLabelValueList(type)}">--%>
    <c:when test="${empty labelValueList}">
        <span class="red">-Undefined-</span>
    </c:when>
    <c:otherwise>
        <%--<c:forEach var="lv" items='${masterManager.getLabelValueList(type)}'>--%>
        <c:forEach var="lv" items='${labelValueList}'>
            <c:set var="selectFlag" value="false"/>
            <c:forEach var="selectValue" items="${requires}">
                <c:if test="${not empty selectValue && selectValue eq lv.value}">
                    <c:set var="selectFlag" value="true"/>
                </c:if>
            </c:forEach>

            <%--<label class="middle">--%>
            <c:choose>
                <c:when test="${selectFlag}">
                    <input name="${name}" type="checkbox" checked="checked" class="ace" value="${lv.value}"/>
                </c:when>
                <c:otherwise>
                    <input name="${name}" type="checkbox" class="ace" value="${lv.value}"/>
                </c:otherwise>
            </c:choose>
            <span class="lbl">${lv.label}</span>&nbsp;&nbsp;&nbsp;
            <%--</label>--%>
        </c:forEach>
    </c:otherwise>
</c:choose>