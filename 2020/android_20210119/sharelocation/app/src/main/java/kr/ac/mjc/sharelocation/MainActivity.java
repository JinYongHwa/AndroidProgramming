package kr.ac.mjc.sharelocation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import kr.ac.mjc.sharelocation.model.Room;
import kr.ac.mjc.sharelocation.model.User;

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

        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        FirebaseAuth auth=FirebaseAuth.getInstance();
        firestore.collection("User").document(auth.getCurrentUser().getEmail()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User user=documentSnapshot.toObject(User.class);
                        if(user.getRoomId()!=null){
                           firestore.collection("Room").document(user.getRoomId())
                                   .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                               @Override
                               public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Room room=documentSnapshot.toObject(Room.class);
                                    if(room!=null){
                                        Intent intent=new Intent(MainActivity.this,RoomActivity.class);
                                        intent.putExtra(RoomActivity.NAME_ROOM,room);
                                        startActivity(intent);
                                    }
                               }
                           });
                        }
                    }
                });
    }
}