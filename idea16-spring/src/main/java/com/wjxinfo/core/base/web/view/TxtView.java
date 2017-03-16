package com.wjxinfo.core.base.web.view;

import com.wjxinfo.core.base.utils.common.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by Carey on 15-4-30.
 */
public class TxtView extends AbstractView {
    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = (String) model.get(Constants.FILE_NAME);
        if (StringUtils.isBlank(fileName)) {
            fileName = "export.txt";
        }
        Boolean isDownLoad = (Boolean) model.get(Constants.IS_DOWNLOAD);
        isDownLoad = (isDownLoad == null ? false : true);

        String data = (String) model.get("data");
        int len = 0;
        if (StringUtils.isNotBlank(data)) {
            len = data.length();
        }

        String mimeType = "text/plain";

        // set content attributes for the response
        if (isDownLoad) {
            String ua = request.getHeader("User-Agent");
            boolean isIE = ua.toLowerCase().indexOf("msie") != -1;
            if (isIE) {
                mimeType = "application/x-msdownload";
            }
        }
        response.setContentType(mimeType);
        response.setContentLength((int) len);
        response.setHeader("Content-Disposition", (isDownLoad ? "attachment; " : "inline; ") + "filename=" + new String(fileName.getBytes(), "iso8859-1"));

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        if (StringUtils.isNotBlank(data)) {
            outStream.write(data.getBytes(Charset.forName("UTF-8")), 0, len);
        }

        outStream.close();
    }
}