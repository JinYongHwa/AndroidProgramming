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
