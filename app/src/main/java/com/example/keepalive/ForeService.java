package com.example.keepalive;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import static android.support.v4.app.NotificationCompat.PRIORITY_HIGH;

public class ForeService extends Service {

  private static final String CHANNEL_ID = "fore";
  private static final String CHANNEL_NAME = "test";

  @Override
  public IBinder onBind(Intent intent) {
    // TODO: Return the communication channel to the service.
    throw new UnsupportedOperationException("Not yet implemented");
  }

  @Override
  public void onCreate() {
    String title = "foreService";
    String body = "this is a foreground notification";
    NotificationCompat.Builder notificationBuilder =
        new NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(PRIORITY_HIGH)
            .setStyle(new NotificationCompat.BigTextStyle().bigText(body).setBigContentTitle(title));

    NotificationManager notificationManager =
        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,
          CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
      notificationManager.createNotificationChannel(notificationChannel);
    }

    startForeground(10000, notificationBuilder.build());
  }


}
