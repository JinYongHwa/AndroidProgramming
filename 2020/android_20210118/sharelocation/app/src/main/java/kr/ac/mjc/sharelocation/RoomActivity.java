package kr.ac.mjc.sharelocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import kr.ac.mjc.sharelocation.model.Room;

public class RoomActivity extends AppCompatActivity {

    public final static String NAME_ROOM="room";
    Room mRoom;

    final int REQ_PERMISSION=20000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        ViewPager viewpager=findViewById(R.id.viewpager);
        TabLayout tablayout=findViewById(R.id.tablayout);

        RoomPageAdapter adapter=new RoomPageAdapter(getSupportFragmentManager(),0);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        mRoom= (Room) getIntent().getSerializableExtra(NAME_ROOM);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int fineStatus=checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int coarseStatus=checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if(fineStatus!= PackageManager.PERMISSION_GRANTED || coarseStatus!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},REQ_PERMISSION);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
