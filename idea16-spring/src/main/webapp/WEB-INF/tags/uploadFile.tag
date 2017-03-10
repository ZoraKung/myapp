<%--
  User: Jack 
  Date: 14-10-15
  Description:
--%>
<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="Id" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Name" %>
<%@ attribute name="fileName" type="java.lang.String" required="true" description="Name" %>
<%@ attribute name="fileType" type="java.lang.String" required="false" description="Name" %>
<%@ attribute name="bindName" type="java.lang.String" required="false" description="Bind Name" %>
<%@ attribute name="multiple" type="java.lang.Boolean" required="false" description="Multiple" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Name" %>
<input type="file" id="${id}" name="${name}" multiple="" class="${empty css ? ace-file-input : css}"/>
<c:if test="${not empty bindName}">
    <input type="hidden" name="${bindName}" id="${id}bindName" value="${fileName}"/>
</c:if>

<script type="text/javascript">
    var ${id}fileName = '${fileName}';
    var ${id}fileType = '${empty fileType ? "*" : fileType}';
    $(function(){
        $('#${id}').ace_file_input({
            no_file:'No File ...',
            btn_choose:'Choose',
            btn_change:'Change',
            droppable:false,
            onchange:null,
            multiple: ${empty multiple?true:false},
            thumbnail:false,
            before_change: function(files, dropped) {
                var file = files[0];
                if(typeof file === "string") {//files is just a file name here (in browsers that don't support FileReader API)
                    if(!(${id}fileType == '*') && ! (/\.(${id}fileType)$/i).test(file) ){
                        matrix.showError(jQuery.i18n.prop("validate_upload_type", ${id}fileType));
                        return false;
                    }
                }
                else {//file is a File object
                    var type = $.trim(file.type);
                    if( ( type.length > 0 && !(${id}fileType == '*') && ! (/\.(${id}fileType)$/i).test(type) )
                            || ( type.length == 0 && !(${id}fileType == '*')  && ! (/\.(${id}fileType)$/i).test(file.name) )//for android default browser!
                            ){
                        matrix.showError(jQuery.i18n.prop("validate_upload_type", ${id}fileType));
                        return false;
                    }

                    if( file.size > 31744000 ) {//~31Mb
                        matrix.showError(jQuery.i18n.prop("validate_upload_size", '31744'));
                        return false;
                    }
                }
                // remove validate info
//                $(this).closest('.form-group').removeClass('has-error').removeClass('has-info');
                $(this).closest('.form-group').find('#${id}-error').remove();
                return true;
            }
        });

        $('#${id}').closest('div.ace-file-input').find('a.remove').on('click', function(){
            $('#${id}').ace_file_input('reset_input');
            window.event.returnValue = false;
            $('#${id}bindName').val('');
        });

        if (${id}fileName != undefined && ${id}fileName != ''){
            $('#${id}').closest('div.ace-file-input').find('label.file-label').addClass("hide-placeholder").addClass("selected");
            var simpleFileName = ${id}fileName.substr(${id}fileName.lastIndexOf('/') + 1);
            $('#${id}').closest('div.ace-file-input').find('span.file-name').attr({'data-title': simpleFileName});
        }
    });
</script>
