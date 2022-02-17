package com.line.connect

/**
 * created by chenliu on  2022/2/17 2:04 下午.
 */
interface IBluetoothConnect {

    fun connect(address: String)

    fun disconnect()

    fun setBluetoothConnectListener(l: BluetoothConnectListener)
}