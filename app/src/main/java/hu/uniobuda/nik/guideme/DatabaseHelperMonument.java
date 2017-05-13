package hu.uniobuda.nik.guideme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.uniobuda.nik.guideme.Models.Monument;

/**
 * Created by tothb on 2017. 04. 16..
 */

public class DatabaseHelperMonument extends SQLiteOpenHelper
{
    public static String DATABASE_NAME = "Test.db";
    public static String TABLE_NAME = "Monuments";

    public DatabaseHelperMonument(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
        //DeleteTable();
        //CreateTable();
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT UNIQUE, DESC TEXT, DATE TEXT, CATEGORY TEXT," +
                " POINTS TEXT , VOTES TEXT, ISENABLED TEXT, LATITUDE FLOAT, LONGITUDE FLOAT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void Insert(String name,String description, String date, String category, String points, String votes, String isEnabled, double latitude, double longitude)
    {
        ContentValues cv = new ContentValues();
        cv.put("NAME",name);
        cv.put("DESC",description);
        cv.put("DATE",date);
        cv.put("CATEGORY",category);
        cv.put("POINTS",points);
        cv.put("VOTES",votes);
        cv.put("ISENABLED",isEnabled);
        cv.put("LATITUDE",latitude);
        cv.put("LONGITUDE",longitude);

        this.getWritableDatabase().insertOrThrow(TABLE_NAME,null,cv);
    }

    public void Delete(String name)
    {
        this.getWritableDatabase().delete(TABLE_NAME,"NAME='"+name+"'",null);
    }

    public void DeleteTable()
    {
        this.getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public void CreateTable()
    {
        this.getWritableDatabase().execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT UNIQUE, DESC TEXT, DATE TEXT, CATEGORY TEXT, POINTS TEXT, VOTES TEXT, ISENABLED TEXT, LATITUDE FLOAT, LONGITUDE FLOAT);");
    }

    public void Update(String oldName, String newName)
    {
        this.getWritableDatabase().execSQL("UPDATE " + TABLE_NAME + "SET NAME='" + newName + "' WHERE NAME='" + oldName + "'");
    }

    public int Count()
    {
        int c = 0;
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME,null);
        while (cursor.moveToNext())
        {
            c++;
        }
        return c;
    }
    public List<Monument> List(String category)
    {
        List<Monument> list = new ArrayList<Monument>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE CATEGORY = '" + category + "' AND ISENABLED != 'false'" ,null);
        while (cursor.moveToNext())
        {
            list.add(new Monument(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getInt(5), cursor.getInt(6),cursor.getString(7), cursor.getDouble(8), cursor.getDouble(9)));
        }

        Collections.sort(list);
        return list;
    }
}
