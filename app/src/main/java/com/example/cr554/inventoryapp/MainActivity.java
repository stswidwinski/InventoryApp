package com.example.cr554.inventoryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cr554.inventoryapp.database.InventoryDBHelper;
import com.example.cr554.inventoryapp.database.InventoryContract.InventoryEntry;

public class MainActivity extends AppCompatActivity {
    private InventoryDBHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //need a button for add edit and delete eventually

        //access the database by instantiating the InventoryDBHelper subclass of SQLiteOpenHelper
        mDbHelper = new InventoryDBHelper(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        displayDBInfo();
    }

    //temp func to display DB info to the screen
    private void displayDBInfo(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection={
                InventoryEntry._ID,
                InventoryEntry.COLUMN_NAME,
                InventoryEntry.COLUMN_SUPPLIER,
                InventoryEntry.COLUMN_PRICE,
                InventoryEntry.COLUMN_QUANTITY
        };

        Cursor cursor= db.query(
                InventoryEntry.TABLE_NAME,  //table to querey
                projection,                 //columns to return
                null,                       //Where clause column
                null,                       //Where clause value
                null,                       //group rows
                null,                       //rowgroup filter
                null);                      //sort order
        TextView dbDisplay = (TextView) findViewById(R.id.db_info);
        try{
            dbDisplay.setText("The inventory table contains: "+cursor.getCount()+" pets.\n\n");
            dbDisplay.append(InventoryEntry._ID + " - " +
                    InventoryEntry.COLUMN_NAME + " - " +
                    InventoryEntry.COLUMN_SUPPLIER + " - " +
                    InventoryEntry.COLUMN_PRICE + " - " +
                    InventoryEntry.COLUMN_QUANTITY + "\n");


        }finally{
            cursor.close();
        }
    }
}
