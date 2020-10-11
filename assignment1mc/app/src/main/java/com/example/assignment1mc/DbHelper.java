package com.example.assignment1mc;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



public class DbHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "symptoms_db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SYMPTOMS = "symptoms";
    private static final String KEY_ID = "id";
    private static final String Fever = "fever";
    private static final String Nausea = "nausea";
    private static final String Headache = "headache";
    private static final String Cough= "cough";
    private static final String Sour_Throat = "sour_throat";
    private static final String DIARRHEA = "diarrhea";
    private static final String MUSCLE_ACHE = "muscle_ache";
    private static final String SMELL_TASTE = "smell_taste";
    private static final String BREATH = "breath";
    private static final String TIRED = "tired";
    private static final String HR = "heart_rate";
    private static final String RR = "respiratory_rate";




    private static final String CREATE_TABLE_SYMPTOMS = "CREATE TABLE " + TABLE_SYMPTOMS
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + HR +" int , "
            + RR + " int , "
            + Fever + " int , "
            + Nausea + " int , "
            + Headache + " int , "
            + Cough + " int , "
            + Sour_Throat + " int , "
            + DIARRHEA + " int , "
            + MUSCLE_ACHE + " int , "
            + SMELL_TASTE + " int , "
            + BREATH + " int , "
            + TIRED + "int );";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_SYMPTOMS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SYMPTOMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SYMPTOMS + "'");
        onCreate(db);
    }
    public long addHR(int hr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HR, hr);
        return db.insert(TABLE_SYMPTOMS, null, values);

    }
    public long addRR(int rr){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RR, rr);
//        long insert= db.insert(TABLE_SYMPTOMS, null, values);
        long update= db.update(TABLE_SYMPTOMS, values,"id = (SELECT MAX(ID) from symptoms)",null);
        return update;

    }

    public long addRatingDetail(int fever, int headache, int nausea, int sour_throat, int cough, int diarrhea, int muscle_ache, int smell_taste, int breath) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Creating content values
        ContentValues values = new ContentValues();
        values.put(Fever, fever);
        values.put(Headache, headache);
        values.put(Nausea, nausea);
        values.put(Cough, cough);
        values.put(Sour_Throat, sour_throat);
        values.put(DIARRHEA, diarrhea);
        values.put(MUSCLE_ACHE, muscle_ache);
        values.put(SMELL_TASTE, smell_taste);
        values.put(BREATH, breath);
//        values.put(TIRED, tired);
        // insert row in students table
//        long insert = db.insert(TABLE_SYMPTOMS, null, values);
//
//        return insert;
        return db.update(TABLE_SYMPTOMS, values,"id = (SELECT MAX(ID) from symptoms)",null);

    }


}
