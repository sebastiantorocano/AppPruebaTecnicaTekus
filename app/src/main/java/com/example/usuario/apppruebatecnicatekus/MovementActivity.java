package com.example.usuario.apppruebatecnicatekus;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.usuario.apppruebatecnicatekus.Connection.DataBase;
import com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks.InsertNotificationAsync;
import com.example.usuario.apppruebatecnicatekus.Util.ShakeDetector;
import com.example.usuario.apppruebatecnicatekus.Util.Useful;

public class MovementActivity extends AppCompatActivity {

    // The following are used for the shake detection

    private ShakeDetector mShakeDetector;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    private SQLiteDatabase db;
    DataBase dataBase;

    Useful useful;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movement);

        dataBase = new DataBase(MovementActivity.this);
        db = dataBase.getReadableDatabase();
        useful= new Useful();


        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new ShakeDetector.OnShakeListener() {
            @Override
            public void onShake() {
                // Do stuff!
            }
        },MovementActivity.this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.btnReturn:
                Return();
                return true;

        }
        return true;
    }


    public void Return() {
        useful.goActivity(MovementActivity.this,MenuActivity.class);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
