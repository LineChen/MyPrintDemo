package com.line.connect

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * created by chenliu on  2022/2/17 2:14 下午.
 */
class BluetoothConnectService(private val connectListener: BluetoothConnectListener) {

    private val bluetoothConnect: IBluetoothConnect = BluetoothConnectImpl()
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private var isConnected = false

    init {
        bluetoothConnect.setBluetoothConnectListener(object : BluetoothConnectListener {
            override fun onNotValidMacAddress(address: String) {
                ioScope.launch(Dispatchers.Main) {
                    connectListener.onNotValidMacAddress(address)
                }
            }

            override fun onConnectSuccess() {
                ioScope.launch(Dispatchers.Main) {
                    isConnected = true
                    connectListener.onConnectSuccess()
                }
            }

            override fun onConnectFailed(t: Throwable) {
                ioScope.launch(Dispatchers.Main) {
                    connectListener.onConnectFailed(t)
                }
            }

            override fun onDisconnectSuccess() {
                ioScope.launch(Dispatchers.Main) {
                    isConnected = false
                    connectListener.onDisconnectSuccess()
                }
            }

            override fun onDisconnectFailed(t: Throwable) {
                ioScope.launch(Dispatchers.Main) {
                    connectListener.onDisconnectFailed(t)
                }
            }
        })
    }

    fun connect(address: String) {
        ioScope.launch {
            bluetoothConnect.connect(address)
        }
    }

    fun disconnect() {
        ioScope.launch {
            bluetoothConnect.disconnect()
        }
    }

    fun isConnected(): Boolean {
        return isConnected
    }


}