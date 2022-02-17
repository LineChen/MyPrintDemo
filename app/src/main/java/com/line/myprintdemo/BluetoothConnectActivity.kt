package com.line.myprintdemo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.line.connect.BluetoothConnectListener
import com.line.connect.BluetoothConnectService

/**
 * created by chenliu on  2022/2/17 3:00 下午.
 */
class BluetoothConnectActivity : AppCompatActivity() {

    companion object {
        private const val ADDRESS = "address"
        private const val NAME = "name"
        fun startBluetoothConnectActivity(context: Context, name: String?, address: String?) {
            val intent = Intent(context, BluetoothConnectActivity::class.java)
            intent.putExtra(NAME, name)
            intent.putExtra(ADDRESS, address)
            context.startActivity(intent)
        }
    }

    private lateinit var tvName: TextView
    private lateinit var btStartConnect: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvStatus: TextView

    private lateinit var bluetoothConnectService: BluetoothConnectService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_connenct)
        tvName = findViewById(R.id.tvName)
        btStartConnect = findViewById(R.id.btStartConnect)
        progressBar = findViewById(R.id.progressBar)
        tvStatus = findViewById(R.id.tvStatus)

        val address = intent.getStringExtra(ADDRESS)
        val name = intent.getStringExtra(NAME)
        tvName.text = name

        bluetoothConnectService = BluetoothConnectService(ConnectListener())

        btStartConnect.setOnClickListener {
            if (bluetoothConnectService.isConnected()) {
                bluetoothConnectService.disconnect()
            } else {
                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(this, "地址为空！！！", Toast.LENGTH_SHORT).show()
                } else {
                    bluetoothConnectService.connect(address!!)
                }
            }
            progressBar.visibility = View.VISIBLE
        }

    }

    private inner class ConnectListener : BluetoothConnectListener {
        override fun onNotValidMacAddress(address: String) {
            tvStatus.text = "非法地址"
            progressBar.visibility = View.GONE
        }

        override fun onConnectSuccess() {
            tvStatus.text = "连接成功"
            progressBar.visibility = View.GONE
            btStartConnect.text = "断开连接"
        }

        override fun onConnectFailed(t: Throwable) {
            tvStatus.text = "连接失败"
            progressBar.visibility = View.GONE
        }

        override fun onDisconnectSuccess() {
            tvStatus.text = "断开成功"
            progressBar.visibility = View.GONE
            btStartConnect.text = "连接"
        }

        override fun onDisconnectFailed(t: Throwable) {
            tvStatus.text = "断开失败"
            progressBar.visibility = View.GONE
        }

    }

}