package com.example.personalrestaurantguide;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;

    // Constructor to initialize the restaurant list
    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the custom layout for each item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_restaurant, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        // Get the current restaurant
        Restaurant restaurant = restaurantList.get(position);

        // Bind the restaurant data to the views
        holder.restaurantName.setText(restaurant.getName());
        holder.restaurantAddress.setText(restaurant.getAddress());
        holder.restaurantTags.setText("Tags: " + restaurant.getTags());
        holder.restaurantImage.setImageResource(restaurant.getImageResId());

        // Click listener to navigate to RestaurantDetailsActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);
            intent.putExtra("restaurant_name", restaurant.getName());
            intent.putExtra("restaurant_address", restaurant.getAddress());
            intent.putExtra("restaurant_tags", restaurant.getTags());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    // ViewHolder class to hold and bind the views for each item
    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName, restaurantAddress, restaurantTags;
        ImageView restaurantImage;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(R.id.restaurant_name);
            restaurantAddress = itemView.findViewById(R.id.restaurant_address);
            restaurantTags = itemView.findViewById(R.id.restaurant_tags);
            restaurantImage = itemView.findViewById(R.id.restaurant_image);
        }
    }
}
