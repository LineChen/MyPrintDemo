package com.line.myprintdemo

import android.annotation.SuppressLint
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
import com.line.connect.GenerateData
import com.line.connect.WriteDataCallback
import com.line.myprintdemo.bean.getOrderData
import com.line.printer.*
import com.line.printer.base.PrinterCommand
import com.line.printer.base.PrinterType
import com.line.printer.base.getPrinterCommand

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
    private lateinit var btn80Print: Button
    private lateinit var btnPrint: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var tvStatus: TextView

    private lateinit var bluetoothConnectService: BluetoothConnectService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_connenct)
        tvName = findViewById(R.id.tvName)
        btStartConnect = findViewById(R.id.btStartConnect)
        btn80Print = findViewById(R.id.btn80Print)
        btnPrint = findViewById(R.id.btnPrint)
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


        btnPrint.setOnClickListener {
            if (bluetoothConnectService.isConnected()) {
                bluetoothConnectService.writeData(object : GenerateData {
                    override fun generate(): List<ByteArray> {
                        return getFormatCommand(getPrinterCommand(PrinterType.MM_58))
                    }
                }, object : WriteDataCallback {
                    override fun onSuccess() {
                        tvStatus.text = "打印成功"
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onFailed(t: Throwable) {
                        tvStatus.text = "打印失败：${t.message}"
                    }
                })
            } else {
                Toast.makeText(this, "请先连接蓝牙打印机", Toast.LENGTH_SHORT).show()
            }
        }

        btn80Print.setOnClickListener {
            if (bluetoothConnectService.isConnected()) {
                bluetoothConnectService.writeData(object : GenerateData {
                    override fun generate(): List<ByteArray> {
                        return getFormatCommand(getPrinterCommand(PrinterType.MM_80))
                    }
                }, object : WriteDataCallback {
                    override fun onSuccess() {
                        tvStatus.text = "打印成功"
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onFailed(t: Throwable) {
                        tvStatus.text = "打印失败：${t.message}"
                    }
                })
            } else {
                Toast.makeText(this, "请先连接蓝牙打印机", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun getFormatCommand(printerCommand: PrinterCommand): List<ByteArray> {
        val lineSpace = 80
        val list: MutableList<ByteArray> = java.util.ArrayList()
        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignCenter())
        list.add(printerCommand.setCharacterSize(17)) //字体放大一倍

        list.add(printerCommand.setTextBold(true))
        list.add(strToBytes("闹元宵火锅店"))
        list.add(printerCommand.printAndFeedLine())
        list.add(printerCommand.printAndFeedLine())

        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.getTowLineString("付款时间", "2022年1月26日 18:23:35"))
        list.add(printerCommand.printAndFeed(lineSpace))

        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.getTowLineString("订单编号", "13887065438432"))
        list.add(printerCommand.printAndFeed(lineSpace))


        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.getTowLineString("顾客姓名", "王可可"))
        list.add(printerCommand.printAndFeed(lineSpace))

        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.getTowLineString("会员等级", "至尊会员-全场消费5.6折优惠"))
        list.add(printerCommand.printAndFeed(lineSpace))


        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.printDashLine())
        list.add(printerCommand.printAndFeed(lineSpace))


        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.getThreeLineStringExactCenter("商品名称", "购买数量", "商品价格"))
        list.add(printerCommand.printAndFeed(lineSpace))

        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.getThreeLineStringLastIndex("烤鱼烤羊排", "1", "20元"))
        list.add(printerCommand.printAndFeed(lineSpace))

        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.getThreeLineStringLastIndex("鱼香茄子", "12", "200元"))
        list.add(printerCommand.printAndFeed(lineSpace))

        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.getThreeLineStringLastIndex("红烧肉烧五花肉烧梅干菜", "111", "2000元"))
        list.add(printerCommand.printAndFeed(lineSpace))


        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.getThreeLineStringLastIndex("炸串", "1000", "20000元"))
        list.add(printerCommand.printAndFeed(lineSpace))


        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        list.add(printerCommand.printDashLine())
        list.add(printerCommand.printAndFeed(lineSpace))


        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        val goodsPricePair = printerCommand.getTowLineStringPair("商品总计", "80元")
        list.add(goodsPricePair.first)
        list.add(printerCommand.setTextBold(true))
        list.add(goodsPricePair.second)
        list.add(printerCommand.printAndFeed(lineSpace))


        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        val memberDiscountPair = printerCommand.getTowLineStringPair("至尊会员 - 全场消费5.6折优惠", "节省44.8元")
        list.add(memberDiscountPair.first)
        list.add(printerCommand.setTextBold(true))
        list.add(memberDiscountPair.second)
        list.add(printerCommand.printAndFeed(lineSpace))


        list.add(printerCommand.initialize())
        list.add(printerCommand.setAlignLeft())
        val realPricePair = printerCommand.getTowLineStringPair("实际付款金额", "35.2元")
        list.add(realPricePair.first)
        list.add(printerCommand.setTextBold(true))
        list.add(realPricePair.second)
        list.add(printerCommand.printAndFeed(lineSpace))

        list.add(printerCommand.printAndFeedLine(3))

        list.add(printerCommand.cutPaper())

        return list
    }


    private fun getAbsolutePrintPositionFormat(): List<ByteArray> {
        val list: MutableList<ByteArray> = java.util.ArrayList()
        val dash = StringBuilder()
        for (i in 0..31) {
            dash.append("-")
        }
        list.add(strToBytes(dash.toString()))
        list.add(DataForSendToPrinterPos58.printAndFeedLine())

        list.add(DataForSendToPrinterPos58.initializePrinter())
        list.add(DataForSendToPrinterPos58.setAbsolutePrintPosition(0, 0))
        list.add(strToBytes("-"))
        list.add(DataForSendToPrinterPos58.setAbsolutePrintPosition(255, 255))
        list.add(strToBytes("00"))
        list.add(DataForSendToPrinterPos58.printAndFeedLine())
        return list
    }

    private fun getCommands(): List<ByteArray> {
        val list = ArrayList<ByteArray>()
        list.add(Commands.RESET)
        list.add(Commands.ALIGN_CENTER)
        list.add(Commands.FONT_C)
        list.add(strToBytes("大斑马动物园")!!)
        list.add(Commands.printLineFeed())
        list.add(Commands.ALIGN_LEFT)
        list.add(Commands.FONT_A)
        list.add(strToBytes("欢迎大家！！！")!!)
        list.add(Commands.printLineFeed(3))
        return list
    }

    private fun getDataForSendToPrinterCmd(): List<ByteArray> {
        val order = getOrderData()
        val list = ArrayList<ByteArray>()
        list.add(DataForSendToPrinter.initializePrinter())
        list.add(DataForSendToPrinter.selectAliment(0x01))
        list.add(DataForSendToPrinter.selectFontSize(0x11))
        list.add(strToBytes("#${order.pickUpCode} 测试外卖")!!)
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())

        list.add(DataForSendToPrinter.initializePrinter())
        list.add(DataForSendToPrinter.selectAliment(0x01))
        list.add(DataForSendToPrinter.selectFontSize(0x01))
        list.add(strToBytes("*${order.shopName}*")!!)
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())

        list.add(DataForSendToPrinter.initializePrinter())
        list.add(DataForSendToPrinter.selectFontSize(0x11))
        list.add(DataForSendToPrinter.selectAliment(0x01))
        list.add(strToBytes("--已在线支付--")!!)
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())

        list.add(DataForSendToPrinter.initializePrinter())
        list.add(DataForSendToPrinter.printBothColumns("配送方式：", order.deliveryTypeStr))
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())

        list.add(DataForSendToPrinter.initializePrinter())
        list.add(DataForSendToPrinter.printBothColumns("下单时间：", order.createTime))
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())

        val onTime = order.receiveTime
        if (onTime != null) {
            list.add(DataForSendToPrinter.initializePrinter())
            list.add(DataForSendToPrinter.printBothColumns("预计送达时间：", onTime))
            list.add(DataForSendToPrinter.printAndFeedLine())
            list.add(DataForSendToPrinter.printAndFeedLine())
        }

        list.add(DataForSendToPrinter.initializePrinter())
        list.add(DataForSendToPrinter.selectOrCancelBoldModel(0x01))
        list.add(strToBytes("客户留言：")!!)
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())
        if (order.remarks == null) {
            list.add(DataForSendToPrinter.printAndFeedLine())
            list.add(DataForSendToPrinter.printAndFeedLine())
        } else {
            list.add(strToBytes(order.remarks)!!)
        }
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())

        list.add(DataForSendToPrinter.initializePrinter())
        if (order.receiver != null && order.receiver.isNotEmpty()) {
            list.add(
                DataForSendToPrinter.printBothColumns(
                    "收货人：",
                    "${order.receiver.substring(0, 1)}**"
                )
            )
            list.add(DataForSendToPrinter.printAndFeedLine())
            list.add(DataForSendToPrinter.printAndFeedLine())
        }
        if (order.receiverMobile != null) {
            list.add(DataForSendToPrinter.printBothColumns("电话：", order.receiverMobile))
            list.add(DataForSendToPrinter.printAndFeedLine())
            list.add(DataForSendToPrinter.printAndFeedLine())
        }
        if (order.riderName != null) {
            list.add(DataForSendToPrinter.printBothColumns("骑手：", order.riderName))
            list.add(DataForSendToPrinter.printAndFeedLine())
            list.add(DataForSendToPrinter.printAndFeedLine())
        }
        if (order.riderMobile != null) {
            list.add(DataForSendToPrinter.printBothColumns("电话：", order.riderMobile))
            list.add(DataForSendToPrinter.printAndFeedLine())
            list.add(DataForSendToPrinter.printAndFeedLine())
        }
        if (order.address != null) {
            list.add(strToBytes("收货地址：${order.address}")!!)
            list.add(DataForSendToPrinter.printAndFeedLine())
            list.add(DataForSendToPrinter.printAndFeedLine())
        }
        list.add(DataForSendToPrinter.printThreeColumns("名称", "数量", "售价"))
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())
        order.orderDetailList.forEach {
            val price = when (it.isDiscount) {
                1 -> numberFormat(it.amount)
                else -> numberFormat(it.shopPrice)
            }
            list.add(DataForSendToPrinter.initializePrinter())
            list.add(DataForSendToPrinter.selectOrCancelBoldModel(0x01))
            list.add(strToBytes(it.goodsName)!!)
            list.add(DataForSendToPrinter.printAndFeedLine())
            list.add(
                DataForSendToPrinter.printThreeColumns(
                    "",
                    "${it.buyCount}",
                    "￥$price"
                )
            )
            list.add(DataForSendToPrinter.printAndFeedLine())
            list.add(DataForSendToPrinter.printAndFeedLine())
        }

        list.add(DataForSendToPrinter.initializePrinter())
        list.add(
            DataForSendToPrinter.printBothColumns(
                "订单原价：",
                "￥${numberFormat(order.money)}"
            )
        )
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(
            DataForSendToPrinter.printBothColumns(
                "配送费：",
                "￥${numberFormat(order.freight)}"
            )
        )
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(
            DataForSendToPrinter.printBothColumns(
                "实付金额：",
                "￥${numberFormat(order.payMoney)}"
            )
        )
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printBothColumns("订单类型：", order.orderTypeStr))
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())
        if (order.orderTypeStr.contains("预约")) {
            list.add(DataForSendToPrinter.printBothColumns("预约时间：", order.appointmentTime))
            list.add(DataForSendToPrinter.printAndFeedLine())
            list.add(DataForSendToPrinter.printAndFeedLine())
        }
        list.add(DataForSendToPrinter.printBothColumns("订单号：", order.orderCode))
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())
        list.add(DataForSendToPrinter.printAndFeedLine())
        return list
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