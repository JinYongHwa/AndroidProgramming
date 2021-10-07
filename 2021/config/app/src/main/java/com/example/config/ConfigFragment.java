package com.example.config;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.Log;

import androidx.annotation.Nullable;

public class ConfigFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    SwitchPreference messageAlarm;
    SwitchPreference alarm;
    SwitchPreference vibrate;

    ListPreference alarmSound;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.screen_config);

        messageAlarm= (SwitchPreference) findPreference(getString(R.string.key_message_alarm));
        alarm= (SwitchPreference) findPreference("alarm");
        vibrate= (SwitchPreference) findPreference("vibrate");
        alarmSound= (ListPreference) findPreference("alarm_sound");

        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getActivity());
        pref.registerOnSharedPreferenceChangeListener(this);
        boolean messageAlarmValue=pref.getBoolean("message_alarm",false);

        if(messageAlarmValue){
            alarm.setEnabled(true);
            vibrate.setEnabled(true);
            alarmSound.setEnabled(true);
        }
        else{
            alarm.setEnabled(false);
            vibrate.setEnabled(false);
            alarmSound.setEnabled(false);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals("message_alarm")){
            Log.d("ConfigFragment","message_alarm");
            boolean messageAlarmValue=sharedPreferences.getBoolean(s,false);
            if(messageAlarmValue){
                alarm.setEnabled(true);
                vibrate.setEnabled(true);
                alarmSound.setEnabled(true);
            }
            else{
                alarm.setEnabled(false);
                vibrate.setEnabled(false);
                alarmSound.setEnabled(false);
            }
        }
    }
}
