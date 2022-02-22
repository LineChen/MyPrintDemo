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

    fun setCharacterSize(size:Int): ByteArray

    fun setTextBold(bold: Boolean): ByteArray

    fun getTowLineString(leftText: String, rightText: String): ByteArray

    fun getThreeLineStringExactCenter(leftText: String, middleText: String, rightText: String): ByteArray

    fun getThreeLineStringLastIndex(leftText: String, middleText: String, rightText: String): ByteArray

    fun printDashLine(): ByteArray


}