package ru.brainix.ept.marster.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ru.brainix.ept.marster.database.ImageBaseContract.ImageParamName;



public class ImageDbHelper extends SQLiteOpenHelper {


    private Context context;

    private final String LOG_TAG = "DbHelper ";

    private static final int databaseVersion = 1;
    private static final String databaseName = "dbImage.db";





    public ImageDbHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_IMAGE_TABLE = "CREATE TABLE "

                + ImageParamName.TABLE_NAME + "("
                + ImageParamName._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ImageParamName.COLUMN_IMAGE_ID + " INTEGER NOT NULL DEFAULT 0,"
                + ImageParamName.COLUMN_STATE + " INTEGER NOT NULL DEFAULT 0,"
                + ImageParamName.COLUMN_IMAGE_BITMAP + " TEXT )";

        sqLiteDatabase.execSQL(CREATE_IMAGE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ImageParamName.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }



}