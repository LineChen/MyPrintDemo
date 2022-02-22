package com.line.printer.base

/**
 * created by chenliu on  2022/2/22 9:50 上午.
 */
interface PrinterCommand {

    fun initialize(): ByteArray

    fun setAlignLeft(): ByteArray

    fun setAlignCenter(): ByteArray

    fun setAlignRight(): ByteArray

    fun printAndFeedLine(): ByteArray

    fun printAndFeedLine(n: Int): ByteArray

    fun setCharacterSize(size: Int): ByteArray

    fun setTextBold(bold: Boolean): ByteArray

    /**
     * 向前走纸
     * 0 <= n <= 255
     */
    fun printAndFeed(n: Int): ByteArray

    /**
     * 获取2段文字，使用场景如：左边文字不加粗，右边文字加粗
     */
    fun getTowLineStringPair(leftText: String, rightText: String): Pair<ByteArray, ByteArray>

    fun getTowLineString(leftText: String, rightText: String): ByteArray

    fun getThreeLineStringExactCenter(leftText: String, middleText: String, rightText: String): ByteArray

    fun getThreeLineStringLastIndex(leftText: String, middleText: String, rightText: String): ByteArray

    fun printDashLine(): ByteArray


}