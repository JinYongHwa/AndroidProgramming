package kr.ac.mjc.storage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    Context mContext;
    List<Image> mImageList;

    OnImageClickListener mListener;

    public ImageAdapter(Context context, List<Image> imageList){
        this.mContext=context;
        this.mImageList=imageList;
    }

    public void setOnImageClickListener(OnImageClickListener listener){
        this.mListener=listener;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_image,parent,false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image image=mImageList.get(position);
        holder.bind(image);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView imageIv;
        Image mImage;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIv=itemView.findViewById(R.id.image_iv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onClick(mImage);
                    }
                }
            });
        }
        public void bind(Image image){
            Glide.with(imageIv).load(image.getImageUrl()).into(imageIv);
            mImage=image;
        }
    }

    interface OnImageClickListener{
        public void onClick(Image image);
    }
}
