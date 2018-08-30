package com.irv.smart.hushghosts;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ivan on 26/12/17.
 */

public class HushGhostsLogService extends IntentService {

  public static final String SERVICE_NAME = "HushGhostsLogService";
  private static final String TAG = SERVICE_NAME;

  public HushGhostsLogService() {
    super(SERVICE_NAME);
  }

  @Override
  protected void onHandleIntent(@Nullable Intent intent) {
    Log.i(TAG, SERVICE_NAME + " started.");
    Bundle extras = intent.getExtras();
    if (extras != null) {
      String logRecType = extras.getString(HushGhostsActivity.LOG_RECORD_TYPE);
      if (HushGhostsActivity.WRITE_LOG_RECORD.equalsIgnoreCase(logRecType)) {
        HushGhostLogger.writeLogRecord(getApplicationContext(), extras.getString(HushGhostsActivity.LOGGED_PH_NUM), extras.getLong(HushGhostsActivity.LOGGED_CALL_TIME));
      }
    }
  }
}
