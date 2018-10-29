package kr.ac.mjc.rss_example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NewsItemLayout extends LinearLayout{

    Context mContext;
    LayoutInflater mInflater;

    TextView titleTv;
    TextView descriptionTv;

    public NewsItemLayout(Context context) {
        super(context);
        this.mContext=context;
        this.mInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewGroup rootView= (ViewGroup) mInflater.inflate(R.layout.item_news,this,true);
        titleTv=rootView.findViewById(R.id.title_tv);
        descriptionTv=rootView.findViewById(R.id.description_tv);
    }


    public void setItem(Item item){
        titleTv.setText(item.getTitle());
        descriptionTv.setText(item.getDescription());
    }
}
