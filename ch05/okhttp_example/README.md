## build.gradle
``` gradle
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

    implementation 'com.squareup.okhttp3:okhttp:3.11.0'
}
```

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
    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1">

    </ScrollView>

    <TextView
        android:id="@+id/output_tv"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"

        />

</LinearLayout>
```

## AndroidManifest.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="kr.ac.mjc.okhttp_example">
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity {
    OkHttpClient client = new OkHttpClient();
    TextView outputTv;
    Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button requestBtn=findViewById(R.id.request_btn);
        final EditText urlEt=findViewById(R.id.url_et);
        outputTv=findViewById(R.id.output_tv);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String output= null;
                ConnectThread connectThread=new ConnectThread(urlEt.getText().toString());
                connectThread.start();
            }
        });
    }



    class ConnectThread extends Thread {
        OkHttpClient client = new OkHttpClient();
        private String mUrl;

        public ConnectThread(String url) {
            this.mUrl = url;
        }

        @Override
        public void run() {
            super.run();

            try {
                final String output = request(mUrl);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        outputTv.setText(output);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String request(String urlStr) throws IOException {
            Request request = new Request.Builder()
                    .url(urlStr)
                    .build();

            Response response = client.newCall(request).execute();
            return response.body().string();
        }
    }
}
```
