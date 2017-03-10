<%@ tag language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/common/taglibs.jsp" %>
<style type="text/css">
    .tag-span {
        position: relative;
    }

    .tag-icon-remove {
        background-color: #fb7142;
        border-radius: 100%;
        color: #fff;
        height: 17px;
        width: 17px;
        position: absolute;
        top: -10px;
        right: -10px;
    }
</style>
<%--default file constants--%>
<%@ attribute name="fileListValue"
              type="org.hkicpa.common.letter.vo.DocumentFileListVo"
              required="true"
              description="Input Value" %>
<%@ attribute name="tagId" type="java.lang.String" required="true" description="TagId" %>
<%@ attribute name="tagName" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="value" type="org.hkicpa.mas.common.vo.EditorVo" required="true" description="Input Value" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Input Value" %>

<input type="hidden" id="id${tagId}" name="${tagName}.id" value="${value.id}"/>
<input type="hidden" id="module${tagId}" name="${tagName}.module" value="${value.module}"/>
<input type="hidden" id="editorCode${tagId}" name="${tagName}.editorCode" value="${value.editorCode}"/>

<div class="row form-group">
    <label class="col-xs-1 control-label">Tags</label>

    <div class="col-xs-8" id="tag${tagId}">
        <c:forEach items="${value.tagNames}" var="tagName">
            <span class="btn btn-xs tag-span form-group" type="button" id="tagSpan${tagName}">
                    <span class="lbl" onclick="doClickTag${tagId}(tagSpan${tagName}, '${tagName}')">${tagName}</span>
                       <a class="remove" onclick="doRemoveTag${tagId}(tagSpan${tagName}, '${tagName}');">
                           <i class="icon-remove tag-icon-remove"></i>
                       </a>
                    </span>
            </span>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </c:forEach>
    </div>
    <div class="col-xs-1">
        <button class="btn btn-xs btn-primary" onclick="doEditorAdd${tagId}();" type="button">
            <span class="lbl">Add Tag</span>
        </button>
    </div>
</div>
<div class="row form-group">
    <table id="upload_file_tag_table" class="table no-border">
        <tbody>
        <c:if test="${not empty fileListValue}">
            <c:forEach items="${fileListValue.documentFileVos}" var="documentFileVo" varStatus="loop">
                <tr>
                    <form:hidden path="documentFileListVo.documentFileVos[${loop.index}].documentId"/>
                    <form:hidden path="documentFileListVo.documentFileVos[${loop.index}].id"/>
                    <form:hidden path="documentFileListVo.documentFileVos[${loop.index}].documentName"/>
                    <form:hidden path="documentFileListVo.documentFileVos[${loop.index}].seq"/>
                    <td class="col-xs-4">
                        <tags:uploadFile id="document_${documentFileVo.id}"
                                         name="documentFileVos[${loop.index}].file"
                                         fileName="${documentFileVo.documentName}"
                                         css="input-xlarge ${css}"/>
                    </td>
                    <td class="col-xs-1">
                            <%--<div title='Delete' class='ui-pg-div ui-inline-custom col-sm-1'--%>
                            <%--onclick="doDelRecord(this, ${loop.index})">--%>
                            <%--<span class='ui-icon icon-trash red'>&nbsp;</span>--%>
                            <%--</div>--%>
                        <c:if test="${not empty documentFileVo.documentId}">
                            <div title='Download' class='ui-pg-div ui-inline-custom col-sm-1'
                                 onclick="doDownload('${documentFileVo.documentId}');">
                                <span class='ui-icon icon-download-alt'>&nbsp;</span>
                            </div>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>
</div>
<%--<div class="row form-group">--%>
<%--<div class="col-sm-offset-9 col-xs-1 btn-group pull-left">--%>
<%--<button id="btn_add_file" class="btn btn-sm btn-primary add-new-line" type="button">Add File</button>--%>
<%--</div>--%>
<%--</div>--%>
<script type="text/javascript">
var tagObject;
var tagsList = new Array();
function doClickTag${tagId}(e, tagName) {
    doEditTag${tagId}(tagName, false);
    $.each(tagsList, function (key, object) {
        if (object.tagName == tagName) {
            tagObject = object;
        }
    });
}

function doEditorAdd${tagId}() {
    tagObject = new Object();
    doEditTag${tagId}('', true);
}

function doEditTag${tagId}(_tagName, _createFlag) {
    var _module = $('#module${tagId}').val();
    var _editorCode = $('#editorCode${tagId}').val();
    //close file input
    $(document).find('.ace-file-input input[type="file"]').each(function (index, item) {
        $(this).width('0');
    });
    Matrix.prototype.ajaxPopupWindowExtend({
                title: 'Editor Tag Form',
                url: '${ctx}/letter-template/ajax/tag-info',
                height: '400',
                params: {
                    'module': _module,
                    'editorCode': _editorCode,
                    'tagName': _tagName
                },
                buttons: [
                    {
                        text: "Test",
                        "class": "btn btn-xs",
                        click: function () {
                            if (!$('#testValueId').hasClass('required')) {
                                $('#testValueId').addClass('required');
                            }
//                            doAddConstantsToJson();
//                            doBindJsonToForm('letterEditForm', 'editorTagVoList');
                            $('#headForm').ajaxSave({
                                url: '${ctx}/editor/ajax/tag-info/test'
                            })
                        }
                    },
                    {
                        text: "Save",
                        "class": "btn btn-primary btn-xs",
                        click: function () {
                            var flag = true;
                            var _tagNameVal = $('#tagNameId').val();
                            var _element = $(this);
                            if ($('#headForm').valid()) {
                                if ($('#testValueId').hasClass('required')) {
                                    $('#testValueId').removeClass('required');
                                }
                                $('span.lbl').each(function () {
                                    var _lblVal = $(this).text();
                                    if (_lblVal == _tagNameVal) {
                                        flag = false;
                                    }
                                });
                                if (flag) {
                                    if (!_createFlag) {
                                        $('#tagSpan' + _tagName).remove();
//                                        for (var _object in tagsList) {
//                                            console.log(_tagName);
//                                            if (tagsList[_object].tagName === _tagName) {
//                                                delete tagsList[_object];
//                                            }
//                                        }
                                    }
                                    doAddTag${id}(_tagNameVal);
                                }
                                doAddConstantsToJson();
                                _element.dialog('destroy').remove();
                            }
                            //close file input
                            $(document).find('.ace-file-input input[type="file"]').each(function (index, item) {
                                $(this).width('100%');
                            });
                        }
                    },
                    {
                        text: "Delete",
                        "class": "btn btn-xs",
                        click: function () {
                            var _element = $(this);
                            var _tagNameVal = $('#tagNameId').val();
                            if ($('#testValueId').hasClass('required')) {
                                $('#testValueId').removeClass('required');
                            }
                            $('#headForm').ajaxSave({
                                url: '${ctx}/editor/ajax/tag-info/delete',
                                callback: function () {
                                    $('#tagSpan' + _tagNameVal).remove();
                                    _element.dialog('destroy').remove();
                                    //close file input
                                    $(document).find('.ace-file-input input[type="file"]').each(function (index, item) {
                                        $(this).width('100%');
                                    });
                                }
                            });
                        }
                    },
                    {
                        text: "Close",
                        "class": "btn btn-xs",
                        click: function () {
                            $(this).dialog('destroy').remove();
                            if (!jQuery.isEmptyObject(tagObject)) {
                                tagObject = null;
                            }
                            //close file input
                            $(document).find('.ace-file-input input[type="file"]').each(function (index, item) {
                                $(this).width('100%');
                            });
                        }
                    }
                ]
            }
    );
}

function doAddTag${id}(_tagNameVal) {
    $("#tag${id}").append(' <span class="btn btn-xs tag-span form-group"  type="button" id="tagSpan' + _tagNameVal + '">' +
            '<span class="lbl" onclick="doClickTag${id}(tagSpan' + _tagNameVal + ', \'' + _tagNameVal + '\')">' + _tagNameVal + '</span>' +
            '<a class="remove" onclick="doRemoveTag${id}(tagSpan' + _tagNameVal + ', \'' + _tagNameVal + '\')">' +
            '<i class="icon-remove tag-icon-remove" ></i>' +
            '</a>' +
            '</span>' +
            '</span>' +
            '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
}

function doRemoveTag${id}(e, tagName) {
    //non binding
    $(e).closest('.tag-span').unbind('click');

    var _module = $('#module${id}').val();
    var _editorCode = $('#editorCode${id}').val();
    matrix.ajaxDelete({
        url: '${ctx}/editor/ajax/tag-info/remove',
        params: {
            'module': _module,
            'editorCode': _editorCode,
            'tagName': tagName
        },
        callback: function () {
            $(e).remove();

            //bind click
            $(e).closest('.tag-span').bind('click', 'doClickTag${tagId}');
        }
    });
    e.stopPropagation;
    return true;
}

function doAddConstantsToJson() {
    tagObject = {
        'id': $('#tagId').val(),
        'editorId': $('#editorId').val(),
        'module': $('#moduleId').val(),
        'editorCode': $('#editorCodeId').val(),
        'tagName': $('#tagNameId').val(),
        'sourceFrom': $('#sourceFromId').val(),
        'outputType': $('#outputTypeId').val(),
        'viewName': $('#viewNameId').val(),
        'fieldName': $('#fieldNameId').val(),
        'customFiledName': $('#customFiledNameId').val(),
        'viewKey': $('#viewKeyId').val(),
        'testValue': $('#testValueId').val()
    }
    tagsList.push(tagObject);
}

//bind data to form
function doBindJsonToForm(e, bindPropertyName) {
    $('#' + e).find('input[name="' + bindPropertyName + '"]').val(JSON.stringify(tagsList));
}

//download
function doDownload(documentId) {
    ajaxExport('${ctx}/document/downloadById', documentId);
}

//delete
function doDelRecord(e, _index) {
    //close file input
    $(document).find('.ace-file-input input[type="file"]').each(function (index, item) {
        $(this).width('0');
    });
    //process table
    var _table_obj = $('#upload_file_tag_table');
    var _table_length = _table_obj.find('tr').length;
    for (var i = _index + 1; i < _table_length; i++) {
        //set seq
        _table_obj.find('tr').eq(i).find('td').eq(0).text(i);
        //set name
        _table_obj.find('tr').eq(i).find('input[name$="documentId"]').attr('name', 'letterVos[' + (i - 1) + '].documentId');
        _table_obj.find('tr').eq(i).find('td').find('input[name$="file"]').attr('id', 'file_' + (i - 1));
        _table_obj.find('tr').eq(i).find('td').find('input[name$="file"]').attr('name', 'letterVos[' + (i - 1) + '].file');
        _table_obj.find('tr').eq(i).find('td').find('input[name$="file"]').attr('value', 'letterVos[' + (i - 1) + '].fileName');
        _table_obj.find('tr').eq(i).find('td').find('div[title="Delete"]').attr('onclick', 'doDelRecord(this, ' + (i - 1) + ')');
    }
    //msg_delete_file_confirm
    var filename = $(e).closest("tr").find('input[name$="file"]').attr('value');

    var options = {
        message: jQuery.i18n.prop('msg_delete_file_confirm', filename),
        callback: function () {
            //delete file
            var _id = null;
//            if ($(e).closest("tr").find('input[name$="id"]').length > 0) _id = $(e).closest("tr").find('input[name$="id"]').val();
            var _documentId = $(e).closest("tr").find('input[name$="documentId"]').val();
            if (_documentId != undefined && _documentId != '') {
                matrix.ajaxAction({
                    url: '${ctx}/document/delete',
                    params: {'id': _documentId},
                    callback: function () {
                        //remove row
                        $(e).closest("tr").remove();
                        doCheckTable(e);
                    }
                });
            } else {
                //remove row
                $(e).closest("tr").remove();
            }
            //close file input
            $(document).find('.ace-file-input input[type="file"]').each(function (index, item) {
                $(this).width('100%');
            });
        }
    };
    matrix.deleteConfirm(options);
}

<%--$('#btn_add_file').on('click', function () {--%>
<%--doAddFile('upload_file_tag_table');--%>
<%--});--%>

<%--function doAddFile(e) {--%>
<%--var _seq = $('#' + e).find('tr').length;--%>
<%--var _index = _seq;--%>
<%--var appendContent =--%>
<%--'<tr>' +--%>
<%--'<form:hidden path="documentFileListVo.documentFileVos[0].documentId"/>' +--%>
<%--'<form:hidden path="documentFileListVo.documentFileVos[0].seq"/>' +--%>
<%--'<td class="col-xs-4">' +--%>
<%--&lt;%&ndash;'<tags:uploadFile id="document_" name="documentFileVos[0].file" fileName="${documentFileVo.documentName}" css="input-xlarge requirements ${css}"/>' +&ndash;%&gt;--%>
<%--'</td>' +--%>
<%--'<td class="col-xs-1">' +--%>
<%--'<div title="Delete" class="ui-pg-div ui-inline-custom col-sm-1" onclick="doDelRecord(this, ' + _index + ')">' +--%>
<%--'<span class="ui-icon icon-trash red"></span>' +--%>
<%--'</div>' +--%>
<%--'</td>' +--%>
<%--'</tr>';--%>
<%--$('#' + e).find('tbody').append(appendContent);--%>
<%--}--%>
</script>



