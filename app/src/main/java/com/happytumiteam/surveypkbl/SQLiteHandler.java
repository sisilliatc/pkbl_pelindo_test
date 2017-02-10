package com.happytumiteam.surveypkbl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by Sisillia on 2/8/2017.
 */

public class SQLiteHandler extends SQLiteOpenHelper {
    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "db_pkbl";

    // Login table name
    private static final String TABLE_USER = "user";

    // Data Mitra table name
    private static final String TABLE_DATA_MITRA = "data_mitra";

    // Login Table Columns names
    private static final String KEY_ID = "id_pegawai";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_LOKASI = "lokasi_kerja";

    // Data Mitra Table Columns names
    private static final String KEY_NO_REGISTRASI = "no_registrasi";
    private static final String KEY_NO_PROPOSAL = "no_proposal";
    private static final String KEY_TANGGAL_KUNJUNGAN = "tanggal_kunjungan";
    private static final String KEY_NAMA_MITRA = "nama_mitra";
    private static final String KEY_BIDANG_USAHA = "bidang_usaha";
    private static final String KEY_ALAMAT = "alamat";
    private static final String KEY_BERTEMU_DENGAN = "bertemu_dengan";
    private static final String KEY_KEAKURATAN_DATA = "keakuratan_data_proposal";
    private static final String KEY_KONDISI_JAMINAN = "kondisi_jaminan";
    private static final String KEY_KONDISI_USAHA = "kondisi_usaha";
    private static final String KEY_KONDISI_KEUANGAN = "kondisi_keuangan";
    private static final String KEY_LAINNYA = "lainnya";
    private static final String KEY_RENCANA_SELANJUTNYA = "rencana_selanjutnya";

    private static final String CREATE_DATA_MITRA_TABLE = "CREATE TABLE " + TABLE_DATA_MITRA + "("
            + KEY_NO_REGISTRASI + " TEXT PRIMARY KEY," + KEY_NO_PROPOSAL + " TEXT UNIQUE,"
            + KEY_TANGGAL_KUNJUNGAN + " TEXT," + KEY_NAMA_MITRA + " TEXT," + KEY_BIDANG_USAHA + " TEXT,"
            + KEY_ALAMAT + " TEXT," + KEY_BERTEMU_DENGAN + " TEXT," + KEY_KEAKURATAN_DATA + " TEXT,"
            + KEY_KONDISI_JAMINAN + " TEXT," + KEY_KONDISI_USAHA + " TEXT," + KEY_KONDISI_KEUANGAN + " TEXT,"
            + KEY_LAINNYA + " TEXT," + KEY_RENCANA_SELANJUTNYA + " TEXT" + ")";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DATA_MITRA_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA_MITRA);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String name, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_EMAIL, email); // Email

        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}

