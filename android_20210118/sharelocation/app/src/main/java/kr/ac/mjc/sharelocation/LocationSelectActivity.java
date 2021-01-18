package kr.ac.mjc.sharelocation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

public class LocationSelectActivity extends AppCompatActivity implements OnMapReadyCallback {

    NaverMap mNavermap;
    final static String NAME_LATITUDE="latitude";
    final static String NAME_LONGTITUDE="longtitude";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_select);
        Button selectBtn=findViewById(R.id.select_btn);
        MapFragment mapFragment= (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng location=mNavermap.getCameraPosition().target;
                Log.d("LocationSelectActivity",location.latitude+","+location.longitude);
                Intent resultIntent=new Intent();
                resultIntent.putExtra(NAME_LATITUDE,location.latitude);
                resultIntent.putExtra(NAME_LONGTITUDE,location.longitude);
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.mNavermap=naverMap;
    }
}
