package kr.ac.mjc.jyh.boradcastreceiver_example;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int permission=checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            if(permission!=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==1){

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Contact Permission is Granted", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
