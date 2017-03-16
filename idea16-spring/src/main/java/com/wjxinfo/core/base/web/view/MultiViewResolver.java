package com.wjxinfo.core.base.web.view;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import java.util.Map;

/**
 * Created by Jack on 14-9-23.
 */
public class MultiViewResolver implements ViewResolver {
    private final static String DEFAULT_RESOLVER = "jsp";
    private final static String JSON_RESOLVER = "json";
    private final static String JSON_VIEW_NAME = "jsonView";
    private final static String EXCEL_RESOLVER = "xls";
    private final static String EXCEL_VIEW_NAME = "excelView";
    private final static String XSSF_EXCEL_RESOLVER = "xlsx";
    private final static String XSSF_EXCEL_VIEW_NAME = "xssfExcelView";
    private final static String PDF_RESOLVER = "pdf";
    private final static String PDF_VIEW_NAME = "pdfView";
    private final static String FILE_RESOLVER = "file";
    private final static String FILE_VIEW_NAME = "fileView";
    private final static String TXT_RESOLVER = "txt";
    private final static String TXT_VIEW_NAME = "txtView";
    private Map<String, ViewResolver> resolvers;

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        String tmpViewName = viewName.trim().toLowerCase();
        if (JSON_RESOLVER.equalsIgnoreCase(tmpViewName)) {
            ViewResolver resolver = resolvers.get(JSON_RESOLVER);
            return resolver.resolveViewName(JSON_VIEW_NAME, locale);
        } else if (EXCEL_RESOLVER.equalsIgnoreCase(tmpViewName)) {
            ViewResolver resolver = resolvers.get(EXCEL_RESOLVER);
            return resolver.resolveViewName(EXCEL_VIEW_NAME, locale);
        } else if (XSSF_EXCEL_VIEW_NAME.equalsIgnoreCase(tmpViewName)) {
            ViewResolver resolver = resolvers.get(XSSF_EXCEL_RESOLVER);
            return resolver.resolveViewName(XSSF_EXCEL_VIEW_NAME, locale);
        } else if (PDF_RESOLVER.equalsIgnoreCase(tmpViewName)) {
            ViewResolver resolver = resolvers.get(PDF_RESOLVER);
            return resolver.resolveViewName(PDF_VIEW_NAME, locale);
        } else if (FILE_RESOLVER.equalsIgnoreCase(tmpViewName)) {
            ViewResolver resolver = resolvers.get(FILE_RESOLVER);
            return resolver.resolveViewName(FILE_VIEW_NAME, locale);
        } else if (TXT_RESOLVER.equalsIgnoreCase(tmpViewName)) {
            ViewResolver resolver = resolvers.get(TXT_RESOLVER);
            return resolver.resolveViewName(TXT_VIEW_NAME, locale);
        } else {
            ViewResolver resolver = resolvers.get(DEFAULT_RESOLVER);
            return resolver.resolveViewName(viewName, locale);
        }
    }

    public Map<String, ViewResolver> getResolvers() {
        return resolvers;
    }

    public void setResolvers(Map<String, ViewResolver> resolvers) {
        this.resolvers = resolvers;
    }
}
