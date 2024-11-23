package com.example.personalrestaurantguide;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<String> restaurantList;

    public RestaurantAdapter(List<String> restaurantList) {
        this.restaurantList = restaurantList;
    }

    @Override
    public RestaurantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        String restaurantName = restaurantList.get(position);
        holder.restaurantName.setText(restaurantName);

        // Handle click to navigate to RestaurantDetailsActivity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), RestaurantDetailsActivity.class);
            intent.putExtra("restaurant_name", restaurantName); // Pass data to the details screen
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {
        TextView restaurantName;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            restaurantName = itemView.findViewById(android.R.id.text1);
        }
    }
}
