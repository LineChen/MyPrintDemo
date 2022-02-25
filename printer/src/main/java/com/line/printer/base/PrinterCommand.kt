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

    /**
     *  获取2段文字，使用场景如：左右两个字符串加起来超过一行，要分2行显示，如：
     *
     *  猪头肉肉夹馍加茄汁拌饭         28元
     *  显示成->
     *  猪头肉肉夹馍加               28元
     *  茄汁拌饭
     *
     *  猪头肉肉夹馍加茄汁拌饭         123456.78元
     *  显示成->
     *  猪头肉肉夹馍加               12345
     *  茄汁拌饭                    6.78元
     */
    fun getTowLineStringLines(leftText: String, rightText: String): List<Pair<ByteArray, ByteArray>>

    fun getTowLineString(leftText: String, rightText: String): ByteArray

    fun getThreeLineStringExactCenter(leftText: String, middleText: String, rightText: String): ByteArray

    fun getThreeLineStringLastIndex(leftText: String, middleText: String, rightText: String): ByteArray

    fun printDashLine(): ByteArray

    fun cutPaper(): ByteArray

}