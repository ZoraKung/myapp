/**
 * jqGrid Misc
 */

//// -----------------------------------------------
//// R&D
//// -----------------------------------------------
//
//// OK for IE9, Chrome
//function fnExcelReport() {
//    //var templateStart = "<table border='2px'><tr bgcolor='#87AFC6'>";
//    var templateStart = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>Export Report</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>';
//    var templateEnd = '</table></body></html>'
//
//    var tab_text = templateStart;
//    tabHeader = $("table.ui-jqgrid-htable")[0]; // id of table
//    tabData = $("table.ui-jqgrid-btable")[0];
//
//    for (j = 0; j < tabHeader.rows.length; j++) {
//        tab_text = tab_text + "<tr>" + tabHeader.rows[j].innerHTML + "</tr>";
//    }
//
//    for (j = 0; j < tabData.rows.length; j++) {
//        tab_text = tab_text + "<tr>" + tabData.rows[j].innerHTML + "</tr>";
//    }
//
//    tab_text = tab_text + templateEnd;
//
//    tab_text = tab_text.replace(/<A[^>]*>|<\/A>/g, "");//remove if u want links in your table
//    tab_text = tab_text.replace(/<img[^>]*>/gi, ""); // remove if u want images in your table
//    tab_text = tab_text.replace(/<input[^>]*>|<\/input>/gi, ""); // reomves input params
//
//    var ua = window.navigator.userAgent;
//    var msie = ua.indexOf("MSIE ");
//
//    if (msie > 0 || !!navigator.userAgent.match(/Trident.*rv\:11\./))      // If Internet Explorer
//    {
//        txtArea1.document.open("txt/html", "replace");
//        txtArea1.document.charset = "utf-8";
//        txtArea1.document.write(tab_text);
//        txtArea1.document.close();
//        txtArea1.focus();
//        sa = txtArea1.document.execCommand("SaveAs", true, "export.xls");
//    }
//    else                 //other browser not tested on IE 11
//        sa = window.open('data:application/vnd.ms-excel,' + encodeURIComponent(tab_text));
//
//    return (sa);
//}
//
//// ----------------------------
//// Not work in IE9
//// ----------------------------
//function exportToExcel() {
//    var htmls = "";
//    var uri = 'data:application/vnd.ms-excel;base64,';
//
//    var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>';
//    var base64 = function (s) {
//        return window.btoa(unescape(encodeURIComponent(s)))
//    };
//
//    var format = function (s, c) {
//        return s.replace(/{(\w+)}/g, function (m, p) {
//            return c[p];
//        })
//    };
//
//    htmls = "YOUR HTML AS TABLE"
//
//    var ctx = {
//        worksheet: 'Worksheet',
//        table: htmls
//    }
//
//    var link = document.createElement("a");
//    link.download = "export.xls";
//    link.href = uri + base64(format(template, ctx));
//    link.click();
//}
//
//// ----------------------------
//// Not work in IE9
//// ----------------------------
//var tableToExcel = (function () {
//    var uri = 'data:application/vnd.ms-excel;base64,'
//        , template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--><meta http-equiv="content-type" content="text/plain; charset=UTF-8"/></head><body><table>{table}</table></body></html>'
//        , base64 = function (s) {
//            return window.btoa(unescape(encodeURIComponent(s)))
//        }
//        , format = function (s, c) {
//            return s.replace(/{(\w+)}/g, function (m, p) {
//                return c[p];
//            })
//        }
//    return function (table, name) {
//        if (!table.nodeType) table = document.getElementById(table)
//        var ctx = {worksheet: name || 'Worksheet', table: table.innerHTML}
//        window.location.href = uri + base64(format(template, ctx))
//    }
//})()

