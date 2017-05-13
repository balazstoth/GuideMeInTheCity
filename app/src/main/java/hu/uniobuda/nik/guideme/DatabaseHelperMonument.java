package hu.uniobuda.nik.guideme;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT UNIQUE, DESC TEXT, DATE TEXT, CATEGORY TEXT, POINTS TEXT, VOTES TEXT, ISENABLED TEXT, PICTURE BLOB, LATITUDE FLOAT, LONGITUDE FLOAT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void Insert(String name,String description, String date, String category, String points, String votes, String isEnabled, byte[] picture, double latitude, double longitude)
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
        cv.put("PICTURE",picture);

        this.getWritableDatabase().insertOrThrow(TABLE_NAME,null,cv);
    }

    public void RefreshRating(String name, float newPoints, int newVotes)
    {
        this.getWritableDatabase().execSQL("UPDATE " + TABLE_NAME + " SET POINTS = '" + newPoints + "', VOTES = '" + newVotes + "' WHERE NAME LIKE '" + name + "'");
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
        this.getWritableDatabase().execSQL("CREATE TABLE " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT UNIQUE, DESC TEXT, DATE TEXT, CATEGORY TEXT, POINTS TEXT, VOTES TEXT, ISENABLED TEXT, PICTURE BLOB, LATITUDE FLOAT, LONGITUDE FLOAT);");
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
            list.add(new Monument(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7),BitmapConvert.fromBytesToImage(cursor.getBlob(8)), cursor.getDouble(9), cursor.getDouble(10)));
        }
     Collections.sort(list);
        return list;
    }

    public List<Monument> OrderByLocation(String category, Location actLocation)
    {
        List<Monument> list = new ArrayList<Monument>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE CATEGORY = '" + category + "' AND ISENABLED != 'false'" ,null);
        while (cursor.moveToNext())
        {
            list.add(new Monument(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6),cursor.getString(7),BitmapConvert.fromBytesToImage(cursor.getBlob(8)), cursor.getDouble(9), cursor.getDouble(10)));
        }
        Collections.sort(list);
        Monument temp;
        for(int i =0; i < list.size()-1; i++){
            for(int j = i+1; j < list.size(); j++){
                if(distance(list.get(i).getLatitude(), list.get(i).getLongitude(), actLocation.getLatitude(), actLocation.getLongitude()) <
                        distance(list.get(j).getLatitude(), list.get(j).getLongitude(), actLocation.getLatitude(), actLocation.getLongitude())){
                    temp = list.get(j);
                    //list.get(j).se = list.get(i);
                    list.set(j, list.get(i));
                    list.set(i, temp);
                }
            }
        }
        return list;
    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
