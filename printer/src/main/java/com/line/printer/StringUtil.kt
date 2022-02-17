package com.line.printer

import java.lang.Exception
import java.nio.charset.Charset
import java.text.DecimalFormat

fun charSetName(): Charset = Charset.forName("gbk")

fun strToBytes(str: String): ByteArray? {

    val data: ByteArray
    try {
        val b = str.toByteArray(charset = Charsets.UTF_8)
        data = String(b).toByteArray(Charset.forName("gbk"))
    } catch (e: Exception) {
        return null
    }
    return data
}


fun strToBytes(str: String, charset: String): ByteArray? {
    val data: ByteArray?
    try {
        val b = str.toByteArray(charset = Charsets.UTF_8)
        data = String(b).toByteArray(Charset.forName("gbk"))
    } catch (e: Exception) {
        return null
    }

    return data
}


fun numberFormat(doubleNumber: Double): String {
    return DecimalFormat("0.00").format(doubleNumber)
}
