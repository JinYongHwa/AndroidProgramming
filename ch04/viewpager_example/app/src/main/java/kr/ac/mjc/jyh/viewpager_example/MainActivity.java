package kr.ac.mjc.jyh.viewpager_example;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager=findViewById(R.id.viewpager);
        ImagePagerAdapter adapter=new ImagePagerAdapter(this);
        viewPager.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
