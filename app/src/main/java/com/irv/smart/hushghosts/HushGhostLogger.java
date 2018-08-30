package com.irv.smart.hushghosts;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ivan on 27/12/17.
 */

public class HushGhostLogger {

  private static final String TAG = "HushGhostLogger";

  public final static String LOG_FILE_NAME = "hushGhostsLog.txt";

  public static void writeLogRecord(Context context, String phoneNumber, long callTime) {
    String recStr = buildLogRecord(phoneNumber, callTime);
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    //TODO : get a log file name from SharedPreferences
    try {
      FileOutputStream fileOutputStream = context.openFileOutput(LOG_FILE_NAME, Context.MODE_PRIVATE);
      fileOutputStream.write(recStr.getBytes());
      fileOutputStream.close();
    } catch (java.io.IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * TODO :
   *
   * @param context
   * @return
   */
  public static List<String> loadLogFileAsList(Context context) {

    List<String> logList = new ArrayList<>();

    //TODO : Remove this  test !!!
    logList.add("Unknown 11:10:05 12/27/2017");
//----------
    //TODO : get a log file name from SharedPreferences
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

    try {
      InputStream inputStream = context.openFileInput(LOG_FILE_NAME);
      if (inputStream != null) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String newLine = null;
        while ((newLine = bufferedReader.readLine()) != null) {
          logList.add(newLine);
        }
        inputStream.close();
      }
    } catch (java.io.IOException e) {
      Log.e(TAG, "Looks like HushGhosts Log file doesn't exist. It could be the first launch or it was deleted. " + e.getMessage());
    }
    return logList;
  }

  public void purgeLogFile(Context context) {
    //TODO : get a log file name from SharedPreferences
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

    context.deleteFile(LOG_FILE_NAME);
  }

  private static String buildLogRecord(String phoneNumber, long callTime) {
    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.CANADA);
    return phoneNumber + " " + df.format(new Date(callTime));
  }

}
