package kr.ac.mjc.storage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements ImageAdapter.OnImageClickListener {

    FirebaseFirestore firestore;
    FirebaseStorage storage;

    Button imageBtn;
    RecyclerView listRv;

    final int REQ_IMAGE=1000;

    List<Image> mImageList=new ArrayList<>();
    ImageAdapter mAdapter=new ImageAdapter(this,mImageList);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firestore=FirebaseFirestore.getInstance();
        storage=FirebaseStorage.getInstance();

        imageBtn=findViewById(R.id.image_btn);
        listRv=findViewById(R.id.list_rv);

        listRv.setAdapter(mAdapter);

        mAdapter.setOnImageClickListener(this);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
        listRv.setLayoutManager(gridLayoutManager);

        firestore.collection("Image").orderBy("date", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange dc:value.getDocumentChanges()){
                            if(dc.getType()== DocumentChange.Type.ADDED){
                                Image image=dc.getDocument().toObject(Image.class);
                                mImageList.add(0,image);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });


        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQ_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_IMAGE && resultCode==RESULT_OK){
            Uri imageUri=data.getData();
            String fileName= UUID.randomUUID().toString();
            storage.getReference().child("image").child(fileName).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                                Log.d("MainActivity",uri.toString());
                                Image image=new Image();
                                image.setImageUrl(uri.toString());
                                firestore.collection("Image").document().set(image);
                             }
                         });
                }
            });
        }
    }

    @Override
    public void onClick(Image image) {
        Intent intent=new Intent(this,ImageActivity.class);
        intent.putExtra("image",image);
        startActivity(intent);
    }
}