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

public class DeleteNotificationAsync extends AsyncTask<Void,Void,Void> {

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
    protected Void doInBackground(Void... params) {

        System.out.println("Notification id delete "+NotificationId);
        sendInformation.peticionDELETE("http://proyectos.tekus.co/Test/api/notifications/"+NotificationId);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pg.dismiss();
        downloadNotificationAsync.execute();
       /* Intent i = new Intent();
        i.setClass(context, AdministrationActivity.class);
        context.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());*/
        super.onPostExecute(aVoid);
    }

}
