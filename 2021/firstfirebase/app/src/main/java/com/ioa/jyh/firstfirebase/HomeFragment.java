package com.ioa.jyh.firstfirebase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView timelineRv;
    HomeAdapter homeAdapter;
    List<Post> mPostList=new ArrayList<>();

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timelineRv=view.findViewById(R.id.timeline_rv);
        homeAdapter=new HomeAdapter(getActivity(),mPostList);
        timelineRv.setAdapter(homeAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        timelineRv.setLayoutManager(layoutManager);

        refresh();
    }
    public void refresh(){

        firestore.collection("post")
                .orderBy("uploadDate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        mPostList.clear();
                        if(task.isSuccessful()){
                            for(DocumentChange dc :task.getResult().getDocumentChanges()){
                                Post post=dc.getDocument().toObject(Post.class);
                                mPostList.add(post);
                            }
                            homeAdapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
