package kr.ac.mjc.config_example;

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
