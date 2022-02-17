package com.line.printer;

import android.graphics.Bitmap;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class DataForSendToPrinterPos80 {
    private static String charsetName = "gbk";

    public DataForSendToPrinterPos80() {
    }

    public static void setCharsetName(String charset) {
        charsetName = charset;
    }

    public static byte[] horizontalPositioning() {
        return new byte[]{9};
    }

    public static byte[] printAndFeedLine() {
        return new byte[]{10};
    }

    public static byte[] printAndBackStandardmodel() {
        return new byte[]{12};
    }

    public static byte[] PrintAndCarriageReturn() {
        return new byte[]{13};
    }

    public static byte[] canclePrintDataByPagemodel() {
        return new byte[]{24};
    }

    public static byte[] sendRealtimestatus(int n) {
        return new byte[]{16, 4, (byte) n};
    }

    public static byte[] requestRealtimeForPrint(int n) {
        return new byte[]{16, 5, (byte) n};
    }

    public static byte[] openCashboxRealtime(int m, int t) {
        return new byte[]{16, 20, 1, (byte) m, (byte) t};
    }

    public static byte[] printByPagemodel() {
        return new byte[]{27, 12};
    }

    public static byte[] setCharRightSpace(int n) {
        return new byte[]{27, 32, (byte) n};
    }

    public static byte[] selectPrintModel(int n) {
        return new byte[]{27, 33, (byte) n};
    }

    public static byte[] setAbsolutePrintPosition(int m, int n) {
        return new byte[]{27, 36, (byte) m, (byte) n};
    }

    public static byte[] selectOrCancleCustomChar(int n) {
        return new byte[]{27, 37, (byte) n};
    }

    public static byte[] defineuserDefinedCharacters(int c1, int c2, byte[] b) {
        byte[] data = new byte[]{27, 38, 3, (byte) c1, (byte) c2};
        data = byteMerger(data, b);
        return data;
    }

    public static byte[] selectBmpModel(int m, int nL, int nH, byte[] b) {
        byte[] data = new byte[]{27, 42, (byte) m, (byte) nL, (byte) nH};
        data = byteMerger(data, b);
        return data;
    }

    public static byte[] selectOrCancelUnderlineModel(int n) {
        return new byte[]{27, 45, (byte) n};
    }

    public static byte[] setDefultLineSpacing() {
        return new byte[]{27, 50};
    }

    public static byte[] setLineSpaceing(int n) {
        return new byte[]{27, 51, (byte) n};
    }

    public static byte[] selectPrinter(int n) {
        return new byte[]{27, 61, (byte) n};
    }

    public static byte[] cancelUserDefinedCharacters(int n) {
        return new byte[]{27, 63, (byte) n};
    }

    public static byte[] initializePrinter() {
        return new byte[]{27, 64};
    }

    public static byte[] setHorizontalmovementPosition(byte[] b) {
        byte[] data = new byte[]{27, 68};
        byte[] nul = new byte[1];
        data = byteMerger(data, b);
        data = byteMerger(data, nul);
        return data;
    }

    public static byte[] selectOrCancelBoldModel(int n) {
        return new byte[]{27, 69, (byte) n};
    }

    public static byte[] selectOrCancelDoubelPrintModel(int n) {
        return new byte[]{27, 71, (byte) n};
    }

    public static byte[] printAndFeed(int n) {
        return new byte[]{27, 74, (byte) n};
    }

    public static byte[] selectPageModel() {
        return new byte[]{27, 76};
    }

    public static byte[] selectFont(int n) {
        return new byte[]{27, 77, (byte) n};
    }

    public static byte[] selectInternationalCharacterSets(int n) {
        return new byte[]{27, 82, (byte) n};
    }

    public static byte[] selectStandardModel() {
        return new byte[]{27, 83};
    }

    public static byte[] selectPrintDirectionUnderPageModel(int n) {
        return new byte[]{27, 84, (byte) n};
    }

    public static byte[] selectOrCancelCW90(int n) {
        return new byte[]{27, 86, (byte) n};
    }

    public static byte[] setPrintAreaUnderPageModel(int xL, int xH, int yL, int yH, int dxL, int dxH, int dyL, int dyH) {
        return new byte[]{27, 87, (byte) xL, (byte) xH, (byte) yL, (byte) yH, (byte) dxL, (byte) dxH, (byte) dyL, (byte) dyH};
    }

    public static byte[] setRelativeHorizontalPrintPosition(int nL, int nH) {
        return new byte[]{27, 92, (byte) nL, (byte) nH};
    }

    public static byte[] selectAlignment(int n) {
        return new byte[]{27, 97, (byte) n};
    }

    public static byte[] selectPrintTransducerOutPutPageOutSignal(int n) {
        return new byte[]{27, 99, 51, (byte) n};
    }

    public static byte[] selectPrintTransducerStopPrint(int n) {
        return new byte[]{27, 99, 52, (byte) n};
    }

    public static byte[] allowOrForbidPressButton(int n) {
        return new byte[]{27, 99, 53, (byte) n};
    }

    public static byte[] printAndFeedForward(int n) {
        return new byte[]{27, 100, (byte) n};
    }

    public static byte[] creatCashboxContorlPulse(int m, int t1, int t2) {
        return new byte[]{27, 112, (byte) m, (byte) t1, (byte) t2};
    }

    public static byte[] selectCharacterCodePage(int n) {
        return new byte[]{27, 116, (byte) n};
    }

    public static byte[] selectOrCancelConvertPrintModel(int n) {
        return new byte[]{27, 123, (byte) n};
    }

    public static byte[] printBmpInFLASH(int n, int m) {
        return new byte[]{28, 112, (byte) n, (byte) m};
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
        return new byte[]{29, 33, (byte) n};
    }

    public static byte[] setAbsolutePositionUnderPageModel(int nL, int nH) {
        return new byte[]{29, 36, (byte) nL, (byte) nH};
    }

    /*public static byte[] definedDownLoadBmp(Bitmap bitmap, BmpType bmpType) {
        byte[] data = new byte[]{29, 42};
        byte[] bmp = BitmapToByteData.downLoadBmpToSendData(bitmap, bmpType);
        data = byteMerger(data, bmp);
        return data;
    }*/

    public static byte[] executePrintDataSaveByTransformToHex() {
        return new byte[]{29, 40, 65, 2, 0, 0, 1};
    }

    public static byte[] printDownLoadBmp(int m) {
        return new byte[]{29, 47, (byte) m};
    }

    public static byte[] startOrStopMacrodeFinition() {
        return new byte[]{29, 58};
    }

    public static byte[] selectOrCancelInvertPrintModel(int n) {
        return new byte[]{29, 66, (byte) n};
    }

    public static byte[] selectHRICharacterPrintPosition(int n) {
        return new byte[]{29, 72, (byte) n};
    }

    public static byte[] setLeftSpace(int nL, int nH) {
        return new byte[]{29, 76, (byte) nL, (byte) nH};
    }

    public static byte[] setHorizontalAndVerticalMoveUnit(int x, int y) {
        return new byte[]{29, 80, (byte) x, (byte) y};
    }

    public static byte[] selectCutPagerModerAndCutPager(int m) {
        return new byte[]{29, 86, (byte) m};
    }

    public static byte[] selectCutPagerModerAndCutPager(int m, int n) {
        if (m != 66) {
            return new byte[0];
        } else {
            return new byte[]{29, 86, (byte) m, (byte) n};
        }
    }

    public static byte[] setPrintAreaWidth(int nL, int nH) {
        return new byte[]{29, 87, (byte) nL, (byte) nH};
    }

    public static byte[] setVerticalRelativePositionUnderPageModel(int nL, int nH) {
        return new byte[]{29, 92, (byte) nL, (byte) nH};
    }

    public static byte[] executeMacrodeCommand(int r, int t, int m) {
        return new byte[]{29, 94, (byte) r, (byte) t, (byte) m};
    }

    public static byte[] openOrCloseAutoReturnPrintState(int n) {
        return new byte[]{29, 97, (byte) n};
    }

    public static byte[] selectHRIFont(int n) {
        return new byte[]{29, 102, (byte) n};
    }

    public static byte[] setBarcodeHeight(int n) {
        return new byte[]{29, 104, (byte) n};
    }

    public static byte[] printBarcode(int m, String content) {
        byte[] data = new byte[]{29, 107, (byte) m};
        byte[] end = new byte[1];
        byte[] text = strTobytes(content);
        data = byteMerger(data, text);
        data = byteMerger(data, end);
        return data;
    }

    public static byte[] printBarcode(int m, int n, String content) {
        byte[] data = new byte[]{29, 107, (byte) m, (byte) n};
        byte[] text = strTobytes(content);
        data = byteMerger(data, text);
        return data;
    }

    public static byte[] printBarcode(int alignment, int HRI, int width, int height, int type, int size, String content) {
        byte[] data1 = new byte[]{27, 97, (byte) alignment};
        byte[] data2 = new byte[]{29, 72, (byte) HRI};
        byte[] data3 = new byte[]{29, 119, (byte) width};
        byte[] data4 = new byte[]{29, 104, (byte) height};
        byte[] data5 = new byte[]{29, 107, (byte) type, (byte) size};
        byte[] text = strTobytes(content);
        data5 = byteMerger(data5, text);
        data5 = byteMerger(byteMerger(byteMerger(byteMerger(data1, data2), data3), data4), data5);
        return data5;
    }

    public static byte[] returnState(int n) {
        return new byte[]{29, 114, (byte) n};
    }

   /* public static byte[] printRasterBmp(int m, Bitmap bitmap, BmpType bmpType, AlignType alignType, int pagewidth) {
        byte[] data = BitmapToByteData.rasterBmpToSendData(m, bitmap, bmpType, alignType, pagewidth);
        return data;
    }*/

    public static byte[] setBarcodeWidth(int n) {
        return new byte[]{29, 119, (byte) n};
    }

    public static byte[] setChineseCharacterModel(int n) {
        return new byte[]{28, 33, (byte) n};
    }

    public static byte[] selectChineseCharModel() {
        return new byte[]{28, 38};
    }

    public static byte[] selectOrCancelChineseCharUnderLineModel(int n) {
        return new byte[]{28, 45, (byte) n};
    }

    public static byte[] CancelChineseCharModel() {
        return new byte[]{28, 46};
    }

    public static byte[] definedUserDefinedChineseChar(int c2, byte[] b) {
        byte[] data = new byte[]{28, 50, -2, (byte) c2};
        data = byteMerger(data, b);
        return data;
    }

    public static byte[] setChineseCharLeftAndRightSpace(int n1, int n2) {
        return new byte[]{28, 83, (byte) n1, (byte) n2};
    }

    public static byte[] selectOrCancelChineseCharDoubleWH(int n) {
        return new byte[]{28, 87, (byte) n};
    }

    public static byte[] printerOrderBuzzingHint(int n, int t) {
        return new byte[]{27, 66, (byte) n, (byte) t};
    }

    public static byte[] printerOrderBuzzingAndWarningLight(int m, int t, int n) {
        return new byte[]{27, 67, (byte) m, (byte) t, (byte) n};
    }

    public static byte[] SetsTheNumberOfColumnsOfTheDataAreaForPDF417(int n) {
        return new byte[]{29, 40, 107, 3, 0, 48, 65, (byte) n};
    }

    public static byte[] SetsTheNumberOfRowsOfTheDataAreaForPDF417(int n) {
        return new byte[]{29, 40, 107, 3, 0, 48, 66, (byte) n};
    }

    public static byte[] SetsTheModuleWidthOfPDF417(int n) {
        return new byte[]{29, 40, 107, 3, 0, 48, 67, (byte) n};
    }

    public static byte[] SetsTheModuleHeightForPDF417(int n) {
        return new byte[]{29, 40, 107, 3, 0, 48, 68, (byte) n};
    }

    public static byte[] SetsTheErrorCorrectionLevelForPDF417(int m, int n) {
        return new byte[]{29, 40, 107, 4, 0, 48, 69, (byte) m, (byte) n};
    }

    public static byte[] SpecifiesOrCancelsVariousPDF417SymbolOptions(int m) {
        return new byte[]{29, 40, 107, 3, 0, 48, 70, (byte) m};
    }

    public static byte[] StoresSymbolDataInThePDF417SymbolStorageArea(int pL, int pH, byte[] b) {
        byte[] data = new byte[]{29, 40, 107, (byte) pL, (byte) pH, 48, 80, 48};
        data = byteMerger(data, b);
        return data;
    }

    public static byte[] PrintsThePDF417SymbolDataInTheSymbolStorageArea() {
        return new byte[]{29, 40, 107, 3, 0, 48, 81, 48};
    }

    public static byte[] TransmitsTheSizeOfTheSymbolDataInTheSymbolStorageAreaPDF417() {
        return new byte[]{29, 40, 107, 3, 0, 48, 82, 48};
    }

    public static byte[] SetsTheSizeOfTheQRCodeSymbolModule(int n) {
        return new byte[]{29, 40, 107, 48, 103, (byte) n};
    }

    public static byte[] SetsTheErrorCorrectionLevelForQRCodeSymbol(int n) {
        return new byte[]{29, 40, 107, 48, 105, (byte) n};
    }

    public static byte[] StoresSymbolDataInTheQRCodeSymbolStorageArea(String code) {
        byte[] b = strTobytes(code);
        int a = b.length;
        int pL;
        int pH;
        if (a <= 255) {
            pL = a;
            pH = 0;
        } else {
            pH = a / 256;
            pL = a % 256;
        }

        byte[] data = new byte[]{29, 40, 107, 48, -128, (byte) pL, (byte) pH};
        data = byteMerger(data, b);
        return data;
    }

    public static byte[] PrintsTheQRCodeSymbolDataInTheSymbolStorageArea() {
        return new byte[]{29, 40, 107, 48, -127};
    }

    public static byte[] printQRcode(int n, int errLevel, String code) {
        byte[] b = strTobytes(code);
        int a = b.length;
        int nL;
        int nH;
        if (a <= 255) {
            nL = a;
            nH = 0;
        } else {
            nH = a / 256;
            nL = a % 256;
        }

        byte[] data = new byte[]{29, 40, 107, 48, 103, (byte) n, 29, 40, 107, 48, 105, (byte) errLevel, 29, 40, 107, 48, -128, (byte) nL, (byte) nH};
        data = byteMerger(data, b);
        byte[] c = new byte[]{29, 40, 107, 48, -127};
        data = byteMerger(data, c);
        return data;
    }

    public static byte[] TransmitsTheSizeOfTheSymbolDataInTheSymbolStorageAreaQRCode() {
        return new byte[]{29, 40, 107, 3, 0, 49, 82, 48};
    }

    public static byte[] SpecifiesTheModeForMaxiCodeSymbol(int n) {
        return new byte[]{29, 40, 107, 3, 0, 50, 65, (byte) n};
    }

    public static byte[] StoresSymbolDataInItheMaxiCodeSymbolStorageArea(int pL, int pH, byte[] b) {
        byte[] data = new byte[]{29, 40, 107, (byte) pL, (byte) pH, 50, 80, 48};
        data = byteMerger(data, b);
        return data;
    }

    public static byte[] PrintsTheMaxiCodeSymbolDataInTheSymbolStorageArea() {
        return new byte[]{29, 40, 107, 3, 0, 50, 81, 48};
    }

    public static byte[] TransmitsTheSizeOfTheEncodedSymbolDataInTheSymbolStorageAreaMaxiCode() {
        return new byte[]{29, 40, 107, 3, 0, 50, 82, 48};
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
            if (charsetName == null || charsetName.equals("")) {
                charsetName = "gbk";
            }

            data = (new String(b, "utf-8")).getBytes(charsetName);
        } catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
        }

        return data;
    }
}