package com.ioa.jyh.firstfirebase;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.ACTION_PICK;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class AddFragment extends Fragment {

    ImageView imageIv;
    EditText messageEt;
    Button submitBtn;

    ProgressBar loadingPb;

    final int REQ_IMAGE=9999;

    Uri selectedImage;

    FirebaseStorage storage=FirebaseStorage.getInstance();
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageIv=view.findViewById(R.id.image_iv);
        messageEt=view.findViewById(R.id.message_et);
        submitBtn=view.findViewById(R.id.submit_btn);
        loadingPb=view.findViewById(R.id.loading_pb);

        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQ_IMAGE);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedImage==null){
                    Toast.makeText(getContext(),R.string.toast_select_image,Toast.LENGTH_SHORT).show();
                    return;
                }
                String message=messageEt.getText().toString();
                if(message.equals("")){
                    Toast.makeText(getContext(),R.string.toast_write_message,Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d("AddFragment","upload");
                loadingPb.setVisibility(View.VISIBLE);
                String fileName= UUID.randomUUID().toString();
                storage.getReference().child("post").child(fileName).putFile(selectedImage)
                        .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                Log.d("AddFragment","success");
                                if(task.isSuccessful()){
                                    task.getResult()
                                            .getMetadata()
                                            .getReference()
                                            .getDownloadUrl()
                                            .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    Uri uri=task.getResult();
                                                    Log.d("AddFragment",uri.toString());

                                                    completeImage(uri.toString());
                                                }
                                            });

                                }
                            }
                        });

            }
        });
    }
    public void completeImage(String url){
        Post post=new Post();
        post.setImageUrl(url);

        FirebaseUser user=auth.getCurrentUser();
        post.setUserId(user.getEmail());

        String message=messageEt.getText().toString();
        post.setMessage(message);

        firestore.collection("post").document().set(post)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        loadingPb.setVisibility(View.GONE);
                        selectedImage=null;
                        imageIv.setImageDrawable(getActivity().getDrawable(R.drawable.outline_add_box_black_48));
                        messageEt.setText("");
                    }
                });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_IMAGE&&resultCode==RESULT_OK){
            selectedImage=data.getData();
            imageIv.setImageURI(selectedImage);
        }
    }
}
