package com.line.myprintdemo

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.line.scan.BluetoothScanListener
import com.line.scan.BluetoothScanManager

/**
 * created by chenliu on  2022/2/16 1:15 下午.
 */
class BluetoothScanActivity : AppCompatActivity() {

    private lateinit var rvList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvScanFinished: TextView

    private val deviceList = mutableListOf<BluetoothDevice>()

    private val adapter = DeviceAdapter()

    private val bluetoothScanManager = BluetoothScanManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_scan)
        rvList = findViewById(R.id.rvList)
        progressBar = findViewById(R.id.progressBar)
        tvScanFinished = findViewById(R.id.tvScanFinished)

        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        bluetoothScanManager.stopScan()
    }

    fun startScan(view: View) {
        deviceList.clear()
        bluetoothScanManager.setOnBluetoothScanListener(object : BluetoothScanListener {
            override fun onNotSupport() {
                Toast.makeText(this@BluetoothScanActivity, "该设备不支持蓝牙", Toast.LENGTH_SHORT).show()
            }

            override fun onBluetoothClosed() {
                //请求用户开启
                val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(intent, 1)
            }

            override fun onBoundDevices(list: List<BluetoothDevice>) {
                deviceList.clear()
                deviceList.addAll(list)
                adapter.notifyDataSetChanged()
            }

            override fun onScanStarted() {
                progressBar.visibility = View.VISIBLE
                tvScanFinished.visibility = View.GONE
            }

            override fun onScanFound(device: BluetoothDevice) {
                if (!deviceList.contains(device) && !device.name.isNullOrEmpty()) {
                    deviceList.add(device)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onScanFinished() {
                progressBar.visibility = View.GONE
                tvScanFinished.visibility = View.VISIBLE
            }

            override fun onScanCanceled() {
                progressBar.visibility = View.GONE
                tvScanFinished.visibility = View.VISIBLE
            }
        })
        bluetoothScanManager.startScan()
    }


    private inner class DeviceAdapter : RecyclerView.Adapter<DeviceHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_bluetooth_device, parent, false)
            return DeviceHolder(itemView)
        }

        override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
            val model = deviceList[position]
            holder.tvName.text = model.name
            holder.tvAddress.text = model.address
        }

        override fun getItemCount(): Int {
            return deviceList.size
        }

    }

    private inner class DeviceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
    }

}