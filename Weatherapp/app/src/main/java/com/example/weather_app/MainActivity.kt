package com.example.weather_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather_app.client.WeatherClient
import com.example.weather_app.models.CurrentWeather
import com.example.weather_app.ui.theme.WeatherappTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import com.example.weather_app.models.Location
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

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
                //WeatherDisplay(location = "Chicago") // Replace with your desired location
                //array more locations
                val locations = listOf("Chicago", "Seoul", "New York", "London")
                MultiLocationWeatherDisplay(locations)
            }
        }
    }
    // For various locations to display
    @Composable
    fun MultiLocationWeatherDisplay(locations: List<String>) {
        LazyColumn {
            items(locations) { location ->
                var weatherData by remember { mutableStateOf<CurrentWeather?>(null) }
                var loading by remember { mutableStateOf(true) }
                var errorMessage by remember { mutableStateOf<String?>(null) }

                // LaunchedEffect to fetch weather data for each location
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
                    Text(text = "Error: $errorMessage for location: $location")
                } else if (weatherData != null) {
                    WeatherCard(weather = weatherData!!)
                } else {
                    Text(text = "No weather data available for $location")
                }
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
            WeatherCard(weather = weatherData!!)
            //WeatherInfo(weather = weatherData!!)
        } else {
            // Unexpected case
            Text(text = "No weather data available.")
        }
    }

    @Composable
    fun WeatherCard(weather: CurrentWeather) {
        // TODO: Card implementation
        Log.d("WeatherCard", "Displaying weather for: ${weather.location.name}") // add log
        val iconResId = when (weather.current.condition.text.lowercase()) {
            "sunny", "clear" -> R.drawable.sunny  // various condition value
            "cloudy", "partly cloudy", "overcast" -> R.drawable.cloudy
            "rain", "light rain", "heavy rain" -> R.drawable.rainy
            "snow", "light snow", "heavy snow" -> R.drawable.snowy
            else -> R.drawable.default_icon  //
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // add image
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(64.dp)
                )

                // location informaton
                Text(text = "Location: ${weather.location.name}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Temperature: ${weather.current.temp_c}°C", style = MaterialTheme.typography.headlineSmall)
                Text(text = "Condition: ${weather.current.condition.text}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Wind Speed: ${weather.current.wind_kph} km/h", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Precipitation: ${weather.current.precip_mm} mm", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Local Time: ${weather.location.localtime}", style = MaterialTheme.typography.bodySmall)
            }
        }
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

@Composable
fun WeatherInfo(weather: CurrentWeather) {
    Column {
        Text(text = "Location: ${weather.location.name}", style = MaterialTheme.typography.titleLarge)
        Text(text = "Temperature: ${weather.current.temp_c}°C", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Condition: ${weather.current.condition.text}", style = MaterialTheme.typography.bodyMedium)
        // TODO: You can add more fields as needed
        Text(text = "Wind Speed: ${weather.current.wind_kph} km/h", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Precipitation: ${weather.current.precip_mm} mm", style = MaterialTheme.typography.bodyMedium)
    }
}