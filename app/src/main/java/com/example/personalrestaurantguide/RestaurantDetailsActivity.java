package com.example.personalrestaurantguide;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RestaurantDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView restaurantName, restaurantDescription, restaurantAddress, restaurantPhone;
    private Button viewMapButton, getDirectionsButton;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        restaurantName = findViewById(R.id.restaurant_name);
        restaurantDescription = findViewById(R.id.restaurant_description);
        restaurantAddress = findViewById(R.id.restaurant_address);
        restaurantPhone = findViewById(R.id.restaurant_phone);
        viewMapButton = findViewById(R.id.view_map_button);
        getDirectionsButton = findViewById(R.id.get_directions_button);

        // Get the restaurant name from the intent
        String name = getIntent().getStringExtra("restaurant_name");
        restaurantName.setText(name);

        // Dummy data for now (replace with actual data)
        restaurantDescription.setText("Delicious food with a great ambiance.");
        restaurantAddress.setText("123 Tasty St, Food City");
        restaurantPhone.setText("(123) 456-7890");

        // Initialize the map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        // Set up listeners for buttons
        viewMapButton.setOnClickListener(v -> {
            // You can add logic to open the full-screen map here
        });

        // Handle click for getting directions
        getDirectionsButton.setOnClickListener(v -> {
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
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Example: Add a marker at the restaurant's location (use actual lat/long)
        LatLng restaurantLocation = new LatLng(40.748817, -73.985428); // Example coordinates (NYC)
        mMap.addMarker(new MarkerOptions().position(restaurantLocation).title("Restaurant Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(restaurantLocation, 15)); // Zoom in on the location
    }
}
