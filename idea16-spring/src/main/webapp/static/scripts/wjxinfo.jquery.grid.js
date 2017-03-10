/**
 *  Jquery Grid Generic
 */
$(function () {
    $.fn.serializeJSON = function () {
        var json = {};
        jQuery.map($(this).serializeArray(), function (n, i) {
            (json[n['name']] === undefined) ? json[n['name']] = n['value'] : json[n['name']] += ',' + (n['value']).escapeSpecialChars();
        });
        return json;
    };

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
 * Narbar Setting
 *
 */

var gridSearchForm_afterShowSearch = function (e) {
    var form = $(e[0]);
    form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
    style_search_form(form);
};

// N/A
//var gridSearchForm_onSearch = function (gridSelector) {
//    var $filter = $("#" + $.jgrid.jqID("fbox_" + this.id)),
//        filterSql = $filter.jqFilter('toSQLString');
//
//    jQuery(gridSelector).jqGrid('setGridParam', {
//        postData: {filterSql: filterSql}
//    }).trigger("reloadGrid");
//};

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

function refreshSearchButtonIcon(gridSelector, pagerSelector) {
    var fts = $(gridSelector).getGridParam("postData").filters;
    var contains = false;
    if (fts !== undefined) {
        contains = (fts.indexOf('field') > -1);
    }

    if (contains == true) {
        $(pagerSelector).find('.navtable > tbody > tr > .ui-pg-button >').find('.icon-search').each(function () {
            var icon = $(this);
            icon.attr('class', 'ui-icon icon-search red');
        })
    }
    else {
        $(pagerSelector).find('.navtable > tbody > tr > .ui-pg-button >').find('.icon-search').each(function () {
            var icon = $(this);
            icon.attr('class', 'ui-icon icon-search grey');
        })
    }
}

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


function set_nav_grid_readonly(gridSelector, pagerSelector) {
    set_nav_grid_customize(gridSelector, pagerSelector,
        {editable: false, addable: false, delable: false, searchable: true, viewable: true, refreshable: true}
    );
}

function set_nav_grid(gridSelector, pagerSelector) {
    set_nav_grid_customize(gridSelector, pagerSelector,
        {editable: true, addable: true, delable: true, searchable: true, viewable: true, refreshable: true}
    );
}

function set_nav_grid_customize(gridSelector, pagerSelector, buttonoptions) {
    var _searchicon = getNavSearchClass(gridSelector);

    jQuery(gridSelector).jqGrid('navGrid', pagerSelector,
        { 	//navbar options
            edit: buttonoptions.editable,
            editicon: 'icon-pencil blue',
            add: buttonoptions.addable,
            addicon: 'icon-plus-sign purple',
            del: buttonoptions.delable,
            delicon: 'icon-trash red',
            search: buttonoptions.searchable,
            searchicon: _searchicon,
            refresh: buttonoptions.refreshable,
            refreshicon: 'icon-refresh green',
            afterRefresh: function () {
                refreshSearchButtonIcon(gridSelector, pagerSelector)
            },
            view: buttonoptions.viewable,
            viewicon: 'icon-zoom-in grey'
        },
        {
            //edit record form
            closeAfterEdit: true,
            recreateForm: true,
            beforeShowForm: gridEditForm_beforeShowForm,
            afterSubmit: gridShowMessage_afterSubmit
        },
        {
            //new record form
            closeAfterAdd: true,
            recreateForm: true,
            viewPagerButtons: false,
            beforeShowForm: gridCreateForm_beforeShowForm,
            afterSubmit: gridShowMessage_afterSubmit
        },
        {
            //delete record form
            recreateForm: true,
            beforeShowForm: gridDeleteForm_beforeShowForm,
            onClick: function (e) {
                alert(1);
            },
            afterSubmit: gridShowMessage_afterSubmit
        },
        {
            //search form
            overlay: false,
            closeOnEscape: true,
            recreateForm: true,
            closeAfterSearch: true,
            onSearch: function () {
                modifySearchingFilter.call($(gridSelector)[0], ',');
            },
            afterShowSearch: gridSearchForm_afterShowSearch,
            afterRedraw: function () {
                style_search_filters($(this));
            },
            onClose: function () {
                //remove other search criteria
                refreshSearchButtonIcon(gridSelector, pagerSelector);
                return true;
            },
            multipleSearch: true,
            multipleGroup: true,
            //showQuery: true,
            recreateFilter: true
        },
        {
            //view record form
            recreateForm: true,
            beforeShowForm: gridViewForm_beforeShowForm
        }
    );
}

// Add Custom Button

function addNavSeparator(gridSelector, pagerSelector) {
    jQuery(gridSelector)
        .navSeparatorAdd(pagerSelector, {sepclass: 'ui-separator', sepcontent: '', position: "last"});
}

function addCustomNavButton(gridSelector, pagerSelector, options) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: options.caption !== undefined ? options.caption : "",
            title: options.title,
            buttonicon: options.buttonicon,
            onClickButton: options.onClick,
            //function () {

            // location.href = url;
            // "${request.contextPath}/${params.controller}/create";
            //},
            position: "last"
        });
}

function addCustomBackNavButton(gridSelector, pagerSelector, url) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_back'),
            buttonicon: "ui-icon icon-arrow-left blue",
            onClickButton: function () {
                location.href = url;
            },
            position: "last"
        });
}

function addCustomItemSelectedNavButton(gridSelector, pagerSelector, options) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: "Items Selected",
            buttonicon: "ui-icon fam-tick blue",
            onClickButton: function () {
                showItemSelected({
                        name: typeof (options.name) !== 'undefined' ? options.name : options.gridName,
                        gridName: options.gridName
                    }
                );
            },
            position: "last"
        })
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: "Add to Exclusion List",
            buttonicon: "ui-icon fam-ruby-add purple",
            onClickButton: function () {
                addToExclusion(gridSelector, {
                        name: typeof (options.name) !== 'undefined' ? options.name : options.gridName,
                        displayColumns: options.displayColumns,
                        gridName: options.gridName
                    }
                );
            },
            position: "last"
        })
    ;
}


function addCustomCreateNavButton(gridSelector, pagerSelector, url) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_create'),
            buttonicon: "ui-icon icon-plus-sign purple",
            onClickButton: function () {
                location.href = url;
                //"${request.contextPath}/${params.controller}/create";
            },
            position: "last"
        })
    ;
}
function addCustomCreateNavButtonWithParam(gridSelector, pagerSelector, url, jsonParam, target, title) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_create'),
            buttonicon: "ui-icon icon-plus-sign purple",
            onClickButton: function () {
                matrix.ajaxJsp({
                    name: title,
                    url: url,
                    params: jsonParam,
                    target: target
                });
            },
            position: "last"
        })
    ;
}
function addCustomGridSettingNavButton(gridSelector, pagerSelector, userSetting) {
    myColumnStateName = userSetting.userName + ":"
        + userSetting.settingType + ":"
        + userSetting.settingName + ":"
        + userSetting.settingTag + ":"
        + userSetting.revampNo;

    jQuery(gridSelector)
        .navSeparatorAdd(pagerSelector, {sepclass: 'ui-separator', sepcontent: '', position: "last"})

        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_show_hide_column'),
            buttonicon: "ui-icon fam-application-view-columns green",
            onClickButton: function () {
                //$(this).jqGrid('columnChooser');
                $(this).jqGrid('columnChooser', {
                    done: function (perm) {
                        if (perm) {
                            this.jqGrid("remapColumns", perm, true);
                            // Manual - saveColumnState.call(this, perm);
                        }
                    }
                });
                //process column
                $("#colchooser_" + $.jgrid.jqID(this.id) + " ul.available li.ui-state-default").each(function(index,item){
                    var _title = $(this).attr('title');
                    var _colNames = $(gridSelector).jqGrid('getGridParam', 'colNames'), _colModel = $(gridSelector).jqGrid('getGridParam', 'colModel'), i, l = _colNames.length, _colName, _colItem;
                    for(i = 0; i < l; i++){
                        _colName = _colNames[i];
                        _colItem = _colModel[i];
                        if (_colName == _title && _colItem.hidden && _colItem.hidedlg){
                            $(this).remove();
                        }
                    }
                });
            },
            position: "last"
        })

        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_save_setting'),
            buttonicon: "ui-icon fam-star",
            onClickButton: function () {
                saveColumnState.call($(this), this.p.remapColumns, userSetting);
            },
            position: "last"
        })

        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_clear_setting'),
            buttonicon: "ui-icon fam-award-star-delete red",
            onClickButton: function () {
                removeObjectFromLocalStorage(userSetting);
                window.location.reload();
            },
            position: "last"
        })

    ;
}

function addCustomGridExportNavButton(gridSelector, pagerSelector, url) {
    jQuery(gridSelector)
        .navSeparatorAdd(pagerSelector, {sepclass: 'ui-separator', sepcontent: '', position: "last"})

        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_export_excel'),
            buttonicon: "ui-icon fam-page-excel",
            onClickButton: function () {
                exportToFile.call($(this), url, 'excel', '-1', null);
            },
            position: "last"
        })

        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_export_pdf'),
            buttonicon: "ui-icon fam-page-white-acrobat",
            onClickButton: function () {
                exportToFile.call($(this), url, 'pdf', '-1', null);
            },
            position: "last"
        })
    ;
}

function addCustomGridExportExcelNavButton(gridSelector, pagerSelector, url) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_export_excel'),
            buttonicon: "icon-download-alt",
            onClickButton: function () {
                var _selected_ids = $(gridSelector).jqGrid("getGridParam", "selarrrow");
                if (_selected_ids.length && _selected_ids.length > 0) {
                    //process filter
                    $(gridSelector).jqGrid("setGridParam", "postData", addFilterToJqGridFilter([
                        {
                            fieldName: 'id',
                            op: 'in',
                            value: _selected_ids.join(',')
                        }
                    ]));
                    exportToFile.call($(this), url, 'excel', '-1', null);
                } else {
                    matrix.showError(i18nQuery('no_selected_row'));
                }
            },
            position: "last"
        });
}

function addCustomGridExportExcelForSelectedNavButton(gridSelector, pagerSelector, url, gridName) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_selected_export_excel'),
            buttonicon: "icon-download-alt",
            onClickButton: function () {
                exportToFile.call($(this), url, 'excel', '0', gridName);
            },
            position: "last"
        });
}

function addCustomGridExportPDFNavButton(gridSelector, pagerSelector, url) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_export_pdf'),
            buttonicon: "ui-icon fam-page-white-acrobat",
            onClickButton: function () {
                var _selected_ids = $(gridSelector).jqGrid("getGridParam", "selarrrow");
                if (_selected_ids.length && _selected_ids.length > 0) {
                    //process filter
                    $(gridSelector).jqGrid("setGridParam", "postData", addFilterToJqGridFilter([
                        {
                            fieldName: 'id',
                            op: 'in',
                            value: _selected_ids.join(',')
                        }
                    ]));
                    exportToFile.call($(this), url, 'pdf', '-1', null);
                } else {
                    matrix.showError(i18nQuery('no_selected_row'));
                }
            },
            position: "last"
        });
}

function addCustomGridExportPDFForSelectedNavButton(gridSelector, pagerSelector, url, gridName) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_selected_export_pdf'),
            buttonicon: "ui-icon fam-page-white-acrobat",
            onClickButton: function () {
                exportToFile.call($(this), url, 'pdf', '0', gridName);
            },
            position: "last"
        });
}

/**
 * Export to file
 * @param url
 * @param fileType: e.g-->excel, pdf
 * @param isDirection: e.g--> 1--select all, 0 -- exclusion select, -1 -- ignore
 * @param gridName: e.g-->userSetting.settingName
 */
function exportToFile(url, fileType, isDirection, gridName) {
    var colNames = this.jqGrid('getGridParam', 'colNames'), i, l = colNames.length, colTitle,
        colModel = this.jqGrid('getGridParam', 'colModel'), colItem, cmName,
        postData = this.jqGrid('getGridParam', 'postData'),
        columnsState = {
            fileType: fileType,
            isDirection: isDirection,
            gridName: (gridName == null ? '' : gridName),
            caption: this.jqGrid('getGridParam', 'caption'),
            _search: this.jqGrid('getGridParam', 'search'),
            page: this.jqGrid('getGridParam', 'page'),
            rows: (this.jqGrid('getGridParam', 'rows') == null ? '10000' : this.jqGrid('getGridParam', 'rows')),
            sidx: this.jqGrid('getGridParam', 'sortname'),
            sord: this.jqGrid('getGridParam', 'sortorder'),
            fields: {}
        },
        colStates = columnsState.fields;

    if (typeof (postData.filters) !== 'undefined') {
        columnsState.filters = postData.filters;
    }
    var fieldStrings = "";
    for (i = 0; i < l; i++) {
        colTitle = colNames[i];
        colItem = colModel[i];
        cmName = colItem.name;
        if (colTitle != '' && cmName != 'act' && cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
            fieldStrings += (fieldStrings.length == 0 ? "" : ",") + '{\"name\":\"' + cmName + '\",\"title\":\"' + colTitle + '\",\"hidden\":\"' + colItem.hidden + '\",\"width\":\"' + colItem.width + '\"}';
        }
    }
    columnsState.fields = '[' + fieldStrings + ']';
    ajaxExport(url, JSON.stringify(columnsState));
}

function ajaxExport(url, data) {
    var form = $("<form>");
    form.attr('style', 'display:none');
    form.attr('target', '');
    form.attr('method', 'post');
    form.attr('action', url);

    var inputData = $('<input>');
    inputData.attr('type', 'hidden');
    inputData.attr('name', 'data');
    inputData.attr('value', data);

    $('body').append(form);
    form.append(inputData);
    form.submit();
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
            $(this).css({ "vertical-align": "middle"});
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

function renderActionColumn(grid, columnName, options) {
    var iCol = getColumnIndexByName(grid, columnName);
    grid.children("tbody")
        .children("tr.jqgrow")
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

/**
 Save grid setting
 */
function saveObjectInLocalStorage(object, userSetting) {
    storageItemName = userSetting.userName + ":"
        + userSetting.settingType + ":"
        + userSetting.settingName + ":"
        + userSetting.settingTag + ":"
        + userSetting.revampNo;

    if (typeof window.localStorage !== 'undefined') {
        window.localStorage.setItem(storageItemName, JSON.stringify(object));
    }
    // Save setting to DB
    saveUserSetting(JSON.stringify(object), userSetting);
}

function removeObjectFromLocalStorage(userSetting) {
    storageItemName = userSetting.userName + ":"
        + userSetting.settingType + ":"
        + userSetting.settingName + ":"
        + userSetting.settingTag + ":"
        + userSetting.revampNo;

    if (typeof window.localStorage !== 'undefined') {
        window.localStorage.removeItem(storageItemName);
    }

    removeUserSetting(userSetting);
}

function getObjectFromLocalStorage(userSetting) {
    var data = retrieveUserSetting(userSetting);

    if (data != '' && data != 'null' && data != null) {
        return JSON.parse(data);
    }

    //    storageItemName = userSetting.userName + ":"
    //        + userSetting.settingType + ":"
    //        + userSetting.settingName + ":"
    //        + userSetting.settingTag + ":"
    //        + userSetting.revampNo;

    //    if (typeof window.localStorage !== 'undefined') {
    //        var obj = window.localStorage.getItem(storageItemName);
    //        //alert(obj);
    //        //alert(JSON.parse(obj));
    //        return JSON.parse(window.localStorage.getItem(storageItemName));
    //    }
}

function saveColumnState(perm, userSetting) {
    var colModel = this.jqGrid('getGridParam', 'colModel'), i, l = colModel.length, colItem, cmName,
        postData = this.jqGrid('getGridParam', 'postData'),
        columnsState = {
            search: this.jqGrid('getGridParam', 'search'),
            page: this.jqGrid('getGridParam', 'page'),
            sortname: this.jqGrid('getGridParam', 'sortname'),
            sortorder: this.jqGrid('getGridParam', 'sortorder'),
            permutation: perm,
            colStates: {}
        },
        colStates = columnsState.colStates;

    if (typeof (postData.filters) !== 'undefined') {
        columnsState.filters = postData.filters;
    }

    for (i = 0; i < l; i++) {
        colItem = colModel[i];
        cmName = colItem.name;
        if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
            colStates[cmName] = {
                width: colItem.width,
                hidden: colItem.hidden
            };
        }
    }
    saveObjectInLocalStorage(columnsState, userSetting);
}

function restoreColumnState(colModel, userSetting) {
    var colItem, i, l = colModel.length, colStates, cmName,
        columnsState = getObjectFromLocalStorage(userSetting);

    if (columnsState) {
        colStates = columnsState.colStates;
        for (i = 0; i < l; i++) {
            colItem = colModel[i];
            cmName = colItem.name;
            if (cmName !== 'rn' && cmName !== 'cb' && cmName !== 'subgrid') {
                colModel[i] = $.extend(true, {}, colModel[i], colStates[cmName]);
            }
        }
    }
    return columnsState;
}

// Ajax Save User Setting to DB
function saveUserSetting(data, userSetting) {
    var dataString = 'settingType=' + userSetting.settingType
            + '&settingName=' + userSetting.settingName
            + '&settingTag=' + userSetting.settingTag
            + '&revampNo=' + userSetting.revampNo
            + '&data=' + data
        ;

    $.ajax({
        type: "POST",
        url: userSettingSaveEndpoint,
        data: dataString,
        success: function (res) {
            //growl('success', res);
            $.gritter.add({
                title: '<i class=\'icon-info-sign bigger-120 blue\'></i>  ' + i18nQuery('label_message'),
                text: res,
                class_name: 'gritter-info gritter-light'
            });
        },
        error: function (xhr, ajaxOptions, thrownError) {
            growl('error', xhr.responseText);
        }
    });
}

// Retrieve User Setting from DB
function retrieveUserSetting(userSetting) {
    var dataString = 'settingType=' + userSetting.settingType
            + '&settingName=' + userSetting.settingName
            + '&settingTag=' + userSetting.settingTag
            + '&revampNo=' + userSetting.revampNo
        ;

    var setting = "";

    $.ajax({
        type: "POST",
        async: false,
        url: userSettingRetrieveEndpoint,
        data: dataString,
        success: function (res) {
            setting = res;
        },
        error: function (xhr, ajaxOptions, thrownError) {
            /*growl('error', xhr.responseText);*/
        }
    });

    return setting;
}

// Remove User Setting from DB
function removeUserSetting(userSetting) {
    var dataString = 'settingType=' + userSetting.settingType
            + '&settingName=' + userSetting.settingName
            + '&settingTag=' + userSetting.settingTag
            + '&revampNo=' + userSetting.revampNo
        ;

    $.ajax({
        type: "POST",
        async: false,
        url: userSettingDeleteEndpoint,
        data: dataString,
        success: function (res) {
            $.gritter.add({
                title: '<i class=\'icon-info-sign bigger-120 blue\'></i>  ' + i18nQuery('label_message'),
                text: res,
                class_name: 'gritter-info gritter-light'
            });
        },
        error: function (xhr, ajaxOptions, thrownError) {
            growl('error', xhr.responseText);
        }
    });
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
    }
    else {
        gconf.userSetting = {
            userName: _userName,
            settingType: "jqgrid",
            settingName: "general",
            settingTag: 'default',
            revampNo: '1',
            localStorage: false
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
    gconf.myColumnsState = restoreColumnState(gconf.colModel, gconf.userSetting);
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
        url: typeof (gconf.url) !== 'undefined' ? gconf.url : "",
        editurl: typeof (gconf.editurl) !== 'undefined' ? gconf.editurl : "",
        datatype: typeof (gconf.datatype) !== 'undefined' ? gconf.datatype : "json",
        data: typeof (gconf.data) !== 'undefined' ? gconf.data : [],
        mtype: typeof (gconf.mtype) !== 'undefined' ? gconf.mtype : "POST",
        height: typeof (gconf.height) !== 'undefined' ? gconf.height : 'auto',
        width: typeof (gconf.width) !== 'undefined' ? gconf.width : 'auto',
        autowidth: typeof (gconf.autowidth) !== 'undefined' ? gconf.autowidth : true,
        shrinkToFit: typeof (gconf.shrinkToFit) !== 'undefined' ? gconf.shrinkToFit : false,
        autoScroll: typeof (gconf.autoScroll) !== 'undefined' ? gconf.autoScroll : false,
        footerrow: typeof (gconf.footerrow) !== 'undefined' ? gconf.footerrow : false,
        viewrecords: typeof (gconf.viewrecords) !== 'undefined' ? gconf.viewrecords : jqgridConstants.viewrecords,
        rowNum: typeof (gconf.rowNum) !== 'undefined' ? gconf.rowNum : jqgridConstants.rowNum,
        rowList: typeof (gconf.rowList) !== 'undefined' ? gconf.rowList : jqgridConstants.rowList,
        pager: typeof (gconf.pagerSelector) !== 'undefined' ? gconf.pagerSelector : 'pager',
        altRows: typeof (gconf.altRows) !== 'undefined' ? gconf.altRows : true,
        multiselect: typeof (gconf.multiselect) !== 'undefined' ? gconf.multiselect : true,
        //multikey: "ctrlKey",
        multiboxonly: typeof (gconf.multiboxonly) !== 'undefined' ? gconf.multiboxonly : true,
        hidegrid: typeof (gconf.hidegrid) !== 'undefined' ? gconf.hidegrid : true,
        rownumbers: false,
        toppager: gconf.bNavBarInTop,
        pgbuttons: typeof (gconf.pgbuttons) !== 'undefined' ? gconf.pgbuttons : true,
        pginput: typeof (gconf.pginput) !== 'undefined' ? gconf.pginput : true,

        gridview: typeof (gconf.gridview) !== 'undefined' ? gconf.gridview : true,
        page: gconf.isColState ? gconf.myColumnsState.page : 1,
        search: gconf.isColState ? gconf.myColumnsState.search : false,
        postData: gconf.isColState ? { filters: gconf.myColumnsState.filters } : (typeof (gconf.postData) !== 'undefined' ? gconf.postData : {}),
        sortname: gconf.isColState ? gconf.myColumnsState.sortname : gconf.sortname,
        sortorder: gconf.isColState ? gconf.myColumnsState.sortorder : gconf.sortorder,
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
        ExpandColClick: true,
        treeIcons: {
            plus: 'icon-folder-close purple tree-icon-middle', minus: 'icon-folder-open tree-icon-middle', leaf: 'icon-check-empty tree-icon-middle'
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

        loadComplete: function (xhr) {
            if (xhr != 'undefined' && xhr.userData != null && !xhr.userData.success && xhr.userData.msg != "") {
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
                    if (gconf.isColState) {
                        $(gconf.gridSelector).jqGrid("remapColumns", gconf.myColumnsState.permutation, true);
                        // $(this).jqGrid("remapColumns", gconf.myColumnsState.permutation, false);
                    }
                }

                //$(gconf.gridSelector).parents(".ui-jqgrid-bdiv").css("overflow-x","hidden");
            }, 0);

            //BUG: saveColumnState.call($(this), this.p.remapColumns);

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
    setColumnHeaderHeight(gconf.gridSelector);

    style_header_icon();
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

/*
 *  Item Selected: Select All - Un-Select All
 *
 */
function showItemSelected(params) {
    params['userId'] = _currentUserId;
    if (params['name'] == undefined) {
        params['name'] = params['gridName'];
    }

    $.ajax({
        type: "POST",
        url: _appContext + "/common/item/selected/ajax/index",
        data: params,
        //async: false,
        success: function (response) {

            if (!$('#select_all_win').length) {
                $("body").append("<div id='select_all_win'></div>");
            }
            $("#select_all_win").empty();
            $("#select_all_win").html(response);
        }
    });
}

function addToExclusion(gridSelector, params) {
    if (params['name'] == undefined) {
        params['name'] = params['gridName'];
    }

    var $grid = $(gridSelector);
    var selectedRows = $grid.jqGrid("getGridParam", "selarrrow");

    if (selectedRows.length) {
        for (i = 0; i < selectedRows.length; i++) {
            var selRowId = selectedRows[i];
            var celValue = "";

            $.each(params.displayColumns, function (key, val) {
                if (celValue != "") {
                    celValue = celValue + "; ";
                }
                celValue = celValue + jQuery(gridSelector).jqGrid('getCell', selRowId, key);
            });

            var rowDataJson = jQuery(gridSelector).getRowData(selRowId);
            delete rowDataJson["act"];

            var rowDataStr = JSON.stringify(rowDataJson);

            params['userId'] = _currentUserId;
            params['rowId'] = selRowId;
            params['displayData'] = celValue;
            params['rowData'] = rowDataStr;

            $.ajax({
                type: "POST",
                url: _appContext + "/common/item/selected/grid/exclude",
                data: params,
                async: false,
                success: function (response) {
                    var obj = jQuery.parseJSON(response);
                    if (obj.state == "OK") {
                        matrix.showMessage(obj.message);
                    }
                    else {
                        matrix.showError(obj.message);
                    }
                }
            });
        }
    }
    else {
        matrix.showError("No selected row");
        return false;
    }
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
        jQuery("#" + params.gridName).setColProp(params.cascadeProperty, { editoptions: {
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
        } });
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
    s += '<option value="">---Please Select---</option>';
    $.each(response, function (key, value) {
        if (value == '') {
            s += '<option value="' + value + '" selected>' + key + '</option>';
        } else {
            s += '<option value="' + value + '">' + key + '</option>';
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

function isOutOfDate(dueDate) {
    if (dueDate == undefined || dueDate == "") {
        return false;
    }
    var now = new Date();
    dueDate = dueDate.replace(/-/g, "/");
    var dueDate2 = new Date(Date.parse(dueDate));
    return dueDate2 < now;
}

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
    if (cellvalue == '1') {
        return "<span class='label label-sm label-success'>Enabled</span>";
    }
    else {
        return "<span class='label label-sm label-warning'>Disabled</span>";
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
        return "<span class='label label-sm label-success'>Yes</span>";
    }
    else {
        return "<span class='label label-sm label-warning'>No</span>";
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
            var userDisplayName = matrix.returnJsonData({url: _appContext + "/common/service/mas-user/" + cellvalue, async: false});
            return userDisplayName;
        }
    }
    return cellvalue;
};


var dateTemplate = {width: 100, sorttype: "date", formatter: "date",
    formatoptions: {srcformat: formatter.jqGridDateTime, newformat: formatter.jqGridDate},
    searchoptions: {searchhidden: true,dataInit: datePick, att: {title: 'Select Date'}, sopt: ['eq','lt', 'gt']},
    searchrules: {date: true}};

var datetimeTemplate = {width: 150, sorttype: "date", formatter: "date",
    formatoptions: {srcformat: formatter.jqGridDateTime, newformat: formatter.jqGridDateTime},
    searchoptions: {dataInit: function (elem) {
        $(elem).datetimepicker({
            dateFormat: 'yy-mm-dd',
            timeFormat: 'HH:mm:ss'
        });
    }, att: {title: 'Select Date'}, sopt: ['lt', 'gt']},
    searchrules: {date: true}};

var dateEditTemplate = { editoptions: {size: 10, maxlengh: 10, dataInit: datePick},
    formatoptions: {newformat: formatter.jqGridDate}
};

var timeEditTemplate = { editoptions: {size: 5, maxlengh: 5, dataInit: timePick}};

var stringTemplate = {searchoptions: {searchhidden: true, sopt: ['cn', 'bw','ew','eq','ne']}};

var stringExtendTemplate = {searchoptions: {searchhidden: true, sopt: ['cn','nc', 'bw','ew','eq','ne','lt','le','gt','ge','in','ni']}};

var numberTemplate = {formatter: 'number', align: 'right', sorttype: 'number', editable: true,
    searchoptions: { sopt: ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'nu', 'nn', 'in', 'ni'] }};

var integerTemplate = {formatter: 'integer', align: 'right', sorttype: 'integer', editable: true,
    editrules: {integer: true},
    searchoptions: { sopt: ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'nu', 'nn', 'in', 'ni'] }};

var IsInTemplate = {align: 'right', editable: true,
    searchoptions: { sopt: ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'nu', 'nn', 'in', 'ni'] }};

var statusTemplate = { width: 80, formatter: statusFormatter, unformat: unformatStatus,
    stype: "select",
    searchoptions: {sopt: ['eq', 'ne'], value: "Y:Enabled;N:Disabled"}};

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


var extendTemplate = {

}

var booleanTemplate = {formatter: booleanFormatter, unformat: unformatBoolean,
    stype: "select",
    searchoptions: {sopt: ['eq', 'ne'], value: "1:Yes;0:No"}};

var percentageTemplate = {formatter: percentageFormatter,
    searchrules: {number: true, minValue: 0, maxValue: 100},
    searchoptions: {sopt: ['eq', 'ne', 'lt', 'le', 'gt', 'ge']}
};

var onlineOfflineTemplate = {formatter: onlineOfflineFormatter, unformat: onlineOfflineUnformatter,
    stype: "select",
    searchoptions: {sopt: ['eq', 'ne'], value: "1:Online;0:Offline"}};

var arrowSortTemplate = {formatter: arrowSortFormatter, width: 50, editable: false, search: false, sortable: true}

var hkCurrencyTemplate = {formatter: hkCurrencyFormatter, unformat: hkCurrencyUnformatter, align: 'right', sorttype: 'number',
    searchoptions: { sopt: ['eq', 'ne', 'lt', 'le', 'gt', 'ge', 'nu', 'nn', 'in', 'ni'] }};

/*var checkboxTemplate = {width: 80, sortable: false, align: 'center',
    formatter: 'checkbox', stype: 'checkbox', edittype: "checkbox",
    editoptions: {value: "1:0"},
    editable: true, search: false
};*/
var checkboxTemplate = {
    width: 80, sortable: false, align: 'center',
    formatter: 'checkbox', stype: 'select', edittype: "checkbox",
    editoptions: {value: "1:0"}, search: true,
    searchoptions: {value: "1:Yes;0:No", sopt: ['eq', 'ne']},
    editable: true
};

