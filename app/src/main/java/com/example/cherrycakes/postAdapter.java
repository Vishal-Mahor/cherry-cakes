package com.example.cherrycakes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class postAdapter extends RecyclerView.Adapter<postAdapter.MyViewHolder> {

    Context context;
    ArrayList<DataSet> dataSet;

    public postAdapter(Context c, ArrayList<DataSet> d) {
        context = c;
        dataSet = d;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_design,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(dataSet.get(position).getName());
        holder.price.setText(dataSet.get(position).getPrice()+"");
        Picasso.get().load(dataSet.get(position).getImage()).into(holder.image);
        holder.view.setVisibility(View.VISIBLE);
        holder.onClick(position);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, price;
        ImageView image;
        View view;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.list_name);
            price = itemView.findViewById(R.id.list_price);
            image = itemView.findViewById(R.id.list_photo);
            view = itemView;
        }

        public void onClick(final int position){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Pay using UPI secure and safe", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, PaymentActivity.class);
                    intent.putExtra("name", dataSet.get(position).getName());
                    intent.putExtra("price", dataSet.get(position).getPrice());
                    context.startActivity(intent);
                }
            });
        }
    }

}