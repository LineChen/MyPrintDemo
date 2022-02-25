package com.line.printer.base

import java.nio.charset.Charset
import kotlin.math.max

/**
 * created by chenliu on  2022/2/22 11:05 上午.
 */
data class PrinterConfig(
    val LINE_BYTE_SIZE: Int,
    val LEFT_LENGTH: Int,
    val LEFT_TEXT_MAX_LENGTH_OF_3_COLUMN: Int,
    val LEFT_TEXT_MAX_BYTE_OF_2_COLUMN: Int
)

val PRINTER_CONFIG_58 = PrinterConfig(32, 16, 5, 14)
val PRINTER_CONFIG_80 = PrinterConfig(48, 24, 8, 20)

class PrintUtil(private val config: PrinterConfig) {

    companion object {
        private const val SPACE = " "
    }

    /**
     * 获取数据长度
     *
     * @param msg
     * @return
     */
    private fun getBytesLength(msg: String): Int {
        return msg.toByteArray(Charset.forName("GBK")).size
    }


    fun getTowLineString(leftText: String, rightText: String): String {
        val sb = StringBuilder()
        val leftTextLength = getBytesLength(leftText)
        val rightTextLength = getBytesLength(rightText)
        sb.append(leftText)

        // 计算两侧文字中间的空格
        val marginBetweenMiddleAndRight = config.LINE_BYTE_SIZE - leftTextLength - rightTextLength
        for (i in 0 until marginBetweenMiddleAndRight) {
            sb.append(SPACE)
        }
        sb.append(rightText)
        return sb.toString()
    }

    fun getTowLineStringPair(leftText: String, rightText: String): Pair<String, String> {
        val sb = StringBuilder()
        val leftTextLength = getBytesLength(leftText)
        val rightTextLength = getBytesLength(rightText)
        sb.append(leftText)

        // 计算两侧文字中间的空格
        val marginBetweenMiddleAndRight = config.LINE_BYTE_SIZE - leftTextLength - rightTextLength
        for (i in 0 until marginBetweenMiddleAndRight) {
            sb.append(SPACE)
        }
        return Pair(sb.toString(), rightText)
    }

    fun getTowLineStringLines(leftText: String, rightText: String): List<Pair<String, String>> {
        val maxLen = config.LEFT_TEXT_MAX_BYTE_OF_2_COLUMN
        val leftTextList = mutableListOf<String>()
        val sb = java.lang.StringBuilder()
        leftText.toCharArray().forEachIndexed { index, c ->
            sb.append(c)
            if (getBytesLength(sb.toString()) >= maxLen || index == leftText.length - 1) {
                leftTextList.add(sb.toString())
                sb.clear()
            }
        }
        val rightTextList = mutableListOf<String>()
        rightText.toCharArray().forEachIndexed { index, c ->
            sb.append(c)
            if (getBytesLength(sb.toString()) >= maxLen || index == rightText.length - 1) {
                rightTextList.add(sb.toString())
                sb.clear()
            }
        }
        val lines = mutableListOf<Pair<String, String>>()
        val maxLines = max(leftTextList.size, rightTextList.size)
        for (i in 0 until maxLines) {
            val leftLineText: String? = if (i >= leftTextList.size) null else leftTextList[i]
            val rightLineText: String? = if (i >= rightTextList.size) null else rightTextList[i]
            val lineStringPair = getTowLineStringPair(leftLineText ?: "", rightLineText ?: "")
            lines.add(lineStringPair)
        }
        return lines
    }


    /**
     * 中间一个text放在正中间
     *
     * @param left
     * @param middleText
     * @param rightText
     * @return
     */
    fun getThreeLineStringExactCenter(left: String, middleText: String, rightText: String): String {
        var leftText = left
        val sb = StringBuilder()
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length > config.LEFT_TEXT_MAX_LENGTH_OF_3_COLUMN) {
            leftText = leftText.substring(0, config.LEFT_TEXT_MAX_LENGTH_OF_3_COLUMN) + ".."
        }
        val leftTextLength = getBytesLength(leftText)
        val middleTextLength = getBytesLength(middleText)
        val rightTextLength = getBytesLength(rightText)
        sb.append(leftText)
        // 计算左侧文字和中间文字的空格长度
        val marginBetweenLeftAndMiddle = config.LEFT_LENGTH - leftTextLength - middleTextLength / 2
        for (i in 0 until marginBetweenLeftAndMiddle) {
            sb.append(SPACE)
        }
        sb.append(middleText)

        // 计算右侧文字和中间文字的空格长度
        val marginBetweenMiddleAndRight =
            config.LINE_BYTE_SIZE - leftTextLength - middleTextLength - rightTextLength - marginBetweenLeftAndMiddle + 1
        for (i in 0 until marginBetweenMiddleAndRight) {
            sb.append(SPACE)
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length - 1, sb.length).append(rightText)
        return sb.toString()
    }

    /**
     * 保证中间一个打印位置的最后一位在17位
     *
     * @param left
     * @param middleText
     * @param rightText
     * @return
     */
    fun getThreeLineStringLastIndex(left: String, middleText: String, rightText: String): String {
        var leftText = left
        val sb = StringBuilder()
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length > config.LEFT_TEXT_MAX_LENGTH_OF_3_COLUMN) {
            leftText = leftText.substring(0, config.LEFT_TEXT_MAX_LENGTH_OF_3_COLUMN) + ".."
        }
        val leftTextLength = getBytesLength(leftText)
        val middleTextLength = getBytesLength(middleText)
        val rightTextLength = getBytesLength(rightText)
        sb.append(leftText)
        // 计算左侧文字和中间文字的空格长度
        val marginBetweenLeftAndMiddle = config.LEFT_LENGTH + 1 - leftTextLength - middleTextLength
        for (i in 0 until marginBetweenLeftAndMiddle) {
            sb.append(SPACE)
        }
        sb.append(middleText)

        // 计算右侧文字和中间文字的空格长度
        val marginBetweenMiddleAndRight =
            config.LINE_BYTE_SIZE - leftTextLength - middleTextLength - rightTextLength - marginBetweenLeftAndMiddle + 1
        for (i in 0 until marginBetweenMiddleAndRight) {
            sb.append(SPACE)
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length - 1, sb.length).append(rightText)
        return sb.toString()
    }

    fun getDashLine(): String {
        val sb = StringBuilder()
        for (i in 0 until config.LINE_BYTE_SIZE) {
            sb.append("-")
        }
        return sb.toString()
    }
}

