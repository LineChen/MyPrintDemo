package com.line.scan

/**
 * created by chenliu on  2022/2/16 11:01 上午.
 */
interface BluetoothScanListener {

    /**
     *蓝牙不可用
     */
    fun onNotSupport()

    /**
     * 蓝牙未开启
     */
    fun onBluetoothClosed()

    fun onBoundDevices(list: List<ScannedDevice>)

    fun onScanStarted()

    /**
     * 扫描结束
     */
    fun onScanFound(device: ScannedDevice)

    fun onScanFinished()

    fun onScanCanceled()

}