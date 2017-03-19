package com.example.tanat.express_test;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class SensorATB extends AppCompatActivity {
    private SensorManager sensorManager;
    private List<Sensor> sensorList;
    private float p_light = -1, p_prox = -1;
    private boolean light = false, prox = false;
    Sensor v_sensor;
    TextView t_light, t_PROXIMITY, t_PRESSURE, t_AMBIENT_TEMPERATURE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_atb);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        t_light = (TextView) findViewById(R.id.t_light);
        t_PROXIMITY = (TextView) findViewById(R.id.t_PROXIMITY);
        t_PRESSURE = (TextView) findViewById(R.id.t_PRESSURE);
        t_AMBIENT_TEMPERATURE = (TextView) findViewById(R.id.t_AMBIENT_TEMPERATURE);


        for (Sensor sensor : sensorList) {
            if (sensor.getType() == Sensor.TYPE_LIGHT) {
                        v_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
                        sensorManager.registerListener(listenerSensorLight, v_sensor,
                                SensorManager.SENSOR_DELAY_NORMAL);
            } else {t_light.setText(R.string.no_sensor);}
            if (sensor.getType() == Sensor.TYPE_PROXIMITY) {
                        v_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
                        sensorManager.registerListener(listenerSensorProximity, v_sensor,
                                SensorManager.SENSOR_DELAY_NORMAL);
            } else {t_PROXIMITY.setText(R.string.no_sensor);}
            if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                        v_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
                        sensorManager.registerListener(listenerSensorPressure, v_sensor,
                                SensorManager.SENSOR_DELAY_NORMAL);
            } else {t_PRESSURE.setText(R.string.no_sensor);}
            if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                        v_sensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
                        sensorManager.registerListener(listenerSensorAmbient, v_sensor,
                                SensorManager.SENSOR_DELAY_NORMAL);
            } else {t_AMBIENT_TEMPERATURE.setText(R.string.no_sensor);}
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(listenerSensorLight, v_sensor);
        sensorManager.unregisterListener(listenerSensorProximity, v_sensor);
        sensorManager.unregisterListener(listenerSensorPressure, v_sensor);
        sensorManager.unregisterListener(listenerSensorAmbient, v_sensor);
    }

    //свет
    SensorEventListener listenerSensorLight = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            //t_light.setText(String.valueOf(event.values[0]));

            //проверять только при включенном освещении
            //записываем первое значение
            p_light = (p_light == -1) ? event.values[0] : p_light;

            //проверяем значение приближения, после закрытия датчика рукой
            if (light) {
                light = ((p_light - event.values[0]) != 0);

                // работает или нет
                if (!light) t_light.setText("Датчик не работает");
                else  t_light.setText(String.valueOf(light));

            } else if (light) t_light.setText(String.valueOf(light));
            else if (!light) t_light.setText("Нет данных");
        }
    };

    //приближение
    SensorEventListener listenerSensorProximity = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            //t_PROXIMITY.setText(String.valueOf(event.values[0]));
            //первое значение
            p_prox = (p_light == -1) ? event.values[0] : p_light;

            //начинать тест ничем не прикрывая датчик
            //проверка близости руки
            if (event.values[0] == 0) {
                light = true;
                prox = ((p_prox - event.values[0]) > 0);

                // работает или нет
                if (!prox) t_PROXIMITY.setText("Датчик не работает");
                else t_PROXIMITY.setText(String.valueOf(prox));

            } else if (prox) {t_PROXIMITY.setText(String.valueOf(true));
            } else if (!prox) {t_PROXIMITY.setText("Нет данных");}
        }
    };

    //давление
    SensorEventListener listenerSensorPressure = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {

            //меньше нуля может быть наверное только под землей, так что я думаю и так норм
            if (event.values[0] > 0) t_PRESSURE.setText(String.valueOf(true));
            else t_PRESSURE.setText("Датчик не работает");
        }
    };

    //температура
    SensorEventListener listenerSensorAmbient = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            t_AMBIENT_TEMPERATURE.setText(String.valueOf(event.values[0]));
        }
    };
}
