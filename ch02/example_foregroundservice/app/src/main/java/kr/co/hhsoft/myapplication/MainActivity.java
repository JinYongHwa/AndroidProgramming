package kr.co.hhsoft.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewpager;

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


        viewpager=findViewById(R.id.viewpager);
        viewpager.setOrientation(viewpager.ORIENTATION_VERTICAL);
        List<Post> postList=new ArrayList<>();
        postList.add(new Post("https://placeimg.com/500/500/nature","test111"));
        postList.add(new Post("https://placeimg.com/500/500/animals","test222"));
        postList.add(new Post("https://placeimg.com/500/500/arch","test333"));

        viewpager.setAdapter( new ViewAdapter(postList) );



    }
}
