package com.example.usuario.apppruebatecnicatekus.Util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;

import com.example.usuario.apppruebatecnicatekus.AdministrationActivity;
import com.example.usuario.apppruebatecnicatekus.MenuActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Usuario on 15/01/2017.
 */

public class Useful {
    public String MillisecondsToDate(long timeInMillis){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timeInMillis);
        String date = DateFormat.format("yyyy-MM-dd hh:mm:ss", cal).toString();


        return  date;
    }


    public void goActivity(Context from,Class to){
        Intent i = new Intent();
        i.setClass(from,to);
        from.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());
    }



    public boolean hasInternet(Context context) {
        boolean hasConnectedWifi = false;
        boolean hasConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("wifi"))
                if (ni.isConnected())
                    hasConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("mobile"))
                if (ni.isConnected())
                    hasConnectedMobile = true;
        }
        return hasConnectedWifi || hasConnectedMobile;
    }







}

