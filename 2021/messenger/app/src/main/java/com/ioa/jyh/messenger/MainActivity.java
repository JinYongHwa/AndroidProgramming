package com.ioa.jyh.messenger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseFirestore firestore=FirebaseFirestore.getInstance();

        EditText writerEt=findViewById(R.id.writer_et);
        Button enterBtn=findViewById(R.id.enter_btn);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String writer=writerEt.getText().toString();
                if(writer.equals("")){
                    Toast.makeText(MainActivity.this,"대화명을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent=new Intent(MainActivity.this,ChatActivity.class);
                intent.putExtra("writer",writer);
                startActivity(intent);
            }
        });

    }
}