<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="false" description="Photo Id" %>
<%@ attribute name="avatar" type="java.lang.String" required="false" description="Avatar" %>
<%@ attribute name="width" type="java.lang.String" required="false" description="Photo Width" %>
<%@ attribute name="height" type="java.lang.String" required="false" description="Photo Height" %>
<%@ attribute name="clazz" type="java.lang.String" required="false" description="Photo class" %>
<c:choose>
    <c:when test="${empty avatar}">
        <img src="${pageContext.request.contextPath}/static/avatar/default_avatar.png"
             width="${width}" height="${height}" id="${id}" class="${clazz}"
                />
    </c:when>
    <c:otherwise>
        <img src="${avatar}"
             width="${width}" height="${height}" id="${id}" class="${clazz}"/>
    </c:otherwise>
</c:choose>


