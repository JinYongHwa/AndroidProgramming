# Background Download 다루기
> 서비스를 이용해 Background 에서 용량이 큰 텍스트파일을 다운로드 받고
> 다운로드 경과를 Notification 에 표시해주기
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
``` xml
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
        //다운로드 버튼이 클릭되었을시
        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //파일 쓰기권한체크
                    int writePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    //파일 읽기권한체크
                    int readPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    
                    //파일 읽기&쓰기 권한이 없을경우 권한체크
                    if(writePermission!= PackageManager.PERMISSION_GRANTED||
                            readPermission!=PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
                        return; //권한이 없는경우 return 시켜 다운로드 서비스가 실행되지 않게한다
                    }
                }
                    //다운로드 서비스 실행
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
    Handler handler=new Handler();      //UI 변경을 위한 Handler 선언

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //안드로이드 시스템으로부터 NotificationService 를 얻어온다
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
        
        //현재 서비스에 상속받은 run메소드가 백그라운드 스레드에서 실행되도록
        //스레드를 생성해서 실행
        new Thread(this).start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void run() {
        
        //이 앱이 설치된 핸드폰의 사용할수있는 디스크 디렉토리를 얻어옴 
        String root = Environment.getExternalStorageDirectory().toString();
        //위에서 얻어온 경로에 test.txt 파일로 다운로드 받을 경로를 미리설정함
        File myDir = new File(root + "/test.txt");
        
        //OkHttp Request 를 다운로드 받을 파일 경로를 설정해 생성시킨다
        Request request = new Request.Builder().url("https://github.com/JinYongHwa/AndroidPrograming/raw/master/ch05/test2.txt").build();
        
        //OkHttpClient를 생성함
        OkHttpClient client=new OkHttpClient();
        int lastPercent=0;      //최종변경된 다운로드받아진 용량%를 저장하는 변수
        try {
        
            //다운로드 동기적으로 시작
            Response response = client.newCall(request).execute();
            //OkHttp 응답으로 부터 다운로드받을 파일의 byteStream 을 얻어옴
            InputStream is = response.body().byteStream();
            //파일을 저장할 stream 을 생성함
            OutputStream os = new FileOutputStream(myDir);

            //데이터를 임시로 저장할 buffer 공간을 확보함
            byte[] data = new byte[4096];
            
            //다운로드 진행도를 계산하기위해 전체 파일용량을 http 헤더로부터 얻어옴
            long contentLength=response.body().contentLength();
            //다운로드 진행도 계산을 위한 다운로드 받은용량을 저장하는변수 
            long downloaded=0;  
            while (true) {
                //OkHttp 응답으로부터 데이터를 buffer 에 저장함
                int read = is.read(data);
                
                //방금 buffer 에 저장한 길이가 -1일 경우 파일읽기가 끝났으므로,
                //현재의 while 문을 빠져나가준다
                if (read == -1) {
                    break;
                }
                //읽어온 데이터를 파일에 쓴다
                os.write(data,0,read);
                
                //다운로드 진행도를 계산하기위해 방금 읽어온 데이터의 길이를 추가해준다
                downloaded += read;
                
                //다운로드 진행도 계산
                final int percent=(int)((float)downloaded/contentLength*100);
                
                //다운로드 진행도가 바뀌었을때만 UI를 변경시켜줌
                if(lastPercent!=percent){
                    lastPercent=percent;
                    
                    //UI 변경을 위해 현재있는 Background Thread 에서 
                    //UI 스레드로 값을 넘겨줌
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
    
        //진행도가 100일경우 파일다운로드가 끝난것이므로 Notification을 삭제한다
        if(percent==100){
            mNotificationManager.cancel(1);
            return; //진행도가 100일경우 return 해 진행도표시가 
        }
        
        //변경된 다운로드 진행도를 Notification에 반영함
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
