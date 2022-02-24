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

            override fun onConnectSuccess(address: String) {
                ioScope.launch(Dispatchers.Main) {
                    isConnected = true
                    connectListener.onConnectSuccess(address)
                }
            }

            override fun onConnectFailed(address: String, t: Throwable) {
                ioScope.launch(Dispatchers.Main) {
                    connectListener.onConnectFailed(address, t)
                }
            }

            override fun onDisconnectSuccess(address: String?) {
                ioScope.launch(Dispatchers.Main) {
                    isConnected = false
                    connectListener.onDisconnectSuccess(address)
                }
            }

            override fun onDisconnectFailed(address: String?, t: Throwable) {
                ioScope.launch(Dispatchers.Main) {
                    connectListener.onDisconnectFailed(address, t)
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

    fun writeData(generateData: GenerateData, callback: WriteDataCallback) {
        val dataList = generateData.generate()
        ioScope.launch {
            var error: Throwable? = null
            dataList.forEach {
                bluetoothConnect.writeData(it, object : WriteDataCallback {
                    override fun onSuccess() {

                    }

                    override fun onFailed(t: Throwable) {
                        error = t
                    }
                })
            }
            ioScope.launch(Dispatchers.Main) {
                if (error == null) {
                    callback.onSuccess()
                } else {
                    callback.onFailed(error!!)
                }
            }
        }
    }


}