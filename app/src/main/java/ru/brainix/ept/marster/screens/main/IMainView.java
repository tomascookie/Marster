package ru.brainix.ept.marster.screens.main;

import java.util.List;
import ru.brainix.ept.marster.network.DataModel;

interface IMainView {

	void setMainList(List<DataModel> list);

	void showStatusBar();

	void hideStatusBar();

	void showLoadingError();



}
