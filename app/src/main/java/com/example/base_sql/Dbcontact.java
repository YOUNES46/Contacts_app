package com.example.base_sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Dbcontact extends SQLiteOpenHelper {
    public static final String DB_Name = "my_DB";
    public static final int DB_Version = 1;
    public static String DB_KEY ="id";
    public static String DB_NAME = "name";
    public static String DB_PHONE = "phone";
    public static String DB_CONTACTS = "contacts";
    private static final String KEY_IMG = "image";


    public Dbcontact(@Nullable Context context ) {
        super(context, DB_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_table = "create table " + DB_CONTACTS + "(" + DB_KEY + " integer primary key autoincrement,"
                + DB_NAME + " varchar(255) DEFAULT'',"
                + DB_PHONE + " integer ,"
                + KEY_IMG + " blob)";
        sqLiteDatabase.execSQL(create_table);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String delete_query = "DROP table if exists " + DB_CONTACTS;
        sqLiteDatabase.execSQL(delete_query);
        onCreate(sqLiteDatabase);
    }
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_NAME,contact.getName());
        values.put(DB_PHONE,contact.getPhone());
        values.put(DB_KEY,contact.getId());
        values.put(KEY_IMG, contact.getImage());
        db.insert(DB_CONTACTS,null,values);

    }
    public ArrayList<Contact> getAllcontacts(){
        ArrayList<Contact> contacts = new ArrayList<>();
       String  select_query = "select * from "+DB_CONTACTS;
       SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.rawQuery(select_query,null);
      if(cursor.moveToFirst()){
          do {
              String name =  cursor.getString(cursor.getColumnIndex(DB_NAME));
              int phone = cursor.getInt(cursor.getColumnIndex(DB_PHONE));
              int id_contact = cursor.getInt(cursor.getColumnIndex(DB_KEY));
              byte[] image = cursor.getBlob(cursor.getColumnIndex(KEY_IMG));

              Contact cont  =new Contact(name,phone,image);
              cont.setId(id_contact);
              contacts.add(cont);

          }while (cursor.moveToNext());

      }




        return contacts;
    }

    public Contact getcont(int id){
        Contact cont =null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_CONTACTS,new String[]{"id","name","phone", "image" }, "id=?",new String[]{String.valueOf(id)},null,null,null,null) ;
        if(cursor.moveToFirst()){
            int id_contact = cursor.getInt(cursor.getColumnIndex(DB_KEY));
            String name =  cursor.getString(cursor.getColumnIndex(DB_NAME));
            int phone = cursor.getInt(cursor.getColumnIndex(DB_PHONE));
            byte[] image = cursor.getBlob(cursor.getColumnIndex(KEY_IMG));

             cont  =new Contact(name,phone,image);
            cont.setId(id_contact);


        }

        return cont;

    }
    public void updatecotacye(Contact cont){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_NAME,cont.getName());
        values.put(DB_PHONE,cont.getPhone());
        values.put(KEY_IMG, cont.getImage());

        db.update(DB_CONTACTS,values,"id=?",new String[]{String.valueOf(cont.getId())});

    }
    public void deletcontac(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(DB_CONTACTS,"id=?",new String[]{String.valueOf(id)});




    }
}
