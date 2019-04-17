package ru.brainix.ept.marster.database;

import android.provider.BaseColumns;

public final class ImageBaseContract {

    private ImageBaseContract(){}

    public static final class ImageParamName implements BaseColumns {

        public final static String TABLE_NAME    = "imageStorage";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_IMAGE_ID  = "imageId";
        public final static String COLUMN_IMAGE_BITMAP  = "image";
        public final static String COLUMN_STATE  = "state";


    }
}