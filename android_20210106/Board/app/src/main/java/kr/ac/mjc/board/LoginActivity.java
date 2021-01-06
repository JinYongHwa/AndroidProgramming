package kr.ac.mjc.board;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button joinBtn=findViewById(R.id.join_btn);
        Button loginBtn=findViewById(R.id.login_btn);

        EditText emailEt=findViewById(R.id.email_et);
        EditText passwordEt=findViewById(R.id.password_et);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();
                if(email.length()==0){
                    Toast.makeText(LoginActivity.this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()==0){
                    Toast.makeText(LoginActivity.this,"패스워드를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });


    }
}
