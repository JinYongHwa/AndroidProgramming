package kr.ac.mjc.jpstagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import static android.content.Intent.ACTION_PICK;
import static android.view.View.GONE;

public class ProfileActivity extends AppCompatActivity {

    final int REQ_IMAGE=1234;
    ImageView profileIv;
    ProgressBar loadingPb;

    Uri tmpProfile=null;
    User mUser;

    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseStorage storage=FirebaseStorage.getInstance();
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileIv=findViewById(R.id.profile_iv);
        EditText nameEt=findViewById(R.id.name_et);
        Button profileBtn=findViewById(R.id.profile_btn);
        loadingPb=findViewById(R.id.loading_pb);

        startLoading();
        firestore.collection("User").document(auth.getCurrentUser().getEmail())
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                endLoading();
                mUser=documentSnapshot.toObject(User.class);
                nameEt.setText(mUser.getName());
                if(mUser.getProfileUrl()!=null){
                    Glide.with(profileIv).load(mUser.getProfileUrl()).into(profileIv);
                }
            }
        });

        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQ_IMAGE);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nameEt.getText().toString();
                if(name.length()==0){
                    Toast.makeText(ProfileActivity.this,R.string.toast_name_check,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tmpProfile==null&&mUser.getProfileUrl()==null){
                    Toast.makeText(ProfileActivity.this,R.string.toast_profile_check,Toast.LENGTH_SHORT).show();
                    return;
                }
                startLoading();
                if(tmpProfile==null){
                    mUser.setName(name);
                    firestore.collection("User").document(mUser.getEmail())
                            .set(mUser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            endLoading();
                            setResult(RESULT_OK);
                            finish();
                        }
                    });
                }
                else{
                    storage.getReference().child("profile").child(auth.getCurrentUser().getEmail()).putFile(tmpProfile)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            User user =new User();
                                            user.setEmail(auth.getCurrentUser().getEmail());
                                            user.setName(name);
                                            user.setProfileUrl(uri.toString());

                                            firestore.collection("User").document(auth.getCurrentUser().getEmail()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    endLoading();
                                                    setResult(RESULT_OK);
                                                    finish();
                                                }
                                            });

                                        }
                                    });
                                }
                            });
                }


            }
        });
    }

    public void startLoading(){
        loadingPb.setVisibility(View.VISIBLE);
    }

    public void endLoading(){
        loadingPb.setVisibility(GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_IMAGE && resultCode==RESULT_OK){
            tmpProfile=data.getData();
            profileIv.setImageURI(tmpProfile);
        }
    }
}
