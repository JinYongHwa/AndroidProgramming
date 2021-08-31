package kr.ac.mjc.twich_example;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class GameAdapter extends BaseAdapter {

    Context mContext;
    ArrayList<Top> mGameList;

    public GameAdapter(Context context,ArrayList<Top> gameList){
        this.mContext=context;
        this.mGameList=gameList;
    }

    @Override
    public int getCount() {
        return mGameList.size();
    }

    @Override
    public Top getItem(int position) {
        return mGameList.get(position);
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
