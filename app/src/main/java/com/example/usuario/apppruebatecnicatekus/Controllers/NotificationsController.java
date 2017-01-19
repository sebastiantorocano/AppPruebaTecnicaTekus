package com.example.usuario.apppruebatecnicatekus.Controllers;

import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.apppruebatecnicatekus.Models.NotificationsModel;
import com.example.usuario.apppruebatecnicatekus.Util.Useful;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * Created by Usuario on 15/01/2017.
 */

public class NotificationsController {

    NotificationsModel notificationsModel;
    Useful useful;

    public NotificationsController(NotificationsModel notificationsModel) {
        this.notificationsModel = notificationsModel;
        useful = new Useful();
    }

    public void insertNotifications(String Date) {


        System.out.println("date " + Date);


        System.out.println("llego insetar");
        notificationsModel.insertIntoTable("Date,Duration,SendState,NotificationId", Date, 0, 1, 0);

    }

    public String getNotificationId(){
        String NotificationId=notificationsModel.search("NotificationId","SendState",1);

        return NotificationId;
    }

    public void updateNofitications(long seconds) {

        System.out.println("llego update notifications");

        String _id = notificationsModel.search("MAX(_id)", "SendState", 1);

        int Duration = (int) seconds;

        System.out.println("seconds uodate " + seconds);
        System.out.println("_id " + _id);
        notificationsModel.updateRecord("Duration", Duration, Integer.parseInt(_id));
        Hashtable result = notificationsModel.searchAllRow("_id", _id);

        System.out.println("imprimir informacion del id " + _id);
        for (int i = 0; i < result.size(); i++) {
            Hashtable data = (Hashtable) result.get(i);

            System.out.println("_id " + data.get("_id"));
            System.out.println("Date " + data.get("Date"));
            System.out.println("Duration " + data.get("Duration"));
            System.out.println("SendState " + data.get("SendState"));
            System.out.println("NotificationId " + data.get("NotificationId"));


        }
    }

    public void deleteNotification(int NotificationId) {
        notificationsModel.deleteWhereRecord("NotificationId", NotificationId);

    }

    public void updateNotificationsDownloaded(String json) {
        try {

            notificationsModel.deleteAllRecord();
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject helper = jsonArray.getJSONObject(i);

                notificationsModel.insertIntoTable("Date,Duration,SendState,NotificationId", helper.getString("Date"), helper.getString("Duration"), 1, helper.getString("NotificationId"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    public Hashtable getAllNotifications() {
        Hashtable result = notificationsModel.searchAll();
        for (int i = 0; i < result.size(); i++) {
            Hashtable data = (Hashtable) result.get(i);

            System.out.println("_id "+data.get("_id"));
            System.out.println("Date "+data.get("Date"));
            System.out.println("Duration "+data.get("Duration"));
            System.out.println("SendState "+data.get("SendState"));
            System.out.println("NotificationId "+data.get("NotificationId"));

        }

        return result;
    }


    public String getNotificationsSendUpdate() {
        JSONObject jsonNotifications = new JSONObject();
        Hashtable result = notificationsModel.searchAll();
        for (int i = 0; i < result.size(); i++) {
            Hashtable data = (Hashtable) result.get(i);

            try {
                jsonNotifications.put("NotificationId", data.get("NotificationId"));
                jsonNotifications.put("Date", data.get("Date"));
                jsonNotifications.put("Duration", data.get("Duration"));


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return jsonNotifications.toString();
    }

    public String getNotificationsSend() {
        JSONObject jsonNotifications = new JSONObject();
        Hashtable result = notificationsModel.searchAll();
        for (int i = 0; i < result.size(); i++) {
            Hashtable data = (Hashtable) result.get(i);

            try {
                jsonNotifications.put("NotificationId", data.get("_id"));
                jsonNotifications.put("Date", data.get("Date"));
                jsonNotifications.put("Duration", data.get("Duration"));

                System.out.println("______json____");
                System.out.println("_id " + data.get("_id"));
                System.out.println("Date " + data.get("Date"));
                System.out.println("Duration " + data.get("Duration"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        return jsonNotifications.toString();
    }


    public void updatesNotificationsResponse(String json) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(json);

            System.out.println("NotificationId json "+jsonObject.getString("NotificationId"));
            notificationsModel.updateWhereRecord("NotificationId",jsonObject.getString("NotificationId"),"SendState",1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        notificationsModel.updateWhereRecord("SendState", 1, "SendState", 1);
        getAllNotifications();
    }

    public void deleteAll() {
        notificationsModel.deleteAllRecord();
    }


}
