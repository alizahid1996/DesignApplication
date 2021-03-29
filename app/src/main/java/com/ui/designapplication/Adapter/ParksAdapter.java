package com.ui.designapplication.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.ui.designapplication.DetailActivity;
import com.ui.designapplication.Models.NearbyCards;
import com.ui.designapplication.Models.myModel;
import com.ui.designapplication.R;
import com.ui.designapplication.databinding.ItemDesignparksBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ParksAdapter extends RecyclerView.Adapter<ParksAdapter.ViewHolder> {
    List<NearbyCards> models;
    Context context;

    public ParksAdapter(List<NearbyCards> models, Context context) {
        this.models = models;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_designparks,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

           NearbyCards model = models.get(position);
           holder.binding.parkName.setText(model.getName());
           holder.binding.Parkaddress.setText(model.getStreetAddress()+model.getCity()+model.getState()+model.getZipCode());
          // Glide.with(context).load(model.getImageUrl()).into(holder.binding.parkImage);
            Picasso.get().load(model.getImageUrl()).into(holder.binding.parkImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailActivity.class);
                intent.putExtra("name",model.getName());
                intent.putExtra("id",model.getId());
                intent.putExtra("Description",model.getDescription());
                intent.putExtra("streetAddress",model.getStreetAddress());
                intent.putExtra("state",model.getState());
                intent.putExtra("city",model.getCity());
                intent.putExtra("zipcode",model.getZipCode());
                //intent.putExtra("decription2",model.getDirections());
                intent.putExtra("latitude",model.getLatitude());
                intent.putExtra("longitude",model.getLongitude());
                intent.putExtra("imageUrl",model.getImageUrl());
                //intent.putExtra("reviews",model.getReviews());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ItemDesignparksBinding binding;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemDesignparksBinding.bind(itemView);
        }
    }
}
