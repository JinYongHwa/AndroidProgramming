package kr.ac.mjc.login;

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

public class JoinActivity extends AppCompatActivity {

    EditText emailEt;
    EditText passwordEt;
    EditText passwordConfirmEt;
    Button joinBtn;

    ProgressBar loadingPb;

    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        emailEt=findViewById(R.id.email_et);
        passwordEt=findViewById(R.id.password_et);
        passwordConfirmEt=findViewById(R.id.password_confirm_et);
        joinBtn=findViewById(R.id.join_btn);
        loadingPb=findViewById(R.id.loading_pb);

        auth=FirebaseAuth.getInstance();

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();
                String passwordConfirm=passwordConfirmEt.getText().toString();

                if(email.length()==0){
                    Toast.makeText(JoinActivity.this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6){
                    Toast.makeText(JoinActivity.this,"패스워드를 6자 이상 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(passwordConfirm)){
                    Toast.makeText(JoinActivity.this,"패스워드를 확인해주세요",Toast.LENGTH_SHORT).show();
                    passwordConfirmEt.getText().clear();
                    passwordConfirmEt.requestFocus();
                    return;
                }
                loadingPb.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        loadingPb.setVisibility(View.GONE);
                        String email=authResult.getUser().getEmail();
                        Toast.makeText(JoinActivity.this,email+"님 가입이 완료되었습니다",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        loadingPb.setVisibility(View.GONE);
                        Toast.makeText(JoinActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                
            }
        });

    }
}













