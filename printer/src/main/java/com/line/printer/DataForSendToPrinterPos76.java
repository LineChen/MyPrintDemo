package com.line.printer;

import android.graphics.Bitmap;

public class DataForSendToPrinterPos76 {
    public DataForSendToPrinterPos76() {
    }

    public static byte[] horizontalPositioning() {
        byte[] data = new byte[]{9};
        return data;
    }

    public static byte[] printAndFeedLine() {
        byte[] data = new byte[]{10};
        return data;
    }

    public static byte[] PrintAndCarriageReturn() {
        byte[] data = new byte[]{13};
        return data;
    }

    public static byte[] sendRealtimestatus(int n) {
        byte[] data = new byte[]{16, 4, (byte)n};
        return data;
    }

    public static byte[] requestRealtimeForPrint(int n) {
        byte[] data = new byte[]{16, 5, (byte)n};
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

    public static byte[] selectOrCancleCustomChar(int n) {
        byte[] data = new byte[]{27, 37, (byte)n};
        return data;
    }

    public static byte[] defineuserDefinedCharacters(int c1, int c2, byte[] b) {
        byte[] data = new byte[]{27, 38, 3, (byte)c1, (byte)c2};
        data = byteMerger(data, b);
        return data;
    }

   /* public static byte[] selectBmpModel(int m, Bitmap bitmap, BmpType bmpType) {
        byte[] data = BitmapToByteData.baBmpToSendData(m, bitmap, bmpType);
        return data;
    }*/

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

    public static byte[] printHeadReplaceEnter() {
        byte[] data = new byte[]{27, 60};
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

    public static byte[] printAndBackFeed(int n) {
        byte[] data = new byte[]{27, 75, (byte)n};
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

    public static byte[] selectOrCancelUnidirectionPrint(int n) {
        byte[] data = new byte[]{27, 85, (byte)n};
        return data;
    }

    public static byte[] selectAlignment(int n) {
        byte[] data = new byte[]{27, 97, (byte)n};
        return data;
    }

    public static byte[] selectPrintTransducerOutPutPageOutSignal(int n) {
        byte[] data = new byte[]{27, 99, 51, (byte)n};
        return data;
    }

    public static byte[] selectPrintTransducerStopPrint(int n) {
        byte[] data = new byte[]{27, 99, 52, (byte)n};
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

    public static byte[] printAndFeedUnidirection(int n) {
        byte[] data = new byte[]{27, 101, (byte)n};
        return data;
    }

    public static byte[] creatCashboxContorlPulse(int m, int t1, int t2) {
        byte[] data = new byte[]{27, 112, (byte)m, (byte)t1, (byte)t2};
        return data;
    }

    public static byte[] selectPrintColor(int n) {
        byte[] data = new byte[]{27, 114, (byte)n};
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

    public static byte[] executePrintDataSaveByTransformToHex() {
        byte[] data = new byte[]{29, 40, 65, 2, 0, 0, 1};
        return data;
    }

    public static byte[] selectCutPagerModerAndCutPager(int m) {
        byte[] data = new byte[]{29, 86, (byte)m};
        return data;
    }

    public static byte[] selectCutPagerModerAndCutPager(int m, int n) {
        if (m != 66) {
            return new byte[0];
        } else {
            byte[] data = new byte[]{29, 86, (byte)m, (byte)n};
            return data;
        }
    }

    public static byte[] openOrCloseAutoReturnPrintState(int n) {
        byte[] data = new byte[]{29, 97, (byte)n};
        return data;
    }

    public static byte[] returnState(int n) {
        byte[] data = new byte[]{29, 114, (byte)n};
        return data;
    }

    public static byte[] setConnectWaitTime(int t1, int t2) {
        byte[] data = new byte[]{29, 122, 48, (byte)t1, (byte)t2};
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

    public static byte[] cancelChineseCharModel() {
        byte[] data = new byte[]{28, 46};
        return data;
    }

    public static byte[] definedUserDefinedChineseChar(int c2, byte[] b) {
        byte[] data = new byte[]{28, 50, -2, (byte)c2};
        data = byteMerger(data, b);
        return data;
    }

    public static byte[] cancelUserDefinedChineseChar(int c2) {
        byte[] data = new byte[]{28, 63, -2, (byte)c2};
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

    public static byte[] setBlackPositionRecord(int a, int m, int nL, int nH) {
        byte[] data = new byte[]{29, 40, 70, 4, 0, (byte)a, (byte)m, (byte)nL, (byte)nH};
        return data;
    }

    public static byte[] feedBlackPaperToPrintPosition() {
        byte[] data = new byte[]{29, 12};
        return data;
    }

    public static byte[] setRollBackLength(int n) {
        byte[] data = new byte[]{27, 94, (byte)n};
        return data;
    }

    public static byte[] setOrderLength(int nL, int nH) {
        byte[] data = new byte[]{27, 126, (byte)nL, (byte)nH};
        return data;
    }

    public static byte[] feedpaperToOrderEnd() {
        byte[] data = new byte[]{27, 127};
        return data;
    }

    public static byte[] printHeaderRecordAndFeedToPrintStartPosition() {
        byte[] data = new byte[]{29, 60};
        return data;
    }

    private static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }
}