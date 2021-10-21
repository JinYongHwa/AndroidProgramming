package com.ioa.jyh.board;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {

    EditText emailEt;
    EditText passwordEt;
    Button loginBtn;
    ProgressBar loadingPb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEt=findViewById(R.id.email_et);
        passwordEt=findViewById(R.id.password_et);
        loginBtn=findViewById(R.id.login_btn);
        loadingPb=findViewById(R.id.loading_pb);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();

                if(email.equals("")){
                    Toast.makeText(LoginActivity.this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals("")){
                    Toast.makeText(LoginActivity.this,"패스워드를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                login(email,password);
            }
        });
    }

    public void login(String email,String password){
        loadingPb.setVisibility(View.VISIBLE);
        User user=new User();
        user.setEmail(email);
        user.setPassword(password);
        Common.getBoardService(LoginActivity.this).login(user).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Log.d("LoginActivity","onResponse");
                loadingPb.setVisibility(GONE);

                String message=response.body().getMessage();

                Toast.makeText(LoginActivity.this,message,Toast.LENGTH_SHORT).show();

                Intent resultIntent=new Intent();

                setResult(RESULT_OK,resultIntent);
                finish();

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("LoginActivity",t.toString());
            }
        });
    }

}
