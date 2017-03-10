<%@ tag language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="Id" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Name" %>
<%@ attribute name="value" type="java.util.List" required="true" description="Value" %>
<%@ attribute name="fileType" type="java.lang.String" required="false" description="File Type" %>
<%@ attribute name="canAdd" type="java.lang.String" required="false" description="Add File" %>
<%@ attribute name="canDelete" type="java.lang.String" required="false" description="Delete File" %>
<%@ attribute name="deleteUrl" type="java.lang.String" required="false" description="Delete File Url" %>
<%@ attribute name="canDownload" type="java.lang.String" required="false" description="Download File" %>

<table id="${id}_table" class="table no-border">
    <c:forEach items="${value}" var="attachmentVo" varStatus="loop">
        <tr>
            <td class="col-sm-11">
                <form:hidden path="${name}[${loop.index}].id"></form:hidden>
                <form:hidden path="${name}[${loop.index}].filePath"></form:hidden>
                <form:hidden path="${name}[${loop.index}].fileName"></form:hidden>
                <form:hidden path="${name}[${loop.index}].displayName"></form:hidden>
                <tags:uploadFile id="${id}_${loop.index}" name="${name}[${loop.index}].file"
                                 fileName="${attachmentVo.displayName}" fileType="${fileType}"></tags:uploadFile>
            </td>
            <c:if test="${canDelete == 'true'}">
                <td class="col-sm-1">
                    <div title='Delete' class='ui-pg-div ui-inline-custom'
                         onclick="${id}doDeleteFile(this, ${loop.index})">
                        <span class='ui-icon icon-trash red'>&nbsp;</span>
                    </div>
                </td>
            </c:if>
            <c:if test="${not empty attachmentVo.id && canDownload == 'true'}">
                <td>
                    <div title='Download' class='ui-pg-div ui-inline-custom'
                         onclick="${id}doDownloadFile('${attachmentVo.filePath}', '${attachmentVo.fileName}', '${attachmentVo.displayName}');">
                        <span class='ui-icon icon-download-alt'>&nbsp;</span>
                    </div>
                </td>
            </c:if>
        </tr>
    </c:forEach>
</table>
<c:if test="${canAdd == 'true'}">
    <div class="row">
        <div class="col-sm-6">
            <button id="${id}add" class="btn btn-sm btn-primary add-new-line"
                    type="button" onclick="${id}doAddFile()">Add More Attachment
            </button>
        </div>
    </div>
</c:if>

<script type="text/javascript">
    function ${id}doDeleteFile(e, index) {
        //process table
        var _table_obj = $('#${id}_table');
        var _table_length = _table_obj.find('tr').length;
        for (var i = index + 1; i < _table_length; i++) {
            //set name
            _table_obj.find('tr').eq(i).find('td').find('input[name$="id"]').attr('name', '${name}[' + (i - 1) + '].id');
            _table_obj.find('tr').eq(i).find('td').find('input[name$="filePath"]').attr('name', '${name}[' + (i - 1) + '].filePath');
            _table_obj.find('tr').eq(i).find('td').find('input[name$="fileName"]').attr('name', '${name}[' + (i - 1) + '].fileName');
            _table_obj.find('tr').eq(i).find('td').find('input[name$="displayName"]').attr('name', '${name}[' + (i - 1) + '].displayName');
            _table_obj.find('tr').eq(i).find('td').find('input[name$="file"]').attr('id', '${id}_file_' + (i - 1));
            _table_obj.find('tr').eq(i).find('td').find('input[name$="file"]').attr('name', '${name}[' + (i - 1) + '].file');
            _table_obj.find('tr').eq(i).find('td').find('div[title="Delete"]').attr('onclick', '${id}doDeleteFile(this, ' + (i - 1) + ')');
        }

        //delete file
        var _fileId = $(e).closest("tr").find('input[name$="id"]').val();
        if (_fileId != undefined && _fileId != '' && ${not empty deleteUrl}) {
            matrix.ajaxAction({
                url: '${deleteUrl}',
                params: {fileId: _fileId},
                callback: function () {
                    //remove row
                    $(e).closest("tr").remove();
                }
            });
        } else {
            //remove row
            $(e).closest("tr").remove();
        }
    }

    function ${id}doAddFile() {
        var _index = $('#${id}_table').find('tr').length;
        var _content = '<tr>'
                + '<td class="col-sm-11">'
                + '     <input type="hidden" name="${name}[' + _index + '].id"/>'
                + '     <input type="hidden" name="${name}[' + _index + '].filePath"/>'
                + '     <input type="hidden" name="${name}[' + _index + '].fileName"/>'
                + '     <input type="hidden" name="${name}[' + _index + '].displayName"/>'
                + '     <input type="file" id="${id}_file_' + _index + '" name="${name}[' + _index + '].file" class="ace-file-input"/>'
                + '</td>'
                + '<c:if test="${canDelete == 'true'}">'
                + '     <td class="col-sm-1">'
                + '         <div title="Delete" class="ui-pg-div ui-inline-custom" onclick="${id}doDeleteFile(this, ' + _index + ')">'
                + '             <span class="ui-icon icon-trash red">&nbsp;</span>'
                + '         </div>'
                + '     </td>'
                + '</c:if>'
                + '<td></td>'
                + '</tr>';

        if (_index > 0){
            $('#${id}_table').find('tr').last().after(_content);
        }else{
            $('#${id}_table').append(_content);
        }

        ${id}doSetFileStyle('${id}_file_' + _index);
    }

    function ${id}doDownloadFile(filePath, fileName, displayName) {
        ajaxExport("${ctx}/common/download/attachment", filePath + "&" + fileName + "&" + displayName);
    }

    function ${id}doSetFileStyle(fileId) {
        var ${id}fileType = '${empty fileType ? "*" : fileType}';
        $('#' + fileId).ace_file_input({
            no_file:'No File ...',
            btn_choose:'Choose',
            btn_change:'Change',
            droppable:false,
            onchange:null,
            multiple: true,
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
                return true;
            }
        });
//        $('#' + fileId).closest('div.ace-file-input').find('label.file-label').addClass("hide-placeholder").addClass("selected");
        $('#' + fileId).closest('div.ace-file-input').find('span.file-name').attr({'data-title': this.defaultValue});
        $('#' + fileId).closest('div.ace-file-input').find('a.remove').addClass('hide');
    }

</script>



