package hu.uniobuda.nik.guideme;

import android.app.Activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Tamás on 2017. 04. 22..
 */

public class DatabaseHelperUser extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    SQLiteDatabase db;

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID + " integer primary key not null, "
            + COLUMN_EMAIL + " text not null, " + COLUMN_USERNAME + " text not null, " + COLUMN_PASSWORD + " text not null ) ";

    public DatabaseHelperUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public void insertUser(User u) {
        ContentValues values = new ContentValues();


        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int counter = cursor.getCount();
        values.put(COLUMN_ID, counter);
        values.put(COLUMN_EMAIL, u.getEmail());
        values.put(COLUMN_USERNAME, u.getUsername());
        values.put(COLUMN_PASSWORD, u.getPassword());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public String Search(String usernName) {
        db = this.getReadableDatabase();
        String query = "select " + COLUMN_USERNAME + ", " + COLUMN_PASSWORD + " from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a, b = "";
        if (cursor.moveToFirst()) {
            do {
                a = cursor.getString(0);
                b = cursor.getString(1);
                if (a.equals(usernName)) {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }

        return b;
    }

    public int CheckUserName(String usernName) {
        db = this.getWritableDatabase();

        String query = "select * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);

        if (!cursor.moveToFirst()) {
            return 1;
        } else if (!cursor.getString(2).equals(usernName))
            return 1;
        else
            return 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }
}
