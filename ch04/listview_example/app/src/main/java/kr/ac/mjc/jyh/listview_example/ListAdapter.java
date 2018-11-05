package kr.ac.mjc.jyh.listview_example;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    List<ListItem> mList=new ArrayList<ListItem>();
    Context mContext;

    public ListAdapter(Context context,List<ListItem> list){
        this.mContext=context;
        this.mList=list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public ListItem getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("ListAdapter",String.format("getView[%d]",position));
        ListItemView itemView;
        if(convertView==null){
            itemView=new ListItemView(mContext);
        }
        else{
            itemView= (ListItemView) convertView;
        }
        ListItem item=getItem(position);
        itemView.setIcon(item.getmIcon());
        itemView.setData1(item.getData1());
        itemView.setData2(item.getData2());
        itemView.setData3(item.getData3());
        return itemView;
    }
}
