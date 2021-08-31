package kr.ac.mjc.jpstagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements TimelineAdapter.OnPostClickListener {

    RecyclerView timelineRv;
    List<Post> mPostList=new ArrayList<>();
    TimelineAdapter mAdapter;

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        timelineRv=view.findViewById(R.id.timeline_rv);

        mAdapter=new TimelineAdapter(mPostList,getActivity());
        timelineRv.setAdapter(mAdapter);
        mAdapter.setOnPostClickListener(this);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        timelineRv.setLayoutManager(linearLayoutManager);

        firestore.collection("Post").orderBy("date", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for(DocumentChange dc:value.getDocumentChanges()){
                            if(dc.getType()== DocumentChange.Type.ADDED){
                                Post post=dc.getDocument().toObject(Post.class);
                                mPostList.add(0,post);
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                });

    }

    @Override
    public void onClick(Post post) {
        Intent intent=new Intent(getActivity(),PostActivity.class);
        intent.putExtra("post",post);
        startActivity(intent);

    }
}
