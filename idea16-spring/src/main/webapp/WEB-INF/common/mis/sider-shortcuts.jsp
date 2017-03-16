<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<div class="sidebar-shortcuts" id="sidebar-shortcuts">
    <div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
        <c:set var="shortcut1" value="${fns:getShortcut(\"MENU_SHORTCUT_1\")}" scope="page"></c:set>
        <c:set var="shortcut2" value="${fns:getShortcut(\"MENU_SHORTCUT_2\")}" scope="page"></c:set>
        <c:set var="shortcut3" value="${fns:getShortcut(\"MENU_SHORTCUT_3\")}" scope="page"></c:set>
        <c:set var="shortcut4" value="${fns:getShortcut(\"MENU_SHORTCUT_4\")}" scope="page"></c:set>

        <button class="btn btn-small btn-success"
                <c:if test="${not empty shortcut1 && not empty shortcut1.privilegeIdentifier}">
                    <shiro:hasPermission name="${shortcut1.privilegeIdentifier}">
                        onclick="run('${shortcut1.parent.code}', '${shortcut1.parent.name}', '${shortcut1.parent.url}', '${shortcut1.code}', '${shortcut1.name}', '${shortcut1.url}');doShortcut('${shortcut1.url}')"
                        title="${shortcut1.description}"
                    </shiro:hasPermission>
                </c:if>>
            <c:choose>
                <c:when test="${not empty shortcut1 && not empty shortcut1.iconCssClass && shortcut1.iconCssClass != 'icon-list-alt'}">
                    <i class="${shortcut1.iconCssClass}"></i>
                </c:when>
                <c:otherwise>
                    <i class="icon-male"></i>
                </c:otherwise>
            </c:choose>
        </button>

        <button class="btn btn-small btn-info"
                <c:if test="${not empty shortcut2 && not empty shortcut2.privilegeIdentifier}">
                    <shiro:hasPermission name="${shortcut2.privilegeIdentifier}">
                        onclick="run('${shortcut2.parent.code}', '${shortcut2.parent.name}', '${shortcut2.parent.url}', '${shortcut2.code}', '${shortcut2.name}', '${shortcut2.url}');doShortcut('${shortcut2.url}')"
                        title="${shortcut2.description}"
                    </shiro:hasPermission>
                </c:if>>
            <c:choose>
                <c:when test="${not empty shortcut2 && not empty shortcut2.iconCssClass && shortcut2.iconCssClass != 'icon-list-alt'}">
                    <i class="${shortcut2.iconCssClass}"></i>
                </c:when>
                <c:otherwise>
                    <i class="icon-pencil"></i>
                </c:otherwise>
            </c:choose>
        </button>

        <button class="btn btn-small btn-warning"
                <c:if test="${not empty shortcut3 && not empty shortcut3.privilegeIdentifier}">
                    <shiro:hasPermission name="${shortcut3.privilegeIdentifier}">
                        onclick="run('${shortcut3.parent.code}', '${shortcut3.parent.name}', '${shortcut3.parent.url}', '${shortcut3.code}', '${shortcut3.name}', '${shortcut3.url}');doShortcut('${shortcut3.url}')"
                        title="${shortcut3.description}"
                    </shiro:hasPermission>
                </c:if>>
            <c:choose>
                <c:when test="${not empty shortcut3 && not empty shortcut3.iconCssClass && shortcut3.iconCssClass != 'icon-list-alt'}">
                    <i class="${shortcut3.iconCssClass}"></i>
                </c:when>
                <c:otherwise>
                    <i class="icon-group"></i>
                </c:otherwise>
            </c:choose>
        </button>

        <button class="btn btn-small btn-danger"
                <c:if test="${not empty shortcut4 && not empty shortcut4.privilegeIdentifier}">
                    <shiro:hasPermission name="${shortcut4.privilegeIdentifier}">
                        onclick="run('${shortcut4.parent.code}', '${shortcut4.parent.name}', '${shortcut4.parent.url}', '${shortcut4.code}', '${shortcut4.name}', '${shortcut4.url}');doShortcut('${shortcut4.url}')"
                        title="${shortcut4.description}"
                    </shiro:hasPermission>
                </c:if>>
            <c:choose>
                <c:when test="${not empty shortcut4 && not empty shortcut4.iconCssClass && shortcut4.iconCssClass != 'icon-list-alt'}">
                    <i class="${shortcut4.iconCssClass}"></i>
                </c:when>
                <c:otherwise>
                    <i class="icon-cogs"></i>
                </c:otherwise>
            </c:choose>
        </button>
    </div>

    <div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
        <span class="btn btn-success"></span>
        <span class="btn btn-info"></span>
        <span class="btn btn-warning"></span>
        <span class="btn btn-danger"></span>
    </div>
</div>

<script type="text/javascript">
    function doShortcut(url) {
        location.href = _appContext + url;
    }
</script>