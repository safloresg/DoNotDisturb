package donotdisturb.fime.mx.donotdisturb.donotdisturb.fime.mx.contacts;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by sergio on 4/2/16.
 */
public class ContactManager {

   private AndQuietDBHelper mHelper ;
    private  Context context;
    ContactManager(Context context)
    {
        this.context = context;
         mHelper = new AndQuietDBHelper(context);
    }

    public Cursor getContacts()
    {
            return  mHelper.SelectAllContacts();
    }

    public void addContact(Contact contact)
    {
        mHelper.insertContact(contact);

    }

    public void deleteAllContacts()
    {
        mHelper.deleteAllContacts();
    }

    public void updateContact(Contact contact)
    {
        mHelper.updateContact(contact);
    }

    public void updateIsSelectedState(boolean[] contactSelArr,Cursor cursor)
    {

        cursor.moveToFirst();
        AndQuietDBHelper.ContactCursor contactCur = (AndQuietDBHelper.ContactCursor) cursor;
        for (boolean isSelected:contactSelArr) {
            Contact currentContact = contactCur.getcontact();
            currentContact.setSelected(isSelected);
            this.updateContact(currentContact);
            contactCur.moveToNext();
        }

    }
}
