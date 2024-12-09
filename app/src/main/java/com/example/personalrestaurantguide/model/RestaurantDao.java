package com.example.personalrestaurantguide.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RestaurantDao {

    @Insert
    void insert(Restaurant restaurant);

    @Update
    void update(Restaurant restaurant);

    @Delete
    void delete(Restaurant restaurant);

    @Query("DELETE FROM restaurant_table WHERE id = :id")
    void deleteRestaurantById(int id);

    @Query("DELETE FROM restaurant_table")
    void deleteAllRestaurants();

    @Query("SELECT * FROM restaurant_table WHERE name LIKE :query OR tags LIKE :query")
    List<Restaurant> searchRestaurants(String query);

    @Query("SELECT * FROM restaurant_table WHERE id = :id")
    Restaurant getRestaurantById(int id);

    @Query("SELECT EXISTS(SELECT 1 FROM restaurant_table WHERE name = :name LIMIT 1)")
    boolean doesRestaurantExist(String name);

    @Query("SELECT * FROM restaurant_table")
    List<Restaurant> getAllRestaurants();
}
