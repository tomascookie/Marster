package ru.brainix.ept.marster.network;


public class DataModel {

    private int    imageId;
    private byte[] imageByteArray;
    private int    imageState;


    public DataModel(int imageId, byte[] imageByteArray, int imageState){

        this.imageId=imageId;
        this.imageByteArray=imageByteArray;
        this.imageState = imageState;

    }


    public int getImageId() {
        return imageId;
    }


    public byte[] getImageByteArray() {
        return imageByteArray;
    }


    public int getImageState() {
        return imageState;
    }
}
