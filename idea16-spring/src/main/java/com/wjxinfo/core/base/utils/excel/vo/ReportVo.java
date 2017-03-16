package com.wjxinfo.core.base.utils.excel.vo;


import java.util.List;
import java.util.SortedMap;

public class ReportVo {
    private List<SortedMap<Integer, ReportHeaderVo>> headerList;

    private List<SortedMap<Integer, ReportDataVo>> dataList;

    public static ReportHeaderVo getReportHeaderVo(String fieldName, String title, int startRow, int endRow, int startCol, int endCol) {
        return getReportHeaderVo(fieldName, title, startRow, endRow, startCol, endCol, 0);
    }

    public static ReportHeaderVo getReportHeaderVo(String fieldName, String title, int startRow, int endRow, int startCol, int endCol, int width) {
        ReportHeaderVo vo = new ReportHeaderVo();
        vo.setFieldName(fieldName);
        vo.setTitle(title);
        vo.setStartRow(startRow);
        vo.setEndRow(endRow);
        vo.setStartCol(startCol);
        vo.setEndCol(endCol);
        vo.setWidth(width);
        return vo;
    }

    public static ReportDataVo getReportDataVo(String fieldName, Object fieldValue, int align, int sort, String format) {
        return getReportDataVo(fieldName, fieldValue, align, sort, format, "", 1, 1, 1, 1);
    }

    public static ReportDataVo getReportDataVo(String fieldName, Object fieldValue, int align, int sort, String format, String formula) {
        return getReportDataVo(fieldName, fieldValue, align, sort, format, formula, 1, 1, 1, 1);
    }

    public static ReportDataVo getReportDataVo(String fieldName, Object fieldValue, int align, int sort, String format, String formula, int startRow, int endRow, int startCol, int endCol) {
        ReportDataVo vo = new ReportDataVo();
        vo.setName(fieldName);
        vo.setValue(fieldValue);
        vo.setAlign(align);
        vo.setSort(sort);
        vo.setFormat(format);
        vo.setFormula(formula);
        vo.setStartRow(startRow);
        vo.setEndRow(endRow);
        vo.setStartCol(startCol);
        vo.setEndCol(endCol);
        return vo;
    }

    public static String getColString(int col) {
        return String.valueOf((char) (65 + col));
    }

    public List<SortedMap<Integer, ReportHeaderVo>> getHeaderList() {
        return headerList;
    }

    public void setHeaderList(List<SortedMap<Integer, ReportHeaderVo>> headerList) {
        this.headerList = headerList;
    }

    public List<SortedMap<Integer, ReportDataVo>> getDataList() {
        return dataList;
    }

    public void setDataList(List<SortedMap<Integer, ReportDataVo>> dataList) {
        this.dataList = dataList;
    }
}
