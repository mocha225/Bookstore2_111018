package com.example.android.bookstore2;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstore2.data.BookContract;
import com.example.android.bookstore2.data.BookContract.BookEntry;


public class BookCursorAdapter extends CursorAdapter {

    public static final String LOG_TAG = BookContract.class.getSimpleName();



    /**
     * Constructs a new {@link BookCursorAdapter}.
     *
     * @param context The context
     * @param c       The cursor from which to get the data.
     */
    public BookCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);

    }

    /**
     * Makes a new blank list item view. No data is set (or bound) to the views yet.
     *
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already
     *                moved to the correct position.
     * @param parent  The parent to which the new view is attached to
     * @return the newly created list item view.
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate a list item view using the layout specified in list_item.xml
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false); }

    /**
     * @param view    Existing view, returned earlier by newView() method
     * @param context app context
     * @param cursor  The cursor from which to get the data. The cursor is already moved to the
     *                correct row.
     */
    @Override
    public void bindView(final View view, final Context context, Cursor cursor) {
        //Find the individual views that we want to modify in the list item layout.
        TextView productNameTextView = view.findViewById(R.id.product_name_textview);
        TextView priceTextView = view.findViewById(R.id.product_price_textview);
        final TextView quantityTextView = view.findViewById(R.id.product_quantity_textview);

        // Find the columns of item attributes that we're interested in
        int nameColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(BookEntry.COLUMN_PRODUCT_QUANTITY);

        // Read the item attributes from the Cursor for the current item
        String currentProdName = cursor.getString(nameColumnIndex);
        Double currentPrice = cursor.getDouble(priceColumnIndex);
        final int currentQuantity = cursor.getInt(quantityColumnIndex);
        final String quantityString = Integer.toString(currentQuantity);

        //convert price to string so it can be displayed
        String priceString = Double.toString(currentPrice);

        //Update the TextViews with the attributes for the current Books
        productNameTextView.setText(currentProdName);
        priceTextView.setText(priceString);
        quantityTextView.setText(quantityString);

        final int currentBookRowId = cursor.getInt(cursor.getColumnIndex(BaseColumns._ID));

        Button saleButton = view.findViewById(R.id.order_button);
        saleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updatedQuantity = Integer.parseInt(quantityString);
                if (updatedQuantity < 0) {
                    updatedQuantity = updatedQuantity -1;

                    ContentValues values = new ContentValues();
                    values.put(BookEntry.COLUMN_PRODUCT_QUANTITY, updatedQuantity);

                    Uri newUri = ContentUris.withAppendedId(BookEntry.CONTENT_URI, currentBookRowId);
                    context.getContentResolver().update(newUri, values, null, null);

                    Toast toast = Toast.makeText(view.getContext(), R.string.negative_quantity,Toast.LENGTH_LONG);
                    toast.show();
                }

            }

        });


    }

}
