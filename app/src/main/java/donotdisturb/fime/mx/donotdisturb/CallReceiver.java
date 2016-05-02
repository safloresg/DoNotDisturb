package donotdisturb.fime.mx.donotdisturb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.nfc.Tag;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import donotdisturb.fime.mx.donotdisturb.donotdisturb.fime.mx.contacts.ContactManager;

/**
 * Created by sergio on 4/9/16.
 */
public class CallReceiver extends BroadcastReceiver {

    private String TAG = "CallReceiver";
    private static int lastRingerMode ;
    private static String lastState;

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Calendar calendar = Calendar.getInstance();

        int startHour,startMin;
        int endHour,endMin;
        int curHour,curMin;
        startHour = sharedPreferences.getInt(StartTime.START_HOUR,100);
        startMin = sharedPreferences.getInt(StartTime.START_MIN,100);
        endHour  = sharedPreferences.getInt(EndTime.END_HOUR,100);
        endMin  = sharedPreferences.getInt(EndTime.END_MIN,100);
        curHour = calendar.get(Calendar.HOUR_OF_DAY);
        curMin = calendar.get(Calendar.MINUTE);
        if (!isInTimeRange(startHour,startMin,endHour,endMin,curHour,curMin) || !isValidDay(sharedPreferences,context))
        {
            return;
        }

        TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        AudioManager audioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        String stateStr = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        int ringMode =  audioManager.getRingerMode();

        if (stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING) &&
                (ringMode == AudioManager.RINGER_MODE_NORMAL || ringMode == AudioManager.RINGER_MODE_VIBRATE))
        {
            String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

            boolean isContactSel = ContactManager.isContactSelected(number,context);
            Log.d(TAG,Boolean.toString(isContactSel));

            if (!isContactSel)
            {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
        }

        if(lastState != null && lastRingerMode != 0)
        if (stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)
                && lastState.equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            audioManager.setRingerMode(lastRingerMode);
        }
        lastState = stateStr;
        lastRingerMode = ringMode;
    }

    //TODO unit test for this method
    public static boolean isInTimeRange(int startHour,int startMin, int endHour, int endMin,int curHour,int curMin)
    {
        //Return false if no values found for any of the hours/min under the sharedPref file
        if (startHour == 100 || startMin == 100 || endHour == 100 || endMin == 100)
        return false;

        //TODO change int comparisons to use .compare instead
        if ( curHour > startHour && curHour < endHour)
            return true;
        else
        {
            if (startHour == endHour && startHour== curHour)
            //if (startHour == endHour && startHour == curHour)
            {
                if (curMin > startMin && curMin < endMin)
                    return true;
                else
                    return false;
            }
            else
                return false;
        }
    }

        public boolean isValidDay(SharedPreferences sharedPreferences,Context context)
    {
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        Calendar calendar = Calendar.getInstance();

        weekDay = dayFormat.format(calendar.getTime());

        boolean isValidDay = sharedPreferences.getBoolean(weekDay,false);
        if (!isValidDay)
            return false;
        else return true;

    }
}
