package me.anikraj.iou;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contactsManager";
    private static final String TABLE_CONTACTS = "contacts";
    private static final String TABLE_CONTACTS2 = "contacts2";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_AMT = "amount";
    private static final String KEY_DATE = "date";
    private static final String KEY_PAYD = "payd";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_DATE + " TEXT," + KEY_AMT + " TEXT,"+ KEY_EMAIL + " TEXT," + KEY_PAYD+ " TEXT"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
        String CREATE_CONTACTS_TABLE2 = "CREATE TABLE " + TABLE_CONTACTS2 + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_PH_NO + " TEXT," + KEY_DATE + " TEXT," + KEY_AMT + " TEXT,"+ KEY_EMAIL + " TEXT," + KEY_PAYD+ " TEXT"+ ")";
        db.execSQL(CREATE_CONTACTS_TABLE2);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addContact(OObject contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getWho()); // Contact Name
        values.put(KEY_PH_NO, contact.getNumber()); // Contact Phone
        values.put(KEY_DATE, contact.getDate());
        values.put(KEY_AMT, contact.getAmount());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_PAYD, contact.getPayed());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection

    }
    void addContact2(OObject contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getWho()); // Contact Name
        values.put(KEY_PH_NO, contact.getNumber()); // Contact Phone
        values.put(KEY_DATE, contact.getDate());
        values.put(KEY_AMT, contact.getAmount());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_PAYD, contact.getPayed());
        // Inserting Row
        db.insert(TABLE_CONTACTS2, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single contact
    OObject getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_DATE, KEY_AMT, KEY_EMAIL, KEY_PAYD }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
            cursor.getString(0);
        OObject contact = new OObject(cursor.getString(1),Long.parseLong(cursor.getString(2)),cursor.getString(0),Double.parseDouble(cursor.getString(4)),Long.parseLong(cursor.getString(3)),Integer.parseInt(cursor.getString(6)));
        // return contact
        return contact;
    }
    OObject getContact2(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS2, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_DATE, KEY_AMT, KEY_EMAIL, KEY_PAYD }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        cursor.getString(0);
        OObject contact = new OObject(cursor.getString(1),Long.parseLong(cursor.getString(2)),cursor.getString(0),Double.parseDouble(cursor.getString(4)),Long.parseLong(cursor.getString(3)),Integer.parseInt(cursor.getString(6)));
        //OObject contact=new OObject("anik",123123123,"1",12,200,2);
        // return contact
        return contact;
    }

    public List<OObject> getAllContactsByNum(long num) {
        List<OObject> contactList = new ArrayList<OObject>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_DATE, KEY_AMT, KEY_EMAIL, KEY_PAYD }, KEY_PH_NO + "=?",
                new String[] { String.valueOf(num) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                OObject contact = new OObject();
                contact.setWho(cursor.getString(1));
                contact.setEmail(cursor.getString(0));
                contact.setNumber(Long.parseLong(cursor.getString(2)));
                contact.setAmount(Double.parseDouble(cursor.getString(4)));
                contact.setDate(Long.parseLong(cursor.getString(3)));
                contact.setPayed(Integer.parseInt(cursor.getString(6)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }



        // return contact list
        return contactList;
    }
    public List<OObject> getAllContactsByNum2(long num) {
        List<OObject> contactList = new ArrayList<OObject>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS2, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO, KEY_DATE, KEY_AMT, KEY_EMAIL, KEY_PAYD }, KEY_PH_NO + "=?",
                new String[] { String.valueOf(num) }, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                OObject contact = new OObject();
                contact.setWho(cursor.getString(1));
                contact.setEmail(cursor.getString(0));
                contact.setNumber(Long.parseLong(cursor.getString(2)));
                contact.setAmount(Double.parseDouble(cursor.getString(4)));
                contact.setDate(Long.parseLong(cursor.getString(3)));
                contact.setPayed(Integer.parseInt(cursor.getString(6)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }



        // return contact list
        return contactList;
    }
    // code to get all contacts in a list view
    public List<OObject> getAllContacts() {
        List<OObject> contactList = new ArrayList<OObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OObject contact = new OObject();
                contact.setWho(cursor.getString(1));
                contact.setEmail(cursor.getString(0));
                contact.setNumber(Long.parseLong(cursor.getString(2)));
                contact.setAmount(Double.parseDouble(cursor.getString(4)));
                contact.setDate(Long.parseLong(cursor.getString(3)));
                contact.setPayed(Integer.parseInt(cursor.getString(6)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }
    public List<OObject> getAllContacts2() {
        List<OObject> contactList = new ArrayList<OObject>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS2;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                OObject contact = new OObject();
                contact.setWho(cursor.getString(1));
                contact.setEmail(cursor.getString(0));
                contact.setNumber(Long.parseLong(cursor.getString(2)));
                contact.setAmount(Double.parseDouble(cursor.getString(4)));
                contact.setDate(Long.parseLong(cursor.getString(3)));
                contact.setPayed(Integer.parseInt(cursor.getString(6)));
                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
    // Getting contacts Count
    public int getContactsCount2() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS2;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }




    public int updateContact(OObject contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getWho()); // Contact Name
        values.put(KEY_PH_NO, contact.getNumber()); // Contact Phone
        values.put(KEY_DATE, contact.getDate());
        values.put(KEY_AMT, contact.getAmount());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_PAYD, contact.getPayed());
        // updating row
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getEmail()) });
    }

    // Deleting single contact
    public void deleteContact(OObject contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getEmail()) });
        db.close();
    }

    public int updateContact2(OObject contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getWho()); // Contact Name
        values.put(KEY_PH_NO, contact.getNumber()); // Contact Phone
        values.put(KEY_DATE, contact.getDate());
        values.put(KEY_AMT, contact.getAmount());
        values.put(KEY_EMAIL, contact.getEmail());
        values.put(KEY_PAYD, contact.getPayed());
        // updating row
        return db.update(TABLE_CONTACTS2, values, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getEmail()) });
    }

    // Deleting single contact
    public void deleteContact2(OObject contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS2, KEY_ID + " = ?",
                new String[] { String.valueOf(contact.getEmail()) });
        db.close();
    }
}