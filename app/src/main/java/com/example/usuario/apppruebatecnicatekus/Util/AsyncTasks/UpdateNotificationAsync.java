package com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks;

import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.usuario.apppruebatecnicatekus.Connection.SendInformation;
import com.example.usuario.apppruebatecnicatekus.Controllers.NotificationsController;
import com.example.usuario.apppruebatecnicatekus.Models.NotificationsModel;

/**
 * Created by Usuario on 18/01/2017.
 */

public class UpdateNotificationAsync extends AsyncTask<Void, Void, String> {


    private NotificationsController notificationsController;
    private NotificationsModel notificationsModel;
    private SendInformation sendInformation;


    public UpdateNotificationAsync(SQLiteDatabase db) {
        sendInformation = new SendInformation();
        notificationsModel = new NotificationsModel(db);
        notificationsController = new NotificationsController(notificationsModel);

    }


    @Override
    protected String doInBackground(Void... params) {
        System.out.println("llego a notification update");
        String NotificationId = notificationsController.getNotificationId();
        System.out.println("Notificacion id to update "+NotificationId);
        String json = notificationsController.getNotificationsSend();
        System.out.println("json a enviar update " + json);
        sendInformation.peticionPUT("http://proyectos.tekus.co/Test/api/notifications/"+NotificationId, json);

        return "OK";

    }
}
