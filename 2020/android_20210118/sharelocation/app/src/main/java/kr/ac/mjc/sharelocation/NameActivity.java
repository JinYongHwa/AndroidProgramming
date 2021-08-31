package kr.ac.mjc.sharelocation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import kr.ac.mjc.sharelocation.model.User;

import static android.view.View.GONE;

public class NameActivity extends AppCompatActivity {

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();

    User user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        EditText nameEt=findViewById(R.id.name_et);
        Button modifyBtn=findViewById(R.id.modify_btn);
        ProgressBar loadingPb=findViewById(R.id.loading_pb);

        firestore.collection("User").document(auth.getCurrentUser().getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                user=documentSnapshot.toObject(User.class);
                nameEt.setText(user.getName());
            }
        });

        modifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameEt.getText().toString();
                if(name.length()==0){
                    Toast.makeText(NameActivity.this,"닉네임을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                loadingPb.setVisibility(View.VISIBLE);
                user.setName(name);
                user.setStatus(2);
                firestore.collection("User").document(auth.getCurrentUser().getEmail())
                        .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        loadingPb.setVisibility(GONE);
                        Intent intent=new Intent(NameActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });
    }
}















