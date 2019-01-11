package com.example.keepalive;

import android.content.Context;

public class NotificationConfig {

  public static final String PREF_KEY_NOTIFICATION_ON = "notification_on";
  private static final String NOTIFICATION = "notification";
  private static Context mContext;

  public static boolean isNotificationOn() {
    return mContext.getSharedPreferences(NOTIFICATION, Context.MODE_PRIVATE).getBoolean(PREF_KEY_NOTIFICATION_ON, true);
  }

  public static void updateNotificationStatus(boolean switchOn) {
    mContext.getSharedPreferences(NOTIFICATION, Context.MODE_PRIVATE).edit().putBoolean(PREF_KEY_NOTIFICATION_ON, switchOn).apply();
  }

  public static void setContext(Context context) {
    mContext = context;
  }

  public static Context getContext() {
    return mContext;
  }
}
