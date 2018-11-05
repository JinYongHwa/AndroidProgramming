package kr.ac.mjc.viewpage_example;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImagePagerAdapter extends PagerAdapter{

    int[] imageList={
        R.drawable.suji1,
        R.drawable.suji2,
        R.drawable.suji3,
        R.drawable.suji4,
        R.drawable.suji5,
    };
    Context mContext;
    public ImagePagerAdapter(Context context){
        this.mContext=context;
    }

    @Override
    public int getCount() {
        return imageList.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==(View)o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater= (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.item_image,container,false);
        ImageView imageView=rootView.findViewById(R.id.image_iv);
        Drawable image=mContext.getResources().getDrawable(imageList[position]);
        imageView.setImageDrawable(image);
        container.addView(rootView);
        return rootView;
    }
}
