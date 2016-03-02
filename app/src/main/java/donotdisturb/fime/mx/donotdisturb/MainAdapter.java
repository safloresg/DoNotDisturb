package donotdisturb.fime.mx.donotdisturb;

/**
 * Created by eduardo on 29/02/16.
 */
import java.util.ArrayList;

import donotdisturb.fime.mx.donotdisturb.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainAdapter extends ArrayAdapter<Model> {

    private final Context context;
    private final ArrayList<Model> modelsArrayList;

    public MainAdapter(Context context, ArrayList<Model> modelsArrayList) {

        super(context, R.layout.main_title, modelsArrayList);

        this.context = context;
        this.modelsArrayList = modelsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater

        View rowView = null;
//        if(!modelsArrayList.get(position).isGroupHeader()){
            rowView = inflater.inflate(R.layout.main_title, parent, false);

            // 3. Get icon,title & counter views from the rowView
            ImageView imgView = (ImageView) rowView.findViewById(R.id.main_icon);
            TextView titleView = (TextView) rowView.findViewById(R.id.main_label);

            // 4. Set the text for textView
            imgView.setImageResource(modelsArrayList.get(position).getIcon());
            titleView.setText(modelsArrayList.get(position).getLabel());
//        }
//        else{
//            rowView = inflater.inflate(R.layout.main_title, parent, false);
//            TextView titleView = (TextView) rowView.findViewById(R.id.header);
//            titleView.setText(modelsArrayList.get(position).getTitle());

//        }

        // 5. retrn rowView
        return rowView;
    }
}
