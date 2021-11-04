package com.ioa.jyh.firstfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    CircleImageView profileIv;
    TextView nameTv;
    TextView emailTv;
    Button editprofileBtn;
    Button logoutBtn;
    RecyclerView listRv;

    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_profile,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profileIv=view.findViewById(R.id.profile_iv);
        nameTv=view.findViewById(R.id.name_tv);
        emailTv=view.findViewById(R.id.email_tv);
        editprofileBtn=view.findViewById(R.id.editprofile_btn);
        logoutBtn=view.findViewById(R.id.logout_btn);
        listRv=view.findViewById(R.id.list_rv);

        logoutBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                auth.signOut();
                Intent intent=new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        editprofileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),ProfileEditActivity.class);
                startActivity(intent);
            }
        });
    }
}
