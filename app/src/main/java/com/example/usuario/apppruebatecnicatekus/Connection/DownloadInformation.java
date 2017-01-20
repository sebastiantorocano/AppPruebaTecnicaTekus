package com.example.usuario.apppruebatecnicatekus.Connection;

import android.os.StrictMode;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Usuario on 15/01/2017.
 */

public class DownloadInformation {

    public String peticionGET( String strUrl )
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        HttpURLConnection http = null;
        String content = null;


        try {
            URL url = new URL( strUrl );
            http = (HttpURLConnection)url.openConnection();
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Accept", "application/json");

            http.setRequestProperty("Authorization","Basic 1152216574");
            http.setRequestMethod("GET");

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

            System.out.println("entro al try luego");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if( http != null ) http.disconnect();
        }
        return content;
    }
}
