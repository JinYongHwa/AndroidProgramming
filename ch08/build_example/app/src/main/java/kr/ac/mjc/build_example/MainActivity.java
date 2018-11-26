package kr.ac.mjc.build_example;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView versionTv=findViewById(R.id.version_tv);
        try {
            PackageInfo info=getPackageManager().getPackageInfo(getPackageName(),0);
            int versionCode=info.versionCode;
            String versionName=info.versionName;
            String message=String.format("현재앱의 버전코드는 %d이고 버전명은 %s 입니다",versionCode,versionName);
            versionTv.setText(message);
            Log.d("test",message);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
