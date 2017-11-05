package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

import model.MyWish;

/**
 * Created by korgua on 2017. 11. 02..
 */

public class DatabaseHandler extends SQLiteOpenHelper{

    private final ArrayList<MyWish> wishList = new ArrayList<>();


    public DatabaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create out table
        String CREATE_WISHES_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "(" +
                Constants.KEY_ID + " INTEGER PRIMARY KEY, " +
                Constants.TITLE_NAME + " TEXT, " +
                Constants.CONTENT_NAME + " TEXT, " +
                Constants.DATE_NAME + " LONG " +
                ");";
        sqLiteDatabase.execSQL(CREATE_WISHES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        //create a new one
        onCreate(sqLiteDatabase);

    }

    public void deleteWish(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID + " = ? ", new String[]{String.valueOf(id)});
        db.close();
    }

    public void addWishes(MyWish wish){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Constants.TITLE_NAME,wish.getTitle());
        contentValues.put(Constants.CONTENT_NAME,wish.getContent());
        contentValues.put(Constants.DATE_NAME,java.lang.System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME,null,contentValues);
        Log.v("addWishes","Successfully");
        db.close();
    }

    public ArrayList<MyWish> getWishes(){
        String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME + ";";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,new String[]{
                Constants.KEY_ID,
                Constants.TITLE_NAME,
                Constants.CONTENT_NAME,
                Constants.DATE_NAME
                },null,null,null,null,Constants.DATE_NAME+" DESC");
        //loop through cursor
        if (cursor.moveToFirst()) {
            do{
                MyWish wish = new MyWish();
                wish.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                wish.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));
                wish.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));
                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                String dateData = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());
                wish.setRecordDate(dateData);
                wishList.add(wish);
            }
            while(cursor.moveToNext());
        }

        return wishList;
    }

}
