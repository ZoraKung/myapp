$(function () {
        _is_submit = true,
        $("#form_master_type_add").validate({
        rules: {
            type: {
                required: true,
                maxlength: 100,
                remote: {
                    url: _appContext + "/sysadmin/master/validate/type",
                    type: "post",
                    dataType: "json",
                    data: {
                        type: function () {
                            var value = $("#type").val();
                            return value.toUpperCase();
                        },
                        masterTypeId: function () {
                            return encodeURIComponent($("#id").val())
                        }

                    }
                }
            },
            dataType:{
                maxlength: 20
            },
            seq: {
                digits: true,
                min: 0,
                maxlength:6
            }

        },
        messages: {
            type: {
                remote: i18nQuery("name_exists")
            }

        }
    });
    $("#btn_master_type_create_cancel").click(function () {
        location.href = _appContext + "/sysadmin/master";
    })

})
