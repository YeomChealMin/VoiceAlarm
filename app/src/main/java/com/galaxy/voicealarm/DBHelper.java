package com.galaxy.voicealarm;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{
    public DBHelper(Context context, String DBName){
        super(context, DBName, null, 1);
    }
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Schedule(_id integer primary key autoincrement, datetime double, content text)");
        db.execSQL("CREATE TABLE Alarm(_id integer primary key autoincrement, week integer, name text)");
    }
    public void onUpgrade(SQLiteDatabase db, int oloVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Schedule");
        db.execSQL("DROP TABLE IF EXISTS Alarm");
        onCreate(db);
    }
    public void query(String query) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(query);
    }
}
