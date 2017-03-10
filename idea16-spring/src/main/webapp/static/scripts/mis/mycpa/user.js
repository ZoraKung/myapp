/**
 * Created by LJJ on 14-6-4.
 */
$(function () {
    var myCPAUserId = $("#myCPAUserId").val();
    $("#myCPAUserEditForm").validate(
        {
            rules: {
                loginName: {required: true,
                    remote: _appContext + "/mycpaadmin/user/validateLoginName?id=" + myCPAUserId
                },
                email: {required: true,
                    email: true
                    // remote: _appContext + "/mycpaadmin/user/validateEmail?id=" + myCPAUserId
                },
                displayName: {
                    required: true
                }
            },
            messages: {
                loginName: {remote: i18nQuery('validate_duplicate')},
                email: {remote: i18nQuery('validate_duplicate')}
            }
        }
    );

    $("#btn_myCPAUser_form_save").click(function () {
        _is_submit = true;
        var password = $("#pwd").val()
        var repeatPassword = $("#repwd").val()
        if (password != repeatPassword) {
            matrix.showError(i18nQuery('password_do_not_match'));
        }
        else {
            $("#myCPAUserEditForm").submit();
        }
    });

    $("#status").radioList("status", "STATUS", $("#myCPAUserStatus").val());

    $("#btn_myCPAUser_form_save_close").click(function () {
        _is_submit = true;
        var password = $("#pwd").val()
        var repeatPassword = $("#repwd").val()
        if (password != repeatPassword) {
            matrix.showError(i18nQuery('password_do_not_match'));
        }
        else {
            $("#myCPAUserEditForm").attr("action", _appContext + "/mycpaadmin/user/saveAndClose");
            $("#myCPAUserEditForm").submit();
        }
    });

    $("#btn_myCPAUser_form_cancel").click(function () {
        _is_submit = true;
        window.location.href = _appContext + "/mycpaadmin/user/list"
    });

    $("#masterName").click(function () {
        $.ajax({
            type: "POST",
            url: _appContext + "/mycpaadmin/user/ajax",
            success: function (response) {
                if (!$('#show_master').length) {
                    $("body").append("<div id='show_selectUser_div'></div>");
                }
                $("#show_master").empty();
                $("#show_master").html(response);
            }
        });
    });

    $("#btn_myCPAUser_form_save_master").click(function () {
        $.ajax({
            type: "POST",
            url: _appContext + "/mycpaadmin/master/save",
            data: {
                userId: $("#myCPAUserId").val(),
                loginName: $("#loginName").val(),
                firstName: $("#firstName").val(),
                lastName: $("#lastName").val(),
                displayName: $("#displayName").val(),
                email: $("#email").val(),
                status: $("#myCPAUserStatus").val()},
            success: function (data) {
                if (data == 'false') {
                    matrix.showError(i18nQuery('validate_duplicate'));
                }
                if (data == 'true') {
                    matrix.showMessage(i18nQuery('master_save_success'))
                }
            }
        });
    });
})