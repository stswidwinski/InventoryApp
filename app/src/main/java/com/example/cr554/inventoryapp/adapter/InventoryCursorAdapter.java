package com.example.cr554.inventoryapp.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.cr554.inventoryapp.R;
import com.example.cr554.inventoryapp.database.InventoryContract;

/**
 * Created by cr554 on 2/24/2017.
 * A custom Cursor Adapter for the Inventory Database.
 * The Cursor adapter takes info from a Cursor and displays it to a list view.
 */

public class InventoryCursorAdapter extends CursorAdapter {
    public InventoryCursorAdapter(Context context, Cursor cursor){
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_view_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        //find views to update
        TextView nameTextView = (TextView) view.findViewById(R.id.product_name);
        TextView supplierTextView = (TextView) view.findViewById(R.id.product_supplier);
        TextView priceTextView = (TextView) view.findViewById(R.id.product_price);
        TextView quantityTextView = (TextView) view.findViewById(R.id.product_quantity);

        //find the columns in the cursor
        int nameColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_NAME);
        int supplierColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_SUPPLIER);
        int priceColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY);

        //extract the values from the cursor
        String name = cursor.getString(nameColumnIndex);
        String supplier =  cursor.getString(supplierColumnIndex);
        double price = cursor.getDouble(priceColumnIndex);
        int quantity = cursor.getInt(quantityColumnIndex);

        //update the views
        nameTextView.setText(name);
        supplierTextView.setText(supplier);
        priceTextView.setText(String.valueOf(price));
        quantityTextView.setText(String.valueOf(quantity));

    }
}
