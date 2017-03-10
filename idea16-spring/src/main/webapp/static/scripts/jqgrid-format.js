/*
 * ------------------------------------------------------------------------
 *
 * Common Template
 *
 * ------------------------------------------------------------------------
 */
function onlineOfflineFormatter(cellvalue, options, rowObject) {
    if (cellvalue == '1') {
        return "<span class='label label-sm label-success'>Online</span>";
    }
    else {
        return "<span class='label label-sm label-warning'>Offline</span>";
    }
}
function onlineOfflineUnformatter(cellvalue, options) {
    if (cellvalue.indexOf("Online") >= 0) {
        return "1";
    }
    else {
        return "0"
    }
}

function hkCurrencyFormatter(cellvalue, options, rowObject) {
    if (cellvalue != '' && cellvalue != '0' && cellvalue != '0.00' && $.isNumeric(cellvalue)) {
        return 'HK$' + cellvalue.toFixed(2);
    } else {
        return cellvalue;
    }
}
function hkCurrencyUnformatter(cellvalue, options) {
    if (cellvalue.indexOf("HK$") >= 0) {
        return cellvalue.substr(3);
    }
    else {
        return "0";
    }
}

function statusFormatter(cellvalue, options, rowObject) {
    if (cellvalue == 'Y' || cellvalue == '1') {
        return "<span class='label label-sm label-success'>活动</span>";
    }
    else {
        return "<span class='label label-sm label-warning'>禁用</span>";
    }
}
function unformatStatus(cellvalue, options) {
    if (cellvalue.indexOf("Enabled") >= 0) {
        return "1";
    }
    else {
        return "0"
    }
}

function taskStatusFormatter(cellvalue, options, rowObject) {
    if (cellvalue == 'PENDING') {
        return "<span class='label label-sm label-info'>PENDING</span>";
    }
    else if (cellvalue == 'BY_PASS') {
        return "<span class='label label-sm label-warning'>BY_PASS</span>";
    }
    else if (cellvalue == 'COMPLETED') {
        return "<span class='label label-sm label-success'>COMPLETED</span>";
    }
}

function unformatTaskStatus(cellvalue, options) {
    if (cellvalue.indexOf("PENDING") >= 0) {
        return "PENDING";
    }
    else if (cellvalue.indexOf("BY_PASS") >= 0) {
        return "BY_PASS";
    }
    else if (cellvalue.indexOf("COMPLETED") >= 0) {
        return "COMPLETED";
    }
}

function instanceStatusFormatter(cellvalue, options, rowObject) {
    if (cellvalue == 'IN_PROGRESS') {
        return "<span class='label label-sm label-primary'>IN_PROGRESS</span>";
    }
    else if (cellvalue == 'COMPLETED') {
        return "<span class='label label-sm label-success'>COMPLETED</span>";
    }
}

function unformatInstanceStatus(cellvalue, options) {
    if (cellvalue.indexOf("IN_PROGRESS") >= 0) {
        return "IN_PROGRESS";
    }
    else if (cellvalue.indexOf("COMPLETED") >= 0) {
        return "COMPLETED";
    }
}

function checkboxFormatter(cellvalue, options, rowObject) {
    if (cellvalue == '1') {
        return '<label class="inline"><input type="checkbox" class="ace ace-switch ace-switch-5" checked="checked"><span class="lbl"></span></label>';
    }
    else {
        return '<label class="inline"><input type="checkbox" class="ace ace-switch ace-switch-5"><span class="lbl"></span></label>';
    }
}

function booleanFormatter(cellvalue, options, rowObject) {
    if (cellvalue == '1') {
        return "<span class='label label-sm label-success'>是</span>";
    }
    else {
        return "<span class='label label-sm label-warning'>否</span>";
    }
}

function unformatBoolean(cellvalue, options) {
    if (cellvalue.indexOf("Yes") >= 0) {
        return "1";
    }
    else {
        return "0"
    }
}


function booleanExtendFormatter(cellvalue, options, rowObject) {
    if (cellvalue == 'Y') {
        return "<span class='label label-sm label-success'>是</span>";
    }
    else {
        return "<span class='label label-sm label-warning'>否</span>";
    }
}

function unformatBooleanExtend(cellvalue, options) {
    if (cellvalue.indexOf("Yes") >= 0) {
        return "Y";
    }
    else {
        return "N"
    }
}

function iconFormatter(cellvalue, options, rowObject) {
    if (cellvalue != '') {
        return "<i class='icon-ui " + cellvalue + "'></i>";
    } else {
        return "";
    }
}

function percentageFormatter(cellvalue, options, rowObject) {
    if (cellvalue == undefined || cellvalue == "") {
        cellvalue = 0;
    }
    var dueDate = rowObject.dueDate;
    if (dueDate == undefined) {
        return '<div data-percent="' + cellvalue + '%" class="progress">' +
            '<div style="width:' + cellvalue + '%;" class="progress-bar progress-bar-success">' +
            '</div></div>';
    }
    if (isOutOfDate(dueDate)) {
        return '<div data-percent="' + cellvalue + '%" class="progress progress-bar-danger">' +
            '<div style="width:' + cellvalue + '%;" class="progress-bar progress-bar-success">' +
            '</div></div>';
    } else {
        return '<div data-percent="' + cellvalue + '%" class="progress">' +
            '<div style="width:' + cellvalue + '%;" class="progress-bar progress-bar-success">' +
            '</div></div>';
    }

}

function arrowSortFormatter(cellvalue, options, rowObject) {
    var gridid = options.gid;
    var rowid = options.rowId;
    var html = '<a href="#" title="Up" onclick="doSortByArrow(\'' + gridid + '\',\'' + rowid + '\', true)"> <span class="ui-icon icon-arrow-up purple"></span></a>';
    html += '<a href="#" title="Down" onclick="doSortByArrow(\'' + gridid + '\',\'' + rowid + '\', false)"> <span class="ui-icon icon-arrow-down blue"></span></a>';
    return html;
}

function createByAndUpdateByFormatter(cellvalue, options, rowObject) {
    var propertyDesc = rowObject.modelProperty.propertyDesc;
    if (cellvalue != undefined && cellvalue != '') {
        if (propertyDesc == 'Last Updated By' || propertyDesc == 'Create By') {
            var userDisplayName = matrix.returnJsonData({
                url: _appContext + "/common/service/mas-user/" + cellvalue,
                async: false
            });
            return userDisplayName;
        }
    }
    return cellvalue;
};

function filenameFormatter(cellvalue) {
    if (cellvalue != '' && cellvalue != null) {
        var fileNameStart = cellvalue.lastIndexOf('/') + 1;
        var fileNameEnd = cellvalue.length;
        //var fileNameEnd = cellvalue.lastIndexOf('_');
        var link = cellvalue.substring(fileNameStart, fileNameEnd);
        return link;
    }
    return '';
}

function downloadFormatter(cellvalue, options, rowObject) {
    if (cellvalue != '' && cellvalue != 'undefined' && cellvalue != null) {
        var fileNames = cellvalue.split(';');
        var href = '';
        for (var i = 0; i < fileNames.length; i++) {
            if (href != '') {
                href += '<br>';
            }
            var _file_name = fileNames[i].substr(fileNames[i].lastIndexOf('/') + 1);
            href += '<a href="javascript:void(0)" onclick="doDownload(\'' + fileNames[i] + '\')">' + _file_name + '</a>';
        }
        return href;
    } else return '';
}

function downloadByTypeFormatter(cellvalue, options, rowObject) {
    if (cellvalue != '' && cellvalue != 'undefined' && cellvalue != null) {
        var fileNames = cellvalue.split(';');
        var href = '';
        for (var i = 0; i < fileNames.length; i++) {
            if (href != '') {
                href += '<br>';
            }
            var _file_name = fileNames[i].substring(0, fileNames[i].lastIndexOf(','));
            href += '<a href="javascript:void(0)" onclick="downloadByType(\'' + fileNames[i] + '\')">' + _file_name + '</a>';
        }
        return href;
    } else return '';
}

function nipUserFormat(cellvalue) {
    return nip_user[cellvalue] || "";
}

//BT:12794
var dateTemplate = {
    width: 100, sorttype: "date", formatter: "date",
    formatoptions: {srcformat: formatter.jqGridDateTime, newformat: formatter.jqGridDate},
    searchoptions: {
        searchhidden: true,
        dataInit: datePick,
        att: {title: 'Select Date'},
        sopt: ['eq', 'lt', 'gt', 'nu', 'nn', 'in', 'ni']
    },
    searchrules: {date: true}
};

var datetimeTemplate1 = {
    width: 150, sorttype: "date", formatter: "date",
    formatoptions: {srcformat: formatter.jqGridDateTime, newformat: formatter.jqGridDateTime},
    searchoptions: {dataInit: datePick, att: {title: 'Select Date'}, sopt: ['eq', 'lt', 'gt', 'nu', 'nn']},
    searchrules: {date: true}
};

var datetimeTemplate = {
    width: 150, sorttype: "date", formatter: "date",
    formatoptions: {srcformat: formatter.jqGridDateTime, newformat: formatter.jqGridDateTime},
    searchoptions: {
        dataInit: function (elem) {
            $(elem).datetimepicker({
                dateFormat: 'yy-mm-dd',
                timeFormat: 'HH:mm:ss'
            });
        }, att: {title: 'Select Date'}, sopt: ['lt', 'gt', 'nu', 'nn']
    },
    searchrules: {date: true}
};

var fullDatetimeTemplate = {
    width: 150, sorttype: "date", formatter: "date",
    formatoptions: {srcformat: formatter.jqGridFullDateTime, newformat: formatter.jqGridFullDateTime},
    searchoptions: {
        dataInit: function (elem) {
            $(elem).datetimepicker({
                dateFormat: 'yy-mm-dd',
                timeFormat: 'HH:mm:ss'
            });
        }, att: {title: 'Select Date'}, sopt: ['lt', 'gt', 'nu', 'nn']
    },
    searchrules: {date: true}
};

var dateEditTemplate = {
    editoptions: {size: 10, maxlengh: 10, dataInit: datePick},
    formatoptions: {newformat: formatter.jqGridDate}
};

var timeEditTemplate = {editoptions: {size: 5, maxlengh: 5, dataInit: timePick}};

//var stringTemplate = {searchoptions: {searchhidden: true, sopt: ['cn','nc', 'bw','ew','eq','ne','nu','nn']}};

var stringExtendTemplate = {
    searchoptions: {
        searchhidden: true,
        sopt: ['cn', 'nc', 'bw', 'ew', 'eq', 'ne', 'lt', 'le', 'gt', 'ge', 'in', 'ni', 'nu', 'nn']
    }
};
//bt 11715, 13136, 12794,all string template use same list
var stringTemplate = stringExtendTemplate;
var numberTemplate = {
    formatter: 'number', align: 'right', sorttype: 'number', editable: true,
    searchoptions: {sopt: ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'in', 'ni', 'nu', 'nn']}
};

var integerTemplate = {
    formatter: 'integer', align: 'center', sorttype: 'integer', editable: true,
    editrules: {integer: true},
    searchoptions: {sopt: ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'in', 'ni', 'nu', 'nn']}
};

var IsInTemplate = {
    align: 'right', editable: true,
    searchoptions: {sopt: ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'in', 'ni', 'nu', 'nn']}
};

var statusTemplate = {
    width: 80, formatter: statusFormatter, unformat: unformatStatus,
    stype: "select",
    searchoptions: {sopt: ['eq', 'ne'], value: "Y:Enabled;N:Disabled"}
};

var statusTemplateForNumber = {
    width: 80, formatter: statusFormatter, unformat: unformatStatus,
    stype: "select",
    searchoptions: {sopt: ['eq', 'ne'], value: "1:Enabled;0:Disabled"}
};

// workflow
var taskStatusTemplate = {
    width: 100, formatter: taskStatusFormatter, unformat: unformatTaskStatus,
    stype: "select",
    searchoptions: {sopt: ['eq', 'ne'], value: "PENDING:PENDING;BY_PASS:BY_PASS;COMPLETED:COMPLETED"}
};

var instanceStatusTemplate = {
    width: 100, formatter: instanceStatusFormatter, unformat: unformatInstanceStatus,
    stype: "select",
    searchoptions: {sopt: ['eq', 'ne'], value: "IN_PROGRESS:IN_PROGRESS;COMPLETED:COMPLETED"}
};


var extendTemplate = {}

var booleanTemplate = {
    align: 'center', formatter: booleanFormatter, unformat: unformatBoolean,
    stype: "select",
    searchoptions: {sopt: ['eq', 'ne'], value: "1:Yes;0:No"}
};

var booleanExtendTemplate = {
    formatter: booleanExtendFormatter, unformat: unformatBoolean,
    stype: "select",
    searchoptions: {sopt: ['eq', 'ne'], value: "Y:Yes;N:No"}
};

var percentageTemplate = {
    formatter: percentageFormatter,
    searchrules: {number: true, minValue: 0, maxValue: 100},
    searchoptions: {sopt: ['eq', 'ne', 'lt', 'le', 'gt', 'ge']}
};

var onlineOfflineTemplate = {
    formatter: onlineOfflineFormatter, unformat: onlineOfflineUnformatter,
    stype: "select",
    searchoptions: {sopt: ['eq', 'ne'], value: "1:Online;0:Offline"}
};

var arrowSortTemplate = {formatter: arrowSortFormatter, width: 50, editable: false, search: false, sortable: true}

var hkCurrencyTemplate = {
    formatter: hkCurrencyFormatter, unformat: hkCurrencyUnformatter, align: 'right', sorttype: 'number',
    searchoptions: {sopt: ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'nu', 'nn', 'in', 'ni']}
}

/*
var checkboxTemplate = {
    width: 80, sortable: false, align: 'center',
    formatter: 'checkbox', stype: 'checkbox', edittype: "checkbox",
    editoptions: {value: "1:0"},
    editable: true, search: false
};
*/

var checkboxTemplate = {
    width: 80, sortable: false, align: 'center',
    formatter: 'checkbox', stype: 'select', edittype: "checkbox",
    editoptions: {value: "1:0"}, search: true,
    searchoptions: {value: "1:Yes;0:No", sopt: ['eq', 'ne']},
    editable: true
};

