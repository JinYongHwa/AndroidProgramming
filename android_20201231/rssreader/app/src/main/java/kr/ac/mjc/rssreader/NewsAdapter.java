package kr.ac.mjc.rssreader;

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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {

    Context mContext;
    List<Item> mNewsList;

    public NewsAdapter(Context context, List<Item> newsList){
        this.mContext=context;
        this.mNewsList=newsList;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_news,parent,false);
        NewsHolder newsHolder=new NewsHolder(view);
        return newsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        Item item=mNewsList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder{

        ImageView imageIv;
        TextView titleTv;
        TextView authorTv;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            imageIv=itemView.findViewById(R.id.image_iv);
            titleTv=itemView.findViewById(R.id.title_tv);
            authorTv=itemView.findViewById(R.id.author_tv);
        }

        public void bind(Item item){
            titleTv.setText(item.getTitle());
            authorTv.setText(item.getAuthor());
            Glide.with(imageIv).load(item.getEnclosure().getUrl()).into(imageIv);
        }
    }
}





















