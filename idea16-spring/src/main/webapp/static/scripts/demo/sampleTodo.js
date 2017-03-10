/**
 * Created by LDJ on 14-11-17.
 */
$(function () {
    $("#btn_todo_edit_save").click(function () {
        var formValid = $("#todoEditForm").valid();
        if (formValid) {
            $("#todoEditForm").submit();
        }
    });

    $("#btn_todo_edit_save_close").click(function () {
        $("#todoEditForm").attr("action", _appContext + "/test/save-close");
        var formValid = $("#todoEditForm").valid();
        if (formValid) {
            $("#todoEditForm").submit();
        }
    });

    $("#btn_todo_edit_cancel").click(function () {
        window.location = _appContext + "/test/list";
    });

    // Validate
    $("#todoEditForm").validate({
        rules: {
            scheduledDate: {
                required: true
            },
//            location:{
//                required: true
//            },
            timeFrom:{
                required: true
            },
            timeTo:{
                required: true
            },
            progress:{
                required: true,
                number: true,
                range:[0,100]
            },
            status:{
                required: true
            },
            description:{
                required: true
            }
        },
        submitHandler: function (form) {
            $.gritter.add({
                title: '<h5><i class="icon-spinner icon-spin orange bigger-125"></i> ' + 'Save...' + '</h5>',
                text: '',
                stick: true,
                time: '',
                class_name: 'gritter-info gritter-center gritter-light'
            });
            form.submit();
        }
    });

});