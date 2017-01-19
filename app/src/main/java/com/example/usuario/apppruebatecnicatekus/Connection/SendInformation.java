package com.example.usuario.apppruebatecnicatekus.Connection;

import android.os.StrictMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Usuario on 15/01/2017.
 */

public class SendInformation {


    public String peticionPOST( String strUrl, String data )
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpURLConnection http = null;
        int responseCode = -1;
        String content = null;

        try {
            URL url = new URL( strUrl );
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization","Basic 1152216574");
            http.setDoOutput(true);

            PrintWriter writer = new PrintWriter(http.getOutputStream());
            writer.print(data);
            writer.flush();

            if( http.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                StringBuilder sb = new StringBuilder();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader( http.getInputStream() ));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                content = sb.toString();
                reader.close();
            }

            responseCode = http.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (http != null) http.disconnect();
        }
        return content;
    }


    public int peticionPUT( String strUrl, String data)
    {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        HttpURLConnection http = null;
        int responseCode = -1;

        try {
            URL url = new URL( strUrl );
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("PUT");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization","Basic 1152216574");
            http.setDoOutput(true);

            PrintWriter writer = new PrintWriter(http.getOutputStream());
            writer.print(data);
            writer.flush();

            responseCode = http.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (http != null) http.disconnect();
        }
        return responseCode;
    }



    public int peticionDELETE( String strUrl )
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection http = null;
        int responseCode = -1;
        try {
            URL url = new URL( strUrl );
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("DELETE");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization","Basic 1152216574");

            // Conectar y obtener el codigo de respuesta
            responseCode = http.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (http != null) http.disconnect();
        }
        return responseCode;
    }
}
