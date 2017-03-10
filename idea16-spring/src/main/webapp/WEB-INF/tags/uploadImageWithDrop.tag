<%--
  User: Jack 
  Date: 14-10-10
  Description: upload image tag
--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="Id" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Name" %>
<%@ attribute name="fileName" type="java.lang.String" required="true" description="Name" %>
<%@ attribute name="documentId" type="java.lang.String" required="false" description="Document Id" %>
<%@ attribute name="width" type="java.lang.String" required="false" description="Width" %>
<%@ attribute name="height" type="java.lang.String" required="false" description="Height" %>
<%@ attribute name="bindName" type="java.lang.String" required="false" description="Bind Name" %>
<input type="file" id="${id}" name="${name}" multiple="" class="ace-file-input"/>
<c:if test="${not empty bindName}">
    <input type="hidden" name="${bindName}" id="${id}bindName" value="${fileName}"/>
</c:if>
<script type="text/javascript">
    var ${id}documentId = '${documentId}';
    var ${id}fileName = '${fileName}';
    $(function () {
        $('#${id}').ace_file_input({
            style: 'well',
            btn_choose: 'Click to upload photo < 500kb',
            btn_change: null,
            no_icon: 'icon-picture',
            thumbnail: 'fit',
            droppable: true,
            multiple:true,
            before_change: function (files, dropped) {
                var file = files[0];
                if (typeof file === "string") {//files is just a file name here (in browsers that don't support FileReader API)
                    if (!(/\.(jpe?g|png|gif)$/i).test(file)) {
                        matrix.showError(i18nQuery("validate_upload_jpg"));
                        return false;
                    }
                }
                else {//file is a File object
                    var type = $.trim(file.type);
                    if (( type.length > 0 && !(/^image\/(jpe?g|png|gif)$/i).test(type) )
                            || ( type.length == 0 && !(/\.(jpe?g|png|gif)$/i).test(file.name) )//for android default browser!
                    ) {
                        matrix.showError(jQuery.i18n.prop("validate_upload_jpg"));
                        return false;
                    }

                    if (file.size > 512000) {//~500Kb
                        matrix.showError(jQuery.i18n.prop("validate_upload_size"));
                        return false;
                    }
                }
                return true;
            }
        });
        $('#${id}').closest('div.ace-file-input').attr('style', 'width: ${empty width ? "280px" : width};height: ${empty height ? "192px" : height};');
        $('#${id}').closest('div.ace-file-input').find('a.remove').on('click', function () {
            $('#${id}').ace_file_input('reset_input');
            $('#${id}bindName').val('');
            var $input = $('#${id}').closest('div.ace-file-input').prev("input[type='hidden']");
            if ($input.length > 0 && $input.val() != "")
                $input.val("");
        });
        if (${id}documentId != undefined && ${id}documentId != '' && ${id}fileName != undefined && ${id}fileName != '') {
            $('#${id}').closest('div.ace-file-input').find('label.file-label').addClass("hide-placeholder").addClass("selected");
            $('#${id}').closest('div.ace-file-input').find('span.file-name').addClass("large");
            $('#${id}').closest('div.ace-file-input').find('span.file-name').attr({'data-title': ${id}fileName});
            var html = '<img class="middle" style="z-index:0;position:relative;width:70%; height:100%;" src="${pageContext.request.contextPath}/servlet/photoServlet?documentType=' + ${id}documentId
                    + '&fileName=' + ${id}fileName + '&temp=<%=Math.random()%>"/>';
            $('#${id}').closest('div.ace-file-input').find('i.icon-picture').before(html);
        }
    });
</script>
