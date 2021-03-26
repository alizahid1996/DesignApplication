package com.ui.designapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ui.designapplication.Models.newModel;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class newAdapter extends RecyclerView.Adapter<newAdapter.myViewHolder> {
    private Context ctx;
    private ArrayList<newModel> list;

    public newAdapter(Context ctx, ArrayList<newModel> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item, parent, false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        newModel model = list.get(position);
        String url = model.getImageUrl();
        Glide.with(ctx).load(url).into(holder.imageUrl);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageUrl;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imageUrl = itemView.findViewById(R.id.image);
        }
    }


}
