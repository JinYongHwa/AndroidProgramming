package kr.ac.mjc.test1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    MainActivity instance=this;

    ImageView imageIv;

    final int REQ_IMAGE_PICK=1003;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MainActivity","onCreate");
        setContentView(R.layout.activity_main);
        EditText idEt = findViewById(R.id.id_et);
        EditText passwordEt=findViewById(R.id.password_et);
        Button loginBtn=findViewById(R.id.login_btn);
        Button callBtn=findViewById(R.id.call_btn);
        Button imageBtn=findViewById(R.id.image_btn);
        imageIv=findViewById(R.id.image_iv);


        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01029730833"));
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=idEt.getText().toString();
               Intent intent=new Intent(instance, SecondActivity.class);

               intent.putExtra("id",id);
               //startActivity(intent);
                startActivityForResult(intent,100);
            }
        });
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQ_IMAGE_PICK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==100 && resultCode==RESULT_OK){
            String name=data.getStringExtra("name");
            Toast.makeText(instance,name,Toast.LENGTH_SHORT).show();
        }
        if(requestCode==REQ_IMAGE_PICK&& resultCode==RESULT_OK){
            Uri imageUri=data.getData();
            imageIv.setImageURI(imageUri);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy");
    }
}