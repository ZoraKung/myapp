/**
 * Created by GTF on 14-5-7.
 */
$(function () {

    scrollToTop("#page-toolbar-pin-flag", "#page-toolbar", "page-toolbar-fixed", "page-toolbar");

    $("#btn_role_edit_save").click(function () {
        _is_submit = true;
        $("#roleEditForm").submit();
    });
    $("#btn_role_edit_save_close").click(function () {
        _is_submit = true;
        $("#roleEditForm").attr("action", _appContext + "/sysadmin/role/save-close");
        $("#roleEditForm").submit();
    });
    $("#btn_role_edit_cancel").click(function () {
        _is_submit = true;
        window.location = _appContext + "/sysadmin/role";
    });
    var roleId = $("#roleId").val();
    // Validate
    $("#roleEditForm").validate(
        {
            rules: {
                name: {
                    remote: _appContext + "/sysadmin/role/validation-name?id=" + roleId,
                    required: true
                }
            },
            messages: {
                name: {
                    remote: function () {
                        matrix.showMessage(i18nQuery('validate_duplicate'))
                    }
                }
            }
        }
    );
});