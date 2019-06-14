package com.udithamelan.foodapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.udithamelan.foodapp.entity.Product;

public class ProductDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "ProductsDB";
    public static final String TABLE_NAME = "Products";
    public static final String PRODUCT_ID = "prod_id";
    public static final String PRODUCT_NAME = "prod_name";
    public static final String PRODUCT_WEIGHT = "prod_weight";
    public static final String PRODUCT_PRICE = "prod_price";
    public static final String PRODUCT_DESCRIPTION = "prod_desc";
    public static final String PRODUCT_AVAILABLE = "prod_available";


    public ProductDBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "( "
                + PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PRODUCT_NAME + " TEXT, "
                + PRODUCT_WEIGHT + " DOUBLE,"
                + PRODUCT_PRICE + " DOUBLE, "
                + PRODUCT_DESCRIPTION + " TEXT, "
                + PRODUCT_AVAILABLE + " INTEGER DEFAULT 0)";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addProductData(String prod_name, Double prod_weight, Double prod_price, String prod_desc, int prod_available){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_NAME, prod_name);
        cv.put(PRODUCT_WEIGHT, prod_weight);
        cv.put(PRODUCT_PRICE, prod_price);
        cv.put(PRODUCT_DESCRIPTION, prod_desc);
        cv.put(PRODUCT_AVAILABLE, prod_available);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor displayProducts(){
        SQLiteDatabase db = this.getWritableDatabase();
        /*Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;*/
        return db.query(
                TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                PRODUCT_NAME + " ASC"
        );
    }

    public void updateAvailability(int id, int prodAv){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_AVAILABLE, 1);
        String idStr = String.valueOf(id);
        String updateAvailable = "UPDATE " + TABLE_NAME + " SET " + PRODUCT_AVAILABLE +  " = " + prodAv + " WHERE " + PRODUCT_ID + " = " + idStr + ";";
        db.execSQL(updateAvailable);
    }

    public Cursor availableProducts(){
        SQLiteDatabase db = this.getWritableDatabase();
        //String sqlCode = "SELECT * FROM " + TABLE_NAME + " WHERE " + PRODUCT_AVAILABLE + " = " + 1 + ";";

        //return db.rawQuery(sqlCode,null);
        String selection = "prod_available=?";
        String args = "1";
        return db.query(
                TABLE_NAME,
                null,
                selection,
                new String[]{"1"},
                null,
                null,
                null,
                null
        );

    }

    public boolean updateData(int id, String name, double weight, double price, String desc, int availability){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(PRODUCT_NAME, name);
        cv.put(PRODUCT_WEIGHT, weight);
        cv.put(PRODUCT_PRICE, price);
        cv.put(PRODUCT_DESCRIPTION, desc);
        cv.put(PRODUCT_AVAILABLE, availability);

        db.update(TABLE_NAME, cv, "prod_id=?", new String[] {String.valueOf(id)});
        return true;
    }

    public Cursor searchResult(String searchQuery){
        SQLiteDatabase db = this.getWritableDatabase();
        //String searchResult = "SELECT * FROM " + TABLE_NAME + " WHERE " + PRODUCT_NAME + " LIKE " + "'%" + searchQuery+"%' OR " + PRODUCT_DESCRIPTION + " LIKE " + "'%" + searchQuery+"%';";
        //Cursor cursor =  db.rawQuery(searchResult,null);

        //return cursor;
        String selection = "prod_Name =? AND prod_desc=?";
        String selectionargs = "%"+ searchQuery + "%";
        return db.query(
                TABLE_NAME,
                null,
                selection,
                new String[]{selectionargs},
                null,
                null,
                null
        );


    }






}
