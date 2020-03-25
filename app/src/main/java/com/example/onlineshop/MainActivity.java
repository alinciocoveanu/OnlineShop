package com.example.onlineshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static ArrayList<Car> carList = new ArrayList<>();
    public static RecyclerView recyclerView;
    private static final String TAG = "MainActivity";
    public static Boolean removeFlag = false;
    private int states = 0;

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "OnStart");
        this.states++;
        Log.i(TAG, String.valueOf(this.states));
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
        this.states++;
        Log.i(TAG, String.valueOf(this.states));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
        this.states++;
        Log.i(TAG, String.valueOf(this.states));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.item2:
                this.addItemToList();
                break;
            case R.id.item3:
                this.removeItemFromList();
                break;
            case R.id.item4:
                this.preferenceActivity();
                break;
            case R.id.item5:
                this.sensorsActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sensorsActivity() {
        Intent intent = new Intent(this, SensorsActivity.class);

        this.startActivity(intent);
    }

    private void preferenceActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);

        this.startActivity(intent);
    }

    private void removeItemFromList() {
        removeFlag = !removeFlag;
        if (removeFlag)
            Toast.makeText(this, "Remove is active. Long touch the item you want to remove.", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Remove is deactivated.", Toast.LENGTH_SHORT).show();
    }

    private void addItemToList() {
        DialogFragment dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(), "car_add");
    }

    public static void saveNewItemToFile(Car car, Context context)
    {
        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput("new_car.txt", MODE_PRIVATE);
            fos.write(car.getName().getBytes());
            fos.write(car.getYear());
            fos.write(car.getPrice());
            Toast.makeText(context, "Saved new car successfully", Toast.LENGTH_SHORT).show();
        }
        catch (IOException e) {
            Toast.makeText(context, "Saving new car failed", Toast.LENGTH_SHORT).show();
        }
        finally {
            if (fos != null){
                try {
                    fos.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
        this.states++;
        Log.i(TAG, String.valueOf(this.states));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        this.states++;
        Log.i(TAG, String.valueOf(this.states));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
        this.states++;
        Log.i(TAG, String.valueOf(this.states));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "OnCreate");

        Car civic = new Car("Honda Civic", 2017, 12000, R.mipmap.honda);
        Car focus = new Car("Ford Focus", 2018, 15000, R.mipmap.ford);
        Car mondeo = new Car("Ford Mondeo", 2018, 22000, R.mipmap.ford);
        Car logan = new Car("Dacia Logan", 2015, 5000, R.mipmap.dacia);
        Car duster = new Car("Dacia Duster", 2017, 12000, R.mipmap.dacia);
        Car astra = new Car("Opel Astra", 2007, 3000, R.mipmap.opel);
        Car civic1 = new Car("Honda Civic", 2004, 3500, R.mipmap.honda);
        Car logan1 = new Car("Dacia Logan", 2005, 1000, R.mipmap.dacia);
        Car audi = new Car("Audi A4", 2009, 10000, R.mipmap.audi);
        Car bmw = new Car("BMW 520d", 2015, 20000, R.mipmap.bmw);

        carList.add(civic);
        carList.add(focus);
        carList.add(mondeo);
        carList.add(logan);
        carList.add(duster);
        carList.add(astra);
        carList.add(civic1);
        carList.add(logan1);
        carList.add(audi);
        carList.add(bmw);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(this, carList);
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null){
            this.states = savedInstanceState.getInt("states");
        }
        this.states++;
        Log.i(TAG, String.valueOf(this.states));
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("states", this.states);
    }
}
