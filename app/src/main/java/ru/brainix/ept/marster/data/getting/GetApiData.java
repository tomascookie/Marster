package ru.brainix.ept.marster.data.getting;

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


    //Запускаем поток
    public String textGetter(){

        GetServerResponse response = new GetServerResponse();

        Thread thread = new Thread(response);
        thread.start();

            try { thread.join(); }

            catch (InterruptedException e) { e.printStackTrace(); }


        return response.getText();
    }


    //Получаем через потоко данные сервера
    private class GetServerResponse implements Runnable {

        private String text;


        public String getText() {
            return text;
        }


        public void run() {

            text = getData();

        }


    };


    //Загружаем данные с АПИ в строку
    private synchronized String getData(){
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
