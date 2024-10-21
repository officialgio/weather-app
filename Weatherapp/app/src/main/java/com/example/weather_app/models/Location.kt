package com.example.weather_app.models

data class Location(
    val name: String,         // City name
    val region: String,       // Region or state
    val country: String,      // Country
    val lat: Double,          // Latitude
    val lon: Double,          // Longitude
    val localtime: String     // Local time in the city
)