<!DOCTYPE html>
<html lang="en">
<meta name="description" content="overview &amp; stats"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>

<head>
    <meta charset="utf-8"/>
    <title><decorator:title/> - MyApp</title>

    <meta name="description" content="User login page"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <!-- basic styles -->

    <link href="${ace3}/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ace3}/assets/css/font-awesome.min.css"/>

    <!--[if IE 7]>
    <link rel="stylesheet" href="${ace3}/assets/css/font-awesome-ie7.min.css"/>
    <![endif]-->

    <!-- page specific plugin styles -->

    <!-- fonts -->

    <link rel="stylesheet" href="${ace3}/assets/css/ace-fonts.css"/>

    <!-- ace styles -->

    <link rel="stylesheet" href="${ace3}/assets/css/ace.min.css"/>
    <link rel="stylesheet" href="${ace3}/assets/css/ace-rtl.min.css"/>

    <!--[if lte IE 8]>
    <link rel="stylesheet" href="${ace3}/assets/css/ace-ie.min.css"/>
    <![endif]-->

    <!-- inline styles related to this page -->
    <link href="${ace3}/assets/css/jquery.gritter.css" type="text/css" rel="stylesheet" media="screen, projection"/>

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <%@ include file="/WEB-INF/common/mis/head-js.jsp" %>

    <!--[if lt IE 9]>
    <script src="${ace3}/assets/js/html5shiv.js"></script>
    <script src="${ace3}/assets/js/respond.min.js"></script>
    <![endif]-->

    <!-- This Page CSS -->
    <link href="${ctxstatic}/styles/login.css" type="text/css" rel="stylesheet" media="screen, projection"/>
</head>

<body class="login-layout">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <decorator:body/>
        </div>
        <!-- /.row -->
    </div>
</div>
<!-- /.main-container -->

<!--[if !IE]> -->

<script type="text/javascript">
    window.jQuery || document.write("<script src='${ace3}/assets/js/jquery-2.0.3.min.js'>" + "<" + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='${ace3}/assets/js/jquery-1.10.2.min.js'>" + "<" + "/script>");
</script>
<![endif]-->

<script type="text/javascript">
    if ("ontouchend" in document) document.write("<script src='${ace3}/assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
</script>

<!-- inline scripts related to this page -->
<script src="${ace3}/assets/js/jquery.gritter.min.js" type="text/javascript"></script>
<script src="${ace3}/assets/js/bootbox.min.js" type="text/javascript"></script>
<!--page specific plugin scripts-->
<script src="${ace3}/assets/js/jquery.validate.min.js"></script>

<!-- Page Script -->
<script src="${ctxstatic}/scripts/mis/login.js" type="text/javascript"></script>

<decorator:getProperty property="page.footer.script"/>
</body>
</html>


