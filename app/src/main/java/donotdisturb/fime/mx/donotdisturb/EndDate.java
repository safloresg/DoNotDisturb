package donotdisturb.fime.mx.donotdisturb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class EndDate extends AppCompatActivity {

    private TimePicker timePicker;
    private TextView time;
    private Calendar calendar;
    private String format = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_date);

        timePicker = (TimePicker) findViewById(R.id.timePicker1);
        time = (TextView) findViewById(R.id.textView);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);
    }

//    public void setTime(View view) {
//        int hour = timePicker.getCurrentHour();
//        int min = timePicker.getCurrentMinute();
//        int hour = timePicker.getHour();
//        int min = timePicker.getMinute();
//        showTime(hour, min);
//    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        }
        else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        time.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }

}
