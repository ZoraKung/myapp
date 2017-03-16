package com.wjxinfo.core.base.utils.pdf;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;

/**
 * Pdf Font Utils
 */
public class PdfFontUtils {
    public static int NORMAL_FONT_SIZE = 12;

    public static int LARGE_FONT_SIZE = 20;

    public static int SMALL_FONT_SIZE = 10;

    private BaseFont cnFont;

    private BaseFont bigFont;

    private BaseFont enFont;

    private Font cnNormalFont;

    private Font bigNormalFont;

    private Font enNormalFont;

    private Font cnBoldFont;

    private Font bigBoldFont;

    private Font enBoldFont;

    private Font cnLargeFont;

    private Font bigLargeFont;

    private Font enLargeFont;

    private Font cnSmallFont;

    private Font bigSmallFont;

    private Font enSmallFont;

    public void PdfFontUtils() {
        this.cnFont = getCnFont();
        this.enFont = getEnFont();
        this.bigFont = getBigFont();
    }

    public BaseFont getCnFont() {
        if (this.cnFont == null) {
            try {
                return BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.cnFont;
        }
    }

    public BaseFont getBigFont() {
        if (this.bigFont == null) {
            try {
                return BaseFont.createFont("MSung-Light", "UniCNS-UCS2-H", BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.bigFont;
        }
    }

    public BaseFont getEnFont() {
        if (this.enFont == null) {
            try {
                return BaseFont.createFont();
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.enFont;
        }
    }

    public Font getCnNormalFont() {
        if (this.cnFont == null) {
            this.cnFont = getCnFont();
        }
        if (this.cnNormalFont == null) {
            try {
                return new Font(cnFont, NORMAL_FONT_SIZE, Font.NORMAL);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.cnNormalFont;
        }
    }

    public Font getBigNormalFont() {
        if (this.bigFont == null) {
            this.bigFont = getBigFont();
        }
        if (this.bigNormalFont == null) {
            try {
                return new Font(bigFont, NORMAL_FONT_SIZE, Font.NORMAL);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.bigNormalFont;
        }
    }

    public Font getEnNormalFont() {
        if (this.enFont == null) {
            this.enFont = getEnFont();
        }
        if (this.enNormalFont == null) {
            try {
                return new Font(enFont, NORMAL_FONT_SIZE, Font.NORMAL);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.enNormalFont;
        }
    }

    public Font getCnBoldFont() {
        if (this.cnFont == null) {
            this.cnFont = getCnFont();
        }
        if (this.cnBoldFont == null) {
            try {
                return new Font(cnFont, NORMAL_FONT_SIZE, Font.BOLD);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.cnBoldFont;
        }
    }

    public Font getBigBoldFont() {
        if (this.bigFont == null) {
            this.bigFont = getBigFont();
        }
        if (this.bigBoldFont == null) {
            try {
                return new Font(bigFont, NORMAL_FONT_SIZE, Font.BOLD);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.bigBoldFont;
        }
    }

    public Font getEnBoldFont() {
        if (this.enFont == null) {
            this.enFont = getEnFont();
        }
        if (this.enBoldFont == null) {
            try {
                return new Font(enFont, NORMAL_FONT_SIZE, Font.BOLD);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.enBoldFont;
        }
    }

    public Font getCnLargeFont() {
        if (this.cnFont == null) {
            this.cnFont = getCnFont();
        }
        if (this.cnLargeFont == null) {
            try {
                return new Font(cnFont, LARGE_FONT_SIZE, Font.BOLD);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.cnLargeFont;
        }
    }

    public Font getBigLargeFont() {
        if (this.bigFont == null) {
            this.bigFont = getBigFont();
        }
        if (this.bigLargeFont == null) {
            try {
                return new Font(bigFont, LARGE_FONT_SIZE, Font.BOLD);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.bigLargeFont;
        }
    }

    public Font getEnLargeFont() {
        if (this.enFont == null) {
            this.enFont = getEnFont();
        }
        if (this.enLargeFont == null) {
            try {
                return new Font(enFont, LARGE_FONT_SIZE, Font.BOLD);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.enLargeFont;
        }
    }

    public Font getCnSmallFont() {
        if (this.cnFont == null) {
            this.cnFont = getCnFont();
        }
        if (this.cnSmallFont == null) {
            try {
                return new Font(cnFont, SMALL_FONT_SIZE, Font.NORMAL);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.cnSmallFont;
        }
    }

    public Font getBigSmallFont() {
        if (this.bigFont == null) {
            this.bigFont = getBigFont();
        }
        if (this.bigSmallFont == null) {
            try {
                return new Font(bigFont, SMALL_FONT_SIZE, Font.NORMAL);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.bigSmallFont;
        }
    }

    public Font getEnSmallFont() {
        if (this.enFont == null) {
            this.enFont = getEnFont();
        }
        if (this.enSmallFont == null) {
            try {
                return new Font(enFont, SMALL_FONT_SIZE, Font.NORMAL);
            } catch (Exception e) {
                return null;
            }
        } else {
            return this.enSmallFont;
        }
    }
}
