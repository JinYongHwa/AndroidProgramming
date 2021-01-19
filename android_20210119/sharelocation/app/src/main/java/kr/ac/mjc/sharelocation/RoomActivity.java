package kr.ac.mjc.sharelocation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import kr.ac.mjc.sharelocation.model.Room;

public class RoomActivity extends AppCompatActivity implements LocationListener {

    public final static String NAME_ROOM = "room";
    Room mRoom;

    final int REQ_PERMISSION = 20000;
    LocationManager mLocationManager;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    public kr.ac.mjc.sharelocation.model.Location myLocation;

    private OnLocationChangeListener mOnLocationChagneListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        ViewPager viewpager = findViewById(R.id.viewpager);
        TabLayout tablayout = findViewById(R.id.tablayout);

        RoomPageAdapter adapter = new RoomPageAdapter(getSupportFragmentManager(), 0);
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        mRoom = (Room) getIntent().getSerializableExtra(NAME_ROOM);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int fineStatus = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            int coarseStatus = checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
            if (fineStatus != PackageManager.PERMISSION_GRANTED || coarseStatus != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, REQ_PERMISSION);
            }
        }
        startLocationTracking();
    }

    public void startLocationTracking() {
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
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startLocationTracking();
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

        String email=auth.getCurrentUser().getEmail();
        myLocation=new kr.ac.mjc.sharelocation.model.Location(location.getLatitude(),location.getLongitude());
        firestore.collection("User").document(email).update("location",myLocation);

        if(this.mOnLocationChagneListener!=null){
            this.mOnLocationChagneListener.onLocationChange(location);
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    public void setOnLocationChangeListener(OnLocationChangeListener onLocationChangeListener){
        this.mOnLocationChagneListener=onLocationChangeListener;
    }

    public interface OnLocationChangeListener{
        public void onLocationChange(Location location);
    }
}
