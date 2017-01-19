package com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.usuario.apppruebatecnicatekus.Connection.DownloadInformation;
import com.example.usuario.apppruebatecnicatekus.Connection.SendInformation;
import com.example.usuario.apppruebatecnicatekus.Controllers.NotificationsController;
import com.example.usuario.apppruebatecnicatekus.Models.NotificationsModel;

/**
 * Created by Usuario on 18/01/2017.
 */

public class InsertNotificationAsync  extends AsyncTask<Void,Void,String> {


    private NotificationsController notificationsController;
    private NotificationsModel notificationsModel;
    private SendInformation sendInformation;


    public InsertNotificationAsync(SQLiteDatabase db) {
        sendInformation=new SendInformation();
        notificationsModel= new NotificationsModel(db);
        notificationsController= new NotificationsController(notificationsModel);

    }



    @Override
    protected String doInBackground(Void... params) {
        System.out.println("llego a notification insert");

        String json=  notificationsController.getNotificationsSend();
        System.out.println("json a enviar "+json);
      String jsonResponse= sendInformation.peticionPOST("http://proyectos.tekus.co/Test/api/notifications/",json);

        System.out.println("json Response "+jsonResponse);
        notificationsController.updatesNotificationsResponse(jsonResponse);
        return "OK";

    }

}
