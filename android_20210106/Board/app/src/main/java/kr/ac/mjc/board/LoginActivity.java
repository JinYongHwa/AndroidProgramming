package kr.ac.mjc.board;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button joinBtn=findViewById(R.id.join_btn);
        Button loginBtn=findViewById(R.id.login_btn);

        EditText emailEt=findViewById(R.id.email_et);
        EditText passwordEt=findViewById(R.id.password_et);

        Handler handler=new Handler();

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
                User user= new User();
                user.setEmail(email);
                user.setPassword(password);

                BoardService boardService=BoardUtil.getInstance(LoginActivity.this).getBoardService();
                Call<Result> call=boardService.login(email,password);
                call.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, final Response<Result> response) {
                        Log.d("LoginActivity",response.toString());
                        Log.d("LoginActivity",String.valueOf(response.body().isResult()));
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                new AlertDialog.Builder(LoginActivity.this)
                                        .setMessage(response.body().getMessage())
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                boolean result=response.body().isResult();
                                                if(result){
                                                    finish();
                                                }
                                            }
                                        })
                                        .show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        Log.d("LoginActivity",t.toString());
                    }
                });

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
