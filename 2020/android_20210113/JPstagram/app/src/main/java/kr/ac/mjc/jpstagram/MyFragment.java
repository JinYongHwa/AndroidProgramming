package kr.ac.mjc.jpstagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class MyFragment  extends Fragment {

    ProgressBar loadingPb;

    final int REQ_PROFILE=1000;

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();

    ImageView profileIv;
    TextView nameTv;

    List<Post> mPostList=new ArrayList<>();
    ProfileAdapter mAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profileIv=view.findViewById(R.id.profile_iv);
        nameTv=view.findViewById(R.id.name_tv);
        Button profileBtn=view.findViewById(R.id.profile_btn);
        Button logoutBtn=view.findViewById(R.id.logout_btn);
        loadingPb=view.findViewById(R.id.loading_pb);
        RecyclerView listRv=view.findViewById(R.id.list_rv);

        mAdapter=new ProfileAdapter(getActivity(),mPostList);
        listRv.setAdapter(mAdapter);
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),3);
        listRv.setLayoutManager(gridLayoutManager);
        mPostList.clear();
        firestore.collection("Post").whereEqualTo("userId",auth.getCurrentUser().getEmail())
                .orderBy("date", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(value!=null){
                            for(DocumentChange dc:value.getDocumentChanges()){
                                if(dc.getType()== DocumentChange.Type.ADDED){
                                    Post post=dc.getDocument().toObject(Post.class);
                                    mPostList.add(0,post);
                                }
                            }
                            mAdapter.notifyDataSetChanged();
                        }

                    }
                });

        loadUser();
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),ProfileActivity.class);
                startActivityForResult(intent,REQ_PROFILE);
            }
        });
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent=new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    public void loadUser(){
        String email=auth.getCurrentUser().getEmail();
        loadingPb.setVisibility(View.VISIBLE);
        firestore.collection("User").document(email).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                loadingPb.setVisibility(View.GONE);
                User user=documentSnapshot.toObject(User.class);
                nameTv.setText(user.getName());
                if(user.getProfileUrl()!=null){
                    Glide.with(profileIv).load(user.getProfileUrl()).into(profileIv);
                }

            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_PROFILE && resultCode==RESULT_OK){
            loadUser();
        }
    }
}
