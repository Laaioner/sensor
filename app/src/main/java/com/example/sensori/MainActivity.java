package com.example.sensori;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
//Adicionar giroscópio e acelerômetro
    SensorManager sensorManager;
    TextView tv, tvAce, tvGir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.tvResultado);
        tvAce=findViewById(R.id.tvAce);
        tvGir=findViewById(R.id.tvGir);
        sensorManager=(SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensorLuz = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Sensor sensorAce = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Sensor sensorGir = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(this,sensorLuz,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorAce,SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this,sensorGir,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType()==Sensor.TYPE_LIGHT){
            tv.setText(Float.toString((sensorEvent.values[0])));
        }

        if (sensorEvent.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
            tvAce.setText(Float.toString(sensorEvent.values[1]));
        }

        if (sensorEvent.sensor.getType()==Sensor.TYPE_GYROSCOPE) {
            tvGir.setText(Float.toString(sensorEvent.values[2]));
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}