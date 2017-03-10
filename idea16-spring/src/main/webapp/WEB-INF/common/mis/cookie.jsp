<%@ page import="javax.servlet.http.Cookie" %>
<%
    Cookie cookies[] = request.getCookies();

    String skinCookieName = "skin";
    Cookie skinCookie = null;
    String skinClass = "";

    if (cookies != null) {
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(skinCookieName)) {
                skinCookie = cookies[i];
            }
        }
    }

    if (skinCookie == null) {
        skinClass = "";   // "", "skin-1", "skin-2", "skin-3"
    } else {
        skinClass = skinCookie.getValue();
    }
%>

<%-- body class="" --%>
<c:set var="skinClass" value="<%=skinClass%>"/>
