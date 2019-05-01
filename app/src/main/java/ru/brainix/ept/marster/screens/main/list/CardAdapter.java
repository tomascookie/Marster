package ru.brainix.ept.marster.screens.main.list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import ru.brainix.ept.marster.screens.imagefull.ImageFullActivity;
import ru.brainix.ept.marster.screens.main.MainActivity;
import ru.brainix.ept.marster.screens.main.MainModel;
import ru.brainix.ept.marster.R;
import ru.brainix.ept.marster.network.DataModel;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private final String LOG_TAG = "CardAdapter ";


    private LayoutInflater inflater;
    private List<DataModel> cards;

    Bitmap bit;

    Context context;

    public CardAdapter(Context context, List<DataModel> cards) {
        this.cards = cards;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = inflater.inflate(R.layout.item_list , viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CardAdapter.ViewHolder viewHolder, int i) {

        final DataModel dataModel = cards.get(i);
        byte[] image = dataModel.getImageByteArray();
        bit = BitmapFactory.decodeByteArray(image, 0, image.length);

        viewHolder.marsPic.setImageBitmap(bit);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Запускаем full_view, передаем в него айди картинки
                MainModel model = new MainModel();

                int imgId = model.getImgId(dataModel.getImageId());

                Intent intent = new Intent(v.getContext(), ImageFullActivity.class);
                intent.putExtra("imageId", imgId);
                v.getContext().startActivity(intent);

            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //Запускаем диалоговое окно подтв удаления, передаем айди
                MainModel model = new MainModel();


                int imgId = model.getImgId(dataModel.getImageId());


                //((MainActivity)v.getContext()).getDialogFragment(imgId);

							DialogFragment dialog = new DialogFragment();

							Bundle args = new Bundle();
							args.putInt("image_id", imgId);
							dialog.setArguments(args);

							dialog.show(((MainActivity)v.getContext()).getSupportFragmentManager(), "custom");

                return false;

            }
        });


    }


    @Override
    public int getItemCount() {
        return cards.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        final ImageView marsPic;

        ViewHolder(final View view){
            super(view);
            marsPic =  view.findViewById(R.id.imageView);
          /*  marsPic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
/*
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bit.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    Intent in1 = new Intent(v.getContext(), ImageFullActivity.class);
                    v.getContext().startActivity(in1);
                }
            });*/
        }
    }
}
