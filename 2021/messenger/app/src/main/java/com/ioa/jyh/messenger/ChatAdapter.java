package com.ioa.jyh.messenger;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    Context mContext;
    List<Message> mMessageList;
    String writer;

    final int VIEW_TYPE_ME=1;
    final int VIEW_TYPE_OTHER=2;

    OnChatListener onChatListener;

    public ChatAdapter(Context context, List<Message> messageList, String writer){
        this.mContext=context;
        this.mMessageList=messageList;
        this.writer=writer;
    }

    public void setOnChatListener(OnChatListener listener){
        onChatListener=listener;
    }

    @Override
    public int getItemViewType(int position) {
        Message message=mMessageList.get(position);
        if( writer.equals(message.getWriter()) ){
            return VIEW_TYPE_ME;
        }
        else{
            return VIEW_TYPE_OTHER;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==VIEW_TYPE_ME){
            View view=LayoutInflater.from(mContext).inflate(R.layout.item_message_me,parent,false);
            return new ViewHolder(view);
        }
        else{
            View view=LayoutInflater.from(mContext).inflate(R.layout.item_message_other,parent,false);
            return new OtherViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message=mMessageList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    public class OtherViewHolder extends ViewHolder{

        TextView writerTv;

        public OtherViewHolder(@NonNull View itemView) {
            super(itemView);
            writerTv=itemView.findViewById(R.id.writer_tv);
        }
        public void bind(Message message){
            messageTv.setText(message.getMessage());
            writerTv.setText(message.getWriter());
            dateTv.setText(message.getFormattedDate());
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView dateTv;
        TextView messageTv;
        Message mMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateTv=itemView.findViewById(R.id.date_tv);
            messageTv=itemView.findViewById(R.id.message_tv);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    onChatListener.onLongClick(mMessage);
                    return false;
                }
            });
        }
        public void bind(Message message){
            mMessage=message;
            messageTv.setText(message.getMessage());
            dateTv.setText(message.getFormattedDate());
        }
    }

    interface OnChatListener{
        public void onLongClick(Message message);
    }
}
