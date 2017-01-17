package com.example.usuario.apppruebatecnicatekus.Connection;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 14/12/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dbTekus.db";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, 11);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Notifications (_id INTEGER PRIMARY KEY AUTOINCREMENT,Date TEXT, Duration INTEGER, SendState INTEGER,NotificationId INTEGER);");


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



        db.execSQL("DROP TABLE IF EXISTS Notifications");
        onCreate(db);
    }
}
