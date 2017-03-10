<%@ tag language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="No" %>
<%@ attribute name="identityType" type="java.lang.String" required="true" description="Input Type" %>
<%@ attribute name="identityUid" type="java.lang.String" required="true" description="Input Uid" %>
<div id="${id}" style="display: none">
    <img src="${ctx}/static/images/red_tick.png" width="25" height="25">
</div>
<script type="text/javascript">
    $(function () {
        $('#${id}').hide();
        $.ajax({
            url: '${ctx}/compliance-alert/tick',
            data: {
                identityType: '${identityType}',
                identityUid: '${identityUid}'
            },
            type: 'post',
            dataType: 'text',
            success: function (response) {
                if (response == "true"){
                    $('#${id}').show();
                }else{
                    $('#${id}').hide();
                }
            }
        })
    })
</script>