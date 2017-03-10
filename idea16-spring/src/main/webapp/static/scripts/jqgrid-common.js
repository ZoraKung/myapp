//use in select item
var idsOfSelectedRows = [];
var dataOfSelectedRows = [];
//use in jqgrid search, advance = 'N'(simple search), advance = 'Y'(advance search)
var advance = 'N';
var firstSearch = 'Y';
var updateIdsOfSelectedRows = function (id, data, isSelected) {
    var index = $.inArray(id, idsOfSelectedRows);
    if (!isSelected && index >= 0) {
        idsOfSelectedRows.splice(index, 1); // remove id from the list
        dataOfSelectedRows.splice(index, 1);
    } else if (index < 0) {
        idsOfSelectedRows.push(id);
        dataOfSelectedRows.push(data);
    }
};

/**
 *  Jquery Grid Generic
 */
$(function () {
    $.fn.getJqGridFilters = function () {
        var searchFields = "";
        jQuery.map($(this).serializeArray(), function (n, i) {
            if (n['value'] != '' && n['value'] != undefined) {
                var names = n['name'].split('_');
                var name = ''
                var op = '';
                if (names.length > 1) {
                    op = names[1].toLowerCase();
                }
                if (names.length > 2) {
                    name = names[2];
                }
                var value = (n['value']).escapeSpecialChars();
                searchFields += (searchFields.length == 0 ? "" : ",") + '{\"field\":\"' + name + '\",\"op\":\"' + op + '\",\"data\":\"' + value + '\"}';
            }
        });
        var filters = {};
        filters['filters'] = '{\"groupOp\":\"AND\",\"rules\":[' + searchFields + ']}';
        return filters;
    }
});

function jqGridSearch(tableId, url, searchFormId, inLine) {
    if (inLine) {
        $("#" + tableId).jqGrid('setGridParam', {
            url: url,
            search: true,
            postData: $("#" + searchFormId).getJqGridFilters(),
            page: 1
        }).trigger("reloadGrid");
    } else {
        $("#" + tableId).jqGrid('setGridParam', {
            url: url,
            search: true,
            postData: $("#" + searchFormId).serializeJSON(),
            page: 1
        }).trigger("reloadGrid");
    }
}

/**
 * Author: Jack, Date: 2015-02-06
 * add filter to jqgrid filter
 * @param filter: [{fieldName:field,op:op,value:data},{fieldName:field,op:op,value:data}]
 * @returns {{}}
 */
function addFilterToJqGridFilter(filter) {
    if (filter.length) {
        var _searchFields = '';
        for (var i = 0; i < filter.length; i++) {
            var _name = filter[i].fieldName, _op = filter[i].op, _value = filter[i].value;
            if (_name != '' && _name != null && _op != '' && _op != null && _value != '' && _value != null) {
                _searchFields += (_searchFields.length == 0 ? "" : ",") + '{\"field\":\"' + _name + '\",\"op\":\"' + _op.toLowerCase() + '\",\"data\":\"' + _value.escapeSpecialChars() + '\"}';
            }
        }
    }
    var filters = {};
    if (_searchFields != '') {
        filters['filters'] = '{\"groupOp\":\"AND\",\"rules\":[' + _searchFields + ']}';
    }
    ;
    return filters;
}

function aceSwitch(cellvalue, options, cell) {
    setTimeout(function () {
        if (cellvalue == "Yes" || cellvalue == true || cellvalue == 'true') {
            $(cell).find('input[type=checkbox]').attr("checked", "checked");
        }
        else {
            $(cell).find('input[type=checkbox]').removeAttr("checked");
        }

        $(cell).find('input[type=checkbox]')
            .wrap('<label class="inline" />')
            .addClass('ace ace-switch ace-switch-2')
            .after('<span class="lbl"></span>');
    }, 0);

}

//Enable date picker
function pickDate(cellvalue, options, cell) {
    setTimeout(function () {
        $(cell).find('input[type=text]')
            .datepicker({format: 'dd-mm-yyyy', autoclose: true});
    }, 0);
}

datePick = function (elem) {
    $(elem).datepicker({
        format: 'dd-mm-yyyy',
        autoclose: true
        //        beforeShow: function (i,e) {
        //            var z = jQuery(i).closest(".ui-dialog").css("z-index") + 4;
        //            e.dpDiv.css('z-index', z);
        //        }
    });
}

timePick = function (elem) {
    $(elem).wrap("<div class='bootstrap-timepicker input-small input-group '></div>");
    ///*@(elem).after("<iframe style=\"position: absolute; z-index: -1; width: 100%; height: 100%; top: 0; left:0; scrolling:no;\" frameborder=\"0\"></iframe>");*/
    $(elem).timepicker({
        minuteStep: 1,
        showSeconds: false,
        showMeridian: false
    }).next().on(ace.click_event, function () {
        $(this).prev().focus();
    });
}

function gridShowMessage_afterSubmit(response, postdata) {
    var obj = jQuery.parseJSON(response.responseText);
    if (obj.state == "OK") {
        $.gritter.add({
            title: "<i class='icon-info-sign bigger-120 blue'></i>  " + i18nQuery('label_message'),
            text: obj.message,
            class_name: "gritter-info gritter-light"
        });
        return [true, obj.state, obj.id];
    }
    else {
        $.gritter.add({
            title: "<i class='icon-bug bigger-120 blue'></i>  " + i18nQuery('label_error'),
            text: obj.message,
            class_name: "gritter-error"
        });

        return [false, obj.message, obj.id]
    }
}

/**
 *  Inline Edit Action
 */
function inlineEditAfterSave(rowid, response) {
    var originalRowId = rowid;
    var $grid = $(this);
    var obj = jQuery.parseJSON(response.responseText);
    var newRowId = obj.id;
    var $tr = $grid.find("tr[id='" + originalRowId + "']");
    $tr.children().each(function () {
        var context = $(this).find("option:selected").text();
        if (context != "") {
            $(this).text(context);
        }
    });
    $tr.children("td:last").find(".hide").each(function () {
        $(this).removeClass("hide");
    });
    if (obj.state == "OK") {
        matrix.showMessage(obj.message, "Message");
        if (newRowId != '' && newRowId != originalRowId) {
            $tr.attr("id", newRowId);
            var $firstTd = $tr.children().eq(0);
            var $input = $firstTd.find("input");
            if ($input.length > 0) {
                var checkboxId = $input.attr("id");
                checkboxId = checkboxId.substring(0, checkboxId.lastIndexOf("_") + 1) + newRowId;
                $input.attr("id", checkboxId);
                $input.attr("name", checkboxId);
            } else {
                $firstTd.text(newRowId);
            }
        }
        return [true, obj.message, obj.id];
    }
    else {
        matrix.showError(obj.message, "Error");
        return [false, obj.message, obj.id]
    }
}

function inlineEditAfterRestore(rowid) {

}

function inlineEditOnSuccess(response) {
    //console.info("onSuccess");
    var $self = $(this);

    var obj = jQuery.parseJSON(response.responseText);

    if (obj.state == "OK") {
        matrix.showMessage(obj.message, "Message");
        setTimeout(function () {
            $self.trigger("reloadGrid");
        }, 50);

        //return [true, obj.state, obj.id];
        return true;
    }
    else {
        matrix.showError(obj.message, "Error");
        //return [false, obj.message, obj.id]
        return false;
    }
}

function inlineEditOnError(rowid, jqXHR, textStatus) {
    //matrix.showError()
}

/**
 *
 * Nav bar Setting
 *
 */

var gridSearchForm_afterShowSearch = function (e) {
    var form = $(e[0]);
    form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
    style_search_form(form);
};


var gridViewForm_beforeShowForm = function (e) {
    var form = $(e[0]);
    form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
    style_show_form(form);
};

var gridDeleteForm_beforeShowForm = function (e) {
    var form = $(e[0]);
    if (form.data('styled')) return false;

    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
    style_delete_form(form);

    form.data('styled', true);
};

var gridCreateForm_beforeShowForm = function (e) {
    var form = $(e[0]);
    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
    style_edit_form(form);
};

var gridEditForm_beforeShowForm = function (e) {
    var form = $(e[0]);
    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
    style_edit_form(form);
};


function getNavSearchClass(gridSelector) {
    var _searchicon = 'icon-search grey';
    var fts = $(gridSelector).getGridParam("postData").filters;
    if (fts != undefined) {
        var contains = (fts.indexOf('field') > -1);
        if (contains == true) {
            _searchicon = 'icon-search red';
        }
    }

    return _searchicon;
}


function style_header_icon() {
    $('.ui-jqgrid').find(".ui-icon-circle-triangle-n").each(function () {
        var icon = $(this);
        icon.attr('class', 'icon-circle-arrow-up white');
        icon.bind('click', function (event) {
            if (icon.hasClass('icon-circle-arrow-up')) {
                icon.attr('class', ' icon-circle-arrow-down white');
            }
            else {
                icon.attr('class', 'icon-circle-arrow-up white');
            }
        });
    });

    $('.ui-jqgrid').find(".ui-icon-circle-triangle-s").each(function () {
        var icon = $(this);
        icon.attr('class', ' icon-circle-arrow-down white');
        icon.bind('click', function (event) {
            if (icon.hasClass('icon-circle-arrow-up')) {
                icon.attr('class', ' icon-circle-arrow-down white');
            }
            else {
                icon.attr('class', 'icon-circle-arrow-up white');
            }
        });
    });
}


// Execute Customize
function style_show_form(form) {
    if (typeof this["style_show_form_customize"] == "function") {
        this["style_show_form_customize"](form);
    }
}

// Customize Edit Form
function style_edit_form(form) {
    if (typeof this["style_edit_form_customize"] == "function") {
        this["style_edit_form_customize"](form);
    }

    //update buttons classes
    var buttons = form.next().find('.EditButton .fm-button');
    buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
    buttons.eq(0).addClass('btn-primary').prepend('<i class="icon-ok"></i>');
    buttons.eq(1).prepend('<i class="icon-remove"></i>')

    buttons = form.next().find('.navButton a');
    buttons.find('.ui-icon').remove();
    buttons.eq(0).append('<i class="icon-chevron-left"></i>');
    buttons.eq(1).append('<i class="icon-chevron-right"></i>');
}

function style_delete_form(form) {
    var buttons = form.next().find('.EditButton .fm-button');
    buttons.addClass('btn btn-sm').find('[class*="-icon"]').remove();//ui-icon, s-icon
    buttons.eq(0).addClass('btn-danger').prepend('<i class="icon-trash"></i>');
    buttons.eq(1).prepend('<i class="icon-remove"></i>')
}

function style_search_filters(form) {
    form.find('.delete-rule').val('X');
    form.find('.add-rule').addClass('btn btn-xs btn-primary');
    form.find('.add-group').addClass('btn btn-xs btn-success');
    form.find('.delete-group').addClass('btn btn-xs btn-danger');
}

function style_search_form(form) {
    var dialog = form.closest('.ui-jqdialog');
    var buttons = dialog.find('.EditTable')
    buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'icon-retweet');
    buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'icon-comment-alt');
    buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'icon-search');
}

function beforeDeleteCallback(e) {
    var form = $(e[0]);
    if (form.data('styled')) return false;

    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
    style_delete_form(form);

    form.data('styled', true);
}

function beforeEditCallback(e) {
    var form = $(e[0]);
    form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
    style_edit_form(form);
}


//it causes some flicker when reloading or navigating grid
//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
//or go back to default browser checkbox styles for the grid
function styleCheckbox(table) {
    /**
     $(table).find('input:checkbox').addClass('ace')
     .wrap('<label />')
     .after('<span class="lbl align-top" />')


     $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
     .find('input.cbox[type=checkbox]').addClass('ace')
     .wrap('<label />').after('<span class="lbl align-top" />');
     */
}


//unlike navButtons icons, action icons in rows seem to be hard-coded
//you can change them like this in here if you want
function updateActionIcons(table) {
    /**
     var replacement =
     {
         'ui-icon-pencil' : 'icon-pencil blue',
         'ui-icon-trash' : 'icon-trash red',
         'ui-icon-disk' : 'icon-ok green',
         'ui-icon-cancel' : 'icon-remove red'
     };
     $(table).find('.ui-pg-div span.ui-icon').each(function(){
						var icon = $(this);
						var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
						if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
					})
     */
}

//Replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
    //alert(table);
    var replacement =
    {
        'ui-icon-seek-first': 'icon-double-angle-left bigger-140',
        'ui-icon-seek-prev': 'icon-angle-left bigger-140',
        'ui-icon-seek-next': 'icon-angle-right bigger-140',
        'ui-icon-seek-end': 'icon-double-angle-right bigger-140'
    };
    $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

        if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
    })
}

function enableTooltips(table) {
    $('.navtable .ui-pg-button').tooltip({container: 'body'});
    $(table).find('.ui-pg-div').tooltip({container: 'body'});
}

function changePageAll(table) {
    $(table).find('select .ui-pg-selbox option[text="ALL"]').text('2000');
}

function getColumnIndexByName(grid, columnName) {
    var cm = grid.jqGrid('getGridParam', 'colModel'), i, l = cm.length;
    for (i = 0; i < l; i++) {
        if (cm[i].name === columnName) {
            return i; // return the index
        }
    }
    return -1;
};

// For Non-Actions Column : Add Div as parent of Actions
function renderActionColumnDiv(grid, columnName) {
    var iCol = getColumnIndexByName(grid, columnName);
    grid.children("tbody")
        .children("tr.jqgrow")
        .children("td:nth-child(" + (iCol + 1) + ")")
        .each(function () {
            $(this).css({"vertical-align": "middle"});
        }
    );

    grid.children("tbody")
        .children("tr.jqgrow")
        .children("td:nth-child(" + (iCol + 1) + ")")
        .each(function () {
            $("<div>").css({"margin-left": "8px", "float": "left"})
                .appendTo($(this));
        }
    );
}

function renderActionColumnInit(grid, columnName, options) {
    var iCol = getColumnIndexByName(grid, columnName);
    grid.children("tbody")
        .children("tr.jqgrow")
        .children("td:nth-child(" + (iCol + 1) + ")")
        .each(function () {
            $(this).children("div").remove()
        });
    //.each(function() {$(this).children("div").empty()});
}

$.fn.isExistIconInAction = function (title) {
    var _result = false;
    $(this).find('div.ui-pg-div').each(function () {
        if ($(this).attr('title') == title) {
            _result = true;
            return false;
        }
    });
    return _result;
}

function renderActionColumn(grid, columnName, options) {
    var iCol = getColumnIndexByName(grid, columnName);
    grid.children("tbody")
        .children("tr.jqgrow")
        .children("td:nth-child(" + (iCol + 1) + ")")
        .each(function () {
            var _validate = true;
            if (options.validate != undefined && $.isFunction(options.validate)) {
                var _row_id = $(this).closest("tr.jqgrow").attr("id");
                _validate = options.validate(_row_id);
            }
            if (_validate) {
                if (!$(this).isExistIconInAction(options.title)) {
                    $("<div>",
                        {
                            //style: "margin-left: 5px; float:left",
                            //class: "ui-pg-div ui-inline-custom",
                            title: options.title,
                            mouseover: function () {
                                $(this).addClass('ui-state-hover');
                            },
                            mouseout: function () {
                                $(this).removeClass('ui-state-hover');
                            },
                            click: options.callback
                        }
                    ).css({"margin-left": "5px", float: "left"})
                        .addClass("ui-pg-div ui-inline-custom")
                        .append('<span class="' + options.iconclass + '"></span>')
                        .appendTo($(this).children("div"));
                }
            }
        }
    );
    $('#'+grid.attr('id')+'_frozen')
        .children("tbody")
        .children("tr.jqgrow")
        .children("td:nth-child(" + (iCol + 1) + ")")
        .each(function () {
                var _validate = true;
                if (options.validate != undefined && $.isFunction(options.validate)) {
                    var _row_id = $(this).closest("tr.jqgrow").attr("id");
                    _validate = options.validate(_row_id);
                }
                if (_validate) {
                    if (!$(this).isExistIconInAction(options.title)) {
                        $("<div>",
                            {
                                //style: "margin-left: 5px; float:left",
                                //class: "ui-pg-div ui-inline-custom",
                                title: options.title,
                                mouseover: function () {
                                    $(this).addClass('ui-state-hover');
                                },
                                mouseout: function () {
                                    $(this).removeClass('ui-state-hover');
                                },
                                click: options.callback
                            }
                        ).css({"margin-left": "5px", float: "left"})
                            .addClass("ui-pg-div ui-inline-custom")
                            .append('<span class="' + options.iconclass + '"></span>')
                            .appendTo($(this).children("div"));
                    }
                }
            }
        );
}

function renderActionColumnEditInline(grid, columnName, options) {
    var iCol = getColumnIndexByName(grid, columnName);
    grid.children("tbody")
        .children("tr.jqgrow")
        .eq(0)
        .children("td:nth-child(" + (iCol + 1) + ")")
        .each(function () {
            $("<div>",
                {
                    //style: "margin-left: 5px; float:left",
                    //class: "ui-pg-div ui-inline-custom",
                    title: options.title,
                    mouseover: function () {
                        $(this).addClass('ui-state-hover');
                    },
                    mouseout: function () {
                        $(this).removeClass('ui-state-hover');
                    },
                    click: options.callback
                }
            ).css({"margin-left": "5px", float: "left"})
                .addClass("ui-pg-div ui-inline-custom hide")
                .append('<span class="' + options.iconclass + '"></span>')
                .appendTo($(this).children("div"));
        }
    );
}

function renderHighlight(grid, columnName, paramete) {
    var iCol = getColumnIndexByName(grid, columnName);
    grid.children("tbody")
        .children("tr.jqgrow")
        .children("td:nth-child(" + (iCol + 1) + ")")
        .each(function () {
            $(this).css({"vertical-align": "middle"});
            if ($(this).context.innerHTML === paramete) {
                $(this).closest("tr.jqgrow").find('td').css({"background-color": "#FEF889"});
            }
        }
    );
};

function renderActionCell(grid, columnName, rowId, options) {
    var iCol = getColumnIndexByName(grid, columnName);
    grid.children("tbody")
        .children("tr.jqgrow#" + rowId)
        .children("td:nth-child(" + (iCol + 1) + ")")
        .each(function () {
            $("<div>",
                {
                    //style: "margin-left: 5px; float:left",
                    //class: "ui-pg-div ui-inline-custom",
                    title: options.title,
                    mouseover: function () {
                        $(this).addClass('ui-state-hover');
                    },
                    mouseout: function () {
                        $(this).removeClass('ui-state-hover');
                    },
                    click: options.callback
                }
            ).css({"margin-left": "5px", float: "left"})
                .addClass("ui-pg-div ui-inline-custom")
                .append('<span class="' + options.iconclass + '"></span>')
                .appendTo($(this).children("div"));
        }
    );

}
// Render Customize Show Action Column
function renderShowAction(grid, columnName, url) {
    renderActionColumn(grid, columnName,
        {
            title: i18nQuery('button_show'),
            iconclass: 'ui-icon icon-zoom-in purple',
            callback: function (e) {
                location.href = url + $(e.target).closest("tr.jqgrow").attr("id");
            }
        }
    );
}

// Render Customize Edit Action Column
function renderEditAction(grid, columnName, url) {
    renderActionColumn(grid, columnName,
        {
            title: i18nQuery('button_edit'),
            iconclass: 'ui-icon icon-pencil',
            callback: function (e) {
                location.href = url + $(e.target).closest("tr.jqgrow").attr("id");
            }
        }
    );
}
// Render Customize Delete Action Column
// 'deletionForm' must defined in the page
function renderDelAction(grid, columnName) {
    renderActionColumn(grid, columnName,
        {
            title: i18nQuery('button_delete'),
            iconclass: 'ui-icon icon-trash red',
            callback: function (e) {
                matrix.confirmEntryDeletion($(e.target).closest("tr.jqgrow").attr("id"));
            }
        }
    );
}

function renderDelAction(grid, columnName, url) {
    renderActionColumn(grid, columnName,
        {
            title: i18nQuery('button_delete'),
            iconclass: 'ui-icon icon-trash red',
            callback: function (e) {
                var options = {url: url + $(e.target).closest("tr.jqgrow").attr("id")};
                matrix.deleteConfirm(options);
            }
        }
    );
}

function renderAjaxDelAction(grid, columnName, url) {
    renderActionColumn(grid, columnName,
        {
            title: i18nQuery('button_delete'),
            iconclass: 'ui-icon icon-trash red',
            callback: function (e) {
                var options = {url: url + $(e.target).closest("tr.jqgrow").attr("id"), grid: grid};
                matrix.ajaxConfirmDeleteAction(options);
            }
        }
    );
}

function renderAjaxDeleteAction(grid, columnName, url) {
    renderActionColumn(grid, columnName,
        {
            title: i18nQuery('button_delete'),
            iconclass: 'ui-icon icon-trash red',
            callback: function (e) {
                var id = $(e.target).closest("tr.jqgrow").attr("id");
                var options = {url: url, id: id, grid: grid};
                matrix.ajaxConfirmDeleteAction(options);
            }
        }
    );
}

// Build jqGrid
var resetAltRows = function () {
    // I think one can improve performance the function a little if needed,
    // but it should be done the same
    $(this).children("tbody:first").children('tr.jqgrow').removeClass('jqgrid-treegrid-alt-row');
    $(this).children("tbody:first").children('tr.jqgrow:visible:odd').addClass('jqgrid-treegrid-alt-row');
};

// Build jqGrid
function jqGridBuild(gconf) {

    // User Setting
    if (typeof (gconf.userSetting) !== 'undefined') {
        gconf.userSetting.settingType = "jqgrid";
        if (typeof (gconf.userSetting.settingTag) === 'undefined') {
            gconf.userSetting.settingTag = "default";
        }
        if (typeof (gconf.userSetting.revampNo) === 'undefined') {
            gconf.userSetting.revampNo = "1";
        }
        if (typeof (gconf.userSetting.userName) === 'undefined') {
            gconf.userSetting.userName = _userName;
        }
        if (typeof (gconf.userSetting.localStorage) === 'undefined') {
            gconf.userSetting.localStorage = false;
        }
        if (typeof (gconf.userSetting.autoSetting) === 'undefined') {
            gconf.userSetting.autoSetting = 'false';
        }
    }
    else {
        gconf.userSetting = {
            userName: _userName,
            settingType: "jqgrid",
            settingName: "general",
            settingTag: 'default',
            revampNo: '1',
            localStorage: false,
            autoSetting: 'false'
        }
    }
    // grid & pager
    if (typeof (gconf.gridSelector) === 'undefined') {
        gconf.gridSelector = "#grid-table";
    }
    if (typeof (gconf.pagerSelector) === 'undefined') {
        gconf.pagerSelector = "#grid-pager";
    }

    // Tree
    if (typeof (gconf.treeGrid) === 'undefined') {
        gconf.treeGrid = false;
    }

    // Nav Bar Position
    if (typeof (gconf.bNavBarInBottom) === 'undefined') {
        gconf.bNavBarInBottom = true;
    }

    if (typeof (gconf.bNavBarInTop) === 'undefined') {
        gconf.bNavBarInTop = false;
    }

    //add callback after load complete
    if (typeof (gconf.afterLoadComplete) === 'undefined') {
        gconf.afterLoadComplete = undefined;
    }

    if (gconf.bNavBarInBottom) {
    }
    else {
        gconf.pagerSelector = gconf.gridSelector + "_toppager";
    }

    if (gconf.bNavBarInTop) {
        gconf.topPagerSelector = gconf.gridSelector + "_toppager";
    }
    else {
        gconf.topPagerSelector = gconf.pagerSelector;
    }
    gconf.grid = $(gconf.gridSelector);

    // Status from DB
    gconf.myColumnsState = restoreColumnState(gconf.colModel, gconf.userSetting, true);
    gconf.isColState = typeof (gconf.myColumnsState) !== 'undefined' && gconf.myColumnsState !== null;
    gconf.firstLoad = true;

    // for Numeric Paging
    if (typeof (gconf.numericPaging) === 'undefined') {
        gconf.numericPaging = false;
    }
    if (typeof (gconf.maxPagers) === 'undefined') {
        gconf.maxPagers = 3;
    }
    // var grid = $(gconf.gridSelector), MAX_PAGERS = 2;
    // End Numeric Paging

    // console.info('create');

    jQuery(gconf.gridSelector).jqGrid({
        //direction: "ltr",
        caption: gconf.caption,
        loadonce: typeof (gconf.loadonce) !== 'undefined' ? gconf.loadonce : false,
        url: typeof (gconf.url) !== 'undefined' ? gconf.url : "",
        editurl: typeof (gconf.editurl) !== 'undefined' ? gconf.editurl : "",
        datatype: typeof (gconf.datatype) !== 'undefined' ? gconf.datatype : "json",
        data: typeof (gconf.data) !== 'undefined' ? gconf.data : [],
        mtype: typeof (gconf.mtype) !== 'undefined' ? gconf.mtype : "POST",
        height: typeof (gconf.height) !== 'undefined' ? gconf.height : 'auto',
        width: typeof (gconf.width) !== 'undefined' ? gconf.width : 'auto',
        autowidth: typeof (gconf.autowidth) !== 'undefined' ? gconf.autowidth : true,
        shrinkToFit: typeof (gconf.shrinkToFit) !== 'undefined' ? gconf.shrinkToFit : false,
        footerrow: typeof (gconf.footerrow) !== 'undefined' ? gconf.footerrow : false,
        viewrecords: typeof (gconf.viewrecords) !== 'undefined' ? gconf.viewrecords : jqgridConstants.viewrecords,
        rowList: typeof (gconf.rowList) !== 'undefined' ? gconf.rowList : jqgridConstants.rowList,
        pager: typeof (gconf.pagerSelector) !== 'undefined' ? gconf.pagerSelector : 'pager',
        altRows: typeof (gconf.altRows) !== 'undefined' ? gconf.altRows : true,
        multiselect: typeof (gconf.multiselect) !== 'undefined' ? gconf.multiselect : true,
        //multikey: "ctrlKey",
        multiboxonly: typeof (gconf.multiboxonly) !== 'undefined' ? gconf.multiboxonly : true,
        // BT 10993
        beforeSelectRow: function (rowid, e) {
            if (typeof (gconf.multiselect) !== 'undefined' && gconf.multiselect) {
                var $myGrid = $(this),
                    i = $.jgrid.getCellIndex($(e.target).closest('td')[0]),
                    cm = $myGrid.jqGrid('getGridParam', 'colModel');
                return (cm[i].name === 'cb');
            }
            else {
                return true;
            }
        },
        //bt:11329
        onSelectAll: function (aRowids, isSelected) {
            //update select ids
            var i, count, id;
            for (i = 0, count = aRowids.length; i < count; i++) {
                id = aRowids[i];
                var _row_data = $(gconf.gridSelector).jqGrid('getRowData', id);
                updateIdsOfSelectedRows(id, _row_data, isSelected);
            }
            //update show selected item
            doShowSelectedCount(gconf.gridSelector);
        },
        onSelectRow: function (id, isSelected) {
            var _row_data = $(gconf.gridSelector).jqGrid('getRowData', id);
            //update select ids
            updateIdsOfSelectedRows(id, _row_data, isSelected);
            //update show selected item
            doShowSelectedCount(gconf.gridSelector);
        },

        hidegrid: typeof (gconf.hidegrid) !== 'undefined' ? gconf.hidegrid : true,
        rownumbers: false,
        toppager: gconf.bNavBarInTop,
        pgbuttons: typeof (gconf.pgbuttons) !== 'undefined' ? gconf.pgbuttons : true,
        pginput: typeof (gconf.pginput) !== 'undefined' ? gconf.pginput : true,

        gridview: typeof (gconf.gridview) !== 'undefined' ? gconf.gridview : true,
        page: gconf.isColState ? gconf.myColumnsState.page : 1,
        search: gconf.isColState ? gconf.myColumnsState.search : false,
        postData: gconf.isColState ? {filters: gconf.myColumnsState.filters} : (typeof (gconf.postData) !== 'undefined' ? gconf.postData : {}),
        sortname: gconf.isColState ? gconf.myColumnsState.sortname : gconf.sortname,
        sortorder: gconf.isColState ? gconf.myColumnsState.sortorder : gconf.sortorder,
        rowNum: gconf.isColState ? gconf.myColumnsState.rowNum : (typeof (gconf.rowNum) !== 'undefined' ? gconf.rowNum : jqgridConstants.rowNum), //bt: 13602
        // rownumbers: true, Not Work - Need reset column
        ignoreCase: true,
        colNames: gconf.colNames,
        colModel: gconf.colModel,
        serializeRowData: gconf.serializeRowData,
        /*        beforeRequest: function () {
         modifySearchingFilter.call(this, ',');
         },*/
        treeGrid: typeof (gconf.treeGrid) !== 'undefined' ? gconf.treeGrid : false,
        treeGridModel: typeof (gconf.treeGridModel) !== 'undefined' ? gconf.treeGridModel : "adjacency",
        ExpandColumn: typeof (gconf.ExpandColumn) !== 'undefined' ? gconf.ExpandColumn : "",
        ExpandColClick: typeof (gconf.ExpandColClick) !== 'undefined' ? gconf.ExpandColClick : true,
        treeIcons: {
            plus: 'icon-folder-close purple tree-icon-middle',
            minus: 'icon-folder-open tree-icon-middle',
            leaf: 'icon-check-empty tree-icon-middle'
        },
        scroll: typeof(gconf.scroll) != 'undefined' ? gconf.scroll : false,
        jsonReader: typeof(gconf.jsonReader) != 'undefined' ? gconf.jsonReader : {},
        treeReader: typeof(gconf.treeReader) != 'undefined' ? gconf.treeReader : {},

        /*subGrid setting */
        subGrid: typeof(gconf.subGrid) != 'undefined' ? gconf.subGrid : false,
        subGridRowExpanded: typeof(gconf.subGridRowExpanded) != 'undefined' ? gconf.subGridRowExpanded : null,
        subGridRowColapsed: typeof(gconf.subGridRowColapsed) != 'undefined' ? gconf.subGridRowColapsed : null,
        subGridOptions: {
            plusicon: "icon-plus center bigger-110 blue",
            minusicon: "icon-minus center bigger-110 blue",
            openicon: "icon-double-angle-right mysubgrid bigger-140 orange"
        },
        /*End of subGrid */
        /*resizeStop: function (newwidth, index) {
         setColumnHeaderHeight(gconf.gridSelector);
         },*/
        loadComplete: function (xhr) {
            frozenFixed($(this));
            if (xhr != undefined && xhr.userData != null && !xhr.userData.success && xhr.userData.msg != "") {
                matrix.showError(xhr.userData.msg);
                /*			}else if (xhr.userData != null && xhr.userData.success && xhr.userData.msg != "") {
                 matrix.showMessage(xhr.userData.msg);*/
            }
            //Numeric Paging
            /*
             if(gconf.numericPaging){
             var grid = $(gconf.gridSelector);
             var MAX_PAGERS = gconf.maxPagers;

             var i, myPageRefresh = function(e) {
             var newPage = $(e.target).text();
             grid.trigger("reloadGrid",[{page:newPage}]);
             e.preventDefault();
             };

             $(grid[0].p.pager + '_center td.myPager').remove();
             var pagerPrevTD = $('<td>', { class: "myPager"}), prevPagesIncluded = 0,
             pagerNextTD = $('<td>', { class: "myPager"}), nextPagesIncluded = 0,
             totalStyle = grid[0].p.pginput === false,
             startIndex = totalStyle? this.p.page-MAX_PAGERS: this.p.page-MAX_PAGERS;
             //startIndex = totalStyle? this.p.page-MAX_PAGERS*2: this.p.page-MAX_PAGERS;
             //for (i=startIndex; i<=this.p.lastpage && (totalStyle? (prevPagesIncluded+nextPagesIncluded<(MAX_PAGERS*2)):(nextPagesIncluded<MAX_PAGERS)); i++) {
             //    if (i<=0 || i === this.p.page) { continue; }
             for (i=startIndex; i<=this.p.lastpage && (totalStyle? (prevPagesIncluded+nextPagesIncluded<(MAX_PAGERS*2 + 1)):(nextPagesIncluded<MAX_PAGERS)); i++) {
             if (i<=0 || (i === this.p.page && grid[0].p.pginput !== false) ) { continue; }

             var link = $('<a>', { href:'#', click:myPageRefresh });
             if(i === this.p.page){
             //link = $('<a>', { href:'#' });
             link = $('<span class="badge badge-warning">');
             //link.html('<span class="badge badge-yellow">' + String(i) + '</span>');
             link.html(String(i));
             }
             else{
             link.html('<span class="badge">' + String(i) + '</span>');
             }
             //link.text(String(i));

             if (i<this.p.page || totalStyle) {
             // if (prevPagesIncluded>0) { pagerPrevTD.append(',&nbsp;'); }
             pagerPrevTD.append(link)
             prevPagesIncluded++;
             } else {
             // if (nextPagesIncluded>0 || (totalStyle && prevPagesIncluded>0)) { pagerNextTD.append(',&nbsp;'); }
             pagerNextTD.append(link);
             nextPagesIncluded++;
             }
             }
             if (prevPagesIncluded > 0) {
             $(grid[0].p.pager + '_center td[id^="prev"]').after(pagerPrevTD);
             }
             if (nextPagesIncluded > 0) {
             $(grid[0].p.pager + '_center td[id^="next"]').before(pagerNextTD);
             }
             }
             */

            // End of Numeric Paging

            // Tree Grid
            if (gconf.treeGrid) {
                // $('.tree-leaf', $(this)).attr('class', 'icon-check-empty tree-icon-middle');
                $('.tree-leaf', $(this)).addClass('icon-check-empty tree-icon-middle');

                //var grid = this;
                resetAltRows.call(this);
                $(this).find('tr.jqgrow td div.treeclick').click(function () {
                    resetAltRows.call(this);
                });
                $(this).find('tr.jqgrow td span.cell-wrapper').click(function () {
                    resetAltRows.call(this);
                });
            }
            // End of TreeGrid

            var table = this;
            setTimeout(function () {
                styleCheckbox(table);
                updateActionIcons(table);
                updatePagerIcons(table);
                enableTooltips(table);
                changePageAll(table);

                if (typeof (gconf.renderCustomizeActionColumn) !== 'undefined') {
                    gconf.renderCustomizeActionColumn(gconf.gridSelector);
                }

                if (gconf.treeGrid) {
                    if (gconf.collapseAll !== undefined && gconf.collapseAll) {
                        $(gconf.gridSelector).find(".tree-minus").trigger("click");
                    }
                }


                if (gconf.bNavBarInTop) {
                    $(gconf.gridSelector).jqGrid('navGrid', gconf.pagerSelector, {cloneToTop: true});
                }

                if (gconf.firstLoad) {
                    gconf.firstLoad = false;
                    //if (gconf.isColState) {
                    // Fixed: Uncaught TypeError: Cannot read property 'el' of undefined
                    if (gconf.isColState && gconf.myColumnsState.permutation.length > 0) {
                        $(gconf.gridSelector).jqGrid("remapColumns", gconf.myColumnsState.permutation, true);
                        // $(this).jqGrid("remapColumns", gconf.myColumnsState.permutation, false);
                    }
                }

                //$(gconf.gridSelector).parents(".ui-jqgrid-bdiv").css("overflow-x","hidden");
            }, 0);

            //BUG: saveColumnState.call($(this), this.p.remapColumns);

            //set select item
            var $this = $(this), i, count;
            for (i = 0, count = idsOfSelectedRows.length; i < count; i++) {
                $this.jqGrid('setSelection', idsOfSelectedRows[i], false);
            }

            //bt: 11329, show selected item
            if (typeof (gconf.multiselect) !== 'undefined' && gconf.multiselect) {
                doShowSelectedCount(gconf.gridSelector);
            }

            //bt: 13602
            if (gconf.isColState && gconf.myColumnsState.rowNum) {
                doSetRowNum(gconf.gridSelector, gconf.myColumnsState.rowNum);
            }

            //bt: 11485, auto save config
            if ((typeof (gconf.userSetting.autoSetting) !== 'undefined') && (gconf.userSetting.autoSetting == 'true')) {
                saveColumnState.call($(this), this.p.remapColumns, gconf.userSetting, true, false);
            }

            //add after load complete
            if (gconf.afterLoadComplete != undefined && $.isFunction(gconf.afterLoadComplete)) {
                gconf.afterLoadComplete(xhr);
            }
        }
        //,
        //resizeStop: function () {
        // Manual - saveColumnState.call($grid, $grid[0].p.remapColumns);
        //}
    });
    if (typeof (gconf.setNavGrid) !== 'undefined') {
        gconf.setNavGrid(gconf.gridSelector, gconf.topPagerSelector, gconf.userSetting);
    }

    if (typeof (gconf.setEditRow) !== 'undefined') {
        gconf.setEditRow();
    }
    //set column height
    //setColumnHeaderHeight(gconf.gridSelector);

    style_header_icon();

    $(gconf.gridSelector + "_toppager_center option[value=10000]").text('ALL');

    if (gconf.autoHeight == undefined || gconf.autoHeight == false) {
        // Height Auto-size
//        var gridTop = $(gconf.gridSelector).offset().top;
//        var windowHeight = $(window).innerHeight();
//        var gridHeight = windowHeight - gridTop - 30;
//        if (gridHeight < 250) gridHeight = 250;
//
//        if (gconf.gridSelector != "#item_selected_grid") {
//            $(gconf.gridSelector).jqGrid('setGridHeight', gridHeight);
        // $(gconf.gridSelector).parents('div.ui-jqgrid-bdiv').css("max-height", "600px");
//        }
//        else {
        //$("#item_selected_grid").parents("div.ui-jqgrid-bdiv").css("max-height", "360px");
//        }
    }

    //set top nav width
    doSetGridNavLayout(gconf.gridSelector);
}

//set rowNum
function doSetRowNum(gridSelector, rowNum) {
    $(gridSelector + '_toppager_center').find('select.ui-pg-selbox').val(rowNum);
}

//set No of select item in paging info
function doShowSelectedCount(gridSelector) {
    $(gridSelector + '_toppager_right').find('div.ui-paging-selected-info').remove();
    $(gridSelector + '_toppager_right').find('div.ui-paging-info').each(function () {
        //var selectedIds = $(gridSelector).jqGrid("getGridParam", "selarrrow");
        //$(this).before('<div class="ui-paging-info ui-paging-selected-info" style="float:left" dir="ltr">' + idsOfSelectedRows.length + ' of selected item</div>');
        $(this).before('<div class="ui-paging-info ui-paging-selected-info" style="float:left" dir="ltr"> ' +
            //'已选择 ' + idsOfSelectedRows.length + ' 条' +
            '</div>');
    });
}

/**
 * Description: set top nav width
 * Date: 2015-06-10
 */
function doSetGridNavLayout(gridSelector) {
    var _left = $(gridSelector + '_toppager_left'), _right = $(gridSelector + '_toppager_right');
    var _table = _left.find('table.ui-pg-table');
    if (_left.width() < _table.width()) {
        var _right_width = _right.width() - (_table.width() - _left.width());
        if (_right_width < 0) {
            _right_width = 1;
        } else {
            _right_width = Math.floor(_right_width);
        }
        _right.css('width', _right_width + 'px');
    }
}

/**
 * Description: Set JqGrid Column Header height
 * Date: 2014-10-26
 */
function setColumnHeaderHeight(gridSelector) {
    var headerRow = $(gridSelector).closest("div.ui-jqgrid-view")
        .find("table.ui-jqgrid-htable>thead>tr.ui-jqgrid-labels");

    // increase the height of the resizing span
    var resizeSpanHeight = 'height: ' + headerRow.height() +
        'px !important; cursor: col-resize;';
    headerRow.find("span.ui-jqgrid-resize").each(function () {
        this.style.cssText = resizeSpanHeight;
    });

    // set position of the dive with the column header text to the middle
    var rowHight = headerRow.height();
    headerRow.find("div.ui-jqgrid-sortable").each(function () {
        var ts = $(this);
        ts.css('top', (rowHight - ts.outerHeight()) / 2 + 'px');
    });
}

var modifySearchingFilter = function (separator) {
    var i, l, rules, rule, parts, j, group, str, iCol, filters,
        cmi, cm = this.p.colModel,
        postData = this.p.postData;
    if (postData && typeof postData.filters !== 'undefined' && postData.filters.length > 0) {
        filters = $.parseJSON(postData.filters);
        if (filters && typeof filters.rules !== 'undefined' && filters.rules.length > 0) {
            rules = filters.rules;
            for (i = 0; i < rules.length; i++) {
                rule = rules[i];
                iCol = getColumnIndexByName($(this), rule.field);
                cmi = cm[iCol];
                if (iCol >= 0 && $.isArray(rule.data)) {
                    rule.data = rule.data.join(',');
                }
            }
            this.p.postData.filters = JSON.stringify(filters);
        }
    }
};

function updateMultiselectHeaderIcon(uiMultiselectMenu) {
    var replacement =
    {
        'ui-icon-check': 'icon-ok',
        'ui-icon-closethick': 'icon-remove',
        'ui-icon-circle-close': 'icon-remove-sign'
    };
    uiMultiselectMenu.find('span.ui-icon').each(function () {
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
        if ($class in replacement) icon.attr('class', replacement[$class]);
    });
}

function updateMultiselectMenuIcon(uiMultiselectMenu) {
    uiMultiselectMenu.find('span.ui-icon').each(function () {
        var icon = $(this);
        icon.attr('class', 'icon-caret-down pull-right');
        icon.html('&nbsp;&nbsp;');
    });
}
/*jqGrid Search Multiselect*/
var dataInitMultiselect = function (elem) {
    setTimeout(function () {
        var $elem = $(elem),
            options = {
                selectedList: 4,
                minWidth: 100,
                height: "auto",
                open: function () {
                    var $menu = $(".ui-multiselect-menu:visible");
                    updateMultiselectHeaderIcon($menu);
                    return;
                }
            };
        $elem.multiselect(options);
        updateMultiselectMenuIcon($elem.siblings('button.ui-multiselect'));
    }, 150);
};

/* jqGrid Search Datepicker */
var dataInitDatepicker = function (elem) {
    $elem = $(elem);
    $elem.wrap('<span class="input-icon input-icon-right"/>');
    $elem.after('<i class="icon-calendar green">&nbsp;&nbsp;</i>');
    $(elem).datepicker({format: formatter.jqDate, autoclose: true});
}

var dataInitPopupSelect = function (elem, params) {
    if (params == undefined || (params != undefined && params.fnName == undefined)) {
        return {};
    }
    $elem = $(elem);
    $elem.attr('readonly', (params.readonly == undefined ? 'false' : params.readonly));
    $elem.wrap('<span class="input-icon input-icon-right"/>');
    $elem.after('<i class="icon-search blue" onclick="' + params.fnName + '(\'' + elem.id + '\');return false;">&nbsp;&nbsp;</i>');
}

var cascadeSelectForEditOptions = function (params) {
    $editoptions = {};
    $editoptions.value = function () {
        return matrix.returnJsonData({url: params.ownerUrl, async: false});
    }
    $editoptions.dataInit = function (elem) {
        var groupId = $(elem).val();
        var rowId = jQuery("#" + params.gridName).jqGrid("getGridParam", "selrow");
        var cascadeValue = jQuery("#" + params.gridName).jqGrid("getCell", rowId, params.cascadeProperty);
        jQuery("#" + params.gridName).setColProp(params.cascadeProperty, {
            editoptions: {
                dataUrl: params.cascadeUrl + groupId,
                buildSelect: function (data) {
                    var response, s = '<select>', i, l, value, label;
                    if (typeof (data) === "string") {
                        response = jQuery.parseJSON(data);
                    }
                    else {
                        response = jQuery.parseJSON(data.responseText);
                    }
                    s += '<option value="">---Please Select---</option>';
                    $.each(response, function (key, value) {
                        if (value == cascadeValue) {
                            s += '<option value="' + value + '" selected>' + key + '</option>';
                        } else {
                            s += '<option value="' + value + '">' + key + '</option>';
                        }
                    })
                    return s + '</select>';
                }
            }
        });
    }
    $editoptions.dataEvents = [
        {
            type: 'change',
            fn: function (e) {
                var ownerValue = $(e.target).val();
                if (ownerValue !== '') {
                    $.getJSON(params.cascadeUrl + ownerValue, function (data) {
                        var rowId = jQuery("#" + params.gridName).jqGrid("getGridParam", "selrow");
                        var recipeSelect = $("#" + rowId + "_" + params.cascadeProperty);
                        var value = recipeSelect.val();
                        recipeSelect.empty();
                        recipeSelect.append('<option value="">---Please Select---</option>');
                        $.each(data, function (key, value) {
                            recipeSelect.append($('<option/>', {
                                value: value,
                                text: key
                            }));
                        });
                        recipeSelect.val(value);
                    });
                }
            }
        }
    ]
    return $editoptions;
};

/**
 * build select from server
 * params: data = return json from server
 */
var jqGridBuildSelect = function (data) {
    var response, s = '<select>', i, l, value, label;
    if (typeof (data) === "string") {
        response = jQuery.parseJSON(data);
    }
    else {
        response = jQuery.parseJSON(data.responseText);
    }
    s += '<option value=""></option>';
    $.each(response, function (key, value) {
        if (value == '') {
            s += '<option value="' + key + '" selected>' + value + '</option>';
        } else {
            s += '<option value="' + key + '">' + value + '</option>';
        }
    })
    return s + '</select>';
}
/**
 * if value exist in jgrid column
 * @param gridId = jqgrid table id
 * @param columnName = jqgrid column name
 * @param value = value
 * @returns {boolean} = true / false
 */
var valueExistInJqGridColumn = function (gridId, columnName, value) {
    var _result = false;
    var _valueList = $('#' + gridId).jqGrid('getCol', columnName, true);
    if (_valueList.length > 0) {
        for (var i = 0; i < _valueList.length; i++) {
            var _value = _valueList[i].value;
            if (_value != '' && _value != undefined && _value == value) {
                _result = true;
                break;
            }
        }
    }
    return _result;
}

var valueExistInJqGridEditColumn = function (tableId, rowId, name, value) {
    var _result = false;
    $('#' + tableId).find('*[name=' + name + ']').each(function () {
        var _value = $(this).val();
        var _rowId = $(this).closest('tr.jqgrow').attr('id');
        if (_rowId != rowId && _value == value) {
            _result = true;
            return false;
        }
    });
    return _result;
}

var setValueToJqGridEditColumn = function (tableId, rowId, name, value) {
    $('#' + tableId).find('*[name=' + name + ']').each(function () {
        var _rowId = $(this).closest('tr.jqgrow').attr('id');
        if (_rowId == rowId) {
            $(this).val(value);
            return false;
        }
    });
}
var getValueToJqGridEditColumn = function (tableId, rowId, name) {
    var _value = "";
    $('#' + tableId).find('*[name=' + name + ']').each(function () {
        var _rowId = $(this).closest('tr.jqgrow').attr('id');
        if (_rowId == rowId) {
            _value = $(this).val();
            return false;
        }
    });
    return _value;
}

//JqGrid Sort By Arrow
var doSortByArrow = function (gridid, rowid, isUp) {
    var $grid = $("#" + gridid);
    var grid_data = $grid.jqGrid("getRowData");
    var ids = $grid.getDataIDs();
    var currentIndex = $.inArray(rowid, ids);
    var cascadeIndex = currentIndex + (isUp ? -1 : 1);
    var records = $grid.getGridParam("records");
    var page = $grid.getGridParam("page");
    var pageSize = $grid.getGridParam("rowNum");
    var sortName = $grid.getGridParam("sortname");
    var total = parseInt((records == null ? 0 : parseInt(records)) / (pageSize == null ? jqgridConstants.rowNum : parseInt(pageSize))) + 1;
    if ((isUp && currentIndex > 0) || ((!isUp) && (currentIndex < records))) {
        var currentData = grid_data[currentIndex];
        var cascadeData = grid_data[cascadeIndex];
        grid_data[currentIndex] = cascadeData;
        grid_data[cascadeIndex] = currentData;
        var reader = {
            root: function (obj) {
                return grid_data;
            },
            page: function (obj) {
                return (page == null ? 1 : parseInt(page));
            },
            total: function (obj) {
                return (total == null ? 1 : parseInt(total));
            },
            records: function (obj) {
                return (records == null ? 0 : parseInt(records));
            },
            repeatitems: false
        };
        $grid.jqGrid('clearGridData').jqGrid('setGridParam', {
            sortname: sortName,
            datatype: 'local',
            data: grid_data,
            rowNum: grid_data.length,
            localReader: reader
        }).trigger('reloadGrid');

        $grid.jqGrid('setGridParam', {
            sortname: 'id',
            datatype: 'json',
            data: [],
            rowNum: pageSize
        });
    }
}

/**
 * Author: Jack
 * Date: 2014-10-23
 * Description: bind jqgrid table data to form property
 */
var doBindDataToForm = function (options) {
    var defaults = {
        gridId: '',
        formId: '',
        bindingName: '',
        properties: []
    }
    var settings = $.extend({}, defaults, options);

    //remove input dom
    $('#' + settings.formId).find('input[name^=' + settings.bindingName + ']').each(function () {
        $(this).remove();
    });
    //bind data to form
    $('#' + settings.gridId).find("tr").not(':first').each(function (index, item) {
        var obj = $('#' + settings.gridId).getRowData(this.id);
        if (settings.properties.length > 0) {
            for (var i = 0; i < settings.properties.length; i++) {
                var property = settings.properties[i];
                var value = obj[property] == undefined ? '' : obj[property];
                $('#' + settings.formId).append('<input name=' + settings.bindingName + '[' + index + '].' + property + ' class="hide"  value="' + value + '"/>');
            }
        } else {
            for (var property in obj) {
                if (property != undefined && property != 'act') {
                    var value = obj[property] == undefined ? '' : obj[property];
                    $('#' + settings.formId).append('<input name=' + settings.bindingName + '[' + index + '].' + property + ' class="hide"  value="' + value + '"/>');
                }
            }
        }
    });
}

var doBindDataToFormExtend = function (options) {
    var defaults = {
        gridId: '',
        formId: '',
        bindingName: '',
        properties: []
    }
    var settings = $.extend({}, defaults, options);

    //remove input dom
    $('#' + settings.formId).find('input[name^=' + settings.bindingName + ']').each(function () {
        $(this).remove();
    });
    //bind data to form
    $('#' + settings.gridId).find("tr").not(':first').each(function (index, item) {
        var rowId = $(this).attr('id');
        var obj = $('#' + settings.gridId).getRowData(this.id);
        if (settings.properties.length > 0) {
            for (var i = 0; i < settings.properties.length; i++) {
                var property = settings.properties[i];
                var propertyName = property.name, propertyIsEdit = (property.isEdit == undefined ? false : property.isEdit);
                var value = "";
                if (propertyIsEdit) {
                    value = getValueToJqGridEditColumn(settings.gridId, rowId, propertyName);
                } else {
                    value = obj[propertyName] == undefined ? '' : obj[propertyName];
                }
                $('#' + settings.formId).append('<input name=' + settings.bindingName + '[' + index + '].' + propertyName + ' class="hide"  value="' + value + '"/>');
            }
        }
    });
}

function isOutOfDate(dueDate) {
    if (dueDate == undefined || dueDate == "") {
        return false;
    }
    var now = new Date();
    dueDate = dueDate.replace(/-/g, "/");
    var dueDate2 = new Date(Date.parse(dueDate));
    return dueDate2 < now;
}

/**
 * Return true / false , result is true if column value not equal property value, else false.
 * @param tableName: table name
 * @param propertyName: column name
 * @param propertyValue, column value , array, e.g ['A', 'B']
 * @returns {boolean} = true / false
 */
var uncheckedJqGridSelectRow = function (tableName, propertyName, propertyValue) {
    var _result = false;
    var _remove_ids = [];
    var _remove_data = [];
    for (var i = 0; i < idsOfSelectedRows.length; i++) {
        var _current_value = dataOfSelectedRows[i][propertyName];
        if ($.inArray(_current_value, propertyValue) == -1) {
            $('#' + idsOfSelectedRows[i]).find('td').css("color", "red");
            $('#' + idsOfSelectedRows[i]).find('td').find('input[id^="jqg_' + tableName + '_"]').removeAttr("checked");
            _remove_ids.push(i);
            _remove_data.push(i);
            _result = true;
        }
    }

    if (_remove_ids.length && _remove_ids.length > 0) {
        var removeIds = _remove_ids.reverse();
        var removeRowData = _remove_data.reverse();
        for (var j = 0; j < removeIds.length; j++) {
            idsOfSelectedRows.splice(removeIds[j], 1);
            dataOfSelectedRows.splice(removeRowData[j], 1);
        }
        doShowSelectedCount('#' + tableName);
    }
    if (_result) {
        return _result;
    }
    return false;
}
