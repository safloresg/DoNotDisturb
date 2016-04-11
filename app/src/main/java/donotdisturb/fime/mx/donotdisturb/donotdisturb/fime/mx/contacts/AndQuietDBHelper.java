package donotdisturb.fime.mx.donotdisturb.donotdisturb.fime.mx.contacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;


public class AndQuietDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "andquiet2.sqlite";
    private static final int VERSION = 1;
    private static final String TABLE_CONTACT = "contact";
    private static final String COLUMN_CONTACT_NAME = "name";
    private static  final String COLUMN_CONTACT_PHONE = "phone";
    private static final String COLUMN_CONTACT_ID = "_id";
    private static final String COLUMN_CONTACT_SELECTED = "selected";

    public AndQuietDBHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory() + File.separator + DB_NAME , null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the contact table

        db.execSQL("CREATE TABLE contact (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " name VARCHAR(30)," +
                        "phone CHAR(10)," +
                        " selected BOOLEAN)"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
    }

    public void insertContact(Contact contact)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CONTACT_NAME,contact.getName());
        cv.put(COLUMN_CONTACT_PHONE,contact.getPhone());
        cv.put(COLUMN_CONTACT_SELECTED,contact.isSelected());
        getWritableDatabase().insert(TABLE_CONTACT,null,cv);
    }

    public Cursor SelectAllContacts()
    {
        Cursor wrapped = getReadableDatabase().query(TABLE_CONTACT
                ,null,null,null,null,null,null,null);
        return new ContactCursor(wrapped);
    }

    public void deleteAllContacts()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CONTACT);
    }

    public ContactCursor getContactByPhoneNum(String phoneNum)
    {
        Cursor wrapped = getWritableDatabase().query(TABLE_CONTACT,
                null,
                COLUMN_CONTACT_PHONE + " = ?",
                new String[]{phoneNum},
                null,
                null,
                    null );
        return new ContactCursor(wrapped);
    }


    public boolean updateContact (Contact contact)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CONTACT_SELECTED,contact.isSelected());
        cv.put(COLUMN_CONTACT_NAME, contact.getName());
        cv.put(COLUMN_CONTACT_PHONE, contact.getPhone());
        return db.update(TABLE_CONTACT, cv, "_id=?",
                new String[]{Long.toString(contact.get_id())}) > 0;
    }

    public static class ContactCursor extends CursorWrapper
    {
        public ContactCursor(Cursor cursor) {
            super(cursor);
        }

        public Contact getcontact()
        {
            if (isBeforeFirst() || isAfterLast())
            {
                return null;
            }
            Contact c = new Contact();
            c.set_id(getLong(getColumnIndex(COLUMN_CONTACT_ID)));
            c.setName(getString(getColumnIndex(COLUMN_CONTACT_NAME)));
            c.setPhone(getString(getColumnIndex(COLUMN_CONTACT_PHONE)));
            c.setSelected(getInt(getColumnIndex(COLUMN_CONTACT_SELECTED))>0);

            return c;
        }
    }
}
