package com.example.personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private List<String> restaurantList;
    private List<String> filteredList;
    private EditText searchEditText;
    private Button addRestaurantButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.restaurant_cards_container);
        searchEditText = findViewById(R.id.search_restaurant);
        addRestaurantButton = findViewById(R.id.add_restaurant_button);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        restaurantList = Arrays.asList(
                "Restaurant A",
                "Restaurant B",
                "Restaurant C"
        );
        filteredList = new ArrayList<>(restaurantList);

        adapter = new RestaurantAdapter(filteredList);
        recyclerView.setAdapter(adapter);


        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterRestaurants(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        addRestaurantButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RestaurantDetailsActivity.class);
            startActivity(intent);
        });
    }


    private void filterRestaurants(String query) {
        filteredList.clear();
        for (String restaurant : restaurantList) {
            if (restaurant.toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(restaurant);
            }
        }
        adapter.notifyDataSetChanged();
    }
}
