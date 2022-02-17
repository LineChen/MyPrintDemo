package com.line.myprintdemo

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothDevice.BOND_BONDED
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.line.scan.BluetoothScanListener
import com.line.scan.BluetoothScanService

/**
 * created by chenliu on  2022/2/16 1:15 下午.
 */
class BluetoothScanActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_ENABLE_BT = 1
        private const val TAG = "BluetoothScanActivity"
    }

    private lateinit var rvList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var tvScanFinished: TextView

    private val deviceList = mutableListOf<BluetoothDevice>()

    private val adapter = DeviceAdapter()

    private val bluetoothScanManager = BluetoothScanService(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_scan)
        rvList = findViewById(R.id.rvList)
        progressBar = findViewById(R.id.progressBar)
        tvScanFinished = findViewById(R.id.tvStatus)

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
                startActivityForResult(intent, REQUEST_ENABLE_BT)
            }

            override fun onBoundDevices(list: List<BluetoothDevice>) {
                list.forEach { device ->
                    if (!deviceList.contains(device) && !device.name.isNullOrEmpty()) {
                        deviceList.add(device)
                    }
                }
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

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: DeviceHolder, position: Int) {
            val model = deviceList[position]
            holder.tvName.text = model.name
            holder.tvAddress.text = model.address
            val bluetoothClass = model.bluetoothClass
            val deviceClass = bluetoothClass.deviceClass; //设备类型（音频、手机、电脑、音箱等等）
            val majorDeviceClass = bluetoothClass.majorDeviceClass;//具体的设备类型（例如音频设备又分为音箱、耳机、麦克风等等）
            holder.tvDeviceType.text = "${
                getTypeStr(
                    deviceClass,
                    majorDeviceClass
                )
            } （deviceClass= $deviceClass, majorDeviceClass = $majorDeviceClass）"

            holder.tvBound.visibility = if (model.bondState == BOND_BONDED) View.VISIBLE else View.GONE


            holder.btnConnect.setOnClickListener {
                BluetoothConnectActivity.startBluetoothConnectActivity(
                    this@BluetoothScanActivity,
                    model.name,
                    model.address
                )
            }
        }

        override fun getItemCount(): Int {
            return deviceList.size
        }

    }

    private inner class DeviceHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvAddress: TextView = itemView.findViewById(R.id.tvAddress)
        val tvDeviceType: TextView = itemView.findViewById(R.id.tvDeviceType)
        val tvBound: TextView = itemView.findViewById(R.id.tvBound)
        val btnConnect: Button = itemView.findViewById(R.id.btnConnect)
    }

    private fun getTypeStr(deviceClass: Int, majorDeviceClass: Int): String {
        when {
            deviceClass == BluetoothClass.Device.AUDIO_VIDEO_WEARABLE_HEADSET -> {
                //音箱
                return "音箱"
            }
            deviceClass == BluetoothClass.Device.AUDIO_VIDEO_MICROPHONE -> {
                //麦克风
                return "麦克风"
            }
            deviceClass == BluetoothClass.Device.AUDIO_VIDEO_HEADPHONES -> {
                //耳机
                return "耳机"
            }
            majorDeviceClass == BluetoothClass.Device.Major.COMPUTER -> {
                //电脑
                return "电脑"
            }
            majorDeviceClass == BluetoothClass.Device.Major.PHONE -> {
                //手机
                return "手机"
            }
            majorDeviceClass == BluetoothClass.Device.Major.HEALTH -> {
                //健康类设备
                return "音健康类设备箱"
            }
            majorDeviceClass == BluetoothClass.Device.Major.IMAGING -> {
                return "打印机"
            }
            else -> {
                //其它蓝牙设备
                return "其它蓝牙设备"
            }
        }
    }

}