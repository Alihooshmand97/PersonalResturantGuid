package com.example.personalrestaurantguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.personalrestaurantguide.model.AppDatabase;
import com.example.personalrestaurantguide.model.Restaurant;
import com.example.personalrestaurantguide.model.RestaurantDao;

public class giRestaurantDetailsActivity extends AppCompatActivity {

    private EditText nameEditText, addressEditText, phoneEditText, descriptionEditText, tagsEditText;
    private Button saveButton, directionsButton, shareButton, deleteButton;
    private RatingBar ratingBar;
    private RestaurantDao restaurantDao;
    private Restaurant currentRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        // Initialize Room Database
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "restaurant-database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        restaurantDao = db.restaurantDao();

        // Initialize Views
        nameEditText = findViewById(R.id.restaurant_name);
        addressEditText = findViewById(R.id.restaurant_address);
        phoneEditText = findViewById(R.id.restaurant_phone);
        descriptionEditText = findViewById(R.id.restaurant_description);
        tagsEditText = findViewById(R.id.restaurant_tags);
        ratingBar = findViewById(R.id.restaurant_rating_bar);
        saveButton = findViewById(R.id.save_button);
        directionsButton = findViewById(R.id.get_directions_button);
        shareButton = findViewById(R.id.share_button);
        deleteButton = findViewById(R.id.delete_button);

        // Get the restaurant ID passed from the previous activity
        int restaurantId = getIntent().getIntExtra("restaurant_id", -1);
        currentRestaurant = restaurantDao.getRestaurantById(restaurantId);

        if (currentRestaurant != null) {
            populateFields(currentRestaurant);
        }

        // Set click listeners
        saveButton.setOnClickListener(v -> saveRestaurant());
        directionsButton.setOnClickListener(v -> openGoogleMaps());
        shareButton.setOnClickListener(v -> shareRestaurant());
        deleteButton.setOnClickListener(v -> deleteRestaurant());
    }

    private void populateFields(Restaurant restaurant) {
        nameEditText.setText(restaurant.getName());
        addressEditText.setText(restaurant.getAddress());
        phoneEditText.setText(restaurant.getPhone());
        descriptionEditText.setText(restaurant.getDescription());
        tagsEditText.setText(restaurant.getTags());
        ratingBar.setRating(restaurant.getRating());
    }

    private void saveRestaurant() {
        if (currentRestaurant == null) {
            currentRestaurant = new Restaurant();
        }
        currentRestaurant.setName(nameEditText.getText().toString());
        currentRestaurant.setAddress(addressEditText.getText().toString());
        currentRestaurant.setPhone(phoneEditText.getText().toString());
        currentRestaurant.setDescription(descriptionEditText.getText().toString());
        currentRestaurant.setTags(tagsEditText.getText().toString());
        currentRestaurant.setRating((int) ratingBar.getRating());

        if (currentRestaurant.getId() == 0) {
            restaurantDao.insert(currentRestaurant);
        } else {
            restaurantDao.update(currentRestaurant);
        }

        Toast.makeText(this, "Restaurant saved!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void openGoogleMaps() {
        double latitude = currentRestaurant.getLatitude();
        double longitude = currentRestaurant.getLongitude();
        String uri = "google.navigation:q=" + latitude + "," + longitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private void shareRestaurant() {
        String details = "Restaurant Name: " + currentRestaurant.getName() + "\n" +
                "Address: " + currentRestaurant.getAddress() + "\n" +
                "Phone: " + currentRestaurant.getPhone() + "\n" +
                "Description: " + currentRestaurant.getDescription() + "\n" +
                "Tags: " + currentRestaurant.getTags() + "\n" +
                "Rating: " + currentRestaurant.getRating();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, details);
        startActivity(Intent.createChooser(intent, "Share via"));
    }

    private void deleteRestaurant() {
        if (currentRestaurant != null) {
            restaurantDao.deleteRestaurantById(currentRestaurant.getId());
            Toast.makeText(this, "Restaurant deleted!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "No restaurant to delete!", Toast.LENGTH_SHORT).show();
        }
    }
}
