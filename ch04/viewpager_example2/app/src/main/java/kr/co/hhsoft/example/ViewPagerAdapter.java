package kr.co.hhsoft.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ViewHolder> {

    String[] urlList={
            "https://i.pravatar.cc/500",
            "https://i.pravatar.cc/500",
            "https://i.pravatar.cc/500",
            "https://i.pravatar.cc/500",
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(urlList[position]);
    }

    @Override
    public int getItemCount() {
        return urlList.length;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageIv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIv=itemView.findViewById(R.id.image_iv);
        }
        public void bind(String url){
            Glide.with(imageIv).load(url).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).centerCrop().into(imageIv);
        }

    }

}
