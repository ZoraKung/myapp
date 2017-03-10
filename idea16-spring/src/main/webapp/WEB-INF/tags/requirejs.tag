<%@ tag import="java.util.Arrays" %>
<%@ tag import="java.util.List" %>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="Require JS" %>
<%
    String[] requires = id.split("[;,\\s]");
    List<String> requireList = Arrays.asList(requires);
    //request.setAttribute("requires", requires);
    //request.setAttribute("requires", requireList);
%>

<% if (requireList.contains("nav")) { %>
<%@include file="/WEB-INF/common/include/css/test-nav-css.jsp" %>
<%@include file="/WEB-INF/common/include/js/test-nav-js.jsp" %>
<%}%>

<% if (requireList.contains("jquery")) { %>
<%@include file="/WEB-INF/common/include/jquery-min-js.jsp" %>
<%}%>

<% if (requireList.contains("clock")) { %>
<%@include file="/WEB-INF/common/include/js/test-clock-js.jsp" %>
<%}%>


