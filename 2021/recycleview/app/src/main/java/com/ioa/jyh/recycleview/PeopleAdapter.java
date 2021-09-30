package com.ioa.jyh.recycleview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.ViewHolder> {

    Context mContext;
    ArrayList<Person> mPeopleList;

    OnItemClickListener onItemClickListener;

    public PeopleAdapter(Context context, ArrayList<Person> peopleList){
        this.mContext=context;
        this.mPeopleList=peopleList;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener=listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("PeopleAdapter","onCreateViewHolder");
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_person,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("PeopleAdapter","onBindViewHolder "+position);
        Person person=mPeopleList.get(position);
        holder.onBind(person);
    }

    @Override
    public int getItemCount() {
        Log.d("PeopleAdapter","getItemCount "+mPeopleList.size());
        return mPeopleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView nameTv;
        TextView phonenumberTv;
        TextView addressTv;
        Person mPerson;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv=itemView.findViewById(R.id.name_tv);
            phonenumberTv=itemView.findViewById(R.id.phonenumber_tv);
            addressTv=itemView.findViewById(R.id.address_tv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(mPerson);
                }
            });
        }
        public void onBind(Person person){
            this.mPerson=person;
            nameTv.setText(person.getName());
            phonenumberTv.setText(person.getPhoneNumber());
            addressTv.setText(person.getAddress());
        }
    }

    interface OnItemClickListener{
        public void onItemClick(Person person);
    }
}
