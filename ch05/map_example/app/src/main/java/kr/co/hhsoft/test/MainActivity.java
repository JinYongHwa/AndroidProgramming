package kr.co.hhsoft.test;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    final int PERMISSION_REQUEST_CODE = 1000;

    EditText keywordEt;

    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        keywordEt=findViewById(R.id.keyword_et);
        keywordEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i== EditorInfo.IME_ACTION_SEARCH){
                    String keyword=keywordEt.getText().toString();
                    Log.d("jyh",keyword);
                    searchPlace(keyword);
                }
                return false;
            }
        });

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int fineLocation = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            if (fineLocation != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION
                }, PERMISSION_REQUEST_CODE);
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onStart() {
        super.onStart();

        int fineLocation = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        if (fineLocation == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationService = (LocationManager) getSystemService(LOCATION_SERVICE);
            locationService.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 100, this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {

        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onLocationChanged(@NonNull Location location) {
        String locationFormat=String.format("[%f,%f]",location.getLatitude(),location.getLongitude());
        Log.d("jyh",locationFormat);

        if(mMap!=null){
            CameraPosition position= CameraPosition.builder()
                    .target(new LatLng(location.getLatitude(),location.getLongitude()))
                    .zoom(15)

                    .build();

            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position));




        }
    }
    public void searchPlace(String keyword){
        OkHttpClient client=new OkHttpClient();
        String url=String.format("http://dapi.kakao.com/v2/local/search/keyword.json?query=%s&category_group_code=FD6",keyword);
        Request request=new Request.Builder()
                .get()
                .url(url)
                .header("Authorization","KakaoAK f5188ec17444d1a543de712635d0098b")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Log.d("jyh",e.toString());
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                String body=response.body().string();
                Gson gson=new Gson();
                KakaoResponse kakaoResponse=gson.fromJson(body,KakaoResponse.class);

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setMarkers(kakaoResponse.getDocuments());
                    }
                });

            }
        });
    }

    ArrayList<Marker> markerList=new ArrayList<>();
    ArrayList<KakaoLocation> mLocations;
    public void setMarkers(ArrayList<KakaoLocation> locations){
        LatLngBounds.Builder boundBuilder=LatLngBounds.builder();
        mLocations=locations;
        markerList.clear();
        for(KakaoLocation location : locations){
            Marker marker=mMap.addMarker(new MarkerOptions().position(new LatLng(location.getY(),location.getX())).title(location.getPlace_name()));
            markerList.add(marker);
            boundBuilder.include(new LatLng(location.getY(),location.getX()));

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(boundBuilder.build(),50));
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                int index=markerList.indexOf(marker);
                KakaoLocation location=mLocations.get(index);
                Log.d("jyh",location.getPlace_name());
                Intent intent=new Intent(MainActivity.this,PlaceActivity.class);
                intent.putExtra("url",location.getPlace_url());
                startActivity(intent);
                return false;
            }
        });
    }
}