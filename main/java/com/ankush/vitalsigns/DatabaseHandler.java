package com.ankush.vitalsigns;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ankush on 24-03-2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "signsValuesManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "signsValues";

    // Contacts Table Columns names
    private static final String KEY_startOfYear = "startOfYear";
    private static final String KEY_exercise = "exercise";
    private static final String KEY_attack = "attack";
    private static final String KEY_blueInhaler = "blueInhaler";
    private static final String KEY_brownInhaler = "brownInhaler";
    private static final String KEY_outside = "outside";
    private static final String KEY_wakeUp = "wakeUp";
    private static final String KEY_meal = "meal";
    private static final String KEY_feelm = "feelMeal";
    private static final String KEY_rr = "rr";
    private static final String KEY_hr = "hr";
    private static final String KEY_isAttack = "isAttack";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_startOfYear + " INTEGER PRIMARY KEY," +
                KEY_exercise + " INTEGER," +
                KEY_attack + " INTEGER," +
                KEY_blueInhaler + " INTEGER," +
                KEY_brownInhaler + " INTEGER," +
                KEY_outside + " INTEGER," +
                KEY_wakeUp + " INTEGER," +
                KEY_meal + " INTEGER," +
                KEY_feelm + " INTEGER," +
                KEY_rr + " INTEGER," +
                KEY_hr + " INTEGER," +
                KEY_isAttack +" INTEGER"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    public int countRows() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int ret = cursor.getCount();
        cursor.close();

        // return count
        return ret;
    }

    public void addRow(SignsValues sv) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_startOfYear, sv.m_startOfYear);
        values.put(KEY_exercise, sv.m_exercise);
        values.put(KEY_attack, sv.m_attack);
        values.put(KEY_blueInhaler, sv.m_blueInhaler);
        values.put(KEY_brownInhaler, sv.m_brownInhaler);
        values.put(KEY_outside, sv.m_outside);
        values.put(KEY_wakeUp, sv.m_wakeUp);
        values.put(KEY_meal, sv.m_meal);
        values.put(KEY_feelm, sv.m_feelMeal);
        values.put(KEY_rr, sv.m_rr);
        values.put(KEY_hr, sv.m_hr);
        values.put(KEY_isAttack, sv.m_isAttack);

        db.insert(TABLE_CONTACTS, null, values);
        db.close();

    }

    public List<SignsValues> getAllRows() {
        List<SignsValues> ret = new ArrayList<SignsValues>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SignsValues sv =
                        new SignsValues(
                                cursor.getInt(0),
                                cursor.getInt(1),
                                cursor.getInt(2),
                                cursor.getInt(3),
                                cursor.getInt(4),
                                cursor.getInt(5),
                                cursor.getInt(6),
                                cursor.getInt(7),
                                cursor.getInt(8),
                                cursor.getInt(9),
                                cursor.getInt(10),
                                cursor.getInt(11)
                        );
                ret.add(sv);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ret;
    }

    public SignsValues getLastRow() {
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        SignsValues sv;
        // looping through all rows and adding to list
        if (cursor.moveToLast()) {
                sv =
                        new SignsValues(
                                cursor.getInt(0),
                                cursor.getInt(1),
                                cursor.getInt(2),
                                cursor.getInt(3),
                                cursor.getInt(4),
                                cursor.getInt(5),
                                cursor.getInt(6),
                                cursor.getInt(7),
                                cursor.getInt(8),
                                cursor.getInt(9),
                                cursor.getInt(10),
                                cursor.getInt(11)
                        );
            cursor.close();
            return sv;
        } else {
            return new SignsValues(0,0,0,0,0,0,0,0,0,0,0,0);
        }

    }

    public void deleteAllContents() {
        SQLiteDatabase db = this.getWritableDatabase(); //get database
        db.execSQL("DELETE FROM " + TABLE_CONTACTS); //delete all rows in a table
        db.close();
    }
}
