package ru.brainix.ept.marster.screens.main;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import java.util.concurrent.ExecutionException;
import ru.brainix.ept.marster.App;
import ru.brainix.ept.marster.network.DataModel;
import ru.brainix.ept.marster.database.ImageDbAdapter;
import ru.brainix.ept.marster.network.DataParser;

public class MainModel implements IMainModel {

    private final String LOG_TAG = "MainModel ";


		@Override
		public void getMainList(CompleteCallback callback) {

			GetList getList = new GetList(callback);
			getList.execute();


		}

		class GetList extends AsyncTask<Void, Void, List<DataModel>> {

			private final CompleteCallback callback;

			GetList(CompleteCallback callback) {
				this.callback = callback;
			}

			@Override
			protected List<DataModel> doInBackground(Void... voids) {

				DataParser dataParser = new DataParser();

				List<DataModel> newList = dataParser.parserThread();


				Log.d(LOG_TAG, "List Formed with size " + newList.size());



				if(newList.size() == 0){ return null; }


				return newList;
			}

			@Override
			protected void onPostExecute(List<DataModel> dataModels) {
				super.onPostExecute(dataModels);
				if (callback != null) {
					callback.onComplete(dataModels);
				}


			}
		}


		interface CompleteCallback {
			void onComplete(List<DataModel> dataModels);
		}


		//TODO: Работа с БД скинуть в отдельный класс

    //Сохраняем изображение со всеми параметрами
    public void insertImg(int imageId, byte[] imageByteArray, int imageState){

        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(App.getContext());

        DataModel imageModel = new DataModel( imageId, imageByteArray, imageState);

        adapter.open();

        adapter.insert(imageModel);

        adapter.close();


    }

    //Проверяем есть ли картинка с нужным айдишником
    public int getImgId(int imageId){

        int anser;

        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(App.getContext());

        adapter.open();

        DataModel myModel = adapter.getData(imageId);

        adapter.close();

        anser = myModel.getImageId();

        Log.d(LOG_TAG, "ImageId " + anser);


        return anser;
    }

    //Получаем статус картинки по айдишнику
    public int getImgState(int imageId){

        int anser;

        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(App.getContext());

        adapter.open();

        DataModel myModel = adapter.getData(imageId);

        adapter.close();

        anser = myModel.getImageState();

        Log.d(LOG_TAG, "ImageId " + anser);


        return anser;
    }




    //Получаем список всех картинок из БД
    public List<DataModel> getByCountId(){


        List<DataModel> ourList;

        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(App.getContext());

        adapter.open();

        ourList = adapter.getDataByCountId();

        adapter.close();


        return ourList;
    }


    //Получаем картинку по ее айди
    public byte[] getImgBitmap(int imageId){


        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(App.getContext());

        adapter.open();

        DataModel myModel = adapter.getData(imageId);

        adapter.close();

			return myModel.getImageByteArray();
    }


    //Меняем статус изображения на неактивный
    public void delImage(int imageId){

        ImageDbAdapter adapter;
        adapter = new ImageDbAdapter(App.getContext());


        adapter.open();

        //adapter.delete(imageId);

        adapter.update(imageId);

        adapter.close();

    }



}
