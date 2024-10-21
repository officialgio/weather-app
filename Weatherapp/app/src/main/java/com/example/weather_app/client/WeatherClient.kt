package com.example.weather_app.client

import android.util.Log
import com.example.weather_app.models.CurrentWeather
import com.example.weather_app.service.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherClient(private val apiKey: String) {
    fun fetchCurrentWeather(location: String, onResult: (CurrentWeather?, String?) -> Unit) {
        RetrofitInstance.api.getCurrentWeather(apiKey, location).enqueue(object : Callback<CurrentWeather> {
            override fun onResponse(call: Call<CurrentWeather>, response: Response<CurrentWeather>) {
                if (response.isSuccessful) {
                    val weather = response.body()
                    if (weather != null) {
                        Log.d("WeatherClient", "Location: ${weather.location.name}, Temperature: ${weather.current.temp_c}°C")
                        onResult(weather, null) // Pass the weather data back
                    } else {
                        Log.d("WeatherClient", "Error: Response body is null")
                        onResult(null, "Response body is null") // Pass an error message
                    }
                } else {
                    Log.d("WeatherClient", "Error: ${response.message()}")
                    onResult(null, "Error: ${response.message()}") // Pass the error message
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.d("WeatherClient", "Failure: ${t.message}")
                onResult(null, "Failure: ${t.message}") // Pass the error message
            }
        })
    }
}
