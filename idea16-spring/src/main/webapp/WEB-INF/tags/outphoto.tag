<%@ tag import="java.util.Date" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="false" description="Photo Id" %>
<%@ attribute name="requestId" type="java.lang.String" required="false" description="Photo Id" %>
<%@ attribute name="width" type="java.lang.String" required="false" description="Photo Width" %>
<%@ attribute name="height" type="java.lang.String" required="false" description="Photo Height" %>
<%@ attribute name="clazz" type="java.lang.String" required="false" description="Photo class" %>
<c:choose>
    <c:when test="${empty requestId}">
        <img src="${pageContext.request.contextPath}/servlet/photoServlet?id=${id}&t=<%=new Date().getTime()%>"
             width="${width}" height="${height}" id="${id}" class="${clazz}"
                />
    </c:when>
    <c:when test="${requestId == 'default'}">
        <img src="${pageContext.request.contextPath}/static/avatar/default_click.jpg"
             width="${width}" height="${height}" id="${id}" class="${clazz}"
                />
    </c:when>
    <c:otherwise>
        <img src="${pageContext.request.contextPath}/servlet/photoServlet?id=${id}&t=<%=new Date().getTime()%>"
             width="${width}" height="${height}" id="${requestId}" class="${clazz}"
                />
    </c:otherwise>

</c:choose>

