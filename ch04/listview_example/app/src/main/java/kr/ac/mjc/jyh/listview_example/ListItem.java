package kr.ac.mjc.jyh.listview_example;

import android.graphics.drawable.Drawable;

public class ListItem {
    private Drawable mIcon;
    private String data1;
    private String data2;
    private String data3;

    public ListItem(Drawable icon,String d1,String d2,String d3){
        this.mIcon=icon;
        this.data1=d1;
        this.data2=d2;
        this.data3=d3;
    }

    public Drawable getmIcon() {
        return mIcon;
    }

    public void setmIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }
}
