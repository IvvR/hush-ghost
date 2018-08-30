package com.irv.smart.hushghosts;


import android.content.ComponentName;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceGroup;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.SwitchPreferenceCompat;

/**
 * PrefHGFrag keeps app preferences.
 */

public class PrefHGFrag extends PreferenceFragmentCompat {

  public static final String TAG = "PrefHGFrag";
  public static final String APP_STATE_ON_OFF = "appStateOnOff";
  public static final String CALL_PREF_CAT = "callPrefCat";
  public static final String IS_CALL_REJECT_KEY = "rejectCall";
  public static final String IS_LOG_REJECTED_KEY = "logRejected";

  /**
   * Reads saved preferences, sets UI elements state
   * @param savedInstanceState
   * @param rootKey
   */
  @Override
  public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
    setPreferencesFromResource(R.xml.preferences, rootKey);

    SharedPreferences defPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = (sharedPreferences, key) -> {
      if (key.equalsIgnoreCase(APP_STATE_ON_OFF)) {
        appOnOff(sharedPreferences);
      }
      if (key.equalsIgnoreCase(IS_CALL_REJECT_KEY)) {
        rejectCallOnOff(sharedPreferences);
      }

    };

    defPrefs.registerOnSharedPreferenceChangeListener(prefListener);
  }

  /**
   * Handles OnOff app using a switch state by enableComponent method
   * @param sharedPreferences
   */
  private void appOnOff(SharedPreferences sharedPreferences) {
    boolean isAppOn = sharedPreferences.getBoolean(APP_STATE_ON_OFF, true);
    enableComponent(isAppOn, GhostCallReceiver.class);
    prefCatOnOff(isAppOn, CALL_PREF_CAT);
  }

  /**
   * Enables / disable  GhostCallReceiver using the switch
   * @param isEnable
   * @param clazz
   */
  public void enableComponent(boolean isEnable, Class<?> clazz) {
    ComponentName componentName = new ComponentName(getActivity(), clazz);
    PackageManager pm = getActivity().getPackageManager();
    int componentState = (isEnable) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    pm.setComponentEnabledSetting(componentName, componentState, PackageManager.DONT_KILL_APP);
  }

  private void prefCatOnOff(boolean isEnable, String prefKey) {
    PreferenceGroup preferenceGroup = (PreferenceGroup) findPreference(prefKey);
    preferenceGroup.setEnabled(isEnable);
  }

  /**
   * Sets Start / Stop handling incoming calls
   * @param sharedPreferences
   */
  private void rejectCallOnOff(SharedPreferences sharedPreferences) {
    boolean isRejectCallOn = sharedPreferences.getBoolean(IS_CALL_REJECT_KEY, true);
    switchOnOff(isRejectCallOn, IS_LOG_REJECTED_KEY);
  }


  public void switchOnOff(boolean isOn, String prefKey) {
    SwitchPreferenceCompat switchPref = (SwitchPreferenceCompat) findPreference(prefKey);
    switchPref.setEnabled(isOn);
  }

}
