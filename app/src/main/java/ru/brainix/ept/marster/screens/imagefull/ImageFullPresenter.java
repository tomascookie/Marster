package ru.brainix.ept.marster.screens.imagefull;

import ru.brainix.ept.marster.screens.main.MainModel;

public class ImageFullPresenter implements IPresenterImageFull {

	private ImageFullActivity mainView;


	@Override
	public void onActivityCreate() {

		int imageId = mainView.getImageId();

		MainModel mainModel = new MainModel();
		byte[] bitmapArray = mainModel.getImgBitmap(imageId);

		mainView.setImage(bitmapArray);

	}


	@Override
	public void onAttachActivity(ImageFullActivity mainView) {
		this.mainView = mainView;

	}


	@Override
	public void onDeattachActivity() {

		mainView = null;

	}

}
