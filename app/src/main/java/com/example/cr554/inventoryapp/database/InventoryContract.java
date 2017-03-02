package com.example.cr554.inventoryapp.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by cr554 on 2/23/2017.
 *
 * InventoryContract is final because it provides constants
 *
 * Contract classes are essential to hold the large amounts of CONSTANT information DB integration
 * requires.
 *
 * some of these constants were deemed "package local" by the compiler. They were made so to suppress warnings.
 * future changes may see the need to privitize/publicize these constants
 */

public final class InventoryContract {

    private InventoryContract(){}
    static final String CONTENT_AUTHORITY = "com.example.cr554.inventoryapp";
    static final String PATH_INVENTORY = "database";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);

    public static abstract class InventoryEntry implements BaseColumns{
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INVENTORY);
        static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+ PATH_INVENTORY;
        static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE+"/"+CONTENT_AUTHORITY+"/"+ PATH_INVENTORY;
        public static final String _ID = BaseColumns._ID;           //INT
        static final String TABLE_NAME = "inventory";        //TEXT
        public static final String COLUMN_NAME = "name";            //TEXT
        public static final String COLUMN_PRICE = "price";          //REAL
        public static final String COLUMN_QUANTITY= "quantity";     //INT
        public static final String COLUMN_SUPPLIER = "supplier";    //TEXT

    }
}
