package kr.ac.mjc.rssreader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public ViewPagerAdapter(Context context, FragmentManager fm, int behavior){
        this(fm,behavior);
        mContext=context;
    }
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NewsFragment("https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=01&plink=RSSREADER");
            case 1:
                return new NewsFragment("https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=02&plink=RSSREADER");
            case 2:
                return new NewsFragment("https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=03&plink=RSSREADER");
            case 3:
                return new NewsFragment("https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=07&plink=RSSREADER");
            default:
                return new NewsFragment("https://news.sbs.co.kr/news/SectionRssFeed.do?sectionId=08&plink=RSSREADER");
        }
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String[] categorys=mContext.getResources().getStringArray(R.array.categorys);
        return categorys[position];
//        switch (position){
//            case 0:
//                return mContext.getResources().getString(R.string.category1);
//            case 1:
//                return mContext.getResources().getString(R.string.category2);
//            case 2:
//                return mContext.getResources().getString(R.string.category3);
//            case 3:
//                return mContext.getResources().getString(R.string.category4);
//            default:
//                return mContext.getResources().getString(R.string.category5);
//
//        }
    }
}
