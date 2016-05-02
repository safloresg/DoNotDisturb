package donotdisturb.fime.mx.donotdisturb.donotdisturb.fime.mx.Days;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import donotdisturb.fime.mx.donotdisturb.R;

/**
 * Created by eduardo on 28/04/16.
 */
public class DaysPicker extends Activity{

    private enum DaysOfWeek{MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY}

    ArrayList<String> weekdays = new ArrayList();
    ArrayList ret = new ArrayList();
    ListView lv;
    Button savedays;
    SharedPreferences shPrefFile;
    DaysAdapter adapter;
    String c;
  //  long courseid;

    public void initialize(){
        weekdays.add(this.getResources().getString(R.string.Monday));
        weekdays.add(this.getResources().getString(R.string.Tuesday));
        weekdays.add(this.getResources().getString(R.string.Wednesday));
        weekdays.add(this.getResources().getString(R.string.Thursday));
        weekdays.add(this.getResources().getString(R.string.Friday));
        weekdays.add(this.getResources().getString(R.string.Saturday));
        weekdays.add(this.getResources().getString(R.string.Sunday));

        shPrefFile = PreferenceManager.getDefaultSharedPreferences(this);
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
    //    lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        savedays = (Button) findViewById(R.id.btnsavedays);

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
                HashMap<Integer,Boolean> checkeditems = adapter.getCheckedItems();
                for (int i = 0; i < checkeditems.size();i++)
                {
                    shPrefFile.edit().putBoolean(weekdays.get(i), checkeditems.get(i)).commit();
                    Log.d(weekdays.get(i),checkeditems.get(i).toString());
                }
                finish();
            }
        });
    }
}
