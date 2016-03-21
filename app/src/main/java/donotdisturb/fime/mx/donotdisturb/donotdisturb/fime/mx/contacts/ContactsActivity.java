package donotdisturb.fime.mx.donotdisturb.donotdisturb.fime.mx.contacts;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

import donotdisturb.fime.mx.donotdisturb.R;

/**
 * Created by sergio on 3/20/16.
 */
public class ContactsActivity extends Activity
{

    private static final String TAG = "CONTACTSACTIVITY";
    @Override
    public void onCreate(Bundle icicle)
    {
        super.onCreate(icicle);
        setContentView(R.layout.activity_contacts);
        FragmentManager fm = getFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);
        Log.d(TAG,"OnCreate");
      if (fragment == null)
        {
            fragment = new ContactsFragment();
            fm.beginTransaction()
                    .add(R.id.fragmentContainer,fragment)
                    .commit();


        }



    }



}
