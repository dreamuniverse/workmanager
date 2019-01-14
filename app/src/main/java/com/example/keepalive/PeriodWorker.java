package com.example.keepalive;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.List;

import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class PeriodWorker extends Worker{

  public PeriodWorker(Context context, WorkerParameters workerParameters) {
    super(context, workerParameters);
  }

  @NonNull
  @Override
  public Result doWork() {
    //only if the notification setting is on will the wake up service starts(can be set periodic without this condition)
    if (isServiceRunning(getApplicationContext(), "ForeService")
        || !NotificationConfig.isNotificationOn()) {
      return Result.success();
    }
    Intent intent = new Intent(getApplicationContext(), ForeService.class);
    intent.setAction("android.intent.action.RESPOND_VIA_MESSAGE");
    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        getApplicationContext().startForegroundService(intent);
      } else {
        getApplicationContext().startService(intent);
      }
    } catch (Exception e) {
    }
    return Result.success();
  }

  private boolean isServiceRunning(Context context, String serviceName) {
    ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    if (am == null) {
      return false;
    }
    List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100);
    for (ActivityManager.RunningServiceInfo info : infos) {
      String className = info.service.getClassName();
      if(serviceName.equals(className))
        return true;
    }
    return false;
  }
}
