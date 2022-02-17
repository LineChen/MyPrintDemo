package com.line.printer;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class Commands {

    public static final int HEIGHT_PARTING_DEFAULT = 255;
    private static final String hexStr = "0123456789ABCDEF";
    private static String[] binaryArray = {"0000", "0001", "0010", "0011",
            "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
            "1100", "1101", "1110", "1111"};

    public static final byte ESC = 0x1B; // 指令换码
    public static final byte FS = 0x1C; // 文本分隔符
    public static final byte GS = 0x1D; // 组分隔符
    public static final byte DLE = 16; // 数据连接换码
    public static final byte EOT = 4; // 传输结束
    public static final byte ENQ = 5; // 询问字符
    public static final byte SP = 32; // 空格
    public static final byte LF = 0x0A; // 打印并换行（水平定位）
    public static final byte HT = 9; // 水平制表（横向列表）
    public static final byte CR = 13; // 归位键
    public static final byte FF = 12;// 走纸控制（打印并回到标准模式（在页模式下） ）
    public static final byte CAN = 24; // 作废（页模式下取消打印数据 ）

    /**
     * CodePage table
     */
    public static final byte CODE_PAGE_PC437 = 0;
    public static final byte CODE_PAGE_KATAKANA = 1;
    public static final byte CODE_PAGE_PC850 = 2;
    public static final byte CODE_PAGE_PC860 = 3;
    public static final byte CODE_PAGE_PC863 = 4;
    public static final byte CODE_PAGE_PC865 = 5;
    public static final byte CODE_PAGE_WPC1252 = 16;
    public static final byte CODE_PAGE_PC866 = 17;
    public static final byte CODE_PAGE_PC852 = 18;
    public static final byte CODE_PAGE_PC858 = 19;
    public static final byte CODE_PAGE_THAI_42 = 20;
    public static final byte CODE_PAGE_THAI_11 = 21;
    public static final byte CODE_PAGE_THAI_13 = 22;
    public static final byte CODE_PAGE_THAI_14 = 23;
    public static final byte CODE_PAGE_THAI_16 = 24;
    public static final byte CODE_PAGE_THAI_17 = 25;
    public static final byte CODE_PAGE_THAI_18 = 26;

    @Retention(SOURCE)
    @Target({ANNOTATION_TYPE})
    public @interface ByteDef {
        /**
         * Defines the allowed constants for this element
         */
        byte[] value() default {};

        /**
         * Defines whether the constants can be used as a flag, or just as an enum (the default)
         */
        boolean flag() default false;
    }

    @ByteDef(flag = true, value = {
            CODE_PAGE_PC437,
            CODE_PAGE_KATAKANA,
            CODE_PAGE_PC850,
            CODE_PAGE_PC860,
            CODE_PAGE_PC863,
            CODE_PAGE_PC865,
            CODE_PAGE_WPC1252,
            CODE_PAGE_PC866,
            CODE_PAGE_PC852,
            CODE_PAGE_PC858,
            CODE_PAGE_THAI_42,
            CODE_PAGE_THAI_11,
            CODE_PAGE_THAI_13,
            CODE_PAGE_THAI_14,
            CODE_PAGE_THAI_16,
            CODE_PAGE_THAI_17,
            CODE_PAGE_THAI_18
    })
    public @interface CodePage {
    }

    /**
     * BarCode table
     */
    public static class BarCode {
        public static final byte UPC_A = 0;
        public static final byte UPC_E = 1;
        public static final byte EAN13 = 2;
        public static final byte EAN8 = 3;
        public static final byte CODE39 = 4;
        public static final byte ITF = 5;
        public static final byte NW7 = 6;
        //public static final byte CODE93      = 72;
        public static final byte CODE128 = 73;
    }

    /**
     * <p>
     * Initialize printer
     * <p>
     * Clears the data in the print buffer and resets the printer modes to the modes that were
     * in effect when the power was turned on.
     * <p>
     * Format
     * ASCII    :  ESC   @
     * HEX      :  0x1B  0x40
     * Decimal  :  27    64
     */
    public static final byte[] RESET = {ESC, 0x40};

    /**
     * Select justification
     * <p>
     * Aligns all the data in one line to the position specified by n as follows.<p>
     * <p>
     * Format <p>
     * ASCII    :  ESC   a      n
     * HEX      :  0x1B  0x61   n
     * Decimal  :  27    97     n
     * <p>
     * Range 0 <= n <= 2  or 48 <= n <= 50 <p>
     * Default n = 0 <p>
     * Left justification : 0 or 48 <p>
     * Centering  : 1 or 49 <p>
     * Right justification : 2 or 50 <p>
     */
    public static final byte[] ALIGN_LEFT = {ESC, 0x61, 0x00};
    public static final byte[] ALIGN_CENTER = {ESC, 0x61, 0x01};
    public static final byte[] ALIGN_RIGHT = {ESC, 0x61, 0x02};

    /**
     * Print and line feed
     * <p>
     * Prints the data in the print buffer and feeds one line based on the current line spacing.
     * <p>
     * Format
     * ASCII    :  LF
     * HEX      :  0x0A
     * Decimal  :  10
     */
    public static final byte[] LINE_FEED = {LF};

    /**
     * Print and feed n lines.
     * <p>
     * Prints the data in the print buffer and feeds n lines .
     * <p>
     * Format <p>
     * ASCII    :  ESC   d      n
     * HEX      :  0x1B  0x64   n
     * Decimal  :  27    100    n
     * <p>
     * Range 0 <= n <= 255 <p>
     * Default n = 1 <p>
     */
    public static final byte[] LINE_FEED_N = {ESC, 0x64, 0x01};

    /**
     * Print and reverse feed n lines.
     * <p>
     * Prints the data in the print buffer and feeds n lines in the reverse direction.
     * <p>
     * Format <p>
     * ASCII    :  ESC   e      n
     * HEX      :  0x1B  0x65   n
     * Decimal  :  27    101    n
     * <p>
     * Range 0 <= n <= 255 <p>
     * Default n = 0 <p>
     * <p>
     * Note: <p>
     * With the some printer,the range of n is  0 <= n <= 2.
     */
    public static final byte[] LINE_FEED_REVERSE = {ESC, 0x65, 0x01};

    /**
     * Turn emphasized mode on/off
     * <p>
     * Turns emphasized mode on or off.<p>
     * When the LBS of n is 0, emphasized mode is turned off.<p>
     * When the LBS of n is 1, emphasized mode is turned on.<p>
     * <p>
     * Format <p>
     * ASCII    :  ESC   E      n
     * HEX      :  0x1B  0x45   n
     * Decimal  :  27    69     n
     * <p>
     * Range 0 <= n <= 255 <p>
     * Default n = 0 <p>
     */
    public static final byte[] EMPHASIZED_OFF = {ESC, 0x45, 0x00};
    public static final byte[] EMPHASIZED_ON = {ESC, 0x45, 0xF};

    /**
     * Select character font
     * <p>
     * Select character fonts.<p>
     * <p>
     * Format <p>
     * ASCII    :  ESC   M      n
     * HEX      :  0x1B  0x4D   n
     * Decimal  :  27    77     n
     * <p>
     * Range 0 <= n <= 2  or 48 <= n <= 50 <p>
     * Default n = 0 <p>
     * font A : 0 or 48 <p>
     * font B : 1 or 49 <p>
     * font C : 2 or 50 <p>
     * Notes: <p>
     * 1. Some printers do not have font C , See the ESC/POS Application Programming Guide
     * (ESC/POS APG ).<p>
     * 2. With the TM-U220, the range of n is n = 0,1,48 and 49. The default value is 1.<p>
     */
    public static final byte[] FONT_A = {ESC, 0x4D, 0x00};
    public static final byte[] FONT_B = {ESC, 0x4D, 0x01};
    public static final byte[] FONT_C = {ESC, 0x4D, 0x02};

    /**
     * Select CN font
     * <p>
     * Select CN fonts.<p>
     * <p>
     * <p>
     * Format <p>
     * ASCII    :  FS    !      n
     * HEX      :  0x1C  0x21   n
     * Decimal  :  28    33     n
     * <p>
     * Range 0 <= n <= 1 <p>
     * Default n = 0 <p>
     * font A : 0 <p>
     * font B : 1 <p>
     */
    public static final byte[] FONT_CN_A = {FS, 0x21, 0x00};
    public static final byte[] FONT_CN_B = {FS, 0x21, 0x01};

    /**
     * Select print modes
     * <p>
     * Selects the character font and styles (emphasize ,double-height,double-width,and
     * underline) together.<p>
     * <p>
     * Format <p>
     * ASCII    :  ESC   !      n
     * HEX      :  0x1B  0x21   n
     * Decimal  :  27    33     n
     * <p>
     * Range 0 <= n <= 255 <p>
     * Default n = 0 <p>
     * <p>
     */
    public static final byte[] FONT_DOUBLE_HEIGHT_OFF = {ESC, 0x21, 0x00};
    public static final byte[] FONT_DOUBLE_HEIGHT_ON = {ESC, 0x21, 0xF};
    public static final byte[] FONT_DOUBLE_HEIGHT_WIDTH_ON = {ESC, 0x21, 0x38};


    public static final byte[] HORIZONTAL_TAB = {ESC, 0x2C, 0x14, 0x1C, 0x00};

    public static final byte[] HT_NEXT = {HT};

    public static final byte[] LINE_HEIGHT_NORMAL = {ESC, 0x32};

    public static final byte[] LINE_HEIGHT_N = {ESC, 0x33, 0x08};

    /**
     * Generate pulse
     * <p>
     * Outputs the pulse specified by t1 and t2 to connector pin m to open the chash drawer,
     * as follows.<p>
     * <p>
     * Format <p>
     * ASCII    :  ESC   p      m     t1      t2
     * HEX      :  0x1B  0x70   m     t1      t2
     * Decimal  :  27    112    m     t1      t2
     * <p>
     * Range : <br>
     * m = 0,1,48,49 <br>
     * 0 <= t1 <= 255 <br>
     * 0 <= t2 <= 255 <br>
     * <p>
     * t1 specifies the pulse ON time as [t1 x 2ms]
     * t1 specifies the pulse OFF time as [t2 x 2ms]
     * <p>
     * Note : <br>
     * some printer , if t2 < 50 ,t2 should be 50.<br>
     * some printer , if t1 < 50 ,t1 should be 50. If t2 < 50 , t2 should be 50<br>
     */
    public static final byte[] GENERATE_PULSE = {ESC, 0x70, 0x00, 0x3C, 0x78};

    /**
     * Select printing color
     * <p>
     * Select the printing color specified by n .<br>
     * When the n is 0,48 , color 1 is selected.<br>
     * When the n is 1,49 , color 2 is selected.<br>
     * <p>
     * Format <br>
     * ASCII    :  ESC   r      n  <br>
     * HEX      :  0x1B  0x72   n  <br>
     * Decimal  :  27    114    n  <br>
     * <p>
     * Range <br>
     * n = 0,1,48,49 <br>
     * <p>
     * Default  <br>
     * n = 0 <br>
     * <p>
     * Note: <br>
     * Some printer , it is recommended to obtain the ESC/POS Application programming
     * Guide (ESC/POS APG), which describes the recommended operation for 2 color printing
     * control;
     */
    public static final byte[] COLOR_SET_1 = {ESC, 0x72, 0x00};
    public static final byte[] COLOR_SET_2 = {ESC, 0x72, 0x01};

    /**
     * Select character code table
     * <p>
     * Selects a page n from the character code table .
     * <p>
     * Format <br>
     * ASCII    :  ESC   t      n
     * HEX      :  0x1B  0x74   n
     * Decimal  :  27    116    n
     * <p>
     * Range : <br>
     * Except for Thai model : 0 <= n <= 5 ,16 <= n <= 19 ,0 = 254,255 <br>
     * For Thai model : 0 <= n <= 5 ,16 <= n <= 26 ,0 = 254,255 <p>
     * Default :<br>
     * Except for Thai model : n = 0 <br>
     * For Thai model : n = 20 <br>
     * <p>
     *
     * @see CodePage
     * @see #CODE_PAGE_PC437
     * @see #CODE_PAGE_KATAKANA
     * @see #CODE_PAGE_PC850
     * @see #CODE_PAGE_PC860
     * @see #CODE_PAGE_PC863
     * @see #CODE_PAGE_PC865
     * @see #CODE_PAGE_WPC1252
     * @see #CODE_PAGE_PC866
     * @see #CODE_PAGE_PC852
     * @see #CODE_PAGE_PC858
     */
    public static final byte[] SELECT_CODE_TAB = {ESC, 0x74, CODE_PAGE_WPC1252};

    /**
     * Turn white/black reverse printing mode on/off
     * <p>
     * Turns white/black reverse printing mode on or off.<p>
     * When the LBS of n is 0, white/black reverse mode is turned off.<p>
     * When the LBS of n is 1, white/black reverse mode is turned on.<p>
     * <p>
     * Format <p>
     * ASCII    :  GS    B      n
     * HEX      :  0x1D  0x45   n
     * Decimal  :  29    66     n
     * <p>
     * Range 0 <= n <= 255 <p>
     * Default n = 0 <p>
     */
    public static final byte[] PRINTING_MODE_OFF = {GS, 0x42, 0x00};
    public static final byte[] PRINTING_MODE_ON = {GS, 0x42, (byte) 0x80};

    /**
     * Select bar code height
     * <p>
     * Selects the height of the bar code as n dots.<p>
     * <p>
     * Format <br>
     * ASCII    :  GS    h      n <br>
     * HEX      :  0x1D  0x68   n <br>
     * Decimal  :  29    104    n <br>
     * <p>
     * Range <br>
     * 0 <= n <= 255
     * <p>
     * Default <br>
     * n = 0 <p>
     */
    public static final byte[] BARCODE_HEIGHT = {GS, 0x68, (byte) 0xA2};

    /**
     * 初始化打印机
     *
     * @return bytes for this command
     * @see #RESET
     */
    public static byte[] initPrinter() {
        return RESET;
    }

    /**
     * 左对齐
     * ESC a n
     *
     * @return bytes for this command
     */
    public static byte[] alignLeft() {
        return ALIGN_LEFT;
    }

    /**
     * 居中对齐
     * ESC a n
     *
     * @return bytes for this command
     */
    public static byte[] alignCenter() {
        return ALIGN_CENTER;
    }

    /**
     * 右对齐
     * ESC a n
     *
     * @return bytes for this command
     */
    public static byte[] alignRight() {
        return ALIGN_RIGHT;
    }

    /**
     * 打印并换行
     *
     * @return bytes for this command
     * @see #LINE_FEED
     */
    public static byte[] printLineFeed() {
        return LINE_FEED;
    }

    /**
     * 打印并走纸n行
     * Prints the data in the print buffer and feeds n lines
     * ESC d n
     *
     * @param n lines
     * @return bytes for this command
     */
    public static byte[] printLineFeed(byte n) {
        LINE_FEED_N[2] = n;
        return LINE_FEED_N;
    }

    /**
     * 打印并反向走纸n行（不一定有效）
     * Prints the data in the print buffer and feeds n lines in the reserve direction
     * ESC e n
     *
     * @param n lines
     * @return bytes for this command
     * @see #LINE_FEED_REVERSE
     */
    public static byte[] printAndReverseFeedLines(byte n) {
        LINE_FEED_REVERSE[2] = n;
        return LINE_FEED_REVERSE;
    }

    /**
     * 加粗设置
     *
     * @param isEmphasized on / off ,true is on
     * @return bytes for this command
     * @see #EMPHASIZED_OFF
     * @see #EMPHASIZED_ON
     */
    public static byte[] emphasizedSetting(boolean isEmphasized) {
        return isEmphasized ? EMPHASIZED_ON : EMPHASIZED_OFF;
    }

    /**
     * Select Font A
     *
     * @return bytes for this command
     * @see #FONT_A
     */
    public static byte[] selectFontA() {
        return FONT_A;
    }

    /**
     * Select Font B
     *
     * @return bytes for this command
     * @see #FONT_B
     */
    public static byte[] selectFontB() {
        return FONT_B;
    }

    /**
     * Select Font C ( some printers don't have font C )
     *
     * @return bytes for this command
     * @see #FONT_C
     */
    public static byte[] selectFontC() {
        return FONT_C;
    }

    /**
     * Select CN Font A
     * FS ! n
     *
     * @return bytes for this command
     * @see #FONT_CN_A
     */
    public static byte[] selectCNFontA() {
        return FONT_CN_A;
    }

    /**
     * Select CN Font B
     * FS ! n
     *
     * @return bytes for this command
     * @see #FONT_CN_B
     */
    public static byte[] selectCNFontB() {
        return FONT_CN_B;
    }

    /**
     * 关闭双倍字高 chart
     * ESC ! n
     *
     * @return bytes for this command
     * @see #FONT_DOUBLE_HEIGHT_OFF
     */
    public static byte[] doubleHeightWidthOff() {
        return FONT_DOUBLE_HEIGHT_OFF;
    }

    /**
     * 双倍字高（仅英文字体有效）
     * ESC ! n
     *
     * @return bytes for this command
     * @see #FONT_DOUBLE_HEIGHT_ON
     */
    public static byte[] doubleHeightOn() {
        return FONT_DOUBLE_HEIGHT_ON;
    }

    /**
     * 双倍字体高宽（仅英文字体有效）
     * ESC ! n
     *
     * @return bytes for this command
     * @see #FONT_DOUBLE_HEIGHT_ON
     */
    public static byte[] doubleHeightWidthOn() {
        return FONT_DOUBLE_HEIGHT_WIDTH_ON;
    }

    public static byte[] printHorizontalTab() {
        return HORIZONTAL_TAB;
    }

    public static byte[] printHTNext() {
        return HT_NEXT;
    }

    public static byte[] printLineNormalHeight() {
        return LINE_HEIGHT_NORMAL;
    }

    public static byte[] printLineHeight(byte height) {
        LINE_HEIGHT_N[2] = height;
        return LINE_HEIGHT_N;
    }

    /**
     * 弹开纸箱
     * Drawer kick-out connector pin 2
     * ESC p m t1 t2
     *
     * @return bytes for this command
     */
    public static byte[] drawerKick() {
        return GENERATE_PULSE;
    }

    /**
     * 选择打印颜色1（不一定有效）
     * ESC r n
     *
     * @return bytes for this command
     */
    public static byte[] selectColor1() {
        return COLOR_SET_1;
    }

    /**
     * 选择打印颜色2（不一定有效）
     * ESC r n
     *
     * @return bytes for this command
     */
    public static byte[] selectColor2() {
        return COLOR_SET_2;
    }

    /**
     * Select character code table
     * ESC t n
     *
     * @param cp CODE_PAGE_WPC1252
     * @return bytes for this command
     * @see CodePage
     */
    public static byte[] selectCodeTab(@CodePage byte cp) {
        SELECT_CODE_TAB[2] = cp;
        return SELECT_CODE_TAB;
    }

    /**
     * white printing mode on (不一定有效)
     * Turn white/black reverse printing mode on
     * GS B n
     *
     * @return bytes for this command
     */
    public static byte[] whitePrintingOn() {
        return PRINTING_MODE_ON;
    }

    /**
     * white printing mode off (不一定有效)
     * Turn white/black reverse printing mode off
     * GS B n
     *
     * @return bytes for this command
     */
    public static byte[] whitePrintingOff() {
        return PRINTING_MODE_OFF;
    }

    /**
     * select bar code height
     * Select the height of the bar code as n dots
     * default dots = 162
     * <p>
     * GS h n
     *
     * @param dots ( height of the bar code )
     * @return bytes for this command
     */
    public static byte[] barcode_height(byte dots) {
        BARCODE_HEIGHT[2] = dots;
        return BARCODE_HEIGHT;
    }

    /**
     * select font hri
     * Selects a font for the Human Readable Interpretation (HRI) characters when printing a barcode, using n as follows:
     *
     * @param n Font
     *          0, 48 Font A
     *          1, 49 Font B
     * @return bytes for this command
     */
    public static byte[] select_font_hri(byte n) {
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 102;
        result[2] = n;
        return result;
    }

    /**
     * select position_hri
     * Selects the print position of Human Readable Interpretation (HRI) characters when printing a barcode, using n as follows:
     *
     * @param n Print position
     *          0, 48 Not printed
     *          1, 49 Above the barcode
     *          2, 50 Below the barcode
     *          3, 51 Both above and below the barcode
     * @return bytes for this command
     */
    public static byte[] select_position_hri(byte n) {
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 72;
        result[2] = n;
        return result;
    }

    /**
     * Set horizontal tab positions
     *
     * @param col ( coulumn )
     * @return bytes for this command
     */
    public static byte[] set_HT_position(byte col) {
        byte[] result = new byte[4];
        result[0] = ESC;
        result[1] = 68;
        result[2] = col;
        result[3] = 0;
        return result;
    }

    /**
     * 字体变大为标准的n倍
     *
     * @param num 倍数
     * @return bytes for this command
     */
    public static byte[] fontSizeSetBig(int num) {
        byte realSize = 0;
        switch (num) {
            case 0:            // 宽度|高度
                realSize = 0;  // 0000|0000
                break;
            case 1:
                realSize = 17; // 0001|0001
                break;
            case 2:
                realSize = 34; // 0010|0010
                break;
            case 3:
                realSize = 51; // 0011|0011
                break;
            case 4:
                realSize = 68; // 0100|0100
                break;
            case 5:
                realSize = 85; // 0101|0101
                break;
            case 6:
                realSize = 102; // 0110|0110
                break;
            case 7:
                realSize = 119; // 0111|0111
                break;
        }
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 33;
        result[2] = realSize;
        return result;
    }

    /**
     * 字体变大为标准的n倍
     *
     * @param num 倍数
     * @return bytes for this command
     */
    public static byte[] fontHeightSizeSetBig(int num) {
        byte realSize = 0;
        switch (num) {
            case 0:            // 宽度|高度
                realSize = 0;  // 0000|0000
                break;
            case 1:
                realSize = 1; // 0000|0001
                break;
            case 2:
                realSize = 2; // 0000|0010
                break;
            case 3:
                realSize = 3; // 0000|0011
                break;
            case 4:
                realSize = 4; // 0000|0100
                break;
            case 5:
                realSize = 5; // 0000|0101
                break;
            case 6:
                realSize = 6; // 0000|0110
                break;
            case 7:
                realSize = 7; // 0000|0111
                break;
        }
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 33;
        result[2] = realSize;
        return result;
    }

    // ------------------------条形码---------------------------
    // 设置条码的位置--与设置文本对齐方式相同
    // 设置条码的宽度GS w n
    public static byte[] setBarCodeWith(int width) {
        byte[] result = new byte[3];
        result[0] = GS;// 0x1D
        result[1] = 0x77;// 'W'
        result[2] = (byte) width;
        return result;
    }

    // 设置条码的高度GS h n
    public static byte[] setBarCodeHeight(int height) {
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 0x68;
        result[2] = (byte) height /*0xA2*/;
        return result;
    }

    // 条码注释打印在条码位置GS H n
    // 打印条码时， 为HRI字符选择打印位置（HRI 是对条码内容注释的字符）
    // 0, 48 不打印;1, 49 在条码上方;2, 50 在条码下方;3, 51 在条码上方及下方
    public static byte[] setHRILocation(int loc) {
        byte[] result = new byte[3];
        result[0] = GS;
        result[1] = 0x48;
        result[2] = (byte) loc;
        return result;
    }

    /**
     * 选定条形码系统（m值）并打印条码
     * <p>
     * 【GS k m d1...dk NUL】 该命令在这种格式下以 NUL 结束 0 UPC-A 11 ≤ k ≤ 12 48 ≤ d ≤ 57 1
     * UPC-E 11 ≤ k ≤ 12 48 ≤ d ≤ 57 2 JAN13 (EAN13) 12 ≤ k ≤ 13 48 ≤ d ≤ 57 3 JAN 8
     * (EAN8) 7 ≤ k ≤ 8 48 ≤ d ≤ 57 4 CODE39 1 ≤ k 48 ≤ d ≤ 57， 65 ≤ d ≤ 90, 32, 36,
     * 37, 43, 45, 46, 47 5 ITF 1 ≤ k (偶数） 48 ≤ d ≤ 57 6 CODABAR 1 ≤ k 48 ≤ d ≤ 57，
     * 65 ≤ d ≤ 68, 36, 43, 45, 46, 47, 58
     * <p>
     * 【GS k m n d1...dn】 n用来指示条码数据的个数， 打印机将其后边 n 字节数据作为条码数据处理
     * 65 UPC-A 11 ≤ n ≤ 12
     * 48 ≤ d ≤ 57
     * 66 UPC-E 11 ≤ n ≤ 12 48 ≤ d ≤ 57
     * 67 JAN13 (EAN13) 12 ≤ n ≤ 13 48 ≤ d ≤ 57
     * 68 JAN 8 (EAN8) 7 ≤ n ≤ 8 48 ≤ d ≤ 57
     * 69 CODE39 1 ≤ n ≤ 255 48 ≤ d ≤ 57， 65 ≤ d ≤ 90, 32, 36, 37, 43, 45, 46, 47
     * 70 ITF 1 ≤ n ≤ 255（偶数） 48 ≤ d ≤ 57
     * 71 CODABAR 1 ≤ n ≤ 255 48 ≤ d ≤ 57， 65 ≤ d ≤ 68, 36, 43, 45, 46, 47, 58
     * 72 CODE93 1 ≤ n ≤ 255 0 ≤ d ≤ 127
     * [实例] 打印 GS k 72 7 67 111 100 101 13 57 51
     * 73 CODE128 2 ≤ n ≤ 255 0 ≤ d ≤ 127
     * [实例] 打印"No. 123456"的实例数据,首先用CODE B打印"No."
     * 然后用CODE C 打印下列数字。GS k 73 10 123 66 78 111 46 123 67 12 34 56
     */
    public static byte[] printBarCode(int m, byte[] dk) {
        byte[] result = new byte[3 + dk.length + 1];
        result[0] = GS;
        result[1] = 0x6B;
        result[2] = (byte) m;// 选定条形码系统
        for (int i = 0; i < dk.length; i++) {
            result[3 + i] = dk[i];
        }
        result[result.length - 1] = 0;
        return result;
    }

    public static byte[] printBarCode(int m, int n, byte[] dn) {
        byte[] result = new byte[4 + n];
        result[0] = GS;
        result[1] = 0x6B;
        result[2] = (byte) m;// 选定条形码系统
        result[3] = (byte) n;// 条码数据的个数
        for (int i = 0; i < n; i++) {
            result[4 + i] = dn[i];
        }
        return result;
    }

    public static byte[] printBarCodeGP(int m, int n, String dn) {
        n = 8;
        byte[] result = new byte[6 + n];
        result[0] = GS;
        result[1] = 0x6B;
        result[2] = (byte) m;// 选定条形码系统
        result[3] = (byte) (n + 2);// 条码数据的个数
        result[4] = (byte) 123;
        result[5] = (byte) 67;
        for (int i = 0; i < n; i++) {
            result[6 + i] = (byte) (10 + i);
        }
        return result;
    }

    /**
     * print bar code
     *
     * @param barcode_typ   ( Barcode.CODE39, Barcode.EAN8 ,...)
     * @param barcode2print value
     * @return bytes for this command
     */
    public static byte[] print_bar_code(byte barcode_typ, String barcode2print) {
        byte[] barcodeBytes = barcode2print.getBytes();
        byte[] result = new byte[3 + barcodeBytes.length + 1];
        result[0] = GS;
        result[1] = 107;
        result[2] = barcode_typ;
        int idx = 3;
        for (byte b : barcodeBytes) {
            result[idx] = b;
            idx++;
        }
        result[idx] = 0;
        return result;
    }

    /**
     * print bar code
     *
     * @param barcode_typ(Barcode.CODE39,Barcode.EAN8,...)
     * @param barcode2print
     * @return bytes for this command
     */
    public static byte[] print_bar_code128(byte barcode_typ, String barcode2print) {
        byte[] barcodebytes = barcode2print.getBytes();
        byte[] result = new byte[4 + barcodebytes.length];
        result[0] = GS;
        result[1] = 107;
        result[2] = barcode_typ;
        result[3] = (byte) barcodebytes.length;
        int idx = 4;

        for (int i = 0; i < barcodebytes.length; i++) {
            result[idx] = barcodebytes[i];
            idx++;
        }

        return result;
    }

    /**
     * 进纸切割
     * Feeds paper to ( cutting position + n x vertical motion unit )
     * and executes a full cut ( cuts the paper completely )
     *
     * @return bytes for this command
     */

    public static byte[] feedPaperCut() {
        byte[] result = new byte[4];
        result[0] = GS;
        result[1] = 86;
        result[2] = 65;
        result[3] = 0;
        return result;
    }

    /**
     * 进纸切割（留部分）
     * Feeds paper to ( cutting position + n x vertical motion unit )
     * and executes a partial cut ( one point left uncut )
     *
     * @return bytes for this command
     */
    public static byte[] feedPaperCutPartial() {
        byte[] result = new byte[4];
        result[0] = GS;
        result[1] = 86;
        result[2] = 66;
        result[3] = 0;
        return result;
    }

    /**
     * 解码图片
     *
     * @param image   图片
     * @param parting 高度分割值
     * @return 数据流
     */
    public static ArrayList<byte[]> decodeBitmapToDataList(Bitmap image, int parting) {
        if (parting <= 0 || parting > 255)
            parting = 255;
        if (image == null)
            return null;

        final int width = image.getWidth();
        final int height = image.getHeight();
        if (width <= 0 || height <= 0)
            return null;
        if (width > 2040) {
            // 8位9针，宽度限制2040像素（但一般纸张都没法打印那么宽，但并不影响打印）
            final float scale = 2040 / (float) width;
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            Bitmap resizeImage;
            try {
                resizeImage = Bitmap.createBitmap(image, 0, 0, width, height, matrix, true);
            } catch (OutOfMemoryError e) {
                return null;
            }
            ArrayList<byte[]> data = decodeBitmapToDataList(resizeImage, parting);
            resizeImage.recycle();
            return data;
        }

        // 宽命令
        String widthHexString = Integer.toHexString(width % 8 == 0 ? width / 8 : (width / 8 + 1));
        if (widthHexString.length() > 2) {
            // 超过2040像素才会到达这里
            return null;
        } else if (widthHexString.length() == 1) {
            widthHexString = "0" + widthHexString;
        }
        widthHexString += "00";

        // 每行字节数(除以8，不足补0)
        String zeroStr = "";
        int zeroCount = width % 8;
        if (zeroCount > 0) {
            for (int i = 0; i < (8 - zeroCount); i++) {
                zeroStr += "0";
            }
        }
        ArrayList<String> commandList = new ArrayList<>();
        // 高度每parting像素进行一次分割
        int time = height % parting == 0 ? height / parting : (height / parting + 1);// 循环打印次数
        for (int t = 0; t < time; t++) {
            int partHeight = t == time - 1 ? height % parting : parting;// 分段高度

            // 高命令
            String heightHexString = Integer.toHexString(partHeight);
            if (heightHexString.length() > 2) {
                // 超过255像素才会到达这里
                return null;
            } else if (heightHexString.length() == 1) {
                heightHexString = "0" + heightHexString;
            }
            heightHexString += "00";

            // 宽高指令
            String commandHexString = "1D763000";
            commandList.add(commandHexString + widthHexString + heightHexString);

            ArrayList<String> list = new ArrayList<>(); //binaryString list
            StringBuilder sb = new StringBuilder();
            // 像素二值化，非黑即白
            for (int i = 0; i < partHeight; i++) {
                sb.delete(0, sb.length());
                for (int j = 0; j < width; j++) {
                    // 实际在图片中的高度
                    int startHeight = t * parting + i;
                    //得到当前像素的值
                    int color = image.getPixel(j, startHeight);
                    int red, green, blue;
                    if (image.hasAlpha()) {
                        //得到alpha通道的值
                        int alpha = Color.alpha(color);
                        //得到图像的像素RGB的值
                        red = Color.red(color);
                        green = Color.green(color);
                        blue = Color.blue(color);
                        final float offset = alpha / 255.0f;
                        // 根据透明度将白色与原色叠加
                        red = 0xFF + (int) Math.ceil((red - 0xFF) * offset);
                        green = 0xFF + (int) Math.ceil((green - 0xFF) * offset);
                        blue = 0xFF + (int) Math.ceil((blue - 0xFF) * offset);
                    } else {
                        //得到图像的像素RGB的值
                        red = Color.red(color);
                        green = Color.green(color);
                        blue = Color.blue(color);
                    }
                    // 接近白色改为白色。其余黑色
                    if (red > 160 && green > 160 && blue > 160)
                        sb.append("0");
                    else
                        sb.append("1");
                }
                // 每一行结束时，补充剩余的0
                if (zeroCount > 0) {
                    sb.append(zeroStr);
                }
                list.add(sb.toString());
            }
            // binaryStr每8位调用一次转换方法，再拼合
            ArrayList<String> bmpHexList = new ArrayList<>();
            for (String binaryStr : list) {
                sb.delete(0, sb.length());
                for (int i = 0; i < binaryStr.length(); i += 8) {
                    String str = binaryStr.substring(i, i + 8);
                    // 2进制转成16进制
                    String hexString = binaryStrToHexString(str);
                    sb.append(hexString);
                }
                bmpHexList.add(sb.toString());
            }

            // 数据指令
            commandList.addAll(bmpHexList);
        }
        ArrayList<byte[]> data = new ArrayList<>();
        for (String hexStr : commandList) {
            data.add(hexStringToBytes(hexStr));
        }
        return data;
    }

    /**
     * 解码图片
     *
     * @param image   图片
     * @param parting 高度分割值
     * @return 数据流
     */
    public static byte[] decodeBitmap(Bitmap image, int parting) {
        ArrayList<byte[]> data = decodeBitmapToDataList(image, parting);
        int len = 0;
        for (byte[] srcArray : data) {
            len += srcArray.length;
        }
        byte[] destArray = new byte[len];
        int destLen = 0;
        for (byte[] srcArray : data) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
            destLen += srcArray.length;
        }
        return destArray;
    }

    /**
     * 解码图片
     *
     * @param image 图片
     * @return 数据流
     */
    public static byte[] decodeBitmap(Bitmap image) {
        return decodeBitmap(image, HEIGHT_PARTING_DEFAULT);
    }

    /**
     * 合并byte数组
     *
     * @param byteArray byte数组
     * @return 一个byte数组
     */
    public static byte[] mergerByteArray(byte[]... byteArray) {
        int length = 0;
        for (byte[] item : byteArray) {
            length += item.length;
        }
        byte[] result = new byte[length];
        int index = 0;
        for (byte[] item : byteArray) {
            for (byte b : item) {
                result[index] = b;
                index++;
            }
        }
        return result;
    }

    /**
     * 2进制转成16进制
     *
     * @param binaryStr 2进制串
     * @return 16进制串
     */
    public static String binaryStrToHexString(String binaryStr) {
        String hex = "";
        String f4 = binaryStr.substring(0, 4);
        String b4 = binaryStr.substring(4, 8);
        for (int i = 0; i < binaryArray.length; i++) {
            if (f4.equals(binaryArray[i]))
                hex += hexStr.substring(i, i + 1);
        }
        for (int i = 0; i < binaryArray.length; i++) {
            if (b4.equals(binaryArray[i]))
                hex += hexStr.substring(i, i + 1);
        }
        return hex;
    }

    /**
     * 16进制指令list转换为byte[]指令
     *
     * @param list 指令集
     * @return byte[]指令
     */
    public static byte[] hexListToByte(List<String> list) {
        ArrayList<byte[]> commandList = new ArrayList<>();
        for (String hexStr : list) {
            commandList.add(hexStringToBytes(hexStr));
        }
        int len = 0;
        for (byte[] srcArray : commandList) {
            len += srcArray.length;
        }
        byte[] destArray = new byte[len];
        int destLen = 0;
        for (byte[] srcArray : commandList) {
            System.arraycopy(srcArray, 0, destArray, destLen, srcArray.length);
            destLen += srcArray.length;
        }
        return destArray;
    }

    /**
     * 16进制串转byte数组
     *
     * @param hexString 16进制串
     * @return byte数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * 16进制char 转 byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) hexStr.indexOf(c);
    }


}
