package com.ioa.jyh.firstfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SecondActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewpager;

    MainAdapter mainAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tabLayout=findViewById(R.id.tablayout);
        viewpager=findViewById(R.id.viewpager);

        mainAdapter=new MainAdapter(getSupportFragmentManager());
        viewpager.setAdapter(mainAdapter);

        tabLayout.setupWithViewPager(viewpager);

        tabLayout.getTabAt(0).setIcon(R.drawable.outline_home_black_48);
        tabLayout.getTabAt(1).setIcon(R.drawable.outline_search_black_48);
        tabLayout.getTabAt(2).setIcon(R.drawable.outline_add_box_black_48);
        tabLayout.getTabAt(3).setIcon(R.drawable.outline_local_mall_black_48);
        tabLayout.getTabAt(4).setIcon(R.drawable.outline_person_black_48);

    }
}
