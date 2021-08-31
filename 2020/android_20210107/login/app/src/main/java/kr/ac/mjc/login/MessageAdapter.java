package kr.ac.mjc.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    SimpleDateFormat timeFormat=new SimpleDateFormat("HH:mm");
    Context mContext;
    List<Message> mMessageList;
    FirebaseUser mUser;

    final int MY_MESSAGE=1;
    final int OTHER_MESSAGE=2;



    public MessageAdapter(Context context, List<Message> messageList, FirebaseUser user){
        this.mContext=context;
        this.mMessageList=messageList;
        this.mUser=user;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MY_MESSAGE){
            View view=LayoutInflater.from(mContext).inflate(R.layout.item_message_me,parent,false);
            MyMessageViewholder myMessageViewholder=new MyMessageViewholder(view);
            return myMessageViewholder;
        }
        else{
            View view=LayoutInflater.from(mContext).inflate(R.layout.item_message_other,parent,false);
            OtherMessageViewHolder otherMessageViewHolder=new OtherMessageViewHolder(view);
            return otherMessageViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message=mMessageList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Message message=mMessageList.get(position);
        if(message.getUserId().equals(mUser.getEmail())){
            return MY_MESSAGE;
        }
        else{
            return OTHER_MESSAGE;
        }
    }

    public abstract class MessageViewHolder extends RecyclerView.ViewHolder{

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        abstract void bind(Message message);
    }

    public class MyMessageViewholder extends MessageViewHolder{

        TextView timeTv;
        TextView messageTv;

        public MyMessageViewholder(@NonNull View itemView) {
            super(itemView);
            timeTv=itemView.findViewById(R.id.time_tv);
            messageTv=itemView.findViewById(R.id.message_tv);
        }

        @Override
        void bind(Message message) {
            messageTv.setText(message.getMessage());
            String time=timeFormat.format(message.getSendDate());
            timeTv.setText(time);
        }
    }

    public class OtherMessageViewHolder extends MessageViewHolder{

        TextView emailTv;
        TextView messageTv;
        TextView timeTv;

        public OtherMessageViewHolder(@NonNull View itemView) {
            super(itemView);
            emailTv=itemView.findViewById(R.id.email_tv);
            messageTv=itemView.findViewById(R.id.message_tv);
            timeTv=itemView.findViewById(R.id.time_tv);
        }

        @Override
        void bind(Message message) {
            emailTv.setText(message.getUserId());
            messageTv.setText(message.getMessage());
            String time=timeFormat.format(message.getSendDate());
            timeTv.setText(time);
        }
    }
}
