package donotdisturb.fime.mx.donotdisturb.donotdisturb.fime.mx.Days;

import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;

import donotdisturb.fime.mx.donotdisturb.R;

/**
 * Created by eduardo on 28/04/16.
 */
public class DaysAdapter extends ArrayAdapter {

    HashMap<Integer, Boolean> checked = new HashMap<Integer, Boolean>();
    ArrayList weekdays;
    Context context;
    //    private int[] colors = new int[] { Color.parseColor("#D0D0D0"), Color.parseColor("#D8D8D8") };
    public DaysAdapter(Context context, int resource, int textViewResourceId, ArrayList<String> weekdays) {
        super(context, resource, textViewResourceId, weekdays);
        this.context=context;
        this.weekdays=weekdays;
        for(int i=0;i<weekdays.size();i++)
        {
            checked.put(i, PreferenceManager.getDefaultSharedPreferences(context).getBoolean(weekdays.get(i), false));
        }
    }

    public DaysAdapter(Context context, int resource, int textViewResourceId, ArrayList weekdays, ArrayList oldweekdays) {
        super(context, resource, textViewResourceId, weekdays);
        this.context=context;
        this.weekdays=weekdays;
        for(int i=0;i<weekdays.size();i++)checked.put(i, false);
        for(int i=0;i<oldweekdays.size();i++)checked.put((Integer) oldweekdays.get(i),true);
    }

    public void toggle(int position){
        if(checked.get(position))checked.put(position, false);
        else checked.put(position, true);
        notifyDataSetChanged();
    }

    public ArrayList getCheckedItemPosition(){
        ArrayList check = new ArrayList();
        for(int i=0;i<checked.size();i++){
            if(checked.get(i))check.add(i);
        }
        return check;
    }

    public HashMap<Integer,Boolean> getCheckedItems(){
        return checked;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        if(row == null){
            LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = vi.inflate(R.layout.day_item, null);
        }

        CheckedTextView checkedTextView = (CheckedTextView)row.findViewById(R.id.checkedtextview);
        checkedTextView.setText( weekdays.get(position).toString());
        checkedTextView.setChecked(checked.get(position));
        return row;
    }
}
