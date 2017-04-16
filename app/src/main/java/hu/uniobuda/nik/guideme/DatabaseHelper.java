package hu.uniobuda.nik.guideme;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tothb on 2017. 04. 16..
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    public static String DATABASE_NAME = "Test.db";
    public static String TABLE_NAME = "Monuments";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT UNIQUE);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void Insert(String name)
    {
        ContentValues cv = new ContentValues();
        cv.put("NAME",name);
        this.getWritableDatabase().insertOrThrow(TABLE_NAME,null,cv);
    }

    public void Delete(String name)
    {
        this.getWritableDatabase().delete(TABLE_NAME,"NAME='"+name+"'",null);
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
    public List<String> List()
    {
        List<String> list = new ArrayList<String>();
        Cursor cursor = this.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME,null);
        while (cursor.moveToNext())
        {
            list.add(cursor.getString(1));
        }
        if (list == null)
            list.add("asdasdasd");

        return list;
    }
}
