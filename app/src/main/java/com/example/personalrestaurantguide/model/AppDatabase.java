package com.example.personalrestaurantguide.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Restaurant.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RestaurantDao restaurantDao();
}
