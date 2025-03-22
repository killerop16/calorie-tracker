DROP TABLE IF EXISTS users, food_item, meal, meal_food_item CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    age INT,
    weight DECIMAL(5,2) CHECK (weight > 0),
    height DECIMAL(5,2) CHECK (height > 0),
    goal VARCHAR(20),
    daily_calories INT
    );

CREATE TABLE IF NOT EXISTS food_item (
     id SERIAL PRIMARY KEY,
     name VARCHAR(100) NOT NULL,
    calories_per_serving INT NOT NULL CHECK (calories_per_serving > 0),
    proteins DECIMAL(5,2) CHECK (proteins >= 0),
    fats DECIMAL(5,2) CHECK (fats >= 0),
    carbohydrates DECIMAL(5,2) CHECK (carbohydrates >= 0)
    );

CREATE TABLE IF NOT EXISTS meal (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    meal_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS meal_food_item (
    meal_id INT REFERENCES meal(id) ON DELETE CASCADE,
    food_id INT REFERENCES food_item(id) ON DELETE CASCADE,
    quantity INT default 1,
    PRIMARY KEY (meal_id, food_id)
    );
