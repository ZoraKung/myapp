/**
 * Created by LJJ on 14-6-4.
 */
$(function () {
    $("#btn_user_form_save").click(function () {
        _is_submit = true;
        var password = $("#pwd").val()
        var repeatPassword = $("#repwd").val()
        if (password != repeatPassword) {
            matrix.showError(i18nQuery('password_do_not_match'));
        }
        else {
            $("#userEditForm").submit();
        }
    });

    $("#btn_user_form_save_close").click(function () {
        _is_submit = true;
        var password = $("#pwd").val()
        var repeatPassword = $("#repwd").val()
        if (password != repeatPassword) {
            matrix.showError(i18nQuery('password_do_not_match'));
        }
        else {
            $("#userEditForm").attr("action", _appContext + "/sysadmin/user/saveAndClose");
            $("#userEditForm").submit();
        }
    });

    var userId = $("#userId").val();
    $("#userEditForm").validate(
        {
            rules: {
                loginName: {required: true,
                    remote: _appContext + "/sysadmin/user/validateLoginName?id=" + userId
                },
                email: {required: true,
                    email: true,
                    //remote: _appContext + "/sysadmin/user/validateEmail?id=" + userId
                },
                displayName: {
                    required: true
                }
            },
            messages: {
                loginName: {remote: i18nQuery('validate_duplicate')},
                //email: {remote: i18nQuery('validate_duplicate')}
            }
        }
    );

    $("#btn_user_form_cancel").click(function () {
        _is_submit = true;
        window.location.href = _appContext + "/sysadmin/user"
    });

    $("#adUserName").change(function () {
        var userName = $("select option:selected").val();
        $.ajax({
            url: _appContext + "/sysadmin/user/adUserName",
            type: "POST",
            dataType: "json",
            data: {userName: userName},
            success: function (data) {
                $("#lastName").val(data.lastName);
                $("#email").val(data.mail);
                $("#firstName").val(data.firstName);
            },
            error: function () {
            }
        });
    });

    $("#UserType").radioList("userType", "USER_TYPE", $("#masUserType").val());

    $("#status").radioList("status", "STATUS", $("#masUserStatus").val());

    $("#UserType").click(function () {
        var userTypeValue = $('input[name="userType"]:radio:checked').val();
        if (userTypeValue == "AD") {
            $("#password").hide();
            $("#adUserName").select2("enable", true);
        }
        if (userTypeValue == "LOCAL") {
            $("#password").removeClass('hide').show();
            $("#adUserName").select2("enable", false);
        }
    });

    var select2Val = $("#masAdUser").val();
    $("#adUserName").dropdownList("ad_user",select2Val);
    $("#adUserName").select2();
    $("#s2id_adUserName").find("span.select2-chosen").html(select2Val);
});