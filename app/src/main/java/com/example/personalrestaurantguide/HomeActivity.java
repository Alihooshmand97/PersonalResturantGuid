package com.example.personalrestaurantguide;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<String> restaurantList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.restaurant_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data for the list
        // Dummy data for the list of restaurants
        restaurantList = Arrays.asList(
                "Restaurant A",
                "Restaurant B",
                "Restaurant C"
        );
        adapter = new RestaurantAdapter(restaurantList);
        recyclerView.setAdapter(adapter);
    }
}
