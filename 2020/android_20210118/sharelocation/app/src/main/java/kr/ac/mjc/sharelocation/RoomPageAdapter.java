package kr.ac.mjc.sharelocation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import kr.ac.mjc.sharelocation.fragments.ChatFragment;
import kr.ac.mjc.sharelocation.fragments.DistanceFragment;
import kr.ac.mjc.sharelocation.fragments.LocationFragment;

public class RoomPageAdapter extends FragmentPagerAdapter {

    public RoomPageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new LocationFragment();
        }
        else if(position==1){
            return new DistanceFragment();
        }
        else{
            return new ChatFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "위치";
        }
        else if(position==1){
            return "거리";
        }
        else{
            return "채팅";
        }

    }
}
