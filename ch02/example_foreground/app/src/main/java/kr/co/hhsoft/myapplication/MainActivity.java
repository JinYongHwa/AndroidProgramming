package kr.co.hhsoft.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button startBtn=findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
            startService(new Intent(MainActivity.this,BackgroundService.class));
            finish();
          }
        });

    }
}
