/**
 * Created by Janice on 15-1-28.
 */
$(function () {
    $("#workFlowMasterForm").find('.select2').select2();
    $("#status").radioList("status", "STATUS", $("#statusVal").val());
    $("#requireClaim").radioList("requireClaim", "common_require_claim", $("#requireClaimVal").val());
    $("#reassignUsers").dropdownList("mas_user", $("#reassignUsersVal").val());
    $("#code").dropdownList("com_workflow_code", $("#masterCode").val());
    $("#code").change(function () {
        _code = $("#code").val();
        var url = _appContext + "/work-flow/findNameByCode";
        if (_code != undefined && _code != '') {
            $.ajax({
                url: url,
                type: "POST",
                dataType: "json",
                data: {codeVal: _code, nameType: "com_workFlow_name"},
                success: function (data) {
                    if (data) {
                        $("#masterName").val(data.name);
                        if ($('#id').val() == '' || $('#id').val() == undefined){
                            $("#version").val(data.version);
                        }
                    }
                }
            })
        }
    })
});