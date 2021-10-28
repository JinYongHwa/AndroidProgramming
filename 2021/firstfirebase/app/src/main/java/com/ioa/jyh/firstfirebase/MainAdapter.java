package com.ioa.jyh.firstfirebase;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class MainAdapter extends FragmentPagerAdapter {
    public MainAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new AddFragment();
            case 3:
                return new ShopFragment();
            default:
                return new ProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
