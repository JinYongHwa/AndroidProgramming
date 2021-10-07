package com.example.config;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button configBtn=findViewById(R.id.config_btn);
        Button alarmBtn=findViewById(R.id.alarm_btn);

        configBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,ConfigActivity.class);
                startActivity(intent);
            }
        });

        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                boolean messageAlarm=pref.getBoolean(getString(R.string.key_message_alarm),false);
                boolean alarm=pref.getBoolean("alarm",false);
                boolean vibrate=pref.getBoolean("vibrate",false);
                String alarmSound=pref.getString("alarm_sound",null);
                //알림 울리게처리
                if(messageAlarm){
                    Toast.makeText(MainActivity.this,"메세지가 왔습니다",Toast.LENGTH_SHORT).show();
                    if(alarm){
                        MediaPlayer mediaPlayer=null;
                        if(alarmSound.equals("카카오톡")){
                            mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.kakaotalk);
                        }
                        else if(alarmSound.equals("카톡")){
                            mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.raw_katok);
                        }
                        else{
                            mediaPlayer=MediaPlayer.create(MainActivity.this,R.raw.kakao_obama);
                        }
                        mediaPlayer.start();
                    }
                    if(vibrate){
                        Vibrator vibrator= (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(1000);
                    }

                }
            }
        });


        SharedPreferences pref=getSharedPreferences("config",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();

        int startCount=pref.getInt("startCount",0);
        startCount++;
        editor.putInt("startCount",startCount);
        editor.commit();
        Toast.makeText(this,"start app count :"+startCount,Toast.LENGTH_SHORT).show();
    }


}