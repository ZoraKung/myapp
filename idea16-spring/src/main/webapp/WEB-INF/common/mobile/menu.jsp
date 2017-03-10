<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>
<%--Set Current Menu Status--%>
<c:set var="firstMenuId" scope="request"><decorator:getProperty property="meta.firstMenuId"/></c:set>
<c:set var="firstMenuDesc" scope="request"><decorator:getProperty property="meta.firstMenuDesc"/></c:set>
<c:set var="firstMenuUrl" scope="request"><decorator:getProperty property="meta.firstMenuUrl"/></c:set>
<c:set var="secondMenuId" scope="request"><decorator:getProperty property="meta.secondMenuId"/></c:set>
<c:set var="secondMenuDesc" scope="request"><decorator:getProperty property="meta.secondMenuDesc"/></c:set>
<c:set var="secondMenuUrl" scope="request"><decorator:getProperty property="meta.secondMenuUrl"/></c:set>
<c:set var="currentMenuId" scope="request"><decorator:getProperty property="meta.menuId"/></c:set>
<c:set var="currentMenuUrl" scope="request"><decorator:getProperty property="meta.menuUrl"/></c:set>
<c:set var="currentMenuDesc" scope="request"><decorator:getProperty property="meta.menuDesc"/></c:set>
<c:set var="specialMenuDesc" scope="request"><decorator:getProperty property="meta.menuDesc"/></c:set>

<c:if test="${empty firstMenuId}">
    <c:set var="firstMenuId" scope="request"><%=request.getSession(true).getAttribute("firstMenuId")%>
    </c:set>
</c:if>
<c:if test="${empty firstMenuDesc}">
    <c:set var="firstMenuDesc" scope="request"><%=request.getSession(true).getAttribute("firstMenuName")%>
    </c:set>
</c:if>
<c:if test="${empty firstMenuUrl}">
    <c:set var="firstMenuUrl" scope="request"><%=request.getSession(true).getAttribute("firstMenuUrl")%>
    </c:set>
</c:if>
<c:if test="${empty secondMenuId}">
    <c:set var="secondMenuId" scope="request"><%=request.getSession(true).getAttribute("secondMenuId")%>
    </c:set>
</c:if>
<c:if test="${empty secondMenuDesc}">
    <c:set var="secondMenuDesc" scope="request"><%=request.getSession(true).getAttribute("secondMenuName")%>
    </c:set>
</c:if>
<c:if test="${empty secondMenuUrl}">
    <c:set var="secondMenuUrl" scope="request"><%=request.getSession(true).getAttribute("secondMenuUrl")%>
    </c:set>
</c:if>
<c:if test="${empty currentMenuId}">
    <c:set var="currentMenuId" scope="request"><%=request.getSession(true).getAttribute("menuId")%>
    </c:set>
</c:if>
<c:if test="${empty currentMenuDesc}">
    <c:set var="currentMenuDesc" scope="request"><%=request.getSession(true).getAttribute("menuName")%>
    </c:set>
</c:if>
<c:if test="${empty currentMenuUrl}">
    <c:set var="currentMenuUrl" scope="request"><%=request.getSession(true).getAttribute("menuUrl")%>
    </c:set>
</c:if>

<ul class="nav nav-list">
    <%--<shiro:hasPermission name="home:view">--%>
    <%--<li class="<c:if test="${currentMenuId eq 'home'}">active</c:if>">--%>
        <%--<a href="<c:url value='/home/'/>">--%>
            <%--<i class="icon-dashboard"></i>--%>
            <%--<span class="menu-text"> Dashboard </span>--%>
        <%--</a>--%>
    <%--</li>--%>
    <%--</shiro:hasPermission>--%>

    <c:forEach items="${fns:getMasMenuList()}" var="firstMenu">
        <c:if test="${firstMenu.parent.id == null && not empty firstMenu.privilegeIdentifier}">
            <shiro:hasPermission name="${firstMenu.privilegeIdentifier}">
                <c:if test="${firstMenuId eq firstMenu.code}">
                    <c:set var="firstMenuDesc" scope="request">${firstMenu.name}</c:set>
                    <c:set var="firstMenuUrl" scope="request">${firstMenu.url}</c:set>
                    <c:set var="firstMenuPrivilegeIdentifier" scope="request">${firstMenu.privilegeIdentifier}</c:set>
                </c:if>

                <li class="<c:if test="${firstMenuId eq firstMenu.code}">active open</c:if>">
                    <a href="#" class="dropdown-toggle">
                        <i class="${firstMenu.iconCssClass}"></i>
                        <span class="menu-text"> ${firstMenu.name}</span>
                        <b class="arrow icon-angle-down"></b>
                    </a>

                    <ul class="submenu">
                        <c:forEach items="${fns:getMasMenuList()}" var="secondMenu">
                            <c:if test="${secondMenu.parent.id eq firstMenu.id && not empty secondMenu.privilegeIdentifier}">
                                <shiro:hasPermission name="${secondMenu.privilegeIdentifier}">
                                    <c:if test="${secondMenu.leaf}">
                                        <c:if test="${currentMenuId eq secondMenu.code}">
                                            <c:set var="currentMenuDesc"
                                                   scope="request">${secondMenu.name}</c:set>
                                        </c:if>
                                        <li class="<c:if test="${currentMenuId eq secondMenu.code}">active</c:if>">
                                            <a href="#"
                                               onclick="run('${firstMenu.code}', '${firstMenu.name}', '${firstMenu.url}', '','','', '${secondMenu.code}', '${secondMenu.name}', '${secondMenu.url}');">
                                                <i class="icon-double-angle-right"></i>${secondMenu.name}
                                            </a>
                                        </li>
                                    </c:if>

                                    <c:if test="${! secondMenu.leaf}">
                                        <c:if test="${secondMenuId eq secondMenu.code}">
                                            <c:set var="secondMenuDesc" scope="request">${secondMenu.name}</c:set>
                                            <c:set var="secondMenuUrl" scope="request">${secondMenu.url}</c:set>
                                            <c:set var="secondMenuPrivilegeIdentifier" scope="request">${secondMenu.privilegeIdentifier}</c:set>
                                        </c:if>

                                        <li class="<c:if test="${secondMenuId eq secondMenu.code}">active open</c:if>">
                                            <a href="#" class="dropdown-toggle">
                                                <i class="${secondMenu.iconCssClass}"></i>
                                                <span class="menu-text"> ${secondMenu.name}</span>
                                                <b class="arrow icon-angle-down"></b>
                                            </a>

                                            <ul class="submenu">
                                                <c:forEach items="${fns:getMasMenuList()}" var="threeMenu">
                                                    <c:if test="${threeMenu.parent.id eq secondMenu.id && not empty threeMenu.privilegeIdentifier}">
                                                            <shiro:hasPermission name="${threeMenu.privilegeIdentifier}">
                                                                <c:if test="${currentMenuId eq threeMenu.code}">
                                                                    <c:set var="currentMenuDesc"
                                                                           scope="request">${threeMenu.name}</c:set>
                                                                </c:if>
                                                                <li class="<c:if test="${currentMenuId eq threeMenu.code}">active</c:if>">
                                                                    <a href="#"
                                                                       onclick="run('${firstMenu.code}', '${firstMenu.name}', '${firstMenu.url}', '${secondMenu.code}', '${secondMenu.name}', '${secondMenu.url}', '${threeMenu.code}', '${threeMenu.name}', '${threeMenu.url}');">
                                                                        <i class="icon-double-angle-right"></i>${threeMenu.name}
                                                                    </a>
                                                                </li>
                                                            </shiro:hasPermission>
                                                    </c:if>
                                                </c:forEach>
                                            </ul>
                                        </li>
                                    </c:if>
                                </shiro:hasPermission>
                            </c:if>
                        </c:forEach>
                    </ul>
                </li>
            </shiro:hasPermission>
        </c:if>
    </c:forEach>

</ul>
<!--/.nav-list-->
<script type="text/javascript">
    function run(firstMenuId, firstMenuName, firstMenuUrl, secondMenuId, secondMenuName, secondMenuUrl, menuId, menuName, menuUrl) {
        var _isLeave = doPromptBeforePage();
        _is_submit = true;
        if (_isLeave) {
            location.href = _appContext + menuUrl;

            jQuery.ajax({
                type: 'POST',
                async: false,
                data: {
                    "menuId": menuId,
                    "menuName": menuName,
                    "menuUrl": menuUrl,
                    "firstMenuId": firstMenuId,
                    "firstMenuName": firstMenuName,
                    "firstMenuUrl": firstMenuUrl,
                    "secondMenuId": secondMenuId,
                    "secondMenuName": secondMenuName,
                    "secondMenuUrl": secondMenuUrl,
                },
                url: '${ctx}/misc/save/menu/status',
                success: function (data, textStatus) {
                    //alert(data);
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    alert("error");
                }
            });
        }
    }

    function doPromptBeforePage() {
        var _changeFlag = false;
        $(document).find('form').each(function (index, item) {
            var _form_id = $(item).attr("id");
            if (_form_id != undefined && _form_id != null && _form_id != '') {
                /*                if (console && console.log){
                 console.log('original data - ' + $('#' + _form_id).data('serialize'));
                 console.log('current  data - ' + $('#' + _form_id).serialize());
                 }*/
                if ($('#' + _form_id).serialize() != $('#' + _form_id).data('serialize')) {
                    _changeFlag = true;
                }
            }
        });
        if (_changeFlag) {
            if (confirm(jQuery.i18n.prop('not_save_exit'))) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
</script>
