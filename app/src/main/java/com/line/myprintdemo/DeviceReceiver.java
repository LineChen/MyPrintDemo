package com.line.myprintdemo;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class DeviceReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if (BluetoothDevice.ACTION_FOUND.equals(action)){
            //搜索到的新设备
            BluetoothDevice btd=intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            //搜索没有配对过的蓝牙设备
            if(btd.getBondState()!=BluetoothDevice.BOND_BONDED){
            }
        }else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
            //搜索结束
        }

    }
}
