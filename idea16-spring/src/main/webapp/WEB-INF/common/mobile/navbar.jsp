<%--<%@ taglib prefix="g" uri="http://www.springframework.org/tags" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-default" id="navbar">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>
    <style>
        .navbar .navbar-brand {
            font-size: 12px;
        }

        .nav:after,
        .navbar:after,
        .navbar-header:after {
            clear: both;
            display: none;
        }
    </style>

    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="<c:url value="/m"/>" class="navbar-brand">
                <small>
                    <img src="${ctx}/static/images/logo.png" height="29" width="47">
                    <%--<i class="icon-leaf"></i>--%>
                    <%--<g:message code="app.name" default="Matrix Apps"/>--%>
                    设计院推广管理系统(M)
                </small>
                <%--<span class="badge">1.1.3</span>--%>
            </a><!-- /.brand -->
        </div>
        <!-- /.navbar-header -->

        <div class="navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <%@include file="/WEB-INF/common/mobile/nav-user.jsp" %>

            </ul>
            <!-- /.ace-nav -->
        </div>
        <!-- /.navbar-header -->
    </div>
    <!-- /.container -->
</div>


