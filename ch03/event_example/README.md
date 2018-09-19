# Android Event 예제

## activity_event_example.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="horizontal">

    <TextView
        android:id="@+id/log_tv"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:scrollbars="vertical"
        android:text="TextView" />

    <LinearLayout
        android:id="@+id/touch_target"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:background="#bbbb00"
        android:orientation="horizontal"></LinearLayout>
</LinearLayout>
```

## MainActivity.java
```java
import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

    GestureDetector mGestures;
    TextView logTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_example);
        logTv=findViewById(R.id.log_tv);
        LinearLayout touchTarget=findViewById(R.id.touch_target);
        logTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                logTv.setText("");
                return false;
            }
        });

        mGestures=new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
               logTv.append("\nonScroll \n\tx = " + distanceX + "\n\ty = " + distanceY);
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
               logTv.append("\nonFling \n\tx = " + velocityX + "\n\ty=" + velocityY);
                return false;

            }
        });

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestures != null) {
            return mGestures.onTouchEvent(event);
        } else {
            return super.onTouchEvent(event);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Toast.makeText(this,"정말 종료하시겠습니까?",Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(this, "가로방향 전환", Toast.LENGTH_SHORT).show();
        }
        if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, "세로방향 전환", Toast.LENGTH_SHORT).show();

        }
    }
}
```


## AndroidManifest.xml
```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="kr.ac.mjc.jyh.event_example">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity"
            android:configChanges="orientation|screenSize|layoutDirection"
            android:screenOrientation="sensor">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```
