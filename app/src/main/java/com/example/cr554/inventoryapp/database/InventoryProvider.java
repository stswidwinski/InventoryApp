package com.example.cr554.inventoryapp.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

/**
 * Created by cr554 on 2/23/2017.
 * Content Provider for the inventory database
 */

public class InventoryProvider extends ContentProvider {

    public static final String LOG_TAG = InventoryProvider.class.getSimpleName();

    private InventoryDBHelper mDbHelper;

    @Override
    public boolean onCreate(){
        //initialize a dbhelper object to gain access to the inventory db
        mDbHelper = new InventoryDBHelper(getContext());
        return true;
    }

    //Why do i have to @NonNull Params. arent they innately non null?\
    //ANSWER: @NonNull prevents me from passing  a null URI to these method.
    //Specifically: @NonNullDenotes that a parameter, field or method return value can never be null
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues){
        return null;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
        return 0;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs){

        return 0;
    }

    @Override
    public String getType(@NonNull Uri uri){
        return null;
    }

}
