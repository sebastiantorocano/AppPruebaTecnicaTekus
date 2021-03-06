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
import com.example.usuario.apppruebatecnicatekus.MenuActivity;
import com.example.usuario.apppruebatecnicatekus.Models.NotificationsModel;
import com.example.usuario.apppruebatecnicatekus.Util.Useful;

/**
 * Created by Usuario on 15/01/2017.
 */

public class DownloadNotificationAsync extends AsyncTask <Void,Void,String> {

    private Context context;
    ProgressDialog pg;
    private DownloadInformation downloadInformation;
    private NotificationsController notificationsController;
    private NotificationsModel notificationsModel;
    AdministrationActivity administrationActivity;


    Useful useful;
    Class To;
    public DownloadNotificationAsync(Context context, SQLiteDatabase db,Class To) {
        this.context=context;
        downloadInformation= new DownloadInformation();
        pg= new ProgressDialog(this.context);
        notificationsModel= new NotificationsModel(db);
        notificationsController= new NotificationsController(notificationsModel);

        administrationActivity= new AdministrationActivity();
        useful= new Useful();
        this.To=To;
    }

    @Override
    protected void onPreExecute() {

        pg.setMessage("Descargando Información");
        pg.setCancelable(false);
        pg.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        String Response= downloadInformation.peticionGET("http://proyectos.tekus.co/Test/api/notifications");
        System.out.println("Response "+Response);
        notificationsController.updateNotificationsDownloaded(Response);

        return "OK";
    }

    @Override
    protected void onPostExecute(String aVoid) {

        pg.dismiss();
            useful.goActivity(context,To);
        super.onPostExecute(aVoid);




    }


}
