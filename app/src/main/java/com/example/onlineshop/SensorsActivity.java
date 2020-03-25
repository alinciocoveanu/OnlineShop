package com.example.onlineshop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Locale;
import java.util.Objects;

public class SensorsActivity extends AppCompatActivity {
    private TextView pressure, light, gyroscope, temperature, accelerometer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensors_activity);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Sensors and location");

        getSensorData();
        getLocation();
    }

    private void getSensorData() {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        SensorEventListener sensorEventListener = new SensorEventListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_PRESSURE)
                    pressure.setText(String.format("Pressure: %.2f hPa", event.values[0]));
                if (event.sensor.getType() == Sensor.TYPE_LIGHT)
                    light.setText(String.format("Light: %.2f lx", event.values[0]));
                if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
                    gyroscope.setText(String.format("Rotation:  %.1f x,%.1f y,%.1f z", event.values[0], event.values[1], event.values[2]));
                if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
                    temperature.setText(String.format("Temperature: %.1f C", event.values[0]));
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                    accelerometer.setText(String.format("Acceleration: %.3f m/s2", event.values[0]));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        assert sensorManager != null;
        pressure = findViewById(R.id.pressure);
        Sensor sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorManager.registerListener(sensorEventListener, sensor1, SensorManager.SENSOR_DELAY_UI);
        light = findViewById(R.id.light);
        Sensor sensor2 = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager.registerListener(sensorEventListener, sensor2, SensorManager.SENSOR_DELAY_UI);
        gyroscope = findViewById(R.id.gyroscope);
        Sensor sensor3 = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        sensorManager.registerListener(sensorEventListener, sensor3, SensorManager.SENSOR_DELAY_UI);
        temperature = findViewById(R.id.temperature);
        Sensor sensor4 = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorManager.registerListener(sensorEventListener, sensor4, SensorManager.SENSOR_DELAY_UI);
        accelerometer = findViewById(R.id.accelerometer);
        Sensor sensor5 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(sensorEventListener, sensor5, SensorManager.SENSOR_DELAY_UI);
    }

    private void getLocation() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                TextView textView = findViewById(R.id.coordinates);
                textView.setText(String.format(Locale.ENGLISH, "%.2f longitude\n%.2f latitude\n%.2f altitude",
                        location.getLongitude(), location.getLatitude(), location.getAltitude()));
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "Permission required for location services", Toast.LENGTH_SHORT).show();
                return;
            } else {
                int REQUEST_PERMISSION_ACCESS_LOCATION = 1;
                ActivityCompat.requestPermissions(SensorsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSION_ACCESS_LOCATION);
            }
        } else {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        }
        assert locationManager != null;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 1, locationListener);
    }
}
