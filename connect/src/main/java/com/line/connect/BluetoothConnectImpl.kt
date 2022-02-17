package com.line.connect

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import java.io.InputStream
import java.io.OutputStream
import java.util.*

/**
 * created by chenliu on  2022/2/17 2:04 下午.
 */
class BluetoothConnectImpl : IBluetoothConnect {
    companion object {
        /**
         * @see android.bluetooth.BluetoothDevice.createRfcommSocketToServiceRecord
         * Hint: If you are connecting to a Bluetooth serial board then try using the well-known
         * SPP UUID 00001101-0000-1000-8000-00805F9B34FB.
         * However if you are connecting to an Android peer then please generate your own unique UUID.
         */
        private val SPP_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    }

    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null
    private var inputStream: InputStream? = null

    private var connectListener: BluetoothConnectListener? = null


    override fun connect(address: String) {
        val validBluetoothAddress = BluetoothAdapter.checkBluetoothAddress(address)
        if (validBluetoothAddress) {
            bluetoothAdapter.cancelDiscovery()
            try {
                val remoteDevice = bluetoothAdapter.getRemoteDevice(address)
                bluetoothSocket = remoteDevice.createRfcommSocketToServiceRecord(SPP_UUID)
                bluetoothSocket?.connect()
                outputStream = bluetoothSocket!!.outputStream
                inputStream = bluetoothSocket!!.inputStream
                connectListener?.onConnectSuccess()
            } catch (t: Throwable) {
                connectListener?.onConnectFailed(t)
            }
        } else {
            connectListener?.onNotValidMacAddress(address)
        }
    }

    override fun disconnect() {
        try {
            outputStream?.flush()
            bluetoothSocket?.close()
            outputStream?.close()
            inputStream?.close()
            outputStream = null
            inputStream = null
            connectListener?.onDisconnectSuccess()
        } catch (t: Throwable) {
            connectListener?.onDisconnectFailed(t)
        }
    }

    override fun setBluetoothConnectListener(l: BluetoothConnectListener) {
        this.connectListener = l
    }
}