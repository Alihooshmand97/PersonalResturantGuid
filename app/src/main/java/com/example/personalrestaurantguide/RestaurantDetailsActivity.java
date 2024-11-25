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
    private boolean isMapReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);


        nameEditText = findViewById(R.id.restaurant_name);
        addressEditText = findViewById(R.id.restaurant_address);
        phoneEditText = findViewById(R.id.restaurant_phone);
        descriptionEditText = findViewById(R.id.restaurant_description);
        tagsEditText = findViewById(R.id.restaurant_tags);
        saveButton = findViewById(R.id.save_button);
        getDirectionsButton = findViewById(R.id.get_directions_button);


        saveButton.setOnClickListener(v -> saveRestaurantData());


        getDirectionsButton.setOnClickListener(v -> openGoogleMaps());


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        loadRestaurantData();


        Button backToHomeButton = findViewById(R.id.back_to_home_button);

        backToHomeButton.setOnClickListener(v -> {
            Intent intent = new Intent(RestaurantDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });
    }


    private void saveRestaurantData() {
        String name = nameEditText.getText().toString();
        String address = addressEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        String tags = tagsEditText.getText().toString();


        if (name.isEmpty() || address.isEmpty() || phone.isEmpty() || description.isEmpty() || tags.isEmpty()) {
            Toast.makeText(this, "All fields must be filled in!", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("RestaurantData", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        editor.putString("name", name);
        editor.putString("address", address);
        editor.putString("phone", phone);
        editor.putString("description", description);
        editor.putString("tags", tags);


        editor.apply();


        double latitude = 40.748817;
        double longitude = -73.985428;
        updateMapMarker(latitude, longitude);

        Toast.makeText(this, "Restaurant data saved!", Toast.LENGTH_SHORT).show();
    }


    private void updateMapMarker(double latitude, double longitude) {
        if (mMap != null) {
            LatLng restaurantLocation = new LatLng(latitude, longitude);
            mMap.clear();
            mMap.addMarker(new MarkerOptions().position(restaurantLocation).title("Restaurant Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));
        }
    }


    private void loadRestaurantData() {
        SharedPreferences sharedPreferences = getSharedPreferences("RestaurantData", MODE_PRIVATE);

        String name = sharedPreferences.getString("name", "No Name");
        String address = sharedPreferences.getString("address", "No Address");
        String phone = sharedPreferences.getString("phone", "No Phone");
        String description = sharedPreferences.getString("description", "No Description");
        String tags = sharedPreferences.getString("tags", "No Tags");


        nameEditText.setText(name);
        addressEditText.setText(address);
        phoneEditText.setText(phone);
        descriptionEditText.setText(description);
        tagsEditText.setText(tags);


        if (isMapReady) {
            addRestaurantMarker();
        }
    }


    private void addRestaurantMarker() {

        double latitude = 40.748817;
        double longitude = -73.985428;

        LatLng restaurantLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(restaurantLocation).title("Restaurant Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        isMapReady = true;


        if (mMap != null) {

            LatLng restaurantLocation = new LatLng(40.748817, -73.985428);
            mMap.addMarker(new MarkerOptions().position(restaurantLocation).title("Restaurant Location"));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15));
        }
    }


    private void openGoogleMaps() {
        double latitude = 40.748817;
        double longitude = -73.985428;

        String uri = "google.navigation:q=" + latitude + "," + longitude;


        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setPackage("com.google.android.apps.maps");


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {

            Toast.makeText(this, "Google Maps is not available", Toast.LENGTH_SHORT).show();
        }
    }
}