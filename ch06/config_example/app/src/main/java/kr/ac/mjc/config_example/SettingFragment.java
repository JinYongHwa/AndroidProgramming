package kr.ac.mjc.config_example;

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
