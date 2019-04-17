package ru.brainix.ept.marster.data.getting;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ru.brainix.ept.marster.MainModel;

public class DataParser {

    private final String LOG_TAG = "DataParser ";

    //Кол-во загружаемых картинок
    private final int MAX_IMG_COUNT = 20;

    String serverRequest;
    List<DataModel> modelList = new ArrayList<>();



    //Запускаем поток
    public List<DataModel> parserThread(Context cntxt){


        GetAndParsData getAndPars = new GetAndParsData();
        getAndPars.setCntxt(cntxt);

        Thread thread = new Thread(getAndPars);
        thread.start();

            try { thread.join(); }

            catch (InterruptedException e) { e.printStackTrace(); }


        return getAndPars.getDataModelList();

    }


    //Получаем данные со всех методов и делаем список
    private class GetAndParsData implements Runnable {

        private List<DataModel> mDataModelList = new ArrayList<>();
        private Context cntxt;





        public void run() {


            mDataModelList = parser(cntxt);


        }


        public List<DataModel> getDataModelList() {
            return mDataModelList;
        }

        public void setCntxt(Context cntxt) {
            this.cntxt = cntxt;
        }
    };






    //Получаем данные, парсим, формируем лист
     private  List<DataModel> parser(Context cntxt){

         CheckNetwork check = new CheckNetwork();

         Boolean status = check.isInternetAvailable();



         if(status){

             Log.d(LOG_TAG, "!!!Загружаем из веба");

             getApiData();

             try { jsonParser(serverRequest, cntxt);  }

             catch (JSONException e) {  e.printStackTrace(); }


             return modelList;


         }

         else {

             Log.d(LOG_TAG, "!!!Нет сети, открываем БД");

             MainModel model = new MainModel();


             return model.getByCountId(cntxt);

         }


    }



    //Получаем ответ сервера, записываем в строку
    private void getApiData(){


        GetApiData getApiData = new GetApiData();
        String text = getApiData.textGetter();

        Log.d(LOG_TAG, "Server Request Downloaded");

        serverRequest = text;
    }



    //Разбираем json сервера, сохраняем данные в модель
    private synchronized void jsonParser(String serverRequest, Context cntxt) throws JSONException{


        JSONObject jObject = new JSONObject(serverRequest);
        JSONArray jArray = jObject.getJSONArray("photos");

        ImageDownloader imageDownloader = new ImageDownloader();


        for(int i=0;  i < MAX_IMG_COUNT; i++) {

            JSONObject iterateObject = jArray.getJSONObject(i);

            int imageId = iterateObject.getInt("id");


            //Проверяем есть ли изображение в бд
            MainModel model = new MainModel();

            int imgId = model.getImgId(cntxt, imageId);

            //Проверяем статус изображения в БД
            int imgStatus = model.getImgState(cntxt, imgId);


            if (imgStatus != 1) {


                //Если изображения нет, то загружаем и сохраняем
                if (imgId == -1) {

                    String imageUrl = iterateObject.getString("img_src");

                    //загружаем картику, переводим в байткод и добавляем в модель
                    byte[] imageFromUrl = imageDownloader.downloadImage(imageUrl);


                    model.insertImg(cntxt, imageId, imageFromUrl, 0);

                    modelList.add(new DataModel(imageId, imageFromUrl, 0));

                    Log.d(LOG_TAG, "Image download from web");


                }

                //Если изображение есть в БД, конвертим и добавляем в список
                else {

                    Bitmap bmp = model.getImgBitmap(cntxt, imageId);


                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                    byte[] img = bos.toByteArray();

                    modelList.add(new DataModel(imageId, img, 0));

                    Log.d(LOG_TAG, "Image showing from database");

                }

            }
        }

        Log.d(LOG_TAG, "server data parsed complete");

    }



}
