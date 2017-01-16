package com.example.usuario.apppruebatecnicatekus.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.format.DateFormat;

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
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();


        return  date;
    }

    public Date FormatDate(String Date){
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date dateFormat = null;

        try {
             dateFormat=formatoDelTexto.parse(Date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateFormat;
    }

    public long DiferenceHours(Date hour1, Date hour2){


        System.out.println("hour1 "+hour1);
        System.out.println("hour2 "+hour2);

        long diff = hour1.getTime() - hour2.getTime();

        long segundos = diff / 1000;



        return segundos;
    }

    public String GetHourDate(String Date){
        String[] arrDate = Date.split(" ");
        String Hour = arrDate[1];

        return Hour;

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

