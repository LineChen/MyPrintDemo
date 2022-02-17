package com.line.connect

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.*

/**
 * created by chenliu on  2022/2/17 1:35 下午.
 * 测试蓝牙连接，可行
 */
class ConnectThread(private val context: Context, device: BluetoothDevice) : Thread() {

    companion object {
        private const val TAG = "ConnectThread"
    }

    private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
        /**
         * 测试发现，使用随机生成的uuid，无法连接uuid，使用下面固定的uuid则可以连接
         * 原因是：
         */
        val myUUid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")//可连接
//                val myUUid = UUID.fromString("00001126-0000-1000-8000-00805F9B34FB")//不可连接
//                val myUUid = UUID.randomUUID()//随机生成的uuid:b1eab1c4-7b65-4248-b90d-71f8a3514ff7
        device.createRfcommSocketToServiceRecord(myUUid)
    }

    override fun run() {
        // Cancel discovery because it otherwise slows down the connection.
        BluetoothAdapter.getDefaultAdapter()?.cancelDiscovery()

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket?.connect()
            (context as Activity).runOnUiThread {
                Toast.makeText(context, "连接成功", Toast.LENGTH_SHORT).show()
            }
            // The connection attempt succeeded. Perform work associated with
            // the connection in a separate thread.
            //manageMyConnectedSocket(socket)
        } catch (t: Throwable) {
            (context as Activity).runOnUiThread {
                Toast.makeText(context, "连接失败", Toast.LENGTH_SHORT).show()
            }
        } finally {
            mmSocket?.close()
        }

    }

    // Closes the client socket and causes the thread to finish.
    fun cancel() {
        try {
            mmSocket?.close()
        } catch (e: IOException) {
            Log.e(TAG, "Could not close the client socket", e)
        }
    }
}