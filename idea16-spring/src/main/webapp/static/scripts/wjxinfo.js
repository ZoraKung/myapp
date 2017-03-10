//var i18nBundlePath = _appContext + '/static/scripts/bundle/';

// - Work : /{contextPath}/static/js/bundle/
// - Not work : js/bundle/
jQuery.i18n.properties({
    name: 'Message',
    path: i18nBundlePath,
    mode: 'both',
    language: _appLanguage
});

function i18nQuery(messageKey) {
    return jQuery.i18n.prop(messageKey);
}

/**
 * Escape Special Chars
 *
 * @returns {string}
 */
String.prototype.escapeSpecialChars = function () {
    return this.replace(/\n/g, "\\\n")
        .replace(/\\/g, "\\\\")
        .replace(/'/g, "\\\'")
        .replace(/"/g, "\\\"")
        .replace(/&/g, "\\\&")
        .replace(/\r/g, "\\\r")
        .replace(/\t/g, "\\\t")
        //        .replace(/\b/g, "\\\b")
        .replace(/\f/g, "\\\f");
};

var entityMap = {
    " ": "&nbsp;",
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': '&quot;',
    "'": '&#39;',
    "/": '&#x2F;',
    "\\": "\\\\"
};

function escapeHtml(string) {
    return String(string).replace(/[ &<>"'\/\\]/g, function (s) {
        return entityMap[s];
    });
}

function analysisXml(string) {
    string = string.replace(/&nbsp;/g, " ");
    string = string.replace(/&amp;/g, "&");
    string = string.replace(/&lt;/g, "<");
    string = string.replace(/&gt;/g, ">");
    string = string.replace(/&quot;/g, '"');
    string = string.replace(/&#39;/g, "'");
    string = string.replace(/&#x2F;/g, "/");
    return string;
}


/**
 * matrix.onSkinChange
 * Date.format
 *
 */

function Matrix(args) {
}
var matrix = new Matrix();

Matrix.prototype.stringToList = function (value, separator) {
    if (value != undefined && value != '') {
        var values = [];
        var t = value.split(separator);
        for (var i = 0; i < t.length; i++) {
            values.push('' + t[i]);
        }
        return values;
    } else {
        return [];
    }
}
//convert "id1, id2" to "'id1','id2'"
Matrix.prototype.stringToListString = function (value, separator) {
    if (value != undefined && value != "") {
        var values = "";
        var t = value.split(separator);
        for (var i = 0; i < t.length; i++) {
            if (values != "") {
                values += separator;
            }
            values += "'" + t[i] + "'";
        }
        return values;
    } else {
        return "";
    }
}

Matrix.prototype.mRenderForDateTimeField = function (data, type, full) {
    var javascriptDate = new Date(data);
    // For IE
    if (javascriptDate == 'Invalid Date') {
        return "<div class='date'>" + data.substring(0, 16) + "<div>";
    }

    return "<div class='date'>" + javascriptDate.format('dd-MM-yyyy HH:mm') + "<div>";
}

Matrix.prototype.mRenderForDateField = function (data, type, full) {
    var javascriptDate = new Date(data);
    // For IE
    if (javascriptDate == 'Invalid Date') {
        return "<div class='date'>" + data.substring(0, 10) + "<div>";
    }

    return "<div class='date'>" + javascriptDate.format('dd-MM-yyyy') + "<div>";
}

Matrix.prototype.mRenderForBooleanField = function (data, type, full) {
    //alert(data);

    if (data == null || data == '') {
        return "";
    }
    if (data) {
        return "<i class='bigger-110 icon-ok-sign blue'></i>";
    }
    else {
        return "No";
    }
}

Matrix.prototype.mRenderForAction = function (data, type, full, options) {
    if (options == undefined) {
        return "<td>&nbsp;</td>";
    }

    if (options.showAsPopup == undefined) {
        options.showAsPopup = false;
    }

    var path = options.path;
    var popupPath = path + '/popup/' + data;
    var showPath = path + '/show/' + data;
    var editPath = path + '/edit/' + data;

    if (options.deleteAction == undefined) {
        options.deleteAction = "matrix.confirmEntryDeletion";
    }


    var cell = '<td><div class="visible-md visible-lg visible-sm visible-xs action-buttons">';
    if (options.showAsPopup) {
        cell += '<a class="blue" href="#" ';
        //cell += ' onclick="matrix.popupWindow(\'' + popupPath + '\',null,null,{width:' + options.width + '});">';
        cell += ' onclick="matrix.popupWindow(\'' + popupPath + '\',null,null,null);">';
        cell += '  <i class="fam-application-view-detail bigger-130"></i></a>';
    }
    else {
        cell += '<a class="blue" href="' + showPath + '">';
        cell += '  <i class="icon-zoom-in bigger-130"></i></a>';
    }

    cell += '<a class="green" href="' + editPath + '">';
    cell += '  <i class="icon-pencil bigger-130"></i></a>';

    cell += '<a class="red" href="#" ';
    cell += ' onclick=\'javascript:' + options.deleteAction + '("' + +data + '");\'>';
    cell += '  <i class="icon-trash bigger-130"></i></a>';

    cell += '</div></td>';

    return cell;
}


Matrix.prototype.onSkinChange = function () {
    var c_name = "skin";
    var c_value = "";
    var colorValue = $("#skin-colorpicker").val();

    if (colorValue == '#438EB9') {
        c_value = ""; // Default
    }
    else if (colorValue == '#222A2D') {
        c_value = "skin-1"; //
    }
    else if (colorValue == '#C6487E') {
        c_value = "skin-2"; //
    }
    else if (colorValue == '#D0D0D0') {
        c_value = "skin-3"; //
    }
    var exDate = new Date()
    exDate.setDate(exDate.getDate() + 365)

    //ace.cookie.set(c_name, c_value, exDate.toGMTString(), "/", "", "");
    ace.cookie.set(c_name, c_value, exDate, "/", "", "");
}

Matrix.prototype.popupWindow = function (url, dataParams, buttons, dialogOptions) {
    if ($("#popupPlaceHolder").length == 0) {
        $("<div id='popupPlaceHolder' style='display: none;'></div>").appendTo("body");
    }

    var pwin = $("#popupPlaceHolder");
    if (dataParams == undefined || dataParams == null) {
        dataParams = {}
    }

    if (buttons == undefined || buttons == null) {
        buttons = [
            {
                html: "<i class='icon-ok bigger-110'></i>&nbsp;" + i18nQuery('button_ok'),
                "class": "btn btn-xs",
                click: function () {
                    $(this).dialog("close");
                }
            },
            {
                html: "<i class='icon-remove bigger-110'></i>&nbsp;" + i18nQuery('button_cancel'),
                "class": "btn btn-xs",
                click: function () {
                    $(this).dialog("close");
                }

            }
        ];
    }

    if (dialogOptions == undefined) {
        dialogOptions = {
            //title: "<div class='widget-header'><h4 class='smaller'><i class='icon-info blue'></i>Details</h4></div>"
            title: "<div class='widget-header'><h4 class='smaller'><i class='icon-info blue'></i></h4></div>"
        }
    }

    if (dialogOptions.iWidth == undefined) {
        dialogOptions.iWidth = 980;
    }

    jQuery.ajax({
        type: 'POST',
        data: dataParams,
        url: url,
        success: function (data, textStatus) {
            pwin.html(data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
        }
    });

    pwin.dialog({
        autoOpen: true,
        height: 'auto',
        resizable: false,
        position: 'top',
        width: dialogOpitons.iWidth,
        modal: true,
        bgiframe: false,
        show: {
            effect: "blind",
            duration: 1000
        },
        hide: {
            effect: "explode",
            duration: 1000
        }
        //title: dialogOpitons.title,
        //buttons: buttons
    });

}

Matrix.prototype.confirm = function (options) {
    if (options.title != '' && options.message != '') {
        bootbox.dialog({
            message: options.message,
            title: options.title,
            buttons: {
                danger: {
                    label: i18nQuery('button_ok'),
                    className: "btn-danger",
                    callback: function () {
                        if (options.url != undefined && options.url != '') {
                            window.location = options.url;
                        } else if ($.isFunction(options.callback)) {
                            options.callback();
                        }
                    }
                },
                main: {
                    label: i18nQuery('button_cancel'),
                    className: "btn-primary",
                    callback: function () {
                        bootbox.hideAll();
                    }
                }
            }
        });
    }
    return false;
}

Matrix.prototype.deleteConfirm = function (options) {
    if (options.message == undefined) {
        options.message = i18nQuery("msg_delete_confirm");
    }
    if (options.title == undefined) {
        options.title = i18nQuery("title_delete_confirm");
    }
    if (options.title != '' && options.message != '') {
        matrix.confirm(options);
    }
    return false;
}

Matrix.prototype.ajaxConfirmEntryDeletion = function (options) {

    var i18nmsg = i18nQuery('msg_delete_confirm');
    var url = "";
    var callback;
    if (options.url != undefined) {
        url = options.url;
    }

    if (options.call != undefined) {
        callback = options.call;
    }
    var ttl = i18nQuery('title_delete_confirm');
    bootbox.dialog({
        message: i18nmsg,
        title: ttl,
        buttons: {
            danger: {
                label: i18nQuery('button_ok'),
                className: "btn-danger",
                callback: function () {
                    jQuery.ajax({
                        type: 'POST',
                        url: url,
                        dataType: "text",
                        success: function (data, textStatus) {
                            if (data == "success") {
                                if (callback) {
                                    callback();
                                }
                                matrix.showMessage(i18nQuery('delete_success'));
                            } else {
                                matrix.showError("delete_failed");
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            matrix.showError("ERROR");
                        }
                    });
                }
            },
            main: {
                label: i18nQuery('button_cancel'),
                className: "btn-primary",
                callback: function () {
                    // Nothing;
                }
            }
        }
    });
    return false;
}

Matrix.prototype.ajaxConfirmDeleteAction = function (options) {
    var i18nmsg = i18nQuery('msg_delete_confirm');
    var url = "";
    var id = "";
    var callback;
    if (options.url != undefined) {
        url = options.url;
    }
    if (options.id != undefined) {
        id = options.id;
    }
    if (options.call != undefined) {
        callback = options.call;
    }
    var ttl = i18nQuery('title_delete_confirm');
    bootbox.dialog({
        message: i18nmsg,
        title: ttl,
        buttons: {
            danger: {
                label: i18nQuery('button_ok'),
                className: "btn-danger",
                callback: function () {
                    jQuery.ajax({
                        type: 'POST',
                        url: url,
                        data: {id: id},
                        dataType: "json",
                        success: function (data, textStatus) {
                            if (data != undefined && data.state == 'OK') {
                                if (callback) {
                                    callback();
                                }

                                if (options.grid) {
                                    $(options.grid).trigger("reloadGrid");
                                }

                                matrix.showMessage(data.message);
                            } else {
                                matrix.showError(data.message);
                            }
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            matrix.showError("ERROR");
                        }
                    });
                }
            },
            main: {
                label: i18nQuery('button_cancel'),
                className: "btn-primary",
                callback: function () {
                    // Nothing;
                }
            }
        }
    });
    return false;
}

Matrix.prototype.confirmEntryDeletion = function (entryId, options) {
    //var i18nmsg = $.i18n.prop('msg_delete_confirm');
    var i18nmsg = i18nQuery('msg_delete_confirm');
    //alert($.i18n.prop('button_ok'));

    if (options == undefined) {
        options = {
            //message: "Are you sure you want to delete the entry??",
            message: i18nmsg,
            formId: "deletionForm"
        }
    }

    if (options.message == undefined) {
        options.message = i18nmsg;
    }

    if (options.formId == undefined) {
        options.formId = "deletionForm"
    }

//    bootbox.confirm(options.message, function (result) {
//        if (result) {
//            if(entryId == undefined){
//
//            }
//            else{
//                $('#id').val(entryId);
//            }
//
//            var formSelector = '#' + options.formId;
//
//            var form = $(formSelector);
//
//            form.submit();
//        }
//    });

    //var ttl = $.i18n.prop('title_delete_confirm');
    var ttl = i18nQuery('title_delete_confirm');
    bootbox.dialog({
        message: options.message,
        title: ttl,
        buttons: {
            danger: {
                label: i18nQuery('button_ok'),
                className: "btn-danger",
                callback: function () {
                    if (entryId != undefined) {
                        $('#id').val(entryId);
                    }
                    var formSelector = '#' + options.formId;
                    var form = $(formSelector);
                    form.submit();
                }
            },
            main: {
                label: i18nQuery('button_cancel'),
                className: "btn-primary",
                callback: function () {
                    // Nothing;
                }
            }
        }
    });

    return false;
}

Date.prototype.format = function (mask) {
    var d = this;
    var zeroize = function (value, length) {
        if (!length) length = 2;
        value = String(value);

        for (var i = 0, zeros = ''; i < (length - value.length); i++) {
            zeros += '0';
        }
        return zeros + value;
    };

    return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0) {
        switch ($0) {
            case 'd':
                return d.getDate();
            case 'dd':
                return zeroize(d.getDate());
            case 'ddd':
                return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
            case 'dddd':
                return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
            case 'M':
                return d.getMonth() + 1;
            case 'MM':
                return zeroize(d.getMonth() + 1);
            case 'MMM':
                return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
            case 'MMMM':
                return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
            case 'yy':
                return String(d.getFullYear()).substr(2);
            case 'yyyy':
                return d.getFullYear();
            case 'h':
                return d.getHours() % 12 || 12;
            case 'hh':
                return zeroize(d.getHours() % 12 || 12);
            case 'H':
                return d.getHours();
            case 'HH':
                return zeroize(d.getHours());
            case 'm':
                return d.getMinutes();
            case 'mm':
                return zeroize(d.getMinutes());
            case 's':
                return d.getSeconds();
            case 'ss':
                return zeroize(d.getSeconds());
            case 'l':
                return zeroize(d.getMilliseconds(), 3);
            case 'L':
                var m = d.getMilliseconds();
                if (m > 99) m = Math.round(m / 10);
                return zeroize(m);
            case 'tt':
                return d.getHours() < 12 ? 'am' : 'pm';
            case 'TT':
                return d.getHours() < 12 ? 'AM' : 'PM';
            case 'Z':
                return d.toUTCString().match(/[A-Z]+$/);

            // Return quoted strings with the surrounding quotes removed

            default:
                return $0.substr(1, $0.length - 2);
        }
    });
};

// Ajax Search for Master Data
Matrix.prototype.ajaxSearch = function (options) {
    if (options == undefined) {
        alert("options undefined");
        return false;
    }
    if (options.sUrl == undefined) {
        alert("options.sUrl undefined");
        return false;
    }


    $(options.tableId).show();

    if (options.iIdColNum == undefined) {
        options.iIdColNum = 0;
    }

    options.aoColumnDefs.push(
        {
            "aTargets": [options.iActionColNum],
            "fnCreatedCell": function (nTd, sData, oData, iRow, iCol) {
                $(nTd).css('text-align', 'center');
                $(nTd).css('width', '100px');
                $(nTd).css('display', 'table-cell');
                $(nTd).css('vertical-align', 'middle');
            },
            "mData": options.iIdColNum,
            "mRender": function (data, type, full) {
                return matrix.mRenderForAction(data, type, full, {path: options.sUrl});
            }
        }
    );

    if (options.fnServerParams == undefined) {
        options.fnServerParams = function (aoData) {
            aoData.push(
                {"name": "q", "value": $("#query").val()},
                {"name": "showProperties", "value": options.showProperties});
        };
    }


    return $(options.tableId).dataTable({
        bProcessing: true,
        bServerSide: true,
        "bFilter": false,
        "bRetrieve": true,
        "bStateSave": true,
        "bInfo": true,
        "bDeferRender": true,
        //"bScrollAutoCss": true,
        //"bScrollCollapse": true,
        //"bJQueryUI": true,
        //"sScrollY": "100%",
        //"sScrollX": "100%",
        //"sScrollY": "200px",
        //"sScrollX": "100%",
        //"sScrollXInner": "110%",
        "bAutoWidth": false,
        sAjaxSource: options.sUrl + '/search',
        aLengthMenu: [
            [10, 50, 100, 500, -1],
            [10, 50, 100, 500, "All"]
        ],
        iDisplayLength: 10,
        "aaSorting": [
            [1, "desc"]
        ],
        aoColumns: options.aoColumns,
        aoColumnDefs: options.aoColumnDefs,
        "fnServerParams": options.fnServerParams,

//        "fnServerParams": function (aoData) {
//            aoData.push(
//                {"name": "q", "value": $("#query").val()},
//                {"name": "showProperties", "value": options.showProperties});
//
//            if(options.aoData != undefined){
//                aoData.push.apply(aoData,
//                    options.aoData
//                );
//            };
//        }

        "fnInitComplete": function () {
            this.css("visibility", "visible");
        },
        "oLanguage": {
            "sProcessing": i18nQuery("jqdt_sProcessing"),
            "sLengthMenu": i18nQuery("jqdt_sLengthMenu"),
            "sZeroRecords": i18nQuery("jqdt_sZeroRecords"),
            "sInfo": i18nQuery("jqdt_sInfo"),
            "sInfoEmpty": i18nQuery("jqdt_sInfoEmpty"),
            "sInfoFiltered": i18nQuery("jqdt_sInfoFiltered")
        }

        //,"fnInitComplete": function() {
        //    this.fnAdjustColumnSizing(true);
        //}
    });
    /* oLanguage
     {
     "sEmptyTable":     "No data available in table",
     "sInfo":           "Showing _START_ to _END_ of _TOTAL_ entries",
     "sInfoEmpty":      "Showing 0 to 0 of 0 entries",
     "sInfoFiltered":   "(filtered from _MAX_ total entries)",
     "sInfoPostFix":    "",
     "sInfoThousands":  ",",
     "sLengthMenu":     "Show _MENU_ entries",
     "sLoadingRecords": "Loading...",
     "sProcessing":     "Processing...",
     "sSearch":         "Search:",
     "sZeroRecords":    "No matching records found",
     "oPaginate": {
     "sFirst":    "First",
     "sLast":     "Last",
     "sNext":     "Next",
     "sPrevious": "Previous"
     },
     "oAria": {
     "sSortAscending":  ": activate to sort column ascending",
     "sSortDescending": ": activate to sort column descending"
     }
     }*/

}

Matrix.prototype.disableDivView = function (divId) {
    if (divId == undefined || divId == null) {
        divId = "theForm";
    }
    $('#' + divId).find('input[class^=input],input[class^=ace],input[type^=file],input[type=checkbox],textarea,select,button').not('button[id*=Cancel],button[id*=cancel]').prop("disabled", true);

}

Matrix.prototype.disableDivButton = function (divId) {
    if (divId == undefined || divId == null) {
        divId = "theForm";
    }
    $('#' + divId).find('button').not('button[id*=Cancel],button[id*=cancel]').prop("disabled", true);

}

Matrix.prototype.disableTabsAllInput = function (tabsId) {
    if (tabsId == undefined || tabsId == null) {
        tabsId = "theTabs";
    }
    $('#' + tabsId).find("ul").find("li").each(function (i, val) {
        var $li = $(this);
        var hrefname = $li.find("a").attr("href");
        var divId = hrefname.substring(1, hrefname.length);
        matrix.disableDivView(divId);
    });
}

Matrix.prototype.disableAllInput = function (formId) {
    if (formId == undefined || formId == null) {
        formId = "theForm";
    }

    var target = "form#" + formId + " :input"
    $(target).each(function () {
        var input = $(this);
        input.attr('disabled', 'disabled');
        $("form#" + formId).filter("select").select2("readonly", true);
        //input.attr('readonly', 'true');

        //input.addClass("disabled");
        //input.css("border","1px solid #f0ad4e");
    });
    matrix.removeRequiredAllInput(formId);
}

Matrix.prototype.removeRequiredAllInput = function (formId) {
    if (formId == undefined || formId == null) {
        formId = "theForm";
    }
    var target = "form#" + formId + " :input";
    $(target).each(function () {
        var input = $(this);
        var label = $('label[for="' + $(this).attr('id') + '"]');
        input.removeClass('required');
        label.removeClass('required');
        $("form#" + formId).filter("select").select2("required", false);
    });
}

Matrix.prototype.showMessage = function (text, title) {
    //close loading
    $('div.gritter-info.loading').remove();

    if (title === undefined || title == null) {
        title = i18nQuery("label_message");
    }

    $.gritter.add({
        title: "<i class='icon-info-sign bigger-120 blue'></i> " + title,
        text: text,
        class_name: "gritter-info gritter-light"
    });
}

Matrix.prototype.loading = function (text) {
    $.gritter.add({
        title: '<h5><i class="icon-spinner icon-spin orange bigger-125"></i> ' + text + '</h5>',
        text: '',
        stick: true,
        time: '600000',
        class_name: 'gritter-info gritter-light loading'
    });
}

Matrix.prototype.loadingWithTime = function (text, time) {
    $.gritter.add({
        title: '<h5><i class="icon-spinner icon-spin orange bigger-125"></i> ' + text + '</h5>',
        text: '',
        stick: true,
        time: time,
        class_name: 'gritter-info gritter-light loading'
    });
}

Matrix.prototype.hideMessage = function () {
    $.gritter.removeAll();
}

Matrix.prototype.showError = function (text, title) {
    //close loading
    $('div.gritter-info.loading').remove();

    if (title === undefined || title == null) {
        title = i18nQuery("label_error");
    }

    $.gritter.add({
        title: "<i class='icon-exclamation-sign bigger-120 red'></i> " + title,
        text: text,
        class_name: "gritter-error gritter-light"
    });
}

/*Get Json Object From Server By Ajax*/
Matrix.prototype.returnJsonData = function (params) {
    var data = $.ajax({
        url: params.url,
        data: (params.data == undefined ? {} : params.data),
        dataType: (params.dataType == undefined ? 'json' : params.dataType),
        async: (params.async == undefined ? true : params.async),
        error: function (xhr, status, err) {
            matrix.showError(err);
        }
    }).responseText;
    if (data && data != '') {
        return JSON.parse(data);
    } else {
        return {};
    }
};

Matrix.prototype.openDocumentFile = function (storePath, documentId) {
    $.ajax({
        url: _appContext + '/document/ajax/getDownloadFileName',
        data: {documentId: documentId, storePath: storePath},
        async: true,
        type: 'post',
        dataType: 'text',
        success: function (response) {
            if (response && response != '') {
                window.open(_appContext + response);
            } else {
                matrix.showError(jQuery.i18n.prop('file_not_exist'));
            }
        },
        error: function () {
            matrix.showError(jQuery.i18n.prop('get_failed', 'Data'));
        }
    });
}

/*Run js from ajax return html*/
Matrix.prototype.runAjaxJs = function (response) {
    var regDetectJs = /<script(.|\n)*?>(.|\n|\r\n)*?<\/script>/ig;
    var jsContained = response.match(regDetectJs);
    if (jsContained) {
        var regGetJS = /<script(.|\n)*?>((.|\n|\r\n)*)?<\/script>/im;

        var jsNums = jsContained.length;
        for (var i = 0; i < jsNums; i++) {
            var jsSection = jsContained[i].match(regGetJS);

            if (jsSection[2]) {
                if (window.execScript) {
                    // ie
                    window.execScript(jsSection[2]);
                } else {
                    // other
                    window.eval(jsSection[2]);
                }
            }
        }
    }
}

// Scroll
function isScrolledIntoView(elem) {
    var docViewTop = $(window).scrollTop();
    var docViewBottom = docViewTop + $(window).height();

    var elemTop = $(elem).offset().top;
    var elemBottom = elemTop + $(elem).height();
    //alert((elemTop <= docViewBottom) && (elemTop > docViewTop));

    return (elemTop <= docViewBottom) && (elemTop > docViewTop);
}

function scrollToTop(flag, elem, fixedClass, unfixedClass) {
    $(window).scroll(function () {
        $(flag).each(function () {
            if (isScrolledIntoView(this)) {
                if ($(elem).hasClass(fixedClass)) {
                    $(elem).removeClass(fixedClass);
                }
                if (!($(elem).hasClass(unfixedClass))) {
                    $(elem).addClass(unfixedClass);
                }
            }
            else {
                if ($(elem).hasClass(unfixedClass)) {
                    $(elem).removeClass(unfixedClass);
                }
                if (!($(elem).hasClass(fixedClass))) {
                    $(elem).addClass(fixedClass);
                }
            }
        });
    });
}

//Resize to fit page size
function resizeGrid(grid_table) {
    var parent_column = grid_table.closest('[class*="col-"]');
    $(window).on('resize.jqGrid', function () {
        grid_table.jqGrid('setGridWidth', parent_column.width());
    });
}

// Clone Table Row
function tableAddRow(e) {
    var template = $(this).closest(".table-inline-edit").find("table.table-template");
    var editTable = $(this).closest(".table-inline-edit").find("table.table-inline-edit");

    var lastId = $(editTable).find("tr:last").attr('id');
    if (lastId === undefined) {
        lastId = -1;
    }
    var rowNumber = parseInt(lastId) + 1;

    var currentRow = $(template).find("tr:last");
    $(currentRow).find(".select2").each(function () {
        $(this).select2("destroy");
    });

    $(template).find("tr:last").clone().attr({'id': rowNumber}).find("input").each(function () {
        $(this).attr({
            'id': function (_, id) {
                return id + '[' + rowNumber + ']'
            },
            'name': function (_, name) {
                return name + '[' + rowNumber + ']'
            },
            'value': ''
        });
        $(this).val('');

    }).end().appendTo($(editTable));

//    $(currentRow).find(".select2").each(function(){
//        $(this).select2();
//    });

    $(editTable).find("tr:last").find(".select2").each(function () {
        $(this).select2();
    });

    $(editTable).find("tr:last").find(".hasDatepicker").each(function () {
        datePick(this);
    });
}

function goToAnchor(anchorName) {
    //window.location.hash = anchorName;
    $('html, body').animate({
        scrollTop: $("a[name=" + anchorName + "]").offset().top
    }, 1000);
}


// Validate Client UI
// 04-01-2014
// Walter
$.fn.extend({
    validateUI: function (rules) {
        this.validate({
            errorElement: 'div',
            errorClass: 'help-block',
            focusInvalid: false,
            rules: rules,
            highlight: function (e) {
                $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
            },

            success: function (e) {
                $(e).closest('.form-group').removeClass('has-error').addClass('has-info');
                $(e).remove();
            },
            errorPlacement: function (error, element) {
                if (element.is(':checkbox') || element.is(':radio')) {
                    var controls = element.closest('div[class*="col-"]');
                    if (controls.find(':checkbox,:radio').length > 1) controls.append(error);
                    else error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
                }
                else if (element.is('.select2')) {
                    error.insertAfter(element.siblings('[class*="select2-container"]:eq(0)'));
                }
                else if (element.is('.chosen-select')) {
                    error.insertAfter(element.siblings('[class*="chosen-container"]:eq(0)'));
                }
                else error.insertAfter(element.parent());
            }
            /*
             ,
             invalidHandler: function (event, validator) { //display error alert on form submit
             $('.alert-danger', $('.login-form')).show();
             },
             ,
             submitHandler: function (form) {
             },
             invalidHandler: function (form) {
             }
             */
        })
    }
});

// ------------------------------------------------------------------------------------
// appInit
/**
 * Initialization
 * jQuery(function($) {
 * $(document).ready(function() {
 * $(function() {
 */
function appInit() {
    /**
     * Ace Setting Button
     */
    //$("#ace-settings-btn").hide();


    /**
     * Disable Enter Key Submit on form
     */
    $(document).keypress(function (evt) {
        var charCode = evt.charCode || evt.keyCode || evt.which;
        if (charCode == 13) {
            if (evt.target.id == "navSearchInput") {
                return true;
            }
            else if (evt.target.nodeName != "TEXTAREA") {
                return false;
            }
        }
    });

    /**
     * JQuery Dialog Title use HTML
     */
    if (typeof jQuery.ui != 'undefined' && typeof jQuery.ui.dialog != 'undefined') {
        $.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
            _title: function (title) {
                if (!this.options.title) {
                    title.html("&#160;");
                } else {
                    title.html(this.options.title);
                }
            }
        }));
    }
    ;

    /**
     * Sidebar Status
     */
    var menu = $('meta[name=menu]').attr("content");

    if (menu !== undefined) {
        var menuIds = menu.split(",");

        var count = menuIds.length;

        var ao = new Array();
        var ac = new Array();
        $.each(menuIds, function (index, item) {
            if (index < (count - 1)) {
                var ele = $("#sidebar #menu-item-" + this);
                if (!ele.hasClass("active")) {
                    ele.addClass("active");
                }
                if (!ele.hasClass("open")) {
                    ele.addClass("open");
                }
                ao.push("menu-item-" + this);
            }
            else {
                var ele = $("#sidebar #menu-item-" + this);
                if (!ele.hasClass("active")) {
                    ele.addClass("active");
                }
                ac.push("menu-item-" + this);
            }
        });

        $("#sidebar li[id^=menu-item-]").each(function () {
            var id = $(this).attr("id");
            if (jQuery.inArray(id, ao) == -1) {
                $(this).removeClass("open");
            }
            if (jQuery.inArray(id, ao) == -1 && jQuery.inArray(id, ac) == -1) {
                $(this).removeClass("active");
            }
        });
    }
    // Sidebar Status End

    //Custom Autocomplete (category selection)
    if (typeof jQuery.ui != 'undefined' && typeof jQuery.ui.autocomplete != 'undefined') {
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
    }

    // jqGrid Init
    // jqGridExtend();

    //ie8
    $(".hasDatepicker").each(function () {
        $(this).closest("span").addClass("input-medium");
        $(this).datepicker({format: "dd-mm-yyyy", autoclose: true}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    });
    $(".hasFutureDatepicker").each(function () {
        $(this).closest("span").addClass("input-medium");
        $(this).datepicker({
            format: "dd-mm-yyyy",
            autoclose: true,
            endDate: '+0d'
        }).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    });
    $(".timepicker").each(function () {
        $(this).closest("span").addClass("input-small");
        $(this).closest("div").addClass("input-small");
        $(this).timepicker({
            minuteStep: 1, showSeconds: false, showMeridian: false, defaultTime: "00:00"
        }).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    });
    $(".datetimepicker").each(function () {
        $(this).closest("span").addClass("input-medium");
        $(this).datetimepicker({format: "DD-MM-YYYY"}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        });
    });
    $('.input-daterange').each(function () {
        $(this).datepicker({format: "dd-mm-yyyy", autoclose: true}).next().on(ace.click_event, function () {
            $(this).prev().focus();
        })
    });
};
$.fn.readonlyDatepicker = function (makeReadonly) {
    $(this).each(function () {

        //find corresponding hidden field
        var name = $(this).attr('name');
        var $hidden = $('input[name="' + name + '"][type="hidden"]');

        //if it doesn't exist, create it
        if ($hidden.length === 0) {
            $hidden = $('<input type="hidden" name="' + name + '"/>');
            $hidden.insertAfter($(this));
        }

        if (makeReadonly) {
            $hidden.val($(this).val());
            $(this).unbind('change.readonly');
            $(this).attr('disabled', true);
        }
        else {
            $(this).bind('change.readonly', function () {
                $hidden.val($(this).val());
            });
            $(this).attr('disabled', false);
        }
    });
};
function aceSetting() {
    $("#ace-settings-btn").on(ace.click_event, function () {
        $(this).hide();
    });
    $("#ace-settings-container").removeClass("hide");

    $("#ace-settings-btn").show();
    $("#ace-settings-btn").toggleClass("open");
    $("#ace-settings-box").toggleClass("open")
}

if (typeof jQuery.ui != 'undefined' && typeof jQuery.ui.multiselect != 'undefined') {
    jQuery.extend(true, $.ui.multiselect, {
        locale: {
            addAll: '<i class="icon-double-angle-left icon-large purple"></i>',
            removeAll: '<i class="icon-double-angle-right icon-large red"></i>',
            itemsCount: i18nQuery('label_available_columns')
        }
    });
}

if (typeof jQuery.jgrid != 'undefined') {
    jQuery.extend(true, $.jgrid.col, {
        width: 500,
        modal: true,
        msel_opts: {dividerLocation: 0.5},
        dialog_opts: {
            minWidth: 550,
            show: 'blind',
            hide: 'explode'
        }
    });
}


/**
 * Ajax Sample
 *
 * Created by WTH on 13-12-2.
 */
function ajaxGetLabelValues(domainName, labelName, valueName) {
    var result = {}

    $.ajax({
        dataType: 'json',
        async: false,
        url: _appContext + "/dataChannel/list.json",
        data: {domain: domainName, label: labelName, value: valueName},
        success: function (data, status, xhr) {
            for (var i = 0; i < data.length; i++) {
                result[data[i].val] = data[i].lbl;
            }
        },
        error: function (xhr, status, err) {
            alert(err);
        },
        complete: function (xhr, status) {
        }
    });

    return result;
}


function ajaxGetConstant(constantName) {
    var result = {}

    $.ajax({
        dataType: 'json',
        async: false,
        url: _appContext + "/dataChannel/list.json",
        data: {constant: constantName},
        success: function (data, status, xhr) {
            for (var i = 0; i < data.length; i++) {
                result[data[i].val] = data[i].lbl;
            }
        },
        error: function (xhr, status, err) {
            alert(err);
        },
        complete: function (xhr, status) {
        }
    });

    return result;
}

/**
 *
 * Home Page Navigation Bar Button
 */
function ajaxNavigationTasks(containner, resTarget) {
    var url = _appContext + '/navigation/tasks';
    var data = {q: $('#q').val(), myp: 'tasks', currentUserId: _currentUserId};

    ajaxNavigation(containner, resTarget, url, data);
}

function ajaxNavigationNotifications(containner, resTarget) {
    var url = _appContext + '/navigation/notifications';
    var data = {'q': $('#q').val(), 'myp': 'notifications', currentUserId: _currentUserId};

    ajaxNavigation(containner, resTarget, url, data);
}

function ajaxNavigationMessages(containner, resTarget) {
    var url = _appContext + '/navigation/messages';
    var data = {'q': $('#q').val(), 'myp': 'messages', currentUserId: _currentUserId};

    ajaxNavigation(containner, resTarget, url, data);
}

function ajaxNavigation(containner, resTarget, url, data) {
    if ($(containner).hasClass('open')) {
        return;
    }

    $.ajax({
        type: "POST",
        sync: false,
        url: url,
        // dataType: 'json',
        data: data,
        success: function (res) {
            $(resTarget).empty();
            $(resTarget).append(res);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            //growl('error', xhr.responseText);
        }
    });
}

// Save client session
// i.e. - Session 1 Trigger - Controller Event Push - Session 2 Page Get Push Data - Session 2 Page save push data to Session 2
//  Session 2 Other page use the push data
function ajaxSaveClientDataToSession(sessionName, sessionValue) {
    // alert(sessionName + ":" + sessionValue) ;

    var url = _appContext + '/navigation/saveClientData';
    var data = {'sessionName': sessionName, 'sessionValue': sessionValue};

    $.ajax({
        type: "POST",
        //sync: false,
        url: url,
        // dataType: 'json',
        data: data,
        success: function (res) {
            //
        },
        error: function (xhr, ajaxOptions, thrownError) {
            //growl('error', xhr.responseText);
        }
    });
}

function escapesHtml(html) {
    var htmlEscapes = {
        '<': '&lt;',
        '>': '&gt;',
        '"': '&quot;',
        '/': '&#x2F;',
        '&': '&amp;',
        "'": '&#x27;',
        '#': '%23'
    };

    var htmlEscaper = /[&<>"\/#]/g;
    return (('' + html).replace(htmlEscaper, function (match) {
        return htmlEscapes[match];
    }));
}

//show audit log
function showAuditLog(params) {
    params['auditLogId'] = _currentUserId;
    if (params['entityName'] == undefined) {
        params['entityName'] = params['gridName'];
    }
    $.ajax({
        type: "POST",
        url: _appContext + "/sysadmin/audit/log-detail/ajax/index",
        data: params,
        success: function (response) {
            if (!$('#show_audit_log').length) {
                $("body").append("<div id='show_audit_log'></div>");
            }
            $("#show_audit_log").empty();
            $("#show_audit_log").html(response);
        }
    });

}

// select user dialog show
//The sample jsp: select_users_entry.jsp
function showLog(params, callback) {
    selectUsersCallback = callback;

    $.ajax({
        type: "POST",
        url: _appContext + "/task-comments/ajax/index",
        data: params,
        success: function (response) {
            if (!$('#show_selectUser_div').length) {
                $("body").append("<div id='show_selectUser_div'></div>");
            }

            $("#show_selectUser_div").empty();
            $("#show_selectUser_div").html(response);

        }
    });
}

$.fn.dropdownList = function (masterType, val, options) {
    var selectElement = $(this);
    var selectId = selectElement.prop('id');
    var url = _appContext + "/common/service/label-value/" + masterType;
    var filters = undefined;
    var options = $.extend(options, {returnMap: false});
    generateSelectElement(selectElement, selectId, filters, url, val, options, masterType);
};

function generateSelectElement(selectElement, selectId, filters, url, val, options, masterType) {
    var asyncFlag = true;
    var returnMap = true; //true -- return map, false -- return list
    if (typeof (options) !== 'undefined' && typeof (options.url) !== 'undefined') {
        url = options.url;
    }
    if (typeof (options) !== 'undefined' && typeof (options.aFlag) !== 'undefined') {
        asyncFlag = options.aFlag;
    }
    if (typeof (options) !== 'undefined' && typeof (options.returnMap) !== 'undefined') {
        returnMap = options.returnMap;
    }

    if (typeof (options) !== 'undefined' && typeof (options.selectedVal) !== 'undefined') {
        val = options.selectedVal;
    }
    if (typeof (options) !== 'undefined' && typeof (options.filters) !== 'undefined' && $.isArray(options.filters)) {
        filters = options.filters;
    }
    if (typeof (val) == 'undefined' || val == null) {
        val = "";
    }
    var values = val.split(',');
    $(selectElement).empty();
    if ($(selectElement).attr('multiple') != 'multiple') {
        selectElement.prepend("<option value=''>--- 请选择 ---</option>");
    }
    selectElement.ready(function () {
        $.ajax({
            //url: _appContext + "/common/service/label-value/" + e,
            url: url,
            type: "POST",
            data: {returnMap: returnMap ? "true" : "false"},
            dataType: "json",
            async: asyncFlag,
            success: function (data) {
                if (jQuery.isEmptyObject(data)) {
                    //$(selectElement).append("<option value='' >" + masterType + "Undefined </option>");
                    if (typeof (options) !== 'undefined' && options.callback != undefined && $.isFunction(options.callback)) {
                        var callback = options.callback;
                        callback($(selectElement));
                    }
                } else {
                    $.each(data, function (key, value) {
                        var lab, val;
                        if (returnMap) {
                            val = escapeHtml(key);
                            lab = value;
                        } else {
                            lab = value.label;
                            val = escapeHtml(value.value);
                        }
                        if (filters == undefined) {
                            $(selectElement).append("<option value=" + val + ">" + lab + "</option>");
                        }
                        ;
                        if (filters != undefined && $.inArray(key, filters) == -1) {
                            $(selectElement).append("<option value=" + val + ">" + lab + "</option>");
                        }
                    });
                    $(selectElement).val(val);
                    selectElement.select2('val', values);
                    if (typeof (options) !== 'undefined' && options.callback != undefined && $.isFunction(options.callback)) {
                        var callback = options.callback;
                        callback($(selectElement));
                    }
                    //initialize form data
                    doSaveFormDataByElement(selectElement);
                }
            },
            error: function () {
            }
        });
    });
}
$.fn.dropdownListNonMaster = function (url, val, options) {
    var selectElement = $(this);
    var selectId = selectElement.prop('id');
    var filters = undefined;
    generateSelectElement(selectElement, selectId, filters, url, val, options, undefined);
};

$.fn.dropdownListForUser = function (privilege, val, options) {
    var selectElement = $(this);
    var selectId = selectElement.prop('id');
    var filters = undefined;
    var url = _appContext + "/common/service/privilege-user-list?privilege=" + privilege;
    generateSelectElement(selectElement, selectId, filters, url, val, options, undefined);
}

$.fn.dropdownListForUserByRole = function (role, val, options) {
    var selectElement = $(this);
    var selectId = selectElement.prop('id');
    var filters = undefined;
    var url = _appContext + "/common/service/role-user-list?role=" + role;
    generateSelectElement(selectElement, selectId, filters, url, val, options, undefined);
}

function generateSelectElementByList(selectElement, selectId, filters, list, val, options, masterType) {
    var asyncFlag = true;
    var returnMap = true; //true -- return map, false -- return list
    if (typeof (options) !== 'undefined' && typeof (options.url) !== 'undefined') {
        url = options.url;
    }
    if (typeof (options) !== 'undefined' && typeof (options.aFlag) !== 'undefined') {
        asyncFlag = options.aFlag;
    }
    if (typeof (options) !== 'undefined' && typeof (options.returnMap) !== 'undefined') {
        returnMap = options.returnMap;
    }
    if (typeof (options) !== 'undefined' && typeof (options.selectedVal) !== 'undefined') {
        val = options.selectedVal;
    }
    if (typeof (options) !== 'undefined' && typeof (options.filters) !== 'undefined' && $.isArray(options.filters)) {
        filters = options.filters;
    }
    if (typeof (val) == 'undefined' || val == null) {
        val = "";
    }
    var values = val.split(',');
    $(selectElement).empty();
    if ($(selectElement).attr('multiple') != 'multiple') {
        selectElement.prepend("<option value=''>--- 请选择 ---</option>");
    }
    $.each(list, function (key, value) {
        var lab, val;
        if (returnMap) {
            val = escapeHtml(key);
            lab = value;
        } else {
            lab = value.label;
            val = escapeHtml(value.value);
        }
        if (filters == undefined) {
            $(selectElement).append("<option value=" + val + ">" + lab + "</option>");
        }
        ;
        if (filters != undefined && $.inArray(key, filters) == -1) {
            $(selectElement).append("<option value=" + val + ">" + lab + "</option>");
        }
    });
    $(selectElement).val(val);
    selectElement.select2('val', values);
    if (typeof (options) !== 'undefined' && options.callback != undefined && $.isFunction(options.callback)) {
        var callback = options.callback;
        callback($(selectElement));
    }
    //initialize form data
    doSaveFormDataByElement(selectElement);
}

$.fn.dropdownListByList = function (list, val, options) {
    var selectElement = $(this);
    var selectId = selectElement.prop('id');
    var filters = undefined;
    generateSelectElementByList(selectElement, selectId, filters, list, val, options, undefined);
};


$.fn.dropdownAccreditation = function (accreditType, masterType, val, natureVal, countryVal, options) {
    var selectElement = $(this);
    var selectId = selectElement.prop('id');
    var url = _appContext + "/student/accrdb/" + accreditType;

    if (typeof (options) !== 'undefined' && typeof (options.url) !== 'undefined') {
        url = options.url;
    }

    selectElement.prepend("<option value=''>--- Please Select ---</option>");
    selectElement.ready(function () {
        $.ajax({
            url: url,
            type: "POST",
            dataType: "json",
            async: false,
            data: {
                masterType: masterType,
                natureValue: natureVal,
                countryValue: countryVal
            },
            success: function (data) {
                if (jQuery.isEmptyObject(data)) {
                    $(selectElement).append("<option value='' >" + masterType + " Undefined </option>");
                } else {
                    $.each(data, function (key, value) {
                        var newkey = escapeHtml(key);

                        if (typeof (val) === 'undefined' || key !== val) {
                            $(selectElement).append("<option value=" + newkey + ">" + value + "</option>");
                        } else {
                            $(selectElement).append("<option value=" + newkey + " selected>" + value + "</option>");
                            $("#s2id_" + selectId).find("span.select2-chosen").html(value);
                        }
                    });
                    if (typeof (options) !== 'undefined' && options.callback != undefined && $.isFunction(options.callback)) {
                        var callback = options.callback;
                        callback($(selectElement));
                    }
                    //initialize form data
                    doSaveFormDataByElement(selectElement);
                }
            },
            error: function () {
            }
        });
    });
};

$.fn.checkboxList = function (name, type, values) {
    var checkBoxID = $(this);
    $.ajax({
        type: "POST",
        url: _appContext + "/common/service/label-value/" + type,
        dataType: 'json',
        success: function (data) {
            if (jQuery.isEmptyObject(data)) {
                $(checkBoxID).append('<span class="red">-  ' + type + '   Undefined-</span>');
            } else {
                for (var key in data) {
                    var newkey = escapeHtml(key);
                    var stringval = values.substring(1, values.length - 1);
                    var vars = stringval.split(",");
                    for (var i = 0; i < vars.length; i++) {
                        vars[i] = vars[i].replace(/(^\s*)|(\s*$)/g, "");
                    }
                    if ($.inArray(newkey, vars) > -1) {
                        $(checkBoxID).append('<label class="middle">' +
                            '<input name="' + name + '" type="checkbox" value="' + newkey +
                            '" checked="checked" class="ace"/>' +
                            '<span class="lbl">' + data[key] + '</span></label>');
                    } else {
                        $(checkBoxID).append('<label class="middle">' +
                            '<input name="' + name + '" type="checkbox" value="' + newkey +
                            '" class="ace"/>' +
                            '<span class="lbl">' + data[key] + '</span></label>');
                    }
                    $(checkBoxID).append("&nbsp;&nbsp;&nbsp;");
                }
                //initialize form data
                doSaveFormDataByElement(checkBoxID);
            }
        }
    });
}


$.fn.radioList = function (name, type, selectValue) {
    var radioID = $(this);
    $.ajax({
        type: "POST",
        url: _appContext + "/common/service/label-value/" + type,
        dataType: 'json',
        success: function (data) {
            if (jQuery.isEmptyObject(data)) {
                $(radioID).append('<span class="red">-' + type + ' Undefined-</span>');
            } else {
                for (var key in data) {
                    var newkey = escapeHtml(key);
                    if (newkey == selectValue) {
                        $(radioID).append('<label class="middle">' +
                            '<input name="' + name + '" type="radio" checked="checked" class="ace" value="' + newkey +
                            '"/>' +
                            '<span class="lbl">' + data[key] + '</span></label>');
                    } else {
                        $(radioID).append('<label class="middle">' +
                            '<input name="' + name + '" type="radio" class="ace" value="' + newkey + '"/>' +
                            '<span class="lbl">' + data[key] + '</span></label>');
                    }
                    $(radioID).append("&nbsp;&nbsp;&nbsp;");
                }
                //initialize form data
                doSaveFormDataByElement(radioID);
            }
        }
    });

}

$.fn.outSearch = function (gridTableName) {

    $("#search_" + gridTableName + "_top").css("display", "none");

    $self = $(this);
    $("#search_" + gridTableName + "_top").click();
    $("#searchmodfbox_" + gridTableName).removeClass("ui-jqdialog");
    var $dt = $('<div></div>');
    $("#searchmodfbox_" + gridTableName).appendTo($dt);
    $self.append($dt);

    var search_click = function () {
        $("#fbox_" + gridTableName + "_search").bind("click", function () {
            $("#search_" + gridTableName + "_top").click();
            $self.empty();
            $("#searchmodfbox_" + gridTableName).removeClass("ui-jqdialog");
            var $dt = $('<div></div>');
            $("#searchmodfbox_" + gridTableName).appendTo($dt);
            $self.append($dt);
        })
    }
    search_click();

    $("#search_" + gridTableName + "_top").bind("click", function () {
        search_click();
    })
}

//uploadphoto Display
$.fn.uploadPhotoDisplay = function (requestName, url) {
    $self = $(this);
    $.fn.editable.defaults.mode = 'inline';
    $.fn.editableform.Display = "<div class='editableform-loading'><i class='light-blue icon-2x icon-spinner icon-spin'></i></div>";
    $.fn.editableform.buttons = '<button type="submit" class="btn btn-info editable-submit"><i class="icon-ok icon-white"></i></button>' +
        '<button type="button" class="btn editable-cancel"><i class="icon-remove"></i></button>';

    try {//ie8 throws some harmless exception, so let's catch it
        if (/msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase())) Image.prototype.appendChild = function (el) {
        }

        var last_gritter
        $(this).editable({
            type: 'image',
            name: requestName,
            value: null,
            image: {
                btn_choose: 'Photo Upload',
                droppable: true,
                name: requestName,//put the field name here as well, will be used inside the custom plugin
                max_size: 110000,//~100Kb
                on_error: function (code) {//on_error function will be called when the selected file has a problem
                    if (last_gritter) $.gritter.remove(last_gritter);
                    if (code == 1) {
                        last_gritter = matrix.showMessage("Please choose a jpg|gif|png image!");
                    } else if (code == 2) {
                        last_gritter = matrix.showMessage("Image size should not exceed 100Kb!");
                    }
                    else {
                    }
                },
                on_success: function () {
                    $.gritter.removeAll();
                }
            },
            url: function (params) {
                var submit_url = url;
                var deferred;
                var value = $(this).next().find('input[type=hidden]:eq(0)').val();
                if (!value || value.length == 0) {
                    deferred = new $.Deferred
                    deferred.resolve();
                    return deferred.promise();
                }

                var $form = $(this).next().find('.editableform:eq(0)')
                var file_input = $form.find('input[type=file]:eq(0)');
                if (!("FormData" in window)) {
                    deferred = new $.Deferred

                    var iframe_id = 'temporary-iframe-' + (new Date()).getTime() + '-' + (parseInt(Math.random() * 1000));
                    $form.after('<iframe id="' + iframe_id + '" name="' + iframe_id + '" frameborder="0" width="0" height="0" src="about:blank" style="position:absolute;z-index:-1;"></iframe>');
                    $form.append('<input type="hidden" name="temporary-iframe-id" value="' + iframe_id + '" />');
                    $form.next().data('deferrer', deferred);//save the deferred object to the iframe
                    $form.attr({
                        'method': 'POST', 'enctype': 'multipart/form-data',
                        'target': iframe_id, 'action': submit_url
                    });

                    $form.get(0).submit();
                    //if we don't receive the response after 60 seconds, declare it as failed!
                    setTimeout(function () {
                        var iframe = document.getElementById(iframe_id);
                        if (iframe != null) {
                            iframe.src = "about:blank";
                            $(iframe).remove();

                            deferred.reject({'status': 'fail', 'message': 'Timeout!'});
                        }
                    }, 60000);
                }
                else {
                    var fd = null;
                    try {
                        fd = new FormData($form.get(0));
                    } catch (e) {
                        fd = new FormData();
                        $.each($form.serializeArray(), function (index, item) {
                            fd.append(item.name, item.value);
                        });
                        //and then add files because files are not included in serializeArray()'s result
                        $form.find('input[type=file]').each(function () {
                            if (this.files.length > 0) fd.append(this.getAttribute('name'), this.files[0]);
                        });
                    }

                    //if file has been drag&dropped , append it to FormData
                    if (file_input.data('ace_input_method') == 'drop') {
                        var files = file_input.data('ace_input_files');
                        if (files && files.length > 0) {
                            fd.append(file_input.attr('name'), files[0]);
                        }
                    }

                    deferred = $.ajax({
                        url: submit_url,
                        type: 'POST',
                        processData: false,
                        contentType: false,
                        dataType: 'json',
                        data: fd,
                        xhr: function () {
                            var req = $.ajaxSettings.xhr();
                            return req;
                        },
                        beforeSend: function () {
                        },
                        success: function (data, textStatus) {
                        }
                    })
                }

                deferred.done(function (res) {
                    if (res.status == 'success') {
                        $self.get(0).src = _appContext + "/servlet/photoServlet?id=" + res.id;
                    }
                    else {
                        alert(res.message);
                    }
                }).fail(function (res) {
                    alert("Failure");
                });

                return deferred.promise();
            },
            success: function (response, newValue) {
            }
        })
    } catch (e) {
    }
}

$.fn.radioInputCombination = function () {
    $this = $(this);
    $this.find('input[type="radio"]').each(function () {
        if (!$(this).prop("checked")) {
            $(this).nextAll("input").first().attr("disabled", "true");
        }
        $(this).click(function () {
            $this.find('input[type="text"]').not(':disabled').attr("disabled", "true");
            $(this).nextAll("input").first().removeAttr("disabled");
        });
    });
}

$.fn.fromToDatepicker = function (fromDate, toDate) {
    $("#" + fromDate + ",#" + toDate).datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 1,
        onSelect: function (selectedDate) {
            if (this.id == fromDate) {
                var dateMin = $('#' + fromDate).datepicker("getDate");
                var rMin = new Date(dateMin.getFullYear(), dateMin.getMonth(), dateMin.getDate() + 1);
                $('#' + toDate).datepicker("option", "minDate", rMin);
            }
        }
    });
}

/**
 * Author: Jack
 * Date: 2014-10-23
 * Description: use in ajax save form
 * param - valid = true,
 * param - url = form.action,
 * param - action = save, approve, reject
 * param - id = id from controller
 * param - callback = function
 * param - withRunning = function, not use
 */
$.fn.ajaxSave = function (options) {
    var $this = $(this);
    var idElement = $this.find('input[name="id"]');
    var defaults = {
        //id: $this.find('input[name="id"]').attr("id"),
        valid: true,
        url: $this.attr('action'),
        action: 'save',
        type: 'POST',
        needLoading: true,
        callback: undefined,
        withRunning: undefined, //not use in user call
        saveButtonId: undefined //not use in user call
    };
    var settings = $.extend({}, defaults, options);

    var check = false;
    if (settings.valid) {
        check = $this.valid();
    }

    var removeDisabledForSaveButton = function () {
        if (settings.saveButtonId != undefined) {
            $('#' + settings.saveButtonId).removeAttr('disabled');
        }
    }

    if ((settings.valid && check) || !settings.valid) {
        //before save , prompt user
        var _file_size_check = $(this).uploadFileSizeCheck();
        if (!settings.needLoading) {

        } else if (_file_size_check == -1) {
            removeDisabledForSaveButton();
            return false;
        } else if (_file_size_check == 1) {
            matrix.loading(jQuery.i18n.prop('loading_upload_file'));
        } else {
            matrix.loading(jQuery.i18n.prop('loading'));
        }

        $this.ajaxSubmit({
            type: settings.type,
            dataType: 'json',
            url: settings.url,
            data: settings.data,
            success: function (response) {
                if (response.state == 'OK') {
                    if (response.id != '' && idElement != undefined && idElement.length > 0) {
                        idElement.val(response.id);
                    }
                    if (settings.withRunning == undefined) {
                        matrix.showMessage(response.message);
                        removeDisabledForSaveButton();
                    }
                    if (settings.callback != undefined && $.isFunction(settings.callback)) {
                        settings.callback(response);
                    }
                    if (settings.withRunning != undefined && $.isFunction(settings.withRunning)) {
                        settings.withRunning();
                    }
                } else if (response.state == 'ERR') {
                    if (settings.callback != undefined && $.isFunction(settings.callback)) {
                        settings.callback(response);
                    }
                    matrix.showError(response.message);
                    removeDisabledForSaveButton();
                }
                //initialize form data
                doSaveAllFormData();
            }, error: function () {
                matrix.showError(jQuery.i18n.prop('action_failure', settings.id, settings.action));
                removeDisabledForSaveButton();
            }
        });
    }
    return false;
}

/**
 * Author: Jack
 * Date: 2014-10-23
 * Description: use in request jsp by ajax
 */
Matrix.prototype.ajaxJsp = function (options) {
    var defaults = {
        name: 'View',
        params: {},
        url: '',
        target: '',
        sync: undefined
    }
    var settings = $.extend({}, defaults, options);

    if (settings.url == '' || settings.target == '') {
        return false;
    }

    $.post(settings.url, settings.params, function (response) {
        if (response != undefined && response != '') {
            $('#' + settings.target + '').html(response);
            //initialize form data
            doSaveFormDataByDivId(settings.target);
        } else {
            matrix.showError(jQuery.i18n.prop('get_failed', settings.name));
            $('#' + settings.target + '').html('');
        }
        if (settings.sync != undefined && $.isFunction(settings.sync)) {
            settings.sync(response);
        }
    })
}

//jqueryui autocomplete, remote database
$.fn.autoCompleteByType = function (masterType) {
    var url = _appContext + "/ajax/autocomplete/" + masterType;
    $(this).autocomplete({
        source: function (request, response) {
            request.pageNo = 1;
            request.pageSize = 2000;
            request.q = request.term;
            $.getJSON(url, request, function (data, status, xhr) {
                response(data);
            });
        },
        minLength: 0,
        delay: 0
    });
}

$.fn.autoCompleteByUrl = function (url) {
    $(this).autocomplete({
        source: function (request, response) {
            request.pageNo = 1;
            request.pageSize = 2000;
            request.q = request.term;
            $.getJSON(url, request, function (data, status, xhr) {
                response(data);
            });
        },
        minLength: 0,
        delay: 0
    });
}

// auto complete list
$.fn.autoCompleteList = function (masterType, options) {
    var url = _appContext + "/common/service/auto-complete-list/" + masterType;
    $(this).autoCompleteListByUrl(url, options);
}

$.fn.autoCompleteListByUrl = function (url, options) {
    var defaults = {
        width: 320,
        max: 10,
        highlight: false,
        multiple: false,
        scroll: true,
        scrollHeight: 300,
        matchSubset: false,
        cacheLength: 0,
        matchCase: true
    }
    var settings = $.extend({}, defaults, options);
    var e = $(this);
    $(e).focus(function () {
        jQuery.ajaxSetup({cache: false});
        $(e).autocomplete({
            source: url
        }, settings);
    })
}
/**
 * Author: Jack
 * Date: 2014-10-23
 * Description: ajax popup window
 */
Matrix.prototype.ajaxPopupWindow = function (options) {
    var defaults = {
        name: 'Popup window',
        params: {},
        url: ''
    }
    var settings = $.extend({}, defaults, options);

    if (settings.url == '') {
        return false;
    }

    $.get(settings.url, settings.params, function (response) {
        if (response != undefined && response != '') {
            if (!$('#div_dialog_id').length) {
                $("body").append("<div id='div_dialog_id'></div>");
            }
            $("#div_dialog_id").empty();
            $("#div_dialog_id").html(response);
        } else {
            matrix.showError(jQuery.i18n.prop('get_failed', settings.name));
        }
    })
}

/**
 * Author: Jack
 * Date: 2015-01-28
 * Description: ajax popup window extend
 */
Matrix.prototype.ajaxPopupWindowExtend = function (options) {
    var defaults = {
        title: 'Popup window',
        params: {},
        url: '',
        position: 'center',
        width: '500',
        height: '450',
        divId: undefined,
        buttons: [
            {
                text: "Close",
                "class": "btn btn-xs btn-primary",
                click: function () {
                    $(this).dialog('destroy').remove();
                }
            }
        ]
    }
    var settings = $.extend({}, defaults, options);
    if (settings.url == '') {
        return false;
    }
    if (settings.divId == undefined || settings.divId == '') {
        settings.divId = 'div_dialog_id';
    }

    $.post(settings.url, settings.params, function (response) {
        if (response != undefined && response != '') {
            if (!$('#' + settings.divId).length) {
                $("body").append("<div id='" + settings.divId + "'></div>");
            }
            $("#" + settings.divId).empty();
            $("#" + settings.divId).html(response);
            $('#' + settings.divId).dialog({
                modal: true,
                title: "<div class='widget-header widget-header-small'><h4 class='smaller'>" + settings.title + "</h4></div>",
                title_html: true,
                position: settings.position,
                width: settings.width,
                height: settings.height,
                close: function (event, ui) {
                    $(this).dialog('destroy').remove();
                },
                buttons: settings.buttons
            });
        } else {
            matrix.showError(jQuery.i18n.prop('get_failed', settings.title));
        }
    })
}

/**
 * Author: Jack
 * Date: 2014-10-23
 * Description: ajax delete
 */
Matrix.prototype.ajaxDelete = function (options) {
    var defaults = {
        title: i18nQuery('title_delete_confirm'),
        message: i18nQuery('msg_delete_confirm'),
        params: {},
        url: '',
        callback: undefined
    }
    var settings = $.extend({}, defaults, options);
    if (settings.url == '') {
        return false;
    }
    bootbox.dialog({
        message: settings.message,
        title: settings.title,
        buttons: {
            danger: {
                label: i18nQuery('button_ok'),
                className: "btn-danger",
                callback: function () {
                    $.getJSON(options.url, options.params, function (response) {
                        if (response.state == 'OK') {
                            matrix.showMessage(response.message);
                            if (settings.callback != undefined && $.isFunction(settings.callback)) {
                                settings.callback();
                            }

                        } else if (response.state == 'ERR') {
                            matrix.showError(response.message);
                        }
                    });
                }
            },
            main: {
                label: i18nQuery('button_cancel'),
                className: "btn-primary",
                callback: function () {
                    bootbox.hideAll();
                }
            }
        }
    });
    return false;
}

/**
 * Author: Jack
 * Date: 2014-10-30
 * Description: ajax action, return object json
 */
Matrix.prototype.ajaxAction = function (options) {
    var defaults = {
        params: {},
        url: '',
        callback: undefined
    }
    var settings = $.extend({}, defaults, options);

    if (settings.url == '') {
        return false;
    }
    matrix.loading(jQuery.i18n.prop('loading'));
    $.getJSON(settings.url, settings.params, function (response) {
        if (response.state == 'OK') {
            matrix.showMessage(response.message);
            if (settings.callback != undefined && $.isFunction(settings.callback)) {
                settings.callback();
            }
        } else if (response.state == 'ERR') {
            matrix.showError(response.message);
        }
    });
}

/**
 * Author: Jack
 * Date: 2014-01-09
 * Description: return object json
 */
Matrix.prototype.ajaxObject = function (options) {
    var defaults = {
        params: {},
        url: '',
        async: true,
        callback: undefined
    }
    var settings = $.extend({}, defaults, options);

    if (settings.url == '' || settings.callback == undefined) {
        return false;
    }

    $.ajax({
        url: settings.url,
        data: settings.params,
        async: settings.async,
        type: 'post',
        dataType: 'json',
        success: function (response) {
            if (!jQuery.isEmptyObject(response)) {
                if (settings.callback != undefined && $.isFunction(settings.callback)) {
                    settings.callback(response);
                }
            } else {
                matrix.showError(jQuery.i18n.prop('get_failed', 'Data'));
            }
        },
        error: function () {
            matrix.showError(jQuery.i18n.prop('get_failed', 'Data'));
        }
    });
}

/**
 * Author: Jack
 * Date: 2015-01-21
 * Description: use in ajax, process session timeout
 */
$.ajaxSetup({
    complete: function (xhr, status) {
        var sessionStatus = xhr.getResponseHeader('sessionstatus');
        if (sessionStatus == 'timeout') {
            _is_submit = true;
            var top = getTopWindow();
            top.location.href = _appContext + '/login';
        }
    }
});

/**
 * Author: Jack
 * Date: 2015-01-21
 * Description: get top window
 */
function getTopWindow() {
    var p = window;
    while (p != p.parent) {
        p = p.parent;
    }
    return p;
}

// Update Analysis Label for Student/Member/PC Misc Tab - 2014-11-29
$.fn.retrieveAnalysisLabel = function (labelPrefix, type) {
    $.ajax({
        type: "POST",
        data: {returnMap: "true"},
        url: _appContext + "/common/service/label-value/" + type,
        dataType: 'json',
        success: function (data) {
            if (jQuery.isEmptyObject(data)) {
                //console.info(type + 'Undefined');
            } else {
                for (var key in data) {
                    var labelId = labelPrefix + key;
                    var uiLabel = data[key];
                    $("#" + labelId).html('<label>' + uiLabel + '</label>');
                }
            }
        }
    });
}
/**
 * Changed: Jack, Date: 2015-01-06
 * target: process hkid tag
 * @param el
 * @param filter
 * @returns {boolean}
 */
var isFormChanged = function (el, filter) {
    el = document.getElementById(el);
    filter = filter || function (el) {
            return false;
        };

    var els = el.elements, l = els.length, i = 0, j = 0, flag = 0, el, opts;

    for (; i < l; ++i, j = 0) {
        el = els[i];
        if (el.name == "") continue;
        switch (el.type) {
            case "text":
//                case "hidden":
            case "password":
            case "textarea":
                if (filter(el)) break;
                //process hkid tag
                if (el.id.lastIndexOf('first') != -1
                    || el.id.lastIndexOf('secord') != -1
                    || el.id.lastIndexOf('third') != -1
                    || el.name.lastIndexOf('age') != -1
                ) {
                    break;
                }
                if (el.defaultValue == el.value) {
                } else {
                    el.defaultValue = el.value;
                    return true;
                }
                break;
            case "radio":
            case "checkbox":
                if (filter(el)) break;
                if (el.defaultChecked != el.checked) {
                    el.defaultChecked = el.checked;
                    return true;
                }
                break;
            case "select-one":
                j = 1;
            case "select-multiple":
                if (filter(el)) break;
                opts = el.options;
                for (; j < opts.length; ++j) {
                    if (opts[j].defaultSelected != opts[j].selected) {
                        opts[j].defaultSelected = opts[j].selected;
                        flag = 1;
                    }
                }
                break;
        }
    }
    if (flag == 1) {
        return true;
    }
    return false;
};

/**
 *   use in master and details ajax save
 * @param tabsId : tabs header div id,  e.g = 'tabs_id'
 * @param headerOptions : ajaxSave(headerOptions), e.g = {callback: function(response)}
 * @param beforeSubmitDetails : before details submit, e.g = function(detailForm)
 * @param detailOptions : ajaxSave(headerOptions), e.g = {callback: function(response)}
 */
$.fn.headerSaveButtonClick = function (options) {
    var $headerForm = $(this);
    var defaults = {
        tabsId: "",
        beforeSubmitHeader: undefined,
        headerOptions: undefined,
        beforeSubmitDetails: undefined,
        detailOptions: undefined,
        finalCallBack: undefined,
        saveButtonId: undefined
    };
    var settings = $.extend({}, defaults, options);
    var $headerFormId = $headerForm.attr("id");
    var $detailForm = $("#" + settings.tabsId).find("div[aria-hidden = 'false']").find("form");
    var $detailFormId = $detailForm.attr('id');
    var $notSaveDetail = false;
    if ($detailForm.length > 0) {
        if ($detailForm.length > 0) {
            if (settings.detailOptions != undefined) {
                for (var detailFormId in settings.detailOptions) {
                    if (Object.prototype.hasOwnProperty.call(settings.detailOptions, detailFormId) && detailFormId == $detailFormId) {
                        var $detailOptions = $.extend({}, settings.detailOptions[detailFormId], {saveButtonId: settings.saveButtonId});
                        if ($detailOptions != undefined && $detailOptions.notSave != undefined && $detailOptions.notSave == true) {
                            $notSaveDetail = true;
                        }
                    }
                }
            }
        }
    }
    var $headerFormCheck = true;
    if ($headerForm.length > 0) {
        $headerFormCheck = $headerForm.valid();
    }
    var headerFormSubmit = function () {
        if ($headerForm.length > 0) {
            var $detailSubmit = {
                withRunning: function () {
                    if ($notSaveDetail == false) {
                        detailFormSubmit();
                    }
                }
            };
            if (settings.headerOptions != undefined) {
                if ($detailForm.length > 0) {
                    var $headerOptions = $.extend({}, settings.headerOptions, $detailSubmit);
                    $headerForm.ajaxSave($headerOptions);
                } else {
                    var $headerOptions = $.extend({}, settings.headerOptions, {saveButtonId: settings.saveButtonId});
                    $headerForm.ajaxSave($headerOptions);
                }
            } else {
                if ($detailForm.length > 0) {
                    var $headerOptions = $.extend({}, {saveButtonId: settings.saveButtonId}, $detailSubmit);
                    $headerForm.ajaxSave($headerOptions);
                } else {
                    var $headerOptions = $.extend({}, {saveButtonId: settings.saveButtonId}, {});
                    $headerForm.ajaxSave($headerOptions);
                }
            }
        } else {
            if (settings.saveButtonId != undefined) {
                $('#' + settings.saveButtonId).removeAttr('disabled');
            }
        }
    }
    var $detailFormCheck = true;
    if ($notSaveDetail == false) {
        if ($detailForm.length > 0) {
            $detailFormCheck = $detailForm.valid();
        }
        var beforeDetailFormSubmit = function ($detailForm) {
            if (settings.beforeSubmitDetails != undefined && $.isFunction(settings.beforeSubmitDetails)) {
                settings.beforeSubmitDetails($detailForm);
            }
        }
        var detailFormSubmit = function () {
            if ($detailForm.length > 0) {
                //run before detail submit
                beforeDetailFormSubmit($detailForm);
                var isRun = false;
                if (settings.detailOptions != undefined) {
                    for (var detailFormId in settings.detailOptions) {
                        if (Object.prototype.hasOwnProperty.call(settings.detailOptions, detailFormId) && detailFormId == $detailFormId) {
                            var $detailOptions = $.extend({}, settings.detailOptions[detailFormId], {saveButtonId: settings.saveButtonId});
                            $detailForm.ajaxSave($detailOptions);
                            isRun = true;
                            break;
                        }
                    }
                }
                if (!isRun) {
                    $detailForm.ajaxSave({saveButtonId: settings.saveButtonId});
                }
                if (settings.finalCallBack != undefined) {
                    setTimeout(function () {
                        settings.finalCallBack();
                    }, 1000);
                }
            } else {
                if (settings.saveButtonId != undefined) {
                    $('#' + settings.saveButtonId).removeAttr('disabled');
                }
                if (settings.finalCallBack != undefined) {
                    settings.finalCallBack();
                }
            }
        }
    }


    if ($headerFormCheck && $detailFormCheck) {
        if (settings.saveButtonId != undefined) {
            $('#' + settings.saveButtonId).prop('disabled', 'disabled');
        }
        if (settings.beforeSubmitHeader != undefined && $.isFunction(settings.beforeSubmitHeader)) {
            settings.beforeSubmitHeader();
        }
        if (isFormChanged($headerFormId)) {
            headerFormSubmit();
        } else {
            if (!$notSaveDetail) {
                detailFormSubmit();
            }
        }
    }
}

/**
 * Author: Jack
 * Date: 2015-3-4
 * Description: Judge the file size is beyond range, Result = -1 - Greater Max Size, 0 = size = 0, 1 - Greater Zero
 * @returns {boolean}
 */
$.fn.uploadFileSizeCheck = function () {
    var _file_size = $(this).uploadFileSize();
    if (_file_size == 0) {
        return 0;
    } else if (_file_size > 0 && _file_size < 32505856) {
        return 1;
    } else if (_file_size > 32505856) {
        matrix.showError(jQuery.i18n.prop('upload_file_size_error', _file_size, '32505856'));
        return -1;
    } else {
        return 0;
    }
}

/**
 * Author: Jack
 * Date: 2015-3-4
 * Description: check upload file size, params = All, Show, Hide, Result = -1 - Greater Max Size, 1 - Greater Zero, 0 - Size = 0,
 * @returns {boolean}
 */
var documentUploadFileSizeCheck = function (params) {
    if (params == undefined || params == '') {
        params = 'All';
    }

    var _file_size = 0;
    $(document).find('form').each(function (index, item) {
        if (params == 'All') {
            _file_size += $(item).uploadFileSize();
        } else if (params == 'Show') {
            if ($(item).closest('div')[0].style.display == 'block') {
                _file_size += $(item).uploadFileSize();
            }
        } else if (params == 'Hide') {
            if ($(item).closest('div')[0].style.display == 'none') {
                _file_size += $(item).uploadFileSize();
            }
        }
    });
    if (_file_size = 0) {
        return 0;
    } else if (_file_size > 0 && _file_size < 32505856) {
        return 1;
    } else if (_file_size > 32505856) {
        matrix.showError(jQuery.i18n.prop('upload_file_size_error', _file_size, '32505856'));
        return -1;
    } else {
        return 0;
    }
}

/**
 * Author: LY
 * Date: 2015-3-4
 * Description: Judge the file size
 * @returns {size}
 */
$.fn.uploadFileSize = function () {
    var $form = $(this);
    var _file_size = 0;
    $form.find('input[type="file"]').each(function (index, item) {
        if (navigator.userAgent.indexOf('MSIE') >= 0) {
            if (item.value) {
                var img = new Image();
                img.src = item.value;
                if (img.fileSize <= 0) {
                    _file_size += 1;
                } else {
                    _file_size += img.fileSize;
                }
            }
        } else {
            if (item.files[0]) {
                _file_size += item.files[0].size;
            }
        }
    });
    return _file_size;
}


/**
 * Author: Jack, Date: 2015-3-6
 * use when require post submit
 * @param url: url
 * @param paramName: param name
 * @param paramValue: param value
 */
function simulateFormSubmit(url, paramName, paramValue) {
    if (url == undefined || url == '' || paramName == undefined || paramName == ''
        || paramValue == undefined || paramValue == '') {
        return;
    }
    var form = $("<form>");
    form.attr('style', 'display:none');
    form.attr('target', '_self');
    form.attr('method', 'post');
    form.attr('action', url);

    var inputData = $('<input>');
    inputData.attr('type', 'hidden');
    inputData.attr('name', paramName);
    inputData.attr('value', paramValue);

    $('body').append(form);
    form.append(inputData);
    form.submit();
}

/**
 * disable browser back button
 */
function disableBrowserBackButton() {
    window.onload = function () {
        history.forward();
    }; //force user to redirect
    window.onunload = function () {
    }; // trick for some browsers like IE
}

/**
 * Save form data of element
 */
function doSaveFormDataByDivId(divId) {
    $('#' + divId).find('form').each(function (index, item) {
        var _form_id = $(item).attr("id");
        if (_form_id != undefined && _form_id != null && _form_id != '') {
            $('#' + _form_id).data('serialize', $('#' + _form_id).serialize());
        }
    });
}

/**
 * Save form data of element
 */
function doSaveFormDataByElement(e) {
    e.closest('form').each(function (index, item) {
        var _form_id = $(item).attr("id");
        if (_form_id != undefined && _form_id != null && _form_id != '') {
            $('#' + _form_id).data('serialize', $('#' + _form_id).serialize());
        }
    });
}

/**
 * Save form data of element
 */
function doSaveAllFormData() {
    $(document).find('form').each(function (index, item) {
        var _form_id = $(item).attr("id");
        if (_form_id != undefined && _form_id != null && _form_id != '') {
            $('#' + _form_id).data('serialize', $('#' + _form_id).serialize());
        }
    });
}

/**
 * Convert String to date
 * @param dateString = dd-MM-yyyy
 * @returns {Date}
 */
function convertStringToDate(dateString) {
    var date = dateString.substring(6, 10) + "-" + dateString.substring(3, 5) + "-" + dateString.substring(0, 2);
    var reg = new RegExp('-', 'g');
    return new Date(parseInt(Date.parse(date.replace(reg, '/')), 10));
}

/**
 * bind address address1 property
 * @param
 *    tabForm:have ajax tab, this is tab form id,or else is form id
 *    addressTypeId:use tags:addresswithRadio id
 *
 */
function supportPlaceholderBindAddress1(tabForm, addressTypeId) {
    if ($("#" + addressTypeId + "_address_div").length == 1 && $("#" + addressTypeId + "_flat").length == 1) {
        var flatVal = $("#" + tabForm).find("#" + addressTypeId + "_flat").val();
        var roomVal = $("#" + tabForm).find("#" + addressTypeId + "_room").val();
        var floorVal = $("#" + tabForm).find("#" + addressTypeId + "_floor").val();
        var blockVal = $("#" + tabForm).find("#" + addressTypeId + "_block").val();
        if (flatVal != '') {
            flatVal = "FLAT " + flatVal + ",";
        }
        if (roomVal != '' && flatVal != '') {
            roomVal = " ROOM " + roomVal + ",";
        } else if (roomVal != '' && flatVal == '') {
            roomVal = "ROOM " + roomVal + ",";
        }
        if (floorVal != '' && (roomVal != '' || flatVal != '')) {
            floorVal = " FLOOR " + floorVal + ",";
        } else if (floorVal != '' && roomVal == '') {
            floorVal = "FLOOR " + floorVal + ",";
        }
        if (blockVal != '' && (roomVal != '' || flatVal != '' || floorVal != '')) {
            blockVal = " BLOCK " + blockVal;
        } else if (blockVal != '' && (roomVal == '' && flatVal == '' && floorVal == '' )) {
            blockVal = "BLOCK " + blockVal;
        }
        var field1AddressVal = flatVal + roomVal + floorVal + blockVal;
        var subFileAddress1 = field1AddressVal.substring(field1AddressVal.length - 1, field1AddressVal.length);
        if (subFileAddress1 == ',') {
            field1AddressVal = field1AddressVal.substring(0, field1AddressVal.length - 1);
        }
        $("#" + addressTypeId + "_field1Address").val(field1AddressVal);
    }
}

/**
 * bind address address1 property
 * @param
 *    tabForm:have ajax tab, this is tab form id,or else is form id
 *    addressTypeId:use tags:addresswithRadio id
 *
 */
function notSupportPlaceholderBindAddress1(tabForm, addressTypeId) {
    if ($("#" + addressTypeId + "_address_div").length == 1 && $("#" + addressTypeId + "_country").length == 1) {
        $("#" + tabForm).find("#" + addressTypeId + "_address_div").find('[placeholder]').each(function () {
            var input = $(this);
            if (input.val() == input.attr('placeholder')) {
                input.val('');
                this.style.color = "#696969";
            }
        })
    }
    if ($("#" + addressTypeId + "_address_div").length == 1 && $("#" + addressTypeId + "_flat").length == 1) {
        var flatVal = $("#" + tabForm).find("#" + addressTypeId + "_flat").val();
        var roomVal = $("#" + tabForm).find("#" + addressTypeId + "_room").val();
        var floorVal = $("#" + tabForm).find("#" + addressTypeId + "_floor").val();
        var blockVal = $("#" + tabForm).find("#" + addressTypeId + "_block").val();
        if (flatVal != '') {
            flatVal = "FLAT " + flatVal + ",";
        }
        if (roomVal != '' && flatVal != '') {
            roomVal = " ROOM " + roomVal + ",";
        } else if (roomVal != '' && flatVal == '') {
            roomVal = "ROOM " + roomVal + ",";
        }
        if (floorVal != '' && (roomVal != '' || flatVal != '')) {
            floorVal = " FLOOR " + floorVal + ",";
        } else if (floorVal != '' && roomVal == '') {
            floorVal = "FLOOR " + floorVal + ",";
        }
        if (blockVal != '' && (roomVal != '' || flatVal != '' || floorVal != '')) {
            blockVal = " BLOCK " + blockVal;
        } else if (blockVal != '' && (roomVal == '' && flatVal == '' && floorVal == '' )) {
            blockVal = "BLOCK " + blockVal;
        }
        var field1AddressVal = flatVal + roomVal + floorVal + blockVal;
        var subFileAddress1 = field1AddressVal.substring(field1AddressVal.length - 1, field1AddressVal.length);
        if (subFileAddress1 == ',') {
            field1AddressVal = field1AddressVal.substring(0, field1AddressVal.length - 1);
        }
        $("#" + addressTypeId + "_field1Address").val(field1AddressVal);
    }
}


/**
 * Get different from two date
 * @param first = Date
 * @param last = Date
 * @returns {Array} = [dd,mm,yyyy]
 */
function getDiffYearMonthDay(first, last) {
    var monthDay = [31, -1, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var increment = 0, year, month, day;
    var ageDiffArr = [];

    if (first.getDate() > last.getDate()) {
        increment = monthDay[first.getMonth()];
    }

    var isLeapYear = ((first.getFullYear() % 4 == 0 && first.getFullYear() % 100 != 0) || first.getFullYear() % 400 == 0) ? true : false;

    if (increment == -1) {
        if (isLeapYear) {
            increment = 29;
        } else {
            increment = 28;
        }
    }

    //calculate day
    if (increment != 0) {
        day = last.getDate() + increment - first.getDate();
        increment = 1;
    } else {
        day = last.getDate() - first.getDate();
        increment = 0;
    }

    //calculate month
    if (first.getMonth() + increment > last.getMonth()) {
        month = last.getMonth() + 12 - (first.getMonth() + increment);
        increment = 1;
    } else {
        month = last.getMonth() - (first.getMonth() + increment);
        increment = 0;
    }

    //calculate year
    year = last.getFullYear() - (first.getFullYear() + increment);

    ageDiffArr[0] = day;
    ageDiffArr[1] = month;
    ageDiffArr[2] = year;

    return ageDiffArr;
}

function getDiffYearMonthDay2(first, last) {
    var increment = 0, year, month, day;
    var ageDiffArr = [];

    if (first.getDate() > last.getDate()) {
        increment = 30;
    }

    //calculate day
    if (increment != 0) {
        day = last.getDate() + increment - first.getDate() + 1;
        increment = 1;
    } else {
        day = last.getDate() - first.getDate() + 1;
        increment = 0;
    }

    //calculate month
    if (first.getMonth() + increment > last.getMonth()) {
        month = last.getMonth() + 12 - (first.getMonth() + increment);
        increment = 1;
    } else {
        month = last.getMonth() - (first.getMonth() + increment);
        increment = 0;
    }

    //calculate year
    year = last.getFullYear() - (first.getFullYear() + increment);

    if (day >= 30) {
        day = 0;
        month += 1;
    }
    if (month >= 12) {
        month = 0;
        year += 1;
    }

    ageDiffArr[0] = day;
    ageDiffArr[1] = month;
    ageDiffArr[2] = year;

    return ageDiffArr;
}

function getDiffYearMonthDayString(first, last) {
    var ageDiffArr = getDiffYearMonthDay2(first, last);
    return ageDiffArr[2] + ' Year(s) ' + ageDiffArr[1] + ' Month(s) ' + ageDiffArr[0] + ' Day(s)';
}

//use in calculate age(same as oracle) , bt:9880
function monthBetween(firstDate, lastDate) {
    var firstDay = firstDate.getDate(), firstMonth = firstDate.getMonth(), firstYear = firstDate.getFullYear(),
        lastDay = lastDate.getDate(), lastMonth = lastDate.getMonth(), lastYear = lastDate.getFullYear(),
        firstMaxDay = maxDayInMonth(firstMonth), lastMaxDay = maxDayInMonth(lastMonth);
    if ((firstDay == lastDay) || (firstDay == firstMaxDay && lastDay == lastMaxDay)) {
        return (lastYear - firstYear) * 12 + (lastMonth - firstMonth) + 1;
    } else {
        return (lastYear - firstYear) * 12 + (lastMonth - firstMonth) + 1 + (lastDay - firstDay) / 31;
    }
}
//find max day in every month
function maxDayInMonth(month) {
    var d = new Date();
    return new Date(d.getFullYear(), month, 0).getDate();
}

/**
 * Author:Jack
 * Date: 2015-04-09
 * Description: serialize form data to json object
 */
$.fn.serializeJSON = function () {
    var self = this,
        json = {},
        push_counters = {},
        patterns = {
            "validate": /^[a-zA-Z][a-zA-Z0-9_]*(?:\[(?:\d*|[a-zA-Z0-9_]+)\])*$/,
            "key": /[a-zA-Z0-9_]+|(?=\[\])/g,
            "push": /^$/,
            "fixed": /^\d+$/,
            "named": /^[a-zA-Z0-9_]+$/
        };

    this.build = function (base, key, value) {
        base[key] = value;
        return base;
    };

    this.push_counter = function (key) {
        if (push_counters[key] === undefined) {
            push_counters[key] = 0;
        }
        return push_counters[key]++;
    };

    $.each($(this).serializeArray(), function () {

        // skip invalid keys
        /*if(!patterns.validate.test(this.name)){
         return;
         }*/
        if (this.name == undefined || this.name == '') {
            return;
        }

        //convert . to []
        this.name = doConvertPointToBracket(this.name);

        var k,
            keys = this.name.match(patterns.key),
            merge = this.value,
            reverse_key = this.name;

        while ((k = keys.pop()) !== undefined) {

            // adjust reverse_key
            reverse_key = reverse_key.replace(new RegExp("\\[" + k + "\\]$"), '');

            // push
            if (k.match(patterns.push)) {
                merge = self.build([], self.push_counter(reverse_key), merge);
            }

            // fixed
            else if (k.match(patterns.fixed)) {
                merge = self.build([], k, merge);
            }

            // named
            else if (k.match(patterns.named)) {
                merge = self.build({}, k, merge);
            }
        }

        json = $.extend(true, json, merge);
    });

    return json;
};

/**
 * Author:Jack
 * Date: 2015-04-09
 * Description: replace from point to bracket
 */
function doConvertPointToBracket(str) {
    var _result = '';
    var _arrayStr = str.split("."), _length = _arrayStr.length;
    for (var i = 0; i < _length; i++) {
        if (_length == 1) {
            _result = str;
        } else {
            if (_result != '') {
                _result += '[' + _arrayStr[i] + ']';
            } else {
                _result = _arrayStr[i];
            }
        }
    }
    return _result;
}

/**
 * Author:Jack
 * Date: 2015-04-09
 * Description: validate all form
 */
function doCheckAllForm() {
    var _check = false;
    $(document).find('form').each(function (index, item) {
        var _name = $(item).attr('voclassname');
        if (_name != undefined && _name != '') {
            var _form_check = true;
            var _tabs = $(item).closest('div.ui-tabs');
            var _panel = $(item).closest('div.ui-tabs-panel');
            var _panel_id = _panel.attr('id');
            var _panel_hidden = _panel.attr('aria-hidden');
            if (_panel_hidden == 'true') {
                $(item).validate().settings.ignore = '';
                _form_check = $(item).valid();
                $(item).validate().settings.ignore = ':hidden';
            } else {
                _form_check = $(item).valid();
            }
            if (_form_check == false) {
                if (_tabs != undefined && _panel_id != undefined) {
                    _tabs.selectTabByID(_panel_id);
                }
                //matrix.showError('Validate Failure: \n' + getValidateErrorString($(item)));
                _check = false;
                return false;
            } else {
                _check = true;
                return true;
            }
        }
    });
    return _check;
}

/**
 * Author:Jack
 * Date: 2015-04-09
 * Description: remove all from form document
 */
function doRemoveFormByFormId(formId) {
    $(document).find('form[id="' + formId + '"]').each(function () {
        $(this).remove();
    });
}

/**
 * Author:Jack
 * Date: 2015-04-09
 * Description: when create, submit all form data to server
 */
function doSubmitAllFormWhenCreate(options) {
    var defaults = {
        url: undefined,
        isAjax: true,
        saveButtonId: undefined,
        callback: undefined
    }
    var settings = $.extend({}, defaults, options);
    //validate
    var _check = doCheckAllForm();
    if (_check == false) {
        //matrix.showError('Validate failed.');
        return false;
    }
    //return if url is undefined
    if (settings.url == undefined) {
        return false;
    }
    var removeDisabledForSaveButton = function () {
        if (settings.saveButtonId != undefined) {
            $('#' + settings.saveButtonId).removeAttr('disabled');
        }
    }

    //disable save button
    if (settings.saveButtonId != undefined) {
        $('#' + settings.saveButtonId).prop('disabled', 'disabled');
    }

    //remove original form
    doRemoveFormByFormId('frm_tmp_id');

    var _form_length = $(document).find('form').length;
    var _have_file = $(document).find('form').find('input[type="file"]').length;
    if (_form_length > 0) {
        //define form
        var _form = $("<form>");
        _form.attr('id', 'frm_tmp_id');
        _form.attr('style', 'display:none');
        _form.attr('target', '_self');
        _form.attr('method', 'post');
        _form.attr('action', settings.url);
        if (_have_file > 0) {
            _form.attr('enctype', 'multipart/form-data');
        }
        $(document).find('form').each(function (index, item) {
            var _name = $(item).attr('voclassname');
            if (_name != undefined && _name != '') {
                var _data = JSON.stringify($(item).serializeJSON());
                //define input
                var _input = $('<input>');
                _input.attr('type', 'hidden');
                _input.attr('name', _name);
                _input.attr('value', _data);
                _form.append(_input);
                //process file
                $(item).find('input[type="file"]').each(function (i, ele) {
                    _form.append(ele);
                });
            }
        });
        //add form to body
        $('body').append(_form);
        if (settings.isAjax) {
            _form.ajaxSave({
                valid: false,
                saveButtonId: settings.saveButtonId,
                callback: settings.callback
            });
        } else {
            //show loading prompt
            var _file_size_check = documentUploadFileSizeCheck();
            if (_file_size_check == -1) {
                removeDisabledForSaveButton();
                return false;
            } else if (_file_size_check == 1) {
                matrix.loading(jQuery.i18n.prop('loading_upload_file'));
            } else {
                matrix.loading(jQuery.i18n.prop('loading'));
            }
            _form.submit();
            removeDisabledForSaveButton();
        }
    }
}

//check jqgrid table is in editable
var doCheckJqTableIsInEditable = function (gridSelector) {
    var _result = false;
    $(gridSelector).find('tr').each(function () {
        var _editable = $(this).attr('editable');
        if (_editable == '1') {
            _result = true;
            return false;
        }
    });
    return _result;
}

$.fn.selectToInput = function (inputName, inputVal, currentVal) {
    var $this = $(this);
    var $input = $("<input type='text' class='input-xxlarge hide' name='" + inputName + "'>");
    $input.attr("name", inputName);
    $input.val(inputVal);
    $this.after($input);
    var content = function (changeVal) {
        if (changeVal == "OTHER") {
            $input.removeClass("hide");
        } else {
            $input.val("");
            $input.addClass("hide");
        }
    }

    $this.bind('change', function () {
        content($this.val())
    })

    if (currentVal != "") {
        content(currentVal);
    }
}

// BT 12733 - Select2 not work in Modal - ui-dialog
if (typeof $.ui != "undefined") {
    $.ui.dialog.prototype._allowInteraction = function (e) {
        return !!$(e.target).closest('.ui-dialog, .ui-datepicker, .select2-drop').length;
    };
}

var getMasterAdditionValue = function (type, value, seq) {
    var _seq = 'first';
    if (seq == 1) {
        _seq = 'first'
    } else if (seq == 2) {
        _seq = "second";
    } else if (seq == 3) {
        _seq = "third";
    }

    var data = $.ajax({
        url: _appContext + '/common/service/additional-value/' + _seq + '/' + type + '/' + value,
        data: {},
        dataType: 'text',
        async: false,
        error: function (xhr, status, err) {
            matrix.showError(err);
        }
    }).responseText;

    return data;
}

/**
 * Author:LY
 * Date: 2015-10-14
 * Description: valid status
 */
function businessObjectValidation(option) {
    var defaults = {
        url: undefined,
        callback: undefined
    };
    var settings = $.extend({}, defaults, option);
    $.ajax({
        url: settings.url,
        type: "post",
        dataType: "json",
        error: function (response) {
            _is_submit = false;
            window.location.reload();
        },
        success: function (response) {
            if (response.result == 'false') {
                _is_submit = true;
                window.location.reload();
            } else if (response.result == 'true') {
                if (settings.callback != undefined && $.isFunction(settings.callback)) {
                    settings.callback(response);
                }
            }
        }
    });
}

/**
 * self bootbox
 * */
Matrix.prototype.ajaxDialog = function (options) {
    var defaults = {
        valid: false,
        url: undefined,
        type: 'POST',
        callback: undefined,
        withRunning: undefined
    };
    var settings = $.extend({}, defaults, options);

    $.ajax({
        type: settings.type,
        dataType: settings.dataType,
        url: settings.url,
        data: settings.data,
        success: function (data) {
            var _title = undefined;
            if (data != undefined && data != null && data != '') {
                if (settings.title != undefined && settings.title != null && settings.title != '') {
                    _title = '<div class=""><i class="icon-leaf pink">&nbsp;</i><span class="green"> ' + settings.title + ' </span></div>';
                }
                var check = true;
                var box = bootbox.dialog({
                    title: _title,
                    message: data,
                    buttons: {
                        submit: {
                            label: i18nQuery('button_ok'),
                            className: "btn-info",
                            callback: function () {
                                $(data).closest('form').each(function (index, item) {
                                    var _form_id = $(item).attr("id");
                                    var $_dom = $('#' + _form_id);

                                    if (settings.valid) {
                                        check = $_dom.valid();
                                    }

                                    if (_form_id != undefined && _form_id != null && _form_id != '' && ((settings.valid && check) || !settings.valid)) {
                                        matrix.loading(jQuery.i18n.prop('loading'));
                                        $_dom.ajaxSubmit({
                                            dataType: 'json',
                                            url: $_dom.attr('action'),
                                            success: function (response) {
                                                //close loading
                                                $('div.gritter-info.loading').remove();
                                                if (response.state == 'OK') {
                                                    if (settings.withRunning == undefined) {
                                                        matrix.showMessage(response.message);
                                                    }
                                                    if (settings.withRunning != undefined && $.isFunction(settings.withRunning)) {
                                                        settings.withRunning(response);
                                                    }
                                                    if (settings.callback != undefined && $.isFunction(settings.callback)) {
                                                        settings.callback(response);
                                                    }
                                                } else if (response.state == 'ERR') {
                                                    matrix.showError(response.message);
                                                }
                                            }, error: function () {
                                                $('div.gritter-info.loading').remove();
                                                matrix.showError(jQuery.i18n.prop('action_failure', settings.id, settings.action));
                                            }
                                        });
                                    }
                                });
                                if (!check) return false;
                            }
                        },
                        main: {
                            label: i18nQuery('button_cancel'),
                            className: "btn-primary",
                            callback: function () {
                                bootbox.hideAll();
                            }
                        }
                    }
                });
            } else {
                matrix.loading(jQuery.i18n.prop('loading_upload_file'));
            }
        }, error: function () {
            matrix.showError(jQuery.i18n.prop('action_failure', settings.id, settings.action));
        }
    });
    return false;
};

/**
 * Render JqGrid table with popup dialog by specific index
 * @author Chen Yan Yi
 * @param options
 */
Matrix.prototype.reloadJqGridWithPopover = function (options) {

    // initial param
    var defaults = {
        grid: undefined,
        processIndex: "id",
        dimetric: undefined,
        placement: 'top',
        trigger: 'click',
        format: undefined
    };
    var settings = $.extend({}, defaults, options);

    if (settings.grid != undefined && settings.grid != null && settings.grid != '') {
        var myGrid = $('#' + settings.grid);

        myGrid.setGridParam({
            loadComplete: function () {
                myGrid.children("tbody").children("tr.jqgrow")
                    .addClass('green');

                myGrid.children("tbody").children("tr.jqgrow td")
                    .removeAttr('data-toggle')
                    .removeAttr('title')
                    .removeAttr('data-placement')
                    .removeAttr('data-content')
                    .removeAttr('data-container');

                if (!$.isEmptyObject(settings.dimetric)) {
                    $.each(settings.dimetric, function (k, v) {
                        myGrid.children("tbody")
                            .children("tr[id=" + k + "].jqgrow")
                            .removeClass('green')
                            .addClass('red');
                        var mark = myGrid.children("tbody")
                            .children("tr[id=" + k + "].jqgrow")
                            .children("td[aria-describedby*=" + settings.processIndex + "]");

                        mark.html(($.isFunction(settings.format) ? settings.format(k, v) : settings.format) || ("<a href='#'>" + mark.html() + "</a>"));

                        mark.attr('data-toggle', 'popover')
                            .attr('data-placement', settings.placement)
                            .attr('title', k)
                            .attr('data-trigger', settings.trigger)
                            .attr('data-container', 'body')
                            .attr('data-content', v)
                            .popover();
                    });
                }
            }
        }).trigger("reloadGrid");

    }
}

/**
 * Generic Lookup
 */
var lookupCallback;
var lookupCustomNavButton;

function showLookupWindow(params, callback) {
    lookupCallback = callback;
    if (params.lookupButton != undefined && $.isFunction(params.lookupButton)) {
        lookupCustomNavButton = params.lookupButton;
    }
    else {
        lookupCustomNavButton = null;
    }

    $.ajax({
        type: "POST",
        url: _appContext + "/common/lookup/ajax/index",
        data: params,
        success: function (response) {
            if (!$('#lookup_div').length) {
                $("body").append("<div id='lookup_div'></div>");
            }
            $("#lookup_div").empty();
            $("#lookup_div").html(response);
        }
    });
}
/**
 * Generic Popup
 */
var popupCallback;

function showPopupContentWindow(params, callback) {
    popupCallback = callback;

    $.ajax({
        type: "POST",
        url: _appContext + "/common/popup/ajax/index",
        data: params,
        success: function (response) {
            if (!$('#popup_div').length) {
                $("body").append("<div id='popup_div'></div>");
            }
            $("#popup_div").empty();
            $("#popup_div").html(response);
        }
    });
}

/**
 *  Generic Download
 * @param filePathAndName
 */
function doDownload(filePathAndName) {
    $.ajax({
        type: "POST",
        url: _appContext + "/common/download/ajax/validate",
        data: {filePathAndName: filePathAndName},
        success: function (response) {
            if (response == "true") {
                doDownload1(filePathAndName, '');
            }
            else {
                matrix.showError("Cannot find the file [" + filePathAndName + "].");
            }
        }
    });
}

/**
 *  Generic Download
 * @param fileNameAndType
 */
function downloadByType(fileNameAndType) {
    var fileName = fileNameAndType.substring(0, fileNameAndType.lastIndexOf(','));
    var documentType = fileNameAndType.substr(fileNameAndType.lastIndexOf(',') + 1);
    $.ajax({
        type: "POST",
        url: _appContext + "/common/download/ajax/validate",
        data: {filePathAndName: fileName, documentType: documentType},
        success: function (response) {
            if (response == "true") {
                doDownload1(fileName, documentType);
            }
            else {
                matrix.showError("Cannot find the file [" + fileName + "].");
            }
        }
    });
}

function doDownload1(filePathAndName, documentType) {
    //var url = '${ctx}/common/download/ajax/index';
    var url = _appContext + '/common/download';

    var form = $("<form>");
    form.attr('style', 'display:none');
    form.attr('target', '');
    form.attr('method', 'post');
    form.attr('action', url);

    var inputData1 = $('<input>');
    inputData1.attr('type', 'hidden');
    inputData1.attr('name', 'documentType');
    inputData1.attr('value', documentType);
    var inputData2 = $('<input>');
    inputData2.attr('type', 'hidden');
    inputData2.attr('name', 'filePathAndName');
    inputData2.attr('value', filePathAndName);

    $('body').append(form);
    form.append(inputData1);
    form.append(inputData2);
    form.submit();
}

function doDownload2(filePathAndName) {
    var url = _appContext + "/common/download/ajax/index?filePathAndName=" + filePathAndName;
    // window.location.href = _appContext + "/common/download?filePathAndName=" + filePathAndName;
    // window.open(url, '_download', 'height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
    window.open(url, '_download', 'toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
}

// Generic Calculation
function doCalculation(options) {
    var params = {
        boType: options.boType,
        boId: options.boId,
        targetDomain: options.targetDomain,
        criteria: options.criteria
    };

    $.ajax({
        type: "POST",
        url: _appContext + "/common/calculation",
        data: params,
        //dataType: "json",
        //contentType: "application/json",
        //async:false,
        success: function (data) {
            var calculationResult = jQuery.parseJSON(data);
            options.callback(calculationResult);
        }
    });
}

function getCalculationResult(itemName, calculationResult) {
    var result = "";
    $.each(calculationResult, function (index, value) {
        var _itemName = value.itemName.toUpperCase();
        var _itemValue = value.itemValue;

        if (itemName.toUpperCase() === _itemName) {
            result = _itemValue;
            return false; // break out
        }
    });
    return result;
}

// Batch Import Data
function batchImportData(params, callback) {
    matrix.loading('The batch import data is in progress ...');
    $.ajax({
        type: "POST",
        url: _appContext + "/common/dts/ajax/imp",
        data: params,
        success: function (response) {
            var obj = JSON.parse(response);
            var result = obj.result;
            if (result != undefined && result == 'OK') {
                var batchJobId = obj.batchJobId;
                params.batchJobId = batchJobId;
                if (params.standaloneProcess !== undefined && params.standaloneProcess == true) {
                    batchProcessData(params, callback);
                }
                else {
                    showPopupContentWindow({
                            popupContentUrl: "/common/dts/ajax/result", // Required
                            popupTitle: "Result", // Required
                            popupWidth: "800", // Option
                            popupHeight: "600", // Option
                            popupContentParameter: JSON.stringify(popupContentParameter)
                        },
                        callback);
                    matrix.hideMessage();
                }
            }
            else {
                //matrix.hideMessage();
                matrix.showError(obj.message);
            }
        },
        error: function (textStatus, errorThrown) {
            //matrix.hideMessage();
            matrix.showError("Failed to batch import data.");
        }
    });
}

// Batch Import Data
function batchProcessData(params, callback) {
    matrix.loading('The import data is processing ...');
    $.ajax({
        type: "POST",
        url: _appContext + "/common/dts/ajax/process",
        data: params,
        success: function (response) {
            var obj = JSON.parse(response);
            var result = obj.result;
            if (result != undefined && result == 'OK') {
                var batchJobId = obj.batchJobId;
                var popupContentParameter = {batchJobId: batchJobId};
                showPopupContentWindow({
                        popupContentUrl: "/common/dts/ajax/result", // Required
                        popupTitle: "Result", // Required
                        popupWidth: "800", // Option
                        popupHeight: "600", // Option
                        popupContentParameter: JSON.stringify(popupContentParameter)
                    },
                    callback);
                matrix.hideMessage();
            }
            else {
                //matrix.hideMessage();
                matrix.showError(obj.message);
            }
        },
        error: function (textStatus, errorThrown) {
            //matrix.hideMessage();
            matrix.showError("Failed to batch import data.");
        }
    });
}


/**
 * Generic Lookup
 */

function generateReceipt(params, callback) {
    matrix.loading('Receipt is generating ...');

    $.ajax({
        type: "POST",
        url: _appContext + "/common/receipt/generate",
        data: params,
        success: function (response) {
            //matrix.hideMessage();
            var obj = jQuery.parseJSON(response);
            callback(obj);
        }
    });
}

Matrix.prototype.downloadByDocTypeAndName = function (docType, docName) {
    $.ajax({
        url: _appContext + '/nip/common/download/check_document_exits',
        data: {docType: docType, docName: docName},
        async: true,
        type: 'post',
        dataType: 'text',
        success: function (response) {
            if (response == 'true') {
                _is_submit = true;
                var form = $("<form>");
                form.attr('style', 'display:none');
                form.attr('target', '');
                form.attr('method', 'post');
                form.attr('action', _appContext + '/nip/common/download');

                var inputData1 = $('<input>');
                inputData1.attr('type', 'hidden');
                inputData1.attr('name', 'docType');
                inputData1.attr('value', docType);
                var inputData2 = $('<input>');
                inputData2.attr('type', 'hidden');
                inputData2.attr('name', 'docName');
                inputData2.attr('value', docName);

                $('body').append(form);
                form.append(inputData1);
                form.append(inputData2);
                form.submit();
            } else {
                matrix.showError(jQuery.i18n.prop('file_not_exist'));
            }
        },
        error: function () {
            matrix.showError(jQuery.i18n.prop('get_failed', 'Data'));
        }
    });
}

function getCmsContent(code, divId) {
    $.ajax({
        type: "POST",
        data: {code: code},
        url: _appContext + "/dms/cms/get-content/",
        dataType: "text",
        success: function (res) {
            $("#" + divId).html(res);
        },
        error: function (xhr, ajaxOptions, thrownError) {
            matrix.showError('Error');
        }
    });
}

