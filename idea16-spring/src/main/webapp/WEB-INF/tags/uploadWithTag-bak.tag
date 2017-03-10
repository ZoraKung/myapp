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
<%@ attribute name="fileId" type="java.lang.String" required="true" description="FileId" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Name" %>
<%@ attribute name="fileName" type="java.lang.String" required="true" description="fileName" %>
<%@ attribute name="fileType" type="java.lang.String" required="false" description="fileType" %>
<%--default tag canstants--%>
<%@ attribute name="tagId" type="java.lang.String" required="true" description="TagId" %>
<%@ attribute name="tagName" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="value" type="org.hkicpa.mas.common.vo.EditorVo" required="true" description="Input Value" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Input Value" %>

<input type="hidden" id="id${tagId}" name="${tagName}.id" value="${value.id}"/>
<input type="hidden" id="module${tagId}" name="${tagName}.module" value="${value.module}"/>
<input type="hidden" id="editorCode${tagId}" name="${tagName}.editorCode" value="${value.editorCode}"/>

<div class="row form-group">
    <label class="col-sm-1 control-label">Tags</label>

    <div class="col-sm-6" id="tag${tagId}">
        <c:forEach items="${value.tagNames}" var="tagName">
            <span class="btn btn-xs tag-span" type="button" id="tagSpan${tagName}">
                    <span class="lbl" onclick="doClickTag${tagId}(tagSpan${tagName}, '${tagName}')">${tagName}</span>
                       <a class="remove" onclick="doRemoveTag${tagId}(tagSpan${tagName}, '${tagName}');">
                           <i class="icon-remove tag-icon-remove"></i>
                       </a>
                    </span>
            </span>
            &nbsp;&nbsp;&nbsp;&nbsp;
        </c:forEach>
    </div>
    <div class="col-sm-1">
        <button class="btn btn-xs btn-primary" onclick="doEditorAdd${tagId}();" type="button">
            <span class="lbl">Add Tag</span>
        </button>
    </div>

</div>
<div class="col-sm-8 form-group">
    <tags:uploadFile id="${fileId}" name="${name}"
                     fileName="${fileName}"
                     css="input-medium requirements ${css}"/>
</div>
<script type="text/javascript">
    var tagObject;
    var tagsList = new Array();
    function doClickTag${tagId}(e, tagName) {
        //add class
        // $(e).addClass('btn-primary');

        doEditTag${id}(tagName);
    }

    function doEditorAdd${tagId}() {
        doEditTag${tagId}('');
    }

    function doEditTag${id}(_tagName) {
        var _module = $('#module${tagId}').val();
        var _editorCode = $('#editorCode${tagId}').val();
        //close file input
        $(document).find('.ace-file-input input[type="file"]').each(function (index, item) {
            $(this).width('0');
        });
        Matrix.prototype.ajaxPopupWindowExtend({
                    title: 'Editor Tag Form',
                    url: '${ctx}/editor/ajax/tag-info',
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
                                if (!$('#testValue').hasClass('required')) {
                                    $('#testValue').addClass('required');
                                }
                                $('#frm_editor_tag').ajaxSave({
                                    url: '${ctx}/editor/ajax/tag-info/test'
                                })
                            }
                        },
                        {
                            text: "Save",
                            "class": "btn btn-primary btn-xs",
                            click: function () {
                                var flag = true;
                                var _tagNameVal = $('#tagNameVal').val();
                                var _element = $(this);

                                if ($('#testValue').hasClass('required')) {
                                    $('#testValue').removeClass('required');
                                }
                                $('span.lbl').each(function () {
                                    var _lblVal = $(this).text();
                                    if (_lblVal == _tagNameVal) {
                                        flag = false;
                                    }
                                });
                                if (flag) {
                                    doAddTag${id}(_tagNameVal);
                                    doAddConstantsToJson();
                                }
                                _element.dialog('destroy').remove();
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
                                var _tagNameVal = $('#tagNameVal').val();
                                if ($('#testValue').hasClass('required')) {
                                    $('#testValue').removeClass('required');
                                }
                                $('#frm_editor_tag').ajaxSave({
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

                                //close file input
                                $(document).find('.ace-file-input input[type="file"]').each(function (index, item) {
                                    $(this).width('100%');
                                });
                            }
                        }
                    ]
                }
        )
        ;
    }

    function doAddTag${id}(_tagNameVal) {
        $("#tag${id}").append(' <span class="btn btn-xs tag-span"  type="button" id="tagSpan' + _tagNameVal + '">' +
                '<span class="lbl" onclick="doClickTag${id}(tagSpan' + _tagNameVal + ', \'' + _tagNameVal + '\')">' + _tagNameVal + '</span>' +
                '<a class="remove" onclick="doRemoveTag${id}(tagSpan' + _tagNameVal + ', \'' + _tagNameVal + '\')">' +
                '<i class="icon-remove tag-icon-remove" ></i>' +
                '</a>' +
                '</span>' +
                '</span>' +
                '&nbsp;&nbsp;&nbsp;&nbsp;');
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
            'id': $('#frm_editor_tag').find('input[name="id"]').val(),
            'tagName': $('#tagNameVal').val(),
            'sourceFrom': $('#sourceForm').val(),
            'outputType': $('#outputType').val(),
            'viewName': $('#viewName').val(),
            'fieldName': $('#fieldName').val(),
            'viewKey': $('#viewKey').val(),
            'testValue': $('#testValue').val()
        }
        tagsList.push(tagObject);
    }

    function doBindJsonToForm(e, bindPropertyName) {
        $('#' + e).find('input[name="' + bindPropertyName + '"]').val(JSON.stringify(tagsList));
    }

</script>



