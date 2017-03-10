jQuery(function () {
    $.validator.setDefaults({
        errorElement: 'span',
        errorClass: 'help-inline',
        focusInvalid: false,
        showErrors: function (errorMap, errorList) {
            $.each(this.successList, function (index, value) {
                $(value).popover('hide');
            });
            $.each(errorList, function (index, value) {
                var _popover = $(value.element).popover({
                    trigger: 'manual',
                    placement: 'right',
                    content: value.message,
                    template: '<div class="popover"><div class="arrow alert-error"></div><div class="popover-inner"><div class="popover-content alert-error"><p></p></div></div></div>'
                });
                _popover.data('popover').options.content = value.message;
                $(value.element).popover('show');
            });
        },
        highlight: function (e) {
            $(e).closest('.control-group').removeClass('info').addClass('error');
        },
        success: function (e) {
            $(e).closest('.control-group').removeClass('error').addClass('info');
            $(e).remove();
        },
        errorPlacement: function (error, element) {
            if (element.is(':checkbox') || element.is(':radio')) {
                var controls = element.closest('.controls');
                if (controls.find(':checkbox,:radio').length > 1) controls.append(error);
                else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
            }
            else if (element.is('.select2')) {
                error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
            }
            else if (element.is('.chzn-select')) {
                error.insertAfter(element.siblings('[class*="chzn-container"]:eq(0)'));
            }
            else error.insertAfter(element);
        }
    });
    //set select default value
    $('select[value]').each(function () {
        $(this).val(this.getAttribute("value"));
    });
    // scrollables
    $('.slim-scroll').each(function () {
        var $this = $(this);
        $this.slimScroll({
            height: $this.data('height') || 100,
            railVisible: true
        });
    });
    //choose file
    $('input[type=file]').ace_file_input().closest('.ace-file-input').addClass('width-95 inline').wrap('<div class="file-input-container"><div class="span4"></div></div>');
});

//override dialog's title function to allow for HTML titles
$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
    _title: function (title) {
        var $title = this.options.title || '&nbsp;'
        if (("title_html" in this.options) && this.options.title_html == true)
            title.html($title);
        else title.text($title);
    }
}));

//custom autocomplete (category selection)
$.widget("custom.catcomplete", $.ui.autocomplete, {
    _renderMenu: function (ul, items) {
        var that = this,
            currentCategory = "";
        $.each(items, function (index, item) {
            if (item.category != currentCategory) {
                ul.append("<li class='ui-autocomplete-category'>" + item.category + "</li>");
                currentCategory = item.category;
            }
            that._renderItemData(ul, item);
        });
    }
});

//Open window
function windowOpen(url, name, width, height) {
    var top = parseInt((window.screen.height - height) / 2, 10), left = parseInt((window.screen.width - width) / 2, 10),
        options = "location=no,menubar=no,toolbar=no,dependent=yes,minimizable=no,modal=yes,alwaysRaised=yes," +
            "resizable=yes,scrollbars=yes," + "width=" + width + ",height=" + height + ",top=" + top + ",left=" + left;
    window.open(url, name, options);
}

//Tip Loading
function loadingTip(message) {
     $.gritter.add({
        title: '<h5><i class="icon-spinner icon-spin orange bigger-125"></i> ' + message + '</h5>',
        text: '',
        stick: true,
        time: '',
        class_name: 'gritter-info gritter-center gritter-light'
    });
}

//Tip Information
function infoTip(title, message) {
    $.gritter.add({
        title: title,
        text: message,
        stick: true,
        time: 3000,
        class_name: 'gritter-info gritter-center gritter-light'
    });
}

//Tip Warning
function warningTip(title, message) {
    $.gritter.add({
        title: title,
        text: message,
        stick: true,
        time: 3000,
        class_name: 'gritter-warning gritter-center gritter-light'
    });
}

//Tip Success
function successTip(title, message) {
    $.gritter.add({
        title: title,
        text: message,
        stick: true,
        time: 3000,
        class_name: 'gritter-success gritter-center gritter-light'
    });
}

//Tip Error
function errorTip(title, message) {
    $.gritter.add({
        title: title,
        text: message,
        stick: true,
        time: '',
        class_name: 'gritter-error gritter-center gritter-light'
    });
}

//Alert Box
function alert(message) {
    bootbox.alert(message);
}

//Confirm Box
function confirm(message, href) {
    bootbox.confirm(message, function (result) {
        if (result) {
            location = href;
        }
    });
    return false;
}

//Confirm Box
function confirm(message) {
    bootbox.confirm(message, function (result) {
        if (result) {
            return true;
        }else{
            return false;
        }
    });
    return false;
}

//prompt Box
function prompt(message, href) {
    bootbox.prompt(message, function (result) {
        if (result) {
            location = href + "/" + result;
        }
    });
    return false;
}

//Dialog Box
function dialog(url, title, width, height) {
    bootbox.dialog("iframe:" + url, [
        {
            "label": "Ok",
            "class": "btn-primary",
            "callback": function () {
                bootbox.hideAll();
            }
        }
    ]);
}

//Jquery dialog
function openShowDialog(url, title, width, height) {
    if(window.location!=window.parent.location){
        $=window.parent.jQuery;
    }else{
        $=jQuery;
    }
    var showIframe = $('<iframe src="' + url + '" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" allowfullscreen></iframe>');
    var showDialog = $("<div></div>").append(showIframe).appendTo("body").dialog({
        autoOpen: true,
        modal: true,
        resizable: true,
        width: (width == 0 || width == null ? 800 : width),
        height: (height == 0 || height == null ? 800 : height),
        title: "<div class='widget-header'><h4 class='smaller'><i class='icon-warning-sign red'></i>" + title + "</h4></div>",
        title_html: true,
        close: function () {
            showIframe.attr("src", "");
            $(this).dialog("destroy");
        },
        buttons: [
            {
                html: "<i class='icon-off bigger-110'></i>&nbsp; Close",
                "class": "btn btn-danger btn-mini",
                click: function () {
                    $(this).dialog("close");
                }
            }
        ]
    });
}



//Jquery dialog
function openHrefDialog(url, title, width, height, href, isKey, multiple) {
    if(window.location!=window.parent.location){
        $=window.parent.jQuery;
    }else{
        $=jQuery;
    }
    var hrefIframe = $('<iframe src="' + url + '" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" allowfullscreen></iframe>');
    var hrefDialog = $("<div></div>").append(hrefIframe).appendTo("body").dialog({
        autoOpen: true,
        modal: true,
        resizable: true,
        width: width,
        height: height,
        title: "<div class='widget-header'><h4 class='smaller'><i class='icon-warning-sign red'></i>" + title + "</h4></div>",
        title_html: true,
        close: function () {
            hrefIframe.attr("src", "");
            $(this).dialog("destroy");
        },
        buttons: [
            {
                html: "<i class='icon-ok bigger-110'></i>&nbsp; Select",
                "class": "btn btn-primary btn-mini",
                click: function () {
                    var doc = hrefIframe[0].contentWindow;
                    var returnValue = doc.doGetSelectResult(multiple);
                    if (returnValue != '') {
                        var returnArray = returnValue.split(",");
                        var key = "";
                        var value = "";
                        for (var i = 0; i < returnArray.length; i++) {
                            var valueArray = returnArray[i].split("|");
                            if (valueArray.length > 0) {
                                key = (key == "" ? "" : key + ",") + valueArray[0].trim();
                            }
                            if (valueArray.length > 1) {
                                value = (value == "" ? "" : value + ",") + valueArray[1].trim();
                            }
                        }
                        if (isKey) {
                            location = href + "/" + key;
                        } else {
                            location = href + "/" + value;
                        }
                        $(this).dialog("close");
                    }
                }
            }
            ,
            {
                html: "<i class='icon-off bigger-110'></i>&nbsp; Close",
                "class": "btn btn-danger btn-mini",
                click: function () {
                    $(this).dialog("close");
                }
            }
        ]
    });
}

//Jquery dialog
function openSaveDialog(url, title, width, height, saveCallback, deleteCallback) {
    if(window.location!=window.parent.location){
        $=window.parent.jQuery;
    }else{
        $=jQuery;
    }
    var saveIframe = $('<iframe src="' + url + '" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" allowfullscreen></iframe>');
    var saveDialog = $("<div></div>").append(saveIframe).appendTo("body").dialog({
        autoOpen: true,
        modal: true,
        resizable: true,
        width: width,
        height: height,
        title: "<div class='widget-header'><h4 class='smaller'><i class='icon-warning-sign red'></i>" + title + "</h4></div>",
        title_html: true,
        close: function () {
            saveIframe.attr("src", "");
            $(this).dialog("destroy");
        },
        buttons: [
            {
                id: 'dialog-select-button',
                html: "<i class='icon-success bigger-110'></i>&nbsp; Save",
                "class": "btn btn-primary btn-mini",
                click: function () {
                    var form = $(saveIframe[0].contentDocument.forms[0]);
                    if (form != null) {
                        $.post(form.attr("action"), form.serialize(), function (result) {
                            if (result.yn && result.msg) {
                                infoTip("Information", result.msg);
                                saveDialog.dialog("close");
                                if (saveCallback && result.data) {
                                    saveCallback(result.data);
                                }
                            }
                            if (!result.yn && result.msg) {
                                errorTip("Error for save", result.msg);
                            }
                        }, "json");
                    }
                }
            }
            ,
            {
                id: 'dialog-clear-button',
                html: "<i class='icon-remove bigger-110'></i>&nbsp; Delete",
                "class": "btn btn-inverse btn-mini",
                click: function () {
                    var form = $(saveIframe[0].contentDocument.forms[0]);
                    if (form != null) {
                        $.get(form.attr("action"), form.serialize(), function (result) {
                            if (result.yn && result.msg) {
                                //infoTip("Information", result.msg);
                                saveDialog.dialog("close");
                                if (deleteCallback) {
                                    deleteCallback();
                                }
                            }
                            if (!result.yn && result.msg) {
                                //errorTip("Error for delete", result.msg);
                            }
                        }, "json");
                    }
                }
            }
            ,
            {
                id: 'dialog-close-button',
                html: "<i class='icon-off bigger-110'></i>&nbsp; Close",
                "class": "btn btn-danger btn-mini",
                click: function () {
                    $(this).dialog("close");
                }
            }
        ]
    });
}

//Jquery dialog
function openSelectDialog(url, title, width, height, returnValueId, returnLabelId, multiple, required) {
    if(window.location!=window.parent.location){
        $=window.parent.jQuery;
    }else{
        $=jQuery;
    }
    var selectIframe = $('<iframe src="' + url + '" width="100%" height="100%" frameborder="0" marginwidth="0" marginheight="0" allowfullscreen></iframe>');
    var selectDialog = $("<div></div>").append(selectIframe).appendTo("body").dialog({
        autoOpen: true,
        modal: true,
        resizable: true,
        width: width,
        height: height,
        title: "<div class='widget-header'><h4 class='smaller'><i class='icon-warning-sign red'></i>" + title + "</h4></div>",
        title_html: true,
        close: function () {
            selectIframe.attr("src", "");
            $(this).dialog("destroy");
        },
        buttons: [
            {
                id: 'dialog-select-button',
                html: "<i class='icon-ok bigger-110'></i>&nbsp; Select",
                "class": "btn btn-primary btn-mini",
                click: function () {
                    var returnValue = "";
                    /*
                     var selectForm = $(iframe[0].contentDocument.forms[0]);
                     if (selectForm != null) {
                     if (multiple) {
                     //get selected data from session
                     var selectIds = "<%=session.getAttribute("idsString")%>";
                     if (selectIds != null && selectIds != "") {
                     returnValue = selectIds.replace("{", "").replace("}", "");
                     }
                     }
                     //get current page select data
                     var checkboxes = selectForm.find("tbody").find("tr").find("input[type='checkbox']");
                     $.each(checkboxes, function (index, item) {
                     if (this.checked && returnValue.indexOf(item.value) < 0) {
                     returnValue += (returnValue == "" ? "" : ",") + item.value;
                     }
                     if (!this.checked && returnValue.indexOf(item.value) >= 0) {
                     returnValue = (returnValue == "" ? "" : returnValue + ",").replace(item.value + ",", "");
                     }
                     });
                     }else{
                     */
                    var doc = selectIframe[0].contentWindow;
                    returnValue = doc.doGetSelectResult(multiple, required);
//                    }
                    if (returnValue == "" && required) {
                        errorTip("Error", "You select at least one .");
                        return false;
                    }
                    if (returnValue != "" && returnValue.split(",").length > 1 && !multiple) {
                        errorTip("Error", "You can only select one .");
                        return false;
                    }

                    var key = "";
                    var value = "";
                    if (returnValue != '') {
                        var returnArray = returnValue.split(",");
                        for (var i = 0; i < returnArray.length; i++) {
                            var valueArray = returnArray[i].split("|");
                            if (valueArray.length > 0) {
                                key = (key == "" ? "" : key + ",") + valueArray[0];
                            }
                            if (valueArray.length > 1) {
                                value = (value == "" ? "" : value + ",") + valueArray[1];
                            }
                        }
                    }
                    if (returnValueId != null && returnValueId != "") {
                        $('#' + returnValueId).val(key);
                    }
                    if (returnLabelId != null && returnLabelId != "") {
                        $('#' + returnLabelId).val(value);
                    }
                    $(this).dialog("close");
                }
            }
            ,
            {
                id: 'dialog-clear-button',
                html: "<i class='icon-remove bigger-110'></i>&nbsp; Remove",
                "class": "btn btn-inverse btn-mini",
                click: function () {
                    if (returnValueId != null && returnValueId != "") {
                        $('#' + returnValueId).val("");
                    }
                    if (returnLabelId != null && returnLabelId != "") {
                        $('#' + returnLabelId).val("");
                    }
                    $(this).dialog("close");
                }
            }
            ,
            {
                id: 'dialog-close-button',
                html: "<i class='icon-off bigger-110'></i>&nbsp; Close",
                "class": "btn btn-danger btn-mini",
                click: function () {
                    $(this).dialog("close");
                }
            }
        ]
    });
}

// post form
function postForm(form, action) {
    var objfrm = document.forms[formname];
    objfrm.setProperty("action", action);
    objfrm.submit();
}

//displaytag post form
function displaytagform(formname, fields) {
    var objfrm = document.forms[formname];
    for (j = fields.length - 1; j >= 0; j--) {
        var f = objfrm.elements[fields[j].f];
        if (f) {
            f.value = fields[j].v;
        }
    }
    objfrm.submit();
}

//String To List
stringToList = function (value) {
    if (value != undefined && value != '') {
        var values = [];
        var t = value.split(',');
        for (var i = 0; i < t.length; i++) {
            values.push('' + t[i]);
        }
        return values;
    } else {
        return [];
    }
};

//Date To String (yyyy-MM-dd)
dateToString = function (value) {
    if (value != undefined && value != '') {
        yy = value.getFullYear();
        mm = value.getMonth() + 1;
        dd = value.getDate();
        var currentDate = yy + '-';
        if (mm >= 10) {
            currentDate = currentDate + mm + '-';
        } else {
            currentDate = currentDate + '0' + mm + '-';
        }
        if (dd >= 10) {
            currentDate = currentDate + dd;
        } else {
            currentDate = currentDate + '0' + dd;
        }
        return currentDate;
    } else {
        return '';
    }
}
