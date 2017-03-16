package com.wjxinfo.core.base.web.controller;

import com.wjxinfo.core.base.utils.common.DateUtils;
import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.utils.bean.validators.BeanValidators;
import com.wjxinfo.core.base.web.utils.I18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Base Controller
 */
public abstract class BaseController {

    private static final String ATT_VERSION = "version";
/*
    @Autowired
    private MessageSource messageSource;
*/

    //Tag
    protected static String ATT_SEARCH_FLAG = "searched";

    protected static String ATT_SEARCH_RESULT = "searchedResult";    //True--Find, Flase--No Find

    protected static String ATT_SEARCH_MAP = "searchMap";

    protected static String ATT_PAGE_MAP = "pageMap";

    protected static String ATT_IDS = "ids";

    protected static String ATT_SELECT_STRING = "idsString";

    protected static String ATT_SEARCH_FORM = "searchForm";

    protected static String ATT_SEARCH_CRITERIA = "searchCriteria";

    protected static String ATT_MESSAGE = "messages";

    protected static String ATT_ERROR = "errors";

    protected static String PAGE_STATUS = "pageStatus";

    //use in jqgrid inline edit
    protected static String JQGRID_OPER = "oper";

    protected static String JQGRID_ADD = "add";

    protected static String JQGRID_EDIT = "edit";

    protected static String JQGRID_DEL = "del";

    protected static String ADVANCE_SEARCH = "advance";
    /**
     * Validator
     */
    @Autowired
    protected Validator validator;

    //Log object
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

/*
    protected static Locale defaultLocal = new Locale("en");
*/

    /**
     * Server Validator Bean
     *
     * @param model  : Model
     * @param object : Object
     * @param groups : Groups
     * @return : True / False
     */
    protected boolean beanValidators(Model model, Object object, Class<?>... groups) {
        try {
            logger.info("validatiWithException");
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            logger.info("ConstraintViolationExceptiont");
            ex.printStackTrace();
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "Validator failure: ");
            addError(model, list.toArray(new String[]{}));
            return false;
        }
        return true;
    }

    protected String beanValidators(Object object, Class<?>... groups) {
        String error = "";
        try {
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "Validator failure: ");
            error = list.toString();
        }
        return error;
    }

    /**
     * Server Validator Bean Groups
     *
     * @param redirectAttributes : Attributes
     * @param object             : Object
     * @param groups             : Groups
     * @return : True / False
     */
    protected boolean beanValidators(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
        try {
            BeanValidators.validateWithException(validator, object, groups);
        } catch (ConstraintViolationException ex) {
            List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
            list.add(0, "Validator failure: ");
            addError(redirectAttributes, list.toArray(new String[]{}));
            return false;
        }
        return true;
    }

    /**
     * Add Message To Model
     *
     * @param model
     * @param messages
     */
    protected void addMessage(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message + "<br/>");
        }
        model.addAttribute(ATT_MESSAGE, sb.toString());
    }

    /**
     * Add Message To redirect attribute
     *
     * @param redirectAttributes : attribute
     * @param messages           : message
     */
    protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message + "<br/>");
        }
        redirectAttributes.addFlashAttribute(ATT_MESSAGE, sb.toString());
    }

    /**
     * Add Error To Model
     *
     * @param model
     * @param messages
     */
    protected void addError(Model model, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message + "<br/>");
        }
        model.addAttribute(ATT_ERROR, sb.toString());
    }

    /**
     * Add Error To redirect attribute
     *
     * @param redirectAttributes : attribute
     * @param messages           : message
     */
    protected void addError(RedirectAttributes redirectAttributes, String... messages) {
        StringBuilder sb = new StringBuilder();
        for (String message : messages) {
            sb.append(message + "<br/>");
        }
        redirectAttributes.addFlashAttribute(ATT_ERROR, sb.toString());
    }

    protected String getLocale() {
        return I18nUtils.defaultLocal.getLanguage();
    }

    /*
    protected String getMessage(String code, Object[] args, String defaultMessage) {
        return messageSource.getMessage(code, args, defaultMessage, defaultLocal);
    }

    protected String getMessage(String code, String defaultMessage) {
        return messageSource.getMessage(code, null, defaultMessage, defaultLocal);
    }
*/
    protected String getMessage(String code, Object[] args) {
        /*return messageSource.getMessage(code, args, defaultLocal);*/
        return I18nUtils.getMessage(code, args);
    }

    protected String getMessage(String code) {
        /*return messageSource.getMessage(code, null, defaultLocal);*/
        return I18nUtils.getMessage(code);
    }

    /**
     * Init data binding
     *
     * @param binder   : binder
     * @param request  : request
     * @param response : response
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder, HttpServletRequest request, HttpServletResponse response) {
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            //            @Override
//            public void setAsText(String text) {
//                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
//            }
            @Override
            public void setAsText(String text) {
                setValue(text);
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? (DateUtils.format((Date) value, DateUtils.YYYY_MM_DD)) : "";
            }

            @Override
            public void setAsText(String text) {
                if (StringUtils.isNotEmpty(text) && text.length() <= 5) {
                    setValue(DateUtils.formatTimeNoSs(text));
                } else if (StringUtils.isNotEmpty(text) && text.length() <= 8) {
                    setValue(DateUtils.formatTime(text));
                } else if (StringUtils.isNotEmpty(text) && text.length() <= 10) {
                    setValue(DateUtils.formatNoTime(text));
                } else if (StringUtils.isNotEmpty(text) && text.length() <= 16) {
                    setValue(DateUtils.formatWithHHmm(text));
                } else if (StringUtils.isNotEmpty(text)) {
                    setValue(DateUtils.format(text));
                }
            }
        });
    }

    protected void addPageToSession(Map<String, Object> pageMap, HttpSession session) {
        session.setAttribute(ATT_PAGE_MAP, pageMap);
    }

    protected void addSearchToSession(Map<String, Object> searchMap, HttpSession session) {
        session.setAttribute(ATT_SEARCH_MAP, searchMap);
    }

    protected void addIdsToSession(Map<String, Object> ids, HttpSession session) {
        session.setAttribute(ATT_IDS, ids);
        String idsString = getSelectedValue(session);
        session.setAttribute(ATT_SELECT_STRING, idsString);
    }

    protected void addCriteriaToSession(Map<String, Object> criteria, HttpSession session) {
        session.setAttribute(ATT_SEARCH_CRITERIA, criteria);
    }

    protected void cleanSession(HttpSession session) {
        session.removeAttribute(ATT_SEARCH_MAP);
        session.removeAttribute(ATT_PAGE_MAP);
        session.removeAttribute(ATT_IDS);
        session.removeAttribute(ATT_SELECT_STRING);
        session.removeAttribute(ATT_SEARCH_CRITERIA);
        session.removeAttribute(ATT_VERSION);
    }

    protected boolean sessionContainSearchMap(HttpSession session) {
        return (session.getAttribute(ATT_SEARCH_MAP) != null);
    }

    protected boolean sessionContainPageMap(HttpSession session) {
        return (session.getAttribute(ATT_PAGE_MAP) != null);
    }

    protected boolean sessionContainIds(HttpSession session) {
        return (session.getAttribute(ATT_IDS) != null);
    }

    protected boolean sessionContainCriteria(HttpSession session) {
        return (session.getAttribute(ATT_SEARCH_CRITERIA) != null);
    }

    protected String getSelectedValue(HttpSession session) {
        String names = "";
        Map<String, Object> sessionIds = getIdsFromSession(session);
        if (sessionIds != null && sessionIds.size() > 0) {
            for (String id : sessionIds.keySet()) {
                if ((sessionIds.get(id) instanceof Map)
                        && (((Map<String, Object>) (sessionIds.get(id))).containsKey("checked"))
                        && ((Boolean) (((Map<String, Object>) (sessionIds.get(id))).get("checked")))) {
                    Map<String, Object> selectIds = (Map<String, Object>) (sessionIds.get(id));
                    if (selectIds.containsKey("name")) {
                        names += (names.equals("") ? "" : ",") + selectIds.get("name");
                    }
                }
            }
        }
        return names;
    }

    protected String getSelectedIds(HttpSession session) {
        String ids = "";
        Map<String, Object> sessionIds = getIdsFromSession(session);
        if (sessionIds != null && sessionIds.size() > 0) {
            for (String id : sessionIds.keySet()) {
                // jdk1.7 -> 1.6
                if ((sessionIds.get(id) instanceof Map)
                        && (((Map<String, Object>) (sessionIds.get(id))).containsKey("checked"))
                        && ((Boolean) (((Map<String, Object>) (sessionIds.get(id))).get("checked")))) {
                    ids += (ids.equals("") ? "" : ",") + id;
                }
            }
        }
        return ids;
    }

    protected boolean modelContainSearchFlag(Model model) {
        return (model.containsAttribute(ATT_SEARCH_FLAG));
    }

    protected Map<String, Object> getSearchMapFromSession(HttpSession session) {
        return (Map<String, Object>) session.getAttribute(ATT_SEARCH_MAP);
    }

    protected Map<String, Object> getPageMapFromSession(HttpSession session) {
        return (Map<String, Object>) session.getAttribute(ATT_PAGE_MAP);
    }

    protected Map<String, Object> getIdsFromSession(HttpSession session) {
        return (Map<String, Object>) session.getAttribute(ATT_IDS);
    }

    protected Map<String, Object> getCriteriaFromSession(HttpSession session) {
        return (Map<String, Object>) session.getAttribute(ATT_SEARCH_CRITERIA);
    }

    protected void saveAdvanceToSession(String rootIdentify, String advance, Model model, HttpSession session) {
        if (advance == null) {
            String sessionAdvance = (String) session.getAttribute(rootIdentify + "_" + ADVANCE_SEARCH);
            if (sessionAdvance == null) {
                session.setAttribute(rootIdentify + "_" + ADVANCE_SEARCH, (advance == null ? "N" : "Y"));
                model.addAttribute(ADVANCE_SEARCH, (advance == null ? "N" : "Y"));
            } else {
                model.addAttribute(ADVANCE_SEARCH, sessionAdvance);
            }
        } else {
            session.setAttribute(rootIdentify + "_" + ADVANCE_SEARCH, advance);
            model.addAttribute(ADVANCE_SEARCH, advance);
        }
    }

    protected String getAdvanceFromSession(String rootIdentify, HttpSession session) {
        return (String) session.getAttribute(rootIdentify + "_" + ADVANCE_SEARCH);
    }
}
