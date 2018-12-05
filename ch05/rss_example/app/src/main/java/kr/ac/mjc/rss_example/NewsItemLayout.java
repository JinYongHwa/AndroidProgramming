package kr.ac.mjc.rss_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class NewsItemLayout extends LinearLayout{

    Context mContext;
    LayoutInflater mInflater;

    ImageView imageIv;
    TextView titleTv;
    TextView descriptionTv;

    public NewsItemLayout(Context context) {
        super(context);
        this.mContext=context;
        this.mInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup rootView= (ViewGroup) mInflater.inflate(R.layout.item_news,this,true);
        titleTv=rootView.findViewById(R.id.title_tv);
        descriptionTv=rootView.findViewById(R.id.description_tv);
        imageIv=rootView.findViewById(R.id.image_iv);
    }


    public void setItem(Item item){
        titleTv.setText(item.getTitle());
        descriptionTv.setText(item.getDescription());
        if(item.getEnclosure()!=null){

            imageIv.setVisibility(VISIBLE);
            Glide.with(imageIv).load(item.getEnclosure().getUrl()).into(imageIv);
        }
        else{
            imageIv.setVisibility(GONE);
            Glide.with(imageIv).clear(imageIv);
        }
    }


}
