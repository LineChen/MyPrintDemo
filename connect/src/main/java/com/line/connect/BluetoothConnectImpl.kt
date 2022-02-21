package com.line.connect

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.util.Log
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
        private const val TAG = "BluetoothConnectImpl"
    }

    private var bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null
    private var inputStream: InputStream? = null

    private var connectListener: BluetoothConnectListener? = null

    /**
     * @see android.bluetooth.BluetoothDevice.createInsecureRfcommSocketToServiceRecord
     *       创建一个 RFCOMM BluetoothSocket 套接字，准备使用 uuid 的 SDP 查找启动到此远程设备的不安全传出连接。
     *       通信通道将没有经过身份验证的链接密钥，即它将受到中间人攻击。对于蓝牙 2.1 设备，链接密钥将被加密，因为加密是强制性的。对于旧设备（蓝牙 2.1 之前的设备），链接密钥不会被加密。如果需要加密和经过身份验证的通信通道，请使用 createRfcommSocketToServiceRecord。
     *       这旨在与 BluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord 一起用于对等蓝牙应用程序。
     *       使用 BluetoothSocket.connect 启动传出连接。这还将执行给定 uuid 的 SDP 查找以确定要连接到哪个通道。
     *       远程设备将通过身份验证，并且此套接字上的通信将被加密。

     * @see android.bluetooth.BluetoothDevice.createRfcommSocketToServiceRecord
     *      创建一个 RFCOMM BluetoothSocket，准备使用 uuid 的 SDP 查找来启动到该远程设备的安全传出连接。
     *      这旨在与 BluetoothAdapter.listenUsingRfcommWithServiceRecord 一起用于对等蓝牙应用程序。
     *      使用 BluetoothSocket.connect 启动传出连接。这还将执行给定 uuid 的 SDP 查找以确定要连接到哪个通道。
     *      远程设备将通过身份验证，并且此套接字上的通信将被加密。
     *      仅当经过身份验证的套接字链接是可能的时才使用此套接字。身份验证是指对链接密钥进行身份验证，以防止中间人类型的攻击。例如，对于蓝牙 2.1 设备，如果任何设备没有输入和输出功能或仅具有显示数字键的功能，则无法进行安全套接字连接。在这种情况下，请使用 createInsecureRfcommSocketToServiceRecord。有关更多详细信息，请参阅蓝牙核心规范版本 2.1 + EDR 的安全模型第 5.2 节（第 3 卷）。
     */
    override fun connect(address: String) {
        reset()
        val validBluetoothAddress = BluetoothAdapter.checkBluetoothAddress(address)
        if (validBluetoothAddress) {
            bluetoothAdapter.cancelDiscovery()
            val remoteDevice = bluetoothAdapter.getRemoteDevice(address)
            try {
                //首先尝试不安全通道连接，再尝试安全的通道连接。有些设备使用安全通道连接会失败，使用不安全的连接可以连接成功
                bluetoothSocket = remoteDevice.createInsecureRfcommSocketToServiceRecord(SPP_UUID)
                bluetoothSocket?.connect()
                outputStream = bluetoothSocket!!.outputStream
                inputStream = bluetoothSocket!!.inputStream
                connectListener?.onConnectSuccess()
            } catch (t: Throwable) {
                try {
                    bluetoothSocket = remoteDevice.createRfcommSocketToServiceRecord(SPP_UUID)
                    bluetoothSocket?.connect()
                    outputStream = bluetoothSocket!!.outputStream
                    inputStream = bluetoothSocket!!.inputStream
                    connectListener?.onConnectSuccess()
                } catch (t: Throwable) {
                    connectListener?.onConnectFailed(t)
                }
            }
        } else {
            connectListener?.onNotValidMacAddress(address)
        }
    }

    override fun disconnect() {
        try {
            reset()
            connectListener?.onDisconnectSuccess()
        } catch (t: Throwable) {
            connectListener?.onDisconnectFailed(t)
        }
    }

    private fun reset() {
        outputStream?.flush()
        bluetoothSocket?.close()
        outputStream?.close()
        inputStream?.close()
        outputStream = null
        inputStream = null
    }

    override fun setBluetoothConnectListener(l: BluetoothConnectListener) {
        this.connectListener = l
    }

    override fun writeData(data: ByteArray, callback: WriteDataCallback) {
        try {
            outputStream?.write(data)
            callback.onSuccess()
        } catch (t: Throwable) {
            disconnect()
            callback.onFailed(t)
        }
    }
}