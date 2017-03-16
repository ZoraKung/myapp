package com.wjxinfo.core.base.web.view;

import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.utils.excel.vo.ReportDataVo;
import com.wjxinfo.core.base.utils.excel.vo.ReportHeaderVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Jack on 14-6-20.
 */
public class ExcelView extends AbstractExcelView {
    //default column width
    int defaultColumnWidth = 5000;

    //style
    private Map<String, HSSFCellStyle> styles;

    //current row no
    private int rownum = 0;

    @Override
    protected void buildExcelDocument(Map<String, Object> model,
                                      HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String fileName = "export.xls";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "inline; filename=" + new String(fileName.getBytes(), "iso8859-1"));

        String title = (String) model.get(Constants.TITLE);
        String subTitle = (String) model.get(Constants.SUBTITLE);
        List<SortedMap<Integer, ReportHeaderVo>> fieldObjects = (List<SortedMap<Integer, ReportHeaderVo>>) model.get(Constants.FIELD_OBJECT);
        List dataList = (List) model.get(Constants.DATA_LIST);
        String startColString = (String) model.get(Constants.START_COL);
        String endColString = (String) model.get(Constants.END_COL);
        int startCol = 1;
        int endCol = 1;
        rownum = 0;
        if (StringUtils.isNotEmpty(startColString)) {
            startCol = Integer.parseInt(startColString);
        }
        if (StringUtils.isNotEmpty(endColString)) {
            endCol = Integer.parseInt(endColString);
        }

        HSSFSheet sheet = workbook.createSheet("Export");
        this.styles = createStyles(workbook);
        // Create title
        if (StringUtils.isNotBlank(title)) {
            HSSFRow titleRow = sheet.createRow(rownum++);
            titleRow.setHeightInPoints(40);
            HSSFCell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);

            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
                    titleRow.getRowNum(), 0, fieldObjects.get(fieldObjects.size() - 1).size() - 1));
        }
        if (StringUtils.isNotEmpty(subTitle)) {
            HSSFRow subTitleRow = sheet.createRow(rownum++);
            subTitleRow.setHeightInPoints(30);
            HSSFCell subTitleCell = subTitleRow.createCell(0);
            subTitleCell.setCellStyle(styles.get("subTitle"));
            subTitleCell.setCellValue(subTitle);
            sheet.addMergedRegion(new CellRangeAddress(subTitleRow.getRowNum(),
                    subTitleRow.getRowNum(), 0, fieldObjects.get(fieldObjects.size() - 1).size() - 1));
        }
        // Create header
        if (fieldObjects == null) {
            throw new RuntimeException("headerList not null!");
        }
        HSSFRow headerRow;
        for (int i = 0; i < fieldObjects.size(); i++) {
            Map<Integer, ReportHeaderVo> headMap = fieldObjects.get(i);
            headerRow = sheet.createRow(rownum++);
            headerRow.setHeightInPoints(20);
            ReportHeaderVo vo;
            HSSFCell cell;
            int m = 0;
            for (Map.Entry<Integer, ReportHeaderVo> entry : headMap.entrySet()) {
//                for (int j = 0; j < headMap.size(); j++) {
                vo = entry.getValue();
                if (vo != null) {
                    cell = headerRow.createCell(m);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(vo.getTitle());
                    if (vo.getStartCol() < vo.getEndCol()) {
                        CellRangeAddress ca = new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), vo.getStartCol(), vo.getEndCol());
                        sheet.addMergedRegion(ca);
                        setRegionStyle(sheet, ca, styles.get("joinHeader"));
                        m = vo.getEndCol();
                    } else {
                        cell.setCellStyle(styles.get("tableHeader"));
                    }
                    if (vo.getWidth() != 0) {
                        sheet.setColumnWidth(i, vo.getWidth());
                    } else {
                        sheet.setColumnWidth(i, defaultColumnWidth);
                    }
                    m++;
                }
            }
        }
        //set data
        int firstRow = 1;
        int lastRow = 1;
        int m = 0;
        for (Object e : dataList) {
            int columnNumber = 0;
            HSSFRow row = this.addRow(sheet);
            StringBuilder sb = new StringBuilder();
            if (e instanceof SortedMap) {
                for (SortedMap.Entry<Integer, ReportDataVo> entry : ((SortedMap<Integer, ReportDataVo>) e).entrySet()) {
                    ReportDataVo val = entry.getValue();
                    this.addCell(workbook, sheet, row, columnNumber++, val.getValue(), val.getFormat(), val.getFormula(), val.getAlign(), null, val.getStartRow(), val.getEndRow(), val.getStartCol(), val.getEndCol());
                    sb.append(val + ", ");
                }
            }
            m++;
            if (m == 1) {
                firstRow = row.getRowNum() + 1;
            } else {
                lastRow = row.getRowNum() + 1;
            }
        }
        if (startCol < endCol) {
            int column = 0;
            int n = 0;
            HSSFRow row = this.addRow(sheet);
            if (dataList.size() > 0 && dataList.get(0) instanceof SortedMap) {
                for (SortedMap.Entry<Integer, ReportDataVo> entry : ((SortedMap<Integer, ReportDataVo>) dataList.get(0)).entrySet()) {
                    ReportDataVo val = entry.getValue();
                    if (n < startCol) {
                        if (n == 0) {
                            this.addCell(workbook, sheet, row, column++, "Total", val.getFormat(), "", val.getAlign(), null, 1, 1, 1, 1);
                        } else {
                            this.addCell(workbook, sheet, row, column++, null, val.getFormat(), "", val.getAlign(), null, 1, 1, 1, 1);
                        }
                    } else if (n >= startCol && n <= endCol) {
                        String formula = "SUM(" + getColChar(n) + firstRow + " : " + getColChar(n) + lastRow + ")";
                        this.addCell(workbook, sheet, row, column++, null, val.getFormat(), formula, val.getAlign(), null, 1, 1, 1, 1);
                    }
                    n++;
                }
            }
        }
    }

    private Map<String, HSSFCellStyle> createStyles(HSSFWorkbook wb) {
        Map<String, HSSFCellStyle> styles = new HashMap<String, HSSFCellStyle>();
        //set title style
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Tahoma");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(titleFont);
        styles.put("title", style);
        //set sub title style
        style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_LEFT);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font subTitleFont = wb.createFont();
        subTitleFont.setFontName("Tahoma");
        subTitleFont.setFontHeightInPoints((short) 10);
        subTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(subTitleFont);
        styles.put("subTitle", style);
        //set data style
        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Tahoma");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        styles.put("data", style);
        //set data style(left align)
        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_LEFT);
        styles.put("data1", style);
        //set data style(center align)
        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_CENTER);
        styles.put("data2", style);
        //set data style(right align)
        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        styles.put("data3", style);
        //set header style
        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Tahoma");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("tableHeader", style);

        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerFont = wb.createFont();
        headerFont.setFontName("Tahoma");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("joinHeader", style);

        return styles;
    }

    private void setRegionStyle(HSSFSheet sheet, CellRangeAddress ca, CellStyle style) {
        for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) {
            HSSFRow row = HSSFCellUtil.getRow(i, sheet);
            for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) {
                HSSFCell cell = HSSFCellUtil.getCell(row, j);
                cell.setCellStyle(style);
            }
        }
    }

    private HSSFRow addRow(HSSFSheet sheet) {
        return sheet.createRow(rownum++);
    }

    private HSSFCell addCell(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, int column, Object val, String cellFormat, String formula, int align, Class<?> fieldType, int startRow, int endRow, int startCol, int endCol) {
        HSSFCell cell = row.createCell(column);
        HSSFCellStyle style = styles.get("data" + (align >= 1 && align <= 3 ? align : ""));
        if (StringUtils.isNotEmpty(cellFormat)) {
            HSSFDataFormat format = wb.createDataFormat();
            style.setDataFormat(format.getFormat(cellFormat));
        }
        if (StringUtils.isNotEmpty(formula)) {
            style.setAlignment(CellStyle.ALIGN_RIGHT);
            cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
            cell.setCellFormula(formula);
        } else {
            try {
                if (val == null) {
                    cell.setCellValue("");
                } else if (val instanceof String) {
                    cell.setCellValue((String) val);
                } else if (val instanceof Integer) {
                    cell.setCellValue((Integer) val);
                } else if (val instanceof Long) {
                    cell.setCellValue((Long) val);
                } else if (val instanceof Double) {
                    cell.setCellValue((Double) val);
                } else if (val instanceof Float) {
                    cell.setCellValue((Float) val);
                } else if (val instanceof BigDecimal) {
                    cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((Double) ((BigDecimal) val).doubleValue());
                } else if (val instanceof Date) {
                    cell.setCellValue((Date) val);
                } else if (val instanceof Boolean) {
                    cell.setCellValue((Boolean) val ? "Yes" : "No");
                } else {
                    if (fieldType != Class.class) {
                        cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, val));
                    } else {
                        cell.setCellValue((String) Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
                                "fieldtype." + val.getClass().getSimpleName() + "Type")).getMethod("setValue", Object.class).invoke(null, val));
                    }
                }
            } catch (Exception ex) {
                //log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
                cell.setCellValue(val.toString());
            }
        }
        if ((startCol < endCol) && (startRow < endRow)) {
            CellRangeAddress ca = new CellRangeAddress(startRow, endRow, startCol, endCol);
            sheet.addMergedRegion(ca);
            setRegionStyle(sheet, ca, style);
        } else if (startCol < endCol) {
            CellRangeAddress ca = new CellRangeAddress(row.getRowNum(), row.getRowNum(), startCol, endCol);
            sheet.addMergedRegion(ca);
            setRegionStyle(sheet, ca, style);
        } else if (startRow < endRow) {
            CellRangeAddress ca = new CellRangeAddress(startRow, endRow, column, column);
            sheet.addMergedRegion(ca);
            setRegionStyle(sheet, ca, style);
        } else {
            cell.setCellStyle(style);
        }
        return cell;
    }

    private String getColChar(int col) {
        return String.valueOf((char) (col + 65));
    }
}