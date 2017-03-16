package com.wjxinfo.core.base.utils;

import org.apache.commons.io.FilenameUtils;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Iterator;

/**
 * Created by Jack on 15-7-2.
 */
public class ImageUtils {
    public static byte[] decodeFromRequest(String imageData) throws IOException {
        imageData = imageData.substring(30);
        imageData = URLDecoder.decode(imageData, "UTF-8");

        BASE64Decoder decoder = new BASE64Decoder();
        byte[] data = decoder.decodeBuffer(imageData);
        for (int i = 0; i < data.length; ++i) {
            if (data[i] < 0) {
                data[i] += 256;
            }
        }
        return data;
    }

    public static void generateImageFromRequest(String imageData, String imageName) throws IOException {
        byte[] data = decodeFromRequest(imageData);
        FileUtils.writeByteToFile(data, imageName);
    }

    public static void cutImage(String srcFileName, String targetFileName, int x, int y, int width, int height) throws IOException {
        FileInputStream is = null;
        ImageInputStream iis = null;
        String extensionName = FilenameUtils.getExtension(srcFileName);
        try {
            is = new FileInputStream(srcFileName);
            Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName(extensionName);
            ImageReader reader = it.next();
            iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, extensionName, new File(targetFileName));
        } finally {
            if (is != null)
                is.close();
            if (iis != null)
                iis.close();
        }
    }

    public static java.util.List<String> cutImage(String srcFileName, String targetDir, int width, int height) {
        java.util.List<String> list = new java.util.ArrayList<String>();
        try {
            String dir = null;
            String extensionName = FilenameUtils.getExtension(srcFileName);
            BufferedImage bi = ImageIO.read(new File(srcFileName));
            int srcWidth = bi.getWidth();
            int srcHeight = bi.getHeight();
            /*if (srcWidth > width) {
                scaleImage(srcFileName, width, srcHeight);
            }*/
            if (srcHeight > height) {
                int rows = 0;
                if (srcHeight % height == 0) {
                    rows = srcHeight / height;
                } else {
                    rows = (int) Math.floor(srcHeight / height) + 1;
                }
                for (int i = 0; i < rows; i++) {
                    dir = targetDir + File.separator + "image_" + i + "." + extensionName;
                    cutImage(srcFileName, dir, 0, i * height, srcWidth, height);
                    list.add(dir);
                }
            } else {
                dir = targetDir + File.separator + "image_1." + extensionName;
                FileUtils.copyFileCover(srcFileName, dir, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void scaleImage(String srcFileName, int scaleWidth, int scaleHeight) {
        try {
            String targetFileName = FilenameUtils.getFullPath(srcFileName) + FilenameUtils.getBaseName(srcFileName) + "_bak." + FilenameUtils.getExtension(srcFileName);
            BufferedImage src = ImageIO.read(new File(srcFileName));
            int width = scaleWidth;
            int height = scaleHeight;
            Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            ImageIO.write(tag, FilenameUtils.getExtension(srcFileName), new File(targetFileName));
            FileUtils.copyFileCover(targetFileName, srcFileName, true);
            FileUtils.deleteFile(targetFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
