package com.example.usuario.apppruebatecnicatekus.Beans;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.usuario.apppruebatecnicatekus.R;

import java.util.ArrayList;

/**
 * Created by Usuario on 15/01/2017.
 */

public class NotificationBean {

    private int _id;
    private String Date;
    private int Duration;
    private int SendState;
    private int NotificationId;

    public void Local(int _id, String date, int duration, int sendState, int notificationId) {
        this._id = _id;
        Date = date;
        Duration = duration;
        SendState = sendState;
        NotificationId = notificationId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getDuration() {
        return Duration;
    }

    public void setDuration(int duration) {
        Duration = duration;
    }

    public int getSendState() {
        return SendState;
    }

    public void setSendState(int sendState) {
        SendState = sendState;
    }

    public int getNotificationId() {
        return NotificationId;
    }

    public void setNotificationId(int notificationId) {
        NotificationId = notificationId;
    }


}
