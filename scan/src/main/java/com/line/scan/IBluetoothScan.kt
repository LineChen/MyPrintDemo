package com.line.scan

/**
 * created by chenliu on  2022/2/16 10:45 上午.
 */
interface IBluetoothScan {

    fun startScan()

    fun stopScan()

    fun setOnBluetoothScanListener(l: BluetoothScanListener)
}