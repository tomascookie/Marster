package ru.brainix.ept.marster;

import android.content.Context;
import android.util.Log;

import java.util.List;

import ru.brainix.ept.marster.data.getting.DataModel;
import ru.brainix.ept.marster.data.getting.DataParser;
import ru.brainix.ept.marster.list.setting.CardAdapter;

public class MainPresenter {

    private final String LOG_TAG = "MainPresenter ";

    //Запускаем поток
    public  CardAdapter launch(Context cntxt){

        Launch downloadImage = new Launch();
        downloadImage.setCntxt(cntxt);


        Thread thread = new Thread(downloadImage);
        thread.start();


            try { thread.join(); }

            catch (InterruptedException e) { e.printStackTrace(); }



       return downloadImage.getCardAdapter();
    }



    //Поток методов загрузки
    private class Launch implements Runnable {

        private Context cntxt;
        private CardAdapter mCardAdapter;




        public void run() {

            mCardAdapter = onActivityLaunch(cntxt);

        }



        public void setCntxt(Context cntxt) {
            this.cntxt = cntxt;
        }

        public CardAdapter getCardAdapter() {
            return mCardAdapter;
        }
    };


    //Вызываем последовательно методы загрузки, формируем список
    private CardAdapter  onActivityLaunch(Context cntxt){


        DataParser dataParser = new DataParser();

        List<DataModel> newList = dataParser.parserThread(cntxt);



        Log.d(LOG_TAG, "List Formed with size " + newList.size());


        if(newList.size() == 0){ return null; }



        CardAdapter adapter = new CardAdapter(cntxt, newList);


        return adapter;


    }














}
