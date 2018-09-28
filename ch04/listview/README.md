

## list_item.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">


    <ImageView
        android:id="@+id/icon_iv"
        android:layout_width="80dp"
        android:layout_height="80dp"
        android:src="@mipmap/ic_launcher_round" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

        <TextView
            android:id="@+id/data1_tv"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:gravity="center_vertical|center_horizontal"
            android:text="TextView" />

        <LinearLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1"
            android:orientation="horizontal">

            <TextView
                android:id="@+id/data2_tv"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:gravity="center_vertical|center_horizontal"
                android:text="TextView" />

            <TextView
                android:id="@+id/data3_tv"
                android:layout_width="wrap_content"
                android:layout_height="match_parent"
                android:layout_weight="1"
                android:gravity="center_vertical|center_horizontal"
                android:text="TextView" />
        </LinearLayout>
    </LinearLayout>
</LinearLayout>
```

## activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <ListView
        android:id="@+id/listview"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</android.support.constraint.ConstraintLayout>
```

## ListItem.java
``` java
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
```

## ListItemView.java
``` java
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
```

## ListAdapter.java
``` java
import android.content.Context;
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
```

## MainActivity.java
``` java
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    ListView mListView;
    List<ListItem> mList=new ArrayList<ListItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView=findViewById(R.id.listview);

        for(int i=0;i<100;i++){
            Drawable icon;
            if(i%2==0){
                icon=getResources().getDrawable(R.drawable.img1);
            }
            else{
                icon=getResources().getDrawable(R.drawable.img2);
            }

            mList.add(new ListItem(icon,"제목"+i,"설명"+i,"가격"+i));
        }

        final ListAdapter adapter=new ListAdapter(this,mList);

        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem item=adapter.getItem(position);
                Toast.makeText(MainActivity.this, item.getData1(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```
