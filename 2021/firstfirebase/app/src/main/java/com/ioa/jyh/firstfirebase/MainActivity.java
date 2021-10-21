package com.ioa.jyh.firstfirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

public class MainActivity extends AppCompatActivity {

    EditText emailEt;
    EditText passwordEt;
    Button emailLoginBtn;
    Button joinBtn;
    Button googleLoginBtn;

    ProgressBar loadingPb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEt=findViewById(R.id.email_et);
        passwordEt=findViewById(R.id.password_et);
        emailLoginBtn=findViewById(R.id.email_login_btn);
        joinBtn=findViewById(R.id.join_btn);
        loadingPb=findViewById(R.id.loading_pb);
        googleLoginBtn=findViewById(R.id.google_login_btn);

//        GoogleSignInOptions gso = new GoogleSignInOptions
//                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
//                .requestEmail()
//                .build();

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });
    }
}