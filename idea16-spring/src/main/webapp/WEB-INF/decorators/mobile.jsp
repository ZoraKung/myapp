<!DOCTYPE html>
<html lang="en">
<meta name="description" content="overview &amp; stats"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

<head>
    <meta charset="utf-8"/>
    <title><decorator:title/> - 设计院推广管理系统(M)</title>
    <%@include file="/WEB-INF/common/mobile/head.jsp" %>
    <%@include file="/WEB-INF/common/mobile/cookie.jsp" %>
    <decorator:head/>
</head>

<span id="ajax_spinner" style="position:absolute; top:50%; left:50%; z-index:3000">
    <img src="${ctx}/static/images/spinner.gif"/>
</span>

<body class="<c:out value="${skinClass}"/>"
        <decorator:getProperty property="body.id" writeEntireProperty="true"/>
        <decorator:getProperty property="body.class" writeEntireProperty="true"/>>

<%@include file="/WEB-INF/common/mobile/navbar.jsp" %>

 <div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <%--<a class="menu-toggler" id="menu-toggler" href="#">--%>
            <%--<span class="menu-text"></span>--%>
        <%--</a>--%>

        <%--<%@include file="/WEB-INF/common/mobile/sidebar.jsp" %>--%>

        <div class="main-content">
            <%--<div class='breadcrumbs' id="breadcrumbs">--%>
                <%--<script type="text/javascript">--%>
                    <%--try {--%>
                        <%--ace.settings.check('breadcrumbs', 'fixed')--%>
                    <%--} catch (e) {--%>
                    <%--}--%>
               <%--</script>--%>

                <%--<%@include file="/WEB-INF/common/mobile/navigation.jsp" %>--%>
            <%--</div>--%>

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
        <%@include file="/WEB-INF/common/mobile/acesetting.jsp" %>

    </div>
    <!-- /.main-container-inner -->

    <!-- Footer / JS -->
    <%@include file="/WEB-INF/common/mobile/footer.jsp" %>
</div>
<!--/.main-container-->

<decorator:getProperty property="page.final"/>
</body>
</html>

