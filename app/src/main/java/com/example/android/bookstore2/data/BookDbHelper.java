package com.example.android.bookstore2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.bookstore2.data.BookContract.BookEntry;


/**
 * Database helper for Pets app. Manages database creation and version management.
 */

public class BookDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = BookDbHelper.class.getSimpleName();

    // Name of the database file
    private static final String DATABASE_NAME = "books.db";

    // Database version. If you change the database schema, you must increment the version number.
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public BookDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // This is called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        // Create a string that contains the SQL statement to create the items table
        final String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " + BookEntry.TABLE_NAME + " ("
                + BookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BookEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + BookEntry.COLUMN_PRODUCT_PRICE + " INTEGER NOT NULL DEFAULT 0, "
                + BookEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER NOT NULL DEFAULT 0, "
                + BookEntry.COLUMN_PRODUCT_SUPPLIER + " TEXT NOT NULL, "
                + BookEntry.COLUMN_PRODUCT_PHONE + " TEXT NOT NULL );";

        Log.v("Database", SQL_CREATE_ITEMS_TABLE);


        //Execute the SQL statement
        db.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    // This is called when the database needs to be upgraded.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            // The database is still at version one, so there's nothing to be done here.
        }
    }
}

