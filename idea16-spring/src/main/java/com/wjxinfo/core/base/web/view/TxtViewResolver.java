package com.wjxinfo.core.base.web.view;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

/**
 * Created by Carey on 15-4-30.
 */
public class TxtViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        TxtView view = new TxtView();
        return view;
    }
}
