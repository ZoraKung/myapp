<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<li class="light-blue">
    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
        <%--bt: 11383 --%>
        <c:set var="avatar" value=" ${fns:getUser().avatar}"></c:set>
        <c:set var="documentId" value=" ${fns:getUser().avatarId}"></c:set>
        <c:set var="photo" value="${fn:trim(avatar)}"></c:set>
        <c:set var="avatarId" value="${fn:trim(documentId)}"></c:set>
        <span id="user_photo">
        <c:choose>
            <c:when test="${photo == '' && avatarId != ''}">
                <tags:outphoto id="${avatarId}" width="35" height="35"
                               requestId="user-photo" clazz="nav-user-photo"></tags:outphoto>
            </c:when>

            <c:otherwise>
                <tags:systemphoto id="uer-photo" width="35" height="35"
                                  clazz="nav-user-photo" avatar="${photo}"></tags:systemphoto>
            </c:otherwise>
        </c:choose>
        </span>
        <span class="user-info">
                <%--<small></small>--%>
                <small>欢迎，</small>
                <shiro:principal property="name"/>
            </span>

        <i class="icon-caret-down"></i>
    </a>

    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
        <li>
            <a href="${ctx}/m/dms/activity/update">
                <i class="icon-search"></i>
                业务活动查询
            </a>
        </li>

        <li>
            <a href="${ctx}/m/dms/activity/create">
                <i class="icon-plus"></i>
                业务活动新增
            </a>
        </li>

        <%--<li>--%>
            <%--<a href="#" onclick="javascript:aceSetting();">--%>
                <%--<i class="icon-cog"></i>--%>
                <%--用户界面--%>
            <%--</a>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<a href="${ctx}/sysadmin/profile/edit">--%>
                <%--<i class="icon-user"></i>--%>
                <%--个人资料--%>
            <%--</a>--%>
        <%--</li>--%>

        <li class="divider"></li>

        <li>
            <a href="${ctx}/logout">
                <i class="icon-off"></i>
                退出系统
            </a>
        </li>
    </ul>
</li>
