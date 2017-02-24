package com.example.cr554.inventoryapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by cr554 on 2/24/2017.
 * the edit activity creates new DB entries and edits existing ones.
 * It differentiates the two by examining the intent that is used to create the activity:
 * if the URI of the intent is null, we are creating a new one, else we're editing an old one
 *
 * Additionally, a "delete" button will be created in the layout to delete an entry in the db
 * if the intent passes a URI (ie: we cant delete a "new" entry before we edit it.
 *
 * WE'll be using "GONE" to do that
 */

public class EditActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
    }
}
