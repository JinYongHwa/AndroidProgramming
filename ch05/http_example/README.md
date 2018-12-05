# HTTP 다루기
> 안드로이드에 내장된 HttpURLConnection 클래스를 이용해 URL주소의 html 화면에 

## activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <EditText
            android:id="@+id/url_et"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:ems="10"
            android:inputType="none" />

        <Button
            android:id="@+id/request_btn"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="요청" />
    </LinearLayout>

    <TextView
        android:id="@+id/output_tv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:scrollbars="vertical"
        android:layout_weight="1" />

</LinearLayout>
```

## AndroidManifest.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="kr.ac.mjc.http_example">
    <uses-permission android:name="android.permission.INTERNET"></uses-permission>
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
    </application>

</manifest>
```

## MainActivity.java
``` java
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends Activity {

    Handler handler=new Handler();
    TextView outputTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button requestBtn=findViewById(R.id.request_btn);
        final EditText urlEt=findViewById(R.id.url_et);
        outputTv=findViewById(R.id.output_tv);
        
        //요청 버튼을 클릭했을시
        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //현재 주소 EditText 에사용자가 입력한 URL를 읽어옴
                String urlStr=urlEt.getText().toString();
                //입력받은 URL 을 스레드 생성자로 넘겨줌
                ConnectThread connectThread=new ConnectThread(urlStr);
                //스레드를 실행시켜 요청을 보냄
                connectThread.start();
            }
        });
    }


    class ConnectThread extends Thread {

        private String mUrl;
        public ConnectThread(String url){
            this.mUrl=url;
        }
        @Override
        public void run() {
            super.run();
            final String output=request(mUrl);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    outputTv.setText(output);
                }
            });
        }
        //주소값을 파라미터로 넣으면 Http 요청후 결과값을 반환시켜주는 메소드
        public String request(String urlStr){
            //읽어온 결과값을 임시로 저장하는 StringBuilder 클래스 생성
            StringBuilder output=new StringBuilder();
            try {
                //파라미터로 받아온 url 값으로 URL 클래스를 생성함
                URL url=new URL(urlStr);
                
                HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                if(conn!=null){
                    //요청이 10초이상 응답이없으면 timeout 이 되도록 설정
                    conn.setConnectTimeout(10000);
                    //요청의 메소드를 GET타입으로 설정
                    conn.setRequestMethod("GET");
                    //Input & Output 이 가능하도록 설정함
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    //Http 요청의 결과코드를 받아옴
                    int responseCode=conn.getResponseCode();
                    //요청이 성공한경우
                    if(responseCode==HttpURLConnection.HTTP_OK){
                        //현재 요청의 결과값을 한줄씩 얻어올수있는 리더를 얻어옴
                        BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        while(true){
                            //값을 한줄씩 읽어옴
                            String line=reader.readLine();
                            //더이상 읽어올 값이 없을경우 while 문을 빠져나감
                            if(line==null){
                                break;
                            }
                            //읽어온 한줄을 output에 추가함
                            output.append(line+"\n");
                        }

                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //읽어온 전체 text를 
            return output.toString();
        }
    }



}
```
