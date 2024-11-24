package com.example.personalrestaurantguide;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestaurantDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private EditText nameEditText, addressEditText, phoneEditText, descriptionEditText, tagsEditText;
    private Button saveButton, getDirectionsButton;
    private GoogleMap mMap;
    private boolean isMapReady = false;  // Flag to check if the map is ready

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        // Initialize EditTexts and buttons
        nameEditText = findViewById(R.id.restaurant_name);
        addressEditText = findViewById(R.id.restaurant_address);
        phoneEditText = findViewById(R.id.restaurant_phone);
        descriptionEditText = findViewById(R.id.restaurant_description);
        tagsEditText = findViewById(R.id.restaurant_tags);
        saveButton = findViewById(R.id.save_button);
        getDirectionsButton = findViewById(R.id.get_directions_button);

        // Set up the save button
        saveButton.setOnClickListener(v -> saveRestaurantData());

        // Set up the get directions button
        getDirectionsButton.setOnClickListener(v -> openGoogleMaps());

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Load saved data when the activity starts
        loadRestaurantData();
    }

    // Save restaurant data to SharedPreferences
    private void saveRestaurantData() {
        String name = nameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String tags = tagsEditText.getText().toString();

        // Validate that none of the fields are empty
        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || description.isEmpty() || tags.isEmpty()) {
            Toast.makeText(this, "All fields must be filled in!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("RestaurantData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save data to SharedPreferences
        editor.putString("name", name);
        editor.putString("address", address);
        editor.putString("phone", phone);
        editor.putString("description", description);
        editor.putString("tags", tags);

        // Commit changes
        editor.apply();

        // Update map with the new location (if latitude and longitude are available)
        double latitude = 40.748817;  // Example latitude (use actual latitude)
        double longitude = -73.985428; // Example longitude (use actual longitude)
        updateMapMarker(latitude, longitude);

        Toast.makeText(this, "com.example.personalrestaurantguide.Restaurant data saved!", Toast.LENGTH_SHORT).show();
    }

    // Update map marker with the given latitude and longitude
    private void updateMapMarker(double latitude, double longitude) {
        if (mMap != null) {
            LatLng restaurantLocation = new LatLng(latitude, longitude);
            mMap.clear(); // Clear any previous markers
            mMap.addMarker(new MarkerOptions().position(restaurantLocation).title("com.example.personalrestaurantguide.Restaurant Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));
        }
    }

    // Load restaurant data from SharedPreferences
    private void loadRestaurantData() {
        SharedPreferences sharedPreferences = getSharedPreferences("RestaurantData", MODE_PRIVATE);

        // Retrieve saved data
        String name = sharedPreferences.getString("name", "No Name");
        String address = sharedPreferences.getString("address", "No Address");
        String phone = sharedPreferences.getString("phone", "No Phone");
        String description = sharedPreferences.getString("description", "No Description");
        String tags = sharedPreferences.getString("tags", "No Tags");

        // Display data in EditText fields
        nameEditText.setText(name);
        addressEditText.setText(address);
        phoneEditText.setText(phone);
        descriptionEditText.setText(description);
        tagsEditText.setText(tags);

        // Only add the marker if the map is ready
        if (isMapReady) {
            addRestaurantMarker();
        }
    }

    // Add a marker to the map
    private void addRestaurantMarker() {
        // Example coordinates (replace with real lat/long from SharedPreferences)
        double latitude = 40.748817;
        double longitude = -73.985428;

        LatLng restaurantLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(restaurantLocation).title("com.example.personalrestaurantguide.Restaurant Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15)); // Zoom in on the location
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        isMapReady = true;

        // Add a marker only when the map is ready
        if (mMap != null) {
            // Example: Add a marker at the restaurant's location
            LatLng restaurantLocation = new LatLng(40.748817, -73.985428); // Use real coordinates
            mMap.addMarker(new MarkerOptions().position(restaurantLocation).title("com.example.personalrestaurantguide.Restaurant Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15)); // Zoom in on the location
        }
    }

    // Open Google Maps for directions
    private void openGoogleMaps() {
        // Get the latitude and longitude of the restaurant (replace with actual values)
        double latitude = 40.748817;  // Example latitude (replace with the actual one)
        double longitude = -73.985428; // Example longitude (replace with the actual one)

        // Create a Uri with the Google Maps directions URL
        String uri = "google.navigation:q=" + latitude + "," + longitude;

        // Create an Intent to open Google Maps
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");

        // Check if there's an app to handle the intent (Google Maps)
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent); // Start the Google Maps activity
        } else {
            // Handle the case where Google Maps is not available
            Toast.makeText(this, "Google Maps is not available", Toast.LENGTH_SHORT).show();
        }
    }
}
