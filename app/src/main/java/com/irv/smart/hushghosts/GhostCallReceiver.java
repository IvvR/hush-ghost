package com.irv.smart.hushghosts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import static android.content.Context.TELEPHONY_SERVICE;

public class GhostCallReceiver extends BroadcastReceiver {

  public static final String TAG = "GhostCallReceiver";
  public static final String FATAL_ERROR_MESSAGE = "Fatal error happened to Hush Ghosts. Pls, stop the program and sent us a raven.";

  public static final String GET_ITHEL = "getITelephony";
  public static final String END_CALL = "endCall";

  public static final String DEFAULT_PH_NUM = "Unknown";


  @Override
  public void onReceive(Context context, Intent intent) {

    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    boolean isCallReject = prefs.getBoolean(PrefHGFrag.IS_CALL_REJECT_KEY, true);

    if (!isCallReject) {
      return;
    }

    String callEvent = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

    if (TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(callEvent)
        && (incomingNumber == null || incomingNumber.length() == 0)) {
      RejectTheCall(context);
    }

    boolean isLogRejectedCall = prefs.getBoolean(PrefHGFrag.IS_LOG_REJECTED_KEY, true);
    if(isLogRejectedCall) {
      handleLog(context);
    }

  }

  private void RejectTheCall(Context context) {
    TelephonyManager mgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

    try {
      Method getITel = mgr.getClass().getDeclaredMethod(GET_ITHEL);
      getITel.setAccessible(true);
      Object iTelephony = getITel.invoke(mgr);

      Method endCall = iTelephony.getClass().getDeclaredMethod(END_CALL);
      endCall.invoke(iTelephony);

    } catch (NoSuchMethodException e) {
      Log.e(TAG, e.getLocalizedMessage() );
      Toast.makeText(context, FATAL_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
    } catch (IllegalAccessException e) {
      Log.e(TAG, e.getLocalizedMessage() );
      Toast.makeText(context, FATAL_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
    } catch (InvocationTargetException e) {
      Log.e(TAG, e.getLocalizedMessage() );
      Toast.makeText(context, FATAL_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
    }
  }

  /**
   *
   *
   * @param context
   */
  private void handleLog(Context context) {
    Intent intent = new Intent(context, HushGhostsLogService.class);
    intent.putExtra(HushGhostsActivity.LOG_RECORD_TYPE, HushGhostsActivity.WRITE_LOG_RECORD);
    intent.putExtra(HushGhostsActivity.LOGGED_PH_NUM, DEFAULT_PH_NUM);
    intent.putExtra(HushGhostsActivity.LOGGED_CALL_TIME, new Date().getTime());
    context.startService(intent);
  }

}
