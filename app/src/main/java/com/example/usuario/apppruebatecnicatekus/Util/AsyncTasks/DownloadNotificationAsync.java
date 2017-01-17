package com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Base64;

import com.example.usuario.apppruebatecnicatekus.AdministrationActivity;
import com.example.usuario.apppruebatecnicatekus.Connection.DownloadInformation;
import com.example.usuario.apppruebatecnicatekus.Controllers.NotificationsController;
import com.example.usuario.apppruebatecnicatekus.Models.NotificationsModel;

/**
 * Created by Usuario on 15/01/2017.
 */

public class DownloadNotificationAsync extends AsyncTask <Void,Void,Void> {

    private Context context;
    ProgressDialog pg;
    private DownloadInformation downloadInformation;
    private NotificationsController notificationsController;
    private NotificationsModel notificationsModel;
    public DownloadNotificationAsync(Context context, SQLiteDatabase db) {
        this.context=context;
        downloadInformation= new DownloadInformation();
        pg= new ProgressDialog(context);
        notificationsModel= new NotificationsModel(db);
        notificationsController= new NotificationsController(notificationsModel);
    }

    @Override
    protected void onPreExecute() {

        pg.setTitle("Tekus");
        pg.setMessage("Descargando Información");
        pg.setCancelable(false);
        pg.show();
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... params) {
        String Response= downloadInformation.peticionGET("http://proyectos.tekus.co/Test/api/notifications");
        System.out.println("Response "+Response);
        notificationsController.updateNotificationsDownloaded(Response);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pg.dismiss();
        Intent i = new Intent();
        i.setClass(context, AdministrationActivity.class);
        context.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onPostExecute(aVoid);
    }


}
