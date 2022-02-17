package com.line.connect

/**
 * created by chenliu on  2022/2/17 1:51 下午.
 */
interface BluetoothConnectListener {

    /**
     * 不合法的蓝牙地址
     */
    fun onNotValidMacAddress(address: String)

    fun onConnectSuccess()

    fun onConnectFailed(t: Throwable)

    fun onDisconnectSuccess()

    fun onDisconnectFailed(t: Throwable)

}