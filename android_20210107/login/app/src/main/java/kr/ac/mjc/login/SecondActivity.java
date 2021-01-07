package kr.ac.mjc.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SecondActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    EditText messageEt;
    Button submitBtn;
    RecyclerView messageRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        messageEt=findViewById(R.id.message_et);
        submitBtn=findViewById(R.id.submit_btn);
        messageRv=findViewById(R.id.message_rv);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=messageEt.getText().toString();
                if(msg.length()==0){
                    return;
                }
                if(auth.getCurrentUser()==null){
                    Toast.makeText(SecondActivity.this,"로그인되있지 않은 사용자입니다",Toast.LENGTH_SHORT).show();
                    return;
                }
                String email=auth.getCurrentUser().getEmail();

                Message message=new Message();
                message.setMessage(msg);
                message.setUserId(email);

                firestore.collection("Message").document().set(message)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                messageEt.getText().clear();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SecondActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }
}
