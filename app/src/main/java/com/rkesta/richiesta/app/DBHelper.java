package com.rkesta.richiesta.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.rkesta.richiesta.model.cart.M_Cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
//      call
//    DB = new DBHelper(this);

    public static final String DATABASE_NAME = "richiestaDB.db";

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Cart_Table_Name);
    }

    public static final String  Cart_Table_Name = "Cart";
    public static final String  Cart_ID = "ID" ;
    public static final String  Cart_Store_ID = "Store_ID" ;
    public static final String  Cart_Store_name = "Store_name" ;
    public static final String  Cart_Store_IMG = "Store_IMG" ;
    public static final String  Cart_Branch_ID = "Branch_ID" ;
    public static final String  Cart_Products_ID = "Products_ID" ;

    public static final String  Cart_Product_nameEN = "Product_nameEN" ;
    public static final String  Cart_Product_nameAR = "Product_nameAR" ;

    public static final String  Cart_Product_Price = "Product_Price" ;
    public static final String  Cart_Product_IMG = "Product_IMG" ;
    public static final String  Cart_Color_ID = "Color_ID" ;
    public static final String  Cart_Color_name = "Color_name" ;
    public static final String  Cart_Color_price = "Color_price" ;
    public static final String  Cart_Additional_ID = "Additional_ID" ;
    public static final String  Cart_Additional_name = "Additional_name" ;
    public static final String  Cart_Additional_price = "Additional_price" ;
    public static final String  Cart_Size_ID = "Size_ID" ;
    public static final String  Cart_Size_name = "Size_name" ;
    public static final String  Cart_Size_price = "Size_price" ;
    public static final String  Cart_Qty = "Qty" ;
    public static final String  Cart_TotalPrice = "TotalPrice" ;
    public static final String  Cart_OrderNotes = "OrderNotes" ;
    public static final String  Cart_Added_Date = "Added_Date" ;

    /**
     ||========================================||
     ||  Cart                                  ||
     ||========================================||
     ||                                        ||
     ||  Cart_Table                            ||
     ||  Cart_ID                               ||
     ||  Cart_Store_ID                         ||
     ||  Cart_Store_name                       ||
     ||  Cart_Store_IMG                        ||
     ||  Cart_Branch_ID                        ||
     ||  Cart_Products_ID                      ||
     ||  Cart_Product_name                     ||
     ||  Cart_Product_Price                    ||
     ||  Cart_Product_IMG                      ||
     ||  Cart_Color_ID                         ||
     ||  Cart_Color_name                       ||
     ||  Cart_Color_price                      ||
     ||  Cart_Additional_ID                    ||
     ||  Cart_Additional_name                  ||
     ||  Cart_Additional_price                 ||
     ||  Cart_Size_ID                          ||
     ||  Cart_Size_name                        ||
     ||  Cart_Size_price                       ||
     ||  Cart_Qty                              ||
     ||  Cart_TotalPrice                       ||
     ||  Cart_OrderNotes                       ||
     ||  Cart_Added_Date                       ||
     ||                                        ||
     ||========================================||
     */

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(
                "create table  "+Cart_Table_Name+"" +
                        "("+Cart_ID+" INTEGER PRIMARY KEY AUTOINCREMENT" +
                        ","+Cart_Store_ID+" text" +
                        ","+Cart_Store_name+" text" +
                        ","+Cart_Store_IMG+" text" +
                        ","+Cart_Branch_ID+" text" +
                        ","+Cart_Products_ID+" text" +
                        ","+Cart_Product_nameEN+" text " +
                        ","+Cart_Product_nameAR+" text " +
                        ","+Cart_Product_Price+" text " +
                        ","+Cart_Product_IMG+" text " +
                        ","+Cart_Color_ID+" text " +
                        ","+Cart_Color_name+" text " +
                        ","+Cart_Color_price+" text " +
                        ","+Cart_Additional_ID+" text " +
                        ","+Cart_Additional_name+" text " +
                        ","+Cart_Additional_price+" text " +
                        ","+Cart_Size_ID+" text " +
                        ","+Cart_Size_name+" text " +
                        ","+Cart_Size_price+" text " +
                        ","+Cart_Qty+" text " +
                        ","+Cart_TotalPrice+" text " +
                        ","+Cart_OrderNotes+" text " +
                        ","+Cart_Added_Date+" text)"
        );


    }


    public void setCart( String Store_ID ,String Store_name ,String Store_IMG ,String Branch_ID ,String Products_ID ,
                         String Product_nameEN ,String Product_nameAR ,String Product_Price ,String Product_IMG ,String Color_ID ,String Color_name ,String Color_price ,String Additional_ID ,String Additional_name ,String Additional_price ,String Size_ID ,String Size_name ,String Size_price ,String Qty ,String TotalPrice ,String OrderNotes ,String Added_Date ) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Cart_Store_ID, Store_ID);
        contentValues.put(Cart_Store_name, Store_name);
        contentValues.put(Cart_Store_IMG, Store_IMG);
        contentValues.put(Cart_Branch_ID, Branch_ID);
        contentValues.put(Cart_Products_ID, Products_ID);
        contentValues.put(Cart_Product_nameEN, Product_nameEN);
        contentValues.put(Cart_Product_nameAR, Product_nameAR);
        contentValues.put(Cart_Product_Price, Product_Price);
        contentValues.put(Cart_Product_IMG, Product_IMG);
        contentValues.put(Cart_Color_ID, Color_ID);
        contentValues.put(Cart_Color_name, Color_name);
        contentValues.put(Cart_Color_price, Color_price);
        contentValues.put(Cart_Additional_ID, Additional_ID);
        contentValues.put(Cart_Additional_name, Additional_name);
        contentValues.put(Cart_Additional_price, Additional_price);
        contentValues.put(Cart_Size_ID, Size_ID);
        contentValues.put(Cart_Size_name, Size_name);
        contentValues.put(Cart_Size_price, Size_price);
        contentValues.put(Cart_Qty, Qty);
        contentValues.put(Cart_TotalPrice, TotalPrice);
        contentValues.put(Cart_OrderNotes, OrderNotes);
        contentValues.put(Cart_Added_Date, Added_Date);

        db.insert(Cart_Table_Name, null, contentValues);
    }


    public ArrayList<M_Cart> getCart()
    {
        ArrayList<M_Cart> Cart_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("select * from " + Cart_Table_Name, null);
        res.moveToFirst();

        while(!res.isAfterLast()){
            M_Cart datanum = new M_Cart(

                    res.getString(res.getColumnIndex(Cart_ID              )), //Cart_ID
                    res.getString(res.getColumnIndex(Cart_Store_ID        )), //Store_ID
                    res.getString(res.getColumnIndex(Cart_Store_name      )), //Store_name
                    res.getString(res.getColumnIndex(Cart_Store_IMG       )), //Store_IMG
                    res.getString(res.getColumnIndex(Cart_Branch_ID       )), //Branch_ID
                    res.getString(res.getColumnIndex(Cart_Products_ID     )), //Products_ID
                    res.getString(res.getColumnIndex(Cart_Product_nameEN    )), //Product_nameEN
                    res.getString(res.getColumnIndex(Cart_Product_nameAR    )), //Product_nameAR
                    res.getString(res.getColumnIndex(Cart_Product_Price   )), //Product_Price
                    res.getString(res.getColumnIndex(Cart_Product_IMG     )), //Product_IMG
                    res.getString(res.getColumnIndex(Cart_Color_ID        )), //Color_ID
                    res.getString(res.getColumnIndex(Cart_Color_name      )), //Color_name
                    res.getString(res.getColumnIndex(Cart_Color_price     )), //Color_price
                    res.getString(res.getColumnIndex(Cart_Additional_ID   )), //Additional_ID
                    res.getString(res.getColumnIndex(Cart_Additional_name )), //Additional_name
                    res.getString(res.getColumnIndex(Cart_Additional_price)), //Additional_price
                    res.getString(res.getColumnIndex(Cart_Size_ID         )), //Size_ID
                    res.getString(res.getColumnIndex(Cart_Size_name       )), //Size_name
                    res.getString(res.getColumnIndex(Cart_Size_price      )), //Size_price
                    res.getString(res.getColumnIndex(Cart_Qty             )), //Qty
                    res.getString(res.getColumnIndex(Cart_TotalPrice      )), //TotalPrice
                    res.getString(res.getColumnIndex(Cart_OrderNotes      )), //OrderNotes
                    res.getString(res.getColumnIndex(Cart_Added_Date      ))  //Added_Dat
                    );

            Cart_list.add(datanum);
            res.moveToNext();
        }
        return Cart_list;
    }


    /**
     *
     DBHelper DB;
     DB = new DBHelper(this);
     DB.Cart_Table_Name

     *
     *
     * @param table_name_and_Where
     * @return
     */
    public boolean deleteallfrom(String table_name_and_Where) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+table_name_and_Where);

        return true;
    }


    /**
     * ex.
     * DB.deleteRow(DB.TABLE_NAME,DB.COLUMN_ID,Something.get(i).get("ID"));
     *
     *
     * @param DATABASE_TABLE
     * @param where
     * @param value
     * @return
     */
    //---deletes a particular title---
    public boolean deleteRow(String DATABASE_TABLE,String where,String value )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, where + " = " + value, null);
        return true ;
    }

//    public boolean updateQuantityproduct( String product_ID ,   String quanitity)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//
//        contentValues.put(product_COLUMN_product_quanitity, quanitity);
//
//        db.update(product_TABLE_NAME, contentValues, product_COLUMN_product_ID+" = "+product_ID, null);
//        return true;
//    }



}
