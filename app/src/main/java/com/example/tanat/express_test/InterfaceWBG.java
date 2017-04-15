package com.example.tanat.express_test;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings.Secure;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import android.widget.TextView;
import java.util.Date;
import java.util.List;
import android.telephony.CarrierConfigManager;

public class InterfaceWBG extends AppCompatActivity {

    WifiManager wifiManager;
    WifiInfo wifiInfo;
    TextView t_wifi, t_bluetoth, tvGPS,tvDBM,tvNetworkType,tvInfo;
    BluetoothAdapter bluetooth;
    int signal_s, signal_a = 0, col_web = 0,mSignalStrength = 0;
    String b_address;
    TelephonyManager mTelephonyManager,s2;
    public String s23;
    CarrierConfigManager mTelephonyManager2;
    MyPhoneStateListener mPhoneStatelistener,mPhoneStatelistener2;
    private LocationManager locationManager;
    //StringBuilder sbGPS = new StringBuilder();
    //StringBuilder sbNet = new StringBuilder();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interface_wbg);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        tvDBM = (TextView) findViewById(R.id.tvDBM);
        tvNetworkType = (TextView) findViewById(R.id.tvNetworkType);



       //s2 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        tvInfo = (TextView) findViewById(R.id.tvInfo);
       // s23 = s2.getDeviceId().toString();
        //info.setText(s2.getDeviceId().toString());
       // info.setText(s23);









        mPhoneStatelistener = new MyPhoneStateListener();
        mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        mTelephonyManager.listen(mPhoneStatelistener, PhoneStateListener.LISTEN_SIGNAL_STRENGTHS);
        mTelephonyManager2 = (CarrierConfigManager)  getSystemService(Context.CARRIER_CONFIG_SERVICE);



        s2 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        StringBuilder info = new StringBuilder();
        info.append("\n\nPhone Info:\n");
        info.append("\nModell: ");
        info.append(Build.MODEL);
        info.append("\nDevice Id: ");
       // info.append(s2.getDeviceId());
        info.append("\nSubscriber Id: ");
       // info.append(s2.getSubscriberId());
        info.append("\nDevice SofwareVersion: ");
       // info.append(s2.getDeviceSoftwareVersion());
        tvInfo.setText(info);




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
        tvGPS = (TextView) findViewById(R.id.tvGPS);

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

            //количество найденных сетей
            col_web = results.size();

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

      // данный метод выводит координаты в зависимости от постовщика (GPS или Моб сеть.)
    private void showLocation(Location location) {
        if (location == null)
            return;
        if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
            tvGPS.setText(formatLocation(location)+ " GPS");
        } else if (location.getProvider().equals(
                LocationManager.NETWORK_PROVIDER)) {
            tvGPS.setText(formatLocation(location)+ " NET");


        }
    }

    // формат выходных данных (для красоты и нормального чтения)
    private String formatLocation(Location location) {
        if (location == null)
            return "";
        return String.format(
                "Coordinates: lat = %1$.4f, lon = %2$.4f, time = %3$tF %3$tT",
                location.getLatitude(), location.getLongitude(), new Date(
                        location.getTime()));
    }

    //газ
    @Override
    protected void onResume() {
        super.onResume();
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10, locationListener);
        //checkEnabled();
    }
    //тормоз
    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(locationListener);
    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            showLocation(location);
        }

        @Override
        public void onProviderDisabled(String provider) {
           // checkEnabled();
        }

        @Override
        public void onProviderEnabled(String provider) {
            //checkEnabled();
            showLocation(locationManager.getLastKnownLocation(provider));
        }



        //метод выводит вкл и выкл поставщика координат
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            if (provider.equals(LocationManager.GPS_PROVIDER)) {
                //tvGPS.setText(" Status: " + String.valueOf(status));
            } else if (provider.equals(LocationManager.NETWORK_PROVIDER)) {
                //tvGPS.setText("Status: " + String.valueOf(status));
            }
        }
    };


   /* private void checkEnabled() {
        tvGPS.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER));
        tvGPS.setText("Enabled: "
                + locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }*/

    class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onSignalStrengthsChanged(SignalStrength signalStrength) {
            super.onSignalStrengthsChanged(signalStrength);
            mSignalStrength = signalStrength.getGsmSignalStrength();
            mSignalStrength = (2 * mSignalStrength) - 113; // -> dBm
            tvDBM.setText("Stats " +mSignalStrength);




            int networkType = mTelephonyManager.getNetworkType();


            String suka = CarrierConfigManager.KEY_CARRIER_VOLTE_TTY_SUPPORTED_BOOL;
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    tvNetworkType.setText("2g only");
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    tvNetworkType.setText("2g/3g is Supported");
                case TelephonyManager.NETWORK_TYPE_LTE:
                    tvNetworkType.setText("2g/3g/4g LTE is Supported");

                       tvNetworkType.setText(suka);
                   /* StringBuilder info = new StringBuilder();
                    info.append("\n\nPhone Info:\n");
                    info.append("\nModell: ");
                    info.append(Build.MODEL);
                    info.append("\nDevice Id: ");
                    info.append(mTelephonyManager.getDeviceId());
                    info.append("\nSubscriber Id: ");
                    info.append(mTelephonyManager.getSubscriberId());
                    info.append("\nDevice SofwareVersion: ");
                    info.append(mTelephonyManager.getDeviceSoftwareVersion());
                    tvInfo.setText(info);*/

                  //  TelephonyManager suka2 = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                   // tvInfo.setText(suka2.getDeviceId());


                //default:
                  //  tvNetworkType.setText("Not found");
            }




        }








    }











}
