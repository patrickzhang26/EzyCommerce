package com.example.ezycommerce.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ezycommerce.model.Product;


import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static DatabaseHelper sInstance;
    private static final String DATABASE_NAME = "EzyCommerce.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Cart";

    private static final String KEY_ID = "id";
    private static final String KEY_PRODUCTID = "product_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_QTY = "quantity";
    private static final String KEY_IMG = "image";

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE Cart ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "product_id INTEGER, "
                + "name TEXT, "
                + "price REAL, "
                + "quantity INTEGER,"
                + "image TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void addProduct(Product prod){
        SQLiteDatabase db  = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            int quantity = addOrUpdate(prod);
            if(quantity != 0){
                String selectQuery = "SELECT  * FROM " + TABLE_NAME;
                Cursor cursor = db.rawQuery(selectQuery, null);
                cursor.moveToFirst();
                while (cursor.isAfterLast() == false) {
                    if (prod.getName() == cursor.getString(cursor.getColumnIndex(KEY_NAME))) {
                        ContentValues args = new ContentValues();
                        int qty = quantity + 1;
                        args.put(KEY_QTY,qty);
                        String whereArgs[] = {cursor.getString(cursor.getColumnIndex(KEY_ID))};
                        db.update(TABLE_NAME, args, "id = ?", whereArgs);
                        break;
                    }
                    cursor.moveToNext();
                }
                cursor.close();
            } else {
                values.put(KEY_PRODUCTID, prod.getId());
                values.put(KEY_NAME, prod.getName());
                values.put(KEY_PRICE, prod.getPrice());
                values.put(KEY_QTY, 1);
                values.put(KEY_IMG, prod.getImg());
            }

            db.insertOrThrow(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add product to database");
        } finally {
            db.endTransaction();
        }
    }

    public int addOrUpdate(Product prod){
        Integer qty = 0;
        SQLiteDatabase db  = getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            cursor.moveToFirst();
            while (cursor.isAfterLast() == false) {
                if (prod.getName() == cursor.getString(cursor.getColumnIndex(KEY_NAME))) {
                    qty = cursor.getInt(cursor.getColumnIndex(KEY_QTY));
                    break;
                }
                cursor.moveToNext();
            }

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get a product from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return qty;
    }

    public List<Product> allProduct(){
        List<Product> prods = new ArrayList<>();
        SQLiteDatabase db  = getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Product prod = new Product();
                    prod.setId(cursor.getInt(cursor.getColumnIndex(KEY_PRODUCTID)));
                    prod.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                    prod.setPrice(cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)));
                    prod.setQuantity(cursor.getInt(cursor.getColumnIndex(KEY_QTY)));
                    prod.setImg(cursor.getString(cursor.getColumnIndex(KEY_IMG)));
                    prods.add(prod);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get product from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return prods;
    }

    public double getSumValue(){
        double sum = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sumQuery = String.format("SELECT SUM(%s) as Total FROM %s",KEY_PRICE,TABLE_NAME);
        Cursor cursor = db.rawQuery(sumQuery,null);
        if(cursor.moveToFirst())
            sum = cursor.getDouble(cursor.getColumnIndex("Total"));
        return sum;
    }

    public void removeProduct(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_NAME + "=\"" + name + "\"", null);
    }

    public void deleteAll() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_NAME, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }
}
