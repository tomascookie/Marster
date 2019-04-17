package ru.brainix.ept.marster;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;

public class ImageFull extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full);

        //Загружаем картинку по айдишнику, отображаем pinch to zoom
        int imageId =  getIntent().getIntExtra("imageId", 0);

        MainModel model = new MainModel();
        Bitmap bmp = model.getImgBitmap(this, imageId);


        PhotoView photoView = findViewById(R.id.imageView);
        photoView.setImageBitmap(bmp);

    }


}
