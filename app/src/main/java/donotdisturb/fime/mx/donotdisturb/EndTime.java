package donotdisturb.fime.mx.donotdisturb;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
    public static String END_HOUR = "END_HOUR";
    public static String END_MIN = "END_MIN";

    private SharedPreferences shPrefFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_time);
        timePicker = (TimePicker) findViewById(R.id.end_time);
        btn_end_time = (Button) findViewById(R.id.btn_end_time);
        calendar = Calendar.getInstance();
        shPrefFile = PreferenceManager.getDefaultSharedPreferences(this);

        btn_end_time.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int end_hour = timePicker.getCurrentHour();
                int end_min = timePicker.getCurrentMinute();
                shPrefFile.edit().putInt(END_HOUR, end_hour).commit();
                shPrefFile.edit().putInt(END_MIN,end_min).commit();

                finish();
            }
        });

        int hour = shPrefFile.getInt(END_HOUR, Calendar.HOUR_OF_DAY);
        int min = shPrefFile.getInt(END_MIN, Calendar.MINUTE);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(min);

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
