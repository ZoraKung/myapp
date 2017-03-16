package com.wjxinfo.core.base.web.view;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.wjxinfo.common.jqgrid.vo.FieldObject;
import com.wjxinfo.core.base.utils.common.DateUtils;
import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.utils.pdf.PdfUtils;
import com.wjxinfo.core.base.utils.reflection_properties.ReflectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Jack on 14-6-20.
 */
public class PdfView extends AbstractITextPdfView {

    private float[] headerWidths = {};

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document,
                                    PdfWriter writer, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String fileName = "export.pdf";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "inline; filename=" + new String(fileName.getBytes(), "iso8859-1"));

        String title = (String) model.get(Constants.TITLE);
        String subTitle = (String) model.get(Constants.SUBTITLE);
        List<FieldObject> fieldObjects = (List<FieldObject>) model.get(Constants.FIELD_OBJECT);
        List dataList = (List) model.get(Constants.DATA_LIST);
        //set title
        if (StringUtils.isNotEmpty(title)) {
            document.add(PdfUtils.setTitle(title, PdfUtils.EN_LARGE_FONT, 10f, 40f, 10f));
        }
        //set sub title
        if (StringUtils.isNotEmpty(subTitle)) {
            document.add(PdfUtils.setTitle(title, PdfUtils.EN_NORMAL_FONT, 6f, 10f, 10f));
        }
        //set header
        if (fieldObjects != null && fieldObjects.size() > 0) {
            setHeaderTable(fieldObjects);
            //set header

            PdfPTable headerTable = PdfUtils.setTable(headerWidths, 20f, 600, 100);
            for (FieldObject fieldObject : fieldObjects) {
                if (!fieldObject.isHidden()) {
                    headerTable.addCell(PdfUtils.setTableHeaderCell(fieldObject.getTitle()));
                }
            }
            document.add(headerTable);
        }
        //set data
        if (fieldObjects != null && fieldObjects.size() > 0 && dataList != null && dataList.size() > 0) {
            PdfPTable dataTable = PdfUtils.setTable(headerWidths, 10f, 600, 98);
            for (Object entity : dataList) {
                for (FieldObject fieldObject : fieldObjects) {
                    if (!fieldObject.isHidden()) {
                        String fieldName = fieldObject.getName();
                        Object fieldValue = ReflectionUtils.getFieldValue(entity, fieldName);
                        String dataValue = "";
                        int align = 0;
                        if (fieldValue != null) {
                            dataValue = fieldValue.toString();
                            String formatter = "";
                            if (fieldValue instanceof java.sql.Date) {
                                align = 1;
                                formatter = DateUtils.YYYY_MM_DD;
                                dataValue = DateUtils.format((Date) fieldValue, formatter);
                            } else if (fieldValue instanceof java.sql.Timestamp) {
                                align = 1;
                                formatter = DateUtils.YYYY_MM_DD_HH_MM_SS;
                                dataValue = DateUtils.format((Date) fieldValue, formatter);
                            } else if (fieldValue instanceof Integer || fieldValue instanceof BigDecimal
                                    || fieldValue instanceof Float || fieldValue instanceof Double) {
                                align = 2;
                            }
                        }
                        dataTable.addCell(PdfUtils.setCell(dataValue, align));
                    }
                }
            }
            document.add(dataTable);
        }
    }

    private void setHeaderTable(List<FieldObject> fieldObjects) {
        if (fieldObjects != null && fieldObjects.size() > 0) {
            this.headerWidths = new float[getNonHiddenSize(fieldObjects)];
            for (int i = 0; i < fieldObjects.size(); i++) {
                if (!fieldObjects.get(i).isHidden()) {
                    if (fieldObjects.get(i).getWidth() != null) {
                        headerWidths[i] = (float) (fieldObjects.get(i).getWidth());
                    } else {
                        headerWidths[i] = 100f;
                    }
                }
            }
        }
    }

    private Integer getNonHiddenSize(List<FieldObject> fieldObjects) {
        Integer size = 0;
        for (int i = 0; i < fieldObjects.size(); i++) {
            if (!fieldObjects.get(i).isHidden()) {
                size += 1;
            }
        }
        return size;
    }
}
