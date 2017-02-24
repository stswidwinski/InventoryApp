package com.example.cr554.inventoryapp.database;

import android.provider.BaseColumns;

/**
 * Created by cr554 on 2/23/2017.
 * InventoryContract is final because it provides constants
 */

public final class InventoryContract {
    private static final String TEXT_TYPE = "TEXT";
    private static final String COMMA_SEPERATOR = ",";

    private InventoryContract(){}

    public static abstract class InventoryEntry implements BaseColumns{
        public static final String TABLE_NAME = "inventory"; //TEXT
        public static final String COLUMN_NAME = "name"; //TEXT
        public static final String COLUMN_PRICE = "price"; //REAL
        public static final String COLUMN_QUANTITY= "quantity"; //INT
        public static final String COLUMN_SUPPLIER = "supplier"; //TEXT
        public static final String _ID = BaseColumns._ID; //INT
    }
}
