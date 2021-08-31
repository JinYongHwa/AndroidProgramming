package kr.ac.mjc.broadcast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int REQ_PER_SMS=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int result=checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            if(result!= PackageManager.PERMISSION_GRANTED){
               requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},REQ_PER_SMS);
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);

        builder.setTitle("안내");
        builder.setMessage("정말 종료하시겠습니까?");
        builder.setPositiveButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==REQ_PER_SMS){
            for(int result: grantResults){
                if(result==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"권한을 허용했습니다",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"권한을 허용하지 않았습니다",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
}