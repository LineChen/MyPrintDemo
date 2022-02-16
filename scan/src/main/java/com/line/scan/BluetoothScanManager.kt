package com.line.scan

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.ACTION_FOUND
import android.content.Context
import android.content.IntentFilter
import android.os.Handler
import android.os.Looper

/**
 * created by chenliu on  2022/2/16 10:47 上午.
 */
class BluetoothScanManager(private val context: Context) : IBluetoothScan {

    companion object {
        private const val START_SCAN = 1
        private const val STOP_SCAN = 2

    }

    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var bluetoothScanListener: BluetoothScanListener? = null

    private val uiHandler = Handler(Looper.getMainLooper())

    //注册蓝牙广播接收者
    private val bluetoothDeviceReceiver = BluetoothDeviceReceiver(onFound = {
        bluetoothScanListener?.onScanFound(it)
    }, onFinished = {
        bluetoothScanListener?.onScanFinished()
    })


    override fun setOnBluetoothScanListener(l: BluetoothScanListener) {
        this.bluetoothScanListener = l
    }

    override fun startScan() {
        stopScan()
        if (bluetoothAdapter == null) {
            bluetoothScanListener?.onNotSupport()
        }
        if (bluetoothAdapter!!.isEnabled) {
            val intentFilter = IntentFilter()
            intentFilter.addAction(ACTION_FOUND)
            intentFilter.addAction(ACTION_DISCOVERY_FINISHED)
            context.registerReceiver(bluetoothDeviceReceiver, intentFilter)

            if (!bluetoothAdapter.isDiscovering) {
                //开启蓝牙扫描
                bluetoothAdapter.startDiscovery()
                bluetoothScanListener?.onScanStarted()
            }

            val list = mutableListOf<BluetoothDevice>()
            bluetoothAdapter.bondedDevices.forEach { d ->
                if (!d?.name.isNullOrEmpty()) {
                    list.add(d)
                }
            }
            if (list.isNotEmpty()) {
                bluetoothScanListener?.onBoundDevices(list)
            }

        } else {
            bluetoothScanListener?.onBluetoothClosed()
        }

    }

    override fun stopScan() {
        try {
            bluetoothAdapter?.let {
                if (bluetoothAdapter.isDiscovering) {
                    //开启蓝牙扫描
                    bluetoothAdapter.cancelDiscovery()
                    bluetoothScanListener?.onScanCanceled()
                }
            }
            context.unregisterReceiver(bluetoothDeviceReceiver)
        } catch (t: Throwable) {

        }
    }
}