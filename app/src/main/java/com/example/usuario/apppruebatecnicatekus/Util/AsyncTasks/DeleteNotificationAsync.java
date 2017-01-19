package com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.usuario.apppruebatecnicatekus.AdministrationActivity;
import com.example.usuario.apppruebatecnicatekus.Connection.DownloadInformation;
import com.example.usuario.apppruebatecnicatekus.Connection.SendInformation;
import com.example.usuario.apppruebatecnicatekus.Controllers.NotificationsController;
import com.example.usuario.apppruebatecnicatekus.Models.NotificationsModel;

/**
 * Created by Usuario on 16/01/2017.
 */

public class DeleteNotificationAsync extends AsyncTask<Void,Void,String> {

    private Context context;
    ProgressDialog pg;
    private DownloadInformation downloadInformation;
    private NotificationsController notificationsController;
    private NotificationsModel notificationsModel;
    private SendInformation sendInformation;
    DownloadNotificationAsync downloadNotificationAsync;
    private int NotificationId;
    public DeleteNotificationAsync(Context context, SQLiteDatabase db,int NotificationId) {
        this.context=context;
        downloadInformation= new DownloadInformation();
        sendInformation=new SendInformation();
        pg= new ProgressDialog(context);
        notificationsModel= new NotificationsModel(db);
        notificationsController= new NotificationsController(notificationsModel);
        this.NotificationId=NotificationId;
         downloadNotificationAsync= new DownloadNotificationAsync(context,db);
    }

    @Override
    protected void onPreExecute() {

        pg.setTitle("Tekus");
        pg.setMessage("Enviando Información");
        pg.setCancelable(false);
        pg.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {

        System.out.println("Notification id delete "+NotificationId);
        sendInformation.peticionDELETE("http://proyectos.tekus.co/Test/api/notifications/"+NotificationId);
        notificationsController.deleteNotification(NotificationId);
        return "OK";

    }

    @Override
    protected void onPostExecute(String aVoid) {
        pg.dismiss();
        super.onPostExecute(aVoid);
    }

}
