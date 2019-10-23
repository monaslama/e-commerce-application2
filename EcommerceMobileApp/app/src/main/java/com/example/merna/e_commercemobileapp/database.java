package com.example.merna.e_commercemobileapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {

    private static String DBname = "Onlineshopping";
    private static int version = 16;
    SQLiteDatabase Onlineshopping;
    private String shoppingTableCreation = "Create table Products(ProId integer Primary key autoincrement," + "name text" + "quantity integer" + "Price integer);";

    public database(Context context) {
        super(context, DBname, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Customers (CustID integer primary key autoincrement ,CutName text not null,Username text not null ," +
                "Password text not null ,Gender text ,Birthdate text,Job text ) ");
        db.execSQL("create table Categories (CatID integer primary key autoincrement,CatName text not null)");
        db.execSQL("create table Products (ProID integer primary key autoincrement ,ProName text not null,Price integer not null,Quantity integer not null ,CatID integer,foreign key (CatID) references Categories (CatID))");
        db.execSQL("create table Orders (OrdID integer primary key autoincrement ,OrdDate text not null,CustID integer not null,Address text not null,foreign key(CustID)references Customers (CustID))");
        db.execSQL("create table OrderDetails (OrdID integer not null, ProID integer not null ,Quantity integer not null,foreign key (OrdID)references Orders (OrdID),foreign key (ProID) references Products (ProID),primary key(OrdID,ProID)  )");
        db.execSQL("create table rememberMeOption(r_ID integer primary key autoincrement ,Username text not null ,Password text not null ) ");
        db.execSQL("create table forgetPassOption(F_ID integer primary key autoincrement ,Username text not null ,Password text not null ,presonal_Q text not null ) ");

        db.execSQL("insert into Categories (CatName) values('Perfumes For Men')");
        db.execSQL("insert into Categories (CatName) values('Perfumes For Women')");

        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Zara',564,20,1)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('XS',321,10,1)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Armani',897,15,1)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Polo',654,15,1)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Black',231,15,1)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('White',471,15,1)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Red',623,15,1)");

        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Celebre',645,20,2)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Little black dress',321,10,2)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Love is belle',987,14,2)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Bon Bon',956,14,2)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Love the heabily',451,14,2)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('Black Orkeed',321,14,2)");
        db.execSQL("insert into Products (ProName,Price,Quantity,CatID) values('White Orkeed',215,14,2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists OrderDetails");
        db.execSQL("drop table if exists rememberMeOption");
        db.execSQL("drop table if exists forgetPassOption");
        onCreate(db);
    }

    public long signup(String name, String username, String password, String gender, String birthdate, String job) {
        ContentValues row = new ContentValues();
        row.put("CutName", name);
        row.put("Username", username);
        row.put("Password", password);
        row.put("Gender", gender);
        row.put("Birthdate", birthdate);
        row.put("Job", job);
        Onlineshopping = getWritableDatabase();
        return Onlineshopping.insert("customers", null, row);
    }

    public boolean login(String username, String password) {
        Onlineshopping = getWritableDatabase();
        Cursor c = Onlineshopping.rawQuery("select * from Customers where Username = '" + username + "' and Password = '" + password + "'", null);
        if (c != null && c.getCount() > 0)//check if username &password is found or not(there is rows in the cursor)
        {
            c.close();
            return true;
        } else {
            c.close();
            return false;
        }
    }

    public Cursor perfumeForMen() {
        Onlineshopping = getReadableDatabase();
        Cursor cursor = Onlineshopping.rawQuery("select * from Products where CatID = 1", null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor;
    }

    public Cursor perfumeForWomen() {
        Onlineshopping = getReadableDatabase();
        Cursor cursor = Onlineshopping.rawQuery("select * from Products where CatID = 2", null);
        if (cursor != null)
            ((Cursor) cursor).moveToFirst();
        return cursor;
    }

    public Cursor searchByText(String name) {
        Onlineshopping = getReadableDatabase();
        Cursor cursor = Onlineshopping.rawQuery("SELECT * FROM Products WHERE ProName LIKE '%" + name + "%'", null);
        if (cursor != null)
            cursor.moveToFirst();
        Onlineshopping.close();
        return cursor;
    }

    public void passwordRecovery(String username, String newPassword) {
        Onlineshopping = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("Password", newPassword);
        Onlineshopping.update("Customers", row, "Username like ?", new String[]{username});
        Onlineshopping.close();
    }

    public boolean answer(String username, String job) {
        Onlineshopping = getWritableDatabase();
        Cursor cursor = Onlineshopping.rawQuery("select * from Customers where Username = '" + username + "' and Job = '" + job + "'", null);//raw query always holds rawQuery(String Query,select args)

        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    public Integer customer_id(String username, String password){
        Onlineshopping=getWritableDatabase();
        Cursor cursor = Onlineshopping.rawQuery("select CustID from Customers where Username = '"+ username +"' and Password = '"+ password+"'", null);//raw query always holds rawQuery(String Query,select args)
        if (cursor !=null)
            ((Cursor) cursor).moveToFirst();
        return cursor.getInt(0);
    }

    public long make_order(String order_date, String address, Integer cust_id)
    {
        ContentValues row = new ContentValues();
        row.put("OrdDate", order_date);
        row.put("CustID", cust_id);
        row.put("Address", address);
        Onlineshopping = getWritableDatabase();
        return Onlineshopping.insert("Orders", null, row);
    }

    public Cursor get_order_id(String order_date, String address, Integer customer_id)
    {
        Onlineshopping=getReadableDatabase();
        Cursor cursor=Onlineshopping.rawQuery("select * from Orders where OrdDate = '" + order_date + "' and CustID = " + customer_id + " and Address = '" + address + "'", null);
        if (cursor !=null)
            ((Cursor) cursor).moveToFirst();
        return cursor;
    }

    public Cursor get_order_id_by_name(String product_name)
    {
        Onlineshopping = getReadableDatabase();
        Cursor cursor = Onlineshopping.rawQuery("SELECT Orders.OrdID " +
                "FROM " +
                "Products " +
                "INNER JOIN OrderDetails ON OrderDetails.ProID = Products.ProID " +
                "INNER JOIN Orders ON Orders.OrdID = OrderDetails.OrdID " +
                "WHERE " +
                "Products.ProName = '" + product_name + "'", null);
        if (cursor !=null)
            cursor.moveToFirst();
        return cursor;
    }

    public Cursor get_product_id(String product_name)
    {
        Onlineshopping = getReadableDatabase();
        Cursor cursor = Onlineshopping.rawQuery("select * from Products where ProName like '" + product_name + "' ",null);
        if(cursor.getCount()!=0)
        {
            cursor.moveToFirst();
            return  cursor;
        }
        return  cursor;
    }

    public long insert_in_order_details(Integer order_id, Integer product_id, Integer quantity)
    {
        ContentValues row = new ContentValues();
        row.put("OrdID", order_id);
        row.put("ProID", product_id);
        row.put("Quantity", quantity);
        Onlineshopping = getWritableDatabase();
        return Onlineshopping.insert("OrderDetails", null, row);
    }

    public Cursor get_all_orders(Integer customer_id)
    {
        Onlineshopping=getReadableDatabase();
        Cursor cursor = Onlineshopping.rawQuery("SELECT * " +
                "FROM " +
                "Products " +
                "INNER JOIN OrderDetails ON OrderDetails.ProID = Products.ProID " +
                "INNER JOIN Orders ON Orders.OrdID = OrderDetails.OrdID " +
                "WHERE " +
                "Orders.CustID = " + customer_id, null);
        if (cursor !=null)
            ((Cursor) cursor).moveToFirst();
        return cursor;
    }

    public void delete_order(Integer order_id)
    {
        Onlineshopping = getWritableDatabase();
        Onlineshopping.delete("Orders", "OrdID = " + order_id, null);
        Onlineshopping.close();
    }

    public Cursor get_quantity(Integer order_id)
    {
        Onlineshopping=getReadableDatabase();
        Cursor cursor = Onlineshopping.rawQuery("SELECT OrderDetails.Quantity " +
                "FROM " +
                "Products " +
                "INNER JOIN OrderDetails ON OrderDetails.ProID = Products.ProID " +
                "INNER JOIN Orders ON Orders.OrdID = OrderDetails.OrdID " +
                "WHERE " +
                "Orders.OrdID = " + order_id, null);
        if (cursor !=null)
            cursor.moveToFirst();
        return cursor;
    }

    public void update_quantity(Integer new_quantity, Integer order_id)
    {
        Onlineshopping = getWritableDatabase();
        ContentValues row = new ContentValues();
        row.put("Quantity", new_quantity);
        Onlineshopping.update("OrderDetails", row,"OrdID like ?", new String[]{order_id.toString()});
        Onlineshopping.close();
    }
}