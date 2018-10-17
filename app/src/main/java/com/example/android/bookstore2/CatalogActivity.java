/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.bookstore2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.android.bookstore2.data.BookContract.BookEntry;
import com.example.android.bookstore2.data.BookDbHelper;

public class CatalogActivity extends AppCompatActivity {


    private BookDbHelper mDbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
    }




    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the bookstore database.
     */
    private void displayDatabaseInfo() {

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                BookEntry._ID,
                BookEntry.COLUMN_PRODUCT_NAME,
                BookEntry.COLUMN_PRODUCT_PRICE,
                BookEntry.COLUMN_PRODUCT_QUANTITY,
                BookEntry.COLUMN_PRODUCT_SUPPLIER,
                BookEntry.COLUMN_PRODUCT_PHONE};

        //Perform query on the items database table
        Cursor cursor = db.query(
                BookEntry.TABLE_NAME,      //The table to query
                projection,                 //The Columns to return
                null,                       //The columns for the WHERE clause
                null,                       //The values for the WHERE clause
                null,                       //Don't group the rows
                null,                        //Don't filter by row groups
                null);

        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            displayView.setText(getString(R.string.row_initemdb) + "pets.\n\n");
            displayView.append(BookEntry._ID + " - " +
                    BookEntry.COLUMN_PRODUCT_NAME + " - " +
                    BookEntry.COLUMN_PRODUCT_PRICE + " - " +
                    BookEntry.COLUMN_PRODUCT_QUANTITY + " - " +
                    BookEntry.COLUMN_PRODUCT_SUPPLIER + " - " +
                    BookEntry.COLUMN_PRODUCT_PHONE + " - " + "\n");

            //Find/Bind index of each column.
            int idColumnIndex = cursor.getColumnIndex(BookEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
            int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_QUANTITY);
            int supplierColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_SUPPLIER);
            int phoneColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_PHONE);

            //Iterate thru all returned rows in cursor.
            while (cursor.moveToNext()) {
                //Use that index to extract string or Int value of the word @ current row
                //cursor is on.
                int currentId = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplier = cursor.getString(supplierColumnIndex);
                String currentPhone = cursor.getString(phoneColumnIndex);

                //Display the values from each respective current column of the current row in the
                // cursor in the TextView.
                displayView.append((currentId + " - " +
                        currentName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplier + " - " +
                        currentPhone));
            }
        } finally {
            //Always close the cursor when done reading from it. This releases all it's resources
            //and makes it invalid.
            cursor.close();
        }
    }

    /**
     * Helper method to insert hardcoded item data into database, for debugging purposes only.
     */
    private void insertItem() {
        //Gets database into the write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create a ContentValues object where column names are the keys, and misc schwag's item
        //attributes are it's values.
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_PRODUCT_NAME, "Superman #1");
        values.put(BookEntry.COLUMN_PRODUCT_PRICE, 5555);
        values.put(BookEntry.COLUMN_PRODUCT_QUANTITY, 2);
        values.put(BookEntry.COLUMN_PRODUCT_SUPPLIER, "B&N");
        values.put(BookEntry.COLUMN_PRODUCT_PHONE, 1555555555);

        //Insert the values to the database
        long rowsInserted = db.insert(BookEntry.TABLE_NAME, null, values);
        if (rowsInserted == -1) {
            Log.d("CatalogActivity", "Problem inserting data ...");
        } else{
            Log.d("CatalogActivity", rowsInserted + " rows inserted successfully...");
        }

        /**
         * Insert new row for Superman #1 in the database, returning the id of that new row.
         * The first arg for {@link db.insert()} is the items table name.
         * The 2nd arg provides the column name in which the framework can insert NULL in the
         * event the ContentView is empty. (If this is set NULL, the framework will not insert
         * a new row when there are no values.
         * The 3rd argument is the ContentValues object containing Superman #1's information.
         */
        db.insert(BookEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate menu options in the app bar overflow menu
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        //Add menu items to app bar
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //User selected menu option in app bar overflow menu
        switch (item.getItemId()) {
            //Respond to "Insert Dummy Data" menu item selection
            case R.id.action_insert_dummy_data:
                insertItem();
                displayDatabaseInfo();
                return true;
            //Respond to "Delete ALL Database Entries" menu item selection
            case R.id.action_delete_all_entries:
                //Do nothing for this stage. Will call to yet-to-be created delete method.
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

