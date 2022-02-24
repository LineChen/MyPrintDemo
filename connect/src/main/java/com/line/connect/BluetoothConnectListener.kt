package com.line.connect

/**
 * created by chenliu on  2022/2/17 1:51 下午.
 */
interface BluetoothConnectListener {

    /**
     * 不合法的蓝牙地址
     */
    fun onNotValidMacAddress(address: String)

    fun onConnectSuccess(address: String)

    fun onConnectFailed(address: String, t: Throwable)

    fun onDisconnectSuccess(address: String?)

    fun onDisconnectFailed(address: String?, t: Throwable)

}