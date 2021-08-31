package kr.ac.mjc.jpstagram;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PostActivity extends AppCompatActivity {

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_timeline);

        ImageView profileIv=findViewById(R.id.profile_iv);
        TextView nameTv=findViewById(R.id.name_tv);
        ImageView imageIv=findViewById(R.id.image_iv);
        TextView textTv=findViewById(R.id.text_tv);

        Post post= (Post) getIntent().getSerializableExtra("post");
        textTv.setText(post.getText());
        Glide.with(imageIv).load(post.getImageUrl()).into(imageIv);

        firestore.collection("User").document(post.getUserId()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user=documentSnapshot.toObject(User.class);
                nameTv.setText(user.getName());
                if(user.getProfileUrl()!=null){
                    Glide.with(profileIv).load(user.getProfileUrl()).into(profileIv);
                }
            }
        });
    }
}
