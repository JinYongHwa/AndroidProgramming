package kr.co.hhsoft.twitch_example;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class GameAdapter extends BaseAdapter {

    ArrayList<Top> mTopList;
    Context mContext;

    public GameAdapter(Context context, ArrayList<Top> tl){
        this.mContext=context;
        this.mTopList=tl;
    }

    @Override
    public int getCount() {
        return mTopList.size();
    }

    @Override
    public Top getItem(int position) {
        return mTopList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GameItemLayout itemLayout;
        if(convertView==null){
            itemLayout=new GameItemLayout(mContext);
        }
        else{
            itemLayout= (GameItemLayout) convertView;
        }
        Top top=getItem(position);
        itemLayout.setTop(top);

        return itemLayout;
    }
}
