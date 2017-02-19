package com.example.tanat.express_test;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class InterfaceWBG extends AppCompatActivity {

    WifiManager wifiManager;
    TextView t_wifi, t_bluetoth, t_glonass;
    BluetoothAdapter bluetooth;
    int signal_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_wbg);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        bluetooth= BluetoothAdapter.getDefaultAdapter();

        t_wifi = (TextView) findViewById(R.id.t_wi_fi);
        t_bluetoth = (TextView) findViewById(R.id.t_bluetoth);
        t_glonass = (TextView) findViewById(R.id.t_glonass);

        //проверка включения wi-fi
        try {
            wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);

            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }

            signal_s = this.getWifiSignalState();


            //проверка наличия сетей wi-fi
            if (wifiManager.startScan()) {
                if (signal_s != 0) {
                    t_wifi.setText(String.valueOf(signal_s));
                    wifiManager.setWifiEnabled(false);
                }
            } else {
                t_wifi.setText("Сети wi-fi не найдены");
                wifiManager.setWifiEnabled(false);
            }
        } catch (Exception e) {
            t_wifi.setText("Wifi не работает");
        }

        //проверка налачия модуля bluetooth
        if (bluetooth != null) {
            //провекра включенности
            if (!bluetooth.isEnabled()) {
                if (bluetooth.enable()) {
                    t_bluetoth.setText("Bluetooth работает");
                    bluetooth.disable();
                } else {
                    t_bluetoth.setText("Bluetooth не работает");
                }
            }
        }

    }

    public int getWifiSignalState() {
        int signalStrength = 5;
        List<ScanResult> results = wifiManager.getScanResults();
        if (results != null) {
            for (ScanResult result : results) {
                if (result != null && wifiManager != null && wifiManager.getConnectionInfo() != null && result.BSSID != null
                        && result.BSSID.equals(wifiManager.getConnectionInfo().getBSSID())) {
                    int level = 0;
                    level = wifiManager.calculateSignalLevel(wifiManager.getConnectionInfo().getRssi(), result.level);
                    if (level != 0 && result.level != 0) {
                        int difference = level * 100 / result.level;
                        if (difference >= 100)
                            signalStrength = 4;
                        else if (difference >= 75)
                            signalStrength = 3;
                        else if (difference >= 50)
                            signalStrength = 2;
                        else if (difference >= 25)
                            signalStrength = 1;
                    }
                }
            }
            signalStrength = results.size();
        }
        return signalStrength;
    }
}
