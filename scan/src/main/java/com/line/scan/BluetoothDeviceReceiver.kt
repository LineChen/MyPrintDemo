package com.line.scan

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * created by chenliu on  2022/2/16 11:30 上午.
 */
class BluetoothDeviceReceiver(private val onFound: (ScannedDevice) -> Unit, private val onFinished: () -> Unit) :
    BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (intent.action == BluetoothDevice.ACTION_FOUND) {
                //搜索到的新设备
                val btd = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                onFound.invoke(ScannedDevice(btd!!.address, btd.name))
            } else if (intent.action == BluetoothAdapter.ACTION_DISCOVERY_FINISHED) {
                onFinished.invoke()
            }
        }
    }

}