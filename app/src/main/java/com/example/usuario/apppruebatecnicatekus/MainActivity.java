package com.example.usuario.apppruebatecnicatekus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void Login(View view) {
        Intent i = new Intent();
        i.setClass(MainActivity.this, MenuActivity.class);
        startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());

    }

    public void Exit(View view) {
        AlertDialog.Builder ad= new AlertDialog.Builder(MainActivity.this);
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
