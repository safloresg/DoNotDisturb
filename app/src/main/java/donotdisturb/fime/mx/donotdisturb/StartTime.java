package donotdisturb.fime.mx.donotdisturb;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class StartTime extends AppCompatActivity {
    private TimePicker timePicker;
    private Calendar calendar;
    private String format = "";
    private Button btn_silence;
    private Button btn_normal;
    private AudioManager audiom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_time);

        timePicker = (TimePicker) findViewById(R.id.start_time);
        btn_silence = (Button) findViewById(R.id.btn_silence);
        btn_normal = (Button) findViewById(R.id.btn_normal);
        calendar = Calendar.getInstance();
        audiom = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        btn_silence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Guardando..", Toast.LENGTH_LONG).show();
                //finish();
            audiom.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        });

        btn_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Guardando..", Toast.LENGTH_LONG).show();
                //finish();
                audiom.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
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
