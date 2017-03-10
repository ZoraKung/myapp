/**
 * Created by GTF on 14-5-23.
 */
$(function () {
    $("#btn_role_edit_save").click(function () {
        _is_submit = true;
        $("#myCPARoleEditForm").submit();
    });
    $("#btn_role_edit_save_close").click(function () {
        _is_submit = true;
        $("#myCPARoleEditForm").attr("action", _appContext + "/mycpaadmin/role/save-close");
        $("#myCPARoleEditForm").submit();
    });
    $("#btn_role_edit_cancel").click(function () {
        _is_submit = true;
        window.location = _appContext + "/mycpaadmin/role";
    });
    var roleId = $("#roleId").val();
    $("#myCPARoleEditForm").validate(
        {
            rules: {
                name: {
                    remote: _appContext + "/mycpaadmin/role/validation-name?id=" + roleId,
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