package com.wjxinfo.core.base.utils.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.wjxinfo.core.base.utils.FileUtils;
import com.wjxinfo.core.base.utils.ImageUtils;
import com.wjxinfo.core.base.utils.security.EncodeUtils;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;

/**
 * Pdf Utils
 */
public class PdfUtils {
    //Font constants
    public static int NORMAL_FONT_SIZE = 12;

    public static int LARGE_FONT_SIZE = 20;

    public static int SMALL_FONT_SIZE = 10;

    public static PdfFontUtils pdfFontUtils = new PdfFontUtils();

    public static Font EN_NORMAL_FONT = pdfFontUtils.getEnNormalFont();

    public static Font CN_NORMAL_FONT = pdfFontUtils.getCnNormalFont();

    public static Font BIG_NORMAL_FONT = pdfFontUtils.getBigNormalFont();

    public static Font EN_BOLD_FONT = pdfFontUtils.getEnBoldFont();

    public static Font CN_BOLD_FONT = pdfFontUtils.getCnBoldFont();

    public static Font BIG_BOLD_FONT = pdfFontUtils.getBigBoldFont();

    public static Font EN_LARGE_FONT = pdfFontUtils.getEnLargeFont();

    public static Font CN_LARGE_FONT = pdfFontUtils.getCnLargeFont();

    public static Font BIG_LARGE_FONT = pdfFontUtils.getBigLargeFont();

    public static Font EN_SMALL_FONT = pdfFontUtils.getEnSmallFont();

    public static Font CN_SMALL_FONT = pdfFontUtils.getCnSmallFont();

    public static Font BIG_SMALL_FONT = pdfFontUtils.getBigSmallFont();

    //Set Default Font
    public static Font DEFAULT_FONT = pdfFontUtils.getEnNormalFont();

    /**
     * Get System Default Image file name
     *
     * @param photoFile
     * @param request
     * @return
     */
    public static String getImageFile(String photoFile, HttpServletRequest request) {
        String filePath = request.getSession().getServletContext().getRealPath("");
        String fileName = filePath + photoFile;
        return fileName;
    }

    /**
     * Set Title Context
     *
     * @param title : Context
     * @param font  : Font
     * @return : Paragraph
     */
    public static Paragraph setTitle(String title, Font font) {
        return setTitle(title, font, 20f, 10f, 10f);
    }

    public static Paragraph setTitle(String title, Font font, Float height, Float spacingBefore, Float spacingAfter) {
        Paragraph paragraph = new Paragraph(title, font);
        paragraph.setLeading(height);
        paragraph.setSpacingBefore(spacingBefore);
        paragraph.setSpacingAfter(spacingAfter);
        paragraph.setAlignment(Element.ALIGN_CENTER);
        return paragraph;
    }

    public static Paragraph setSeparateLine(int width, int height) {
        Paragraph paragraph = new Paragraph();
        LineSeparator line = new LineSeparator(height, width, new BaseColor(204, 204, 204), Element.ALIGN_CENTER, -2);
        paragraph.add(line);
        return paragraph;
    }

    /**
     * Set PdfPTable Cell
     *
     * @param content     : Content
     * @param font        : Font
     * @param align:      0 -- left, 1 -- center, 2 -- right
     * @param colspan     : Colspan
     * @param rowspan     : Rowspan
     * @param borderwidth : Border Width
     * @return : PdfPCell
     */
    public static PdfPCell setCell(String content, Font font, int align, int colspan, int rowspan, int borderwidth) {
        PdfPCell cell = new PdfPCell(new Paragraph(content, font));
        cell.setFixedHeight(25);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        cell.setHorizontalAlignment(align);
        if (colspan > 1) {
            cell.setColspan(colspan);
        }
        if (rowspan > 1) {
            cell.setRowspan(rowspan);
        }
        cell.setBorderWidth(borderwidth);
        return cell;
    }

    public static PdfPCell setCell(String content, Font font, int colspan, int rowspan, int borderwidth) {
        return PdfUtils.setCell(content, DEFAULT_FONT, 0, 1, 1, 0);
    }

    /**
     * Set PdfPTable Cell
     *
     * @param content : Content
     * @return
     */
    public static PdfPCell setCell(String content) {
        return PdfUtils.setCell(content, DEFAULT_FONT, 1, 1, 0);
    }

    public static PdfPCell setCell(String content, int align) {
        return PdfUtils.setCell(content, DEFAULT_FONT, align, 1, 1, 0);
    }

    public static PdfPCell setLabelCell(String content) {
        return PdfUtils.setCell(content, EN_BOLD_FONT, 1, 1, 0);
    }

    /**
     * Set PdfPTable Header Cell
     *
     * @param content : Content
     * @return : Cell
     */
    public static PdfPCell setTableHeaderCell(String content) {
        PdfPCell cell = setCell(content, EN_BOLD_FONT, 1, 1, 0);
        cell.setGrayFill(0.75f);
        return cell;
    }

    /**
     * Set PdfPTable
     *
     * @param widths       : Column Width Array
     * @param spaceBefore  : Space Before
     * @param totalWidth   : Total Width
     * @param widthPercent : Width Percent
     * @return : Table
     */
    public static PdfPTable setTable(float[] widths, float spaceBefore, float totalWidth, float widthPercent) {
        PdfPTable table = new PdfPTable(widths);
        table.setSpacingBefore(spaceBefore);
        table.setTotalWidth(totalWidth);
        table.setWidthPercentage(widthPercent);
        return table;
    }

    /**
     * Set TextArea Cell
     *
     * @param content : Content
     * @param colspan : Colspan
     * @param font    : Font
     * @return : Cell
     */
    public static PdfPCell setTextAreaCell(String content, int colspan, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(content, font));
        cell.setFixedHeight(90);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_TOP);
        if (colspan > 1) {
            cell.setColspan(colspan);
        }
        cell.setBorderWidth(0);
        return cell;
    }

    /**
     * Set Image Cell
     *
     * @param fileName : Image File Name
     * @param colspan  : Colspan
     * @param rowspan  : Rowspan
     * @param adjust   : Adjust
     * @return : Cell
     */
    public static PdfPCell setImageCell(String fileName, int colspan, int rowspan, boolean adjust) {
        PdfPCell cell = new PdfPCell();
        try {
            Image image = Image.getInstance(fileName);
            if (image != null) {
                if (adjust) {
                    cell = new PdfPCell(image, true);
                } else {
                    cell = new PdfPCell(image, false);
                }
            }
        } catch (Exception e) {
        }
        if (colspan > 1) {
            cell.setColspan(colspan);
        }
        if (rowspan > 1) {
            cell.setRowspan(rowspan);
        }
        return cell;
    }

    public static Image setImage(String fileName, int width, int height) {
        Image image = null;
        try {
            image = Image.getInstance(fileName);
            image.scaleAbsolute(width, height);
            image.setAlignment(Element.ALIGN_CENTER);
        } catch (Exception e) {
        }
        return image;
    }

    public static PdfPCell setImageCell(String fileName, int height) {
        PdfPCell cell = new PdfPCell();
        try {
            Image image = Image.getInstance(fileName);
            if (image != null) {
                cell = new PdfPCell(image, true);
            }
        } catch (Exception e) {
        }
        cell.setFixedHeight(height);
        cell.setBorder(0);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    /**
     * Generate Barcode Image
     *
     * @param writer  : PdfWriter
     * @param content : Content
     * @param x       : X
     * @param y       : Y
     * @param percent : Percent
     * @return : Image
     */
    public static Image setBarcode128(PdfWriter writer, String content, int width, int height, int x, int y, int percent) {
        PdfContentByte cb = writer.getDirectContent();
        Barcode128 code128 = new Barcode128();
        code128.setCode(content.trim());
        code128.setCodeType(Barcode128.CODE128);
        Image code128Image = code128.createImageWithBarcode(cb, null, null);
        code128Image.setAbsolutePosition(x, y);
        code128Image.scaleAbsolute(width, height);
        code128Image.scalePercent(percent);
        return code128Image;
    }

    /**
     * Set Barcode QRCode Image
     *
     * @param writer  : PdfWriter
     * @param content : Content
     * @param width   : Width
     * @param height  : Height
     * @param x       : X
     * @param y       : Y
     * @param percent : Percent
     * @return : Cell
     */
    public static Image setBarcodeQRCode(PdfWriter writer, String content, int width, int height, int x, int y, int percent) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BarcodeQRCode qrcode = new BarcodeQRCode(content.trim(), width, height, null);
            Image qrcodeImage = qrcode.getImage();
            qrcodeImage.setAbsolutePosition(x, y);
            qrcodeImage.scalePercent(percent);
            return qrcodeImage;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Set Document
     *
     * @param rectangle : Rectangle
     * @param bkColor   : Background Color
     * @param title     : Title
     * @param author    : Author
     * @param subject   : Subject
     * @param keywords  : Keywords
     * @param creator   : Creator
     * @param left      : Left Margin
     * @param right     : Right Margin
     * @param top       : Top Margin
     * @param bottom    : Bottom Margin
     * @return : Document
     */
    public static Document setDocument(Rectangle rectangle, BaseColor bkColor, String title, String author,
                                       String subject, String keywords, String creator,
                                       int left, int right, int top, int bottom) {
        rectangle.setBackgroundColor(bkColor);
        Document document = new Document(rectangle);
        document.addTitle(title);
        document.addAuthor(author);
        document.addSubject(subject);
        document.addKeywords(keywords);
        document.addCreator(creator);
        document.setMargins(left, right, top, bottom);
        return document;
    }

    /**
     * Set Document
     *
     * @param rectangle : Rectangle
     * @param left      : Left Margin
     * @param right     : Right Margin
     * @param top       : Top Margin
     * @param bottom    : Bottom Margin
     * @return : Document
     */
    public static Document setDocument(Rectangle rectangle, int left, int right, int top, int bottom) {
        Document document = new Document(rectangle);
        document.setMargins(left, right, top, bottom);
        return document;
    }

    /**
     * Set Document
     *
     * @return
     */
    public static Document setDocument() {
        Document document = new Document(PageSize.A4);
        document.setMargins(20, 20, 25, 25);
        return document;
    }

    /**
     * Set PdfWriter
     *
     * @param document : Document
     * @param fileName : Output File Name
     * @return
     */
    public static PdfWriter setWriter(Document document, String fileName) {
        try {
            return PdfWriter.getInstance(document, new FileOutputStream(fileName));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Return response to client
     *
     * @param fileName : Pdf File Name
     * @param response : Response
     */
    public static void responsePdf(String fileName, HttpServletResponse response) {
        File file = new File(fileName);
        if (file.exists()) {
            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("application/pdf");
            //if download pdf file, please add
            response.setHeader("Content-disposition", "attachment; filename=" + EncodeUtils.urlEncode(fileName));

            ServletOutputStream out;
            try {
                FileInputStream inputStream = new FileInputStream(file);
                out = response.getOutputStream();
                int b = 0;
                byte[] buffer = new byte[512];
                while (b != -1) {
                    b = inputStream.read(buffer);
                    out.write(buffer, 0, b);
                }
                inputStream.close();
                out.close();
                out.flush();
            } catch (Exception e) {
                try {
                    response.sendError(500, "Error: report display failure.");
                } catch (Exception ex) {
                }
            }
        } else {
            try {
                response.sendError(500, "Error: report file is not exist.");
            } catch (Exception e) {
            }
        }
    }

    public static void generatePdfFromUrl(String url, String pdfFilename) {
        String filePath = FilenameUtils.getFullPathNoEndSeparator(pdfFilename);
        FileUtils.createDirectoryIfNotExist(filePath);

        PdfWriter pdfWriter = null;
        //create a new document
        Document document = setDocument();
        try {
            //get Instance of the PDFWriter
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFilename));

            //open document
            document.open();

            //URL for HTML page
            URL myWebPage = new URL(url);
            InputStream is = myWebPage.openStream();

            InputStreamReader fis = new InputStreamReader(is);
            //get the XMLWorkerHelper Instance
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            //convert to PDF
            worker.parseXHtml(pdfWriter, document, fis);

            fis.close();
            is.close();
            //close the document
            document.close();
            //close the writer
            pdfWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String generateHtmlFileFormat(String html) {
        StringBuffer htmlFile = new StringBuffer();
        //add header
        htmlFile.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
                "</head>\n" +
                "<body>");
        //add content
        htmlFile.append(html);
        //add footer
        htmlFile.append("</body>\n" +
                "</html>");
        return htmlFile.toString();
    }

    //html -- html format file string
    public static void generatePdfFromString(String html, String pdfFilename) {
        String filePath = FilenameUtils.getFullPathNoEndSeparator(pdfFilename);
        FileUtils.createDirectoryIfNotExist(filePath);
        PdfWriter pdfWriter = null;
        //create a new document
        Document document = setDocument();
        try {
            //get Instance of the PDFWriter
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFilename));

            //open document
            document.open();

            document.newPage();

            //process string to html format file
            String htmlFileContent = generateHtmlFileFormat(html);
            //String for HTML page
            InputStream is = new ByteArrayInputStream(htmlFileContent.getBytes());

            InputStreamReader fis = new InputStreamReader(is);
            //get the XMLWorkerHelper Instance
            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            //convert to PDF
            worker.parseXHtml(pdfWriter, document, fis);

            fis.close();
            is.close();
            //close the document
            document.close();
            //close the writer
            pdfWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void generatePdfFromImage(String pdfFileName, String imageFileName) {
        if ((new File(imageFileName)).exists()) {
            //A4 LANDSCAPE
            Document document = new Document(PageSize.A4.rotate());
            document.setMargins(20, 20, 25, 25);
            try {
                FileOutputStream fos = new FileOutputStream(pdfFileName);
                PdfWriter writer = PdfWriter.getInstance(document, fos);
                writer.open();
                document.open();
                float width = document.getPageSize().getWidth();
                float height = document.getPageSize().getHeight();
                //process image
                String targetFileDir = FilenameUtils.getFullPath(imageFileName) + FilenameUtils.getBaseName(imageFileName) + "_bak";
                FileUtils.createDirectoryIfNotExist(targetFileDir);
                java.util.List<String> imageList = ImageUtils.cutImage(imageFileName, targetFileDir, (int) width, (int) height);
                if (imageList != null && imageList.size() > 0) {
                    for (String cutImage : imageList) {
                        Image image = Image.getInstance(cutImage);
                        image.scaleAbsoluteWidth(document.getPageSize().getWidth() * 0.95f);
                        document.newPage();
                        document.add(image);
                    }
                } else {
                    document.newPage();
                }
                document.close();
                writer.close();
                fos.close();
                //delete image fileName
                for (String cutImage : imageList) {
                    FileUtils.deleteFile(cutImage);
                }
                FileUtils.deleteDirectory(new File(targetFileDir));
                FileUtils.deleteFile(imageFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void generatePdfFromRequest(String imageData, String imageFileName, String pdfFileName) throws IOException {
        ImageUtils.generateImageFromRequest(imageData, imageFileName);
        generatePdfFromImage(pdfFileName, imageFileName);
    }
}
