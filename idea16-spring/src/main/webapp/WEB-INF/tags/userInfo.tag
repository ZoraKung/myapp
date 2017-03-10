<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="userId" type="java.lang.String" required="true" description="User Id" %>
<div id="test"></div>
<script type="text/javascript">
    $(function(){
        var userDisplayName = matrix.returnJsonData({url: "${ctx}/common/service/mas-user/"+${userId}, async: false});
        $("#test").append(userDisplayName);
    })
</script>



