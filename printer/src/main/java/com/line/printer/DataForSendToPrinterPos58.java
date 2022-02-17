package com.line.printer;

import android.graphics.Bitmap;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class DataForSendToPrinterPos58 {
    private static String charsetName = "gbk";

    public DataForSendToPrinterPos58() {
    }

    public static void setCharsetName(String charset) {
        charsetName = charset;
    }

    public static byte[] horizontalPositioning() {
        byte[] data = new byte[]{9};
        return data;
    }

    public static byte[] printAndFeedLine() {
        byte[] data = new byte[]{10};
        return data;
    }

    public static byte[] setCharRightSpace(int n) {
        byte[] data = new byte[]{27, 32, (byte)n};
        return data;
    }

    public static byte[] selectPrintModel(int n) {
        byte[] data = new byte[]{27, 33, (byte)n};
        return data;
    }

    public static byte[] setAbsolutePrintPosition(int m, int n) {
        byte[] data = new byte[]{27, 36, (byte)m, (byte)n};
        return data;
    }

    public static byte[] selectOrCancleCustomChar(int n) {
        byte[] data = new byte[]{27, 37, (byte)n};
        return data;
    }

    public static byte[] defineuserDefinedCharacters(int c1, int c2, byte[] b) {
        byte[] data = new byte[]{27, 38, 3, (byte)c1, (byte)c2};
        data = byteMerger(data, b);
        return data;
    }

    public static byte[] selectBmpModel(int m, int nL, int nH, byte[] b) {
        byte[] data = new byte[]{27, 42, (byte)m, (byte)nL, (byte)nH};
        data = byteMerger(data, b);
        return data;
    }

    public static byte[] selectOrCancelUnderlineModel(int n) {
        byte[] data = new byte[]{27, 45, (byte)n};
        return data;
    }

    public static byte[] setDefultLineSpacing() {
        byte[] data = new byte[]{27, 50};
        return data;
    }

    public static byte[] setLineSpaceing(int n) {
        byte[] data = new byte[]{27, 51, (byte)n};
        return data;
    }

    public static byte[] cancelUserDefinedCharacters(int n) {
        byte[] data = new byte[]{27, 63, (byte)n};
        return data;
    }

    public static byte[] initializePrinter() {
        byte[] data = new byte[]{27, 64};
        return data;
    }

    public static byte[] setHorizontalmovementPosition(byte[] b) {
        byte[] data = new byte[]{27, 68};
        byte[] nul = new byte[1];
        data = byteMerger(data, b);
        data = byteMerger(data, nul);
        return data;
    }

    public static byte[] selectOrCancelBoldModel(int n) {
        byte[] data = new byte[]{27, 69, (byte)n};
        return data;
    }

    public static byte[] selectOrCancelDoubelPrintModel(int n) {
        byte[] data = new byte[]{27, 71, (byte)n};
        return data;
    }

    public static byte[] printAndFeed(int n) {
        byte[] data = new byte[]{27, 74, (byte)n};
        return data;
    }

    public static byte[] selectFont(int n) {
        byte[] data = new byte[]{27, 77, (byte)n};
        return data;
    }

    public static byte[] selectInternationalCharacterSets(int n) {
        byte[] data = new byte[]{27, 82, (byte)n};
        return data;
    }

    public static byte[] selectOrCancelCW90(int n) {
        byte[] data = new byte[]{27, 86, (byte)n};
        return data;
    }

    public static byte[] setRelativeHorizontalPrintPosition(int nL, int nH) {
        byte[] data = new byte[]{27, 92, (byte)nL, (byte)nH};
        return data;
    }

    public static byte[] selectAlignment(int n) {
        byte[] data = new byte[]{27, 97, (byte)n};
        return data;
    }

    public static byte[] allowOrForbidPressButton(int n) {
        byte[] data = new byte[]{27, 99, 53, (byte)n};
        return data;
    }

    public static byte[] printAndFeedForward(int n) {
        byte[] data = new byte[]{27, 100, (byte)n};
        return data;
    }

    public static byte[] creatCashboxContorlPulse(int m, int t1, int t2) {
        byte[] data = new byte[]{27, 112, (byte)m, (byte)t1, (byte)t2};
        return data;
    }

    public static byte[] selectCharacterCodePage(int n) {
        byte[] data = new byte[]{27, 116, (byte)n};
        return data;
    }

    public static byte[] selectOrCancelConvertPrintModel(int n) {
        byte[] data = new byte[]{27, 123, (byte)n};
        return data;
    }

    public static byte[] printBmpInFLASH(int n, int m) {
        byte[] data = new byte[]{28, 112, (byte)n, (byte)m};
        return data;
    }

    /*public static byte[] definedFlashBmp(List<Bitmap> list, int n, BmpType bmpType) {
        byte[] data = new byte[]{28, 113, (byte)n};
        if (list != null && list.size() != 0) {
            for(int i = 0; i < list.size(); ++i) {
                Bitmap bitmap = (Bitmap)list.get(i);
                byte[] bmp = BitmapToByteData.flashBmpToSendData(bitmap, bmpType);
                data = byteMerger(data, bmp);
            }

            return data;
        } else {
            return new byte[0];
        }
    }*/

    public static byte[] selectCharacterSize(int n) {
        byte[] data = new byte[]{29, 33, (byte)n};
        return data;
    }

  /*  public static byte[] definedDownLoadBmp(Bitmap bitmap, BmpType bmpType) {
        byte[] data = new byte[]{29, 42};
        byte[] bmp = BitmapToByteData.downLoadBmpToSendData(bitmap, bmpType);
        data = byteMerger(data, bmp);
        return data;
    }*/

    public static byte[] printDownLoadBmp(int m) {
        byte[] data = new byte[]{29, 47, (byte)m};
        return data;
    }

    public static byte[] selectOrCancelInvertPrintModel(int n) {
        byte[] data = new byte[]{29, 66, (byte)n};
        return data;
    }

    public static byte[] selectHRICharacterPrintPosition(int n) {
        byte[] data = new byte[]{29, 72, (byte)n};
        return data;
    }

    public static byte[] setLeftSpace(int nL, int nH) {
        byte[] data = new byte[]{29, 76, (byte)nL, (byte)nH};
        return data;
    }

    public static byte[] setHorizontalAndVerticalMoveUnit(int x, int y) {
        byte[] data = new byte[]{29, 80, (byte)x, (byte)y};
        return data;
    }

    public static byte[] setPrintAreaWidth(int nL, int nH) {
        byte[] data = new byte[]{29, 87, (byte)nL, (byte)nH};
        return data;
    }

    public static byte[] selectHRIFont(int n) {
        byte[] data = new byte[]{29, 102, (byte)n};
        return data;
    }

    public static byte[] setBarcodeHeight(int n) {
        byte[] data = new byte[]{29, 104, (byte)n};
        return data;
    }

    public static byte[] printBarcode(int m, String content) {
        byte[] data = new byte[]{29, 107, (byte)m};
        byte[] end = new byte[1];
        byte[] text = strTobytes(content);
        data = byteMerger(data, text);
        data = byteMerger(data, end);
        return data;
    }

    public static byte[] printBarcode(int m, int n, String content) {
        byte[] data = new byte[]{29, 107, (byte)m, (byte)n};
        byte[] text = strTobytes(content);
        data = byteMerger(data, text);
        return data;
    }

    public static byte[] printcode128(String content) {
        byte[] data = new byte[]{29, 107, 73, 10, 123, 65, 48, 49, 50, 51, 52, 53, 54, 55};
        byte[] text = strTobytes(content);
        data = byteMerger(data, text);
        byte[] end = new byte[]{13, 10};
        data = byteMerger(data, end);
        return data;
    }

   /* public static byte[] printRasterBmp(int m, Bitmap bitmap, BmpType bmpType, AlignType alignType, int pagewidth) {
        byte[] data = BitmapToByteData.rasterBmpToSendData(m, bitmap, bmpType, alignType, pagewidth);
        return data;
    }*/

    public static byte[] setBarcodeWidth(int n) {
        byte[] data = new byte[]{29, 119, (byte)n};
        return data;
    }

    public static byte[] setChineseCharacterModel(int n) {
        byte[] data = new byte[]{28, 33, (byte)n};
        return data;
    }

    public static byte[] selectChineseCharModel() {
        byte[] data = new byte[]{28, 38};
        return data;
    }

    public static byte[] selectOrCancelChineseCharUnderLineModel(int n) {
        byte[] data = new byte[]{28, 45, (byte)n};
        return data;
    }

    public static byte[] CancelChineseCharModel() {
        byte[] data = new byte[]{28, 46};
        return data;
    }

    public static byte[] definedUserDefinedChineseChar(int c2, byte[] b) {
        byte[] data = new byte[]{28, 50, -2, (byte)c2};
        data = byteMerger(data, b);
        return data;
    }

    public static byte[] setChineseCharLeftAndRightSpace(int n1, int n2) {
        byte[] data = new byte[]{28, 83, (byte)n1, (byte)n2};
        return data;
    }

    public static byte[] selectOrCancelChineseCharDoubleWH(int n) {
        byte[] data = new byte[]{28, 87, (byte)n};
        return data;
    }

    public static byte[] queryPrinterState() {
        byte[] data = new byte[]{27, 118};
        return data;
    }

    public static byte[] openOrCloseLableModelInReceip(Boolean open) {
        byte[] data;
        if (open) {
            data = new byte[]{31, 27, 31, 0, 1, 1, -128, 1};
            return data;
        } else {
            data = new byte[]{31, 27, 31, 0, 1, 1, -128, 0};
            return data;
        }
    }

    public static byte[] endOfLable() {
        byte[] data = new byte[]{31, 27, 31, 0, 1, 1, -127};
        return data;
    }

    public static byte[] checkLableAndGap() {
        byte[] data = new byte[]{31, 27, 31, 0, 1, 1, -126};
        return data;
    }

    public static byte[] setTheLableWidth(int width) {
        byte[] data = new byte[]{31, 27, 31, 0, 1, 1, -125, (byte)width};
        return data;
    }

    public static byte[] selectFontB() {
        byte[] data = new byte[]{31, 27, 31, 48, 1};
        return data;
    }

    public static byte[] seletFontA() {
        byte[] data = new byte[]{27, 33, 0};
        return data;
    }

    public static byte[] setSpeed(int n) {
        byte[] data = new byte[]{31, 27, 31, 47, (byte)n};
        return data;
    }

    public static byte[] setDormancyTime(int n) {
        byte[] data = new byte[]{31, 27, 31, 0, 0, 1, -94, (byte)n};
        return data;
    }

    public static byte[] setOffTime(int n) {
        byte[] data = new byte[]{31, 27, 31, 0, 0, 1, -93, (byte)n};
        return data;
    }

    private static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }

    private static byte[] strTobytes(String str) {
        byte[] data = null;

        try {
            byte[] b = str.getBytes("utf-8");
            if (charsetName == null | charsetName == "") {
                charsetName = "gbk";
            }

            data = (new String(b, "utf-8")).getBytes(charsetName);
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        return data;
    }
}