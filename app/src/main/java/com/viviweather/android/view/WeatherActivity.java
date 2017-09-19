package com.viviweather.android.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.viviweather.android.R;
import com.viviweather.android.gson.Forecast;
import com.viviweather.android.gson.Weather;
import com.viviweather.android.service.AutoUpdateService;
import com.viviweather.android.util.Constant;
import com.viviweather.android.util.HttpUtil;
import com.viviweather.android.util.LogUtils;
import com.viviweather.android.util.Utility;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {


    @BindView(R.id.title_city)
    TextView mTitleCity;
    @BindView(R.id.title_update_time)
    TextView mTitleUpdateTime;
    @BindView(R.id.degree_text)
    TextView mDegreeText;
    @BindView(R.id.weather_info_text)
    TextView mWeatherInfoText;
    @BindView(R.id.forecast_layout)
    LinearLayout mForecastLayout;
    @BindView(R.id.aqi_text)
    TextView mAqiText;
    @BindView(R.id.pm25_text)
    TextView mPm25Text;
    @BindView(R.id.comfort_text)
    TextView mComfortText;
    @BindView(R.id.car_wash_text)
    TextView mCarWashText;
    @BindView(R.id.sport_text)
    TextView mSportText;
    @BindView(R.id.weather_layout)
    ScrollView mWeatherLayout;
    @BindView(R.id.bg_pic_iv)
    ImageView mPic;
    @BindView(R.id.swipe_refresh)
    public SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.nav_button)
    Button mNavButton;
    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;
    private String mWeatherId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_weather);
        ButterKnife.bind(this);

        mSwipeRefresh.setColorSchemeResources(R.color.colorPrimary);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = sp.getString("weather", null);
        String pic = sp.getString("pic", null);
        if (weatherString != null) {
            // 如果有缓存直接解析天气数据
            Weather weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.basic.weatherId;
            LogUtils.d("WeatherActivity","缓存 mWeatherId = " + mWeatherId);
            showWeatherInfo(weather);
        } else {
            // 没有缓存,去服务器查询天气状况
            mWeatherId = getIntent().getStringExtra("weather_id");
            mWeatherLayout.setVisibility(View.INVISIBLE);
            requestWeater(mWeatherId);
            LogUtils.d("WeatherActivity","getIntent mWeatherId = " + mWeatherId);
        }
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeater(mWeatherId);
                LogUtils.d("WeatherActivity","Refresh mWeatherId = " + mWeatherId);
            }
        });

        if (pic != null) {
            Glide.with(this).load(pic).into(mPic);
        } else {
            loadPicture();
        }
    }

    //    @OnClick(R.id.nav_button)
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.nav_button:
//                mDrawerLayout.openDrawer(GravityCompat.START);
//                break;
//        }
//    }
    @OnClick(R.id.nav_button)
    public void onClick() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    /**
     * 根据天气id从服务器请求查询城市的天气信息
     */
    public void requestWeater(final String weatherId) {
        LogUtils.d("WeatherActivity","requestWeater = " + weatherId);
        String url = Constant.WEATHER_URL + weatherId + Constant.WEATHER_KEY;
        HttpUtil.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                String s = response.body().toString();
                final Weather weather = Utility.handleWeatherResponse(responseText);
                LogUtils.e("WeatherActivity","Weather [ responseText ]>>" + responseText);
                LogUtils.e("WeatherActivity","Weather [ s ] >>" + s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText).apply();
                            mWeatherId = weather.basic.weatherId;
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        mSwipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取网络失败", Toast.LENGTH_SHORT).show();
                        mSwipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
        loadPicture();
    }

    /**
     * 展示Weather实体类中的数据
     */
    private void showWeatherInfo(Weather weather) {
        if (weather != null && "ok".equals(weather.status)) {
            String cityName = weather.basic.cityName;
            String updateTime = weather.basic.update.updateTime.split(" ")[1];
//        String updateTime = weather.basic.update.updateTime.split(" ")[1];
            String degree = weather.now.temperature + "℃";
            String weatherInfo = weather.now.mMore.info;

            mTitleCity.setText(cityName);
            mTitleUpdateTime.setText(updateTime);
            mDegreeText.setText(degree);
            mWeatherInfoText.setText(weatherInfo);
            mForecastLayout.removeAllViews();
            for (Forecast forecast : weather.forecastList) {
                View view = LayoutInflater.from(this).inflate(R.layout.forecast_item,
                        mForecastLayout, false);

                TextView dateText = (TextView) view.findViewById(R.id.date_text);
                TextView infoText = (TextView) view.findViewById(R.id.info_text);
                TextView maxText = (TextView) view.findViewById(R.id.max_text);
                TextView minText = (TextView) view.findViewById(R.id.min_text);

                dateText.setText(forecast.date);
                infoText.setText(forecast.more.info);
                maxText.setText(forecast.temperature.max);
                minText.setText(forecast.temperature.min);
                mForecastLayout.addView(view);
            }
            if (weather.aqi != null) {
                mAqiText.setText(weather.aqi.city.aqi);
                mPm25Text.setText(weather.aqi.city.pm25);
            }
            String comfort = "舒适度:  " + weather.suggestion.mComfort.info;
            String carwash = "洗车指数:  " + weather.suggestion.mCarWash.info;
            String sport = "运动建议:  " + weather.suggestion.sport.info;
            mComfortText.setText(comfort);
            mCarWashText.setText(carwash);
            mSportText.setText(sport);
            mWeatherLayout.setVisibility(View.VISIBLE);

            Intent intent = new Intent(this, AutoUpdateService.class);
            startService(intent);
        } else {
            Toast.makeText(WeatherActivity.this, "获取天气信息失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPicture() {
        HttpUtil.sendOkHttpRequest(Constant.BG_PICTURE, new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String pic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                editor.putString("pic", pic).apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(pic).into(mPic);
                    }
                });
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
