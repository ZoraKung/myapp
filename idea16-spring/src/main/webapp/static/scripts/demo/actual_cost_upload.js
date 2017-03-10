Matrix.prototype.confirmEntryUpload = function () {
    var ttl = "Upload Confirm"
    bootbox.dialog({
        message: 'Are you sure you want to upload the data?',
        title: ttl,
        buttons: {
            danger: {
                label: i18nQuery('button_ok'),
                className: "btn-danger",
                callback: function () {
                    $('#actual_cost_upload_file').uploadify('upload', '*');
//                            $("#actual_cost_upload_Form").submit();
                }
            },
            main: {
                label: i18nQuery('button_cancel'),
                className: "btn-primary",
                callback: function () {
                }
            }
        }
    });
    return false;
}

$(function () {
    var jsessionid = $("#jsessionid").val();
    $("#btn_actual_cost_upload_submit").bind("click", function () {
        Matrix.prototype.confirmEntryUpload();
    });
    $("#btn_actual_cost_upload_cancel").bind("click", function () {
        window.location = _appContext + "/home";
    });
    $("#actual_cost_upload_file").uploadify({
        method: 'post',
        'swf': _appContext + '/static/jquery/customize/uploadify/uploadify.swf',
        'uploader': _appContext + '/kso/actual/upload/cost;jsessionid=' + jsessionid,
        'buttonText': '上传',
        'buttonImage': _appContext + '/static/jquery/customize/uploadify/browse-btn.png',
        fileTypeExts: '*.xls;*.xlsx',
        multi: false,
        auto: false,
//        method: 'post',
//        'swf': _appContext + '/static/jquery/customize/uploadify/uploadify.swf',
//        'uploader': _appContext + '/kso/actual/upload/cost;jsessionid=' + jsessionid,
//        fileObjName: 'file',         // The name of the file object to use in your server-side script
//        fileSizeLimit: 0,                  // The maximum size of an uploadable file in KB (Accepts units B KB MB GB if string, 0 for no limit)
//        successTimeout: 30,                 // The number of seconds to wait for Flash to detect the server's response after the file has finished uploading
//        removeCompleted: true,              // Remove queue items from the queue when they are done uploading
//        removeTimeout: 0,
//        width: 150,
//        height: 30,
//        multi: false,
//        auto: false,
//        buttonClass: 'btn hide',
//        buttonText: 'Upload',
//        button_image_url: _appContext + '/static/jquery/customize/uploadify/browse-btn.png',   //if not setup , send request to list (current context path)
//        hideButton: 'false',
//        fileTypeExts: '*.xls;*.xlsx',
//        fileSizeLimit: '2MB',
//        fileTypeDesc: 'Excel Files',
//        'formData': {'local': ''},
        onUploadSuccess: function (file, data, response) {
            var isFoundError = false;
            var logId;
            if (data != undefined) {
                var map = eval('(' + data + ')');
                for (var key in map) {
                    if (key == 'isFoundError') {
                        isFoundError = map[key];
                    }
                    if (key == 'logId') {
                        logId = map[key];
                    }
                }
            }
            $("#logId").val(logId);
            $.ajax({
                type: 'post',//可选get
                url: _appContext + '/kso/actual/ajax/show-result',
                data: 'isFoundError=' + isFoundError,
                success: function (response) {
                    $("#result").html(response);
                },
                error: function () {
                }
            });
        }
    });
})
