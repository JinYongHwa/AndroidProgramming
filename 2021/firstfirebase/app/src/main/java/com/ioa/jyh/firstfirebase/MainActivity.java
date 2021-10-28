package com.ioa.jyh.firstfirebase;

import static android.view.View.GONE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity {

    EditText emailEt;
    EditText passwordEt;
    Button emailLoginBtn;
    Button joinBtn;
    Button googleLoginBtn;

    ProgressBar loadingPb;

    private FirebaseAuth mAuth;

    final int  REQ_GOOGLE_SIGNIN=1000;

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

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestIdToken("594616078139-fm1t7lslld5vg8f8bs8mgde8fqu7v6vs.apps.googleusercontent.com")
                .build();
        mAuth=FirebaseAuth.getInstance();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=mGoogleSignInClient.getSignInIntent();
                startActivityForResult(intent,REQ_GOOGLE_SIGNIN);
            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });
        emailLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();
                if(email.equals("")){
                    Toast.makeText(MainActivity.this,R.string.toast_email,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.equals("")){
                    Toast.makeText(MainActivity.this,R.string.toast_password,Toast.LENGTH_SHORT).show();
                    return;
                }
                login(email,password);

            }
        });
    }

    public void login(String email,String password){
        loadingPb.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loadingPb.setVisibility(GONE);
                        if(task.isSuccessful()){
                            updateUI(mAuth.getCurrentUser());
                        }
                        else{
                            String message=task.getException().getMessage();
                            Toast.makeText(MainActivity.this,message,Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("MainActivity", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("MainActivity", "signInWithCredential:failure", task.getException());

                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    public void updateUI(FirebaseUser user){
        if(user!=null){     //로그인 되어있을때
            Intent intent=new Intent(this,SecondActivity.class);
            startActivity(intent);
            finish();
        }
        else{   //로그인안됬을때
            
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_GOOGLE_SIGNIN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("MainActivity", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("MainActivity", "Google sign in failed", e);
            }


        }

    }
}