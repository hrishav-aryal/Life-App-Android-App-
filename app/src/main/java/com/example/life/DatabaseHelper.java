package com.example.life;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "lifeDatabase";
    private static final String col1 = "id";
    private static final String col2 = "year";
    private static final String col3 = "month";
    private static final String col4 = "day";
    private static final String col5 = "expected_death_age";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "create table " + TABLE_NAME + " (" +
                col1 + " integer primary key autoincrement, " +
                col2 + " integer, " + col3 + " integer, " + col4 + " integer, " + col5 + " integer)";
        sqLiteDatabase.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //sqLiteDatabase.execSQL("DROP if TABLE EXISTS " + TABLE_NAME);
        //onCreate(sqLiteDatabase);
    }

    public boolean addData(int year, int month, int day, int age){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2, year);
        contentValues.put(col3, month);
        contentValues.put(col4, day);
        contentValues.put(col5, age);

        long check = db.insert(TABLE_NAME, null, contentValues);

        if(check == -1) return false;
        else return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);


        return data;
    }
}
