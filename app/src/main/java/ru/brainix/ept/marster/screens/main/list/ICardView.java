package ru.brainix.ept.marster.screens.main.list;

import android.content.Context;

public interface ICardView {

	void setImage(byte[] imageArray);

	void itemClick(final int imageId);

	void itemLongClick(final int imageId);

}
