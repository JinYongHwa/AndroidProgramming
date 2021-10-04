package com.example.config;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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


        SharedPreferences pref=getSharedPreferences("config",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();

        int startCount=pref.getInt("startCount",0);
        startCount++;
        editor.putInt("startCount",startCount);
        editor.commit();
        Toast.makeText(this,"start app count :"+startCount,Toast.LENGTH_SHORT).show();
    }

}