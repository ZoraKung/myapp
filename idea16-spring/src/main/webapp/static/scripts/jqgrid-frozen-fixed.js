frozenFixed = function ($grid) {

    $grid.jqGrid('gridResize', {
        minWidth: 450,
        stop: function () {
            fixPositionsOfFrozenDivs.call(this);
            fixGboxHeight.call(this);
        }
    });
    $grid.bind("jqGridResizeStop", function () {
        resizeColumnHeader.call(this);
        fixPositionsOfFrozenDivs.call(this);
        fixGboxHeight.call(this);
    });
    $grid.jqGrid('setFrozenColumns');
    $grid.triggerHandler("jqGridAfterGridComplete");
    resizeColumnHeader.call($grid[0]);
    fixPositionsOfFrozenDivs.call($grid[0]);

    $(window).on("resize", function () {
        // apply the fix an all grids on the page on resizing of the page
        $("table.ui-jqgrid-btable").each(function () {
            fixPositionsOfFrozenDivs.call(this);
        });
    });

};
resizeColumnHeader = function () {
    var rowHeight, resizeSpanHeight,
    // get the header row which contains
        headerRow = $(this).closest("div.ui-jqgrid-view")
            .find("table.ui-jqgrid-htable>thead>tr.ui-jqgrid-labels");

    // reset column height
    headerRow.find("span.ui-jqgrid-resize").each(function () {
        this.style.height = '';
    });

    // increase the height of the resizing span
    resizeSpanHeight = 'height: ' + headerRow.height() + 'px !important; cursor: col-resize;';
    headerRow.find("span.ui-jqgrid-resize").each(function () {
        this.style.cssText = resizeSpanHeight;
    });

    // set position of the dive with the column header text to the middle
    rowHeight = headerRow.height();
    headerRow.find("div.ui-jqgrid-sortable").each(function () {
        var $div = $(this);
        $div.css('top', (rowHeight - $div.outerHeight()) / 2 + 'px');
    });
},
fixPositionsOfFrozenDivs = function () {
    var $rows;
    if (this.grid === undefined) {
        return;
    }
    if (this.grid.fbDiv !== undefined) {
        $rows = $('>div>table.ui-jqgrid-btable>tbody>tr', this.grid.bDiv);
        $('>table.ui-jqgrid-btable>tbody>tr', this.grid.fbDiv).each(function (i) {
            var rowHeight = $($rows[i]).height(), rowHeightFrozen = $(this).height();
            if ($(this).hasClass("jqgrow")) {
                $(this).height(rowHeight);
                rowHeightFrozen = $(this).height();
                if (rowHeight !== rowHeightFrozen) {
                    $(this).height(rowHeight + (rowHeight - rowHeightFrozen));
                }
            }
        });
        $(this.grid.fbDiv).height(this.grid.bDiv.clientHeight + 1);
        $(this.grid.fbDiv).css($(this.grid.bDiv).position());
    }
    if (this.grid.fhDiv !== undefined) {
        $rows = $('>div>table.ui-jqgrid-htable>thead>tr', this.grid.hDiv);
        $('>table.ui-jqgrid-htable>thead>tr', this.grid.fhDiv).each(function (i) {
            var rowHeight = $($rows[i]).height(), rowHeightFrozen = $(this).height();
            $(this).height(rowHeight);
            rowHeightFrozen = $(this).height();
            if (rowHeight !== rowHeightFrozen) {
                $(this).height(rowHeight + (rowHeight - rowHeightFrozen));
            }
        });
        $(this.grid.fhDiv).height(this.grid.hDiv.clientHeight);
        $(this.grid.fhDiv).css($(this.grid.hDiv).position());
    }
},
fixGboxHeight = function () {
    var gviewHeight = $("#gview_" + $.jgrid.jqID(this.id)).outerHeight(),
        pagerHeight = $(this.p.pager).outerHeight();

    $("#gbox_" + $.jgrid.jqID(this.id)).height(gviewHeight + pagerHeight);
};
