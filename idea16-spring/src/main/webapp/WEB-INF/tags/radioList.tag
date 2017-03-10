<%@ tag import="com.wjxinfo.common.master.service.MasterManager" %>
<%@ tag import="java.util.ArrayList" %>
<%@ tag import="java.util.List" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="type" type="java.lang.String" required="true" description="Radio Type" %>
<%@ attribute name="selectValue" type="java.lang.String" required="false" description="Radio Value" %>
<%
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
            <%--<label class="middle">--%>
            <c:choose>
                <c:when test="${not empty selectValue && selectValue == lv.value}">
                    <input name="${name}" type="radio" checked="checked" class="ace" value="${lv.value}"/>
                </c:when>
                <c:otherwise>
                    <input name="${name}" type="radio" class="ace" value="${lv.value}"/>
                </c:otherwise>
            </c:choose>
            <span class="lbl">${lv.label}</span>&nbsp;&nbsp;&nbsp;
            <%--</label>--%>
        </c:forEach>
    </c:otherwise>
</c:choose>