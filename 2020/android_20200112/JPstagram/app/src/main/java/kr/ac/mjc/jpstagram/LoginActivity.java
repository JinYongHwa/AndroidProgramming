package kr.ac.mjc.jpstagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.view.View.VISIBLE;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        moveMain();

        EditText emailEt=findViewById(R.id.email_et);
        EditText passwordEt=findViewById(R.id.password_et);

        Button loginBtn=findViewById(R.id.login_btn);
        Button joinBtn=findViewById(R.id.join_btn);
        ProgressBar loadingPb=findViewById(R.id.loading_pb);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();
                if(email.length()==0){
                    Toast.makeText(LoginActivity.this,R.string.toast_email_check,Toast.LENGTH_SHORT).show();
                    emailEt.requestFocus();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(LoginActivity.this,R.string.toast_password_check,Toast.LENGTH_SHORT).show();
                    passwordEt.requestFocus();
                    return;
                }
                FirebaseAuth auth=FirebaseAuth.getInstance();
                loadingPb.setVisibility(VISIBLE);
                auth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                loadingPb.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this,R.string.toast_login_success,Toast.LENGTH_SHORT).show();
                                moveMain();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingPb.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this,R.string.toast_login_fail,Toast.LENGTH_SHORT).show();
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

    public void moveMain(){
        FirebaseAuth auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser()!=null){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
