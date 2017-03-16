package com.wjxinfo.core.base.utils.excel.utils;

import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.utils.excel.annotation.ExcelField;
import com.wjxinfo.core.base.utils.excel.vo.ReportDataVo;
import com.wjxinfo.core.base.utils.excel.vo.ReportHeaderVo;
import com.wjxinfo.core.base.utils.reflection_properties.ReflectionUtils;
import com.wjxinfo.core.base.utils.security.EncodeUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;

/**
 * Export Excel
 */
public class ExportExcel {

    private static Logger log = LoggerFactory.getLogger(ExportExcel.class);

    //annotation list(Object[]{ExcelField, field/method)
    List<Object[]> annotationList = new ArrayList<Object[]>();

    //defalult column width
    int defaultColumnWidth = 3000;

    //workboot
    private SXSSFWorkbook wb;

    private HSSFWorkbook wbExcel97;

    //worksheet
    private Sheet sheet;

    //style
    private Map<String, CellStyle> styles;

    //current row no
    private int rownum;

    /**
     * construct function
     *
     * @param cls : entity object
     */
    public ExportExcel(Class<?> cls) {
        this(null, "", false, cls, 1);
    }

    public ExportExcel(String title, Class<?> cls) {
        this(title, "", false, cls, 1);
    }

    public ExportExcel(String title, Class<?> cls, int type) {
        this(title, "", false, cls, type);
    }

    public ExportExcel(String title, String subTitle, Class<?> cls) {
        this(title, subTitle, false, cls, 1);
    }

    public ExportExcel(String title, Class<?> cls, boolean isExcel97) {
        this(title, "", isExcel97, cls, 1);
    }

    /**
     * construct function
     *
     * @param title  : excel title
     * @param cls    : entity object
     * @param type   : export type(1:export data; 2-export template)
     * @param groups : import group
     */
    public ExportExcel(String title, String subTitle, boolean isExcel97, Class<?> cls, int type, int... groups) {
        // Get annotation field
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, f});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, f});
                }
            }
        }
        // Get annotation method
        Method[] ms = cls.getDeclaredMethods();
        for (Method m : ms) {
            ExcelField ef = m.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                if (groups != null && groups.length > 0) {
                    boolean inGroup = false;
                    for (int g : groups) {
                        if (inGroup) {
                            break;
                        }
                        for (int efg : ef.groups()) {
                            if (g == efg) {
                                inGroup = true;
                                annotationList.add(new Object[]{ef, m});
                                break;
                            }
                        }
                    }
                } else {
                    annotationList.add(new Object[]{ef, m});
                }
            }
        }
        // Field sorting
        Collections.sort(annotationList, new Comparator<Object[]>() {
            public int compare(Object[] o1, Object[] o2) {
                return new Integer(((ExcelField) o1[0]).sort()).compareTo(
                        new Integer(((ExcelField) o2[0]).sort()));
            }

            ;
        });
        // Initialize
        List<String> headerList = new ArrayList<String>();
        for (Object[] os : annotationList) {
            String t = ((ExcelField) os[0]).title();
            // if export template, delete annotation
            if (type == 1) {
                String[] ss = StringUtils.split(t, "**", 2);
                if (ss.length == 2) {
                    t = ss[0];
                }
            }
            headerList.add(t);
        }
        initialize(title, subTitle, isExcel97, headerList);
    }

    /**
     * construct function
     *
     * @param title   : excel title
     * @param headers : excel headers
     */
    public ExportExcel(String title, String[] headers) {
        initialize(title, "", false, Arrays.asList(headers));
    }

    public ExportExcel(String title, String subTitle, List<SortedMap<Integer, ReportHeaderVo>> headerList) {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = wb.createSheet("Export");
        this.styles = createStyles(wb);
        // Create title
        if (StringUtils.isNotBlank(title)) {
            Row titleRow = sheet.createRow(rownum++);
            titleRow.setHeightInPoints(40);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
                    titleRow.getRowNum(), 0, headerList.get(headerList.size() - 1).size() - 1));
        }
        if (StringUtils.isNotEmpty(subTitle)) {
            Row subTitleRow = sheet.createRow(rownum++);
            subTitleRow.setHeightInPoints(30);
            Cell subTitleCell = subTitleRow.createCell(0);
            subTitleCell.setCellStyle(styles.get("subTitle"));
            subTitleCell.setCellValue(subTitle);
            sheet.addMergedRegion(new CellRangeAddress(subTitleRow.getRowNum(),
                    subTitleRow.getRowNum(), 0, headerList.get(headerList.size() - 1).size() - 1));
        }
        // Create header
        if (headerList == null) {
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow;
        for (int i = 0; i < headerList.size(); i++) {
            Map<Integer, ReportHeaderVo> headMap = headerList.get(i);
            headerRow = sheet.createRow(rownum++);
            headerRow.setHeightInPoints(20);
            ReportHeaderVo vo;
            Cell cell;
            int m = 0;
            for (int j = 0; j < headMap.size(); j++) {
                vo = headMap.get(j);
                if (vo != null) {
                    cell = headerRow.createCell(m);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
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
/*        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }*/
        log.debug("Initialize success.");
    }

    /**
     * construct function
     *
     * @param title
     * @param subTitle
     * @param headerList
     */
/*    public ExportExcel(String title, String subTitle, List<String> headerList) {
        initialize(title, subTitle, headerList);
    }*/

    /**
     * set region cell style
     *
     * @param sheet
     * @param ca
     * @param style
     */
    public static void setRegionStyle(Sheet sheet, CellRangeAddress ca, CellStyle style) {
        for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) {
            Row row = CellUtil.getRow(i, sheet);
            for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) {
                Cell cell = CellUtil.getCell(row, j);
                cell.setCellStyle(style);
            }
        }
    }

    /**
     * initialize function
     *
     * @param title
     * @param headerList
     */
    private void initialize(String title, String subTitle, boolean isExcel97, List<String> headerList) {
        if (isExcel97) {
            this.wbExcel97 = new HSSFWorkbook();
            this.sheet = wbExcel97.createSheet("Export");
            this.styles = createStyles(wbExcel97);
        } else {
            this.wb = new SXSSFWorkbook(500);
            this.sheet = wb.createSheet("Export");
            this.styles = createStyles(wb);
        }
        // Create title
        if (StringUtils.isNotBlank(title)) {
            Row titleRow = sheet.createRow(rownum++);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(),
                    titleRow.getRowNum(), 0, headerList.size() - 1));
        }
        if (StringUtils.isNotEmpty(subTitle)) {
            Row subTitleRow = sheet.createRow(rownum++);
            subTitleRow.setHeightInPoints(20);
            Cell subTitleCell = subTitleRow.createCell(0);
            subTitleCell.setCellStyle(styles.get("subTitle"));
            subTitleCell.setCellValue(subTitle);
            sheet.addMergedRegion(new CellRangeAddress(subTitleRow.getRowNum(),
                    subTitleRow.getRowNum(), 0, headerList.size() - 1));
        }
        // Create header
        if (headerList == null) {
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(16);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styles.get("tableHeader"));
            String[] ss = StringUtils.split(headerList.get(i), "**", 2);
            if (ss.length == 2) {
                cell.setCellValue(ss[0]);
                Comment comment = this.sheet.createDrawingPatriarch().createCellComment(
                        new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
                comment.setString(new XSSFRichTextString(ss[1]));
                cell.setCellComment(comment);
            } else {
                cell.setCellValue(headerList.get(i));
            }
            sheet.setColumnWidth(i, defaultColumnWidth);
        }
/*        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }*/
        log.debug("Initialize success.");
    }

    /**
     * set excel style
     *
     * @param wb
     * @return
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        //set title style
        CellStyle tstyle = wb.createCellStyle();
        tstyle.setAlignment(CellStyle.ALIGN_CENTER);
        tstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Tahoma");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        tstyle.setFont(titleFont);
        styles.put("title", tstyle);

        //set sub title style
        CellStyle sstyle = wb.createCellStyle();
        sstyle.setAlignment(CellStyle.ALIGN_LEFT);
        sstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font subTitleFont = wb.createFont();
        subTitleFont.setFontName("Tahoma");
        subTitleFont.setFontHeightInPoints((short) 10);
        subTitleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        sstyle.setFont(subTitleFont);
        styles.put("subTitle", sstyle);

        //set data style
        CellStyle dstyle = wb.createCellStyle();
        dstyle.setWrapText(true);
        dstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        dstyle.setBorderRight(CellStyle.BORDER_THIN);
        dstyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        dstyle.setBorderLeft(CellStyle.BORDER_THIN);
        dstyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        dstyle.setBorderTop(CellStyle.BORDER_THIN);
        dstyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        dstyle.setBorderBottom(CellStyle.BORDER_THIN);
        dstyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Tahoma");
        dataFont.setFontHeightInPoints((short) 10);
        dstyle.setFont(dataFont);
        styles.put("data", dstyle);

        //set data style(left align)
        CellStyle d1style = wb.createCellStyle();
        d1style.cloneStyleFrom(styles.get("data"));
        d1style.setAlignment(CellStyle.ALIGN_LEFT);
        styles.put("data1", d1style);

        //set data style(center align)
        CellStyle d2style = wb.createCellStyle();
        d2style.cloneStyleFrom(styles.get("data"));
        d2style.setAlignment(CellStyle.ALIGN_CENTER);
        styles.put("data2", d2style);

        //set data style(right align)
        CellStyle d3style = wb.createCellStyle();
        d3style.cloneStyleFrom(styles.get("data"));
        d3style.setAlignment(CellStyle.ALIGN_RIGHT);
        styles.put("data3", d3style);

        //set data "Date" style (left align)
        CellStyle dtstyle = wb.createCellStyle();
        dtstyle.cloneStyleFrom(styles.get("data"));
        dtstyle.setAlignment(CellStyle.ALIGN_LEFT);
        dtstyle.setDataFormat(wb.createDataFormat().getFormat("dd-mm-yyyy"));
        styles.put("dataDate", dtstyle);

        //set header style
        CellStyle hstyle = wb.createCellStyle();
        hstyle.setWrapText(true);
        hstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        hstyle.setBorderRight(CellStyle.BORDER_THIN);
        hstyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        hstyle.setBorderLeft(CellStyle.BORDER_THIN);
        hstyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        hstyle.setBorderTop(CellStyle.BORDER_THIN);
        hstyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        hstyle.setBorderBottom(CellStyle.BORDER_THIN);
        hstyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //style.setWrapText(true);
        hstyle.setAlignment(CellStyle.ALIGN_CENTER);
        hstyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        hstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Tahoma");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        hstyle.setFont(headerFont);
        styles.put("tableHeader", hstyle);

        CellStyle jstyle = wb.createCellStyle();
        jstyle.setWrapText(true);
        jstyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        jstyle.setBorderRight(CellStyle.BORDER_THIN);
        jstyle.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        jstyle.setBorderLeft(CellStyle.BORDER_THIN);
        jstyle.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        jstyle.setBorderTop(CellStyle.BORDER_THIN);
        jstyle.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        jstyle.setBorderBottom(CellStyle.BORDER_THIN);
        jstyle.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        //style.setWrapText(true);
        jstyle.setAlignment(CellStyle.ALIGN_CENTER);
        jstyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        jstyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headerFont = wb.createFont();
        headerFont.setFontName("Tahoma");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        jstyle.setFont(headerFont);
        styles.put("joinHeader", jstyle);

        return styles;
    }

    /**
     * add row
     *
     * @return
     */
    public Row addRow() {
        return sheet.createRow(rownum++);
    }


    /**
     * add cell
     *
     * @param row
     * @param column
     * @param val
     * @return
     */
    public Cell addCell(Row row, int column, Object val) {
        return this.addCell(row, column, val, "", "", 0, Class.class);
    }

    /**
     * add cell
     *
     * @param row
     * @param column
     * @param val
     * @param cellFormat
     * @return
     */
    public Cell addCell(Row row, int column, Object val, String cellFormat) {
        return this.addCell(row, column, val, cellFormat, "", 0, Class.class);
    }

    public Cell addCell(Row row, int column, String cellFormat, String formula) {
        return this.addCell(row, column, null, cellFormat, formula, 0, Class.class);
    }

    public Cell addCell(Row row, int column, Object val, String cellFormat, String formula, int align, Class<?> fieldType) {
        return this.addCell(row, column, val, cellFormat, formula, align, fieldType, 1, 1, 1, 1);
    }

    /**
     * add cell
     *
     * @param row
     * @param column
     * @param val
     * @param align
     * @param fieldType
     * @return
     */
    public Cell addCell(Row row, int column, Object val, String cellFormat, String formula, int align, Class<?> fieldType, int startRow, int endRow, int startCol, int endCol) {
        Cell cell = row.createCell(column);
        CellStyle style = styles.get("data" + (align >= 1 && align <= 3 ? align : ""));
        if (StringUtils.isNotEmpty(cellFormat)) {
            DataFormat format = wb.createDataFormat();
            style.setDataFormat(format.getFormat(cellFormat));
        }
        if (StringUtils.isNotEmpty(formula)) {
            style.setAlignment(CellStyle.ALIGN_RIGHT);
            cell.setCellType(Cell.CELL_TYPE_FORMULA);
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
                    cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    cell.setCellValue((Double) ((BigDecimal) val).doubleValue());
                } else if (val instanceof Date) {
                    style = styles.get("dataDate");
                    if (StringUtils.isNotEmpty(cellFormat)) {
                        style.setDataFormat(wb.createDataFormat().getFormat(cellFormat));
                    }
                    cell.setCellValue((Date) val);
                } else {
                    if (fieldType != Class.class) {
                        cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, val));
                    } else {
                        cell.setCellValue((String) Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
                                "fieldtype." + val.getClass().getSimpleName() + "Type")).getMethod("setValue", Object.class).invoke(null, val));
                    }
                }
            } catch (Exception ex) {
                log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
                cell.setCellValue(val.toString());
            }
        }

        cell.setCellStyle(style);

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
        }
        return cell;
    }

    public <E> ExportExcel setDataList(List<E> list) {
        return this.setDataList(list, 1, 1);
    }

    public <E> ExportExcel setDataList(List<E> list, int startCol, int endCol) {
        return setDataList(list, startCol, endCol, false);
    }

    /**
     * set data
     *
     * @param list
     * @param <E>
     * @return
     */
    public <E> ExportExcel setDataList(List<E> list, int startCol, int endCol, boolean removeHeaderLine) {
        int firstRow = 1;
        int lastRow = 1;
        int m = 0;
        for (E e : list) {
            int colunm = 0;
            Row row;
            if (removeHeaderLine && m == 0) {
                row = sheet.getRow(sheet.getLastRowNum());
            } else {
                row = this.addRow();
            }
            StringBuilder sb = new StringBuilder();
            if (annotationList == null || (annotationList != null && annotationList.size() == 0)) {
                if (e instanceof SortedMap) {
                    for (SortedMap.Entry<Integer, ReportDataVo> entry : ((SortedMap<Integer, ReportDataVo>) e).entrySet()) {
                        ReportDataVo val = entry.getValue();
                        this.addCell(row, colunm++, val.getValue(), val.getFormat(), val.getFormula(), val.getAlign(), null, val.getStartRow(), val.getEndRow(), val.getStartCol(), val.getEndCol());
                        sb.append(val + ", ");
                    }
                }
            } else {
                for (Object[] os : annotationList) {
                    ExcelField ef = (ExcelField) os[0];
                    if (StringUtils.isNotEmpty(ef.formula())) {
                        String formula = ef.formula().replace("row", String.valueOf(row.getRowNum() + 1));
                        this.addCell(row, colunm++, null, ef.format(), formula, ef.align(), null);
                    } else {
                        Object val = null;
                        // Get entity value
                        try {
                            if (StringUtils.isNotBlank(ef.value())) {
                                val = ReflectionUtils.invokeGetter(e, ef.value());
                            } else {
                                if (os[1] instanceof Field) {
                                    val = ReflectionUtils.invokeGetter(e, ((Field) os[1]).getName());
                                } else if (os[1] instanceof Method) {
                                    val = ReflectionUtils.invokeMethod(e, ((Method) os[1]).getName(), new Class[]{}, new Object[]{});
                                }
                            }
                            // If is dict, get dict label
//                            if (StringUtils.isNotBlank(ef.dictType())) {
//                                val = DictionaryUtils.getDictionaryLabel(val == null ? "" : val.toString(), ef.dictType(), "");
//                            }
                        } catch (Exception ex) {
                            // Failure to ignore
                            log.info(ex.toString());
                            val = "";
                        }
                        this.addCell(row, colunm++, val, ef.format(), "", ef.align(), ef.fieldType());
                        sb.append(val + ", ");
                    }
                }
            }
            m++;
            if (m == 1) {
                firstRow = row.getRowNum() + 1;
            } else {
                lastRow = row.getRowNum() + 1;
            }
            log.debug("Write success: [" + row.getRowNum() + "] " + sb.toString());
        }
        if (startCol < endCol) {
            int column = 0;
            int n = 0;
            Row row = this.addRow();
            if (annotationList == null || (annotationList != null && annotationList.size() == 0)) {
                if (list.size() > 0 && list.get(0) instanceof SortedMap) {
                    for (SortedMap.Entry<Integer, ReportDataVo> entry : ((SortedMap<Integer, ReportDataVo>) list.get(0)).entrySet()) {
                        ReportDataVo val = entry.getValue();
                        if (n < startCol) {
                            if (n == 0) {
                                this.addCell(row, column++, "Total", val.getFormat(), "", val.getAlign(), null);
                            } else {
                                this.addCell(row, column++, null, val.getFormat(), "", val.getAlign(), null);
                            }
                        } else if (n >= startCol && n <= endCol) {
                            String formula = "SUM(" + getColChar(n) + firstRow + " : " + getColChar(n) + lastRow + ")";
                            this.addCell(row, column++, null, val.getFormat(), formula, val.getAlign(), null);
                        }
                        n++;
                    }
                }
            } else {
                for (Object[] os : annotationList) {
                    ExcelField ef = (ExcelField) os[0];
                    if (n < startCol) {
                        if (n == 0) {
                            this.addCell(row, column++, "Total", ef.format(), "", ef.align(), null);
                        } else {
                            this.addCell(row, column++, null, ef.format(), "", ef.align(), null);
                        }
                    } else if (n >= startCol && n <= endCol) {
                        String formula = "SUM(" + getColChar(n) + firstRow + " : " + getColChar(n) + lastRow + ")";
                        this.addCell(row, column++, null, ef.format(), formula, ef.align(), null);
                    }
                    n++;
                }
            }
        }
        return this;
    }

    private String getColChar(int col) {
        return String.valueOf((char) (col + 65));
    }

    /**
     * output data stream
     *
     * @param os
     * @return
     * @throws IOException
     */
    public ExportExcel write(OutputStream os) throws IOException {
        wb.write(os);
        return this;
    }

    public ExportExcel write(OutputStream os, boolean isExcel97) throws IOException {
        if (isExcel97) {
            wbExcel97.write(os);
        } else {
            wb.write(os);
        }
        return this;
    }

    /**
     * output to client
     *
     * @param response
     * @param fileName
     * @return
     * @throws IOException
     */
    public ExportExcel write(HttpServletResponse response, String fileName) throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        /*response.setHeader("Content-Disposition", "attachment; filename=" + fileName);*/
        response.setHeader("Content-Disposition", "attachment; filename=" + EncodeUtils.urlEncode(fileName));
        write(response.getOutputStream());
        return this;
    }

    /**
     * output to file
     *
     * @param name
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ExportExcel writeFile(String name) throws FileNotFoundException, IOException {
        FileOutputStream os = new FileOutputStream(name);
        this.write(os);
        return this;
    }

    public ExportExcel writeFile(String name, boolean isExcel97) throws FileNotFoundException, IOException {
        FileOutputStream os = new FileOutputStream(name);
        if (isExcel97) {
            this.write(os, isExcel97);
        } else {
            this.write(os);
        }
        return this;
    }

    /**
     * clean temp file
     */
    public ExportExcel dispose() {
        wb.dispose();
        return this;
    }
}
