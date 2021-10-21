package com.ioa.jyh.board;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<Board> mBoardList=new ArrayList<>();
    BoardAdapter mBoardAdapter=new BoardAdapter(this,mBoardList);
    int mPage=1;
    boolean mPageLock=false;
    Navigator mNavigator;

    final int REQ_LOGIN=1000;
    final int REQ_WRITE=1001;

    User mUser=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addFab=findViewById(R.id.add_fab);

        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCheck();
            }
        });

        RecyclerView boardListRv=findViewById(R.id.board_list_rv);
        boardListRv.setAdapter(mBoardAdapter);

        mBoardAdapter.setOnBoardClickListener(new BoardAdapter.OnBoardClickListener() {
            @Override
            public void onBoardClick(Board board) {
                Log.d("MainActivity",board.getTitle());
                Intent intent=new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra("boardId",board.getId());
                startActivity(intent);
            }
        });

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        boardListRv.setLayoutManager(layoutManager);



        boardListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("RecyclerView", String.format("state[%d]",newState));
                if(!recyclerView.canScrollVertically(1)){
                    Log.d("RecyclerView","scroll end");
                    if(!mPageLock){
                        if(mNavigator!=null && mNavigator.getLastPage()>mPage){
                            addNextPage();
                        }

                    }

                }

            }
        });

        init();

    }

    public void init(){
        mPageLock=true;
        mPage=1;

        Common.getBoardService(MainActivity.this).list(mPage).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                List<Board> boardList=response.body().getBoardList();
                mNavigator=response.body().getNav();
                mBoardList.clear();
                mBoardList.addAll(boardList);
                mBoardAdapter.notifyDataSetChanged();
                mPageLock=false;

//                for(Board board:boardList){
//                    Log.d("MainActivity",String.format("id[%s]",board.getCreateDate()));
//                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d("MainActivity",t.toString());
            }
        });
    }

    public void addNextPage(){
        mPageLock=true;
        mPage++;
        Log.d("MainActivity",String.format("page[%d]",mPage));
        Common.getBoardService(MainActivity.this).list(mPage).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                List<Board> boardList=response.body().getBoardList();
                mNavigator=response.body().getNav();
                mBoardList.addAll(boardList);
                mBoardAdapter.notifyDataSetChanged();
                mPageLock=false;
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    public void userCheck(){
        Common.getBoardService(MainActivity.this).userinfo().enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.body().isResult()){ //로그인이 되있을때
                    User loginUser=response.body().getUser();
                    Intent intent=new Intent(MainActivity.this,WriteActivity.class);
                    intent.putExtra("user",loginUser);
                    startActivityForResult(intent,REQ_WRITE);
                }
                else{       //로그인이 안되있을때
                    Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                    startActivityForResult(intent,REQ_LOGIN);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_LOGIN && resultCode==RESULT_OK){
            User loginUser= (User) data.getSerializableExtra("user");
            mUser=loginUser;
            Intent intent=new Intent(MainActivity.this,WriteActivity.class);

            startActivityForResult(intent,REQ_WRITE);
        }
        //글쓰기 성공시
        if(requestCode==REQ_WRITE&& resultCode==RESULT_OK){
            init();
        }

    }
}