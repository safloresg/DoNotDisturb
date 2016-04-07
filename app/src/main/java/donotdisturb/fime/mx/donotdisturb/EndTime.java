package donotdisturb.fime.mx.donotdisturb;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EndTime extends Activity {
    private TimePicker timePicker;
    private Calendar calendar;
    private String format = "";
    private Button btn_end_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_time);

        timePicker = (TimePicker) findViewById(R.id.end_time);
        btn_end_time = (Button) findViewById(R.id.btn_end_time);
        calendar = Calendar.getInstance();

        btn_end_time.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int end_hour = timePicker.getCurrentHour();
                int end_min = timePicker.getCurrentMinute();
                Log.d("HORA: ", String.valueOf(end_hour));
                Log.d("MIN: ", String.valueOf(end_min));
                finish();
            }
        });

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
