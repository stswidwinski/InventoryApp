package com.example.cr554.inventoryapp;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cr554.inventoryapp.adapter.InventoryCursorAdapter;
import com.example.cr554.inventoryapp.database.InventoryDBHelper;
import com.example.cr554.inventoryapp.database.InventoryContract.InventoryEntry;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor>{
    private InventoryDBHelper mDbHelper;
    private static final int INVENTORY_LOADER =0;
    InventoryCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //have fab open editor activity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this,EditActivity.class);
                startActivity(intent);
            }
        });

        //find list view
        ListView itemListView = (ListView) findViewById(R.id.inventory_list);

        //set up adatper to create list item for each row of pet data in the cursor
        mCursorAdapter = new InventoryCursorAdapter(this, null);
        itemListView.setAdapter(mCursorAdapter);

        //set up the item click listener
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Uri currentItemUri = ContentUris.withAppendedId(InventoryEntry.CONTENT_URI,id);
                intent.setData(currentItemUri);
                startActivity(intent);
            }
        });

        //launch loader
        getLoaderManager().initLoader(INVENTORY_LOADER,null,this);

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
                null,                       //row group filter
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {InventoryEntry._ID,
                InventoryEntry.COLUMN_NAME,
                InventoryEntry.COLUMN_SUPPLIER,
                InventoryEntry.COLUMN_PRICE,
                InventoryEntry.COLUMN_QUANTITY};

        return new CursorLoader(this,               //parent activity context
                InventoryEntry.CONTENT_URI,         //uri of current item
                projection,                         //columns to include in the cursor
                null,                               //null selection clause
                null,                               //null selection arguments
                null);                              //default sort
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);

    }
}
