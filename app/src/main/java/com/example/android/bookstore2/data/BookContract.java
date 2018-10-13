package com.example.android.bookstore2.data;

import android.provider.BaseColumns;

public final class BookContract {

    public final class BookEntry implements BaseColumns {

        public static final String COLUMN_ID = BaseColumns._ID;

        public static final String TABLE_NAME = "books";
        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_SUPPLIER = "supplier";
        public static final String COLUMN_PRODUCT_PHONE = "phone";
    }
}