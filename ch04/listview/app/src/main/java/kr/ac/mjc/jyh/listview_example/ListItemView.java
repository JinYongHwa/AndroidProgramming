package kr.ac.mjc.jyh.listview_example;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListItemView extends LinearLayout {

    ImageView mIconIV;
    TextView data1TV;
    TextView data2TV;
    TextView data3TV;

    public ListItemView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item, this, true);

        mIconIV=findViewById(R.id.icon_iv);
        data1TV=findViewById(R.id.data1_tv);
        data2TV=findViewById(R.id.data2_tv);
        data3TV=findViewById(R.id.data3_tv);

    }

    public void setIcon(Drawable icon){
        mIconIV.setImageDrawable(icon);
    }
    public void setData1(String data){
        data1TV.setText(data);
    }
    public void setData2(String data){
        data2TV.setText(data);
    }
    public void setData3(String data){
        data3TV.setText(data);
    }
}
