<%--<%@ taglib prefix="g" uri="http://www.springframework.org/tags" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-default" id="navbar">
    <script type="text/javascript">
        try {
            ace.settings.check('navbar', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="#" class="navbar-brand">
                <small>
                    <img src="${ctx}/static/images/logo.png" height="29" width="47" >
                <%--<i class="icon-leaf"></i>--%>
                <%--<g:message code="app.name" default="Matrix Apps"/>--%>
                    MyApp
                </small>
                <span class="badge">1.1.3</span>
            </a><!-- /.brand -->
        </div>
        <!-- /.navbar-header -->

        <div class="navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <%--idea16test<%@include file="/WEB-INF/common/mis/nav-user.jsp" %>--%>

            </ul>
            <!-- /.ace-nav -->
        </div>
        <!-- /.navbar-header -->
    </div>
    <!-- /.container -->
</div>


