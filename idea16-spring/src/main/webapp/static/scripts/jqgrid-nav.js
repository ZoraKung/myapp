var selectedAllData = [];
var selectedAllIds = [];

// jqGrid Nav Button Config
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
                firstSearch = 'N';
                var _url = $(gridSelector).getGridParam('url');
                if (_url.indexOf('firstSearch') == -1) {
                    if (_url.indexOf('?') != -1) {
                        _url += '&firstSearch=N';
                    } else {
                        _url += '?firstSearch=N';
                    }
                }
                $(gridSelector).setGridParam({url: _url});
            },
            afterShowSearch: gridSearchForm_afterShowSearch,
            afterRedraw: function () {
                style_search_filters($(this));
            },
            beforeShowSearch: function(form){
                $(form).keydown(function(e) {
                    if (e.keyCode == 13) {
                        //trigger change event
                        $(e.target).change();
                        setTimeout(function() {
                            $('#fbox_' + gridSelector.substr(1, gridSelector.length - 1) + '_search').click();
                        }, 200);
                    }
                });
                return true;
            },
            onClose: function (form) {
                //bt: 11916
                $('#fbox_' + gridSelector.substr(1, gridSelector.length - 1) + '_search').unbind('keydown');
                //remove other search criteria
                refreshSearchButtonIcon(gridSelector, pagerSelector);
                return true;
            },
            multipleSearch: true,
            multipleGroup: true,
            //showQuery: true,
            recreateFilter: false,
            //bt: 11916
            savekey: [true, 13],
            //searchOnEnter: true,
            //bt: 12738
            groupOps: [
                { op: "AND", text: "AND" },
                { op: "OR", text: "OR" }
            ]
        },
        {
            //view record form
            recreateForm: true,
            beforeShowForm: gridViewForm_beforeShowForm
        }
    );
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

// jqGrid Custom Nav Button

function addNavSeparator(gridSelector, pagerSelector) {
    jQuery(gridSelector)
        .navSeparatorAdd(pagerSelector, {sepclass: 'ui-separator', sepcontent: '', position: "last"});
}

function isExistButtonInNav(pagerSelector, title) {
    var _result = false;
    $(pagerSelector).find('td.ui-pg-button').each(function () {
        if ($(this).attr('title') == title) {
            _result = true;
            return false;
        }
    });
    return _result;
}

function addCustomNavButton(gridSelector, pagerSelector, options) {
    if (!isExistButtonInNav(pagerSelector, options.title)) {
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

function addCustomCreateNavButton(gridSelector, pagerSelector, url) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_create'),
            buttonicon: "ui-icon icon-plus-sign purple",
            onClickButton: function () {
                location.href = url;
                /*  matrix.loading("请稍候...");
               $("body").load(encodeURI(url),function (responseTxt, statusTxt, xhr) {
                    if(statusTxt=="error")
                        matrix.showMessage("内容加载失败,请刷新重试!");
                    $('div.gritter-info.loading').remove();
                });*/
            },
            position: "last"
        })
    ;
}

function addCustomSwitchSearchNavButton(gridSelector, pagerSelector, url) {
    var _title = 'Switch to Extended View';
    var _buttonicon = 'ui-icon fam-table-add';
    if (advance == 'Y') {
        _title = 'Switch to Simplified View';
        _buttonicon = 'ui-icon fam-table-delete';
    }

    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
//            title: i18nQuery('button_switch'),
//            buttonicon: "ui-icon fam-table-delete",
            title: _title,
            buttonicon: _buttonicon,
            onClickButton: function () {
                if (advance == 'N') {
                    advance = 'Y';
                } else {
                    advance = 'N';
                }
                firstSearch = 'Y';
                location.href = url + "?advance=" + advance;
            },
            position: "last"
        });
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

/*
 *  Grid Setting
 *
 */
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
                            //bt: 11388
                            saveColumnState.call(this, perm, userSetting, true, true);
                        }
                        //set column header style
                        setColumnHeaderHeight(gridSelector);
                    }
                });
                //process column
                $("#colchooser_" + $.jgrid.jqID(this.id) + " ul.available li.ui-state-default").each(function (index, item) {
                    var _title = $(this).attr('title');
                    var _colNames = $(gridSelector).jqGrid('getGridParam', 'colNames'), _colModel = $(gridSelector).jqGrid('getGridParam', 'colModel'), i, l = _colNames.length, _colName, _colItem;
                    for (i = 0; i < l; i++) {
                        _colName = _colNames[i];
                        _colItem = _colModel[i];
                        if (((_colName == '' || _colName == 'Id') && (_colName == _title) && (_colItem.name == 'id') && _colItem.hidden) || (_colName == _title && _colItem.hidden && _colItem.hidedlg)) {
                            $(this).remove();
                        }
                    }
                });

                //Setup custom event bindings and give the right side an initial sort
                BindColPickerActions($.jgrid.jqID(this.id));
                SortColPickerAvailable($.jgrid.jqID(this.id));

                //bt:11388, add save setting button
                //addSaveSettingButtonInColumnChooser($.jgrid.jqID(this.id), this.p.remapColumns, userSetting);
                //bt: 11388
                changeOkToSaveSettings($.jgrid.jqID(this.id));
            },
            position: "last"
        })

        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_save_setting'),
            buttonicon: "ui-icon fam-disk",
            onClickButton: function () {
                saveColumnState.call($(this), this.p.remapColumns, userSetting, true, true);
            },
            position: "last"
        })

        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_clear_setting'),
            buttonicon: "ui-icon fam-award-star-delete red",
            onClickButton: function () {
                if (matrix.deleteConfirm({
                    title: jQuery.i18n.prop('title_reset_confirm'),
                    message: jQuery.i18n.prop('msg_reset_confirm'),
                    callback: function () {
                        removeObjectFromLocalStorage(userSetting, true);
                        window.location.reload();
                    }
                }));
            },
            position: "last"
        })

    ;
}
//change Ok button label to save settings
function changeOkToSaveSettings(gridId) {
    var _chooser_id = 'colchooser_' + gridId;
    $('#' + _chooser_id).next().find('div.ui-dialog-buttonset').find('button.ui-button').find('span').eq(0).text('保存');
}

//add save setting button in column chooser
function addSaveSettingButtonInColumnChooser(gridId, remapColumns, userSetting) {
    var _chooser_id = 'colchooser_' + gridId;
    $('#' + _chooser_id).next().append($('<div class="ui-dialog-buttonset" style="float:left"></div>').append($('<button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" aria-disabled="false" role="button" type="button"><span class="ui-button-text">保存设置</span></button>').click(function () {
        saveColumnState.call($('#' + gridId), remapColumns, userSetting, true, true);
    })));
}

//function to add click event bindings to the dialog actions
function BindColPickerActions(gridId) {
    var colpickerId = 'colchooser_' + gridId;

    //When moving an item from selected to available (Hiding)
    $('#' + colpickerId + ' .selected a:not(.SortModifier)').bind('click', function () {
        SortColPickerAvailable(gridId);
        BindColPickerActions(gridId);
    });

    //When moving an item from available to selected (Showing)
    $('#' + colpickerId + ' .available a:not(.SortModifier)').bind('click', function () {
        BindColPickerActions(gridId);
    });

    //add a class to the actions that have been modified to keep track
    $('#colchooser_' + colpickerId + ' .available a:not(.SortModifier), #' + colpickerId + ' .available a:not(.SortModifier)').addClass('SortModifier');
}

//function to sort the available list
function SortColPickerAvailable(gridId) {
    //get the list of li items
    var colpickerId = 'colchooser_' + gridId;
    var available = $('#' + colpickerId + ' .available .connected-list');
    var li = available.children('.ui-element');

    //detatch and sort the li items
    li.detach().sort(function (a, b) {
        return $(a).attr('title').toUpperCase().localeCompare($(b).attr('title').toUpperCase());
    });

    //re-attach the li items
    available.append(li);
}

/**
 * Save grid setting
 */
function saveObjectInLocalStorage(object, userSetting, toDB, addAlert) {
    if (!toDB) {
        storageItemName = userSetting.userName + ":"
            + userSetting.settingType + ":"
            + userSetting.settingName + ":"
            + userSetting.settingTag + ":"
            + userSetting.revampNo;

        if (typeof window.localStorage !== 'undefined') {
            window.localStorage.setItem(storageItemName, JSON.stringify(object));
        }
    } else {
        // Save setting to DB
        saveUserSetting(JSON.stringify(object), userSetting, addAlert);
    }
}

function removeObjectFromLocalStorage(userSetting, toDB) {
    if (!toDB) {
        storageItemName = userSetting.userName + ":"
            + userSetting.settingType + ":"
            + userSetting.settingName + ":"
            + userSetting.settingTag + ":"
            + userSetting.revampNo;

        if (typeof window.localStorage !== 'undefined') {
            window.localStorage.removeItem(storageItemName);
        }
    } else {
        removeUserSetting(userSetting);
    }
}

function getObjectFromLocalStorage(userSetting, toDB) {
    if (toDB) {
        var data = retrieveUserSetting(userSetting);

        if (data != '' && data != 'null' && data != null) {
            return JSON.parse(data);
        }
    } else {
        storageItemName = userSetting.userName + ":"
            + userSetting.settingType + ":"
            + userSetting.settingName + ":"
            + userSetting.settingTag + ":"
            + userSetting.revampNo;

        if (typeof window.localStorage !== 'undefined') {
            var obj = window.localStorage.getItem(storageItemName);
            return JSON.parse(window.localStorage.getItem(storageItemName));
        }
    }
}

function saveColumnState(perm, userSetting, toDB, addAlert) {
    var colModel = this.jqGrid('getGridParam', 'colModel'), i, l = colModel.length, colItem, cmName,
        postData = this.jqGrid('getGridParam', 'postData'),
        columnsState = {
            search: this.jqGrid('getGridParam', 'search'),
            page: this.jqGrid('getGridParam', 'page'),
            rowNum: this.jqGrid('getGridParam', 'rowNum'), //bt:13602
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
    saveObjectInLocalStorage(columnsState, userSetting, toDB, addAlert);
}

function restoreColumnState(colModel, userSetting, toDB) {
    var colItem, i, l = colModel.length, colStates, cmName,
        columnsState = getObjectFromLocalStorage(userSetting, toDB);

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
function saveUserSetting(data, userSetting, addAlert) {
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
            if (addAlert) {
                $.gritter.add({
                    title: '<i class=\'icon-info-sign bigger-120 blue\'></i>  ' + i18nQuery('label_message'),
                    text: jQuery.i18n.prop('setting_save_success'),
                    class_name: 'gritter-info gritter-light'
                });
            }
        },
        error: function (xhr, ajaxOptions, thrownError) {
            if (addAlert) {
                growl('error', xhr.responseText);
            }
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

/*
 *  HTML Table Export to Excel
 *
 */
/**
 *  Export HTML Table to Excel
 * @param gridSelector
 * @param pagerSelector
 * @param params
 */
function addCustomExportExcelByClientNavButton(gridSelector, pagerSelector, params) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: 'HTML Export to Excel',
            buttonicon: "ui-icon fam-page-excel",
            onClickButton: function () {
                jqGridTableToExcelReport(gridSelector, params);
            },
            position: "last"
        });
}

/**
 *
 * @param gridSelector
 * @param params
 * @returns {{rowNums: Array, colNums: Array}}
 */
function retrieveExportRowAndCols(gridSelector, params) {
    if (params == undefined) {
        params = {};
    }

    if (params['excludeColModelNames'] == undefined) {
        params['excludeColModelNames'] = [];
    }

    var excludeColModelNames = params['excludeColModelNames'];

    var inx = 0;
    var colNums = new Array();
    var rowNums = new Array();

    var jqgridRowIDs = $(gridSelector).getDataIDs();                // Fetch the RowIDs for this grid
    var headerData = $(gridSelector).getRowData(jqgridRowIDs[0]);   // Fetch the list of "name" values in our colModel

    var bIsMultiSelect = $(gridSelector).jqGrid('getGridParam', 'multiselect');

    if (bIsMultiSelect) {
        inx = 1;
    }

    for (var headerValue in headerData) {
        var isColumnHidden = $(gridSelector).jqGrid("getColProp", headerValue).hidden;
        if (!isColumnHidden && headerValue != 'act' && $.inArray(headerValue, excludeColModelNames) == -1) {
            colNums.push(inx);
        }
        inx++;
    }

    if (bIsMultiSelect) {
        var selectedIDs = $(gridSelector).jqGrid("getGridParam", "selarrrow");
        // alert(JSON.stringify(selectedIDs));
        for (i = 0; i < selectedIDs.length; i++) {
            var index = $(gridSelector).jqGrid('getInd', selectedIDs[i]);
            rowNums.push(index);
        }

        if (selectedIDs.length == 0) {
            matrix.showMessage("No records selected.");
        }
    }
    else {
        rowNums.push(-1);
    }
    //alert(JSON.stringify(rowNums));
    return {rowNums: rowNums, colNums: colNums};
}


function jqGridTableToExcelReport(gridSelector, params) {
    var selectedRowAndCol = retrieveExportRowAndCols(gridSelector, params);
    var colNums = selectedRowAndCol.colNums;
    var rowNums = selectedRowAndCol.rowNums;

    var templateStart = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>Export Report</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>';
    var templateEnd = '</table></body></html>'

    var tab_text = templateStart;
    tabHeaderSelector = "#gview_" + $(gridSelector).attr('id') + " table.ui-jqgrid-htable";
    tabDataSelector = $(gridSelector).attr('id');
    // tabHeader = $("table.ui-jqgrid-htable")[0];
    // tabData = $("table.ui-jqgrid-btable")[0];
    tabHeader = $(tabHeaderSelector)[0];
    tabData = $(gridSelector);

    $(tabHeader).find("tr").each(function (rowIndex) {
        tab_text = tab_text + "<tr>";
        $(this).find("th").each(function (cellIndex) {
            if ($.inArray(cellIndex, colNums) != -1) {
                //tab_text = tab_text + "<th>" + $(this).html() + "</th>";
                tab_text = tab_text + $(this)[0].outerHTML;
            }
        });

        tab_text = tab_text + "</tr>";
    });

    $(tabData).find("tr").each(function (rowIndex) {
        if (rowNums[0] == -1 || $.inArray(rowIndex, rowNums) != -1) {
            tab_text = tab_text + "<tr>";
            $(this).find("td").each(function (cellIndex) {
                if ($.inArray(cellIndex, colNums) != -1) {
                    //tab_text = tab_text + "<td>" + $(this).html() + "</td>";
                    tab_text = tab_text + $(this)[0].outerHTML;
                    //alert(JSON.stringify($(this)[0].outerHTML));
                }
            });

            tab_text = tab_text + "</tr>";
        }
    });

    tab_text = tab_text + templateEnd;

    tab_text = tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
    tab_text = tab_text.replace(/<img[^>]*>/gi, ""); // remove if u want images in your table
    tab_text = tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params

    var ua = window.navigator.userAgent;
    var msie = ua.indexOf("MSIE ");

    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
    {

        //if ($("#myFram") === undefined) {
        $('<iframe style="display:none" id="myFrame" name="myFrame"/>').appendTo('body');
        //    alert("append");
        //}

        myFrame.document.open("txt/html", "replace");
        myFrame.document.charset = "utf-8";
        myFrame.document.write(tab_text);
        myFrame.document.close();
        myFrame.focus();
        sa = myFrame.document.execCommand("SaveAs", true, "export.xls");

        $("body").remove("#myFram");
    }
    else                 //other browser not tested on IE 11
        sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));

    return (sa);
}

/*
 *  Export to Excel ( for Excel 2007 ~ )
 *
 */
function addCustomExportExcelByServerNavButton(gridSelector, pagerSelector, params) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: '[Items Selected] Export to Excel',
            buttonicon: "ui-icon fam-page-excel",
            onClickButton: function () {
                // ExportJQGridDataToExcel(gridSelector, params);
                ExportJQGridDataToExcel2(gridSelector, params);
            },
            position: "last"
        });
}

//Start: Add by liangyuan For Process COM0024->Accumulated BOC Card List Verification at 2015-05-19
function addCustomExportExcelByServerNavButtonWithoutUpdate(gridSelector, pagerSelector, params) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: 'Export for verification without update',
            buttonicon: "ui-icon fam-page-excel",
            onClickButton: function () {
                // ExportJQGridDataToExcel(gridSelector, params);
                ExportJQGridDataToExcel2(gridSelector, params);
            },
            position: "last"
        });
}

function addCustomExportExcelByServerNavButtonWithUpdate(gridSelector, pagerSelector, params, exportName, exportUid) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: 'Export (With update)',
            buttonicon: "ui-icon fam-page-excel",
            onClickButton: function () {
                // ExportJQGridDataToExcel(gridSelector, params);
                ExportJQGridDataToExcel2(gridSelector, params);
                var selectedRows = $(gridSelector).jqGrid("getGridParam", "selarrrow");
                if (selectedRows && selectedRows.length > 0) {
                    $.ajax({
                        type: "POST",
                        url: _appContext + "/common/boc/accVerification/exportUpdate",
                        data: {
                            ids: selectedRows.join(','),
                            exportName: exportName,
                            exportUid: exportUid
                        },
                        async: false
                    });
                }
            },
            position: "last"
        });
}

function addCustomExportExcelByServerNavButtonExportAllWithUpdate(origGridSetting, gridSelector, pagerSelector, options, exportName, exportUid) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: "Export all(With update)",
            buttonicon: "ui-icon fam-tick blue",
            onClickButton: function () {
                showItemSelectedAllWithUpdate(origGridSetting, options, gridSelector, exportName, exportUid);
            },
            position: "last"
        })
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: "Add selected record(s) to the list",
            buttonicon: "ui-icon fam-brick-add blue",
            onClickButton: function () {
                showAddToList(origGridSetting, options, gridSelector);
                //alert(exportName);
                // alert(exportUid);
            },
            position: "last"
        })
    ;
}

function showItemSelectedAllWithUpdate(origGridSetting, params, sourceGridSelector, exportName, exportUid) {
    $(sourceGridSelector).jqGrid('setGridParam', { rowNum: 10000 });
//    $(sourceGridSelector).jqGrid('setGridParam', { loadonce: true });

    $(sourceGridSelector).jqGrid('setGridParam', { gridComplete: function () {
        selectAllGridCompleteWithUpdate(origGridSetting, params, sourceGridSelector, exportName, exportUid);
    }});
    // $(sourceGridSelector).jqGrid('setGridParam', { loadComplete: myloadComplete});

    $(sourceGridSelector).trigger("reloadGrid", [
        {page: 1}
    ]);
//    $(sourceGridSelector).trigger("reloadGrid");
//    $(sourceGridSelector).jqGrid('setGridParam', { rowNum: 10 });
    return;
}

//function selectAllGridComplete() {
function selectAllGridCompleteWithUpdate(origGridSetting, params, sourceGridSelector, exportName, exportUid) {
    selectedAllData = [];
    selectedAllIds = [];

    var ids = jQuery(sourceGridSelector).jqGrid('getDataIDs');
    for (var i = 0; i < ids.length; i++) {
        var rowId = ids[i];
        var rowData = jQuery(sourceGridSelector).jqGrid('getRowData', rowId);

        selectedAllData.push(rowData);
        selectedAllIds.push(rowId);
    }

    showSelectedAllWindowWithUpdate(origGridSetting, params, sourceGridSelector, exportName, exportUid);

    // alert(JSON.stringify(ids))
    $(sourceGridSelector).jqGrid('setGridParam', { gridComplete: function () {
        //alert(20)
    }});

    $(sourceGridSelector + "_toppager_center select").val("10000");
    $(sourceGridSelector).jqGrid('setGridParam', { rowNum: 10000});
    //$(sourceGridSelector).jqGrid('setGridParam', { loadonce: false });
    //$(sourceGridSelector).trigger("reloadGrid");

    saveSelectedIdAndDataTolocalStorage(params);
}

function showSelectedAllWindowWithUpdate(origGridSetting, params, sourceGridSelector, exportName, exportUid) {
    params['userId'] = _currentUserId;
    if (params['name'] == undefined) {
        params['name'] = params['gridName'];
    }
    if (params['excludeColModelNames'] == undefined) {
        params['excludeColModelNames'] = [];
    }

    var item_selected_jqgrid = $.extend({}, origGridSetting, {});

    // Handle exclude columns
    var excludeColModelNames = params['excludeColModelNames'];
    var colModel = item_selected_jqgrid.colModel;
    var colNames = item_selected_jqgrid.colNames;
//    var colModel = jQuery(sourceGridSelector).jqGrid ('getGridParam', 'colModel');
//    var colNames = jQuery(sourceGridSelector).jqGrid ('getGridParam', 'colNames');
    for (var i = 0; i < excludeColModelNames.length; i++) {
        for (var j = 0; j < colModel.length; j++) {
            if (colModel[j].name == excludeColModelNames[i]) {
                colModel.splice(j, 1);
                colNames.splice(j, 1);
            }
        }
    }

    // Action -> Un-select
    for (var j = 0; j < colModel.length; j++) {
        if (colModel[j].name == 'act') {
            colNames[j] = 'Un-select';
        }
    }
    // Config colModel for popup window
    resetColModel(colModel);

    var selectAllSetting = {
        loadonce: true,
        caption: "",
        datatype: "local",
        data: selectedAllData,
        gridSelector: "#item_selected_grid",
        pagerSelector: "#item_selected_pager",
        //multiselect: false, // Cause -> Uncaught TypeError: Cannot read property 'name' of undefined
        rowNum: 10,
        rowList: [10, 50, 100, 10000],
        autowidth: false,
        shrinkToFit: true,//true,
        sortable: false,
        colNames: colNames,
        colModel: colModel,

        renderCustomizeActionColumn: function (gridSelector) {
            renderActionColumn($(gridSelector), 'act',
                {
                    title: i18nQuery('button_delete'),
                    iconclass: "ui-icon icon-trash red",
                    callback: function (e) {
                        var id = $(e.target).closest("tr.jqgrow").attr("id");
                        var removedData = $(sourceGridSelector).jqGrid('getRowData', id);

                        var index = selectedAllIds.indexOf(id);
                        if (index > -1) {
                            selectedAllData.splice(index, 1);
                            selectedAllIds.splice(index, 1);
                            saveSelectedIdAndDataTolocalStorage(params)
                        }

                        $(gridSelector).jqGrid('delRowData', id);
                    }
                }
            );
        },
        setNavGrid: function (gridSelector, topPagerSelector, userSetting) {
            set_nav_grid_customize(gridSelector, topPagerSelector,
                {editable: false, addable: false, delable: false, searchable: false, viewable: false, refreshable: false}
            );


            addCustomExportExcelByServerNavButtonWithUpdate(gridSelector, topPagerSelector, params, exportName, exportUid)

            addCustomUnSelectAllNavButton(gridSelector, topPagerSelector, params, userSetting);
        }
    };

    item_selected_jqgrid = $.extend({}, item_selected_jqgrid, selectAllSetting);

    //alert(JSON.stringify(gender_list));
    //alert(JSON.stringify(selectedAllData));
    $.ajax({
        type: "POST",
        url: _appContext + "/common/item/selected/ajax/index2",
        data: params,
        //async: false,
        success: function (response) {

            if (!$('#select_all_win').length) {
                $("body").append("<div id='select_all_win'></div>");
            }
            $("#select_all_win").empty();
            $("#select_all_win").html(response);

            if (item_selected_jqgrid !== undefined) {
                setTimeout(function () {
                    jqGridBuild(item_selected_jqgrid);

                    $("#item_selected_grid").setGridParam({rowNum: 10});
                    //$("#item_selected_grid").setGridParam({ rowNum: 10 }).trigger("reloadGrid");
                    $("#item_selected_grid_toppager_center select").val("10");
                    $("#item_selected_grid_toppager_center option[value=10000]").text('ALL');
                }, 50);
            }
        }
    });
}


function addCustomExportExcelByServerNavButtonGenFile(gridSelector, pagerSelector, params) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: 'Gen BOC Status Change notification File',
            buttonicon: "ui-icon fam-page-excel",
            onClickButton: function () {
                // ExportJQGridDataToExcel(gridSelector, params);
                ExportJQGridDataToExcel2(gridSelector, params);
            },
            position: "last"
        });
}
//End: Add by Liangyuan  For Process COM0024->Accumulated BOC Card List Verification at 2015-05-19

// Un-select All ( for Select All New version )
function addCustomUnSelectAllNavButton(gridSelector, pagerSelector, params, userSetting) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: 'Un-select All',
            buttonicon: "ui-icon fam-folder-delete",
            onClickButton: function () {
                $('#item_selected_grid').jqGrid('clearGridData');
                //var storageDataName = userSetting.settingName + ":selectedAll:data";
                //var storageIdsName = userSetting.settingName + ":selectedAll:ids";

                selectedAllData = [];
                selectedAllIds = [];

//                if (typeof window.localStorage !== 'undefined') {
//                    window.localStorage.setItem(storageDataName, JSON.stringify(selectedAllData));
//                    window.localStorage.setItem(storageIdsName, JSON.stringify(selectedAllIds));
//                }
                saveSelectedIdAndDataTolocalStorage(params);
            },
            position: "last"
        });
}

/**
 *
 * @param gridSelector
 * @param excelFilename
 * @param url
 * @constructor
 */
function ExportJQGridDataToExcel(gridSelector, params) {
    if (params == undefined) {
        params = {};
    }

    if (params['url'] == undefined) {
        params['url'] = _appContext + '/common/jqgrid/export/excel';
    }
    if (params['excelFilename'] == undefined) {
        params['excelFilename'] = 'export.xlsx';
    }
    if (params['excludeColModelNames'] == undefined) {
        params['excludeColModelNames'] = [];
    }

    var url = params['url'];
    var excelFilename = params['excelFilename'];
    var excludeColModelNames = params['excludeColModelNames'];

    //  For each visible column in our jqGrid, fetch it's Name, and it's Header-Text value
    var columnNames = new Array();       //  The "name" values from our jqGrid colModel
    var columnHeaders = new Array();     //  The Header-Text, from the jqGrid "colNames" section
    var inx = 0;
    var allColumnNames = $(gridSelector).jqGrid('getGridParam', 'colNames');

    var jqgridRowIDs = $(gridSelector).getDataIDs();                // Fetch the RowIDs for this grid
    var headerData = $(gridSelector).getRowData(jqgridRowIDs[0]);   // Fetch the list of "name" values in our colModel

    //  If our jqGrid has "MultiSelect" set to true, remove the first (checkbox) column, otherwise we'll
    //  create an exception of: "A potentially dangerous Request.Form value was detected from the client."
    var bIsMultiSelect = $(gridSelector).jqGrid('getGridParam', 'multiselect');
    if (bIsMultiSelect) {
        // allColumnNames = allColumnNames.splice(0, 1); // NOT work
        inx = 1;
    }

    for (var headerValue in headerData) {
        //  If this column ISN'T hidden, and DOES have a column-name, then we'll export its data to Excel.
        var isColumnHidden = $(gridSelector).jqGrid("getColProp", headerValue).hidden;
        //if (!isColumnHidden && headerValue != null) {
        if (!isColumnHidden && headerValue != 'act' && $.inArray(headerValue, excludeColModelNames) == -1) {
            columnNames.push(headerValue);
            columnHeaders.push(allColumnNames[inx]);
            //alert(headerValue + ":" + allColumnNames[inx]);
        }
        inx++;
    }

    //  We now need to build up a (potentially very long) tab-separated string containing all of the data (and a header row)
    //  which we'll want to export to Excel.

    //  First, let's append the header row...
    var excelData = '';
    for (var k = 0; k < columnHeaders.length; k++) {
        excelData += columnHeaders[k] + "\t";
    }
    excelData = removeLastChar(excelData) + "\r\n";

    // Retrieved selected rows
    var exportRows = new Array();

    if (bIsMultiSelect) {
        var selectedIDs = $(gridSelector).jqGrid("getGridParam", "selarrrow");
        for (i = 0; i < selectedIDs.length; i++) {
            //var rowData = $(gridSelector).getRowData(selectedIDs[i]);
            var rowData = $(gridSelector).jqGrid('getRowData', selectedIDs[i]);
            exportRows.push(rowData);
        }
    }
    else {
        exportRows = $(gridSelector).jqGrid('getRowData');
    }
    //alert(JSON.stringify(exportRows));

    var cellValue = '';
    for (i = 0; i < exportRows.length; i++) {
        var data = exportRows[i];

        for (var j = 0; j < columnNames.length; j++) {
            // Fetch one jqGrid cell's data, but make sure it's a string
            cellValue = '' + data[columnNames[j]];

            if (cellValue == null)
                excelData += "\t";
            else {
                if (cellValue.indexOf("a href") > -1) {
                    //  Some of my cells have a jqGrid cell with a formatter in them, making them hyperlinks.
                    //  We don't want to export the "<a href..> </a>" tags to our Excel file, just the cell's text.
                    cellValue = $(cellValue).text();
                }
                //  Make sure we are able to POST data containing apostrophes in it
                cellValue = cellValue.replace(/'/g, "&apos;");

                excelData += cellValue + "\t";
            }
        }
        excelData = removeLastChar(excelData) + "\r\n";
    }

    if (exportRows != undefined && exportRows.length > 0) {
        postAndRedirect(url + "?filename=" + excelFilename, { excelData: excelData });
    }
}

/**
 *
 * @param gridSelector
 * @param excelFilename
 * @param url
 * @constructor
 */
function ExportJQGridDataToExcel2(gridSelector, params) {
    if (params == undefined) {
        params = {};
    }

    if (params['url'] == undefined) {
        params['url'] = _appContext + '/common/jqgrid/export/excel';
    }
    if (params['excelFilename'] == undefined) {
        params['excelFilename'] = 'export.xlsx';
    }
    if (params['excludeColModelNames'] == undefined) {
        params['excludeColModelNames'] = [];
    }

    var url = params['url'];
    var excelFilename = params['excelFilename'];
    var excludeColModelNames = params['excludeColModelNames'];

    var excelData = parseJqGridTableForSever(gridSelector, params);
    // BT 11675
    if (excelData != '') {
        // TODO Escape html
        excelData = escapeHtml(excelData);
        // alert(excelData);
        postAndRedirect(url + "?filename=" + excelFilename, { excelData: excelData });
    }
}


function removeLastChar(str) {
    return str.substring(0, str.length - 1);
}

function postAndRedirect(url, postData) {
    var postFormStr = "<form method='POST' action='" + url + "'>\n";

    for (var key in postData) {
        if (postData.hasOwnProperty(key)) {
            postFormStr += "<input type='hidden' name='" + key + "' value='" + postData[key] + "'></input>";
        }
    }

    postFormStr += "</form>";

    var formElement = $(postFormStr);

    $('body').append(formElement);
    $(formElement).submit();
}

function parseJqGridTableForSever(gridSelector, params) {
    //alert($(gridSelector).attr('id'));
    var selectedRowAndCol = retrieveExportRowAndCols(gridSelector, params);
    var colNums = selectedRowAndCol.colNums;
    var rowNums = selectedRowAndCol.rowNums;

    // 11675
    if (rowNums == undefined || rowNums.length == 0) {
        return '';
    }

    tabHeaderSelector = "#gview_" + $(gridSelector).attr('id') + " table.ui-jqgrid-htable";
    tabDataSelector = $(gridSelector).attr('id');
    // tabHeader = $("table.ui-jqgrid-htable")[0];
    // tabData = $("table.ui-jqgrid-btable")[0];
    tabHeader = $(tabHeaderSelector)[0];
    tabData = $(gridSelector);

    var excelData = "";
    $(tabHeader).find("tr").each(function (rowIndex) {
        $(this).find("th").each(function (cellIndex) {
            if ($.inArray(cellIndex, colNums) != -1) {
                // excelData += $(this).text() + "\t";
                excelData += ($(this).text()).replace(/'/g, "&apos;") + "\t";
            }
        });
        excelData = removeLastChar(excelData) + "\r\n";
    });

    $(tabData).find("tr").each(function (rowIndex) {
        if (rowIndex != 0 && (rowNums[0] == -1 || $.inArray(rowIndex, rowNums) != -1)) {
            // excelData += "" + rowIndex + ":";
            $(this).find("td").each(function (cellIndex) {
                if ($.inArray(cellIndex, colNums) != -1) {
                    //excelData += $(this).text() + "\t";

                    excelData += ($(this).text()).replace(/'/g, "&apos;") + "\t";
                }
            });

            excelData = removeLastChar(excelData) + "\r\n";
        }
    });
    return excelData;
}


/*
 *  Item Selected: Select All - Un-Select All
 *
 */
// New Version - Just handle current page, add selected records to ALL list
function addCustomAddToListNavButton(origGridSetting, gridSelector, pagerSelector, options) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: "Add selected record(s) to the list",
            buttonicon: "ui-icon fam-brick-add blue",
            onClickButton: function () {
                showAddToList(origGridSetting, options, gridSelector);
            },
            position: "last"
        })
    ;
}

function getSelectedIdAndDataFromlocalStorage(params) {
    if (params['gridName'] !== undefined) {
        // params['gridName'] = 'main';
        // Get selected all data from local storage
        var storageDataName = params['gridName'] + ":selectedAll:data";
        var storageIdsName = params['gridName'] + ":selectedAll:ids";

        if (typeof window.localStorage !== 'undefined') {
            var dataObj = window.localStorage.getItem(storageDataName);
            if (dataObj !== undefined && dataObj != null) {
                selectedAllData = JSON.parse(dataObj);
            }

            var idsObj = window.localStorage.getItem(storageIdsName);
            if (idsObj !== undefined && idsObj != null) {
                selectedAllIds = JSON.parse(idsObj);
            }
        }
    }
}

function saveSelectedIdAndDataTolocalStorage(params) {
    if (params['gridName'] !== undefined) {
        var storageDataName = params['gridName'] + ":selectedAll:data";
        var storageIdsName = params['gridName'] + ":selectedAll:ids";

        if (typeof window.localStorage !== 'undefined') {
            window.localStorage.setItem(storageDataName, JSON.stringify(selectedAllData));
            window.localStorage.setItem(storageIdsName, JSON.stringify(selectedAllIds));
        }
    }
}

function showSelectedAllWindow(origGridSetting, params, sourceGridSelector) {
    params['userId'] = _currentUserId;
    if (params['name'] == undefined) {
        params['name'] = params['gridName'];
    }
    if (params['excludeColModelNames'] == undefined) {
        params['excludeColModelNames'] = [];
    }

    var item_selected_jqgrid = $.extend({}, origGridSetting, {});

    // Handle exclude columns
    var excludeColModelNames = params['excludeColModelNames'];
    var colModel = item_selected_jqgrid.colModel;
    var colNames = item_selected_jqgrid.colNames;
//    var colModel = jQuery(sourceGridSelector).jqGrid ('getGridParam', 'colModel');
//    var colNames = jQuery(sourceGridSelector).jqGrid ('getGridParam', 'colNames');
    for (var i = 0; i < excludeColModelNames.length; i++) {
        for (var j = 0; j < colModel.length; j++) {
            if (colModel[j].name == excludeColModelNames[i]) {
                colModel.splice(j, 1);
                colNames.splice(j, 1);
            }
        }
    }

    // Action -> Un-select
    for (var j = 0; j < colModel.length; j++) {
        if (colModel[j].name == 'act') {
            colNames[j] = 'Un-select';
        }
    }
    // Config colModel for popup window
    resetColModel(colModel);

    var selectAllSetting = {
        loadonce: true,
        caption: "",
        datatype: "local",
        data: selectedAllData,
        gridSelector: "#item_selected_grid",
        pagerSelector: "#item_selected_pager",
        //multiselect: false, // Cause -> Uncaught TypeError: Cannot read property 'name' of undefined
        rowNum: 10,
        rowList: [10, 50, 100, 10000],
        autowidth: false,
        shrinkToFit: true,//true,
        sortable: false,
        colNames: colNames,
        colModel: colModel,

        renderCustomizeActionColumn: function (gridSelector) {
            renderActionColumn($(gridSelector), 'act',
                {
                    title: i18nQuery('button_delete'),
                    iconclass: "ui-icon icon-trash red",
                    callback: function (e) {
                        var id = $(e.target).closest("tr.jqgrow").attr("id");
                        var removedData = $(sourceGridSelector).jqGrid('getRowData', id);

                        var index = selectedAllIds.indexOf(id);
                        if (index > -1) {
                            selectedAllData.splice(index, 1);
                            selectedAllIds.splice(index, 1);
                            saveSelectedIdAndDataTolocalStorage(params)
                        }

                        $(gridSelector).jqGrid('delRowData', id);
                    }
                }
            );
        },
        setNavGrid: function (gridSelector, topPagerSelector, userSetting) {
            set_nav_grid_customize(gridSelector, topPagerSelector,
                {editable: false, addable: false, delable: false, searchable: false, viewable: false, refreshable: false}
            );

            addCustomExportExcelByServerNavButton(gridSelector, topPagerSelector, { });

            addCustomUnSelectAllNavButton(gridSelector, topPagerSelector, params, userSetting);
        }
    };

    item_selected_jqgrid = $.extend({}, item_selected_jqgrid, selectAllSetting);

    //alert(JSON.stringify(gender_list));
    //alert(JSON.stringify(selectedAllData));
    $.ajax({
        type: "POST",
        url: _appContext + "/common/item/selected/ajax/index2",
        data: params,
        //async: false,
        success: function (response) {

            if (!$('#select_all_win').length) {
                $("body").append("<div id='select_all_win'></div>");
            }
            $("#select_all_win").empty();
            $("#select_all_win").html(response);

            if (item_selected_jqgrid !== undefined) {
                setTimeout(function () {
                    jqGridBuild(item_selected_jqgrid);

                    $("#item_selected_grid").setGridParam({rowNum: 10});
                    //$("#item_selected_grid").setGridParam({ rowNum: 10 }).trigger("reloadGrid");
                    $("#item_selected_grid_toppager_center select").val("10");
                    $("#item_selected_grid_toppager_center option[value=10000]").text('ALL');
                }, 50);
            }
        }
    });
}

// New Version - Just handle current page, add selected records to ALL list
function showAddToList(origGridSetting, params, sourceGridSelector) {
    // Retrieve from local storage
    getSelectedIdAndDataFromlocalStorage(params);

    // Handle Current Page's selected rows
    var RowList = $(sourceGridSelector).getGridParam('selarrrow');
    for (var i = 0, list = RowList.length; i < list; i++) {
        var selectedId = RowList[i];
        var selectedData = $(sourceGridSelector).jqGrid('getRowData', selectedId);

        if (selectedAllIds.indexOf(selectedId) == -1) {
            selectedAllData.push(selectedData);
            selectedAllIds.push(selectedId);
        }
    }

    // Save local storage
    if (RowList.length > 0) {
        saveSelectedIdAndDataTolocalStorage(params);
    }

    // Show popup window
    showSelectedAllWindow(origGridSetting, params, sourceGridSelector);
}

function resetColModel(colModel) {
    for (var j = 0; j < colModel.length; j++) {
        if (colModel[j].formatter !== undefined && colModel[j].formatter != 'actions') {
            delete colModel[j].formatter;
            // alert(colModel[j].name);
        }
    }
}


//  @Deprecated
// Old Version - retrieve all records from DB
function showItemSelectedAllOnce(origGridSetting, params, sourceGridSelector) {
    params['userId'] = _currentUserId;
    if (params['name'] == undefined) {
        params['name'] = params['gridName'];
    }
    if (params['excludeColModelNames'] == undefined) {
        params['excludeColModelNames'] = [];
    }
    var item_selected_jqgrid = $.extend({}, origGridSetting, {});

    // Handle exclude columns
    var excludeColModelNames = params['excludeColModelNames'];
    var colModel = item_selected_jqgrid.colModel;
    var colNames = item_selected_jqgrid.colNames;
    for (var i = 0; i < excludeColModelNames.length; i++) {
        for (var j = 0; j < colModel.length; j++) {
            if (colModel[j].name == excludeColModelNames[i]) {
                colModel.splice(j, 1);
                colNames.splice(j, 1);
            }
        }
    }

    // Action -> Un-select
    for (var j = 0; j < colModel.length; j++) {
        if (colModel[j].name == 'act') {
            colNames[j] = 'Un-select';
        }
    }

    var postData = $(sourceGridSelector).jqGrid("getGridParam", "postData");

    var selectAllSetting = {
        loadonce: true,
        caption: "",
        gridSelector: "#item_selected_grid",
        pagerSelector: "#item_selected_pager",
        multiselect: false,
        rowNum: 10000,
        rowList: [10, 50, 100, 10000],
        shrinkToFit: false,//true,
        sortable: false,
        postData: postData,
        colNames: colNames,
        colModel: colModel,

        renderCustomizeActionColumn: function (gridSelector) {
            renderActionColumn($(gridSelector), 'act',
                {
                    title: i18nQuery('button_delete'),
                    iconclass: "ui-icon icon-trash red",
                    callback: function (e) {
                        var id = $(e.target).closest("tr.jqgrow").attr("id");
                        //matrix.confirmEntryDeletion(id, {formId: "theDeleteForm"});
                        $(gridSelector).jqGrid('delRowData', id);
                        // TODO: Save deleted item id in DB
                    }
                }
            );
        },
        setNavGrid: function (gridSelector, topPagerSelector, userSetting) {
            set_nav_grid_customize(gridSelector, topPagerSelector,
                {editable: false, addable: false, delable: false, searchable: false, viewable: false, refreshable: false}
            );

            addCustomExportExcelByServerNavButton(gridSelector, topPagerSelector,
                {
                }
            );
        }
    };

    item_selected_jqgrid = $.extend({}, item_selected_jqgrid, selectAllSetting);

    $.ajax({
        type: "POST",
        url: _appContext + "/common/item/selected/ajax/index2",
        data: params,
        //async: false,
        success: function (response) {

            if (!$('#select_all_win').length) {
                $("body").append("<div id='select_all_win'></div>");
            }
            $("#select_all_win").empty();
            $("#select_all_win").html(response);

            if (item_selected_jqgrid !== undefined) {
                setTimeout(function () {
                    jqGridBuild(item_selected_jqgrid);

                    $("#item_selected_grid").setGridParam({rowNum: 50});
                    $("#item_selected_grid").setGridParam({ rowNum: 50 }).trigger("reloadGrid");
                    $("#item_selected_grid_toppager_center select").val("50");
                    $("#item_selected_grid_toppager_center option[value=10000]").text('ALL');
                }, 50);
            }
        }
    });
}

// Version 3 - retrieve all records from DB
function addCustomSelectAllNavButton(origGridSetting, gridSelector, pagerSelector, options) {
    // Nothing
}
function addCustomSelectAllNavButtonBAK(origGridSetting, gridSelector, pagerSelector, options) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: "Select all",
            buttonicon: "ui-icon fam-tick blue",
            onClickButton: function () {
                showItemSelectedAll(origGridSetting, options, gridSelector);
            },
            position: "last"
        })
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: "Add selected record(s) to the list",
            buttonicon: "ui-icon fam-brick-add blue",
            onClickButton: function () {
                showAddToList(origGridSetting, options, gridSelector);
            },
            position: "last"
        })
    ;
}


//function selectAllGridComplete() {
function selectAllGridComplete(origGridSetting, params, sourceGridSelector) {
    selectedAllData = [];
    selectedAllIds = [];

    var ids = jQuery(sourceGridSelector).jqGrid('getDataIDs');
    for (var i = 0; i < ids.length; i++) {
        var rowId = ids[i];
        var rowData = jQuery(sourceGridSelector).jqGrid('getRowData', rowId);

        selectedAllData.push(rowData);
        selectedAllIds.push(rowId);
    }

    showSelectedAllWindow(origGridSetting, params, sourceGridSelector);

    // alert(JSON.stringify(ids))
    $(sourceGridSelector).jqGrid('setGridParam', { gridComplete: function () {
        //alert(20)
    }});

    $(sourceGridSelector + "_toppager_center select").val("10000");
    $(sourceGridSelector).jqGrid('setGridParam', { rowNum: 10000});
    //$(sourceGridSelector).jqGrid('setGridParam', { loadonce: false });
    //$(sourceGridSelector).trigger("reloadGrid");


    saveSelectedIdAndDataTolocalStorage(params);
}


// Select All Version 3
function showItemSelectedAll(origGridSetting, params, sourceGridSelector) {
    $(sourceGridSelector).jqGrid('setGridParam', { rowNum: 10000 });
//    $(sourceGridSelector).jqGrid('setGridParam', { loadonce: true });

    $(sourceGridSelector).jqGrid('setGridParam', { gridComplete: function () {
        selectAllGridComplete(origGridSetting, params, sourceGridSelector);
    }});
    // $(sourceGridSelector).jqGrid('setGridParam', { loadComplete: myloadComplete});

    $(sourceGridSelector).trigger("reloadGrid", [
        {page: 1}
    ]);
//    $(sourceGridSelector).trigger("reloadGrid");
//    $(sourceGridSelector).jqGrid('setGridParam', { rowNum: 10 });
    return;
}

/**
 * @Deprecated use addCustomSelectAllNavButton instead
 */
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
            buttonicon: "ui-icon fam-folder-add",
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

/**
 * @Deprecated use addCustomSelectAllNavButton instead
 */
function showItemSelected(params) {
    alert("The function has been deprecated.");
}

/**
 * @Deprecated use addCustomSelectAllNavButton instead
 */
function addToExclusion(gridSelector, params) {
    alert("The function has been deprecated.");
    return false;
}

/*
 *  Export Related By Jack
 */
/**
 * @Deprecated
 */
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

function addCustomGridExportToExcelNavButton(gridSelector, pagerSelector, url) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_export_excel'),
            buttonicon: "ui-icon fam-tick blue",
            onClickButton: function () {
                exportToFile.call($(this), url, 'excel', '1', null);
            },
            position: "last"
        })
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_selected_export_excel'),
            buttonicon: "ui-icon fam-page-excel",
            onClickButton: function () {
                if (idsOfSelectedRows.length && idsOfSelectedRows.length > 0) {
                    exportToFile.call($(this), url, 'excel', '-1', null);
                } else {
                    matrix.showError(i18nQuery('no_selected_row'));
                }
            },
            position: "last"
        });
}

function addCustomGridExportAllToExcelNavButton(gridSelector, pagerSelector, url) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_export_excel'),
            buttonicon: "ui-icon fam-page-excel",
            onClickButton: function () {
                exportToFile.call($(this), url, 'excel', '1', null);
            },
            position: "last"
        });
}
/**
 *  @Deprecated
 *
 */
function addCustomGridExportSelectedToExcelNavButton(gridSelector, pagerSelector, url) {
    jQuery(gridSelector)
        .navButtonAdd(pagerSelector, {
            caption: "",
            title: i18nQuery('button_selected_export_excel'),
            buttonicon: "ui-icon fam-page-excel",
            onClickButton: function () {
//                var _selected_ids = $(gridSelector).jqGrid("getGridParam", "selarrrow");
                if (idsOfSelectedRows.length && idsOfSelectedRows.length > 0) {
                    exportToFile.call($(this), url, 'excel', '-1', null);
                } else {
                    matrix.showError(i18nQuery('no_selected_row'));
                }
            },
            position: "last"
        });
}

/**
 * @Deprecated
 */
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
/**
 *  @Deprecated
 *
 */
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

/**
 * @Deprecated
 */
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
 *  @Deprecated
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
            idsOfSelectedRows: idsOfSelectedRows.join(','),
            gridName: (gridName == null ? '' : gridName),
            caption: this.jqGrid('getGridParam', 'caption'),
            _search: this.jqGrid('getGridParam', 'search'),
            page: this.jqGrid('getGridParam', 'page'),
            rows: (this.jqGrid('getGridParam', 'rows') == null ? '-1' : this.jqGrid('getGridParam', 'rows')),
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
    form.attr('target', '_blank');
    form.attr('method', 'post');
    form.attr('action', url);

    var inputData = $('<input>');
    inputData.attr('type', 'hidden');
    inputData.attr('name', 'data');
    inputData.attr('value', data);

    $('body').append(form);
    form.append(inputData);
    form.submit();
    form.remove();
}
