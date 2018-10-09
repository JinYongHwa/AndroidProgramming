package kr.ac.mjc.jyh.fragment_example;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SelecterFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView= (ViewGroup) inflater.inflate(R.layout.fragment_selecter,container,false);
        Button firstBtn=rootView.findViewById(R.id.first_btn);
        Button secondBtn=rootView.findViewById(R.id.second_btn);
        firstBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.setImage(0);
            }
        });
        secondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.setImage(1);
            }
        });
        return rootView;

    }
}
