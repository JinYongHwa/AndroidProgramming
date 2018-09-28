package kr.ac.mjc.jyh.viewpager_example;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImagePagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;

    public ImagePagerAdapter(Context context){
        this.mContext=context;
        this.mLayoutInflater= (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    int[] mResources = {
            R.drawable.suji1,
            R.drawable.suji2,
            R.drawable.suji3,
            R.drawable.suji4,
            R.drawable.suji5
    };

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView= mLayoutInflater.inflate(R.layout.pager_item,container,false);
        ImageView imageView=itemView.findViewById(R.id.image);
        imageView.setImageResource(mResources[position]);
        container.addView(itemView);
        return itemView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
