package kr.ac.mjc.background_download_example;

import android.app.Notification;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadService extends Service implements Runnable{

    NotificationManager mNotificationManager;
    Notification mNotification;
    Handler handler=new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mNotificationManager= (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mNotification = new Notification.Builder(this)
                .setContentTitle("다운로드중..")
                .setContentText("다운로드중..")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setOngoing(true)
                .setProgress(100,0,false)
                .getNotification();
        mNotificationManager.notify(1, mNotification);

        new Thread(this).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void run() {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/test.txt");
        Request request = new Request.Builder().url("https://github.com/JinYongHwa/AndroidPrograming/raw/master/ch05/test2.txt").build();
        OkHttpClient client=new OkHttpClient();
        int lastPercent=0;
        try {
            Response response = client.newCall(request).execute();
            InputStream is = response.body().byteStream();
            OutputStream os = new FileOutputStream(myDir);

            byte[] data = new byte[4096];
            long contentLength=response.body().contentLength();
            long downloaded=0;
            while (true) {
                int read = is.read(data);
                if (read == -1) {
                    break;
                }
                os.write(data,0,read);
                downloaded += read;
                final int percent=(int)((float)downloaded/contentLength*100);
                if(lastPercent!=percent){
                    lastPercent=percent;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            publishProgress(percent);
                        }
                    });
                }
            }

            handler.post(new Runnable() {

                @Override
                public void run() {
                    publishProgress(100);
                }
            });



        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void publishProgress(float percent){
        if(percent==100){
            mNotificationManager.cancel(1);
            return;
        }
        mNotification = new Notification.Builder(this)
                .setContentTitle("다운로드중..")
                .setContentText("다운로드중..")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setOngoing(true)
                .setProgress(100,(int)percent,false)
                .getNotification();
        mNotificationManager.notify(1, mNotification);
    }
}
