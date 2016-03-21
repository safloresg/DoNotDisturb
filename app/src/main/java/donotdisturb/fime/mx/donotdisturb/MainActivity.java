package donotdisturb.fime.mx.donotdisturb;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

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
            case 3:
                Intent i = new Intent(MainActivity.this,ContactsActivity.class);
                startActivity(i);
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
