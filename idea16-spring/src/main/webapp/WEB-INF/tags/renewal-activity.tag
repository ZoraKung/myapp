<%@ tag language="java" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/common/taglibs.jsp" %>
<%@ attribute name="id" type="java.lang.String" required="true" description="No" %>
<%@ attribute name="renewalType" type="java.lang.String" required="true" description="Input Name" %>
<%@ attribute name="identifyUid" type="java.lang.String" required="true" description="Input Value" %>
<div class="row">
    <div class="col-xs-12">
        <table id="${id}tbl_renewal_activity"></table>
        <div id="${id}pager_renewal_activity"></div>
    </div>
</div>
<script type="text/javascript">
var ${id}renewalType = '${renewalType}';
var ${id}identifyUid = '${identifyUid}';

var ${id}tbl_renewal_activity = {
    url: "${ctx}/ajax/common/renewal/activity/search",
    postData: {renewalType: ${id}renewalType, identifyUid: ${id}identifyUid},
    bNavBarInTop: false,
    bNavBarInBottom: true,
    autoHeight: false,
    gridSelector: "#${id}tbl_renewal_activity",
    pagerSelector: "#${id}pager_renewal_activity",
    pgbuttons: true,
    pginput: true,
    viewrecords: true,
    sortname: "fiscalYear",
    sortorder: "desc",
    rowList: -1,
    multiselect: false,

    colNames: [
        "Id", "Renewal Type", "Idv Uid"
        , "Year of Renewal"
        , "Date"
        , "Activity"
        , "Batch No."
        , "Address To"
        , "Status"
        , "Remarks"
        , "Responsible Person"
    ],

    colModel: [
        {name: 'id', index: 'id', editable: false, search: false, hidden: true},
        {name: 'renewalType', index: 'renewalType', editable: true, search: false, hidden: true},
        {name: 'idvUid', index: 'idvUid', editable: true, search: false, hidden: true},

        {name: 'fiscalYear', index: 'fiscalYear', width: 100,
            search: true, sortable: true,
            template: stringExtendTemplate
        },
        {name: 'updatedDate', index: 'updatedDate', width: 100,
            search: true, sortable: true,
            template: dateTemplate
        },
        {name: 'activityName', index: 'activityName', width: 150,
            search: true, sortable: true,
            template: stringExtendTemplate
        },
        {name: 'batchNo', index: 'batchNo', width: 100,
            search: true, sortable: true,
            template: stringExtendTemplate
        },
        {name: 'addressTo', index: 'addressTo', width: 150,
            search: true, sortable: true,
            template: stringExtendTemplate
        },
        {name: 'status', index: 'status', width: 100,
            search: true, sortable: true,
            template: stringExtendTemplate
        },
        {name: 'remarks', index: 'remarks', width: 150,
            search: true, sortable: true,
            template: stringExtendTemplate
        },
        {name: 'responsiblePerson', index: 'responsiblePerson', width: 150,
            search: true, sortable: true,
            template: stringExtendTemplate
        }
    ],
    setNavGrid: function (gridSelector, topPagerSelector, userSetting) {
        set_nav_grid_customize(gridSelector, topPagerSelector,
                {
                    editable: false, addable: false, delable: false, searchable: true, viewable: false, refreshable: false
                });
    }
};

$(function () {
    jqGridBuild(${id}tbl_renewal_activity);
});
</script>

