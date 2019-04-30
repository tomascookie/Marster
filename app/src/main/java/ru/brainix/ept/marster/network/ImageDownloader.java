package ru.brainix.ept.marster.network;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build.VERSION_CODES;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class ImageDownloader {

private final String LOG_TAG = "ImageDownloader ";


		@TargetApi(VERSION_CODES.KITKAT)
		public byte[] getImageByte(String urlAdress){

			URL url = null;

			try {
				url = new URL(urlAdress);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			ByteArrayOutputStream output = new ByteArrayOutputStream();
			URLConnection conn = null;

			try {
				conn = url.openConnection();
			} catch (IOException e) {
				e.printStackTrace();
			}

			conn.setRequestProperty("User-Agent", "Firefox");

			try (InputStream inputStream = conn.getInputStream()) {
				int n = 0;
				byte[] buffer = new byte[1024];
				while (-1 != (n = inputStream.read(buffer))) {
					output.write(buffer, 0, n);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return output.toByteArray();
		}


    //Класс загрузки через поток
    private class DownloadImage implements Runnable {

        private Bitmap image;
        private String urlAdress;


        public Bitmap getImage() {
            return image;
        }


        public void run() {

            image = getUrlImage(urlAdress);

        }


        public void setUrlAdress(String urlAdress) {
            this.urlAdress = urlAdress;
        }
    };


    //Метод загрузки картинки
    private  Bitmap getUrlImage(String urlAdress){


        URL url = null;

        try { url = new URL(urlAdress);  }

        catch (MalformedURLException e) { e.printStackTrace(); }


        Bitmap bmp = null;

        try {   bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream()); }

        catch (IOException e) {  e.printStackTrace(); }


        Log.d(LOG_TAG, "Image downloaded");

        return bmp;

    }




}

