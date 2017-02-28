package com.example.cr554.inventoryapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cr554.inventoryapp.database.InventoryContract.InventoryEntry;
/**
 * Created by cr554 on 2/23/2017.
 * a custom helper class. SQLiteOpenHelper si a class designed to manage database creation and Version Management
 * the class allows ContentProviders (such as our InventoryProvider) to defer opening and upgrading the database
 * until first use - thus avoiding blocking application start up with db operations
 */

public class InventoryDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "storeInventory.db";
    private static final int DATABASE_VERSION = 1;

    public InventoryDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME + "("
                +InventoryEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
                +InventoryEntry.COLUMN_NAME+" TEXT NOT NULL, "
                +InventoryEntry.COLUMN_PRICE+" REAL NOT NULL, "
                +InventoryEntry.COLUMN_QUANTITY+" INTEGER NOT NULL, "
                +InventoryEntry.COLUMN_SUPPLIER+" TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_INVENTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //"still version 1 dont need to do anything" - udacity, 2017
        //onUpgrade should be used to drop or add tables, and any other tasks necessary to
        //upgrade the DB to the new schema.
        //The method also holds a recipt of what it does so it can rollback changes if there is an
        //exception thrown.
    }
}
