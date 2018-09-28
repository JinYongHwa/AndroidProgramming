package kr.ac.mjc.jyh.listview_example;

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
