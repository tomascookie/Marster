package ru.brainix.ept.marster.screens.main.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import ru.brainix.ept.marster.App;
import ru.brainix.ept.marster.R;
import ru.brainix.ept.marster.screens.imagefull.ImageFullActivity;
import ru.brainix.ept.marster.screens.main.MainActivity;

public class CardViewHolder extends RecyclerView.ViewHolder implements ICardView{


	private ImageView marsPic;

	CardViewHolder(final View view){
		super(view);

		marsPic =  view.findViewById(R.id.imageView);

	}

	@Override
	public void setImage(byte[] imageArray) {
		marsPic.setImageBitmap(BitmapFactory.decodeByteArray(imageArray, 0, imageArray.length));
	}

	@Override
	public void itemClick(final int imageId) {

		itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent(App.getContext(), ImageFullActivity.class);
				intent.putExtra("imageId", imageId);
				v.getContext().startActivity(intent);
			}
		});


	}

	@Override
	public void itemLongClick(final int imageId) {

		itemView.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				DialogFragment dialog = new DialogFragment();

				Bundle args = new Bundle();
				args.putInt("image_id", imageId);
				dialog.setArguments(args);

				dialog.show(((MainActivity)v.getContext()).getSupportFragmentManager(), "custom");

				return false;
			}
		});


	}
}
