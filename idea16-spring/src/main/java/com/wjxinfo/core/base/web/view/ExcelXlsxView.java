package com.wjxinfo.core.base.web.view;

import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.utils.excel.vo.ReportDataVo;
import com.wjxinfo.core.base.utils.excel.vo.ReportHeaderVo;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by Jack on 14-6-20.
 */
public class ExcelXlsxView extends AbstractXSSFExcelView {
    //default column width
    int defaultColumnWidth = 5000;

    //style
    private Map<String, XSSFCellStyle> styles;

    //current row no
    private int rownum = 0;

    @Override
    protected void buildExcelDocument(Map<String, Object> model, XSSFWorkbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String sourceFrom = "jqgrid";

        if (model.get("sourceFrom") != null && !(model.get("sourceFrom") instanceof String)) {
            sourceFrom = (String) model.get("sourceFrom");
        }

        if ((StringUtils.isBlank(sourceFrom) || sourceFrom.equalsIgnoreCase("jqgrid")) && model.containsKey("header")
                && model.containsKey("data")) {
            buildExcelDocumentFromJqGrid(model, workbook, request, response);
        } else {
            buildExcelDocumentFromFieldObject(model, workbook, response);
        }
    }

    private void buildExcelDocumentFromFieldObject(Map<String, Object> model, XSSFWorkbook workbook, HttpServletResponse response) throws Exception {
        String fileName = "export.xlsx";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "inline; filename=" + new String(fileName.getBytes(), "iso8859-1"));

        String title = (String) model.get(Constants.TITLE);
        String subTitle = (String) model.get(Constants.SUBTITLE);
        List<SortedMap<Integer, ReportHeaderVo>> fieldObjects = (List<SortedMap<Integer, ReportHeaderVo>>) model.get
                (Constants.FIELD_OBJECT);
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

        XSSFSheet sheet = workbook.createSheet("Export");
        this.styles = createStyles(workbook);
        // Create title
        if (StringUtils.isNotBlank(title)) {
            XSSFRow titleRow = sheet.createRow(rownum++);
            titleRow.setHeightInPoints(40);
            XSSFCell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);

            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), 0, fieldObjects
                    .get(fieldObjects.size() - 1).size() - 1));
        }
        if (StringUtils.isNotEmpty(subTitle)) {
            XSSFRow subTitleRow = sheet.createRow(rownum++);
            subTitleRow.setHeightInPoints(30);
            XSSFCell subTitleCell = subTitleRow.createCell(0);
            subTitleCell.setCellStyle(styles.get("subTitle"));
            subTitleCell.setCellValue(subTitle);
            sheet.addMergedRegion(new CellRangeAddress(subTitleRow.getRowNum(), subTitleRow.getRowNum(), 0,
                    fieldObjects.get(fieldObjects.size() - 1).size() - 1));
        }
        // Create header
        if (fieldObjects == null) {
            throw new RuntimeException("headerList not null!");
        }
        XSSFRow headerRow;
        for (int i = 0; i < fieldObjects.size(); i++) {
            Map<Integer, ReportHeaderVo> headMap = fieldObjects.get(i);
            headerRow = sheet.createRow(rownum++);
            headerRow.setHeightInPoints(20);
            ReportHeaderVo vo;
            XSSFCell cell;
            int m = 0;
            for (Map.Entry<Integer, ReportHeaderVo> entry : headMap.entrySet()) {
//                for (int j = 0; j < headMap.size(); j++) {
                vo = entry.getValue();
                if (vo != null) {
                    cell = headerRow.createCell(m);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(vo.getTitle());
                    if (vo.getStartCol() < vo.getEndCol()) {
                        CellRangeAddress ca = new CellRangeAddress(headerRow.getRowNum(), headerRow.getRowNum(), vo
                                .getStartCol(), vo.getEndCol());
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
            XSSFRow row = this.addRow(sheet);
            StringBuilder sb = new StringBuilder();
            if (e instanceof SortedMap) {
                for (SortedMap.Entry<Integer, ReportDataVo> entry : ((SortedMap<Integer, ReportDataVo>) e).entrySet()) {
                    ReportDataVo val = entry.getValue();
                    this.addCell(workbook, sheet, row, columnNumber++, val.getValue(), val.getFormat(), val
                            .getFormula(), val.getAlign(), null, val.getStartRow(), val.getEndRow(), val.getStartCol
                            (), val.getEndCol());
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
            XSSFRow row = this.addRow(sheet);
            if (dataList.size() > 0 && dataList.get(0) instanceof SortedMap) {
                for (SortedMap.Entry<Integer, ReportDataVo> entry : ((SortedMap<Integer, ReportDataVo>) dataList.get
                        (0)).entrySet()) {
                    ReportDataVo val = entry.getValue();
                    if (n < startCol) {
                        if (n == 0) {
                            this.addCell(workbook, sheet, row, column++, "Total", val.getFormat(), "", val.getAlign()
                                    , null, 1, 1, 1, 1);
                        } else {
                            this.addCell(workbook, sheet, row, column++, null, val.getFormat(), "", val.getAlign(),
                                    null, 1, 1, 1, 1);
                        }
                    } else if (n >= startCol && n <= endCol) {
                        String formula = "SUM(" + getColChar(n) + firstRow + " : " + getColChar(n) + lastRow + ")";
                        this.addCell(workbook, sheet, row, column++, null, val.getFormat(), formula, val.getAlign(),
                                null, 1, 1, 1, 1);
                    }
                    n++;
                }
            }
        }
    }

    private void buildExcelDocumentFromJqGrid(Map<String, Object> model, XSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (model.get("header") == null || !(model.get("header") instanceof List)) {
            List<String> list = new ArrayList<>();
            list.add("Header undefined");
            model.put("header", list);
        }

        if (model.get("data") == null || !(model.get("data") instanceof List)) {
            List<String> list = new ArrayList<>();
            list.add("Data not found");
            model.put("data", list);
        }

        List<Object> header = (List<Object>) model.get("header");
        List<Object> data = (List<Object>) model.get("data");

        XSSFSheet sheet = workbook.createSheet("sheet 1");

        //Style for header cell
        XSSFCellStyle headerStyle = workbook.createCellStyle();

        headerStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        headerStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);

        XSSFCellStyle dataStyle = workbook.createCellStyle();

        dataStyle.setFillForegroundColor(IndexedColors.WHITE.index);
        //dataStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        dataStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);

        int rowNum = 0;
        int colNum = 0;
        // Create Header Row
        if (header != null && header.size() > 0) {
            List<Object> headerRow = (List<Object>) header.get(0);
            this.createRow(sheet, headerRow, rowNum, headerStyle);
            rowNum++;
            colNum = headerRow.size();
        }

        // Create Data Row
        for (Object dataRowObj : data) {
            List<Object> dataRow = (List<Object>) dataRowObj;
            this.createRow(sheet, dataRow, rowNum, dataStyle);
            rowNum++;
        }

        for (int i = 0; i < colNum; i++) {
            sheet.autoSizeColumn(i, true);
        }
    }

    private void createRow(XSSFSheet sheet, List<Object> cellValues, int rowNum, XSSFCellStyle style) {
        XSSFRow row = sheet.createRow(rowNum);

        int cellIndex = 0;
        for (Object cellValue : cellValues) {
            this.createCell(row, cellValue, cellIndex, style);
            cellIndex++;
        }
    }

    private void createCell(XSSFRow row, Object cellValue, int cellIndex, XSSFCellStyle style) {
        XSSFCell cell = row.createCell(cellIndex);
        if (cellValue != null) {
            cell.setCellValue(cellValue.toString());
        }
        cell.setCellStyle(style);
    }

    private Map<String, XSSFCellStyle> createStyles(XSSFWorkbook wb) {
        Map<String, XSSFCellStyle> styles = new HashMap<String, XSSFCellStyle>();
        //set title style
        XSSFCellStyle style = wb.createCellStyle();
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

    private void setRegionStyle(XSSFSheet sheet, CellRangeAddress ca, CellStyle style) {
        for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) {
                XSSFCell cell = row.getCell(j);
                cell.setCellStyle(style);
            }
        }
    }

    private XSSFRow addRow(XSSFSheet sheet) {
        return sheet.createRow(rownum++);
    }

    private XSSFCell addCell(XSSFWorkbook wb, XSSFSheet sheet, XSSFRow row, int column, Object val, String
            cellFormat, String formula, int align, Class<?> fieldType, int startRow, int endRow, int startCol, int
                                     endCol) {
        XSSFCell cell = row.createCell(column);
        XSSFCellStyle style = styles.get("data" + (align >= 1 && align <= 3 ? align : ""));
        if (StringUtils.isNotEmpty(cellFormat)) {
            XSSFDataFormat format = wb.createDataFormat();
            style.setDataFormat(format.getFormat(cellFormat));
        }
        if (StringUtils.isNotEmpty(formula)) {
            style.setAlignment(CellStyle.ALIGN_RIGHT);
            cell.setCellType(XSSFCell.CELL_TYPE_FORMULA);
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
                    cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((Double) ((BigDecimal) val).doubleValue());
                } else if (val instanceof Date) {
                    cell.setCellValue((Date) val);
                } else if (val instanceof Boolean) {
                    cell.setCellValue((Boolean) val ? "Yes" : "No");
                } else {
                    if (fieldType != Class.class) {
                        cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, val));
                    } else {
                        cell.setCellValue((String) Class.forName(this.getClass().getName().replaceAll(this.getClass()
                                .getSimpleName(), "fieldtype." + val.getClass().getSimpleName() + "Type")).getMethod
                                ("setValue", Object.class).invoke(null, val));
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