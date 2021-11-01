package com.ioa.jyh.firstfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    Context mContext;
    List<Post> mPostList;

    public HomeAdapter(Context context,List<Post> postList){
        this.mContext=context;
        this.mPostList=postList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_home,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post=mPostList.get(position);
        holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView profileIv;
        TextView writerTv;
        ImageView imageIv;
        TextView messageTv;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileIv=itemView.findViewById(R.id.profile_iv);
            writerTv=itemView.findViewById(R.id.writer_tv);
            imageIv=itemView.findViewById(R.id.image_iv);
            messageTv=itemView.findViewById(R.id.message_tv);
        }

        public void bind(Post post){
            Glide.with(imageIv).load(post.getImageUrl()).into(imageIv);
            writerTv.setText(post.getUserId());
            messageTv.setText(post.getMessage());
        }

    }
}
