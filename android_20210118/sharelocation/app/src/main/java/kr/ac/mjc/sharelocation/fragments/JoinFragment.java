package kr.ac.mjc.sharelocation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import kr.ac.mjc.sharelocation.R;
import kr.ac.mjc.sharelocation.RoomActivity;
import kr.ac.mjc.sharelocation.model.Room;

public class JoinFragment extends Fragment {

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_join,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText passwordEt=view.findViewById(R.id.password_et);
        Button joinRoom=view.findViewById(R.id.join_btn);

        joinRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password=passwordEt.getText().toString();
                firestore.collection("Room").whereEqualTo("password",password)
                        .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documents=queryDocumentSnapshots.getDocuments();
                        if(documents.size()==0){
                            Toast.makeText(getActivity(),"비밀번호에 맞는 방이 없습니다",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Room room=documents.get(0).toObject(Room.class);
                            firestore.collection("User").document(auth.getCurrentUser().getEmail())
                                    .update("roomId",room.getId())
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Intent intent=new Intent(getActivity(), RoomActivity.class);
                                            intent.putExtra(RoomActivity.NAME_ROOM,room);
                                            startActivity(intent);
                                        }
                                    });


                        }
                    }
                });
            }
        });
    }
}
