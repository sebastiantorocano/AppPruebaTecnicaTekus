package com.example.usuario.apppruebatecnicatekus;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.apppruebatecnicatekus.Util.ShakeDetector;

import java.util.List;

public class MenuActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


    }


    public void FindNotifications(View view) {
        Intent i = new Intent();
        i.setClass(MenuActivity.this, AdministrationActivity.class);
        startActivity(i);
    }

    public void DetectMovement(View view) {
        Intent i = new Intent();
        i.setClass(MenuActivity.this, MovementActivity.class);
        startActivity(i);
    }
}