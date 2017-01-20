package com.example.usuario.apppruebatecnicatekus.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.usuario.apppruebatecnicatekus.Connection.DataBase;
import com.example.usuario.apppruebatecnicatekus.Controllers.NotificationsController;
import com.example.usuario.apppruebatecnicatekus.Models.NotificationsModel;
import com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks.InsertNotificationAsync;
import com.example.usuario.apppruebatecnicatekus.Util.AsyncTasks.UpdateNotificationAsync;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Usuario on 15/01/2017.
 */

public class ShakeDetector  implements SensorEventListener{

    Context CONTEXT;
    private SQLiteDatabase db;
    DataBase dataBase;
    NotificationsController notificationsController;
    Useful useful;
    NotificationsModel notificationsModel;



    // Minimum acceleration needed to count as a shake movement

    // Arrays to store gravity and linear acceleration values
    private float[] mGravity = { 0.0f, 0.0f, 0.0f };
    private float[] mLinearAcceleration = { 0.0f, 0.0f, 0.0f };

    // Indexes for x, y, and z values
    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    // OnShakeListener that will be notified when the shake is detected
    private OnShakeListener mShakeListener;

    // Start time for the shake detection
    long startTime = 0;

    // Counter for shake movements
    int moveCount = 0;


    private static final float SHAKE_THRESHOLD = 1.0f;
    private static final float Minumun_Shake = 0.97f;
    private static final int SHAKE_WAIT_TIME_MS = 250;
    private static final float ROTATION_THRESHOLD = 2.0f;
    private static final int ROTATION_WAIT_TIME_MS = 100;

    private SensorManager mSensorManager;
    private Sensor mSensorAcc;
    private Sensor mSensorGyr;
    private long mShakeTime = 0;
    private long mRotationTime = 0;

    long seconds = 0;

    boolean flag=false;

    InsertNotificationAsync insertNotificationAsync;
    UpdateNotificationAsync updateNotificationAsync;

    public ShakeDetector(OnShakeListener shakeListener, Context c) {
        mShakeListener = shakeListener;
        CONTEXT=c;

        dataBase= new DataBase(CONTEXT);
        db = dataBase.getReadableDatabase();
        notificationsModel= new NotificationsModel(db);
        notificationsController= new NotificationsController(notificationsModel);
        useful= new Useful();



    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        detectShake(event);
    }


    private void detectShake(SensorEvent event) {

        long now = System.currentTimeMillis();


        if ((now - mShakeTime) > SHAKE_WAIT_TIME_MS) {


            float gX = event.values[0] / SensorManager.GRAVITY_EARTH;
            float gY = event.values[1] / SensorManager.GRAVITY_EARTH;
            float gZ = event.values[2] / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement
            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            // Change background color if gForce exceeds threshold;
            // otherwise, reset the color


            System.out.println("gforce "+gForce);
            if (gForce > SHAKE_THRESHOLD || gForce<Minumun_Shake) {
                 seconds=now-mShakeTime;
                if(seconds==2000){
                    flag=true;


                    String StringStartTime=useful.MillisecondsToDate(now);
                    notificationsController.deleteAll();
                    notificationsController.insertNotifications(StringStartTime);
                    insertNotificationAsync= new InsertNotificationAsync(db);
                    insertNotificationAsync.execute();
                }
                if(seconds>2000 && flag==false){
                    flag=true;


                    String StringStartTime=useful.MillisecondsToDate(now);
                    notificationsController.insertNotifications(StringStartTime);
                    insertNotificationAsync= new InsertNotificationAsync(db);
                    insertNotificationAsync.execute();
                }


                System.out.println("seconds "+seconds);
            }else{
                flag=false;
                if(seconds>2000){
                    System.out.println("duration "+seconds);
                    long totalDuration=(now-mShakeTime)/1000;
                    System.out.println("total duration "+totalDuration);

                    notificationsController.updateNofitications(totalDuration);
                    updateNotificationAsync= new UpdateNotificationAsync(db);
                    updateNotificationAsync.execute();

                }
                seconds=0;
                System.out.println("se detuvo");
                mShakeTime = now;
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Intentionally blank
    }


    // (I'd normally put this definition in it's own .java file)
    public interface OnShakeListener {
        public void onShake();
    }



}