<%--<%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<%@ taglib prefix="fns" uri="/WEB-INF/tlds/fns.tld" %>--%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ace3" value="${pageContext.request.contextPath}/static/theme/ace3"/>
<c:set var="ctxstatic" value="${pageContext.request.contextPath}/static"/>

<c:set var="dateFormat" value="dd-MM-yyyy"/>
<c:set var="dateTimeFormat" value="dd-MM-yyyy HH:mm"/>
<c:set var="timeFormat" value="HH:mm"/>
<c:set var="moneyFormat" value="HK$0.00"/>
<c:set var="percentFormat" value="0.00%"/>
