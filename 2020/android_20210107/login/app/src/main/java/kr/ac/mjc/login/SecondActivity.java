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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore firestore;

    EditText messageEt;
    Button submitBtn;
    RecyclerView messageRv;

    List<Message> mMessageList=new ArrayList<>();
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        messageEt=findViewById(R.id.message_et);
        submitBtn=findViewById(R.id.submit_btn);
        messageRv=findViewById(R.id.message_rv);

        Button logoutBtn=findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent=new Intent(SecondActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        messageAdapter=new MessageAdapter(this,mMessageList,auth.getCurrentUser());
        messageRv.setAdapter(messageAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        messageRv.setLayoutManager(linearLayoutManager);

        firestore.collection("Message").orderBy("sendDate", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                             for(DocumentChange documentChange:value.getDocumentChanges()){
                                if(documentChange.getType()== DocumentChange.Type.ADDED){
                                    Message message=documentChange.getDocument().toObject(Message.class);
                                    mMessageList.add(message);
                                }
                             }
                             messageAdapter.notifyDataSetChanged();
                             messageRv.scrollToPosition(mMessageList.size()-1);
                    }
                });




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
