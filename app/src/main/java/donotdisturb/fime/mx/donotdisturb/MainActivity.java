package donotdisturb.fime.mx.donotdisturb;

import android.app.Activity;
import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ListActivity {
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // if extending Activity
//        setContentView(R.layout.activity_main);

        // 1. pass context and data to the custom adapter
        MainAdapter adapter = new MainAdapter(this, generateData());

        // if extending Activity 2. Get ListView from activity_main.xml
//        ListView listView = (ListView) findViewById(R.id.);

        // 3. setListAdapter
        setListAdapter(adapter);
    }

    private ArrayList<Model> generateData(){
        ArrayList<Model> models = new ArrayList<Model>();
        models.add(new Model(R.drawable.ic_action_calendar_month,"Dias"));
        models.add(new Model(R.drawable.ic_action_clock,"Hora inicio"));
        models.add(new Model(R.drawable.ic_action_clock, "Hora fin"));
        models.add(new Model(R.drawable.ic_action_user, "Contactos"));

        return models;
    }


}
