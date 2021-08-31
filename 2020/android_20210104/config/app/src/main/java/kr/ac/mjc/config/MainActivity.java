package kr.ac.mjc.config;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button countBtn=findViewById(R.id.count_btn);
        Button configBtn=findViewById(R.id.config_btn);

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
//        SharedPreferences.Editor editor=pref.edit();
//        count=pref.getInt("count",0);



        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                count++;
//                editor.putInt("count",count);
//                editor.commit();
//                Toast.makeText(MainActivity.this,"count is "+count,Toast.LENGTH_SHORT).show();
                boolean alarmEnabled=pref.getBoolean("alarm_enabled",false);
                boolean toastAlarm=pref.getBoolean("toast_alarm",false);
                String sound=pref.getString("sound",null);
                Log.d("config",String.format("alarmEnabled[%s] toastAlarm[%s] sound[%s]",alarmEnabled,toastAlarm,sound));
                if(alarmEnabled&&sound!=null){
                    MediaPlayer mp=null;
                    switch (sound){
                        case "오바마카카오톡":
                            mp=MediaPlayer.create(MainActivity.this,R.raw.kakao_obama);
                            break;
                        case "카카오톡":
                            mp=MediaPlayer.create(MainActivity.this,R.raw.kakaotalk);
                            break;
                        case "카톡":
                            mp=MediaPlayer.create(MainActivity.this,R.raw.katok);
                            break;
                    }
                    mp.start();
                }
                if(toastAlarm){
                    Toast.makeText(MainActivity.this,"메세지가 왔습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });
        configBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ConfigActivity.class);
                startActivity(intent);

            }
        });
    }
}