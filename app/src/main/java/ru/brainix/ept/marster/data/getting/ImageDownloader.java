package ru.brainix.ept.marster.data.getting;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownloader {

private final String LOG_TAG = "ImageDownloader ";


    //Запускаем поток
    public byte[] downloadImage(String urlAdress){

        DownloadImage downloadImage = new DownloadImage();
        downloadImage.setUrlAdress(urlAdress);

        Thread thread = new Thread(downloadImage);
        thread.start();


            try { thread.join(); }

            catch (InterruptedException e) { e.printStackTrace(); }


        //Конвертим битмап в байтовый массив
        Bitmap finImg = downloadImage.getImage();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        finImg.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        byte[] img = bos.toByteArray();


        return img;
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
    private synchronized Bitmap getUrlImage(String urlAdress){

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

