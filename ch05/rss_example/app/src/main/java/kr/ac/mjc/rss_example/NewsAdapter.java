package kr.ac.mjc.rss_example;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class NewsAdapter extends BaseAdapter {

    ArrayList<Item> newsList=new ArrayList<Item>();
    Context mContext;
    public NewsAdapter(Context context,ArrayList<Item> data){
        this.mContext=context;
        this.newsList=data;
    }
    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Item getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsItemLayout itemLayout;
        if(convertView==null){
            itemLayout=new NewsItemLayout(mContext);
        }
        else{
            itemLayout= (NewsItemLayout) convertView;
        }
        Item item=getItem(position);
        itemLayout.setItem(item);



        return itemLayout;
    }
}
