<%
    //renderRequest.getPortletSession(true).setAttribute("com.qhms.mnms.session.message.showed", Boolean.FALSE);
%>

<c:set var="msg" value=""/>
<c:set var="err" value=""/>

<%-- Success Messages --%>
<c:if test="${not empty messages}">
    <c:forEach var="mess" items="${messages}">
        <c:if test="${(not empty mess) && !(mess eq '') && (not empty msg) && !(msg eq '')}">
            <c:set var="msg"><c:out value="${msg}" escapeXml="false"/><br/></c:set>
        </c:if>
        <c:if test="${(not empty mess) && !(mess eq '')}">
            <c:set var="msg">${msg}<c:out value="${mess}" escapeXml="false"/></c:set>
        </c:if>
    </c:forEach>
    <c:remove var="messages" scope="session"/>
</c:if>

<c:if test="${not empty i18nmessages}">
    <c:forEach var="mess" items="${i18nmessages}">
        <c:if test="${(not empty mess) && !(mess eq '') && (not empty msg) && !(msg eq '')}">
            <c:set var="msg"><c:out value="${msg}" escapeXml="false"/><br/></c:set>
        </c:if>
        <c:if test="${(not empty mess) && !(mess eq '')}">
            <c:set var="msg"><c:out value="${msg}" escapeXml="false"/><fmt:message key="${mess}"/></c:set>
        </c:if>
    </c:forEach>
    <c:remove var="i18nmessages" scope="session"/>
</c:if>

<%-- Error Messages --%>
<c:if test="${not empty errors}">
    <c:forEach var="mess" items="${errors}">
        <c:if test="${(not empty mess) && !(mess eq '') && (not empty err) && !(err eq '')}">
            <c:set var="err"><c:out value="${err}" escapeXml="false"/><br/></c:set>
        </c:if>
        <c:if test="${(not empty mess) && !(mess eq '')}">
            <c:set var="err"><c:out value="${err}" escapeXml="false"/><c:out value="${mess}" escapeXml="false"/></c:set>
        </c:if>
    </c:forEach>
    <c:remove var="errors" scope="session"/>
</c:if>

<c:if test="${not empty i18nerrors}">
    <c:forEach var="mess" items="${i18nerrors}">
        <c:if test="${(not empty mess) && !(mess eq '') && (not empty err) && !(err eq '')}">
            <c:set var="err"><c:out value="${err}" escapeXml="false"/><br/></c:set>
        </c:if>
        <c:if test="${(not empty mess) && !(mess eq '')}">
            <c:set var="err"><c:out value="${err}" escapeXml="false"/><fmt:message key="${mess}"/></c:set>
        </c:if>
    </c:forEach>
    <c:remove var="i18nerrors" scope="session"/>
</c:if>

<c:if test="${not empty msg}">
    <script type="text/javascript">
        <%--alert('${msg}');--%>

        $.gritter.add({
            title: '<i class="icon-comments icon-large"></i> Message',
            text: '${msg}',
            class_name: 'gritter-info gritter-light'
        });
    </script>

    <%--<script type="text/javascript">--%>
    <%--$(function(){--%>
    <%--$.gritter.add({--%>
    <%--title: '<i class="icon-comments icon-large"></i> Message',--%>
    <%--text: '${msg}',--%>
    <%--class_name: 'gritter-info gritter-light'--%>
    <%--});--%>
    <%--});--%>
    <%--</script>--%>

    <c:remove var="msg" scope="session"/>
</c:if>

<c:if test="${not empty err}">
    <script type="text/javascript">
        $.gritter.add({
            title: '<i class="icon-exclamation-sign icon-large"></i> Error',
            text: '${err}',
            class_name: 'gritter-error gritter-light'
        });
    </script>

    <%--<script type="text/javascript">--%>
    <%--$(function(){--%>
    <%--$.gritter.add({--%>
    <%--title: '<i class="icon-exclamation-sign icon-large"></i> Error',--%>
    <%--text: '${err}',--%>
    <%--class_name: 'gritter-error gritter-light'--%>
    <%--});--%>
    <%--});--%>
    <%--</script>--%>

    <c:remove var="err" scope="session"/>
</c:if>

<%
    //renderRequest.getPortletSession(true).setAttribute("com.qhms.mnms.session.message.showed", Boolean.TRUE);
%>

