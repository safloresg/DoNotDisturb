package donotdisturb.fime.mx.donotdisturb;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import donotdisturb.fime.mx.donotdisturb.donotdisturb.fime.mx.Days.DaysPicker;
import donotdisturb.fime.mx.donotdisturb.donotdisturb.fime.mx.contacts.ContactsActivity;


public class MainActivity extends ListActivity {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        MainAdapter adapter = new MainAdapter(this, generateData());
        FragmentManager fm = getFragmentManager();
        setListAdapter(adapter);
    }
    @Override
    public void onListItemClick(ListView listView,View view, int pos ,long l )
    {
        switch (pos)
        {
            case 0:
                Intent i0 = new Intent(MainActivity.this,DaysPicker.class);
                startActivity(i0);
                break;
            case 1:
                Intent i1 = new Intent(MainActivity.this,StartTime.class);
                startActivity(i1);
                break;
            case 2:
                Intent i2 = new Intent(MainActivity.this,EndTime.class);
                startActivity(i2);
                break;
            case 3:
                Intent i3 = new Intent(MainActivity.this,ContactsActivity.class);
                startActivity(i3);
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

// ---- Genera cada menu en la pantalla principal ----
    private ArrayList<Model> generateData(){
        ArrayList<Model> models = new ArrayList<>();
        models.add(new Model(R.drawable.ic_action_calendar_month,"Dias"));
        models.add(new Model(R.drawable.ic_action_clock,"Hora inicio"));
        models.add(new Model(R.drawable.ic_action_clock, "Hora fin"));
        models.add(new Model(R.drawable.ic_action_user, "Contactos"));

        return models;
    }


}
