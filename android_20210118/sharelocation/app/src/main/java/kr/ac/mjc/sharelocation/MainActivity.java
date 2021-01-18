package kr.ac.mjc.sharelocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewpager=findViewById(R.id.viewpager);
        TabLayout tablayout=findViewById(R.id.tablayout);

        MainPageAdapter adapter=new MainPageAdapter(getSupportFragmentManager(),0);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
    }
}