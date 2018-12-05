## res/values/styles.xml
> 액티비티의 스타일을 정의하는 리소스파일
``` xml
<resources>
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="windowNoTitle">false</item>
        <item name="windowActionBar">true</item>
    </style>
</resources>
```



## res/values/arrays.xml
> 문자열배열을 저장하는 리소스 파일
``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="array_name">
        <item>오바마카카오톡</item>
        <item>카카오톡</item>
        <item>카톡</item>
        <item>카톡왔숑</item>
    </string-array>

</resources>
```

## res/values/colors.xml
> 색상값을 저장하는 리소스파일
``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="colorPrimary">#423630</color>
    <color name="colorPrimaryDark">#7b736f</color>
    <color name="colorAccent">#fccd01</color>
</resources>
```

## res/values/dimens.xml
> 각종 사이즈값을 저장하는 리소스파일
``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="text_size">20sp</dimen>
</resources>
```

## res/values/strings.xml
> 문자열 값을 저장하는 리소스파일
``` xml
<resources>
    <string name="app_name">config_example</string>
    <string name="main_title">카카오톡</string>
</resources>
```

## res/xml/setting.xml
> 설정화면을 정의한 레이아웃파일
``` xml
<?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android"
    android:key="setting">

    <PreferenceCategory android:title="새 메시지 알림"
        >

        <SwitchPreference
            android:defaultValue="false"
            android:key="message_alarm"
            android:title="메시지 알림" />
        <SwitchPreference
            android:defaultValue="false"
            android:key="alarm"
            android:title="소리" />
        <ListPreference
            android:defaultValue="오바마카카오톡"
            android:entries="@array/array_name"
            android:entryValues="@array/array_name"
            android:key="alarm_sound"
            android:title="알림음" />
        <SwitchPreference
            android:defaultValue="false"
            android:key="vibrate"
            android:title="진동" />
    </PreferenceCategory>
</PreferenceScreen>
```

## SettingFragment.java
> 설정화면을 관장하는 Fragment
``` java
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    
    //xml 에 정의된 설정 위젯들을 멤버변수로 선언
    SwitchPreference messageAlarmSwitch;
    SwitchPreference alarmSwitch;
    SwitchPreference vibrateSwitch;
    ListPreference alarmSoundList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);  //setting.xml 을 현재 Fragment의 레이아웃으로 등록함
        
        //SharedPreference 로부터 설정이 저장된 객체를 얻어옴
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getActivity());
        
        //setting.xml 로부터 위젯들을 얻어와 초기화
        messageAlarmSwitch= (SwitchPreference) findPreference("message_alarm");
        alarmSwitch= (SwitchPreference) findPreference("alarm");
        vibrateSwitch= (SwitchPreference) findPreference("vibrate");
        alarmSoundList= (ListPreference) findPreference("alarm_sound");
        
        //설정값이 변경되었을때 현재클래스의 onSharedPreferenceChanged 가 호출되도록함
        pref.registerOnSharedPreferenceChangeListener(this);

        boolean messageAlarm=pref.getBoolean("message_alarm",false);
        alarmSoundList.setSummary(pref.getString("alarm_sound", ""));
        if(messageAlarm){
            alarmSwitch.setEnabled(true);
            vibrateSwitch.setEnabled(true);
            alarmSoundList.setEnabled(true);
        }
        else{
            alarmSwitch.setEnabled(false);
            vibrateSwitch.setEnabled(false);
            alarmSoundList.setEnabled(false);
        }
    }
    
    //설정값이 변경되었을때 호출되는 메소드
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //변경된 설정값의 key 가 alarm_sound 일경우
        if(key.equals("alarm_sound")){
            alarmSoundList.setSummary(sharedPreferences.getString("alarm_sound", ""));
        }
        if(key.equals("message_alarm")){
            //message_alarm 이 꺼진경우 알람,진동,알람사운드 전체를 disable
            //message_alarm 이 꺼진경우 알람,진동,알람사운드 전체를 enable 
            boolean messageAlarm=sharedPreferences.getBoolean("message_alarm",false);
            if(messageAlarm){
                alarmSwitch.setEnabled(true);
                vibrateSwitch.setEnabled(true);
                alarmSoundList.setEnabled(true);
            }
            else{
                alarmSwitch.setEnabled(false);
                vibrateSwitch.setEnabled(false);
                alarmSoundList.setEnabled(false);
            }
        }
    }
}
```

## SettingActivity.java
``` java
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }
}
```

## res/menu/main_menu.xml
> 메인액티비티의 ActionBar를 
``` xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:android="http://schemas.android.com/apk/res/android">

    <item
        android:id="@+id/setting"
        android:icon="@drawable/baseline_settings_white_48"
        android:title=""
        app:showAsAction="always"/>
</menu>
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

    <Button
        android:id="@+id/alarm_btn"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="알람"
        android:textSize="@dimen/text_size" />

    <Button
        android:id="@+id/config_clear_btn"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="설정초기화" />
</LinearLayout>
```

## MainActivity.java
``` java
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        

        Button alarmBtn=findViewById(R.id.alarm_btn);
        Button configClearBtn=findViewById(R.id.config_clear_btn);
        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                boolean messageAlarm=pref.getBoolean("message_alarm",false);
                boolean alarm=pref.getBoolean("alarm",false);
                String alarmSound=pref.getString("alarm_sound","오바마카카오톡");
                if(messageAlarm&&alarm){
                    String fileName="";
                    MediaPlayer mp = null;
                    switch (alarmSound){
                        case "오바마카카오톡":
                            mp = MediaPlayer.create(MainActivity.this,R.raw.kakao_obama);
                            break;
                        case "카카오톡":
                            mp = MediaPlayer.create(MainActivity.this,R.raw.kakaotalk);
                            break;
                        case "카톡":
                            mp = MediaPlayer.create(MainActivity.this,R.raw.katok);
                            break;
                        case "카톡왔숑":
                            mp = MediaPlayer.create(MainActivity.this,R.raw.katok2);
                            break;
                    }
                    mp.start();
                    Toast.makeText(MainActivity.this,alarmSound,Toast.LENGTH_SHORT).show();
                }

            }
        });
        configClearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor=pref.edit();
                editor.clear().commit();

            }
        });
    }

    //액션바를 main_menu.xml 로 초기화시켜줌
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    //액션바의 버튼이 클릭되었을시
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //설정 버튼이 클릭되었을시
        if(item.getItemId()==R.id.setting){
            //SettingActivity를 띄움
            Intent intent=new Intent(MainActivity.this,SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
```

## AndroidManifest.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="kr.ac.mjc.config_example">

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
        <activity android:name=".SettingActivity"></activity>
    </application>

</manifest>
```
