package com.example.cr554.inventoryapp;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cr554.inventoryapp.database.InventoryContract.InventoryEntry;


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

public class EditActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int EXISTING_INVENTORY_LOADER = 1;
    private Uri mCurrentItemUri;
    private EditText mNameEditText;
    private EditText mSupplierEditText;
    private EditText mPriceEditText;
    private EditText mQuantityEditText;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Button deleteButton = (Button) findViewById(R.id.delete);
        Button cancelButton = (Button) findViewById(R.id.cancel);
        Button submitButton = (Button) findViewById(R.id.submit);

        //find the relevant views
        mNameEditText = (EditText) findViewById(R.id.edit_name);
        mSupplierEditText = (EditText) findViewById(R.id.edit_supplier);
        mPriceEditText = (EditText) findViewById(R.id.edit_price);
        mQuantityEditText = (EditText) findViewById(R.id.edit_quantity);

        // we're inspecting the intent for the URI. if its null, we're creating a new data point,
        // else we're editing a current one.
        Intent intent = getIntent();
        mCurrentItemUri = intent.getData();
        if(mCurrentItemUri == null){
            setTitle("Add Item");
            deleteButton.setVisibility(View.GONE); //no sense deleting something that is being added to the data base
        }else{
            //get current URI's info for each file

            setTitle("Edit Item");
            getLoaderManager().initLoader(EXISTING_INVENTORY_LOADER,null,this);
        }

        //TODO: add onTouchListener to the edit views to highlight everything inside the edit text when the user selects it.

        //set onclick for submit
        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                saveItem();
            }
        });

        //set onclick for delete
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });

        //set onclick for cancel
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //delete button func
    private void deleteItem(){
        if (mCurrentItemUri!=null){
            int rowsDeleted = getContentResolver().delete(mCurrentItemUri, null, null);
            if (rowsDeleted ==0){
                Toast.makeText(this, "nothing to delete",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"delete success",Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    //submit button func
    private void saveItem(){
        //get inputs from edit text field.
        //trim removes leading or trailing whitespaces
        String nameString = mNameEditText.getText().toString().trim();
        String supplierString = mSupplierEditText.getText().toString().trim();
        String priceString = mPriceEditText.getText().toString().trim();
        String quantityString = mQuantityEditText.getText().toString().trim();

        //if nothing is changed, we can return early
        if (mCurrentItemUri == null
                && TextUtils.isEmpty(nameString)
                && TextUtils.isEmpty(supplierString)
                && TextUtils.isEmpty(priceString)
                && TextUtils.isEmpty(quantityString)){
            return;
        }
        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_NAME,nameString);
        values.put(InventoryEntry.COLUMN_SUPPLIER,supplierString);

        //plugs corner case where price or quantity are left blank by defaulting them to 0
        double price = 0.00;
        int quantity = 0;
        if(!TextUtils.isEmpty(priceString)){
            price = Double.parseDouble(priceString);
        }
        if(!TextUtils.isEmpty(quantityString)){
            quantity = Integer.parseInt(quantityString);
        }
        values.put(InventoryEntry.COLUMN_PRICE,price);
        values.put(InventoryEntry.COLUMN_QUANTITY,quantity);

        //determine if we are entering a new entry or editing an old one
        if(mCurrentItemUri == null){ //then we're dealing with a new entry
            Uri newURi = getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
            if (newURi == null) {
                Toast.makeText(this, "failure",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "success",Toast.LENGTH_SHORT).show();
            }
        }else{ //we're editing an existing entry
            int rowsAffected = getContentResolver().update(mCurrentItemUri,values,null,null);
            if (rowsAffected==0){
                Toast.makeText(this,"no rows affected",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,rowsAffected+" rows affected",Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle b){
        String[] projection = {InventoryEntry._ID,
                InventoryEntry.COLUMN_NAME,
                InventoryEntry.COLUMN_SUPPLIER,
                InventoryEntry.COLUMN_PRICE,
                InventoryEntry.COLUMN_QUANTITY};

        return new CursorLoader(this,  //parent activity context
                mCurrentItemUri,       //uri of current item
                projection,            //columns to include in the cursor
                null,                  //null selection clause
                null,                  //null selection arguments
                null);                 //default sort

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor){
        if (cursor == null || cursor.getCount() <1){
            return;
        }
        if (cursor.moveToFirst()){
            //find the columns of the attributes
            int nameColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_NAME);
            int supplierColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_SUPPLIER);
            int priceColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY);

            //extract the values from the cursor
            String name = cursor.getString(nameColumnIndex);
            String supplier =  cursor.getString(supplierColumnIndex);
            double price = cursor.getDouble(priceColumnIndex);
            int quantity = cursor.getInt(quantityColumnIndex);

            //update the views
            mNameEditText.setText(name);
            mSupplierEditText.setText(supplier);
            mPriceEditText.setText(String.valueOf(price));
            mQuantityEditText.setText(String.valueOf(quantity));
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader){
        mNameEditText.setText("");
        mSupplierEditText.setText("");
        mPriceEditText.setText("");
        mQuantityEditText.setText("");
    }
}
