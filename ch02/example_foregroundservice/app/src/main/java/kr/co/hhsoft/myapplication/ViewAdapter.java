package kr.co.hhsoft.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;

import java.util.List;

public class ViewAdapter extends RecyclerView.Adapter<ViewAdapter.ViewHolder>{
    List<Post> mPostLit;
    public ViewAdapter(List<Post> postList){
        this.mPostLit=postList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(mPostLit.get(position));
    }

    @Override
    public int getItemCount() {
        return mPostLit.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageIv;
        TextView textTv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIv=itemView.findViewById(R.id.image_iv);
            textTv=itemView.findViewById(R.id.text_tv);
        }
        public void bind(Post post){
            Glide.with(imageIv).load(post.getUrl()).centerCrop().transition(DrawableTransitionOptions.withCrossFade()).into(imageIv);
            textTv.setText(post.getTitle());
        }
    }
}
