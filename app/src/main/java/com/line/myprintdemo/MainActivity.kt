package com.line.myprintdemo

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothAdapter.ACTION_DISCOVERY_FINISHED
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.ACTION_FOUND
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
    val BtReciever = DeviceReceiver()

    fun scan(view: View) {
        startActivity(Intent(this, BluetoothScanActivity::class.java))


        //注册蓝牙广播接收者
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(ACTION_FOUND)
//        intentFilter.addAction(ACTION_DISCOVERY_FINISHED)
//        registerReceiver(BtReciever, intentFilter)
//
//        if (!bluetoothAdapter.isDiscovering) {
//            bluetoothAdapter.startDiscovery()
//        }

        //        //获取可配对蓝牙设备
//        val device = bluetoothAdapter.bondedDevices
//        Log.d("===", "bondedDevices: " + device.size)
    }
}