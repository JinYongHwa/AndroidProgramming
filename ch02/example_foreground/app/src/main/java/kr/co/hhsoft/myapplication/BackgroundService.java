package kr.co.hhsoft.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.Timer;
import java.util.TimerTask;

public class BackgroundService extends Service {
  Notification notification;
  int count=0;
  NotificationManager notificationManager;

  @RequiresApi(api = Build.VERSION_CODES.M)
  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


      notificationManager=getSystemService(NotificationManager.class);
      NotificationChannel channel=new NotificationChannel("download","test",NotificationManager.IMPORTANCE_DEFAULT);
      notificationManager.createNotificationChannel(channel);
      notification=getDownloadNotification(0);


      notificationManager.notify(1,notification);
      startForeground(1,notification);
    }
    Timer timer=new Timer();
    TimerTask timerTask=new TimerTask() {
      @Override
      public void run() {
        count+=10;
        notification=getDownloadNotification(count);
        notificationManager.notify(1,notification);
        if(count==100){
          notification=getCompleteNotification();
          notificationManager.notify(1,notification);
          timer.cancel();

        }
      }
    };
    timer.schedule(timerTask,1000,1000);
    return super.onStartCommand(intent, flags, startId);
  }

  public Notification getDownloadNotification(int progress){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      return new Notification.Builder(this,"download")
        .setContentTitle("다운로드중")
        .setContentText("파일을 다운로드중입니다")
        .setProgress(100,progress,false)
        .setSmallIcon(R.mipmap.ic_launcher)
        .build();
    }
    return null;
  }
  public Notification getCompleteNotification(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

      Intent intent=new Intent(BackgroundService.this,MainActivity.class);
      intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      PendingIntent pendingIntent=PendingIntent.getActivity(BackgroundService.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
      return new Notification.Builder(this,"download")
        .setContentTitle("다운로드완료")
        .setContentText("파일이 다운로드되었습니다")
        .setSmallIcon(R.mipmap.ic_launcher)

        .setContentIntent(pendingIntent)
        .build();
    }
    return null;
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
