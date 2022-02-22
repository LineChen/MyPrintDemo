package com.line.printer;


import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;

/**
 * created by chenliu on  2022/2/15 5:02 下午.
 */
public class Print80Util {

    /**
     * 打印纸一行最大的字节
     */
    private static final int LINE_BYTE_SIZE = 48;

    /**
     * 打印三列时，中间一列的中心线距离打印纸左侧的距离
     */
    private static final int LEFT_LENGTH = 24;

    /**
     * 打印三列时，中间一列的中心线距离打印纸右侧的距离
     */
    private static final int RIGHT_LENGTH = 16;

    /**
     * 打印三列时，第一列汉字最多显示几个文字
     */
    private static final int LEFT_TEXT_MAX_LENGTH = 5;


    /**
     * 获取数据长度
     *
     * @param msg
     * @return
     */
    public static int getBytesLength(String msg) {
        return msg.getBytes(Charset.forName("GBK")).length;
    }


    public static String getTowLineString(String leftText, String rightText) {
        StringBuilder sb = new StringBuilder();
        int leftTextLength = getBytesLength(leftText);
        int rightTextLength = getBytesLength(rightText);
        sb.append(leftText);

        // 计算两侧文字中间的空格
        int marginBetweenMiddleAndRight = LINE_BYTE_SIZE - leftTextLength - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }
        sb.append(rightText);
        return sb.toString();
    }

    /**
     * 中间一个text放在正中间
     *
     * @param leftText
     * @param middleText
     * @param rightText
     * @return
     */
    public static String getThreeLineStringExactCenter(String leftText, String middleText, String rightText) {
        StringBuilder sb = new StringBuilder();
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
            leftText = leftText.substring(0, LEFT_TEXT_MAX_LENGTH) + "..";
        }
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int rightTextLength = getBytesLength(rightText);

        sb.append(leftText);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = LEFT_LENGTH - leftTextLength - middleTextLength / 2;

        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append("-");
        }
        sb.append(middleText);

        // 计算右侧文字和中间文字的空格长度
        int marginBetweenMiddleAndRight = LINE_BYTE_SIZE - leftTextLength - middleTextLength - rightTextLength - marginBetweenLeftAndMiddle + 1;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append("-");
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);
        return sb.toString();
    }

    /**
     * 保证中间一个打印位置的最后一位在17位
     *
     * @param leftText
     * @param middleText
     * @param rightText
     * @return
     */
    public static String getThreeLineStringLastIndex(String leftText, String middleText, String rightText) {
        StringBuilder sb = new StringBuilder();
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
            leftText = leftText.substring(0, LEFT_TEXT_MAX_LENGTH) + "..";
        }
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int rightTextLength = getBytesLength(rightText);

        sb.append(leftText);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = LEFT_LENGTH + 1 - leftTextLength - middleTextLength;

        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append("-");
        }
        sb.append(middleText);

        // 计算右侧文字和中间文字的空格长度
        int marginBetweenMiddleAndRight = LINE_BYTE_SIZE - leftTextLength - middleTextLength - rightTextLength - marginBetweenLeftAndMiddle + 1;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append("-");
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);
        return sb.toString();
    }

    @NotNull
    public static String getDashLine() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < LINE_BYTE_SIZE; i++) {
            sb.append("-");
        }
        return sb.toString();
    }
}
