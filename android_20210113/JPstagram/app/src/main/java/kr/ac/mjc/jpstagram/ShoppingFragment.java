package kr.ac.mjc.jpstagram;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.mjc.jpstagram.model.Item;
import kr.ac.mjc.jpstagram.model.NaverResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingFragment extends Fragment {

    ShoppingAdapter mAdapter;
    List<Item> mItemList=new ArrayList<>();
    Handler handler=new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shopping,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText queryEt=view.findViewById(R.id.query_et);
        RecyclerView resultRv=view.findViewById(R.id.result_rv);

        mAdapter=new ShoppingAdapter(getActivity(),mItemList);
        resultRv.setAdapter(mAdapter);

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        resultRv.setLayoutManager(gridLayoutManager);

        queryEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("ShoppingFragment",queryEt.getText().toString());
                search(queryEt.getText().toString());
            }
        });
    }

    public void search(String query){
        NaverService naverService=NaverApi.getInstance().getNaverService();
        naverService.shopList(query,100,1).enqueue(new Callback<NaverResult>() {
            @Override
            public void onResponse(Call<NaverResult> call, Response<NaverResult> response) {
                List<Item> items=response.body().getItems();
                mItemList.addAll(items);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
//                for(Item item:items){
//                    Log.d("ShoppingFragment",item.getTitle());
//                }
            }

            @Override
            public void onFailure(Call<NaverResult> call, Throwable t) {

            }
        });
    }
}
