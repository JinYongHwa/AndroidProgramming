
## fragment_selecter.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <Button
        android:id="@+id/first_btn"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="첫번째이미지" />

    <Button
        android:id="@+id/second_btn"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="두번째이미지" />

</LinearLayout>
```

## fragment_viewer.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:id="@+id/image"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</LinearLayout>
```

## SelecterFragment.java
``` java
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SelecterFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.fragment_selecter,container,false);
        Button firstBtn=rootView.findViewById(R.id.first_btn);
        Button secondBtn=rootView.findViewById(R.id.second_btn);
        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.setImage(0);
            }
        });
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.setImage(1);
            }
        });
        return rootView;

    }
}
```

## ViewerFragment.java
``` xml
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewerFragment extends Fragment {

    ImageView imageView;
    int[] imageList={R.drawable.image1,R.drawable.image2};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.fragment_viewer,container,false);
        imageView=rootView.findViewById(R.id.image);
        return rootView;
    }

    public void setImage(int index){
        imageView.setImageResource(imageList[index]);
    }
}
```


## activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".MainActivity">

    <fragment
        android:id="@+id/selecter_fragment"
        android:name="kr.ac.mjc.jyh.fragment_example.SelecterFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1"></fragment>

    <fragment
        android:id="@+id/viewer_fragment"
        android:name="kr.ac.mjc.jyh.fragment_example.ViewerFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1"></fragment>
</LinearLayout>
```

## MainActivity.java
``` java
import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void setImage(int index){
        ViewerFragment vf= (ViewerFragment) getFragmentManager().findFragmentById(R.id.viewer_fragment);
        vf.setImage(index);
    }
}
```
