package com.wjxinfo.core.base.web.view;

import com.wjxinfo.core.base.utils.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by Jack on 14-6-20.
 */
public class FileView extends AbstractView {

    @Override
    protected boolean generatesDownloadContent() {
        return true;
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {
        String fileName = (String) model.get(Constants.FILE_NAME);
        Boolean isDownLoad = (Boolean) model.get(Constants.IS_DOWNLOAD);
        isDownLoad = (isDownLoad == null ? false : true);

        File downloadFile = new File(fileName);
        FileInputStream inputStream = new FileInputStream(downloadFile);

        // get MIME type of the file
        String mimeType = "";
        if (!isDownLoad) {
            mimeType = FileUtils.getMimeType(downloadFile);
        }
        if (StringUtils.isEmpty(mimeType)) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }

        // set content attributes for the response
        if (isDownLoad) {
            String ua = request.getHeader("User-Agent");
            boolean isIE = ua.toLowerCase().indexOf("msie") != -1;
            if (isIE) {
                mimeType = "application/x-msdownload";
            }
        }
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
        response.setHeader("Content-Disposition", (isDownLoad ? "attachment; " : "inline; ") + "filename=" + new String(downloadFile.getName().getBytes(), "iso8859-1"));

        // get output stream of the response
        OutputStream outStream = response.getOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead = -1;

        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }

        inputStream.close();
        outStream.close();
    }
}

