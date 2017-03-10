<%@ tag language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="No" %>
<%@ attribute name="name" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="value" type="org.hksi.shared.mis.common.vo.EditorVo" required="true" description="Input Value" %>
<%@ attribute name="css" type="java.lang.String" required="false" description="Input Value" %>
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
<input type="hidden" id="id${id}" name="${name}.id" value="${value.id}"/>
<input type="hidden" id="module${id}" name="${name}.module" value="${value.module}"/>
<input type="hidden" id="editorCode${id}" name="${name}.editorCode" value="${value.editorCode}"/>

<div class="form-group tagClass">
    <label class="col-sm-1 control-label">Tags</label>

    <div class="col-sm-9" id="tag${id}">
        <c:forEach items="${value.tagNames}" var="tagName">
            <span class="btn btn-xs tag-span" type="button" id="tagSpan${tagName}">
                    <span class="lbl" onclick="doClickTag${id}(tagSpan${tagName}, '${tagName}')">${tagName}</span>
                       <a class="remove" onclick="doRemoveTag${id}(tagSpan${tagName}, '${tagName}');">
                           <i class="icon-remove tag-icon-remove"></i>
                       </a>
                    </span>
            </span>
            &nbsp;&nbsp;&nbsp;&nbsp;
        </c:forEach>
        <%--<span id="newTagName${tagName}"></span>--%>
    </div>
    <div class="col-sm-1">
        <button class="btn btn-xs btn-primary addBtn" onclick="doEditorAdd${id}();" type="button">
            <span class="lbl">Add Tag</span>
        </button>
    </div>
</div>
<div class="form-group">
    <textarea name="${name}.content" id="${id}" class="editor-content"><c:out value="${value.content}"/></textarea>
    <%--<textarea name="${name}.content" id="${id}" class="editor-content ckeditor">${value.content}</textarea>--%>
</div>
<script type="text/javascript">
    $(function () {
        var editor${id} = CKEDITOR.replace('${id}', {
            toolbar: 'TOOLBAR_GENERAL', height: 150,
			entities : false,
			basicEntities : false
			
        });
        editor${id}.on('change', function (e) {
            doChangeEvent${id}();
        });
    });

    function doChangeEvent${id}() {
        CKEDITOR.tools.setTimeout(function () {
            CKEDITOR.instances["${id}"].updateElement();
        }, 0);
    }

    function doClickTag${id}(e, tagName) {
        //add class
        // $(e).addClass('btn-primary');

        doEditTag${id}(tagName);
    }

    function doEditorAdd${id}() {
        doEditTag${id}('');
    }

    function doEditTag${id}(_tagName) {
        var _module = $('#module${id}').val();
        var _editorCode = $('#editorCode${id}').val();

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
                                $('#frm_editor_tag').ajaxSave({
                                    url: '${ctx}/editor/ajax/tag-info/save',
                                    callback: function () {
                                        $('span.lbl').each(function () {
                                            var _lblVal = $(this).text();
                                            if (_lblVal == _tagNameVal) {
                                                flag = false;
                                            }
                                        });
                                        if (flag) {
                                            doAddTag${id}(_tagNameVal);
                                        }
                                        _element.dialog('destroy').remove();
                                    }
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
                                    }
                                });
                            }
                        },
                        {
                            text: "Close",
                            "class": "btn btn-xs",
                            click: function () {
                                $(this).dialog('destroy').remove();
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
                $(e).closest('.tag-span').bind('click', 'doClickTag${id}');
            }
        });
        e.stopPropagation;
        return true;
    }
</script>


