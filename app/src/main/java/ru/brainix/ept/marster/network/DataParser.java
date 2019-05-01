package ru.brainix.ept.marster.network;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.brainix.ept.marster.screens.main.MainModel;

public class DataParser {

    private final String LOG_TAG = "DataParser ";

    //Кол-во загружаемых картинок
    private final int MAX_IMG_COUNT = 20;

    private List<DataModel> modelList = new ArrayList<>();



    //Запускаем поток
    public List<DataModel> parserThread(){


        GetAndParsData getAndPars = new GetAndParsData();

        Thread thread = new Thread(getAndPars);
        thread.start();

            try { thread.join(); }

            catch (InterruptedException e) { e.printStackTrace(); }


        return getAndPars.getDataModelList();

    }


    //Получаем данные со всех методов и делаем список
    private class GetAndParsData implements Runnable {

        private List<DataModel> mDataModelList = new ArrayList<>();

        public void run() {

            mDataModelList = parser();

        }


        public List<DataModel> getDataModelList() {
            return mDataModelList;
        }

    };



    //Получаем данные, парсим, формируем лист
     private  List<DataModel> parser(){


         if(internetStatus()){

             Log.d(LOG_TAG, "!!!Загружаем из веба");

             try { jsonParser();  }

             catch (JSONException e) {  e.printStackTrace(); }


             return modelList;


         }

         else {

             Log.d(LOG_TAG, "Нет сети, открываем БД");

             MainModel model = new MainModel();


             return model.getByCountId();

         }


    }



    //Получаем ответ сервера, записываем в строку
    private String getApiData(){


        GetApiData getApiData = new GetApiData();

        return getApiData.getData();


    }


    private Boolean internetStatus(){
			CheckNetwork check = new CheckNetwork();

			return check.isInternet();
		}



		private byte[] downloadImage(String imageUrl){

     	ImageDownloader imageDownloader = new ImageDownloader();
			return imageDownloader.getImageByte(imageUrl);
		}



    //Разбираем json сервера, сохраняем данные в модель
    private void jsonParser() throws JSONException {


        JSONObject jObject = new JSONObject(getApiData());
        JSONArray jArray = jObject.getJSONArray("photos");


        for(int i=0;  i < MAX_IMG_COUNT; i++) {

            JSONObject iterateObject = jArray.getJSONObject(i);

            int imageId = iterateObject.getInt("id");


            //Проверяем есть ли изображение в бд
            MainModel model = new MainModel();

            int imgId = model.getImgId(imageId);

            //Проверяем статус изображения в БД
            int imgStatus = model.getImgState(imgId);


            if (imgStatus != 1) {


                //Если изображения нет, то загружаем и сохраняем
                if (imgId == -1) {

                    String imageUrl = iterateObject.getString("img_src");

                    //загружаем картику, переводим в байткод и добавляем в модель
                    byte[] imageFromUrl = downloadImage(imageUrl);


                    model.insertImg(imageId, imageFromUrl, 0);

                    modelList.add(new DataModel(imageId, imageFromUrl, 0));

                    Log.d(LOG_TAG, "Image download from web");


                }

                //Если изображение есть в БД, конвертим и добавляем в список
                else {

                    byte[] img = model.getImgBitmap(imageId);

                    modelList.add(new DataModel(imageId, img, 0));

                    Log.d(LOG_TAG, "Image showing from database");

                }

            }
        }

        Log.d(LOG_TAG, "server data parsed complete");

    }



}
