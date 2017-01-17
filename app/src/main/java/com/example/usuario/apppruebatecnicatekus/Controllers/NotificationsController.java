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
        notificationsModel.insertIntoTable("Date,Duration,SendState,NotificationId", Date, 0, 0,0);

    }

    public void updateNofitications(String DateStart, String DateNow,long seconds) {

        System.out.println("llego update notifications");

        String _id = notificationsModel.search("MAX(_id)", "SendState", 0);

        String HourDateStart = useful.GetHourDate(DateStart);
        String HourNow = useful.GetHourDate(DateNow);

        int Duration= (int) seconds;

        System.out.println("seconds uodate "+seconds);
        System.out.println("_id "+_id);
        notificationsModel.updateRecord("Duration",Duration,Integer.parseInt(_id));
    }
    public void updateNotificationsDownloaded(String json){
        try {

            notificationsModel.deleteAllRecord();
            JSONArray jsonArray=new JSONArray(json);
            for (int i = 0; i <jsonArray.length() ; i++) {
                JSONObject helper=jsonArray.getJSONObject(i);

                    notificationsModel.insertIntoTable("Date,Duration,SendState,NotificationId",helper.getString("Date"),helper.getString("Duration"),1,helper.getString("NotificationId"));


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public  Hashtable getAllNotifications(){
        Hashtable result= notificationsModel.searchAll();

        return  result;
    }

    public String getNotificationsSend() {
        JSONArray arrayNotifications= new JSONArray();
        JSONObject jsonNotifications = new JSONObject();
        Hashtable result = notificationsModel.searchAllRow("SendState", 0);
        for (int i = 0; i < result.size(); i++) {
            Hashtable data = (Hashtable) result.get(i);

            try {
                jsonNotifications.put("NotificationId",data.get("_id"));
                jsonNotifications.put("Date",data.get("Date"));
                jsonNotifications.put("Duration",data.get("Duration"));

                System.out.println("______json____");
                System.out.println("_id "+data.get("_id"));
                System.out.println("Date "+data.get("Date"));
                System.out.println("Duration "+data.get("Duration"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            arrayNotifications.put(jsonNotifications);

        }
        return arrayNotifications.toString();
    }



}
