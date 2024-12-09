package com.example.personalrestaurantguide;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delay for 3 seconds and then move to HomeActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // After the delay, move to HomeActivity
                Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Finish SplashActivity so that it's removed from the back stack
            }
        }, 3000); // 3000 milliseconds = 3 seconds
    }
}
