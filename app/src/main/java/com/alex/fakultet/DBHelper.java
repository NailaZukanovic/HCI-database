package com.alex.fakultet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DBHelper extends SQLiteOpenHelper {

    public static final String db_name = "Faculty.db";


    public DBHelper(Context context) {
        super(context, db_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE table Profesor(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, full_name TEXT, field TEXT, semester TEXT)");
        MyDB.execSQL("CREATE table Predmet(id INTEGER PRIMARY KEY AUTOINCREMENT, subject TEXT, semester TEXT)");
        MyDB.execSQL("CREATE table Profesor_Predmet(id_professor INTEGER PRIMARY KEY AUTOINCREMENT, id_subject TEXT)");
    }

    public Boolean registerProfessor(String username, String password, String full_name, String field, String semester) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);
        values.put("full_name", full_name);
        values.put("field", field);
        values.put("semester", semester);
        long result = db.insert("Profesor", null, values);

        if (result == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    public void insertSubject(String subject_name, String semester) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("subject", subject_name);
        values.put("semester", semester);
        db.insert("Profesor", null, values);
        db.close();
    }

    public void isnertProfessorPredmet(String id_subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_subject", id_subject);
        db.insert("Profesor_Predmet", null,values);
        db.close();
    }

    public Cursor getData(String username) {
        SQLiteDatabase MyDB = this.getReadableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM Profesor WHERE username = ?", new String[] {username});
        return cursor;
    }


    public boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM Profesor WHERE username = ?", new String[] {username});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassword( String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM Profesor WHERE password = ? ", new String[] {password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS Profesor");
        MyDB.execSQL("DROP TABLE IF EXISTS Predmet");
        MyDB.execSQL("DROP TABLE IF EXISTS Profesor_Predmet");
        onCreate(MyDB);
    }
}
