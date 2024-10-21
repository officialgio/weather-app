package com.example.weather_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.weather_app.client.WeatherClient
import com.example.weather_app.models.CurrentWeather
import com.example.weather_app.ui.theme.WeatherappTheme

class MainActivity : ComponentActivity() {

    private lateinit var weatherClient: WeatherClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the WeatherClient with your API key
        val apiKey = "db5c154a96d84b27ab1232250242010" // Make sure to replace with your actual API key
        weatherClient = WeatherClient(apiKey)

        // Setting up the UI
        setContent {
            WeatherappTheme {
                // Call the weather display composable
                WeatherDisplay(location = "Chicago") // Replace with your desired location
            }
        }
    }

    @Composable
    fun WeatherDisplay(location: String) {
        var weatherData by remember { mutableStateOf<CurrentWeather?>(null) }
        var loading by remember { mutableStateOf(true) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        LaunchedEffect(location) {
            loading = true
            fetchCurrentWeather(location) { weather, error ->
                weatherData = weather
                errorMessage = error
                loading = false
            }
        }

        if (loading) {
            CircularProgressIndicator()
        } else if (errorMessage != null) {
            // Display error message
            Text(text = "Error: $errorMessage")
        } else if (weatherData != null) {
            // Display weather data
            WeatherInfo(weather = weatherData!!)
        } else {
            // Unexpected case
            Text(text = "No weather data available.")
        }
    }

    @Composable
    fun WeatherCard(weather: CurrentWeather) {
        // TODO: Card implementation
    }

    private fun fetchCurrentWeather(location: String, onResult: (CurrentWeather?, String?) -> Unit) {
        weatherClient.fetchCurrentWeather(location) { weather, error ->
            if (weather != null) {
                Log.d("MainActivity", "Fetched weather data for ${weather.location.name}")
                onResult(weather, null) // Pass the weather data
            } else {
                Log.e("MainActivity", "Error fetching weather: $error")
                onResult(null, error) // Pass the error message
            }
        }
    }
}

<<<<<<< HEAD
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WeatherappTheme {
        Greeting("Android")
=======


@Composable
fun WeatherInfo(weather: CurrentWeather) {
    Column {
        Text(text = "Location: ${weather.location.name}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Temperature: ${weather.current.temp_c}°C", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Condition: ${weather.current.condition.text}", style = MaterialTheme.typography.bodyMedium)

        // TODO: You can add more fields as needed
>>>>>>> SAMPLES/main
    }
}