package com.ioa.jyh.broadcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int PERMISSION_REQUEST=2222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

            int result=checkSelfPermission(Manifest.permission.PROCESS_OUTGOING_CALLS);
            int result2=checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            if(result != PackageManager.PERMISSION_GRANTED || result2!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{
                        Manifest.permission.PROCESS_OUTGOING_CALLS,
                        Manifest.permission.RECEIVE_SMS
                        },
                        PERMISSION_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PERMISSION_REQUEST){
            Toast.makeText(this,"권한체크 완료됨",Toast.LENGTH_SHORT).show();
        }
    }
}