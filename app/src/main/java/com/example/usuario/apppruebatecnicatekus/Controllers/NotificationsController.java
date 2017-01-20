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

    /*
    *Metodo para insertar notificaciones en la base de datos
    * @param Date fecha de inicio del movimiento
     */
    public void insertNotifications(String Date) {
        notificationsModel.insertIntoTable("Date,Duration,SendState,NotificationId", Date, 0, 1, 0);

    }

    /*
    *función para obtener el NotificationId de la base de datos
    * @return NotificationId
     */
    public String getNotificationId(){
        String NotificationId=notificationsModel.search("NotificationId","SendState",1);

        return NotificationId;
    }

    /*
    *Metodo para modificar la duracion del movimiento
    * @param seconds segundos de duracion
     */
    public void updateNofitications(long seconds) {

        String _id = notificationsModel.search("MAX(_id)", "SendState", 1);

        int Duration = (int) seconds;

        notificationsModel.updateRecord("Duration", Duration, Integer.parseInt(_id));
        Hashtable result = notificationsModel.searchAllRow("_id", _id);

    }


    /*
  *Metodo para eliminar un registro
  * @param NotificationId  , es el parametro por el cual se hace la busqueda para eliminar
   */
    public void deleteNotification(int NotificationId) {
        notificationsModel.deleteWhereRecord("NotificationId", NotificationId);

    }

   /*Metodo insertar las notificaciones descargadas
    * @param json  , json de las notificaciones
    */
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



    /*funcion obtener todas las notificaciones
    * @param Hashtable
    */
    public Hashtable getAllNotifications() {
        Hashtable result = notificationsModel.searchAll();

        return result;
    }


    /*funcion obtener  la notificacion a enviar
   * @param String
   */
    public String getNotificationsSend() {
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


    /*Metodo para modificar el NotificationId de un regisro
   * @param json, json que retorna cuando se inserta una notificación
   */
    public void updatesNotificationsResponse(String json) {
        JSONArray jsonArray = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            notificationsModel.updateWhereRecord("NotificationId",jsonObject.getString("NotificationId"),"SendState",1);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        notificationsModel.updateWhereRecord("SendState", 1, "SendState", 1);
        getAllNotifications();
    }

    /*Metodo eliminar todas las notificaciones
   */
    public void deleteAll() {
        notificationsModel.deleteAllRecord();
    }


}
