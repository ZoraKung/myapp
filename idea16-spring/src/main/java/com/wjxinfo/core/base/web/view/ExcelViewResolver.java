package com.wjxinfo.core.base.web.view;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * Created by Jack on 14-9-23.
 */
public class ExcelViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        ExcelView view = new ExcelView();
        return view;
    }
}
