package com.example.usuario.apppruebatecnicatekus.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.text.format.DateFormat;

import com.example.usuario.apppruebatecnicatekus.Connection.DataBase;
import com.example.usuario.apppruebatecnicatekus.Controllers.NotificationsController;
import com.example.usuario.apppruebatecnicatekus.Models.NotificationsModel;

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
    private static final int MIN_SHAKE_ACCELERATION = 2;

    // Minimum number of movements to register a shake
    private static final int MIN_MOVEMENTS = 2;

    // Maximum time (in milliseconds) for the whole shake to occur
    private static final int MAX_SHAKE_DURATION = 500;

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
        setCurrentAcceleration(event);
        float maxLinearAcceleration = getMaxCurrentLinearAcceleration();

        if (maxLinearAcceleration >= MIN_SHAKE_ACCELERATION) {
            long now = System.currentTimeMillis();

            if (startTime == 0) {
                startTime = now;
            }




            long elapsedTime = now - startTime;


            System.out.println("elapsedTime "+elapsedTime);




            if (elapsedTime >= 2000) {
                System.out.println("mayor ");

                String StringStartTime=useful.MillisecondsToDate(startTime);
                System.out.println("start time string  "+StringStartTime);
                notificationsController.insertNotifications(StringStartTime);

                System.out.println("now "+now);
                String StringdateNow=useful.MillisecondsToDate(now);
                System.out.println("dateNow "+StringdateNow);

                Date DatestartTime=useful.FormatDate(StringStartTime);
                Date DatenowTime=useful.FormatDate(StringdateNow);
               long seconds= useful.DiferenceHours(DatenowTime,DatestartTime);


                System.out.println("seconds "+seconds);
                if(seconds>=2){

                    notificationsController.updateNofitications(StringStartTime,StringdateNow,seconds);

                       String json= notificationsController.getNotificationsSend();

                        System.out.println("json "+json);


                }





                resetShakeDetection();
           }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Intentionally blank
    }

    private void setCurrentAcceleration(SensorEvent event) {
        /*
         *  BEGIN SECTION from Android developer site. This code accounts for
         *  gravity using a high-pass filter
         */

        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate

        final float alpha = 0.8f;

        // Gravity components of x, y, and z acceleration
        mGravity[X] = alpha * mGravity[X] + (1 - alpha) * event.values[X];
        mGravity[Y] = alpha * mGravity[Y] + (1 - alpha) * event.values[Y];
        mGravity[Z] = alpha * mGravity[Z] + (1 - alpha) * event.values[Z];

        // Linear acceleration along the x, y, and z axes (gravity effects removed)
        mLinearAcceleration[X] = event.values[X] - mGravity[X];
        mLinearAcceleration[Y] = event.values[Y] - mGravity[Y];
        mLinearAcceleration[Z] = event.values[Z] - mGravity[Z];

        /*
         *  END SECTION from Android developer site
         */
    }

    private float getMaxCurrentLinearAcceleration() {
        // Start by setting the value to the x value
        float maxLinearAcceleration = mLinearAcceleration[X];

        // Check if the y value is greater
        if (mLinearAcceleration[Y] > maxLinearAcceleration) {
            maxLinearAcceleration = mLinearAcceleration[Y];
        }

        // Check if the z value is greater
        if (mLinearAcceleration[Z] > maxLinearAcceleration) {
            maxLinearAcceleration = mLinearAcceleration[Z];
        }

        // Return the greatest value
        return maxLinearAcceleration;
    }

    private void resetShakeDetection() {
        startTime = 0;
        moveCount = 0;
    }

    // (I'd normally put this definition in it's own .java file)
    public interface OnShakeListener {
        public void onShake();
    }



}