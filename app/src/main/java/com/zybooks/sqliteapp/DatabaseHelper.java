package com.zybooks.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import androidx.annotation.Nullable;

//'DatabaseHelper' class extends the 'SQLiteOpenHelper'
public class DatabaseHelper extends SQLiteOpenHelper {
    //declare variables
    private Context context;
    public static final String DATABASE_NAME = "student.db"; // database name
    public static final int DATABASE_VERSION = 1; // database version
    public static final String TABLE_NAME = "Grades"; // table name
    public static final String COL_0 = "name"; // column number 0
    public static final String COL_1 = "surname"; // column number 1
    public static final String COL_2 = "marks"; // column number 2
    public static final String COL_3 = "id"; // column number 3

    //constructor
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create table
        String create_command = "Create TABLE "+TABLE_NAME+"("+COL_0+" text, "+COL_1+" text, "+COL_2+" text, "+COL_3+" text);";
        // execute query.
        sqLiteDatabase.execSQL(create_command);
    }

    // method to insert data into the table.
    public void insertData(ContentValues values){
        // return the current database object in writtable mode.
        SQLiteDatabase db = this.getWritableDatabase();
        // insert data to table with 'ContentValues' object form.
        db.insert(TABLE_NAME,null,values);
        //'close the database.
        db.close();
    }
    // method is created by us to get data from table.
    public Cursor getAlldata(){
        // return the current database object in readable mode.
        SQLiteDatabase db = this.getReadableDatabase();
        // execute query and return the 'Cursor' object which is referencing the table rows.
        // get all columns data from table
        Cursor c = db.rawQuery("select * from "+TABLE_NAME,null);
        return c;
    }

    // method to update rows with data with respect to 'id'.
    public void updateData(String id,ContentValues values){
        // return the current database object in readable mode.
        SQLiteDatabase db = this.getWritableDatabase();
        // update table data based on id
        if(db.update(TABLE_NAME,values,COL_3+"=?",new String[]{id})>0){
            Toast.makeText(context, "You have successfully updated the table. ", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "There are no table rows with this id. ", Toast.LENGTH_SHORT).show();
        }
    }

    // delete rows with a given 'id'.
    public void deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        // delete rows in the table with column(COL_3) and the requested 'id'.
        if(db.delete(TABLE_NAME,COL_3+"=?",new String[]{id})>0){
            Toast.makeText(context, "Entry deleted. ", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "There are no rows with this id. ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

