package com.example.android.bookstore2.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class BookContract {

        private BookContract() {}

        public static final String CONTENT_AUTHORITY = "com.example.android.bookstore";

        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

        public static final String PATH_BOOKS = "books";

        public static final class BookEntry implements BaseColumns {

            /**
             * Content URI to access book data in the content provider
             */
            public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

            /**
             * the MIME type of the CONTENT_URI for a list of books
             */
            public static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/"
                    + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

            /**
             * the MIME type of the CONTENT_URI for a single book
             */
            public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +
                    CONTENT_AUTHORITY + "/" + PATH_BOOKS;


        public static final String TABLE_NAME = "books";

        public static final String COLUMN_PRODUCT_NAME = "name";
        public static final String COLUMN_PRODUCT_PRICE = "price";
        public static final String COLUMN_PRODUCT_QUANTITY = "quantity";
        public static final String COLUMN_PRODUCT_SUPPLIER = "supplier";
        public static final String COLUMN_PRODUCT_PHONE = "phone";
    }
}