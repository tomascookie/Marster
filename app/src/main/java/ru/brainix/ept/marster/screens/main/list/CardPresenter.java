package ru.brainix.ept.marster.screens.main.list;

import java.util.List;
import ru.brainix.ept.marster.network.DataModel;

public class CardPresenter {

	private List<DataModel> repositories;
	private DataModel repo;

	public CardPresenter(List<DataModel> repositories){
		this.repositories=repositories;
	}


	public void onBindRepositoryRowViewAtPosition(int position, ICardView rowView) {
		repo = repositories.get(position);

		int imageId = repo.getImageId();

		rowView.setImage(repo.getImageByteArray());

		rowView.itemClick(imageId);
		rowView.itemLongClick(imageId);
	}

	public int getRepositoriesRowsCount() {
		return repositories.size();
	}


}
