package com.example.cr554.inventoryapp.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by cr554 on 2/23/2017.
 * InventoryContract is final because it provides constants
 */

public final class InventoryContract {

    private InventoryContract(){}
    public static final String CONTENT_AUTHORITY = "com.example.cr554.inventoryapp";
    public static final String PATH_INVENTORY = "inventory";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);

    public static abstract class InventoryEntry implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);
        public static final String TABLE_NAME = "inventory";        //TEXT
        public static final String COLUMN_NAME = "name";            //TEXT
        public static final String COLUMN_PRICE = "price";          //REAL
        public static final String COLUMN_QUANTITY= "quantity";     //INT
        public static final String COLUMN_SUPPLIER = "supplier";    //TEXT
        public static final String _ID = BaseColumns._ID;           //INT
    }
}
