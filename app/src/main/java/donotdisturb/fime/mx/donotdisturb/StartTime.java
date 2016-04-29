package donotdisturb.fime.mx.donotdisturb;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.app.AppCompatActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class StartTime extends Activity {
    private TimePicker timePicker;
    private Calendar calendar;
    private String format = "";
    private Button btn_start_time;
    public static final String START_HOUR = "START_HOUR";
    public static final String START_MIN = "START_MIN";
    private SharedPreferences shPrefFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_time);

        timePicker = (TimePicker) findViewById(R.id.start_time);
        btn_start_time = (Button) findViewById(R.id.btn_start_time);
        calendar = Calendar.getInstance();
        shPrefFile = PreferenceManager.getDefaultSharedPreferences(this);

        btn_start_time.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                int start_hour = timePicker.getCurrentHour();
                int start_min = timePicker.getCurrentMinute();
                shPrefFile.edit().putInt(START_HOUR, start_hour).commit();
                shPrefFile.edit().putInt(START_MIN,start_min).commit();

                finish();
            }
        });
            int hour = shPrefFile.getInt(START_HOUR, Calendar.HOUR_OF_DAY);
            int min = shPrefFile.getInt(START_MIN, Calendar.MINUTE);
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
