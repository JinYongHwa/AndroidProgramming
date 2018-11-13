## res/values/styles.xml
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
``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="colorPrimary">#423630</color>
    <color name="colorPrimaryDark">#7b736f</color>
    <color name="colorAccent">#fccd01</color>
</resources>
```

## res/values/dimens.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name="text_size">20sp</dimen>
</resources>
```

## res/values/strings.xml
``` xml
<resources>
    <string name="app_name">config_example</string>
    <string name="main_title">카카오톡</string>
</resources>
```

## res/xml/setting.xml

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
``` java
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    SwitchPreference messageAlarmSwitch;
    SwitchPreference alarmSwitch;
    SwitchPreference vibrateSwitch;
    ListPreference alarmSoundList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getActivity());
        messageAlarmSwitch= (SwitchPreference) findPreference("message_alarm");
        alarmSwitch= (SwitchPreference) findPreference("alarm");
        vibrateSwitch= (SwitchPreference) findPreference("vibrate");
        alarmSoundList= (ListPreference) findPreference("alarm_sound");
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

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals("alarm_sound")){
            alarmSoundList.setSummary(sharedPreferences.getString("alarm_sound", ""));
        }
        if(key.equals("message_alarm")){
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
```java
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
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(R.string.main_title);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.setting){
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
