package com.wjxinfo.core.base.web.utils;

import com.wjxinfo.core.base.utils.common.StringUtils;
import com.wjxinfo.core.base.utils.json.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by WTH on 2014/5/19. changed by jack on 2015/4/9
 */
public class RequestHelper {

    public static void printAllParameter(HttpServletRequest request) {
        Enumeration<String> enumName = request.getParameterNames();

        System.out.println("Request Method --> " + request.getMethod());
        System.out.println("Parameter Name / Value : ");
        while (enumName.hasMoreElements()) {
            String name = enumName.nextElement();
            System.out.println("\t" + name + " --> " + request.getParameter(name));
        }
    }

    public static String getRequestPrefix(HttpServletRequest request) {
        String requestRri = request.getHeader("Host") + request.getContextPath();
        return requestRri;
    }

    public static Map<String, MultipartFile> getMultiFilesByRequest(MultipartHttpServletRequest request) {
        Map<String, MultipartFile> uploadFileMap = new LinkedHashMap<String, MultipartFile>();
        for (Iterator it = request.getFileNames(); it.hasNext(); ) {
            String key = (String) it.next();
            MultipartFile file = request.getFile(key);
            if (file != null && !file.isEmpty() && file.getOriginalFilename().length() > 0) {
                uploadFileMap.put(key, file);
            }
        }
        return uploadFileMap;
    }

    public static long getMultiFileSizeByRequest(HttpServletRequest request) {
        long uploadFileSize = 0l;
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if (multipartResolver.isMultipart(request)) {
            Map<String, MultipartFile> uploadFileMap = getMultiFilesByRequest((MultipartHttpServletRequest) request);
            for (String key : uploadFileMap.keySet()) {
                uploadFileSize += uploadFileMap.get(key).getSize();
            }
        }
        return uploadFileSize;
    }

    public static Object[] getObjectVoByRequest(HttpServletRequest request) {
        List objects = new ArrayList<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()) {
            Class<?> clazz = null;
            try {
                clazz = Class.forName(key);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (clazz != null && clazz.getName().equalsIgnoreCase(key)) {
                String[] values = parameterMap.get(key);
                for (String value : values) {
                    if (StringUtils.isNotEmpty(value)) {
                        Object object = ObjectMapper.getObjectFromJson(value, clazz);
                        if (object != null) {
                            objects.add(object);
                        }
                    }
                }
            }
        }
        return objects.toArray();
    }

    public static Object getObjectVoByRequest(HttpServletRequest request, Class<?> clazz) {
        Map<String, Object> parameters = ServletUtils.getParametersStartingWith(request, "");
        if (parameters.size() > 0) {
            for (String key : parameters.keySet()) {
                if (clazz != null && clazz.getName().equalsIgnoreCase(key)) {
                    return ObjectMapper.getObjectFromJson(parameters.get(key).toString(), clazz);
                }
            }
        }
        return null;
    }

    public static List getObjectVosByRequest(HttpServletRequest request, Class<?> clazz) {
        List objects = new ArrayList<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String key : parameterMap.keySet()) {
            if (clazz != null && clazz.getName().equalsIgnoreCase(key)) {
                String[] values = parameterMap.get(key);
                for (String value : values) {
                    if (StringUtils.isNotEmpty(value)) {
                        Object object = ObjectMapper.getObjectFromJson(value, clazz);
                        if (object != null) {
                            objects.add(object);
                        }
                    }
                }
            }
        }
        return objects;
    }
}
