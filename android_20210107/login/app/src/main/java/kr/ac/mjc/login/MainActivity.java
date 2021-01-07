package kr.ac.mjc.login;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
    FirebaseAuth auth;

    final int LOGIN_GOOGLE=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEt=findViewById(R.id.email_et);
        passwordEt=findViewById(R.id.password_et);
        emailLoginBtn=findViewById(R.id.email_login_btn);
        joinBtn=findViewById(R.id.join_btn);
        googleLoginBtn=findViewById(R.id.google_login_btn);

        loadingPb=findViewById(R.id.loading_pb);
        auth=FirebaseAuth.getInstance();
        loginSuccess();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .build();

        final GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,gso);

        googleLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=googleSignInClient.getSignInIntent();
                startActivityForResult(intent,LOGIN_GOOGLE);
            }
        });



        emailLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEt.getText().toString();
                String password=passwordEt.getText().toString();
                loadingPb.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email,password)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                loginSuccess();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("MainActivity",e.toString());
                                Toast.makeText(MainActivity.this,"로그인에 실패하였습니다",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    public void loginSuccess(){
        FirebaseUser loginUser=auth.getCurrentUser();
        if(loginUser!=null){
            Intent intent=new Intent(MainActivity.this,SecondActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            loginSuccess();
                        } else {
                            Toast.makeText(MainActivity.this, "인증이 실패하였습니다", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==LOGIN_GOOGLE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Log.d("MainActivity",e.toString());
                // Google Sign In failed, update UI appropriately

                // ...
            }

        }
    }
}