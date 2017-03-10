var commentId = null;
function loadUploadMessage(message) {
    var msg = message.msg;
    var fileId = message.fileId
    $("#fileId").val(fileId);
    if (msg) {
        matrix.showMessage(msg);
    }
}

function reloadGrid(data) {
    data = eval("(" + data + ")")
    var message = data.tips;
    if (data) {
        if (data.state == 'true') {
            matrix.showMessage(message + "  success");
        } else {
            matrix.showMessage(message + "  failed");
        }

        $("#task_comment_grid").trigger("reloadGrid");
        if (CKEDITOR.instances['textarea_comment_content']) {
            editor.destroy();
        }
        $("#textarea_comment_content").val("")
        $("#new_comment").addClass("hide");
    }
    $("#commentdiv").find("#parentId").val("");
    $("#commentdiv").find("#fileId").val("");
}

function editComment(e) {
    $("#commentdiv").find("#multipartFile").val("");
    if (CKEDITOR.instances['textarea_comment_content']) {
        matrix.showMessage("open failed :Please close the edit box");
    } else {
        var id = $(e.target).closest("tr.jqgrow").attr("id");
        commentId = id;
        var randomInteger = Math.round(Math.random() * 1000 + 1);
        $.ajax({
            type: "post",
            dataType: "text",
            data: {id: id},
            url: _appContext + "/task-comments/edit/" + randomInteger,
            success: setEditor,
            error: function (xmlobj, state, errorThrown) {
                alert("network occur error");
            }
        });
    }
}

function replyComment(e) {
    var id = $(e.target).closest("tr.jqgrow").attr("id");
    $("#commentdiv").find("#parentId").val(id);
    addComment();
}

function download(e) {
    var cid = $(e.target).closest("tr.jqgrow").attr("id");
    var params = {
        "comId": cid
    };
    $.ajax({
        type: "post",
        url: _appContext + "/task-comments/check_document_exits",
        data: params,
        dataType: "text",
        error: function (textStatus, errorThrown) {
            matrix.showError("ERROR");
        },
        success: function (data, textStatus) {
            if (data == "true") {
                window.location.href = _appContext + "/task-comments/download?comId=" + cid;
            }
            else {
                matrix.showError(i18nQuery("file_not_exist"));
            }
        }
    });
}

function setEditor(data) {
    if (data) {
        data = eval("(" + data + ")")
        var commentResource = data.comment;
        var documentIdResource = data.documentEntityId;
        var documentNameResource = data.documentName;
        $("#new_comment").removeClass('hide').show();
        $("#textarea_comment_content").after("<script>var editor = CKEDITOR.replace('textarea_comment_content');" +
            " </script>");
        editor.setData(commentResource);

//        $("#multipartFile").before("<input name=\"multipartFile\" type=\"file\" id=\"multipartFile\" class=\"uploadFile input-large\"/>").remove();
        if (documentIdResource) {
            $("#multipartFile").parent().find("a").each(function () {
                $(this).remove();
            })

            $("#multipartFile").after("<a href=" + _appContext + "/task-comments/download?docId=" + documentIdResource + ">" + documentNameResource + "</a>")
        }
    }

    else {
        commentId = null;
    }
}

function addComment() {
    commentId = null;
    if (CKEDITOR.instances['textarea_comment_content']) {
        matrix.showMessage("open failed :Please close the edit box");
    } else {
        $("#new_comment").removeClass('hide').show();
        $("#textarea_comment_content").after("<script>var editor = CKEDITOR.replace('textarea_comment_content');" +
            " </script>");
        $("#multipartFile").after("<input name=\"multipartFile\" type=\"file\" id=\"multipartFile\" class=\"uploadFile input-large\"/>").remove();
        $("#multipartFile").parent().find("a").each(function () {
            $(this).remove();
        })
    }
}

function operateGrid(data) {
    if ("add" == data || "edit" == data || "del" == data) {
        matrix.showMessage(data + "success");
    } else {
        matrix.showMessage(data);
    }

}

$(function () {
    $("#btn_task_list_criteria_search").bind("click", function () {
        jqGridSearch("task_list_grid", _appContext + "/task-tracking/search", "task_search_criteria_form", true);
        $("#search_result").removeClass("hide");
    })

    $(function () {
        jQuery.validator.addMethod("ComparedToToday", function (value, element) {
            var date = new Date();
            var starttime = new Date(date.getFullYear(), date.getMonth() + 1, date.getDate());
            var starttimes = starttime.getTime();

            var arrs = value.split("-");
            var endtime = new Date(arrs[0], arrs[1], arrs[2]);
            var endtimes = endtime.getTime();

            return this.optional(element) || endtimes - starttimes >= 0;
        }, "Must be greater than today");
    })

    $("#form_task_edit_generalInfo").validate({
        rules: {
            name: {
                required: true,
                maxlength: 30,
                remote: {
                    url: _appContext + "/task/check_name",
                    type: "post",
                    dataType: "json",
                    data: {
                        id: function () {
                            return encodeURIComponent($("#tid").val());
                        },
                        name: function () {
                            return $("#name").val();
                        }
                    }
                }
            },
            description: {
                maxlength: 40
            },
            priority: {
                required: true
            },
            dueDate: {
                required: true,
                dateISO: true,
                ComparedToToday: true
            },
            personNames: {
                required: true
            },
            completionPercentage: {
                digits: true,
                range: [0, 100]
            }
        },
        messages: {
            name: {remote: "Name is exists."}
        }
    });
    function showRequest() {
        return $("#form_task_edit_generalInfo").valid();
    }

    $("#btn_task_list_save").click(function () {
        if (showRequest) {
            $("#form_task_edit_generalInfo").submit();
        }
    })
    $("#btn_task_list_cancel").click(function () {
        location.href = _appContext + "/task/index";
    })
    $("#btn_task_new_comment_upload").click(function () {
        if (getFileSize(document.all('multipartFile').value)) {
            $("#task_tracking_uploadForm").submit();
        }
    })

    function convertArray(o) {
        var v = {};
        for (var i in o) {
            if (typeof (v[o[i].name]) == 'undefined') v[o[i].name] = o[i].value;
            else v[o[i].name] += "," + o[i].value;
        }
        return v;
    }

    $("#btn_task_new_comment_publish").click(function () {
        var taskId = $("#tid").val();
        var commentContent = editor.document.getBody().getHtml();
        var randomInteger = Math.round(Math.random() * 1000 + 1);
        var uploadFileId = $("#fileId").val();
        var parentId = $("#parentId").val();
        $.ajax({
            type: "post",
            dataType: "text",
            data: {
                taskId: taskId,
                comment: commentContent,
                documentId: uploadFileId,
                id: commentId,
                parentId: parentId
            },
            url: _appContext + "/task-comments/publish?" + randomInteger,
            success: reloadGrid,
            error: function (xmlobj, state, errorThrown) {
                alert("network occur error");
            }
        });
    })

    $("#btn_task_new_comment_cancel").click(function () {
        if (CKEDITOR.instances['textarea_comment_content']) {
            editor.destroy();
        }
        $("#textarea_comment_content").val("")
        $("#new_comment").addClass("hide");
        $("#commentdiv").find("#parentId").val("");
        $("#multipartFile").parent().find("a").each(function () {
            $(this).remove();
        })
    })

    $("#select-users").click(function () {
        var userIds = $('#personIds').val();
        var userNames = $('#personNames').val();
        var callback = function (uIds, uNames, options) {
            $('#personIds').val(uIds);
            $('#personNames').val(uNames);
        }
        showLog({
            userNames: userNames,
            userIds: userIds
        }, callback);

    });

});
//--delete--
jQuery(function ($) {
    $("#del_OK").click(function () {
        del();
    });

    $("#del_Cancel").click(function () {
        $("#del_dialog").hide();
    });
});

function del() {
    $("#del_dialog").hide();
    var delid = $("#del_dialog").find("#delId").val();
    var gridname = $("#del_dialog").find("#gridname").val();
    var delurl = $("#del_dialog").find("#delurl").val();
    var params = {
        "id": delid
    };
    $.ajax({
        type: "post",
        url: _appContext + delurl,
        data: params,
        dataType: "text",
        cache: false,
        error: function (textStatus, errorThrown) {
            matrix.showError("ERROR");
        },
        success: function (data, textStatus) {
            var obj =  eval("("+data+")");
            if (obj.message == "success") {
                $("#" + gridname).jqGrid("delRowData", obj.id);
                matrix.showMessage(i18nQuery('delete_success'));
            } else {
                matrix.showMessage(i18nQuery('delete_failed'));
            }
        }
    });
};


function getFileSize(filename) {
    if (filename == '') {
        return false;
    }
    return true;
}

