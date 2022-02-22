package com.line.printer.base

/**
 * created by chenliu on  2022/2/22 11:18 上午.
 */
enum class PrinterType {
    MM_58,
    MM_80
}

fun getPrinterCommand(type: PrinterType): PrinterCommand {
    return when (type) {
        PrinterType.MM_58 -> {
            PrinterCommand58()
        }
        PrinterType.MM_80 -> {
            PrinterCommand80()
        }
    }
}