package com.example.tanat.express_test;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class InterfaceWBG extends AppCompatActivity {

    WifiManager wifiManager;
    WifiInfo wifiInfo;
    TextView t_wifi, t_bluetoth;
    BluetoothAdapter bluetooth;
    int signal_s, signal_a = 0;
    String b_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_wbg);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //проверка местоположения
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                InterfaceWBG.this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
                InterfaceWBG.this.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            }
        }

        bluetooth= BluetoothAdapter.getDefaultAdapter();

        t_wifi = (TextView) findViewById(R.id.t_wi_fi);
        t_bluetoth = (TextView) findViewById(R.id.t_bluetoth);

        //проверка включения wi-fi
        try {
            wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }

            wifiInfo = wifiManager.getConnectionInfo();

            //проверка наличия сетей wi-fi
            wifiManager.startScan();
            List<ScanResult> results = wifiManager.getScanResults();
            for (ScanResult result : results) {
                if(result != null) {
                    signal_a = WifiManager.calculateSignalLevel(result.level, 5);
                    if (signal_a > signal_s) {
                        // самый лучший сигнал
                        signal_s = signal_a;
                    }
                }
            }

            t_wifi.setText(R.string.wifi_on);
            wifiManager.setWifiEnabled(false);

        } catch (Exception e) {
            t_wifi.setText(R.string.wifi_off);
        }

        //проверка налачия модуля bluetooth
        if (bluetooth != null) {
            //провекра включенности
            if (!bluetooth.isEnabled()) {
                if (bluetooth.enable()) {
                    t_bluetoth.setText(R.string.bluet_on);
                    //аппаратный адрес
                    b_address = bluetooth.getAddress();
                    bluetooth.disable();
                } else {
                    t_bluetoth.setText(R.string.bluet_off);
                }
            }
        }
    }

}
