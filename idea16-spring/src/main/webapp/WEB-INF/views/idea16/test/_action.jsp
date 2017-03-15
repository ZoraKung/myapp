<%--
  Created by IntelliJ IDEA.
  User: GHL
  Date: 2016/7/19
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<script type="text/javascript">

    $('#btn_query').bind('click', function () {
        var id = $('#testId').val();
        $.ajax({
            type: "POST",
            url: "${ctx}/test/find/" + id,
            dataType: "json",
            success: function (res) {
                $('#testName').val(res.loginName);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                matrix.showError('Error');
            }
        });
    });

</script>
