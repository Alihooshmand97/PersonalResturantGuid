package com.example.personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.personalrestaurantguide.model.AppDatabase;
import com.example.personalrestaurantguide.model.Restaurant;
import com.example.personalrestaurantguide.model.RestaurantDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private RestaurantDao restaurantDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Room Database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "restaurant-database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        restaurantDao = db.restaurantDao();

        // Add sample data if database is empty
        addSampleData();

        // Setup RecyclerView
        recyclerView = findViewById(R.id.restaurant_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RestaurantAdapter(restaurantDao.getAllRestaurants());
        recyclerView.setAdapter(adapter);

        // Setup Search Bar
        EditText searchBar = findViewById(R.id.search_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                List<Restaurant> filteredList = restaurantDao.searchRestaurants("%" + s.toString() + "%");
                adapter.updateList(filteredList);
            }

            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        // Setup About Button
        findViewById(R.id.about_button).setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(intent);
        });

        // Setup Floating Action Button
        FloatingActionButton fabAddRestaurant = findViewById(R.id.fab_add_restaurant);
        fabAddRestaurant.setOnClickListener(v -> {
            // Open RestaurantDetailsActivity to add a new restaurant
            Intent intent = new Intent(HomeActivity.this, RestaurantDetailsActivity.class);
            startActivity(intent);
        });
    }

    private void addSampleData() {
        if (restaurantDao.getAllRestaurants().isEmpty()) {
            Restaurant sample1 = new Restaurant();
            sample1.setName("Pizza Paradise");
            sample1.setAddress("123 Main St");
            sample1.setPhone("123-456-7890");
            sample1.setDescription("Delicious pizza and pasta.");
            sample1.setTags("Italian, Pizza");
            sample1.setRating(5);
            sample1.setLatitude(40.748817);
            sample1.setLongitude(-73.985428);

            Restaurant sample2 = new Restaurant();
            sample2.setName("Sushi Haven");
            sample2.setAddress("456 Elm St");
            sample2.setPhone("987-654-3210");
            sample2.setDescription("Fresh sushi and sashimi.");
            sample2.setTags("Japanese, Sushi");
            sample2.setRating(4);
            sample2.setLatitude(34.052235);
            sample2.setLongitude(-118.243683);

            restaurantDao.insert(sample1);
            restaurantDao.insert(sample2);
        }
    }
}
