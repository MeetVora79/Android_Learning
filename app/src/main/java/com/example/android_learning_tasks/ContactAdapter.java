package com.example.android_learning_tasks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Context context;
    ArrayList<ContactModel> contactList;
    ArrayList<ContactModel> filteredList;

    public ContactAdapter(Context context, ArrayList<ContactModel> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.filteredList = new ArrayList<>(contactList);
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contact_raw_w5_t2, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        ContactModel contact = filteredList.get(position);
        holder.tvName.setText(contact.getName());
        holder.tvPhone.setText(contact.getPhone());
        holder.ivContact.setImageResource(contact.getImage());

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            Intent intent = new Intent(context, ContactDetail_w5_t2.class);
            intent.putExtra("name", contactList.get(pos).getName());
            intent.putExtra("phone", contactList.get(pos).getPhone());
            intent.putExtra("image", contactList.get(pos).getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPhone;
        ImageView ivContact;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            ivContact = itemView.findViewById(R.id.ivContact);
        }
    }

    public void filter(String query){
        filteredList.clear();
        String q = query.trim().toLowerCase();

        if(q.isEmpty()){
            filteredList.addAll(contactList);
        }else{
            for(ContactModel c : contactList){
                if(c.getName().toLowerCase().contains(q)){
                    filteredList.add(c);
                }
            }
        }
        notifyDataSetChanged();
    }
}
