package kr.ac.mjc.sharelocation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import kr.ac.mjc.sharelocation.model.User;

public class LoginActivity extends AppCompatActivity {
    ProgressBar loadingPb;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    final int REQ_GOOGLE_LOGIN=1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginBtn=findViewById(R.id.login_btn);
        loadingPb=findViewById(R.id.loading_pb);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail()
                .build();

        GoogleSignInClient client= GoogleSignIn.getClient(this,gso);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=client.getSignInIntent();
                startActivityForResult(intent,REQ_GOOGLE_LOGIN);
            }
        });

        FirebaseUser user=auth.getCurrentUser();
        if(user!=null){
            loadingPb.setVisibility(View.VISIBLE);
            loginBtn.setVisibility(View.GONE);
            firestore.collection("User").document(user.getEmail()).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            User tmpUser=documentSnapshot.toObject(User.class);
                            if(tmpUser.getStatus()==1){
                                moveName();
                            }
                            else if(tmpUser.getStatus()==2){
                                moveMain();
                            }
                        }
                    });
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            User user=new User();
                            user.setId(firebaseUser.getEmail());
                            user.setName(firebaseUser.getDisplayName());
                            firestore.collection("User")
                                    .document(user.getId()).get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Log.d("LoginActivity","success");
                                    User tmpUser=documentSnapshot.toObject(User.class);
                                    if(tmpUser==null){
                                        Log.d("LoginActivity","first login");
                                        firestore.collection("User").document(user.getId())
                                                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                moveName();
                                            }
                                        });
                                    }
                                    else{
                                        if(tmpUser.getStatus()==1){
                                            moveName();
                                        }
                                        else if(tmpUser.getStatus()==2){
                                            moveMain();
                                        }

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("LoginActivity","failure");
                                }
                            });

                        } else {
                            // If sign in fails, display a message to the user.


                        }

                        // ...
                    }
                });
    }

    public void moveName(){
        Intent intent=new Intent(LoginActivity.this, NameActivity.class);
        startActivity(intent);
        finish();
    }

    public void moveMain(){
        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GOOGLE_LOGIN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {

            }
        }
    }
}
