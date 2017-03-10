function show_property() {
    var dialog = $("#property_list").removeClass('hide').dialog({
        modal: true,
        title: "<div class='widget-header widget-header-small'><h4 class='smaller'>Audit Fields</h4></div>",
        title_html: true,
        position: 'center',
        width: '700',
        height: '500',
        buttons: [
            {
                text: "Close",
                "class": "btn btn-xs",
                click: function () {
                    $(this).dialog("close");
                }
            }
        ]
    })
}
function findproperty(modelid) {
    var params = {
        "id": modelid
    };
    $.ajax({
        type: "post",
        url: _appContext + "/sysadmin/audit/config/findproperty",
        data: params,
        dataType: "json",
        cache: false,
        error: function () {
            alert("error");
        },
        success: function (data) {
            if (data.msg == "success") {
                var num = 0;
                var arrayObj = new Array();
                $("#property_list_table").empty();
                var backdata = data.data;
                $.each(backdata, function (i, val) {
                    if (!val.leaf) {
                        arrayObj.push(val);
                        return true;
                    }

                    var len = backdata.length;
                    if (num % 3 == 0) {
                        $("#property_list_table").append('<div class="row">');
                    }
                    if (val.status == "1" && val.level == 0) {
                        $("#property_list_table").append('<div class="form-group col-xs-4">' +
                            '<input name="property" type="checkbox" id="' + val.id + '" value="' + val.id +
                            '" checked="checked" class="ace" onclick="changstate(\'' + val.id + '\')" />' +
                            '<span class="lbl"><a href="#" onclick="changstate(\'' + val.id + '\')">' + val.propertyDesc + '</a></span></label></div>');
                        num++;
                    }
                    if (val.status == "0" && val.level == 0) {
                        $("#property_list_table").append('<div class="form-group col-xs-4">' +
                            '<input name="property" type="checkbox" id="' + val.id + '" value="' + val.id +
                            '"class="ace" onclick="changstate(\'' + val.id + '\')" />' +
                            '<span class="lbl"><a href="#" onclick="changstate(\'' + val.id + '\')">' + val.propertyDesc + '</a></span></label></div>');
                        num++;
                    }
                    if (num % 3 == 0) {
                        $("#property_list_table").append('</div>');
                    }
                })




                if (arrayObj.length > 0) {
                    $.each(arrayObj, function (i, val) {
                        if (!val.leaf && val.status == "1") {
                            /*$("#property_list_table").append('<hr/><div class="form-group col-xs-12">' +
                             '<div class="widget-box transparent">' +
                             '<div class="widget-header widget-header-flat">' +
                             '<h5 class="lighter">' +
                             '<input class="ace" name="property" id="' + val.id + '" type="checkbox" checked="checked" onclick="changstate(' + val.id + ')" />' +
                             '<span class="lbl"><a href="#" onclick="changstate(\'' + val.id + '\')">' + val.propertyDesc + '</a></span></h5>' +
                             '<div class="widget-toolbar">' +
                             '<span id="i' + val.id + '" onclick="findchild(\'' + val.id + '\')"> <a href="#" data-action="collapse">' +
                             '<i class="icon-chevron-up"></i></a></span>' +
                             '</div></div>' +
                             '<div id="n' + val.id + '" style="display:none">' +
                             '<div class="widget-body"></div>' +
                             '</div></div></div>');*/
                            $("#property_list_table").append('<label class="middle col-sm-6">' +
                                '<input name="' + name + '" type="checkbox" value="' + val.id +
                                '" checked="checked" onclick="changstate(\'' + val.id + '\')" class="ace"/>' +
                                '<span class="lbl">' + val.propertyDesc + '</span></label>');

                        }
                        if (!val.leaf && val.status == "0") {
                            /*$("#property_list_table").append('<hr/><div class="form-group col-xs-12">' +
                             '<div class="widget-box transparent">' +
                             '<div class="widget-header widget-header-flat">' +
                             '<h5 class="lighter">' +
                             '<input class="ace" name="property" id="' + val.id + '" type="checkbox" onclick="changstate(\'' + val.id + '\')"/>' +
                             '<span class="lbl"><a href="#" onclick="changstate(\'' + val.id + '\')">' + val.propertyDesc + '</a></span></h5>' +
                             '<div class="widget-toolbar">' +
                             '<span id="i' + val.id + '" onclick="findchild(\'' + val.id + '\')"><a href="#" data-action="collapse">' +
                             '<i class="icon-chevron-up"></i></a></span>' +
                             '</div></div>' +
                             '<div id="n' + val.id + '" style="display:none">' +
                             '<div class="widget-body"></div>' +
                             '</div></div></div>');*/

                            $("#property_list_table").append('<label class="middle col-sm-6">' +
                                '<input name="' + name + '" type="checkbox" value="' + val.id +
                                '" onclick="changstate(\'' + val.id + '\')" class="ace"/>' +
                                '<span class="lbl">' +val.propertyDesc + '</span></label>');
                        }
                    })
                }
                show_property();
            }
        }
    });
}

function findchild(parentid) {
    var $this = $("#n" + parentid);
    var $icon = $("#i" + parentid);
    var num = 0;
    if ($this.css("display") == "none") {
        $this.css("display", "block");
    } else {
        $this.css("display", "none");
    }
    var $i = $icon.find("i");
    if ($i.hasClass("icon-chevron-up")) {
        $i.replaceWith('<i class="icon-chevron-down"></i>');
    } else {
        $i.replaceWith('<i class="icon-chevron-up"></i>');
    }
    var params = {
        "id": parentid
    };
    $.ajax({
        type: "post",
        url: _appContext + "/sysadmin/audit/config/findchild",
        data: params,
        dataType: "json",
        cache: false,
        error: function () {
            alert("error");
        },
        success: function (data) {
            $this.empty();
            if (data.msg == "success") {
                var backdata = data.data;
                $.each(backdata, function (i, val) {
                    var len = backdata.length;
                    if (num % 3 == 0) {
                        $("#property_list_table").append('<div class="row">');
                    }
                    if (val.status == "1") {
                        $this.append('<div class="form-group col-xs-4">' +
                            '<input name="property" type="checkbox" id="' + val.id + '" value="' + val.id +
                            '" checked="checked" class="ace" onclick="changstate(\'' + val.id + '\')" />' +
                            '<span class="lbl"><a href="#" onclick="changstate(\'' + val.id + '\')">' + val.propertyDesc + '</a></span></label></div>');
                        num++;
                    }
                    if (val.status == "0") {
                        $this.append('<div class="form-group col-xs-4">' +
                            '<input name="property" type="checkbox" id="' + val.id + '" value="' + val.id +
                            '" class="ace" onclick="changstate(\'' + val.id + '\')" />' +
                            '<span class="lbl"><a href="#" onclick="changstate(\'' + val.id + '\')">' + val.propertyDesc + '</a></span></label></div>');
                        num++;
                    }
                    if (num % 3 == 0) {
                        $("#property_list_table").append('</div>');
                    }
                })
            }
        }
    })
}
function changstate(b) {
    var params = {
        "id": b
    };
    $.ajax({
        type: "post",
        url: _appContext + "/sysadmin/audit/config/changstate",
        data: params,
        success: function (data) {
            var obj = eval("(" + data + ")");
            if (obj.msg == "success") {
                if (obj.data == "1") {
                    $("#" + b).prop("checked", true);
                } else {
                    $("#" + b).prop("checked", false);
                }
            }

        }
    })
}

jQuery(function ($) {

    $("#consoleForm").validate({
        rules: {
            entityClass: {
                required: true
            }
        }
    });
});
function showRequest() {
    return $("#consoleForm").valid();
}

function saveform() {
    if (showRequest()) {
        var params = $("#consoleForm").serialize();
        var addactionUrl = _appContext + "/sysadmin/audit/config/save";
        $.ajax({
            url: addactionUrl,
            data: params,
            dataType: "text",
            cache: false,
            error: function (textStatus, errorThrown) {
                alert("ajax error: " + textStatus);
            },
            success: function (data, textStatus) {
                if (data == "success") {
                    matrix.showMessage(i18nQuery('entity_save_success'));
                    $("#entityClass option:selected").remove();
                    $("#addDlg").dialog("close");
                    $("#audit_config_grid").trigger("reloadGrid");
                } else {
                    matrix.showMessage(i18nQuery('entity_save_failed'));
                }
            }
        });
    }
}