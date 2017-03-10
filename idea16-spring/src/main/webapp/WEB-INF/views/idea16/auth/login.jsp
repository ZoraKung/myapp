<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>登录</title>
</head>

<body>

<div class="login-container">

    <div class="space-24"></div>

    <div class="position-relative">
        <div id="login-box" class="login-box visible widget-box no-border">
            <div class="widget-body">
                <div class="widget-main">
                    <h1>设计院推广管理系统</h1>
                    <h4 class="header blue">
                        <i class="fa fa-key green"></i>
                        登录系统
                    </h4>

                    <div class="space-6"></div>
                    <form:form id="loginForm" action="" method="post">
                        <fieldset>
                            <label class="block clearfix">
                                <span class="block input-icon input-icon-right">
                                    <input type="text" class="form-control required Login Name span12"
                                           name="username"
                                           placeholder="用户名"
                                           id="username"
                                           value="${username}"
                                           onfocus="javascript:clearErrorMessage();"
                                    />

                                    <i class="icon-user"></i>
                                </span>
                            </label>

                            <label class="block clearfix">
                                <span class="block input-icon input-icon-right">
                                    <input type="password" name="password" class="form-control required Password span12"
                                           placeholder="密码"
                                           id="password"
                                           onfocus="javascript:clearErrorMessage();"/>
                                    <i class="icon-lock"></i>
                                </span>
                            </label>

                            <div class="space"></div>

                            <div class="clearfix">
                                <button type="button" class="width-35 pull-right btn btn-sm btn-primary"
                                        id="btn-login">
                                    <i class="icon-key"></i>
                                    登录
                                </button>
                            </div>

                            <div class="space-4"></div>
                        </fieldset>

                        <label>
                            <span class="block">
                                <span id="loginErrorPlace" class="login-error">
                                    <c:if test="${not empty shiroLoginFailure}">
                                        错误的用户名或密码，请重试.
                                    </c:if>
                                </span>
                            </span>
                        </label>
                    </form:form>

                </div>

            </div>
        </div>

    </div>
</div>

</body>
</html>