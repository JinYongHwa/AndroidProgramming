package kr.ac.mjc.background_download_example;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.security.Permission;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button downloadBtn=findViewById(R.id.download_btn);

        downloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    int writePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    int readPermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    if(writePermission!= PackageManager.PERMISSION_GRANTED||
                            readPermission!=PackageManager.PERMISSION_GRANTED){
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
                        return;
                    }
                }
                    Intent i=new Intent(MainActivity.this,DownloadService.class);
                startService(i);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
