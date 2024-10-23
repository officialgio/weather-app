package com.example.weather_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
<<<<<<< HEAD
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import com.example.weather_app.client.WeatherClient
import com.example.weather_app.models.CurrentWeather
import com.example.weather_app.ui.theme.WeatherappTheme
=======
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
>>>>>>> SAMPLES/main
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
    fun MultiLocationWeatherDisplay(locations: List<String>) {
        Column {
            // App name & description with improved header
            AppHeader()
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
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
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
    }

<<<<<<< HEAD
    @Composable
    fun AppHeader() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color(0xFFBBDEFB)) // 하늘색 배경
                .shadow(8.dp) // 그림자 효과 추가
        ) {
            Image(
                painter = painterResource(id = R.drawable.weather_background), // 배경 이미지 리소스
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color(0xFF0288D1) // 그라데이션 효과
                            )
                        )
                    )
            ) {
                Text(
                    text = "Weather Now",
                    style = MaterialTheme.typography.displayLarge,  // 더 큰 제목 스타일
                    color = Color.White
                )
                Text(
                    text = "Check real-time weather information!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.7f), // 반투명 텍스트
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
=======
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
>>>>>>> SAMPLES/main
        }
    }

    @Composable
    fun WeatherCard(weather: CurrentWeather) {
<<<<<<< HEAD
        val iconResId = when (weather.current.condition.text.lowercase()) {
            "sunny", "clear" -> R.drawable.sunny
            "cloudy", "partly cloudy", "overcast" -> R.drawable.cloudy
            "rain", "light rain", "heavy rain", "patchy rain nearby" -> R.drawable.rainy
            "snow", "light snow", "heavy snow" -> R.drawable.snowy
            else -> R.drawable.default_icon
        }

        val locationDisplay = if (weather.location.country == "South Korea") {
            "${weather.location.name}, South Korea"
        } else {
            "${weather.location.name}, ${weather.location.country}"
=======
        // TODO: Card implementation
        Log.d("WeatherCard", "Displaying weather for: ${weather.location.name}") // add log
        val iconResId = when (weather.current.condition.text.lowercase()) {
            "sunny", "clear" -> R.drawable.sunny  // various condition value
            "cloudy", "partly cloudy", "overcast" -> R.drawable.cloudy
            "rain", "light rain", "heavy rain" -> R.drawable.rainy
            "snow", "light snow", "heavy snow" -> R.drawable.snowy
            else -> R.drawable.default_icon  //
>>>>>>> SAMPLES/main
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
<<<<<<< HEAD
                .padding(16.dp)
                .shadow(6.dp, shape = RoundedCornerShape(16.dp)), // 그림자 추가 및 둥근 모서리
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.85f)  // 약간의 투명도 추가
            )
        ) {
            Row( // Row로 아이콘과 텍스트를 수평으로 배치
                verticalAlignment = Alignment.CenterVertically, // 수직 중앙 정렬
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = iconResId),
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(72.dp) // 아이콘 크기 조정
                        .offset(y = (-45).dp)
                )

                Spacer(modifier = Modifier.width(16.dp)) // 아이콘과 텍스트 사이 여백 추가

                Column(
                    horizontalAlignment = Alignment.Start, // 텍스트 왼쪽 정렬
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = locationDisplay,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "${weather.current.temp_c}°C",
                        style = MaterialTheme.typography.displaySmall, // 온도 텍스트를 크게 표시
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = weather.current.condition.text,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Wind Speed: ${weather.current.wind_kph} km/h",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Precipitation: ${weather.current.precip_mm} mm",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "Local Time: ${weather.location.localtime}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
=======
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
>>>>>>> SAMPLES/main
            }
        }
    }


<<<<<<< HEAD

=======
>>>>>>> SAMPLES/main
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
=======

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
>>>>>>> SAMPLES/main
