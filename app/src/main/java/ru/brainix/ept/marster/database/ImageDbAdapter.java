package ru.brainix.ept.marster.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ru.brainix.ept.marster.data.getting.DataModel;
import ru.brainix.ept.marster.database.ImageBaseContract.ImageParamName;

public class ImageDbAdapter {

    private final String LOG_TAG = "ImageDbAdapter ";


    private ImageDbHelper dbHelper;
    private SQLiteDatabase database;

    //Устанавливаем дб-хелпер в адаптере
    public ImageDbAdapter(Context context){
        dbHelper = new ImageDbHelper(context.getApplicationContext());
    }

    //Устанавливаем хелпер на запись/чтение
    public ImageDbAdapter open(){
        database = dbHelper.getWritableDatabase();
        return this;
    }

    //Закрываем хелпер
    public void close(){
        dbHelper.close();
    }




    //Добавляем элемент в БД
    public long insert(DataModel model){

        ContentValues cv = new ContentValues();
        cv.put(ImageParamName.COLUMN_IMAGE_ID,     model.getImageId());
        cv.put(ImageParamName.COLUMN_STATE,        model.getImageState());
        cv.put(ImageParamName.COLUMN_IMAGE_BITMAP, model.getImageByteArray());

        return  database.insert(ImageParamName.TABLE_NAME, null, cv);
    }


    //неиспользуемый Удаляем элемент из БД
    public long delete(int imageId){

        String whereClause = ImageParamName.COLUMN_IMAGE_ID +" = ?";
        String[] whereArgs = new String[]{String.valueOf(imageId)};

        return database.delete(ImageParamName.TABLE_NAME, whereClause, whereArgs);
    }


    //Меняем статус изображения на неактивный
    public long update(int imageId){

        String whereClause = ImageParamName.COLUMN_IMAGE_ID + "=" + imageId;
        ContentValues cv = new ContentValues();

        cv.put(ImageParamName.COLUMN_STATE, 1);

        return database.update(ImageParamName.TABLE_NAME, cv, whereClause, null);

    }




    //Получаем поле по imageId
    public DataModel getData(int imageId){

        DataModel user = new DataModel(-1, null, -1);

        String query = String.format("SELECT * FROM %s WHERE %s=?", ImageParamName.TABLE_NAME, ImageParamName.COLUMN_IMAGE_ID);
        Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(imageId) });

        if(cursor.moveToFirst()){

            byte[] bitImage = cursor.getBlob(cursor.getColumnIndex(ImageParamName.COLUMN_IMAGE_BITMAP));
            int state = cursor.getInt(cursor.getColumnIndex(ImageParamName.COLUMN_STATE));

            if(bitImage==null){ return user; }

            else{

                user = new DataModel(imageId, bitImage, state);

            }
        }
        cursor.close();



        return  user;
    }


    //Получаем все данные из бд сразу в лист
    public List<DataModel> getDataByCountId(){

        List<DataModel> list = new ArrayList<>();

        Cursor  cursor = database.rawQuery("select * from "+ ImageParamName.TABLE_NAME,null);


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                int state = cursor.getInt(cursor.getColumnIndex(ImageParamName.COLUMN_STATE));



                byte[] bitImage = cursor.getBlob(cursor.getColumnIndex(ImageParamName.COLUMN_IMAGE_BITMAP));
                int imageId = cursor.getInt(cursor.getColumnIndex(ImageParamName.COLUMN_IMAGE_ID));

                list.add(new DataModel(imageId, bitImage, state));

                cursor.moveToNext();
            }
        }


        cursor.close();
        return  list;



    }




}
