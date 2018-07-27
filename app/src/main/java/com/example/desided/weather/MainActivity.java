package com.example.desided.weather;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.desided.weather.GPS.Gps;
import com.example.desided.weather.inetConnect.ApiInterface;
import com.example.desided.weather.inetConnect.Connection;
import com.example.desided.weather.inetConnect.pojo.WeatherResp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity {

    private static final String KEY = "55601e455e6eec3872bd4a9f81ca3ce9";
    private static final String IdLondon = "2643741";
    private static final String IdParis = "2988507";
    private static final String IdNewYork = "5128581";
    private static final String IdTokyo = "1850147";
    private ImageButton mButtonLondon;
    private ImageButton mButtonParis;
    private ImageButton mButtonCurrentLocation;
    private ImageButton mButtonTokyo;
    private ImageButton mButtonNewYork;
    private TextView mTextViewCurrentLocation;
    private TextView mTextViewTodayDate;
    private TextView mTextViewHumidity;
    private TextView mTextViewWind;
    private TextView mTextViewDegree;
    private TextView mTextViewWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            Window window = this.getWindow();
            window.setStatusBarColor(this.getColor(R.color.back));
        }
        setViews();
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION}, 1);

        setOnClickListeners();
        getGps();

    }


    void setViews(){
        mButtonCurrentLocation = findViewById(R.id.button_corrent);
        mButtonLondon = findViewById(R.id.button_london);
        mButtonNewYork = findViewById(R.id.button_ny);
        mButtonParis = findViewById(R.id.button_paris);
        mButtonTokyo = findViewById(R.id.button_tokyo);
        mTextViewCurrentLocation = findViewById(R.id.current_location);
        mTextViewTodayDate = findViewById(R.id.today_date);
        mTextViewHumidity = findViewById(R.id.humidity);
        mTextViewWind = findViewById(R.id.wind);
        mTextViewDegree = findViewById(R.id.degrees);
        mTextViewWeather = findViewById(R.id.weather_main);
    }

    void setOnClickListeners(){
        onClickListener(mButtonCurrentLocation);
        onClickListener(mButtonLondon);
        onClickListener(mButtonNewYork);
        onClickListener(mButtonParis);
        onClickListener(mButtonTokyo);
    }

    void onClickListener(ImageButton imageButton){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.button_corrent:
                        getGps();
                        break;
                    case R.id.button_london:
                        getWeatherByCity(IdLondon);
                        break;
                    case R.id.button_ny:
                        getWeatherByCity(IdNewYork);
                        break;
                    case R.id.button_paris:
                        getWeatherByCity(IdParis);
                        break;
                    case R.id.button_tokyo:
                        getWeatherByCity(IdTokyo);
                        break;
                }
            }
        });
    }

    void getGps(){
        Gps gps = new Gps(this);
        if (gps.isCanGetLocation()){
            String lat = String.valueOf(gps.getLatitude());
            String lon = String.valueOf(gps.getLongitude());
            getDataCurrentLocation(lat, lon);
        }
    }

    void getDataCurrentLocation(String lat, String lon){
        ApiInterface apiInterface = Connection.getConnect().create(ApiInterface.class);

        Call<WeatherResp> call = apiInterface.getWeatherByLocation(lat, lon, "metric", KEY);
        call.enqueue(new Callback<WeatherResp>() {
            @Override
            public void onResponse(Call<WeatherResp> call, Response<WeatherResp> response) {
                WeatherResp weatherResp = response.body();
                setTextOnView(weatherResp);
            }

            @Override
            public void onFailure(Call<WeatherResp> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    void getWeatherByCity(String city){
        ApiInterface apiInterface = Connection.getConnect().create(ApiInterface.class);
        Call<WeatherResp> call = apiInterface.getWeatherByCity(city, "metric", KEY);
        call.enqueue(new Callback<WeatherResp>() {
            @Override
            public void onResponse(Call<WeatherResp> call, Response<WeatherResp> response) {
                WeatherResp weatherResp = response.body();
                setTextOnView(weatherResp);
            }

            @Override
            public void onFailure(Call<WeatherResp> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    void setTextOnView(WeatherResp weatherResp){
        mTextViewCurrentLocation.setText(weatherResp.getName());
        mTextViewWind.setText(weatherResp.getWind());
        mTextViewTodayDate.setText(weatherResp.getDate());
        mTextViewHumidity.setText(weatherResp.getHumidity());
        mTextViewDegree.setText(weatherResp.getTemp());
        mTextViewWeather.setText(weatherResp.getWeather());
    }
}
