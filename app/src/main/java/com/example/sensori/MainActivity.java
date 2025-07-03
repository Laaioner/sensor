package com.example.sensori;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

public class MainActivity extends AppCompatActivity {
    LocationManager lm;
    TextView tvLatitude, tvLongitude;
    Button bnt;

    MapView map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        tvLongitude = findViewById(R.id.tvLongitude);
        tvLatitude = findViewById(R.id.tvLatitude);
        bnt = findViewById(R.id.button);
        map = findViewById(R.id.map);
        map.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                track=false;
                return false;
            }
        });
        Configuration.getInstance().setUserAgentValue(getPackageName());

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        bnt.setOnClickListener(v -> getLocalizacao());

    }

    public void getLocalizacao() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    },
                    1);
            return;
        }

        Location location;
        //location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0 , 0 , locationListener);
    }

    boolean track = true;
    public void showLocalizacao(double latitude, double longitude){
        GeoPoint userLocation = new GeoPoint(latitude, longitude);
        Marker marker = new Marker(map);
        marker.setPosition(userLocation);
        marker.setTitle("Estamos aqui");

        map.getOverlay().clear();
        if (track){
            map.getController().setCenter(userLocation);
        }
        map.getController().setZoom(18.8);
        map.getOverlays().add(marker);
        map.invalidate();
    }
    public final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            tvLatitude.setText(Double.toString(location.getLatitude()));
            tvLongitude.setText(Double.toString(location.getLatitude()));
            showLocalizacao(location.getLatitude(), location.getLongitude());
        }
    };
}

