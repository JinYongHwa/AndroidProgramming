package kr.ac.mjc.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardViewHolder> {

    Context mContext;
    List<Board> mBoardList;

    public BoardAdapter(Context context, List<Board> boardList){
        this.mContext=context;
        this.mBoardList=boardList;
    }

    @NonNull
    @Override
    public BoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_board,parent,false);
        BoardViewHolder boardViewHolder=new BoardViewHolder(view);
        return boardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BoardViewHolder holder, int position) {
        Board board=mBoardList.get(position);
        holder.bind(board);
    }

    @Override
    public int getItemCount() {
        return mBoardList.size();
    }

    class BoardViewHolder extends RecyclerView.ViewHolder{

        TextView titleTv;
        TextView writeDateTv;
        TextView writerTv;

        public BoardViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv=itemView.findViewById(R.id.title_tv);
            writeDateTv=itemView.findViewById(R.id.write_date_tv);
            writerTv=itemView.findViewById(R.id.writer_tv);
        }
        public void bind(Board board){
            titleTv.setText(board.getTitle());
            writeDateTv.setText(board.getFormattedWriteDate());
            writerTv.setText(board.getWriter());
        }
    }
}
