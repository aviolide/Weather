package com.example.desided.weather.inetConnect;

import com.example.desided.weather.inetConnect.pojo.WeatherResp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("weather?")
    Call<WeatherResp> getWeatherByLocation(@Query("lat") String lat,
                                           @Query("lon") String lon,
                                           @Query("units") String units,
                                           @Query("APPID") String apiid);

    @GET("weather?")
    Call<WeatherResp> getWeatherByCity(@Query("id") String id,
                                       @Query("units") String units,
                                       @Query("APPID") String apiid);


}
