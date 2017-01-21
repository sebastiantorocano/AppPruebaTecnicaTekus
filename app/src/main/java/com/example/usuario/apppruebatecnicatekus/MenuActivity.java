package com.example.usuario.apppruebatecnicatekus;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.apppruebatecnicatekus.Connection.DataBase;
import com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks.DownloadNotificationAsync;
import com.example.usuario.apppruebatecnicatekus.Util.ShakeDetector;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MenuActivity extends AppCompatActivity {

    DownloadNotificationAsync downloadNotificationAsync;
    private SQLiteDatabase db;
    DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        dataBase = new DataBase(MenuActivity.this);
        db = dataBase.getReadableDatabase();
        downloadNotificationAsync = new DownloadNotificationAsync(MenuActivity.this, db,AdministrationActivity.class);

    }


    public void FindNotifications(View view) {
        downloadNotificationAsync.execute();

    }

    public void DetectMovement(View view) {
        Intent i = new Intent();
        i.setClass(MenuActivity.this, MovementActivity.class);
        startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void Exit(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(MenuActivity.this);
        ad.setTitle("Alerta");
        ad.setMessage("¿Esta seguro que desea salir?");
        ad.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });

        ad.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ad.show();
    }

}