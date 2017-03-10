<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="docId" type="java.lang.String" required="true" description="Document Id" %>

<button class="btn btn-xs btn-primary" id="btn_download${docId}" type="button">Download File</button>
<script type="text/javascript">
    $(function () {
        $("#btn_download${docId}").click(function () {
            window.open("${ctx}/download-file/download?id=" +${docId});
        })
    })
</script>

