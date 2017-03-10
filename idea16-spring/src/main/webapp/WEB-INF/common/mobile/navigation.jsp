<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<ul class="breadcrumb">
    <li>
        <i class="icon-home home-icon"></i>
        <a href='<c:url value="/m"/>'>
            首页
        </a>
    </li>
    <%--first level menu--%>
    <c:choose>
        <c:when test="${(not empty firstMenuDesc) && (not empty firstMenuPrivilegeIdentifier) }">
            <%--<c:when test="${not empty parentMenuDesc}">--%>
            <shiro:hasPermission name="${firstMenuPrivilegeIdentifier}">
                <li>
                    <%--<a href="<c:url value='${firstMenuUrl}'/>">--%>
                        <c:out value="${firstMenuDesc}" escapeXml="false"/>
                    <%--</a>--%>
                </li>
            </shiro:hasPermission>
        </c:when>
    </c:choose>
   <%-- second level menu--%>
    <c:choose>
        <c:when test="${(not empty secondMenuDesc) && (not empty secondMenuPrivilegeIdentifier) }">
            <%--<c:when test="${not empty parentMenuDesc}">--%>
            <shiro:hasPermission name="${secondMenuPrivilegeIdentifier}">
                <li>
                   <%-- <a href="<c:url value='${secondMenuUrl}'/>">--%>
                        <c:out value="${secondMenuDesc}" escapeXml="false"/>
                   <%-- </a>--%>
                </li>
            </shiro:hasPermission>
        </c:when>
    </c:choose>
    <%--leaf menu --%>
    <li class="active">
        <c:set var="_currentMenuDesc">
            <c:choose>
                <c:when test="${not empty currentMenuDesc && 'null' != currentMenuDesc}">
                    <c:out value="${currentMenuDesc}" escapeXml="false"/>
                </c:when>
                <c:otherwise>
                    <%--<i class="fam-help"></i>--%>
                </c:otherwise>
            </c:choose>
        </c:set>

        <c:choose>
            <c:when test="${not empty currentMenuUrl && 'null' != currentMenuUrl}">
                <%--<a href="<c:url value='${currentMenuUrl}'/>">--%>
                    <c:out value="${_currentMenuDesc}" escapeXml="false"/>
                <%--</a>--%>

            </c:when>
            <c:otherwise>
                <%--<a href="<c:url value='#'/>" onclick="showNavigationTips()">--%>
                    <c:out value="${_currentMenuDesc}" escapeXml="false"/>
                <%--</a>--%>
            </c:otherwise>
        </c:choose>

        <%--<c:out value="${currentMenuDesc}" escapeXml="false"/>--%>
    </li>
</ul>
<script type="text/javascript">
    function showNavigationTips() {
        matrix.showMessage("Not found the parameters [menuId] and [parentMenuId] in this page.");
    }
</script>
