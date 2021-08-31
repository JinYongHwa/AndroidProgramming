package kr.ac.mjc.jpstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager=findViewById(R.id.viewpager);
        TabLayout tabLayout=findViewById(R.id.tab_layout);

        MainPagerAdapter adapter=new MainPagerAdapter(getSupportFragmentManager(),0);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.baseline_home_black_48);
        tabLayout.getTabAt(1).setIcon(R.drawable.baseline_search_black_48);
        tabLayout.getTabAt(2).setIcon(R.drawable.baseline_add_circle_outline_black_48);
        tabLayout.getTabAt(3).setIcon(R.drawable.baseline_shopping_bag_black_48);
        tabLayout.getTabAt(4).setIcon(R.drawable.baseline_account_circle_black_48);

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String token=instanceIdResult.getToken();

                String email=auth.getCurrentUser().getEmail();
                firestore.collection("User").document(email).update("token",token);
            }
        });
    }

    public void movePage(int position){
        viewPager.setCurrentItem(position);
    }
}