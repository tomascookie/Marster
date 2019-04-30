package ru.brainix.ept.marster.network;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetApiData {

    private final String LOG_TAG = "GetApiData ";

    private URL mURL;
    private BufferedReader mBufferedReader;
    private String bufferLog;


    //Загружаем данные с АПИ в строку
    public String getData(){
        try {

            mURL = new URL("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=100&page=1&api_key=DEMO_KEY");
            HttpsURLConnection httpclient = (HttpsURLConnection) mURL.openConnection();
            httpclient.setRequestMethod("GET");
            httpclient.connect();

            mBufferedReader = new BufferedReader(new InputStreamReader(httpclient.getInputStream()));
            StringBuilder buf = new StringBuilder();

            String line = null;

            while ((line = mBufferedReader.readLine()) != null) {

                buf.append(line + "\n");
            }

            bufferLog = buf.toString();

            Log.d(LOG_TAG, bufferLog);




        }


        catch (IOException e) {
            e.printStackTrace();
        }

        finally {

            if (mBufferedReader != null) {

                try { mBufferedReader.close();  }

                catch (IOException e) {  e.printStackTrace(); }
            }

        }

       return bufferLog;


    }






}
