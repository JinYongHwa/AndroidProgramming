package com.ioa.jyh.board;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import okhttp3.OkHttpClient;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {

    Context mContext;
    List<Board> mBoardList;

    OnBoardClickListener mListener;

    public BoardAdapter(Context context,List<Board> boardList){
        this.mContext=context;
        this.mBoardList=boardList;
    }

    public void setOnBoardClickListener(OnBoardClickListener listener){
        this.mListener=listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Log.d("BoardAdapter","onCreateViewHolder");
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_board,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Log.d("BoardAdapter","onBindViewHolder "+position);
        Board board=mBoardList.get(position);
        holder.bind(board);
    }

    @Override
    public int getItemCount() {
//        Log.d("BoardAdapter","getItemCount "+mBoardList.size());
        return mBoardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView titleTv;
        TextView writerTv;
        TextView viewCountTv;
        TextView createDateTv;
        Board mBoard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv=itemView.findViewById(R.id.title_tv);
            writerTv=itemView.findViewById(R.id.writer_tv);
            viewCountTv=itemView.findViewById(R.id.viewcount_tv);
            createDateTv=itemView.findViewById(R.id.createdate_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onBoardClick(mBoard);
                }
            });

        }

        public void bind(Board board){
            mBoard=board;
            titleTv.setText(board.getTitle());
            writerTv.setText(board.getEmail());
            viewCountTv.setText(Integer.toString(board.getViewCount()));
            createDateTv.setText(board.getFormattedCreateDate());
        }
    }

    interface OnBoardClickListener{
        void onBoardClick(Board board);
    }
}
