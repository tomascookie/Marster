package ru.brainix.ept.marster.screens.main;

import java.util.List;

import ru.brainix.ept.marster.network.DataModel;
import ru.brainix.ept.marster.screens.main.MainModel.CompleteCallback;

public class MainPresenter implements IMainPresenter {

    //private final String LOG_TAG = "MainPresenter ";

    private MainModel mainModel;

    private MainActivity mainView;



    @Override
    public void onActivityCreate() {

			mainView.showStatusBar();


			mainModel = new MainModel();


			mainModel.getMainList(new CompleteCallback() {
				@Override
				public void onComplete(List<DataModel> dataModels) {
					mainView.hideStatusBar();

					if (dataModels == null) {

						mainView.showLoadingError();

					} else {

						mainView.setMainList(dataModels);
					}

				}
			});






    }

		@Override
		public void onAttachActivity(MainActivity main) {

			mainView = main;

		}

		@Override
		public void onDeattachActivity() {

			mainView = null;

		}


	/*	//Получаем сформированный список
		private List<DataModel> getList(){

			if(mainModel==null){

				mainModel = new MainModel();

			}

			return mainModel.getMainList();

		}
*/



}
