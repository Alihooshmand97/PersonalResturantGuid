package com.example.personalrestaurantguide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
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
                .inflate(R.layout.restaurant_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RestaurantViewHolder holder, int position) {
        String restaurantName = restaurantList.get(position);
        holder.restaurantNameTextView.setText(restaurantName);
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        TextView restaurantNameTextView;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            restaurantNameTextView = itemView.findViewById(R.id.restaurant_name);
        }
    }
}
