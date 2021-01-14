package kr.ac.mjc.jpstagram;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

import kr.ac.mjc.jpstagram.model.Item;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ShoppingHolder> {

    Context mContext;
    List<Item> mItemList;

    OnShoppingListener mListener;

    public ShoppingAdapter(Context context, List<Item> itemList){
        this.mContext=context;
        this.mItemList=itemList;
    }
    public void setOnShoppingListener(OnShoppingListener listener){
        this.mListener=listener;
    }


    @NonNull
    @Override
    public ShoppingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_shopping,parent,false);
        return new ShoppingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingHolder holder, int position) {
        Item item=mItemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    class ShoppingHolder extends RecyclerView.ViewHolder{

        ImageView imageIv;
        TextView titleTv;
        TextView priceTv;
        Item mItem;

        public ShoppingHolder(@NonNull View itemView) {
            super(itemView);
            imageIv=itemView.findViewById(R.id.image_iv);
            titleTv=itemView.findViewById(R.id.title_tv);
            priceTv=itemView.findViewById(R.id.price_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onClick(mItem);
                    }
                }
            });
        }
        public void bind(Item item){
            Glide.with(imageIv).load(item.getImage()).into(imageIv);
            titleTv.setText(item.getTitle());
            priceTv.setText(
                    String.format("%d원",item.getLprice())
            );
            mItem=item;
        }
    }
    interface OnShoppingListener{
        public void onClick(Item item);
    }
}
