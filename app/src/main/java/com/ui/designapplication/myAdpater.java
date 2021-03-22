package com.ui.designapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class myAdpater extends RecyclerView.Adapter<myAdpater.myViewHolder> {
    private Context ctx;
    private ArrayList<imageModel> list;


    public myAdpater(Context ctx, ArrayList<imageModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item, parent, false);
        myViewHolder rvh = new myViewHolder(v);
        return rvh;

    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        imageModel model = list.get(position);

        Glide.with(ctx).load(model.getImageUri()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.image1);


        }
    }
}
