package com.example.personalrestaurantguide;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<Restaurant> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.restaurant_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Populate the list with dummy data
        restaurantList = new ArrayList<>();
        restaurantList.add(new Restaurant("The Gourmet Garden", "123 Greenway Blvd", "Organic, Vegan", R.drawable.sample_image));
        restaurantList.add(new Restaurant("Sushi Paradise", "456 Ocean Ave", "Japanese, Sushi", R.drawable.sample_image));
        restaurantList.add(new Restaurant("Bella Italia", "789 Piazza St", "Italian, Pasta", R.drawable.sample_image));
        restaurantList.add(new Restaurant("Burger Haven", "101 Grill Lane", "American, Burgers", R.drawable.sample_image));
        restaurantList.add(new Restaurant("Taco Fiesta", "202 Fiesta Blvd", "Mexican, Tacos", R.drawable.sample_image));
        restaurantList.add(new Restaurant("Seafood Delights", "303 Marina Drive", "Seafood, Fresh Catch", R.drawable.sample_image));
        restaurantList.add(new Restaurant("Café Bliss", "404 Coffee St", "Coffee, Desserts", R.drawable.sample_image));
        restaurantList.add(new Restaurant("Spicy Treats", "505 Curry Lane", "Indian, Curry", R.drawable.sample_image));
        restaurantList.add(new Restaurant("The Green Fork", "606 Salad Ave", "Healthy, Salads", R.drawable.sample_image));
        restaurantList.add(new Restaurant("BBQ Central", "707 Smokehouse Blvd", "American, BBQ", R.drawable.sample_image));

        // Set up the adapter with the data
        adapter = new RestaurantAdapter(restaurantList);
        recyclerView.setAdapter(adapter);
    }
}
