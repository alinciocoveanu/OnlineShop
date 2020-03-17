package com.example.onlineshop;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Car> mData;
    private LayoutInflater mInflater;
    private Context mContext;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<Car> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Car car = mData.get(position);
        holder.myTextView.setText(car.getName());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ItemDescriptionActivity.class);
                Car myCar = mData.get(position);
                intent.putExtra("imageView", myCar.getImgUrl());
                intent.putExtra("textView", myCar.getName());
                intent.putExtra("textView1", "Year: " + myCar.getYear());
                intent.putExtra("textView2", "Price: " + myCar.getPrice());
                mContext.startActivity(intent);
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;
        LinearLayout parentLayout;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.carList);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}