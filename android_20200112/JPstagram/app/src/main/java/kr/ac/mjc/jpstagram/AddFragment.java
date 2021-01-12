package kr.ac.mjc.jpstagram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.ACTION_PICK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AddFragment extends Fragment {

    ImageView imageIv;
    EditText textEt;
    Button uploadBtn;
    ProgressBar loadingPb;

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseStorage storage=FirebaseStorage.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();

    final int REQ_IMAGE=1000;

    Uri tmpImage=null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageIv=view.findViewById(R.id.image_iv);
        textEt=view.findViewById(R.id.text_et);
        uploadBtn=view.findViewById(R.id.upload_btn);
        loadingPb=view.findViewById(R.id.loading_pb);

        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQ_IMAGE);
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=textEt.getText().toString();
                if(text.length()==0){
                    Toast.makeText(getActivity(),R.string.toast_text_check,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(tmpImage==null){
                    Toast.makeText(getActivity(),R.string.toast_image_check,Toast.LENGTH_SHORT).show();
                    return;
                }
                loadingPb.setVisibility(VISIBLE);
                String fileName= UUID.randomUUID().toString();
                storage.getReference().child("post").child(fileName).putFile(tmpImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Post post=new Post();
                                        post.setImageUrl(uri.toString());
                                        post.setText(text);
                                        post.setUserId(auth.getCurrentUser().getEmail());

                                        firestore.collection("Post").document().set(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                loadingPb.setVisibility(GONE);
                                                clear();
                                                FragmentActivity fragmentActivity=getActivity();
                                                MainActivity mainActivity= (MainActivity) fragmentActivity;
                                                mainActivity.movePage(0);
                                            }
                                        });
                                    }
                                });
                            }
                        });

            }
        });
    }

    public void clear(){
        textEt.getText().clear();
        imageIv.setImageDrawable(getResources().getDrawable(R.drawable.baseline_add_circle_outline_black_48));
        tmpImage=null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_IMAGE && resultCode==RESULT_OK){
            tmpImage=data.getData();
            imageIv.setImageURI(tmpImage);
        }
    }
}
