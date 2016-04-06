package donotdisturb.fime.mx.donotdisturb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EndTime extends AppCompatActivity {
    private TimePicker timePicker;
    private Calendar calendar;
    private String format = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_time);

        timePicker = (TimePicker) findViewById(R.id.end_time);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);
    }

    public void setTime(View view) {
//        int hour = timePicker.getCurrentHour();
//        int min = timePicker.getCurrentMinute();
        int hour = timePicker.getHour();
        int min = timePicker.getMinute();
        showTime(hour, min);
    }

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
    }
}
