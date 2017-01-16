package com.example.usuario.apppruebatecnicatekus;

import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.usuario.apppruebatecnicatekus.Beans.Notification;
import com.example.usuario.apppruebatecnicatekus.Connection.DataBase;
import com.example.usuario.apppruebatecnicatekus.Controllers.NotificationsController;
import com.example.usuario.apppruebatecnicatekus.Models.NotificationsModel;
import com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks.DownloadNotificationAsync;
import com.example.usuario.apppruebatecnicatekus.Util.Useful;

import java.util.ArrayList;

public class AdministrationActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    DataBase dataBase;
    NotificationsController notificationsController;
    Useful useful;
    NotificationsModel notificationsModel;
    DownloadNotificationAsync downloadNotificationAsync;

    ListView NotificationList;

    private Integer[] imgid={
            R.drawable.calculate
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration);




        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        dataBase= new DataBase(AdministrationActivity.this);
        db = dataBase.getReadableDatabase();
        notificationsModel= new NotificationsModel(db);
        notificationsController= new NotificationsController(notificationsModel);
        useful= new Useful();

        notificationsController.notificationDownload();
        downloadNotificationAsync= new DownloadNotificationAsync(AdministrationActivity.this,db);

        callAsyncDownloadNotification();


        NotificationList = (ListView)findViewById(R.id.NotificationList);
        ArrayList<String> Notifications= new ArrayList<>();
        Notifications.add("prueba 1");

        Notification adapter= new Notification(this,Notifications,imgid,AdministrationActivity.this);

        NotificationList.setAdapter(adapter);


    }


    public void callAsyncDownloadNotification(){
        downloadNotificationAsync.execute();
    }



}
