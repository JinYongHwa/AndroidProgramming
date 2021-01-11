package kr.ac.mjc.jpstagram;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        EditText nameEt=findViewById(R.id.name_et);
        EditText emailEt=findViewById(R.id.email_et);
        EditText passwordEt=findViewById(R.id.password_et);
        EditText passwordConfirmEt=findViewById(R.id.password_confirm_et);
        Button joinBtn=findViewById(R.id.join_btn);
        ProgressBar loadingPb=findViewById(R.id.loading_pb);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameEt.getText().toString();
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();
                String passwordConfirm=passwordConfirmEt.getText().toString();


                if(email.length()==0){
                    Toast.makeText(JoinActivity.this,R.string.toast_email_check,Toast.LENGTH_SHORT).show();
                    emailEt.requestFocus();
                    return;
                }
                if(name.length()==0){
                    Toast.makeText(JoinActivity.this,R.string.toast_name_check,Toast.LENGTH_SHORT).show();
                    nameEt.requestFocus();
                    return;
                }

                if(password.length()<6){
                    Toast.makeText(JoinActivity.this,R.string.toast_password_check,Toast.LENGTH_SHORT).show();
                    passwordEt.requestFocus();
                    return;
                }
                if(!password.equals(passwordConfirm)){
                    Toast.makeText(JoinActivity.this,R.string.toast_password_confirm_check,Toast.LENGTH_SHORT).show();
                    passwordConfirmEt.getText().clear();
                    passwordConfirmEt.requestFocus();
                    return;
                }
                FirebaseAuth auth= FirebaseAuth.getInstance();
                loadingPb.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                FirebaseFirestore firestore=FirebaseFirestore.getInstance();
                                User user=new User();
                                user.setEmail(email);
                                user.setName(name);
                                firestore.collection("User").document(email).set(user)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                loadingPb.setVisibility(View.GONE);
                                                Toast.makeText(JoinActivity.this,R.string.toast_join_success,Toast.LENGTH_SHORT).show();
                                                finish();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                loadingPb.setVisibility(View.GONE);
                                Toast.makeText(JoinActivity.this,R.string.toast_join_fail,Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
