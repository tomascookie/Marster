package ru.brainix.ept.marster.screens.main.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import ru.brainix.ept.marster.R;

public class CardAdapter  extends RecyclerView.Adapter<CardViewHolder> {

	private final CardPresenter presenter;

	public CardAdapter(CardPresenter repositoriesPresenter) {
		this.presenter = repositoriesPresenter;
	}

	@Override
	public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		return new CardViewHolder(LayoutInflater.from(parent.getContext())
				.inflate(R.layout.item_list, parent, false));
	}

	@Override
	public void onBindViewHolder(CardViewHolder holder, int position) {
		presenter.onBindRepositoryRowViewAtPosition(position, holder);



	}

	@Override
	public int getItemCount() {
		return presenter.getRepositoriesRowsCount();
	}
}