package com.example.cr554.inventoryapp.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.example.cr554.inventoryapp.database.InventoryContract.InventoryEntry;

/**
 * Created by cr554 on 2/23/2017.
 * Content Provider for the inventory database
 */

public class InventoryProvider extends ContentProvider {

    private InventoryDBHelper mDbHelper;
    private static final int INVENTORY = 100;
    private static final int INVENTORY_ID= 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static{
        //we're adding these uri's to UriMatcher. If the supplied uri matches either of these, it returns the 3rd input.
        //UriMatcher.addUri(String authority, String path, int return_code)
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY,InventoryContract.PATH_INVENTORY,INVENTORY); //com.example.cr554.inventoryapp, database, 100
        sUriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY,InventoryContract.PATH_INVENTORY +"/#",INVENTORY_ID); //com.example.cr554.inventoryapp, /#, 101
    }

    @Override
    public boolean onCreate(){
        //initialize a dbhelper object to gain access to the inventory db
        mDbHelper = new InventoryDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //get readable database
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        //perform appropriate query
        switch (match) {
            case INVENTORY:
                cursor = database.query(InventoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case INVENTORY_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(InventoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cant Query");
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues){
        final int match = sUriMatcher.match(uri);
        switch (match){
            case INVENTORY:
                return insertEntry(uri,contentValues);
            default:
                throw new IllegalArgumentException("cant insert");
        }
    }

    private Uri insertEntry(Uri uri, ContentValues contentValues){
        //TODO:validate

        //get db to write
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        //insert into DB
        long id = database.insert(InventoryEntry.TABLE_NAME,null, contentValues);
        //notify that there was a change to the db
        getContext().getContentResolver().notifyChange(uri,null);
        return ContentUris.withAppendedId(uri,id);

    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String selection, String[] selectionArgs){
        final int match = sUriMatcher.match(uri);
        switch (match){
            case INVENTORY:
                return updateEntry(uri, contentValues,selection, selectionArgs);
            case INVENTORY_ID:
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateEntry(uri, contentValues, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("cant update");
        }
    }
    private int updateEntry(Uri uri, ContentValues contentValues,String selection, String[] selectionArgs){
        //TODO:validate

        //get writable db
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        //update row
        int rowsUpdated = database.update(InventoryEntry.TABLE_NAME,contentValues,selection, selectionArgs);

        if (rowsUpdated!=0){
            //notify that there was a change to the db if at least one row was changed
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs){
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;
        final int match =sUriMatcher.match(uri);
        switch (match) {
            case INVENTORY:
                // Delete all rows that match the selection and selection args
                rowsDeleted = database.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);

                break;
            case INVENTORY_ID:
                // Delete a single row given by the ID in the URI
                selection = InventoryEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                rowsDeleted = database.delete(InventoryEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri,null);
        return rowsDeleted;
    }

    @Override
    public String getType(@NonNull Uri uri){
        final int match = sUriMatcher.match(uri);
        switch (match){
            case INVENTORY:
                return InventoryEntry.CONTENT_LIST_TYPE;
            case INVENTORY_ID:
                return InventoryEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("IDK URI: "+uri);
        }
    }

}
