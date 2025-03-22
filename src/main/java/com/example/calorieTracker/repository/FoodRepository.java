package com.example.calorieTracker.repository;

import com.example.calorieTracker.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<FoodItem, Long> {
    Optional<FoodItem> findByName(String name);
}
