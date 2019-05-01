package ru.brainix.ept.marster.screens.main;

import java.util.List;
import ru.brainix.ept.marster.network.DataModel;
import ru.brainix.ept.marster.screens.main.MainModel.CompleteCallback;

interface IMainModel {

	 void getMainList(CompleteCallback callback);

}
