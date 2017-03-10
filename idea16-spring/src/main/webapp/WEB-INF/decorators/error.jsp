<!DOCTYPE html>
<html lang="en">
<meta name="description" content="overview &amp; stats"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

<head>
    <meta charset="utf-8"/>
    <title><decorator:title/> - HKICPA MAS</title>
    <%@include file="/WEB-INF/common/mis/head.jsp" %>
    <%@include file="/WEB-INF/common/mis/cookie.jsp" %>
    <decorator:head/>
</head>

<span id="ajax_spinner" style="position:absolute; top:50%; left:50%; z-index:3000">
    <img src="${ctx}/static/images/spinner.gif"/>
</span>

<body class="<c:out value="${skinClass}"/>"
        <decorator:getProperty property="body.id" writeEntireProperty="true"/>
        <decorator:getProperty property="body.class" writeEntireProperty="true"/>>

<%@include file="/WEB-INF/common/mis/navbar.jsp" %>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <%@include file="/WEB-INF/common/mis/sidebar.jsp" %>

        <div class="main-content">
            <div class='breadcrumbs' id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>

                <%--<%@include file="/WEB-INF/common/mis/navigation.jsp" %>--%>

                <%--<div class="nav-search" id="nav-search">--%>
                <%--<form class="form-search">--%>
                <%--<span class="input-icon">--%>
                <%--<input type="text" placeholder="Search ..." class="nav-search-input"--%>
                <%--id="nav-search-input" autocomplete="off"/>--%>
                <%--<i class="icon-search nav-search-icon"></i>--%>
                <%--</span>--%>
                <%--</form>--%>
                <%--</div>--%>
                <!--#nav-search-->
            </div>

            <div class="page-content">
                <div class="row">
                    <div class="col-xs-12">
                        <decorator:body/>
                    </div>
                </div>
            </div>
            <!--/.page-content-->

            <!--/#ace-settings-container-->
        </div>
        <!--/.main-content-->

        <%--ACE Theme setting--%>
        <%@include file="/WEB-INF/common/mis/acesetting.jsp" %>

    </div>
    <!-- /.main-container-inner -->

    <!-- Footer / JS -->
    <%@include file="/WEB-INF/common/mis/footer.jsp" %>
</div>
<!--/.main-container-->

<decorator:getProperty property="page.final"/>
</body>
</html>

