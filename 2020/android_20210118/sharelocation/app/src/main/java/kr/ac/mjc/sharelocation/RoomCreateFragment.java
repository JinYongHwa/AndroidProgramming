package kr.ac.mjc.sharelocation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import kr.ac.mjc.sharelocation.model.Address;
import kr.ac.mjc.sharelocation.model.Document;
import kr.ac.mjc.sharelocation.model.KakaoResult;
import kr.ac.mjc.sharelocation.model.Location;
import kr.ac.mjc.sharelocation.model.Room;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class RoomCreateFragment extends Fragment {

    int REQ_LOCATION=10000;
    private Room mRoom=new Room();

    Button locationBtn;

    Handler handler=new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_roomcreate,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText titleEt=view.findViewById(R.id.title_et);
        EditText passwordEt=view.findViewById(R.id.password_et);
        EditText limitUserEt=view.findViewById(R.id.limit_user_et);
        locationBtn=view.findViewById(R.id.location_btn);
        Button createBtn=view.findViewById(R.id.create_btn);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=titleEt.getText().toString();
                String password=passwordEt.getText().toString();
                int limitUser=Integer.parseInt(limitUserEt.getText().toString());
                if(title.length()==0){
                    Toast.makeText(getActivity(),"방제목을 입력해주세요",Toast.LENGTH_SHORT).show();
                    titleEt.requestFocus();
                    return;
                }
                if(password.length()!=4){
                    Toast.makeText(getActivity(),"패스워드는 4자리로 입력해주세요",Toast.LENGTH_SHORT).show();
                    passwordEt.requestFocus();
                    return;
                }
                if(limitUser<2){
                    Toast.makeText(getActivity(),"제한인원은 2명이상 입력해주세요",Toast.LENGTH_SHORT).show();
                    limitUserEt.requestFocus();
                    return;
                }
            }
        });

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),LocationSelectActivity.class);
                startActivityForResult(intent,REQ_LOCATION);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_LOCATION && resultCode==RESULT_OK){
            double latitude=data.getDoubleExtra(LocationSelectActivity.NAME_LATITUDE,0);
            double longtitude=data.getDoubleExtra(LocationSelectActivity.NAME_LONGTITUDE,0);
            Log.d("RoomCreateFramgnet",latitude+","+longtitude);
            mRoom.setTargetLocation(new Location(latitude,longtitude));
            KakaoApi.getInstance().getKakaoService().geocoding(latitude,longtitude).enqueue(new Callback<KakaoResult>() {
                @Override
                public void onResponse(Call<KakaoResult> call, Response<KakaoResult> response) {

                    List<Document> documents=response.body().getDocuments();
                    if(documents.size()>0){
                        Address roadAddress=documents.get(0).getRoad_address();
                        Address address=documents.get(0).getAddress();
                        if(roadAddress!=null){
                            mRoom.setAddress(roadAddress.getAddress_name());
                        }
                        else if(address!=null){
                            mRoom.setAddress(address.getAddress_name());
                        }
                        else{
                            Toast.makeText(getActivity(),"선택한 위치의 주소를 찾을수 없습니다",Toast.LENGTH_SHORT).show();
                        }

                    }
                    else{
                        Toast.makeText(getActivity(),"선택한 위치의 주소를 찾을수 없습니다",Toast.LENGTH_SHORT).show();
                    }
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(mRoom.getAddress()!=null){
                                locationBtn.setText(mRoom.getAddress());
                            }
                        }
                    });




                }

                @Override
                public void onFailure(Call<KakaoResult> call, Throwable t) {
                    Log.d("RoomCreateFramgnet",t.toString());
                }
            });
        }

    }
}
