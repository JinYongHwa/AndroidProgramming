package kr.ac.mjc.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class SecondActivity extends AppCompatActivity implements MessageAdapter.OnMessageClickListener {

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
        messageAdapter.setOnMessageClickListener(this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        messageRv.setLayoutManager(linearLayoutManager);

        firestore.collection("Message").orderBy("sendDate", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                             for(DocumentChange documentChange:value.getDocumentChanges()){
                                if(documentChange.getType()== DocumentChange.Type.ADDED){
                                    Message message=documentChange.getDocument().toObject(Message.class);
                                    String id=documentChange.getDocument().getId();
                                    Log.d("SecondActivity",id);
                                    message.setId(id);
                                    mMessageList.add(message);
                                    messageAdapter.notifyDataSetChanged();
                                    messageRv.scrollToPosition(mMessageList.size()-1);
                                }
                                if(documentChange.getType()== DocumentChange.Type.REMOVED){
                                    String id=documentChange.getDocument().getId();
                                    for(Message message:mMessageList){
                                        if(message.getId().equals(id)){
                                            mMessageList.remove(message);
                                            break;
                                        }
                                    }
                                    messageAdapter.notifyDataSetChanged();
                                }
                                if(documentChange.getType()== DocumentChange.Type.MODIFIED){
                                    String id=documentChange.getDocument().getId();
                                    Message message=documentChange.getDocument().toObject(Message.class);
                                    for(Message findMessage:mMessageList){
                                        if(findMessage.getId().equals(id)){
                                            findMessage.setMessage(message.getMessage());
                                        }
                                    }
                                    messageAdapter.notifyDataSetChanged();
                                }
                             }

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
    public void modifyDialog(final Message message){
        final EditText editText=new EditText(this);
        new AlertDialog.Builder(this)
                .setTitle("메세지 수정")
                .setView(editText)
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String msg=editText.getText().toString();
                        if(msg.length()==0){
                            return;
                        }
                        message.setMessage(msg);
                        firestore.collection("Message").document(message.getId()).update("message",message.getMessage());

                    }
                })
                .show();
    }

    @Override
    public void onLongClick(final Message message) {
        Log.d("SecondActivity",message.getMessage());
        new AlertDialog.Builder(this)
                .setTitle("선택")
                .setItems(new String[]{"삭제", "수정"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            firestore.collection("Message").document(message.getId()).delete();
                        }
                        if(which==1){
                            modifyDialog(message);
                        }
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();

    }
}
