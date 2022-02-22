package com.line.printer.base

import com.line.printer.DataForSendToPrinterPos58
import com.line.printer.strToBytes

/**
 * created by chenliu on  2022/2/22 10:43 上午.
 */
class PrinterCommand58 : PrinterCommand {

    private val printUtil = PrintUtil(PRINTER_CONFIG_58)

    override fun initialize(): ByteArray {
        return DataForSendToPrinterPos58.initializePrinter()
    }

    override fun setAlignLeft(): ByteArray {
        return DataForSendToPrinterPos58.selectAlignment(0)
    }

    override fun setAlignCenter(): ByteArray {
        return DataForSendToPrinterPos58.selectAlignment(1)
    }

    override fun setAlignRight(): ByteArray {
        return DataForSendToPrinterPos58.selectAlignment(2)
    }

    override fun printAndFeedLine(): ByteArray {
        return DataForSendToPrinterPos58.printAndFeedLine()
    }

    override fun printAndFeedLine(n: Int): ByteArray {
        return DataForSendToPrinterPos58.printAndFeedForward(n)
    }

    override fun setCharacterSize(size: Int): ByteArray {
        return DataForSendToPrinterPos58.selectCharacterSize(size)
    }

    override fun setTextBold(bold: Boolean): ByteArray {
        return DataForSendToPrinterPos58.selectOrCancelBoldModel(if (bold) 1 else 0)
    }

    override fun getTowLineString(leftText: String, rightText: String): ByteArray {
        return strToBytes(printUtil.getTowLineString(leftText, rightText))
    }

    override fun getThreeLineStringExactCenter(leftText: String, middleText: String, rightText: String): ByteArray {
        return strToBytes(printUtil.getThreeLineStringExactCenter(leftText, middleText, rightText))
    }

    override fun getThreeLineStringLastIndex(leftText: String, middleText: String, rightText: String): ByteArray {
        return strToBytes(printUtil.getThreeLineStringLastIndex(leftText, middleText, rightText))
    }

    override fun printDashLine(): ByteArray {
        return strToBytes(printUtil.getDashLine())
    }
}