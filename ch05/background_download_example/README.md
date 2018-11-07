## AndroidManifest.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="kr.ac.mjc.background_download_example">
    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"></uses-permission>
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"></uses-permission>
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <service android:name=".DownloadService"></service>
    </application>

</manifest>
```

## activity_main.xml
``` java
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <Button
        android:id="@+id/download_btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_weight="1"
        android:text="파일 다운로드" />
</LinearLayout>
```
## MainActivity.java
``` java
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.security.Permission;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button downloadBtn=findViewById(R.id.download_btn);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    int writePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int readPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    if(writePermission!= PackageManager.PERMISSION_GRANTED||
                            readPermission!=PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
                        return;
                    }
                }
                    Intent i=new Intent(MainActivity.this,DownloadService.class);
                startService(i);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
``` 

## build.gradle
``` gradle
 implementation 'com.squareup.okhttp3:okhttp:3.11.0'
```

## DownloadService
``` java
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
```
