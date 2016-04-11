package donotdisturb.fime.mx.donotdisturb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

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
}
