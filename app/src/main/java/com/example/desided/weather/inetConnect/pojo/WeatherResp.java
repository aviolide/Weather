package com.example.desided.weather.inetConnect.pojo;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WeatherResp {

    @SerializedName("weather")
    public List<Weather> mWeather;

    @SerializedName("wind")
    public Wind mWind;

    @SerializedName("main")
    public Main mMain;

    @SerializedName("name")
    public String name;

    public class Weather {
        @SerializedName("main")
        public String main;
    }

    public class Wind {
        @SerializedName("speed")
        String speed;
        @SerializedName("deg")
        String deg;
    }
    public class Main {
        @SerializedName("temp")
        String temp;
        @SerializedName("pressure")
        String pressure;
        @SerializedName("humidity")
        String humidity;
    }

    public WeatherResp(List<Weather> weather, Wind wind, Main main) {
        mWeather = weather;
        mWind = wind;
        mMain = main;
    }

    public String getWeather(){
        return mWeather.get(0).main;
    }

    public String getName() {
        return name;
    }

    public String getHumidity(){
        return mMain.humidity +" %";
    }

    public String getWind(){
        return mWind.speed + " m/sec";
    }

    public String getTemp(){
        return String.valueOf(new BigDecimal(mMain.temp).intValue());
    }

    public String getDate(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE MMM dd", Locale.ENGLISH);
        return simpleDateFormat.format(date);
    }
}
