package com.example.keepalive;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.util.concurrent.TimeUnit;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {


  private ToggleButton toggleButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    NotificationConfig.setContext(getApplicationContext());
    initView();
    setWork();
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  private void setWork() {
    //workmanager period work (not less than 15 minutes)
    PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest
        .Builder(PeriodWorker.class, 16, TimeUnit.MINUTES)
        .build();
    WorkManager.getInstance().enqueueUniquePeriodicWork("pullwork", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
  }

  private void initView() {
    toggleButton = findViewById(R.id.notification_switch);
    toggleButton.setTextOff("foreground service is off");
    toggleButton.setTextOn("foreground service is on and period work is running");
    toggleButton.setChecked(NotificationConfig.isNotificationOn());
    toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Intent intent = new Intent(NotificationConfig.getContext(), ForeService.class);
        intent.setAction("android.intent.action.PULL_UP");
        if (isChecked) {
          NotificationConfig.updateNotificationStatus(true);
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent);
          } else {
            startService(intent);
          }
        } else {
          NotificationConfig.updateNotificationStatus(false);
          stopService(intent);
        }
      }
    });
  }
}