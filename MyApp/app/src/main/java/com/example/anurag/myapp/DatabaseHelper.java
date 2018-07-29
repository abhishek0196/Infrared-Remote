package com.example.anurag.myapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String TAG = "DatabaseHelper";
    private static final String TABLENAME = "IRDevices";
    private static final String col1 = "brand";
    private static final String col2 = "product";
    private static final String col3 = "power";
    private static final String col4 = "mute";
    private static final String col5 = "volup";
    private static final String col6 = "voldown";
    private static final String col7 = "chup";
    private static final String col8 = "chdown";

    public DatabaseHelper(Context context) {
        super(context, TABLENAME , null , 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createtable = "create table if not exists IRDevices ( brand TEXT , product TEXT , power TEXT , mute TEXT , volup TEXT , voldown TEXT , chup TEXT , chdown TEXT);";
        db.execSQL(createtable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        SQLiteOpenHelper p;
    }

    public boolean addData(String brand , String product ,String power , String mute , String volup , String voldown , String chup , String chdown )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("brand",brand);
        cv.put("product",product);
        cv.put("power",power);
        cv.put("mute",mute);
        cv.put("volup",volup);
        cv.put("voldown",voldown);
        cv.put("chup",chup);
        cv.put("chdown",chdown);
        long result = db.insert(TABLENAME,null,cv);

        if( result == -1)
            return false;
        else
            return true;
    }

    public Cursor getData(String query)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(query,null);
        return data;

    }

    public void clear()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from IRDevices");
    }
}
