## build.gradle
> ViewPager 는 안드로이드에 내장된 클래스가 아님으로 support 패키지를 추가해준다
``` gradle
dependencies {
    //추가
    implementation 'com.android.support:support-v4:28.0.0'
}
```

## pager_item.xml
> ViewPager의 한개 아이템에 대한 레이아웃 설정
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:id="@+id/image"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_weight="1"
        android:src="@mipmap/ic_launcher" />
</LinearLayout>
```

## activity_main.xml
``` xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <android.support.v4.view.ViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="300dp"
         />
</LinearLayout>
```

##  ImagePagerAdapter.java
> 
``` java
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ImagePagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;     //레이아웃을 가져올수있는 Inflater 인스턴스

    public ImagePagerAdapter(Context context){
        this.mContext=context;
        this.mLayoutInflater= (LayoutInflater) this.mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    int[] mResources = {
            R.drawable.suji1,
            R.drawable.suji2,
            R.drawable.suji3,
            R.drawable.suji4,
            R.drawable.suji5
    };
    
    
    //PagerAdapter의 메소드를 오버라이드해 뷰페이저에서 그려야할 전체 아이템 갯수를 반환한다
    @Override
    public int getCount() {
        return mResources.length;
    }

    //instantiateItem메소드에서 생성한 객체를 이용할 것인지 여부를 반환 한다. 
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }
    
    //ViewPager에서 사용할 아이템을 반환한다  
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //레이아웃을 리소스로부터 가져온다
        View itemView= mLayoutInflater.inflate(R.layout.pager_item,container,false);
        ImageView imageView=itemView.findViewById(R.id.image);
        //이미지뷰에 현재 아이템에 해당하는 사진을 등록한다
        imageView.setImageResource(mResources[position]);
        
        //ViewPager 컨테이너에 지금 설정한 레이아웃을 추가해준다
        container.addView(itemView);
        return itemView;
    }
    
    //View 객체를 삭제 한다.
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
```

## MainActivity.java
``` java
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager=findViewById(R.id.viewpager);
        
        //어뎁터를 생성시켜서 ViewPager 에 등록해준다
        ImagePagerAdapter adapter=new ImagePagerAdapter(this);
        viewPager.setAdapter(adapter);
        
        //어뎁터에 데이터가 바뀌었으니 다시 그리도록 
        adapter.notifyDataSetChanged();
    }
}
```
