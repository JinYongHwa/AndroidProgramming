package kr.ac.mjc.sharelocation.fragments;

import android.location.Location;
import android.media.MediaDrm;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;

import java.util.List;

import kr.ac.mjc.sharelocation.R;
import kr.ac.mjc.sharelocation.RoomActivity;
import kr.ac.mjc.sharelocation.model.User;

public class LocationFragment extends Fragment implements OnMapReadyCallback,RoomActivity.OnLocationChangeListener {

    NaverMap mNaverMap;
    LatLng myLocation;

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MapFragment mapFragment= (MapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        RoomActivity roomActivity= (RoomActivity) getActivity();
        roomActivity.setOnLocationChangeListener(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.mNaverMap=naverMap;

        if(myLocation!=null){
            LocationOverlay locationOverlay=mNaverMap.getLocationOverlay();
            locationOverlay.setVisible(true);
            locationOverlay.setPosition(myLocation);
        }

        String email=auth.getCurrentUser().getEmail();
        firestore.collection("User").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user=documentSnapshot.toObject(User.class);
                String roomId=user.getRoomId();

                firestore.collection("User").whereEqualTo("roomId",roomId)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value!=null){
                            List<DocumentChange> documentChanges=value.getDocumentChanges();
                            for(DocumentChange documentChange:documentChanges){
                                if(documentChange.getType()== DocumentChange.Type.ADDED){
                                    User user=documentChange.getDocument().toObject(User.class);
                                    if(!user.getId().equals(email) && user.getLocation()!=null){
                                        Marker marker=new Marker();
                                        marker.setPosition(new LatLng(user.getLocation().getLat(),user.getLocation().getLng()));
                                        marker.setCaptionText(user.getName());
                                        marker.setMap(mNaverMap);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });

    }


    @Override
    public void onLocationChange(Location location) {
        Log.d("LocationFragment",location.getLatitude()+","+location.getLongitude());
        myLocation=new LatLng(location.getLatitude(),location.getLongitude());

        if(mNaverMap!=null){
            LocationOverlay locationOverlay=mNaverMap.getLocationOverlay();
            locationOverlay.setVisible(true);
            locationOverlay.setPosition(myLocation);
        }

    }
}
