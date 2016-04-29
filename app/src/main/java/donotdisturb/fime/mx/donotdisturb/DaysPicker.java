package donotdisturb.fime.mx.donotdisturb;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by eduardo on 28/04/16.
 */
public class DaysPicker extends Activity{
    ArrayList weekdays = new ArrayList();
    ArrayList ret = new ArrayList();
    ListView lv;
    Button savedays;
    DaysAdapter adapter;
    String c;
    long courseid;

    public void initialize(){
        weekdays.add("Lunes");
        weekdays.add("Martes");
        weekdays.add("Miercoles");
        weekdays.add("Jueves");
        weekdays.add("Viernes");
        weekdays.add("Sabado");
        weekdays.add("Domingo");
    }

    public void getoldweekdays(long id){
        ret.clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
        ret.clear();
        initialize();
        lv = (ListView) findViewById(R.id.listView1);
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        savedays = (Button) findViewById(R.id.btnsavedays);

        courseid = getIntent().getLongExtra("courseid", -1);
        adapter = new DaysAdapter(this, R.layout.day_item, R.id.checkedtextview, weekdays);
        lv.setAdapter(adapter);
        for(int i=0;i<ret.size();i++)lv.setItemChecked((Integer) ret.get(i), true);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.toggle(position);
            }
        });

        savedays.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ArrayList checkeditem = adapter.getCheckedItems();
                finish();
            }
        });
    }
}
