<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<meta name="author" content="http://www.wjxinfo.com"/>
<meta http-equiv="X-UA-Compatible" content="IE=EDGE">
<%--<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">--%>
<%--<meta http-equiv="X-UA-Compatible" content="IE=9,IE=10" />--%>

<!-- ACE Theme -->
<!-- fam-icons -->
<link rel="stylesheet" href="${ace3}/fam/css/fam-icons.css" type="text/css"/>

<!-- basic styles -->
<link rel="shortcut icon" href="<c:url value="/static/images/favicon.ico"/>" type="image/x-icon">
<link rel="icon" href="<c:url value="/static/images/favicon.ico"/>"/>
<link rel="stylesheet" href="${ace3}/assets/css/bootstrap.min.css" type="text/css"/>
<link rel="stylesheet" href="${ace3}/assets/css/font-awesome.min.css" type="text/css"/>
<!--[if IE 7]>
<link rel="stylesheet" href="${ace3}/assets/css/font-awesome-ie7.min.css" type="text/css" />
<![endif]-->


<link href="${ace3}/assets/css/ui.multiselect.css" rel="stylesheet"/>


<%@ include file="/WEB-INF/common/include/jquery.gritter-css.jsp" %>
<%-- inline styles related to this page --%>
<decorator:getProperty property="page.header.requires"/>
<!-- fonts -->
<link rel="stylesheet" href="${ace3}/assets/css/ace-fonts.css" type="text/css"/>
<!-- ace styles -->
<link rel="stylesheet" href="${ace3}/assets/css/ace.min.css" type="text/css"/>
<link rel="stylesheet" href="${ace3}/assets/css/ace-rtl.min.css" type="text/css"/>
<link rel="stylesheet" href="${ace3}/assets/css/ace-skins.min.css" type="text/css"/>
<!--[if lte IE 8]>
<link rel="stylesheet" href="${ace3}/assets/css/ace-ie.min.css" type="text/css" />
<![endif]-->
<!-- ace settings handler -->
<script src="${ace3}/assets/js/ace-extra.min.js"></script>
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="${ace3}/assets/js/html5shiv.js" ></script>
<script src="${ace3}/assets/js/respond.min.js" ></script>
<![endif]-->

<%--<link rel="stylesheet" href="${ctx}/static/styles/ui.tabs.paging.css" type="text/css"/>--%>
<link rel="stylesheet" href="${ctx}/static/styles/wjxinfo-update.css" type="text/css"/>

<!--[if !IE]> -->
<script type="text/javascript">
    window.jQuery || document.write("<script src='${ace3}/assets/js/jquery-2.0.3.min.js'>" + "<" + "/script>");
</script>
<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
window.jQuery || document.write("<script src='${ace3}/assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->
<decorator:getProperty property="page.header.script"/>
<%@ include file="head-js.jsp" %>
<script src="${ctx}/static/scripts/global.js"></script>
<link href="${ctx}/static/styles/mas1.css" type="text/css" rel="stylesheet"/>
<link href="${ctx}/static/styles/mas2.css" type="text/css" rel="stylesheet"/>