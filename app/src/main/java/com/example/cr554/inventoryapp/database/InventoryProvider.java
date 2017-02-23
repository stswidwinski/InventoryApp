package com.example.cr554.inventoryapp.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by cr554 on 2/23/2017.
 */

public class InventoryProvider extends ContentProvider {

    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();

    private InventoryDBHelper mDbHelper;

    @Override
    public boolean onCreate(){
        mDbHelper = new InventoryDBHelper(getContext());
        //initialize a dbhelper object to gain access to the inventory db
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues){
        return null;
    }
    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs){

        return 0;
    }

    @Override
    public String getType(Uri uri){
        return null;
    }

}
