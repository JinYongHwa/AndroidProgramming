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
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;

import java.util.HashMap;
import java.util.List;

import kr.ac.mjc.sharelocation.R;
import kr.ac.mjc.sharelocation.RoomActivity;
import kr.ac.mjc.sharelocation.model.Room;
import kr.ac.mjc.sharelocation.model.User;

public class LocationFragment extends Fragment implements OnMapReadyCallback,RoomActivity.OnLocationChangeListener {

    NaverMap mNaverMap;
    LatLng myLocation;

    HashMap<String,Marker> mMarkerStore=new HashMap<>();

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

                firestore.collection("Room").document(roomId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Room room=documentSnapshot.toObject(Room.class);
                        Marker marker=new Marker();
                        marker.setPosition(new LatLng(room.getTargetLocation().getLat(),room.getTargetLocation().getLng()));
                        marker.setIcon(OverlayImage.fromResource(R.drawable.baseline_location_on_black_48));
                        marker.setMap(mNaverMap);
                        mMarkerStore.put("target",marker);
                        setBound();
                    }
                });

                firestore.collection("User")
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value!=null){
                            List<DocumentChange> documentChanges=value.getDocumentChanges();
                            for(DocumentChange documentChange:documentChanges){
                                User user=documentChange.getDocument().toObject(User.class);

                                if(user.getRoomId().equals(roomId)){
                                    if(documentChange.getType()== DocumentChange.Type.ADDED){

                                        if(!user.getId().equals(email) && user.getLocation()!=null){
                                            Marker marker=new Marker();
                                            marker.setPosition(new LatLng(user.getLocation().getLat(),user.getLocation().getLng()));
                                            marker.setCaptionText(user.getName());
                                            marker.setMap(mNaverMap);
                                            mMarkerStore.put(user.getId(),marker);
                                        }
                                        else if(user.getId().equals(email)&& user.getLocation()!=null){
                                            LocationOverlay locationOverlay=mNaverMap.getLocationOverlay();
                                            locationOverlay.setPosition(new LatLng(user.getLocation().getLat(),user.getLocation().getLng()));
                                            locationOverlay.setVisible(true);

                                            Marker marker=new Marker();
                                            marker.setPosition(new LatLng(user.getLocation().getLat(),user.getLocation().getLng()));
                                            mMarkerStore.put(user.getId(),marker);
                                        }
                                    }
                                    if(documentChange.getType()== DocumentChange.Type.MODIFIED){

                                        if(!user.getId().equals(email) && user.getLocation()!=null){
                                            Marker marker=mMarkerStore.get(user.getId());
                                            if(marker!=null){
                                                marker.setPosition(new LatLng(user.getLocation().getLat(),user.getLocation().getLng()));
                                                marker.setCaptionText(user.getName());
                                            }



                                        }
                                    }
                                    if(documentChange.getType()== DocumentChange.Type.REMOVED){
                                        if(!user.getId().equals(email) && user.getLocation()!=null){
                                            Marker marker=mMarkerStore.get(user.getId());
                                            marker.setMap(null);
                                            mMarkerStore.remove(user.getId());
                                        }
                                    }
                                }
                                else{
                                    Marker marker=mMarkerStore.get(user.getId());
                                    if(marker!=null){
                                        marker.setMap(null);
                                        mMarkerStore.remove(user.getId());
                                    }

                                }

                            }

                            setBound();
                        }
                    }
                });
            }
        });

    }
    public void setBound(){
        double maxLat=-90;
        double minLat=90;
        double maxLng=-180;
        double minLng=180;

        for(String key:mMarkerStore.keySet()){

            Marker marker=mMarkerStore.get(key);
            LatLng position=marker.getPosition();
            if(maxLat<position.latitude){
                maxLat=position.latitude;
            }
            if(minLat>position.latitude){
                minLat= position.latitude;
            }
            if(maxLng<position.longitude){
                maxLng=position.longitude;
            }
            if(minLng>position.longitude){
                minLng=position.longitude;
            }
        }

        LatLngBounds bound=new LatLngBounds(new LatLng(minLat,minLng),new LatLng(maxLat,maxLng));
        mNaverMap.moveCamera(CameraUpdate.fitBounds(bound,150));
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
