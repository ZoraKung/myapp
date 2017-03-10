<%@ tag language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="No" %>
<%@ attribute name="certType" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="identifyUid" type="java.lang.String" required="true" description="Input Value" %>
<%@ attribute name="status" type="java.lang.String" required="false" description="Input Value" %>
<input type="hidden" id="${id}cert_type_id" value="${certType}"/>
<input type="hidden" id="${id}identify_uid_id" value="${identifyUid}"/>
<input type="hidden" id="${id}status_id" value="${status}"/>
<div class="row">
    <div class="col-xs-11">
        <table id="${id}tbl_print_cert"></table>
        <div id="${id}pager_print_cert"></div>
    </div>
</div>
<script type="text/javascript">
var ${id}certType = $('#${id}cert_type_id').val();
var ${id}identifyUid = $('#${id}identify_uid_id').val();
var ${id}status = $('#${id}status_id').val();
var ${id}statusList = matrix.returnJsonData({url: "${ctx}/common/service/label-value/com_cert_log_status", data: {filter: 'N;R'}, async: false});
var ${id}reasonList = matrix.returnJsonData({url: "${ctx}/common/service/label-value/reissue_reason", async: false});
var ${id}userList = matrix.returnJsonData({url: "${ctx}/common/service/label-value/mas_user", async: false});

var ${id}print_cert_jqgrid = {
    url: "${ctx}/common/ajax/print-cert/search",
    editurl: "${ctx}/common/ajax/print-cert/edit",
    postData: {certType: ${id}certType, identifyUid: ${id}identifyUid, status: ${id}status},
    bNavBarInTop: false,
    bNavBarInBottom: true,
    autoHeight: false,
    gridSelector: "#${id}tbl_print_cert",
    pagerSelector: "#${id}pager_print_cert",
    pgbuttons: true,
    pginput: true,
    viewrecords: true,
    sortname: "printDate",
    sortorder: "desc",
    rowList: -1,
    multiselect: false,

    colNames: [
        "Id", "Certificate Type", "Identify Uid"
        , "Cert. Status"
        , "Cert. No."
        , "Reason"
        , "Cert. Remarks"
        , "Reissue Date"
        , "Print Date"
        , "Responsible Person"
        , "Action"
    ],

    colModel: [
        {name: 'id', index: 'id', editable: false, search: false, hidden: true},
        {name: 'certType', index: 'certType', editable: true, search: false, hidden: true},
        {name: 'identifyUid', index: 'identifyUid', editable: true, search: false, hidden: true},

        {name: 'logStatus', index: 'logStatus', width: 100,
            stype: "select", formatter: ${id}statusFormatter,
            search: true, searchoptions: {sopt: ['eq', 'ne'], value: ${id}statusList},
            editable: true, edittype: 'select',
            editoptions: {
                width: 120,
                dataUrl: '${ctx}/common/service/label-value/com_cert_log_status?filter=N;R',
                buildSelect: jqGridBuildSelect
            }
        },
        {name: 'certNumber', index: 'certNumber', width: 100,
            search: true, searchoptions: {sopt: ['cn', 'bw', 'eq']},
            editable: true, edittype: 'select', /*editrules:{required: true},*/
            editoptions: {
                width: 120,
                dataUrl: '${ctx}/common/ajax/print-cert/certNumberList/${certType}',
                buildSelect: jqGridBuildSelect,
                dataEvents: [
                    {
                        type: 'change',
                        fn: function (e) {
                            var _certNumber = $(e.target).val();
                            var _exist = valueExistInJqGridColumn('tbl_print_cert', 'certNumber', _certNumber);
                            if (_exist) {
                                $(e.target).val('');
                                matrix.showError(jQuery.i18n.prop('check_number_exists'));
                                return false;
                            }
                            var $status = $(e.target).closest("tr.jqgrow").find("td").eq(7).find("select");
                            if (_certNumber == '') {
                                $status.val('');
                            }
                        }
                    }
                ]
            }
        },
        {name: 'reissueReason', index: 'reissueReason', width: 100,
            stype: "select", formatter: ${id}reasonFormatter,
            search: true, searchoptions: {sopt: ['eq', 'ne'], value: ${id}reasonList},
            editable: true, edittype: 'select', editrules: {required: true},
            editoptions: {
                value: ${id}reasonList,
                width: 100
            }
        },
        {name: 'reissueRemark', index: 'reissueRemark', width: 200,
            editable: true, search: true, searchoptions: {sopt: ['cn', 'bw', 'eq']},
            sortable: true
        },
        {name: 'reissueDate', index: 'reissueDate', width: 100, sortable: true,
            editable: true,
            editoptions: {size: 10, maxlengh: 10,
                dataInit: function (element) {
                    $(element).datepicker({format: 'dd-mm-yyyy', autoclose: false}),
                            $(element).width(120)
                }},
            editrules: {required: true},
            template: dateTemplate, search: true
        },
        {name: 'printDate', index: 'printDate', width: 100, sortable: true,
            editable: false, template: dateTemplate, search: true
        },
        {name: 'responsiblePerson', index: 'responsiblePerson', width: 100,
            formatter: ${id}userFormatter,
            editabe: false,
            search: true, searchoptions: {sopt: ['cn', 'bw', 'eq']}
        },
        {name: 'act', index: 'act', width: 80, fixed: true, sortable: false, resize: false,
            search: false, editable: false, viewable: false,
            formatter: 'actions',
            formatoptions: {
                delbutton: false,
                editbutton: true,
                onEdit: function (e) {
                    var $certNumber = $("#" + e).find("td").eq(6).find("select");
                    var $status = $("#" + e).find("td").eq(7).find("select");
                    if ($certNumber.val() == '') {
                        $status.val('');
                    }
                },
                onError: inlineEditOnError,
                afterSave: inlineEditAfterSave,
                afterRestore: inlineEditAfterRestore, keys: true
            }
        }
    ],
    renderCustomizeActionColumn: function (gridSelector) {
        renderActionColumn($(gridSelector), 'act', ${id}delIcon);
    },
    setNavGrid: function (gridSelector, topPagerSelector, userSetting) {
        set_nav_grid_customize(gridSelector, topPagerSelector,
                {
                    editable: false, addable: false, delable: false, searchable: false, viewable: false, refreshable: false
                });
        addCustomNavButton(gridSelector, topPagerSelector, {
            caption: "",
            title: "Add",
            buttonicon: "fam-add",
            onClick: function () {
                if (${id}identifyUid == undefined || ${id}identifyUid == '') {
                    matrix.showError(jQuery.i18n.prop('save_main_first'));
                } else {
                    var rowId = "0_" + new Date().getTime();
                    jQuery(gridSelector).jqGrid('addRow',
                            {
                                rowID: rowId,
                                initdata: {
                                    "id": rowId,
                                    "stockId": '',
                                    "certType": ${id}certType,
                                    "identifyUid": ${id}identifyUid,
                                    "certStatus": ''
                                },
                                position: "first",
                                useDefValues: true,
                                useFormatter: true,
                                addRowParams: {}
                            }
                    );
                    renderActionColumnEditInline($(gridSelector), 'act', ${id}delIcon);
                }
            }
        });
    }
};

$(function () {
    jqGridBuild(${id}print_cert_jqgrid);
});

function ${id}reasonFormatter(cellvalue, options, rowObject) {
    if (cellvalue != '' && cellvalue != 'undefined' && cellvalue != null) {
        return ${id}reasonList[cellvalue];
    }
    else return '';
}
;

function ${id}userFormatter(cellvalue, options, rowObject) {
    if (cellvalue != '' && cellvalue != 'undefined' && cellvalue != null) {
        return ${id}userList[cellvalue];
    }
    else return '';
}
;

function ${id}statusFormatter(cellvalue, options, rowObject) {
    if (cellvalue != '' && cellvalue != 'undefined' && cellvalue != null) {
        return ${id}statusList[cellvalue];
    }
    else return '';
}
;

var ${id}delIcon = {
    title: i18nQuery('button_delete'),
    iconclass: "ui-icon icon-trash red",
    callback: function (e) {
        var rowId = $(e.target).closest("tr.jqgrow").attr("id");
        matrix.ajaxDelete({
            name: 'Delete Print Cert',
            url: '${ctx}/common/ajax/print-cert/delete',
            params: {id: rowId},
            callback: function () {
                $(e.target).closest("tr.jqgrow").remove();
            }
        });
        return false;
    }
}
</script>

