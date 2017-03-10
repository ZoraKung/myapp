<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Captcha input name" %>
<input type="text" id="${name}" name="${name}" maxlength="5" class="captcha required input-small"
       style="width:60px;"/>
<img src="${pageContext.request.contextPath}/servlet/captchaServlet"
     onclick="$('.${name}Refresh').click();" class="mid ${name}"
     style="vertical-align:middle;"/>
<a href="javascript:"
   onclick="$('.${name}').attr('src','${pageContext.request.contextPath}/servlet/captchaServlet?'
           +new Date().getTime());" class="mid ${name}Refresh">Another?</a>
