package com.example.weather_app.models

data class CurrentWeather(
    val location: Location,             // Location details
    val current: CurrentWeatherDetails  // Current weather details
)

data class CurrentWeatherDetails(
    val temp_c: Double,               // Temperature in Celsius
    val condition: Condition,         // Weather condition like "Sunny", "Rain"
    val wind_kph: Double,             // Wind speed in km/h
    val humidity: Int,                // Humidity in percentage
    val feelslike_c: Double           // Feels-like temperature
)
