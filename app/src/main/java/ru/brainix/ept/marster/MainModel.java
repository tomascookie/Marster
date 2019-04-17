package ru.brainix.ept.marster;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.util.List;

import ru.brainix.ept.marster.data.getting.DataModel;
import ru.brainix.ept.marster.database.ImageDbAdapter;

public class MainModel {

    private final String LOG_TAG = "MainModel ";



    //Сохраняем изображение со всеми параметрами
    public void insertImg(Context cntxt, int imageId, byte[] imageByteArray, int imageState){

        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(cntxt);

        DataModel imageModel = new DataModel( imageId, imageByteArray, imageState);

        adapter.open();

        adapter.insert(imageModel);

        adapter.close();


    }


    //Проверяем есть ли картинка с нужным айдишником
    public int getImgId(Context cntxt, int imageId){

        int anser;

        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(cntxt);

        adapter.open();

        DataModel myModel = adapter.getData(imageId);

        adapter.close();

        anser = myModel.getImageId();

        Log.d(LOG_TAG, "ImageId " + anser);


        return anser;
    }

    //Получаем статус картинки по айдишнику
    public int getImgState(Context cntxt, int imageId){

        int anser;

        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(cntxt);

        adapter.open();

        DataModel myModel = adapter.getData(imageId);

        adapter.close();

        anser = myModel.getImageState();

        Log.d(LOG_TAG, "ImageId " + anser);


        return anser;
    }




    //Получаем список всех картинок из БД
    public List<DataModel> getByCountId(Context cntxt){


        List<DataModel> ourList;

        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(cntxt);

        adapter.open();

        ourList = adapter.getDataByCountId();

        adapter.close();


        return ourList;
    }


    //Получаем картинку по ее айди
    public Bitmap getImgBitmap(Context cntxt, int imageId){


        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(cntxt);

        adapter.open();

        DataModel myModel = adapter.getData(imageId);

        adapter.close();

        byte[] bitmapArray = myModel.getImageByteArray();

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inSampleSize = 2;

        Bitmap bmp = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);


        return bmp;
    }


    //Меняем статус изображения на неактивный
    public void delImage(Context cntxt, int imageId){

        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(cntxt);


        adapter.open();

        //adapter.delete(imageId);

        adapter.update(imageId);

        adapter.close();

    }







}
