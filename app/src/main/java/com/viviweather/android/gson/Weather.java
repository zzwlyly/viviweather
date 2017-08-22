package com.viviweather.android.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *
 * 类名:   Weather
 *
 * 创建者:   zzw
 *
 * 创建时间:  2017/8/22 11:34
 *
 * 描述：    天气预报详细信息
 *
 */

public class Weather {

    public String status;

    public Basic basic;

    public AQI aqi;

    public Now now;

    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
