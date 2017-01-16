package com.example.usuario.apppruebatecnicatekus.Connection;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Usuario on 15/01/2017.
 */

public class SendInformation {


    public int peticionPOST( String strUrl, String data ,String UserAuthorization)
    {
        HttpURLConnection http = null;
        int responseCode = -1;

        try {
            URL url = new URL( strUrl );
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("POST");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", UserAuthorization);
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


    public int peticionPUT( String strUrl, String data ,String UserAuthorization)
    {
        HttpURLConnection http = null;
        int responseCode = -1;

        try {
            URL url = new URL( strUrl );
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("PUT");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", UserAuthorization);
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



    public int peticionDELETE( String strUrl,String UserAuthorization )
    {
        HttpURLConnection http = null;
        int responseCode = -1;
        try {
            URL url = new URL( strUrl );
            http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod("DELETE");
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", UserAuthorization);

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
