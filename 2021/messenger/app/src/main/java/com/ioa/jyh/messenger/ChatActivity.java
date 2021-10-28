package com.ioa.jyh.messenger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements ChatAdapter.OnChatListener {

    EditText messageEt;
    Button submitBtn;

    RecyclerView chatlistRv;

    String writer;

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    List<Message> mMessageList=new ArrayList<>();
    ChatAdapter mChatAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messageEt=findViewById(R.id.message_et);
        submitBtn=findViewById(R.id.submit_btn);
        chatlistRv=findViewById(R.id.chatlist_rv);

        writer=getIntent().getStringExtra("writer");

        mChatAdapter=new ChatAdapter(this,mMessageList,writer);
        mChatAdapter.setOnChatListener(this);

        chatlistRv.setAdapter(mChatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        chatlistRv.setLayoutManager(layoutManager);

        firestore.collection("message")

                .orderBy("date", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for(DocumentChange documentChange:value.getDocumentChanges()){
                            if(documentChange.getType() == DocumentChange.Type.ADDED){
                                Message message=documentChange.getDocument().toObject(Message.class);
                                mMessageList.add(message);

                            }
                        }
                        mChatAdapter.notifyDataSetChanged();
                        chatlistRv.scrollToPosition(mMessageList.size()-1);


                    }
                });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageText=messageEt.getText().toString();
                if(messageText.equals("")){
                    return;
                }
                Message message=new Message();
                message.setMessage(messageText);
                message.setWriter(writer);
                firestore.collection("message").add(message)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            messageEt.setText("");
                        }
                    }
                });
            }
        });

    }


    @Override
    public void onLongClick(Message message) {
        new AlertDialog.Builder(this)
                .setItems(R.array.functions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }
}
