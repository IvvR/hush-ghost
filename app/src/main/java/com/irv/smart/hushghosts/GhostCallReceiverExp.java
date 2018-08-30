package com.irv.smart.hushghosts;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.CompletableFuture;

import static android.content.Context.TELEPHONY_SERVICE;

public class GhostCallReceiverExp extends BroadcastReceiver {

  public static final String GET_ITHEL = "getITelephony";
  public static final String END_CALL = "endCall";
  public static final String SIL_RING = "silenceRinger";
  public static final String ANS_RING = "answerRingingCall";

  private boolean isCallBraking = true;
  private boolean isCallSilenced = true;


  @Override
  public void onReceive(Context context, Intent intent) {
    CompletableFuture future;



    String callEvent = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
    String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

    if (TelephonyManager.EXTRA_STATE_RINGING.equalsIgnoreCase(callEvent)
        && (incomingNumber == null || incomingNumber.length() == 0)) {
      RejectTheCall(context);
    }

  }

  private void RejectTheCall (Context context) {
    TelephonyManager mgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

    try {
      Method getITel = mgr.getClass().getDeclaredMethod(GET_ITHEL);
      getITel.setAccessible(true);
      Object iTelephony = getITel.invoke(mgr);

      Method endCall = iTelephony.getClass().getDeclaredMethod(END_CALL);
      endCall.invoke(iTelephony);

    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

  }

  private void silenceRinger(Context context) {
    TelephonyManager mgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

    try {

      Method getITel = mgr.getClass().getDeclaredMethod(GET_ITHEL);
      getITel.setAccessible(true);
      Object iTelephony = getITel.invoke(mgr);

      Method sRing = iTelephony.getClass().getDeclaredMethod(SIL_RING);
      sRing.invoke(iTelephony);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
  }


  /**
   * Yeah. good try but no sugar. cause no way to get a permission
   *  <uses-permission android:name="android.permission.MODIFY_PHONE_STATE" />
   *
   * @param context
   */
  private void answerCall(Context context) {
    TelephonyManager mgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);

    try {
      Method getITel = mgr.getClass().getDeclaredMethod(GET_ITHEL);
      getITel.setAccessible(true);
      Object iTelephony = getITel.invoke(mgr);
      Method ansCall = iTelephony.getClass().getDeclaredMethod(ANS_RING);
      ansCall.invoke(iTelephony);
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

  }

}
