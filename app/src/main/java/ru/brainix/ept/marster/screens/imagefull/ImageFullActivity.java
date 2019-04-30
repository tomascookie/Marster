package ru.brainix.ept.marster.screens.imagefull;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
import ru.brainix.ept.marster.R;

public class ImageFullActivity extends AppCompatActivity implements IViewImageFull {

	private ImageFullPresenter presenter;

	@Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_image_full);

       if(presenter==null){

        	presenter = new ImageFullPresenter();

        }

        presenter.onAttachActivity(this);

        presenter.onActivityCreate();





    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		presenter.onDeattachActivity();
	}

	@Override
  public void setImage(byte[] bitmapArray) {


			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			options.inSampleSize = 2;

			Bitmap bmp = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);

			PhotoView photoView = findViewById(R.id.imageView);
			photoView.setImageBitmap(bmp);

    }

  @Override
  public int getImageId() {

		return getIntent().getIntExtra("imageId", 0);

	}


}
